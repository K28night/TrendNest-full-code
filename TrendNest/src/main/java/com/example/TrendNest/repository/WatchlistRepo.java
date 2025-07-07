package com.example.TrendNest.repository;

import com.example.TrendNest.entity.Products;
import com.example.TrendNest.entity.Watchlist;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WatchlistRepo extends JpaRepository<Watchlist, Long> {
   List<Watchlist> findByUser_Id(Long userId);

@Modifying
@Transactional
@Query("DELETE FROM Watchlist w WHERE w.user.id = :userId AND w.product.id = :productId")
void deleteByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);


    @Query("SELECT w.product FROM Watchlist w WHERE w.user.id = :userId")
List<Products> findProductsByUserId(@Param("userId") Long userId);

}
