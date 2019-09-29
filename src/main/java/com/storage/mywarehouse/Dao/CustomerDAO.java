package com.storage.mywarehouse.Dao;

import com.storage.mywarehouse.Entity.Customer;
import com.storage.mywarehouse.Hibernate.NewHibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CustomerDAO {
    public static void update(Customer customer) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(customer);
        tx.commit();
        session.close();
    }
}
