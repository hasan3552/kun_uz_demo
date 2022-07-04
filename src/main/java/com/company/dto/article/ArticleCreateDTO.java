package com.company.dto.article;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class ArticleCreateDTO {

    @NotNull
    @Size(min = 10, max = 255, message = "About Me must be between 10 and 255 characters")
    private String title;

    @NotBlank(message = "Mazgi description qani")
    private String description;

    @NotBlank(message = "Mazgi content qani")
    private String content;

    private Integer regionId;
    private Integer categoryId;

    private List<Integer> typesList;
    private List<String> tagList;

}
