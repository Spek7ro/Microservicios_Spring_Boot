package com.paymentchain.transactions.controllers;

import com.paymentchain.transactions.entities.Transaction;
import com.paymentchain.transactions.repository.TransactionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController{

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Transaction> post(@RequestBody @Valid Transaction tx) {
        return ResponseEntity.ok(transactionRepository.save(tx));
    }

}
