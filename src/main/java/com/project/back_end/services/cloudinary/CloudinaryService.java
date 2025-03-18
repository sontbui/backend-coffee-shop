package com.project.back_end.services.cloudinary;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
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
}
