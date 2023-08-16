package com.expensetracker.sheaexpensetracker.repository;

import com.expensetracker.sheaexpensetracker.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT * FROM transaction WHERE user = :user AND date = :date", nativeQuery = true)
    List<Transaction> getTodaysTransactions(String user, LocalDate date);

    @Query(value = "SELECT * FROM transaction WHERE user = :user AND date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Transaction> getTransactionsByDates(String user, LocalDate startDate, LocalDate endDate);

}
