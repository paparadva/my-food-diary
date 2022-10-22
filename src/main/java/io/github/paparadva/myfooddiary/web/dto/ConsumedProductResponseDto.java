package io.github.paparadva.myfooddiary.web.dto;

public record ConsumedProductResponseDto(String productName, int grams, int kcal, double protein, double fat, double carb) {
}
