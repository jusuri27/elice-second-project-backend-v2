package com.shoux_kream.category.dto;

import com.shoux_kream.category.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryResponse {

    private Long id;
    private String title;
    private String themeClass;
    private String description;
    private String imageUrl;


    public CategoryResponse(Category category) {
        this.id = category.getCategoryId();
        this.title = category.getTitle();
        this.description = category.getDescription();
        this.themeClass = category.getThemeClass();
        this.imageUrl = category.getImageUrl();
    }

    public CategoryResponse(String title, String description, String themeClass) {
        this.title = title;
        this.description = description;
        this.themeClass = themeClass;
    }

    public Category toEntity() {
        return new Category(title, description, themeClass, imageUrl);
    }
}
