var InitValue=0;
var TotValue=0;
var PasoValue=0;

// variables para conseguir el destino de la distribucion
var IdDest = "";
var DescripDest = "";
var numDest = "";


//para saber si hemos modificado los interesados
g_VldInterSaved = false;


function onLoadWin()
{
	parent.document.getElementById("VldResMain").rows = "*, 100%";

	try {
		parent.VldTbl.document.getElementById("Close").disabled = false;
		parent.VldTbl.document.getElementById("QryButt").disabled = false;
	}
	catch(e){}

	if (!parent.bIsDtrList) {
		// Ocultamos el formulario de detras para que no se vea por detras
		top.g_FormVld.style.visibility = 'hidden';
	}
	else {
		IdDest = "";
		DescripDest = "";
	}
}


function OnWindowVldInterLoad()
{
   parent.VldInter.rows = "*, 100%";
   // Establecemos el titulo del Frame
   top.SetInnerText(parent.VldTitle.document.getElementById("V_Title"), top.GetIdsLan( "IDS_TITCAMPOINT" ));
}


function CancelarInter()
{
	var oForm = top.g_FormVld;

	// Cerramos los frames
	top.SetInnerText(parent.VldTitle.document.getElementById("V_Title"), "");
	parent.VldInter.rows = "100%, *";

	eval(top.g_VldPath + ".document.getElementById('Vld').style.display = 'none'");

	oForm.style.visibility = "visible";

	top.setFocus(top.g_FormVld.document.Interesados.document.getElementById("validInt"));

	g_VldInterSaved = false;

	// activamos el boton de guardar
	if (top.g_WndVld.bIsActiveSave) {
		top.Main.Folder.FolderBar.ActivateSave();
	}

	return;
}


function onrowover(row,Enable)
{
   row.style.backgroundColor= g_color4;
   if (Enable)
      row.style.cursor="pointer";
   else
      row.style.cursor="";
}

function onrowout(row)
{
     row.style.backgroundColor="";
     row.style.cursor="";

}

// Funcion que asigna la descripcion a los campos sustitutos
function asignSustitutos(strCadena, fldid, tblvalidated)
{
	var OFrm = top.g_FormVld;
	var oArrInterested;
	var oArrOneInter;
	var items = top.g_FormVld.elements;

	// Rellenamos todos los campos sustitutos
	for(var ii = 0; ii < items.length; ii++) {
		if (items[ii].getAttribute("FldId") == fldid){
			if (items[ii].getAttribute("Sustituto")){
				// lo borramos
				items[ii].value = "";

				if ( (tblvalidated == top.FLD_INTERESTED_FLDID) && (strCadena != "") ){
					// Sacamos los interesados
					oArrInterested = getInterested(strCadena);
					// Metemos los interesados en el textbox y saltamos una linea

					for (var jj=0; jj < oArrInterested.length; jj++){
						oArrOneInter = top.getTokens(oArrInterested[jj].value,"#","#",4);

						if (items[ii].value == ""){
							items[ii].value += oArrOneInter[1];
						}
						else {
							items[ii].value +=  unescape("%0D%0A") + oArrOneInter[1];
						}

						delete oArrOneInter;
					}
				}
				else {
					items[ii].value = strCadena;
				}
			}
			else {
				items[ii].setAttribute("valueSust", strCadena);
			}
		}
   }

   return;
}


