package app.core.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exception.CouponSystemException;
import app.core.services.CustomerServiceImpl;

@CrossOrigin
@RestController
@RequestMapping("/api/customer/")
public class CustomerController {// extends ClientController {

	@Autowired
	private CustomerServiceImpl customerService;

//	@PostMapping
//	@Override
//	public boolean login(@RequestParam UserCredentials userCredentials) throws CouponSystemException, LoginException {
//		return customerService.login(userCredentials);
//	}

	@PostMapping // (path = "purchaseCoupon")
	public void purchaseCoupon(@RequestBody Coupon coupon) throws CouponSystemException {
		customerService.purchaseCoupon(coupon);
	}

	@GetMapping(path = "coupons")
	public List<Coupon> getCustomerCoupons() throws CouponSystemException {
		return customerService.getCustomerCoupons();
	}

	@GetMapping(path = "coupons-by-category")
	public List<Coupon> getCustomerCoupons(@RequestParam Category category) throws CouponSystemException {
		return customerService.getCustomerCoupons(category.name());
	}

	@GetMapping(path = "coupons-until-price")
	public List<Coupon> getCustomerCoupons(@RequestParam double maxPrice) throws CouponSystemException {
		return customerService.getCustomerCoupons(maxPrice);
	}

	@GetMapping(path = "details")
	public Customer getCustomerDetails(HttpServletRequest req) throws CouponSystemException {
		return customerService.getCustomerDetails();
	}

}
