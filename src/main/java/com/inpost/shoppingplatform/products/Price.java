package com.inpost.shoppingplatform.products;

import lombok.Value;

@Value
public class Price {

    int valueInCents;
    String currency;

    public Price reduceBy(int discount) {
        int newPrice = this.valueInCents - discount;
        return new Price(newPrice, this.currency);
    }
}
