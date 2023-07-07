package services;

import static constants.Endpoints.*;

import io.restassured.response.Response;

public class BookStoreService extends BaseRest {
	
	public Response getBookStore() {
		return getSemToken(BOOKSTORES);
	}
	public Response getBookStoreComISBN( String isbn, String token) {
		return getComId(BOOKSTORE,"?ISBN=" + isbn, token);
	}
	public Response postBookStore( Object payload, String token) {
		return postBook(BOOKSTORES, payload, token);
	}
	public Response deleteBookStore( String id, String token) {
		return deleteBookStore(BOOKSTORES, "?UserId="+id, token);
	}
	public Response deleteBook( Object payload, String token) {
		return deleteBook(BOOKSTORE, payload, token);
	}
	public Response putBook(Object payload,String isbn, Object token) {
		return put(BOOKSTORES, payload,isbn, token);
	}
}
