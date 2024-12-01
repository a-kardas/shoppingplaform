package com.inpost.shoppingplatform.products;

import com.inpost.shoppingplatform.products.discounts.DiscountCalculator;
import com.inpost.shoppingplatform.products.ports.ProductsRepository;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.inpost.shoppingplatform.products.ports", annotationClass = Mapper.class)
class ProductsConfiguration {

    @Bean
    ProductPriceAssessor productPriceAssessor(ProductsRepository productsRepository, DiscountCalculator discountCalculator) {
        return new ProductPriceAssessor(productsRepository, discountCalculator);
    }
}
