package io.github.paparadva.myfooddiary.service;

import io.github.paparadva.myfooddiary.web.dto.ConsumedProductRequestDto;
import io.github.paparadva.myfooddiary.web.dto.ConsumedProductsResponse;

import java.time.LocalDate;
import java.util.List;

public interface ConsumedProductService {
    void saveConsumedProducts(LocalDate date, List<ConsumedProductRequestDto> productRequests);
    ConsumedProductsResponse getConsumedProducts(LocalDate date);
}
