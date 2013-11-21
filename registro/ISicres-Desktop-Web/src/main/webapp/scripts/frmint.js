var g_PersonValidation = false;

function UpperCase(obj)
{
	var caseSensitive = parent.document.getElementById("Interesados")
		.getAttribute("caseSensitive");
	if (caseSensitive == 'CS'){
		obj.value = obj.value.toUpperCase();
	}

}

function GetCaseSensitive(){
	var caseSensitive = parent.document.getElementById("Interesados")
		.getAttribute("caseSensitive");
	return caseSensitive;
}

function LoadFrame()
{
	var cadena = parent.document.getElementById("Interesados").getAttribute("value");

	var Mode = parent.document.getElementById("Interesados").getAttribute("readOnly");

	var arrInter = cadena.split("&&");
	if (cadena != ""){
		cadena = "";
		for (var i = 0; i < arrInter.length; i++) {

				var datas = arrInter[i].split("#");

				if(datas[4]  != 0){
					datas[3] += "";
				}

				arrInter[i] = datas.join("#");
		}
		cadena = arrInter.join("&&");

	}

	g_PersonValidation = top.Main.Folder.FolderData.FolderFormTree.document.getElementById("PersonValidation").value;

	if (!isNaN(g_PersonValidation)){
		g_PersonValidation = (g_PersonValidation == 1);
	}

	CleanText();
	CleanTable();

	document.getElementById("cadena").name = parent.document.getElementById("Interesados").getAttribute("CtrlId");

	if (cadena != "") {
		var arrInt = cadena.split("&&");

		if (arrInt.length > 0) {
			var datas = arrInt[0].split("#");

			g_PersonValidation = (parseInt(datas[0], 10) != 0);
		}
	}

	var imgValInt = top.Main.Folder.FolderData.FolderFormData.document.getElementById("imgValInter");
	var frameInteresados = parent.document.getElementById("Interesados");

	imgValInt.style.cursor = 'hand';
	imgValInt.style.position = 'absolute';
	imgValInt.style.top = frameInteresados.style.top;
	imgValInt.style.left = (parseInt(frameInteresados.style.left) + 490) + "px";

	if (g_PersonValidation){
		if(top.g_FdrReadOnly || (Mode == "1")){
			imgValInt.style.display = 'none';
		}
		else{
			top.Main.Folder.FolderData.FolderFormData.document.getElementById("HabilitarValidationInt").value = 1;
			imgValInt.src="images/user_check.png";
			imgValInt.style.display = 'block';
		}
		LoadFrameTable(cadena, Mode);
	}
	else {
		if(top.g_FdrReadOnly || (Mode == "1")){
			imgValInt.style.display = 'none';
		}
		else{
			top.Main.Folder.FolderData.FolderFormData.document.getElementById("HabilitarValidationInt").value = 0;
			imgValInt.src="images/user_check_deshabilitado.png";
			imgValInt.style.display = 'block';
		}
		LoadFrameText(cadena, Mode);
	}

}


function LoadFrameText(cadena, Mode)
{
	var text = document.getElementById("freeText");
	var valor = "";
	var caseSensitive = parent.document.getElementById("Interesados")
		.getAttribute("caseSensitive");

	if (caseSensitive == 'CS'){
		text.style.textTransform = "uppercase";
	}


	if (cadena != "") {
		var arrInt = cadena.split("&&");

		for (var i = 0; i < arrInt.length; i++)	{
			var datas = arrInt[i].split("#");

			if (valor == "") {
				valor = decodeURIComponent((datas[1]+'').replace(/\+/g, '%20'));
			}
			else {
				valor += "\r\n" + decodeURIComponent((datas[1]+'').replace(/\+/g, '%20'));
			}
		}
	}

	top.SetInnerText(text,  valor);

	if (top.g_FdrReadOnly || (Mode == "1")){
		text.readOnly = true;
		text.className = "textareaRO";
	}
	else {
		text.readOnly = false;
		text.className = "textarea";
	}

	document.getElementById("validate").style.display = "none";
	document.getElementById("free").style.display = "block";
}


function CleanText()
{
	top.SetInnerText(document.getElementById("freeText"), "");
}


