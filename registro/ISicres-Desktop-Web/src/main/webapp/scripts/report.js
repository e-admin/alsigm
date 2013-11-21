var lastRepSelected = "";
var lastCode = "";


function SelectTpInf(check)
{
	if (check.checked && (lastRepSelected != check.value))	{
		parent.ReportBtn.document.getElementById("btnOK").disabled = true;

		parent.parent.ReportData.document.location.href = GetReportList(check.value);

		lastRepSelected = check.value;

		if (top.GetDlgParam(5) == 2){
			lastCode = "";
			parent.parent.parent.RelFilter.document.getElementById("oCode").value = "";
			parent.parent.parent.RelFilter.document.getElementById("oDesc").value = "";

			if (check.value == "RMD"){
				top.SetInnerText(parent.parent.parent.RelFilter.document.getElementById("tdHeader2"), top.GetIdsLan("IDS_TITSETUNITDEST"));
			}
			else {
				top.SetInnerText(parent.parent.parent.RelFilter.document.getElementById("tdHeader2"), top.GetIdsLan("IDS_TITSETUNITORIG"));
			}
		}
	}
}


function InitData()
{
	var docDest = window.ReportOpc.ReportSel.document;
	var items = docDest.getElementsByTagName("*");

	for (var i = 0; i < items.length; i++){
		if (items[i].name == "opcimp"){
			items[i].checked = true;
			lastRepSelected = items[i].value;
			break;
		}
	}

	if (top.GetDlgParam(5) == 0){
		top.document.body.rows = "47%,*";
	}

	window.ReportData.document.location.href = GetReportList(lastRepSelected);
}


function OnWindowReportLoad()
{
	var items = document.getElementsByTagName("*");

	parent.ReportOpc.ReportBtn.document.getElementById("btnOK").disabled = true;

	for (var i = 0; i < items.length; i++){
		if (items[i].name == "reportRadio")	{
			items[i].checked = true;
			parent.ReportOpc.ReportBtn.document.getElementById("btnOK").disabled = false;
			break;
		}
	}

	return;
}

function GetReportList(val)
{
	var sUrl = top.GetDlgParam(0) + "/report.jsp?SessionPId=" + top.GetDlgParam(1) +
		"&ArchivePId=" + top.GetDlgParam(2) + "&Opcion=" + val;

	return(sUrl);
}


