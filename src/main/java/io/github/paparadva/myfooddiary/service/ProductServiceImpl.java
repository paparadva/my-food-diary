package io.github.paparadva.myfooddiary.service;

import io.github.paparadva.myfooddiary.model.Product;
import io.github.paparadva.myfooddiary.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Override
    public Optional<Product> getProduct(String name) {
        return repository.findByName(name);
    }

    @Override
    public void saveProducts(List<Product> products) {
        repository.saveAll(products);
    }
}
