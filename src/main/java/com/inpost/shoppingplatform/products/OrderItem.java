package com.inpost.shoppingplatform.products;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItem(@NotNull(message = "Product id is required.") UUID productId, @Min(value = 0, message = "The quantity must be positive.") int quantity) {}
