package dataFactory;

import java.util.Locale;

import com.github.javafaker.Faker;
import model.Login;

public class DynamicFactory {
	private static Faker faker = new Faker(Locale.ENGLISH);
	
	public static Login gerarUser() {
		
		Login accountUser = new Login();
		accountUser.setUserName(faker.name().fullName());
		accountUser.setPassword("12345*Abcd");
		
		return accountUser;
	}
	public static Login gerarUserComSenhaInvalida() {
		
		Login accountUser = new Login();
		accountUser.setUserName(faker.name().firstName());
		accountUser.setPassword("123456");
		
		return accountUser;
	}
	public static Login gerarUserSemSenha() {
		
		Login accountUser = new Login();
		accountUser.setUserName(faker.name().firstName());
		accountUser.setPassword("");
		
		return accountUser;
	}
	public static Login gerarUserSemNome() {
		
		Login accountUser = new Login();
		accountUser.setUserName("");
		accountUser.setPassword("12345*Abcd");
		
		return accountUser;
	}
	public static Login gerarUserComCamposEmBranco() {
		
		Login accountUser = new Login();
		accountUser.setUserName("");
		accountUser.setPassword("");
		
		return accountUser;
	}

}
