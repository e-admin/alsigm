function contentLinksInsideTab(tab){

	// los enlaces se muestran dentro del panel del tab
	jQuery('a',tab).click(function(event) {
		event.preventDefault();
		jQuery.ajax({
			type:'POST',
			cache:false,
			url:this.href,
			success:function(data){
				jQuery(tab).html(data);
				return false;
			}
		});
       return false;
    });
}

function calcula_digito_control(cif){
	//algoritmo para comprobacion de codigos tipo CIF
    suma = parseInt(cif.charAt(2)) + parseInt(cif.charAt(4)) + parseInt(cif.charAt(6));
    for (i = 1; i < 8; i += 2) {
		temp1 = 2 * parseInt(cif.charAt(i));
        temp1 += '';
        temp1 = temp1.substring(0,1);
        temp2 = 2 * parseInt(cif.charAt(i));
        temp2 += '';
        temp2 = temp2.substring(1,2);
        if (temp2 == '') {
			temp2 = '0';
        }
        suma += (parseInt(temp1) + parseInt(temp2));
    }
    suma += '';
    n = 10 - parseInt(suma.substring(suma.length-1, suma.length));

	return n;
}

//Validacion del NIF
function valida_nif(a, errorsId, infoId){

	//longitud de la cadena del NIF
	var lengthStringNif = 9;
	var hayGuion = false;
	//variable para comprobar el formato del nif
	var formatOK = true;
	var nif = jQuery.trim(jQuery('#'+a).val()).toUpperCase();

	//indica si el nif es correcto
	var resul = true;

	//comprobams si el NIF contiene guiones
	if (nif.indexOf("-") == -1){
		//comprobamos si la longitud es la correcta
		formatOK = (nif.length == lengthStringNif);
	}
	else {
		//Descomponemos el nif en dos cadenas para comprobar el correcto formato
		var cadenaNumNIF = nif.substr(0, nif.indexOf("-"));
		var cadenaLetraNIF = nif.substr(nif.indexOf("-") + 1);
		//comprobamos si la longitud es la correcta y solamente existe un guion
		formatOK = ((cadenaNumNIF.length == lengthStringNif - 1) && (cadenaLetraNIF.length == 1));
		hayGuion = true;
	}

	if (formatOK){
		var letras = "TRWAGMYFPDXBNJZSQVHLCKE";
		//obtenemos el numero para calcular el digito de control
		var num_digito_control = parseInt(nif.substr(0, lengthStringNif - 1), 10)%23;

		//validamos que la parte numerica del NIF sea numero
		if (!/^([0-9])*$/.test(nif.substr(0, lengthStringNif - 1))){
			//mensaje de error:  NIF incorrecto.
			jQuery('#'+errorsId).html(top.GetIdsLan("IDS_MSG_INVALID_NIF"));
			jQuery('#'+errorsId).show();
			jQuery('#'+infoId).hide();
			jQuery('#'+a).val(nif);
			resul = false;
		} else {
			//Obtenemos el digito de control del NIF
			var digitoControl = letras.substring(num_digito_control, num_digito_control + 1);
			//validamos si el codigo de control calculado es igual al codigo de control del nif
			if (nif.substr(nif.length - 1) != digitoControl){
				//mensaje de error: Digito de control incorrecto. Actualizado a {letra}
				jQuery('#'+infoId).html(top.GetIdsLan("IDS_MSG_INVALID_COD_CONTROL") + digitoControl);
				jQuery('#'+infoId).show();
				jQuery('#'+errorsId).hide();
				jQuery('#'+a).val(nif.substr(0,8) + (hayGuion?"-":"") + digitoControl);
				resul = false;
			}
		}
	}
	else {
		//mensaje de error: Formato incorrecto
		jQuery('#'+errorsId).html(top.GetIdsLan("IDS_MSG_FORMAT_INVALID"));
		jQuery('#'+errorsId).show();
		jQuery('#'+infoId).hide();
		resul = false;
	}

	//validamos si el NIF ha sido correcto
	if(resul){
		//NIF CORRECTO
		jQuery('#'+a).val(nif);
		return 1;
	}else{
		//NIF ERRONEO
		jQuery('#'+a).focus();
	}


	return resul;
}

