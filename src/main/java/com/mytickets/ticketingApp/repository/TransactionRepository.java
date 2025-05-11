package com.mytickets.ticketingApp.repository;

import com.mytickets.ticketingApp.model.Transaction;
import com.mytickets.ticketingApp.model.TransactionStatus;
import com.mytickets.ticketingApp.model.TransactionType;
import com.mytickets.ticketingApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByBuyer(User buyer);

    List<Transaction> findByBuyerId(Long buyerId);

    List<Transaction> findBySeller(User seller);

    List<Transaction> findBySellerId(Long sellerId);

    List<Transaction> findByStatus(TransactionStatus status);

    List<Transaction> findByType(TransactionType type);

    Optional<Transaction> findByTransactionNumber(String transactionNumber);

    List<Transaction> findByTransactionDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.seller.id = ?1 AND t.status = 'COMPLETED'")
    Double sumCompletedSalesByUser(Long userId);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.buyer.id = ?1 AND t.status = 'COMPLETED'")
    Double sumCompletedPurchasesByUser(Long userId);
}