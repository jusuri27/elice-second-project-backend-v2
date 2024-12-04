package com.shoux_kream.category.controller;

import com.shoux_kream.category.dto.CategoryResponse;
import com.shoux_kream.category.service.CategoryServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/v2/category")
@RequiredArgsConstructor
public class CategoryControllerV2 {

    private final CategoryServiceV2 categoryServiceV2;

    @GetMapping("/add")
    public String addCategoryPage() {
        return "category/category-add"; }

    @GetMapping("/edit/{id}")
    public String editCategoryPage(){
        return "category/category-edit";
    }

    @GetMapping("/category-list")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return categoryServiceV2.getAllCategories();
    }

    @PostMapping("/category-add")
    public ResponseEntity<CategoryResponse> createCategory(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("themeClass") String themeClass,
            @RequestParam("image") MultipartFile imageFile) throws IOException {

        // 이미지 파일이 null 인지 확인
        if (imageFile == null || imageFile.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        // DTO 로 변환
        CategoryResponse categoryResponse = new CategoryResponse(title, description, themeClass);

        // 카테고리 생성 및 이미지 업로드
        CategoryResponse createdCategory = categoryServiceV2.createCategory(categoryResponse, imageFile);
        return ResponseEntity.ok(createdCategory);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable(name ="id") Long id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("themeClass") String themeClass,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {

        // 카테고리 DTO 생성
        CategoryResponse categoryResponse = new CategoryResponse(title, description, themeClass);

        // 카테고리 수정 및 이미지 업로드
        CategoryResponse updatedCategory = categoryServiceV2.updateCategory(id, categoryResponse, imageFile);

        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        return categoryServiceV2.deleteCategory(categoryId);
    }
}
