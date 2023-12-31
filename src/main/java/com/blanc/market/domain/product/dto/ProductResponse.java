package com.blanc.market.domain.product.dto;

import com.blanc.market.domain.ingredient.dto.IngredientDto;
import com.blanc.market.domain.product.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private int price;
    private String imageUrl;
    private String description;
    private int likeCount;
    private Set<IngredientDto> ingredients;
    private Category category;
}