function Print()
{
	var docDest = parent.ReportSel.document;
	var items = docDest.getElementsByTagName("*");
	var itemsInf = parent.parent.ReportData.document.getElementsByTagName("*");
	var opc, repId, sRet;
	var args = new Array();

	if (top.GetDlgParam(5) == 2){
		var oFieldHour1 = parent.parent.parent.RelFilter.document.getElementById("hora1");
		var oFieldHour2 = parent.parent.parent.RelFilter.document.getElementById("hora2");

		if (parent.parent.parent.RelFilter.document.getElementById("FilterHour").checked == true){
			if (!CompareDate(oFieldHour1, oFieldHour2)){return;}
		}
	}

	if ((top.GetDlgParam(5) == 0) &&
		(parent.ReportSel.document.getElementById("chkSelected").checked == true)){
		if (top.GetDlgParam(6) == ""){
			top.alert(top.GetIdsLan("IDS_MSG_SELECT_ONE_FDR"));
			return;
		}
	}

	document.getElementById("btnOK").disabled = true;
	document.getElementById("btnCancel").disabled = true;

	for (var i = 0; i < items.length; i++){
		if (items[i].name == "opcimp"){
			if (items[i].checked == true){
				opc = items[i].value;
				break;
			}
		}
	}

	if ((opc == "CM") && (top.GetDlgParam(5) == 1)) {
		opc = "C1";
	}

	if ((opc == "RMD") && ((top.GetDlgParam(5) == 0) || (top.GetDlgParam(5) == 3))){
		opc = "RMDS";
	}

	if ((opc == "RMO") && ((top.GetDlgParam(5) == 0) || (top.GetDlgParam(5) == 3))){
		opc = "RMOS";
	}

	for (var j = 0; j < itemsInf.length; j++){
		if (itemsInf[j].name == "reportRadio"){
			if (itemsInf[j].checked == true){
				repId = itemsInf[j].value;
				break;
			}
		}
	}

	args[0] = top.GetDlgParam(0);
	args[1] = "SessionPId=" + top.GetDlgParam(1) +
				"&ArchivePId=" + top.GetDlgParam(2) +
				"&FolderId=" + top.GetDlgParam(3) +
				"&FdrQryPId=" + top.GetDlgParam(4) +
				"&ReportId=" + repId.toString() +
				"&Opcion=" + opc;
	args[2] = "reportdoc.jsp";
	args[3] = top.GetIdsLan( "IDS_PRINTING" );
	args[4] = top.Idioma;

	if (top.GetDlgParam(5) == 2){
		if (parent.parent.parent.RelFilter.document.getElementById("FilterHour").checked == true){
			var HoraDesde = parent.parent.parent.RelFilter.document.getElementById("hora1").value;
			var HoraHasta = parent.parent.parent.RelFilter.document.getElementById("hora2").value;

			args[1] = args[1] + "&Fecha=" + parent.parent.parent.RelFilter.document.getElementById("date").value +
				"&Hora1=" + HoraDesde +
				"&Hora2=" + HoraHasta +
				"&Unit=" + parent.parent.parent.RelFilter.document.getElementById("oCode").value;
		}
		else{
			args[1] = args[1] + "&Fecha=" + parent.parent.parent.RelFilter.document.getElementById("date").value +
				"&Unit=" + parent.parent.parent.RelFilter.document.getElementById("oCode").value;
		}
	}
	else if ((top.GetDlgParam(5) == 0) &&
		(parent.ReportSel.document.getElementById("chkSelected").checked == true)){
		args[1] = args[1] + "&List=" + top.GetDlgParam(6);
	}
	else if (top.GetDlgParam(5) == 3){
		args[1] = args[1] + "&PrintOpc=" + top.GetDlgParam(8);
	}

	sRet = top.ShowModalDialog(top.GetDlgParam(0) + "/waiting.htm", args, 50, 350, "");

	if ((sRet != undefined) && (sRet != "")){
		window.open(top.GetDlgParam(0) + sRet, "", "resizable=yes, location=no", false);
	}

	document.getElementById("btnOK").disabled = false;
	document.getElementById("btnCancel").disabled = false;
}


function ValidateDate(oField)
{
	var fecha = top.miTrim(oField.value);
	var bIsCorrect = true;

	if (fecha == null || fecha == "")
	{
		bIsCorrect = false;
	}
	else
	{
		var sArrFecha = fecha.split("-");

		if (sArrFecha.length != 3)
		{
			sArrFecha = fecha.split("/");

			if (sArrFecha.length != 3)
			{
				bIsCorrect = false;
			}
		}

		if (bIsCorrect)
		{
			var year = parseInt(sArrFecha[2], 10);
			var month = parseInt(sArrFecha[1], 10);
			var date = parseInt(sArrFecha[0], 10);

			if ((year < 1970) || (year > 2100) || isNaN(year))
			{
				bIsCorrect = false;
			}

			if ((month < 1) || (month > 12) || isNaN(month))
			{
				bIsCorrect = false;
			}

			if ((date < 1) || (date > 31) || isNaN(date))
			{
				bIsCorrect = false;
			}
			else
			{
				if ((month == 4) || (month == 6) || (month == 9) || (month == 11))
				{
					if (date > 30)
					{
						bIsCorrect = false;
					}
				}

				if (month == 2)
				{
					if ((year == 2000) || (year%4 != 0))
					{
						if (date > 28)
						{
							bIsCorrect = false;
						}
					}
					else
					{
						if (date > 29)
						{
							bIsCorrect = false;
						}
					}
				}
			}
		}
	}

	if (bIsCorrect)
	{
		var newDate = new Date(year, month - 1, date);

		oField.value = top.FormatDate(newDate);
	}

	if (!bIsCorrect)
	{
		alert(top.GetIdsLan("IDS_DATEFORMAT_NOVALID"));
		oField.value = top.FormatDate(new Date());
		oField.focus();
		oField.select();
	}
}


