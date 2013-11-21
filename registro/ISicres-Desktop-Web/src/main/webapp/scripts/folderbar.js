var STATE_DISTR_PENDIENTE = "1";



function FldData(Id, Value, CtrlId, sValueSust)
{
   this.Id     = Id;
   this.Value  = Value;
   this.CtrlId = CtrlId;
   this.valueSust = sValueSust;
}

function PageCtrl(Page, Id)
{
   this.Page = Page;
   this.Id   = Id;
}

function BadFld(Id)
{
   this.Id   = Id;
}

function FldRnm( Id, Clase, Value )
{
   this.Id    = Id;
   this.Clase = Clase;
   this.Value = Value;
}

var FldDataArr       = new Array();
var FldRnmArr        = new Array();
var PageCtrls        = new Array();
var BadFldArr        = new Array();
var oArrStrUpdate    = new Array();
var g_NextReg        = 0;

//******************
var elemento      = 2000;  // Para que no coincidan identificadores de pagina con resto de elementos
var PageCnt       = 0;
var existFileScan = false;
// para controlar los eventos que saltan antes de tener el formulario cargado
var bLoadForm     = false;
var InitCountStamp = 1;
//******************

function OnWindowLoad()
{
	if (top.Main.Folder.FolderBar.document.getElementById("FolderNum"))
	{
		top.Main.Folder.FolderBar.document.getElementById("FolderNum").value = top.g_FolderSel.toString();
	}

	// se hace el onload del form para recargarlo
	if (top.g_LoadForm)
	{
		top.Main.Folder.FolderData.FolderFormData.OnWindowLoad();
		top.g_LoadForm = false;
	}
	if (top.g_OpenFolderPenDtr){

		var oForm = document.getElementById("formDist");
		var inputId = document.createElement("INPUT");

		inputId.setAttribute("type", "hidden");
		inputId.setAttribute("name", "Ids");
		inputId.setAttribute("id", "Ids");

		inputId.value = top.g_DistId;

		oForm.appendChild(inputId);
	      var query = 'STATE=1 AND ID_ARCH = ' + top.g_ArchiveId + ' AND ID_FDR = ' + top.g_FolderId;

		document.getElementById("distWhere").value = query;
	}

	top.Main.Folder.FolderData.FolderFormTree.Sel1stElem();
}

function OpenFolderEx()
{
	var strParams = "";

	top.g_ShowTable = true;
	top.g_FolderId = parseInt(getFolderIdTable((top.g_FolderSel - top.Main.Table.TableData.g_FirstRow + 1).toString()));

	strParams = "SessionPId=" + top.g_SessionPId.toString();
	strParams += "&ArchiveId=" + top.g_ArchiveId.toString();
	strParams += "&FolderId=" + top.g_FolderId.toString();
	strParams += "&FolderPId=" + top.g_FolderPId.toString();
	strParams += "&ArchivePId=" + top.g_ArchivePId.toString();
	strParams += "&FdrQryPId=" + top.g_FdrQryPId.toString();
	strParams += "&Row=" + top.g_FolderSel.toString();
	strParams += "&Form=true";

	top.XMLHTTPRequestGet(top.g_URL + "/openfolder.jsp?" + strParams, top.ResponseOpenFolder, true);
}


function OpenFolder()
{
	if ((top.g_FolderSel > (top.Main.Table.TableData.g_FirstRow + top.Main.Table.TableData.g_RowsVisible - 1))
		|| (top.g_FolderSel < top.Main.Table.TableData.g_FirstRow)){
		top.g_ShowTable = false;
		window.open(top.g_URL + "/tbltext2.jsp?SessionPId=" + top.g_SessionPId + "&Row=" + top.g_FolderSel
			+ "&FdrQryPId=" + top.g_FdrQryPId.toString(), "TableData","location=no",true);
	}
	else {
	      OpenFolderEx();
	}
}


function First()
{
   top.g_FolderSel = 1;
   OpenFolder();
}

function Prev()
{
   top.g_FolderSel--;
   OpenFolder();
}

function Next()
{
   top.g_FolderSel++;
   OpenFolder();
}

function Last()
{
   top.g_FolderSel = top.Main.Table.TableData.g_TotalRows;
   OpenFolder();
}

function GoTo()
{
   var Page = top.Main.Folder.FolderBar.document.getElementById("FolderNum").value;

   if ((!isNaN(Page)) && (Page != "") && (parseInt(Page) <= top.Main.Table.TableData.g_TotalRows) && (parseInt(Page) >= 1))
   {
      top.g_FolderSel = parseInt(Page);
      OpenFolder();
   }
	else
	{
	   alert ( top.GetIdsLan( "IDS_ERRORNUMFLD" ) );
	   document.getElementById("FolderNum").select();
	   document.getElementById("FolderNum").focus();
	}
}


function Print(obj)
{
	if (obj.className != "SubOptionsDisabled"){
		var sRet;
		var args = new Array;
		var URL = top.g_URL + "/report.htm";

		args[0] = top.g_URL;
		args[1] = top.g_SessionPId.toString();
		args[2] = top.g_ArchivePId.toString();
		args[3] = top.g_FolderId.toString();
		args[4] = top.g_FdrQryPId.toString();
		args[5] = 1;
		args[6] = "";
		args[7] = top.Idioma.toString();

		sRet = top.ShowModalDialog(URL, args, 400, 450, "");
	}
}

function Compulsa(obj)
{



	var URL = top.g_URL + "/compulsa.jsp?sessionPId=" + top.g_SessionPId+"&idLibro="+top.g_ArchiveId.toString()+"&idRegistro="+top.g_FolderId.toString();
	window.open(URL, "FolderFormCompulsaData","location=no",true);

}


function OnKey(aEvent)
{
   if (top.GetKeyCode(aEvent) == 13)
      GoTo();
}


function CloseModify(Obj)
{
	if (Obj.className != "SubOptionsDisabled") {
		mostrarCapaCargando();
		consOc( parent.FolderData.FolderFormTree.document.getElementById("R0") );
		Obj.className = "SubOptionsDisabled";
		BuildStrUpdateFolder();
	}
}


//Añade al frame "frmStrUpdate" los datos que se deben guardar de la carpeta.
function BuildStrUpdateFolder()
{
	var OElement, StrCad = "";

	OElement = document.createElement("TEXTAREA");

	OElement.setAttribute("class", "input");
	OElement.setAttribute("name", "FldData");
	OElement.setAttribute("id", "FldData");

	// creamos la cadena con los campos que se modifican y limpiamos los interesados modificados
	for( var ii=0; ii< FldDataArr.length; ii++) {
		StrCad += FldDataArr[ii].Id + '|' + encodeURIComponent(FldDataArr[ii].Value) + '|' + FldDataArr[ii].CtrlId + '||';
		AsignValueToFldsSust(FldDataArr[ii].valueSust, FldDataArr[ii].Id);
	}

	OElement.value = StrCad;
	document.getElementById("frmStrUpdate").appendChild( OElement );

	for( var ii=0; ii< FldRnmArr.length; ii++) {
		oArrStrUpdate[oArrStrUpdate.length] = "2|" + "CL" + FldRnmArr[ii].Clase + "|" + FldRnmArr[ii].Id + "|" + FldRnmArr[ii].Value + "|-|CL-";
	}

	// Array de Inputs para pasar la cadena de modificacion y que la coja
	for (var ii=0; ii < oArrStrUpdate.length; ii++){
		//verificamos la cadena con la modificación ya se encuentra en el formulario
		if(validateIfAddDataFormFrmStrUpdate(oArrStrUpdate[ii])){
			//sino se encuentra en el formulario lo añadimos
			var OElement1 = document.createElement("INPUT");
			OElement1.setAttribute("class", "input");
			OElement1.setAttribute("type", "text");
			OElement1.setAttribute("name", "TreeUpdate");
			OElement1.value = oArrStrUpdate[ii];
			document.getElementById("frmStrUpdate").appendChild( OElement1 );
		}
	}

	// Se lanza el applet para subirnos los ficheros escaneados si hay
	if (existFileScan){
		//Cuando se termine de subir los archivos se llamara a "ExecuteUpdateFolder" en la funcion "ExecuteSendScanFilesToServer".
		SendScanFilesToServer(document.getElementById("frmStrUpdate"));
	} else {
		//No es necesario subir archivos, llamar a guardar carpeta.
		ExecuteUpdateFolder();
	}

	// Inicializamos de nuevo las variables, por si se produce algún error en
	// ejecución que no sean tratados los datos dos veces
	oArrStrUpdate = new Array();
}

