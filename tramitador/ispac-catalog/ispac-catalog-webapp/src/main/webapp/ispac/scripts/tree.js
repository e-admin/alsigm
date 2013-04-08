function addChlidNodes(id, nivel, hijos) {
	try {
		xmlhttp = new ActiveXObject ('Msxml2.XMLHTTP');
	} catch (e) {
		try {
			xmlhttp = new ActiveXObject ('Microsoft.XMLHTTP');
		} catch (E)	{
			xmlhttp = false; 
		} 
	}
	if (!xmlhttp && typeof XMLHttpRequest!='undefined') {xmlhttp = new XMLHttpRequest ();}	
	
	xmlhttp.open('GET', '/ispaccatalog/encuentraHijos.do?procedureId=' + id + '&nivelTree=' + nivel + '&numHijos=' + hijos, false);
	xmlhttp.onreadystatechange=function() {if(xmlhttp.readyState==4) {}}
  	
  	xmlhttp.send(null);
  	  	
  	return xmlhttp.responseText;	
}

function abre(capa, nivel) {
	//Realizar la eleccion entre expandir y contraer... dentro de expandir, ver tambien si es necesario 
	//  preguntar por lo hijos
	capa.innerHTML = capa.innerHTML + addChlidNodes(capa.id, nivel, capa.childNodes.length);
}