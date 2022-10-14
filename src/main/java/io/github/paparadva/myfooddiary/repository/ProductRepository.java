package io.github.paparadva.myfooddiary.repository;

import io.github.paparadva.myfooddiary.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findByName(String foodName);
}
