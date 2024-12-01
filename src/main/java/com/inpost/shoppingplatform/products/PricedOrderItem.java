package com.inpost.shoppingplatform.products;

import java.math.BigDecimal;
import java.util.UUID;

public record PricedOrderItem(UUID id, int quantity, BigDecimal regularUnitPrice, BigDecimal regularTotalPrice, BigDecimal finalTotalPrice, String currency) {

    public static PricedOrderItem of(OrderItem orderItem, Product product, Price regularTotalPrice, Price finalTotalPrice) {
        return new PricedOrderItem(
                orderItem.productId(),
                orderItem.quantity(),
                PriceOutputFormater.format(product.getUnitPrice().getValueInCents()),
                PriceOutputFormater.format(regularTotalPrice.getValueInCents()),
                PriceOutputFormater.format(finalTotalPrice.getValueInCents()),
                finalTotalPrice.getCurrency()
        );
    }
}