function AsigValue( id )
{
	// si no estamos en la distribucion de cambiar destino
	if ( ( top.g_Field ) && (parent.bIsDtrList == 0) ){
		var Desc = id.getAttribute("Description");

		if (document.getElementById("ParentName") != null){
			if (document.getElementById("ParentName").value != ""){
				Desc = Desc + " (" + document.getElementById("ParentName").value + ")";
			}
		}
		else if (id.getAttribute("ParentName") != null){
			if (id.getAttribute("ParentName") != ""){
				Desc = Desc + " (" + id.getAttribute("ParentName") + ")";
			}
		}

		top.g_Field.value = id.getAttribute("Code");

		if (top.g_Field.getAttribute("tblvalidated") != top.VAL_TP_TYPEMATTER){
			asignSustitutos(Desc, top.g_Field.getAttribute("FldId"), top.g_Field.getAttribute("tblvalidated"));
		}

		top.SetInnerText(parent.VldTitle.document.getElementById("V_Title"), "");
		parent.document.getElementById("VldResMain").rows = "100%,*";

		top.g_FormVld.style.visibility = "visible";
		top.setFocus(top.g_Field);
		eval(top.g_VldPath + ".document.getElementById('Vld').style.display = 'none'");
		eval( top.g_VldPath + ".OnHelpWindow=true" );

		if (top.g_Field.getAttribute("tblvalidated") == top.VAL_TP_TYPEMATTER){
			if (top.g_WndVld.name != "Query"){
				eval( top.g_VldPath + ".OnHelpWindow=false" );
				window.FieldValue = "";
				top.g_Field.onblur();
				eval( top.g_VldPath + ".OnHelpWindow=true" );
				window.FieldValue = top.g_Field.value;
			}
		}

		//Activamos el guardar
		if (top.g_FolderView){
			top.Main.Folder.FolderBar.ActivateSave();
		}

		if (parent.VldFrmData == 1){
			var URL = top.g_URL + "/SaveRememberLastSelectedUnit.jsp?SessionId=" + top.g_SessionPId
				+ "&BookId=" + top.g_ArchiveId.toString()
				+ "&UnitCode=" + id.getAttribute("Code")
				+ "&UnitType=" + parent.VldFrmDataFldId;

			var Data = "SessionId=" + top.g_SessionPId
					 + "&BookId=" + top.g_ArchiveId.toString()
					 + "&UnitCode=" + id.getAttribute("Code")
					 + "&UnitType=" + parent.VldFrmDataFldId;

			top.XMLHTTPRequestPost(URL, RememberLastSelected, true, Data);
		}


	}
	else{
		numDest = id.getAttribute("numDest");
		IdDest = id.getAttribute("Code").toUpperCase();
		DescripDest =  id.getAttribute("Description");

		top.SetInnerText(parent.VldTitle.document.getElementById("V_Title"), "");
		parent.document.getElementById("VldResMain").rows = "100%,*";

		window.returnValue = numDest + "#" + IdDest + "#" + DescripDest + "#";
		window.close();
	}

	if ((top.Main != null)
	 && (top.Main.Folder != null)
	 && (top.Main.Folder.FolderData != null)
	 && (top.Main.Folder.FolderData.FolderFormTree != null)
	 && (top.Main.Folder.FolderData.FolderFormTree.document != null)
	 && (top.Main.Folder.FolderData.FolderFormTree.document.getElementById("divDocs") != null)
	 && (top.Main.Folder.FolderData.FolderFormTree.document.getElementById("divDocs").style != null)){
		top.Main.Folder.FolderData.FolderFormTree.document.getElementById("divDocs").style.visibility="visible";
	}

	return;
}

function RememberLastSelected(){

}


function SetValues(Inicio, Paso, Tot)
{
	InitValue=Inicio;
	TotValue=Tot;
	PasoValue=Paso;

	if (Inicio==1 || TotValue==0 ){
		document.getElementById("FirstImg").style.visibility = "hidden";
		document.getElementById("PrevImg").style.visibility = "hidden";
	}
	else {
		document.getElementById("FirstImg").style.visibility = "visible";
		document.getElementById("PrevImg").style.visibility = "visible";
	}

	if ( ((Inicio + PasoValue - 1)>=TotValue) || (TotValue==0) ) {
		document.getElementById("NextImg").style.visibility = "hidden";
		document.getElementById("LastImg").style.visibility = "hidden";
	}
	else {
		document.getElementById("NextImg").style.visibility = "visible";
		document.getElementById("LastImg").style.visibility = "visible";
	}
}


function FirtValues()
{
   parent.VldTbl.ShowRes("VldRes.jsp?tblvalidated=" + parent.VldCtlType +
      "&VldQuery=" + parent.VldQuery
      + "&VldQueryValue=" + escape(parent.VldQueryValue)
      + "&TypeId=" + parent.VldTypeId
      + "&IdCrl=" + parent.VldIdCrl + "&TblFldId=" + parent.TblFldId
      + "&SessionPId=" + parent.SessionPId
      + "&ArchivePId=" + parent.ArchivePId + "&TypeBusc=" + parent.VldTypeBusc.toString()
      + "&Enabled=" + parent.VldEnabled.toString()
      + "&IsDtrList=" + parent.bIsDtrList + "&ListPId=" + parent.VldListPId
      + "&FrmData=0"+ "&FrmDataBusc=" + parent.VldFrmDataBusc);
}

