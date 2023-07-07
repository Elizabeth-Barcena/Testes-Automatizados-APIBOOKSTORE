package model;

import io.restassured.response.Response;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Token {
	public String token;
	public String expires;
	public String status;
	public String result;
	
	public void guardarInfo(Response response) {
		token = response.path("token");
		expires = response.path("expires");
		status = response.path("status");
		result = response.path("result");
		 
	}
}
