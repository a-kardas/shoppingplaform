package com.inpost.shoppingplatform.products.ports;

import com.inpost.shoppingplatform.products.OrderItem;
import com.inpost.shoppingplatform.products.Price;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

import static java.util.Comparator.comparing;
import static java.util.Objects.isNull;

@Data
@NoArgsConstructor
public class PercentageBasedDiscount {

    private UUID productId;
    private List<PercentageBasedDiscountRule> rules;

    public PercentageBasedDiscount(UUID productId, List<PercentageBasedDiscountRule> rules) {
        this.productId = productId;
        this.rules = sortRules(rules);
    }

    @SuppressWarnings("unused")
    public void setRules(List<PercentageBasedDiscountRule> rules) {
        this.rules = sortRules(rules);
    }

    public Price reducePriceIfApplicable(OrderItem orderItem, Price regularPrice) {
        BigDecimal percentageDiscount = qualifiesForDiscount(orderItem.quantity());
        if (isNull(percentageDiscount)) {
            return new Price(regularPrice.getValueInCents(), regularPrice.getCurrency());
        } else {
            int discountInCents = percentageDiscount.multiply(new BigDecimal(regularPrice.getValueInCents())).setScale(0, RoundingMode.HALF_UP).intValue();
            int newPrice = regularPrice.getValueInCents() - discountInCents;
            return new Price(newPrice, regularPrice.getCurrency());
        }
    }

    private BigDecimal qualifiesForDiscount(int orderQuantity) {
        return rules.stream()
                .filter(rule -> rule.match(orderQuantity))
                .findFirst().map(rule -> rule.discountPercent)
                .orElse(null);
    }

    private List<PercentageBasedDiscountRule> sortRules(List<PercentageBasedDiscountRule> rules) {
        return rules.stream().sorted(comparing(PercentageBasedDiscountRule::getMinimumOrderQuantity).reversed()).toList();
    }

    @Data
    public static class PercentageBasedDiscountRule {
        private int minimumOrderQuantity;
        private BigDecimal discountPercent;

        boolean match(int orderQuantity) {
            return orderQuantity >= this.minimumOrderQuantity;
        }
    }
}
