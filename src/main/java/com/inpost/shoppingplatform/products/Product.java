package com.inpost.shoppingplatform.products;

import java.util.UUID;

public record Product(UUID id, String name, Price unitPrice) {

    @SuppressWarnings("unused")
    public Product(UUID id, String name, int valueInCents, String currency) {
        this(id, name, new Price(valueInCents, currency));
    }

    public Price calculateRegularTotalPrice(int quantity) {
        if (quantity < 0) {
            throw new CalculationException("Cannot calculate total price. Quantity cannot be negative.");
        }
        int valueInCents = this.unitPrice.valueInCents() * quantity;
        return new Price(valueInCents, this.unitPrice.currency());
    }

}