// Funcion que comprueba si se debe insertar en el formulario frmStrUpdate
function validateIfAddDataFormFrmStrUpdate(cadenaModificacion){
	//añadimos los datos al formulario como un campo
	var result = true;

	//Obtenemos el formulario que contiene la información de los documentos adjuntados
	var oForm = document.getElementById("frmStrUpdate");

	//recorremos los elementos del formulario
	for(var jj=0; jj< oForm.length; jj++){
		// si el elemento es de tipo TEXTO y con el nombre TREEUPDATE
		if((oForm[jj].type == "text") && (oForm[jj].name = "TreeUpdate")){
			//pasamos a comparar, si la cadena almacenada es identica al que intentamos adjuntar
			if(oForm[jj].value == cadenaModificacion){
				//El documento ya se encuentra en el formulario por tanto no se añade
				result = false;
			}
		}
	}

	return result;
}

//Ejecuta el servlet de subir los cambios de la carpeta.
function ExecuteUpdateFolder()
{
	var strParams = "";

	strParams = "SessionPId=" + top.g_SessionPId.toString()
		+ "&FolderPId=" + top.g_FolderPId.toString()
		+ "&FolderId=" + top.g_FolderId.toString()
		+ "&ArchiveId=" + top.g_ArchiveId.toString()
		+ "&ArchivePId=" + top.g_ArchivePId.toString();

	document.getElementById("frmStrUpdate").action = top.g_URL + "/flushfdr.jsp?" + strParams;
	top.g_CloseFolder = 1;

	try {
		// copiamos la parte del array de modificaciones que es persistente
		top.CopyPersistentArray(top.Main.Folder.FolderData.FolderFormData.document.getElementById("FrmData"));
	}catch(e){}

	ClearAllInvalids();
	document.getElementById("frmStrUpdate").submit();

	// Deshabilitamos las toolbar y los eventos del formulario
	top.Main.Folder.ToolBarFrm.ToolBarDisabled();
	bLoadForm = false;

	top.g_CopyFdr = 0;
	top.g_SavePending = false;
	top.g_changeDataRegistro = false;

	return;
}

function ocultarCapaCargando(){
	var capa = parent.FolderData.FolderFormData.document.getElementById("cargando");

	if(capa != null){
		capa.style.display = "none";
	}
}

function mostrarCapaCargando(){
	var capa = parent.FolderData.FolderFormData.document.getElementById("cargando");

	if(capa != null){
		capa.style.display = "block";
	}
}
function ResponseUpdate()
{
	var docFrmUpdate = document.getElementById("frmUpdate").contentWindow.document;

	if (docFrmUpdate.getElementById("ErrorMessage") != null){
		var CtrlIds = docFrmUpdate.getElementsByName("CtrlId");

		top.Main.Folder.FolderBar.ResetForm();
		top.Main.Folder.ToolBarFrm.ToolBarEnabled();

		for (var i = 0; i < CtrlIds.length; i++){
			top.Main.Folder.FolderBar.SetBadField(CtrlIds[i].value);
		}

		top.Main.Folder.FolderBar.ShowFirstBadField();
		top.Main.Folder.FolderBar.bLoadForm = true;

		alert(docFrmUpdate.getElementById("ErrorMessage").value);
	}
	else if (top.GetInnerText(docFrmUpdate.body) != ""){
		var response = docFrmUpdate.body.innerHTML;

		if (response != ""){
			if (response.indexOf("alert(") != -1){
				top.Main.Folder.FolderBar.bLoadForm = true;
				g_CloseFolder=0;
				evalAlert(response);
			}
			else {

				if (existFileScan) {
					 top.Main.Folder.FolderBar.DeleteScanFiles();
				}
				top.g_bIsSaved = true;
			top.SetFolderFormTree(response);

		}
		}
	}

	docFrmUpdate.body.innerHTML = "";
	ocultarCapaCargando();
}

//funcion que genera la paginacion de registros
function tablaPaginacionRegistros(){
	var result = "";

	result += "<table border=\"0\" cellPadding=\"0\" cellSpacing=\"0\">";
	result +=   "<tr>";
	result +=     "<td width=\"10\"></td>";
	result +=     "<td width=\"220\" class=\"SubOptionsContainer\" align=\"center\" tabIndex=\"-1\">";
	result +=       "<div id=\"counter\" align=\"center\">" + GetCounterHTML() + "</div>";
	result +=     "</td>";
	result +=   "</tr>";
	result += "</table>";

	return result;
}

// funcion que genera la botonera de la paginacion de Registros, mostrando la
// informacion del elemento en el que estamos posicionados y los elementos
// totales, ademas permite ir al registro introducido por el input "FolderNum"
function tablaPaginacionRegistrosGoTo(){
	var result = "";

	result += "<table border=\"0\" cellPadding=\"0\" cellSpacing=\"0\">";
	result += "<tr>";

	result += "<td width=\"10\"></td>";
	result += "<td class=\"SubOptionsContainer\" align=\"center\" tabIndex=\"-1\">";
	result +=    "<div id=\"counter\" align=\"center\"></div>";
	result += "</td>";

	result += "<td width=\"10\"align=\"center\" class=\"SubOptions\">" + top.GetIdsLan( "IDS_OPCCARPETA" ) + ":</td>";
	result += "<td width=\"5\"></td>";

	//generamos INPUT para introducir el registro en el que nos queremos posicionar
	result += "<td width=\"15\">";
	if (top.Main.Table.TableData.g_TotalRows > 1){
		result += "<input style=\"width:40px; height:18px\" class=\"input\" id=\"FolderNum\" onKeyPress=\"OnKey(event);\" tabIndex=\"1\"></input>";
	} else {
		result += "<input style=\"width:40px; height:18px\" class=\"input\" id=\"FolderNum\" ReadOnly=\"1\"></input>";
	}
	result += "</td>";

	//Boton IR a...
	if (top.Main.Table.TableData.g_TotalRows > 1){
		result += "<td width=\"40\" class=\"SubOptions\" onmouseover=\"Over(this)\" onmouseout=\"Out(this)\" onclick=\"javascript:GoTo();\" onkeydown=\"javascript:if (top.GetKeyCode(event)==13){GoTo();}\" tabIndex=\"1\"> ";
	} else {
		result += "<td width=\"40\" class=\"SubOptionsDisabled\"> ";
	}
	result +=   "<div align=\"center\">" + top.GetIdsLan( "IDS_OPCIR" ) + "</div>";
	result += "</td>";

	//Se generan botones de paginacion PRIMERO y ANTERIOR
	result += generarBotonesPaginaPrimeroAnterior();

	//Se genera literal con la posicion del elemento actual y elementos totales
	result += "<td width=\"10\"></td>";
	result += "<td width=\"25\" align=\"center\" class=\"SubOptions\"><nobr>| " + top.GetIdsLan( "IDS_OPCCARPETA" ) + " "
                  + top.g_FolderSel + " " + top.GetIdsLan( "IDS_PREPDE" ) + " " + top.Main.Table.TableData.g_TotalRows + " |</nobr>";
	result += "</td>";
	result += "<td width=\"10\"></td>";

	//Se generan botones de paginacion SIGUIENTE y ULTIMO
	result += generarBotonesPaginaSiguienteUltimo();

	result += "<td width=\"3\"></td>";

	result += "</tr></table>";

	return result;
}

