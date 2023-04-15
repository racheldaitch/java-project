package app.core.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import app.core.exception.CouponSystemException;
import app.core.services.AdminServiceImpl;
import app.core.services.ClientService;
import app.core.services.CompanyServiceImpl;
import app.core.services.CustomerServiceImpl;

@Service
public class LoginManager {
	@Autowired
	private AdminServiceImpl adminServiceImpl;

	@Autowired
	private ApplicationContext ctx;

	/**
	 * A method checks login details.
	 * 
	 * @param email      - of the connecting user
	 * @param password   - of the connecting user
	 * @param clientType - the connection type.
	 * @return a login in case of success, or an error if the login details are
	 *         incorrect - relying on the login method in the service that is
	 *         activated by this method.
	 */
	public ClientService login(String email, String password, ClientType clientType) throws CouponSystemException {
		switch (clientType.name()) {

		case "ADMINISTRATOR":
			if (adminServiceImpl.login(email, password)) {
				return adminServiceImpl;
			}
			throw new CouponSystemException("Email or Password is wrong!");

		case "COMPANY":
			CompanyServiceImpl companyServiceImpl = ctx.getBean(CompanyServiceImpl.class);
			if (companyServiceImpl.login(email, password)) {
				return companyServiceImpl;
			}
			throw new CouponSystemException("Email or Password is wrong!");

		case "CUSTOMER":
			CustomerServiceImpl customerServiceImpl = ctx.getBean(CustomerServiceImpl.class);
			if (customerServiceImpl.login(email, password)) {
				return customerServiceImpl;
			}
			throw new CouponSystemException("Email or Password is wrong!");

		default:
			throw new CouponSystemException("Invalid Client Type");
		}

	}
}
