package com.ferdev.shoppingcart.services;

import com.ferdev.shoppingcart.entities.CartLine;

import java.util.List;

public interface CartLineService {

    List<CartLine> getAllCartLines();

    void createCartLine(CartLine cartLine, int productId);

    void deleteCartLineById(int id);

    void updateCartLineById(CartLine cartLine, int productId);

    void deleteAllCartLines();

}
