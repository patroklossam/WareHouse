package com.storage.mywarehouse.Entity;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class ColumnTest {

    private static final int ONE = 1;
    private static final String ANY = "ANY";

    @Test
    public void testSettersGetters() {
        // given
        Column sut = new Column();
        sut.setColumnId(ONE);
        sut.setName(ANY);

        // then
        assertEquals(ONE, sut.getColumnId());
        assertEquals(ANY, sut.getName());
    }

    @Test
    public void testConstructor() {
        // given
        Column sut = new Column(ONE, ANY);

        // then
        assertEquals(ONE, sut.getColumnId());
        assertEquals(ANY, sut.getName());
    }
}
