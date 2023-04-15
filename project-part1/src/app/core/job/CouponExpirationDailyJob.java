package app.core.job;

import java.util.concurrent.TimeUnit;

import app.core.data.dao.CouponsDAO;
import app.core.data.dao.CouponsDBDAO;
import app.core.exception.CouponSystemException;

public class CouponExpirationDailyJob implements Runnable {

	private CouponsDAO couponsDAO;

	public CouponExpirationDailyJob() {
		super();
		this.couponsDAO = new CouponsDBDAO();
	}

	/**
	 * Daily job, performed once a day, deletes expired coupons. When deleting
	 * expired coupons, coupons are also deleted from the coupons table and the
	 * purchase history is also deleted from the purchases table.
	 */
	@Override
	public void run() {
		while (true) {
			try {

				couponsDAO.deleteExpiredCoupons();
				TimeUnit.HOURS.sleep(24);
				;
			} catch (CouponSystemException e) {
				System.out.println(e);
			} catch (InterruptedException e) {
				System.out.println(e);

			}
		}
	}

}
