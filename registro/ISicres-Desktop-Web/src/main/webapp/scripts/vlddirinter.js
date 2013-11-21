var g_timerID;


// Pone el returnValue de la ventana con el texto y el id seleccionados
function SelectDir()
{
	if (!document.getElementById("btnAceptar").disabled)  {
		var iIndex = oListDir.getListSelectedIndex();
		var oElem = oListDir.oRows[iIndex].oColumns[0];
		var strPref = oElem.GetArgColumn("PreferenceDir");

		if (strPref == -1) {
			strPref = "0"
		}

		var strPob = oListDir.oRows[iIndex].oColumns[2].Text;
		var strCiu = oListDir.oRows[iIndex].oColumns[3].Text;
		var strCod = oListDir.oRows[iIndex].oColumns[1].Text;
		var strText = oElem.Text + " " + strCod + " " + strPob + " " + strCiu;
		var strRet = oElem.Id + "|" + strText + "|" + strPref + "||";

		window.returnValue = strRet;
		window.close();
	}

	return;
}


// Funcion que recarga el formulario de ciudades
function CargarCiudades()
{
	var oList = document.getElementById("oSelectProv");
	var index = document.getElementById("oSelectProv").selectedIndex;

	EraseCiudades();

	if (oList.options[index].value != "0")  {
		var valor = oList.options[index].text;
		var URL = "vldciudad.jsp?SessionPId=" + top.ParamValue(top.document.location.search, "SessionPId") + "&ProvId=" + oList.options[index].value;

		top.XMLHTTPRequestGet(URL, ResponseLoadCities, true);
	}

	return;
}


function ResponseLoadCities()
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
	}
	else if (top.miTrim(XMLText) != "") {
		var XMLDoc = top.g_oXMLHTTPRequest.responseXML;
		var datas = XMLDoc.documentElement.getElementsByTagName("Ciudad");

		if (datas != null){
			for (var i = 0; i < datas.length; i++) {
				var opt = document.createElement("OPTION");

				document.getElementById("oSelectCiu").appendChild(opt);

				opt.text = datas[i].getElementsByTagName("Nombre")[0].firstChild.data;
				opt.value = datas[i].getElementsByTagName("Id")[0].firstChild.data;
				opt.setAttribute("Code", datas[i].getElementsByTagName("Codigo")[0].firstChild.data);
			}

			//Posible error de Internet explorer. Hay que hacerlo asi
			g_timerID = setTimeout("SelectCiudades()", 1);

		}
	}
}


function EraseCiudades()
{
	for (var i = document.getElementById("oSelectCiu").options.length - 1; i >= 0; i--){
		if (document.getElementById("oSelectCiu").options[i].value != "0"){
			document.getElementById("oSelectCiu").options[i]= null;
		}
	}
}


function SelectCiudades()
{
	var tabDir = document.getElementById("tbDoms");
	var index = getSelectedDir(tabDir);

	clearTimeout(g_timerID);

	if (index != -1){
		var City = top.GetInnerText(tabDir.rows[index].cells[3]);

		for (var i = 0; i < document.getElementById("oSelectCiu").options.length; i++){
			if (document.getElementById("oSelectCiu").options[i].text == City) {
				document.getElementById("oSelectCiu").selectedIndex = i;
				break;
			}
		}
	}
	else {
		document.getElementById("oSelectCiu").selectedIndex = 0;
	}

}


function SetTabSize()
{
	var tabDoms = document.getElementById("tbDoms");
	var rowNull = document.getElementById("null");
	var space = (document.getElementById("oDivTabDom").clientHeight) - tabDoms.clientHeight + rowNull.clientHeight - 10;

	if (space > 13)	{
		rowNull.height = space.toString();
	} else {
		rowNull.height = 13;
	}
}

function SetTabSizeTel()
{
	var tabDirs = document.getElementById("tbDirs");
	var rowNullTel = document.getElementById("null2");
	var space = (document.getElementById("oDivTabDir").clientHeight) - tabDirs.clientHeight + rowNullTel.clientHeight - 10;

	if (space > 13)	{
		rowNullTel.height = space.toString();
	} else {
		rowNullTel.height = 13;
	}
}


