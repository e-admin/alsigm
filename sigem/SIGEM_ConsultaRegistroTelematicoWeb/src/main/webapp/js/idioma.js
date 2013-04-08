
function cambiarIdioma(){
	var frm = document.getElementById('recargarIdioma');
	var selIdioma = document.getElementById('selIdioma');
	var idioma = selIdioma.options[selIdioma.selectedIndex].value;
	frm.src = 'jsp/recargaIdioma.jsp?idioma=' + idioma;
}

function cambiarIdiomaSinRefresco(){
	var frm = document.getElementById('recargarIdioma');
	var selIdioma = document.getElementById('selIdioma');
	var idioma = selIdioma.options[selIdioma.selectedIndex].value;
	frm.src = 'jsp/recargaIdioma.jsp?idioma=' + idioma+'&refresco=false';
}