//Funcion que genera el conjunto de botones del formulario de registro
function GetBarText(Mode)
{
   var HTMLText = "";
   //variable que identifica si se ha generado la tabla BotonesRegistro
   var existTableBotonesRegistro = false;

   if (Mode == "Query") {

		if (top.g_FolderView && (top.g_FolderId != -1) && (top.g_LastReg != 0))	{
			//paginacion de registros desde el formulario del registro
			HTMLText += "<td align=\"left\" width=\"220\">";
			//tabla de paginacion
			HTMLText += tablaPaginacionRegistros();
			HTMLText += "</td>";
			HTMLText += "<td align=\"right\"><table id=\"botonesRegistro\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\"><tr>";
			existTableBotonesRegistro = true;
		}

		if (!top.g_FolderView){
			//paginacion de registros en vista formulario desde el resultado de búsquedas
			HTMLText += "<td align=\"left\" width=\"275\">";
			//tabla de paginacion
			HTMLText += tablaPaginacionRegistrosGoTo();
			HTMLText += "</td>";
			HTMLText += "<td align=\"right\"><table id=\"botonesRegistro\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\"><tr>";
			existTableBotonesRegistro = true;

		} else {
			//comprobamos si ha entrado por alguno de los anteriores if para no pintar dos veces la tabla con id: botonesRegistro
			if(!existTableBotonesRegistro){
				HTMLText += "<td></td>";
				HTMLText += "<td align=\"right\"><table id=\"botonesRegistro\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\"><tr>";
			}

			//Se genera el boton REGISTRAR o GUARDAR segun corresponda
			if (!top.g_FdrReadOnly)	{
				HTMLText += generarBotonRegistrarGuardar();
			}
		}

		//se genera el boton: LIMPIAR siempre que sea en modo edición
		if((!top.g_FdrReadOnly) || ((top.g_FolderView) && (!top.g_FdrReadOnly))){
			HTMLText += generarBotonLimpiarPantallaRegistro();
		}

		//se generan los botones basicos del registro: IMPRIMIR, SELLAR Y COMPULSAR
		var FrmTree = top.Main.Folder.FolderData.FolderFormTree.document;
		var EBookType = (parseInt(FrmTree.getElementById("BookType").value, 10) == 1) ? true : false;
		if (top.g_FolderId == -1) {
			HTMLText += generarBotonesBasicosRegistroDesactivados(EBookType);
		}
		else {
			HTMLText += generarBotonesBasicosRegistro(EBookType);
		}

		// Botón de distribución manual
		// se comprueban los permisos de distribución manual, que sea un
		// registro existente, y que si se abre desde la bandeja de distribución
		// se realice desde la bandeja de aceptados
		if ((parseInt(FrmTree.getElementById("CanDist").value, 10) == 1) && (top.g_FolderId != -1)
			&& ((top.g_OpenFolderDtr && top.g_OpenEditDistr && !top.g_FdrReadOnly) || !top.g_OpenFolderDtr)){
			HTMLText += generarBotonDistribucionManualFormularioReg();
		}


		//se generan los botones de configuracion: CONF. SELLO, CONF. ESCANES y CONF. REGISTRO
		if((!top.g_FdrReadOnly) || ((top.g_FolderView) && (!top.g_FdrReadOnly))){
			//Boton para ocultar/mostrar capa de botones de configuracion
			HTMLText += "<td class=\"SubOptions\" width=\"130\" onmouseover=\"Over(this)\" onmouseout=\"Out(this)\" onclick=\"OcultarMostrarBotonesConf()\">";
			HTMLText += "<div align=\"center\"><IMG id=\"config\" src=\"images/configure.png\" title=\""+ top.GetIdsLan("IDS_LABEL_CONFIG") +"\" border=\"0\" align=\"middle\"/> " + top.GetIdsLan("IDS_LABEL_CONFIG") + "  <IMG id=\"imgdesplegarConf\" src=\"images/flechas_dobles_dcha_sin_fondo.png\" border=\"0\" align=\"middle\"/>";
			HTMLText += "</div></td>";
			HTMLText += "<td width=\"4\"></td>";
			HTMLText += "<td>" + generarBotonesDeConfiguracion() + "</td>";
		}

		//se comprueba si se abre la carpeta desde url, si es correcto se generan los botones ACEPTAR y RECHAZAR
		if (top.g_OpenFolderPenDtr)	{
			HTMLText += generarBotonesRegistroAccesoPorURL();
		}

		//se genera el boton: NUEVO REGISTRO
		if ( (top.g_FolderId != -1) && (top.g_OpenType == 1) ) {
			HTMLText += generarBotonNuevoRegistroBcl();
		}

		//se genera el boton: INTERCAMBIO REGISTRAL
		if (top.g_canSendIntercambioRegistral=="true"
			&& top.g_EnabledIntercambioRegistral=="true"
				&& top.g_isBookIntercambioRegistral=="true"
					&& (top.g_FolderId != -1)) {
			HTMLText += generarBotonIntercambioRegistral();
		}

		HTMLText += "</tr></table></td>";

   }
   return(HTMLText);
}

//Se generan los botones de navegacion SIGUIENTE y ULTIMO
function generarBotonesPaginaSiguienteUltimo(){
	var result = "";

	if (top.g_FolderSel < top.Main.Table.TableData.g_TotalRows)	{
		result += "<td>";
		result +=   "<IMG src=\"images/trans.gif\" width=\"7\" height=\"1\"/>";
		result +=   "<IMG id=\"imgNext\" src=\"images/rightarrow.gif\" width=\"10\" height=\"11\" title=\"" + top.GetIdsLan("IDS_OPCSIGUIENTE") + "\" border=\"0\" onmouseover=\"this.style.cursor='pointer';\" onclick=\"javascript:if(top.g_OpcAval){top.g_OpcAval=false;Next();}\" onkeydown=\"javascript:if (top.GetKeyCode(event)==13){if(top.g_OpcAval){top.g_OpcAval=false;Next();}}\"/>";
		result +=   "<IMG src=\"images/trans.gif\" width=\"7\" height=\"1\"/>";
		result +=   "<IMG id=\"imgLast\" src=\"images/lastarrow.gif\" width=\"10\" height=\"11\" title=\"" + top.GetIdsLan("IDS_OPCULTIMA") + "\" border=\"0\" onmouseover=\"this.style.cursor='pointer';\" onclick=\"javascript:if(top.g_OpcAval){top.g_OpcAval=false;Last();}\" onkeydown=\"javascript:if (top.GetKeyCode(event)==13){if(top.g_OpcAval){top.g_OpcAval=false;Last();}}\"/>";
		result +=   "<IMG src=\"images/trans.gif\" width=\"7\" height=\"1\"/>";
		result += "</td>";
	}
	else {
		result += "<td>";
		result +=   "<IMG src=\"images/trans.gif\" width=\"28\" height=\"1\"/>";
		result += "</td>";
	}
	return result;
}

//Se generan los botones de navegacion PRIMERO y ANTERIOR
function generarBotonesPaginaPrimeroAnterior(){
	var result = "";

	if (top.g_FolderSel > 1){
		result += "<td>";
		result +=   "<IMG src=\"images/trans.gif\" width=\"10px\" height=\"1px\"/>";
		result +=   "<IMG id=\"imgFirst\" src=\"images/firstarrow.gif\" width=\"10\" height=\"11\" title=\"" + top.GetIdsLan("IDS_OPCPRIMERA") + "\" border=\"0\" onmouseover=\"this.style.cursor='pointer';\" onclick=\"javascript:if(top.g_OpcAval){top.g_OpcAval=false;First();}\" onkeydown=\"javascript:if (top.GetKeyCode(event)==13){if(top.g_OpcAval){top.g_OpcAval=false;First();}}\"/>";
		result +=   "<IMG src=\"images/trans.gif\" width=\"7\" height=\"1\"/>";
		result +=   "<IMG id=\"imgPrev\" src=\"images/leftarrow.gif\" width=\"10\" height=\"11\" title=\"" + top.GetIdsLan("IDS_OPCANTERIOR") + "\" border=\"0\" onmouseover=\"this.style.cursor='pointer';\" onclick=\"javascript:if(top.g_OpcAval){top.g_OpcAval=false;Prev();}\" onkeydown=\"javascript:if (top.GetKeyCode(event)==13){if(top.g_OpcAval){top.g_OpcAval=false;Prev();}}\"/>";
		result +=   "<IMG src=\"images/trans.gif\" width=\"7\" height=\"1\"/>";
		result += "</td>";
	}
	else {
		result += "<td>";
		result +=   "<IMG src=\"images/trans.gif\" width=\"28\" height=\"1\"/>";
		result += "</td>";
	}

	return result;
}

//Se genera el boton de GUARDAR o REGISTRAR segun criterio
function generarBotonRegistrarGuardar(){
	var result = "";
	//comprobamos que boton se ha de pintar
	if( top.g_FolderId == -1 ){
		//REGISTRAR
		result += "<td width=\"100\" id=\"SaveMenuBtn\" class=\"SubOptions\" valign=\"middle\" onmouseover=\"Over(this)\" onmouseout=\"Out(this)\" onclick=\"CloseModify(this);\" onkeydown=\"javascript:if (top.GetKeyCode(event)==13){CloseModify(this);}\" tabIndex=\"1\">";
		result +=   "<div align=\"center\"><IMG id=\"imgsave\" src=\"images/disk.png\" title=\""+ top.GetIdsLan("IDS_OPCREGISTRAR") +"\" border=\"0\" align=\"middle\"/> " + top.GetIdsLan( "IDS_OPCREGISTRAR" ) + "</div>";
		result += "</td>";
	}
	else {
		//GUARDAR
		result += "<td width=\"90\" id=\"SaveMenuBtn\" class=\"SubOptionsDisabled\" valign=\"middle\" onmouseover=\"Over(this)\" onmouseout=\"Out(this)\" onclick=\"CloseModify(this);\" onkeydown=\"javascript:if (top.GetKeyCode(event)==13){CloseModify(this);}\" tabIndex=\"1\">";
		result +=   "<div align=\"center\"><IMG id=\"imgsave\" src=\"images/disk.png\" title=\""+ top.GetIdsLan("IDS_OPCGUARDAR") +"\" border=\"0\" align=\"middle\"/> " + top.GetIdsLan( "IDS_OPCGUARDAR" ) + "</div>";
		result += "</td>";
	}

	return result;
}

