package app.core.services;

import org.springframework.beans.factory.annotation.Autowired;

import app.core.exception.CouponSystemException;
import app.core.repositories.CompanyRepository;
import app.core.repositories.CouponRepository;
import app.core.repositories.CustomerRepository;

public abstract class ClientService {

	@Autowired
	protected CouponRepository couponRepository;
	@Autowired
	protected CompanyRepository companyRepository;
	@Autowired
	protected CustomerRepository customerRepository;

	public abstract boolean login(String email, String password) throws CouponSystemException;

}
