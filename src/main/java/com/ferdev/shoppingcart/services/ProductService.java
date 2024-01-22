package com.ferdev.shoppingcart.services;

import com.ferdev.shoppingcart.dtos.ProductDto;
import com.ferdev.shoppingcart.entities.Product;
import com.ferdev.shoppingcart.utils.Filter;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAllProducts(Filter filter);

    ProductDto getProductById(int id);

}
