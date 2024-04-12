package org.example.userservice.Repositories;

import org.example.userservice.Models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {
    Optional<Token> findByToken(String token);
    void deleteByToken(String Token);
}
