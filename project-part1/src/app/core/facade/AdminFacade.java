package app.core.facade;

import java.util.List;

import app.core.beans.Company;
import app.core.beans.Customer;
import app.core.exception.CouponSystemException;

public class AdminFacade extends ClientFacade {

	private String email = "admin@admin.com";
	private String password = "admin";

	public AdminFacade() {
		super();

	}

	/**
	 * The method receives an administrator's email and password, and checks whether
	 * the login details are correct.
	 * 
	 * @param email    - of administrator.
	 * @param password - of administrator.
	 * @return boolean value if the login details are correct.
	 */
	@Override
	public boolean login(String email, String password) {

		return email.equalsIgnoreCase(this.email) && password.equals(this.password);
	}

	/**
	 * The method adds a company to the database after checking that the company
	 * name and email do not exist in the system.
	 * 
	 * @param company - Company with details: name, email and password.
	 * @throws CouponSystemException if name and email exists in the database.
	 */
	public void addCompany(Company company) throws CouponSystemException {
		if (!companiesDAO.isCompanyNameEmailExists(company.getName(), company.getEmail())) {
			companiesDAO.addCompany(company);
			return;
		}
		throw new CouponSystemException("Company " + company.getName() + " cannot be added - Name and email exists");
	}

	/**
	 * The method updates a company's email and/or password after checking that the
	 * company exists in the system by company code.
	 * 
	 * @param company - Company with details: code, name, email and password after
	 *                updating.
	 * @throws CouponSystemException if company does not exist in the database.
	 */
	public void updateCompany(Company company) throws CouponSystemException {
		if (companiesDAO.isCompanyIDExists(company.getId())) {
			Company companyUpdate = companiesDAO.getOneCompany(company.getId());
			companyUpdate.setEmail(company.getEmail());
			companyUpdate.setPassword(company.getPassword());
			companiesDAO.updateCompany(companyUpdate);
			return;
		}
		throw new CouponSystemException("Company " + company.getId() + " does not exists - cannot be updated");
	}

	/**
	 * The method deletes a company if it exists, and deletes all the company's
	 * coupons and also deletes the history of the purchase of this coupon from the
	 * purchase table.
	 * 
	 * @param companyID - the company code.
	 * @throws CouponSystemException if company does not exist in the database.
	 */
	public void deleteCompany(int companyID) throws CouponSystemException {
		if (companiesDAO.isCompanyIDExists(companyID)) {
			couponsDAO.deleteCouponsOfCompany(companyID);
			companiesDAO.deleteCompany(companyID);
			return;
		}
		throw new CouponSystemException("Company " + companyID + " does not exists - cannot be deleted");
	}

	/**
	 * The method creates a list of all the companies in the database.
	 * 
	 * @return list of companies details in the database.
	 * @throws CouponSystemException if there are no registered companies in the
	 *                               database.
	 */
	public List<Company> getAllCompany() throws CouponSystemException {
		List<Company> allCompanies = companiesDAO.getAllCompany();
		if (allCompanies.isEmpty()) {
			throw new CouponSystemException("There are no registered companies");
		}
		return allCompanies;
	}

	/**
	 * The method fetches the company details from the database according to the
	 * company code number.
	 * 
	 * @param companyID - the company code.
	 * @return company details from the database by company code.
	 * @throws CouponSystemException if company code not exists in the database.
	 */
	public Company getOneCompany(int companyID) throws CouponSystemException {
		Company company = companiesDAO.getOneCompany(companyID);
		if (company.getId() == 0) {
			throw new CouponSystemException("Company " + companyID + " not found");
		}
		return company;
	}

	/**
	 * The method adds a customer to the database after checking that the customer
	 * email do not exist in the system.
	 * 
	 * @param customer - Customer with details: first name, last name, email and
	 *                 password.
	 * @throws CouponSystemException if email is exists in the database.
	 */
	public void addCustomer(Customer customer) throws CouponSystemException {
		if (!customersDAO.isCustomerEmailExists(customer.getEmail())) {
			customersDAO.addCustomer(customer);
			return;
		}
		throw new CouponSystemException("Customer " + customer.getFirstName() + " " + customer.getLastName()
				+ " cannot be added - Email exists");
	}

	/**
	 * The method updates a customer's firstName, lastName, email and/or password
	 * after checking that the customer exists in the system by id.
	 * 
	 * @param customer - Customer with details: first name, last name, email and
	 *                 password after update.
	 * @throws CouponSystemException if customer is not exists in the database.
	 */
	public void updateCustomer(Customer customer) throws CouponSystemException {
		if (customersDAO.isCustomerIDExists(customer.getId())) {
			Customer customerUpdate = customersDAO.getOneCustomer(customer.getId());
			customerUpdate.setFirstName(customer.getFirstName());
			customerUpdate.setLastName(customer.getLastName());
			customerUpdate.setEmail(customer.getEmail());
			customerUpdate.setPassword(customer.getPassword());
			customersDAO.updateCustomer(customerUpdate);
			return;
		}
		throw new CouponSystemException("Customer " + customer.getId() + " cannot be update - not found");
	}

	/**
	 * The method deletes a customer and deletes the coupons he purchased from the
	 * purchases table.
	 * 
	 * @param customerID - customer ID number.
	 * @throws CouponSystemException if customer does not exist in the database.
	 */
	public void deleteCustomer(int customerID) throws CouponSystemException {
		if (customersDAO.isCustomerIDExists(customerID)) {
			customersDAO.deleteCustomer(customerID);
			return;
		}
		throw new CouponSystemException("Customer " + customerID + " does not exist - cannot be deleted");
	}

	/**
	 * The method creates a list of all customer details registered in the database.
	 * 
	 * @return the list of customers details in the database.
	 * @throws CouponSystemException if there are no registered customers in the
	 *                               database.
	 */
	public List<Customer> getAllCustomer() throws CouponSystemException {
		List<Customer> allCustomers = customersDAO.getAllCustomers();
		if (allCustomers == null) {
			throw new CouponSystemException("There are no registered customers");
		}
		return allCustomers;
	}

	/**
	 * The method fetches the customer details from the database according to the
	 * customer id number.
	 * 
	 * @param customerID - customer ID number.
	 * @return customer details from the database by customerID.
	 * @throws CouponSystemException if customer id not exists in the database.
	 */
	public Customer getOneCustomer(int customerID) throws CouponSystemException {
		Customer customer = customersDAO.getOneCustomer(customerID);
		if (customer.getId() == 0) {
			throw new CouponSystemException("customer " + customerID + " not found");
		}
		return customer;
	}

}
