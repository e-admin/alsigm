var g_ArchiveName = "";
var g_MaxCharArchiveName = 30;

var g_Maximized = false;
var g_PrevFormTreeWidth = 250;
var g_SessionPId     = "";
var g_ArchivePId     = 0;
var g_ArchiveId      = 0;
var g_ArchiveName    = "";
var g_FdrQryPId      = 0;
var g_AppId          = 1;
var g_FolderPId      = -1;
var g_FdrReadOnly    = true;
var g_CloseFolder    = 1;
var g_ShowTable      = true;

var g_WndVld = null;
var g_FormVld = null;

// Para controlar las pulsaciones sobre el arbol de libros de registro
var g_TreeFunc = true;
var g_OpcAval = true;

// Pagina seleccionada en la carpeta que se esta visualizando
var g_Page = 0;
var g_PrevPage = -1;

// Navegacion y FolderViews
var g_FolderView  = false;

var g_URL = "";

var g_FolderSel = -1;
var g_FolderId  = -1;
var g_DistId  = -1;

var g_BookPerms = 1; // por defecto tenemos permiso de consulta
var g_CreateInterPerms = 0; // por defecto no podemos crear interesados
var g_ModifyInterPerms = 0; // por defecto no podemos modificar interesados

// Para controlar sucesivos clicks sobre una misma carpeta
var g_sinPulsar = true;

// Para controlar si se abre una carpeta, si es nueva o si es un bucle
var g_OpenType = 0;
var g_Field = null; // Campo que lanza la ayuda

// para deshabilitar el arbol de la izquierda en el formulario
var g_ActivateTree = false;

var g_bIsBucle = false;
var g_LoadForm = false;

// para saber si es la primera vez que se abre la ventana de carpeta
var g_bIsNewFolder = false;
// Es una array con los CtrId que son ReadOnly en el formulario
var g_ArrFldsReadOnly = null;

//indica que el libro abierto esta bloqueado
var g_bIsLockBook = true;

// controla que no se habra mas de dos veces la ventana de ayuda
var g_oHelpWindow = null; // referencia a la ventana de ayuda

// array para hacer la copia del array de modificaciones que es persistente
var g_oArrCopiaFldData  = null;

// Array con los ficheros escaneados que se tienen que borrar del local despues del upload
var g_ArrScanFiles   = null;

// Control del unload de la ventana principal para no hacer dos veces logout
var g_bIsUnload      = false;

// para saber cuando estamos guardando una carpeta
var g_bIsSaved=false;

// para saber si se abre una carpeta desde la distribucion
var g_OpenFolderDtr=false;

// para saber si se abre una carpeta desde url
var g_OpenFolderPenDtr=false;

// para saber si se abre la carpeta desde distribución en modo edición
var g_OpenEditDistr = false;

//IDENTIFICADORES de CAMPOS
var g_FLD_DATEREG = 2;

//Indica el id. de registro a copiar
var g_CopyFdr = 0;

//Indica si hay algo pendiente de guardar
var g_SavePending = false;

var g_ArrFdrSelected = new Array();

//Indica que se ha producido en error en un evento de validacion
var g_ErrorOnValidate = false;
var g_IsBookAdm = false;

var g_UseOSAuth = false;
var g_UseLDAP = false;
var g_UserDN = "";
var g_CanChangePassword = true;

var g_FirstReg = 0;
var g_LastReg = 0;

//Variable para XMLHTTPRrequest
var g_oXMLHTTPRequest = null;
var oXMLHTTPRequest = null;

//Para ventanas modales en Mozilla
var g_WinModal = null;

var g_CaseSensitive = "";
var g_CodeAsocReg = "";
var g_typeSearchAdvanced = false;
var g_TypeSearch = 0;

// Para abrir registros en vista formulario cuando el usuario es de consulta
var g_Form = false;

//Indica si la oficina actual esta habilitada (true), en caso contrario estaria dada de baja y no se podrian dar de alta registros.
var g_OfficeEnabled = "";

var g_frmStrUpdate; //Se usara para guardar el formulario con los datos modificados de la carpeta, usado en la parte de subir ficheros "SendScanFilesToServer"


//variable que indica si al abrir la ventana de busqueda de interesados desde el formulario del registro tiene que realizar alguna operativa
var g_actionInitFormInter = null;

// variable que se utiliza para saber si el registro ha sido modificado y
// esta pendiente de guardar (se utiliza para el uso de teclas de acceso rapido, y llevar un control del boton guardar)
var g_changeDataRegistro = false;

//variable que indica si un usuario puede borrar ficheros adjuntados a un registro
var g_deleteFilePerms = 0;


function FormDataLoaded()
{
	var strParams = "";

	// Lo hacemos aqui porque es cuando aseguramos que estan todos los frames cargados
	if ( g_FolderView )	{
		strParams = "ArchiveId=" + g_ArchiveId.toString();
		strParams += "&ArchivePId=" + g_ArchivePId.toString();
		strParams += "&SessionPId=" + g_SessionPId.toString();
		strParams += "&FolderId=" + g_FolderId.toString();
		strParams += "&FolderPId=" + g_FolderPId.toString();
		strParams += "&FdrQryPId=" + top.g_FdrQryPId.toString();
		strParams += "&Row=" + top.g_FolderSel.toString();
		strParams += "&Form=" + top.g_Form;
		strParams += "&OpenFolderDtr=" + top.g_OpenFolderDtr.toString();
		strParams += "&OpenEditDistr=" + top.g_OpenEditDistr.toString();

		if (top.g_CopyFdr != 0)	{
			strParams += "&CopyFdr=" + top.g_CopyFdr.toString();
		}

		top.g_bIsNewFolder = true;

		switch (top.g_OpenType){
			case 1: {
				top.XMLHTTPRequestGet(top.g_URL + "/newfolder.jsp?" + strParams, ResponseNewFolder, true);
				break;
			}
			default: {
				top.XMLHTTPRequestGet(top.g_URL + "/openfolder.jsp?" + strParams, ResponseOpenFolder, true);
				break;
			}
		}
	}
}


function ResponseNewFolder()
{
	if (top.g_oXMLHTTPRequest.readyState != 4){
		return;
	}

	if (top.g_oXMLHTTPRequest.status != 200){
		alert(top.g_oXMLHTTPRequest.statusText + " (" + top.g_oXMLHTTPRequest.status.toString() + ")");
		return;
	}

	var HTMLText = top.g_oXMLHTTPRequest.responseText;

	if (HTMLText.indexOf("alert(") != -1){
		evalAlert(HTMLText);
	}
	else{
		top.SetFolderFormTree(HTMLText);
	}
}


function ResponseOpenFolder()
{
	if (top.g_oXMLHTTPRequest.readyState != 4){
		return;
	}

	if (top.g_oXMLHTTPRequest.status != 200){
		alert(top.g_oXMLHTTPRequest.statusText + " (" + top.g_oXMLHTTPRequest.status.toString() + ")");
		return;
	}

	var HTMLText = top.g_oXMLHTTPRequest.responseText;

	if (HTMLText.indexOf("alert(") != -1){
		try {
			top.Main.Folder.FolderBar.bLoadForm = true;
			window.open(top.g_URL + "/fldbarupdate.htm", "FolderBar", "location=no", true);
			top.g_Page = 0;
			top.g_sinPulsar = true;
			top.g_OpcAval = true;

			if (top.g_FolderView){
				window.open(top.g_URL + "/tb_folder.htm", "ToolBarFrm", "location=no", true);
				top.Main.Folder.ToolBarFrm.ToolBarEnabled();
				top.g_ActivateTree = true;
				top.g_bIsNewFolder = false;
			}
			else{
				window.open(top.g_URL + "/tb_form.htm", "ToolBarFrm", "location=no", true);
			}

			top.ShowForm();
			top.Main.Folder.ToolBarFrm.habilitar();
			top.g_ActivateTree = true;
			top.g_bIsNewFolder = false;
		}
		catch(e){}

		evalAlert(HTMLText);
	}
	else{
		top.SetFolderFormTree(HTMLText);
	}
}


