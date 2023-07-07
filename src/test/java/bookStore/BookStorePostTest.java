package bookStore;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataFactory.DynamicFactory;
import helper.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import model.Login;
import model.Token;
@Epic("Endpoint: BookStore / verbo POST")
public class BookStorePostTest extends BaseTest{
	public String idUser;
	public String token2;
	public String resposta;
	public String isbn2;
	
	@BeforeEach
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
	@Test
	@Description("Adiciona livro no carrinho")
	public void adicionarLivroNoCarrinho() {
		isbn2 = collectionIsbn.retornaISBN();
		isbn.setIsbn(isbn2);
		
		listISBN.setUserId(idUser);
		listISBN.adicionaLivro(isbn);
		
		
		Response response = bookStoreService.postBookStore(listISBN, token2);
		
		assertAll("teste",
				 ()-> assertThat(response.statusCode(), is(201))
				);
	}
	@Test
	@Description("Adiciona livro no carrinho sem token")
	public void adicionarLivroNoCarrinhoSemToken() {
		isbn2 = collectionIsbn.retornaISBN();
		isbn.setIsbn(isbn2);
		
		listISBN.setUserId(idUser);
		listISBN.adicionaLivro(isbn);
		
		Response response = bookStoreService.postBookStore(listISBN, "");
		
		assertAll("teste",
				 ()-> assertThat(response.statusCode(), is(401))
				);
	}
	@Test
	@Description("Adiciona livro que não existe no carrinho")
	public void adicionarLivroQueNaoExisteNoCarrinho() {
		isbn2 = "nao existe";
		isbn.setIsbn(isbn2);
		
		listISBN.setUserId(idUser);
		listISBN.adicionaLivro(isbn);
		
		Response response = bookStoreService.postBookStore(listISBN, token2);
		
		assertAll("teste",
				 ()-> assertThat(response.statusCode(), is(400))
				);
	}
	@Test
	@Description("Adiciona livro com id incorreto no carrinho")
	public void adicionarLivroComIdIncorreto() {
		isbn2 = collectionIsbn.retornaISBN();
		isbn.setIsbn(isbn2);
		
		listISBN.setUserId(idUser+"123");
		listISBN.adicionaLivro(isbn);
		
		Response response = bookStoreService.postBookStore(listISBN, token2);
		
		assertAll("teste",
				 ()-> assertThat(response.statusCode(), is(401))
				);
	}
	@Test
	@Description("Adiciona 2 livros iguais no carrinho")
	public void adicionar2LivrosIguaisSeparadamente() {
		isbn2 = collectionIsbn.retornaISBN();
		isbn.setIsbn(isbn2);
		
		listISBN.setUserId(idUser);
		listISBN.adicionaLivro(isbn);
		
		Response response = bookStoreService.postBookStore(listISBN, token2);
		Response response2 = bookStoreService.postBookStore(listISBN, token2);
		assertAll("teste",
				 ()-> assertThat(response.statusCode(), is(201)),
				 ()-> assertThat(response2.statusCode(), is(201))
				);
	}
	@Test
	@Description("Adiciona 2 livros iguais ao mesmo tempo")
	public void adicionar2LivrosIguaisAoMesmoTempo() {
		isbn2 = collectionIsbn.retornaISBN();
		isbn.setIsbn(isbn2);
		
		listISBN.setUserId(idUser);
		listISBN.adicionaLivro(isbn);
		listISBN.setUserId(idUser);
		listISBN.adicionaLivro(isbn);
		
		Response response = bookStoreService.postBookStore(listISBN, token2);

		assertAll("teste",
				 ()-> assertThat(response.statusCode(), is(201))
				);
	}
	@AfterEach
	public void removerUsuario() {
		if(idUser!= null) {
			accountService.deleteAccount(idUser, token2);
		}
	}

}
