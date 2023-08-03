package riccardogulin.u5d9.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import riccardogulin.u5d9.exceptions.UnauthorizedException;
import riccardogulin.u5d9.users.User;

@Component
public class JWTTools {

	@Value("${spring.jwt.secret}")
	private String secret;

	public String createToken(User u) {
		String token = Jwts.builder().setSubject(u.getId().toString()) // A chi appartiene il token (Subject)
				.setIssuedAt(new Date(System.currentTimeMillis())) // Quando è stato emesso il token (IAT - Issued At)
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // Quando scadrà il
				// token
				.signWith(Keys.hmacShaKeyFor(secret.getBytes())) // Genero la firma del token
				.compact(); // Crea il token con quanto detto prima
		return token;
	}

	public void verifyToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new UnauthorizedException("Il token non è valido! Per favore effettua di nuovo il login");
		}
	}

	public String extractSubject(String token) {
		return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parseClaimsJws(token)
				.getBody().getSubject();
		// Nel body (payload) ci sono il subject ("sub"), la data di emissione ("iat") e
		// la data di scadenza ("exp")
		// Nel nostro caso il subject è l'ID dell'utente
	}
}
