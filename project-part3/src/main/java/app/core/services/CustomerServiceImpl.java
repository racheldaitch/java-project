package app.core.services;

import java.util.List;
import java.util.Optional;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.core.auth.JwtUtil;
import app.core.auth.UserCredentials;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exception.CouponSystemException;

@Service
@Transactional
public class CustomerServiceImpl extends ClientService implements CustomerService {

	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * The method receives a customer's email and password, and checks against the
	 * database whether the login details are correct. If the login information is
	 * correct, the method initializes the customerID.
	 * 
	 * @param email    - of a customer.
	 * @param password - of a customer.
	 * @return boolean value if the login details are correct.
	 */

	@Override
	public String login(UserCredentials userCredentials) throws CouponSystemException, LoginException {
		if (customerRepository.existsByEmailAndPassword(userCredentials.getEmail(), userCredentials.getPassword())) {
			Customer customer = customerRepository.findByEmailAndPassword(userCredentials.getEmail(),
					userCredentials.getPassword());
			userCredentials.setId(customer.getId());
			userCredentials.setName(customer.getFirstName() + " " + customer.getLastName());
			return this.jwtUtil.generateToken(userCredentials);
		}
		throw new LoginException("Loging failed - Email or Password is wrong!");
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
	public Coupon purchaseCoupon(int couponId, int customerId) throws CouponSystemException {
		if (customerRepository.existsCouponPurchase(customerId, couponId)) {
			throw new CouponSystemException("You cannot purchase more than one coupon of the same type");
		}
		if (!couponRepository.isInventoryAndDateCoupon(couponId)) {
			System.out.println(!couponRepository.isInventoryAndDateCoupon(couponId));
			throw new CouponSystemException("Cannot be purchased - No coupon in stock or expired");
		}

		couponRepository.addCouponPurchase(customerId, couponId);
		couponRepository.updateAmountCoupon(couponId);

		Optional<Coupon> couponFromDate = couponRepository.findById(couponId);
		return couponFromDate.get();

	}

	public List<Coupon> getAllCoupons() throws CouponSystemException {
		return couponRepository.findAll();
	}

	/**
	 * The method creates a list of all the coupons of the client who logged in.
	 * 
	 * @return the list of the coupons of the customer who has logged in.
	 * @throws CouponSystemException if the customer has no coupons in the database.
	 */
	public List<Coupon> getCustomerCoupons(int customerId) throws CouponSystemException {
		List<Coupon> coupons = couponRepository.findCouponsOfCustomer(customerId);
		return coupons;
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
	public List<Coupon> getCustomerCoupons(String category, int customerId) throws CouponSystemException {
		List<Coupon> coupons = couponRepository.findCategoryCouponsOfCustomer(customerId, category);
		return coupons;
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
	public List<Coupon> getCustomerCoupons(double maxPrice, int customerId) throws CouponSystemException {
		List<Coupon> coupons = couponRepository.findCouponsMaxPriceOfCustomer(customerId, maxPrice);
		return coupons;
	}

	/**
	 * The method extracts from the database the details of the customer that made a
	 * login.
	 * 
	 * @return the details of the customer that logged in.
	 * @throws CouponSystemException if the operation failed.
	 */
	public Customer getCustomerDetails(int customerId) throws CouponSystemException {
		return customerRepository.findById(customerId).get();
	}

}
