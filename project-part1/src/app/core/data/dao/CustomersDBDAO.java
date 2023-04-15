package app.core.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.core.beans.Customer;
import app.core.data.ConnectionPool;
import app.core.exception.CouponSystemException;

public class CustomersDBDAO implements CustomersDAO {

	/**
	 * The method receives a customer's email and password and checks in the
	 * database whether the customer exists in the customers table.
	 * 
	 * @param email    - of a customer.
	 * @param password - of a customer.
	 * @return boolean value does the customer exist in the database using the email
	 *         and password.
	 * @throws CouponSystemException if the data check against the database failed.
	 */
	@Override
	public boolean isCustomerExists(String email, String password) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select exists(select * from `customers` where (email=?)and(password = ?));";
		try (PreparedStatement stmt = con.prepareStatement(sql);) {
			stmt.setString(1, email);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int answer = rs.getInt(1);
			if (answer == 1) {
				return true;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("isCustomerExists failed");
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
		return false;
	}

	/**
	 * The method receives a customer's id and checks in the database whether the
	 * customer exists in the customers table.
	 * 
	 * @param customerID - customer ID number.
	 * @return boolean value Whether the customer exists in the database using the
	 *         customer id.
	 * @throws CouponSystemException if the data check against the database failed.
	 */
	@Override
	public boolean isCustomerIDExists(int customerID) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select exists(select * from `customers` where id=?);";
		try (PreparedStatement stmt = con.prepareStatement(sql);) {
			stmt.setInt(1, customerID);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int answer = rs.getInt(1);
			if (answer == 1) {
				return true;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Checking whether the customer exists by id failed");
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
		return false;
	}

	/**
	 * The method receives a customer's email and checks in the database whether the
	 * customer exists in the customers table.
	 * 
	 * @param email - of a customer.
	 * @return boolean value does the customer exists in the database using the
	 *         email.
	 * @throws CouponSystemException if the data check against the database failed.
	 */
	@Override
	public boolean isCustomerEmailExists(String email) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select exists(select * from `customers` where (email = ?));";
		try (PreparedStatement stmt = con.prepareStatement(sql);) {
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int answer = rs.getInt(1);
			if (answer == 1) {
				return true;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Checking whether the customer exists by email failed");
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
		return false;
	}

	/**
	 * The method receives a customer and adds it to the customers table in the
	 * database. and initializes the customer id according to the id the customer
	 * received in the database.
	 * 
	 * @param customer - customer details: first name, last name, email and
	 *                 password.
	 * @throws CouponSystemException if adding the customer to the database
	 *                               customers table failed.
	 */
	@Override
	public void addCustomer(Customer customer) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "insert into `customers` values(0, ?, ?, ?, ?);";
		try (PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pstm.setString(1, customer.getFirstName());
			pstm.setString(2, customer.getLastName());
			pstm.setString(3, customer.getEmail());
			pstm.setString(4, customer.getPassword());
			pstm.executeUpdate();
			ResultSet rs = pstm.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1);
			customer.setId(id);
			System.out.println("Customer " + customer.getId() + " has been successfully added");
		} catch (SQLException e) {
			throw new CouponSystemException("Add customer failed");
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	/**
	 * The method receives a customer with updated details, and updates the customer
	 * details in the database by the customer id.
	 * 
	 * @param customer - customer details: first name, last name, email and password
	 *                 after updating.
	 * @throws CouponSystemException if the customer update in the database fails.
	 */
	@Override
	public void updateCustomer(Customer customer) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "update `customers` set first_name = ?, last_name = ?, email = ?, password = ? where id =?;";
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, customer.getFirstName());
			pstmt.setString(2, customer.getLastName());
			pstmt.setString(3, customer.getEmail());
			pstmt.setString(4, customer.getPassword());
			pstmt.setInt(5, customer.getId());
			pstmt.executeUpdate();
			System.out.println("Customer " + customer.getId() + " update successful");
		} catch (SQLException e) {
			throw new CouponSystemException("Update customer faild ");
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	/**
	 * The method deletes the customer in the database in the customers table and
	 * also deletes his purchase history from the purchases table.
	 * 
	 * @param customerID - customer ID number.
	 * @throws CouponSystemException if deleting the customer and deleting their
	 *                               purchase history from the database failed.
	 */
	@Override
	public void deleteCustomer(int customerID) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "delete from `customers` where id = ?;";
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, customerID);
			pstmt.executeUpdate();
			System.out.println("customer " + customerID + " and customer's coupons deleted successfully");
		} catch (SQLException e) {
			throw new CouponSystemException("Delete customer failed");
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	/**
	 * The method receives a customer's email and password and returns all customer
	 * details from the database.
	 * 
	 * @param email    - of a customer.
	 * @param password - of a customer.
	 * @return full customer details from database: id, first name, last name, email
	 *         and password. or an empty client if it does not exist in the
	 *         database.
	 * @throws CouponSystemException if failed to get customer data from database.
	 */
	@Override
	public Customer getCustomer(String email, String password) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select * from `customers` where (email=?)and(password = ?);";
		Customer customer = new Customer();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			customer.setId(rs.getInt("id"));
			customer.setFirstName(rs.getString("first_Name"));
			customer.setLastName(rs.getString("last_Name"));
			customer.setEmail(rs.getString("email"));
			customer.setPassword(rs.getString("password"));
		} catch (SQLException e) {
			throw new CouponSystemException("Get customer failed");
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
		return customer;
	}

	/**
	 * The method receives from Database the details of the registered customers
	 * from customers table.
	 * 
	 * @return the list of the customers that exist in the database or an empty list
	 *         if there are no registered customers.
	 * @throws CouponSystemException if the operation of getting the customers list
	 *                               from database failed.
	 */
	@Override
	public List<Customer> getAllCustomers() throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select * from `customers`;";
		List<Customer> customerslist = new ArrayList<>();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Customer customer = new Customer();
				customer.setId(rs.getInt("id"));
				customer.setFirstName(rs.getString("first_Name"));
				customer.setLastName(rs.getString("last_Name"));
				customer.setEmail(rs.getString("email"));
				customer.setPassword(rs.getString("password"));
				customerslist.add(customer);
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Get all customers failed");
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
		return customerslist;
	}

	/**
	 * The method receives a customer id and extracts the customer details from the
	 * customers table in the database.
	 * 
	 * @param customerID - customer ID number.
	 * @return customer details in the database - id, first name, last name, email
	 *         and password if a customer does not exist, an empty customer will be
	 *         returned.
	 * @throws CouponSystemException if the operation of getting the customer
	 *                               failed.
	 */
	@Override
	public Customer getOneCustomer(int customerID) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select * from `customers` where id = ?;";
		Customer customer = new Customer();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, customerID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				customer.setId(rs.getInt("id"));
				customer.setFirstName(rs.getString("first_Name"));
				customer.setLastName(rs.getString("last_Name"));
				customer.setEmail(rs.getString("email"));
				customer.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Get one customer failed");
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

		return customer;
	}

}
