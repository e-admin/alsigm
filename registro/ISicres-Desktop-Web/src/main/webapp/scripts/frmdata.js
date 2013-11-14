var FieldValue    = "";
var FieldCadena   = "";
var FolderBarWnd  = parent.parent.FolderBar;
var OnHelpWindow  = false;
var FldId="";
// Para saber si estaba activado el boton de guardar
var bIsActiveSave = false;

// Tamanos del formulario
g_FormWidth = 0;
g_FormHeight = 0;

function MarkBadFields()
{
	// Marcamos campos que estan mal
	for (var j = 0; j < FolderBarWnd.BadFldArr.length; j++) {
		// ponemos el campo editable
		document.getElementById(FolderBarWnd.BadFldArr[j].Id).setAttribute("readOnly", false);
		// le damos el foco
		top.setFocus(document.getElementById(FolderBarWnd.BadFldArr[j].Id));

		if (document.getElementById(FolderBarWnd.BadFldArr[j].Id).value == "")	{
			document.getElementById(FolderBarWnd.BadFldArr[j].Id).style.backgroundColor = "red";
		}else{
			document.getElementById(FolderBarWnd.BadFldArr[j].Id).style.color = "red";
		}
	}
}


function OnWindowLoad()
{
	var bAdd = true;

	top.ShowForm();

	try{
	// Agregamos los Ids de todos los controles al array. (Solo si no estan en la pagina)
	for (var i = 0; (i < FolderBarWnd.PageCtrls.length) && (bAdd); i++){
		if (parseInt(FolderBarWnd.PageCtrls[i].Page) == top.g_Page){
			bAdd = false;
		}
	}

	if (bAdd){
		var Count = FolderBarWnd.PageCtrls.length;

		for (i = 0; i < document.getElementById("FrmData").length; i++)	{
			FolderBarWnd.PageCtrls[Count] = new FolderBarWnd.PageCtrl(top.g_Page.toString(), document.getElementById("FrmData")[i].name);
			Count++;
		}
	}

	// Decimos cual es el campo interesado de nuestro formulario
	// y ponemos a readonly los campos si el formulario es de solo lectura
	for (i = 0; i < document.getElementById("FrmData").length; i++){
		if (top.g_FdrReadOnly){
			//comprobamos el tipo de campo
			if(document.getElementById("FrmData")[i].type == "radio"){
				document.getElementById("FrmData")[i].setAttribute("disabled", "disabled");
			}else{
				document.getElementById("FrmData")[i].setAttribute("readOnly", "readOnly");
			}
			document.getElementById("FrmData")[i].style.backgroundColor = "#DDDDDD";
			document.getElementById("FrmData")[i].tabIndex = "-1";
		}
	}

	// Mostramos la imagenes de los campos de ayuda
	if ( (!top.g_FdrReadOnly) && (!top.g_LoadForm) ){
		for (var i = 0; i < document.images.length; i++){
			// Ponemos las imagenes de la ayuda
			if (document.images[i].src.indexOf("buscar2.gif") != -1 || document.images[i].src.indexOf("calendarM.gif") != -1){
				var inputsImg = document.getElementsByName(document.images[i].id);

				if (inputsImg.length != 0) {
					with (document.images[i].style) {
						if (!document.images[i].getAttribute("isReadOnly"))	{
							display = "block";
						}
						if (document.images[i].src.indexOf("buscar2.gif") != -1){
							width = 17;
							height = 17;
						}else{
							width = 20;
							height = 15;
						}
						left = parseInt(inputsImg[0].style.left, 10) + parseInt(inputsImg[0].style.width, 10) - 2;
						top = parseInt(inputsImg[0].style.top, 10) + parseInt(inputsImg[0].style.height, 10) - 20 ;
					}
				}
			}
			else{
				if (document.images[i].getAttribute("isObli"))	{
					document.images[i].style.posTop  = document.images[i].style.posTop - 5;
					document.images[i].style.posLeft  = document.images[i].style.posLeft - 2;
				}
			}
		}
	}

	document.getElementById("FrmData").style.visibility = 'visible';
	}catch(e){
	}

	// Activamos el arbol de documentos
	top.g_ActivateTree = true;
	top.g_OpcAval=true;

	// Habilitamos las toolbar en modo formulario
	if (!top.g_FolderView){
		top.Main.Folder.ToolBarFrm.habilitar();
	}
	else{
		top.Main.Folder.ToolBarFrm.ToolBarEnabled();
	}

	FolderBarWnd.bLoadForm = true;

	return;
}

function modificaciones()
{
	if((FolderBarWnd.FldDataArr.length > 0) && (!top.g_FdrReadOnly)){
		try	{
			for (var ii=0; ii < FolderBarWnd.FldDataArr.length; ii++){
				var item = document.getElementsByName(FolderBarWnd.FldDataArr[ii].CtrlId);

				if (item.length != 0){
					if (top.g_bIsSaved)	{
						FolderBarWnd.FldDataArr[ii].valueSust = top.getSustValue(item[0].getAttribute("FldId"), document.getElementById("FrmData"));
						item[0].setAttribute("valueSust", FolderBarWnd.FldDataArr[ii].valueSust);
						FolderBarWnd.FldDataArr[ii].Value = item[0].value;
					}
					else {
						item[0].value = FolderBarWnd.FldDataArr[ii].Value;
					}

					FolderBarWnd.AsignValueToFldsSust(FolderBarWnd.FldDataArr[ii].valueSust, item[0].getAttribute("FldId"));
				}
			}
		}
		catch(e){}
	}

	top.g_bIsSaved = false;

	if (top.g_CopyFdr != 0){
		for (i = 0; i < document.getElementById("FrmData").length; i++)	{
			if (!document.getElementById("FrmData")[i].getAttribute("Sustituto")){
				cambioValor(document.getElementById("FrmData")[i]);
			}
		}
	}

	if (document.getElementById("Interesados")){
		if (top.g_CopyFdr != 0)	{
			cambioValor(document.getElementById("Interesados"));
		}
	}

	return;
}

