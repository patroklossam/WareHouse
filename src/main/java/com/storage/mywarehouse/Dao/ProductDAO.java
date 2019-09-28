package com.storage.mywarehouse.Dao;

import com.storage.mywarehouse.Entity.Product;
import com.storage.mywarehouse.Hibernate.NewHibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import static org.hibernate.criterion.Restrictions.and;
import static org.hibernate.criterion.Restrictions.eq;

public class ProductDAO {

    public static Product findBy(String brand, String type) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Product existingProduct = (Product) session.createCriteria(Product.class)
                .add(and(eq("brand", brand), eq("type", type)))
                .uniqueResult();
        transaction.commit();
        session.close();
        return existingProduct;
    }

    public static Product saveWith(String brand, String type, String description, double price) {
        int productId;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Product productWithHighestId = (Product) session.createCriteria(Product.class).addOrder(Order.desc("productId")).setMaxResults(1).uniqueResult();
        transaction.commit();
        if (productWithHighestId == null) {
            productId = 0;
        } else {
            productId = productWithHighestId.getProductId() + 1;
        }
        Product p = new Product(productId, brand, type, description, price);
        transaction = session.beginTransaction();
        session.save(p);
        transaction.commit();
        session.close();
        return p;
    }
}
