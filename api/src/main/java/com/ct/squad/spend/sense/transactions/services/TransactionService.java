package com.ct.squad.spend.sense.transactions.services;

import com.ct.squad.spend.sense.transactions.dto.request.CreateTransactionDto;
import com.ct.squad.spend.sense.transactions.models.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction createTransaction(CreateTransactionDto body);

    void saveAll(List<Transaction> transactions);

    Long countData();
}
