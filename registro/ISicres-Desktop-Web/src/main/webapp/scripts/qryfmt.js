var FldId         = "";
var OnHelpWindow  = false;

// Tamano del formulario
var g_FormWidth   = 0;
var g_FormHeight  = 0;
var g_TypeSearch = 0;
var NUMERO_FILA;

function OpenVldHlpWnd(URL, Name, Width, Height, Scroll)
{
	window.open(URL, "Vld","location=no",true);

	top.g_WndVld.document.getElementById("Vld").style.left = "5px";
	top.g_WndVld.document.getElementById("Vld").style.top  = "5px";
	top.g_WndVld.document.getElementById("Vld").style.height = Height;
	top.g_WndVld.document.getElementById("Vld").style.width =  Width;
	top.g_WndVld.document.getElementById("Vld").style.display = "block";

}

function VldHelp(aEvent, ObjId, TblFldId, TblValidated, caseSensitive)
{
	top.g_WndVld = top.Main.Workspace.Query;
	top.g_FormVld = top.Main.Workspace.Query.document.getElementById('QryFmtForm');
	top.g_Field = document.getElementById(ObjId);
	top.g_VldPath = "top.Main.Workspace.Query" ;


	if (TblValidated != "0") {
		var URL = top.g_URL + "/mainvld.htm?SessionPId=" + top.g_SessionPId
			+ "&ArchivePId=" + top.g_ArchivePId
			+ "&FldId=" + ObjId
			+ "&tblvalidated=" + TblValidated
			+ "&TblFldId=" + TblFldId
			+ "&Idioma=" + top.Idioma.toString()
			+ "&caseSensitive=" + caseSensitive
			+ "&Enabled=0&IsDtrList=0";

		OnHelpWindow = true;
		OpenVldHlpWnd(URL, 'name', '97%', '97%', 'no');
	}

	top.PreventDefault(aEvent);
	top.StopPropagation(aEvent);
}


function OnWindowLoad()
{
	var inputs = document.getElementsByTagName("input");

	// Mostramos la imagenes de los campos de ayuda
	for (var i = 0; i < document.images.length; i++){
		// Ponemos las imagenes de la ayuda
		if (document.images[i].src.indexOf("buscar2.gif") != -1 || document.images[i].src.indexOf("calendarM.gif") != -1){
			var inputsImg = document.getElementsByName(document.images[i].id);

			if (inputsImg.length != 0) {
				with (document.images[i].style) {
					display = "block";
					if (document.images[i].src.indexOf("buscar2.gif") != -1){
						width = 17;
						height = 17;
					}else{
						width = 20;
						height = 15;
					}
					left = parseInt(inputsImg[0].style.left, 10) + parseInt(inputsImg[0].style.width, 10) + 5;
					top = parseInt(inputsImg[0].style.top, 10) + parseInt(inputsImg[0].style.height, 10) - 17;
				}
			}
		}
	}

	// Le damos el foco al primer campo del formulario
	top.setFormFocus(document.getElementById("QryFmtForm"), g_FormWidth, g_FormHeight);
	// permitimos que vuelva a tener foco el frame
	top.g_TreeFunc=true;
	top.Main.Workspace.Query.tabIndex = "1";

	if (inputs != null)	{
		for (i = 0; i < inputs.length; i++){
			if ((inputs[i].id == g_FLD_DATEREG) && (inputs[i].value == "")){
				inputs[i].value = FormatDate(new Date());
			}
		}
	}

	initCalendar();

	return;
}

function showOrder()
{
	top.SetOpacity(document.getElementById("tdLEST"), 20);
	top.SetOpacity(document.getElementById("tabBTN"), 20);
	top.SetOpacity(document.getElementById("cabecera_seccion_tab"), 20);
	top.SetOpacity(document.getElementById("cabecera_seccion_tab_advanced"), 20);
	DisabledTool();
	SetEnabledLEST(false);
	SetEnabledBluetoolbar(false);
	top.Main.Workspace.Query.document.getElementById("divOrder").style.display = "block";
	top.Main.Workspace.Query.document.getElementById("divSearch").style.display = "none";

}


