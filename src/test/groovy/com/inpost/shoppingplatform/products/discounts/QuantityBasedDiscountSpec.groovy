package com.inpost.shoppingplatform.products.discounts

import com.inpost.shoppingplatform.products.OrderItem
import com.inpost.shoppingplatform.products.Price
import spock.lang.Specification

import static com.inpost.shoppingplatform.products.discounts.QuantityBasedDiscount.QuantityBasedDiscountRule

class QuantityBasedDiscountSpec extends Specification {

    def "should apply discount according to provided rules"() {
        given:
            def orderItem = new OrderItem(UUID.randomUUID(), /*quantity*/ 2)
            def regularPrice = new Price(/*valueInCents*/ 1000, "USD")
        and:
            def unsortedDiscountRules = [
                    new QuantityBasedDiscountRule(/*minimumOrderQuantity*/ 3, /*discountInCents*/ 100),
                    new QuantityBasedDiscountRule(/*minimumOrderQuantity*/ 1, /*discountInCents*/ 50),
                    new QuantityBasedDiscountRule(/*minimumOrderQuantity*/ 2, /*discountInCents*/ 70)
            ]
            def discount = new QuantityBasedDiscount(UUID.randomUUID(), unsortedDiscountRules)

        when:
            def reducedPrice = discount.reducePriceIfApplicable(orderItem, regularPrice)

        then: "70¢ discount is expected"
            reducedPrice == new Price(/*valueInCents*/ 930, "USD")
    }

    def "should not apply discount if order item does not meet requirements"() {
        given:
            def orderItem = new OrderItem(UUID.randomUUID(), /*quantity*/ 1)
            def regularPrice = new Price(/*valueInCents*/ 1000, "USD")
        and:
            def unsortedDiscountRules = [
                    new QuantityBasedDiscountRule(/*minimumOrderQuantity*/ 5, /*discountInCents*/ 100),
                    new QuantityBasedDiscountRule(/*minimumOrderQuantity*/ 3, /*discountInCents*/ 50),
                    new QuantityBasedDiscountRule(/*minimumOrderQuantity*/ 4, /*discountInCents*/ 70)
            ]
            def discount = new QuantityBasedDiscount(UUID.randomUUID(), unsortedDiscountRules)

        when:
            def result = discount.reducePriceIfApplicable(orderItem, regularPrice)

        then: "discount is not expected"
            result == new Price(/*valueInCents*/ 1000, "USD")
    }

    def "should not apply discount if rules are null or empty"() {
        given:
            def orderItem = new OrderItem(UUID.randomUUID(), /*quantity*/ 1)
            def regularPrice = new Price(/*valueInCents*/ 1000, "USD")
        and:
            def discount = new QuantityBasedDiscount(UUID.randomUUID(), discountRules)

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
