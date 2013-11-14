var g_RowSel      = 0;
var g_TotalRows   = 0;
var g_FirstRow    = 0;
var g_LastRow     = 0;
var g_RowsVisible = 0;
var g_FolderIdSel = 0;
var g_AutoDist    = 0;
var g_NumAjaxStop = 0;

function OnWindowLoad()
{
	var tabData = document.getElementById("ResultsTable");
	var bPreSelected = false;

	if (top.g_ArchiveName != '') {
		top.SetArchiveName(top.g_ArchiveName,'TBL');
	};

	if (top.g_ShowTable){
		// Cargamos las dos toolbar
		top.ShowTableFr();
	}
	else {
		top.Main.Folder.FolderBar.OpenFolderEx();
	}

	for (var i = 0; i < tabData.rows.length; i++){
		if (FindSelection(tabData.rows[i].cells[1].firstChild.value)){
			if (g_RowSel == 0)	{
				SetRowSel(i, parseInt(tabData.rows[i].id));
				tabData.rows[i].cells[1].firstChild.checked = true;
			}
			else {
				resaltarSeleccion(tabData.rows[i]);
				tabData.rows[i].cells[1].firstChild.checked = true;
			}

			bPreSelected = true;
		}
	}

	if (!bPreSelected){
		SetRowSel(1, parseInt(tabData.rows[1].id));
	}

	if (document.getElementById("FolderNum")){
		document.getElementById("FolderNum").value = top.g_FolderSel.toString();
	}

	if (document.getElementById("UpdProtectedFlds").value == "0"){
		document.getElementById("opEdit").className = "SubOptionsDisabled";
	}

	if (document.getElementById("CanDist").value == "0"){
		document.getElementById("opDist").className = "SubOptionsDisabled";
	}

	if (document.getElementById("CanOpenReg").value == "0"){
		document.getElementById("opOpen").className = "SubOptionsDisabled";
	}

	if (((top.g_BookPerms >> (top.POS_PERM_CREAR)) % 2 == 0) || top.g_bIsLockBook){
		document.getElementById("opCopy").className = "SubOptionsDisabled";
	document.getElementById("NewFolderBtn").className = "SubOptionsDisabled";
	}

	if (top.g_bIsLockBook || top.g_OfficeEnabled == "false"){
		document.getElementById("opDist").className = "SubOptionsDisabled";
		document.getElementById("opOpen").className = "SubOptionsDisabled";
		document.getElementById("opCopy").className = "SubOptionsDisabled";
		document.getElementById("opEdit").className = "SubOptionsDisabled";

	}

	for (var i = 0; i < document.getElementsByTagName("div").length; i++) {
		if (document.getElementsByTagName("div").item(i).getAttribute("name") == "nbsp"){
			document.getElementsByTagName("div").item(i).innerHTML = "&nbsp;";
		}
	}
}


function SetTotalRows(TotalRows)
{
   g_TotalRows = TotalRows;
}


function SetFirstRow(FirstRow)
{
   g_FirstRow = FirstRow;
}


function SetRowsVisible(RowsVisible)
{
   g_RowsVisible = RowsVisible;
   g_LastRow = g_FirstRow + RowsVisible - 1;
}

function SetAutoDist(autoDist)
{
	g_AutoDist = autoDist;
}

function OnClickFormBtn()
{
	if(top.g_OpcAval){
		OpenForm();
		deshabilitar();
	}
}


//funcion que se invoca desde el boton volver de las consultas de registros
//(busqueda simple o busqueda avanzada)
function OnClickQryBtn()
{
	if(top.g_OpcAval){
		//deshabilitamos los botones de busqueda/formulario
		deshabilitar();
		//comprobamos el tipo de busqueda
		if (top.g_typeSearchAdvanced) {
			//cargamos la pantalla de busqueda avanzada
			top.ShowAdvancedQuery();
		}else{
			//cargamos la pantalla de busquesa simple
			top.ShowQuery();
		}
		// activamos los botones correspondientes a nuevo registro, relaciones,
		// intercambio registral...
		top.Main.Workspace.EnabledTool();
		//vaciamos la seleccion de registros (seleccionados en el resultado de busqueda)
		EmptySelection();
	}

	if(top.g_typeSearchAdvanced){
		// si es la busqueda avanzada invocamos al servlet para cargar los
		// operadores/campos...
		reloadTypeSearchAvanced();
	}
}

