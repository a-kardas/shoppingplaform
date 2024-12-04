package com.inpost.shoppingplatform.products.adapters;

import com.inpost.shoppingplatform.products.OrderItem;
import com.inpost.shoppingplatform.products.PricedOrderItem;
import com.inpost.shoppingplatform.products.ProductPriceAssessor;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductPriceAssessor productPriceAssessor;

    @PostMapping("/price")
    PricedOrderItem priceOrderItem(@RequestBody @Validated OrderItem orderItem){
        return productPriceAssessor.calculateTotalPrice(orderItem);
    }
}
