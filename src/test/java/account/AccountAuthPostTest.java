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
@Epic("Endpoint: Account/V1/Authorized --- verbo POST")
public class AccountAuthPostTest extends BaseTest {
	public String idUser;
	public String token2;
	public String resposta;

	@Test
	@Description("Cria e loga usuario e verifica a autenticação")
	public void LogarUsuarioComAuth() {
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
				 ()-> assertThat(response3.statusCode(), is(200)),
				 ()-> assertThat(response3.getBody().asString(), is("true"))
				);
	}
	@Test
	@Description(" Verificar autenticação com campo senha em branco")
	public void verificarAuthComCampoSenhaEmBranco() {
		 Login user = DynamicFactory.gerarUserSemSenha();
		 
		 Response response = accountService.postAccount(user);
		 
		 idUser = response.path("userID");
		 
		 Token token = new Token();
		 
		 Response response2 = accountService.postAccountToken(user);
		 token2 = response2.path("token");
		 token.guardarInfo(response2);
		 
		 user.setToken(token);
		 
		 Response response3 = accountService.postAccountAuth(user);
		 
		 assertAll("teste",
				 ()-> assertThat(response3.statusCode(), is(400))
				);
		
	}
	@Test
	@Description(" Verificar autenticação com campo nome em branco")
	public void verificarAuthComCampoNomeEmBranco() {
		 Login user = DynamicFactory.gerarUserSemNome();
		 
		 Response response = accountService.postAccount(user);
		 
		 idUser = response.path("userID");
		 
		 Token token = new Token();
		 
		 Response response2 = accountService.postAccountToken(user);
		 token2 = response2.path("token");
		 token.guardarInfo(response2);
		 
		 user.setToken(token);
		 
		 Response response3 = accountService.postAccountAuth(user);
		 
		 assertAll("teste",
				 ()-> assertThat(response3.statusCode(), is(400))
				);
		
	}
	@Test
	@Description(" Verificar autenticação com campo nome e senha em branco")
	public void verificarAuthComCamposEmBranco() {
		 Login user = DynamicFactory.gerarUserComCamposEmBranco();
		 
		 Response response = accountService.postAccount(user);
		 
		 idUser = response.path("userID");
		 
		 Token token = new Token();
		 
		 Response response2 = accountService.postAccountToken(user);
		 token2 = response2.path("token");
		 token.guardarInfo(response2);
		 
		 user.setToken(token);
		 
		 Response response3 = accountService.postAccountAuth(user);
		 
		 assertAll("teste",
				 ()-> assertThat(response3.statusCode(), is(400))
				);
		
	}
	@Test
	@Description(" Verificar autenticação com usuário que não existe")
	public void verificarAuthComUsuarioQueNaoExiste() {
		 Login user = DynamicFactory.gerarUser();
		 
		 Response response3 = accountService.postAccountAuth(user);
		 
		 assertAll("teste",
				 ()-> assertThat(response3.statusCode(), is(404))
				);
		
	}
	@Test
	@Description("Verificar autenticação com usuário sem token")
	public void VerificaAuthComUsuarioSemToken() {
		 Login user = DynamicFactory.gerarUser();
		 
		 Response response = accountService.postAccount(user);
		 
		 idUser = response.path("userID");
		 
		 Response response3 = accountService.postAccountAuth(user);
		
	 assertAll("teste",
				 ()-> assertThat(response3.statusCode(), is(400)),
				 ()-> assertThat(response3.getBody().asString(), is("false"))
				);
	 }
	@Test
	@Description("Verificar autenticação com senha de usuario incorreta")
	public void VerificaAuthComSenhaIncorreta() {
		 Login user = DynamicFactory.gerarUser();
		 
		 Response response = accountService.postAccount(user);
		 
		 idUser = response.path("userID");
		 
		 Token token = new Token();
		 
		 Response response2 = accountService.postAccountToken(user);
		 token2 = response2.path("token");
		 token.guardarInfo(response2);
		 
		 user.setToken(token);
		 
		 user.setPassword("123456");
		
		 Response response3 = accountService.postAccountAuth(user);
		
	 assertAll("teste",
				 ()-> assertThat(response3.statusCode(), is(404))
				);
	 }
	@Test
	@Description("Verificar autenticação com nome de usuario incorreto")
	public void VerificaAuthComNomeIncorreto() {
		 Login user = DynamicFactory.gerarUser();
		 
		 Response response = accountService.postAccount(user);
		 
		 idUser = response.path("userID");
		 
		 Token token = new Token();
		 
		 Response response2 = accountService.postAccountToken(user);
		 token2 = response2.path("token");
		 token.guardarInfo(response2);
		 
		 user.setToken(token);
		 
		 user.setUserName("Abcdef");
		
		 Response response3 = accountService.postAccountAuth(user);
		
	 assertAll("teste",
				 ()-> assertThat(response3.statusCode(), is(404))
				);
	 }
	@AfterEach
	public void removerUsuario() {
		if(idUser!= null) {
			accountService.deleteAccount(idUser, token2);
		}
	}
}
