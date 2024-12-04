package com.inpost.shoppingplatform.products.ports;

import com.inpost.shoppingplatform.products.adapters.mybatis.UuidTypeHandler;
import com.inpost.shoppingplatform.products.adapters.mybatis.PercentageBasedDiscountRulesTypeHandler;
import com.inpost.shoppingplatform.products.adapters.mybatis.QuantityBasedDiscountRulesTypeHandler;
import com.inpost.shoppingplatform.products.discounts.DiscountPolicy;
import com.inpost.shoppingplatform.products.discounts.PercentageBasedDiscount;
import com.inpost.shoppingplatform.products.discounts.QuantityBasedDiscount;
import org.apache.ibatis.annotations.Arg;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface DiscountRepository {

    @Select("SELECT discount_policy FROM applicable_discounts WHERE product_id=#{productId}")
    Optional<DiscountPolicy> currentlyApplicable(@Param("productId") UUID productId);

    @ConstructorArgs({
            @Arg(column = "product_id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Arg(column = "discount_configuration", javaType = List.class, typeHandler = QuantityBasedDiscountRulesTypeHandler.class)
    })
    @Select("SELECT product_id, discount_configuration " +
            "FROM applicable_discounts " +
            "WHERE discount_policy = 'QUANTITY_BASED'::discount_policy AND product_id=#{productId}")
    Optional<QuantityBasedDiscount> getQuantityBasedDiscountFor(@Param("productId") UUID productId);


    @ConstructorArgs({
            @Arg(column = "product_id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Arg(column = "discount_configuration", javaType = List.class, typeHandler = PercentageBasedDiscountRulesTypeHandler.class)
    })
    @Select("SELECT product_id, discount_configuration " +
            "FROM applicable_discounts " +
            "WHERE discount_policy = 'PERCENTAGE_BASED'::discount_policy AND product_id=#{productId}")
    Optional<PercentageBasedDiscount> getPercentageBasedDiscountFor(@Param("productId") UUID productId);
}