function ValidateHour(oField)
{
	var hora = miTrim(oField.value);
	var bIsCorrect = true;

	if (hora == "")
	{
		bIsCorrect = false;
	}
	else
	{
		var sArrHora = hora.split(":");

		if (sArrHora.length != 2)
		{
			bIsCorrect = false;
		}

		if (bIsCorrect)
		{
			var hh = parseInt(sArrHora[0], 10);
			var mm = parseInt(sArrHora[1], 10);

			if ((hh < 0) || (hh > 24) || isNaN(hh))
			{
				bIsCorrect = false;
			}

			if ((mm < 0) || (mm > 59) || isNaN(mm))
			{
				bIsCorrect = false;
			}
		}
	}

	if (bIsCorrect)
	{
		var newDate = new Date(2004, 0, 26, hh, mm);

		oField.value = FormatHour(newDate);
	}

	if (!bIsCorrect)
	{
		alert(top.GetIdsLan("IDS_HOURFORMAT_NOVALID"));
		oField.value = FormatHour(new Date());
		oField.focus();
		oField.select();
	}

	return (bIsCorrect);
}


function ShowUnitList(caseSensitive)
{
	var FldId, sRet;
	var tokens = new Array;
	var args = new Array;
	var tpInf = parent.RelOpc.ReportOpc.ReportSel.document.getElementsByName("opcimp");

	for (var i = 0; i < tpInf.length; i++){
		if (tpInf[i].checked == true){
			FldId = (tpInf[i].value == "RMD")?8:7;
			break;
		}
	}

	args[0] = top.GetDlgParam(0);
	args[1] = top.GetDlgParam(1);
	args[2] = top.GetDlgParam(2);
	args[3] = 1000;
	args[4] = 1;
	args[5] = FldId;
	args[6] = top.GetDlgParam(3);
	args[7] = 1;

	sRet = ValidateList(args, caseSensitive);

	if (sRet && (sRet != ""))	{
		tokens = top.getTokens(sRet, "#", "#", 3);

		idProc = tokens[0];
		document.getElementById("oCode").value = tokens[1];
		document.getElementById("oDesc").value = tokens[2];
	}
}


function ValidateCode(oField)
{
	oField.value = top.miTrim(oField.value);
	oField.value = oField.value.toUpperCase();

	if (oField.value == "")	{
		document.getElementById("oDesc").value = "";

		return
	}

	if (oField.value != lastCode){
		var FldId, URL, resp;
		var tpInf = parent.RelOpc.ReportOpc.ReportSel.document.getElementsByName("opcimp");

		document.body.style.cursor = "wait";

		for (var i = 0; i < tpInf.length; i++){
			if (tpInf[i].checked == true){
				FldId = (tpInf[i].value == "RMD")?8:7;
				break;
			}
		}

		parent.RelOpc.ReportOpc.ReportBtn.document.getElementById("btnOK").disabled = true;
		parent.RelOpc.ReportOpc.ReportBtn.document.getElementById("btnCancel").disabled = true;

		URL = top.GetDlgParam(0) + "/validatecode.jsp?SessionPId=" + top.GetDlgParam(1)
			+ "&ArchivePId=" + top.GetDlgParam(2)
			+ "&FldId=" + FldId
			+ "&Code=" + oField.value;

		top.XMLHTTPRequestGet(URL, GetResponse, false);
	}
}


