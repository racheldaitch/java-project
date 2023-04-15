package app.core.services;

import java.util.List;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exception.CouponSystemException;

public interface AdminService {// extends ClientService {

	/**
	 * The method adds a company to the database after checking that the company
	 * name and email do not exist in the system.
	 * 
	 * @param company - Company with details: name, email and password.
	 * @throws CouponSystemException if name and email exists in the database.
	 */
	Company addCompany(Company company, int adminId) throws CouponSystemException;

	/**
	 * The method updates a company's email and/or password after checking that the
	 * company exists in the system by company code.
	 * 
	 * @param company - Company with details: code, name, email and password after
	 *                updating.
	 * @throws CouponSystemException if company does not exist in the database.
	 */
	Company updateCompany(Company company, int adminId) throws CouponSystemException;

	/**
	 * The method deletes a company if it exists, and deletes all the company's
	 * coupons and also deletes the history of the purchase of this coupon from the
	 * purchase table.
	 * 
	 * @param companyID - the company code.
	 * @throws CouponSystemException if company does not exist in the database.
	 */
	void deleteCompany(int companyID, int adminId) throws CouponSystemException;

	/**
	 * The method creates a list of all the companies in the database.
	 * 
	 * @return list of companies details in the database.
	 * @throws CouponSystemException if there are no registered companies in the
	 *                               database.
	 */
	List<Company> getAllCompany(int adminId) throws CouponSystemException;

	/**
	 * The method fetches the company details from the database according to the
	 * company code number.
	 * 
	 * @param companyID - the company code.
	 * @return company details from the database by company code.
	 * @throws CouponSystemException if company code not exists in the database.
	 */
	Company getOneCompany(int companyID, int adminId) throws CouponSystemException;

	/**
	 * The method adds a customer to the database after checking that the customer
	 * email do not exist in the system.
	 * 
	 * @param customer - Customer with details: first name, last name, email and
	 *                 password.
	 * @throws CouponSystemException if email is exists in the database.
	 */
	Customer addCustomer(Customer customer, int adminId) throws CouponSystemException;

	/**
	 * The method updates a customer's firstName, lastName, email and/or password
	 * after checking that the customer exists in the system by id.
	 * 
	 * @param customer - Customer with details: first name, last name, email and
	 *                 password after update.
	 * @throws CouponSystemException if customer is not exists in the database.
	 */
	Customer updateCustomer(Customer customer, int adminId) throws CouponSystemException;

	/**
	 * The method deletes a customer and deletes the coupons he purchased from the
	 * purchases table.
	 * 
	 * @param customerID - customer ID number.
	 * @throws CouponSystemException if customer does not exist in the database.
	 */
	void deleteCustomer(int customerID, int adminId) throws CouponSystemException;

	/**
	 * The method creates a list of all customer details registered in the database.
	 * 
	 * @return the list of customers details in the database.
	 * @throws CouponSystemException if there are no registered customers in the
	 *                               database.
	 */
	List<Customer> getAllCustomers(int adminId) throws CouponSystemException;

	/**
	 * The method fetches the customer details from the database according to the
	 * customer id number.
	 * 
	 * @param customerID - customer ID number.
	 * @return customer details from the database by customerID.
	 * @throws CouponSystemException if customer id not exists in the database.
	 */
	Customer getOneCustomer(int customerId, int adminId) throws CouponSystemException;

}
