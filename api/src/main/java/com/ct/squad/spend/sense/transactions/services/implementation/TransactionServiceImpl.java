package com.ct.squad.spend.sense.transactions.services.implementation;

import com.ct.squad.spend.sense.commons.dto.ClassificationDto;
import com.ct.squad.spend.sense.commons.dto.ClassifyDto;
import com.ct.squad.spend.sense.commons.http.AgentService;
import com.ct.squad.spend.sense.transactions.dto.request.CreateTransactionDto;
import com.ct.squad.spend.sense.transactions.dto.response.MonthlyStatsDto;
import com.ct.squad.spend.sense.transactions.models.Transaction;
import com.ct.squad.spend.sense.transactions.repositories.TransactionRepository;
import com.ct.squad.spend.sense.transactions.services.TransactionService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public Long countData() {
        return transactionRepository.count();
    }

    @Override
    public List<MonthlyStatsDto> yearlySpent() {
        LocalDate now = LocalDate.now();
        YearMonth current = YearMonth.from(now);
        YearMonth start = current.minusMonths(11);

        List<Object[]> resultsOfMonthlyIncome = transactionRepository.getMonthlyInfoRange(
                start.getYear(), start.getMonthValue(),
                current.getYear(), current.getMonthValue(),
                1
        );
        List<Object[]> resultsOfMonthlyOutcome = transactionRepository.getMonthlyInfoRange(
                start.getYear(), start.getMonthValue(),
                current.getYear(), current.getMonthValue(),
                2
        );

        Map<YearMonth, Double> incomeMap = resultsOfMonthlyIncome.stream()
                .collect(Collectors.toMap(
                        row -> YearMonth.of(((Number) row[0]).intValue(), ((Number) row[1]).intValue()),
                        row -> ((Number) row[2]).doubleValue()
                ));

        Map<YearMonth, Double> outcomeMap = resultsOfMonthlyOutcome.stream()
                .collect(Collectors.toMap(
                        row -> YearMonth.of(((Number) row[0]).intValue(), ((Number) row[1]).intValue()),
                        row -> ((Number) row[2]).doubleValue()
                ));

        List<MonthlyStatsDto> months = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            YearMonth ym = start.plusMonths(i);
            String monthName = ym.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            months.add(new MonthlyStatsDto(
                    monthName,
                    incomeMap.getOrDefault(ym, 0.0),
                    outcomeMap.getOrDefault(ym, 0.0)
            ));
        }

        return months;
    }
}
