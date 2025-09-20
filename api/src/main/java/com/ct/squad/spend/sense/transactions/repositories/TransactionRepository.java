package com.ct.squad.spend.sense.transactions.repositories;

import com.ct.squad.spend.sense.transactions.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
