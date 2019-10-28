
package com.storage.mywarehouse.Dao;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.storage.mywarehouse.Entity.Customer;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

public class CustomerDaoTest {

    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final String ANY = "ANY";
    private static final String ANY2 = "ANY2";
    private static final double DOUBLE_VAL = 1.1;
    private Customer sut1;
    private Customer sut2;
    private Customer ret1;
    private List<Customer> rets;

    @AfterEach
    public void restore(){
	CustomerDAO.deleteAll(CustomerDAO.findAll());
    }

    @Test
    public void testSaveNFindAllNDeleteAll() {
        sut1 = new Customer(ONE, ANY, ANY, ANY, DOUBLE_VAL, ANY, ANY, ANY, ANY);
        sut2 = new Customer(TWO, ANY2, ANY2, ANY2, DOUBLE_VAL, ANY2, ANY2, ANY2, ANY2);

	CustomerDAO.save(sut1);
	CustomerDAO.save(sut2);

	rets = CustomerDAO.findAll();

        assertAll("test if customers are saved",
            () -> assertEquals(TWO, rets.size()),
            () -> assertEquals(ANY, rets.get(0).getName()),
            () -> assertEquals(ANY2, rets.get(1).getLastName()));

	CustomerDAO.delete(sut1);
	rets = CustomerDAO.findAll();
	assertEquals(ONE, rets.size());

	CustomerDAO.deleteAll(CustomerDAO.findAll());
	rets = CustomerDAO.findAll();
	assertEquals(ZERO, rets.size());
    }

    @Test
    public void testUpdate() {
        sut1 = new Customer(ONE, ANY, ANY, ANY, DOUBLE_VAL, ANY, ANY, ANY, ANY);
	CustomerDAO.save(sut1);
	rets = CustomerDAO.findAll();

	ret1 = rets.get(0);
	assertEquals(ANY, ret1.getName());

	sut1.setName(ANY2);
	CustomerDAO.update(sut1);

	rets = CustomerDAO.findAll();
	ret1 = rets.get(0);

	assertEquals(ANY2, ret1.getName());
    }
}
