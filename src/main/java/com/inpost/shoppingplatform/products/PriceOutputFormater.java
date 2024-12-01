package com.inpost.shoppingplatform.products;

import java.math.BigDecimal;
import java.math.RoundingMode;

class PriceOutputFormater {

    private static final int OUTPUT_SCALE = 2;
    private static final BigDecimal CENTS = new BigDecimal(100);

    static BigDecimal format(int valueInCents) {
        return BigDecimal.valueOf(valueInCents)
                .divide(CENTS, OUTPUT_SCALE, RoundingMode.HALF_UP);
    }
}
