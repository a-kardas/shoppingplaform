package com.inpost.shoppingplatform.products.ports;

import com.inpost.shoppingplatform.infrastructure.mybatis.UuidTypeHandler;
import com.inpost.shoppingplatform.products.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface ProductsRepository {

    @Results({
            @Result(property = "id", column = "id", typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name"),
            @Result(property = "unitPrice.valueInCents", column = "price_in_cents"),
            @Result(property = "unitPrice.currency", column = "currency_iso_code")
    })
    @Select("SELECT " +
            "id, name, price_in_cents, currency_iso_code " +
            "FROM products " +
            "WHERE id=#{id}")
    Optional<Product> getProductsById(@Param("id") UUID id);
}