function LoadFrameTable(cadena, Mode)
{
	var tabData = document.getElementById("TabInt");
	var btnImg = document.getElementById("imgHelp");
	var inputValid = document.getElementById("validInt");

	var caseSensitive = parent.document.getElementById("Interesados")
		.getAttribute("caseSensitive");

	if (caseSensitive == 'CS'){
		inputValid.style.textTransform = "uppercase";
	}


	if (cadena != "") {
		var arrInt = cadena.split("&&");

		for (var i = 0; i < arrInt.length; i++) {
			var datas = arrInt[i].split("#");
				insertRowTableInt(datas[0]?datas[0]:"", datas[1]?datas[1]:"", datas[2]?datas[2]:"", datas[3]?datas[3]:"", datas[4]?datas[4]:"", datas[5]?datas[5]:"", datas[6]?datas[6]:"", datas[7]?datas[7]:"", datas[8]?datas[8]:"");
		}
	}

	for (var i = 0; i < tabData.rows.length; i++){
		var row = tabData.rows[i];

		if (row.id != "head"){
			for (var j = 0; j < row.cells.length; j++) {
				if (top.g_FdrReadOnly || (Mode == "1"))	{
					row.cells[j].className = "reportRO";
				}
				else {
					row.cells[j].className = "report";
				}
			}
		}
	}

	if (top.g_FdrReadOnly || (Mode == "1")){
		btnImg.style.display = "none";
		inputValid.className = "inputRO";
	}
	else {
		btnImg.style.display = "block";
		inputValid.className = "input";
	}

	document.getElementById("validate").style.display = "block";
	document.getElementById("free").style.display = "none";
	document.getElementById("cadena").value = cadena;

	SetTabSize();
}

function LoadFrameInt()
{
	var cadena = parent.document.getElementById("Interesados").getAttribute("value");

	var Mode = parent.document.getElementById("Interesados").getAttribute("readOnly");

	var arrInter = cadena.split("&&");


	g_PersonValidation = top.Main.Folder.FolderData.FolderFormTree.document.getElementById("PersonValidation").value;

	if (!isNaN(g_PersonValidation)){
		g_PersonValidation = (g_PersonValidation == 1);
	}

	CleanText();
	CleanTable();

	document.getElementById("cadena").name = parent.document.getElementById("Interesados").getAttribute("CtrlId");

	if (cadena != "") {
		var arrInt = cadena.split("&&");

		if (arrInt.length > 0) {
			var datas = arrInt[0].split("#");

			g_PersonValidation = (parseInt(datas[0], 10) != 0);
		}
	}

	if (top.Main.Folder.FolderData.FolderFormData.document.getElementById("HabilitarValidationInt")){
		LoadFrameTable(cadena, Mode);
	}
	else {
		LoadFrameText(cadena, Mode);
	}
}

function insertRowTableInt(uidInt, nomInt, uidDom, domInt, domType, uidRepre, nomRepre, uidDomRepre, domRepre, domRepreType)
{
	var tabData = document.getElementById("TabInt");
	var newRow;
	var newCell = new Array;
	var typeDescription;

	newRow = tabData.insertRow(tabData.rows.length - 1);
	newRow.tabIndex=-1;
	newRow.align="left";

	newCell[0] = newRow.insertCell(0);
	top.SetInnerText(newCell[0], nomInt);
	newCell[0].className = "report";
	newCell[0].uid = uidInt;
	newCell[0].setAttribute("idType", domType);

	newCell[1] = newRow.insertCell(1);
	top.SetInnerText(newCell[1], domInt);
	newCell[1].className = "report";
	newCell[1].uid = uidDom;

	newCell[2] = newRow.insertCell(2);
	top.SetInnerText(newCell[2], nomRepre);
	newCell[2].className = "report";
	newCell[2].uid = uidRepre;
	newCell[2].setAttribute("idType", domRepreType);

	newCell[3] = newRow.insertCell(3);
	top.SetInnerText(newCell[3], domRepre);
	newCell[3].className = "report";
	newCell[3].uid = uidDomRepre;

	SetTabSize();
}

function SetTabSize()
{
	var tabData = document.getElementById("TabInt");
	var validInt = document.getElementById("validInt");
	var rowNull = document.getElementById("null");
	var space = parent.document.getElementById("Interesados").clientHeight - tabData.clientHeight - (2*validInt.clientHeight) + rowNull.clientHeight - 8;

	if (space > 13)	{
		rowNull.height = space.toString();
	}
	else {
		rowNull.height = "13";
	}

    if (parent.document.getElementById("Interesados").clientHeight - (2*validInt.clientHeight) -2 >= 0){
	    document.getElementById("divTab").style.height = parent.document.getElementById("Interesados").clientHeight - (2*validInt.clientHeight) -2;
	}
	else{
	    document.getElementById("divTab").style.height = (2*validInt.clientHeight) + 2 - parent.document.getElementById("Interesados").clientHeight;
	}
}

