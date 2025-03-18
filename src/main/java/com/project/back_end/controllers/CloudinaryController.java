package com.project.back_end.controllers;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.project.back_end.responses.ResponseObject;
import com.project.back_end.services.cloudinary.CloudinaryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/cloudinary")
@Tag(name = "Cloudinary API", description = "APIs for managing images in cloudinary")
public class CloudinaryController {
    private final CloudinaryService cloudinaryService;

    public CloudinaryController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @Operation(summary = "Upload an iamge to cloudinary", description = "Push single file to cloudinary.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Upload successful"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/upload")
    public ResponseEntity<ResponseObject> uploadSingleFileToSpecificFolder(
            @RequestParam("file") MultipartFile file,
            @RequestParam("folderName") String folderName
            ) {
        try {
            String imageUrl = cloudinaryService.uploadImageToFolder(file, folderName);
            return ResponseEntity.ok(ResponseObject.builder()
                    .data(imageUrl)
                    .message("Image uploaded successfully")
                    .status(String.valueOf(HttpStatus.SC_OK))
                    .build());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(ResponseObject.builder()
                    .message(e.getMessage())
                    .status(String.valueOf(HttpStatus.SC_BAD_REQUEST))
                    .build());
        }
    }

    @Operation(summary = "Upload multiple images to cloudinary", description = "Push multiple file to cloudinary.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Upload successful"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/upload/multiple")
    public ResponseEntity<ResponseObject> uploadMultipleFilesToSpecificFolder(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("folderName") String folderName
            ) {
        try {
            List<String> imageUrls = cloudinaryService.uploadMultipleImagesToFolder(files, folderName);
            return ResponseEntity.ok(ResponseObject.builder()
                    .data(imageUrls)
                    .message("Images uploaded successfully")
                    .status(String.valueOf(HttpStatus.SC_OK))
                    .build());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(ResponseObject.builder()
                    .message(e.getMessage())
                    .status(String.valueOf(HttpStatus.SC_BAD_REQUEST))
                    .build());
        }
    }


    @Operation(summary = "Delete an image from cloudinary", description = "Delete an image from cloudinary.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Delete successful"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseObject> deleteImage(
            @RequestParam("imageUrl") String imageUrl
            ) {
        try {
            cloudinaryService.deleteImage(imageUrl);
            return ResponseEntity.ok(ResponseObject.builder()
                    .message("Image deleted successfully")
                    .status(String.valueOf(HttpStatus.SC_OK))
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(ResponseObject.builder()
                    .message(e.getMessage())
                    .status(String.valueOf(HttpStatus.SC_BAD_REQUEST))
                    .build());
        }
    }

}
