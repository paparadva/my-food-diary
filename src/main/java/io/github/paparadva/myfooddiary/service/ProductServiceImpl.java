package io.github.paparadva.myfooddiary.service;

import io.github.paparadva.myfooddiary.exception.ProductNotFoundException;
import io.github.paparadva.myfooddiary.model.Product;
import io.github.paparadva.myfooddiary.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Value("${myfooddiary.search.default-result-limit}")
    private int defaultSearchResultLimit;

    private final ProductRepository repository;

    @Override
    public Product getProduct(String name) {
        return repository.findByName(name).orElseThrow(() -> new ProductNotFoundException(name));
    }

    @Override
    public void saveProducts(List<Product> products) {
        repository.saveAll(products);
    }

    @Override
    public List<String> searchProducts(String nameQuery) {
        String tsquery = String.join(":* & ", nameQuery.split("\\s+")) + ":*";
        log.info("Tsquery string: \"{}\"", tsquery);
        return repository.searchProductNames(tsquery, defaultSearchResultLimit);
    }
}
