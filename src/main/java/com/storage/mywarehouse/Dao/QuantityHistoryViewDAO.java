package com.storage.mywarehouse.Dao;

import com.storage.mywarehouse.Hibernate.NewHibernateUtil;
import com.storage.mywarehouse.View.QuantityHistoryView;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import java.util.List;

public class QuantityHistoryViewDAO {

    public static List findAll() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        List q_history = session.createCriteria(QuantityHistoryView.class)
                .addOrder(Order.asc("warehouse"))
                .addOrder(Order.asc("brand"))
                .addOrder(Order.asc("type"))
                .addOrder(Order.asc("date"))
                .list();
        session.close();
        return q_history;
    }
}
