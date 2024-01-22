package com.ferdev.shoppingcart.services;

import com.ferdev.shoppingcart.utils.Filter;
import com.ferdev.shoppingcart.utils.Mapper;
import com.ferdev.shoppingcart.dtos.ProductDto;
import com.ferdev.shoppingcart.entities.Product;
import com.ferdev.shoppingcart.exceptions.ProductNotFoundException;
import com.ferdev.shoppingcart.repositories.CartLineRepository;
import com.ferdev.shoppingcart.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CartLineRepository cartLineRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CartLineRepository cartLineRepository) {
        this.productRepository = productRepository;
        this.cartLineRepository = cartLineRepository;
    }

    @Override
    public List<ProductDto> getAllProducts(Filter filter) {
        return this.productRepository.findAll().stream()
                .filter(filter)
                .map(Mapper::toDto)
                .map(p -> {
                    p.setInTheCart(isInTheCart(p.getId()));
                    return p;
                })
                .toList();
    }

    @Override
    public ProductDto getProductById(int id) {
        Optional<Product> optionalProduct = this.productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            ProductDto product = Mapper.toDto(optionalProduct.get());
            product.setInTheCart(isInTheCart(product.getId()));
            return product;
        } else {
            throw new ProductNotFoundException("There is no product with id " + id);
        }
    }

    private boolean isInTheCart(int productId) {
        return !this.cartLineRepository.findByProductId(productId).isEmpty();
    }

}