// Funcion que comprueba si el valor ha sido modificado respecto a lo que hay
// almacenado en la variable FolderBarWnd.FldDataArr
function NoCambiado( Field )
{
	if (FolderBarWnd.FldDataArr){
		for( var ii=0; ii<FolderBarWnd.FldDataArr.length; ii++ ){
			//validamos del array de campos modificados o persistentes
			if ((FolderBarWnd.FldDataArr[ii].Id == Field.getAttribute("FldId"))
					&& (!Field.getAttribute("Sustituto"))){
				//si el valor actual es diferente al almacenado
				if(FolderBarWnd.FldDataArr[ii].Value  != Field.value){
					// con lo que la variable que almacena si hay datos del
					// registro pendientes de guardar se activa a true
					top.g_changeDataRegistro = true;

				}

				FolderBarWnd.FldDataArr[ii].Value  = Field.value;
	            FolderBarWnd.FldDataArr[ii].valueSust = Field.getAttribute("valueSust");

		        return false;
			}
		}
	} else {
		return false;
	}

	// se considera que el valor del campo ha cambiado, con lo que la variable
	// que almacena si hay datos del
	// registro pendientes de guardar se activa a true
	top.g_changeDataRegistro = true;

	return true;
}

// Funcion que comprueba si un campo ha cambiado de valor, si ha cambiado el
// valor se añade al array de campos modificados (FolderBarWnd.FldDataArr)
function cambioValor( Field )
{
	var strValue = "";


	if (NoCambiado(Field)){

		var cantidad = FolderBarWnd.FldDataArr.length;

		strValue = Field.value;

		if (top.g_CopyFdr == 0){
			FolderBarWnd.FldDataArr[cantidad] =
				 new FolderBarWnd.FldData( Field.getAttribute("FldId"), strValue, Field.name, Field.getAttribute("valueSust"));
		}
		/*Si estamos copiando un registro entonces no hay que meter en el array los campos vacios, solo los que tengan valor*/
		else{
			if (Field.value!=null && Field.value!="" && Field.getAttribute("FldId")!=null && Field.getAttribute("FldId")!=""){
				FolderBarWnd.FldDataArr[cantidad] =
					 new FolderBarWnd.FldData( Field.getAttribute("FldId"), strValue, Field.name, Field.getAttribute("valueSust"));
				FolderBarWnd.FldDataArr[cantidad].valueSust = top.getSustValue(Field.getAttribute("FldId"), document.getElementById("FrmData"));
				Field.setAttribute("valueSust", FolderBarWnd.FldDataArr[cantidad].valueSust);
			}
		}
   }

   return;
}


function ChkModifFocus (Field)
{
	var fldValidated = Field.getAttribute("tblValidated");

	if (FolderBarWnd.bLoadForm)	{
		if (top.g_FdrReadOnly || Field.getAttribute("Sustituto")){
			Field.blur();
		}
		else{
			if (!window.OnHelpWindow){
				if (fldValidated != "0") {
					window.FieldValue = Field.value.toUpperCase();
				}
				else {
					window.FieldValue = Field.value;
				}
            }
		}
	}

	return;
}


function ChkModifBlur( Field )
{
	var fldValidated = Field.getAttribute("tblValidated");

	if (FolderBarWnd.bLoadForm)	{
		if (!top.g_FdrReadOnly && !Field.getAttribute("Sustituto"))	{
			if (window.OnHelpWindow){
				OnHelpWindow = false;

				if ((window.FieldValue != Field.value) && (!Field.getAttribute("readOnly"))){
					Field.value = top.miTrim(Field.value);

					if (fldValidated != "0") {
						Field.value = Field.value.toUpperCase();
					}

					cambioValor(Field);
				}
			}
			else{
				if ((window.FieldValue != Field.value) && (!Field.getAttribute("readOnly"))){
					Field.value = top.miTrim(Field.value);

					if (fldValidated != "0") {
						Field.value = Field.value.toUpperCase();
					}

					Field.setAttribute("valueSust", "");
					CleanSust(Field.getAttribute("FldId"));

					cambioValor( Field );
				}
			}
		}
	}

	return;
}


function ProcessTypeMatter(FldId, sMatter, FldIdAdd, CodeAdd, DescAdd)
{
	var sText = top.miTrim(sMatter);

	if ((sText.substring(0, 2) != "<%") && (sText.substring(sText.length - 2, 2) != "%>")){
		SetFieldDescription(FldId, sText);
	}
	else{
		var tokens, sText2 = "";

		sText = sText.substring(2, sText.length);
		sText = sText.substring(0, sText.length - 2);

		tokens = sText.split("/");

		for (var i = 0; i < tokens.length; i++)	{
			var labels = tokens[i].split("#");

			if (labels.length == 1)	{
				sText2 += tokens[i];
			}
			else{
				var args = new Array();
				var resp;

				args[0] = labels[0];
				args[1] = labels[1];

				resp = top.ShowModalDialog(top.g_URL + "/frmvarres.htm", args, 230, 300, "");

				if ((resp == "") || (resp == null))	{
					sText2 = "";
					break;
				}
				else {
					sText2 += resp;
				}
			}
		}

		sText2 = sText2.substring(0, 254);

		if (sText2 != ""){
			for (var i = 0; i < document.getElementById("FrmData").length; i++)	{
				if (document.getElementById("FrmData")[i].IsResum == 1)	{
					document.getElementById("FrmData")[i].value = sText2;
					cambioValor(document.getElementById("FrmData")[i]);
				}
			}
		}

		SetCodeAndFieldDescription(FldId, "", "")
	}

	if (FldIdAdd != 0){
		SetCodeAndFieldDescription(FldIdAdd, CodeAdd, DescAdd)
	}
}


function LookupField(FldId)
{
	var Fields = new Array();
	var index = 0;

	for (var i = 0; i < document.getElementById("FrmData").length; i++)	{
		if (document.getElementById("FrmData")[i].getAttribute("FldId") == FldId){
			Fields[index] = document.getElementById("FrmData")[i];
			index++;
		}
	}

	return (Fields);
}


function SetCodeAndFieldDescription(FldId, Code, Name)
{
	var Fields = LookupField(FldId);

	for (var i = 0; i < Fields.length; i++){
		if (Fields[i].getAttribute("Sustituto")){
			Fields[i].value = Name;
		}
		else{
			Fields[i].value = Code;
			Fields[i].setAttribute("valueSust", Name);
			cambioValor(Fields[i]);
		}
	}

	SetErrorImg(FldId, false);
}


function SetFieldDescription(FldId, Name)
{
	var Fields = LookupField(FldId);

	for (var i = 0; i < Fields.length; i++){
		if (Fields[i].getAttribute("Sustituto")){
			Fields[i].value = Name;
		}
		else{
			Fields[i].setAttribute("valueSust", Name);
			cambioValor(Fields[i]);
		}
	}

	SetErrorImg(FldId, false);
}


