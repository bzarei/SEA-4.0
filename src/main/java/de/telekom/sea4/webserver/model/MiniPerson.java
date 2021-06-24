package de.telekom.sea4.webserver.model;

//import javax.persistence.Entity;

//@Entity
public class MiniPerson {

	private String vorname;
	private String nachname;
	private String standort;
	
	public MiniPerson() {  }
	
	public MiniPerson(String vorname, String nachname, String standort) {
		super();
		this.vorname = vorname;
		this.nachname = nachname;
		this.standort = standort;
	}
		
	public String getStandort() {
		return standort;
	}

	public void setStandort(String standort) {
		this.standort = standort;
	}

	public String getVorname() {
		return vorname;
	}
	
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	
	public String getNachname() {
		return nachname;
	}
	
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}	
}
