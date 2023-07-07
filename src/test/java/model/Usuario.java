package model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
public class Usuario {
	public String userId;
	
	List<Isbn> collectionOfIsbns = new ArrayList<Isbn>();
	
	public void adicionaLivro(Isbn isbn) {
		collectionOfIsbns.add(isbn);
	}
	

}
