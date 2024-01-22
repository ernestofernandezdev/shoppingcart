package com.ferdev.shoppingcart.services;

import com.ferdev.shoppingcart.entities.CartLine;
import com.ferdev.shoppingcart.entities.Product;
import com.ferdev.shoppingcart.exceptions.ExistingProductCartLineException;
import com.ferdev.shoppingcart.exceptions.ProductNotFoundException;
import com.ferdev.shoppingcart.repositories.CartLineRepository;
import com.ferdev.shoppingcart.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartLineServiceImpl implements CartLineService {

    private CartLineRepository cartLineRepository;
    private ProductRepository productRepository;

    public CartLineServiceImpl(CartLineRepository cartLineRepository, ProductRepository productRepository) {
        this.cartLineRepository = cartLineRepository;
        this.productRepository = productRepository;
    }


    @Override
    public List<CartLine> getAllCartLines() {
        return this.cartLineRepository.findAll();
    }

    @Override
    public void createCartLine(CartLine cartLine, int productId) {
        Optional<Product> optionalProduct = this.productRepository.findById(productId);
        System.out.println("Hola");

        if (optionalProduct.isPresent()) {
            System.out.println("Pedimos la cart line");
            List<CartLine> list = this.cartLineRepository.findByProductId(productId);
            if (list.isEmpty()) {
                cartLine.setProduct(optionalProduct.get());
                this.cartLineRepository.save(cartLine);
            } else {
                throw new ExistingProductCartLineException("A cart line with this product already exists");
            }
        } else {
            throw new ProductNotFoundException("There is no product with id " + productId);
        }
    }

    @Override
    public void updateCartLineById(CartLine updatedCartLine, int productId) {
        Optional<CartLine> optionalCartLine = this.cartLineRepository.findById(updatedCartLine.getId());

        if (optionalCartLine.isPresent()) {
            Optional<Product> optionalProduct = productRepository.findById(productId);
            if (optionalProduct.isPresent()) {
                updatedCartLine.setProduct(optionalProduct.get());
                this.cartLineRepository.save(updatedCartLine);
            } else {
                throw new ProductNotFoundException("There is no product with id " + productId);
            }
        } else {
            throw new ProductNotFoundException("There is no product with id " + updatedCartLine.getId());
        }

    }

    @Override
    public void deleteCartLineById(int id) {
        Optional<CartLine> optionalCartLine = this.cartLineRepository.findById(id);

        if (optionalCartLine.isPresent()) {
            this.cartLineRepository.delete(optionalCartLine.get());
        } else {
            throw new ProductNotFoundException("There is no product with id " + id);
        }
    }

    @Override
    public void deleteAllCartLines() {
        this.cartLineRepository.deleteAll();
    }
}
