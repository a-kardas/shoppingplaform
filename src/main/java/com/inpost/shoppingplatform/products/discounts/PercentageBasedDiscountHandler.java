package com.inpost.shoppingplatform.products.discounts;

import com.inpost.shoppingplatform.products.OrderItem;
import com.inpost.shoppingplatform.products.Price;
import com.inpost.shoppingplatform.products.ports.DiscountRepository;
import com.inpost.shoppingplatform.products.ports.PercentageBasedDiscount;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.inpost.shoppingplatform.products.discounts.DiscountPolicy.PERCENTAGE_BASED;
import static java.lang.String.format;

@RequiredArgsConstructor
final class PercentageBasedDiscountHandler implements DiscountPolicyHandler {

    private final DiscountRepository discountRepository;

    @Override
    public DiscountPolicy handles() {
        return PERCENTAGE_BASED;
    }

    @Override
    public Price applyDiscount(OrderItem orderItem, Price regularTotalPrice) {
        Optional<PercentageBasedDiscount> percentageBasedDiscount = this.discountRepository.getPercentageBasedDiscountFor(orderItem.productId());
        return percentageBasedDiscount.map(discount -> discount.reducePriceIfApplicable(orderItem, regularTotalPrice))
                .orElseThrow(() -> new MissingDiscountException(format("Quantity-based discount for product [id=%s] not found.", orderItem.productId())));
    }

}
