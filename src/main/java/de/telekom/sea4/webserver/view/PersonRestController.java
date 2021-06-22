package de.telekom.sea4.webserver.view;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import de.telekom.sea4.webserver.model.Person;
import de.telekom.sea4.webserver.model.Personen;
import de.telekom.sea4.webserver.model.Size;
import de.telekom.sea4.webserver.service.PersonService;

/** mit dieser Annotation braucht man @ResponseBody nicht mehr. 
 *	RestController liefert json zurück.
 */
@RestController 
public class PersonRestController {

	private PersonService personService;
	
	@Autowired
	public PersonRestController(PersonService personService) {
		super();
		this.personService = personService;
	}
	
	/**
	 * @see <a href="http://localhost:8080/json/persons/all">http://localhost:8080/json/persons/all</a>
	 */
	@GetMapping("/json/persons/all")  
	public Personen getAllPersons() {  	
		return personService.getAll();
	}
	
	/**
	 * @see <a href="http://localhost:8080/json/persons/size">http://localhost:8080/json/persons/size</a>
	 * @return
	 */
	@GetMapping("/json/persons/size")  
	public Size getSize() {
		return new Size(personService.size());
	}
	
	/**
	 * @see <a href="http://localhost:8080/json/person/{id}">http://localhost:8080/json/person/{id}</a>
	 * @return
	 */
	@GetMapping("/json/person/{id}")  
	public ResponseEntity<Person> getPerson(@PathVariable("id") Long id) {
		Optional<Person> op = personService.get(id);
		if (op.isEmpty()) {
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND); 
		} else {
			return new ResponseEntity<Person>(op.get(),HttpStatus.OK );
		  }
	}
	
	/**
	 * @see <a href="http://localhost:8080/json/person">http://localhost:8080/json/person</a>
	 * @return
	 */
	@PostMapping("/json/person")  
	public Person addPerson(@RequestBody Person person) {
		return personService.add(person);
	}
	
	/**
	 * @see <a href="http://localhost:8080/json/person/{id}">http://localhost:8080/json/person/{id}</a>
	 * @return
	 */
	@DeleteMapping("/json/person/{id}")  
	public void removePerson(@PathVariable("id") int id) {
		personService.remove((long) id);
	}
	
	@DeleteMapping("/json/person/all")  
	public void removeAllPersons() {
		personService.removeAll();
	}
	
	/**
	 * @see <a href="http://localhost:8080/json/person/{id}">http://localhost:8080/json/person/{id}</a>
	 * @return
	 */
	@PutMapping("/json/person/{id}")  
	public Person updatePerson(@PathVariable("id") Long id, @RequestBody Person p) {
		return personService.update(p);
	}
	
}
