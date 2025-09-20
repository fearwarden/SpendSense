package com.ct.squad.spend.sense.transactions.models.enums;

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

    private final Category category;
}