function CleanTable(){
	var tabData = document.getElementById("TabInt");

	for (var i = tabData.rows.length - 1; i >= 0; i--){
		if ((tabData.rows[i].id != "head") && (tabData.rows[i].id != "null")){
			tabData.deleteRow(i);
		}
	}

	document.getElementById("cadena").value = "";
}


function SetChange()
{
	if (g_PersonValidation || (!g_PersonValidation && (document.getElementById("freeText").readOnly == false)))	{
		top.Main.Folder.FolderBar.ActivateSave();
		document.getElementById("hasChanged").value = "1";
	}
}


function GetData()
{
	var data = "";

	if (g_PersonValidation){
		data = document.getElementById("cadena").value;
	}
	else {
		data = GetDataText();
	}

	return data;
}


function GetDataText()
{
	var data = "";
//	var arr1 = top.GetInnerText(document.getElementById("freeText")).split("\r\n");
	var arr1 = document.getElementById("freeText").value.split("\r\n");
	var arr2 = new Array();
	var j = 0;

	for (var i = 0; i < arr1.length; i++) {
		if (arr1[i].length > top.MAX_LENGTH_INTER) {
			var arr3 = new Array();
			var temp = top.miTrim(arr1[i]);

			if (temp.indexOf(" ") != -1) {
				var k = 0;

				arr3[k] = "";

				while (temp.indexOf(" ") != -1) {
					var nPos = temp.indexOf(" ");
					var cad = temp.substr(0, nPos);

					if ((arr3[k].length + cad.length + 1) > top.MAX_LENGTH_INTER) {
						k++;
						arr3[k] = cad;
					}
					else {
						arr3[k] = arr3[k] + " " + cad;
					}

					temp = temp.substr(nPos+1, temp.length - 1);
					temp = top.miTrim(temp);
				}

				if (temp.length > 0) {
					if ((arr3[k].length + temp.length + 1) > top.MAX_LENGTH_INTER) {
						k++;
						arr3[k] = temp;
					}
					else {
						arr3[k] = arr3[k] + " " + temp;
					}
				}
			}
			else {
				arr3[0] = temp;
			}

			for (var k = 0; k < arr3.length; k++) {
				if (arr3[k].length > top.MAX_LENGTH_INTER) {
					var temp = arr3[k];

					do {
						arr2[j] = temp.substr(0, top.MAX_LENGTH_INTER - 1);
						temp = temp.substr(top.MAX_LENGTH_INTER, temp.length -1);
						j++
					}
					while (temp.length > top.MAX_LENGTH_INTER)

					if (temp.length > 0){
						arr2[j] = temp;
						j++;
					}
				}
				else {
					arr2[j] = arr3[k];
					j++
				}
			}
		}
		else {
			arr2[j] = arr1[i];
			j++;
		}
	}

	for (var i = 0; i < arr2.length; i++) {
		var temp = top.miTrim(arr2[i]);

		if (temp != "")	{
			temp = encodeURIComponent(temp);

			if (data.length == 0) {
				data = "0#" + temp + "#0##";
			}
			else {
				data += " && " +  "0#" + temp + "#0##";
			}
		}
	}

	return data;
}


function HasChanged()
{
	return (document.getElementById("hasChanged").value == "1");
}


function Blur(Obj)
{
	parent.document.getElementById("Interesados").setAttribute("value", GetData());

	if (HasChanged()){
		try	{
			top.Main.Folder.FolderData.FolderFormData.cambioValor(parent.document.getElementById("Interesados"));
		}
		catch(e){}
	}
}

function Enable(Enabled)
{
	if (!g_PersonValidation){
		if (Enabled){
			document.getElementById("freeText").readOnly = false;
			document.getElementById("freeText").className = "textarea";
		}
		else {
			document.getElementById("freeText").readOnly = true;
			document.getElementById("freeText").className = "textareaRO";
		}
	}
	else
	{
		var tabData = document.getElementById("TabInt");

		for (var i = 0; i < tabData.rows.length; i++){
			var row = tabData.rows[i];

			for (var j = 0; j < row.cells.length; j++)	{
				if (row.id != "head"){
					if (!Enabled){
						row.cells[j].className = "reportRO";
					}
					else {
						row.cells[j].className = "report";
					}
				}
			}
		}

		if (!Enabled){
			document.getElementById("imgHelp").style.visibility = "hidden";
			document.getElementById("validInt").className = "inputRO";
		}
		else {
			document.getElementById("imgHelp").style.visibility = "visible";
			document.getElementById("validInt").className = "input";
		}
	}
}

