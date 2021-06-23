package de.telekom.sea4.webserver.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import de.telekom.sea4.webserver.model.Person;

@Repository    
public interface PersonRepository extends CrudRepository<Person, Long> {
	
	//No code is necessary
	//This is needed for working Spring-Boot

}