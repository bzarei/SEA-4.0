package de.telekom.sea4.webserver.model;

import java.util.List;
import java.util.ArrayList;

public class Personen {
	
	private List<Person> personen = new ArrayList<Person>();

	// standard constructor
	public Personen() { }
	
	// constructor mit ArrayList
	public Personen(List<Person> all) {
		this.personen = all;
	}
	
	public List<Person> getPersonen() {
		return personen;
	}

	public void setPersonen(List<Person> personen) {
		this.personen = personen;
	}
}
