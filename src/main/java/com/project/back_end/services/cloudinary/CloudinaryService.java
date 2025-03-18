package com.project.back_end.services.cloudinary;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.project.back_end.controllers.UserAccountController;
import com.project.back_end.models.User;
import com.project.back_end.services.users.UserAccountService;
import com.project.back_end.utils.General;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CloudinaryService {
    private final int MAX_FILES = 5;
    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    // Upload single img to specific folder
    public String uploadImageToFolder(MultipartFile file, String folderName) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), 
            ObjectUtils.asMap("folder", folderName));
        return uploadResult.get("url").toString();
    }

    // Upload multiple file to specific folder
    public List<String> uploadMultipleImagesToFolder(MultipartFile[] files, String folderName) throws IOException {
        if(files.length > MAX_FILES) {
            throw new IOException("Exceed maximum number of files. Must be upload less than " + MAX_FILES + " files");
        }
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap("folder", folderName));
            imageUrls.add(uploadResult.get("url").toString());
        }
        return imageUrls;
    }

    public void deleteImage(String imageUrl) {
        try {
            if(imageUrl.equals(General.URL_AVATAR_DEFAULT_FEMALE) || 
            imageUrl.equals(General.URL_AVATAR_DEFAULT_LGBT) || 
            imageUrl.equals(General.URL_AVATAR_DEFAULT_MALE) || 
            imageUrl == null || 
            imageUrl.isEmpty()) {
                return;
            }
            if (imageUrl == null || imageUrl.isEmpty()) return;
            String publicId = extractPublicId(imageUrl);
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            System.out.println("Deleted image: " + publicId);
        } catch (Exception e) {
            System.err.println("Failed to delete image: " + e.getMessage());
        }
    }

    private String extractPublicId(String imageUrl) {
        try {
            String[] parts = imageUrl.split("/upload/");
            if (parts.length < 2) {
                return null;
            }
            String path = parts[1]; 
            String[] pathParts = path.split("/");
            StringBuilder publicId = new StringBuilder();
            for (int i = 0; i < pathParts.length; i++) {
                if (!pathParts[i].matches("^v\\d+$")) {
                    if (publicId.length() > 0) {
                        publicId.append("/");
                    }
                    publicId.append(pathParts[i]);
                }
            }
            int dotIndex = publicId.lastIndexOf(".");
            if (dotIndex != -1) {
                publicId.setLength(dotIndex); 
            }
    
            return publicId.toString();
        } catch (Exception e) {
            System.err.println("Error extracting public ID: " + e.getMessage());
            return null;
        }
    }
    
}