function FireOnBlur( Field )
{
	var fldValidated = Field.getAttribute("tblValidated");

	if (!FolderBarWnd.bLoadForm) {return;}

	if (top.g_FdrReadOnly || Field.getAttribute("Sustituto") || Field.getAttribute("readOnly")) {return;}

	Field.value = top.miTrim(Field.value);

	if (((fldValidated != "0") && (fldValidated != top.VAL_TP_TRANSPORT) && (fldValidated != top.VAL_TP_ORIGREG))){
		Field.value = Field.value.toUpperCase();
	}

	if (Field.value == ""){
		Field.setAttribute("valueSust", "");
		CleanSust(Field.getAttribute("FldId"));
	}

	if (window.FieldValue == Field.value){
		OnHelpWindow = false;
		return;
	}

	if (window.OnHelpWindow){
		OnHelpWindow = false;
		SetErrorImg(Field.getAttribute("FldId"), false);
		cambioValor(Field);
	}
	else{
		if (window.FieldValue == Field.value) {return;}

		if (Field.getAttribute("tblvalidated") != "0"){
			if ((Field.getAttribute("tblvalidated") != top.VAL_TP_ORIGREG) && (Field.getAttribute("tblvalidated") != top.VAL_TP_TRANSPORT)){
				Field.setAttribute("valueSust", "");
				CleanSust(Field.getAttribute("FldId"));

				if (Field.value != ""){
					var URL;

					top.g_Field = Field;

					document.body.style.cursor = "wait";

					if (Field.getAttribute("tblvalidated") != top.VAL_TP_UNITS){

						URL = top.g_URL + "/validatecode.jsp?SessionPId=" + top.g_SessionPId.toString()
							+ "&ArchivePId=" + top.g_ArchivePId.toString()
							+ "&FldId=" + Field.getAttribute("FldId")
							+ "&Code=" + Field.value;

						top.XMLHTTPRequestGet(URL, Validate, false);
					}
					else {
						top.CallActionForm("ValidateUnit", Field.value,
							Field.getAttribute("FldId"), 0, ResponseActionForm);
					}
				}
				else {
					SetErrorImg(Field.getAttribute("FldId"), false);
				}
			}
			else if (Field.getAttribute("tblvalidated") == top.VAL_TP_ORIGREG){
				ValidateTpOrigReg(Field);
			}
		}

		cambioValor(Field);
	}

	return;
}


function ValidateTpOrigReg(Field)
{
	var litEnt = top.GetIdsLan("IDS_OPC_ENTRADA");
	var litSal = top.GetIdsLan("IDS_OPC_SALIDA");
	var initEnt, initSal;

	initEnt = litEnt.substring(0, 1);
	initSal = litSal.substring(0, 1);

	if (Field.value == ""){
		SetErrorImg(Field.getAttribute("FldId"), false);
		return;
	}

	if ((Field.value.toUpperCase() != initEnt.toUpperCase() ) && (Field.value.toUpperCase() != initSal.toUpperCase())
		&& (Field.value.toUpperCase() != litEnt.toUpperCase()) && (Field.value.toUpperCase() != litSal.toUpperCase())){
		top.g_ErrorOnValidate = true;
		SetErrorImg(Field.getAttribute("FldId"), true);

		alert(top.GetIdsLan("IDS_MSG_FIELDS_NOT_VALID"));

		Field.focus();
	}
	else{
		if (Field.value.toUpperCase() == initEnt.toUpperCase()) {
			Field.value = litEnt;
		}
		else if (Field.value.toUpperCase() == initSal.toUpperCase()){
			Field.value = litSal;
		}

		SetErrorImg(Field.getAttribute("FldId"), false);
	}
}


function OpenVldHlpWnd(URL, Name, Width, Height, Scroll)
{
	window.open(URL, "Vld","location=no",true);

	if ((top.Main != null)
	 && (top.Main.Folder != null)
	 && (top.Main.Folder.FolderData != null)
	 && (top.Main.Folder.FolderData.FolderFormTree != null)
	 && (top.Main.Folder.FolderData.FolderFormTree.document != null)
	 && (top.Main.Folder.FolderData.FolderFormTree.document.getElementById("divDocs") != null)){
		top.Main.Folder.FolderData.FolderFormTree.document.getElementById("divDocs").style.visibility="hidden";
	}

	top.g_WndVld.document.getElementById("Vld").style.left = "5px";
	top.g_WndVld.document.getElementById("Vld").style.top  = "5px";
	top.g_WndVld.document.getElementById("Vld").style.height = Height;
	top.g_WndVld.document.getElementById("Vld").style.width = Width;
	top.g_WndVld.document.getElementById("Vld").style.display = "block";
}

function VldHelp(aEvent, oField)
{
	var Params;

	if ( (!top.g_FdrReadOnly) && (!oField.getAttribute("readOnly")) )  {
		top.g_WndVld      = top.Main.Folder.FolderData.FolderFormData;
		top.g_FormVld     = top.Main.Folder.FolderData.FolderFormData.document.getElementById("FrmData");
		top.g_Field       = oField;
		top.g_VldPath     = "top.Main.Folder.FolderData.FolderFormData";

		if (parseInt(top.g_Field.getAttribute("tblvalidated"), 10) > 0)  {
			if (top.Main.Folder.FolderBar.document.getElementById("SaveMenuBtn").className == "SubOptions") {
				bIsActiveSave = true;
			}
			else {
				bIsActiveSave = false;
			}

			OnHelpWindow = true;
			top.Main.Folder.FolderBar.DesactivateSave();

			if (top.g_Field.getAttribute("tblvalidated") == top.FLD_INTERESTED_FLDID) {
				Params = "SessionPId=" + top.g_SessionPId.toString()
					+ "&ArchivePId=" + top.g_ArchivePId.toString()
					+ "&VldInter=1"
                    + "&Idioma=" + top.Idioma.toString()
			        + "&FldId=" + top.g_Field.getAttribute("FldId")
			        + "&IsDtrList=0"
			        + "&FrmData=1"
			        + "&Inter=" + top.g_Field;
			}
			else {
				Params = "SessionPId=" + top.g_SessionPId.toString()
					+ "&ArchivePId=" + top.g_ArchivePId.toString()
					+ "&FldId=" + top.g_Field.getAttribute("FldId")
					+ "&tblvalidated=" + top.g_Field.getAttribute("tblvalidated")
					+ "&TblFldId=" + top.g_Field.getAttribute("TblFldId")
					+ "&VldInter=0"
					+ "&Idioma=" + top.Idioma.toString()
					+ "&Enabled=1&IsDtrList=0"
					+ "&FrmData=1";
			}

			OpenVldHlpWnd(top.g_URL + "/mainvld.htm?" + Params, 'name', '97%', '97%', 'auto');
		}
   }

   top.PreventDefault(aEvent);
}