function ResponseNewFldBuc()
{
	if (top.g_oXMLHTTPRequest.readyState != 4){
		return;
	}

	if (top.g_oXMLHTTPRequest.status != 200){
		alert(top.g_oXMLHTTPRequest.statusText + " (" + top.g_oXMLHTTPRequest.status.toString() + ")");
		return;
	}

	var HTMLText = top.g_oXMLHTTPRequest.responseText;

	if (HTMLText.indexOf("alert(") != -1){
		try {
			top.Main.Folder.FolderBar.bLoadForm = true;
			window.open(top.g_URL + "/fldbarupdate.htm", "FolderBar", "location=no", true);
			top.g_Page = 0;
			top.g_OpcAval = true;

			if (top.g_FolderView){
				window.open(top.g_URL + "/tb_folder.htm", "ToolBarFrm", "location=no", true);
				top.Main.Folder.ToolBarFrm.ToolBarEnabled();
				top.g_ActivateTree = true;
				top.g_bIsBucle = false;
				top.g_LoadForm = false;
			}
			else{
				window.open(top.g_URL + "/tb_form.htm", "ToolBarFrm", "location=no", true);
			}

			top.ShowForm();
			top.Main.Folder.ToolBarFrm.habilitar();
			top.g_ActivateTree = true;
			top.g_bIsBucle = false;
			top.g_LoadForm = false;
		}
		catch(e){}

		evalAlert(HTMLText);
	}
	else{
		top.SetFolderFormTree(HTMLText);

		// se hace una copia de la parte del array de modificaciones que es persistente
		CopyPersistentArray(top.Main.Folder.FolderData.FolderFormData.document.getElementById('FrmData'));
	}
}

function ResponseSendIntercambioRegistral()
{
	if (top.g_oXMLHTTPRequest.readyState != 4){
		return;
	}

	if (top.g_oXMLHTTPRequest.status != 200){
		alert(top.g_oXMLHTTPRequest.statusText + " (" + top.g_oXMLHTTPRequest.status.toString() + ")");
		return;
	}

	var HTMLText = top.g_oXMLHTTPRequest.responseText;
	evalAlert(HTMLText);
}


function ResponseFrmData()
{
	if (top.g_oXMLHTTPRequest.readyState != 4){
		return;
	}

	if (top.g_oXMLHTTPRequest.status != 200){
		alert(top.g_oXMLHTTPRequest.statusText + " (" + top.g_oXMLHTTPRequest.status.toString() + ")");
		return;
	}

	var HTMLText = top.g_oXMLHTTPRequest.responseText;


	if (HTMLText.indexOf("alert(") != -1){
		try {
			top.g_ActivateTree=true;
			top.ShowForm();

			if(top.g_FolderView){
				top.Main.Folder.ToolBarFrm.ToolBarEnabled();
			}
			else {
				top.Main.Folder.ToolBarFrm.habilitar();
			}
		}
		catch(excep){}

		evalAlert(HTMLText);
	}
	else {
		var doc = top.Main.Folder.FolderData.FolderFormData.document;

		doc.body.innerHTML = HTMLText;

		top.Main.Folder.FolderData.FolderFormData.getParamsPersons(doc.getElementById("CanUpdatePer").value,
			doc.getElementById("CanAddPer").value);
		top.Main.Folder.FolderData.FolderFormData.getFormTam(doc.getElementById("Width").value,
			doc.getElementById("Height").value);

		if (doc.getElementById("Interesados")){
			doc.getElementById("Interesados").src = "frmint.htm";
		}

		top.Main.Folder.FolderData.FolderFormData.OnWindowLoad();
		top.Main.Folder.FolderData.FolderFormData.modificaciones();
		top.Main.Folder.FolderData.FolderFormData.MarkBadFields();
		top.Main.Folder.FolderData.FolderFormData.getTemplateForm();

		doc.body.style.cursor = "cursor";

		if( top.g_FolderId != -1 ){
			// Activamos botón Nuevo Registro
			asignarClassName("NewBtn", "SubOptions");
			// Activamos botón Intercambio Registral
			asignarClassName("SendIRBtn", "SubOptions");
			// Activamos botón Limpiar
			asignarClassName("ClearRegBtn", "SubOptions");
			// Activamos botón Imprimir
			asignarClassName("btnPrintReg", "SubOptions");
			// Activamos botón Sello
			asignarClassName("selloReg", "SubOptions");
			// Activamos botón Compulsa
			asignarClassName("compulsa", "SubOptions");
		}

		setTimeout("top.Main.Folder.FolderData.FolderFormData.SetFormFocus()", 10);
	}
}

// Funcion que aplica el estilo que se indique como parametro al boton indicado
function asignarClassName(boton, className){
	//comprobamos si el boton existe
	if(top.Main.Folder.FolderBar.document.getElementById(boton)){
		//si existe le aplicamos el estilo
		top.Main.Folder.FolderBar.document.getElementById(boton).className=className;
	}
}

function CallActionForm(Action, Code, FldId, Init, fnCallback)
{
	var URL = top.g_URL + "/actionform.jsp?SessionPId=" + top.g_SessionPId.toString()
		+ "&InitValue=" + Init.toString() + "&Code=" + escape(Code) + "&FldId=" + FldId.toString();
	var params = "";

	if (Action == "ValidateUnit"){
		params = "&Action=ValidateUnit";
	}
	else if (Action == "ValidateInt"){
		params = "&Action=ValidateInt";
	}
	else if (Action == "OtherOffices"){
		params = "&Action=OtherOffices";
	}

	URL += params;

	top.XMLHTTPRequestGet(URL, fnCallback, true);
}


function ResponseActionForm()
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
	}
	else {
		var Action = Action = XMLDoc.documentElement.getElementsByTagName("Action")[0].firstChild.data;;

		if (Action == "ValidateUnit"){
			top.Main.Folder.FolderData.FolderFormData.ValidateUnit(XMLDoc);
		}
		else if (Action == "OtherOffices"){
			top.ValidateOtherOffices(XMLDoc);
		}
		else if (Action == "ValidateInt") {
			top.Main.Folder.FolderData.FolderFormData.Interesados.ValidateInt(XMLDoc);
		}
	}
}


function ValidateOtherOffices(XMLDoc)
{
	document.body.style.cursor = "cursor";

	if ((XMLDoc == null) || (XMLDoc.documentElement == null)){
		eval(top.g_oXMLHTTPRequest.responseText);
	}
	else{
		var args = new Array();
		var sRet;
		var Action = XMLDoc.documentElement.getElementsByTagName("Action")[0].firstChild.data;

		args[0] = XMLDoc;
		args[1] = top.g_URL;
		args[2] = top.g_SessionPId.toString();
		args[3] = "0";
		args[4] = Action;
		args[5] = top.Idioma;
		args[6] = top.GetIdsLan( "IDS_OPCCHANGEOFFICE");

		document.body.style.cursor = "wait";

		sRet = top.ShowModalDialog(top.g_URL + "/dlglist.htm", args, 550, 750, "");

		if ((sRet != null) && (sRet != ""))	{
			var arrTokens = top.getTokens(sRet, "#", "#", 2);
			var URL = top.g_URL + "/changeoffice.jsp?SessionPId=" + top.g_SessionPId.toString()
				+ "&OfficeCode=" + arrTokens[0];

			top.g_TreeFunc = false;

			window.open(URL, "LEST","location=no",true);
		}
	}
}


function SetFolderFormTree(HTMLText)
{
	if (HTMLText.length < 10){return;}

	var doc = top.Main.Folder.FolderData.FolderFormTree.document;

	doc.body.innerHTML = HTMLText;

	top.SetInnerText(doc.getElementById("lbAnexos"), top.GetIdsLan("IDS_ANEXO"));

	top.Main.Folder.FolderData.FolderFormTree.getParams(parseInt(doc.getElementById("FolderPId").value, 10),
		parseInt(doc.getElementById("FdrReadOnly").value, 10), parseInt(doc.getElementById("FolderId").value, 10),
		parseInt(doc.getElementById("VldSave").value, 10));
	top.Main.Folder.FolderData.FolderFormTree.Row = 1;

    if (top.g_FolderView){
	window.open(top.g_URL + "/tb_folder.htm", "ToolBarFrm","location=no",true);
	}
	else {
		window.open(top.g_URL + "/tb_form.htm", "ToolBarFrm","location=no",true);
	}

	top.Main.Folder.FolderData.FolderFormTree.DeleteUL(doc.getElementById("UL0"));

	top.Main.Folder.FolderData.FolderFormTree.AddHistoricDistr();
	top.Main.Folder.FolderData.FolderFormTree.AddHistoricIntercambioRegistral();
	top.Main.Folder.FolderData.FolderFormTree.AddHistoricReg();
	top.Main.Folder.FolderData.FolderFormTree.AddAsocRegs();
	top.AttachEvent(doc.body, "click", function(){top.Main.Folder.FolderData.FolderFormTree.ChkClick();});

	if(!IsExplorerBrowser()){
		top.Main.Folder.FolderData.FolderFormTree.document.getElementById("tree").className= "tree_ff";
	}else{
		top.Main.Folder.FolderData.FolderFormTree.document.getElementById("tree").className= "tree_ie";
	}

	var funct = function(e){e = e || window.event; top.Main.Folder.FolderData.FolderFormTree.ChkKeyPress(e);};
	top.AttachEvent(doc.body, "keypress", funct);
}

