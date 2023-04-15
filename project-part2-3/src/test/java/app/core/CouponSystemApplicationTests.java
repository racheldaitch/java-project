package app.core;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exception.CouponSystemException;
import app.core.login.ClientType;
import app.core.login.LoginManager;
import app.core.repositories.CouponRepository;
import app.core.services.AdminServiceImpl;
import app.core.services.CompanyServiceImpl;
import app.core.services.CustomerServiceImpl;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CouponSystemApplicationTests {

	private static AdminServiceImpl adminServiceImpl;
	private static CompanyServiceImpl companyServiceImpl;
	private static CustomerServiceImpl customerServiceImpl;
	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private LoginManager loginManager;

	/**
	 * The method receives an administrator's email and password, and checks whether
	 * the login details are correct.
	 * 
	 * @param email    - of administrator.
	 * @param password - of administrator.
	 * @return boolean value if the login details are correct.
	 */
	@Test
	@Order(1)
	public void loginAdmin() throws CouponSystemException {
		System.out.println("==============================================");
		System.out.println("=================ADMIN SERVICE=================");
		System.out.println("==============================================");

		adminServiceImpl = (AdminServiceImpl) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);

		Assertions.assertEquals("admin@admin.com", adminServiceImpl.getEmail());
		Assertions.assertEquals("admin", adminServiceImpl.getPassword());

		CouponSystemException e = Assertions.assertThrows(CouponSystemException.class,
				() -> loginManager.login("adminn@admin.com", "agdmin", ClientType.ADMINISTRATOR));
		System.out.println(e);

	}

	Company company1 = Company.builder().name("Osem").email("osem@com").password("1234").build();
	Company company2 = Company.builder().name("Sharp").email("sharp@com").password("12345").build();
	Company company3 = Company.builder().name("Kedem").email("kedem@com").password("123456").build();
	Company company4 = Company.builder().name("Maya").email("maya@com").password("1234567").build();
	Company company5 = Company.builder().name("Tours").email("tours@com").password("321321").build();

	/**
	 * The method adds a company to the database after checking that the company
	 * name and email do not exist in the system.
	 * 
	 * @param company - Company with details: name, email and password.
	 * @throws CouponSystemException if name and email exists in the database.
	 */
	@Test
	@Order(2)
	public void addCompany() throws CouponSystemException {
		System.out.println();
		System.out.println("======ADD COMPANY======");
		System.out.println("==============================================");

		adminServiceImpl.addCompany(company1);
		adminServiceImpl.addCompany(company2);
		adminServiceImpl.addCompany(company3);
		adminServiceImpl.addCompany(company4);
		adminServiceImpl.addCompany(company5);

		Assertions.assertEquals(1, company1.getId());
		Assertions.assertEquals(2, company2.getId());
		Assertions.assertEquals(3, company3.getId());
		Assertions.assertEquals(4, company4.getId());
		Assertions.assertEquals(5, company5.getId());

		CouponSystemException e = Assertions.assertThrows(CouponSystemException.class,
				() -> adminServiceImpl.addCompany(company1));
		System.out.println(e);
	}

	/**
	 * The method updates a company's email and/or password after checking that the
	 * company exists in the system by company code.
	 * 
	 * @param company - Company with details: code, name, email and password after
	 *                updating.
	 * @throws CouponSystemException if company does not exist in the database.
	 */
	@Test
	@Order(3)
	public void updateCompany() throws CouponSystemException {
		System.out.println();
		System.out.println("======UPDATE COMPANY======");
		System.out.println("==============================================");

		Company companyUpdate = Company.builder().id(2).name("Sharp").email("sharp12@com").password("123456").build();
		Company companyUpdate2 = Company.builder().id(3).name("Kedemm").email("kedem@com").password("123456").build();
		adminServiceImpl.updateCompany(companyUpdate);

		Assertions.assertEquals("sharp12@com", companyUpdate.getEmail());
		CouponSystemException e = Assertions.assertThrows(CouponSystemException.class,
				() -> adminServiceImpl.updateCompany(companyUpdate2));
		System.out.println(e);
	}

	/**
	 * The method deletes a company if it exists, and deletes all the company's
	 * coupons and also deletes the history of the purchase of this coupon from the
	 * purchase table.
	 * 
	 * @param companyID - the company code.
	 * @throws CouponSystemException if company does not exist in the database.
	 */
	@Test
	@Order(4)
	public void deleteCompany() throws CouponSystemException {

		System.out.println();
		System.out.println("======DELETE COMPANY======");
		System.out.println("==============================================");

		adminServiceImpl.deleteCompany(5);

		CouponSystemException e = Assertions.assertThrows(CouponSystemException.class,
				() -> adminServiceImpl.deleteCompany(5));
		System.out.println(e);
	}

	/**
	 * The method creates a list of all the companies in the database.
	 * 
	 * @return list of companies details in the database.
	 * @throws CouponSystemException if there are no registered companies in the
	 *                               database.
	 */
	@Test
	@Order(5)
	public void getAllCompany() throws CouponSystemException {

		System.out.println();
		System.out.println("======GET ALL COMPANIES======");
		System.out.println("==============================================");
		List<Company> getAllCompany = adminServiceImpl.getAllCompany();
		getAllCompany.forEach(System.out::println);
	}

	/**
	 * The method fetches the company details from the database according to the
	 * company code number.
	 * 
	 * @param companyID - the company code.
	 * @return company details from the database by company code.
	 * @throws CouponSystemException if company code not exists in the database.
	 */
	@Test
	@Order(6)
	public void getOneCompany() throws CouponSystemException {

		System.out.println();
		System.out.println("======GET ONE COMPANY======");

		System.out.println(adminServiceImpl.getOneCompany(1));

		CouponSystemException e = Assertions.assertThrows(CouponSystemException.class,
				() -> adminServiceImpl.getOneCompany(10));
		System.out.println(e);
	}

	/**
	 * The method adds a customer to the database after checking that the customer
	 * email do not exist in the system.
	 * 
	 * @param customer - Customer with details: first name, last name, email and
	 *                 password.
	 * @throws CouponSystemException if email is exists in the database.
	 */
	Customer customer1 = Customer.builder().firstName("Riki").lastName("Cohen").email("riki@com").password("123")
			.build();
	Customer customer2 = Customer.builder().firstName("Tamar").lastName("Levi").email("tamar@com").password("1234")
			.build();
	Customer customer3 = Customer.builder().firstName("Rotem").lastName("Sason").email("rotem@com").password("12345")
			.build();
	Customer customer4 = Customer.builder().firstName("Zohar").lastName("Israeli").email("zohar@com").password("123456")
			.build();
	Customer customer5 = Customer.builder().firstName("Dan").lastName("Nagar").email("dan@com").password("2468")
			.build();

	@Test
	@Order(7)
	public void addCustomer() throws CouponSystemException {

		System.out.println();
		System.out.println("======ADD CUSTOMER======");
		System.out.println("==============================================");

		adminServiceImpl.addCustomer(customer1);
		adminServiceImpl.addCustomer(customer2);
		adminServiceImpl.addCustomer(customer3);
		adminServiceImpl.addCustomer(customer4);
		adminServiceImpl.addCustomer(customer5);

		Assertions.assertEquals(1, customer1.getId());
		Assertions.assertEquals(2, customer2.getId());
		Assertions.assertEquals(3, customer3.getId());
		Assertions.assertEquals(4, customer4.getId());
		Assertions.assertEquals(5, customer5.getId());

		CouponSystemException e = Assertions.assertThrows(CouponSystemException.class,
				() -> adminServiceImpl.addCustomer(customer1));
		System.out.println(e);
	}

	/**
	 * The method updates a customer's firstName, lastName, email and/or password
	 * after checking that the customer exists in the system by id.
	 * 
	 * @param customer - Customer with details: first name, last name, email and
	 *                 password after update.
	 * @throws CouponSystemException if customer is not exists in the database.
	 */
	@Test
	@Order(8)
	public void updateCustomer() throws CouponSystemException {

		System.out.println();
		System.out.println("======UPDATE CUSTOMER======");
		System.out.println("==============================================");

		Customer customerUpdate = Customer.builder().id(2).firstName("Tamari").lastName("Levi").email("tamar@com")
				.password("1234").build();
		Customer customerUpdate2 = Customer.builder().id(10).firstName("Rotem").lastName("Sason").email("rotem@com")
				.password("12345").build();
		adminServiceImpl.updateCustomer(customerUpdate);
		Assertions.assertEquals("Tamari", customerUpdate.getFirstName());
		CouponSystemException e = Assertions.assertThrows(CouponSystemException.class,
				() -> adminServiceImpl.updateCustomer(customerUpdate2));
		System.out.println(e);
	}

	/**
	 * The method deletes a customer and deletes the coupons he purchased from the
	 * purchases table.
	 * 
	 * @param customerID - customer ID number.
	 * @throws CouponSystemException if customer does not exist in the database.
	 */
	@Test
	@Order(9)
	public void deleteCustomer() throws CouponSystemException {

		System.out.println();
		System.out.println("======DELETE CUSTOMER======");
		System.out.println("==============================================");
		adminServiceImpl.deleteCustomer(4);
		CouponSystemException e = Assertions.assertThrows(CouponSystemException.class,
				() -> adminServiceImpl.deleteCustomer(4));
		System.out.println(e);
	}

	/**
	 * The method creates a list of all customer details registered in the database.
	 * 
	 * @return the list of customers details in the database.
	 * @throws CouponSystemException if there are no registered customers in the
	 *                               database.
	 */
	@Test
	@Order(10)
	public void getAllCustomer() throws CouponSystemException {

		System.out.println();
		System.out.println("======GET ALL CUSTOMERS======");
		System.out.println("==============================================");

		List<Customer> getAllCustomers = adminServiceImpl.getAllCustomers();
		getAllCustomers.forEach(System.out::println);

	}

	@Test
	@Order(11)
	/**
	 * The method fetches the customer details from the database according to the
	 * customer id number.
	 * 
	 * @param customerID - customer ID number.
	 * @return customer details from the database by customerID.
	 * @throws CouponSystemException if customer id not exists in the database.
	 */
	public void getOneCustomer() throws CouponSystemException {

		System.out.println();
		System.out.println("======GET ONE CUSTOMER======");
		System.out.println("==============================================");
		System.out.println(adminServiceImpl.getOneCustomer(1));
		CouponSystemException e = Assertions.assertThrows(CouponSystemException.class,
				() -> adminServiceImpl.getOneCustomer(10));
		System.out.println(e);

	}

	/**
	 * The method receives a company's email and password, and checks against the
	 * database whether the login details are correct. If the login information is
	 * correct, the method initializes the companyID.
	 * 
	 * @param email    - of a company.
	 * @param password - of a company.
	 * @return boolean value if the login details are correct.
	 * @throws CouponSystemException
	 */
	@Test
	@Order(12)
	public void loginCompany() throws CouponSystemException {

		System.out.println();
		System.out.println("==============================================");
		System.out.println("=================COMPANY SERVICE=================");
		System.out.println("==============================================");

		companyServiceImpl = (CompanyServiceImpl) loginManager.login("osem@com", "1234", ClientType.COMPANY);
		Assertions.assertEquals("osem@com", companyServiceImpl.getCompanyDetails().getEmail());
		Assertions.assertEquals("1234", companyServiceImpl.getCompanyDetails().getPassword());

		CouponSystemException e = Assertions.assertThrows(CouponSystemException.class,
				() -> loginManager.login("osemm@com", "1234", ClientType.COMPANY));
		System.out.println(e);
	}

	/**
	 * The method adds a coupon to the company that logged in after checking that
	 * the company does not have a coupon with the same title as the coupon it is
	 * adding.
	 * 
	 * @param coupon of a company - Coupon with: company code, category type, title,
	 *               description, start date, end date, amount, price and image.
	 * @throws CouponSystemException if the company has a coupon with the same title
	 *                               as the coupon you are adding.
	 */
	@Test
	@Order(13)
	public void addCoupon() throws CouponSystemException {
		System.out.println();
		System.out.println("======ADD COUPON======");
		System.out.println("==============================================");
		Company company = companyServiceImpl.getCompanyDetails();
		Coupon coupon1 = Coupon.builder().company(company).category(Category.ELECTRICITY).title("frigerator")
				.description("Upside down refrigerator").startDate(Date.valueOf(LocalDate.now()))
				.endDate(Date.valueOf("2023-12-03")).amount(30).price(2000).image("Refrigerator image").build();
		Coupon coupon2 = Coupon.builder().company(company).category(Category.ELECTRICITY).title("Oven")
				.description("DeLonghi E123").startDate(Date.valueOf(LocalDate.now()))
				.endDate(Date.valueOf("2023-12-03")).amount(30).price(6500).image("DeLonghi image").build();
		Coupon coupon3 = Coupon.builder().company(company).category(Category.FOOD).title("Bread")
				.description("Dark bread").startDate(Date.valueOf(LocalDate.now())).endDate(Date.valueOf("2023-12-05"))
				.amount(60).price(6).image("bread image").build();
		Coupon coupon4 = Coupon.builder().company(company).category(Category.RESTAURANT).title("Breakfast")
				.description("Meal for two").startDate(Date.valueOf(LocalDate.now()))
				.endDate(Date.valueOf("2023-12-03")).amount(30).price(250).image("Breakfast image").build();
		Coupon coupon5 = Coupon.builder().company(company).category(Category.VACATION)
				.title("Vacation abroad - Plaza Switzerland").description("Flight and night for one person")
				.startDate(Date.valueOf(LocalDate.now())).endDate(Date.valueOf("2024-03-03")).amount(20).price(1700)
				.image("vacation image").build();
		Coupon coupon6 = Coupon.builder().company(company).category(Category.VACATION).title("Hotel")
				.description("Leonardo Plaza 2 nights").startDate(Date.valueOf(LocalDate.now()))
				.endDate(Date.valueOf("2024-12-03")).amount(15).price(2500).image("hotel image").build();

		companyServiceImpl.addCoupon(coupon1);
		companyServiceImpl.addCoupon(coupon2);
		companyServiceImpl.addCoupon(coupon3);
		companyServiceImpl.addCoupon(coupon4);
		companyServiceImpl.addCoupon(coupon5);
		companyServiceImpl.addCoupon(coupon6);

		Assertions.assertEquals(1, coupon1.getId());

		CouponSystemException e = Assertions.assertThrows(CouponSystemException.class,
				() -> companyServiceImpl.addCoupon(coupon1));
		System.out.println(e);
	}

	/**
	 * The method receives a coupon after updating the details you wish to update,
	 * and checks that it exists in the database using The code of the coupon, if it
	 * exists, it updates the coupon details. The coupon code and company code
	 * cannot be updated.
	 * 
	 * @param coupon - Coupon with: company code, category type, title, description,
	 *               start date, end date, amount, price and image after updating.
	 * @throws CouponSystemException if the coupon that the company adds does not
	 *                               exist in the database.
	 */
	@Test
	@Order(14)
	public void updateCoupon() throws CouponSystemException {

		System.out.println();
		System.out.println("======UPDATE COUPON======");
		System.out.println("==============================================");
		Company company = companyServiceImpl.getCompanyDetails();
		Coupon couponUpdate = Coupon.builder().id(3).company(company).category(Category.FOOD).title("One Bread")
				.description("Dark bread").startDate(Date.valueOf(LocalDate.now())).endDate(Date.valueOf("2023-12-05"))
				.amount(60).price(6).image("bread image").build();
		Coupon couponUpdate2 = Coupon.builder().id(10).company(company).category(Category.FOOD).title("One Bread")
				.description("Dark bread").startDate(Date.valueOf(LocalDate.now())).endDate(Date.valueOf("2023-12-05"))
				.amount(60).price(6).image("bread image").build();
		companyServiceImpl.updateCoupon(couponUpdate);
		CouponSystemException e = Assertions.assertThrows(CouponSystemException.class,
				() -> companyServiceImpl.updateCoupon(couponUpdate2));
		System.out.println(e);

	}

	/**
	 * The method receives an id of a coupon and deletes the coupon from the
	 * database and also deletes the history of the purchase of this coupon from the
	 * purchase table, deletes the purchases of the coupon.
	 * 
	 * @param couponID - the coupon code.
	 * @throws CouponSystemException if coupon does not exist in the database.
	 */
	@Test
	@Order(15)
	public void deleteCoupon() throws CouponSystemException {

		System.out.println();
		System.out.println("======DELETE COUPON======");
		System.out.println("==============================================");

		companyServiceImpl.deleteCoupon(6);
		CouponSystemException e = Assertions.assertThrows(CouponSystemException.class,
				() -> companyServiceImpl.deleteCoupon(6));
		System.out.println(e);
	}

	/**
	 * The method creates a list of all the coupons of the company that has logged
	 * in.
	 * 
	 * @return the list of the coupons of the company that has logged in.
	 * @throws CouponSystemException if the company has no coupons in the database.
	 */
	@Test
	@Order(16)
	public void getCompanyCoupons() throws CouponSystemException {

		System.out.println();
		System.out.println("======GET COMPANY COUPONS======");
		System.out.println("==============================================");

		List<Coupon> getCompanyCoupons = companyServiceImpl.getCompanyCoupons();
		getCompanyCoupons.forEach(System.out::println);

	}

	/**
	 * The method creates a list of all the coupons from the selected category, of
	 * the company that made a login.
	 * 
	 * @param category - the category of the coupon.
	 * @return the list of coupons according to the selected category of the entered
	 *         company.
	 * @throws CouponSystemException if the company has no coupons from this
	 *                               category.
	 */
	@Test
	@Order(17)
	public void getCompanyCouponsByCategory() throws CouponSystemException {

		System.out.println();
		System.out.println("======GET COMPANY COUPONS BY CATEGORY======");
		System.out.println("==============================================");

		List<Coupon> getCompanyCoupons = companyServiceImpl.getCompanyCoupons(Category.FOOD);
		getCompanyCoupons.forEach(System.out::println);

	}

	/**
	 * The method creates a list of all the coupons up to a selected maximum price,
	 * of the company that made a login.
	 * 
	 * @param maxPrice - maximum price of coupons you wish to receive.
	 * @return the list of coupons of the company that connected up to a selected
	 *         price.
	 * @throws CouponSystemException if the company has no coupons up to the
	 *                               selected price.
	 */
	@Test
	@Order(18)
	public void getCompanyCouponsByPrice() throws CouponSystemException {

		System.out.println();
		System.out.println("======GET COMPANY COUPONS BY MAX PRICE======");
		System.out.println("==============================================");

		List<Coupon> getCompanyCoupons = companyServiceImpl.getCompanyCoupons(1000);
		getCompanyCoupons.forEach(System.out::println);

	}

	/**
	 * The method extracts from the database the details of the company that made a
	 * login.
	 * 
	 * @return the details of the company that logged in.
	 * @throws CouponSystemException if the operation failed.
	 */
	@Test
	@Order(19)
	public void getCompanyDetails() throws CouponSystemException {

		System.out.println("======GET COMPANY DETAILS======");
		System.out.println("==============================================");
		System.out.println(companyServiceImpl.getCompanyDetails());

	}

	/**
	 * The method receives a customer's email and password, and checks against the
	 * database whether the login details are correct. If the login information is
	 * correct, the method initializes the customerID.
	 * 
	 * @param email    - of a customer.
	 * @param password - of a customer.
	 * @return boolean value if the login details are correct.
	 * @throws CouponSystemException
	 */
	@Test
	@Order(20)
	public void loginCustomer() throws CouponSystemException {
		System.out.println();
		System.out.println("==============================================");
		System.out.println("=================CUSTOMER SERVICE=================");
		System.out.println("==============================================");

		customerServiceImpl = (CustomerServiceImpl) loginManager.login("riki@com", "123", ClientType.CUSTOMER);
		Assertions.assertEquals("riki@com", customerServiceImpl.getCustomerDetails().getEmail());
		Assertions.assertEquals("123", customerServiceImpl.getCustomerDetails().getPassword());

		CouponSystemException e = Assertions.assertThrows(CouponSystemException.class,
				() -> loginManager.login("rikim@com", "123", ClientType.CUSTOMER));
		System.out.println(e);
	}

	/**
	 * The method makes a coupon purchase after checking that the customer does not
	 * have a coupon with the same code and that the coupon is in stock and the
	 * expiration date has also not passed, after the purchase the method updates
	 * the coupon stock and lowers it by one.
	 * 
	 * @param coupon - Coupon with: code, company code, category type, title,
	 *               description, start date, end date, amount, price and image.
	 * @throws CouponSystemException if the customer has a coupon with the same code
	 *                               or there is no coupon in stock or the
	 *                               expiration date of the coupon has arrived.
	 */
	@Test
	@Order(21)
	public void purchaseCoupons() throws CouponSystemException {
		System.out.println();
		System.out.println("======PURCHASE COUPONS======");
		System.out.println("==============================================");

		Coupon coupon1 = couponRepository.findById(1).get();
		Coupon coupon2 = couponRepository.findById(4).get();

		customerServiceImpl.purchaseCoupon(coupon1);
		customerServiceImpl.purchaseCoupon(coupon2);

		CouponSystemException e = Assertions.assertThrows(CouponSystemException.class,
				() -> customerServiceImpl.purchaseCoupon(coupon1));
		System.out.println(e);
	}

	/**
	 * The method creates a list of all the coupons of the client who logged in.
	 * 
	 * @return the list of the coupons of the customer who has logged in.
	 * @throws CouponSystemException if the customer has no coupons in the database.
	 */
	@Test
	@Order(22)
	public void getCustomerCoupons() throws CouponSystemException {

		System.out.println();
		System.out.println("======GET CUSTOMER COUPONS======");
		System.out.println("==============================================");

		List<Coupon> getCustomerCoupons = customerServiceImpl.getCustomerCoupons();
		getCustomerCoupons.forEach(System.out::println);

	}

	/**
	 * The method creates a list of all the coupons from the selected category of
	 * the customer who logged in.
	 * 
	 * @param category - the category of the coupon.
	 * @return the list of coupons by category of the customer that logged in.
	 * @throws CouponSystemException if the customer has no coupons from this
	 *                               category in the database.
	 */
	@Test
	@Order(23)
	public void getCustomerCouponsByCategory() throws CouponSystemException {

		System.out.println();
		System.out.println("======GET CUSTOMER COUPONS BY CATEGORY======");
		System.out.println("==============================================");

		List<Coupon> getCustomerCouponsByCategory = customerServiceImpl.getCustomerCoupons(Category.ELECTRICITY.name());
		getCustomerCouponsByCategory.forEach(System.out::println);
	}

	/**
	 * The method creates a list of all the coupons of the customer who logged in up
	 * to a selected maximum price.
	 * 
	 * @param maxPrice - maximum price of coupons you wish to receive.
	 * @return the list of coupons up to the selected price price of the customer
	 *         that logged in.
	 * @throws CouponSystemException if the customer does not have coupons in the
	 *                               database up to the selected price.
	 */
	@Test
	@Order(24)
	public void getCustomerCouponsByPrice() throws CouponSystemException {

		System.out.println();
		System.out.println("======GET CUSTOMER COUPONS BY MAX PRICE======");
		System.out.println("==============================================");

		List<Coupon> getCustomerCouponsByPrice = customerServiceImpl.getCustomerCoupons(2000);
		getCustomerCouponsByPrice.forEach(System.out::println);
	}

	/**
	 * The method extracts from the database the details of the customer that made a
	 * login.
	 * 
	 * @return the details of the customer that logged in.
	 * @throws CouponSystemException if the operation failed.
	 */
	@Test
	@Order(25)
	public void getCustomerDetails() throws CouponSystemException {

		System.out.println("======GET CUSTOMER DETAILS======");
		System.out.println("==============================================");
		System.out.println(customerServiceImpl.getCustomerDetails());

	}

}
