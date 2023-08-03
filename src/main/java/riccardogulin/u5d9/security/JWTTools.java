package riccardogulin.u5d9.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import riccardogulin.u5d9.users.User;

@Component
public class JWTTools {

	public String createToken(User u) {
		String token = Jwts.builder().setSubject(u.getId().toString()) // A chi appartiene il token
				.setIssuedAt(new Date(System.currentTimeMillis())) // Quando è stato emesso il token
				.setExpiration(new Date(System.currentTimeMillis())) // Quando scadrà il
				// token
				.signWith(Keys.hmacShaKeyFor(
						"supersecretsupersecretsupersecretsupersecretsupersecretsupersecretsupersecretsupersecretsupersecretsupersecretsupersecretsupersecretsupersecretsupersecretsupersecretsupersecretsupersecretsupersecret"
								.getBytes())) // Genero la firma del token
				.compact(); // Crea il token con quanto detto prima
		return token;
	}

	public void verifyToken(String token) {
	}
}
