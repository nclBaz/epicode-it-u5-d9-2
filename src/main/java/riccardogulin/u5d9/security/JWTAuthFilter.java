package riccardogulin.u5d9.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import riccardogulin.u5d9.exceptions.UnauthorizedException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
	// estendere questo ci serve per stabilire che questa classe sia un Security
	// Filter

	@Autowired
	JWTTools jwttools;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 0. Questo metodo verrà eseguito per ogni request che richieda autorizzazione
		// 1. Prima di tutto dovrò estrarre il token dall'Authorization header
		String authHeader = request.getHeader("Authorization"); // Bearer
																// eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwMjc3MzUwNS1kOWUxLTRiMmMtYjJkZS1iNWJlZGVlZTg5MzYiLCJpYXQiOjE2OTEwNTg5MzcsImV4cCI6MTY5MTY2MzczN30.n-v6f5vdE8l8Nv3sa73H2P4DvZFV-BCWm40B2mnB8cE
		System.out.println("JTW FILTERRRRRRRRRRRRRRRRRRRRRRR");
		if (authHeader == null || !authHeader.startsWith("Bearer "))
			throw new UnauthorizedException("Per favore passa il token nell'authorization header");
		String token = authHeader.substring(7);
		System.out.println(token);

		// 2. Verifico che il token non sia nè scaduto nè sia stato manipolato
		jwttools.verifyToken(token);

		// 3. Se è tutto OK puoi procedere al prossimo blocco della filter chain
		filterChain.doFilter(request, response);

		// 4. Se non è OK --> 401 ("Per favore effettua di nuovo il login")
	}

}
