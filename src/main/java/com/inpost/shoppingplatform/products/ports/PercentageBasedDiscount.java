package com.inpost.shoppingplatform.products.ports;

import java.util.List;
import java.util.UUID;

public class PercentageBasedDiscount {

    private UUID productId;
    private List<PercentageBasedDiscountRule> rules;

    public static class PercentageBasedDiscountRule {
        private int minimumOrderQuantity;
        private int discount;
    }
}
