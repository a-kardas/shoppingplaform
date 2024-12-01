package com.inpost.shoppingplatform.products.ports;

import com.inpost.shoppingplatform.products.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;
import java.util.UUID;

@Mapper
public interface ProductsRepository {

    Set<Product> getProductsByIds(Set<UUID> ids);
}
