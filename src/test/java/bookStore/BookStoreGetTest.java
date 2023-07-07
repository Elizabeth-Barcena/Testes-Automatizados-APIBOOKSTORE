package bookStore;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.Test;

import helper.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
@Epic("Endpoint: BookStore / verbo GET")
public class BookStoreGetTest extends BaseTest{
	
	public String idUser;
	public String token2;
	public String resposta;
	public String isbn;
	
	
	@Test
	@Description("Ver todos os livros")
	public void verTodosOsLivros() {
		Response response = bookStoreService.getBookStore();
		 
		 assertAll("teste",
				 ()-> assertThat(response.statusCode(), is(200))
				);
	}
	
	@Test
	@Description("Ver todos os livros pelo isbn")
	public void verLivroPorIsbn() {
		isbn = collectionIsbn.retornaISBN();
		
		Response response = bookStoreService.getBookStoreComISBN(isbn,token2);
		 
		 assertAll("teste ver usuario",
				 ()-> assertThat(response.statusCode(), is(200))
				);
	}
	@Test
	@Description("Ver todos os livros com isbn incorreto")
	public void verLivroPorIsbnIncorreto() {
		isbn = collectionIsbn.retornaISBN();
		
		Response response = bookStoreService.getBookStoreComISBN(isbn+"123",token2);
		 
		 assertAll("teste ver usuario",
				 ()-> assertThat(response.statusCode(), is(400))
				);
	}
	
}
