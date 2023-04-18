package app.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.core.entities.Customer;
import app.core.exception.CouponSystemException;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	/**
	 * The method receives a customer's email and password and checks in the
	 * database whether the customer exists in the customers table.
	 * 
	 * @param email    - of a customer.
	 * @param password - of a customer.
	 * @return boolean value does the customer exist in the database using the email
	 *         and password.
	 * @throws CouponSystemException if the data check against the database failed.
	 */
	boolean existsByEmailAndPassword(String email, String password) throws CouponSystemException;

	/**
	 * The method receives a customer's email and password or checks in the database
	 * whether the customer exists in the customers table.
	 * 
	 * @param email    - of a customer.
	 * @param password - of a customer.
	 * @return boolean value does the customer exist in the database using the email
	 *         and password.
	 * @throws CouponSystemException if the data check against the database failed.
	 */
	boolean existsByEmailOrPassword(String email, String password) throws CouponSystemException;

	/**
	 * The method receives a customer's email and checks in the database whether the
	 * customer exists in the customers table.
	 * 
	 * @param email - of a customer.
	 * @return boolean value does the customer exists in the database using the
	 *         email.
	 * @throws CouponSystemException if the data check against the database failed.
	 */
	boolean existsByEmail(String email) throws CouponSystemException;

	/**
	 * The method receives a customer's email and password and returns all customer
	 * details from the database.
	 * 
	 * @param email    - of a customer.
	 * @param password - of a customer.
	 * @return full customer details from database: id, first name, last name, email
	 *         and password. or an empty client if it does not exist in the
	 *         database.
	 * @throws CouponSystemException if failed to get customer data from database.
	 */
	Customer findByEmailAndPassword(String email, String password) throws CouponSystemException;

	/**
	 * A query checks whether the customer has a coupon with a specific code, and
	 * returns a boolean value.
	 * 
	 * @param customerID - customer ID number.
	 * @param couponID   - code of coupon.
	 * @return Returns a boolean value whether the customer has a coupon with this
	 *         code
	 */
	@Query(value = "SELECT CASE WHEN (select exists(select * from `customer_vs_coupons` where customer_id=? and coupon_id=?)) = 1 THEN 'True' else 'False' END;", nativeQuery = true)
	boolean existsCouponPurchase(int customerId, int couponId);
}
