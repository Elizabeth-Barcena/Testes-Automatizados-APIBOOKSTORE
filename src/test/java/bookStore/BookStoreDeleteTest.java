package bookStore;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
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
@Epic("Endpoint: BookStore / verbo DELETE")
public class BookStoreDeleteTest extends BaseTest{
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
				 ()-> assertThat(response3.statusCode(), is(201))
				);
	}
	@Test
	@Description("Deleta todos os livros do carrinho")
	public void deletaTodosOsLivrosDoCarrinho() {
		
		Response response = bookStoreService.deleteBookStore(idUser, token2);
		assertAll("teste",
				 ()-> assertThat(response.statusCode(), is(204))
				);
	}
	@Test
	@Description("Deleta todos os livros do carrinho sem token")
	public void deletaTodosOsLivrosDoCarrinhoSemToken() {
		
		Response response = bookStoreService.deleteBookStore(idUser, null);
		assertAll("teste",
				 ()-> assertThat(response.statusCode(), is(401))
				);
	}
	@Test
	@Description("Deleta todos os livros do carrinho com id incorreto")
	public void deletaTodosOsLivrosDoCarrinhoComIdIncorreto() {
		
		Response response = bookStoreService.deleteBookStore(idUser+"123", token2);
		assertAll("teste",
				 ()-> assertThat(response.statusCode(), is(401))
				);
	}
	@Test
	@Description("Deleta um livro do carrinho")
	public void deletaUmLivroDoCarrinho() {
		
		livros.setIsbn(isbn2);
		livros.setUserId(idUser);
		
		Response response = bookStoreService.deleteBook(livros, token2);
		assertAll("teste",
				 ()-> assertThat(response.statusCode(), is(204))
				);
		
	}
	@Test
	@Description("Deleta um livro do carrinho sem token")
	public void deletaUmLivroDoCarrinhoSemToken() {
		
		livros.setIsbn(isbn2);
		livros.setUserId(idUser);
		
		Response response = bookStoreService.deleteBook(livros, null);
		assertAll("teste",
				 ()-> assertThat(response.statusCode(), is(401))
				);
		
	}
	@Test
	@Description("Deleta um livro que não esta no carrinho carrinho")
	public void deletaUmLivroQueNãoEstaNoCarrinho() {
		String livroNaoEstaNoCarrinho = collectionIsbn.retornaISBN();
		
		if(livroNaoEstaNoCarrinho == isbn2) {
			 livroNaoEstaNoCarrinho = collectionIsbn.retornaISBN();
		}
		livros.setIsbn(livroNaoEstaNoCarrinho);
		livros.setUserId(idUser);
		Response response = bookStoreService.deleteBook(livros, token2);
		assertAll("teste",
				 ()-> assertThat(response.statusCode(), is(400))
				);
		
	}
	
	
}
