package com.inpost.shoppingplatform.products.discounts

import com.inpost.shoppingplatform.products.OrderItem
import com.inpost.shoppingplatform.products.Price
import spock.lang.Specification

import static com.inpost.shoppingplatform.products.discounts.PercentageBasedDiscount.PercentageBasedDiscountRule

class PercentageBasedDiscountSpec extends Specification {

    def "should apply discount according to provided rules"() {
        given:
            def orderItem = new OrderItem(UUID.randomUUID(), /*quantity*/ 2)
            def regularPrice = new Price(/*valueInCents*/ 1000, "USD")
        and:
            def unsortedDiscountRules = [
                    new PercentageBasedDiscountRule(/*minimumOrderQuantity*/ 3, /*discountPercent*/ 0.25),
                    new PercentageBasedDiscountRule(/*minimumOrderQuantity*/ 1, /*discountPercent*/ 0.05),
                    new PercentageBasedDiscountRule(/*minimumOrderQuantity*/ 2, /*discountPercent*/ 0.10)
            ]
            def discount = new PercentageBasedDiscount(UUID.randomUUID(), unsortedDiscountRules)

        when:
            def reducedPrice = discount.reducePriceIfApplicable(orderItem, regularPrice)

        then: "10% discount is expected"
            reducedPrice == new Price(/*valueInCents*/ 900, "USD")
    }

    def "should not apply discount if order item does not meet requirements"() {
        given:
            def orderItem = new OrderItem(UUID.randomUUID(), /*quantity*/ 2)
            def regularPrice = new Price(/*valueInCents*/ 1000, "USD")
        and:
            def unsortedDiscountRules = [
                    new PercentageBasedDiscountRule(/*minimumOrderQuantity*/ 4, /*discountPercent*/ 0.25),
                    new PercentageBasedDiscountRule(/*minimumOrderQuantity*/ 3, /*discountPercent*/ 0.05),
                    new PercentageBasedDiscountRule(/*minimumOrderQuantity*/ 5, /*discountPercent*/ 0.10)
            ]
            def discount = new PercentageBasedDiscount(UUID.randomUUID(), unsortedDiscountRules)

        when:
            def reducedPrice = discount.reducePriceIfApplicable(orderItem, regularPrice)

        then: "discount is not expected"
            reducedPrice == new Price(/*valueInCents*/ 1000, "USD")
    }

    def "should not apply discount if rules are null or empty"() {
        given:
            def orderItem = new OrderItem(UUID.randomUUID(), /*quantity*/ 1)
            def regularPrice = new Price(/*valueInCents*/ 1000, "USD")
        and:
            def discount = new PercentageBasedDiscount(UUID.randomUUID(), discountRules)

        when:
            def result = discount.reducePriceIfApplicable(orderItem, regularPrice)

        then: "discount is not expected"
            result == new Price(/*valueInCents*/ 1000, "USD")

        where:
            discountRules   | _
            []              | _
            null            | _
    }
}
