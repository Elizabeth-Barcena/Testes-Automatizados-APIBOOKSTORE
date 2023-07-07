package services;
import io.restassured.response.Response;


import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseRest {
		private RequestSpecification requestSpec;
		
	/*	public BaseRest() {
			requestSpec = getSpecBuilder().build();
		}*/
		
		public Response getComId(String endpoint,String id, Object token) {
			return 
			 given().
				contentType(ContentType.JSON).
				header("Authorization", "Bearer " + token).
			when().
				get(endpoint + "/" + id).
			then().
				//log().body().
				//log().status().
				extract().
				response();
		}
		public Response get(String endpoint, Object token) {
			return 
			 given().
				contentType(ContentType.JSON).
				header("Authorization", "Bearer " + token).
			when().
				get(endpoint).
			then().
			//	log().body().
				//log().status().
				extract().
				response();
		}
		public Response getSemToken(String endpoint) {
			return 
			given().
			when().
				get(endpoint).
			then().
				//log().body().
				//log().status().
				extract().
				response();
		}
		public Response post(String endpoint, Object payload) {
			return
			 given().	 
			 	contentType(ContentType.JSON).
				body(payload). 
			when().
				post(endpoint).
			then().
				//log().body().
				//log().status().
				//log().all().
			extract().
			response();			
			
		}
		public Response postBook(String endpoint, Object payload, String token) {
			return
			 given().	 
			 	contentType(ContentType.JSON).
				header("Authorization", "Bearer " + token).
				body(payload). 
			when().
				post(endpoint).
			then().
				//log().body().
				//log().status().
				//log().all().
			extract().
			response();			
			
		}
		
		public Response delete(String endpoint, Object id, Object token) {
			return
		
			given().
			header("Authorization", "Bearer " + token).
		    when().
		    	delete(endpoint + "/" + id).
		    then().
		  //  log().body().
			//log().status().
		    extract().
			response();
			
		}
		public Response deleteBook(String endpoint, Object payload, String token) {
			return
			 given().	 
			 	contentType(ContentType.JSON).
				header("Authorization", "Bearer " + token).
				body(payload). 
			when().
				delete(endpoint).
			then().
				//log().body().
				//log().status().
				//log().all().
			extract().
			response();			
			
		}
		public Response deleteBookStore(String endpoint, Object id, Object token) {
			return
		
			given().
			header("Authorization", "Bearer " + token).
		    when().
		    	delete(endpoint + "/" + id).
		    then().
		   // log().body().
			//log().status().
		    extract().
			response();
			
		}
		public Response put(String endpoint, Object payload,String isbn, Object token) {
			return		
			 given().
			 	header("Authorization", "Bearer " + token).
				body(payload).
				contentType(ContentType.JSON).
			when().
				put(endpoint+"/"+isbn).
			then().
			//	log().body().
				//log().status().
				extract().
				response();
		}

}


