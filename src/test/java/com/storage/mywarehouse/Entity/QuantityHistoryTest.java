package com.storage.mywarehouse.Entity;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QuantityHistoryTest {
    private static final Integer MOCK_INT = 1;
    private static final Date MOCK_DATE = new Date();
    private QuantityHistory sut;

    @Test
    public void testSettersGetters() {
        sut = new QuantityHistory();

        sut.setId(MOCK_INT);
        sut.setWareHouseEntryId(MOCK_INT);
        sut.setQuantity(MOCK_INT);
        sut.setDate(MOCK_DATE);

        assertEquals(MOCK_INT, sut.getId());
        assertEquals(MOCK_INT, sut.getWareHouseEntryId());
        assertEquals(MOCK_INT, sut.getQuantity());
        assertEquals(MOCK_DATE, sut.getDate());

        assertAll("test setter getter",
            () -> assertEquals(MOCK_INT, sut.getId()),
            () -> assertEquals(MOCK_INT, sut.getWareHouseEntryId()),
            () -> assertEquals(MOCK_INT, sut.getQuantity()),
            () -> assertEquals(MOCK_DATE, sut.getDate()));
    }

    @Test
    public void testConstructor() {
        sut = new QuantityHistory(MOCK_INT, MOCK_INT, MOCK_INT, MOCK_DATE);

        assertAll("test constructor",
            () -> assertEquals(MOCK_INT, sut.getId()),
            () -> assertEquals(MOCK_INT, sut.getWareHouseEntryId()),
            () -> assertEquals(MOCK_INT, sut.getQuantity()),
            () -> assertEquals(MOCK_DATE, sut.getDate()));
    }

    @Test
    public void testConstructor_WithOneParam() {
        sut = new QuantityHistory(MOCK_INT);

        assertEquals(MOCK_INT, sut.getId());
    }
}
