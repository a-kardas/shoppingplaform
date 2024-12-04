package com.inpost.shoppingplatform.products.discounts;

import com.inpost.shoppingplatform.products.OrderItem;
import com.inpost.shoppingplatform.products.Price;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static java.util.Comparator.comparing;
import static java.util.Objects.isNull;

public record PercentageBasedDiscount(UUID productId, List<PercentageBasedDiscountRule> rules) {

    public PercentageBasedDiscount(UUID productId, List<PercentageBasedDiscountRule> rules) {
        this.productId = productId;
        this.rules = isNull(rules)? emptyList() : sortRules(rules);
    }

    public Price reducePriceIfApplicable(OrderItem orderItem, Price regularPrice) {
        BigDecimal percentageDiscount = qualifiesForDiscount(orderItem.quantity());
        if (isNull(percentageDiscount)) {
            return new Price(regularPrice.valueInCents(), regularPrice.currency());
        } else {
            int discountInCents = percentageDiscount.multiply(new BigDecimal(regularPrice.valueInCents()))
                    .setScale(0, RoundingMode.HALF_UP)
                    .intValue();
            int newPrice = regularPrice.valueInCents() - discountInCents;
            return new Price(newPrice, regularPrice.currency());
        }
    }

    private BigDecimal qualifiesForDiscount(int orderQuantity) {
        return rules.stream()
                .filter(rule -> rule.match(orderQuantity))
                .findFirst().map(rule -> rule.discountPercent)
                .orElse(null);
    }

    private List<PercentageBasedDiscountRule> sortRules(List<PercentageBasedDiscountRule> rules) {
        return rules.stream().sorted(comparing(PercentageBasedDiscountRule::minimumOrderQuantity).reversed()).toList();
    }

    public record PercentageBasedDiscountRule(int minimumOrderQuantity, BigDecimal discountPercent) {
        boolean match(int orderQuantity) {
            return orderQuantity >= this.minimumOrderQuantity;
        }
    }
}