function Conmute(Mode)
{
	g_PersonValidation = (Mode == "1");

	if (g_PersonValidation) {
		document.getElementById("free").style.display = "none";
		document.getElementById("validate").style.display = "block";
		SetTabSize();
	}
	else {
		document.getElementById("free").style.display = "block";
		document.getElementById("validate").style.display = "none";
	}

	parent.document.getElementById("Interesados").setAttribute("value", GetData());

	try	{
		top.Main.Folder.FolderData.FolderFormData.cambioValor(parent.document.getElementById("Interesados"));
	}
	catch(e){}
}


function InsertInt(Id, Name)
{
	var cadena = document.getElementById("cadena").value;
	var tabData = document.getElementById("TabInt");
	var Found = false;

	for (var i = 0; (i < tabData.rows.length) && !Found; i++){
		if (tabData.rows[i].firstChild.uid){
			Found = (tabData.rows[i].firstChild.uid == Id);
		}
	}

	if (!Found){
		var newcadena = Id + "#" + Name + "###";

		insertRowTableInt(Id, Name, "", "", "", "","", "","", "");

		if (cadena == ""){
			document.getElementById("cadena").value = newcadena;
		}
		else {
			document.getElementById("cadena").value = cadena + " && " + newcadena;
		}

		parent.document.getElementById("Interesados").setAttribute("value", document.getElementById("cadena").value);
		SetChange();
		top.Main.Folder.FolderData.FolderFormData.cambioValor(parent.document.getElementById("Interesados"));
	}
}

function InsertInt(Id, Name, IdAddress, Address, IdType)
{
	var cadena = document.getElementById("cadena").value;
	var tabData = document.getElementById("TabInt");
	var Found = false;
	var Description = "";

	for (var i = 0; (i < tabData.rows.length) && !Found; i++){
		if (tabData.rows[i].firstChild.uid){
			Found = (tabData.rows[i].firstChild.uid == Id);
		}
	}

	if (!Found){
		//var newcadena = Id + "#" + Name + "#" + IdAddress + "#" + Address + "###";
		var newcadena;
		if(IdType != 0){

			if (IdType == 1){
				Description = top.GetIdsLan( "IDS_TELEFONO_FIJO");
			}
			if (IdType == 2){
				Description = top.GetIdsLan( "IDS_CORREO");
			}
			if (IdType == 3){
				Description = top.GetIdsLan( "IDS_FAX");
			}
			if (IdType == 4){
				Description = top.GetIdsLan( "IDS_DEU");
			}
			if (IdType == 5){
				Description = top.GetIdsLan( "IDS_TELEFONO_MOVIL");
			}
			newcadena = Id + "#" + Name + "#" + IdAddress + "#" + Address + " " + Description + "#" + IdType + "###";
		}else{
			newcadena = Id + "#" + Name + "#" + IdAddress + "#" + Address + "#" + IdType + "###";
		}

		insertRowTableInt(Id, Name, IdAddress, Address + " " + Description, IdType, "","","","","");

		if (cadena == ""){
			document.getElementById("cadena").value = newcadena;
		}
		else {
			document.getElementById("cadena").value = cadena + " && " + newcadena;
		}

		parent.document.getElementById("Interesados").setAttribute("value", document.getElementById("cadena").value);
		SetChange();
		top.Main.Folder.FolderData.FolderFormData.cambioValor(parent.document.getElementById("Interesados"));
	}
}

function InsertInteresados(Id, Name, IdAddress, Address, IdType)
{
	var cadena = document.getElementById("cadena").value;
	var tabData = document.getElementById("TabInt");
	var Found = false;
	var Description = "";

	for (var i = 0; (i < tabData.rows.length) && !Found; i++){
		if (tabData.rows[i].firstChild.uid){
			Found = (tabData.rows[i].firstChild.uid == Id);
		}
	}

	if (!Found){
		//var newcadena = Id + "#" + Name + "#" + IdAddress + "#" + Address + "###";
		var newcadena = Id + "#" + Name + "#" + IdAddress + "#" + Address + "#" + IdType + "###";

		insertRowTableInt(Id, Name, IdAddress, Address, IdType);

		if (cadena == ""){
			document.getElementById("cadena").value = newcadena;
		}
		else {
			document.getElementById("cadena").value = cadena + " && " + newcadena;
		}

		parent.document.getElementById("Interesados").setAttribute("value", document.getElementById("cadena").value);
		SetChange();
		top.Main.Folder.FolderData.FolderFormData.cambioValor(parent.document.getElementById("Interesados"));
	}
}

