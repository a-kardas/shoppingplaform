package com.inpost.shoppingplatform.products.ports;

import com.inpost.shoppingplatform.products.adapters.mybatis.UuidTypeHandler;
import com.inpost.shoppingplatform.products.Product;
import org.apache.ibatis.annotations.Arg;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface ProductsRepository {

    @ConstructorArgs({
            @Arg(column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Arg(column = "name", javaType = String.class),
            @Arg(column = "price_in_cents", javaType = int.class),
            @Arg(column = "currency_iso_code", javaType = String.class)
    })
    @Select("SELECT " +
            "id, name, price_in_cents, currency_iso_code " +
            "FROM products " +
            "WHERE id=#{id}")
    Optional<Product> getProductsById(@Param("id") UUID id);
}
