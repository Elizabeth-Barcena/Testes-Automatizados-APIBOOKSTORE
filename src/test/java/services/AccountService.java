package services;

import io.restassured.response.Response;

import static constants.Endpoints.*;


public class AccountService extends BaseRest{
	
	
	public Response getAccount(String id, String token) {
		return getComId(ACCOUNTUSER, id, token);
	}
	public Response postAccount(Object usuario) {
		return post(ACCOUNTUSER, usuario);
	}
	public Response postAccountToken(Object usuario) {
		return  post(ACCOUNTTOKEN, usuario);
	}
	public Response postAccountAuth(Object usuario) {
		return  post(ACCOUNTAUTHO, usuario);
	}
	public Response deleteAccount(Object id, Object token) {
		return  delete(ACCOUNTUSER, id, token);
	}
}

