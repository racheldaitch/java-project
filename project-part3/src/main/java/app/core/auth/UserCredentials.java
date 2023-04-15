package app.core.auth;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

//import app.core.login.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentials {
	@Id
	private int id;
	private String name;
	private String email;
	private String password;
	@Enumerated(EnumType.STRING)
	private ClientType clientType;

	public enum ClientType {
		ADMIN, COMPANY, CUSTOMER;

	}
}