function reloadTypeSearchAvanced() {

	var URL = top.g_URL + "/qryinitadvansearch.jsp?SessionPId="
			+ top.g_SessionPId
			+ "&ArchiveId=" + top.g_ArchiveId
			+ "&ArchivePId=" + top.g_ArchivePId.toString()
			+ "&method=reload";

	window.open(URL, "Query","location=yes",true);
}

function SetRowSel(Row, FolderId)
{
	var table = document.getElementById('ResultsTable');

	if (g_RowSel != Row) {
		top.g_FolderId = FolderId;
		g_FolderIdSel = FolderId;

		if (g_RowSel > 0) {
			for (var i = 0; i < table.rows.length; i++){
				resetearStyleSeleccion(table.rows[i]);
				table.rows[i].cells[1].firstChild.checked = false;
			}

			EmptySelection();
		}

		g_RowSel = Row;

		resaltarSeleccion(table.rows[Row]);
		top.SetTableFocus(table.rows[Row]);

		if(top.Main.Table.TableData.g_FirstRow == 0){
			top.Main.Table.TableData.g_FirstRow = document.getElementById("FolderNum").value;
		}

		top.g_FolderSel = parseInt(top.Main.Table.TableData.g_FirstRow) + parseInt(top.Main.Table.TableData.g_RowSel - 1);

		if (document.getElementById("FolderNum")){
			document.getElementById("FolderNum").value = top.g_FolderSel.toString();
		}
	}
}


function OnPressKey(aEvent)
{
	var key = top.GetKeyCode(aEvent);

	switch(key)	{
		case 40: {
			if (g_RowSel < document.getElementById("ResultsTable").rows.length - 1)	{
				SetRowSel(g_RowSel + 1, document.getElementById("ResultsTable").rows[g_RowSel + 1].id);
			}

			break;
		}
		case 38: {
			if (g_RowSel > 1) {
				SetRowSel(g_RowSel - 1, document.getElementById("ResultsTable").rows[g_RowSel - 1].id);
			}

			break;
		}
	}
}


function OnDblClick(ev)
{
	top.StopPropagation(ev);
}


function OnClickCheck(check, ev)
{
	var tabData = document.getElementById("ResultsTable");

	for (var i = 0; i < tabData.rows.length; i++){
		if (tabData.rows[i].cells[1].firstChild.value == check.value){
			if (check.checked == true){
				resaltarSeleccion(tabData.rows[i]);

				if (tabData.rows[g_RowSel].cells[1].firstChild.checked == false){
					resetearStyleSeleccion(tabData.rows[g_RowSel]);
				}

				AddSelection(check.value);
			}
			else {
				resetearStyleSeleccion(tabData.rows[i]);

				DelSelection(check.value);
			}

			break;
		}
	}

	for (i = 0; i < tabData.rows.length; i++) {
		if (tabData.rows[i].cells[1].firstChild.checked == true){
			g_RowSel = i;
			top.g_FolderId = tabData.rows[i].cells[1].firstChild.value;
			top.g_FolderSel = top.Main.Table.TableData.g_FirstRow + top.Main.Table.TableData.g_RowSel - 1;

			if (document.getElementById("FolderNum")){
				document.getElementById("FolderNum").value = top.g_FolderSel.toString();
			}

			break;
		}
	}

	OnDblClick(ev);
}


function ShowTable()
{
	top.g_ShowTable = true;

	if (top.g_FolderSel != (top.Main.Table.TableData.g_FirstRow + top.Main.Table.TableData.g_RowSel - 1)) {
		window.open(top.g_URL + "/tbltext2.jsp?SessionPId=" + top.g_SessionPId + "&Row=" + top.g_FolderSel
			+ "&FdrQryPId=" + top.g_FdrQryPId.toString(), "TableData","location=no",true);
	}
	else {
		top.ShowTableFr();
		habilitar();
	}
}


