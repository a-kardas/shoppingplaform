package com.inpost.shoppingplatform.products

import spock.lang.Specification

class PriceOutputFormaterSpec extends Specification {

    def "should format the price so that it's easily readable by humans"() {
        when:
            def formattedPrice = PriceOutputFormater.format(valueInCents)

        then:
            formattedPrice == expectedFormattedPrice

        where:
            valueInCents    | expectedFormattedPrice
            0               | 0.00
            1               | 0.01
            99              | 0.99
            100             | 1.00
            199             | 1.99
            9999            | 99.99
            99999           | 999.99
            100000          | 1000.00
    }
}
