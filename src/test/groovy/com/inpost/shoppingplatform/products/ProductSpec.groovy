package com.inpost.shoppingplatform.products

import spock.lang.Specification

class ProductSpec extends Specification {

    def "should calculate total price"() {
        given:
            def unitPrice = new Price(999, "USD")
            def product = new Product(UUID.randomUUID(), "Cheesecake", unitPrice)

        when:
            def regularTotalPrice = product.calculateRegularTotalPrice(quantity)

        then:
            regularTotalPrice == expectedPrice

        where:
            quantity    | expectedPrice
            0           | new Price(0, "USD")
            1           | new Price(999, "USD")
            2           | new Price(1998, "USD")
    }

    def "should throw exception if quantity is less than 0"() {
        given:
            def unitPrice = new Price(999, "USD")
            def product = new Product(UUID.randomUUID(), "Cheesecake", unitPrice)

        when:
            product.calculateRegularTotalPrice(-1)

        then:
            thrown CalculationException
    }
}
