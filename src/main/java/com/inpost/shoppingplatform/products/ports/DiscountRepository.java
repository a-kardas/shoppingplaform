package com.inpost.shoppingplatform.products.ports;

import com.inpost.shoppingplatform.products.discounts.DiscountPolicy;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface DiscountRepository {

    Optional<DiscountPolicy> currentlyApplicable(UUID productId);
}
