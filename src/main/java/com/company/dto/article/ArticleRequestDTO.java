package com.company.dto.article;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class ArticleRequestDTO {

    @NotBlank
    private List<String> idList;
}