function SetArchiveName(ArchiveName,type)
{
	g_ArchiveName = unescape(ArchiveName);

	if (ArchiveName.length > g_MaxCharArchiveName) {
		ArchiveName = g_ArchiveName.substr(0, g_MaxCharArchiveName - 3) + "...";
	}

	switch(type){
		case "QRY" : {
			top.Main.Workspace.document.getElementById("ArchiveName").innerHTML = unescape(ArchiveName);

			if (ArchiveName != "") {
				top.Main.Workspace.document.getElementById("ArchiveName").className = "BookNameEx";
			}
			else {
				top.Main.Workspace.document.getElementById("ArchiveName").className = "BookName";
			}
			break;
		}
		case "TBL": {
			top.Main.Table.TableData.document.getElementById("ArchiveName").innerHTML = unescape(ArchiveName);
			break;
		}
		case "FRM": {
			top.Main.Folder.ToolBarFrm.document.getElementById("ArchiveName").innerHTML = unescape(ArchiveName);
			break;
		}
		case "REP": {
			top.Main.Reports.document.getElementById("ArchiveName").innerHTML = unescape(ArchiveName);
			break;
		}
	}
}


function ToggleMaximized(strClass)
{
   if (strClass != "SubOptionsDisabled"){
      if (g_Maximized) {
         top.Main.Folder.FolderData.document.getElementById('FolderFSet').cols = g_PrevFormTreeWidth.toString() + "px, *";
         top.Main.Folder.ToolBarFrm.document.getElementById("ToggleBtn").innerHTML = "<div align=\"center\">" + top.GetIdsLan( "IDS_OPCMAXIMIZAR" ) + "</div>";
      }
      else {
         top.Main.Folder.ToolBarFrm.document.getElementById("ToggleBtn").innerHTML = "<div align=\"center\">" + top.GetIdsLan( "IDS_OPCRESTAURAR" ) + "</div>";
         g_PrevFormTreeWidth = top.Main.Folder.FolderData.document.getElementById("FolderFormTree").offsetWidth;
         top.Main.Folder.FolderData.document.getElementById('FolderFSet').cols = "0, *";
      }

      g_Maximized = !g_Maximized;
   }
}

function OpenNewWindow(URL, Name, Width, Height, Scroll,strResize)
{
	if( Width > screen.availWidth ) {
		Width = screen.availWidth;
	}

	if( Height > screen.availHeight ) {
		Height = screen.availHeight;
	}

	var winl = (screen.availWidth - Width) / 2;
	var wint = (screen.availHeight - Height) / 2;

	Props = 'height='+Height+',width='+Width+',top='+wint+',left='+winl+',scrollbars='+Scroll+',resizable='+strResize+',location=no';
	win = window.open(URL, Name, Props,true);

	if (parseInt(navigator.appVersion) >= 4) {
		win.window.focus();
	}
}


function ShowQuery()
{
	top.Main.document.getElementById('frSetWork').rows = "100%,*,*,*,*";

	if (top.g_ArchivePId != 0) {
		// Le damos el foco al primer campo del formulario
		top.setFormFocus(top.Main.Workspace.Query.document.getElementById("QryFmtForm"), top.Main.Workspace.Query.g_FormWidth, top.Main.Workspace.Query.g_FormHeight);
		top.g_TreeFunc=true;
	}

	// Activamos el frame workspace
	top.Main.document.getElementById("Workspace").tabIndex = "1";
	top.Main.document.getElementById("Table").tabIndex = "-1";
	top.Main.Table.document.getElementById("TableData").tabIndex = "-1";
	top.Main.document.getElementById("Folder").tabIndex = "-1";
	top.Main.Folder.document.getElementById("FolderBar").tabIndex = "-1";
	top.Main.Folder.document.getElementById("FolderData").tabIndex = "-1";
	top.Main.Folder.document.getElementById("ToolBarFrm").tabIndex = "-1";

	if (top.g_FolderView) {
	   top.Main.Folder.FolderData.FolderFormData.tabIndex = "-1";
	}

	top.Main.document.getElementById("Distr").tabIndex = "-1";
	top.Main.document.getElementById("Reports").tabIndex = "-1";
}

function ShowAdvancedQuery() {

	top.Main.document.getElementById('frSetWork').rows = "100%,*,*,*,*";

	// Activamos el frame workspace
	top.Main.document.getElementById("Workspace").tabIndex = "1";
	top.Main.document.getElementById("Table").tabIndex = "-1";
	top.Main.Table.document.getElementById("TableData").tabIndex = "-1";
	top.Main.document.getElementById("Folder").tabIndex = "-1";
	top.Main.Folder.document.getElementById("FolderBar").tabIndex = "-1";
	top.Main.Folder.document.getElementById("FolderData").tabIndex = "-1";
	top.Main.Folder.document.getElementById("ToolBarFrm").tabIndex = "-1";

	if (top.g_FolderView) {
	   top.Main.Folder.FolderData.FolderFormData.tabIndex = "-1";
	}

	top.Main.document.getElementById("Distr").tabIndex = "-1";
	top.Main.document.getElementById("Reports").tabIndex = "-1";

}

function ShowTableFr()
{
	top.Main.document.getElementById('frSetWork').rows = "*,100%,*,*,*";
	top.g_OpcAval=true;

	// Activamos el frame Table
	top.Main.document.getElementById("Workspace").tabIndex = "-1";
	top.Main.document.getElementById("Table").tabIndex = "1";
	top.Main.Table.document.getElementById("TableData").tabIndex = "1";
	top.Main.document.getElementById("Folder").tabIndex = "-1";
	top.Main.Folder.document.getElementById("FolderBar").tabIndex = "-1";
	top.Main.Folder.document.getElementById("FolderData").tabIndex = "-1";
	top.Main.Folder.document.getElementById("ToolBarFrm").tabIndex = "-1";

	if (top.g_FolderView){
		top.Main.Folder.FolderData.FolderFormData.tabIndex = "-1";
	}

	top.Main.document.getElementById("Distr").tabIndex = "-1";
	top.Main.document.getElementById("Reports").tabIndex = "-1";
}


function ShowForm()
{
	top.Main.document.getElementById("Folder").tabIndex = "1";
	top.Main.Folder.document.getElementById("FolderBar").tabIndex = "1";
	top.Main.Folder.document.getElementById("FolderData").tabIndex = "1";
	top.Main.Folder.document.getElementById("ToolBarFrm").tabIndex = "1";

	if (top.g_FolderView){
		top.Main.Folder.FolderData.FolderFormData.tabIndex = "1";
	}
	else {
		top.Main.document.getElementById('frSetWork').rows = "*,*,100%,*,*";

		// Desactivamos el resto de frames
		top.Main.document.getElementById("Workspace").tabIndex = "-1";
		top.Main.document.getElementById("Table").tabIndex = "-1";
		top.Main.Table.document.getElementById("TableData").tabIndex = "-1";
		top.Main.document.getElementById("Distr").tabIndex = "-1";
		top.Main.document.getElementById("Reports").tabIndex = "-1";
	}
}


function ShowDistribution(strClassName)
{
	if (strClassName != "SubOptionsDisabled"){
		top.Main.document.getElementById('frSetWork').rows = "*,*,*,100%,*";

		// Activamos el frame Distr
		top.Main.document.getElementById("Workspace").tabIndex = "-1";
		top.Main.document.getElementById("Table").tabIndex = "-1";
		top.Main.Table.document.getElementById("TableData").tabIndex = "-1";
		top.Main.document.getElementById("Folder").tabIndex = "-1";
		top.Main.Folder.document.getElementById("FolderBar").tabIndex = "-1";
		top.Main.Folder.document.getElementById("FolderData").tabIndex = "-1";
		top.Main.Folder.document.getElementById("ToolBarFrm").tabIndex = "-1";

		if (top.g_FolderView) {
			top.Main.Folder.FolderData.FolderFormData.tabIndex = "-1";
		}

		top.Main.document.getElementById("Distr").tabIndex = "1";
		top.Main.document.getElementById("Reports").tabIndex = "-1";

		top.Main.Distr.DoOnLoad(0);
	}
}

