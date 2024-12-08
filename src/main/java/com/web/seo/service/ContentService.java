package com.web.seo.service;

import com.web.seo.dto.ContentDTO;
import com.web.seo.entity.Content;
import com.web.seo.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import com.web.seo.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ContentService {
    private static final Logger logger = LoggerFactory.getLogger(ContentService.class);
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private UserService userService;
    @Value("${upload.image.path}")
    private String uploadImage;
    @Value("${upload.image.before.path}")
    private String uploadImageBefore;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private Environment environment;

    public Content getContentById(Long id) {
        return contentRepository.findById(id).orElseThrow(() -> new RuntimeException("Content not found"));
    }

    public List<Content> getContentAll() {
        return contentRepository.findAll();
    }

    public void incrementViews(Long id) {
        Content content = getContentById(id);
        contentRepository.save(content);
    }

    public Content getLatestContent() {
        return contentRepository.findTopByOrderByIdDesc();  // Mengambil data terbaru
    }

    public Content saveContent(Content content) {
        return contentRepository.save(content);
    }

    public Content createContent(ContentDTO contentDTO, MultipartFile file) throws IOException {
        Content newContent = new Content();
        newContent.setBody(contentDTO.getBody());
        newContent.setCreatedAt(LocalDateTime.now());
        newContent.setUpdatedAt(LocalDateTime.now());
        newContent.setSource(contentDTO.getSource());
        newContent.setTitle(contentDTO.getTitle());
        newContent.setCategory(contentDTO.getCategory());
        newContent.setTag(contentDTO.getTag());
        saveContentImage(newContent, file);
        return saveContent(newContent);
    }

    public void saveContentImage(Content content, MultipartFile image) throws IOException {
        logger.info("Starting to save content image.");

        if (image == null || image.isEmpty()) {
            logger.error("Uploaded file is empty");
            throw new IllegalArgumentException("Uploaded file is empty");
        }

        String fileName = UUID.randomUUID() + "-" + image.getOriginalFilename();
        logger.info("Generated file name: {}", fileName);

        String uploadPath = Paths.get("src/main/resources/static/seo/images/content/").toAbsolutePath().toString();
        if (uploadPath == null) {
            logger.error("Upload path is not defined in resources");
            throw new IllegalStateException("Upload path is not defined in resources");
        }
        logger.info("Upload path: {}", uploadPath);

        String finalPath = uploadPath + File.separator + fileName;
        logger.info("Image is going to save @ {}", finalPath);

        File file = new File(finalPath).getAbsoluteFile();
        file.getParentFile().mkdirs(); // Buat direktori jika belum ada

        // Menyimpan file
        try (InputStream is = image.getInputStream();
             OutputStream out = new FileOutputStream(file)) {
            byte[] buf = new byte[1024];
            int len;
            while ((len = is.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            logger.info("File saved successfully.");
        } catch (Exception e) {
            logger.error("Error saving file: {}", e.getMessage(), e);
        }

        content.setImage("\\seo\\images\\content\\" + fileName);
        logger.info("Image path set to database: {}", content.getImage());
    }
}
