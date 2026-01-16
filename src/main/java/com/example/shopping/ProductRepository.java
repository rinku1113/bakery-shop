package com.example.shopping;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    // created_at の降順で上位6件を取得
    List<Product> findTop6ByOrderByCreatedAtDesc();
}

