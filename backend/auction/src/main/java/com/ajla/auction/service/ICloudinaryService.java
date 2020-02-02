package com.ajla.auction.service;

import java.io.IOException;
import java.util.Map;

public interface ICloudinaryService {
    String saveProfileImage(String urlImage, Long userId) throws IOException;
}
