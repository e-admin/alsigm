var g_CaseSensitive = "";
var g_regDist = "";

function navigate(iType)
{
	window.open(top.g_URL + "/tbltext2.jsp?SessionPId=" + top.g_SessionPId + "&Row=" +  iType.toString() + "&FdrQryPId=" + top.g_FdrQryPId.toString(), "TableData","location=no",true);
}

function navigateOrdenado(iType)
{
	var orderByTable = document.getElementById("orderByTable").value;
	window.open(top.g_URL + "/tbltext2.jsp?SessionPId=" + top.g_SessionPId + "&Row=" +  iType.toString() + "&FdrQryPId=" + top.g_FdrQryPId.toString() + "&orderByTable=" + orderByTable, "TableData","location=no",true);
}

function GoTo()
{
   var Page = document.getElementById("FolderNum").value;

   if ((!isNaN(Page)) && (Page != "") && (parseInt(Page) <= top.Main.Table.TableData.g_TotalRows) && (parseInt(Page) >= 1))
      window.open(top.g_URL + "/tbltext2.jsp?SessionPId=" + top.g_SessionPId + "&Row=" + document.getElementById("FolderNum").value + "&FdrQryPId=" + top.g_FdrQryPId.toString(), "TableData","location=no",true);
	else
	{
	   alert ( top.GetIdsLan( "IDS_ERRORNUMFLD" ) );
	   document.getElementById("FolderNum").select();
	   document.getElementById("FolderNum").focus();
	}
}

function Print()
{
	var sRet;
	var args = new Array;
	var URL = top.g_URL + "/report.htm";

	args[0] = top.g_URL;
	args[1] = top.g_SessionPId.toString();
	args[2] = top.g_ArchivePId.toString();
	args[3] = top.g_FolderId.toString();
	args[4] = top.g_FdrQryPId.toString();
	args[5] = 0;
	args[6] = "";
	args[7] = top.Idioma;

	for (var i = 0;  i < top.g_ArrFdrSelected.length; i++){
		args[6] = args[6] + top.g_ArrFdrSelected[i].toString() + "_";
	}

	if (top.g_ArrFdrSelected.length > 0){
		args[6] = top.g_ArrFdrSelected.length.toString() + "_" + args[6];
	}

	sRet = top.ShowModalDialog(URL, args, 400, 450, "");
}


function Copy(obj)
{
	var rowSel = parent.TableData.g_RowSel;

	if (obj.className == "SubOptionsDisabled"){
		return;
	}

	if (rowSel != 0){
		var tabData = parent.TableData.document.getElementById("ResultsTable");
		var fdrSel = tabData.rows[rowSel].cells[1].firstChild.value;

		top.g_TreeFunc = false;

	    top.OpenNewWindow(top.g_URL + "/default.jsp?AppId=" + top.g_AppId.toString()
			+ "&SessionPId=" + top.g_SessionPId.toString()
            + "&FolderView=1&ArchiveId=" + top.g_ArchiveId.toString()
            + "&ArchiveName=" + top.g_ArchiveName.toString()
            + "&ArchivePId=" + top.g_ArchivePId.toString()
            + "&FolderPId=" + top.g_FolderPId.toString()
            + "&Idioma=" + top.Idioma.toString()
            + "&numIdioma=" + top.numIdioma.toString()
            + "&OpenType=1"
            + "&CopyFdr=" + fdrSel.toString(), "", "10000", "10000", "auto","yes");

		top. g_TreeFunc = true;
	}
	else {
		top.alert(top.GetIdsLan("IDS_MSG_SELECT_ONE_FDR"));
	}
}


