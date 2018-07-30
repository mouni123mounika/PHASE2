package com.cg.paytm.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.cg.paytm.beans.Customer;
import com.cg.paytm.beans.Wallet;

public class WalletRepoImpl implements IWalletRepo {
	Connection con = null;

	public WalletRepoImpl() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "Capgemini123");
		con.setAutoCommit(true);
	}

	public boolean save(Customer customer) {
		// TODO Auto-generated method stub
		PreparedStatement p, p1, p2, p3;
		try {
			p = con.prepareStatement("select * from customer where mobilenumber=?");
			p.setString(1, customer.getMobileNo());
			ResultSet i = p.executeQuery();
			if (i.next()) {
				p2 = con.prepareStatement("update wallet set balance = ? where mobilenumber=?");
				p2.setBigDecimal(1, customer.getWallet().getBalance());
				p2.setString(2, customer.getMobileNo());
				p2.executeUpdate();
				p2.close();
				return false;
			}
			p = con.prepareStatement("Insert into customer values(?,?)");
			p.setString(1, customer.getMobileNo());
			p.setString(2, customer.getName());
			p.execute();
			p1 = con.prepareStatement("Insert into wallet values(?,?)");
			p1.setString(1, customer.getMobileNo());
			p1.setBigDecimal(2, customer.getWallet().getBalance());
			p1.execute();
			p3 = con.prepareStatement("Insert into transaction values(?,'Accounted created')");
			p3.setString(1, customer.getMobileNo());
			p3.execute();
			p.close();
			p1.close();
			p3.close();

		} catch (SQLException e) {
			System.out.println("");
		}
		return true;
	}

	public Customer findOne(String mobileNo) throws SQLException {
		@SuppressWarnings("unused")
		PreparedStatement p, p1, p2;
		p = con.prepareStatement("select * from customer where mobilenumber=?");
		p.setString(1, mobileNo);
		p1 = con.prepareStatement("select * from wallet where mobilenumber=?");
		p1.setString(1, mobileNo);

		ResultSet r = p.executeQuery();
		ResultSet r1 = p1.executeQuery();

		Customer c = new Customer();
		while (r.next()) {
			c.setName(r.getString(1));
			c.setMobileNo(r.getString(2));
		}
		while (r1.next())
			c.setWallet(new Wallet(r1.getBigDecimal(2)));
		if (c.getMobileNo() == null) {
			return null;
		} else {
			return c;
		}
	}

	public void saveTransaction(String mobileNo, String s) throws SQLException {
		PreparedStatement p;
		p = con.prepareStatement("insert into transaction values(?,?)");
		p.setString(1, mobileNo);
		p.setString(2, s);
		p.execute();
		p.close();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getTransaction(String mobileNo) throws SQLException {
		PreparedStatement p;
		p = con.prepareStatement("select statement from transaction where mobilenumber=?");
		p.setString(1, mobileNo);

		ResultSet rs = p.executeQuery();
		List list = new LinkedList();
		while (rs.next()) {
			list.add(rs.getString(1));
		}
		p.close();
		return list;
	}

}
