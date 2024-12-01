package com.inpost.shoppingplatform.products.discounts;

import com.inpost.shoppingplatform.products.OrderItem;
import com.inpost.shoppingplatform.products.Price;

import static com.inpost.shoppingplatform.products.discounts.DiscountPolicy.PERCENTAGE_BASED;

final class PercentageBasedDiscountHandler implements DiscountPolicyHandler {

    @Override
    public DiscountPolicy handles() {
        return PERCENTAGE_BASED;
    }

    @Override
    public Price applyDiscount(OrderItem orderItem, Price regularTotalPrice) {
        return new Price(0, "USD");
    }


}
