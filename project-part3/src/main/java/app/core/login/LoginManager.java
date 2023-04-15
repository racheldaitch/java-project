package app.core.login;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.core.auth.UserCredentials;
import app.core.exception.CouponSystemException;
import app.core.services.AdminServiceImpl;
import app.core.services.CompanyServiceImpl;
import app.core.services.CustomerServiceImpl;

@Service
public class LoginManager {
	@Autowired
	private AdminServiceImpl adminServiceImpl;
	@Autowired
	private CompanyServiceImpl companyServiceImpl;
	@Autowired
	private CustomerServiceImpl customerServiceImpl;

	/**
	 * A method checks login details.
	 * 
	 * @param email      - of the connecting user
	 * @param password   - of the connecting user
	 * @param clientType - the connection type.
	 * @return a login in case of success, or an error if the login details are
	 *         incorrect - relying on the login method in the service that is
	 *         activated by this method.
	 * @throws LoginException
	 */
	public String login(UserCredentials userCredentials) throws CouponSystemException, LoginException {
		switch (userCredentials.getClientType().name()) {

		case "ADMIN":

			String tokenAdmin = adminServiceImpl.login(userCredentials);
			return tokenAdmin;

		case "COMPANY":

			String tokenCompany = companyServiceImpl.login(userCredentials);
			return tokenCompany;

		case "CUSTOMER":

			String tokenCustomer = customerServiceImpl.login(userCredentials);
			return tokenCustomer;
		default:
			throw new CouponSystemException("Invalid Client Type");
		}

	}
}
