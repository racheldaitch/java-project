package app.core.services;

import java.util.List;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.exception.CouponSystemException;

public interface CompanyService {

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
	Coupon addCoupon(Coupon coupon, int companyId) throws CouponSystemException;

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
	Coupon updateCoupon(Coupon coupon, int companyId) throws CouponSystemException;

	/**
	 * The method receives an id of a coupon and deletes the coupon from the
	 * database and also deletes the history of the purchase of this coupon from the
	 * purchase table, deletes the purchases of the coupon.
	 * 
	 * @param couponID - the coupon code.
	 * @throws CouponSystemException if coupon does not exist in the database.
	 */
	void deleteCoupon(int couponID, int companyId) throws CouponSystemException;

	/**
	 * The method creates a list of all the coupons of the company that has logged
	 * in.
	 * 
	 * @return the list of the coupons of the company that has logged in.
	 * @throws CouponSystemException if the company has no coupons in the database.
	 */
	List<Coupon> getCompanyCoupons(int companyId) throws CouponSystemException;

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
	List<Coupon> getCompanyCoupons(Category category, int companyId) throws CouponSystemException;

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
	List<Coupon> getCompanyCoupons(double maxPrice, int companyId) throws CouponSystemException;

	/**
	 * The method extracts from the database the details of the company that made a
	 * login.
	 * 
	 * @return the details of the company that logged in.
	 * @throws CouponSystemException if the operation failed.
	 */
	Company getCompanyDetails(int companyId) throws CouponSystemException;

}
