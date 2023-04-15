package app.core.facade;

import app.core.data.dao.CompaniesDAO;
import app.core.data.dao.CompaniesDBDAO;
import app.core.data.dao.CouponsDAO;
import app.core.data.dao.CouponsDBDAO;
import app.core.data.dao.CustomersDAO;
import app.core.data.dao.CustomersDBDAO;
import app.core.exception.CouponSystemException;

public abstract class ClientFacade {

	protected CompaniesDAO companiesDAO = new CompaniesDBDAO();
	protected CustomersDAO customersDAO = new CustomersDBDAO();
	protected CouponsDAO couponsDAO = new CouponsDBDAO();

	public abstract boolean login(String email, String password) throws CouponSystemException;

}
