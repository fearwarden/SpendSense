package com.ct.squad.spend.sense.transactions.services;

import com.ct.squad.spend.sense.transactions.models.Transaction;

import java.util.List;

public interface TransactionService {

    void saveAll(List<Transaction> transactions);
}
