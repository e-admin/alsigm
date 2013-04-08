//DEFINICION DE CONSTANTES
var REEMPLAZO_VALORES_EXACTOS 			= "0";
var REEMPLAZO_VALORES_PARCIALES  		= "1";
var REEMPLAZO_VALORES_NULOS  			= "2";

function isReemplazoValoresExactos(formaReemplazo){
	if(formaReemplazo ==  REEMPLAZO_VALORES_EXACTOS) return true;
	
	return false;
}

function isReemplazoValoresParciales(formaReemplazo){
	if(formaReemplazo ==  REEMPLAZO_VALORES_PARCIALES) return true;
	
	return false;
}

function isReemplazoValoresNulos(formaReemplazo){
	if(formaReemplazo ==  REEMPLAZO_VALORES_NULOS) return true;
	
	return false;
}

function getSelect(nombre,funcionOnChange, arrayElementos, idSeleccionado) {
    if(idSeleccionado== "") idSeleccionado = "0";
	var str = "<select name='" + nombre +"' id='"+ nombre +"' onchange='"+ funcionOnChange +"'>";
    for(var i=0;i<arrayElementos.length;i++){
      var valor = arrayElementos[i][0];
      var texto = arrayElementos[i][1];
      str += "<option value='"+ valor +"'";

      if(valor == idSeleccionado){
        str += " SELECTED";
      }
      str += ">"+ texto +"</option>";
    }
  str += "</select>";

  return str;
}



