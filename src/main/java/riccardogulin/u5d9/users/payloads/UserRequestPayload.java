package riccardogulin.u5d9.users.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequestPayload {
	private String name;
	private String surname;
	private String email;
	private String password;
}
