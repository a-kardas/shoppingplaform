package com.inpost.shoppingplatform.products.ports;

import java.util.List;
import java.util.UUID;

public class QuantityBasedDiscount {

    private UUID productId;
    private List<QuantityBasedDiscountRule> rules;

    public static class QuantityBasedDiscountRule {
        private int minimumOrderQuantity;
        private Integer discount;
    }
}
