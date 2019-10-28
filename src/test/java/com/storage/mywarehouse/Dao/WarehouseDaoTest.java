package com.storage.mywarehouse.Dao;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.storage.mywarehouse.Entity.Warehouse;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

public class WarehouseDaoTest {

    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final String ANY = "ANY";
    private static final String ANY2 = "ANY2";
    private static final String ANY3 = "ANY3";
    private static final double DOUBLE_VAL = 1.1;
    private Warehouse sut1;
    private Warehouse sut2;
    private Warehouse sut3;
    private Warehouse ret1;
    private List<Warehouse> rets;

    @AfterEach
    public void restore(){
	WarehouseDAO.deleteAll(WarehouseDAO.findAll());
    }

    @Test
    public void testSaveNFindAllNDeleteAll() {
        sut1 = new Warehouse(ANY);
        sut2 = new Warehouse(ANY2);
        sut3 = new Warehouse(ANY3);

	WarehouseDAO.save(sut1);
	WarehouseDAO.save(sut2);
	WarehouseDAO.save(sut3);

	rets = WarehouseDAO.findAll();

        assertAll("test if warehouses are saved",
            () -> assertEquals(THREE, rets.size()),
            () -> assertEquals(ONE, WarehouseDAO.findByName(ANY2).getWarehouseId()),
            () -> assertEquals(ZERO, WarehouseDAO.findByName(ANY).getWarehouseId()),
            () -> assertEquals(TWO, WarehouseDAO.findByName(ANY3).getWarehouseId()));
	
	WarehouseDAO.delete(sut2);
	rets = WarehouseDAO.findAll();

	assertEquals(TWO, rets.size());

	WarehouseDAO.deleteAll(WarehouseDAO.findAll());
	rets = WarehouseDAO.findAll();

	assertEquals(ZERO, rets.size());
    }

    @Test
    public void testUpdate() {
        sut1 = new Warehouse(ONE, ANY);
	WarehouseDAO.save(sut1);
	ret1 = WarehouseDAO.findByName(ANY);

	assertEquals(ANY, ret1.getName());

	sut1.setName(ANY2);
	WarehouseDAO.update(sut1);

	assertEquals(ret1.getWarehouseId(), WarehouseDAO.findByName(ANY2).getWarehouseId());
    }
}

