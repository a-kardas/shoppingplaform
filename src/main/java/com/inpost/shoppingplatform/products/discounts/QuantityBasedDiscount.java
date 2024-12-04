package com.inpost.shoppingplatform.products.discounts;

import com.inpost.shoppingplatform.products.OrderItem;
import com.inpost.shoppingplatform.products.Price;

import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static java.util.Comparator.comparing;
import static java.util.Objects.isNull;

public record QuantityBasedDiscount(UUID productId, List<QuantityBasedDiscountRule> rules) {

    public QuantityBasedDiscount(UUID productId, List<QuantityBasedDiscountRule> rules) {
        this.productId = productId;
        this.rules = isNull(rules) ? emptyList() : sortRules(rules);
    }

    public Price reducePriceIfApplicable(OrderItem orderItem, Price regularPrice) {
        Integer discountInCents = qualifiesForDiscount(orderItem.quantity());
        if (isNull(discountInCents)) {
            return new Price(regularPrice.valueInCents(), regularPrice.currency());
        } else {
            int newPrice = regularPrice.valueInCents() - discountInCents;
            return new Price(newPrice, regularPrice.currency());
        }
    }

    public Integer qualifiesForDiscount(int orderQuantity) {
        return rules.stream()
                .filter(rule -> rule.match(orderQuantity))
                .findFirst().map(rule -> rule.discountInCents)
                .orElse(null);
    }

    private List<QuantityBasedDiscountRule> sortRules(List<QuantityBasedDiscountRule> rules) {
        return rules.stream().sorted(comparing(QuantityBasedDiscountRule::minimumOrderQuantity).reversed()).toList();
    }

    public record QuantityBasedDiscountRule(int minimumOrderQuantity, int discountInCents) {
        boolean match(int orderQuantity) {
            return orderQuantity >= this.minimumOrderQuantity;
        }
    }
}
