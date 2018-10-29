package com.storage.mywarehouse.Entity;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ColumnTest {

    private static final int ONE = 1;
    private static final String ANY = "ANY";
    private Column sut;

    @Test
    public void testSettersGetters() {
        // given
        sut = new Column();

        sut.setColumnId(ONE);
        sut.setName(ANY);

        // then
        Assertions.assertAll("test setter getters",
            () -> assertEquals(ONE, sut.getColumnId()),
            () -> assertEquals(ANY, sut.getName()));
    }

    @Test
    public void testConstructor() {
        // given
        sut = new Column(ONE, ANY);

        // then
        Assertions.assertAll("test constructor",
            () -> assertEquals(ONE, sut.getColumnId()),
            () -> assertEquals(ANY, sut.getName()));
    }
}