//Validacion del NIE
function valida_nie(a, errorsId, infoId){

	var nie = jQuery.trim(jQuery('#'+a).val()).toUpperCase();

	//Longitud del nie
	var lengthNIE = 0;
	//Prefijo del nie
	var prefijoNIE = "";
	//Variable que indica si existen guiones en la cadena del nie
	var hayGuion = false;
	var hayGuionInicial = false;
	var nieSinPrefijo = "";
	//variable numerica del valor de la letra inicial del nie
	var prefixValNum ="0";
	//numero de posiciones a recortar
	var numPosicCutCadena = 0;

	//indica si el nie es correcto
	var resul = true;

	//comprobamos el prefijo de la cadena de entrada empieza por X0
	if(nie.substr(0, 2) == "X0"){
		//comprobamos si hay algun guion despues del prefijo
		if(nie.substr(2, 1) == "-"){
			//si justo despues del prefijo existe un guion el formato no es correcto
			formatOK = false;
		}else{
			//comprobamos la longitud del nie sin contar los guiones
			if((nie.replace("-","")).length >9){
				//si la longitud es mayor a 9 digitos va por el metodo viejo
				lengthNIE = 10;
				prefijoNIE = "X0";
				numPosicCutCadena = 2;
			}else{
				lengthNIE = 9;
				numPosicCutCadena = 1;
				prefijoNIE = "X";
			}

			//el formato de la cadena es correcto
			formatOK = (nie.substr(0, numPosicCutCadena) == prefijoNIE);
		}
	}else{
		lengthNIE = 9;
		numPosicCutCadena = 1;
		//buscamos el prefijo
		if(nie.substr(0, 1) == "X"){
			//si la cadena empieza por X
			prefijoNIE = "X";
		}else{
			if(nie.substr(0, 1) == "Y"){
				//si la cadena empieza por Y
				prefijoNIE = "Y";
				prefixValNum="1";
			}else{
				//si la cadena empieza por cualquier otra letra
				prefijoNIE = "Z";
				prefixValNum="2";
			}
		}
		formatOK = (nie.substr(0, numPosicCutCadena) == prefijoNIE);
	}

	if (formatOK){
		//obtenemos de la cadena del NIE la parte sin el prefijo
		nieSinPrefijo = nie.substr(numPosicCutCadena);

		//Comprobamos si ademas existen guiones en la cadena
		if ((nieSinPrefijo.substr(0, 1)) == "-"){
			nieSinPrefijo = nieSinPrefijo.substr(1);
			hayGuionInicial = true;
		}

		if (nieSinPrefijo.indexOf("-") == -1){
			formatOK = (nieSinPrefijo.length == (lengthNIE - numPosicCutCadena));
		}
		else {
			//obtenemos las cadenas segun la posicion de los guiones
			var cadenaNumNIE = nieSinPrefijo.substr(0, nieSinPrefijo.indexOf("-"));
			var cadenaGuionNIE = nieSinPrefijo.substr(nieSinPrefijo.indexOf("-") + 1);
			//validamos el correcto tamaño de las cadenas
			formatOK = ((cadenaNumNIE.length == (lengthNIE - (numPosicCutCadena+1))) && (cadenaGuionNIE.length == 1));
			hayGuion = true;
		}
	}

	if (formatOK){
		var letras = "TRWAGMYFPDXBNJZSQVHLCKE";
		//Parte numerica del NIE
		var numNIE = nieSinPrefijo.substr(0, lengthNIE - (numPosicCutCadena+1));

		//Obtenemos la posicion del codigo de control correspondiente al prefijo mas el numero del NIE
		var numero = parseInt((prefixValNum + numNIE), 10)%23;

		//Comprobamos si la la parte numerica del nie es correcta (si realmente es numerica)
		if (!/^([0-9])*$/.test(numNIE)){
			//mensaje de error: NIE incorrecto
			jQuery('#'+errorsId).html(top.GetIdsLan("IDS_MSG_INVALID_NIE"));
			jQuery('#'+errorsId).show();
			jQuery('#'+infoId).hide();
			jQuery('#'+a).val(nie);
			resul = false;
		} else {
			//obtenemos el codigo de control correspondiente al NIE
			var codControl = letras.substring(numero, numero + 1);
			//comprobamos el codigo de control
			if (nieSinPrefijo.substr(nieSinPrefijo.length - 1) != codControl){
				//mensaje de error: Digito de control incorrecto. Actualizado a {letra}
				jQuery('#'+infoId).html(top.GetIdsLan("IDS_MSG_INVALID_COD_CONTROL") + codControl);
				jQuery('#'+infoId).show();
				jQuery('#'+errorsId).hide();
				//comprobamos el prefijo
				if((prefijoNIE == "X0") && (nie.length == 10)){
					//si el prefijo es X0 no se permite incluir guion despues del prefijo
					jQuery('#'+a).val(prefijoNIE + nieSinPrefijo.substr(0, lengthNIE - (numPosicCutCadena+1)) + (hayGuion?"-":"") + codControl);
				}else{
					//en cambio cualquiera de los demas prefijos pueden separarse del numero por guion
					jQuery('#'+a).val(prefijoNIE + (hayGuionInicial?"-":"") + nieSinPrefijo.substr(0, lengthNIE - (numPosicCutCadena+1)) + (hayGuion?"-":"") + codControl);
				}

				resul = false;
			}
		}
	} else {
		//mensaje de error: Formato incorrecto
		jQuery('#'+errorsId).html(top.GetIdsLan("IDS_MSG_FORMAT_INVALID"));
		jQuery('#'+errorsId).show();
		jQuery('#'+infoId).hide();
		resul = false;
	}

	//validamos si el nie es correcto
	if(resul){
		//NIE CORRECTO
		jQuery('#'+a).val(nie);
		return 3;
	}else{
		//NIE INCORRECTO
		jQuery('#'+a).focus();
	}
	return resul;
}

