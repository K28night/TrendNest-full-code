package com.example.TrendNest.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.TrendNest.service.RegistrationService;
import com.example.TrendNest.entity.Registration;
import com.example.TrendNest.entity.Watchlist;
import com.example.TrendNest.entity.Products;

@CrossOrigin(origins = "*")
@RestController
public class TrendController {
    @Autowired
    private RegistrationService trendser;

    @GetMapping("/users")
    public List<Registration> getAllPatient() {
        return trendser.getAllPatients();
    }

    @GetMapping("/products")
    public List<Products> getAllProdect() {
        return trendser.getAllProducts();
    }

    @PostMapping("/registration")
    public Registration registerUser(@RequestBody Registration registration) {
        return trendser.saveUser(registration);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Registration login) {
        // example: validate from DB
       return trendser.getUser(login.getEmail(),login.getPassword());
    }
 @PostMapping("/addProduct")
  public Products addProduct(
    @RequestParam("name") String name,
    @RequestParam("price") String price,
    @RequestParam("oldPrice") String oldPrice,
    @RequestParam("description") String description,
    @RequestParam("userId") Long userId,
    @RequestParam("image") MultipartFile image
) throws IOException {
        return trendser.addProducts(name,price,oldPrice,description,userId,image);
    }
//  @GetMapping("/watchlist")
// public List<Products> getWatchlist(@RequestParam Long userId) {
//     List<Watchlist> watchlistItems = watchlistRepo.findByUserId(userId);
//     return watchlistItems.stream()
//             .map(Watchlist::getProduct)
//             .collect(Collectors.toList());
// }
@PostMapping("/Watchlist")
public ResponseEntity<String> addToWatchlist(@RequestParam Long userId, @RequestParam Long productId) {
    trendser.addProductToWatchlist(userId, productId);
    return ResponseEntity.ok("Product added to watchlist");
}
@DeleteMapping("/watchlist")
public ResponseEntity<String> removeFromWatchlist(@RequestParam Long userId, @RequestParam Long productId) {
    trendser.deleteByUserIdAndProductId(userId, productId);
    return ResponseEntity.ok("Product removed from watchlist");
}
    @GetMapping("/watchlist")
    public List<Products> getWatchlist(@RequestParam Long userId) {
        return trendser.getWatchlistByUserId(userId);
    }
}
