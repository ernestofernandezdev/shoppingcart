package com.ferdev.shoppingcart.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private Integer id;

    private String name;

    private String description;

    private Double price;

    private String imageFullName;

    private boolean inTheCart;
}
