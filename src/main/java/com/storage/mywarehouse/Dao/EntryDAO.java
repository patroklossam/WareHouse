package com.storage.mywarehouse.Dao;

import com.storage.mywarehouse.Entity.Entry;
import com.storage.mywarehouse.Entity.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import java.util.List;

import static org.hibernate.criterion.Restrictions.and;
import static org.hibernate.criterion.Restrictions.eq;

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

    public static Entry save(Entry entry) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Entry entryWithHighestId = (Entry) session.createCriteria(Entry.class)
                .addOrder(Order.desc("entryId"))
                .setMaxResults(1)
                .uniqueResult();
        int entryId = entryWithHighestId == null ? 0 : entryWithHighestId.getEntryId() + 1;
        entry.setEntryId(entryId);
        session.save(entry);
        tx.commit();
        session.close();
        return entry;
    }
    public static void delete(Entry entry) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(entry);
        tx.commit();
        session.close();
    }
    
    @SuppressWarnings("unchecked")
    public static List<Entry> findAll() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List entries = session.createCriteria(Entry.class).list();
        tx.commit();
        session.close();
        return entries;
    }
    public static void deleteAll(List<Entry> entries) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        for (Entry e : entries) {
            session.delete(e);
        }
        tx.commit();
        session.close();
    }

    public static boolean isProductDuplicate(int warehouseId,int productId){

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        List <Entry> entries = session.createCriteria(Entry.class).
                add(and(eq("productId", productId))).
                                add(and(eq("warehouseId",warehouseId)))
                .list();
        session.close();
        if(entries!=null){
            if(entries.size()>0) {
                return true;
            }
        }

        return false;


    }
    public static List<Entry> findProductInWarehouse(int warehouseId,int productId){

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        List <Entry> entries = session.createCriteria(Entry.class).
                add(and(eq("productId", productId))).
                add(and(eq("warehouseId",warehouseId)))
                .list();
        session.close();
        return entries;

    }


    public static void update(Entry entry){

        Session session=NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx=session.beginTransaction();
        session.update(entry);
        tx.commit();
        session.close();
    }


}