// Abre una carpeta de las que aparecen en la lista
function OpenExistFolder(iIdFolder, index, total)
{
	if (top.g_sinPulsar)
	{
		top.g_sinPulsar=false;
		top.OpenFolder(iIdFolder, index, total);
	}

	return;
}


function AsigFocus()
{
	var table = document.getElementById('ResultsTable');

	if ( (g_RowSel >= 1) && (g_RowSel <= g_LastRow) ) {
		top.SetTableFocus(table.rows[g_RowSel]);
	}
	else  {
		if ((top!=null)&&(table!=null))
			top.SetTableFocus(table.rows[1]);
	}

	return;
}


function AddSelection(FdrId)
{
	if (top.g_ArrFdrSelected.length >= 254)
	{
		alert(top.GetIdsLan("IDS_MSG_EXCEDEED_SELECTION"));
	}
	else
	{
		top.g_ArrFdrSelected.push(FdrId);
	}
}


function DelSelection(FdrId)
{
	for (var i = 0;  i < top.g_ArrFdrSelected.length; i++)
	{
		if (top.g_ArrFdrSelected[i] == FdrId)
		{
			top.g_ArrFdrSelected.splice(i, 1);
		}
	}
}

//vacia la variable top.g_ArrFdrSelected que contiene los registros
//seleccionados en pantalla
function EmptySelection()
{
	while (top.g_ArrFdrSelected.length > 0)
	{
		top.g_ArrFdrSelected.pop();
	}
}


function FindSelection(FdrId)
{
	for (var i = 0;  i < top.g_ArrFdrSelected.length; i++)
	{
		if (top.g_ArrFdrSelected[i] == FdrId)
		{
			return true;
		}
	}

	return false;
}


function GetBarText()
{
	var HTMLText = "<td width='65%'></td>";

	if (top.Main.Table.TableData.g_TotalRows > 0){

		HTMLText += "<td id=\"opExcel\" class=\"SubOptions2\" onclick=\"exportExcel()\" onmouseover=\"OverTbltext(this)\" onmouseout=\"OutTbltext(this)\" onkeydown=\"javascript:if (top.GetKeyCode(event)==13){Edit(this);}\" tabIndex=\"1\">";
		HTMLText += "<div align=\"center\" style=\"vertical-align:middle\"><IMG src=\"images/excel.png\" align=\"middle\"/>"+ top.GetIdsLan( "IDS_OPC_EXCEL" ) + "</IMG></div>";
		HTMLText += "</td>";

		HTMLText += "<td id=\"opEdit\" class=\"SubOptions2\" onclick=\"Edit(this)\" onmouseover=\"OverTbltext(this)\" onmouseout=\"OutTbltext(this)\" onkeydown=\"javascript:if (top.GetKeyCode(event)==13){Edit(this);}\" tabIndex=\"1\">";
		HTMLText += "<div align=\"center\" style=\"vertical-align:middle\"><IMG src=\"images/edit.png\" align=\"middle\"/>"+ top.GetIdsLan( "IDS_OPC_EDITAR" ) + "</IMG></div>";
		HTMLText += "</td>";

		HTMLText += "<td  id=\"opCopy\" class=\"SubOptions2\" onclick=\"Copy(this)\" onmouseover=\"OverTbltext(this)\" onmouseout=\"OutTbltext(this)\" onkeydown=\"javascript:if (top.GetKeyCode(event)==13){Copy(this);}\" tabIndex=\"1\">";
		HTMLText += "<div align=\"center\" style=\"vertical-align:middle\"><IMG src=\"images/copy.png\" align=\"middle\"/> " + top.GetIdsLan( "IDS_OPC_COPIAR" ) + "</IMG></div>";
		HTMLText += "</td>";

		HTMLText += "<td class=\"SubOptions2\" onclick=\"javascript:Print()\" onmouseover=\"OverTbltext(this)\" onmouseout=\"OutTbltext(this)\" onkeydown=\"javascript:if (top.GetKeyCode(event)==13){Print();}\" tabIndex=\"1\">";
		HTMLText += "<div align=\"center\" style=\"vertical-align:middle\"><IMG src=\"images/printer.png\" align=\"middle\"/> " + top.GetIdsLan( "IDS_OPCIMPRIMIR" ) + "</IMG></div>";
		HTMLText += "</td>";

		HTMLText += "<td  id=\"opDist\" class=\"SubOptions2\" onclick=\"javascript:Distribute(this)\" onmouseover=\"OverTbltext(this)\" onmouseout=\"OutTbltext(this)\" onkeydown=\"javascript:if (top.GetKeyCode(event)==13){Distribute(this);}\" tabIndex=\"1\">";
		HTMLText += "<div align=\"center\" style=\"vertical-align:middle\"><IMG src=\"images/ico_distrib.gif\" align=\"middle\"/> " + top.GetIdsLan( "IDS_BTNDIST" ) + "</IMG></div>";
		HTMLText += "</td>";

		HTMLText += "<td id=\"opOpen\" class=\"SubOptions2\" onclick=\"javascript:OpenReg(this)\" onmouseover=\"OverTbltext(this)\" onmouseout=\"OutTbltext(this)\" onkeydown=\"javascript:if (top.GetKeyCode(event)==13){OpenReg(this);}\" tabIndex=\"1\">";
		HTMLText += "<div align=\"center\" style=\"vertical-align:middle\"><IMG src=\"images/folder.gif\" align=\"middle\"/> " + top.GetIdsLan( "IDS_BTNOPENREG" ) + "</IMG></div>";
		HTMLText += "</td>";

	}

	return(HTMLText);
}

