package de.telekom.sea4.webserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.telekom.sea4.webserver.model.Person;
import de.telekom.sea4.webserver.repository.PersonRepository;


@Service
public class PersonService {

	private PersonRepository personRepository;

	@Autowired
	public PersonService(PersonRepository personRepository) {
		super();
		this.personRepository = personRepository;
	}
	
	public long size() {
		System.out.println("size = " + personRepository.count());
		return personRepository.count();
	}
	
//	public Iterable<T> getAll() {
//		return personRepository.findAll();
//	}
//	
//	public List<Person> getAll4ServerTemplate() {
//		return personRepository.getAll4ServerTemplate();
//	}
//	
//	public Person get(Long id) {
//		return new Person(1, "Herr", "Max", "Mustermann", "10-12-1980", "Bonn", "Max.Mustermann@telekom.de");
//	  	return personRepository.findById(); 
//	}
//	
	public Person add(Person person) {
		System.out.println("Person wurde angelegt.");
		personRepository.save(person);
		return person;
	}

	public void remove(Long id) {
		personRepository.deleteById(id);
	}
	
	public Person update(Person p) {
		return personRepository.save(p);
	}

	public void removeAll() {
		personRepository.deleteAll();		
	}
}
