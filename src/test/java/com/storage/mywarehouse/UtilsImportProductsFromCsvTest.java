/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storage.mywarehouse;

import com.storage.mywarehouse.Entity.Product;
import com.storage.mywarehouse.Hibernate.NewHibernateUtil;
import com.storage.mywarehouse.Util;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

/**
 *
 * @author veruz
 */
public class UtilsImportProductsFromCsvTest {

    private Session session = null;
    private File file = null;
    public UtilsImportProductsFromCsvTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        file = new File("./products.csv");//src/test/resources/products.csv
        session = NewHibernateUtil.getSessionFactory().openSession();
    }
    
    @AfterEach
    public void tearDown() {
        file = null;
        
        deleteTestEntries();
        session.close();
    }

    
    @Test
    public void testExistense(){
    List<Product> products = session.createCriteria(Product.class)
                .add(Restrictions.and(Restrictions.eq("brand", "unit"),Restrictions.like("type", "test", MatchMode.START))).list();
        
        assertEquals(0, products.size());
    }
    
    @Test
    public void testParseProducts() throws IOException{
        
        Util.parseProducts(file);
        
        List<Product> products = session.createCriteria(Product.class)
                .add(Restrictions.and(Restrictions.eq("brand", "unit"),Restrictions.like("type", "test", MatchMode.START))).list();
        
        assertEquals(2, products.size());
        
    }
    
    private void deleteTestEntries(){
        List<Product> products = session.createCriteria(Product.class)
                .add(Restrictions.and(Restrictions.eq("brand", "unit"),Restrictions.like("type", "test", MatchMode.START))).list();
        
        Transaction transaction = session.beginTransaction();
        for(Product p : products){
            session.delete(p);
        }
        transaction.commit();
    }
}