function VldHelp(aEvent, oField, caseSensitive)
{
	var Params;

	if ( (!top.g_FdrReadOnly) && (!oField.getAttribute("readOnly")) )  {
		top.g_WndVld      = top.Main.Folder.FolderData.FolderFormData;
		top.g_FormVld     = top.Main.Folder.FolderData.FolderFormData.document.getElementById("FrmData");
		top.g_Field       = oField;
		top.g_VldPath     = "top.Main.Folder.FolderData.FolderFormData";

		if (parseInt(top.g_Field.getAttribute("tblvalidated"), 10) > 0)  {
			if (top.Main.Folder.FolderBar.document.getElementById("SaveMenuBtn").className == "SubOptions") {
				bIsActiveSave = true;
			}
			else {
				bIsActiveSave = false;
			}

			OnHelpWindow = true;
			top.Main.Folder.FolderBar.DesactivateSave();

			if (top.g_Field.getAttribute("tblvalidated") == top.FLD_INTERESTED_FLDID) {
				Params = "SessionPId=" + top.g_SessionPId.toString()
					+ "&ArchivePId=" + top.g_ArchivePId.toString()
					+ "&VldInter=1"
				+ "&Idioma=" + top.Idioma.toString()
	                + "&FldId=" + top.g_Field.getAttribute("FldId")
	                + "&IsDtrList=0"
	                + "&caseSensitive=" + caseSensitive
	                + "&FrmData=1&FrmDataBusc=1";
			}
			else {
				Params = "SessionPId=" + top.g_SessionPId.toString()
					+ "&ArchivePId=" + top.g_ArchivePId.toString()
					+ "&FldId=" + top.g_Field.getAttribute("FldId")
					+ "&tblvalidated=" + top.g_Field.getAttribute("tblvalidated")
					+ "&TblFldId=" + top.g_Field.getAttribute("TblFldId")
					+ "&VldInter=0"
					+ "&Idioma=" + top.Idioma.toString()
					+ "&Enabled=1&IsDtrList=0"
	                + "&caseSensitive=" + caseSensitive
	                + "&FrmData=1&FrmDataBusc=1";
			}

			OpenVldHlpWnd(top.g_URL + "/mainvld.htm?" + Params, 'name', '97%', '97%', 'auto');
		}
   }

   top.PreventDefault(aEvent);
}


function VldInterPress(aEvent, Field)
{
	if ((top.GetKeyCode(aEvent) == 13) && (Field.getAttribute("tblvalidated") == top.FLD_INTERESTED_FLDID) ) {
		var valor = Field.value;

		if (valor != "") {
			// Si no existe el campo interesado ya, lo metemos
			if ( NotExist(valor, Field.cadena) ) {
				// activamos el guardar
				top.Main.Folder.FolderBar.ActivateSave();

				// Metemos el valor en la cadena
				if (Field.cadena == "") {
					Field.cadena = "0#" + valor + "#0##" ;
				}
				else {
					Field.cadena += " && " + "0#" + valor + "#0##";
				}

				// Borramos el campo
				Field.value = "";

				// metemos el valor en los campos sustitutos
				for (var jj=0; jj < document.getElementById("FrmData").length; jj++){
					if ( (document.getElementById("FrmData")[jj].getAttribute("FldId") == Field.getAttribute("FldId"))
						&& (document.getElementById("FrmData")[jj].getAttribute("Sustituto")) ) {
						document.getElementById("FrmData")[jj].value = getValueInter(Field.cadena);
					}
				}

				// Metemos el valor en el array de cambios
				cambioValor( Field );
			}
			else {
				Field.value = "";
			}
		}
	}
}

function NotExist(strCad2, strCad1)
{
   var strFind = new RegExp("#" + strCad2 + "#","i");
   // Miramos si la cadena esta
   var iPosFound = strCad1.search(strFind);
   if (iPosFound > -1)
   {
      return false;
   }
   else
   {
      return true;
   }
}

// Crea el string que se mete en el campo sustituto de interesados
function getValueInter(strCadena)
{
   var strCopia = new String(strCadena);
   var pattern = / && /i;
   var strRet = "";
   var oArrTok;
   var iPosIni = 0;
   var iPosFin = strCopia.length;
   if (iPosFin > 0)
   {
      iPosFin = strCopia.search(pattern);
      while (iPosFin > -1)
      {
         oArrTok = top.getTokens(strCopia.substr(iPosIni, iPosFin),"#","#",4);
         strRet += oArrTok[1] + unescape("%0D%0A");
         strCopia = strCopia.substr(iPosFin + 4, strCopia.length - (iPosFin + 4));
         iPosFin = strCopia.search(pattern);
         delete oArrTok;
      }
      oArrTok = top.getTokens(strCopia,"#","#",4);
      strRet += oArrTok[1];
      delete oArrTok;
   }
   delete strCopia;
   return strRet;
}

function chkKeyCode( Code )
{
   if( ( Code != 9 ) && ( Code != 16 ) && ( Code != 17 ) && ( Code != 18 ) && ( Code != 20 )
      && (( Code < 33 ) || ( Code > 40 )) && ( Code != 45 ) && (( Code < 112 ) || ( Code > 123 )) )
   {
      return true;
   }
   return false;
}

// Asigna el foco al control que ha lanzado las tablas de validacion
function AsigFocus()
{
	if (!top.g_FdrReadOnly)
	{
		if ((top.g_Field) && (OnHelpWindow || top.g_ErrorOnValidate))
		{
			if (OnHelpWindow)
			{
				FireOnBlur(top.g_Field);
			}

			top.setFocus(top.g_Field);
			top.g_ErrorOnValidate = false;
		}
		else
		{
			//top.setFormFocus(document.forms.FrmData, g_FormWidth, g_FormHeight);
		}
   }

   return;
}

// Activa el boton de guardar si pulsamos una tecla sobre un campo editable
function EnabledSave(bIsInter, aEvent)
{
	var srcElement = top.GetSrcElement(aEvent);

	if (bIsInter)  {
		VldInterPress(aEvent, srcElement);
	}
	else  {
		if( (chkKeyCode(top.GetKeyCode(aEvent))) && !srcElement.getAttribute("readOnly")) {
			top.Main.Folder.FolderBar.ActivateSave();
		}
	}

	return;
}

