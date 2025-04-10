import java.util.ArrayList;
import java.util.List;

class Transaction {
    private String senderId;
    private String receiverId;
    private double amount;

    public Transaction(String senderId, String receiverId, double amount) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction from " + senderId + " to " + receiverId + " of amount ₹" + amount;
    }
}

class User {
    private String userId;
    private double balance;
    private List<Transaction> transactionHistory;

    public User(String userId, double initialBalance) {
        this.userId = userId;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public double getBalance() {
        return balance;
    }

    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    public void deductBalance(double amount) {
        if (amount <= balance) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Insufficient balance");
        }
    }

    public void addBalance(double amount) {
        balance += amount;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}

class UPIService {
    public static void transfer(User sender, User receiver, double amount) {
        // Validate sufficient balance
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero");
        }

        // Deduct from sender's balance
        sender.deductBalance(amount);
        // Add to receiver's balance
        receiver.addBalance(amount);

        // Create transaction record
        Transaction transaction = new Transaction(sender.getUserId(), receiver.getUserId(), amount);
        // Log transaction in both users' histories
        sender.addTransaction(transaction);
        receiver.addTransaction(transaction);
    }
}

public class UPISimulation {
    public static void main(String[] args) {
        // Create users
        User user1 = new User("user1", 2000); // Initial balance of ₹2000
        User user2 = new User("user2", 1500); // Initial balance of ₹1500

        // Perform transactions
        try {
            UPIService.transfer(user1, user2, 500); // user1 transfers ₹500 to user2
            UPIService.transfer(user2, user1, 300); // user2 transfers ₹300 to user1
            UPIService.transfer(user1, user2, 2500); // This should throw an exception (insufficient balance)
        } catch (IllegalArgumentException e) {
            System.out.println("Transaction failed: " + e.getMessage());
        }

        // Print transaction histories
        System.out.println("User  1 Transaction History:");
        for (Transaction t : user1.getTransactionHistory()) {
            System.out.println(t);
        }

        System.out.println("\nUser  2 Transaction History:");
        for (Transaction t : user2.getTransactionHistory()) {
            System.out.println(t);
        }

        // Print final balances
        System.out.println("\nFinal Balances:");
        System.out.println("User  1 Balance: ₹" + user1.getBalance());
        System.out.println("User  2 Balance: ₹" + user2.getBalance());
    }
}