//Genera los botones correspondientes a la visualizacion de un registro mediante acceso por URL
function generarBotonesRegistroAccesoPorURL(){
	var result = "";
	//boton ACEPTAR DISTRIBUCION
	result += "<td id=\"Aceptar\" width=\"65\" class=\"SubOptions\" onmouseover=\"Over(this)\" onmouseout=\"Out(this)\" onclick=\"Accept(this);\" onkeydown=\"javascript:if (top.GetKeyCode(event)==13){Accept(this);}\" tabIndex=\"1\">";
	result +=   "<div align=\"center\"><IMG id=\"imgacceptar\" src=\"images/accept.png\" title=\""+ top.GetIdsLan("IDS_BTNACEPTAR") +"\" border=\"0\" align=\"middle\"/> " + top.GetIdsLan("IDS_BTNACEPTAR") + "</div>";
	result += "</td>";

	//boton RECHAZAR DISTRIBUCION
	result += "<td id=\"Rechazar\" width=\"65\" class=\"SubOptions\" onmouseover=\"Over(this)\" onmouseout=\"Out(this)\" onclick=\"SetRemarks(this);\" onkeydown=\"javascript:if (top.GetKeyCode(event)==13){SetRemarks(this);}\" tabIndex=\"1\">";
	result +=   "<div align=\"center\"><IMG id=\"imgrechazar\" src=\"images/rechazar.png\" title=\""+ top.GetIdsLan("IDS_OPC_RECHAZAR") +"\" border=\"0\" align=\"middle\"/> " + top.GetIdsLan("IDS_OPC_RECHAZAR") + "</div>";
	result += "</td>";

	return result;
}

//Boton DISTRIBUCION MANUAL formulario de Registro
function generarBotonDistribucionManualFormularioReg(){
	var result = "";

	result += "<td id=\"DistribucionManual\"  width=\"92\" class=\"SubOptions\" onclick=\"javascript:Distribute(this)\" onmouseover=\"Over(this)\" onmouseout=\"Out(this)\" onkeydown=\"javascript:if (top.GetKeyCode(event)==13){Distribute(this);}\" tabIndex=\"1\">";
	result += "<div align=\"center\"><IMG src=\"images/ico_distrib.gif\" title=\""+ top.GetIdsLan("IDS_BTNDIST") +"\" border=\"0\" align=\"middle\"/> " + top.GetIdsLan( "IDS_BTNDIST" ) + "</IMG></div>";
	result += "</td>";

	return result;
}

//Boton NUEVO REGISTRO
function generarBotonNuevoRegistroBcl(){
	var result = "";

	result +="<td id=\"NewBtn\" width=\"120\" class=\"SubOptionsDisabled\" onmouseover=\"Over(this)\" onmouseout=\"Out(this)\" onclick=\"top.NewFolderBucle(this.className)\" onkeydown=\"if (top.GetKeyCode(event)==13){top.NewFolderBucle(this.className);}\" tabIndex=\"1\">";
	result += "<div align=\"center\"><IMG id=\"imgnewreg\" src=\"images/nuevo_registro.png\" title=\""+ top.GetIdsLan("IDS_OPCNUEVA") +"\" border=\"0\" align=\"middle\"/> " + top.GetIdsLan( "IDS_OPCNUEVA" ) + "</div>";
	result += "</td>";

	return result;
}

//BOTON INTERCAMBIO REGISTRAL
function generarBotonIntercambioRegistral(){
	var result = "";

	result += "<td id=\"SendIRBtn\" width=\"130\" class=\"SubOptionsDisabled\" onmouseover=\"Over(this)\" onmouseout=\"Out(this)\" onclick=\"top.SendIntercambioRegistral(this.className)\" onkeydown=\"if (top.GetKeyCode(event)==13){top.SendIntercambioRegistral(this.className);}\" tabIndex=\"1\">";
	result +=   "<div align=\"center\"><IMG id=\"imgintercambio\" src=\"images/change_dest.png\" title=\""+ top.GetIdsLan("IDS_BTNSENDIR") +"\" border=\"0\" align=\"middle\"/> " + top.GetIdsLan( "IDS_BTNSENDIR_LABEL" ) + "</div>";
	result += "</td>";

	return result;
}

//BOTON LIMPIAR FORMULARIO DEL REGISTRO
function generarBotonLimpiarPantallaRegistro(){
	var result = "";

	result += "<td id=\"ClearRegBtn\" width=\"75\" class=\"SubOptions\" onmouseover=\"Over(this)\" onmouseout=\"Out(this)\" onclick=\"top.ClearFormRegister(this.className)\" onkeydown=\"if (top.GetKeyCode(event)==13){top.ClearFormRegister(this.className);}\" tabIndex=\"1\">";
	result +=   "<div align=\"center\"><IMG id=\"imgClearReg\" src=\"images/clear.gif\" title=\""+ top.GetIdsLan("IDS_BTNLIMPIAR") +"\" border=\"0\" align=\"middle\"/> " + top.GetIdsLan( "IDS_BTNLIMPIAR" ) + "</div>";
	result += "</td>";

	return result;
}

//funcion que pinta los botones DESACTIVADOS de IMPRIMIR, SELLAR Y COMPULSA
function generarBotonesBasicosRegistroDesactivados(EBookType){
	var result = "";

	result += "<td id=\"btnPrintReg\"width=\"90\" class=\"SubOptionsDisabled\" valign=\"middle\">";
	result +=   "<div align=\"center\"><IMG id=\"imgprint\" src=\"images/printer.png\" title=\""+ top.GetIdsLan("IDS_OPCIMPRIMIR") +"\" border=\"0\" align=\"middle\"/> " + top.GetIdsLan( "IDS_OPCIMPRIMIR" ) + "</div>";
	result += "</td>";

	result += "<td width=\"65\" class=\"SubOptionsDisabled\" valign=\"middle\">";
	result +=   "<div align=\"center\" valign=\"middle\"><IMG id=\"imgsello\" src=\"images/sellar.png\" title=\""+ top.GetIdsLan("IDS_OPCSELLO") +"\" border=\"0\" align=\"middle\"/> " + top.GetIdsLan( "IDS_OPCSELLO" ) + "</div>";
	result += "</td>";

	result += "<td id=\"compulsa\" width=\"95\" class=\"SubOptionsDisabled\" valign=\"middle\">";
	result +=   "<div align=\"center\"><IMG id=\"imgcompulsa\" src=\"images/compulsa.png\" title=\""+ top.GetIdsLan("IDS_LABEL_COMPULSA") +"\" border=\"0\" align=\"middle\"/> " + top.GetIdsLan( "IDS_LABEL_COMPULSA" ) + "</div>";
	result += "</td>";

	return result;
}

//funcion que pinta los botones de IMPRIMIR, SELLAR Y COMPULSA
function generarBotonesBasicosRegistro(EBookType){
	var result = "";

	if (!top.g_FdrReadOnly) {
		//boton de IMPRIMIR
		result += "<td id=\"btnPrintReg\" width=\"90\" class=\"SubOptions\" valign=\"middle\" onmouseover=\"Over(this)\" onmouseout=\"Out(this)\" onclick=\"Print(this)\" onkeydown=\"if (top.GetKeyCode(event)==13){Print(this);}\" tabIndex=\"1\">";
		result +=   "<div align=\"center\"><IMG id=\"imgprint\" src=\"images/printer.png\" title=\""+ top.GetIdsLan("IDS_OPCIMPRIMIR") +"\" border=\"0\" align=\"middle\"/> " + top.GetIdsLan( "IDS_OPCIMPRIMIR" ) + "</div>";
		result += "</td>";
	}

	if (!top.g_OpenFolderDtr || (top.g_OpenFolderDtr && top.g_OpenEditDistr && !top.g_FdrReadOnly)) {
		//boton de SELLAR
		result += "<td id=\"selloReg\" width=\"65\" class=\"SubOptions\"  valign=\"middle\" onmouseover=\"Over(this)\" onmouseout=\"Out(this)\" onclick=\"PrintSello(this, 1)\" onkeydown=\"if (top.GetKeyCode(event)==13){PrintSello(this, 1);}\" tabIndex=\"1\">";
		result +=   "<div align=\"center\"><IMG id=\"imgsello\" src=\"images/sellar.png\" title=\""+ top.GetIdsLan("IDS_OPCSELLO") +"\" border=\"0\" align=\"middle\"/> " + top.GetIdsLan( "IDS_OPCSELLO" ) + "</div>";
		result += "</td>";
	}

	if (!top.g_FdrReadOnly) {
		//boton de COMPULSAR
		result += "<td id=\"compulsa\" width=\"95\" valign=\"middle\" class=\"SubOptions\" onmouseover=\"Over(this)\" onmouseout=\"Out(this)\" onclick=\"Compulsa(this)\" onkeydown=\"if (top.GetKeyCode(event)==13){Compulsa(this);}\" tabIndex=\"1\">";
		result +=   "<div align=\"center\"><IMG id=\"imgcompulsa\" src=\"images/compulsa.png\" title=\""+ top.GetIdsLan("IDS_LABEL_COMPULSA") +"\" border=\"0\" align=\"middle\"/> " + top.GetIdsLan( "IDS_LABEL_COMPULSA" ) + "</div>";
		result += "</td>";
	}
	return result;
}

