package helper;

import org.junit.jupiter.api.BeforeEach;

import dataFactory.DynamicFactory;
import model.CollectionOfIsbns;
import model.Isbn;
import model.Livros;
import model.Usuario;
import services.AccountService;
import services.BaseRest;
import services.BookStoreService;

public class BaseTest {
	protected DynamicFactory dynamicFactory;
	protected BaseRest base;
	protected AccountService accountService;
	protected BookStoreService bookStoreService;
	protected CollectionOfIsbns collectionIsbn;
	protected Usuario listISBN;
	protected Isbn isbn;
	protected Livros livros;
	@BeforeEach
	public void CollectionOfIsbn() {
		dynamicFactory = new DynamicFactory();
		base = new BaseRest();
		accountService = new AccountService();
		bookStoreService = new BookStoreService();
		collectionIsbn = new CollectionOfIsbns();
		listISBN = new Usuario();
		isbn = new Isbn();
		livros = new Livros();
	}

}