function GetBarText2()
{
    var HTMLText = "<TH class=\"report3\" width=\"15px\"></TH>";

	if (top.Main.Table.TableData.g_TotalRows > 0){
		HTMLText += "<TH class=\"report3\" height=\"20\" width=\"115px\" align=\"center\">";
	    HTMLText += top.GetIdsLan( "IDS_OPCCARPETA" ) + ": ";

		if (top.Main.Table.TableData.g_TotalRows > top.Main.Table.TableData.g_RowsVisible)	{
			HTMLText += "<input style=\"width:40px; height:18px\" class=\"input\" id=\"FolderNum\" onKeyPress=\"OnKey(event);\" tabIndex=\"1\"></input>";
		}
		else{
			HTMLText += "<input style=\"width:40px; height:18px\" class=\"input\" id=\"FolderNum\" readonly=\"1\" tabIndex=\"-1\"></input>";
		}
		HTMLText += "</TH>";
		if (top.Main.Table.TableData.g_TotalRows > top.Main.Table.TableData.g_RowsVisible){
			HTMLText += "<TH class=\"report3\" align=\"left\" onclick=\"javascript:GoTo();\" onmouseover=\"this.style.cursor='pointer';\" onkeydown=\"javascript:if (top.GetKeyCode(event)==13){GoTo();}\" tabIndex=\"1\">";
		}
		else{
			HTMLText += "<TH class=\"reportDisable2\">";
		}
		HTMLText += top.GetIdsLan( "IDS_OPCIR" ) + "</TH>";

		if (top.Main.Table.TableData.g_FirstRow > 1){
			HTMLText += "<TH class=\"report3\" align=\"left\">";
			HTMLText += "<IMG src=\"images/trans.gif\" width=\"20px\" height=\"1px\"/><IMG id=\"imgFirst\" src=\"images/firstarrow.gif\" width=\"10px\" height=\"11px\" title=\""+ top.GetIdsLan("IDS_ETQPRINCIPIO") +"\" border=\"0\" onclick=\"javascript:if(top.g_OpcAval){top.g_OpcAval=false;navigateOrdenado(0);}\" onmouseover=\"this.style.cursor='pointer';\"/><IMG src=\"images/trans.gif\" width=\"7px\" height=\"1px\"/>";
			HTMLText += "<IMG id=\"imgPrev\" src=\"images/leftarrow.gif\" width=\"10px\" height=\"11px\" title=\"" + top.GetIdsLan("IDS_ETQANTERIOR") + "\" border=\"0\" onclick=\"javascript:if(top.g_OpcAval){top.g_OpcAval=false;navigateOrdenado(-2);}\" onmouseover=\"this.style.cursor='pointer';\"/><IMG src=\"images/trans.gif\" width=\"10px\" height=\"1px\"/>";
		}
		else{
			HTMLText += "<TH class=\"report3\" align=\"left\"><IMG src=\"images/trans.gif\" width=\"20px\" height=\"1\"/>";
		}

		HTMLText += "<nobr>| " + top.GetIdsLan( "IDS_OPCCARPETA" )
			+ " " + top.Main.Table.TableData.g_FirstRow + " - " + top.Main.Table.TableData.g_LastRow + " "
			+ top.GetIdsLan("IDS_PREPDE" ) + " " + top.Main.Table.TableData.g_TotalRows + " |</nobr>";

		if (top.Main.Table.TableData.g_LastRow < top.Main.Table.TableData.g_TotalRows){
			HTMLText += "<IMG src=\"images/trans.gif\" width=\"10px\" height=\"1px\"/><IMG id=\"imgNext\" src=\"images/rightarrow.gif\" width=\"10px\" height=\"11px\" title=\""+ top.GetIdsLan("IDS_ETQSIGUIENTE") + "\" border=\"0\" onclick=\"javascript:if(top.g_OpcAval){top.g_OpcAval=false;navigateOrdenado(-3);}\" onmouseover=\"this.style.cursor='pointer';\"/>";
			HTMLText += "<IMG src=\"images/trans.gif\" width=\"7px\" height=\"1px\" /><IMG id=\"imgLast\" src=\"images/lastarrow.gif\" width=\"10px\" height=\"11px\" title=\""+ top.GetIdsLan("IDS_ETQFINAL") + "\" border=\"0\" onclick=\"javascript:if(top.g_OpcAval){top.g_OpcAval=false;navigateOrdenado(-1);}\" onmouseover=\"this.style.cursor='pointer';\"/>";
		}

		HTMLText += "</TH><TH class=\"report3\" width=\"30%\"></TH>";
		HTMLText +="<TH class=\"report3\" align=\"right\"><input class=\"checkbox\" type=\"checkbox\" name=\"checkSel\" value=\"6\" IdArch=\"2\" IdFdr=\"1\" onclick=\"CheckAllSel()\" tabIndex=\"1\" ID=\"checkSel\" /><LABEL id=\"SelAll\">"+ top.GetIdsLan( "IDS_OPCSELECT" ) + "</SCRIPT></LABEL></TH>";
		HTMLText +="<TH class=\"report3\" align=\"right\"><input class=\"checkbox\" type=\"checkbox\" name=\"checkDesSel\" value=\"6\" IdArch=\"2\" IdFdr=\"1\" onclick=\"CheckDesAllSel()\" tabIndex=\"1\" ID=\"checkDesSel\"/><LABEL id=\"DesSelAll\">" + top.GetIdsLan( "IDS_QUITARSELECT" ) + "</SCRIPT></LABEL></TH>";
		HTMLText +="<TH class=\"report3\" width=\"2\"></TH>";
	}

	return(HTMLText);
}

