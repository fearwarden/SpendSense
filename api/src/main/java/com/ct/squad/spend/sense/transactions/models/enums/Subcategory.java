package com.ct.squad.spend.sense.transactions.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Subcategory {
    GROCERIES(Category.ESSENTIALS),
    UTILITIES(Category.ESSENTIALS),
    PHARMACY(Category.ESSENTIALS),
    PUBLIC_TRANSPORT(Category.TRANSPORTATION),
    FUEL(Category.TRANSPORTATION),
    TAXI(Category.TRANSPORTATION),
    RESTAURANTS_TAKEAWAY(Category.LEISURE),
    ENTERTAINMENT(Category.LEISURE),
    SHOPPING(Category.LEISURE),
    MISC(Category.TRANSPORTATION);

    @JsonCreator
    public static Subcategory fromString(String value) {
        if (value == null) return null;
        return Subcategory.valueOf(value.toUpperCase());
    }

    private final Category category;
}
