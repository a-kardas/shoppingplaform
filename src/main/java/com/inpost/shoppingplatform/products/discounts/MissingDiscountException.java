package com.inpost.shoppingplatform.products.discounts;

public class MissingDiscountException extends RuntimeException {

    public MissingDiscountException(String message) {
        super(message);
    }
}
