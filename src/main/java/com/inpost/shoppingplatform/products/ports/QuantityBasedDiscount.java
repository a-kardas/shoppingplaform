package com.inpost.shoppingplatform.products.ports;

import com.inpost.shoppingplatform.products.OrderItem;
import com.inpost.shoppingplatform.products.Price;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

import static java.util.Comparator.comparing;
import static java.util.Objects.isNull;

@Data
@NoArgsConstructor
public class QuantityBasedDiscount {

    private UUID productId;
    private List<QuantityBasedDiscountRule> rules;

    public QuantityBasedDiscount(UUID productId, List<QuantityBasedDiscountRule> rules) {
        this.productId = productId;
        this.rules = sortRules(rules);
    }

    @SuppressWarnings("unused")
    public void setRules(List<QuantityBasedDiscountRule> rules) {
        this.rules = sortRules(rules);
    }

    public Price reducePriceIfApplicable(OrderItem orderItem, Price regularPrice) {
        Integer discountInCents = qualifiesForDiscount(orderItem.quantity());
        if (isNull(discountInCents)) {
            return new Price(regularPrice.getValueInCents(), regularPrice.getCurrency());
        } else {
            int newPrice = regularPrice.getValueInCents() - discountInCents;
            return new Price(newPrice, regularPrice.getCurrency());
        }
    }

    public Integer qualifiesForDiscount(int orderQuantity) {
        return rules.stream()
                .filter(rule -> rule.match(orderQuantity))
                .findFirst().map(rule -> rule.discountInCents)
                .orElse(null);
    }

    private List<QuantityBasedDiscountRule> sortRules(List<QuantityBasedDiscountRule> rules) {
        return rules.stream().sorted(comparing(QuantityBasedDiscountRule::getMinimumOrderQuantity).reversed()).toList();
    }

    @Data
    public static class QuantityBasedDiscountRule {
        private int minimumOrderQuantity;
        private Integer discountInCents;

        boolean match(int orderQuantity) {
            return orderQuantity >= this.minimumOrderQuantity;
        }
    }
}