function closeOrder()
{
	document.getElementById("divOrder").style.display = "none";
	document.getElementById("divSearch").style.display = "block";
	SetEnabledLESTParent(true);
	SetEnabledBluetoolbarParent(true);
	parent.EnabledTool();
	top.SetOpacity(parent.document.getElementById("tdLEST"), 100);
	top.SetOpacity(parent.document.getElementById("tabBTN"), 100);
	top.SetOpacity(parent.document.getElementById("cabecera_seccion_tab"), 100);
	top.SetOpacity(parent.document.getElementById("cabecera_seccion_tab_advanced"), 100);
}


function SetEnabledLEST(bSet)
{
	for (var i = 0; i < frames.length; i++){
		if (frames[i].name == "LEST"){

			var tags = frames[i].document.getElementsByTagName("A");

			for (var j = 0; j < tags.length; j++){
				tags[j].setAttribute("disabled", bSet?"":"true");
			}

			tags = frames[i].document.getElementsByTagName("IMG");

			for (var j = 0; j < tags.length; j++){
				tags[j].setAttribute("disabled", bSet?"":"true");
			}

			break;
		}
	}
}

function SetEnabledLESTParent(bSet)
{
	for (var i = 0; i < parent.frames.length; i++){
		if (parent.frames[i].name == "LEST"){
			var tags = parent.frames[i].document.getElementsByTagName("A");

			for (var j = 0; j < tags.length; j++){
				tags[j].setAttribute("disabled", bSet?"":"true");
			}

			tags = parent.frames[i].document.getElementsByTagName("IMG");

			for (var j = 0; j < tags.length; j++){
				tags[j].setAttribute("disabled", bSet?"":"true");
			}

			break;
		}
	}
}

function SetEnabledBluetoolbar(bSet)
{

	var tags = document.getElementById("seccion_tab").getElementsByTagName("A");
	var tags_advanced = document.getElementById("seccion_tab_advanced").getElementsByTagName("A");

	for (var j = 0; j < tags.length; j++){
		tags[j].setAttribute("disabled", bSet?"":"true");
	}

	for (var j = 0; j < tags_advanced.length; j++){
		tags_advanced[j].setAttribute("disabled", bSet?"":"true");
	}
}

function SetEnabledBluetoolbarParent(bSet)
{

	var tags = parent.document.getElementById("seccion_tab").getElementsByTagName("A");
	var tags_advanced = parent.document.getElementById("seccion_tab_advanced").getElementsByTagName("A");

	for (var j = 0; j < tags.length; j++){
		tags[j].setAttribute("disabled", bSet?"":"true");
	}

	for (var j = 0; j < tags_advanced.length; j++){
		tags_advanced[j].setAttribute("disabled", bSet?"":"true");
	}
}

function DoSubmit()
{
	top.g_TreeFunc=false;
    top.Main.Workspace.DisabledTool();
	ClearAllInvalids();

	document.QryFmtForm.action = top.g_URL + "/tbltext.jsp?SessionPId=" + top.g_SessionPId
		+ "&FdrQryPId=" + top.g_FdrQryPId.toString()
		+ "&ArchivePId=" + top.g_ArchivePId.toString()
		+ "&TypeSearch=" + top.g_TypeSearch.toString();

	return true;
}

function ClearFields()
{
	ClearAllInvalids();
	top.Main.Workspace.Query.document.getElementById("QryFmtForm").elements[1].focus();
	top.Main.Workspace.Query.document.getElementById("QryFmtForm").reset();
}

function ClearFieldsReports()
{
	top.Main.Reports.ShowFmt.document.getElementById("QryFmtForm").elements[1].focus();
	top.Main.Reports.ShowFmt.document.getElementById("QryFmtForm").reset();
}


function ClearAllInvalids()
{
   var items = document.getElementsByTagName("*");

   for (var i = 0; i < items.length; i++){

		if ( items[i].style.backgroundColor == "red" )	{
			items[i].style.backgroundColor = "white";
		}
		if ( items[i].style.color == "red" )	{
			items[i].style.color = "#000000";
		}
	}
}


function SetBadField(FieldIDStr)
{
	var items = document.getElementsByTagName("*");

	for (var i = 0; i < items.length; i++){
		if ( (items[i].name == FieldIDStr) )	{
			items[i].style.color = "red";
			items[i].focus();
			items[i].select();
			break;
		}
	}
}


