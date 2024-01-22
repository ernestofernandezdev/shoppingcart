package com.ferdev.shoppingcart.utils;

import com.ferdev.shoppingcart.dtos.ProductDto;
import com.ferdev.shoppingcart.entities.Product;

import java.util.function.Function;

public class Mapper {
    public static ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageFullName(product.getImageFullName())
                .build();
    }
}
