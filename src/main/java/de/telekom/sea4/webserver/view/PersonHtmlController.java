package de.telekom.sea4.webserver.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import de.telekom.sea4.webserver.service.PersonService;

// Controller liefert html zurück
@Controller
public class PersonHtmlController {
	// sollte eigentlich aus der html-Datei eingelesen und befüllt werden:
	private static final String HTML_TEMPLATE = "\n" 
			+ "<!doctype html>\n" 
			+ "<html lang=de>\n" 
			+ "<head>\n"
			+ "<meta charset=utf-8>\n"
			+ "<link rel=\"shortcut icon\" "
			+ "type=\"image/x-icon\" "
			+ "href=\"images/favicon.ico\">"
			+ "<title>Title</title>\n" 
			+ "</head>\n" 
			+ "<body>"
			+ "<h1>size: %d</h1>"
			+ "</body>" 
			+ "</html>";
	private PersonService personService;

	@Autowired
	public PersonHtmlController(PersonService personService) {
		super();
		this.personService = personService;
	}

//	@GetMapping("/size") // URL: "http://localhost:8080/size" über diese Url wird die Methode getSize() erreicht
//	@ResponseBody // somit sagen wir dem Spring dass die Rückgabe-html String die Antwort in body 					// ist
//	public String getSize() { // Rückgabe ist html
//		return String.format(HTML_TEMPLATE, personService.size());
//	}
	
//	@GetMapping("/size2")
//	public String getSize2(Model model) {
//		model.addAttribute("size3", personService.size());
//		return "size";    // template size.html 
//	}

	@GetMapping("/count") // URL: "http://localhost:8080/count" über diese Url wird die Methode getCount() erreicht
	public String getCount(Model model) { // Rückgabe ist html
		String str = "Have fun!";
		model.addAttribute("name", str); // "name" kommt aus count.html: <h1 th:text="Hello ${name}!"></h1>
		return "count";   // template count.html
	}

	/**
	 * über http://localhost:8080/count2/?name=Hello Kiti kann man name ersetzen
	 * @param model
	 * @param name
	 * @return
	 */
	@GetMapping("/count2")
	public String getCount2(Model model,
			@RequestParam(value = "name",   
					required = false,         //false: Eingabe von name in url ist nicht unbedingt erforderlich
					defaultValue = "Germany") String str
	) {
		model.addAttribute("name", str);
		return "count";       // template count.html
	}
	
//	@GetMapping("/personen")
//	public String getPersonen(Model model) {
//		model.addAttribute("PersonenList", personService.getAll4ServerTemplate());
//		return "personen";   // template personen.html
//	}
}
