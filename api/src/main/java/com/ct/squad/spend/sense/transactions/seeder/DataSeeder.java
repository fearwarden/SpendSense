package com.ct.squad.spend.sense.transactions.seeder;

import com.ct.squad.spend.sense.commons.properties.AppProperties;
import com.ct.squad.spend.sense.transactions.models.Transaction;
import com.ct.squad.spend.sense.transactions.services.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder {

    private final ObjectMapper objectMapper;
    private final AppProperties appProperties;
    private final TransactionService transactionService;

    @PostConstruct
    public void seedData() throws IOException {
        Resource resource = appProperties.getData();

        Transaction[] transactions = objectMapper.readValue(resource.getContentAsByteArray(), Transaction[].class);

        int batchSize = 100;

        for (int i = 0; i < transactions.length; i += batchSize) {
            int end = Math.min(i + batchSize, transactions.length);
            List<Transaction> batch = Arrays.asList(Arrays.copyOfRange(transactions, i, end));
            transactionService.saveAll(batch);
        }

    }
}
