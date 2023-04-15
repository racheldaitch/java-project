package app.core.login;

import app.core.exception.CouponSystemException;
import app.core.facade.AdminFacade;
import app.core.facade.ClientFacade;
import app.core.facade.CompanyFacade;
import app.core.facade.CustomerFacade;

public class LoginManager {

	private static LoginManager instance;

	private LoginManager() {

	}

	/**
	 * A method belonging to the Singleton class, creates a single instance of the
	 * object and returns it.
	 * 
	 * @return instance.
	 */
	public static LoginManager getInstance() {
		if (instance == null) {

			synchronized (Object.class) {

				if (instance == null) {
					instance = new LoginManager();
				}
			}
		}
		return instance;
	}

	/**
	 * The method receives email, password and connection type and checks for each
	 * one Of the three types of clients, are the login details correct, if correct
	 * - login operation.
	 * 
	 * @param email      - of the user.
	 * @param password   - of the user.
	 * @param clientType - the type of connector.
	 * @return client type is initialized or null in case of wrong connection.
	 * @throws CouponSystemException if the login information is incorrect.
	 */
	public ClientFacade login(String email, String password, ClientType clientType) throws CouponSystemException {
		switch (clientType.name()) {

		case "ADMINISTRATOR":
			AdminFacade adminFacade = new AdminFacade();
			if (adminFacade.login(email, password)) {
				return adminFacade;
			}

		case "COMPANY":
			CompanyFacade companyFacade = new CompanyFacade();
			if (companyFacade.login(email, password)) {
				return companyFacade;
			}

		case "CUSTOMER":
			CustomerFacade customerFacade = new CustomerFacade();
			if (customerFacade.login(email, password)) {
				return customerFacade;
			}
		}
		return null;

	}
}
