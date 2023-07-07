package account;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import dataFactory.DynamicFactory;
import helper.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import model.Login;
import model.Token;
@Epic("Endpoint: Account/V1/GenerateToken --- verbo POST")
public class AccountGerarTokenPostTest extends BaseTest {
	public String idUser;
	public String token2;
	public String resposta;
	
	@Test
	@Description("Gera token")
	public void gerarToken() {
		 Login user = DynamicFactory.gerarUser();
		 
		 Response response = accountService.postAccount(user);
		 
		 idUser = response.path("userID");

		 Token token = new Token();
		 
		 Response response2 = accountService.postAccountToken(user);
		 token2 = response2.path("token");
		 token.guardarInfo(response2);
		
		 assertAll("teste",
				 ()-> assertThat(response2.statusCode(), is(200))
				);
	}
	@Test
	@Description(" Gera token com campo senha em branco")
	public void gerarTokenComCampoSenhaEmBranco() {
		 Login user = DynamicFactory.gerarUserSemSenha();
		 
		 Response response = accountService.postAccount(user);
		 
		 idUser = response.path("userID");
		 
		 Token token = new Token();
		 
		 Response response2 = accountService.postAccountToken(user);
		 token2 = response2.path("token");
		 token.guardarInfo(response2);
		 
		 user.setToken(token);
		 
		 accountService.postAccountAuth(user);
		 
		 assertAll("teste",
				 ()-> assertThat(response2.statusCode(), is(400))
				);
		
	}
	@Test
	@Description("Gera token com campo nome em branco")
	public void gerarTokenComCampoNomeEmBranco() {
		 Login user = DynamicFactory.gerarUserSemNome();
		 
		 Response response = accountService.postAccount(user);
		 
		 idUser = response.path("userID");
		 
		 Token token = new Token();
		 
		 Response response2 = accountService.postAccountToken(user);
		 token2 = response2.path("token");
		 token.guardarInfo(response2);
		 
		 user.setToken(token);
		 
		 accountService.postAccountAuth(user);
		 
		 assertAll("teste",
				 ()-> assertThat(response2.statusCode(), is(400))
				);
		
	}
	@Test
	@Description("Gera token com usuario que nao existe")
	public void gerarTokenComUsuarioQueNaoExiste() {
		
		 Login user = DynamicFactory.gerarUser();
		 
		 Token token = new Token();
		 
		 Response response2 = accountService.postAccountToken(user);
		 token2 = response2.path("token");
		 token.guardarInfo(response2);
		 
		 user.setToken(token);
		 
		 assertAll("teste",
				 ()-> assertThat(response2.statusCode(), is(400))
				);
		
	}
	@Test
	@Description("Gera token com nome e senha em branco")
	public void gerarTokenComTodosOsCamposEmBranco() {
		
		 Login user = DynamicFactory. gerarUserComCamposEmBranco();
		 
		 Response response = accountService.postAccount(user);
		 
		 idUser = response.path("userID");
		 
		 Token token = new Token();
		 
		 Response response2 = accountService.postAccountToken(user);
		 token2 = response2.path("token");
		 token.guardarInfo(response2);
		 
		 user.setToken(token);
		 
		 assertAll("teste",
				 ()-> assertThat(response2.statusCode(), is(400))
				);
		
	}
	@Test
	@Description("Gerar token com senha de usuario incorreta")
	public void gerarTokenComSenhaIncorreta() {
		 Login user = DynamicFactory.gerarUser();
		 
		 Response response = accountService.postAccount(user);
		 
		 idUser = response.path("userID");
		 
		 user.setPassword("123456");
		 
		 Token token = new Token();
		 
		 Response response2 = accountService.postAccountToken(user);
		 token2 = response2.path("token");
		 token.guardarInfo(response2);
		 
		 user.setToken(token);

	 assertAll("teste",
				 ()-> assertThat(response2.statusCode(), is(400))
				);
	 }
	@Test
	@Description("Gerar token com nome de usuario incorreto")
	public void gerarTokenComNomeIncorreto() {
		 Login user = DynamicFactory.gerarUser();
		
		 Response response = accountService.postAccount(user);
		 
		 idUser = response.path("userID");
		 
		 user.setUserName("Abcdef");
		
		 Token token = new Token();
		 
		 Response response2 = accountService.postAccountToken(user);
		 token2 = response2.path("token");
		 token.guardarInfo(response2);
		 
		 user.setToken(token);
		
	 assertAll("teste",
				 ()-> assertThat(response2.statusCode(), is(400))
				);
	 }
	
	@AfterEach
	public void removerUsuario() {
		if(idUser!= null) {
			accountService.deleteAccount(idUser, token2);
		}
	}

}
