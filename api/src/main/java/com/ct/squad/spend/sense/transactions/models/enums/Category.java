package com.ct.squad.spend.sense.transactions.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Category {
    ESSENTIALS,
    TRANSPORTATION,
    LEISURE,
    TRAVEL,
    SUBSCRIPTIONS,
    MISC,
    INCOME;

    @JsonCreator
    public static Category fromString(String value) {
        if (value == null) return null;
        return Category.valueOf(value.toUpperCase());
    }
}
