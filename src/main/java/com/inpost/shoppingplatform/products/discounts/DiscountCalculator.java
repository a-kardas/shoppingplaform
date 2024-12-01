package com.inpost.shoppingplatform.products.discounts;

import com.inpost.shoppingplatform.products.OrderItem;
import com.inpost.shoppingplatform.products.ports.DiscountRepository;

import java.util.Map;
import java.util.Optional;

import com.inpost.shoppingplatform.products.Price;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DiscountCalculator {

    private final DiscountRepository discountRepository;
    private final Map<DiscountPolicy, DiscountPolicyHandler> discountHandlers;

    public Price applyDiscountIfApplicable(OrderItem orderItem, Price regularTotalPrice) {
        Optional<DiscountPolicy> applicableDiscount = this.discountRepository.currentlyApplicable(orderItem.productId());
        return applicableDiscount
                .map(discountPolicy -> {
                    DiscountPolicyHandler discountPolicyHandler = discountHandlers.get(discountPolicy);
                    return discountPolicyHandler.applyDiscount(orderItem, regularTotalPrice);
                })
                .orElse(regularTotalPrice);
    }
}
