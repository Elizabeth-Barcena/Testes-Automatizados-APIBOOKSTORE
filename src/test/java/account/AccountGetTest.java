package account;

import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.AfterEach;

import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import dataFactory.DynamicFactory;
import helper.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import model.Login;
import model.Token;

@Epic("Endpoint: Account/V1/User --- verbo GET")
public class AccountGetTest extends BaseTest{
	public String idUser;
	public String token2;
	public String resposta;
	
	@BeforeEach
	public void criarUsuario() {
		 Login user = DynamicFactory.gerarUser();
		 
		 Response response = accountService.postAccount(user);
		 
		 idUser = response.path("userID");
		 
		 Token token = new Token();
		 
		 Response response2 = accountService.postAccountToken(user);
		 token2 = response2.path("token");
		 token.guardarInfo(response2);
		 user.setToken(token);
		 
		 Response response3 = accountService.postAccountAuth(user);
		 
		 assertAll("teste",
				 ()-> assertThat(response.statusCode(), is(201)),
				 ()-> assertThat(response2.statusCode(), is(200)),
				 ()-> assertThat(response3.statusCode(), is(200))
				);
	}
	@Test
	public void verUsuario() {
		 Response response = accountService.getAccount(idUser, token2);
		 
		 assertAll("teste ver usuario",
				 ()-> assertThat(response.statusCode(), is(200))
				);
		 
	}
	@Test
	@Description("Ver usuário sem autorização")
	public void verUsuarioSemAutorização() {
		 Response response = accountService.getAccount(idUser, null);
		 
		 assertAll("teste ver usuario",
				 ()-> assertThat(response.statusCode(), is(401))
				);
		 
	}
	@Test
	@Description("Ver usuário com token incorreto")
	public void verUsuarioComTokenIncorreto() {
		 Response response = accountService.getAccount(idUser, token2 + "123");
		 
		 assertAll("teste ver usuario",
				 ()-> assertThat(response.statusCode(), is(401))
				);
		 
	}
	@AfterEach
	public void removerUsuario() {
		if(idUser!= null) {
			accountService.deleteAccount(idUser, token2);
		}
	}

}
