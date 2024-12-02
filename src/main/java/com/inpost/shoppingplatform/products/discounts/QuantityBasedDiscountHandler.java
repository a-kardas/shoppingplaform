package com.inpost.shoppingplatform.products.discounts;

import com.inpost.shoppingplatform.products.OrderItem;
import com.inpost.shoppingplatform.products.Price;
import com.inpost.shoppingplatform.products.ports.DiscountRepository;
import com.inpost.shoppingplatform.products.ports.QuantityBasedDiscount;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.inpost.shoppingplatform.products.discounts.DiscountPolicy.QUANTITY_BASED;
import static java.lang.String.format;

@RequiredArgsConstructor
final class QuantityBasedDiscountHandler implements DiscountPolicyHandler {

    private final DiscountRepository discountRepository;

    @Override
    public DiscountPolicy handles() {
        return QUANTITY_BASED;
    }

    @Override
    public Price applyDiscount(OrderItem orderItem, Price regularTotalPrice) {
        Optional<QuantityBasedDiscount> quantityBasedDiscount = this.discountRepository.getQuantityBasedDiscountFor(orderItem.productId());
        return quantityBasedDiscount.map(discount -> discount.reducePriceIfApplicable(orderItem, regularTotalPrice))
                .orElseThrow(() -> new MissingDiscountException(format("Quantity-based discount for product [id=%s] not found.", orderItem.productId())));
    }
}
