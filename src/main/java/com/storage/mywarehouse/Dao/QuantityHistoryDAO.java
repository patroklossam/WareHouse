package com.storage.mywarehouse.Dao;

import com.storage.mywarehouse.Entity.Entry;
import com.storage.mywarehouse.Entity.QuantityHistory;
import com.storage.mywarehouse.Hibernate.NewHibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import java.util.Date;

public class QuantityHistoryDAO {
    public static QuantityHistory saveFrom(Entry entry) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(entry);

        QuantityHistory qh = new QuantityHistory();
        QuantityHistory pqh = (QuantityHistory) session.createCriteria(QuantityHistory.class).addOrder(Order.desc("id")).setMaxResults(1).uniqueResult();
        qh.setId(pqh == null ? 0 : pqh.getId() + 1);
        qh.setDate(new Date());
        qh.setWareHouseEntryId(entry.getEntryId());
        qh.setQuantity(entry.getQuantity());
        session.save(qh);

        tx.commit();
        session.close();
        return qh;
    }
}
