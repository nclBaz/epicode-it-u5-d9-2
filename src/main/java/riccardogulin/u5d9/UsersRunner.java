package riccardogulin.u5d9;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import riccardogulin.u5d9.users.UserRequestPayload;
import riccardogulin.u5d9.users.UsersService;

@Component
public class UsersRunner implements CommandLineRunner {
	@Autowired
	UsersService usersServ;

	@Override
	public void run(String... args) throws Exception {
		Faker faker = new Faker(new Locale("it"));

		for (int i = 0; i < 30; i++) {
			String name = faker.name().firstName();
			String surname = faker.name().lastName();
			String email = faker.internet().emailAddress();
			String password = "1234";
			UserRequestPayload user = new UserRequestPayload(name, surname, email, password);
			// usersServ.create(user);
		}

	}

}
