package com.storage.mywarehouse.Dao;

import com.storage.mywarehouse.Entity.Warehouse;
import com.storage.mywarehouse.Hibernate.NewHibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import java.util.List;

import static org.hibernate.criterion.Restrictions.and;
import static org.hibernate.criterion.Restrictions.eq;

public class WarehouseDAO {
    @SuppressWarnings("unchecked")
    public static List<Warehouse> findAll() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List warehouseList = session.createCriteria(Warehouse.class).list();
        tx.commit();
        session.close();
        return warehouseList;
    }

    public static Warehouse findByName(String name) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Warehouse existingWarehouse = (Warehouse) session.createCriteria(Warehouse.class)
                .add(and(
                        eq("name", name)
                )).uniqueResult();
        transaction.commit();
        session.close();
        return existingWarehouse;
    }

    public static Warehouse save(Warehouse warehouse) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        int warehouseId;
        Warehouse withHighestId = (Warehouse) session.createCriteria(Warehouse.class)
                .addOrder(Order.desc("warehouseId"))
                .setMaxResults(1)
                .uniqueResult();
        if (withHighestId == null) {
            warehouseId = 0;
        } else {
            warehouseId = withHighestId.getWarehouseId() + 1;
        }
        warehouse.setWarehouseId(warehouseId);
        session.save(warehouse);
        transaction.commit();
        session.close();
        return warehouse;
    }

    public static void deleteWarehouse(Warehouse warehouse) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(warehouse);
        tx.commit();
        session.close();
    }
}
