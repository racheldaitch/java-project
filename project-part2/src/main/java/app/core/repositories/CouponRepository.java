package app.core.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.exception.CouponSystemException;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

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
	List<Coupon> findByCompanyId(int companyId);

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
	List<Coupon> findByCompanyIdAndCategory(int companyId, Category category);

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
	List<Coupon> findByCompanyIdAndPriceLessThanEqual(int companyId, double price);

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
	@Query(value = "SELECT CASE WHEN (select exists(select * from `coupons` where endDate > now() and amount > 0 and id = ?)) = 1 THEN 'True' else 'False' END;", nativeQuery = true)
	boolean isInventoryAndDateCoupon(int couponID) throws CouponSystemException;

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
	@Modifying
	@Query(value = "insert into `customer_vs_coupons` values(?, ?);", nativeQuery = true)
	void addCouponPurchase(int customerID, int couponID) throws CouponSystemException;

	/**
	 * The method receives a coupon code and updates the amount of coupons of the
	 * same type in the database - subtracting 1 from the amount.
	 * 
	 * @param couponID - the coupon code.
	 * @throws CouponSystemException if updating the amount of coupons in the
	 *                               database failed.
	 */
	@Modifying
	@Query(value = "update `coupons` set amount = amount-1  where id = ?;", nativeQuery = true)
	void updateAmountCoupon(int couponID) throws CouponSystemException;

	/**
	 * The method deletes from the coupons table in the database the coupons whose
	 * expiration date has passed.
	 * 
	 * @throws CouponSystemException if the deletion of their coupons and purchases
	 *                               against the database failed.
	 */

//	@Modifying
//	@Query(value = "delete from `coupons` where end_date < now();", nativeQuery = true)
//	void deleteExpiredCoupons() throws CouponSystemException;
	void deleteByEndDateBefore(LocalDate date);

	/**
	 * The method deletes from the purchased coupons table in the database the
	 * coupons whose expiration date has passed.
	 * 
	 * @throws CouponSystemException if the deletion of their coupons and purchases
	 *                               against the database failed.
	 */
//	@Modifying
//	@Query(value = "delete from `customer_vs_coupons` where coupon_id in (select id from `coupons` where end_date < now());", nativeQuery = true)
//	void deleteExpiredPurchasedCoupons() throws CouponSystemException;

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
	@Query(value = "select * from `coupons` join `customer_vs_coupons` on coupons.id = customer_vs_coupons.coupon_id where customer_id = ?;", nativeQuery = true)
	List<Coupon> findCouponsOfCustomer(int customerID) throws CouponSystemException;

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
	@Query(value = "select * from `coupons` join `customer_vs_coupons` on coupons.id = customer_vs_coupons.coupon_id where customer_id = ? and  category = ?;", nativeQuery = true)
	List<Coupon> findCategoryCouponsOfCustomer(int customerID, String category) throws CouponSystemException;

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
	@Query(value = "select * from `coupons` join `customer_vs_coupons` on coupons.id = customer_vs_coupons.coupon_id where customer_id = ? and  price<=?;", nativeQuery = true)
	List<Coupon> findCouponsMaxPriceOfCustomer(int customerID, double MaxPrice) throws CouponSystemException;

}
