package com.example.demo.dto;

public class BankAccount {
    private String upiId;
    private String accountNumber;
    private String ifsc;

    // Default constructor
    public BankAccount() {
    }

    // Parameterized constructor
    public BankAccount(String upiId, String accountNumber, String ifsc) {
        this.upiId = upiId;
        this.accountNumber = accountNumber;
        this.ifsc = ifsc;
    }

    // Getters and Setters
    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }
}
