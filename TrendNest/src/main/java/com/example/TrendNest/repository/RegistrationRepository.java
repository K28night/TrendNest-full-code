package com.example.TrendNest.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.TrendNest.entity.Registration;


@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long>{
     boolean existsByEmail(String email);

     Registration findByEmailAndPassword(String email, String password);
     
}
