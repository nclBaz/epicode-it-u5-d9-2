package riccardogulin.u5d9.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import riccardogulin.u5d9.exceptions.UnauthorizedException;
import riccardogulin.u5d9.users.User;
import riccardogulin.u5d9.users.UsersService;
import riccardogulin.u5d9.users.payloads.UserLoginPayload;
import riccardogulin.u5d9.users.payloads.UserRequestPayload;

@Controller
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	UsersService usersService;
	
	@Autowired
	JWTTools jwtTools;

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public User saveUser(@RequestBody UserRequestPayload body) {
		User created = usersService.create(body);

		return created;
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserLoginPayload body) {
		// 1. Verifichiamo che l'email dell'utente sia presente nel db

		User user = usersService.findByEmail(body.getEmail());

		// 2. In caso affermativo, devo verificare che la pw corrisponda a quella
		// trovata nel db
		if (body.getPassword().equals(user.getPassword())) {

			// 3. Se le credenziali sono OK --> genero un JWT e lo invio come risposta
			String token = jwtTools.createToken(user);
			return new ResponseEntity<>(token, HttpStatus.OK);

		} else {
			// 4. Se le credenziali NON sono OK --> 401
			throw new UnauthorizedException("Credenziali non valide!");
		}
	}

}
