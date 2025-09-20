package com.ct.squad.spend.sense.transactions.services;

import com.ct.squad.spend.sense.transactions.models.enums.Category;

import java.util.Map;

public interface TransactionAnalyticsService {
    Map<Category, Double> getMonthlySpendingByCategory();
}
