package com.ajla.auction.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService implements ICloudinaryService {

    private final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "auctionabh",
            "api_key", "619132779487451",
            "api_secret", "I-zcYsn5xE8yNe851JBeF9Hamco"));

    @Override
    public String saveProfileImage(String urlImage, Long userId) throws IOException {
        Map params = ObjectUtils.asMap("public_id", "user_" + userId);
        Map uploadResult = cloudinary.uploader()
                .upload(urlImage, params);
        return (String) uploadResult.get("url");
    }
}
