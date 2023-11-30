package com.blanc.market.domain.product.controller;

import com.blanc.market.domain.product.dto.ProductRequest;
import com.blanc.market.domain.product.dto.ProductResponse;
import com.blanc.market.domain.product.entity.Category;
import com.blanc.market.domain.product.service.ProductService;
import com.blanc.market.domain.review.dto.ReviewResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Slf4j
@Tag(name = "Product", description = "Product API Document")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
  

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.getAllProducts(page, size));
    }


    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewResponse>> getReviewsForProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getReviewsForProduct(id));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }


    //검색 컨트롤러
    @Operation(summary = "키워드로 제품 조회", description = "sort=default:name 정렬, sort=likeCont:좋아요순 정렬")
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<ProductResponse>> searchProduct(@PathVariable String keyword, int page,
                                                               @RequestParam(defaultValue = "10") int size,
                                                               @RequestParam(defaultValue = "name") String sort){
        return ResponseEntity.ok(productService.searchProductForKeyword(keyword, page, size, sort).getContent());
    }

    @GetMapping("/search/{category}/category")
    public ResponseEntity<List<ProductResponse>> searchProductForCategory(@PathVariable String category, int page, @RequestParam(defaultValue = "10") int size){
        Category categoryEnum = Category.valueOf(category.toUpperCase());

        return ResponseEntity.ok(productService.searchProductForCategory(categoryEnum, page, size).getContent());
    }

}

