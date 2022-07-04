package com.company.dto.article;

import com.company.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ArticleChangeStatusDTO {

    @NotBlank
    private ArticleStatus status;
}