function OnBlurField( Field )
{
	var fldValidated = Field.getAttribute("Validated");

	if (fldValidated != "0"){
		Field.value = top.miTrim(Field.value).toUpperCase();
	}
	else {
		Field.value = top.miTrim(Field.value);
	}

	return;
}


function PulsarTecla( Field, aEvent )
{
	switch (top.GetKeyCode(aEvent))  {
		case(13):{
			OnBlurField( Field );
            break;
        }
	}

	return;
}


// Asigna el foco al control que ha lanzado las tablas de validacion
function AsigFocus()
{
	if ((top.g_Field) && (OnHelpWindow) ){
		OnHelpWindow = false;
		top.setFocus(top.g_Field);
	}
	else {
		//top.setFormFocus(document.getElementById("QryFmtForm"), g_FormWidth, g_FormHeight);
	}

	return;
}


// Asigna los tamanos del formulario a las variables globales
function getFormTam(strWidth ,strHeight)
{
   g_FormWidth    = parseInt(strWidth);
   g_FormHeight   = parseInt(strHeight);
   return;
}


// icono de ayuda en los campos
function imgHelpRpt(aEvent, ObjId, FldId, TblFldId, TblVal)
{
	if (TblVal != "0"){
		var args = new Array;
		var sRet = "";

		args[0] = top.g_URL;
		args[1] = top.g_SessionPId;
		args[2] = top.g_ArchivePId;
		args[3] = FldId;
		args[4] = TblVal;
		args[5] = TblFldId;
		args[6] = top.Idioma.toString();
		args[7] = 0;

		sRet = ValidateList(args);

		if (sRet && (sRet != "")){
			var tokens = new Array;
			var objFields = top.Main.Reports.ShowFmt.document.getElementsByName(ObjId);

			tokens = top.getTokens(sRet, "#", "#", 3);

			for (var i = 0; i < objFields.length; i++) {
				if (objFields[i].nodeName == "INPUT"){
					objFields[i].value = tokens[1];
				}
			}
		}
	}

	top.PreventDefault(aEvent);
}

function imgHelpRpt(aEvent, ObjId, FldId, TblFldId, TblVal, caseSensitive)
{
	if (TblVal != "0"){
		var args = new Array;
		var sRet = "";

		args[0] = top.g_URL;
		args[1] = top.g_SessionPId;
		args[2] = top.g_ArchivePId;
		args[3] = FldId;
		args[4] = TblVal;
		args[5] = TblFldId;
		args[6] = top.Idioma.toString();
		args[7] = 0;

		sRet = ValidateList(args, caseSensitive);

		if (sRet && (sRet != "")){
			var tokens = new Array;
			var objFields = top.Main.Reports.ShowFmt.document.getElementsByName(ObjId);

			tokens = top.getTokens(sRet, "#", "#", 3);

			for (var i = 0; i < objFields.length; i++) {
				if (objFields[i].nodeName == "INPUT"){
					objFields[i].value = tokens[1];
				}
			}
		}
	}

	top.PreventDefault(aEvent);
}

// rellena las variables globales gon los parametros obtenidos en el XML
function getParams(ArchiveName, ArchivePId, ArchiveId, FdrQryPId, Permisos, BookAdm)
{
	top.g_ArchiveName = ArchiveName;
	top.SetArchiveName(ArchiveName, "QRY");
	top.g_ArchivePId = ArchivePId;
	top.g_ArchiveId  = ArchiveId;
	top.g_FdrQryPId  = FdrQryPId;
	top.g_IsBookAdm = BookAdm;

	// Miramos los permisos del libro que hemos abierto
	if ( Permisos != "" )
	{
		top.g_BookPerms = Permisos;
	}

	// Si la oficina esta dada de baja solo permitir consulta sobre el libro.
	if (top.g_OfficeEnabled == "false")
	{
		top.g_BookPerms	= 1;
	}
}


function DoOnLoadResponse()
{
	var sRet, opc;
	var args = new Array;
	var URL = top.g_URL + "/report.htm";
	var index = top.Main.Reports.document.getElementById("SelPrint").selectedIndex;

	opc = top.Main.Reports.document.getElementById("SelPrint").options[index].value;

	args[0] = top.g_URL;
	args[1] = top.g_SessionPId.toString();
	args[2] = top.g_ArchivePId.toString();
	args[3] = top.g_FolderId.toString();
	args[4] = top.g_FdrQryPId.toString();
	args[5] = 3;
	args[6] = "";
	args[7] = top.Idioma.toString();
	args[8] = opc;

	sRet = top.ShowModalDialog(URL, args, 400, 450, "");

	DisableQryControls(false);
}