function valida_cif(a, errorsId, infoId){
	var resul = true;
	var cif = jQuery.trim(jQuery('#'+a).val()).toUpperCase();
	var tipo;

	//Tipos de nifs con codigo de control numerico
	var letrasCodNum = "ABCDEFGHIJUV";
	//Tipo de nifs con codigo de control caracter
	var letrasCodLetra = "NPQRSW";

	//validamos si es distinto de null
	if(cif != ' '){
		//validamos la longitud del cif
		if(cif.length == 9){
			var n = calcula_digito_control(cif);

			//Validamos si el NIF comienza por los siguientes caracteres
			if (/^[ABCDEFGHJNPQRSUVW]{1}[0-9]{7}/.test(cif)) {
				var temp_n = n + '';

				//validamos el cif, que el tipo de organizacion corresponde ademas con el codigo de control si corresponde con un caracter o un digito
				if ((cif.charAt(8) == String.fromCharCode(64 + n) && (letrasCodLetra.indexOf(cif.charAt(0)) != -1))
				 || (cif.charAt(8) == parseInt(temp_n.substring(temp_n.length-1, temp_n.length))) && (letrasCodNum.indexOf(cif.charAt(0)) != -1)) {
					jQuery('#'+a).val(cif);
					return 2;
				} else if ((cif.charAt(8) != String.fromCharCode(64 + n)) && (letrasCodLetra.indexOf(cif.charAt(0)) != -1) ){
					//validamos que el codigo de control se debe representar con letra
					//mensaje de error: Digito de control incorrecto. Actualizado a {letra}
					jQuery('#'+infoId).html(top.GetIdsLan("IDS_MSG_INVALID_COD_CONTROL") + String.fromCharCode(64 + n));
					jQuery('#'+infoId).show();
					jQuery('#'+errorsId).hide();
					jQuery('#'+a).val(cif.substr(0,8) + String.fromCharCode(64 + n));
					resul = false;
				} else if (cif.charAt(8) != parseInt(temp_n.substring(temp_n.length-1, temp_n.length)) && (letrasCodNum.indexOf(cif.charAt(0)) != -1)) {
					//validamos si el codigo de control se representa con numero
					//mensaje de error: Digito de control incorrecto. Actualizado a {letra}
					jQuery('#'+infoId).html(top.GetIdsLan("IDS_MSG_INVALID_COD_CONTROL") + parseInt(temp_n.substring(temp_n.length-1, temp_n.length)));
					jQuery('#'+infoId).show();
					jQuery('#'+errorsId).hide();
					jQuery('#'+a).val(cif.substr(0,8) + parseInt(temp_n.substring(temp_n.length-1, temp_n.length)));
					resul = false;
				} else {
					//mensaje de error: CIF incorrecto
					jQuery('#'+errorsId).html(top.GetIdsLan("IDS_MSG_INVALID_CIF"));
					jQuery('#'+errorsId).show();
					jQuery('#'+infoId).hide();
					jQuery('#'+a).val(cif);
					resul = false;
				}
			//Comprobamos si el caracter inicial esta catalogado como desfasados
			}else if(/^[KLM]{1}/.test(cif)){
				//no se valida el nif se acepta cualquier cadena
				jQuery('#'+a).val(cif);
				return 2;
			}else {
				//mensaje de error: formato incorrecto
				jQuery('#'+errorsId).html(top.GetIdsLan("IDS_MSG_FORMAT_INVALID"));
				jQuery('#'+errorsId).show();
				jQuery('#'+infoId).hide();

				resul = false;
			}
		}else{
			//mensaje de error: Formato incorrecto
			jQuery('#'+errorsId).html(top.GetIdsLan("IDS_MSG_FORMAT_INVALID"));
			jQuery('#'+errorsId).show();
			jQuery('#'+infoId).hide();
			resul = false;
		}
	}

	if (!resul) {
		jQuery('#'+a).focus();
	}

	return resul;
}

