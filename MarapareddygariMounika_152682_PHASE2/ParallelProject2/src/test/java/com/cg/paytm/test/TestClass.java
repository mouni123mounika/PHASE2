package com.cg.paytm.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cg.paytm.beans.Customer;
import com.cg.paytm.beans.Wallet;
import com.cg.paytm.exception.InvalidInputException;
import com.cg.paytm.service.WalletService;
import com.cg.paytm.service.WalletServiceImpl;

public class TestClass {
	WalletService service;
	Customer cust1, cust2, cust3;

	@Before
	public void initData() throws ClassNotFoundException, SQLException {
		Map<String, Customer> data = new HashMap<String, Customer>();
		cust1 = new Customer("MOUNI", "9994521881", new Wallet(new BigDecimal(9000)));
		cust2 = new Customer("PAVI", "9963751616", new Wallet(new BigDecimal(6000)));
		cust3 = new Customer("YOGINI", "9922950519", new Wallet(new BigDecimal(7000)));

		data.put("9994521881", cust1);
		data.put("9963751616", cust2);
		data.put("9922950519", cust3);
		service = new WalletServiceImpl();

	}

	@Test(expected = NullPointerException.class)
	public void testCreateAccount() {
		service.createAccount(null, null, null);
	}

	@Test
	public void testCreateAccount1() {
		Customer c = new Customer();
		Customer cust = new Customer();
		cust = service.createAccount("MOUNI", "9994521881", new BigDecimal(7000));
		c.setMobileNo("9994521881");
		c.setName("MOUNI");
		c.setWallet(new Wallet(new BigDecimal(50000)));
		Customer actual = c;
		Customer expected = cust;
		assertNotSame(expected, actual);
	}

	@Test
	public void testCreateAccount2() {
		Customer cust = new Customer();
		cust = service.createAccount("MOUNIKA", "9441406680", new BigDecimal(5000));
	}

	@Test(expected = InvalidInputException.class)
	public void testCreateAccount3() {
		@SuppressWarnings("unused")
		Customer cust = new Customer();
		cust = service.createAccount("JAGAN", "9000417627", new BigDecimal(-7000));
	}

	public void testCreateAccount5() {
		service.createAccount("", "", new BigDecimal(0));
	}

	@Test
	public void testShowBalance2() throws SQLException {
		Customer cust = new Customer();
		cust = service.showBalance("9922950519");
		assertNotEquals(cust, cust3);
	}

	@Test
	public void testShowBalance3() throws SQLException {
		Customer cust = new Customer();
		cust = service.showBalance("9994521881");
		BigDecimal actual = cust.getWallet().getBalance();
		BigDecimal expected = new BigDecimal(9000);
		assertNotEquals(expected, actual);
	}

	@Test
	public void testFundTransfer2() throws InvalidInputException, SQLException {
		cust1 = service.fundTransfer("9994521881", "9963751616", new BigDecimal(2000));
		BigDecimal actual = cust1.getWallet().getBalance();
		BigDecimal expected = new BigDecimal(7000);
		assertNotEquals(expected, actual);
	}

	@Test(expected = InvalidInputException.class)
	public void testDeposit() throws SQLException {
		service.depositAmount("900", new BigDecimal(-2000));
	}

	@Test
	public void testDeposit2() throws SQLException {
		cust1 = service.depositAmount("9963751616", new BigDecimal(2000));
		BigDecimal actual = cust1.getWallet().getBalance();
		BigDecimal expected = new BigDecimal(8000);
		assertNotEquals(expected, actual);
	}

	@Test
	public void testWithdraw2() throws SQLException {
		cust1 = service.withdrawAmount("9963751616", new BigDecimal(2000));
		BigDecimal actual = cust1.getWallet().getBalance();
		BigDecimal expected = new BigDecimal(4000);
		assertNotEquals(expected, actual);
	}

	@After
	public void testAfter() {
		service = null;
	}

}