//funcion que genera los botones de configuracion: CONF. SELLO, CONF. ESCANES y CONF. REGISTRO
function generarBotonesDeConfiguracion(){
	var result = "";

	result += "<div id=\"botonesConfiguracion\" name=\"botonesConfiguracion\" style=\"display: none\">";
	result +=  "<table id=\"botonesConfRegistro\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\">";
	result +=    "<tr>";

		// Boton de configurar sello
		if (!top.g_FdrReadOnly)	{
			result += "<td id=\"confSelloReg\" class=\"SubOptions\" width=\"95\" onmouseover=\"Over(this)\" onmouseout=\"Out(this)\" onclick=\"PrintSello(this, 2)\" onkeydown=\"if (top.GetKeyCode(event)==13){PrintSello(this, 2);}\" tabIndex=\"1\">";
			result +=   "<div align=\"center\"><IMG id=\"imgconfigsello\" src=\"images/confsellar.png\" title=\""+ top.GetIdsLan("IDS_OPCCONFSELLO") +"\" border=\"0\" align=\"middle\"/> ";
			result +=    top.GetIdsLan( "IDS_OPCCONFSELLO" );
			result += "</div></td>";
		}
		// Boton de configurar escaner
		if ((top.g_FolderView) && (!top.g_FdrReadOnly))	{
			result += "<td id=\"confScanner\" class=\"SubOptions\" width=\"120\" onmouseover=\"Over(this)\" onmouseout=\"Out(this)\" onclick=\"ConfScanner(this)\" onkeydown=\"if (top.GetKeyCode(event)==13){ConfScanner(this);}\" tabIndex=\"1\">";
			result +=   "<div align=\"center\"><IMG id=\"imgconfigscanner\" src=\"images/confscanner.png\" title=\""+ top.GetIdsLan("IDS_OPCCONFSCAN") +"\" border=\"0\" align=\"middle\"/> ";
			result +=    top.GetIdsLan( "IDS_OPCCONFSCAN" );
			result += "</div></td>";
		}
		// Boton de configurar registro
		if ((top.g_FolderView) && (!top.g_FdrReadOnly))	{
			result += "<td id=\"confRegistro\" class=\"SubOptions\" width=\"115\"  onmouseover=\"Over(this)\" onmouseout=\"Out(this)\" onclick=\"ConfRegister(this)\" onkeydown=\"if (top.GetKeyCode(event)==13){ConfRegister(this);}\" tabIndex=\"1\">";
			result +=   "<div align=\"center\"><IMG id=\"imgconfigregistro\" src=\"images/confregistro.png\" title=\""+ top.GetIdsLan("IDS_FRM_CONF_FLDS") +"\" border=\"0\" align=\"middle\"/> ";
			result +=    top.GetIdsLan( "IDS_FRM_CONF_FLDS" );
			result += "</div></td>";
		}

	result +=    "</tr>";
	result +=  "</table>";
	result + "</div>";

	return result;
}

function OcultarMostrarBotonesConf(){

	if (document.getElementById("botonesConfiguracion").style.display == 'none'){
		//comprobamos si debemos ampliar el tamaño del frame, para incluir los botones de configuración
		if(validateExpandFrame()){
			top.Main.Folder.document.getElementById("frSetFolder").rows="50px,*";
		}
		document.getElementById("imgdesplegarConf").src = "images/flechas_dobles_izq_sin_fondo.png";
		document.getElementById("botonesConfiguracion").style.display = 'block';
	}
	else{
		// incidamos el tamaño del frame, por si ha sido modificado debido a la
		// resolución de la pantalla, lo reseteamos siempre
		top.Main.Folder.document.getElementById("frSetFolder").rows="24px,*";

		document.getElementById("imgdesplegarConf").src = "images/flechas_dobles_dcha_sin_fondo.png";
		document.getElementById("botonesConfiguracion").style.display = 'none';
	}

}

// Función que comprueba si se debe ampliar el tamaño del frame que contiene el
// panel de botones del formulario del registro
function validateExpandFrame(){
	var result = false;

	// comprobamos si el tamaño de la ventana abierta es menor de 1268px (1280 resolución real de pantalla) y el
	// registro no es nuevo (en este caso, tiene los botones básicos y no
	// necesita expanderse el frame)
	if((document.getElementsByTagName('body')[0].clientWidth<1268) && (top.g_FolderId != -1)){
		result = true;
	}

	return result;
}

function FrmNavigate(index)
{
	var btnSave = document.getElementById("SaveMenuBtn");

	if (btnSave != null){
		if (btnSave.className == "SubOptions" )	{
			if( !window.confirm( top.GetIdsLan( "IDS_QRYCONTGUARDAR" ) ) ){
				return false;
			}
		}
	}

	if ((index >= 1) && (index <= top.g_LastReg)){
	    var aux=top.Main.Folder.document.readyState;
	    if(aux && aux!='complete') return;
	    aux=top.Main.Folder.ToolBarFrm.document.readyState;
	    if(aux && aux!='complete') return;
	    aux=parent.FolderData.document.readyState;
	    if(aux && aux!='complete') return;
	    aux=parent.FolderData.FolderFormData.document.readyState;
	    if(aux && aux!='complete') return;
	    aux=parent.FolderData.FolderFormTree.document.readyState;
	    if(aux && aux!='complete') return;
	    aux=parent.FolderData.FolderFormData.document.getElementById("Interesados").document.readyState;
	    if(aux && aux!='complete') return;
	    aux=parent.FolderData.FolderFormData.document.getElementById("Interesados").document.Interesados.document.readyState;
	    if(aux && aux!='complete') return;

		var strParams = "SessionPId=" + top.g_SessionPId.toString();
		var oFrameData = parent.FolderData.FolderFormData;
		var oFrameTree = parent.FolderData.FolderFormTree;
		var itemsFrmData = oFrameData.document.getElementsByTagName("*");
		var items = document.getElementsByTagName("*");

		strParams += "&ArchivePId=" + top.g_ArchivePId.toString();
		strParams += "&ArchiveId=" + top.g_ArchiveId.toString();
		strParams += "&FolderPId=" + top.g_FolderPId.toString();
		strParams += "&FolderId=" + top.g_FolderId.toString();
		strParams += "&FdrQryPId=" + top.g_FdrQryPId.toString();
		strParams += "&Index=" + index.toString();

		top.g_OpcAval=false;
		top.g_ActivateTree=false;
		top.Main.Folder.ToolBarFrm.ToolBarDisabled();

		for (var i = 0; i < itemsFrmData.length; i++){
			var item = itemsFrmData[i];

			if (item.className == "input"){
				item.className = "inputRO";
				item.readOnly = true;
			}
			else if (item.className == "textarea"){
				item.className = "textareaRO";
				item.readOnly = true;
			}
			else if ((item.nodeName == "IMG") && (item.name != "thumbnail")){
				item.style.visibility = "hidden";
			}
		}

		try	{
			if (oFrameData.document.getElementById("Interesados")){
				oFrameData.document.getElementById("Interesados").document.Interesados.Enable(false);
			}
		}
		catch(e){alert(e.description);}

		for (var i = 0; i < items.length; i++){
			if (items[i].className == "SubOptions"){
				items[i].className = "SubOptionsDisabled";
			}
		}

		document.body.style.cursor = "wait";
		oFrameData.document.body.style.cursor = "wait";

		top.g_NextReg = index;

		top.XMLHTTPRequestGet(top.g_URL + "/FrmNavigate.jsp?" + strParams, ResponseNavigate, true);
	}
}


function ResponseNavigate(){
	if (top.g_oXMLHTTPRequest.readyState != 4){
		return;
	}

	if (top.g_oXMLHTTPRequest.status != 200){
		alert(top.g_oXMLHTTPRequest.statusText + " (" + top.g_oXMLHTTPRequest.status.toString() + ")");
		return;
	}

	var HTMLText = top.g_oXMLHTTPRequest.responseText;

	top.g_ActivateTree = true;
	top.g_OpcAval = true;

	if (HTMLText.indexOf("alert(") != -1){
		top.Main.Folder.ToolBarFrm.ToolBarEnabled();

		evalAlert(HTMLText);
	}
	else{
		var aux=top.document.readyState;
	    if(aux && aux!='complete') return;
	    aux=top.Main.document.readyState;
	    if(aux && aux!='complete') return;
	    aux=top.Main.Folder.document.readyState;
	    if(aux && aux!='complete') return;
	    aux=top.Main.Folder.FolderData.document.readyState;
	    if(aux && aux!='complete') return;
	    aux=top.Main.Folder.FolderData.FolderFormTree.document.readyState;
	    if(aux && aux!='complete') return;
	    aux=top.Main.Folder.FolderData.FolderFormData.document.readyState;
	    if(aux && aux!='complete') return;

		top.g_FirstReg = top.g_NextReg;
		top.Main.Folder.FolderBar.LoadCounter();
		top.Main.Folder.ToolBarFrm.ToolBarEnabled();
		top.SetFolderFormTree(HTMLText);
	}
}

