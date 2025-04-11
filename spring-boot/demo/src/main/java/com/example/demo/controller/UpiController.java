package com.example.demo.controller;

import com.example.demo.dto.User;
import com.example.demo.dto.BankAccount;
import com.example.demo.dto.Transaction;
import com.example.demo.service.UpiService;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UpiController {
    private final UpiService upiService;

    public UpiController(UpiService upiService) {
        this.upiService = upiService;
    }

    @PostMapping("/add-user")
    public String addUser(@RequestParam String upiId, @RequestParam double balance) {
        return upiService.addUser(upiId, balance);
    }

    @PostMapping("/pay")
    public String pay(@RequestParam String sender, @RequestParam String receiver, @RequestParam double amount) {
        return upiService.pay(sender, receiver, amount);
    }

    @GetMapping("/balance/{upiId}")
    public double getBalance(@PathVariable String upiId) {
        return upiService.getBalance(upiId);
    }

    @GetMapping("/transactions/{upiId}")
    public List<Transaction> getTransactionHistory(@PathVariable String upiId) {
        return upiService.getTransactionHistory(upiId);
    }

    @GetMapping("/fraud/check/{upiId}")
    public boolean checkFraud(@PathVariable String upiId) {
        return upiService.checkFraud(upiId);
    }

    @PostMapping("/link-account")
    public String linkBankAccount(@RequestBody BankAccount account) {
        return upiService.linkBankAccount(account.getUpiId(), account.getAccountNumber(), account.getIfsc());
    }

    @GetMapping("/summary/daily")
    public Map<String, Object> getDailySummary() {
        return upiService.getDailySummary();
    }
}
