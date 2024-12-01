package com.inpost.shoppingplatform.products;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record Order(Set<OrderItem> items) {

    public Set<UUID> productIds() {
        return items.stream().map(OrderItem::productId).collect(Collectors.toSet());
    }

    public int numberOfProducts() {
        return items.size();
    }
}