function GetResponse()
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
		document.getElementById("oCode").focus();
		document.getElementById("oCode").select();
	}
	else{
		if (XMLDoc.documentElement.getElementsByTagName("Error").length != 0) {
			top.g_ErrorOnValidate = true;

			document.getElementById("oCode").value = "";
			document.getElementById("oDesc").value = "";

			alert(XMLDoc.documentElement.getElementsByTagName("Error")[0].firstChild.data);

			document.getElementById("oCode").focus();
			document.getElementById("oCode").select();
			lastCode = "";
		}
		else{
			document.getElementById("oDesc").value = XMLDoc.documentElement.getElementsByTagName("Description")[0].firstChild.data;
			lastCode = document.getElementById("oCode").value;
		}
	}

	parent.RelOpc.ReportOpc.ReportBtn.document.getElementById("btnOK").disabled = false;
	parent.RelOpc.ReportOpc.ReportBtn.document.getElementById("btnCancel").disabled = false;
}


function SetFocus(oField)
{
	lastCode = oField.value;
}


function CompareDate(oFldHour1, oFldHour2)
{
	var sHour1 = oFldHour1.value.split(":");
	var sHour2 = oFldHour2.value.split(":");
	var bIsCorrect = true;

	if (parseInt(sHour1[0]) > parseInt(sHour2[0]))
	{
		bIsCorrect = false;
	}

	if ((parseInt(sHour1[0]) == parseInt(sHour2[0])) &&
		(parseInt(sHour1[1]) > parseInt(sHour2[1])))
	{
		bIsCorrect = false;
	}

	if (!bIsCorrect)
	{
		alert(top.GetIdsLan("IDS_HOUR_NOTGREATER"));
		oFldHour1.focus();
		oFldHour1.select();
	}

	return bIsCorrect;
}

function ToggleCloseRegBtn(check)
{
	// parent.CloseRegBtn.document.getElementById("btnCloseReg").disabled = !check.checked;

	parent.CloseRegBtn.document.getElementById("btnCloseReg").disabled = false;

	if (check.value == "chkCloseRegistersRange") {
		parent.parent.ReportOpc.ReportBtn.document.getElementById("btnOK").disabled = true;
		document.getElementById("fechaCierre1").disabled = false;
		document.getElementById("fechaCierre2").disabled = false;
		document.getElementById("imgFechaCierre1").style.display = "inline";
		document.getElementById("imgFechaCierre2").style.display = "inline";
	} else {
		parent.parent.ReportOpc.ReportBtn.document.getElementById("btnOK").disabled = false;
		document.getElementById("fechaCierre1").disabled = true;
		document.getElementById("fechaCierre2").disabled = true;
		document.getElementById("imgFechaCierre1").style.display = "none";
		document.getElementById("imgFechaCierre2").style.display = "none";
	}
}

