package com.storage.mywarehouse.Entity;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class EntryTest {

    private static final int ONE = 1;

    @Test
    public void testGettersSetters() {
        // given when
        Entry sut = new Entry();
        sut.setEntryId(ONE);
        sut.setWarehouseId(ONE);
        sut.setProductId(ONE);
        sut.setQuantity(ONE);

        // then
        assertEquals(ONE, sut.getEntryId());
        assertEquals(ONE, sut.getWarehouseId());
        assertEquals(ONE, sut.getProductId());
        assertEquals(ONE, sut.getQuantity());
    }


    @Test
    public void testConstructor() {
        // given when
        Entry sut = new Entry(ONE, ONE, ONE, ONE);

        // then
        assertEquals(ONE, sut.getQuantity());
        assertEquals(ONE, sut.getWarehouseId());
        assertEquals(ONE, sut.getProductId());
        assertEquals(ONE, sut.getQuantity());
    }
}
