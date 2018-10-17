package com.storage.mywarehouse.Entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    public void testProductConstructor() {
        // empty constructor
        final Product product = new Product();
        assertEquals(0, product.getProductId());
        assertNull(product.getBrand());
        assertNull(product.getType());
        assertNull(product.getDescription());
        assertEquals(0d, product.getPrice());
        product.setProductId(5);
        product.setBrand("brand");
        product.setType("type");
        product.setDescription("desc");
        product.setPrice(3.5);
        assertEquals(5, product.getProductId());
        assertEquals("brand", product.getBrand());
        assertEquals("type", product.getType());
        assertEquals("desc", product.getDescription());
        assertEquals(3.5, product.getPrice());

        // constructor with productId, brand, type, price
        final Product product2 = new Product(55, "brand2", "type2", 66.5);
        assertEquals(55, product2.getProductId());
        assertEquals("brand2", product2.getBrand());
        assertEquals("type2", product2.getType());
        assertNull(product2.getDescription());
        assertEquals(66.5, product2.getPrice());

        // constructor with productId, brand, type, description, price
        final Product product3 = new Product(32, "brand3", "type3", "desc3", 89.23);
        assertEquals(32, product3.getProductId());
        assertEquals("brand3", product3.getBrand());
        assertEquals("type3", product3.getType());
        assertEquals("desc3", product3.getDescription());
        assertEquals(89.23, product3.getPrice());
    }
}
