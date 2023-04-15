package app.tests;

import java.util.List;

import app.core.beans.Category;
import app.core.beans.Coupon;
import app.core.data.dao.CouponsDAO;
import app.core.data.dao.CouponsDBDAO;
import app.core.exception.CouponSystemException;
import app.core.facade.CustomerFacade;
import app.core.login.ClientType;
import app.core.login.LoginManager;

public class TestCustomer {

	CustomerFacade customer;

	public void runAllCustomerFacadeTests() {
		login();
		if (customer != null) {
			purchaseCoupons();
			getCustomerCoupons();
			getCustomerCouponsByCategory();
			getCustomerCouponsByPrice();
			getCustomerDetails();

		}

	}

	/**
	 * The method receives a customer's email and password, and checks against the
	 * database whether the login details are correct. If the login information is
	 * correct, the method initializes the customerID.
	 * 
	 * @param email    - of a customer.
	 * @param password - of a customer.
	 * @return boolean value if the login details are correct.
	 */
	public void login() {
		try {
			System.out.println();
			System.out.println("==============================================");
			System.out.println("=================CUSTOMER FACADE=================");
			System.out.println("==============================================");

			customer = (CustomerFacade) LoginManager.getInstance().login("riki@com", "123", ClientType.CUSTOMER);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
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
	public void purchaseCoupons() {
		System.out.println();
		System.out.println("======PURCHASE COUPONS======");
		System.out.println("==============================================");

		try {
			CouponsDAO couponsDAO = new CouponsDBDAO();
			Coupon coupon1 = couponsDAO.getOneCoupon(1);
			customer.purchaseCoupon(coupon1);
			couponsDAO = new CouponsDBDAO();
			Coupon coupon2 = couponsDAO.getOneCoupon(2);
			customer.purchaseCoupon(coupon2);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The method creates a list of all the coupons of the client who logged in.
	 * 
	 * @return the list of the coupons of the customer who has logged in.
	 * @throws CouponSystemException if the customer has no coupons in the database.
	 */
	public void getCustomerCoupons() {

		System.out.println();
		System.out.println("======GET CUSTOMER COUPONS======");
		System.out.println("==============================================");

		try {
			List<Coupon> getCustomerCoupons = customer.getCustomerCoupons();
			for (Coupon coupon : getCustomerCoupons) {
				System.out.println(coupon);
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
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
	public void getCustomerCouponsByCategory() {

		System.out.println();
		System.out.println("======GET CUSTOMER COUPONS BY CATEGORY======");
		System.out.println("==============================================");

		try {
			List<Coupon> getCustomerCoupons = customer.getCustomerCoupons(Category.FOOD);
			for (Coupon coupon : getCustomerCoupons) {
				System.out.println(coupon);
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}

		try {
			List<Coupon> getCustomerCoupons = customer.getCustomerCoupons(Category.ELECTRICITY);
			for (Coupon coupon : getCustomerCoupons) {
				System.out.println(coupon);
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
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
	public void getCustomerCouponsByPrice() {

		System.out.println();
		System.out.println("======GET CUSTOMER COUPONS BY MAX PRICE======");
		System.out.println("==============================================");

		try {
			List<Coupon> getCustomerCoupons = customer.getCustomerCoupons(4000);
			for (Coupon coupon : getCustomerCoupons) {
				System.out.println(coupon);
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The method extracts from the database the details of the customer that made a
	 * login.
	 * 
	 * @return the details of the customer that logged in.
	 * @throws CouponSystemException if the operation failed.
	 */
	public void getCustomerDetails() {

		System.out.println();
		System.out.println("======GET CUSTOMER DETAILS======");
		System.out.println("==============================================");

		try {
			System.out.println(customer.getCustomerDetails());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}

	}

}
