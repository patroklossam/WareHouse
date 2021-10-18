package com.storage.mywarehouse.Dao;

import com.storage.mywarehouse.Entity.Entry;
import com.storage.mywarehouse.Entity.Product;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.storage.mywarehouse.Entity.Warehouse;
import com.storage.mywarehouse.View.WarehouseEntry;
import com.storage.mywarehouse.View.WarehouseProduct;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

public class WarehouseProductDaoTest {

    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int NINE = 9;
    private static final String ANY = "ANY";
    private static final String ANY2 = "ANY2";
    private static final double DOUBLE_VAL = 1.1;
    private Warehouse w1;
    private Product p1;
    private Product p2;
    private Entry e1;
    private Entry e2;
    private List<WarehouseProduct> rets;
    private List<WarehouseEntry> rets2;

    
    @BeforeEach
    public void init(){
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
    public void restore(){
        EntryDAO.deleteAll(EntryDAO.findAll());
        ProductDAO.deleteAll(ProductDAO.findAll());
        WarehouseDAO.deleteAll(WarehouseDAO.findAll());
    }

    @Test
    public void testSaveNFindAllNDeleteAll() {
        
        rets = WarehouseProductDAO.findById(ZERO);
        assertEquals(ONE, rets.size());
        assertEquals(ANY, rets.get(0).getBrand());
        
        rets = WarehouseProductDAO.findByQuantity(THREE);
        assertEquals(ONE, rets.size());
        assertEquals(ANY2, rets.get(0).getBrand());
        
        rets = WarehouseProductDAO.findByParam("Brand", ANY);
        assertEquals(ONE, rets.size());
        assertEquals(ANY, rets.get(0).getBrand());
        
        rets = WarehouseProductDAO.findByParamContainingValue("Brand", ANY);
        assertEquals(TWO, rets.size());
        
        
        rets2 = WarehouseEntryDAO.findByWarehouseId(ZERO);
        assertEquals(TWO, rets.size());
        assertEquals(ANY, rets.get(0).getBrand());
        
    }
}
