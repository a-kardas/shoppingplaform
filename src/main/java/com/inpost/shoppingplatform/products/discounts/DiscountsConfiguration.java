package com.inpost.shoppingplatform.products.discounts;

import com.inpost.shoppingplatform.products.ports.DiscountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Configuration
class DiscountsConfiguration {

    @Bean
    PercentageBasedDiscountHandler percentageBasedDiscountHandler(DiscountRepository discountRepository) {
        return new PercentageBasedDiscountHandler(discountRepository);
    }

    @Bean
    QuantityBasedDiscountHandler quantityBasedDiscountHandler(DiscountRepository discountRepository) {
        return new QuantityBasedDiscountHandler(discountRepository);
    }

    @Bean
    DiscountCalculator discountCalculator(DiscountRepository discountRepository, Set<DiscountPolicyHandler> discountPolicyHandlers) {
        Map<DiscountPolicy, DiscountPolicyHandler> handlersByPolicy = discountPolicyHandlers.stream()
                .collect(toMap(
                        DiscountPolicyHandler::handles,
                        identity(),
                        (h1, h2) -> { throw new IllegalStateException("Detected at least two handlers responsible for the same discount policy!"); },
                        () -> new EnumMap<>(DiscountPolicy.class)));

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
