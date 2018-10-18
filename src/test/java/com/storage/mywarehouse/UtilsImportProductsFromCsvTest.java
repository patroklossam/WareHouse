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
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ClassLoader classLoader = getClass().getClassLoader();
        
        file = new File("src/test/resources/products.csv");
//        file = new File(classLoader.getResource("products.csv").getFile());
        session = NewHibernateUtil.getSessionFactory().openSession();
    }
    
    @After
    public void tearDown() {
        file = null;
        
        deleteTestEntries();
        session.close();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    private void deleteTestEntries(){
        List products = session.createCriteria(Product.class)
                .add(Restrictions.and(Restrictions.eq("brand", "unit"),Restrictions.like("type", "test", MatchMode.START))).list();
        
        for(Product p : (List<Product>) products){
            session.delete(p);
            
        }
    }
    
    @Test
    public void testExistense(){
    List products = session.createCriteria(Product.class)
                .add(Restrictions.and(Restrictions.eq("brand", "unit"),Restrictions.like("type", "test", MatchMode.START))).list();
        
        assertEquals(0, products.size());
    }
    
    @Test
    public void testParseProducts() throws IOException{
        
        Util.parseProducts(file);
        
        List products = session.createCriteria(Product.class)
                .add(Restrictions.and(Restrictions.eq("brand", "unit"),Restrictions.like("type", "test", MatchMode.START))).list();
        assertNotNull(products);
        assertEquals(2, products.size());
        
    }
}
