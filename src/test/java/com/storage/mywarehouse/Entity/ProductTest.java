package com.storage.mywarehouse.Entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

	private Product subjectUnderTest;

	@Test
	public void standardconstructorSetsDefaultValues() {
		subjectUnderTest = new Product();

		assertAll("standardConstructor", () -> assertEquals(0, subjectUnderTest.getProductId()),
				() -> assertNull(subjectUnderTest.getBrand()), () -> assertNull(subjectUnderTest.getType()),
				() -> assertNull(subjectUnderTest.getDescription()),
				() -> assertEquals(0d, subjectUnderTest.getPrice()));
	}

	@Test
	public void constructorProductIdBrandTypePriceSetsCorrectValues() {
		int anyProductId = 55;
		String anyBrand = "brand2";
		String anyType = "type2";
		double anyPrice = 66.5;
		subjectUnderTest = new Product(anyProductId, anyBrand, anyType, anyPrice);

		assertAll("initialize with productId brand type price",
				() -> assertEquals(anyProductId, subjectUnderTest.getProductId()),
				() -> assertEquals(anyBrand, subjectUnderTest.getBrand()),
				() -> assertEquals(anyType, subjectUnderTest.getType()),
				() -> assertNull(subjectUnderTest.getDescription()),
				() -> assertEquals(anyPrice, subjectUnderTest.getPrice()));
	}

	@Test
	public void constructorProductIdBrandTypeDescriptionPriceSetsCorrectValues() {
		int anyProductId = 32;
		String anyBrand = "brand3";
		String anyType = "type3";
		String anyDescription = "description3";
		double anyPrice = 89.23;
		subjectUnderTest = new Product(anyProductId, anyBrand, anyType, anyDescription, anyPrice);
		assertAll("initialize with productId brand type description price",
				() -> assertEquals(anyProductId, subjectUnderTest.getProductId()),
				() -> assertEquals(anyBrand, subjectUnderTest.getBrand()),
				() -> assertEquals(anyType, subjectUnderTest.getType()),
				() -> assertEquals(anyDescription, subjectUnderTest.getDescription()),
				() -> assertEquals(anyPrice, subjectUnderTest.getPrice()));
	}


}
