package com.storage.mywarehouse.Entity;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class CustomerTest {

    private static final int ONE = 1;
    private static final String ANY = "ANY";
    private static final double DOUBLE_VAL = 1.1;

    @Test
    public void testSettersGetters() {
        Customer sut = new Customer();

        sut.setCustomerId(ONE);
        sut.setName(ANY);
        sut.setLastName(ANY);
        sut.setOccupation(ANY);
        sut.setDiscount(DOUBLE_VAL);

        assertEquals(ONE, sut.getCustomerId());
        assertEquals(ANY, sut.getName());
        assertEquals(ANY, sut.getLastName());
        assertEquals(ANY, sut.getOccupation());
        assertEquals(DOUBLE_VAL, sut.getDiscount(), 0.01);
    }

    @Test
    public void testConstructor() {
        Customer sut = new Customer(ONE, ANY, ANY, ANY, DOUBLE_VAL);


        assertEquals(ONE, sut.getCustomerId());
        assertEquals(ANY, sut.getName());
        assertEquals(ANY, sut.getLastName());
        assertEquals(ANY, sut.getOccupation());
        assertEquals(DOUBLE_VAL, sut.getDiscount(), 0.01);
    }
}
