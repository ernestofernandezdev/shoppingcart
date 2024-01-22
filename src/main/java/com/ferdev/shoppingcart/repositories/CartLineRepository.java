package com.ferdev.shoppingcart.repositories;

import com.ferdev.shoppingcart.entities.CartLine;

import java.util.List;
import java.util.Optional;

public interface CartLineRepository {

    List<CartLine> findAll();

    Optional<CartLine> findById(int id);

    void save(CartLine cartLine);

    void delete(CartLine cartLine);

    void deleteAll();

    List<CartLine> findByProductId(int productId);

}
