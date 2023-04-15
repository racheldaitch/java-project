package app.core.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.core.auth.UserCredentials;
import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exception.CouponSystemException;
import app.core.services.CustomerServiceImpl;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/customer/")
public class CustomerController {

	@Autowired
	private CustomerServiceImpl customerService;

	@PostMapping(path = "purchase/{couponId}", headers = { HttpHeaders.AUTHORIZATION })
	@ResponseStatus(HttpStatus.CREATED)
	public Coupon purchaseCoupon(@PathVariable int couponId, HttpServletRequest req) throws CouponSystemException {
		UserCredentials user = (UserCredentials) req.getAttribute("user");
		return customerService.purchaseCoupon(couponId, user.getId());
	}

	@GetMapping(path = "all-coupons")
	@ResponseStatus(HttpStatus.OK)
	public List<Coupon> getAllCoupons() throws CouponSystemException {
		return customerService.getAllCoupons();
	}

	@GetMapping(path = "coupons", headers = { HttpHeaders.AUTHORIZATION })
	@ResponseStatus(HttpStatus.OK)
	public List<Coupon> getCustomerCoupons(HttpServletRequest req) throws CouponSystemException {
		UserCredentials user = (UserCredentials) req.getAttribute("user");
		return customerService.getCustomerCoupons(user.getId());
	}

	@GetMapping(path = "coupons-by-category", headers = { HttpHeaders.AUTHORIZATION })
	@ResponseStatus(HttpStatus.OK)
	public List<Coupon> getCustomerCoupons(@RequestParam Category category, HttpServletRequest req)
			throws CouponSystemException {
		UserCredentials user = (UserCredentials) req.getAttribute("user");
		return customerService.getCustomerCoupons(category.name(), user.getId());
	}

	@GetMapping(path = "coupons-until-price", headers = { HttpHeaders.AUTHORIZATION })
	@ResponseStatus(HttpStatus.OK)
	public List<Coupon> getCustomerCoupons(@RequestParam double maxPrice, HttpServletRequest req)
			throws CouponSystemException {
		UserCredentials user = (UserCredentials) req.getAttribute("user");
		return customerService.getCustomerCoupons(maxPrice, user.getId());
	}

	@GetMapping(path = "details", headers = { HttpHeaders.AUTHORIZATION })
	@ResponseStatus(HttpStatus.OK)
	public Customer getCustomerDetails(HttpServletRequest req) throws CouponSystemException {
		UserCredentials user = (UserCredentials) req.getAttribute("user");
		return customerService.getCustomerDetails(user.getId());
	}

}
