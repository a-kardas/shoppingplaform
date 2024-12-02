package com.inpost.shoppingplatform.products.ports;

import com.inpost.shoppingplatform.infrastructure.mybatis.UuidTypeHandler;
import com.inpost.shoppingplatform.products.adapters.mybatis.PercentageBasedDiscountRulesTypeHandler;
import com.inpost.shoppingplatform.products.adapters.mybatis.QuantityBasedDiscountRulesTypeHandler;
import com.inpost.shoppingplatform.products.discounts.DiscountPolicy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface DiscountRepository {

    @Select("SELECT discount_policy FROM applicable_discounts WHERE product_id=#{productId}")
    Optional<DiscountPolicy> currentlyApplicable(@Param("productId") UUID productId);

    @Results({
            @Result(property = "productId", column = "product_id", typeHandler = UuidTypeHandler.class),
            @Result(property = "rules", column = "discount_configuration", typeHandler = QuantityBasedDiscountRulesTypeHandler.class)
    })
    @Select("SELECT product_id, discount_configuration " +
            "FROM applicable_discounts " +
            "WHERE discount_policy = 'QUANTITY_BASED'::discount_policy AND product_id=#{productId}")
    Optional<QuantityBasedDiscount> getQuantityBasedDiscountFor(@Param("productId") UUID productId);


    @Results({
            @Result(property = "productId", column = "product_id", typeHandler = UuidTypeHandler.class),
            @Result(property = "rules", column = "discount_configuration", typeHandler = PercentageBasedDiscountRulesTypeHandler.class)
    })
    @Select("SELECT product_id, discount_configuration " +
            "FROM applicable_discounts " +
            "WHERE discount_policy = 'PERCENTAGE_BASED'::discount_policy AND product_id=#{productId}")
    Optional<PercentageBasedDiscount> getPercentageBasedDiscountFor(@Param("productId") UUID productId);
}