function insertRowDom(domId, Dir, CodPostal, City, Prov, principal)
{
	var tabDir = document.getElementById("tbDoms");

	with (tabDir){
		var newRow = insertRow(tabDir.rows.length - 1);
		var datas = new Array;
		var newCell = new Array;

		newRow.id = "rowDatas";
		newRow.className="Style5";

		newCell[0] = newRow.insertCell(-1);
		newCell[0].width="3%";
		newCell[0].setAttribute("id", domId);

		datas[0]=document.createElement("input");
		datas[0].setAttribute ("type", "checkbox");
		datas[0].setAttribute("class", "checkbox");
		top.AttachEvent(datas[0], "click", CheckDir);

		newCell[0].appendChild(datas[0]);

		newCell[1] = newRow.insertCell(-1);
		newCell[1].width="44%";
		top.SetInnerText(newCell[1], Dir);

		newCell[2] = newRow.insertCell(-1);
		newCell[2].width="12%";
		top.SetInnerText(newCell[2], CodPostal);

		newCell[3] = newRow.insertCell(-1);
		newCell[3].width="15%";
		top.SetInnerText(newCell[3], City);

		newCell[4] = newRow.insertCell(-1);
		newCell[4].width="20%";
		top.SetInnerText(newCell[4], Prov);

		newCell[5] = newRow.insertCell(-1);
		newCell[5].width="6%";

		// Input hidden para almacenar si el domicilio es principal
		datas[1]=document.createElement("input");
		datas[1].setAttribute ("type", "hidden");

		// Imagen que indica si el domicilio es principal
		datas[2]=document.createElement("img");
		datas[2].setAttribute ("src", "./images/asterisk_orange.png");
		datas[2].setAttribute("title",top.GetIdsLan("IDS_DIR_PRINCIPAL"));
		datas[2].setAttribute("alt",top.GetIdsLan("IDS_DIR_PRINCIPAL"));

		if (principal == 1) {
			datas[1].setAttribute("value", 1);
			newCell[5].appendChild(datas[1]);
			newCell[5].appendChild(datas[2]);
		} else {
			newCell[5].appendChild(datas[1]);
			datas[1].setAttribute("value", 0);
		}
	}
}

function insertRowTel(telId, DirTel, TypeDirTel, principal)
{
	var tabDirTel = document.getElementById("tbDirs");

	with (tabDirTel){
		var newRow = insertRow(tabDirTel.rows.length - 1);
		var datas = new Array;
		var newCell = new Array;
		var descriptionDirtel;

		newRow.id = "rowDatasTel";
		newRow.className="Style5";

		newCell[0] = newRow.insertCell(-1);
		newCell[0].width="4%";
		newCell[0].setAttribute("id", telId);

		datas[0]=document.createElement("input");
		datas[0].setAttribute ("type", "checkbox");
		datas[0].setAttribute("class", "checkbox");
		top.AttachEvent(datas[0], "click", CheckDirTel);

		newCell[0].appendChild(datas[0]);

		newCell[1] = newRow.insertCell(-1);
		newCell[1].width="45%";
		top.SetInnerText(newCell[1], DirTel);

		newCell[2] = newRow.insertCell(-1);
		newCell[2].width="45%";
		for (var i = 0; i < document.getElementById("oSelectDir").options.length; i++){
			if (document.getElementById("oSelectDir").options[i].value == TypeDirTel) {
				descriptionDirtel = document.getElementById("oSelectDir").options[i].text;
				break;
			}
		}
		top.SetInnerText(newCell[2], descriptionDirtel);

		newCell[3] = newRow.insertCell(-1);
		newCell[3].width="6%";

		// Input hidden para almacenar si el domicilio es principal
		datas[1]=document.createElement("input");
		datas[1].setAttribute ("type", "hidden");

		datas[2]=document.createElement("img");
		datas[2].setAttribute ("src", "./images/asterisk_orange.png");
		datas[2].setAttribute("title",top.GetIdsLan("IDS_DIR_PRINCIPAL"));
		datas[2].setAttribute("alt",top.GetIdsLan("IDS_DIR_PRINCIPAL"));

		if (principal == 1) {
			datas[1].setAttribute("value", 1);
			newCell[3].appendChild(datas[1]);
			newCell[3].appendChild(datas[2]);
		} else {
			datas[1].setAttribute("value", 0);
			newCell[3].appendChild(datas[1]);
		}
	}
}


