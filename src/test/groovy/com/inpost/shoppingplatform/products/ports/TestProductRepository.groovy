package com.inpost.shoppingplatform.products.ports

import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface TestProductRepository {

    @Insert('''
        INSERT INTO products(id, name, price_in_cents, currency_iso_code) 
        VALUES (#{productId}, #{name}, #{priceInCents}, #{currency})
    ''')
    void addProduct(
            @Param("productId") UUID productId,
            @Param("name") String name,
            @Param("priceInCents") Integer priceInCents,
            @Param("currency") String currency)

    @Delete("DELETE FROM products")
    void clearAll()
}