function PrevValues()
{
   var inicio = InitValue - PasoValue - 1;
   if (inicio < 0)
      inicio=0;

   parent.VldTbl.ShowRes("VldRes.jsp?tblvalidated=" + parent.VldCtlType +
      "&InitValue=" + inicio + "&VldQuery=" + parent.VldQuery
      + "&VldQueryValue=" + escape(parent.VldQueryValue)
      + "&TypeId=" + parent.VldTypeId + "&IdCrl=" + parent.VldIdCrl
      + "&TblFldId=" + parent.TblFldId + "&SessionPId=" + parent.SessionPId
      + "&ArchivePId=" + parent.ArchivePId + "&TypeBusc=" + parent.VldTypeBusc.toString()
      + "&Enabled=" + parent.VldEnabled.toString()
      + "&IsDtrList=" + parent.bIsDtrList
      + "&ListPId=" + parent.VldListPId
      + "&FrmData=0"+ "&FrmDataBusc=" + parent.VldFrmDataBusc);
}

function NextValues()
{
   var inicio = InitValue + PasoValue - 1;

   if (inicio > TotValue)
      inicio = (TotValue % PasoValue) - 1;

   parent.VldTbl.ShowRes("VldRes.jsp?tblvalidated=" + parent.VldCtlType
   + "&InitValue=" + inicio + "&VldQuery=" + parent.VldQuery + "&TypeId=" + parent.VldTypeId
   + "&VldQueryValue=" + escape(parent.VldQueryValue)
   + "&IdCrl=" + parent.VldIdCrl + "&TblFldId=" + parent.TblFldId
   + "&SessionPId=" + parent.SessionPId + "&ArchivePId=" + parent.ArchivePId
   + "&TypeBusc=" + parent.VldTypeBusc.toString()
   + "&Enabled=" + parent.VldEnabled.toString()
   + "&IsDtrList=" + parent.bIsDtrList + "&ListPId=" + parent.VldListPId
   + "&FrmData=0"+ "&FrmDataBusc=" + parent.VldFrmDataBusc);
}

function LastValues()
{
	var inicio;

	if((TotValue % PasoValue) == 0){
		inicio = TotValue - PasoValue;
	}else{
		inicio = TotValue - (TotValue % PasoValue);
	}

   parent.VldTbl.ShowRes("VldRes.jsp?tblvalidated=" + parent.VldCtlType
      + "&InitValue=" + inicio + "&VldQuery=" + parent.VldQuery + "&TypeId=" + parent.VldTypeId
      + "&VldQueryValue=" + escape(parent.VldQueryValue)
      + "&IdCrl=" + parent.VldIdCrl + "&TblFldId=" + parent.TblFldId
      + "&SessionPId=" + parent.SessionPId + "&ArchivePId=" + parent.ArchivePId
      + "&TypeBusc=" + parent.VldTypeBusc.toString()
      + "&Enabled=" + parent.VldEnabled.toString()
      + "&IsDtrList=" + parent.bIsDtrList + "&ListPId=" + parent.VldListPId
      + "&FrmData=0"+ "&FrmDataBusc=" + parent.VldFrmDataBusc);
}

function DownValues(TypeId,IdCrl)
{
   parent.VldQuery = "";
   parent.VldListPId = 0;

   parent.VldTypeBuscOld  = parent.VldTypeBusc;
   parent.VldTypeBusc     = 1;
   parent.VldTypeIdOld  = parent.VldTypeId;
   parent.VldTypeId     = TypeId;
   parent.VldIdCrlOld   = parent.VldIdCrl;
   parent.VldIdCrl      = IdCrl;
   FirtValues();
}


function UpValues(TypeId,IdCrl)
{
   parent.VldQuery = "";
   parent.VldListPId = 0;

   if (TypeId==-1)
   {
      if (parent.VldTypeId!=0){
	parent.VldTypeBusc  = parent.VldTypeBuscOld;
	parent.VldTypeId    = parent.VldTypeIdOld;
	parent.VldIdCrl     = parent.VldIdCrlOld;
      }
      else
      {
	parent.VldTypeBusc  = 2;
	parent.VldTypeId    = parent.VldTypeIdOld;
	parent.VldIdCrl     = parent.VldIdCrlOld;
      }
      FirtValues();
   }
   else
   {
      parent.VldTypeBusc  = 2;
      parent.VldTypeId    = TypeId;
      parent.VldIdCrl     = IdCrl;
      FirtValues();
   }
}


function SetSeekingField(NumFields, Label, Name)
{
	var cmb = parent.VldTbl.document.getElementById("cmbFields");

	if (cmb.getAttribute("filled") == "0"){
		var numOpt = cmb.options.length;

		cmb.options[numOpt] = new Option(Label, numOpt.toString());
		cmb.options[numOpt].colname = Name;

		if (numOpt == NumFields){
			cmb.setAttribute("filled", "1");
			cmb.selectedIndex = numOpt;
		}
	}
}