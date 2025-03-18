package com.project.back_end.repositories;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.springframework.stereotype.Repository;

@Repository
public class CloudianryRepository {
    

    public static boolean isImage(String url) {
        return url.endsWith(".jpg") || url.endsWith(".png") || url.endsWith(".jpeg");
    }

    public boolean existingImage(String imageUrl) {
        try {
            URL url = URI.create(imageUrl).toURL();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK; 
        } catch (Exception e) {
            return false; 
        }
    }
}
