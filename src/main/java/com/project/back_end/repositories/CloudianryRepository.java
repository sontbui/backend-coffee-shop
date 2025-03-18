package com.project.back_end.repositories;

import org.springframework.stereotype.Repository;

@Repository
public class CloudianryRepository {
    

    public static boolean isImage(String url) {
        return url.endsWith(".jpg") || url.endsWith(".png") || url.endsWith(".jpeg");
    }
}
