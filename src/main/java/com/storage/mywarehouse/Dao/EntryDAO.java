package com.storage.mywarehouse.Dao;

import com.storage.mywarehouse.Entity.Entry;
import com.storage.mywarehouse.Hibernate.NewHibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class EntryDAO {
    @SuppressWarnings("unchecked")
    public static List<Entry> findByWarehouseId(int warehouseId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List entries = session.createCriteria(Entry.class)
                .add(Restrictions.eq("warehouseId", warehouseId))
                .list();
        tx.commit();
        session.close();
        return entries;
    }

    public static void delete(Entry entry) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(entry);
        tx.commit();
        session.close();
    }
}
