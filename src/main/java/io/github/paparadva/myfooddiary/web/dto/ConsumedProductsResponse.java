package io.github.paparadva.myfooddiary.web.dto;

import java.time.LocalDate;
import java.util.List;

public record ConsumedProductsResponse(LocalDate date, List<ConsumedProductResponseDto> consumedProducts) {
}
