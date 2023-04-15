package app.core.data.dao;

import java.util.List;

import app.core.beans.Category;
import app.core.beans.Coupon;
import app.core.exception.CouponSystemException;

public interface CouponsDAO {

	/**
	 * The method receives a coupon's code and checks in the database whether the
	 * coupon exists in the coupons table.
	 * 
	 * @param couponID - the coupon code.
	 * @return boolean value Whether the coupon exists exists in the database.
	 * @throws CouponSystemException if the data check against the database failed.
	 */
	boolean isCouponExists(int couponID) throws CouponSystemException;

	/**
	 * The method receives a coupon and adds it to the coupons table in the
	 * database. and initializes the coupon code according to the code the coupon
	 * received in the database.
	 * 
	 * @param coupon - Coupon with: company code, category type, title, description,
	 *               start date, end date, amount, price and image.
	 * @throws CouponSystemException if adding the coupon to the database failed.
	 */
	void addCoupon(Coupon coupon) throws CouponSystemException;

	/**
	 * This method makes it possible to update the database on an existing coupon.
	 * The method receives an updated coupon and updates the details in the database
	 * according to the code number of the coupon.
	 * 
	 * @param coupon - Coupon with: company code, category type, title, description,
	 *               start date, end date, amount, price and image after updating.
	 * @throws CouponSystemException if the coupon update in the database fails.
	 */
	void updateCoupon(Coupon coupon) throws CouponSystemException;

	/**
	 * The method receives a coupon code and updates the amount of coupons of the
	 * same type in the database - subtracting 1 from the amount.
	 * 
	 * @param couponID - the coupon code.
	 * @throws CouponSystemException if updating the amount of coupons in the
	 *                               database failed.
	 */
	void updateAmountCoupon(int couponID) throws CouponSystemException;

	/**
	 * The method receives a coupon code and deletes the coupon from the coupons
	 * table in the database and also deletes its purchase history, deletes the
	 * coupon from the purchases table.
	 * 
	 * @param couponID - the coupon code.
	 * @throws CouponSystemException if the coupon delete in the database failed.
	 */
	void deleteCoupon(int couponID) throws CouponSystemException;

	/**
	 * The method receives a company code and deletes in the database from the
	 * coupons table all the company's coupons, and also deletes their purchase
	 * history, deletes the company's coupons from the purchases table.
	 * 
	 * @param companyID - the company code.
	 * @throws CouponSystemException if the deletion of the company's coupons from
	 *                               the coupons table and the purchases table in
	 *                               the database failed.
	 */
	void deleteCouponsOfCompany(int companyID) throws CouponSystemException;

	/**
	 * The method receives from the database the list of coupons in the coupons
	 * table.
	 * 
	 * @return list of the coupons that exist in the database or an empty list if
	 *         there are no registered coupons.
	 * @throws CouponSystemException if the operation of getting the coupons list
	 *                               from the database failed.
	 */
	List<Coupon> getAllCoupons() throws CouponSystemException;

	/**
	 * The method receives by company code the list of all the company's coupons
	 * from the coupons table.
	 * 
	 * @param companyID - the company code.
	 * @return list of company coupons that exist in the database or an empty list
	 *         if there are no registered coupons to the company.
	 * @throws CouponSystemException if the operation of getting the company coupons
	 *                               list from the database failed.
	 */
	List<Coupon> getAllCouponsOfCompany(int companyID) throws CouponSystemException;

	/**
	 * The method receives by company code the list of all the company's coupons
	 * from the coupons table according to the selected category.
	 * 
	 * @param companyID - the company code.
	 * @param category  - the category of the coupon.
	 * @return list of company coupons from the selected category that exist in the
	 *         database or an empty list if there are no registered coupons to the
	 *         company according to this choice.
	 * @throws CouponSystemException if the operation of receiving the company's
	 *                               coupons from the database according to the
	 *                               selected category failed.
	 */
	List<Coupon> getCategoryCouponsOfCompany(int companyID, Category category) throws CouponSystemException;

