package de.telekom.sea4.webserver.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
		logger.info(String.format("PersonService instanziiert! %s",this.toString()));
		logger.info(String.format("PersonRepository durch Annotation instanziiert! %s",personRepository.toString()));
		this.personRepository = personRepository;
	}
	
	public long size() {
		logger.info(String.format("size: %s",personRepository.count()));
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
	/**
	 * es wird geprüft ob vorname, nachname, Geburtsdatum, Standort und email nicht identisch sind.
	 * sollte der fall sein wird eine Neuanlage der Person abgewisen. Sonst wird neue Person angelegt.
	 * @param person
	 * @return
	 */
	public Person add(Person person) {
		if (person.getId() == null) {
			person.setId(-1L);   // set Id to default value
		} 
		Personen persons = getAll();
		List<Person> personen = persons.getPersonen();	
		for (int i = 0; i < persons.getPersonen().size(); i++) {	
			if ((personen.get(i).toString()) == (person.toString())) {
				logger.info(String.format("Person %s %s %s %s %s existiert bereits mit ID %s. Eine Neuanlage ist nicht möglich!", 
						person.getVorname(), person.getNachname(),person.getBirthDate(), person.getStandort(),person.getEmail(),personen.get(i).getId()));
				return null;
			}	
		}
		logger.info(String.format("Person mit ID %s ist neu angelegt: %s %s %s %s %s",person.getId(), 
				person.getVorname(), person.getNachname(),person.getBirthDate(), person.getStandort(),person.getEmail()));
		return personRepository.save(person);
	}

	public void remove(Long id) {
		personRepository.deleteById(id);
		logger.info(String.format("Person mit ID %s ist gelöscht.",id));
	}
	
	public Person update(Person person) {
		logger.info(String.format("Person mit ID %s ist modifiziert: %s %s %s %s %s",person.getId(), 
				person.getVorname(), person.getNachname(),person.getBirthDate(), person.getStandort(),person.getEmail()));
		return personRepository.save(person);
	}

	public void removeAll() {
		personRepository.deleteAll();		
	}
	
	public Personen selectPersonen(String ort) {
		Personen ps = new Personen();
		for (Person p : personRepository.selectPersonen(ort)) {
			ps.getPersonen().add(p);
		}
		return ps;
	}
	
	public Personen searchPersonenByName(String lastname) {
		Personen ps = new Personen();
		for (Person p : personRepository.selectPersonenByName(lastname)) {
			ps.getPersonen().add(p);
		}
		return ps;
	}
}
