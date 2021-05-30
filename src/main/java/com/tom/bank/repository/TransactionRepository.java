package com.tom.bank.repository;

import com.tom.bank.objects.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionHistory, Integer> {

    List<TransactionHistory> findAllByFromAccountContaining(String fromAccount);
}
