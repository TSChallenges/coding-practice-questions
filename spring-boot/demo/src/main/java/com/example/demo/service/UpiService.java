package com.example.demo.service;

import com.example.demo.dto.BankAccount;
import com.example.demo.dto.Transaction;
import com.example.demo.dto.User;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UpiService {
    private final Map<String, User> users = new HashMap<>();
    private final Map<String, BankAccount> bankAccounts = new HashMap<>();
    private final List<Transaction> transactions = new ArrayList<>();

    public String addUser(String upiId, Double balance) {
        if (!isValidUpi(upiId)) {
            return "Invalid UPI format - " + upiId;
        }
        User user = new User();
        user.setUpiId(upiId);
        user.setBalance(balance);

        users.put(upiId, user);
        return "User added successfully - " + upiId;
    }

    public boolean isValidUpi(String upiId) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+$";
        return Pattern.matches(regex, upiId);
    }

    public String pay(String sender, String receiver, double amount) {
        if (!isValidUpi(sender) || !isValidUpi(receiver)) {
            return "Invalid UPI format.";
        }

        User senderUser  = users.get(sender);
        User receiverUser  = users.get(receiver);

        if (senderUser  == null || receiverUser  == null) {
            return "Sender or receiver does not exist.";
        }

        if (amount <= 0) {
            return "Amount must be greater than zero.";
        }

        if (senderUser.getBalance() < amount) {
            return "Insufficient balance.";
        }

        // Process payment
        senderUser.setBalance(senderUser.getBalance() - amount);
        receiverUser.setBalance(receiverUser.getBalance() + amount);

        // Record transaction
        Transaction transaction = new Transaction();
        transaction.setDate(LocalDateTime.now());
        transaction.setAmount(amount);
        transaction.setType("debit");
        transaction.setRecipient(receiver);
        senderUser.getTransactionHistory().add(transaction);
        transactions.add(transaction);

        transaction = new Transaction();
        transaction.setDate(LocalDateTime.now());
        transaction.setAmount(amount);
        transaction.setType("credit");
        transaction.setRecipient(sender);
        receiverUser.getTransactionHistory().add(transaction);
        transactions.add(transaction);

        return "Payment successful.";
    }

    public double getBalance(String upiId) {
        User user = users.get(upiId);
        return user != null ? user.getBalance() : 0;
    }

    public List<Transaction> getTransactionHistory(String upiId) {
        User user = users.get(upiId);
        return user != null ? user.getTransactionHistory() : Collections.emptyList();
    }

    public boolean checkFraud(String upiId) {
        List<Transaction> userTransactions = transactions.stream()
                .filter(t -> t.getRecipient().equals(upiId))
                .collect(Collectors.toList());

        // Check for more than 5 payments made within a minute to the same recipient
        Map<String, List<Transaction>> transactionsByRecipient = new HashMap<>();
        for (Transaction transaction : userTransactions) {
            transactionsByRecipient
                    .computeIfAbsent(transaction.getRecipient(), k -> new ArrayList<>())
                    .add(transaction);
        }

        for (List<Transaction> trans : transactionsByRecipient.values()) {
            long count = trans.stream()
                    .filter(t -> t.getDate().isAfter(LocalDateTime.now().minusMinutes(1)))
                    .count();
            if (count > 5) {
                return true; // Fraud detected
            }
        }

        // Check for 3 exact amount transfers in < 10 seconds
        for (int i = 0; i < userTransactions.size(); i++) {
            Transaction currentTransaction = userTransactions.get(i);
            int count = 1; // Start with the current transaction

            for (int j = i + 1; j < userTransactions.size(); j++) {
                Transaction nextTransaction = userTransactions.get(j);
                // Check if the amount is the same and within 10 seconds
                if (currentTransaction.getAmount() == nextTransaction.getAmount() &&
                        nextTransaction.getDate().isBefore(currentTransaction.getDate().plusSeconds(10))) {
                    count++;
                }

                // If we have found 3 transactions, return true
                if (count >= 3) {
                    return true; // Fraud detected
                }
            }
        }

        return false; // No fraud detected
    }

    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public void scheduledCashbackPayout() {
        for (User  user : users.values()) {
            user.setBalance(user.getBalance() + 50); // Credit ₹50 cashback
        }
    }

    public String linkBankAccount(String upiId, String accountNumber, String ifsc) {
        BankAccount account = new BankAccount();
        account.setUpiId(upiId);
        account.setAccountNumber(accountNumber);
        account.setIfsc(ifsc);
        bankAccounts.put(upiId, account);
        return "Bank account linked successfully.";
    }

    public Map<String, Object> getDailySummary() {
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalTransactions", transactions.size());
        summary.put("totalDebit", transactions.stream().filter(t -> t.getType().equals("debit")).mapToDouble(Transaction::getAmount).sum());
        summary.put("totalCredit", transactions.stream().filter(t -> t.getType().equals("credit")).mapToDouble(Transaction::getAmount).sum());

        // Count unique users
        Set<String> uniqueUsers = new HashSet<>();
        for (Transaction transaction : transactions) {
            uniqueUsers.add(transaction.getType()); // Assuming type is the UPI ID
        }
        summary.put("uniqueUsersToday", uniqueUsers.size());

        return summary;
    }

}