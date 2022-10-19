package io.github.paparadva.myfooddiary.service;

import io.github.paparadva.myfooddiary.web.dto.ConsumedProductDto;
import io.github.paparadva.myfooddiary.web.dto.ConsumedProductsResponse;

import java.time.LocalDate;
import java.util.List;

public interface ConsumedProductService {
    void saveConsumedProducts(LocalDate date, List<ConsumedProductDto> productRequests);
    ConsumedProductsResponse getConsumedProducts(LocalDate date);
}
