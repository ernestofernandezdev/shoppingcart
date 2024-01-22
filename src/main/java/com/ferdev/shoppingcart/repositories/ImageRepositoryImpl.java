package com.ferdev.shoppingcart.repositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Repository
public class ImageRepositoryImpl implements ImageRepository {

    @Value("${images.path}")
    private String imagesPath;

    @Override
    public byte[] find(String fullName) throws IOException {
        String fullPath = this.imagesPath + "/" + fullName;

        return Files.readAllBytes(Paths.get(fullPath));
    }

}
