package com.cg.paytm.repo;

import java.sql.SQLException;
import java.util.List;

import com.cg.paytm.beans.Customer;

public interface IWalletRepo {

	public boolean save(Customer customer);

	public Customer findOne(String mobileNo) throws SQLException;

	public void saveTransaction(String sourceMobileNo, String string) throws SQLException;

	@SuppressWarnings("rawtypes")
	public List getTransaction(String mobileNo) throws SQLException;
}
