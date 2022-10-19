package io.github.paparadva.myfooddiary.exception;

public class ProductDoesNotExist extends RuntimeException {
    public ProductDoesNotExist(String productName) {
        super("Product \"" + productName + "\" doest not exist");
    }
}
