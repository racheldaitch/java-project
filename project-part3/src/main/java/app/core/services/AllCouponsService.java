package app.core.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.core.entities.Coupon;
import app.core.exception.CouponSystemException;
import app.core.repositories.CouponRepository;

@Service
@Transactional
public class AllCouponsService {
	@Autowired
	private CouponRepository couponRepository;

	public List<Coupon> getAllCoupons() throws CouponSystemException {
		return couponRepository.findAll();
	}
}
