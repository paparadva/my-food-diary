package io.github.paparadva.myfooddiary.service;

import io.github.paparadva.myfooddiary.exception.ProductDoesNotExist;
import io.github.paparadva.myfooddiary.mapper.ConsumedProductMapperImpl;
import io.github.paparadva.myfooddiary.model.ConsumedProduct;
import io.github.paparadva.myfooddiary.repository.ConsumedProductRepository;
import io.github.paparadva.myfooddiary.repository.ProductRepository;
import io.github.paparadva.myfooddiary.web.dto.ConsumedProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Consumed product history unit tests")
class ConsumedProductServiceImplTest {

    ConsumedProductServiceImpl service;

    @Mock
    ConsumedProductRepository consumedProductRepository;

    @Mock
    ProductRepository productRepository;

    @Captor
    ArgumentCaptor<List<ConsumedProduct>> consumedProductCaptor;

    @Captor
    ArgumentCaptor<String> stringCaptor;

    @Captor
    ArgumentCaptor<LocalDate> dateCaptor;

    @BeforeEach
    void init() {
        service = new ConsumedProductServiceImpl(
                consumedProductRepository,
                productRepository,
                new ConsumedProductMapperImpl());
    }

    @Test
    void shouldSaveTwoConsumedProducts() {
        // arrange
        var date = LocalDate.of(2022, 10, 18);
        var requestData = List.of(
                new ConsumedProductRequest("product1", 10),
                new ConsumedProductRequest("product2", 20));

        when(productRepository.existsById(anyString())).thenReturn(true);

        // act
        service.saveConsumedProducts(date, requestData);

        // assert
        verify(consumedProductRepository).saveAll(consumedProductCaptor.capture());
        var savedEntities = consumedProductCaptor.getValue();
        assertEquals(2, savedEntities.size());

        ConsumedProduct consumed1 = savedEntities.get(0);
        assertEquals("product1", consumed1.getProductName());
        assertEquals(10, consumed1.getGrams());
        assertEquals(date, consumed1.getConsumptionDate());
        assertEquals(0, consumed1.getEntryIndex());

        ConsumedProduct consumed2 = savedEntities.get(1);
        assertEquals("product2", consumed2.getProductName());
        assertEquals(20, consumed2.getGrams());
        assertEquals(date, consumed2.getConsumptionDate());
        assertEquals(1, consumed2.getEntryIndex());
    }

    @Test
    void shouldDeletePreviousRecordsBeforeSave() {
        // arrange
        var date = LocalDate.of(2022, 10, 18);
        var requestData = List.<ConsumedProductRequest>of();

        // act
        service.saveConsumedProducts(date, requestData);

        // assert
        verify(consumedProductRepository, times(1)).deleteAllByConsumptionDate(dateCaptor.capture());
        LocalDate deletedDate = dateCaptor.getValue();
        assertEquals(date, deletedDate);
    }

    @Test
    void shouldValidateTwoProductNames() {
        // arrange
        var date = LocalDate.of(2022, 10, 18);
        var requestData = List.of(
                new ConsumedProductRequest("product1", 0),
                new ConsumedProductRequest("product2", 0));

        when(productRepository.existsById(anyString())).thenReturn(true);

        // act
        service.saveConsumedProducts(date, requestData);

        // assert
        verify(productRepository, times(2)).existsById(stringCaptor.capture());
        var validatedProductNames = stringCaptor.getAllValues();
        assertEquals("product1", validatedProductNames.get(0));
        assertEquals("product2", validatedProductNames.get(1));
    }

    @Test
    void shouldThrowExceptionIfProductDoesNotExist() {
        // arrange
        var date = LocalDate.of(2022, 10, 18);
        var requestData = List.of(new ConsumedProductRequest("fake product", 0));

        when(productRepository.existsById("fake product")).thenReturn(false);

        // act, assert
        var exception = assertThrows(ProductDoesNotExist.class,
                () -> service.saveConsumedProducts(date, requestData));
        assertTrue(exception.getMessage().contains("fake product"));
    }
}