//Funcion que limpia el formulario del registro
function ClearFormRegister(strClassName){

	if (strClassName != "SubOptionsDisabled"){
		//Deshabilitamos el botón guardar, de esta forma no se muestra el alert de confirmación
		top.Main.Folder.FolderBar.document.getElementById("SaveMenuBtn").className = "SubOptionsDisabled";

		//Generamos el array de parámetros
		var strParams = "";
		strParams = "ArchiveId=" + g_ArchiveId.toString();
		strParams += "&ArchivePId=" + g_ArchivePId.toString();
		strParams += "&SessionPId=" + g_SessionPId.toString();
		strParams += "&FolderId=" + g_FolderId.toString();
		strParams += "&FolderPId=" + g_FolderPId.toString();
		strParams += "&FdrQryPId=" + top.g_FdrQryPId.toString();
		strParams += "&Row=" + top.g_FolderSel.toString();
		strParams += "&Form=" + top.g_Form;
		strParams += "&OpenFolderDtr=" + g_OpenFolderDtr.toString();

		//comprobamos si el limpiar se aplica sobre un registro abierto o un nuevo registro
		if ( (top.g_FolderId != -1)) {
			//si es sobre un registro abierto
			//cerramos los datos del registro (desbloqueamos el registro)
			top.CloseFolder();
			//recargamos los datos del registro en pantalla
			top.XMLHTTPRequestGet(top.g_URL + "/openfolder.jsp?" + strParams, ResponseOpenFolder, true);
		}else{
			//cargamos los datos para un nuevo registro
			top.XMLHTTPRequestGet(top.g_URL + "/newfolder.jsp?" + strParams, ResponseNewFolder, true);
		}
	}
}

function ChangeOffice(strClassName)
{
	if (strClassName != "SubOptionsDisabled"){
		top.CallActionForm("OtherOffices", "", 0, 0, ResponseActionForm);
	}
}


function Over(obj)
{
	if (obj.className != "SubOptionsDisabled"){
		obj.className="OptionsOver";
	}
}

function Out(obj)
{
	if (obj.className != "SubOptionsDisabled"){
		obj.className="Options";
	}
}

function NewFolder(obj)
{
	if (obj.className == "SubOptionsDisabled"){
		return;
	}

	g_TreeFunc = false;

	top.OpenNewWindow(top.g_URL + "/default.jsp?AppId=" + top.g_AppId.toString()
		+ "&SessionPId=" + g_SessionPId.toString()
        + "&FolderView=1&ArchiveId=" + g_ArchiveId.toString()
        + "&ArchiveName=" + g_ArchiveName.toString()
        + "&ArchivePId=" + g_ArchivePId.toString()
        + "&FolderPId=" + (top.g_FolderPId+1).toString()
        + "&Idioma=" + Idioma.toString()
        + "&numIdioma=" + numIdioma.toString()
        + "&OpenType=1", "", "10000", "10000", "auto","yes");

	g_TreeFunc = true;
}

function SendIntercambioRegistral(strClassName)
{
	var strParams = "";

	if (strClassName != 'SubOptionsDisabled'){
		if (!top.g_FdrReadOnly)	{
			if( top.Main.Folder.FolderBar.document.getElementById("SaveMenuBtn").className == "SubOptions" ){
	            if( !window.confirm( top.GetIdsLan( "IDS_QRYCONTGUARDAR" ) ) ) {
			       return;
				}
			}
		}
	}


	top.g_WndVld      = top.Main.Folder.FolderData.FolderFormData;
	top.g_FormVld = top.Main.Folder.FolderData.FolderFormData.document.getElementById("FrmData");

	strParams = "ArchiveId=" + g_ArchiveId.toString();
	strParams += "&SessionPId=" + g_SessionPId.toString();
	strParams += "&FolderId=" + g_FolderId.toString();
	strParams += "&FolderPId=" + g_FolderPId.toString();
	strParams += "&ArchivePId=" + g_ArchivePId.toString();
	strParams += "&FrmData=false";
	strParams += "&EnviarIR=1";

	window.open(top.g_URL + "/mainvld.htm?" + strParams, "Vld","location=no",true);

	top.g_WndVld.document.getElementById("Vld").style.left = "5px";
	top.g_WndVld.document.getElementById("Vld").style.top  = "5px";
	top.g_WndVld.document.getElementById("Vld").style.height = '97%';
	top.g_WndVld.document.getElementById("Vld").style.width =  '97%';
	top.g_WndVld.document.getElementById("Vld").style.display = "block";

	//top.XMLHTTPRequestGet(top.g_URL + "/EnviarIntercambiosRegistrales.do?" + strParams, ResponseSendIntercambioRegistral, true);
}

function NewFolderBucle(strClassName)
{
	var strParams = "";

	if (strClassName != 'SubOptionsDisabled'){
		if (!top.g_FdrReadOnly)	{
			if( top.Main.Folder.FolderBar.document.getElementById("SaveMenuBtn").className == "SubOptions" ){
	            if( !window.confirm( top.GetIdsLan( "IDS_QRYCONTGUARDAR" ) ) ) {
			top.Main.Folder.FolderBar.document.getElementById("SaveMenuBtn").focus();
			return;
				}
			}
		}

		strParams = "ArchiveId=" + g_ArchiveId.toString();
		strParams += "&SessionPId=" + g_SessionPId.toString();
		strParams += "&FolderId=" + g_FolderId.toString();
		strParams += "&FolderPId=" + g_FolderPId.toString();
		strParams += "&ArchivePId=" + g_ArchivePId.toString();
		strParams += "&FrmData=false";

		top.g_bIsBucle = true;
		top.g_LoadForm = true;

		top.g_ActivateTree = false;

		if(top.Main.Folder.FolderData.FolderFormData.document.getElementById("Vld")){
			top.Main.Folder.FolderData.FolderFormData.document.getElementById("Vld").style.display = "none";
		}

		if(top.Main.Folder.FolderData.FolderFormData.document.getElementById('FrmData')){
			top.Main.Folder.FolderData.FolderFormData.document.getElementById('FrmData').style.visibility = "visible";
		}
		top.XMLHTTPRequestGet(top.g_URL + "/newfldbuc.jsp?" + strParams, ResponseNewFldBuc, true);

		// Deshabilitamos las toolbar
		top.Main.Folder.ToolBarFrm.ToolBarDisabled();
	}

	return;
}


function Logout(bNotConfirm)
{
   if (! top.g_bIsUnload)
   {
      if (bNotConfirm)
      {
         top.g_bIsUnload = true;
         window.open(top.g_URL + "/logout.jsp?SessionPId=" + g_SessionPId
                  + "&AppId=" + g_AppId.toString()
                  + "&Idioma=" + top.Idioma.toString()
                  + "&numIdioma=" + top.numIdioma.toString()
                  + "&LoadDefault=true", "frBlank","location=no",true);

         top.g_SessionPId = "";
         return true;
      }
      else
      {
         if ( window.confirm(top.GetIdsLan( "IDS_MSG_EXIT" )) )
         {
            var gotoBlank = (top.g_UseLDAP && top.g_UseOSAuth);
            var loadDefault = (top.g_UseLDAP && top.g_UseOSAuth);

		top.g_bIsUnload = true;

			window.open(top.g_URL + "/logout.jsp?SessionPId=" + g_SessionPId
				+ "&AppId=" + g_AppId.toString()
                + "&Idioma=" + top.Idioma.toString()
                + "&numIdioma=" + top.numIdioma.toString()
                + "&LoadDefault=" + loadDefault.toString()
                + "&GotoBlank=" + gotoBlank.toString(), "_top","location=no",true);

            top.g_SessionPId = "";

            return true;
         }
         else
         {
            return false;
         }
      }
   }
}

function CloseFolder()
{
	var URL = top.g_URL + "/closefolder.jsp?SessionPId=" + g_SessionPId
		+ "&FolderPId=" + g_FolderPId.toString()
		+ "&ArchivePId=" + g_ArchivePId.toString()
		+ "&FolderId=" + g_FolderId.toString()
		+ "&ArchiveId=" + g_ArchiveId.toString();

	if ((g_FolderPId != -1) && (!top.g_FdrReadOnly || g_OpenFolderDtr)) {
		top.XMLHTTPRequestGet(URL, ShowCloseFolderResponse, false);
	}
}