// Anade una direccion a la lista de direcciones
function AddDir()
{
	var indexCity = document.getElementById("oSelectCiu").selectedIndex;
	var indexProv = document.getElementById("oSelectProv").selectedIndex;

	var optCiu = (indexCity != 0)?document.getElementById("oSelectCiu").options[indexCity].value:"0";
	var optProv = (indexProv != 0)?document.getElementById("oSelectProv").options[indexProv].value:"0";

	if ( (top.miTrim(document.getElementById("oTxtDir").value) == "") || (optCiu == "0") || (optProv == "0") ) {
		alert(top.GetIdsLan("IDS_DIRPOS_REQUERIDOS"));
	}
	else {
		if(caseSensitive == 'CS') {
			document.getElementById("oTxtDir").value = document.getElementById("oTxtDir").value.toUpperCase();
			document.getElementById("oTxtCod").value = document.getElementById("oTxtCod").value.toUpperCase();
		}

		// La direccion principal es la que primero aparece en la lista
		var principal = 1;
		var tabDir = document.getElementById("tbDoms");
		for (var i = tabDir.rows.length - 1; i >= 0; i--){
			if (tabDir.rows[i].id == "rowDatas"){
				if (tabDir.rows[i].cells[0] != null) {
					principal = 0;
				}
			}
		}

		insertRowDom("0", document.getElementById("oTxtDir").value, document.getElementById("oTxtCod").value,
			(indexCity != 0)?document.getElementById("oSelectCiu").options[indexCity].text:"",
			(indexProv != 0)?document.getElementById("oSelectProv").options[indexProv].text:"",
			principal);

		SetTabSize();

		document.getElementById("oTxtDir").value = "";
		document.getElementById("oTxtCod").value = "";
		document.getElementById("oSelectCiu").selectedIndex = 0;
		document.getElementById("oSelectProv").selectedIndex = 0;

		EraseCiudades();

		HabilitarAceptar();
	}
}

function AddDirTel()
{
	if (changeComboTel()){
		var indexTel = document.getElementById("oSelectDir").selectedIndex;

		if ( (top.miTrim(document.getElementById("oTxtDirTel").value) == "") || (indexTel == "0") ) {
			alert(top.GetIdsLan("IDS_DIRTEL_REQUERIDOS"));
		}
		else {

			// La direccion principal es la que primero aparece en la lista
			var principal = 1;
			var tabDir = document.getElementById("tbDirs");
			for (var i = tabDir.rows.length - 1; i >= 0; i--){
				if (tabDir.rows[i].id == "rowDatasTel"){
					if (tabDir.rows[i].cells[0] != null) {
						principal = 0;
					}
				}
			}

			insertRowTel("0", document.getElementById("oTxtDirTel").value,
			document.getElementById("oSelectDir").options[indexTel].value,
			principal);

			SetTabSizeTel();

			document.getElementById("oTxtDirTel").value = "";
			document.getElementById("oSelectDir").selectedIndex = 0;

			HabilitarAceptar();
		}
	}
}

// Borra todas las direcciones seleccionadas
function DelDirs()
{
	var tabDir = document.getElementById("tbDoms");
	var ppal = false;
	var primera = false;

	var numRowsCheck = getNumberOfRowsSelected(tabDir, "rowDatas");

	if(numRowsCheck <= 0){
		alert(top.GetIdsLan("IDS_MSGALERT_DELET_TERCEROS"));
	}
	else{
		for (var i = tabDir.rows.length - 1; i >= 0; i--){
			if (tabDir.rows[i].id == "rowDatas"){
				if (tabDir.rows[i].cells[0].firstChild.checked == true){
					tabDir.rows[i].style.display = "none";
					tabDir.rows[i].cells[0].firstChild.checked = false;
					if(tabDir.rows[i].cells[5].firstChild.getAttribute("value") == 1){
						if (i == 1){
							primera = true;
						}
						ppal = true;
					}
				}
			}
		}

		SetTabSize();

		document.getElementById("oTxtDir").value = "";
		document.getElementById("oTxtCod").value = "";
		document.getElementById("oSelectProv").selectedIndex = 0;
		document.getElementById("oSelectProv").onchange();

		if (primera){
			if(tabDir.rows[2].cells[0].firstChild != null){
				tabDir.rows[2].cells[0].firstChild.checked = true;
				SetDirPrinc('tbDoms','rowDatas',5);
			}
		}else{
			if(ppal && (tabDir.rows[1].cells[0] != null)){
				tabDir.rows[1].cells[0].firstChild.checked = true;
				SetDirPrinc('tbDoms','rowDatas',5);
			}
		}

		HabilitarAceptar();
	}
}

