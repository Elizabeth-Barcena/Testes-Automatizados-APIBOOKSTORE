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
import model.Livros;
import model.Login;
import model.Token;
@Epic("Endpoint: BookStore / verbo PUT")
public class BookStorePutTest extends BaseTest{
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
		 
		 isbn2 = collectionIsbn.retornaISBN();
		 isbn.setIsbn(isbn2);
			
		 listISBN.setUserId(idUser);
		 listISBN.adicionaLivro(isbn);
			
		 Response response3 = bookStoreService.postBookStore(listISBN, token2);
		 
		 assertAll("teste",
				 ()-> assertThat(response.statusCode(), is(201)),
				 ()-> assertThat(response.path("username"), equalTo(user.getUserName())),
				 ()-> assertThat(response2.statusCode(), is(200)),
				 ()-> assertThat(response3.statusCode(), is(201))
				);
	}
	@Test
	@Description("Modificar carrinho")
	public void modificarCarrinho() {
		Livros livro = new Livros();
		
		String codigoLivro = collectionIsbn.retornaISBN();
		if(isbn2 == codigoLivro) {
			 codigoLivro = collectionIsbn.retornaISBN();
		}
		isbn.setIsbn(codigoLivro);
		
		livro.setUserId(idUser);
		livro.setIsbn(codigoLivro);
		
		Response response = bookStoreService.putBook(livro,isbn2, token2);
		
		assertAll("teste",
				 ()-> assertThat(response.statusCode(), is(200))
				);
	}
	@Test
	@Description("Modificar carrinho com livro que não existe")
	public void modificarCarrinhoComLivroQueNaoExiste() {
		Livros livro = new Livros();
		
		String codigoLivro = "naoexiste123";
		
		isbn.setIsbn(codigoLivro);
		livro.setUserId(idUser);
		livro.setIsbn(codigoLivro);
		
		Response response = bookStoreService.putBook(livro,isbn2, token2);
		
		assertAll("teste",
				 ()-> assertThat(response.statusCode(), is(400))
				);
	}
	@Test
	@Description("Modificar carrinho sem o token")
	public void modificarCarrinhoSemToken() {
		Livros livro = new Livros();
		
		String codigoLivro = collectionIsbn.retornaISBN();
		if(isbn2 == codigoLivro) {
			 codigoLivro = collectionIsbn.retornaISBN();
		}
		isbn.setIsbn(codigoLivro);
		
		livro.setUserId(idUser);
		livro.setIsbn(codigoLivro);

		Response response = bookStoreService.putBook(livro,isbn2, "");
		
		assertAll("teste",
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
