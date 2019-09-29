package com.storage.mywarehouse.Dao;

import com.storage.mywarehouse.Hibernate.NewHibernateUtil;
import com.storage.mywarehouse.View.WarehouseProduct;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class WarehouseProductDAO {
    @SuppressWarnings("unchecked")
    public static List<WarehouseProduct> findById(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List products = session.createCriteria(WarehouseProduct.class)
                .add(Restrictions.eq("productId", id))
                .list();
        tx.commit();
        session.close();
        return products;
    }

    @SuppressWarnings("unchecked")
    public static List<WarehouseProduct> findByQuantity(int quantity) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List emptyWarehouseProduct = session.createCriteria(WarehouseProduct.class)
                .add(Restrictions.eq("quantity", quantity))
                .list();
        tx.commit();
        session.close();
        return emptyWarehouseProduct;
    }
}
