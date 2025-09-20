package com.ct.squad.spend.sense.transactions.services.implementation;

import com.ct.squad.spend.sense.transactions.models.Transaction;
import com.ct.squad.spend.sense.transactions.repositories.TransactionRepository;
import com.ct.squad.spend.sense.transactions.services.TransactionService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    @PersistenceContext
    private final EntityManager entityManager;

    private final TransactionRepository transactionRepository;

    @Override
    public void saveAll(List<Transaction> transactions) {
        try {
            transactionRepository.saveAll(transactions);
            entityManager.flush(); // force writing to the db
            entityManager.clear(); // clear the context memory
        } catch (Exception ignored) {}
    }
}