// lanza la ayuda en los campos
function imgHelp(aEvent)
{
	var srcElement = top.GetSrcElement(aEvent);

	if ( !top.g_FdrReadOnly )  {
		try  {
			VldHelp(aEvent, document.getElementsByName(srcElement.id)[0]);
		}
		catch(e){}
	}

	return;
}

function imgHelp(aEvent, caseSensitive)
{
	var srcElement = top.GetSrcElement(aEvent);

	if ( !top.g_FdrReadOnly )  {
		try  {
			VldHelp(aEvent, document.getElementsByName(srcElement.id)[0], caseSensitive);
		}
		catch(e){}
	}

	return;
}

// Asigna los tamanos del formulario a las variables globales
function getFormTam(strWidth ,strHeight)
{
   g_FormWidth = parseInt(strWidth);
   g_FormHeight = parseInt(strHeight);
}

// Crea un array con todos los campos readonly del formulario
// que servira de plantilla para el bucle de nueva carpeta
function getTemplateForm()
{
   if (top.g_bIsNewFolder)  {
      top.g_bIsNewFolder = false;

      if (top.g_FolderView){
         if (top.g_ArrFldsReadOnly != null) {
            delete top.g_ArrFldsReadOnly;
            top.g_ArrFldsReadOnly = null;
         }

         top.g_ArrFldsReadOnly = new Array();

         for (var ii = 0; ii < document.getElementById("FrmData").elements.length; ii++ ){
            if ( ( (document.getElementById("FrmData").elements[ii].type == "text") || (document.getElementById("FrmData").elements[ii].nodeName.toUpperCase() == "TEXTAREA") )
               && (document.getElementById("FrmData").elements[ii].getAttribute("readOnly")) ){
               top.g_ArrFldsReadOnly[top.g_ArrFldsReadOnly.length] = document.getElementById("FrmData").elements[ii].name;
            }
         }
      }
   }

   return;
}

// consigue los parametros de permisos de interesados
function getParamsPersons(iCanUpdatePersons, iCanAddPersons)
{
   top.g_CreateInterPerms = iCanAddPersons;
   top.g_ModifyInterPerms = iCanUpdatePersons;
}

//limpia los campos sustitutos de otro campo
function CleanSust(strFldId)
{
	for(var ii=0; ii < document.getElementById("FrmData").length; ii++) {
		if ( (document.getElementById("FrmData")[ii].getAttribute("FldId") == strFldId) && (document.getElementById("FrmData")[ii].getAttribute("Sustituto")) ){
			document.getElementById("FrmData")[ii].value = "";
		}
	}
}


function SetErrorImg(FldId, bSet)
{
	for(var i=0; i < document.getElementById("FrmData").length; i++) {
		if (document.getElementById("FrmData")[i].getAttribute("FldId") == FldId) {
			if (bSet){
				document.getElementById("FrmData")[i].style.color = "red";
			}else{
				document.getElementById("FrmData")[i].style.color = "#000000";
			}
		}
	}
}

function SetFormFocus()
{
	var oForm = document.body.getElementsByTagName("*");
	var iPosX = g_FormWidth;
	var iPosY = g_FormHeight;

	// Ponemos el foco en el primer campo de la pagina que estamos visualizando
	if ( (top.g_FolderView) && (!top.g_FdrReadOnly) )
	{
		var iIndex = -1;

		for (var ii = 0; ii < oForm.length; ii++)
		{
			var canFocused = (oForm[ii].tagName == "INPUT") && (oForm[ii].type == "text")
				&& (oForm[ii].getAttribute("readOnly") == false);

			if (!canFocused) {
				canFocused = (oForm[ii].tagName == "TEXTAREA") && (oForm[ii].getAttribute("readOnly") == false);
			}

			if (!canFocused) {
				canFocused = (oForm[ii].tagName == "IFRAME") && (oForm[ii].getAttribute("readOnly") == false);
			}

			// Miramos si es el campo mas a la izquierda para darle el foco
			if ( canFocused )
			{
				if (oForm[ii].style.posTop <= iPosY)
				{
					if (oForm[ii].style.posTop == iPosY)
					{
						if (oForm[ii].style.posLeft < iPosX)
						{
							iIndex = ii;
							iPosX = oForm[ii].style.posLeft;
						}
					}
					else
					{
						iIndex = ii;
						iPosY = oForm[ii].style.posTop;
						iPosX = oForm[ii].style.posLeft;
					}
				}
			}
		}

		if (iIndex > -1)
		{
			if (oForm[iIndex].id == "Interesados"){
			top.setFocus(oForm[iIndex].document.frames.Interesados.document.getElementsByTagName("TEXTAREA")[0]);
			}
			else {
				top.setFocus(oForm[iIndex]);
			}
		}

	}
}


function LimitText(obj, Limit)
{
	if (obj.value.length > Limit)
	{
		obj.value = obj.value.slice(0, Limit);
	}
}


function Validate()
{
	if (top.g_oXMLHTTPRequest.readyState != 4){
		return;
	}

	if (top.g_oXMLHTTPRequest.status != 200){
		alert(top.g_oXMLHTTPRequest.statusText + " (" + top.g_oXMLHTTPRequest.status.toString() + ")");
		return;
	}

	var XMLDoc = top.g_oXMLHTTPRequest.responseXML;

	document.body.style.cursor = "cursor";

	if ((XMLDoc == null) || (XMLDoc.documentElement == null)){
		eval(top.g_oXMLHTTPRequest.responseText);
		top.g_ErrorOnValidate = true;
	}
	else{
		var FldId = parseInt(XMLDoc.documentElement.getElementsByTagName("FldId")[0].firstChild.data, 10);

		if (XMLDoc.documentElement.getElementsByTagName("Error").length != 0) {
			var Fields = LookupField(FldId);

			top.g_ErrorOnValidate = true;
			SetErrorImg(FldId, true);
			alert(XMLDoc.documentElement.getElementsByTagName("Error")[0].firstChild.data);

			for (var i = 0; i < Fields.length; i++){
				if (Fields[i].getAttribute("readOnly") == false){
					Fields[i].focus();
				}
			}
		}
		else{
			var bFoundTypeMatter = false;
			var Code = XMLDoc.documentElement.getElementsByTagName("Code")[0].firstChild.data;
			var Desc = XMLDoc.documentElement.getElementsByTagName("Description")[0].firstChild.data;
			var FldIdAdd = 0, CodeAdd = "", DescAdd = "";

			if (XMLDoc.documentElement.getElementsByTagName("FldIdAdd").length != 0){
				FldIdAdd = parseInt(XMLDoc.documentElement.getElementsByTagName("FldIdAdd")[0].firstChild.data, 10);
				CodeAdd = XMLDoc.documentElement.getElementsByTagName("CodeAdd")[0].firstChild.data;
				DescAdd = XMLDoc.documentElement.getElementsByTagName("DescriptionAdd")[0].firstChild.data;
			}

			for (var i = 0; i < document.getElementById("FrmData").length; i++){
				if (document.getElementById("FrmData")[i].getAttribute("FldId") == FldId){
					if ((!document.getElementById("FrmData")[i].getAttribute("Sustituto")) &&
						(document.getElementById("FrmData")[i].getAttribute("tblvalidated") == top.VAL_TP_TYPEMATTER)){
						bFoundTypeMatter = true;
						break;
					}
				}
			}

			if (bFoundTypeMatter){
				ProcessTypeMatter(FldId, Desc, FldIdAdd, CodeAdd, DescAdd);
			}
			else{
				SetFieldDescription(FldId, Desc);
			}
		}
	}
}