function DoValidation(TopURL, SessionPId, Value, InitValue, fnCallback)
{

	var URL = top.g_URL + "/interesado/seachTerceros.action?SessionPId=" + top.g_SessionPId.toString()
	+ "&InitValue=" + InitValue.toString() + "&Code=" + escape(Value) + "&FldId=0";

	top.XMLHTTPRequestGet(URL, "", true);

}


function ValidInt()
{
	if (top.g_FdrReadOnly){return;}

	var validInt = document.getElementById("validInt");
	//validInt.value = top.miTrim(validInt.value);

	var caseSensitive = parent.document.getElementById("Interesados")
		.getAttribute("caseSensitive");
	if (caseSensitive == 'CS'){
		validInt.value = top.miTrim(validInt.value).toUpperCase();
	} else {
		validInt.value = top.miTrim(validInt.value);
	}


	if (validInt.value != "") {
		DoValidation(top.g_URL, top.g_SessionPId.toString(), validInt.value, 0, ResponseActionForm);
	}
}


function ValidateInt(XMLDoc)
{
	var validInt = document.getElementById("validInt");

	document.body.style.cursor = "cursor";

	if ((XMLDoc == null) || (XMLDoc.documentElement == null)){
		eval(top.g_oXMLHTTPRequest.responseText);
		validInt.focus();
		validInt.select();
	}
	else{
		var Action = XMLDoc.documentElement.getElementsByTagName("Action")[0].firstChild.data;
		var size = 0;
		var doBlur = true;

		if (XMLDoc.documentElement.getElementsByTagName("Total").length != 0){
			size = parseInt(XMLDoc.documentElement.getElementsByTagName("Total")[0].firstChild.data, 10);
		}
		if (size == 0){
			NewInt();
		}
		else if (size == 1){
			var id = XMLDoc.documentElement.getElementsByTagName("Persona")[0].getElementsByTagName("Id")[0].firstChild.data;
			var name = XMLDoc.documentElement.getElementsByTagName("Persona")[0].getElementsByTagName("Nombre")[0].firstChild.data;
			var idDom = "";
			var idDirTel = "";
			var dom = "";
			var dirTel = "";
			var tipo = "";
			if ((XMLDoc.documentElement.getElementsByTagName("Persona")[0].getElementsByTagName("Domicilio")[0] != null)  &&
				(XMLDoc.documentElement.getElementsByTagName("Persona")[0].getElementsByTagName("Domicilio")[0].firstChild != null)){
				idDom = XMLDoc.documentElement.getElementsByTagName("Persona")[0].getElementsByTagName("Domicilio")[0].getElementsByTagName("Id")[0].firstChild.data;
				dom = XMLDoc.documentElement.getElementsByTagName("Persona")[0].getElementsByTagName("Domicilio")[0].getElementsByTagName("Direccion")[0].firstChild.data + " " +
					  XMLDoc.documentElement.getElementsByTagName("Persona")[0].getElementsByTagName("Domicilio")[0].getElementsByTagName("Poblacion")[0].firstChild.data + " " +
					  XMLDoc.documentElement.getElementsByTagName("Persona")[0].getElementsByTagName("Domicilio")[0].getElementsByTagName("CodPostal")[0].firstChild.data + " " +
					  XMLDoc.documentElement.getElementsByTagName("Persona")[0].getElementsByTagName("Domicilio")[0].getElementsByTagName("Provincia")[0].firstChild.data;
				tipo = "0";
			}

			if ((XMLDoc.documentElement.getElementsByTagName("Persona")[0].getElementsByTagName("Telematica")[0] != null)  &&
				(XMLDoc.documentElement.getElementsByTagName("Persona")[0].getElementsByTagName("Telematica")[0].firstChild != null)){
				idDirTel = XMLDoc.documentElement.getElementsByTagName("Persona")[0].getElementsByTagName("Telematica")[0].getElementsByTagName("IdTel")[0].firstChild.data;
				dirTel = XMLDoc.documentElement.getElementsByTagName("Persona")[0].getElementsByTagName("Telematica")[0].getElementsByTagName("DireccionTel")[0].firstChild.data;
				tipo = XMLDoc.documentElement.getElementsByTagName("Persona")[0].getElementsByTagName("Telematica")[0].getElementsByTagName("TipoTel")[0].firstChild.data;
			}

			if (XMLDoc.documentElement.getElementsByTagName("Persona")[0].getElementsByTagName("Tipo")[0].firstChild.data == "1"){
				apellido1 = XMLDoc.documentElement.getElementsByTagName("Persona")[0].getElementsByTagName("Apellido1")[0].firstChild.data;
				apellido2 = XMLDoc.documentElement.getElementsByTagName("Persona")[0].getElementsByTagName("Apellido2")[0].firstChild.data;

				if (apellido2 != "") {apellido1 = apellido1 + " " + apellido2;}
				name = apellido1 + ", " + name;
			}

			if(idDom != ""){
				InsertInt(id, name, idDom, dom, 0);
			}else{
				InsertInt(id, name, idDirTel, dirTel, tipo);
			}
		}
		else if (size > 1){
			var maxResult = 0;
			if (XMLDoc.documentElement.getElementsByTagName("MaxResult").length != 0){
				maxResult = parseInt(XMLDoc.documentElement.getElementsByTagName("MaxResult")[0].firstChild.data, 10);
			}
			if (maxResult > 0 && size > maxResult){
				alert(top.GetIdsLan("IDS_BUSINTER_MAX_RESULT"));
			} else {
				doBlur = SelectInt(Action, XMLDoc);
			}
		}

		validInt.value = "";

		if (!doBlur) {
			validInt.focus();
		}

		if (doBlur) {
			try	{
				top.Main.Folder.FolderData.FolderFormData.cambioValor(parent.document.getElementById("Interesados"));
			}
			catch(e){}
		}
	}
}


