package com.blanc.market.domain.product.service;

import com.blanc.market.domain.product.dto.ProductRequest;
import com.blanc.market.domain.product.dto.ProductResponse;
import com.blanc.market.domain.product.entity.Product;
import com.blanc.market.domain.product.repository.ProductRepository;
import com.blanc.market.domain.review.dto.ReviewResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private NamedLockProductFacade namedLockProductFacade;

    @Autowired
    private ProductRepository productRepository;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = productRepository.save(Product.builder()
                .name("Test Product")
                .price(100)
                .description("Description")
                .build()
        );
    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    void createProduct() {
        // Given
        ProductRequest productRequest = new ProductRequest(
                "New Product",
                150,
                null,
                "New Description",
                null,
                null
        );

        // When
        productService.createProduct(productRequest);

        // Then
        List<Product> products = productRepository.findAll();
        assertEquals(2, products.size());
    }

    @Test
    void getAllProducts() {
        // When
        Page<ProductResponse> productPage = productService.getAllProducts(0, 10);

        // Then
        assertNotNull(productPage);
        assertEquals(1, productPage.getContent().size());
    }

    @Test
    void getProductById() {
        // When
        ProductResponse retrievedProduct = productService.getProductById(testProduct.getId());

        // Then
        assertNotNull(retrievedProduct);
        assertEquals(testProduct.getId(), retrievedProduct.getId());
    }

    @Test
    void getReviewsForProduct() {
        // When
        List<ReviewResponse> reviewResponses = productService.getReviewsForProduct(testProduct.getId());

        // Then
        assertNotNull(reviewResponses);
        assertEquals(0, reviewResponses.size());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateLikeCount() throws InterruptedException {
        int initialLikeCount = testProduct.getLikeCount();

        int threadCount = 32;
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    namedLockProductFacade.updateLikeCount(testProduct.getId(), 1);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Product product = productRepository.findById(testProduct.getId()).orElseThrow();
        assertEquals(initialLikeCount + threadCount, product.getLikeCount());
    }

    @Test
    void delete() {
        // When
        productService.delete(testProduct.getId());

        // Then
        assertTrue(productRepository.findById(testProduct.getId()).orElseThrow().isDeleted());
    }
}