function ShowCloseFolderResponse()
{
	if (top.g_oXMLHTTPRequest.readyState != 4){
		return;
	}

	if (top.g_oXMLHTTPRequest.status != 200){
		alert(top.g_oXMLHTTPRequest.statusText + " (" + top.g_oXMLHTTPRequest.status.toString() + ")");
		return;
	}

	var resp = top.g_oXMLHTTPRequest.responseText;

	if (resp != null){
		if (resp != ""){
			eval(resp);
		}
	}
}

function OpenFolder(FolderId, index, total)
{
	var URL = top.g_URL + "/default.jsp?AppId=" + top.g_AppId.toString()
			+ "&SessionPId=" + top.g_SessionPId + "&FolderView=1&ArchiveId=" + top.g_ArchiveId.toString()
            + "&ArchiveName=" + top.g_ArchiveName.toString()
            + "&ArchivePId=" + top.g_ArchivePId.toString()
            + "&FolderPId=" + (top.g_FolderPId+1).toString()
            + "&FolderId=" + FolderId.toString()
            + "&VldSave=1" + "&Idioma=" + top.Idioma.toString()
            + "&numIdioma=" + top.numIdioma.toString()
            + "&FirstReg=" + index.toString() + "&LastReg=" + total.toString()
            + "&FdrQryPId=" + top.g_FdrQryPId.toString()
	        + "&OpenType=0";

	// si el usuario no tiene permisos el registro se abre bloqueado
	if (!( ((top.g_BookPerms >> (top.POS_PERM_MODIFICAR)) % 2 != 0) // Puede modificar, el bit 3 es 1 --> 1xx
		&& (!top.g_bIsLockBook) )){
		URL +="&Form=true";
	}

	top.OpenNewWindow(URL, "", "10000", "10000", "auto","yes");

	top.g_sinPulsar = true;
}

// abre una carpeta de la distribucion
function OpenFolderPenDtr(iFolderId, iArchiveId, iDistId)
{
   window.open(top.g_URL + "/default.jsp?AppId=" + top.g_AppId.toString() + "&SessionPId=" + top.g_SessionPId
			+ "&FolderView=1&ArchiveId=" + iArchiveId.toString()
               + "&FolderId=" + iFolderId.toString()
               + "&DistId=" + iDistId.toString()
               + "&VldSave=1" + "&Idioma=" + top.Idioma.toString()
               + "&numIdioma=" + top.numIdioma.toString()
               + "&OpenType=0&OpenFolderDtr=1&OpenFolderPenDtr=1", "_self","location=no",true);

}

function ChkClose(strClass)
{
	if (strClass != "SubOptionsDisabled") {
		try {
			if (top.Main.Folder.FolderBar.document.getElementById("SaveMenuBtn").className == "SubOptions" ) {
				if( !window.confirm( top.GetIdsLan( "IDS_QRYCONTGUARDAR" ) ) ) {
					return false;
				}
			}
		}
		catch(ex){ }

		return true;
	}
	else {
		return false;
	}
}

function Close()
{
	try {
		if (top.Main.Folder.FolderBar.document.getElementById("SaveMenuBtn").className == "SubOptions" ) {
			if( !window.confirm( top.GetIdsLan( "IDS_QRYCONTGUARDAR" ) ) ) {
				return false;
			}
		}
	}
	catch(ex){ }

	return true;

}


function removeParam(strCadena,strParam)
{
   var strRet = "";
   var iPosIni = 0;
   var iPosFin = 0;

   strParam = strParam + "=";
   iPosIni = strCadena.indexOf("&" + strParam);
   if (iPosIni == -1)
   {
      iPosIni = strCadena.indexOf("?" + strParam);
   }
   if (iPosIni != -1)
   {
      iPosFin = strCadena.indexOf("&",iPosIni + 1);
      if (iPosFin != -1)
      {
         strRet = strCadena.substr(0,iPosIni) + strCadena.substr(iPosFin, strCadena.length - iPosFin);
      }
      else
      {
         strRet = strCadena.substr(0,iPosIni);
      }
   }
   else
   {
      strRet = strCadena;
   }

   if (strRet != "")
   {
      return "?" + strRet.substr(1);
   }
   else
   {
      return "";
   }
}

function ParamValue( Cadena, Param )
{
   var arrcadena = Cadena.split( "&" );
   var arrparam;
   for( var ii=0; ii<arrcadena.length; ii++ )
   {
      arrparam = arrcadena[ii].split( "=" );
      if( arrparam.length > 0 )
      {
         if( ii == 0 )
         {
            arrparam[0] = arrparam[0].substr( 1 );
         }
         if( arrparam[0] == Param )
         {
            return arrparam[1];
         }
      }
   }
   return "";
}

function getExplorerVer()
{
   var iPosVer = navigator.appVersion.indexOf('MSIE') + 5; // Posicion de la version del explorer
   if (iPosVer != -1)
   {
      return parseFloat(navigator.appVersion.substr( iPosVer,navigator.appVersion.indexOf(';',iPosVer) - iPosVer ));
   }
   else
   {
      return 0;
   }
}


function setFocus(Field)
{
	if (Field == null){return;}

	try  {
		if (Field.setActive){
			Field.setActive();
		}

		Field.setAttribute("autocomplete", "OFF");
		Field.focus();
    }
    catch(e){}
}


// Asigna el foco a un control si esta mas a la izquierda que
// las coordenadas x e y que se le pasan
function setFormFocus(oForm, iPosX, iPosY)
{
	var iIndex = -1;

	if(oForm){
	for (var ii = 0; ii < oForm.length; ii++){
		// Miramos si es el campo mas a la izquierda para darle el foco
		if ( (oForm[ii].type == "text") && !(oForm[ii].readOnly) ){
			if (parseInt(oForm[ii].style.top, 10) <= iPosY){
				if (parseInt(oForm[ii].style.top, 10) == iPosY){
					if (parseInt(oForm[ii].style.left, 10) < iPosX){
						iIndex = ii;
						iPosX = parseInt(oForm[ii].style.left, 10);
					}
				}
				else {
					iIndex = ii;
					iPosY = parseInt(oForm[ii].style.top, 10);
					iPosX = parseInt(oForm[ii].style.left, 10);
				}
			}
		}
	}
	}
	if (iIndex > -1) {
		setFocus(oForm.elements[iIndex]);
	}

	return;
}


// Consigue la URL donde esta una aplicacion en un servidor
function getURL()
{
	var sProtocol = top.document.location.protocol;
	var strPathName = top.document.location.pathname;
	var iPosFin = 0;

	// el fichero cuelga directamente del servidor
	if (strPathName.substr(0,1) == "/") {
		iPosFin = strPathName.indexOf("/",1);

		if (iPosFin != -1){
			strPathName = strPathName.substr(0,iPosFin);
		}
		else {
			strPathName = "";
		}
	}
	else {
		iPosFin = strPathName.lastIndexOf("/");

		if (iPosFin != -1){
			strPathName = "/" + strPathName.substr(0,iPosFin);
		}
		else {
			strPathName = "";
		}
	}

	return sProtocol + "//" + top.document.location.host + strPathName;
}


// Funcion que obtiene los tokens de una cadena --> aa|bb|cc||
function getTokens(strCadena, strSep, strEndSep, iNumTokens)
{
   var oArray = new Array();
   var iPosIni = 0;
   var iPosFin = 0;
   var iPosFound = strCadena.indexOf(strEndSep);
   var iIndex = 0;
   if (iNumTokens == -1) // Buscamos hasta encontrar "||" --> strEndSep
   {
      if (iPosFound == -1)
      {
         return null;
      }
      else
      {
         iPosFin = strCadena.indexOf(strSep);
         while ( (iPosFin < iPosFound) && (iPosFin != -1) )
         {
            oArray[iIndex] = strCadena.substr(iPosIni, iPosFin - iPosIni);
            iPosIni = iPosFin + strSep.length;
            iPosFin = strCadena.indexOf(strSep, iPosIni);
            iIndex++;
         }
         oArray[iIndex] = strCadena.substr(iPosIni, iPosFound - iPosIni);
      }
   }
   else // buscamos el numero de tokens que nos han dado
   {
      iPosFin = strCadena.indexOf(strSep);
      while ( (iNumTokens > 0) && (iPosFin != -1) )
      {
         oArray[iIndex] = strCadena.substr(iPosIni, iPosFin - iPosIni);
         iPosIni = iPosFin + strSep.length;
         iPosFin = strCadena.indexOf(strSep, iPosIni);
         iIndex++;
         iNumTokens--;
      }
   }
   return oArray;
}

