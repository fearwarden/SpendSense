package com.ct.squad.spend.sense.transactions.services;

import com.ct.squad.spend.sense.transactions.dto.request.CreateTransactionDto;
import com.ct.squad.spend.sense.transactions.models.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface TransactionService {

    Transaction createTransaction(CreateTransactionDto body) throws JsonProcessingException;

    void saveAll(List<Transaction> transactions);
}
