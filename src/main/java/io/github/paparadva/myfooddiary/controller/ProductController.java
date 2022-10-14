package io.github.paparadva.myfooddiary.controller;

import io.github.paparadva.myfooddiary.model.Product;
import io.github.paparadva.myfooddiary.service.ProductService;
import io.github.paparadva.myfooddiary.service.ProductSuggestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductSuggestionService suggestionService;

    @GetMapping("/search")
    public List<String> searchProductSuggestions(@RequestParam String query) {
        log.info("Incoming product suggestions request. Query: {}", query);
        return suggestionService.fetchSuggestions(query);
    }

    @GetMapping
    public Product getProductByName(@RequestParam String name) {
        return productService.getProduct(name).orElseThrow();
    }
}