//paginacion de registros
function GetCounterHTML()
{
	var HTMLText ="";

	if (top.g_FirstReg > 1)
	{
		HTMLText += "<IMG id=\"imgFirst\" src=\"images/firstarrow.gif\" width=\"10\" height=\"11\" title=\"" + top.GetIdsLan("IDS_ETQPRINCIPIO") + "\" border=\"0\" onmouseover=\"this.style.cursor='pointer';\" onclick=\"if(top.g_OpcAval){FrmNavigate(1);}\" onkeydown=\"if (top.GetKeyCode(event)==13){if(top.g_OpcAval){FrmNavigate(1);}}\"/>";
		HTMLText += "<IMG src=\"images/trans.gif\" width=\"7\" height=\"1\"/>";
		HTMLText += "<IMG id=\"imgPrev\" src=\"images/leftarrow.gif\" width=\"10\" height=\"11\" title=\"" + top.GetIdsLan("IDS_ETQANTERIOR") + "\" border=\"0\" onmouseover=\"this.style.cursor='pointer';\" onclick=\"if(top.g_OpcAval){FrmNavigate(top.g_FirstReg - 1);}\" onkeydown=\"if (top.GetKeyCode(event)==13){if(top.g_OpcAval){FrmNavigate(top.g_FirstReg - 1);}}\"/>";
		HTMLText += "<IMG src=\"images/trans.gif\" width=\"7\" height=\"1\"/>";
	}
	else
	{
		HTMLText += "<IMG src=\"images/trans.gif\" width=\"28\" height=\"1\"/>";
	}

	HTMLText += "<nobr>| " + top.g_FirstReg.toString() + " " + top.GetIdsLan( "IDS_PREPDE" ) + " " + top.g_LastReg.toString() + " |</nobr>";
	HTMLText += "<IMG src=\"images/trans.gif\" width=\"7\" height=\"1\"/>";

	if (top.g_FirstReg < top.g_LastReg)
	{
		HTMLText += "<IMG id=\"imgNext\" src=\"images/rightarrow.gif\" width=\"10\" height=\"11\" title=\"" + top.GetIdsLan("IDS_ETQSIGUIENTE") + "\" border=\"0\" onmouseover=\"this.style.cursor='pointer';\" onclick=\"if(top.g_OpcAval){FrmNavigate(top.g_FirstReg + 1);}\" onkeydown=\"if (top.GetKeyCode(event)==13){if(top.g_OpcAval){FrmNavigate(top.g_FirstReg + 1);}}\"/>";
		HTMLText += "<IMG src=\"images/trans.gif\" width=\"7\" height=\"1\"/>";
		HTMLText += "<IMG id=\"imgLast\" src=\"images/lastarrow.gif\" width=\"10\" height=\"11\" title=\"" + top.GetIdsLan("IDS_ETQFINAL") + "\" border=\"0\" onmouseover=\"this.style.cursor='pointer';\" onclick=\"if(top.g_OpcAval){FrmNavigate(top.g_LastReg);}\" onkeydown=\"if (top.GetKeyCode(event)==13){if(top.g_OpcAval){FrmNavigate(top.g_LastReg);}}\"/>";
		HTMLText += "<IMG src=\"images/trans.gif\" width=\"7\" height=\"1\"/>";
	}
	else
	{
		HTMLText += "<IMG src=\"images/trans.gif\" width=\"28\" height=\"1\"/>";
	}

	return HTMLText;
}

function LoadCounter()
{
	document.getElementById("counter").innerHTML = GetCounterHTML();
}


function Over(obj)
{
   if (obj.className != "SubOptionsDisabled")
   {
	obj.className="SubOptionsOver";
   }
}


function Out(obj)
{
   if (obj.className != "SubOptionsDisabled")
	obj.className="SubOptions";
}


function consOc( OLista )
{
	if ( OLista.className != "" ) {
		if ( OLista.id.search( "_0" ) != -1 ){
			oArrStrUpdate[oArrStrUpdate.length] = "0|" + OLista.className + "|" + OLista.id.substring( 0, OLista.id.indexOf( '_' ) ) + "|-|-|CL-";
			return;
		}
		else {
			if ( OLista.id.search( "LI" ) != -1 ) {
				oArrStrUpdate[oArrStrUpdate.length] = "1|" + OLista.className + "|" + OLista.id + "|" + top.miTrim(top.GetInnerText(GetChildA(OLista)))
					+ "|" + OLista.parentNode.parentNode.id + "|" + OLista.parentNode.parentNode.className;
			}
		}
	}

	for( var ii = 0; ii < OLista.childNodes.length; ii++ ) {
		if (OLista.childNodes[ii].nodeType == 1) {
			consOc( OLista.childNodes[ii]);
		}
	}

	return;
}

function ClearAllInvalids()
{
	var oFrame = parent.FolderData.FolderFormData;

	try {
		for (var i = 0; i < oFrame.document.images.length; i++) {
			if ( (oFrame.document.images[i].name != "thumbnail")
             && ((oFrame.document.images[i].src.indexOf("buscar2.gif") == -1)
		&& (oFrame.document.images[i].src.indexOf("calendarM.gif") == -1))
             && (!oFrame.document.images[i].getAttribute("isObli")) ){
				oFrame.document.images[i].style.display = "none";
			}
		}
	}
	catch(e){}

	for (i = (BadFldArr.length - 1); i >= 0; i--)  {
		delete BadFldArr[i];
	}

	BadFldArr.length = 0;
}


// Elimina todos los elementos que hemos creado. Tipo file y text
function ResetForm()
{
	var Node;
	var Form = document.getElementById("frmStrUpdate");

	for (var i=0; i < Form.length; i++) {
		Node = Form[i];

		if ((Node.type == "text")) {
			Form.removeChild(Node);
			i--;
		}
	}

	// Se borra el array de modificaciones
	oArrStrUpdate.length = 0;
}

function SetBadField(FieldIDStr)
{
   BadFldArr[BadFldArr.length] = new BadFld(FieldIDStr);
}

function ShowFirstBadField()
{
	var bFound = false;
	var Page = 0;

	// Lo hacemos asi para intentar mostrar siempre la primera pagina
	for (var j = 0; j < PageCtrls.length; j++) {
		for (var i = 0; i < BadFldArr.length; i++) {
			if (PageCtrls[j].Id == BadFldArr[i].Id) {
				Page = parseInt(PageCtrls[j].Page);
				bFound = true;
				break;
			}
		}

		if (bFound){
			break;
		}
	}

	if (top.g_Page != Page){
		top.g_Page = Page;

		var URL = top.g_URL + "/frmdata.jsp?SessionPId=" + top.g_SessionPId
			+ "&Row=" + top.Main.Folder.FolderData.FolderFormTree.Row
			+ "&Page=" + Page
			+ "&FdrQryPId=" + top.g_FdrQryPId.toString()
			+ "&FolderPId=" + top.g_FolderPId.toString()
			+ "&ArchivePId=" + top.g_ArchivePId.toString();

		top.XMLHTTPRequestGet(URL, top.ResponseFrmData, true);

		top.Main.Folder.FolderData.FolderFormTree.SelElem(5, Page.toString());
	}
	else {
		top.Main.Folder.FolderData.FolderFormData.MarkBadFields();
	}
}


function ActivateSave()
{
   document.getElementById("SaveMenuBtn").className = "SubOptions";
   top.g_SavePending = true;
   DesactivateCompulsa();
}

function DesactivateSave()
{
   document.getElementById("SaveMenuBtn").className = "SubOptionsDisabled";
   top.g_SavePending = false;
   ActivateCompulsa();
}

function ActivateCompulsa()
{
	// buscamos el boton compulsa
	var botonCompulsa = document.getElementById("compulsa");
	// comprobamos si el boton compulsa existe
	if(botonCompulsa){
	   botonCompulsa.className = "SubOptions";
	   top.g_SavePending = true;
	}
}

function DesactivateCompulsa()
{
   // buscamos el boton compulsa
   var botonCompulsa = document.getElementById("compulsa");
   // comprobamos si el boton compulsa existe
   if(botonCompulsa){
	   botonCompulsa.className = "SubOptionsDisabled";
	   top.g_SavePending = false;
   }
}