function OverTbltext(obj)
{
   if (obj.className != "SubOptionsDisabled")
   {
	obj.className="SubOptionsOver2";
   }
}


function OutTbltext(obj)
{
   if (obj.className != "SubOptionsDisabled")
   {
	obj.className="SubOptions2";
   }
}


function DoOnKeyDown(obj, aEvent)
{
	if (top.GetKeyCode(aEvent) == 13){
		obj.onclick();
	}
}


function deshabilitar()
{
	document.getElementById("QryBtn").className="SubOptionsDisabled";
	document.getElementById("FormBtn").className="SubOptionsDisabled";
	top.g_OpcAval=false;
}

function habilitar()
{
	document.getElementById("QryBtn").className="Options";
	document.getElementById("FormBtn").className="Options";
	top.g_OpcAval=true;
}

function OpenForm()
{
	var Row = g_FirstRow + g_RowSel - 1;
	var strParams = "ArchiveId=" + top.g_ArchiveId.toString();

	strParams += "&SessionPId=" + top.g_SessionPId.toString();
	strParams += "&FolderId=" + top.g_FolderId.toString();
	strParams += "&ArchivePId=" + top.g_ArchivePId.toString();
	strParams += "&FdrQryPId=" + top.g_FdrQryPId.toString();
	strParams += "&Row=" + Row.toString();
	strParams += "&Form=true";

	top.XMLHTTPRequestGet(top.g_URL + "/openfolder.jsp?" + strParams, top.ResponseOpenFolder, true);
}

