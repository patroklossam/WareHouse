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

import com.storage.mywarehouse.Dao.ProductDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.storage.mywarehouse.Entity.Product;

/**
 *
 * @author veruz
 */
public class UtilsImportProductsFromCsvTest {

	private File file = new File("src/test/resources/products.csv");

	@AfterEach
	public void tearDown() {
		deleteTestEntries();
	}

	@Test
	public void testExistence() {
		List<Product> products = ProductDAO.findAllByBrandAndTypeStartLike("unit", "test");
		
		assertThat("should not find any product", products, hasSize(0));
	}

	@Test
	public void testParseProducts() throws IOException {
		Util.parseProducts(file);
		List<Product> products = ProductDAO.findAllByBrandAndTypeStartLike("unit", "test");

		assertThat("should find 2 products, inserted from products file", products, hasSize(2));
	}

	@Test
	public void testDeleteAll() throws IOException {
		Util.parseProducts(file);
		deleteTestEntries();
		List<Product> products = ProductDAO.findAllByBrandAndTypeStartLike("unit", "test");

		assertThat("should delete all products", products, hasSize(0));
	}

	private void deleteTestEntries() {
		List<Product> products = ProductDAO.findAllByBrandAndTypeStartLike("unit", "test");
		ProductDAO.deleteAll(products);
	}
}
