package de.telekom.sea4.webserver.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import de.telekom.sea4.webserver.model.Person;

@Repository    
public interface PersonRepository extends CrudRepository<Person, Long> {  // oder JpaRepository 
	
	//No code is necessary
	//This area is needed for working Spring-Boot
	
	// suche nach alle Personen aus einem bestimmten Ort	
	@Query(value="SELECT * FROM persons_sea4 WHERE standort=:ort",nativeQuery=true)
	Iterable<Person> selectPersonen(@Param("ort") String ort);
	
	// suche nach alle Personen mit bestimmten Nachnamen
	@Query(value="SELECT * FROM persons_sea4 WHERE nachname=:lastname",nativeQuery=true)
	Iterable<Person> selectPersonenByName(@Param("lastname") String lastname);	 
}