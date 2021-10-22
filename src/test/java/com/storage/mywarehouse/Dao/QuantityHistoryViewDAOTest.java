package com.storage.mywarehouse.Dao;

import com.storage.mywarehouse.View.QuantityHistoryView;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuantityHistoryViewDAOTest {

    @Test
    void findAll() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        List q_history = session.createCriteria(QuantityHistoryView.class)
                .addOrder(Order.asc("warehouse"))
                .addOrder(Order.asc("brand"))
                .addOrder(Order.asc("type"))
                .addOrder(Order.asc("date"))
                .list();
        session.close();
        Assert.assertEquals(q_history, QuantityHistoryViewDAO.findAll());
    }
}