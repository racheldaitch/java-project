package app.core.services;

import java.util.List;

import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exception.CouponSystemException;

public interface CustomerService {

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
	void purchaseCoupon(Coupon coupon) throws CouponSystemException;

	/**
	 * The method creates a list of all the coupons of the client who logged in.
	 * 
	 * @return the list of the coupons of the customer who has logged in.
	 * @throws CouponSystemException if the customer has no coupons in the database.
	 */
	List<Coupon> getCustomerCoupons() throws CouponSystemException;

	/**
	 * The method creates a list of all the coupons from the selected category of
	 * the customer who logged in.
	 * 
	 * @param category - the category of the coupon.
	 * @return the list of coupons by category of the customer that logged in.
	 * @throws CouponSystemException if the customer has no coupons from this
	 *                               category in the database.
	 */
	public List<Coupon> getCustomerCoupons(String category) throws CouponSystemException;

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
	public List<Coupon> getCustomerCoupons(double maxPrice) throws CouponSystemException;

	/**
	 * The method extracts from the database the details of the customer that made a
	 * login.
	 * 
	 * @return the details of the customer that logged in.
	 * @throws CouponSystemException if the operation failed.
	 */
	public Customer getCustomerDetails() throws CouponSystemException;

}
