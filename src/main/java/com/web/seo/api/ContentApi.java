package com.web.seo.api;

import com.web.seo.dto.ContentDTO;
import com.web.seo.entity.Content;
import com.web.seo.service.ContentService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ContentApi {
    @Autowired
    private ContentService contentService;

    @GetMapping("/{id}")
    public Content getContent(@PathVariable Long id) {
        return contentService.getContentById(id);
    }

    @GetMapping("/")
    public List<Content> getAllContent() {
        return contentService.getContentAll();
    }

    @RequestMapping(path = "/", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createContent(@ApiParam(name = "file", required = true)
                                           @RequestPart("file") MultipartFile file,
                                           @ModelAttribute ContentDTO contentDTO) throws IOException {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is required");
            }

            // Panggil service untuk membuat konten baru
            Content contentUpload = contentService.createContent(contentDTO, file);

            return ResponseEntity.status(HttpStatus.CREATED).body(contentUpload);
        } catch (EntityNotFoundException e) {
            // Jika id tidak ditemukan
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Content not found");
        } catch (Exception e) {
            // Menangkap pengecualian umum lainnya
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred");
        }
    }
}
