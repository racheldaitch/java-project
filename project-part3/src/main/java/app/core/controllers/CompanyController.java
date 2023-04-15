package app.core.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.core.auth.UserCredentials;
import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.exception.CouponSystemException;
import app.core.services.CompanyServiceImpl;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/company/")
public class CompanyController extends AuthController {

	@Autowired
	private CompanyServiceImpl companyService;

	@PostMapping(headers = { HttpHeaders.AUTHORIZATION })
	@ResponseStatus(HttpStatus.CREATED)
	public Coupon addCoupon(@RequestBody Coupon coupon, HttpServletRequest req) throws CouponSystemException {
		UserCredentials user = (UserCredentials) req.getAttribute("user");
		return companyService.addCoupon(coupon, user.getId());
	}

	@PutMapping(headers = { HttpHeaders.AUTHORIZATION })
	@ResponseStatus(HttpStatus.CREATED)
	public Coupon updateCoupon(@RequestBody Coupon coupon, HttpServletRequest req) throws CouponSystemException {
		UserCredentials user = (UserCredentials) req.getAttribute("user");
		return companyService.updateCoupon(coupon, user.getId());
	}

	@DeleteMapping(path = "{couponID}", headers = { HttpHeaders.AUTHORIZATION })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCoupon(@PathVariable int couponID, HttpServletRequest req) throws CouponSystemException {
		UserCredentials user = (UserCredentials) req.getAttribute("user");
		companyService.deleteCoupon(couponID, user.getId());
	}

	@GetMapping(path = "coupons", headers = { HttpHeaders.AUTHORIZATION })
	@ResponseStatus(HttpStatus.OK)
	public List<Coupon> getCompanyCoupons(HttpServletRequest req) throws CouponSystemException {
		UserCredentials user = (UserCredentials) req.getAttribute("user");
		return companyService.getCompanyCoupons(user.getId());
	}

	@GetMapping(path = "coupons-by-category", headers = { HttpHeaders.AUTHORIZATION })
	@ResponseStatus(HttpStatus.OK)
	public List<Coupon> getCompanyCoupons(@RequestParam Category category, HttpServletRequest req)
			throws CouponSystemException {
		System.out.println(category);
		UserCredentials user = (UserCredentials) req.getAttribute("user");
		return companyService.getCompanyCoupons(category, user.getId());
	}

	@GetMapping(path = "coupons-until-price", headers = { HttpHeaders.AUTHORIZATION })
	@ResponseStatus(HttpStatus.OK)
	public List<Coupon> getCompanyCoupons(@RequestParam double maxPrice, HttpServletRequest req)
			throws CouponSystemException {
		UserCredentials user = (UserCredentials) req.getAttribute("user");
		return companyService.getCompanyCoupons(maxPrice, user.getId());
	}

	@GetMapping(path = "details", headers = { HttpHeaders.AUTHORIZATION })
	@ResponseStatus(HttpStatus.OK)
	public Company getCompanyDetails(HttpServletRequest req) throws CouponSystemException {
		UserCredentials user = (UserCredentials) req.getAttribute("user");
		return companyService.getCompanyDetails(user.getId());
	}

}
