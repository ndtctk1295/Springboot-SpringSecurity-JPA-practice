package com.example.demo.repositories;

import com.example.demo.models.BlacklistToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlacklistTokenRepository extends JpaRepository<BlacklistToken, Integer> {
     BlacklistToken findByToken(String token);

     @Query("SELECT b FROM BlacklistToken b")
     List<BlacklistToken> findAllTokens();
     @Query("SELECT b FROM BlacklistToken b WHERE b.active = true")
     List<BlacklistToken> findAllActiveTokens();
}