function CloseRegistersByDay () {

	// Necesario para determinar el tipo de relaciones
	var docDest = parent.parent.ReportOpc.ReportSel.document;
	var items = docDest.getElementsByTagName("*");

	var itemsInf = parent.parent.ReportData.document.getElementsByTagName("*");
	var opc, repId, sRet;
	var args = new Array();

	if (top.GetDlgParam(5) == 2){
		var oFieldHour1 = parent.parent.parent.RelFilter.document.getElementById("hora1");
		var oFieldHour2 = parent.parent.parent.RelFilter.document.getElementById("hora2");

		if (parent.parent.parent.RelFilter.document.getElementById("FilterHour").checked == true){
			if (!CompareDate(oFieldHour1, oFieldHour2)){return;}
		}
	}

	// Deshabilitar el boton Cerrar para evitar que se pulse de nuevo mientras se ejecuta el proceso
	document.getElementById("btnCloseReg").disabled = true;

	for (var i = 0; i < items.length; i++){
		if (items[i].name == "opcimp"){
			if (items[i].checked == true){
				opc = items[i].value;
				break;
			}
		}
	}

	if ((opc == "RMD") && ((top.GetDlgParam(5) == 0) || (top.GetDlgParam(5) == 3))){
		opc = "RMDS";
	}

	if ((opc == "RMO") && ((top.GetDlgParam(5) == 0) || (top.GetDlgParam(5) == 3))){
		opc = "RMOS";
	}

	// Construir el array de argumentos para el servlet de cierre de registros
	args[0] = top.GetDlgParam(0);
	args[1] = "SessionPId=" + top.GetDlgParam(1) +
				"&ArchivePId=" + top.GetDlgParam(2) +
				"&FolderId=" + top.GetDlgParam(3) +
				"&FdrQryPId=" + top.GetDlgParam(4) +
				"&Opcion=" + opc;
	args[2] = "closereg.jsp";
	args[3] = top.GetIdsLan( "IDS_CLOSINGREGS" );
	args[4] = top.Idioma;

	if (top.GetDlgParam(5) == 2){
		if (parent.parent.parent.RelFilter.document.getElementById("FilterHour").checked == true){
			var HoraDesde = parent.parent.parent.RelFilter.document.getElementById("hora1").value;
			var HoraHasta = parent.parent.parent.RelFilter.document.getElementById("hora2").value;

			args[1] = args[1] + "&Fecha=" + parent.parent.parent.RelFilter.document.getElementById("date").value +
				"&Hora1=" + HoraDesde +
				"&Hora2=" + HoraHasta +
				"&Unit=" + parent.parent.parent.RelFilter.document.getElementById("oCode").value;
		}
		else{
			args[1] = args[1] + "&Fecha=" + parent.parent.parent.RelFilter.document.getElementById("date").value +
				"&Unit=" + parent.parent.parent.RelFilter.document.getElementById("oCode").value;
		}
	}

	// Lanzar un popup de espera que llama a la url indicada en la lista de argumentos
	top.ShowModalDialog(top.GetDlgParam(0) + "/waiting.htm", args, 50, 350, "");

	document.getElementById("btnCloseReg").disabled = false;
}

function CloseRegistersByDateRange () {

	var args = new Array();

	// Construir el array de argumentos para el servlet de cierre de registros
	args[0] = top.GetDlgParam(0);
	args[1] = "SessionPId=" + top.GetDlgParam(1) +
				"&ArchivePId=" + top.GetDlgParam(2) +
				"&FolderId=" + top.GetDlgParam(3) +
				"&FdrQryPId=" + top.GetDlgParam(4);

	args[2] = "closereg.jsp";
	args[3] = top.GetIdsLan( "IDS_CLOSINGREGS" );
	args[4] = top.Idioma;

	var fechaDesde = parent.CloseRegCheck.document.getElementById("fechaCierre1").value;
	var fechaHasta = parent.CloseRegCheck.document.getElementById("fechaCierre2").value;
	var arrayOptions = parent.parent.parent.RelOpc.ReportOpc.ReportSel.document.getElementsByName("opcimp");
	var option = null;

    for(var i=0;i<arrayOptions.length;i++)
    {
        if (arrayOptions[i].checked) {
            option = arrayOptions[i].value;
            break;
	}
    }

	args[1] = args[1] + "&FechaDesde=" + fechaDesde
					  +	"&FechaHasta=" + fechaHasta
					  + "&Unit=" + parent.parent.parent.RelFilter.document.getElementById("oCode").value
					  + "&Opcion=" + option;

	// Lanzar un popup de espera que llama a la url indicada en la lista de argumentos
	top.ShowModalDialog(top.GetDlgParam(0) + "/waiting.htm", args, 50, 350, "");

	document.getElementById("btnCloseReg").disabled = false;

}


function DoCloseRegisters() {

	var closingTypes = parent.CloseRegCheck.document.getElementsByName("chkCloseRegisters");
	var closingType = null;

	if (closingTypes != null) {
		for (var i=0; i < closingTypes.length; i++) {
			if (closingTypes[i].checked == true) {
				closingType = closingTypes[i].value;
			}
		}
	}

	if (closingType != null) {
		if (closingType == "chkCloseRegistersRange") {
			CloseRegistersByDateRange();
		} else {
			if (closingType == "chkCloseRegistersDay") {
				CloseRegistersByDay();
			}
		}
	}

	return;
}