function Edit(obj)
{
	var sRet;
	var args = new Array;
	var URL = top.g_URL + "/frmedit.htm";
	var tabData;
	var numCheck = 0;

	if (obj.className == "SubOptionsDisabled"){
		return;
	}

	// Id de columna estado
	var IdColState = GetIdColumn(top.FLD_STATE_FLDID);

	args[0] = top.g_URL;
	args[1] = top.g_SessionPId.toString();
	args[2] = top.g_ArchivePId.toString();
	args[3] = "";
	args[4] = top.Idioma;
	args[5] = top.g_CaseSensitive

	tabData = parent.TableData.document.getElementById("ResultsTable");

	for (i = 0; i < tabData.rows.length; i++){
		if (tabData.rows[i].cells[1].firstChild.checked == true){

			if (top.GetInnerText(tabData.rows[i].cells[IdColState]).toUpperCase() == (top.GetIdsLan("IDS_ESTADO_CERRADO"))) {

				top.alert(top.GetIdsLan("IDS_EDIT_REG_NO_PERM_X_CERRADO"));
				return;
			}
			else {
				numCheck++;
				args[3] = args[3] + tabData.rows[i].cells[1].firstChild.value + "_";
			}
		}
	}

	if (numCheck > 0){
		args[3] = numCheck.toString() + "_" + args[3];

		sRet = top.ShowModalDialog(URL, args, 350, 400, "");

		if (sRet != null){
			var tokens = sRet.split("#");

			if (tokens.length > 3){
				var IdColUnit = GetIdColumn(tokens[0]);
				// var IdColState = GetIdColumn(top.FLD_STATE_FLDID);
				var arrFdrId = tokens[3].split("_");

				if (IdColUnit != -1){
					for (i = 1; i < tabData.rows.length; i++){
						if (tabData.rows[i].cells[1].firstChild.checked == true){
							top.SetInnerText(tabData.rows[i].cells[IdColUnit], tokens[1]);

							if (IdColState != -1){
								for (var j = 0; j < arrFdrId.length; j++){
									if (tabData.rows[i].id == arrFdrId[j]){
										top.SetInnerText(tabData.rows[i].cells[IdColState], tokens[2]);
										break;
									}
								}
							}
						}
					}
				}
			}
		}
	}
	else{
		top.alert(top.GetIdsLan("IDS_MSG_SELECT_ONE_FDR"));
	}
}

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
	args[3] = "";
	args[4] = top.Idioma;
	args[5] = g_CaseSensitive;

	tabData = parent.TableData.document.getElementById("ResultsTable");

	for (i = 0; i < tabData.rows.length; i++){
		if (tabData.rows[i].cells[1].firstChild.checked == true){
			numCheck++;

			args[3] = args[3] + tabData.rows[i].cells[1].firstChild.value + "_";
		}
	}

	if (numCheck > 0){
		args[3] = numCheck.toString() + "_" + args[3];

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
	else{
		top.alert(top.GetIdsLan("IDS_MSG_SELECT_ONE_FDR"));
	}

}

function SendToIR(obj){

	var sRet;
	var args = new Array;
	var tabData;
	var numCheck = 0;
	var regList ="";

	if (obj.className == "SubOptionsDisabled"){
		return;
	}

	args[0] = top.g_URL;
	args[1] = "SessionPId=" + top.g_SessionPId.toString();
	args[2] = "EnvioManualBandejaIntercambioRegistral.do";
	args[3] = "Enviar a IR"
	args[4] = top.Idioma;

	tabData = parent.TableData.document.getElementById("ResultsTable");

	for (i = 0; i < tabData.rows.length; i++){
		if (tabData.rows[i].cells[1].firstChild.checked == true){

				numCheck++;
				regList = regList + tabData.rows[i].cells[1].firstChild.value + "_";

		}
	}

	if (numCheck > 0){
		regList = numCheck.toString() + "_" + regList;
		args[1] = args[1] + "&List=" + regList;
		sRet = top.ShowModalDialog(top.g_URL + "/waiting.htm", args, 50, 350, "");
	}

	else{
		top.alert(top.GetIdsLan("IDS_MSG_SELECT_ONE_FDR"));
	}

}



function GetIdColumn(FldId)
{
	var tabData = parent.TableData.document.getElementById("ResultsTable");

	for (i = 0; i < tabData.rows[0].cells.length; i++){
		if (tabData.rows[0].cells[i].FldId){
			if (tabData.rows[0].cells[i].FldId == FldId){
				return (i);
			}
		}
	}

	return -1;
}


function OnKey(aEvent)
{
	var code = top.GetKeyCode(aEvent);

	if (code == 13){
		GoTo();
	}
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
	{
		obj.className="SubOptions";
	}
}

function SetCaseSensitive(caseSensitive)
{
	g_CaseSensitive = caseSensitive;
}

//limita los textareas a un numero de caracteres
function LimitMaxLength(oEvent, iMax)
{
	var element = top.GetSrcElement(oEvent);

	if (element.value.length >= iMax) {
		element.value = element.value.substr(0,iMax);
		top.PreventDefault(oEvent);
	}
}
function SetRegDist(regDist)
{
	g_regDist = regDist;
}

