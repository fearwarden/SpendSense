package com.ct.squad.spend.sense.commons.dto;

import com.ct.squad.spend.sense.transactions.models.enums.Category;
import com.ct.squad.spend.sense.transactions.models.enums.Subcategory;
import lombok.Data;

@Data
public class ClassificationDto {
    private Category category;
    private Subcategory subcategory;
}
