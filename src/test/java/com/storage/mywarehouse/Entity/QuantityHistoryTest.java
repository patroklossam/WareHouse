package com.storage.mywarehouse.Entity;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import org.junit.jupiter.api.Test;

public class QuantityHistoryTest {
    private static final Integer MOCK_INT = 1;
    private static final Date MOCK_DATE = new Date();

    @Test
    public void testSettersGetters() {
        QuantityHistory sut = new QuantityHistory();
        sut.setId(MOCK_INT);
        sut.setWareHouseEntryId(MOCK_INT);
        sut.setQuantity(MOCK_INT);
        sut.setDate(MOCK_DATE);

        assertEquals(MOCK_INT, sut.getId());
        assertEquals(MOCK_INT, sut.getWareHouseEntryId());
        assertEquals(MOCK_INT, sut.getQuantity());
        assertEquals(MOCK_DATE, sut.getDate());
    }

    @Test
    public void testConstructor() {
        QuantityHistory sut = new QuantityHistory(MOCK_INT, MOCK_INT, MOCK_INT, MOCK_DATE);

        assertEquals(MOCK_INT, sut.getId());
        assertEquals(MOCK_INT, sut.getWareHouseEntryId());
        assertEquals(MOCK_INT, sut.getQuantity());
        assertEquals(MOCK_DATE, sut.getDate());
    }

    @Test
    public void testConstructor_WithOneParam() {
        QuantityHistory sut = new QuantityHistory(MOCK_INT);

        assertEquals(MOCK_INT, sut.getId());
    }
}
