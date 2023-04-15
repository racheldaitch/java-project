package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.core.entities.Coupon;
import app.core.exception.CouponSystemException;
import app.core.services.AllCouponsService;

@CrossOrigin("*")
@RestController
@RequestMapping("/allCoupons")
public class AllCouponsController {
	@Autowired
	private AllCouponsService allCouponsService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Coupon> getAllCoupons() throws CouponSystemException {
		return allCouponsService.getAllCoupons();
	}
}
