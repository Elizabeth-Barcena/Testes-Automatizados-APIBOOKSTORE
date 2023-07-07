package account;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataFactory.DynamicFactory;
import helper.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import model.Login;
import model.Token;
@Epic("Endpoint: Account/v1/User --- verbo DELETE")
public class AccountDeleteTest extends BaseTest{
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

		 assertAll("teste",
				 ()-> assertThat(response.statusCode(), is(201)),
				 ()-> assertThat(response2.statusCode(), is(200))
				);
	}
	
	@Test
	@Description("Deleta usuário")
	public void deletarUsuario() {
		Response response = accountService.deleteAccount(idUser, token2);
		assertAll("teste",
				 ()-> assertThat(response.statusCode(), is(200))
				);
	}
	@Test
	@Description("Deleta usuário com token incorreto")
	public void deletarUsuarioComtokenIncorreto() {
		Response response = accountService.deleteAccount(idUser, token2+"123");
		assertAll("teste",
				 ()-> assertThat(response.statusCode(), is(204))
				);
	}
	@Test
	@Description("Deleta usuário sem token")
	public void deletarUsuarioSemToken() {
		Response response = accountService.deleteAccount(idUser, null);
		assertAll("teste",
				 ()-> assertThat(response.statusCode(), is(204))
				);
	}
	@Test
	@Description("Deleta usuário com Id incorreto")
	public void deletarUsuarioComIdIncorreto() {
		Response response = accountService.deleteAccount(idUser + "123", token2);
		assertAll("teste",
				 ()-> assertThat(response.statusCode(), is(404))
				);
	}
}

