package io.github.paparadva.myfooddiary.service;


import io.github.paparadva.myfooddiary.model.ProductSuggestion;

import java.util.List;

public interface ProductSuggestionService {
    void saveProducts(List<ProductSuggestion> products);
    List<String> fetchSuggestions(String query);
}
