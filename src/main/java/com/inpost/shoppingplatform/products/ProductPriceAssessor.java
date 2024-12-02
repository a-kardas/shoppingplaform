package com.inpost.shoppingplatform.products;

import com.inpost.shoppingplatform.products.discounts.DiscountCalculator;
import com.inpost.shoppingplatform.products.ports.ProductsRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static java.lang.String.format;

@RequiredArgsConstructor
public class ProductPriceAssessor {

    private final ProductsRepository productsRepository;
    private final DiscountCalculator discountCalculator;

    public PricedOrderItem calculateTotalPrice(OrderItem orderItem) {
        Optional<Product> selectedProduct = this.productsRepository.getProductsById(orderItem.productId());
        return selectedProduct
                .map(product -> {
                    Price regularTotalPrice = product.calculateRegularTotalPrice(orderItem.quantity());
                    Price finalTotalPrice = this.discountCalculator.applyDiscountIfApplicable(orderItem, regularTotalPrice);
                    return PricedOrderItem.of(orderItem, product, regularTotalPrice, finalTotalPrice);
                })
                .orElseThrow(() -> new MissingProductException(format("Product not found: %s", orderItem.productId())));
    }
}
