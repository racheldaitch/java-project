package app.tests;

import java.util.List;

import app.core.beans.Company;
import app.core.beans.Customer;
import app.core.exception.CouponSystemException;
import app.core.facade.AdminFacade;
import app.core.login.ClientType;
import app.core.login.LoginManager;

public class TestAdmin {

	AdminFacade admin;

	public void runAllAdminTests() {
		login();

		if (admin != null) {
			allAddCompany();
			updateCompany();
			deleteCompany();
			getAllCompany();
			getOneCompany();
			addCustomer();
			updateCustomer();
			deleteCustomer();
			getAllCustomer();
			getOneCustomer();

		}
	}

	/**
	 * The method receives an administrator's email and password, and checks whether
	 * the login details are correct.
	 * 
	 * @param email    - of administrator.
	 * @param password - of administrator.
	 * @return boolean value if the login details are correct.
	 */
	public void login() {

		try {
			System.out.println("==============================================");
			System.out.println("=================ADMIN FACADE=================");
			System.out.println("==============================================");

			admin = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin",
					ClientType.ADMINISTRATOR);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * The method adds a company to the database after checking that the company
	 * name and email do not exist in the system.
	 * 
	 * @param company - Company with details: name, email and password.
	 * @throws CouponSystemException if name and email exists in the database.
	 */
	public void allAddCompany() {

		System.out.println();
		System.out.println("======ADD COMPANY======");
		System.out.println("==============================================");

		Company company1 = new Company("Osem", "osem@com", "1234");
		Company company2 = new Company("Sharp", "sharp@com", "12345");
		Company company3 = new Company("Kedem", "kedem@com", "123456");
		Company company4 = new Company("Maya", "maya@com", "1234567");
		Company company5 = new Company("Tours", "tours@com", "321321");

		try {
			admin.addCompany(company1);
			admin.addCompany(company2);
			admin.addCompany(company3);
			admin.addCompany(company4);
			admin.addCompany(company5);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The method updates a company's email and/or password after checking that the
	 * company exists in the system by company code.
	 * 
	 * @param company - Company with details: code, name, email and password after
	 *                updating.
	 * @throws CouponSystemException if company does not exist in the database.
	 */
	public void updateCompany() {

		System.out.println();
		System.out.println("======UPDATE COMPANY======");
		System.out.println("==============================================");

		try {
			Company companyUptade1 = new Company(2, "Sharp", "sharp@com", "4321");
			admin.updateCompany(companyUptade1);

		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The method deletes a company if it exists, and deletes all the company's
	 * coupons and also deletes the history of the purchase of this coupon from the
	 * purchase table.
	 * 
	 * @param companyID - the company code.
	 * @throws CouponSystemException if company does not exist in the database.
	 */
	public void deleteCompany() {

		System.out.println();
		System.out.println("======DELETE COMPANY======");
		System.out.println("==============================================");

		try {
			admin.deleteCompany(3);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The method creates a list of all the companies in the database.
	 * 
	 * @return list of companies details in the database.
	 * @throws CouponSystemException if there are no registered companies in the
	 *                               database.
	 */
	public void getAllCompany() {

		System.out.println();
		System.out.println("======GET ALL COMPANIES======");
		System.out.println("==============================================");

		try {
			List<Company> getAllCompany = admin.getAllCompany();
			for (Company company : getAllCompany) {
				System.out.println(company);
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The method fetches the company details from the database according to the
	 * company code number.
	 * 
	 * @param companyID - the company code.
	 * @return company details from the database by company code.
	 * @throws CouponSystemException if company code not exists in the database.
	 */
	public void getOneCompany() {

		System.out.println();
		System.out.println("======GET ONE COMPANY======");

		try {
			System.out.println(admin.getOneCompany(1));

		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The method adds a customer to the database after checking that the customer
	 * email do not exist in the system.
	 * 
	 * @param customer - Customer with details: first name, last name, email and
	 *                 password.
	 * @throws CouponSystemException if email is exists in the database.
	 */
	public void addCustomer() {

		System.out.println();
		System.out.println("======ADD CUSTOMER======");
		System.out.println("==============================================");

		Customer customer1 = new Customer(0, "Riki", "Cohen", "riki@com", "123");
		Customer customer2 = new Customer(0, "Tamar", "Levi", "tamar@com", "1234");
		Customer customer3 = new Customer(0, "Rotem", "Sason", "rotem@com", "12345");
		Customer customer4 = new Customer(0, "Zohar", "Israeli", "zohar@com", "123456");
		Customer customer5 = new Customer(0, "Dan", "Nagar", "dan@com", "2468");

		try {
			admin.addCustomer(customer1);
			admin.addCustomer(customer2);
			admin.addCustomer(customer3);
			admin.addCustomer(customer4);
			admin.addCustomer(customer5);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * The method updates a customer's firstName, lastName, email and/or password
	 * after checking that the customer exists in the system by id.
	 * 
	 * @param customer - Customer with details: first name, last name, email and
	 *                 password after update.
	 * @throws CouponSystemException if customer is not exists in the database.
	 */
	public void updateCustomer() {

		System.out.println();
		System.out.println("======UPDATE CUSTOMER======");
		System.out.println("==============================================");

		try {
			Customer customerUpdate = new Customer(2, "Tamar", "Levin", "tamarlevin@com", "123");
			admin.updateCustomer(customerUpdate);

		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The method deletes a customer and deletes the coupons he purchased from the
	 * purchases table.
	 * 
	 * @param customerID - customer ID number.
	 * @throws CouponSystemException if customer does not exist in the database.
	 */
	public void deleteCustomer() {

		System.out.println();
		System.out.println("======DELETE CUSTOMER======");
		System.out.println("==============================================");

		try {
			admin.deleteCustomer(4);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The method creates a list of all customer details registered in the database.
	 * 
	 * @return the list of customers details in the database.
	 * @throws CouponSystemException if there are no registered customers in the
	 *                               database.
	 */
	public void getAllCustomer() {

		System.out.println();
		System.out.println("======GET ALL CUSTOMERS======");
		System.out.println("==============================================");

		try {
			List<Customer> getAllCustomer = admin.getAllCustomer();
			for (Customer customer : getAllCustomer) {
				System.out.println(customer);
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The method fetches the customer details from the database according to the
	 * customer id number.
	 * 
	 * @param customerID - customer ID number.
	 * @return customer details from the database by customerID.
	 * @throws CouponSystemException if customer id not exists in the database.
	 */
	public void getOneCustomer() {

		System.out.println();
		System.out.println("======GET ONE CUSTOMER======");
		System.out.println("==============================================");

		try {
			System.out.println(admin.getOneCustomer(1));

		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		System.out.println();
		System.out.println();

	}

}
