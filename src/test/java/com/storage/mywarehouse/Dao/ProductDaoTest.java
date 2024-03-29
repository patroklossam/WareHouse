package com.storage.mywarehouse.Dao;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.storage.mywarehouse.Entity.Product;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

public class ProductDaoTest {

    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final String ANY = "ANY";
    private static final String ANY2 = "ANY2";
    private static final double DOUBLE_VAL = 1.1;
    private Product sut1;
    private Product sut2;
    private Product res1;
    private List<Product> rets;

    @AfterEach
    public void restore() {
        ProductDAO.deleteAll(ProductDAO.findAll());
    }

    @Test
    public void testSaveNFindAllNDeleteAll() {
        sut1 = new Product(ZERO, ANY, ANY, DOUBLE_VAL);
        sut2 = new Product(ONE, ANY2, ANY2, DOUBLE_VAL);

        ProductDAO.save(sut1);
        ProductDAO.save(sut2);

        rets = ProductDAO.findAll();

        assertAll("test if products are saved",
                () -> assertEquals(TWO, rets.size()),
                () -> assertEquals(ANY, rets.get(0).getBrand()),
                () -> assertEquals(ANY2, rets.get(1).getBrand()));

        ProductDAO.delete(sut1);
        rets = ProductDAO.findAll();
        assertEquals(ONE, rets.size());
        
        res1 = ProductDAO.findById(ONE);
        assertEquals(ANY2, res1.getBrand());
        
        ProductDAO.deleteAll(ProductDAO.findAll());
        rets = ProductDAO.findAll();
        assertEquals(ZERO, rets.size());
    }
}
