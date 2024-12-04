package com.shoux_kream.category.service;

import com.shoux_kream.category.dto.CategoryResponse;
import com.shoux_kream.category.entity.Category;
import com.shoux_kream.category.repository.CategoryRepository;
import com.shoux_kream.config.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceV2 {

    private final CategoryRepository categoryRepository;
    private final S3Uploader s3Uploader;

    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<Category> categoriesEntity = categoryRepository.findAll();
        List<CategoryResponse> CategoryResponse = categoriesEntity.stream().map(CategoryResponse::new).collect(Collectors.toList());
        return new ResponseEntity<>(CategoryResponse, HttpStatus.OK);
    }

    @Transactional
    public CategoryResponse createCategory(CategoryResponse categoryResponse, MultipartFile imageFile) throws IOException {
        // 이미지 파일이 존재하면 S3에 업로드
        String imageUrl = s3Uploader.upload(imageFile, "category");

        // 카테고리 생성 및 저장
        Category category = new Category(categoryResponse.getTitle(), categoryResponse.getDescription(), categoryResponse.getThemeClass(), imageUrl);
        Category savedCategory = categoryRepository.save(category);

        return new CategoryResponse(savedCategory);
    }

    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryResponse categoryResponse, MultipartFile imageFile) {
        // 카테고리 존재 여부 확인
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));

        // 이미지 파일이 있는 경우에만 S3에 업로드
        String imageUrl = category.getImageUrl(); // 기존 이미지 URL을 기본값으로 설정
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                imageUrl = s3Uploader.upload(imageFile, "categories");
            } catch (IOException e) {
                throw new RuntimeException("이미지 업로드 중 오류가 발생했습니다.", e);
            }
        }

        // 카테고리 정보 업데이트
        category.updateCategory(categoryResponse.getTitle(), categoryResponse.getDescription(), categoryResponse.getThemeClass(), imageUrl);

        return new CategoryResponse(category);
    }
    public ResponseEntity<Void> deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