function NewInt()
{
	var caseSensitive = parent.document.getElementById("Interesados")
			.getAttribute("caseSensitive");
	var URL = top.g_URL + "/dlgnewint.htm?SessionPId=" + top.g_SessionPId
		+ "&PermsCreate=" + top.g_CreateInterPerms.toString()
		+ "&Idioma=" + top.Idioma
		+ "&numIdioma=" + top.numIdioma;
	var strRet = top.ShowModalDialog(URL, null, 250, 450, "");

	if ((strRet != "") && (strRet != null)){
		var arguments = new Array();
		URL = top.g_URL + "/vldnewinter.htm?SessionPId=" + top.g_SessionPId
			+ "&PersonId=0&PersonType=" + strRet
			+ "&Idioma=" + top.Idioma
			+ "&numIdioma=" + top.numIdioma
			+ "&caseSensitive=" + caseSensitive;

		arguments[0] = "";
		arguments[1] = "";
		arguments[2] = "";
		arguments[3] = "";
		arguments[4] = "";

		strRet = top.ShowModalDialog(URL, arguments, 450, 580, "");

		if ( (strRet) && (strRet != "") ) {
			var arrTokens = top.getTokens(strRet, "|", "||", 9);

			// Se aniade desde aqui
			var validInt = document.getElementById("validInt");
			if (validInt != null){
				validInt.value = arrTokens[8];

				if ((validInt.value != null) && (validInt.value != "")){
					ValidInt();
				}
			}
			// hasta aqui y que sustituye desde aqui
			//InsertInt(arrTokens[0], arrTokens[1], arrTokens[4], arrTokens[3]);
			// hasta aqui
		}
		else {
			top.setFocus(document.getElementById("validInt"));
		}
	}

	else {
		top.setFocus(document.getElementById("validInt"));
	}
}


function SelectInt(Action, XMLDoc)
{
	var args = new Array();
	var sRet;
	var selected = false;

	args[0] = XMLDoc;
	args[1] = top.g_URL;
	args[2] = top.g_SessionPId.toString();
	args[3] = "0";
	args[4] = Action;
	args[5] = top.Idioma;
	args[6] = top.GetIdsLan( "IDS_INTS_SELECT");

	document.body.style.cursor = "wait";

	sRet = top.ShowModalDialog(top.g_URL + "/dlglist.htm", args, 550, 750, "");

	if ((sRet != null) && (sRet != ""))	{
		var arrTokens = top.getTokens(sRet, "#", "#", 8);

		if(arrTokens[3] != ""){
			InsertInteresados(arrTokens[2], arrTokens[1], arrTokens[4], arrTokens[3], arrTokens[7]);
		}else{
			InsertInteresados(arrTokens[2], arrTokens[1], arrTokens[6], arrTokens[5], arrTokens[7]);
		}

		selected = true;
	}

	document.body.style.cursor = "cursor";

	return (selected);
}