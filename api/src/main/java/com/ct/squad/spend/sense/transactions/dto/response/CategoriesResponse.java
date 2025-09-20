package com.ct.squad.spend.sense.transactions.dto.response;

import com.ct.squad.spend.sense.transactions.models.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoriesResponse {
    private Map<Category, Double> categories;
}
