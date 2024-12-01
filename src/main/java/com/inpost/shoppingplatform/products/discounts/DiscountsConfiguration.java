package com.inpost.shoppingplatform.products.discounts;

import com.inpost.shoppingplatform.products.ports.DiscountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Configuration
class DiscountsConfiguration {

    @Bean
    PercentageBasedDiscountHandler percentageBasedDiscountHandler() {
        return new PercentageBasedDiscountHandler();
    }

    @Bean
    QuantityBasedDiscountHandler quantityBasedDiscountHandler() {
        return new QuantityBasedDiscountHandler();
    }

    @Bean
    DiscountCalculator discountCalculator(DiscountRepository discountRepository, Set<DiscountPolicyHandler> discountPolicyHandlers) {
        Map<DiscountPolicy, DiscountPolicyHandler> handlersByPolicy = discountPolicyHandlers.stream()
                .collect(toMap(DiscountPolicyHandler::handles, identity()));
        if (handlersByPolicy.size() != DiscountPolicy.values().length) {
            throw new IllegalStateException(
                    String.format("Invalid configuration detected. Missing discount policy handler(s). Supported polices: %s, handlers found for: %s",
                            DiscountPolicy.values(),
                            handlersByPolicy.keySet()));
        } else {
            return new DiscountCalculator(discountRepository, handlersByPolicy);
        }
    }
}
