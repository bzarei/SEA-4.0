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
		
//	@Query(value="SELECT vorname, nachname, standort FROM persons_sea4 WHERE standort = ?1",nativeQuery=true)
//	public Iterable<MiniPerson> selectMiniPerson(String searchExpression);
	
	@Query(value="SELECT * FROM persons_sea4 WHERE standort=:ort",nativeQuery=true )
//	@Query(value="SELECT * FROM persons_sea4",nativeQuery=true ) // suche nach alle Personen 
	Iterable<Person> selectPersonen(@Param("ort") String ort);	 // suche nach alle Personen aus einem bestimmten Ort
}