function DisableQryControls(val)
{
	var inputs = top.Main.Reports.ShowFmt.document.getElementsByTagName("INPUT");
	var imgs = top.Main.Reports.ShowFmt.document.getElementsByTagName("IMG");
	var bluetoolbar = top.Main.Reports.document.getElementsByTagName("A");

	for (var i = 0; i < inputs.length; i++){
		if (inputs[i].className == "button") {
			inputs[i].disabled = val;
		}
	}

	for (i = 0; i < imgs.length; i++){
		imgs[i].disabled = val;
	}

	if (val == true){
		for (i = 0; i < bluetoolbar.length; i++){
			bluetoolbar[i].className = "inactivo";
		}
	}
	else {
		for (i = 0; i < bluetoolbar.length; i++){
			bluetoolbar[i].className = "activo";
		}
	}

	if (val == true){
		top.Main.Reports.document.getElementById("Back").className = "SubOptionsDisabled";
	}
	else {
		top.Main.Reports.document.getElementById("Back").className = "Options";
	}
}

function DoSubmitReportFmt()
{
	ClearAllInvalids();
	document.QryFmtForm.action = top.g_URL + "/QryGetWhere.jsp?SessionPId=" + top.g_SessionPId
		+ "&FdrQryPId=" + top.g_FdrQryPId.toString()
		+ "&ArchivePId=" + top.g_ArchivePId.toString();

	return true;
}

function aggregateOrder(selAll)
{
	var listFields = document.getElementById("listFields");
	var listOrder = document.getElementById("listOrder");

	for (var i = 0; i < listFields.options.length; i++){
		var opt = listFields.options[i];


		if (opt.selected || selAll) {
			var oOption = document.createElement("OPTION");

			listOrder.options.add(oOption);
			oOption.text = opt.text + " (ASC)";
			oOption.value = opt.value;
			oOption.setAttribute("sense", "ASC");
			oOption.setAttribute("label", oOption.text);
		}
	}

	for (var i = listFields.options.length - 1; i >= 0; i--){
		var opt = listFields.options[i];

		if (opt.selected || selAll){
			listFields.options[i] = null;
		}
	}
}

function removeOrder(selAll)
{
	var listFields = document.getElementById("listFields");
	var listOrder = document.getElementById("listOrder");

	for (var i = 0; i < listOrder.options.length; i++){
		var opt = listOrder.options[i];

		if (opt.selected || selAll) {
			var oOption = document.createElement("OPTION");

			listFields.options.add(oOption);
			var posicionRecortar = opt.getAttribute("label").indexOf("(",0);
			var nameCampo = opt.getAttribute("label").substring(0,posicionRecortar);
			oOption.text = nameCampo;
			oOption.value = opt.value;

		}
	}

	for (var i = listOrder.options.length - 1; i >= 0; i--){
		var opt = listOrder.options[i];

		if (opt.selected || selAll){
			listOrder.options[i] = null;
		}
	}
}

function upOrder()
{
	var listOrder = document.getElementById("listOrder");

	for (var i = 0; i < listOrder.options.length; i++){
		var opt = listOrder.options[i];

		if (opt.selected && (i != 0)) {
			var antOpt = listOrder.options[i - 1];
			var text = opt.text, val = opt.value, sense = opt.getAttribute("sense");
			var label = opt.getAttribute("label");

			opt.value = antOpt.value;
			opt.text = antOpt.text;
			opt.selected = false;
			opt.setAttribute("sense", antOpt.getAttribute("sense"));
			opt.setAttribute("label", antOpt.getAttribute("label"));
			antOpt.value = val;
			antOpt.text = text;
			antOpt.selected = true;
			antOpt.setAttribute("sense", sense);
			antOpt.setAttribute("label", label);
		}
	}
}


