package com.shoux_kream.category.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.shoux_kream.item.entity.Item;
import com.shoux_kream.timestamp.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Table(name = "category")
@Entity
@Getter
@NoArgsConstructor
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "themeClass")
    private String themeClass;

    @Column(name = "image_url")
    private String imageUrl;

    public Category(String title, String description, String themeClass, String imageUrl) {
        this.title = title;
        this.description = description;
        this.themeClass = themeClass;
        this.imageUrl = imageUrl;
    }


    public void updateCategory(String title, String description, String themeClass, String imageUrl) {
        this.title = title;
        this.description = description;
        this.themeClass = themeClass;
        this.imageUrl = imageUrl;
    }
}
