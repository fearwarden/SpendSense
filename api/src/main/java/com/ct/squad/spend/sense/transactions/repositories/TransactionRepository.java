package com.ct.squad.spend.sense.transactions.repositories;

import com.ct.squad.spend.sense.transactions.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query(value = """
            SELECT YEAR(t.transactionDate) AS year,
                   MONTH(t.transactionDate) AS month,
                   SUM(t.amount) AS total
            FROM Transaction t
            WHERE (YEAR(t.transactionDate) > :startYear
                   OR (YEAR(t.transactionDate) = :startYear AND MONTH(t.transactionDate) >= :startMonth))
              AND (YEAR(t.transactionDate) < :endYear
                   OR (YEAR(t.transactionDate) = :endYear AND MONTH(t.transactionDate) <= :endMonth))
              AND t.direction = :direction
            GROUP BY YEAR(t.transactionDate), MONTH(t.transactionDate)
            ORDER BY YEAR(t.transactionDate), MONTH(t.transactionDate)
            """)
    List<Object[]> getMonthlyInfoRange(@Param("startYear") int startYear,
                                       @Param("startMonth") int startMonth,
                                       @Param("endYear") int endYear,
                                       @Param("endMonth") int endMonth,
                                       @Param("direction") int direction);


}
