package app.core.data.dao;

import java.util.List;

import app.core.beans.Customer;
import app.core.exception.CouponSystemException;

public interface CustomersDAO {

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
	boolean isCustomerExists(String email, String password) throws CouponSystemException;

	/**
	 * The method receives a customer's id and checks in the database whether the
	 * customer exists in the customers table.
	 * 
	 * @param customerID - customer ID number.
	 * @return boolean value Whether the customer exists in the database using the
	 *         customer id.
	 * @throws CouponSystemException if the data check against the database failed.
	 */
	boolean isCustomerIDExists(int customerID) throws CouponSystemException;

	/**
	 * The method receives a customer's email and checks in the database whether the
	 * customer exists in the customers table.
	 * 
	 * @param email - of a customer.
	 * @return boolean value does the customer exists in the database using the
	 *         email.
	 * @throws CouponSystemException if the data check against the database failed.
	 */
	boolean isCustomerEmailExists(String email) throws CouponSystemException;

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
	void addCustomer(Customer customer) throws CouponSystemException;

	/**
	 * The method receives a customer with updated details, and updates the customer
	 * details in the database by the customer id.
	 * 
	 * @param customer - customer details: first name, last name, email and password
	 *                 after updating.
	 * @throws CouponSystemException if the customer update in the database fails.
	 */
	void updateCustomer(Customer customer) throws CouponSystemException;

	/**
	 * The method deletes the customer in the database in the customers table and
	 * also deletes his purchase history from the purchases table.
	 * 
	 * @param customerID - customer ID number.
	 * @throws CouponSystemException if deleting the customer and deleting their
	 *                               purchase history from the database failed.
	 */
	void deleteCustomer(int customerID) throws CouponSystemException;

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
	Customer getCustomer(String email, String password) throws CouponSystemException;

	/**
	 * The method receives from Database the details of the registered customers
	 * from customers table.
	 * 
	 * @return the list of the customers that exist in the database or an empty list
	 *         if there are no registered customers.
	 * @throws CouponSystemException if the operation of getting the customers list
	 *                               from database failed.
	 */
	List<Customer> getAllCustomers() throws CouponSystemException;

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
	Customer getOneCustomer(int customerID) throws CouponSystemException;

}
