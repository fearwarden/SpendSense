package com.ct.squad.spend.sense.transactions.repositories;

import com.ct.squad.spend.sense.transactions.models.Transaction;
import com.ct.squad.spend.sense.transactions.models.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.category = :category AND t.transactionDate >= :after")
    Double getMonthlySpendingByCategory(Category category, Date after);
}
