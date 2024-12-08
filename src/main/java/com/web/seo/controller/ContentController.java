package com.web.seo.controller;

import com.web.seo.entity.Content;
import com.web.seo.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private ContentService contentService;

    @GetMapping("/{id}")
    public Content getContent(@PathVariable Long id) {
        contentService.incrementViews(id);
        return contentService.getContentById(id);
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        System.out.println("Uploading image of size: " + file.getSize() + " bytes");
        Content content = contentService.getContentById(id);
//            content.setImage(file.getBytes());
        contentService.saveContent(content);
        return ResponseEntity.ok("Image uploaded successfully");
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<String> getImage(@PathVariable Long id) {
        Content content = contentService.getContentById(id);
        if (content.getImage() != null) {
//            String base64Image = Base64.getEncoder().encodeToString(content.getImage());
//            return ResponseEntity.ok("data:image/jpeg;base64," + base64Image);
        }
        return ResponseEntity.notFound().build();
    }
}
