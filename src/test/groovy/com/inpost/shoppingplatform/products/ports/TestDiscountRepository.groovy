package com.inpost.shoppingplatform.products.ports

import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface TestDiscountRepository {

    @Insert('''
        INSERT INTO applicable_discounts(product_id, discount_policy, discount_configuration) 
        VALUES (#{productId}, 'QUANTITY_BASED'::discount_policy, #{jsonConfiguration}::json)
    ''')
    void addQuantityBasedDiscountConfiguration(
            @Param("productId") UUID productId,
            @Param("jsonConfiguration") jsonDiscountConfiguration)

    @Insert('''
        INSERT INTO applicable_discounts(product_id, discount_policy, discount_configuration) 
        VALUES (#{productId}, 'PERCENTAGE_BASED'::discount_policy, #{jsonConfiguration}::json)
    ''')
    void addPercentageBasedDiscountConfiguration(
            @Param("productId") UUID productId,
            @Param("jsonConfiguration") jsonDiscountConfiguration)

    @Delete("DELETE FROM applicable_discounts")
    void clearAll()
}