function ActivateTree()
{
	top.g_ActivateTree=true;

	if (!top.g_FolderView){
		top.g_OpcAval=true;
		top.Main.Folder.ToolBarFrm.habilitar();
	}
	else {
		top.Main.Folder.ToolBarFrm.ToolBarEnabled();
	}
}


function ValidateUnit(XMLDoc)
{
	var Action = XMLDoc.documentElement.getElementsByTagName("Action")[0].firstChild.data;
	var FldId = parseInt(XMLDoc.documentElement.getElementsByTagName("FldId")[0].firstChild.data, 10);

	if (XMLDoc.documentElement.getElementsByTagName("Error").length != 0){
		var Fields = LookupField(FldId);

		top.g_ErrorOnValidate = true;
		SetErrorImg(FldId, true);
		alert(XMLDoc.documentElement.getElementsByTagName("Error")[0].firstChild.data);

		for (var i = 0; i < Fields.length; i++){
			if (Fields[i].getAttribute("readOnly") == false){
				top.setFocus(Fields[i]);
				break;
			}
		}
	}
	else{
		var Total = parseInt(XMLDoc.documentElement.getElementsByTagName("Total")[0].firstChild.data, 10);

		if (Total == 1){
			var Code = XMLDoc.documentElement.getElementsByTagName("Node")[0].getElementsByTagName("Code")[0].firstChild.data;
			var Name = XMLDoc.documentElement.getElementsByTagName("Node")[0].getElementsByTagName("FullName")[0].firstChild.data;

			SetCodeAndFieldDescription(FldId, Code, Name);
		}
		else{
			var args = new Array();
			var sRet;

			args[0] = XMLDoc;
			args[1] = top.g_URL;
			args[2] = top.g_SessionPId.toString();
			args[3] = FldId.toString();
			args[4] = Action;
			args[5] = top.Idioma;
			args[6] = top.GetIdsLan( "IDS_UNITS_SELECT" );

			sRet = top.ShowModalDialog(top.g_URL + "/dlglist.htm", args, 550, 750, "");

			if (sRet != null){
				var tokensRet = top.getTokens(sRet, "#", "#", 2);

				SetCodeAndFieldDescription(FldId, tokensRet[0], tokensRet[1])
			}
			else{
				SetCodeAndFieldDescription(FldId, "", "")
			}
		}
	}
}


function AddAsocReg()
{
	if (top.g_FdrReadOnly){
		alert(top.GetIdsLan("IDS_ASOCREG_ADD_BLOCK"));
	} else {
		var URL = top.g_URL
				+ "/getsearchasociacionregistros.jsp?SessionPId=" + top.g_SessionPId
				+ "&ArchiveId=" + top.g_ArchiveId
				+ "&FolderId=" + top.g_FolderId;


		var args = new Array();
		args[0] = top.g_URL;
		args[1] = top.g_SessionPId;
		args[2] = top.g_ArchivePId.toString();
		args[3] = top.Idioma;
		args[4] = top.g_ArchiveId.toString();
		args[5] = top.g_FolderId.toString();

		var resp = 0;
		resp = top.ShowModalDialog(URL, args, 180, 475, "");

		if ((resp != null) && (resp == 10)){
			top.Main.Folder.FolderData.FolderFormTree.OpenPageAsocRegs("onclick");
		}
	}

}

function DelAsocReg(bookId, folderId){
	if (top.g_FdrReadOnly){
		alert(top.GetIdsLan("IDS_ASOCREG_DEL_BLOCK"));
	} else {
		if (bookId != null && folderId != null ){

			var URL = "saveselectasociacionregistros.jsp?SessionPId=" + top.g_SessionPId
				+ "&ArchiveId=" + bookId
				+ "&FolderId=" + folderId
				+ "&ValidateType=1";

			top.XMLHTTPRequestGet(URL, ValidateAsocRegsSelected, false);

			top.Main.Folder.FolderData.FolderFormTree.OpenPageAsocRegs("onclick");
		}
	}
}

function FormatFieldAsocRegs(field)
{
	var fldValidated = (field.getAttribute("Validated") == "1")?true:false;;

	if (field.className == "input"){
		field.value = top.miTrim(field.value);

		if ((field.getAttribute("DataType") == "2") && (field.value != "")){
			ValidateDate(field);
		}
		else{
			if (fldValidated){
				field.value = field.value.toUpperCase();
			}
		}
	}
}

function ValidateBookSelected(bookId){
	if (bookId != null && bookId.length > 0){

		URL = top.g_URL + "/validatesearchasociacionregistros.jsp?SessionPId=" + top.g_SessionPId.toString()
							+ "&ArchivePId=" + top.g_ArchivePId.toString()
							+ "&registerBook=" + bookId
							+ "&ValidationType=1";

		top.XMLHTTPRequestGet(URL, ValidateAsocRegsSelected, false);

		var codeResult = top.g_CodeAsocReg;

		if (codeResult == 5){
			alert(top.GetIdsLan("IDS_ASOCREG_BOOK_INVALID"));
			return false;
		}
	}
	return true;
}

function ShowValidationListAsocRegs(FldType, ValidType, FldId, caseSensitive)
{
	var sRet = "";
	var args = new Array;

	args[0] = top.GetDlgParam(0);
	args[1] = top.GetDlgParam(1);
	args[2] = top.GetDlgParam(2);
	args[3] = FldId.toString();
	args[4] = ValidType.toString();
	args[5] = FldId.toString();
	args[6] = top.GetDlgParam(0);
	args[7] = 0;
	//parametro que indica si la busqueda es para los libros
	args[8] = true;

	sRet = top.ValidateList(args, caseSensitive);

	if (sRet && (sRet != "")) {
		var tokens = new Array;
		var objs = document.getElementsByName(FldType);

		tokens = top.getTokens(sRet, "#", "#", 3);

		for (var i = 0; i < objs.length; i++) {
			var obj = objs.item(i);

			if ((obj.className == "input") && (obj.FldId == FldId)) {
				obj.value = tokens[1];
				break;
			}
		}
	}
}

