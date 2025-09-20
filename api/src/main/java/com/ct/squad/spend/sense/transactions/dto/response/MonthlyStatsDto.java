package com.ct.squad.spend.sense.transactions.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyStatsDto {
    private String month;
    private double totalIncome;
    private double totalOutcome;
}
