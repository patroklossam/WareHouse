package com.storage.mywarehouse.Dao;

import com.storage.mywarehouse.Entity.Customer;
import com.storage.mywarehouse.Hibernate.NewHibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import java.util.List;

public class CustomerDAO {
    @SuppressWarnings("unchecked")
    public static List<Customer> findAll() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List customers = session.createCriteria(Customer.class).list();
        tx.commit();
        session.close();
        return customers;
    }

    public static void save(Customer customer) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Customer customerWithHighestId = (Customer) session.createCriteria(Customer.class).addOrder(Order.desc("customerId")).setMaxResults(1).uniqueResult();
        int nextId;
        if (customerWithHighestId == null) {
            nextId = 0;
        } else {
            nextId = customerWithHighestId.getCustomerId() + 1;
        }
        customer.setCustomerId(nextId);
        session.save(customer);
        tx.commit();
        session.close();
    }

    public static void update(Customer customer) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(customer);
        tx.commit();
        session.close();
    }

    public static void delete(Customer customer) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(customer);
        tx.commit();
        session.close();
    }
}
