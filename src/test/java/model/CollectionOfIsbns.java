package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionOfIsbns {
	public String isbn; 
	
	 List<String> list = new ArrayList<String>();
	 
	 public String retornaISBN() {
		 list.add("9781449325862");
		 list.add("9781449331818");
		 list.add("9781449337711");
		 list.add("9781449365035");
		 list.add("9781491904244");
		 list.add("9781491950296");
		 list.add("9781593275846");
		 list.add("9781593277574");
		 
		Random rand = new Random();
		return list.get(rand.nextInt(list.size()));

	 }
	 
}
