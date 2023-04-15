package app.tests;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import app.core.beans.Category;
import app.core.beans.Coupon;
import app.core.exception.CouponSystemException;
import app.core.facade.CompanyFacade;
import app.core.login.ClientType;
import app.core.login.LoginManager;

public class TestCompany {

	CompanyFacade company;

	public void runAllCompanyTests() {
		login();
		if (company != null) {
			addCoupon();
			updateCoupon();
			deleteCoupon();
			getCompanyCoupons();
			getCompanyCouponsByCategory();
			getCompanyCouponsByPrice();
			getCompanyDetails();

		}

	}

	/**
	 * The method receives a company's email and password, and checks against the
	 * database whether the login details are correct. If the login information is
	 * correct, the method initializes the companyID.
	 * 
	 * @param email    - of a company.
	 * @param password - of a company.
	 * @return boolean value if the login details are correct.
	 */
	public void login() {
		try {
			System.out.println();
			System.out.println("==============================================");
			System.out.println("=================COMPANY FACADE=================");
			System.out.println("==============================================");

			company = (CompanyFacade) LoginManager.getInstance().login("osem@com", "1234", ClientType.COMPANY);

		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
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
	public void addCoupon() {
		System.out.println();
		System.out.println("======ADD COUPON======");
		System.out.println("==============================================");

		Coupon coupon1 = new Coupon(0, company.getCompanyId(), Category.ELECTRICITY, "Refrigerator",
				"Upside down refrigerator", Date.valueOf(LocalDate.now()), Date.valueOf("2023-03-03"), 30, 6500,
				"Refrigerator image");
		Coupon coupon2 = new Coupon(0, company.getCompanyId(), Category.ELECTRICITY, "Oven", "DeLonghi E123",
				Date.valueOf(LocalDate.now()), Date.valueOf("2023-03-03"), 30, 2000, "DeLonghi image");
		Coupon coupon3 = new Coupon(0, company.getCompanyId(), Category.FOOD, "Bread", "Dark bread",
				Date.valueOf(LocalDate.now()), Date.valueOf("2023-03-03"), 50, 6, "bread image");
		Coupon coupon4 = new Coupon(0, company.getCompanyId(), Category.RESTAURANT, "Breakfast", "Meal for two",
				Date.valueOf(LocalDate.now()), Date.valueOf("2023-03-03"), 30, 250, "Breakfast image");
		Coupon coupon5 = new Coupon(0, company.getCompanyId(), Category.VACATION, "Vacation abroad - Plaza Switzerland",
				"Flight and night for one person", Date.valueOf(LocalDate.now()), Date.valueOf("2023-03-03"), 20, 1700,
				"vacation image");
		Coupon coupon6 = new Coupon(0, company.getCompanyId(), Category.VACATION, "Hotel", "Leonardo Plaza 2 nights",
				Date.valueOf(LocalDate.now()), Date.valueOf("2023-03-03"), 15, 2500, "hotel image");
		try {
			company.addCoupon(coupon1);
			company.addCoupon(coupon2);
			company.addCoupon(coupon3);
			company.addCoupon(coupon4);
			company.addCoupon(coupon5);
			company.addCoupon(coupon6);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
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
	public void updateCoupon() {

		System.out.println();
		System.out.println("======UPDATE COUPON======");
		System.out.println("==============================================");

		try {
			Coupon couponUpdate = new Coupon(1, company.getCompanyId(), Category.ELECTRICITY, "Refrigerator",
					"Upside down refrigerator", Date.valueOf(LocalDate.now()), Date.valueOf("2023-03-03"), 30, 7000,
					"black");
			company.updateCoupon(couponUpdate);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The method receives an id of a coupon and deletes the coupon from the
	 * database and also deletes the history of the purchase of this coupon from the
	 * purchase table, deletes the purchases of the coupon.
	 * 
	 * @param couponID - the coupon code.
	 * @throws CouponSystemException if coupon does not exist in the database.
	 */
	public void deleteCoupon() {

		System.out.println();
		System.out.println("======DELETE COUPON======");
		System.out.println("==============================================");

		try {
			company.deleteCoupon(2);
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The method creates a list of all the coupons of the company that has logged
	 * in.
	 * 
	 * @return the list of the coupons of the company that has logged in.
	 * @throws CouponSystemException if the company has no coupons in the database.
	 */
	public void getCompanyCoupons() {

		System.out.println();
		System.out.println("======GET COMPANY COUPONS======");
		System.out.println("==============================================");

		try {
			List<Coupon> getCompanyCoupons = company.getCompanyCoupons();
			for (Coupon coupon : getCompanyCoupons) {
				System.out.println(coupon);
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
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
	public void getCompanyCouponsByCategory() {

		System.out.println();
		System.out.println("======GET COMPANY COUPONS BY CATEGORY======");
		System.out.println("==============================================");

		try {
			List<Coupon> getCompanyCoupons = company.getCompanyCoupons(Category.FOOD);
			for (Coupon coupon : getCompanyCoupons) {
				System.out.println(coupon);
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
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
	public void getCompanyCouponsByPrice() {

		System.out.println();
		System.out.println("======GET COMPANY COUPONS BY MAX PRICE======");
		System.out.println("==============================================");

		try {
			List<Coupon> getCompanyCoupons = company.getCompanyCoupons(1000);
			for (Coupon coupon : getCompanyCoupons) {
				System.out.println(coupon);
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The method extracts from the database the details of the company that made a
	 * login.
	 * 
	 * @return the details of the company that logged in.
	 * @throws CouponSystemException if the operation failed.
	 */
	public void getCompanyDetails() {

		System.out.println();
		System.out.println("======GET COMPANY DETAILS======");
		System.out.println("==============================================");

		try {
			System.out.println(company.getCompanyDetails());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		System.out.println();
		System.out.println();

	}
}
