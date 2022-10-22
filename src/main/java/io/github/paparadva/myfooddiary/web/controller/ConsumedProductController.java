package io.github.paparadva.myfooddiary.web.controller;

import io.github.paparadva.myfooddiary.service.ConsumedProductService;
import io.github.paparadva.myfooddiary.web.dto.ConsumedProductRequestDto;
import io.github.paparadva.myfooddiary.web.dto.ConsumedProductsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/history")
@Slf4j
@RequiredArgsConstructor
public class ConsumedProductController {

    private final ConsumedProductService service;

    @PostMapping("/{date}")
    public void saveConsumedProducts(@PathVariable LocalDate date, @RequestBody List<ConsumedProductRequestDto> productRequests) {
        log.info("Incoming save consumed products request date={}, products={}", date, productRequests);
        service.saveConsumedProducts(date, productRequests);
    }

    @GetMapping("/{date}")
    public ConsumedProductsResponse getConsumedProducts(@PathVariable LocalDate date) {
        log.info("Incoming get consumed products request date={}", date);
        return service.getConsumedProducts(date);
    }
}
