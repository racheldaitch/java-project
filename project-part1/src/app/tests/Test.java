package app.tests;

import app.core.data.ConnectionPool;
import app.core.exception.CouponSystemException;
import app.core.job.CouponExpirationDailyJob;

public class Test {

	public void testAll() {

		try {
			CouponExpirationDailyJob jobs = new CouponExpirationDailyJob();
			Thread job = new Thread(jobs);
			job.setDaemon(true);
			job.start();

			new TestAdmin().runAllAdminTests();
			new TestCompany().runAllCompanyTests();
			new TestCustomer().runAllCustomerFacadeTests();

			ConnectionPool.getInstance().closeAllConnections();
		} catch (CouponSystemException e) {
			System.out.println(e);
		}
	}

}
