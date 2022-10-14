package io.github.paparadva.myfooddiary.service;

import io.github.paparadva.myfooddiary.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> getProduct(String name);
    void saveProducts(List<Product> products);
}
