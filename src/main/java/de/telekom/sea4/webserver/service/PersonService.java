package de.telekom.sea4.webserver.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.telekom.sea4.webserver.model.MiniPerson;
import de.telekom.sea4.webserver.model.Person;
import de.telekom.sea4.webserver.model.Personen;
import de.telekom.sea4.webserver.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class PersonService {

	private PersonRepository personRepository;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public PersonService(PersonRepository personRepository) {
		super();
		logger.info("MyLogging: PersonService instanziiert! " + this.getClass().toString());
		logger.info("MyLogging: PersonRepository durch Annotation instanziiert! " + personRepository.getClass().toString());
		this.personRepository = personRepository;
	}
	
	public long size() {
		logger.info("size: " + personRepository.count());
		return personRepository.count();
	}
	
	public Personen getAll() {
		Personen persons = new Personen();
		for (Person p:personRepository.findAll()) {
			persons.getPersonen().add(p);
		}
		return persons;
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
		logger.info("Person ist gelöscht."+ this.toString());
	}
	
	public Person update(Person person) {
		logger.info("Person wird aktualisiert."+ this.toString());
		return personRepository.save(person);
	}

	public void removeAll() {
		personRepository.deleteAll();		
	}
	
//	public Iterable<MiniPerson> searchByStandOrt(String ort) {
//		logger.info("Logging für searchByStandort! " + ort);
//		return personRepository.selectMiniPerson(ort);	
//	}
	
	public Personen selectPersonen(String ort) {
		Personen ps = new Personen();
		for (Person p : personRepository.selectPersonen(ort)) {
			ps.getPersonen().add(p);
		}
		return ps;
	}
}
