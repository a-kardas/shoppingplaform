package com.inpost.shoppingplatform.products.discounts

import com.inpost.shoppingplatform.common.ITSpec
import com.inpost.shoppingplatform.products.OrderItem
import com.inpost.shoppingplatform.products.Price
import com.inpost.shoppingplatform.products.ports.TestDiscountRepository
import com.inpost.shoppingplatform.products.ports.TestProductRepository
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject

class QuantityBasedDiscountHandlerITSpec extends ITSpec {

    private static final UUID VOLVO_UUID = UUID.randomUUID()
    private static final UUID TESLA_UUID = UUID.randomUUID()

    @Autowired
    TestProductRepository testProductRepository

    @Autowired
    TestDiscountRepository testDiscountRepository

    @Subject
    @Autowired
    QuantityBasedDiscountHandler quantityBasedDiscountHandler

    def setup() {
        testProductRepository.addProduct(VOLVO_UUID, "Volvo", 1_000_000, "USD")
        testProductRepository.addProduct(TESLA_UUID, "Tesla", 999_999, "USD")
    }

    def cleanup() {
        testDiscountRepository.clearAll()
        testProductRepository.clearAll()
    }

    def "should apply percentage discount"() {
        given:
            def orderItem = new OrderItem(TESLA_UUID, 1)
            def regularPrice = new Price(999_999, "USD")

        and: 'tesla discount exists'
            testDiscountRepository.addQuantityBasedDiscountConfiguration(TESLA_UUID, teslaDiscountConfiguration())

        when:
            def reducedPrice = quantityBasedDiscountHandler.applyDiscount(orderItem, regularPrice)

        then: '10$ discount is expected'
            reducedPrice == new Price(998_999, "USD")
    }

    def "should apply greater discount if order includes more products"() {
        given:
            def orderItem = new OrderItem(TESLA_UUID, 4)
            def regularPrice = new Price(3_999_996, "USD")

        and: 'tesla discount exists'
            testDiscountRepository.addQuantityBasedDiscountConfiguration(TESLA_UUID, teslaDiscountConfiguration())

        when:
            def reducedPrice = quantityBasedDiscountHandler.applyDiscount(orderItem, regularPrice)

        then: '30$ discount is expected'
            reducedPrice == new Price(3_996_996, "USD")
    }

    def "should throw exception if discount is not applicable"() {
        given:
            def orderItem = new OrderItem(VOLVO_UUID, 1)
            def regularPrice = new Price(1_000_000, "USD")

        when:
            quantityBasedDiscountHandler.applyDiscount(orderItem, regularPrice)

        then:
            thrown MissingDiscountException
    }

    private static String teslaDiscountConfiguration() {
        '''
        [
            {"minimumOrderQuantity": 1, "discountInCents": 1000},
            {"minimumOrderQuantity": 3, "discountInCents": 3000},
            {"minimumOrderQuantity": 5, "discountInCents": 5000}
        ]
        '''
    }
}
