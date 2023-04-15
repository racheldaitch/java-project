package app.core.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import app.core.auth.UserCredentials.ClientType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

@Component
public class JwtUtil extends JwtUtilAbstract<UserCredentials, Integer> {

	@Override
	public String generateToken(UserCredentials user) {

		Map<String, Object> claims = new HashMap<>();
		claims.put("name", user.getName());
		claims.put("email", user.getEmail());
		claims.put("clientType", user.getClientType());
		user.setPassword(null);
//		claims.put("user", user); You can return a foreign object that will contain the parameters
		String token = this.createToken(claims, user.getId());
		return token;
	}

	@Override
	public UserCredentials extractUser(String token) throws JwtException {
		Claims claims = this.extractAllClaims(token);
		int id = Integer.parseInt(claims.getSubject());
		String name = claims.get("name", String.class);
		String email = claims.get("email", String.class);
		ClientType clientType = ClientType.valueOf(claims.get("clientType", String.class));
		UserCredentials user = new UserCredentials(id, name, email, null, clientType);
		return user;
	}

}
