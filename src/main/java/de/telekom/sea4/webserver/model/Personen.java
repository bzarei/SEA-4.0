package de.telekom.sea4.webserver.model;

import java.util.List;
import java.util.ArrayList;

public class Personen {
	
	private List<Person> personen = new ArrayList<Person>();

	public List<Person> getPersonen() {
		return personen;
	}
	
	public Personen(List<Person> all) {
		this.personen = all;
	}

	public void setPersonen(List<Person> personen) {
		this.personen = personen;
	}
	
	public List<Person> getPersones() {
		return personen;
	}

}