function downOrder()
{
	var listOrder = document.getElementById("listOrder");

	for (var i = listOrder.options.length - 1; i >= 0; i--){
		var opt = listOrder.options[i];

		if (opt.selected && (i != listOrder.options.length - 1)) {
			var nextOpt = listOrder.options[i + 1];
			var text = opt.text, val = opt.value, sense = opt.getAttribute("sense");
			var label = opt.getAttribute("label");

			opt.value = nextOpt.value;
			opt.text = nextOpt.text;
			opt.selected = false;
			opt.setAttribute("sense", nextOpt.getAttribute("sense"));
			opt.setAttribute("label", nextOpt.getAttribute("label"));
			nextOpt.value = val;
			nextOpt.text = text;
			nextOpt.selected = true;
			nextOpt.setAttribute("sense", sense);
			nextOpt.setAttribute("label", label);
		}
	}
}


function changeSense()
{
	var listOrder = document.getElementById("listOrder");

	for (var i = 0; i < listOrder.options.length; i++){
		var opt = listOrder.options[i];

		if (opt.selected) {
			var valSense = (opt.getAttribute("sense")=="ASC")?"DESC":"ASC";

			opt.setAttribute("sense", valSense);

			if (opt.getAttribute("sense")=="ASC"){
				opt.text = opt.text.replace("(DESC)","(ASC)");
			}
			else{
				opt.text = opt.text.replace("(ASC)","(DESC)");
			}

			opt.label = opt.text;
		}
	}
}


function OnOK()
{

	getOrderQuery();

	document.getElementById('btSubmit').click();
}

function changeTypeSearchSimple(){

	document.getElementById("seccion_tab").style.display = "block";
	document.getElementById("seccion_tab_advanced").style.display = "none";

	var URL = top.g_URL + "/qryfmtmp.jsp?SessionPId=" + top.g_SessionPId + "&ArchiveId=" + top.g_ArchiveId
			+ "&ArchivePId=" + top.g_ArchivePId.toString();

	top.g_typeSearchAdvanced = false;
	window.open(URL, "Query","location=yes",true);

}

function changeTypeSearchAvanced(){

	document.getElementById("seccion_tab").style.display = "none";
	document.getElementById("seccion_tab_advanced").style.display = "block";

	var URL = top.g_URL + "/qryinitadvansearch.jsp?SessionPId="
			+ top.g_SessionPId
			+ "&ArchiveId=" + top.g_ArchiveId
			+ "&ArchivePId=" + top.g_ArchivePId.toString();

	top.g_typeSearchAdvanced = true;
	window.open(URL, "Query","location=yes",true);
}

function CargarOperadores(statusIndex){

	document.getElementById("method").value ="loadDatos";

	document.getElementById("oSelectOperador_" + statusIndex).value = "";
	document.getElementById("where_" + statusIndex).value = "";

	document.getElementById("QryFmtForm").submit();

}

function addTableRow(){

	// Ocultamos las imagenes de campo invalido para que por defecto no salgan
	ClearAllInvalids();

	NUMERO_FILA = getNumeroFila();
	NUMERO_FILA++;

	setNumeroFila(NUMERO_FILA);

	var NUMERO_INDICE = getNumeroFila() - 1;

	var tabla = document.getElementById("tblCondiciones");
    var tbody = tabla.getElementsByTagName("tbody").item(0);

    tbody.appendChild(getTableRow(NUMERO_INDICE));

    return NUMERO_INDICE;

}