//funcion que bloquea la pantalla  y muestra la capa de interesados
function showCapaInteresados(data){
	$.blockUI({ message: data,
		css: { top: '10%',
		  left: '10%',
		     width: '600px',
		    height: '310px',
		    cursor: 'cursor'}
	  });
}

//funcion que muestra el mensaje cargando
function showCapaLoading(){
	$.blockUI({message: "<h1>"+ top.GetIdsLan( "IDS_LOAD") +"</h1>"});
}

//funcion deshabilita el bloqueo de pantalla
function ocultarCapaInteresados(){
	$.unblockUI();
}

//funcion que inserta el string de interesados en el formulario de registro
function OnOk(strInteresados){
	// Guardamos la cadena en el campo
	if ( top.g_Field ){
		// Asignamos los valores de la lista a los campos interesados
		asignSustitutos(strInteresados, top.g_Field.getAttribute("FldId"), top.g_Field.getAttribute("tblvalidated"));

		top.g_WndVld.document.getElementById("Interesados").setAttribute("value",strInteresados);
		top.g_WndVld.document.getElementById("Interesados").contentWindow.LoadFrameInt();
		top.g_WndVld.document.getElementById("Interesados").contentWindow.SetChange();
		top.g_WndVld.cambioValor(top.g_Field);

		//Activamos el guardar
		top.Main.Folder.FolderBar.ActivateSave();
	}
	//ocultamos la capa
	ocultarCapaInteresados();
}

//añade un interesado seleccionado en el formulario de registro
function addTerceroRegisterForm(idTercero){
	$.ajax({
           type: 'POST',
           cache: false,
           url: 'interesado/crud.action?method=flushFormRegister',
           dataType: 'html',
           data: {'tercero.id':  idTercero},
           success: function(data) {
		    // se invoca a la funcion OnOk (copia de una legada) que se encarga de actualizar el atributo "value"
		    // del frame "Interesados" con la cadena recuperada
			OnOk($.trim(data));
           }
       });
}

//funcion que invoca a la pantalla de nuevo interesado fisico
function newTerceroFisico(){
	$.unblockUI();
	invocarVentanaHelpInteresado(top.g_Field, "eventoNuevoInteresadoFisico");
}

//funcion que invoca a la pantalla de nuevo interesado juridico
function newTerceroJuridico(){
	$.unblockUI();
	invocarVentanaHelpInteresado(top.g_Field, "eventoNuevoInteresadoJuridico");
}

//funcion que invoca a la ventana de ayuda del campo de interesados
function invocarVentanaHelpInteresado(oField, operativa){
	var Params;

	//comprobamos si el campo esta bloqueado
	if ( (!top.g_FdrReadOnly) && (!oField.getAttribute("readOnly")) )  {
		//se settean todos los datos necesarios
		top.g_WndVld      = top.Main.Folder.FolderData.FolderFormData;
		top.g_FormVld     = top.Main.Folder.FolderData.FolderFormData.document.getElementById("FrmData");
		top.g_Field       = oField;
		top.g_VldPath     = "top.Main.Folder.FolderData.FolderFormData";

		if (parseInt(top.g_Field.getAttribute("tblvalidated"), 10) > 0)  {
			if (top.Main.Folder.FolderBar.document.getElementById("SaveMenuBtn").className == "SubOptions") {
				top.g_WndVld.bIsActiveSave = true;
			}
			else {
				top.g_WndVld.bIsActiveSave = false;
			}

			OnHelpWindow = true;

			//asignamos a la variable global la operativa que tiene que hacer
			//al abrir la pantalla de busqueda
			top.g_actionInitFormInter = operativa;

			//se desactiva la barra de guardar
			top.Main.Folder.FolderBar.DesactivateSave();

			//se genera la variable parametros que se pasará al abrir la ventana
			Params = "SessionPId=" + top.g_SessionPId.toString()
				+ "&ArchivePId=" + top.g_ArchivePId.toString()
				+ "&VldInter=1"
                + "&Idioma=" + top.Idioma.toString()
		        + "&FldId=" + top.g_Field.getAttribute("FldId")
		        + "&IsDtrList=0"
		        + "&FrmData=1"
		        + "&Inter=" + top.g_Field;

			//se invoca a abrir la ventana con los parametros y tamaño indicado
			OpenVldHlpWnd(top.g_URL + "/mainvld.htm?" + Params, 'name', '97%', '97%', 'auto');
		}
   }
}