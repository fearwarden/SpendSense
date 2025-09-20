package com.ct.squad.spend.sense.transactions.services.implementation;

import com.ct.squad.spend.sense.transactions.models.enums.Category;
import com.ct.squad.spend.sense.transactions.repositories.TransactionRepository;
import com.ct.squad.spend.sense.transactions.services.TransactionAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

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

    private Date getFirstDayOfMonth() {
        LocalDate firstDay = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        return Date.from(firstDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
