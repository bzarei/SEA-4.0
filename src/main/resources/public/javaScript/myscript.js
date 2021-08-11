/* suche nach eindeutige id=id4711 in index.html
 * und ersetze durch "Erleben, was verbindet" */
var para = document.getElementById("id4711");
para.textContent = "Erleben, was verbindet!";

function getPictoImg(anrede) {
	switch (anrede) {
		case "Herr":
			return 'images/man.png';
		case "Frau":
			return 'images/frau.png';
		case "Sonstiges":
			return 'images/unbekannt.png';
		default:
			return 'images/unbekannt.png';
	}
}

function getJson(serverResponse) { 	// serverResponse beinhaltet json mit allen kommunikations-metadaten
	return serverResponse.json();	// .json ist der reine json-inhalt
}

// Submit: Daten aus dem Browser einlesen und an den Server senden (method: POST)
function oninputclick(event) {   // bei event-click
	event.preventDefault();      // verhindert dass das event von Browser verwendet wird (verhindert GET-Request)
	var anrede = document.getElementById("id001").value;
	var vorname = document.getElementById("id002").value;
	var nachname = document.getElementById("id003").value;
	var date = document.getElementById("id004").value;
	var ort = document.getElementById("id005").value;
	var email = document.getElementById("id006").value;
	var jsonDataString = `{"anrede":"${anrede}","vorname":"${vorname}","nachname":"${nachname}","birthDate":"${date}","standort":"${ort}","email":"${email}"}`;
		
	fetch("/json/person", {
		method: 'POST',
		body: jsonDataString,
		headers: { 'Content-Type': 'application/json' } 		    
	});	
	resetById(idform);
	onRefreshClick();
}

// Update: ID und Daten (neu) dazu aus dem Browser einlesen und an den Server senden (method: PUT)
function onUpdateClick(event) {
	event.preventDefault();
	document.getElementById("id017").classList.remove("hideit");  // Feld-Eigabe ID einblenden
	document.getElementById("id010").classList.add("hideit");     // Submit ausblenden 
	document.getElementById("idblink").classList.add("hideit");  
	var id = document.getElementById("id000").value;
	var anrede = document.getElementById("id001").value;
	var vorname = document.getElementById("id002").value;
	var nachname = document.getElementById("id003").value;
	var date = document.getElementById("id004").value;
	var ort = document.getElementById("id005").value;
	var email = document.getElementById("id006").value;
	var version = document.getElementById("id018").value;
	var jsonDataString = `{"id":"${id}","anrede":"${anrede}","vorname":"${vorname}","nachname":"${nachname}","birthDate":"${date}","standort":"${ort}","email":"${email}","version":"${version}"}`;	
		
	fetch(`/json/person/${id}`, {
		method: 'PUT',
		body: jsonDataString,
		headers: { 'Content-Type': 'application/json' } 		    
	});	
}

// Delete: ID aus dem Browser einlesen und an den Server zum Löschen routen
function onDeleteClick(event) {   
	event.preventDefault();
	document.getElementById("id017").classList.remove("hideit");  // Feld-Eigabe ID einblenden
	document.getElementById("id010").classList.add("hideit");     // Submit ausblenden
	document.getElementById("idblink").classList.add("hideit"); 
	var id = document.getElementById("id000").value;
		fetch(`/json/person/${id}`, {
			method: 'DELETE'
		});
}

function getOnePersonAndPackInHtml(person) {
	if (person.id != undefined) {    // 405:  Method Not Allowed
		document.getElementById("id001").value = person.anrede;
		document.getElementById("id002").value = person.vorname;
		document.getElementById("id003").value = person.nachname;
		document.getElementById("id004").value = person.birthDate;
		document.getElementById("id005").value = person.standort;
		document.getElementById("id006").value = person.email;
		document.getElementById("id000").value = person.id;
		document.getElementById("id018").value = person.version;
	}
	else { resetById(idform); }	
}

// Search: suche nach Daten zu einer ID und in Browser anzeigen
function onSearchClick(event) {
	event.preventDefault();
	document.getElementById("id017").classList.remove("hideit");  // Feld-Eigabe ID einblenden
	document.getElementById("id010").classList.add("hideit");     // Submit ausblenden
	document.getElementById("idblink").classList.add("hideit"); 
	var id = document.getElementById("id000").value;
		fetch(`/json/person/${id}`, {
			method: 'GET',  // Optional
		}) 
		.then(getJson)
		.then(getOnePersonAndPackInHtml); 
}

// Delete all: Alle Datensätze aus DB werden gelöscht!
function onDeleteAllClick(event) {
	event.preventDefault();
	var check = confirm('Wollen Sie alle Teilnehmer dieser Liste wirklich endgültig löschen?'); 
	if (check == false) {
		history.back();
	} else {
		alert("Alle Datensätze werden aus DB endgueltig gelöscht!");
		fetch("/json/person/all", {
			method: 'DELETE'
		});
	  }	
}

