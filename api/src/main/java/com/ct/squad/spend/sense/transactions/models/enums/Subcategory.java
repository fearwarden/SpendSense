package com.ct.squad.spend.sense.transactions.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Subcategory {
    GROCERIES(Category.ESSENTIAL),
    UTILITIES(Category.ESSENTIAL),
    PHARMACY(Category.ESSENTIAL),
    PUBLIC_TRANSPORT(Category.TRANSPORTATION),
    FUEL(Category.TRANSPORTATION),
    TAXI(Category.TRANSPORTATION),
    RESTAURANTS_TAKEAWAY(Category.LEISURE),
    ENTERTAINMENT(Category.LEISURE),
    SHOPPING(Category.LEISURE);

    private final Category category;
}