// quita espacios en blanco por alante y por detras
function miTrim( pcadena )
{
   if ( ( pcadena != "" ) && ( pcadena != null ) )
   {
      for( ; pcadena.charAt(0) == " "; )
      {
         pcadena = pcadena.substring( 1, pcadena.length );
      }

      for( ; pcadena.charAt( pcadena.length - 1 ) == " "; )
      {
         pcadena = pcadena.substring( 0, pcadena.length - 1 );
      }
   }
   return pcadena;
}

// devuelve la version del sistema operativo
function getInfoClient()
{
   var oArrInfo = new Array();
   var strCad = window.navigator.appVersion;
   var iIndex1 = strCad.indexOf("Windows");
   var iIndex2;

   if (iIndex1 != -1) // Es un Windows
   {
      oArrInfo[0] = "Windows";
      iIndex2 = strCad.indexOf("9", iIndex1);
      if (iIndex2 != -1)
      {
         oArrInfo[1] = "9x";
         oArrInfo[2] = strCad.substr(iIndex2, strCad.indexOf(")",iIndex2) - iIndex2);
      }
      else
      {
         iIndex2 = strCad.indexOf("NT");
         if (iIndex2 != -1)
         {
            oArrInfo[1] = "NT";
            oArrInfo[2] = strCad.substr(iIndex2, strCad.indexOf(")",iIndex2) - iIndex2);
         }
         else
         {
            iIndex1 = iIndex1 + 8;
            oArrInfo[1] = strCad.substr(iIndex1,2);
            oArrInfo[2] = strCad.substr(iIndex1, strCad.indexOf(")",iIndex1) - iIndex1);
         }
      }
   }
   else  // No es Windows
   {
         oArrInfo[0] = "undefined";
         oArrInfo[1] = "undefined";
         oArrInfo[2] = "undefined";
   }
   return oArrInfo;
}

// Consigue el nombre del fichero dentro de un path
function GetNamePath( cadena )
{
   var ii = cadena.lastIndexOf( '\\' );
   if( ii != -1 )
   {
      return cadena.substring( ii+1, cadena.length );
   }
   else
   {
      return cadena;
   }
}

// decodifica una cadena escapada para HTML
function HTMLDecode(strEncode)
{
	var re;

	if (strEncode) {
		re = /&AMP;/g;
		strEncode = strEncode.replace(re, "&");
		re = /&LT;/g;
		strEncode = strEncode.replace(re, "<");
		re = /&GT;/g;
		strEncode = strEncode.replace(re, ">");
		re = /&QUOTE; /g;
		strEncode = strEncode.replace(re, "\"");
		re = /&APOS;/g;
		strEncode = strEncode.replace(re, "\'");
		re = /&amp;/g;
		strEncode = strEncode.replace(re, "&");
		re = /&lt;/g;
		strEncode = strEncode.replace(re, "<");
		re = /&gt;/g;
		strEncode = strEncode.replace(re, ">");
		re = /&quote; /g;
		strEncode = strEncode.replace(re, "\"");
		re = /&apos;/g;
		strEncode = strEncode.replace(re, "\'");

		return strEncode;
   }
   else {
		return "";
   }
}

// inicia de nuevo la aplicacion
function InitApp()
{
   window.open(top.g_URL + "/default.jsp?AppId=" + top.g_AppId + "&Idioma=" + top.Idioma + "&numIdioma=" + top.numIdioma, "_top","location=no",true);
}

// Dice si cierto elemento (un string) esta en un array
function existInArray(strElem, oArray)
{
   var ii=0;
   if (oArray != null)
   {
      for (ii=0; (ii<oArray.length) && (oArray[ii] != strElem); ii++)
      {}
      return ii != oArray.length;
   }
   return false;
}

// Consigue el directorio virtual donde esta una aplicacion en un servidor
function getVirtualRoot()
{
   var strPathName = top.document.location.pathname;
   var iPosFin = 0;
   // el fichero cuelga directamente del servidor
   if (strPathName.substr(0,1) == "/")
   {
      iPosFin = strPathName.indexOf("/",1);
      if (iPosFin != -1)
      {
         strPathName = strPathName.substr(0,iPosFin);
      }
   }
   else
   {
      strPathName = "";
   }
   return strPathName;
}

// lanza las ayudas
function OpenHelp()
{
   if (g_oHelpWindow == null)
   {
      g_oHelpWindow = window.open(top.g_URL + "/help.htm", "","top=30,left=50,height=480,width=700,toolbar=yes,scrollbars=auto,status=no,titlebar=no,resizable=yes,location=no",true);
   }
   else
   {
      g_oHelpWindow.focus();
   }
}

// cierra la ayuda si esta abierta al cerrar la ventana desde la que lanzo
function CloseHelp()
{
   if (g_oHelpWindow != null)
   {
      g_oHelpWindow.close();
   }
}

// cierra la carpeta o la session del usuario
function CloseWindow()
{
	if ( (top.g_FolderPId != -1) && parseInt(top.g_CloseFolder) )  {
		top.CloseFolder();
	}
	else {
		if (top.g_SessionPId != "") {
			top.Logout(true);
		}
	}

	top.CloseHelp();
}

// copia la parte del array de modificaciones que es persistente
function CopyPersistentArray(oForm)
{
	var jj=0;
	var bFound = false;
	var strValue = "", strName = "", strSust = "", strFldId = "";
	var Fields = top.Main.Folder.FolderData.FolderFormTree.document.getElementById("Fields").value;
	var arrFields = Fields.split(";");

	g_oArrCopiaFldData = new Array();

	for (var ii=0; ii < arrFields.length; ii++) {
		// buscamos primero en el array de modificaciones
		bFound = false;

		for(jj=0; (jj < top.Main.Folder.FolderBar.FldDataArr.length) && !(bFound); jj++) {
			if (arrFields[ii] == top.Main.Folder.FolderBar.FldDataArr[jj].Id)	{
				strValue = top.Main.Folder.FolderBar.FldDataArr[jj].Value;
				strName = top.Main.Folder.FolderBar.FldDataArr[jj].CtrlId;
				strFldId = top.Main.Folder.FolderBar.FldDataArr[jj].Id;
				strSust = top.Main.Folder.FolderBar.FldDataArr[jj].valueSust;
				bFound = true;
			}
		}

		// si no esta lo buscamos en el formulario
		if ( (!bFound) && (oForm) )	{
			for(jj=0; (jj < oForm.elements.length) && !(bFound); jj++)
			{
				if ( (oForm.elements[jj].FldId == arrFields[ii]) && !(oForm.elements[jj].Sustituto) )
				{
					bFound = true;
					strSust = getSustValue(oForm.elements[jj].FldId, oForm);
					strValue = oForm.elements[jj].value;
					strName = oForm.elements[jj].name;
					strFldId = oForm.elements[jj].FldId;
				}
			}
		}

		if (bFound)	{
			g_oArrCopiaFldData[g_oArrCopiaFldData.length] = new top.Main.Folder.FolderBar.FldData(strFldId, strValue, strName, strSust);
		}
	}
}


// restaura la parte del array de modificaciones que es persistente
function RestorePersistentArray(oArrFldData)
{
   if (g_oArrCopiaFldData)
   {
      for(var ii=0; ii < g_oArrCopiaFldData.length; ii++)
      {
         oArrFldData[ii] = new top.Main.Folder.FolderBar.FldData(g_oArrCopiaFldData[ii].Id, g_oArrCopiaFldData[ii].Value, g_oArrCopiaFldData[ii].CtrlId, g_oArrCopiaFldData[ii].valueSust);
         delete g_oArrCopiaFldData[ii];
      }
      delete g_oArrCopiaFldData;
      g_oArrCopiaFldData = null;
   }
}

// consigue el valor de un campo sustituto al guardarlo
function getSustValue(sFldId, oForm)
{
	for(var ii=0; ii < oForm.elements.length; ii++)	{
		if ((oForm.elements[ii].Sustituto)	&& (oForm.elements[ii].FldId == sFldId) ) {
			return oForm.elements[ii].value;
		}
	}

	return "";
}

