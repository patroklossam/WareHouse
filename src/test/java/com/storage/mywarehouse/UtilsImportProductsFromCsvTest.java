/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storage.mywarehouse;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.hasSize;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.storage.mywarehouse.Entity.Product;
import com.storage.mywarehouse.Hibernate.NewHibernateUtil;

/**
 *
 * @author veruz
 */
public class UtilsImportProductsFromCsvTest {

	private Session session = null;
	private File file = null;


	@BeforeEach
	public void setUp() {
		file = new File("src/test/resources/products.csv");
		session = NewHibernateUtil.getSessionFactory().openSession();
	}

	@AfterEach
	public void tearDown() {
		file = null;

		deleteTestEntries();
		session.close();
	}

	@Test
	public void testExistense() {
		List<Product> products = readAllTestProductsFromDatabase();
		
		assertThat("should not find any product", products, hasSize(0));
	}

	@Test
	public void testParseProducts() throws IOException {
		Util.parseProducts(file);
		List<Product> products = readAllTestProductsFromDatabase();

		assertThat("should find 2 products, inserted from products file", products, hasSize(2));
	}

	@SuppressWarnings("unchecked")
	private List<Product> readAllTestProductsFromDatabase() {
		return session.createCriteria(Product.class).add(
				Restrictions.and(Restrictions.eq("brand", "unit"), Restrictions.like("type", "test", MatchMode.START)))
				.list();
	}

	private void deleteTestEntries() {
		List<Product> products = readAllTestProductsFromDatabase();

		Transaction transaction = session.beginTransaction();
		for (Product p : products) {
			session.delete(p);
		}
		transaction.commit();
	}
}
