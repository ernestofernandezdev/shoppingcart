package com.ferdev.shoppingcart.services;

import com.ferdev.shoppingcart.exceptions.ImageNotFoundException;
import com.ferdev.shoppingcart.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    private ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public byte[] getImage(String imageFullName) {
        try {
            return this.imageRepository.find(imageFullName);
        } catch (IOException exc) {
            throw new ImageNotFoundException("The requested image does not exist.");
        }
    }

}
