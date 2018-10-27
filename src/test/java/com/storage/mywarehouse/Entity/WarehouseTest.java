package com.storage.mywarehouse.Entity;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class WarehouseTest {
    private static final int ONE = 1;
    private static final String ANY = "ANY";

    @Test
    public void testSettersGetters() {
        Warehouse sut = new Warehouse();
        sut.setName(ANY);
        sut.setWarehouseId(ONE);

        assertEquals(ANY, sut.getName());
        assertEquals(ONE, sut.getWarehouseId());
    }

    @Test
    public void testConstructors() {
        Warehouse sut = new Warehouse(ONE, ANY);

        assertEquals(ANY, sut.getName());
        assertEquals(ONE, sut.getWarehouseId());
    }
}