function getTableRow(NUMERO_INDICE)
{
    var row = creaElemento("<tr>");

    var td = document.createElement("td");
	td.innerHTML = "<input type='hidden' name='numregistro' id='numregistro' value='"+ NUMERO_INDICE + "'> <select class='comboSearchAvanced' name='oSelectCampo_"+ NUMERO_INDICE +"' id='oSelectCampo_"+ NUMERO_INDICE +"' onchange='CargarOperadores(" + NUMERO_INDICE + ")' tabindex='1'><option value=''></option></select>";
    row.appendChild(td);

	td = document.createElement("td");
	td.innerHTML = "<select class='comboSearchAvanced' name='oSelectOperador_"+ NUMERO_INDICE +"' id='oSelectOperador_"+ NUMERO_INDICE +"' tabindex='1'></select>";
    row.appendChild(td);

	td = document.createElement("td");
	td.innerHTML = "<input type='text' name='where_"+ NUMERO_INDICE +"' id='where_"+ NUMERO_INDICE +"' class='inputTextSearchAvanced' tabIndex='1'></input>";
    row.appendChild(td);

    td = document.createElement("td");
    td.innerHTML = "<select name='nexo_"+ NUMERO_INDICE +"' id='nexo_"+ NUMERO_INDICE +"' class='comboSearchAvancednexo' tabIndex='1'><option value='or'>"+ top.GetIdsLan('IDS_SEARCH_LABEL_NEXO_OR')+"</option><option value='and'>"+ top.GetIdsLan('IDS_SEARCH_LABEL_NEXO_AND')+"</option></select>";
    row.appendChild(td);

	td = document.createElement("td");
	td.innerHTML = "<img src='images/masCampos.gif' id='btnAdd_" + NUMERO_INDICE + "'  name='btnAdd_" + NUMERO_INDICE + "' onclick='javascript:newTableRow(); HabilitarBotones()' style='visibility:hidden'/> <img src='images/menosCampos.gif' id='btnDelete_" + NUMERO_INDICE + "'  name='btnDelete_" + NUMERO_INDICE + "' onclick='javascript: HabilitarBotones(); removeTableRows(" + NUMERO_INDICE + ")'/>"
	row.appendChild(td);

    return row;
}

function removeTableRows(id)
{
	var tabla = document.getElementById("tblCondiciones");
	var rows = document.getElementsByName("numregistro");

	for (var i = 0; i < rows.length; i++)
	{
		if (rows[i].value==id)
        {
		tabla.deleteRow(rows[i].parentNode.parentNode.rowIndex);
        }
	}

	document.getElementById("contadorreg").value = document.getElementById("contadorreg").value - 1;

	document.getElementById("method").value ="deleteRow";

	document.getElementById("QryFmtForm").submit();

}


function getNumeroFila(){

	if (document.getElementById("contadorreg").value!=""){
		return parseInt(document.getElementById("contadorreg").value,10);
	}
	else
		return 1;
}

function setNumeroFila(valor){

	document.getElementById("contadorreg").value = valor;
}


