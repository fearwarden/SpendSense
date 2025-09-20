package com.ct.squad.spend.sense.transactions.services.implementation;

import com.ct.squad.spend.sense.transactions.dto.request.CreateTransactionDto;
import com.ct.squad.spend.sense.transactions.models.Transaction;
import com.ct.squad.spend.sense.transactions.models.enums.Category;
import com.ct.squad.spend.sense.transactions.models.enums.Subcategory;
import com.ct.squad.spend.sense.transactions.repositories.TransactionRepository;
import com.ct.squad.spend.sense.transactions.services.TransactionService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;

    private final TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(CreateTransactionDto body) {
        Transaction transaction = modelMapper.map(body, Transaction.class);
        transaction.setCategory(Category.ESSENTIALS);
        transaction.setSubcategory(Subcategory.GROCERIES);
        return transactionRepository.save(transaction);
    }

    @Override
    public void saveAll(List<Transaction> transactions) {
        try {
            transactionRepository.saveAll(transactions);
            entityManager.flush(); // force writing to the db
            entityManager.clear(); // clear the context memory
        } catch (Exception ignored) {}
    }

    @Override
    public Long countData() {
        return transactionRepository.count();
    }
}
