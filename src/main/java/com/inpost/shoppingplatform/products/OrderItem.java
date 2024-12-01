package com.inpost.shoppingplatform.products;

import java.util.UUID;

public record OrderItem(UUID productId, int quantity) {}
