package com.ferdev.shoppingcart.repositories;

import com.ferdev.shoppingcart.entities.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    Optional<Product> findById(int id);

}
