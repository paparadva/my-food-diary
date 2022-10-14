package io.github.paparadva.myfooddiary.service;

import io.github.paparadva.myfooddiary.model.Product;

import java.util.List;

public interface ProductService {
    Product getProduct(String name);
    void saveProducts(List<Product> products);
}
