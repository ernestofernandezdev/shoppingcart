package com.ferdev.shoppingcart.utils;

import com.ferdev.shoppingcart.entities.Product;

import java.util.function.Predicate;

public class Filter implements Predicate<Product> {

    private String keyword;
    private int minPrice;
    private int maxPrice;

    public Filter(String keyword, int minPrice, int maxPrice) {
        this.keyword = keyword;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }


    @Override
    public boolean test(Product product) {
        return product.getPrice() >= this.minPrice &&
               product.getPrice() <= this.maxPrice &&
               (product.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                product.getDescription().toLowerCase().contains(keyword.toLowerCase()));
    }
}