function CheckAllSel(){
	document.getElementById("checkDesSel").checked=false;
	CheckAll(document.getElementById("checkSel"));
	// recorremos la tabla para resaltar los registros seleccionados
	var tabData = document.getElementById("ResultsTable");
	for (var i = 0; i < tabData.rows.length; i++){
		if (tabData.rows[i].cells[1].firstChild.checked == true){
			resaltarSeleccion(tabData.rows[i]);
			AddSelection(tabData.rows[i].cells[1].firstChild.value);
		}
		else{
			resetearStyleSeleccion(tabData.rows[i]);
			DelSelection(tabData.rows[i].cells[1].firstChild.value);
		}
	}
}

function CheckDesAllSel(){
	document.getElementById("checkSel").checked=false;
	CheckAll(document.getElementById("checkSel"));
	document.getElementById("checkDesSel").checked=false;

	// recorremos la tabla para resetear los registros
	var tabData = document.getElementById("ResultsTable");
	for (var i = 0; i < tabData.rows.length; i++){
		resetearStyleSeleccion(tabData.rows[i]);
		DelSelection(tabData.rows[i].cells[1].firstChild.value);
	}
}

function CheckAll(Obj)
{
	var checks = document.getElementsByName("checkrow");
	var SelAll = document.getElementById("SelAll");

	if (checks.length){
		for (var i = 0; i < checks.length; i++){
			checks[i].checked = Obj.checked;
		}
	}
	else
	{
		document.getElementById("checkrow").checked = Obj.checked;
	}
}

function clickOrderCampoTable(fldId)
{
	// insertamos la funcion jquery que ordena
	insertarfuncionjquery();

	var orden = "";
	var cadena = document.getElementById("ordenCampo" + fldId).src;
	cadena = cadena.substring(cadena.indexOf("images/")+7,cadena.length);

	if (cadena == "asc.gif"){
		document.getElementById("ordenCampo" + fldId).src = "./images/desc.gif";
		orden="DESC";
	}
	else if(cadena == "desc.gif"){
		document.getElementById("ordenCampo" + fldId).src = "./images/bg.gif";
	}
	else{
		document.getElementById("ordenCampo" + fldId).src = "./images/asc.gif";
		orden="ASC";
	}

	// funcion que graba los datos de ordenacion en el campo hidden orderByTable
	writeInputOrderByTable(fldId, orden);
}

function writeInputOrderByTable(fldId, orden){
	var input = document.getElementById("orderByTable").value;
	var varFldId = "FLD" + fldId + " ";

	if(input.indexOf(varFldId)==-1){
		if((input!="") && (input!=null)){
			input += ",";
		}
		input += varFldId + orden;
	}else{
		var inputFragmentado = input.split(',');
		if (orden != ""){
			// modificamos el elemento para el ordenado
			for (var i = 0; i < inputFragmentado.length; i++) {
				if(inputFragmentado[i].indexOf(varFldId)==0){
					inputFragmentado[i] = varFldId + orden;
				}
			}
		}else{
			// debemos borrar el elemento del orden
			for (var i = 0; i < inputFragmentado.length; i++) {
				if(inputFragmentado[i].indexOf(varFldId)==0){
					inputFragmentado[i] = "";
				}
			}
		}

		// construimos de nuevo el String con el orden generado
		input ="";
		for (var i = 0; i < inputFragmentado.length; i++) {
			if(inputFragmentado[i]!=""){
				if (input != ""){
					input +=",";
				}
				input += inputFragmentado[i];
			}
		}
	}
	document.getElementById("orderByTable").value = input;
}


