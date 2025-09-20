package com.ct.squad.spend.sense.transactions.services;

import com.ct.squad.spend.sense.transactions.dto.response.MonthlyStatsDto;
import com.ct.squad.spend.sense.transactions.models.enums.Category;

import java.util.List;
import java.util.Map;

public interface TransactionAnalyticsService {
    Map<Category, Double> getMonthlySpendingByCategory();

    List<MonthlyStatsDto> yearlySpent();

    Long countData();

}