function OpenReg (obj) {

	var sRet;
	var args = new Array;
	var tabData;
	var numCheck = 0;

	if (obj.className == "SubOptionsDisabled"){
		return;
	}

	// Id de columna estado
	var IdColState = GetIdColumn(top.FLD_STATE_FLDID);
	var regList = "";

	args[0] = top.g_URL;
	args[1] = "SessionPId=" + top.g_SessionPId.toString();
			//	"&ArchivePId=" + top.g_ArchivePId.toString() +
			//	"&FolderId=" + top.g_FolderId.toString() +
			//	"&FdrQryPId=" + top.g_FdrQryPId.toString();
	args[2] = "openreg.jsp";
	args[3] = top.GetIdsLan( "IDS_OPENINGREG" );
	args[4] = top.Idioma;

	tabData = parent.TableData.document.getElementById("ResultsTable");

	for (i = 0; i < tabData.rows.length; i++){
		if (tabData.rows[i].cells[1].firstChild.checked == true){

			if (top.GetInnerText(tabData.rows[i].cells[IdColState]).toUpperCase() != (top.GetIdsLan("IDS_ESTADO_CERRADO"))) {

				top.alert(top.GetIdsLan("IDS_EDIT_REG_NO_PERM_X_NO_CERRADO"));
				return;
			}
			else {
				numCheck++;
				regList = regList + tabData.rows[i].cells[1].firstChild.value + "_";
			}
		}
	}

	if (numCheck > 0){
		regList = numCheck.toString() + "_" + regList;
		args[1] = args[1] + "&List=" + regList;

		sRet = top.ShowModalDialog(top.g_URL + "/waiting.htm", args, 50, 350, "");
	}
	else{
		top.alert(top.GetIdsLan("IDS_MSG_SELECT_ONE_FDR"));
	}
}

//Funcion que llama el boton "Exp.Excel"
function exportExcel() {
	fileDownloadToken = new Date().getTime(); //Obtenemos un token unico que servira de indicador para la descarga del excel.
	$("#frmOperations").attr('src', top.g_URL + "/exportexcel.jsp?fileDownloadToken=" + fileDownloadToken + "&typeSearch=" + top.g_TypeSearch);
	$("#frmOperations").load(finishDownload); //El evento se ejecutara solo si hay un error y se devuelve un alert, en tal caso ocultar "cargando".
	blockUIForDownload(); //Bloqueamos la pantalla para indicar que se esta generando el excel.
}

var fileDownloadCheckTimer; //Tendra la funcion que se ejecutara cada 500ms para chequear si ha terminado la descarga del Excel.
var fileDownloadToken;      //Token inicializado al iniciar la carga y que se chequeara para ver si ha finalizado.

//Bloquear la lista de carpetas para indicar que se esta descargando el Excel.
function blockUIForDownload() {
	blockUI(top.GetIdsLan("IDS_EXCEL_EXPORTING")); //Bloquear pantalla
	//Se ejecutara una funcion cada 500ms que comprobara la cookie "fileDownloadToken", solo se llamara a "finisDownload()" cuando
	//la cookie tenga el valor del token "fileDownloadToken" (Se llama igual que la cookie), y esto solo ocurrira cuando el servlet
	//termine la generacion del excel.
	fileDownloadCheckTimer = window.setInterval(
		function () {
			var cookieValue = $.cookie('fileDownloadToken');
			if (cookieValue == fileDownloadToken)
				finishDownload();
		}, 500);
}

//Funcion que se ejecuta al finalizar la descarga del Excel o si hay un error.
function finishDownload() {
	 window.clearInterval(fileDownloadCheckTimer);
	 $.cookie('fileDownloadToken', null); //Limpiar el valor de la cookie
	 unblockUI(300); //Desbloquear pantalla con un fadeout de 300ms.
}

function unblockUI(fadeOutValue) {
	if (fadeOutValue == undefined) fadeOutValue = 0;
	$.unblockUI({ fadeOut: fadeOutValue });
}

function blockUI(message) {
	$.blockUI.defaults.css.border = 'none';
	$.blockUI.defaults.css.backgroundColor = "#ffffff";
	$.blockUI.defaults.overlayCSS.backgroundColor = "#ffffff";
	if (message == undefined) message = top.GetIdsLan( "IDS_LOAD");
	$.blockUI({ message: '<img src="./images/loading.gif" /><br>' + message });
}
