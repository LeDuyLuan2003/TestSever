package com.SpringBootJdk22.SpringBootJdk22.controller;

import com.SpringBootJdk22.SpringBootJdk22.model.Category;
import com.SpringBootJdk22.SpringBootJdk22.model.Image;
import com.SpringBootJdk22.SpringBootJdk22.model.Product;
import com.SpringBootJdk22.SpringBootJdk22.service.CategoryService;
import com.SpringBootJdk22.SpringBootJdk22.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/api/products")
public class ProductApiController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
    @PostMapping(consumes = { "multipart/form-data" })
    public Product createProduct(
            @RequestParam("name") String name,
            @RequestParam("price") double price,
            @RequestParam("category") Long categoryId,
            @RequestParam("description") String description,
            @RequestParam("avatarFile") MultipartFile avatarFile,
            @RequestParam("imageFiles") MultipartFile[] imageFiles) throws IOException {

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setCategory(categoryService.getCategoryById(categoryId).orElse(null)); // Set category here
        product.setDescription(description);

        if (!avatarFile.isEmpty()) {
            String avatarFileName = saveFile(avatarFile);
            product.setAvatar(avatarFileName);
        }

        Product savedProduct = productService.addProduct(product);

        for (MultipartFile imageFile : imageFiles) {
            if (!imageFile.isEmpty()) {
                String imageFileName = saveFile(imageFile);
                Image image = new Image();
                image.setUrl(imageFileName);
                image.setProduct(savedProduct);
                productService.addImage(image);
            }
        }

        return savedProduct;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Product not found on :: " + id));
        return ResponseEntity.ok().body(product);
    }

    @PutMapping(value = "/{id}", consumes = { "multipart/form-data" })
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("price") double price,
            @RequestParam("category") Long categoryId,
            @RequestParam("description") String description,
            @RequestParam("avatarFile") MultipartFile avatarFile,
            @RequestParam("imageFiles") MultipartFile[] imageFiles) throws IOException {

        Product existingProduct = productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Product not found on :: " + id));

        if (!avatarFile.isEmpty()) {
            String avatarFileName = saveFile(avatarFile);
            existingProduct.setAvatar(avatarFileName);
        }

        existingProduct.setName(name);
        existingProduct.setPrice(price);
        existingProduct.setCategory(categoryService.getCategoryById(categoryId).orElseThrow(() -> new IllegalArgumentException("Invalid category ID")));
        existingProduct.setDescription(description);

        Product updatedProduct = productService.updateProduct(existingProduct);

        for (MultipartFile imageFile : imageFiles) {
            if (!imageFile.isEmpty()) {
                String imageFileName = saveFile(imageFile);
                Image image = new Image();
                image.setUrl(imageFileName);
                image.setProduct(updatedProduct);
                productService.addImage(image);
            }
        }

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Product not found on :: " + id));
        productService.deleteProductById(id);
        return ResponseEntity.ok().build();
    }

    private String saveFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get("uploads", fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());
        return fileName;
    }
}
