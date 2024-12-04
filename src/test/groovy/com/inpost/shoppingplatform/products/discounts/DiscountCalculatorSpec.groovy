package com.inpost.shoppingplatform.products.discounts

import com.inpost.shoppingplatform.products.OrderItem
import com.inpost.shoppingplatform.products.Price
import com.inpost.shoppingplatform.products.ports.DiscountRepository
import spock.lang.Specification
import spock.lang.Subject

import static com.inpost.shoppingplatform.products.discounts.DiscountPolicy.QUANTITY_BASED
import static com.inpost.shoppingplatform.products.discounts.DiscountPolicy.PERCENTAGE_BASED

class DiscountCalculatorSpec extends Specification {

    def discountRepository = Mock(DiscountRepository)
    def quantityBasedDiscountHandler = Mock(QuantityBasedDiscountHandler)
    def percentageBasedDiscountHandler = Mock(PercentageBasedDiscountHandler)
    def handlers = new EnumMap(Map.of(
            QUANTITY_BASED, quantityBasedDiscountHandler,
            PERCENTAGE_BASED, percentageBasedDiscountHandler
    ))

    @Subject
    def calculator = new DiscountCalculator(discountRepository, handlers)

    def "should delegate responsibility to quantity-based discount handler"() {
        given:
            def orderItem = new OrderItem(/*productId*/ UUID.randomUUID(), /*quantity*/ 5)
            def regularPrice = new Price(/*valueInCents*/ 1000, 'USD')
            def priceAfterDiscount = new Price(/*valueInCents*/ 800, 'USD')

        when:
            def result = calculator.applyDiscountIfApplicable(orderItem, regularPrice)

        then:
            1 * discountRepository.currentlyApplicable(orderItem.productId()) >> Optional.of(QUANTITY_BASED)
            1 * quantityBasedDiscountHandler.applyDiscount(orderItem, regularPrice) >> priceAfterDiscount

            result == priceAfterDiscount
    }

    def "should delegate responsibility to percentage-based discount handler"() {
        given:
            def orderItem = new OrderItem(/*productId*/ UUID.randomUUID(), /*quantity*/ 5)
            def regularPrice = new Price(/*valueInCents*/ 1000, 'USD')
            def priceAfterDiscount = new Price(/*valueInCents*/ 800, 'USD')

        when:
            def result = calculator.applyDiscountIfApplicable(orderItem, regularPrice)

        then:
            1 * discountRepository.currentlyApplicable(orderItem.productId()) >> Optional.of(PERCENTAGE_BASED)
            1 * percentageBasedDiscountHandler.applyDiscount(orderItem, regularPrice) >> priceAfterDiscount

            result == priceAfterDiscount
    }

    def "should not delegate responsibility to any handler if discount is not applicable"() {
        given:
            def orderItem = new OrderItem(/*productId*/ UUID.randomUUID(), /*quantity*/ 5)
            def regularPrice = new Price(/*valueInCents*/ 1000, 'USD')

        when:
            def result = calculator.applyDiscountIfApplicable(orderItem, regularPrice)

        then:
            1 * discountRepository.currentlyApplicable(orderItem.productId()) >> Optional.empty()
            0 * quantityBasedDiscountHandler.applyDiscount(_, _)
            0 * percentageBasedDiscountHandler.applyDiscount(_, _)

            result == regularPrice
    }
}
