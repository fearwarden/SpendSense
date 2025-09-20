package com.ct.squad.spend.sense.transactions.services.implementation;

import com.ct.squad.spend.sense.transactions.dto.response.MonthlyStatsDto;
import com.ct.squad.spend.sense.transactions.models.enums.Category;
import com.ct.squad.spend.sense.transactions.repositories.TransactionRepository;
import com.ct.squad.spend.sense.transactions.services.TransactionAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionAnalyticsServiceImpl implements TransactionAnalyticsService {

    private final TransactionRepository transactionRepository;

    @Override
    public Map<Category, Double> getMonthlySpendingByCategory() {
        Map<Category, Double> map = new EnumMap<>(Category.class);

        for(Category category : Category.values()) {
            Double value = transactionRepository.getMonthlySpendingByCategory(category, getFirstDayOfMonth());
            if(value == null) value = 0d;
            map.put(category, value);
        }

        return map;
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

    @Override
    public Long countData() {
        return transactionRepository.count();
    }

    private Date getFirstDayOfMonth() {
        LocalDate firstDay = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        return Date.from(firstDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
