package com.storage.mywarehouse.Dao;

import com.storage.mywarehouse.View.WarehouseEntry;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class WarehouseEntryDAO {
    @SuppressWarnings("unchecked")
    public static List<WarehouseEntry> findByWarehouseId(int warehouseId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List warehouseEntries = session.createCriteria(WarehouseEntry.class)
                .add(Restrictions.eq("warehouseId", warehouseId))
                .list();
        tx.commit();
        session.close();
        return warehouseEntries;
    }
}
