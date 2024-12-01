package com.inpost.shoppingplatform.products.discounts;

import com.inpost.shoppingplatform.products.OrderItem;
import com.inpost.shoppingplatform.products.Price;

public sealed interface DiscountPolicyHandler permits QuantityBasedDiscountHandler, PercentageBasedDiscountHandler {

    DiscountPolicy handles();

    Price applyDiscount(OrderItem orderItem, Price regularTotalPrice);
}
