package io.github.paparadva.myfooddiary.service;

import io.github.paparadva.myfooddiary.web.dto.ConsumedProductRequest;

import java.time.LocalDate;
import java.util.List;

public interface ConsumedProductService {
    void saveConsumedProducts(LocalDate date, List<ConsumedProductRequest> productRequests);
}
