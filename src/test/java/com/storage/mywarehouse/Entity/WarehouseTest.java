package com.storage.mywarehouse.Entity;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

public class WarehouseTest {

    private static final int ONE = 1;
    private static final String ANY = "ANY";
    private Warehouse sut;


    @Test
    public void testSettersGetters() {
        sut = new Warehouse();
        sut.setName(ANY);
        sut.setWarehouseId(ONE);

        assertAll("test all setters getters",
            () -> assertEquals(ANY, sut.getName()),
            () -> assertEquals(ONE, sut.getWarehouseId()));
    }

    @Test
    public void testConstructors() {
        sut = new Warehouse(ONE, ANY);

        assertEquals(ANY, sut.getName());
        assertEquals(ONE, sut.getWarehouseId());

        assertAll("test constructor",
            () -> assertEquals(ANY, sut.getName()),
            () -> assertEquals(ONE, sut.getWarehouseId()));
    }
}