	/**
	 * The method receives by company code the list of all the company's coupons
	 * from the coupons table according up to a selected price.
	 * 
	 * @param companyID - the company code.
	 * @param maxPrice  - maximum price of coupons you wish to receive.
	 * @return list of company coupons up to a selected price that exist in the
	 *         database or an empty list if there are no registered coupons to the
	 *         company according to this choice.
	 * @throws CouponSystemException if the operation of receiving the company's
	 *                               coupons from the database up to a selected
	 *                               price failed..
	 */
	List<Coupon> getCouponsMaxPriceOfCompany(int companyID, double price) throws CouponSystemException;

	/**
	 * The method receives a coupon code and receives the requested coupon details
	 * from the coupons table database.
	 * 
	 * @param couponID - the coupon code.
	 * @return coupon details in the database - id, company code, category type,
	 *         title, description, start date, end date, amount, price and image. if
	 *         a coupon does not exist, an empty coupon will be returned.
	 * @throws CouponSystemException if failed to get coupon from database.
	 */
	Coupon getOneCoupon(int couponID) throws CouponSystemException;

	/**
	 * The method adds coupon purchases to the database to the purchases table by
	 * customer ID number and coupon code. If the customer has a coupon with the
	 * same code as the coupon he wishes to purchase, the method will not allow him
	 * to purchase the coupon.
	 * 
	 * @param customerID - customer ID number.
	 * @param couponID   - the coupon code.
	 * @throws CouponSystemException if the customer already has a coupon of the
	 *                               same type in the database.
	 */
	void addCouponPurchase(int customerID, int couponID) throws CouponSystemException;

	/**
	 * The method deletes the coupon purchase from the purchases table in the
	 * database.
	 * 
	 * @param customerID - customer ID number.
	 * @param couponID   - the coupon code.
	 * @throws CouponSystemException if deleting the purchase history from Database
	 *                               failed.
	 */
	void deleteCouponPurchase(int customerID, int couponID) throws CouponSystemException;

	/**
	 * The method checks in the database in the coupons table by coupon code if the
	 * coupon is in stock and also if the expiration date of the coupon has not
	 * passed.
	 * 
	 * @param couponID - the coupon code.
	 * @return boolean value true if the coupon is in stock and the expiration date
	 *         has also not passed or false if no.
	 * @throws CouponSystemException if the check against the database failed.
	 */
	boolean isInventoryAndDateCoupon(int couponD) throws CouponSystemException;

	/**
	 * The method deletes from the coupons table and the purchases table in the
	 * database the coupons whose expiration date has passed.
	 * 
	 * @throws CouponSystemException if the deletion of their coupons and purchases
	 *                               against the database failed.
	 */
	void deleteExpiredCoupons() throws CouponSystemException;

	/**
	 * The method receives from the database of the purchases table the details of
	 * the coupons purchased by the specific selected customer.
	 * 
	 * @param customerID - customer ID number.
	 * @return the list of coupons the customer has purchased with the coupon
	 *         details or an empty list if the customer has not purchased any
	 *         coupons.
	 * @throws CouponSystemException if the operation of getting the customer's
	 *                               coupon list from the database failed.
	 */
	List<Coupon> getCouponsOfCustomer(int customerID) throws CouponSystemException;

	/**
	 * The method receives from the database of the purchases table the details of
	 * the coupons purchased by the customer according to a specific category.
	 * 
	 * @param customerID - customer ID number.
	 * @param category   - the category of the coupon.
	 * @return the list of coupons that the customer purchased with the details of
	 *         the coupons according to the selected category or an empty list if
	 *         the customer did not purchase coupons from this category.
	 * @throws CouponSystemException if the operation of receiving the customer's
	 *                               coupon list by category from the database
	 *                               failed.
	 */
	List<Coupon> getCategoryCouponsOfCustomer(int customerID, Category category) throws CouponSystemException;

	/**
	 * The method receives from the database of the purchases table the details of
	 * the coupons purchased by the customer up to a selected maximum price.
	 * 
	 * @param customerID - customer ID number.
	 * @param MaxPrice   - maximum price of coupons you wish to receive.
	 * @return the list of coupons that the customer has purchased with the details
	 *         of the coupons up to the selected maximum price, or an empty list if
	 *         the customer has not purchased coupons up to this price.
	 * @throws CouponSystemException if the operation of receiving the customer's
	 *                               coupon list up to a selected maximum price from
	 *                               the database failed.
	 */
	List<Coupon> getCouponsMaxPriceOfCustomer(int customerID, double MaxPrice) throws CouponSystemException;

}