// quita la doble comilla de los campos
function Replace(strCopia)
{
   //strCopia = strCopia.replace(/"/g,"");
   strCopia = strCopia.replace(/\x7c/g,"");
   return strCopia.replace(/#/g,"");
}


function Relations()
{
	var sRet;
	var args = new Array;
	var URL = top.g_URL + "/relations.htm";

	args[0] = top.g_URL;
	args[1] = top.g_SessionPId.toString();
	args[2] = top.g_ArchivePId.toString();
	args[3] = top.g_FolderId.toString();
	args[4] = top.g_FdrQryPId.toString();
	args[5] = 2;
	args[6] = "";
	args[7] = top.Idioma;
	//el siguiente parametro indica la ventana padre
	args[8] = {opener: self};

    sRet = window.ShowModalDialog(URL, args, 400, 700, "");
}


function FormatHour(fecha)
{
	var hora;

	if (fecha.getHours() < 10)
	{
		hora = "0" + fecha.getHours();
	}
	else
	{
		hora = fecha.getHours();
	}

	if (fecha.getMinutes() < 10)
	{
		hora = hora + ":0" + fecha.getMinutes();
	}
	else
	{
		hora = hora + ":" + fecha.getMinutes();
	}

	return hora;
}


function FormatDate(fecha)
{
	var dia;

	if (fecha.getDate() < 10){
		dia = "0" + fecha.getDate();
	}
	else{
		dia = fecha.getDate();
	}

	if ((fecha.getMonth() + 1) < 10){
		dia = dia + "-0" + (fecha.getMonth() + 1);
	}
	else{
		dia = dia + "-" + (fecha.getMonth() + 1);
	}

	dia = dia + "-" + fecha.getFullYear();

	return dia;
}


function ActivateFrmtMenu()
{
	top.g_ActivateTree = true;

	if (!top.g_FolderView)
	{
		top.g_OpcAval=true;
		top.Main.Folder.ToolBarFrm.habilitar();
	}
	else
	{
		ActivateSubMenu(top.Main.Folder.FolderBar.document.getElementById("MenuFolderBar"));

		if(top.Main.Folder.FolderBar.document.getElementById("botonesRegistro")){
			ActivateSubMenu(top.Main.Folder.FolderBar.document.getElementById("botonesRegistro"));
		}

		if(top.Main.Folder.FolderBar.document.getElementById("botonesConfRegistro")){
			ActivateSubMenu(top.Main.Folder.FolderBar.document.getElementById("botonesConfRegistro"));
		}
	}
}

function ActivateSubMenu(tabMenu){
	top.Main.Folder.ToolBarFrm.ToolBarEnabledEx();
	for (var i = 0; i < tabMenu.rows[0].cells.length; i++)
	{
		if (tabMenu.rows[0].cells[i].className == "SubOptions")
		{
			tabMenu.rows[0].cells[i].className = "SubOptionsDisabled";
		}
	}
}


function DoReports()
{
	top.Main.document.getElementById('frSetWork').rows = "*,*,*,*,100%";

	// Activamos el frame Reports
	top.Main.document.getElementById("Workspace").tabIndex = "-1";
	top.Main.document.getElementById("Table").tabIndex = "-1";
	top.Main.Table.document.getElementById("TableData").tabIndex = "-1";
	top.Main.document.getElementById("Folder").tabIndex = "-1";
	top.Main.Folder.document.getElementById("FolderBar").tabIndex = "-1";
	top.Main.Folder.document.getElementById("FolderData").tabIndex = "-1";
	top.Main.Folder.document.getElementById("ToolBarFrm").tabIndex = "-1";

	if (top.g_FolderView) {
		//top.Main.Folder.FolderData.FolderFSet.document.getElementById("FolderFormData").tabIndex = "-1";
		top.Main.Folder.FolderData.document.getElementById('FolderFormData').tabIndex = "-1";
	}

	top.Main.document.getElementById("Distr").tabIndex = "-1";
	top.Main.document.getElementById("Reports").tabIndex = "1";

	top.SetArchiveName(top.g_ArchiveName, "REP");

	top.Main.Reports.document.getElementById("Back").className = "SubOptionsDisabled";
	//top.Main.Reports.document.getElementsByName("SelPrint").item(0).checked = true;
	//top.Main.Reports.document.getElementsByName("SelPrint").item(0).onclick();
	top.Main.Reports.document.getElementById("SelPrint").value = "0";
	top.Main.Reports.document.getElementById("SelPrint").onchange();
}


function ValidateList(args)
{
	var wnd;
	var sVal;

	wnd = args[0] + "/mainvld.htm?SessionPId=" + args[1]
	    + "&ArchivePId=" + args[2].toString()
	    + "&FldId=" + args[3].toString()
	    + "&tblvalidated=" + args[4].toString()
	    + "&TblFldId=" + args[5].toString()
	    + "&VldInter=0"
	    + "&Idioma=" + top.Idioma
	    + "&Enabled=" + args[7].toString()
	    + "&IsDtrList=2";

	sVal = top.ShowModalDialog(wnd, "name", 500, 700, "");

	return (sVal);
}

function ValidateList(args, caseSensitive)
{
	var wnd;
	var sVal;

	if (args[8])
	{
		wnd = args[0] + "/mainvld.htm?SessionPId=" + args[1]
		    + "&ArchivePId=" + args[2].toString()
		    + "&FldId=" + args[3].toString()
		    + "&tblvalidated=" + args[4].toString()
		    + "&TblFldId=" + args[5].toString()
		    + "&VldInter=0"
		    + "&Idioma=" + top.Idioma
		    + "&Enabled=" + args[7].toString()
		    + "&IsDtrList=2"
		    + "&caseSensitive=" + caseSensitive
		    + "&queryBook=" + args[8];
	}else{
		wnd = args[0] + "/mainvld.htm?SessionPId=" + args[1]
		    + "&ArchivePId=" + args[2].toString()
		    + "&FldId=" + args[3].toString()
		    + "&tblvalidated=" + args[4].toString()
		    + "&TblFldId=" + args[5].toString()
		    + "&VldInter=0"
		    + "&Idioma=" + top.Idioma
		    + "&Enabled=" + args[7].toString()
		    + "&IsDtrList=2"
		    + "&caseSensitive=" + caseSensitive;
	}

	sVal = top.ShowModalDialog(wnd, "name", 500, 700, "");

	return (sVal);
}


// devuelve la extension del fichero
function getExtensionFile(strPathFile)
{
   var ii = strPathFile.lastIndexOf( '\.' );
   if( ii != -1 )
   {
      return strPathFile.substring( ii, strPathFile.length );
   }
   else
   {
      return "";
   }
}

function StyleDisplayBlock()
{
	var displayBlock = (!IsExplorerBrowser())?"table-row":"block";

	return(displayBlock);
}

function XMLHTTPRequestGet(URL, fnCallback, Async)
{
	if (g_oXMLHTTPRequest){
		g_oXMLHTTPRequest = null;
	}

	if (window.XMLHttpRequest) {
	try {
			g_oXMLHTTPRequest = new XMLHttpRequest();
        } catch(e) {
			g_oXMLHTTPRequest = false;
        }
    }
    else if (window.ActiveXObject) {
	try {
		g_oXMLHTTPRequest = new ActiveXObject("Msxml2.XMLHTTP");
	} catch(e) {
		try {
			g_oXMLHTTPRequest = new ActiveXObject("Microsoft.XMLHTTP");
		} catch(e) {
			g_oXMLHTTPRequest = false;
		}
		}
    }

    try {
		if (g_oXMLHTTPRequest) {
			g_oXMLHTTPRequest.onreadystatechange = fnCallback;
			g_oXMLHTTPRequest.open("GET", URL, Async);
			g_oXMLHTTPRequest.send("");
		}
	}
	catch(e){
		alert(e.description);
	}
}


function XMLHTTPRequestPost(URL, fnCallback, Async, Data)
{
	if (g_oXMLHTTPRequest){
		g_oXMLHTTPRequest = null;
	}

	if (window.XMLHttpRequest) {
	try {
			g_oXMLHTTPRequest = new XMLHttpRequest();
        } catch(e) {
			g_oXMLHTTPRequest = false;
        }
    }
    else if (window.ActiveXObject) {
	try {
		g_oXMLHTTPRequest = new ActiveXObject("Msxml2.XMLHTTP");
	} catch(e) {
		try {
			g_oXMLHTTPRequest = new ActiveXObject("Microsoft.XMLHTTP");
		} catch(e) {
			g_oXMLHTTPRequest = false;
		}
		}
    }

    try {
		if (g_oXMLHTTPRequest) {
			g_oXMLHTTPRequest.onreadystatechange = fnCallback;
			g_oXMLHTTPRequest.open("POST", URL, Async);
			g_oXMLHTTPRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			g_oXMLHTTPRequest.setRequestHeader("Content-length", Data.length);
			g_oXMLHTTPRequest.setRequestHeader("Connection", "close");
			g_oXMLHTTPRequest.send(Data);
		}
	}
	catch(e){
		alert(e.description);
	}
}


function SetInnerText(item, text)
{
	if (item.innerText == null){
		item.textContent = text;
	}
	else {
		item.innerText = text;
	}
}

function GetInnerText(item)
{
	var text;

	if (item.innerText == null){
		text = item.textContent;
	}
	else {
		text = item.innerText;
	}

	return (text);
}

function GetKeyCode(aEvent)
{
	var keyCode = (aEvent.keyCode)?aEvent.keyCode:aEvent.which;

	return (keyCode);
}

function PreventDefault(aEvent)
{
	if (aEvent.prevenDefault){
		aEvent.preventDefault();
	}
	else {
		aEvent.returnValue = false;
	}
}

function GetSrcElement(aEvent)
{
	var srcElement = (aEvent.srcElement)?aEvent.srcElement:aEvent.target;

	return srcElement;
}

function StopPropagation(aEvent)
{
	if (aEvent == null){return;}

	if (aEvent.stopPropagation){
		aEvent.stopPropagation();
	}
	else {
		aEvent.cancelBubble = true;
	}
}

function GetEventPositionX(aEvent)
{
	var posX = (aEvent.x)?aEvent.x:aEvent.pageX;

	return posX;
}

function GetEventPositionY(aEvent)
{
	var posY = (aEvent.y)?aEvent.y:aEvent.pageY;

	return posY;
}

function ShowModalDialog(URL, arguments, height, width, sFeatures)
{
	var ret = "";
	var style = "";
	if (top.showModalDialog){
		style = "dialogHeight:" + height.toString() + "px;dialogWidth:" + width.toString() + "px;center:Yes;help:No;resizable:No;status:No;unadorned:Yes;scroll:no;";

		if (sFeatures != ""){
			style += ";" + sFeatures;
		}

		ret = top.showModalDialog(URL, arguments, style);
	}
	else {
		style = "height=" + height.toString() + "px,width=" + width.toString() + "px,resizable=no,scrollbars=no,modal=yes,dialog=yes,status=no,menubar=no,location=no,dependent=yes";
		style += ",screenY=" + ((screen.availHeight-height)/2);
		style += ",screenX="+((screen.availWidth-width)/2);

		for (var i = 0; i < arguments.length; i++){
			if (arguments[i].documentElement != null){
				eval("top.param" + i + "= " + arguments[i]);
			}
			else {
				eval("top.param" + i + "= '" + arguments[i] + "'");
			}
		}

		DisableEvents(window.top);

		top.g_WinModal = window.open(URL, "", style);

		top.g_WinModal.focus();
	}

	return (ret);
}

function DisableEvents(win)
{
	var topFrames = win.frames;

	for (var i = 0; i < topFrames.length; i++) {
		for (var j = 0; j < topFrames[i].document.forms.length; j++) {
			for (var h = 0; h < topFrames[i].document.forms[j].elements.length; h++) {
				topFrames[i].document.forms[j].elements[h].disabled = true;
			}
		}

	        topFrames[i].window.onfocus = HandleFocus;
		topFrames[i].document.onclick = HandleFocus;

		DisableEvents(topFrames[i].window);
	}
}

function EnableEvents(win)
{
	if (top.showModalDialog){return;}
	if (win == null){return;}

	var topFrames = win.frames;

	for (var i = 0; i < topFrames.length; i++) {
		for (var j = 0; j < topFrames[i].document.forms.length; j++) {
		    for (var h = 0; h < topFrames[i].document.forms[j].elements.length; h++) {
			    topFrames[i].document.forms[j].elements[h].disabled = false;
			}
		}

		topFrames[i].window.onfocus = null;
		topFrames[i].document.onclick = null;

		EnableEvents(topFrames[i].window);
	}
}

function HandleFocus(aEvent)
{
	if (top.g_WinModal){
		if (!top.g_WinModal.closed){
			top.g_WinModal.focus();
		}
		else{
			window.top.releaseEvents (Event.CLICK|Event.FOCUS);
			window.top.onclick = "";
		}
	}

	return false;
}

function SetTableFocus(obj)
{
	if (obj.focus){
		obj.focus();
	}
}

function GetDlgParam(i)
{
	var param = null;

	if (top.dialogArguments){
		param = top.dialogArguments[i];
	}
	else{
		eval("param=" + "top.window.opener.top.param" + i );
	}

	return (param);
}


function IsExplorerBrowser()
{
	var ret = (navigator.appName.indexOf("Microsoft Internet Explorer") != -1)?true:false;

	return (ret);
}


function AttachEvent(item, namEvent, func)
{
	if (item.addEventListener){
		item.addEventListener(namEvent, func, false);
	}
    else if (item.attachEvent) {
		item.attachEvent("on" + namEvent, func);
    }
}


function GetXMLParser()
{
	var XMLParser = null;

	if (window.ActiveXObject) {
		try {
			XMLParser = new ActiveXObject("MSXML2.DOMDocument");
		} catch(e) {
			try {
				XMLParser = new ActiveXObject("MSXML2.DOMDocument");
			} catch(e) {
				try {
					XMLParser = new ActiveXObject("Microsoft.XMLDOM");
				}
				catch(e){
					XMLParser = null;
				}
			}
		}
	}

	return XMLParser;
}


function SetOpacity(elem, degree)
{
	if (top.IsExplorerBrowser()){
		elem.style.filter = "alpha(opacity=" + degree + ")";
	}
	else {
		elem.style.opacity = degree/100;
	}
}

function confirmMessage(message, select)
{
		var typeDist;
		var state;

		if(confirm(message)){

			top.Main.document.getElementById('frSetWork').rows = "*,*,*,100%,*";

			// Activamos el frame Distr
			top.Main.document.getElementById("Workspace").tabIndex = "-1";
			top.Main.document.getElementById("Table").tabIndex = "-1";
			top.Main.Table.document.getElementById("TableData").tabIndex = "-1";
			top.Main.document.getElementById("Folder").tabIndex = "-1";
			top.Main.Folder.document.getElementById("FolderBar").tabIndex = "-1";
			top.Main.Folder.document.getElementById("FolderData").tabIndex = "-1";
			top.Main.Folder.document.getElementById("ToolBarFrm").tabIndex = "-1";

			if (top.g_FolderView) {
				top.Main.Folder.FolderData.FolderFormData.tabIndex = "-1";
			}

			top.Main.document.getElementById("Distr").tabIndex = "1";
			top.Main.document.getElementById("Reports").tabIndex = "-1";
			if (select == '0'){
				typeDist = '1';
				state = '1';
			} else {
				typeDist = '2';
				state = '4';
			}

			top.Main.Distr.isFromConfim(typeDist, state);
		}
}

var urlSkinCSS = cargarSkinCSS();

function cargarSkinCSS() {
	var url = getURL();
	var urlSkinCSS = url + "/resourceServlet/css/skin.css";
	return urlSkinCSS;
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
 * Funcion que bloquea la pantalla de registro mostrando el mensaje espere por favor...
 */
function bloqueoDePantallaRegistro(){

	//asignamos los valores por defecto para las capas
	$.blockUI.defaults.css.border = 'none';
	$.blockUI.defaults.css.backgroundColor = "#000000";
	$.blockUI.defaults.overlayCSS.backgroundColor = "#000000";
	$.blockUI({ message: null });

	//cargamos las capas de bloqueo del registro
	top.Main.Folder.FolderData.FolderFormTree.$.blockUI({ message: null });
	top.Main.Folder.FolderData.FolderFormData.$.blockUI({ message: '<img src="./images/loading.gif" /><br><nobr>' + top.GetIdsLan("IDS_WAIT_PLEASE") + '</nobr>'});
	top.Main.Folder.FolderBar.$.blockUI({ message: null });
}

/**
 * Funcion que deshabilita las capas emergentes del bloqueo de pantalla
 */
function desbloqueoDePantallaRegistro(){
	 top.Main.Folder.FolderData.FolderFormTree.$.unblockUI();
	 top.Main.Folder.FolderData.FolderFormData.$.unblockUI();
	 top.Main.Folder.FolderBar.$.unblockUI();
}
