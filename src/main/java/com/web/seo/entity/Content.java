package com.web.seo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contents")
public class Content implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true, required = false)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String body;
    private String source;
    private String category;
    private String tag;
    private String image;
    @Column(name = "created_at")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm z", timezone = "Asia/Jakarta")
    @Schema(hidden = true, required = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm z", timezone = "Asia/Jakarta")
    @Schema(hidden = true, required = false)
    private LocalDateTime updatedAt;
    @ManyToOne
    @JsonIgnore
    private User user;
}
