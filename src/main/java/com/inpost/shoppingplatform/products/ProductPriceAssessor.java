package com.inpost.shoppingplatform.products;

import com.inpost.shoppingplatform.products.discounts.DiscountCalculator;
import com.inpost.shoppingplatform.products.ports.ProductsRepository;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.function.Function.identity;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toSet;

@RequiredArgsConstructor
public class ProductPriceAssessor {

    private final ProductsRepository productsRepository;
    private final DiscountCalculator discountCalculator;

    public Set<PricedOrderItem> calculateTotalPrice(Order order) {
        Set<Product> selectedProducts = this.productsRepository.getProductsByIds(order.productIds());
        if (order.numberOfProducts() != selectedProducts.size()) {
            throw new MissingProductException(format("One or more products not found: %s", detectMissingProducts(order, selectedProducts)));
        }

        Map<UUID, Product> productsById = selectedProducts.stream().collect(Collectors.toMap(Product::getId, identity()));

        return order.items().stream().map(orderItem -> {
            Product product = productsById.get(orderItem.productId());
            Price regularTotalPrice = product.calculateRegularTotalPrice(orderItem.quantity());
            Price finalTotalPrice = this.discountCalculator.applyDiscountIfApplicable(orderItem, regularTotalPrice);
            return PricedOrderItem.of(orderItem, product, regularTotalPrice, finalTotalPrice);
        }).collect(toSet());
    }

    private static Set<UUID> detectMissingProducts(Order order, Set<Product> existingProducts) {
        Set<UUID> existingIds = existingProducts.stream().map(Product::getId).collect(toSet());
        return order.productIds().stream().filter(not(existingIds::contains)).collect(toSet());
    }
}