/**
* Json wird vom Server in Browser ausgegeben
* hier wird geprüft, ob Liste der Teilnehmern noch leer ist:
* wenn ja, wird table-header anhand remove ausgeblendet
* (class .hideit in mycss.css)
* und wenn mindestens eine Person submitet ist soll die Tabelle nach dem Refresh mit header angezeigt werden.    
*/
function getAllPersonsFromJsonUndPackInHTMLForTable(myjson) {
	var i = 0;
	var t_header = document.getElementById("thid001");
	var t_body = document.getElementById("tbid001");

	if (myjson.personen.length == 0) {	
		t_header.classList.add("hideit");     // table-header unsichtbar      
	} 
	else {
		t_header.classList.remove("hideit");  // table-header sichtbar
	}		
	
	// table wird neu gebaut
	for (var laufvariable of myjson.personen) {
		t_body.insertAdjacentHTML("beforeend",
			"<tr>"
		    	+ `<td> ${++i} </td>` // id automatisch vergeben; Neu: Id wird im Browser gelesen und gespeichert
				+ "<td><img src='" + getPictoImg(laufvariable.anrede)+"'+ width=25px height=25px></td>"
				+ `<td>${laufvariable.id}</td>`
				+ `<td>${laufvariable.anrede}</td>`
				+ `<td>${laufvariable.vorname}</td>`
				+ `<td>${laufvariable.nachname}</td>`
				+ `<td>${laufvariable.birthDate}</td>`
				+ `<td>${laufvariable.standort}</td>`
				+ `<td>${laufvariable.email}</td>`
//				+ `<td><img id='edit${laufvariable.id}'src='images/edit.png' onclick='onEditClick("row${i}", ${laufvariable.id}, ${i}, ${laufvariable.version})' title='Bearbeiten'></td>`
//			 	+ `<td><img id='delete${laufvariable.id}'src='images/delete.png' onclick='onDelClick(${laufvariable.id})' title='Entfernen'></td>`
//              + `<td>${laufvariable.version}</td>`  // Version muss nicht in Browser für die Tabelle angezeigt werden, nur intressant für EWU 
			+ "</tr>")
	}
}

function getSearchResultFromJsonUndPackInHTML(myjson) {
	var i = 0; 
	var t_body = document.getElementById("tbid002"); 
	// Search table wird neu gebaut
	for (var laufvariable of myjson.personen) {
		t_body.insertAdjacentHTML("beforeend",
			"<tr>"
		    	+ `<td> ${++i} </td>` // id automatisch vergeben; Neu: Id wird im Browser gelesen und gespeichert
				+ `<td>${laufvariable.id}</td>`
				+ `<td>${laufvariable.anrede}</td>`
				+ `<td>${laufvariable.vorname}</td>`
				+ `<td>${laufvariable.nachname}</td>`
				+ `<td>${laufvariable.birthDate}</td>`
				+ `<td>${laufvariable.email}</td>`
				+ `<td>${laufvariable.standort}</td>`
			+ "</tr>")
	}
}

function resetById(id) {
	document.getElementById(id).reset();
}

function onRefreshClick() {
	document.getElementById("tbid001").innerHTML="";
	refreshTable();
}
  
function onLosClick(event) {
	event.preventDefault();
	document.getElementById("tbid002").innerHTML="";
	buildSearchTable();	
} 

function buildSearchTable() {
	document.getElementById("div001").classList.remove("hideit"); // do div sichtbar
	var standort = document.getElementById("id021").value;
	fetch(`/json/search?ort=${standort}`)   // Query Parameter: ort=${standort}
		.then(getJson) 				  	 
		.then(getSearchResultFromJsonUndPackInHTML)  		
}

/* bei jedem neu laden dieser Seite "/index.html" wird diese Funktion aufgerufen und die Tabelle
   wird neu gebaut */
function refreshTable() { 
	// Verbindung mit dem Server für Anzeige aller Personen bzw. fetch lädt die Seite auf dem Server
	try {
		fetch("/json/persons/all")
			.then(getJson) 				  	 // entspricht: .then( irgendwas => irgendwas.json() )
			.then(getAllPersonsFromJsonUndPackInHTMLForTable)  // entpricht: cell.textContent = myjson.personen[0].vorname);
	} catch (exception) { alert("Aktualisierung der Tabelle kann nicht erfolgen!"); }		
}

function onEditClick(row, id, counter, version) {
	var editRow = document.getElementById(row);
    var id = editRow.cells[0].innerHTML;
	var salut = editRow.cells[1].innerHTML;
	var firstname = editRow.cells[2].innerHTML;
	var lastname = editRow.cells[3].innerHTML;
	var birthday = editRow.cells[4].innerHTML;
	var location = editRow.cells[5].innerHTML;
	var email = editRow.cells[6].innerHTML;
	editRow.innerHTML = `<th> ${id}   </th>`
			+	`<td contenteditable> ${salut}</td>` 
			+	`<td contenteditable> ${firstname}</td>`
			+	`<td contenteditable> ${lastname}</td>`
			+	`<td contenteditable> ${birthday}</td>`
			+	`<td contenteditable> ${location}</td>`
			+	`<td contenteditable> ${email}</td>`
			+	`<td><img class='icon' src='${getIcon(salut)}'></td>`
			+	`<td><img class='icon' id='save${id}'src='img/save.png' onclick='savePerson("row${counter}", ${id}, ${counter}, ${version})' title='Speichern'></td>`
			+	`<td><img class='icon' id='delete${id}'src='img/delete.jpeg' onclick='deletePerson(${id})' title='Entfernen'></td>`;
}

document.getElementById("idblink").classList.remove("hideit"); 
document.getElementById("id010").classList.remove("hideit");
refreshTable();
	
// Submit
document.getElementById("id010").addEventListener("click",oninputclick);

// Delete
document.getElementById("id012").addEventListener("click",onDeleteClick);

// Update
document.getElementById("id013").addEventListener("click",onUpdateClick);

// Delete All
document.getElementById("id014").addEventListener("click",onDeleteAllClick);

// Refresh
document.getElementById("id015").addEventListener("click",onRefreshClick);

// Search
document.getElementById("id016").addEventListener("click",onSearchClick);

// Search by Standort
document.getElementById("id020").addEventListener("click",onLosClick);

		
		