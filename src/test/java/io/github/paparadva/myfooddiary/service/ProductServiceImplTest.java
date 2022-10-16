package io.github.paparadva.myfooddiary.service;

import io.github.paparadva.myfooddiary.exception.ProductNotFoundException;
import io.github.paparadva.myfooddiary.model.Product;
import io.github.paparadva.myfooddiary.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DisplayName("Product service unit tests")
class ProductServiceImplTest {
    @Mock
    ProductRepository productRepository;

    final int DEFAULT_SEARCH_LIMIT = 10;

    ProductServiceImpl service;

    @Captor
    ArgumentCaptor<String> stringCaptor;

    @Captor
    ArgumentCaptor<List<Product>> listCaptor;

    @Captor
    ArgumentCaptor<Integer> intCaptor;

    @BeforeEach
    void init() {
        service = new ProductServiceImpl(productRepository, DEFAULT_SEARCH_LIMIT);
    }

    @Test
    void shouldReturnProductWhenFound() {
        // arrange
        String name = "Test Product";
        Product product = new Product();
        when(productRepository.findByName(anyString())).thenReturn(Optional.of(product));

        // act
        Product productResult = service.getProduct(name);

        // assert
        assertEquals(productResult, product);
    }

    @Test
    void shouldPassNameToRepository() {
        // arrange
        String name = "Test Product";
        when(productRepository.findByName(anyString())).thenReturn(Optional.of(new Product()));

        // act
        service.getProduct(name);

        // assert
        verify(productRepository).findByName(stringCaptor.capture());
        String passedName = stringCaptor.getValue();
        assertEquals(name, passedName);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        // arrange
        String name = "Test Product";
        when(productRepository.findByName(anyString())).thenReturn(Optional.empty());

        // act, assert
        var thrownException = assertThrows(
                ProductNotFoundException.class,
                () -> service.getProduct(name));
        assertTrue(thrownException.getMessage().contains(name));
    }

    @Test
    void shouldPassProductsToRepository() {
        // arrange
        List<Product> list = List.of(new Product());

        // act
        service.saveProducts(list);

        // assert
        verify(productRepository).saveAll(listCaptor.capture());
        assertEquals(list, listCaptor.getValue());
    }

    @Test
    void shouldFormatSingleWordQuery() {
        // arrange
        String searchInput = "говядина";
        String expectedQuery = "говядина:*";
        when(productRepository.searchProductNames(eq(searchInput), anyInt())).thenReturn(List.of());

        // act
        service.searchProducts(searchInput);

        // assert
        verify(productRepository).searchProductNames(stringCaptor.capture(), anyInt());
        String tsQuery = stringCaptor.getValue();
        assertEquals(expectedQuery, tsQuery);
    }

    @Test
    void shouldFormatMultipleWordQuery() {
        // arrange
        String searchInput = "говядина вареная";
        String expectedQuery = "говядина:* & вареная:*";
        when(productRepository.searchProductNames(eq(searchInput), anyInt())).thenReturn(List.of());

        // act
        service.searchProducts(searchInput);

        // assert
        verify(productRepository).searchProductNames(stringCaptor.capture(), anyInt());
        String tsQuery = stringCaptor.getValue();
        assertEquals(expectedQuery, tsQuery);
    }

    @Test
    void shouldRemoveWhitespace() {
        // arrange
        String searchInput = "  говядина \t\n  вареная    ";
        String expectedQuery = "говядина:* & вареная:*";
        when(productRepository.searchProductNames(eq(searchInput), anyInt())).thenReturn(List.of());

        // act
        service.searchProducts(searchInput);

        // assert
        verify(productRepository).searchProductNames(stringCaptor.capture(), anyInt());
        String tsQuery = stringCaptor.getValue();
        assertEquals(expectedQuery, tsQuery);
    }

    @Test
    void shouldUseDefaultSearchLimit() {
        // arrange
        String searchInput = "";
        when(productRepository.searchProductNames(anyString(), anyInt())).thenReturn(List.of());

        // act
        service.searchProducts(searchInput);

        // assert
        verify(productRepository).searchProductNames(any(), intCaptor.capture());
        assertEquals(DEFAULT_SEARCH_LIMIT, intCaptor.getValue());
    }
}