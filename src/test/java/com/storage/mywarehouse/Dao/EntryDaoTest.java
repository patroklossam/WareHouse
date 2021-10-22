package com.storage.mywarehouse.Dao;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.storage.mywarehouse.Entity.Warehouse;
import com.storage.mywarehouse.Entity.Product;
import com.storage.mywarehouse.Entity.Entry;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

public class EntryDaoTest {

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

    @AfterEach
    public void restore(){
	EntryDAO.deleteAll(EntryDAO.findAll());
    }

    @Test
    public void testSaveNFindAllNDeleteAll() {
        w1 = new Warehouse(ANY);
	WarehouseDAO.save(w1);
        
        p1 = new Product(ONE, ANY, ANY, DOUBLE_VAL);
        p2 = new Product(TWO, ANY2, ANY2, DOUBLE_VAL);
        ProductDAO.save(p1);
        ProductDAO.save(p2);

        e1 = new Entry(ZERO, ONE, ONE, TWO);
        e2 = new Entry(ONE, ONE, TWO, THREE);
        
        EntryDAO.save(e1);
        EntryDAO.save(e2);
        
        rets = EntryDAO.findAll();

        assertAll("test if entries are saved",
            () -> assertEquals(TWO, rets.size()),
            () -> assertEquals(TWO, EntryDAO.findByWarehouseId(ONE).size()),
            () -> assertEquals(TWO, EntryDAO.findProductInWarehouse(ONE, ONE).get(0).getQuantity()),
            () -> assertEquals(THREE, EntryDAO.findProductInWarehouse(ONE, TWO).get(0).getQuantity()),
            () -> assertEquals(true, EntryDAO.isProductDuplicate(ONE, ONE)));
	
        e1.setQuantity(NINE);
        EntryDAO.update(e1);
        
        rets = EntryDAO.findProductInWarehouse(ONE, ONE);
        
        assertEquals(NINE, rets.get(0).getQuantity());
        
	EntryDAO.delete(e2);
	rets = EntryDAO.findAll();

	assertEquals(ONE, rets.size());

	EntryDAO.deleteAll(EntryDAO.findAll());
	rets = EntryDAO.findAll();

	assertEquals(ZERO, rets.size());
    }
}