function DelDirsTel()
{
	var tabDir = document.getElementById("tbDirs");
	var ppal = false;
	var primera = false;

	var numRowsCheck = getNumberOfRowsSelected(tabDir, "rowDatasTel");

	if(numRowsCheck <= 0){
		alert(top.GetIdsLan("IDS_MSGALERT_DELET_TERCEROS"));
	}
	else{
		for (var i = tabDir.rows.length - 1; i >= 0; i--){
			if (tabDir.rows[i].id == "rowDatasTel"){
				if (tabDir.rows[i].cells[0].firstChild.checked == true){
					tabDir.rows[i].style.display = "none";
					tabDir.rows[i].cells[0].firstChild.checked = false;
					if(tabDir.rows[i].cells[3].firstChild.getAttribute("value") == 1){
						if (i == 1){
							primera = true;
						}
						ppal = true;
					}
				}
			}
		}

		SetTabSizeTel();

		document.getElementById("oTxtDirTel").value = "";
		document.getElementById("oSelectDir").selectedIndex = 0;

		if (primera){
			if(tabDir.rows[2].cells[0].firstChild != null){
				tabDir.rows[2].cells[0].firstChild.checked = true;
				SetDirPrinc('tbDirs','rowDatasTel',3);
			}
		}else{
			if(ppal && (tabDir.rows[1].cells[0] != null)){
				tabDir.rows[1].cells[0].firstChild.checked = true;
				SetDirPrinc('tbDirs','rowDatasTel',3);
			}
		}

		HabilitarAceptar();
	}
}

function getSelectedDir(tbl)
{
	var index = -1;

	for (var i = 0; i < tbl.rows.length; i++){
		if ((tbl.rows[i].id == "rowDatas") || (tbl.rows[i].id == "rowDatasTel")  ){
			if (tbl.rows[i].cells[0].firstChild.checked == true){
				index = i;
				break;
			}
		}
	}

	return (index);
}


function CheckDir()
{
	var tabDir = document.getElementById("tbDoms");
	var index = getSelectedDir(tabDir);

	document.getElementById("oTxtDir").value = (index != -1)?top.GetInnerText(tabDir.rows[index].cells[1]):"";
	document.getElementById("oTxtCod").value = (index != -1)?top.GetInnerText(tabDir.rows[index].cells[2]):"";

	if (index != -1){
		var Prov = top.GetInnerText(tabDir.rows[index].cells[4]);

		for (var i = 0; i < document.getElementById("oSelectProv").options.length; i++){
			if (document.getElementById("oSelectProv").options[i].text == Prov) {
				document.getElementById("oSelectProv").selectedIndex = i;
				break;
			}
		}

		CargarCiudades();
	}
	else {
		document.getElementById("oSelectProv").selectedIndex = 0;
		EraseCiudades();
	}

}

function CheckDirTel()
{
	var tabDirTel = document.getElementById("tbDirs");
	var index = getSelectedDir(tabDirTel);

	document.getElementById("oTxtDirTel").value = (index != -1)?top.GetInnerText(tabDirTel.rows[index].cells[1]):"";

	if (index != -1){
		var TypeTel = top.GetInnerText(tabDirTel.rows[index].cells[2]);

		for (var i = 0; i < document.getElementById("oSelectDir").options.length; i++){
			if (document.getElementById("oSelectDir").options[i].text == TypeTel) {
				document.getElementById("oSelectDir").selectedIndex = i;
				break;
			}
		}

	}
	else {
		document.getElementById("oSelectDir").selectedIndex = 0;
	}

}

function ModifDir()
{
	var tabDir = document.getElementById("tbDoms");
	var index = getSelectedDir(tabDir);

	if (index <= 0){
		alert(top.GetIdsLan("IDS_MSGALERT_MODIF_TERCEROS"));
	}
	else
	{
		var indCity = document.getElementById("oSelectCiu").selectedIndex;
		var indProv = document.getElementById("oSelectProv").selectedIndex;

		if(caseSensitive == 'CS') {
			document.getElementById("oTxtDir").value = document.getElementById("oTxtDir").value.toUpperCase();
			document.getElementById("oTxtCod").value = document.getElementById("oTxtCod").value.toUpperCase();
		}

		top.SetInnerText(tabDir.rows[index].cells[1], document.getElementById("oTxtDir").value);
		top.SetInnerText(tabDir.rows[index].cells[2], document.getElementById("oTxtCod").value);
		top.SetInnerText(tabDir.rows[index].cells[3], (indCity != 0)?document.getElementById("oSelectCiu").options[indCity].text:"");
		top.SetInnerText(tabDir.rows[index].cells[4], (indProv != 0)?document.getElementById("oSelectProv").options[indProv].text:"");
	}

	HabilitarAceptar();
}

