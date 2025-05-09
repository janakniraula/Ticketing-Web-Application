package com.mytickets.ticketingApp.service.impl;

import com.mytickets.ticketingApp.model.Transaction;
import com.mytickets.ticketingApp.model.TransactionStatus;
import com.mytickets.ticketingApp.repository.TransactionRepository;
import com.mytickets.ticketingApp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public Optional<Transaction> getTransactionByNumber(String transactionNumber) {
        return transactionRepository.findByTransactionNumber(transactionNumber);
    }

    @Override
    public List<Transaction> getTransactionsByBuyer(Long buyerId) {
        return transactionRepository.findByBuyerId(buyerId);
    }

    @Override
    public List<Transaction> getTransactionsBySeller(Long sellerId) {
        return transactionRepository.findBySellerId(sellerId);
    }

    @Override
    public List<Transaction> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findByTransactionDateBetween(startDate, endDate);
    }

    @Override
    @Transactional
    public Transaction createTransaction(Transaction transaction) {
        // Set default values if not provided
        if (transaction.getTransactionDate() == null) {
            transaction.setTransactionDate(LocalDateTime.now());
        }

        if (transaction.getStatus() == null) {
            transaction.setStatus(TransactionStatus.PENDING);
        }

        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public Transaction updateTransactionStatus(Long id, TransactionStatus status) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));

        transaction.setStatus(status);
        return transactionRepository.save(transaction);
    }

    @Override
    public Double getTotalSalesByUser(Long userId) {
        Double total = transactionRepository.sumCompletedSalesByUser(userId);
        return total != null ? total : 0.0;
    }

    @Override
    public Double getTotalPurchasesByUser(Long userId) {
        Double total = transactionRepository.sumCompletedPurchasesByUser(userId);
        return total != null ? total : 0.0;
    }

    @Override
    @Transactional
    public Transaction processPayment(Long transactionId, String paymentMethod, String paymentDetails) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + transactionId));

        // In a real application, this would integrate with a payment gateway
        // For now, we'll just update the status
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setPaymentIntentId("PAYMENT_" + paymentMethod + "_" + System.currentTimeMillis());

        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public Transaction refundTransaction(Long transactionId, String reason) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + transactionId));

        // Check if transaction can be refunded
        if (transaction.getStatus() != TransactionStatus.COMPLETED) {
            throw new RuntimeException("Only completed transactions can be refunded");
        }

        // In a real application, this would integrate with a payment gateway for the refund
        // For now, we'll just update the status
        transaction.setStatus(TransactionStatus.REFUNDED);

        return transactionRepository.save(transaction);
    }
}