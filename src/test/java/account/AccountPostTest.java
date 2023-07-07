package account;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
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
@Epic("Endpoint: Account/V1/User --- verbo POST")
public class AccountPostTest extends BaseTest{
	public String idUser;
	public String token2;
	public String resposta;
	@Test
	@Description(" Cria usuario")
	public void criarUsuario() {
		 Login user = DynamicFactory.gerarUser();
		 
		 Response response = accountService.postAccount(user);
		 
		 idUser = response.path("userID");
		 
		 
		 Token token = new Token();
		 
		 Response response2 = accountService.postAccountToken(user);
		 token2 = response2.path("token");
		 token.guardarInfo(response2);
		 
		 user.setToken(token);
		 
		 accountService.postAccountAuth(user);
		 
		 assertAll("teste",
				 ()-> assertThat(response.statusCode(), is(201)),
				 ()-> assertThat(response.path("username"), equalTo(user.getUserName()))
				);
	}
	@Description(" Cria usuario com senha invalida")
	@Test
	public void criarUsuarioComSenhaInválida() {
		 Login user = DynamicFactory.gerarUserComSenhaInvalida();
		 
		 Response response = accountService.postAccount(user);
		 
		 idUser = response.path("userID");
		 
		 Token token = new Token();
		 
		 Response response2 = accountService.postAccountToken(user);
		 token2 = response2.path("token");
		 token.guardarInfo(response2);
		 
		 user.setToken(token);
		 
		 accountService.postAccountAuth(user);
		 
		 assertAll("teste",
				 ()-> assertThat(response.statusCode(), is(406))
				);
	}
	@Test
	@Description(" Cria usuario sem senha")
	public void criarUsuarioSemSenha() {
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
				 ()-> assertThat(response.statusCode(), is(406))
				);
	}
	@Test
	@Description(" Cria usuario sem nome")
	public void criarUsuarioSemNome() {
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
				 ()-> assertThat(response.statusCode(), is(406))
				);
	}
	
	
	@AfterEach
	public void removerUsuario() {
		if(idUser!= null) {
			accountService.deleteAccount(idUser, token2);
		}
	}

}
