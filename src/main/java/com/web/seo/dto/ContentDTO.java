package com.web.seo.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContentDTO {

    @Schema(description = "Title of the content", example = "My New Content", required = true)
    private String title;

    @Schema(description = "Body of the content", example = "This is the content body", required = true)
    private String body;

    @Schema(description = "Source of the content", example = "Internet", required = true)
    private String source;

    @Schema(description = "Category of the content", example = "Category Content", required = true)
    private String category;

    @Schema(description = "Tag of the content", example = "Tag Content", required = true)
    private String tag;
}
