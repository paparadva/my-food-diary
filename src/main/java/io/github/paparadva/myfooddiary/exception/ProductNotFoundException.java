package io.github.paparadva.myfooddiary.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String productName) {
        super("Product \"" + productName + "\" was not found");
    }
}
