package com.ferdev.shoppingcart.controllers;

import com.ferdev.shoppingcart.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/{imageFullName}")
    public ResponseEntity<byte[]> getImage(@PathVariable(name = "imageFullName") String imageFullName) {
        byte[] image = this.imageService.getImage(imageFullName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(image.length);
        headers.setContentDispositionFormData("attachment", "imagen.jpg");

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

}
