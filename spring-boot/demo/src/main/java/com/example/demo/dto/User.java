package com.example.demo.dto;

import java.util.List;
import java.util.ArrayList;

public class User {
    private String upiId;
    private double balance;
    private List<Transaction> transactionHistory = new ArrayList<>();

    // Getters and Setters
    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void setTransactionHistory(List<Transaction> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }
}
