package app.core.job;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import app.core.repositories.CouponRepository;

@Transactional
@Component
public class CouponExpirationDailyJob {
	@Autowired
	private CouponRepository couponRepository;

	/**
	 * A daily job, performed once a day at midnight, deletes expired coupons. The
	 * deletion is from the coupons table and also from the purchase history table.
	 */
	@Scheduled(cron = "0 00 00 ? * *")
//	@Scheduled(timeUnit = TimeUnit.SECONDS, fixedRate = 1)
	public void doAllDay() {
//		try {
//			couponRepository.deleteExpiredPurchasedCoupons();
		couponRepository.deleteByEndDateBefore(LocalDate.now());
//		} catch (CouponSystemException e) {
//			System.out.println(e);
//		}
	}

}
