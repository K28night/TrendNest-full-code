package com.example.TrendNest.service;

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.TrendNest.entity.Products;
import com.example.TrendNest.entity.Registration;
import com.example.TrendNest.entity.Watchlist;
import com.example.TrendNest.repository.ProdectRepository;
import com.example.TrendNest.repository.RegistrationRepository;
import com.example.TrendNest.repository.WatchlistRepo;

@Service
public class RegistrationService {
    @Autowired
    private ProdectRepository prepo;
    @Autowired
    private RegistrationRepository regrepo;

    @Autowired
    private WatchlistRepo watchlistRepo;

    public List<Registration> getAllPatients() {
        return regrepo.findAll();
    }

    public List<Products> getAllProducts() {
        return prepo.findAll();
    }

    public Registration saveUser(Registration registration) {
        // Optional: check if email exists
        if (regrepo.existsByEmail(registration.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        return regrepo.save(registration);
    }

    public ResponseEntity<?> getUser(String email, String password) {
        Registration user = regrepo.findByEmailAndPassword(email, password);
        if (user != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("user", user);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    public Products addProducts(String name, String price, String oldPrice, String description, Long userId,
            MultipartFile image) throws IOException {
        Products product = new Products();
        product.setName(name);
        product.setPrice(price);
        product.setOldPrice(oldPrice);
        product.setDescription(description);
        Registration user = regrepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        product.setSale(true);
        product.setUser(user);

        // 2. Handle image upload
        String uploadDir = "src/main/resources/static/uploads"; // relative path
        Files.createDirectories(Paths.get(uploadDir));

        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        product.setImage("/uploads/" + fileName); // this works with Spring Boot static path

        return prepo.save(product);
    }

    // public List<Products> getWatchlistByUserId(Long userId) {
    // List<Watchlist> watchlistItems = watchlistRepo.findByUserId(userId);
    // return watchlistItems.stream()
    // .map(Watchlist::getProduct)
    // .collect(Collectors.toList());
    // }
    public void addProductToWatchlist(Long userId, Long productId) {
        Registration user = regrepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Products product = prepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Watchlist watchItem = new Watchlist();
        watchItem.setUser(user);
        watchItem.setProduct(product);

        watchlistRepo.save(watchItem);
    }

    public void deleteByUserIdAndProductId(Long userId, Long productId) {
        watchlistRepo.deleteByUserIdAndProductId(userId, productId);
    }

    public List<Products> getWatchlistByUserId(Long userId) {
        regrepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return watchlistRepo.findProductsByUserId(userId);

    }

}
