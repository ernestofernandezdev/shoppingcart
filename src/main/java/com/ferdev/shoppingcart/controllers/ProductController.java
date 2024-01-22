package com.ferdev.shoppingcart.controllers;

import com.ferdev.shoppingcart.dtos.ProductDto;
import com.ferdev.shoppingcart.services.ProductService;
import com.ferdev.shoppingcart.utils.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public ResponseEntity<List<ProductDto>> getProducts(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                                        @RequestParam(name = "minPrice", defaultValue = "0") int minPrice,
                                                        @RequestParam(name = "maxPrice", defaultValue = "2147483647") int maxPrice
                                                        ) {
        Filter filter = new Filter(keyword, minPrice, maxPrice);
        List<ProductDto> products = this.productService.getAllProducts(filter);

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable(name = "id") int id) {
        ProductDto product = this.productService.getProductById(id);

        return ResponseEntity.ok(product);
    }

}
