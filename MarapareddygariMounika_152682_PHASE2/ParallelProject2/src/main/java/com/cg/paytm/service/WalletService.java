package com.cg.paytm.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.cg.paytm.beans.Customer;
import com.cg.paytm.exception.InvalidInputException;

public interface WalletService {

	public Customer createAccount(String name, String mobileno, BigDecimal amount);

	public Customer showBalance(String mobileno) throws SQLException;

	public void acceptCustomerDetails(Customer cust);

	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount)
			throws InvalidInputException, SQLException;

	public Customer depositAmount(String mobileNo, BigDecimal amount) throws SQLException;

	public Customer withdrawAmount(String mobileNo, BigDecimal amount) throws SQLException;

	@SuppressWarnings("rawtypes")
	public List printTransaction(String mobileNo) throws SQLException;
}