function SearchRegs(Init, fnCallback) {

	var URL = "validatesearchasociacionregistros.jsp?SessionPId=" + top.g_SessionPId
			+ "&InitValue=" + Init.toString()
			+ "&ArchiveId=" + top.g_ArchiveId
			+ "&FolderId=" + top.g_FolderId;

	var regFields = document.getElementsByName("RegField");
	var items = document.getElementsByTagName("*");
	var regWhere = "";

	var canSearch = false;
	var registerBook = "";
	for (var i = 0; i < regFields.length; i++) {
		var item = regFields[i];
		var itemName = item.getAttribute("FldName");
		var itemValue = item.value;


		if (!canSearch){
			if (itemName == "@FLD0" && itemValue != null && itemValue != ""){
				canSearch = true;
				registerBook = itemValue;
			}
		}
		if ((item.className == "input") && (item.value != "")){
			if (itemName != "@FLD0"){
				if (regWhere == ""){
					regWhere = item.getAttribute("FldName") + ";" +  GetOperator(item.getAttribute("FldName")) + ";" + item.value + ";#";
				} else {
					regWhere = regWhere +  item.getAttribute("FldName") + ";" + GetOperator(item.getAttribute("FldName")) + ";" + item.value + ";#";
				}
			}
		}
	}
	if (!canSearch){
		alert(top.GetIdsLan("IDS_ASOCREG_REGTYPE_REQUIRED"));
		return;
	}

	for (var i = 0; i < items.length; i++){
		if ((items[i].nodeName == "IMG") && items[i].getAttribute("FldName")){
			items[i].style.visibility = "hidden";
		}
	}

	URL += "&regWhere=" + escape(regWhere) + "&registerBook=" + escape(registerBook);

	document.body.style.cursor = "wait";
	if (ValidateBookSelected(registerBook)){
		top.XMLHTTPRequestGet(URL, fnCallback, false);
		var rv = top.returnValue;
	}
	return;
}

function ResponseSearch()
{
	if (top.g_oXMLHTTPRequest.readyState != 4){
		return;
	}
	if (top.g_oXMLHTTPRequest.status != 200){
		alert(top.g_oXMLHTTPRequest.statusText + " (" + top.g_oXMLHTTPRequest.status.toString() + ")");
		return;
	}
	var XMLText = top.g_oXMLHTTPRequest.responseText;

	if (XMLText.indexOf("alert(") != -1){
		evalAlert(XMLText);
	} else{
		var XMLDoc = top.g_oXMLHTTPRequest.responseXML;

		if ((XMLDoc != null)
		 && (XMLDoc.documentElement != null)){

			if ((XMLDoc.documentElement.getElementsByTagName("Error") != null)
			 && (XMLDoc.documentElement.getElementsByTagName("Error").length != 0)){
				alert(XMLDoc.documentElement.getElementsByTagName("Error")[0].firstChild.data);
				return;
			}

			var Total = parseInt(XMLDoc.documentElement.getElementsByTagName("Total")[0].firstChild.data, 10);

			if (Total > 0){
				var args = new Array();
				var sRet;
				var selected = false;

				args[0] = XMLDoc;
				args[1] = top.g_URL;
				args[2] = top.g_SessionPId.toString();
				args[3] = "0";
				args[4] = "SearchAsocRegs";
				args[5] = top.Idioma;
				args[6] = top.GetIdsLan( "IDS_ASOCREG_SELECT");


				document.body.style.cursor = "wait";
				sRet = top.ShowModalDialog(top.g_URL + "/asocRegsSearchResults.htm", args, 550, 750, "");

				top.returnValue = sRet;

				if ((sRet != null) && (sRet != ""))	{
					var arrTokens = getTokensAsocRegs(sRet, "#");

					if (arrTokens.length > 0){
						var URL = "validateselectasociacionregistros.jsp?SessionPId=" + top.g_SessionPId
							+ "&ArchiveId=" + top.g_ArchiveId
							+ "&FolderId=" + top.g_FolderId
							+ "&AsocRegsSelected=" + sRet;

						top.XMLHTTPRequestGet(URL, ValidateAsocRegsSelected, false);

						var rv = top.returnValue;

						var codeResult = top.g_CodeAsocReg;

						if ((codeResult >= 0) && (codeResult <= 3)){
							SaveAsocReg(codeResult, sRet, "");
						} else if (codeResult == 5) {
							top.returnValue = "";
							alert(top.GetIdsLan("IDS_ASOCREG_REL_NOTVALID"));
						} else if (codeResult == 4) {
							SelectPrimaryReg(sRet);
						}

					}


				//	insertRowInt(document.getElementById("tbRegs"), parseInt(arrTokens[2], 10), 0, arrTokens[1], "", document.getElementById("tbRegs").rows.length - 1);
				//	SetTabSize();
				}
			}

			document.body.style.cursor = "cursor";
		} else {
			alert(top.GetIdsLan("IDS_NOT_RESULT"));
		}
	}
}

function SaveAsocReg(code, asocRegsSelect, regPrimary){
	var URL = "saveselectasociacionregistros.jsp?SessionPId=" + top.g_SessionPId
			+ "&ArchiveId=" + top.g_ArchiveId
			+ "&FolderId=" + top.g_FolderId
			+ "&AsocRegsSelected=" + asocRegsSelect
			+ "&ValidateCode=" + code
			+ "&RegPrimary=" + regPrimary;

	top.XMLHTTPRequestGet(URL, ValidateAsocRegsSelected, false);

	var codeResult = top.g_CodeAsocReg;

	if (codeResult == 10){
		top.close();
		top.returnValue = codeResult;
		return;
	}
}

function SelectPrimaryReg(sRet){
	var URL = "validateselectasociacionregistros.jsp?SessionPId=" + top.g_SessionPId
			+ "&ArchiveId=" + top.g_ArchiveId
			+ "&FolderId=" + top.g_FolderId
			+ "&AsocRegsSelected=" + sRet
			+ "&ValidateType=1";

	document.body.style.cursor = "wait";

	top.XMLHTTPRequestGet(URL, ResponseSelectPrimaryReg, false);

	var rv = top.returnValue;

	if (rv != null){
		SaveAsocReg("4", sRet, rv);
	}
}

