package com.inpost.shoppingplatform.products;

import lombok.Data;

import java.util.UUID;

@Data
public class Product {

    private UUID id;
    private String name;
    private Price unitPrice;

    public Price calculateRegularTotalPrice(int quantity) {
        int valueInCents = this.unitPrice.getValueInCents() * quantity;
        return new Price(valueInCents, this.unitPrice.getCurrency());
    }

}
