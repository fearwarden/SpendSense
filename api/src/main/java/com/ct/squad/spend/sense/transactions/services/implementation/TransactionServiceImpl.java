package com.ct.squad.spend.sense.transactions.services.implementation;

import com.ct.squad.spend.sense.commons.dto.ClassificationDto;
import com.ct.squad.spend.sense.commons.dto.ClassifyDto;
import com.ct.squad.spend.sense.commons.http.AgentService;
import com.ct.squad.spend.sense.transactions.dto.request.CreateTransactionDto;
import com.ct.squad.spend.sense.transactions.models.Transaction;
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
    private final AgentService agentService;

    @Override
    public Transaction createTransaction(CreateTransactionDto body) {
        modelMapper.typeMap(CreateTransactionDto.class, Transaction.class)
                .addMappings(
                        mapper ->
                                mapper.skip(Transaction::setId)
                );
        Transaction transaction = modelMapper.map(body, Transaction.class);
        ClassificationDto classify = agentService.classify(new ClassifyDto(transaction.toString()));
        transaction.setCategory(classify.getCategory());
        transaction.setSubcategory(classify.getSubcategory());
        return transactionRepository.save(transaction);
    }

    @Override
    public void saveAll(List<Transaction> transactions) {
        try {
            transactionRepository.saveAll(transactions);
            entityManager.flush(); // force writing to the db
            entityManager.clear(); // clear the context memory
        } catch (Exception ignored) {
        }
    }
}