function PrintSello(obj, iOpc)
{
	if (obj.className != "SubOptionsDisabled"){
		var oActiveX = null;
		var bPluginInstall = false;
		var bCancelInstall = false;
		var bPrintInstall = false;
		var bPrintConfig = false;
		var FrmTree = top.Main.Folder.FolderData.FolderFormTree.document;
		var FrmData = top.Main.Folder.FolderData.FolderFormData.document;
		var RegNumber = FrmTree.getElementById("RegNumber").value;
		var RegDate = FrmTree.getElementById("RegDate").value;
		var OficCode = FrmTree.getElementById("StampOfficeCode").value;
		var OficDesc = FrmTree.getElementById("StampOfficeDesc").value;
		var UnitCode = FrmTree.getElementById("UnitCode").value;
		var BookType = (parseInt(FrmTree.getElementById("BookType").value, 10) == 1) ? false : true;

		document.body.style.cursor = "wait";
		FrmTree.body.style.cursor = "wait";
		FrmData.body.style.cursor = "wait";

		while (!bPluginInstall) {
			try {
				oActiveX = new ActiveXObject("ISValPrnAsp.ValPrn.4");
				bPluginInstall = true;
			}
			catch(e) {
				if (bCancelInstall) {
					alert(top.GetIdsLan("IDS_MUST_INSTALL_PLUG_IMP"));
					bPluginInstall = true;
				}
				else {
					bCancelInstall = true;
					var URLPlugin = top.g_URL + "/plugins/isvalprn.cab";
					var URL = top.g_URL + "/install.htm?URLPlugin=" + URLPlugin + "&CLSID=14D33624-3A57-4A56-9530-70FD8CC8D093&VerPlugin=-1,-1,-1,-1&Title=" + top.GetIdsLan("IDS_TITLE_INST_SCAN") + "&BgText=" + top.GetIdsLan("IDS_TITLE_INST_SCAN");
					var strRet = top.ShowModalDialog(URL, "", 150, 150, "edge:raised");
				}
			}
		}

		if (oActiveX) {
			try {
				oActiveX.SetLanguage(top.numIdioma);

				if (iOpc == 2){
					oActiveX.Config();
				}
				else {
					while (!bPrintInstall) {
						try {
							oActiveX.Init(RegNumber, RegDate, BookType, OficCode, OficDesc, UnitCode, (InitCountStamp == 1));
							bPrintInstall = true;
						}
						catch(e) {
							if (!bPrintConfig) {
								oActiveX.Config();
								bPrintConfig = true;
							}
							else {
								alert(top.GetIdsLan("IDS_ERR_INIT_PRINT"));
								bPrintInstall = true;
							}
						}
					}

					oActiveX.SendToPrint();
					InitCountStamp++;
				}
			}
			catch(e) {
				alert(top.GetIdsLan("IDS_ERR_INIT_PRINT"));
			}

			delete oActiveX;
			oActiveX = null;

			document.body.style.cursor = "cursor";
			FrmTree.body.style.cursor = "cursor";
			FrmData.body.style.cursor = "cursor";
		}
	}
}


function FldsArrFiles(strIdFile, strPathFile, mustDelete)
{
   this.IdFile = strIdFile;
   this.PathFile = strPathFile;
   this.mustDelete = mustDelete;
}



//Funcion principal de subida de ficheros.
function SendScanFilesToServer(oForm)
{
	top.g_frmStrUpdate = oForm; //Guardamos el formulario para despues usarlo en "ExecuteSendScanFilesToServer"

	var URL = top.g_URL + "/sendFiles.jsp?sessionPId=" + top.g_SessionPId+"&idLibro="+top.g_ArchiveId.toString()+"&idRegistro="+top.g_FolderId.toString();
	window.open(URL, "FolderFormCompulsaData","location=no",true);

}


//Funcion que ejecuta la subida de ficheros al servidor, solo se debe ejecutar si el applet de subir ficheros esta cargado.
	function ExecuteSendScanFilesToServer(applet)
	{
		var result;
		var strFileScan = "";
		var oElemTxt = null;


		if (! top.g_ArrScanFiles) {
			top.g_ArrScanFiles = new Array();
		}
		else {
			top.g_ArrScanFiles.length = 0;
		}

		for(var ii=0; ii < top.g_frmStrUpdate.length; ii++){
			if ( (top.g_frmStrUpdate[ii].type == "file") && (top.g_frmStrUpdate[ii].value == "") ) {
				top.g_ArrScanFiles[top.g_ArrScanFiles.length] = new FldsArrFiles(top.g_frmStrUpdate[ii].getAttribute('name'), top.g_frmStrUpdate[ii].getAttribute('pathfile'), top.g_frmStrUpdate[ii].getAttribute('mustdelete'));
			}
		}
		if (top.g_ArrScanFiles.length > 0){

			if (applet != null){

				//Configurar el applet de subir ficheros, indicamos servlet, un timeout de 30 segundos y que no borre los ficheros subidos.
				applet.configure("FileUploadScan", "30000", false);
				applet.addParam("FolderId", top.g_FolderId.toString());
				applet.addParam("SessionPId", top.g_SessionPId.toString());

				try {
					//Añadir los ficheros al applet
					for (var ii=0; (ii < top.g_ArrScanFiles.length); ii++) {
						//Enviamos el id del fichero LI[IDENTIFICIADOR] y el path del fichero
						applet.addFile(top.g_ArrScanFiles[ii].IdFile, top.g_ArrScanFiles[ii].PathFile);
					}

					//Subir los ficheros
					result = applet.sendFiles();

					// Si se sube correctamente, metemos un campo de texto con la ruta
					if (result){
						for (var ii=0; (ii < top.g_ArrScanFiles.length); ii++) {

							strFileScan = top.g_ArrScanFiles[ii].IdFile + "|" + "\\" + top.GetNamePath(top.g_ArrScanFiles[ii].PathFile);
							oElemTxt = top.g_frmStrUpdate.ownerDocument.createElement("input");
							oElemTxt.setAttribute("type", "text");
							oElemTxt.setAttribute("name", "FileScan");
							oElemTxt.value= strFileScan;

							top.g_frmStrUpdate.appendChild(oElemTxt);
						}
						//Una vez terminada la subida de los ficheros se actualiza la carpeta
						top.Main.Folder.FolderBar.ExecuteUpdateFolder();

					} else {
						alert(top.GetIdsLan("IDS_ERR_SAVE_SCAN_FILES"));
					}
				}
				catch(e){
					alert(e.description);
	            }
			}
		} else{
			//si no hay ficheros para subir al servidor se actualiza los datos de la carpeta
			top.Main.Folder.FolderBar.ExecuteUpdateFolder();
		}


		return;
	}



// Lanza la configuracion del escaner
function ConfScanner(obj)
{
	if (obj.className != "SubOptionsDisabled"){
		var URL = top.g_URL + "/configureEscaner.jsp?sessionPId=" + top.g_SessionPId+"&idLibro="+top.g_ArchiveId.toString()+"&idRegistro="+top.g_FolderId.toString();
		window.open(URL, "FolderFormCompulsaData","location=no",true);
	}
}

// Consigue el FolderId a partir de la fila
function getFolderIdTable(sRow)
{
   var oTable = top.Main.Table.TableData;
   for (var ii=0; ii < oTable.document.getElementById("ResultsTable").rows.length; ii++)
   {
      if (parseInt(oTable.document.getElementById("ResultsTable").rows.item(ii).Row) == parseInt(sRow))
      {
         return oTable.document.getElementById("ResultsTable").rows.item(ii).id;
         break;
      }
   }
   return "-1";
}

// Lanza la ventana modal para elegir los registros que se persisten
function ConfRegister(oButton)
{
	var strParams     = "";
	var strRet        = "";
	var oArrFldsConf  = top.Main.Folder.FolderData.FolderFormData.oArrFldsConf;

	if (oButton.className != "SubOptionsDisabled")	{
		var args = new Array();
		var URL = top.g_URL + "/UserConfig.jsp?SessionPId=" + top.g_SessionPId
			+ "&ArchiveId=" + top.g_ArchiveId.toString() + "&FolderId=" + top.g_FolderId;

		args[0] = top.g_URL;
		args[1] = top.g_SessionPId;
		args[2] = top.g_ArchiveId.toString();
		args[3] = top.Idioma;

		strRet = top.ShowModalDialog(URL, args, 400, 400, "edge:raised;dialogHide:yes");

		if ((strRet != "") && (strRet != null)){
			var XMLDoc = top.GetXMLParser();
			var arrParams, arrFields;
			var Fields = "";
			var frmInt = top.Main.Folder.FolderData.FolderFormData.document.getElementById("Interesados");

			try {
				XMLDoc.async = false;
				XMLDoc.loadXML(strRet);
				arrParams = XMLDoc.documentElement.getElementsByTagName("Parameter");

				for (var i = 0; i < arrParams.length; i++)	{
					if (arrParams[i].getAttribute("Id") == "1"){
						top.Main.Folder.FolderData.FolderFormTree.document.getElementById("PersonValidation").value = (arrParams[i].getAttribute("Checked") == "1");

						if (frmInt != null){
							if (frmInt.getAttribute("readOnly") != "true"){
								// se cambia el icono de Interesados habilitado segun se conmute

								top.Main.Folder.FolderData.FolderFormData.document.getElementById("HabilitarValidationInt").value = arrParams[i].getAttribute("Checked");

								var imagen = top.Main.Folder.FolderData.FolderFormData.document.getElementById("imgValInter");

								if(top.Main.Folder.FolderData.FolderFormData.document.getElementById("HabilitarValidationInt").value>0){
									imagen.src="images/user_check.png";
									imagen.style.display = 'block';
								}
								else{
									imagen.src="images/user_check_deshabilitado.png";
									imagen.style.display = 'block';
								}

								frmInt.document.Interesados.Conmute((arrParams[i].getAttribute("Checked") == "1"));
							}
						}
					}

					if (arrParams[i].getAttribute("Id") == "2"){
						top.Main.Folder.FolderData.FolderFormTree.document.getElementById("ShowScannerUI").value = arrParams[i].getAttribute("Checked");
					}
				}

				arrFields = XMLDoc.documentElement.getElementsByTagName("Field");

				for (i = 0; i < arrFields.length; i++) {
					Fields = Fields + arrFields[i].getAttribute("Id").toString() + ";";
				}

				top.Main.Folder.FolderData.FolderFormTree.document.getElementById("Fields").value = Fields;
			}
			catch(e){
				alert(e.description);
			}
		}

		top.setFormFocus(top.Main.Folder.FolderData.FolderFormData.document.getElementById("FrmData"), top.Main.Folder.FolderData.FolderFormData.g_FormWidth, top.Main.Folder.FolderData.FolderFormData.g_FormHeight);
		oButton.className="SubOptions";
	}
}

