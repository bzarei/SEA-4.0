package de.telekom.sea4.webserver.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.telekom.sea4.webserver.model.Person;
import de.telekom.sea4.webserver.model.Personen;
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
	
	public Personen getAll() {
		Personen ps = new Personen();
		for (Person p:personRepository.findAll()) {
			ps.getPersonen().add(p);
		}
		return ps;
	}
	
	public Optional<Person> get(Long id) {
	  	return personRepository.findById(id); 
	}
	
	public Person add(Person person) {
		if (person.getId() == null) {
			person.setId(-1L);   // set Id to default value
		} 
		System.out.println("Person wird angelegt.");
		return personRepository.save(person);
	}

	public void remove(Long id) {
		personRepository.deleteById(id);
		System.out.println("Person ist gelöscht.");
	}
	
	public Person update(Person p) {
		System.out.println("Person wird aktualisiert.");
		return personRepository.save(p);
	}

	public void removeAll() {
		personRepository.deleteAll();		
	}
}
