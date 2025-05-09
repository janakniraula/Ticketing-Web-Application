// src/main/java/com/mytickets/ticketingApp/controller/TransactionController.java
package com.mytickets.ticketingApp.controller;

import com.mytickets.ticketingApp.model.Transaction;
import com.mytickets.ticketingApp.model.TransactionStatus;
import com.mytickets.ticketingApp.security.services.UserDetailsImpl;
import com.mytickets.ticketingApp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id)
                .map(transaction -> new ResponseEntity<>(transaction, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/number/{transactionNumber}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Transaction> getTransactionByNumber(@PathVariable String transactionNumber) {
        return transactionService.getTransactionByNumber(transactionNumber)
                .map(transaction -> new ResponseEntity<>(transaction, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/my-purchases")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Transaction>> getMyPurchases() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        List<Transaction> transactions = transactionService.getTransactionsByBuyer(userDetails.getId());
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/my-sales")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Transaction>> getMySales() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        List<Transaction> transactions = transactionService.getTransactionsBySeller(userDetails.getId());
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/date-range")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Transaction>> getTransactionsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        List<Transaction> transactions = transactionService.getTransactionsByDateRange(startDate, endDate);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody Transaction transaction) {
        Transaction newTransaction = transactionService.createTransaction(transaction);
        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Transaction> updateTransactionStatus(
            @PathVariable Long id,
            @RequestParam TransactionStatus status) {

        Transaction updatedTransaction = transactionService.updateTransactionStatus(id, status);
        return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
    }

    @GetMapping("/my-total-sales")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Double>> getMyTotalSales() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        Double total = transactionService.getTotalSalesByUser(userDetails.getId());

        Map<String, Double> response = new HashMap<>();
        response.put("total", total);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/my-total-purchases")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Double>> getMyTotalPurchases() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        Double total = transactionService.getTotalPurchasesByUser(userDetails.getId());

        Map<String, Double> response = new HashMap<>();
        response.put("total", total);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{id}/process-payment")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Transaction> processPayment(
            @PathVariable Long id,
            @RequestParam String paymentMethod,
            @RequestParam(required = false) String paymentDetails) {

        Transaction processedTransaction = transactionService.processPayment(id, paymentMethod, paymentDetails);
        return new ResponseEntity<>(processedTransaction, HttpStatus.OK);
    }

    @PostMapping("/{id}/refund")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Transaction> refundTransaction(
            @PathVariable Long id,
            @RequestParam String reason) {

        Transaction refundedTransaction = transactionService.refundTransaction(id, reason);
        return new ResponseEntity<>(refundedTransaction, HttpStatus.OK);
    }
}