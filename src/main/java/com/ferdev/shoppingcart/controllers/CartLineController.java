package com.ferdev.shoppingcart.controllers;

import com.ferdev.shoppingcart.dtos.CartLineDto;
import com.ferdev.shoppingcart.entities.CartLine;
import com.ferdev.shoppingcart.services.CartLineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cartlines")
public class CartLineController {

    private CartLineService cartLineService;

    public CartLineController(CartLineService cartLineService) {
        this.cartLineService = cartLineService;
    }

    @GetMapping("")
    public ResponseEntity<List<CartLine>> getCartLines() {
        return ResponseEntity.ok(this.cartLineService.getAllCartLines());
    }

    @PostMapping("")
    public ResponseEntity<String> createCartLine(@RequestBody CartLineDto cartLineDto) {
        int productId = cartLineDto.getProductId();
        CartLine cartLine = CartLine.builder().amount(cartLineDto.getAmount()).build();

        this.cartLineService.createCartLine(cartLine, productId);

        return ResponseEntity.ok("Se creó la linea del carrito correctamente.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCartLine(@RequestBody CartLineDto cartLineDto,
                                                 @PathVariable(name = "id") Integer id) {

        CartLine cartLine = CartLine.builder().amount(cartLineDto.getAmount()).id(id).build();
        int productId = cartLineDto.getProductId();

        this.cartLineService.updateCartLineById(cartLine, productId);

        return ResponseEntity.ok("Se modificó la linea del carrito correctamente.");

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> updateCartLine(@PathVariable(name = "id") Integer id) {
        this.cartLineService.deleteCartLineById(id);

        return ResponseEntity.ok("Se eliminó la linea del carrito correctamente.");

    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteAll() {
        this.cartLineService.deleteAllCartLines();

        return ResponseEntity.ok("Se eliminaron todas las lineas del carrito.");

    }

}
