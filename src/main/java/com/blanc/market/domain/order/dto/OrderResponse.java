package com.blanc.market.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.Mapping;

@Getter
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private int totalPrice;
}
