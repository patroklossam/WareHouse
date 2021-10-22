package com.storage.mywarehouse.View;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class QuantityHistoryViewTest {

    Date date = Date.from(Instant.parse("2000-01-01T00:00:00.000Z"));
    QuantityHistoryView quantityHistoryView = new QuantityHistoryView(1, "warehouse", "brand", "type", 1, date);

    @Test
    void getId() {
        Assert.assertEquals(1, quantityHistoryView.getId());
    }

    @Test
    void setId() {
        quantityHistoryView.setId(1);
        Assert.assertEquals(1, quantityHistoryView.getId());
    }

    @Test
    void getWarehouse() {
        Assert.assertEquals("warehouse", quantityHistoryView.getWarehouse());
    }

    @Test
    void setWarehouse() {
        quantityHistoryView.setWarehouse("warehouse");
        Assert.assertEquals("warehouse", quantityHistoryView.getWarehouse());
    }

    @Test
    void getBrand() {
        Assert.assertEquals("brand", quantityHistoryView.getBrand());
    }

    @Test
    void setBrand() {
        quantityHistoryView.setBrand("brand");
        Assert.assertEquals("brand", quantityHistoryView.getBrand());
    }

    @Test
    void getType() {
        Assert.assertEquals("type", quantityHistoryView.getType());
    }

    @Test
    void setType() {
        quantityHistoryView.setType("type");
        Assert.assertEquals("type", quantityHistoryView.getType());
    }

    @Test
    void getQuantity() {
        Assert.assertEquals(1, quantityHistoryView.getQuantity());
    }

    @Test
    void setQuantity() {
        quantityHistoryView.setQuantity(1);
        Assert.assertEquals(1, quantityHistoryView.getQuantity());
    }

    @Test
    void getDate() {
        Assert.assertEquals(date, quantityHistoryView.getDate());
    }

    @Test
    void setDate() {
        quantityHistoryView.setDate(date);
        Assert.assertEquals(date, quantityHistoryView.getDate());
    }

    @Test
    void testToString() {
        Assert.assertEquals("warehouse" + "\t" + "brand" + "\t" + "type" + "\t" + date + "\t" + 1, quantityHistoryView.toString());
    }
}