function ValidateAsocRegsSelected()
{
	if (top.g_oXMLHTTPRequest.readyState != 4){
		return;
	}

	if (top.g_oXMLHTTPRequest.status != 200){
		alert(top.g_oXMLHTTPRequest.statusText + " (" + top.g_oXMLHTTPRequest.status.toString() + ")");
		return;
	}

	var XMLText = top.g_oXMLHTTPRequest.responseText;

	if (XMLText.indexOf("alert(") != -1){
		evalAlert(XMLText);
	} else{
		var XMLDoc = top.g_oXMLHTTPRequest.responseXML;

		if (XMLDoc.documentElement.getElementsByTagName("Error").length != 0){
			alert(XMLDoc.documentElement.getElementsByTagName("Error")[0].firstChild.data);
			return;
		}

		var code = parseInt(XMLDoc.documentElement.getElementsByTagName("Code")[0].firstChild.data, 10);
		top.g_CodeAsocReg = code;

	}
}

function getTokensAsocRegs(strCadena, strSep)
{
	var oArray = new Array();
	var iPosIni = 0;
	var iPosFin = 0;
	var iIndex = 0;


	iPosFin = strCadena.indexOf(strSep);
	while (iPosFin != -1)
	{
		oArray[iIndex] = strCadena.substr(iPosIni, iPosFin - iPosIni);
		iPosIni = iPosFin + strSep.length;
		iPosFin = strCadena.indexOf(strSep, iPosIni);
		iIndex++;
	}

	return oArray;
}

function ResponseSelectPrimaryReg()
{
	if (top.g_oXMLHTTPRequest.readyState != 4){
		return;
	}

	if (top.g_oXMLHTTPRequest.status != 200){
		alert(top.g_oXMLHTTPRequest.statusText + " (" + top.g_oXMLHTTPRequest.status.toString() + ")");
		return;
	}

	var XMLText = top.g_oXMLHTTPRequest.responseText;

	if (XMLText.indexOf("alert(") != -1){
		evalAlert(XMLText);
	} else{
		var XMLDoc = top.g_oXMLHTTPRequest.responseXML;

		if (XMLDoc.documentElement.getElementsByTagName("Error").length != 0){
			alert(XMLDoc.documentElement.getElementsByTagName("Error")[0].firstChild.data);
			return;
		}

		var args = new Array();
		var sRet;
		var selected = false;

		args[0] = XMLDoc;
		args[1] = top.g_URL;
		args[2] = top.g_SessionPId.toString();
		args[3] = "0";
		args[4] = "SelectPrimaryRegs";
		args[5] = top.Idioma;
		args[6] = top.GetIdsLan( "IDS_ASOCREG_SELECT_PRIMARY");


		document.body.style.cursor = "wait";

		sRet = top.ShowModalDialog(top.g_URL + "/asocRegsSelectPrimary.htm", args, 550, 750, "");

		top.returnValue = sRet;
	}
}


function activarDesactivarTerceros()
{
	var IntValidation = top.Main.Folder.FolderData.FolderFormData.document.getElementById("HabilitarValidationInt").value;

	// conmutamos el valor del valuePersonValidation para que cambiar la configuracion
	if (IntValidation == 0){
		IntValidation = 1;
	}
	else{
		IntValidation = 0;
	}

	var Data = "IntValidation=" + escape(IntValidation);
	var URL = top.g_URL + "/SavePersonValidation.jsp?SessionPId=" + top.g_SessionPId
		+ "&BookId=" + top.g_ArchiveId.toString();

	top.Main.Folder.FolderData.FolderFormData.document.getElementById("HabilitarValidationInt").value = IntValidation;

	top.XMLHTTPRequestPost(URL, ModifConfInter, true, Data);

}

function ModifConfInter()
{
	var imagen = document.getElementById("imgValInter");

	if(document.getElementById("HabilitarValidationInt").value>0){
		imagen.src="images/user_check.png";
		imagen.style.display = 'block';
	}
	else{
		imagen.src="images/user_check_deshabilitado.png";
		imagen.style.display = 'block';
	}

	var frmInt = top.Main.Folder.FolderData.FolderFormData.document.getElementById("Interesados");
	frmInt.contentWindow.Conmute((top.Main.Folder.FolderData.FolderFormData.document.getElementById("HabilitarValidationInt").value== "1"));
}

/**
 * Busca en una cadena un mensaje de Alert y lo ejecuta.
 * Se usa para poder mostrar los mensajes de error cuando hay una respuesta que lo incluya, independientemente de su posicion.
 * @param message Cadena que incluye el mensaje de error.
 */
function evalAlert(message) {
	var startAlert = 0;
	var endAler = 0;

	startAlert = message.indexOf("alert(");
	if (startAlert != -1) {
		endAlert = message.indexOf(");", startAlert);
		eval(message.substr(startAlert, endAlert - startAlert + 2));
	}
}

/**
 * Funcion encargada de pasar el valor del check a su respectivo input
 * @param checkbox
 * @param idCampo
 */
function changeValueSelect(checkbox, idCampo){
	if(document.getElementById(idCampo)){
		if(checkbox.checked){
			document.getElementById(idCampo).value = "1";
		}else{
			document.getElementById(idCampo).value = "";
		}
		FireOnBlur(document.getElementById(idCampo));
		top.Main.Folder.FolderBar.ActivateSave();
	}
}

/**
 * Funcion encargada de pasar el valor del radio a su respectivo input
 * @param radio
 * @param idCampo
 */
function changeValueRadioDocFisica(radio, idCampo){

	var inputHidden1, inputHidden2, inputHidden3;

	//Obtenemos los input ocultos relacionados con el radioButton
	inputHidden1 = document.getElementsByName("504")[0];
	inputHidden2 = document.getElementsByName("505")[0];
	inputHidden3 = document.getElementsByName("506")[0];

	//reiniciamos el valor de todos los campos
	inputHidden1.value = 0;
	inputHidden2.value = 0;
	inputHidden3.value = 0;

	//asignamos el valor 1 al input correspondiente
	document.getElementsByName(idCampo)[0].value = 1;

	//lanzamos el evento fireOnBlur de cada uno de los campos
	FireOnBlur(inputHidden1);
	FireOnBlur(inputHidden2);
	FireOnBlur(inputHidden3);

	//activamos el boton de guardar
	top.Main.Folder.FolderBar.ActivateSave();
}