function creaElemento(datosElemento){
	//si el elemento contiene "<" -> el parametro es codigo HTML
	//sino se crea y se devuelve el elemento creado
	datosElemento=trim(datosElemento);

	var pos=datosElemento.indexOf("<");
	if(pos==-1 || document.all){ return document.createElement(datosElemento); }

	//es codigo html y no estamos usando IExplorer

	//obtener el tipo de parametro a crear (finaliza con el caracter espacio o con "/")
	var posSeparador=datosElemento.indexOf(" ");
	if(posSeparador==-1){
		posSeparador=datosElemento.indexOf("/");
		if(posSeparador==-1){
			posSeparador=datosElemento.indexOf(">");
			if(posSeparador==-1){ return null; }
		}
	}
	var tipoElemento=datosElemento.substring(1,posSeparador);
	var elemento=document.createElement(tipoElemento);

	//trim de la cadena restante
	var cadenaAtributos=trim(datosElemento.substring(posSeparador+1));

	//recorrer la cadena restante y extraer los atributos nombreAtributo="'valorAtributo'"
	//Se detecta el final de un atributo, por las comilla simple.
	//para obtener la cadena completa de un atributo hay que buscar las dos comillas
	//tener en cuenta que esta no puede estar precedida de una "\" (caracter de escape)

	do{
		var posComilla1=getPosComillaSimple(0,cadenaAtributos);
		var posComilla2=0;
		var posSeparador=0;

		if(posComilla1!=-1){
			posComilla2=getPosComillaSimple(posComilla1+1,cadenaAtributos);
			if(posComilla2==-1){
				return elemento;
			}
			posComilla2=posComilla2+posComilla1+1;
			posSeparador=posComilla2+1;
		}else{
			var posSeparador=cadenaAtributos.indexOf(" ");
			if(posSeparador==-1){
				posSeparador=cadenaAtributos.indexOf("/");
				if(posSeparador==-1){
					posSeparador=cadenaAtributos.indexOf(">");
				}
			}
		}

		if(posSeparador!=-1){
			var cadenaAtrib=trim(cadenaAtributos.substring(0,posSeparador));
			cadenaAtributos=trim(cadenaAtributos.substring(posSeparador));

			//valor de atributo solo puede estar separado por "="
			//para evitar el problema con los espacios extras se utiliza trim
			var posIgual=cadenaAtrib.indexOf("=");
			if(posIgual==-1){ return elemento; }

			var nombreAtributo=trim(cadenaAtrib.substring(0,posIgual));
			var valorAtributo="";
			if(posComilla1==-1){
				valorAtributo=trim(cadenaAtrib.substring(posIgual+1));
				valorAtributo=valorAtributo.replace(/'/g,"");
			}else{
				valorAtributo=trim(cadenaAtrib.substring(posComilla1+1,posComilla2));
			}
			//alert(nombreAtributo+'='+valorAtributo);
			elemento.setAttribute(nombreAtributo,valorAtributo);
		 }
	}while(posSeparador!=-1);

	return elemento;
}

function trim(cadena)
{
	if (cadena=="") return cadena;
	for(i=0; i<cadena.length; )
	{
		if(cadena.charAt(i)==" ")
			cadena=cadena.substring(i+1, cadena.length);
		else
			break;
	}

	for(i=cadena.length-1; i>=0; i=cadena.length-1)
	{
		if(cadena.charAt(i)==" ")
			cadena=cadena.substring(0,i);
		else
			break;
	}
	return cadena;
}

function addOptionInSelect(select,option){
	try{
		select.add(option);
	}catch(e){
		select.add(option,null);
	}
}

  /**
  * Obtiene la posicion de la primera comilla simple valida de la cadena.
  * Es valida si no va precedida del caracter de escape "\"
  */
function getPosComillaSimple(inicio,cadena){
	var cadenaAux=cadena.substring(inicio);
	var posComilla=0;
	do{
		posComilla=cadenaAux.indexOf("'");
		if(posComilla==-1){
			return -1;
		}
		var carAnterior=cadenaAux.charAt(posComilla-1);
		if(carAnterior=="\\"){
			cadenaAux=cadenaAux.substring(posComilla+1);
		}
		}while(carAnterior=="\\");
		return posComilla;
 }

function doSearchAvanced(){

	top.Main.Workspace.Query.document.getElementById("method").value ="search";
	top.Main.Workspace.Query.document.getElementById("QryFmtForm").submit();

}


function OnWindowLoadAdvSearch()
{
	//Comprobamos resolucion de pantalla para aniadir scroll y modificar las posiciones de las imagenes
	if (screen.width<1280){
		document.getElementById("divSearch").style.overflow = "scroll";
		document.getElementById("divSearch").style.width = "730px";
		document.getElementById("divSearch").style.height = "510px";

		for(var i=0; i<document.getElementById("contadorreg").value; i++){
			if(document.getElementById("imghelp_" +i)){
				document.getElementById("imghelp_" +i).style.left="119px";
				document.getElementById("imghelp_" +i).style.top="-15px";
			}
		}
	}

	// Habilitamos los botones adecuados de la barra de herramientas
	top.Main.Workspace.EnabledTool();
	// Le damos el foco al primer campo del formulario
	top.setFormFocus(document.getElementById("QryFmtForm"), g_FormWidth, g_FormHeight);
	top.g_TreeFunc=true;
	top.Main.Workspace.Query.tabIndex = "1";

	initCalendar();

	return;
}

function HabilitarBotones(){

	HabilitarDeshabilitarAdd();
	HabilitarDeshabilitarDelete();
	HabilitarDeshabilitarNexo();

}

function HabilitarDeshabilitarAdd(){
	//habilita el ultimo boton de anadir

	var contRegistros = document.getElementById("contadorreg").value;

	if((document.getElementById("oSelectCampo_" + (contRegistros-1)).value != ' ') && (document.getElementById("oSelectCampo_" + (contRegistros-1)).value != '')){
		document.getElementById("btnAdd_" + (contRegistros-1)).style.visibility="visible";
	}

	if (contRegistros > 1){
		for(var i=0; i<contRegistros-1; i++){
			document.getElementById("btnAdd_" + i).style.visibility="hidden";
		}
	}
	else{
		if(document.getElementById("oSelectCampo_0").value != ' ' && (document.getElementById("oSelectCampo_0").value != '')){
			document.getElementById("btnAdd_0").style.visibility="visible";
		}else{
			document.getElementById("btnAdd_0").style.visibility="hidden";
		}
	}
}

function HabilitarDeshabilitarDelete(){
	//evalua para activar el boton borrar
	var contRegistros = document.getElementById("contadorreg").value;

	if (contRegistros > 1){
		for (var i = 0; i < contRegistros; i++){
			document.getElementById("btnDelete_" + i).style.visibility="visible";
		}
	}else
	{
		document.getElementById("btnDelete_0").style.visibility="hidden";
	}
}

function HabilitarDeshabilitarNexo(){

	//evalua para activar el combobox de Nexo
	var contRegistros = document.getElementById("contadorreg").value;

	if (contRegistros > 0){
		for (var i = contRegistros -1 ; i >= 0; i--){
			if (i == contRegistros - 1) {
				document.getElementById("nexo_" + i).disabled=true;
				document.getElementById("nexo_" + i).value = " ";
			} else {
				document.getElementById("nexo_" + i).disabled=false;
				if((document.getElementById("nexo_" + i)) && (document.getElementById("nexo_" + i).value!="and")){
					document.getElementById("nexo_" + i).options[0].selected = true;
				}
			}
		}
	}
}

function getOrderQuery(){
	var listOrder = document.getElementById("listOrder");
	var data = "";

	for (var i = 0; i < listOrder.options.length; i++){
		var opt = listOrder.options[i];

		if (data == ""){
			data = opt.value + " " + opt.getAttribute("sense");
		}
		else {
			data = data + "," + opt.value + " " + opt.getAttribute("sense");
		}
	}

	g_TypeSearch=0;
	document.getElementById("dataOrder").value = data;
}

function evaluateCaseSensitive(nameCampo, caseSensitive){

	if (caseSensitive!="CI"){
		document.getElementById(nameCampo).value = document.getElementById(nameCampo).value.toUpperCase();
	}
}

/**
 * Función que se utiliza para generar la consulta por el combo del campo 503 (Involucrado inter. registral)
 * @param fldId - Id del campo
 */
function changeComboIntRegistral(fldId){
	//obtenemos los datos del combo de Inter. Registral
	var combo = document.getElementById("combo_" + fldId);

	//Buscamos los combos con los operadores
	var combosOperadores = document.getElementsByTagName("select");
	var comboOperador;
	var input = document.getElementById(fldId);

	for (var i = 0; i < combosOperadores.length; i++){
		// Entre los diferentes combos comprobamos el que cumpla que tiene el
		// atributo fld con el id del campo 503
		if(combosOperadores[i].getAttribute("comboFldId") == fldId){
			comboOperador = combosOperadores[i];
		}
	}

	//asignamos los parámetros de búsqueda
	asignarDatosSearchByIntercambioRegistral(combo, input, comboOperador);

}

function changeComboIntRegistralSearchAdvan(combo, statusIndex){
	//comprobamos el valor del combo de Inter. Registral
	var comboOperador = document.getElementById("oSelectOperador_" + statusIndex);
	var input = document.getElementById("where_" + statusIndex);

	//asignamos los parámetros de búsqueda
	asignarDatosSearchByIntercambioRegistral(combo, input, comboOperador);

}

/**
 * Función que asigna los parámetros de búsqueda cuando buscamos por el campo de
 * invol. intercambio registral
 *
 * @param combo -
 *            Opción de búsqueda (si ha sido inv. intercambio registral o no)
 * @param input -
 *            Input en el que se insertará el valor a buscar (1 - Invol.
 *            Intercambio Reg / 0 o vacio - No ha sido incluido en un
 *            intercambio registral)
 * @param comboOperador - Combo con el operador de búsqueda (igual, distinto...)
 */
function asignarDatosSearchByIntercambioRegistral(combo, input, comboOperador){
	//comprobamos el valor del combo de Inter. Registral
	switch(combo.value)
	{
		case "0":
			//no incluido en inter. registral
			//búscamos todos los registros que el campo 503 sea distintos a 1
			input.value = "1";
			comboOperador.selectedIndex = 1;
			break;
		case "1":
			//SI ha sido incluido en inter. registral
			//búscamos todos los registros que el campo 503 sea igual a 1
			input.value = "1";
			comboOperador.selectedIndex = 0;
			break;
		default:
			//cualquier otro valor
			//No se realiza búsqueda por este campo
			input.value = "";
			comboOperador.selectedIndex = 0;
	}
}