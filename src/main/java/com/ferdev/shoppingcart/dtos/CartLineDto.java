package com.ferdev.shoppingcart.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartLineDto {

    @NotNull
    private Integer amount;

    @NotNull
    private Integer productId;

}