//Lanza el applet de subir ficheros para borrar los ficheros escaneados del local
function DeleteScanFiles()
{
	//alert("DeleteScanFiles");
	//top.sendfiles_open(ExecuteDeleteScanFiles);
	var URL = top.g_URL + "/deleteLocalFiles.jsp?sessionPId=" + top.g_SessionPId+"&idLibro="+top.g_ArchiveId.toString()+"&idRegistro="+top.g_FolderId.toString();
	window.open(URL, "FolderFormCompulsaData","location=no",true);

}

//Funcion que se ejecutara para borrar ficheros en local. Esta funcion se llama una vez cargado el applet.
function ExecuteDeleteScanFiles(applet)
{
	if (top.g_ArrScanFiles) {

		try {
			for(var ii=0; ii < top.g_ArrScanFiles.length; ii++) {
				if (top.g_ArrScanFiles[ii].mustDelete == "1"){
					applet.deleteFile(top.g_ArrScanFiles[ii].PathFile);
				}
			}
		}
		catch(e){
			alert(top.GetIdsLan("IDS_NOT_DELETE_FILES"));
		}


		// se destruye el array
		for(var jj=0; jj < top.g_ArrScanFiles.length;jj++) {
			delete top.g_ArrScanFiles[jj];
		}

		top.g_ArrScanFiles.length = 0;
		top.g_ArrScanFiles = null;
	}

	return;
}

// asigna un valor a todos los campos sustituto de un determinado FLDID
// excepto para los campos interesados
function AsignValueToFldsSust(strValue, strFldId)
{
	try	{
		var oForm = parent.FolderData.FolderFormData.document.getElementById("FrmData");

		for (var ii=0; ii < oForm.elements.length; ii++) {
			if ((oForm.elements[ii].FldId == strFldId) && (oForm.elements[ii].Sustituto)) {
				oForm.elements[ii].value = strValue;
			}
		}
	}
	catch(e){}
}

function SaveConfig()
{
	var items = document.getElementsByTagName("IMG");
	var Fields = "";
	var Params = "";

	for (var i = 0; i < items.length; i++) {
		var item = items[i];

		if ((item.getAttribute("Ind") == "0") && (item.src.indexOf("uncheck.gif") == -1)){
			if (Fields == "") {
				Fields = item.id;
			}
			else {
				Fields = Fields + ";" + item.id;
			}
		}
		else if ((item.getAttribute("Ind") == "1") && (item.src.indexOf("uncheck.gif") == -1)){
			if (Params == "") {
				Params = item.id;
			}
			else {
				Params = Params + ";" + item.id;
			}
		}
	}

	var Data = "Fields=" + escape(Fields) + "&Params=" + escape(Params);
	var URL = top.GetDlgParam(0) + "/SaveUserConfig.jsp?SessionPId=" + top.GetDlgParam(1)
		+ "&BookId=" + top.GetDlgParam(2);

	document.body.style.cursor = "wait";

	top.XMLHTTPRequestPost(URL, SaveConfigResponse, true, Data);
}


function SaveConfigResponse()
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
	else{
		top.returnValue = top.g_oXMLHTTPRequest.responseText;
		top.close();
	}
}

function Accept(opcion)
{
	ExecuteOperation("dtraceptex.jsp", STATE_DISTR_PENDIENTE);
}
function ExecuteOperation(page, State)
{
	  var oForm = document.getElementById("formDist");
	  top.Main.Folder.FolderBar.document.body.style.cursor = "wait";
	  DisabledToolbar();

        oForm.action = top.g_URL + "/" + page + "?SessionPId=" + top.g_SessionPId +
            "&TypeDistr=1&InitValue=0";
        oForm.submit();
}

function SelectBook(HTMLCode, InitValue)
{
	var args = new Array();
	var sRet;

	args[0] = HTMLCode;
	args[1] = top.g_URL;
	args[2] = top.g_SessionPId.toString();
	args[3] = top.Idioma;

	sRet = top.ShowModalDialog(top.g_URL + "/validatebooks.htm", args, 275, 500, "");

	top.Main.Folder.document.body.style.cursor = "wait";

	if ((sRet != null) && (sRet != ""))	{
		var selectTypeDistr = 1;
		var oForm = document.getElementById("formDist");

        oForm.action = top.g_URL + "/dtraceptex.jsp?SessionPId=" + top.g_SessionPId +
            "&TypeDistr=1&InitValue=0&BookId=" + sRet;
        oForm.submit();
	}
}

function SetRemarks(opcion)
{
	var args = new Array();
	var caseSensitive = 'CI';
	args[0] = "";
	args[1] = 1;
	args[2] = top.GetIdsLan( "IDS_OPC_RECHAZAR" );
	args[3] = top.Idioma;
	args[4] = caseSensitive;
	args[5] = 1;


		var resp = top.ShowModalDialog(top.g_URL + "/showremarks.htm", args, 230, 370, "");

		if ((resp != "") && (resp != null)){
			var oForm = document.getElementById("formDist");

			document.getElementById("Remarks").value = resp;

			DisabledToolbar();
			top.Main.Folder.FolderBar.document.body.style.cursor = "wait";


			oForm.action = top.g_URL + "/dtrrechex.jsp?SessionPId=" + top.g_SessionPId +
				"&TypeDistr=1&InitValue=0";
			oForm.submit();
		}
}

function DisabledToolbar()
{
    top.Main.Folder.FolderBar.document.getElementById("Rechazar").className = "SubOptionsDisabled";
    top.Main.Folder.FolderBar.document.getElementById("Aceptar").className = "SubOptionsDisabled";
    top.Main.Folder.ToolBarFrm.ToolBarDisabled();

}

function EnabledToolbar()
{
    top.Main.Folder.FolderBar.document.getElementById("Rechazar").className = "SubOptions";
    top.Main.Folder.FolderBar.document.getElementById("Aceptar").className = "SubOptions";
    top.Main.Folder.ToolBarFrm.ToolBarEnabled();
    top.Main.Folder.FolderBar.document.body.style.cursor = "cursor";

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
 * función que permite la distribución manual desde el formulario de registro
 */
function Distribute(obj){
	var sRet;
	var args = new Array;
	var URL = top.g_URL + "/dtrselectuser.jsp";
	var tabData;
	var numCheck = 0;

	if (obj.className == "SubOptionsDisabled"){
		return;
	}

	args[0] = top.g_URL;
	args[1] = top.g_SessionPId.toString();
	args[2] = top.g_ArchivePId.toString();
	args[3] = 1 + "_" + top.g_FolderId.toString();
	args[4] = top.Idioma;
	args[5] = top.g_CaseSensitive;

	sRet = top.ShowModalDialog(URL, args, 450, 500, "");
	if (sRet != null && sRet != ""){
		var tokens = sRet.split("#");

		if (tokens.length > 1){
			var IdColDist = GetIdColumn(tokens[0]);
			var arrFdrId = tokens[1].split("_");

			if (IdColDist != -1){
				for (i = 1; i < tabData.rows.length; i++){
					if (tabData.rows[i].cells[1].firstChild.checked == true){
							for (var j = 0; j < arrFdrId.length; j++){
								if (tabData.rows[i].id == arrFdrId[j]){
									top.SetInnerText(tabData.rows[i].cells[IdColDist], 'SI');
									break;
								}
							}

					}
				}
			}
		}
	}
}