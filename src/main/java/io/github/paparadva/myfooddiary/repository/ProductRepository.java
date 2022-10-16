package io.github.paparadva.myfooddiary.repository;

import io.github.paparadva.myfooddiary.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findByName(String foodName);

    @Query(value = "SELECT name FROM my_food_diary.product WHERE ts_name @@ to_tsquery('russian', ?1) LIMIT ?2",
            nativeQuery = true)
    List<String> searchProductNames(String searchQuery, int resultLimit);
}
