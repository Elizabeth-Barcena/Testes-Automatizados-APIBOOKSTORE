package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.RequiredArgsConstructor;
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Login {
	private String userName;
	private String password;
	private Token token;
	
	
	
}
