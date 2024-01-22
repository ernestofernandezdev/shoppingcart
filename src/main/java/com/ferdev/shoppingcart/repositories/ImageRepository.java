package com.ferdev.shoppingcart.repositories;

import java.io.IOException;

public interface ImageRepository {

    byte[] find(String fullName) throws IOException;

}
