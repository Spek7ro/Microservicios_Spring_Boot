package com.paymentchain.transactions.controllers;

import com.paymentchain.transactions.entities.Transaction;
import com.paymentchain.transactions.repository.TransactionRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transaction")
public class TransactionRestController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Operation(summary = "Lista de transacciones")
    @GetMapping
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Operation(summary = "Obtener una transacción por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getById(@PathVariable (name = "id") long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent()) {
            return new ResponseEntity<>(transaction.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Obtener las transacciones de un cliente")
    @GetMapping("/customer/transactions/")
    public List<Transaction> getByCustomer(@RequestParam String ibanAccount) {
        return transactionRepository.findByIbanAccount(ibanAccount);
    }

    @Operation(summary = "Crear una transacción")
    @PostMapping
    public ResponseEntity<Transaction> post(@RequestBody @Valid Transaction tx) {
        return ResponseEntity.ok(transactionRepository.save(tx));
    }

    @Operation(summary = "Actualizar una transacción")
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> put(@PathVariable (name = "id") long id, @RequestBody @Valid Transaction tx) {
       Transaction transactionFind = transactionRepository.findById(id).get();
       if (transactionFind != null) {
           transactionFind.setAmount(tx.getAmount());
           transactionFind.setChannel(tx.getChannel());
           transactionFind.setDate(tx.getDate());
           transactionFind.setDescription(tx.getDescription());
           transactionFind.setFee(tx.getFee());
           transactionFind.setIbanAccount(tx.getIbanAccount());
           transactionFind.setReference(tx.getReference());
           transactionFind.setStatus(tx.getStatus());
           return new ResponseEntity<>(transactionRepository.save(transactionFind), HttpStatus.OK);
       } else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }

    @Operation(summary = "Eliminar una transacción por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Transaction> delete(@PathVariable (name = "id") long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent()) {
            transactionRepository.delete(transaction.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