function ModifDirTel()
{
	if (changeComboTel()){
		var tabDir = document.getElementById("tbDirs");
		var index = getSelectedDir(tabDir);

		if (index <= 0){
			alert(top.GetIdsLan("IDS_MSGALERT_MODIF_TERCEROS"));
		}
		else
		{
			var indDir = document.getElementById("oSelectDir").selectedIndex;

			if(caseSensitive == 'CS') {
				document.getElementById("oTxtDirTel").value = document.getElementById("oTxtDirTel").value.toUpperCase();
			}

			top.SetInnerText(tabDir.rows[index].cells[1], document.getElementById("oTxtDirTel").value);
			top.SetInnerText(tabDir.rows[index].cells[2], (indDir != 0)?document.getElementById("oSelectDir").options[indDir].text:"");
		}

		HabilitarAceptar();
	}
}


// Selecciona el campo de la combo correspondiente
function SelectTypeDoc(oCombo, strValue)
{
   var bFound = false;
   for( var ii=0; (ii < oCombo.options.length) && (!bFound); ii++)
   {
      if (oCombo.options.item(ii).value == strValue)
      {
         bFound = true;
         oCombo.options.item(ii).selected = bFound;
      }
   }
   return;
}

//Habilita el boton Aceptar
function HabilitarAceptar(){

	if (((document.getElementById("oPerApe1").value!="") && (document.getElementById("oPerNombre").value!=""))
		|| document.getElementById("oRazon").value!="")
	{
		document.getElementById("btnOK").disabled = false;
	}
	else
	{
		document.getElementById("btnOK").disabled = true;
	}

	return;
}


function doBlur(obj)
{
	var desc = top.miTrim(obj.value);

	obj.value = top.Replace(desc);
}

function SetDirPrinc(tbId, rowsId, cellId) {

	var tabDir = document.getElementById(tbId);
	var index = getSelectedDir(tabDir);
	var datas = new Array;
	var numCheck = 0;

	//validamos que no haya mas de una direccion seleccionada
	for (var j = tabDir.rows.length - 1; j >= 0; j--){
		if (tabDir.rows[j].id == rowsId){
			if (tabDir.rows[j].cells[0].firstChild.checked == true){
				numCheck += 1;
			}
		}
	}
	if (numCheck <= 0){
		alert(top.GetIdsLan("IDS_MSGALERT_MODIF_TERCEROS"));
	}
	else if(numCheck > 1){
		alert(top.GetIdsLan("IDS_ERRORSELECCION"));
	}else{
		if (index != -1){
			for (var i = tabDir.rows.length - 1; i >= 0; i--){
				if (tabDir.rows[i].id == rowsId){
					if (tabDir.rows[i].cells[cellId].hasChildNodes()) {
					while (tabDir.rows[i].cells[cellId].childNodes.length >= 1 ) {
						tabDir.rows[i].cells[cellId].removeChild(tabDir.rows[i].cells[cellId].firstChild );
					}
					}
					if (tabDir.rows[i].cells[0].firstChild.checked == true){
						tabDir.rows[i].cells[0].firstChild.checked = false;

						// Input hidden para almacenar si el domicilio es principal
						datas[0]=document.createElement("input");
						datas[0].setAttribute ("type", "hidden");
						datas[0].setAttribute("value", 1);

						// Imagen que indica si el domicilio es principal
						datas[1]=document.createElement("img");
						datas[1].setAttribute ("src", "./images/asterisk_orange.png");
						datas[1].setAttribute("title",top.GetIdsLan("IDS_DIR_PRINCIPAL"));
						datas[1].setAttribute("alt",top.GetIdsLan("IDS_DIR_PRINCIPAL"));

						tabDir.rows[i].cells[cellId].appendChild(datas[0]);
						tabDir.rows[i].cells[cellId].appendChild(datas[1]);

					} else {
						datas[0]=document.createElement("input");
						datas[0].setAttribute ("type", "hidden");
						datas[0].setAttribute("value", 0);
						tabDir.rows[i].cells[cellId].appendChild(datas[0]);
					}
				}
			}
		}
	}

	HabilitarAceptar();
}

// Obtiene el numero de elementos seleccionados
function getNumberOfRowsSelected(tabla, nameRow)
{
	var numCheck = 0;

	with (tabla){
		for (var i = 0; i < rows.length; i++){
			if (rows[i].id == nameRow){
				if (rows[i].cells[0].firstChild.checked == true){
					numCheck += 1;
				}
			}
		}
	}
	return (numCheck);
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