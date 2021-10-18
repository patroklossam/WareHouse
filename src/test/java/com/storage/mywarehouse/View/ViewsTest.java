package com.storage.mywarehouse.View;

import com.storage.mywarehouse.Dao.*;
import com.storage.mywarehouse.Entity.Entry;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.storage.mywarehouse.Entity.Product;
import com.storage.mywarehouse.Entity.Warehouse;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class ViewsTest {

    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int NINE = 9;
    private static final String ANY = "ANY";
    private static final String ANY2 = "ANY2";
    private static final String ANY3 = "ANY3";
    private static final double DOUBLE_VAL = 1.1;
    private Warehouse w1;
    private Product p1;
    private Product p2;
    private Entry e1;
    private Entry e2;
    private Entry ret1;
    private List<Entry> rets;

    @BeforeEach
    public void init() {
        w1 = new Warehouse(ANY);
        WarehouseDAO.save(w1);

        p1 = new Product(ZERO, ANY, ANY, DOUBLE_VAL);
        p2 = new Product(ONE, ANY2, ANY2, DOUBLE_VAL);
        ProductDAO.save(p1);
        ProductDAO.save(p2);

        e1 = new Entry(ZERO, ZERO, ZERO, TWO);
        e2 = new Entry(ONE, ZERO, ONE, THREE);

        EntryDAO.save(e1);
        EntryDAO.save(e2);

    }

    @AfterEach
    public void restore() {
        EntryDAO.deleteAll(EntryDAO.findAll());
        ProductDAO.deleteAll(ProductDAO.findAll());
        WarehouseDAO.deleteAll(WarehouseDAO.findAll());
    }

    @Test
    public void testViews() {

        WarehouseProduct wp = WarehouseProductDAO.findById(ONE).get(0);

        WarehouseProduct test = new WarehouseProduct(ONE, ONE, ANY2, ANY2, THREE, DOUBLE_VAL, ANY);
        
        assertEquals(wp.getBrand(), test.getBrand());
        assertEquals(wp.getId(), test.getId());
        assertEquals(wp.getProductId(), test.getProductId());
        assertEquals(wp.getWarehouse(), test.getWarehouse());

    }
}
