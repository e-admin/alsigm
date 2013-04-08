function size() {
	
	var tamanyo = 0;
	
	if (typeof window.innerWidth != 'undefined') {
	
		tamanyo = window.innerHeight;
	}
	else if (typeof document.documentElement != 'undefined' && typeof document.documentElement.clientWidth != 'undefined' && document.documentElement.clientWidth != 0) {
	
		tamanyo = document.documentElement.clientHeight;
	}
	else {
		
		tamanyo = document.getElementsByTagName('body')[0].clientHeight;
	}
	
	return tamanyo;
}

function showLayer(id) {
	
	var element;
	var elements;
	var i;

	element = document.getElementById(id);

	if (element != null) {
	
		// Deshabilitar el scroll
		document.body.style.overflow = "hidden";
	  
		element.style.position = "absolute";
		//element.style.height = document.body.clientHeight;	
		element.style.height = document.body.scrollHeight + 1200;
		element.style.width = document.body.clientWidth + 1200;
		element.style.left = -600;
		element.style.top = -600;
	
		element.style.display = "block";
		
		if (isIE) {
		
			elements = document.getElementsByTagName("SELECT");
			
		  	for (i = 0; i < elements.length; i++) {
				elements[i].style.visibility = "hidden";
		  	}
		}
	}
}

function hideLayer(id) {

	var element;
	var elements;
	var i;

	element = document.getElementById(id);

	if (element != null) {
	
		// Habilitar el scroll
		document.body.style.overflow = "auto";

 		element.style.display = "none";

		if (isIE) {
		
			elements = document.getElementsByTagName("SELECT");
		
			for (i = 0; i< elements.length; i++) {
				elements[i].style.visibility = "visible";
			}
		}
	}
}