function orderTable()
{
	//esta variable indica el numero de registro en que estamos situados
	var iType = top.Main.Table.TableData.g_FirstRow;

	var orderByTable = document.getElementById("orderByTable").value;
	return (top.g_URL + "/tbltext2.jsp?SessionPId=" + top.g_SessionPId + "&Row=" +  iType.toString() + "&FdrQryPId=" + top.g_FdrQryPId.toString()+ "&orderByTable=" + escape(orderByTable));
}

function insertarfuncionjquery()
{
	//Al cargar la pagina mediante ajax no se estan ejecutando los scripts de inicializacion de estas variables, asi
	//que se guardan para volver a restaurarlas una vez termina la carga.
	var rowSel      = g_RowSel;
	var totalRows   = g_TotalRows;
	var firstRow    = g_FirstRow;
	var lastRow     = g_LastRow;
	var rowsVisible = g_RowsVisible;
	var folderIdSel = g_FolderIdSel;
	var autoDist    = g_AutoDist;

	$(document).ready(function(){
		$("#ResultsTable .ordenarResults a").click(function(){
			var url = orderTable();
			$("#cargando").css("display", "block");

			$("#resultados").load(url +  " #results",
				function(){
					$("#cargando").css("display", "none");
					g_NumAjaxStop = 0;
				});

			$("#resultados").ajaxStop(
				function(){
					if (g_NumAjaxStop == 0){
						var input = document.getElementById("orderByTable").value;
						if ((input!="") && (input!=null)){
							var inputFragmentado = input.split(',');
							for (var i = 0; i <inputFragmentado.length; i++){
								var elemento = inputFragmentado[i].split(' ');
								var campo = elemento[0].substring(elemento[0].indexOf("FLD")+3, elemento[0].Length);
								if(elemento[1] == "ASC"){
									document.getElementById("ordenCampo" + campo).src="./images/asc.gif";
								}
								else{
									document.getElementById("ordenCampo" + campo).src="./images/desc.gif";
								}

							}
						}
						g_NumAjaxStop = 1;
					}
					//Una vez que se ha cargado la pagina se restauran las variables.
					g_RowSel      = rowSel;
					g_TotalRows   = totalRows;
					g_FirstRow    = firstRow;
					g_LastRow     = lastRow;
					g_RowsVisible = rowsVisible;
					g_FolderIdSel = folderIdSel;
					g_AutoDist    = autoDist;
				}
			);

			// se pasa a la variable el valor de num de registro donde estabamos situados
			if (document.getElementById("FolderNum")){
				top.Main.Table.TableData.g_FirstRow = document.getElementById("FolderNum").value;
			}

			return false;
		});
	});
}

// funcion que modifica las imagenes de ordenacion segun este el campo oculto orderByTable
function modifImagesResponseServer()
{
	if(navigator.appName == "Microsoft Internet Explorer"){
		var input = document.getElementById("orderByTable").value;
		if ((input!="") && (input!=null)){
			var inputFragmentado = input.split(',');
			for (var i = 0; i <inputFragmentado.length; i++){
				var elemento = inputFragmentado[i].split(' ');
				for(var j = 0; j <elemento.length; j++){
					var campo = elemento[0].substring(elemento[0].indexOf("FLD")+3, elemento[0].Length);
					var imagen = document.getElementById("ordenCampo" + campo);
					if(elemento[1] == "ASC"){
						imagen.src="./images/asc.gif";
					}
					else{
						imagen.src="./images/desc.gif";
					}
				}
			}
		}
	}
}

//comprobamos el navegador si no es IE deshabilitamos la opcion de ordenar
//o bien si el resultado devuelto es igual a un registro tambien se deshabilita la opcion de ordenar
function validarNavegador(id){
	if((navigator.appName != "Microsoft Internet Explorer") || (top.Main.Table.TableData.g_TotalRows <=1)){
		var column = document.getElementById("ordenCampo" + id);
		if (column != null) column.style.display="none";
	}
}


function resaltarSeleccion(row){
	row.style.color="#e11d03";
	row.style.cursor="pointer";
}

function resetearStyleSeleccion(row){
	row.style.color= g_color3;
    row.style.cursor="";
}
