package app.core.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.exception.CouponSystemException;
import app.core.services.CompanyServiceImpl;

@CrossOrigin
@RestController
@RequestMapping("/api/company/")
public class CompanyController {

	@Autowired
	private CompanyServiceImpl companyService;

//	@PostMapping
//	@Override
//	public boolean login(@RequestParam UserCredentials userCredentials) throws CouponSystemException, LoginException {
//		return companyService.login(userCredentials);
//	}

	@PostMapping // (path = "add-coupon")
	public void addCoupon(@RequestBody Coupon coupon) throws CouponSystemException {
		companyService.addCoupon(coupon);
	}

	@PutMapping
	public void updateCoupon(@RequestBody Coupon coupon, HttpServletRequest req) throws CouponSystemException {
		companyService.updateCoupon(coupon);
	}

	@DeleteMapping(path = "{couponID}")
	public void deleteCoupon(@PathVariable int couponID) throws CouponSystemException {
		companyService.deleteCoupon(couponID);
	}

	@GetMapping(path = "coupons")
	public List<Coupon> getCompanyCoupons() throws CouponSystemException {
		return companyService.getCompanyCoupons();
	}

	@GetMapping(path = "coupons-by-category")
	public List<Coupon> getCompanyCoupons(@RequestParam Category category) throws CouponSystemException {
		return companyService.getCompanyCoupons(category);
	}

	@GetMapping(path = "coupons-until-price")
	public List<Coupon> getCompanyCoupons(@RequestParam double maxPrice) throws CouponSystemException {
		return companyService.getCompanyCoupons(maxPrice);
	}

	@GetMapping(path = "details")
	public Company getCompanyDetails() throws CouponSystemException {
		return companyService.getCompanyDetails();
	}

//	@GetMapping("getCompanyId")
//	public int getCompanyId() {
//		return companyService.getCompanyId();
//	}

}
