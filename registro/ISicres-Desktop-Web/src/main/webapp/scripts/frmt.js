var lastSelected = -1;
var TreeElemSel;
var TreeType;
var TreeElemAux;
var TreeTypeAux;
var IsMenu        = false;
var IsClickMenu   = false;
var IsClickElem   = false;


function SetSelected(item)
{
	if (item != null) {
		var items = document.getElementsByTagName("*");

		if (lastSelected != -1) {
			items[lastSelected].style.backgroundColor = "#f4ffff";
            items[lastSelected].style.fontWeight = "normal";
        }

        item.style.backgroundColor = g_color4;
        item.style.fontWeight = "bold";

        for (var i = 0; i < items.length; i++) {
			if (items[i] == item){
				lastSelected = i;
				break;
			}
        }
	}
}

function OpenPageData(aEvent)
{
	//comprobamos si se debe ocultar el frame de visualizacion de documentos
	ocultarFrameFolderPageFile();

	var srcElement = top.GetSrcElement(aEvent);
	var parentId = srcElement.parentNode.id;

	if (top.g_ActivateTree)  {
		top.g_PrevPage = top.g_Page;
	    top.g_Page = parseInt(parentId);

	// Deshabilitamos las toolbar
        if (!top.g_FolderView) {
		top.g_OpcAval=false;
                top.Main.Folder.ToolBarFrm.deshabilitar();
	}
	else  {
			top.Main.Folder.ToolBarFrm.ToolBarDisabled();
	}

	var URL = top.g_URL + "/frmdata.jsp?SessionPId=" + top.g_SessionPId
		+ "&Row=" + Row
		+ "&Page=" + parentId
		+ "&FdrQryPId=" + top.g_FdrQryPId.toString()
		+ "&FolderPId=" + top.g_FolderPId.toString()
		+ "&FolderId=" + top.g_FolderId.toString()
		+ "&ArchivePId=" + top.g_ArchivePId.toString()
        + "&ArchiveId=" + top.g_ArchiveId.toString()
		+ "&CopyFdr=" + top.g_CopyFdr.toString();

		top.XMLHTTPRequestGet(URL, top.ResponseFrmData, true);

	top.g_ActivateTree = false;
	//top.g_PrevPage = -1;

	if (top.g_FolderView)  {
			ReActivateMenuBar();
		}
    }
}


/*
 * Funcion que abre una pagina
 */
function OpenPage(aEvent, Id, FileName)
{

	//mostramos el frame FolderPageFile para la visualizacion de documentos
	mostrarFrameFolderPageFile();

	var element = top.GetSrcElement(aEvent);

	//"openPage:"+aEvent+";"+element.parentNode.parentNode.parentNode.id.toString()+";"+Id+";"+FileName);

	top.g_PrevPage = top.g_Page;

	if (top.g_ActivateTree) {
		// Deshabilitamos las toolbar
	        if (!top.g_FolderView) {
			top.g_OpcAval=false;
		        top.Main.Folder.ToolBarFrm.deshabilitar();
	        }
		else {
			top.Main.Folder.ToolBarFrm.ToolBarDisabled();
		}

		SetSelected(element);


		window.open(top.g_URL + "/getpage.jsp?SessionPId=" + top.g_SessionPId
			+ "&BookId=" + top.g_ArchiveId.toString()
				+ "&RegId=" + top.g_FolderId.toString()
				+ "&DocId=" + element.parentNode.parentNode.parentNode.id.toString()
				+ "&PageId=" + Id.toString()
				+ "&FileName=" + encodeURIComponent(FileName)
		+ "&topURL=" + top.g_URL, "FolderPageFile","location=no",true);

		top.g_ActivateTree = false;
	}
}


function ChkKeyPress(aEvent)
{
	var keyCode = top.GetKeyCode(aEvent);

	if (IsMenu && (keyCode == 27))  {
		HideMenu();
    }

    return;
}

   // consigue los parametros del XML
function getParams(iFolderPId, iFdrReadOnly, iFolderId, iVldSave)
{
    top.g_FolderPId = iFolderPId;

	if (iFdrReadOnly) {
		top.g_FdrReadOnly = true;
	}
	else {
		top.g_FdrReadOnly = false;
	}

	top.g_FolderId = iFolderId;
	top.g_VldSave = iVldSave;
}


// Agrega en el arbol la opcion de historico de distribucion
function AddHistoricDistr()
{
	if (top.g_FolderId > 0) {
		document.getElementById("UL0").innerHTML += '<LI class="CL5" id="liHistDistr">'
            + '<IMG src="images/clock_go.png" border="0" style="cursor:pointer;margin-right:2px" onclick="SelElem( 5, this.parentNode.id );OpenPageDistr(event);"/>'
            + '<A class="Item" onmouseover="OverArchive();" onmouseout="OutArchive();" onclick="SelElem( 5, this.parentNode.id );OpenPageDistr(event);"'
            + ' onkeydown="if (top.GetKeyCode(event)==13){SelElem( 5, this.parentNode.id );OpenPageDistr(event);}" tabIndex="1">'
            + top.GetIdsLan("IDS_TIT_HIST_DISTR") + '</A></LI>';
    }
}

//Agrega en el arbol la opcion de historico de intercambio registral del registro
function AddHistoricIntercambioRegistral()
{
	if (top.g_FolderId > 0) {
		document.getElementById("UL0").innerHTML += '<LI class="CL5" id="liHistInterCambioReg">'
            + '<IMG src="images/clock_go.png" border="0" style="cursor:pointer;margin-right:2px" onclick="SelElem( 5, this.parentNode.id );OpenPageHistIntercambioRegistral(event);"/>'
            + '<A class="Item" onmouseover="OverArchive();" onmouseout="OutArchive();" onclick="SelElem( 5, this.parentNode.id );OpenPageHistIntercambioRegistral(event);"'
            + ' onkeydown="if (top.GetKeyCode(event)==13){SelElem( 5, this.parentNode.id );OpenPageHistIntercambioRegistral(event);}" tabIndex="1">'
            + top.GetIdsLan("IDS_TIT_HIST_INTERCAMBIO_REG") + '</A></LI>';
    }
}



// Agrega en el arbol la opcion de historico de modificaciones del registro
function AddHistoricReg()
{
	if (top.g_FolderId > 0) {
		document.getElementById("UL0").innerHTML += '<LI class="CL5" id="liHistReg">'
            + '<IMG src="images/clock_edit.png" border="0" style="cursor:pointer;margin-right:2px" onclick="SelElem( 5, this.parentNode.id );OpenPageModifReg(event);"/>'
            + '<A class="Item" onmouseover="OverArchive();" onmouseout="OutArchive();" onclick="SelElem( 5, this.parentNode.id );OpenPageModifReg(event);"'
            + ' onkeydown="if (top.GetKeyCode(event)==13){SelElem( 5, this.parentNode.id );OpenPageModifReg(event);}" tabIndex="1">'
            + top.GetIdsLan("IDS_TIT_HIST_REG") + '</A></LI>';
    }
}

// Agrega en el arbol la opcion de Registros asociados
function AddAsocRegs()
{
	if (top.g_FolderId > 0) {
		document.getElementById("UL0").innerHTML += '<LI class="CL5" id="liAsocRegs">'
            + '<IMG src="images/report_link.png" border="0" style="cursor:pointer;margin-right:2px" onclick="SelElem( 5, this.parentNode.id );OpenPageAsocRegs(event);"/>'
            + '<A class="Item" onmouseover="OverArchive();" onmouseout="OutArchive();" onclick="SelElem( 5, this.parentNode.id );OpenPageAsocRegs(event);"'
            + ' onkeydown="if (top.GetKeyCode(event)==13){SelElem( 5, this.parentNode.id );OpenPageAsocRegs(event);}" tabIndex="1">'
            + top.GetIdsLan("IDS_TIT_ASOC_REGS") + '</A></LI>';
    }
}


// Abre el historico de distribucion
function OpenPageDistr(aEvent)
{
	//comprobamos si se debe ocultar el frame de visualizacion de documentos
	ocultarFrameFolderPageFile();

	var element = top.GetSrcElement(aEvent);

    top.g_PrevPage = top.g_Page;

    if (top.g_ActivateTree) {
		// Deshabilitamos las toolbar
        if (!top.g_FolderView){
			top.g_OpcAval=false;
            top.Main.Folder.ToolBarFrm.deshabilitar();
        }
        else {
            top.Main.Folder.ToolBarFrm.ToolBarDisabled();
        }

        SetSelected(element);

        window.open(top.g_URL + "/dtrfdr.jsp?SessionPId=" + top.g_SessionPId
            + "&FolderId=" + top.g_FolderId.toString() + "&ArchiveId=" + top.g_ArchiveId.toString()
            + "&InitValue=1&State=0", "FolderFormData","location=no",true);

        top.g_ActivateTree = false;
   }
}

function OpenPageHistIntercambioRegistral(aEvent)
{
	//comprobamos si se debe ocultar el frame de visualizacion de documentos
	ocultarFrameFolderPageFile();

	var element = top.GetSrcElement(aEvent);

    top.g_PrevPage = top.g_Page;

    if (top.g_ActivateTree) {
		// Deshabilitamos las toolbar
        if (!top.g_FolderView){
			top.g_OpcAval=false;
            top.Main.Folder.ToolBarFrm.deshabilitar();
        }
        else {
            top.Main.Folder.ToolBarFrm.ToolBarDisabled();
        }

        SetSelected(element);

        window.open(top.g_URL + "/MostrarHistorialIntercambioRegistral.do?SessionPId=" + top.g_SessionPId
            + "&FolderId=" + top.g_FolderId.toString() + "&ArchiveId=" + top.g_ArchiveId.toString()
            + "&InitValue=1&State=0", "FolderFormData","location=no",true);

        top.g_ActivateTree = false;
   }
}






// Abre el historico de modificaciones del registro
function OpenPageModifReg(aEvent)
{

	//comprobamos si se debe ocultar el frame de visualizacion de documentos
	ocultarFrameFolderPageFile();

	var element = top.GetSrcElement(aEvent);

    top.g_PrevPage = top.g_Page;

    if (top.g_ActivateTree) {
		// Deshabilitamos las toolbar
        if (!top.g_FolderView){
			top.g_OpcAval=false;
            top.Main.Folder.ToolBarFrm.deshabilitar();
        }
        else {
            top.Main.Folder.ToolBarFrm.ToolBarDisabled();
        }

        SetSelected(element);

        window.open(top.g_URL + "/updhisfdr.jsp?SessionPId=" + top.g_SessionPId
            + "&FolderId=" + top.g_FolderId.toString() + "&ArchiveId=" + top.g_ArchiveId.toString()
            + "&InitValue=1&State=0", "FolderFormData","location=no",true);

        top.g_ActivateTree = false;
   }
}


//Muestra los documentos originales
function OpenPageOrigDocs(aEvent)
{
	var OpenType = top.g_OpenType;
	var element = top.GetSrcElement(aEvent);

	top.g_PrevPage = top.g_Page;

	if (top.g_ActivateTree)
    {
	// Deshabilitamos las toolbar
	if (!top.g_FolderView)
	{
		top.g_OpcAval=false;
            top.Main.Folder.ToolBarFrm.deshabilitar();
         }
         else
         {
		top.Main.Folder.ToolBarFrm.ToolBarDisabled();
         }

         SetSelected(element);

         window.open(top.g_URL + "/origdocfdr.jsp?SessionPId=" + top.g_SessionPId
			+ "&FolderId=" + top.g_FolderId.toString() + "&ArchivePId=" + top.g_ArchivePId.toString()
            + "&OpenType=" + top.g_OpenType.toString(), "FolderFormData","location=no",true);

         top.g_ActivateTree = false;
      }
}


//Muestra los documentos originales
function OpenPageAsocRegs(aEvent)
{
	var element = top.GetSrcElement(aEvent);

    top.g_PrevPage = top.g_Page;

    if (top.g_ActivateTree) {
		// Deshabilitamos las toolbar
        if (!top.g_FolderView){
			top.g_OpcAval=false;
            top.Main.Folder.ToolBarFrm.deshabilitar();
        }
        else {
            top.Main.Folder.ToolBarFrm.ToolBarDisabled();
        }

        SetSelected(element);

        window.open(top.g_URL + "/asocregsfdr.jsp?SessionPId=" + top.g_SessionPId
            + "&FolderId=" + top.g_FolderId.toString() + "&ArchiveId=" + top.g_ArchiveId.toString()
            + "&InitValue=1", "FolderFormData","location=no",true);

        top.g_ActivateTree = false;
   }
}



 // abre la ultima hoja abierta si no hay elementos en la bandeja de distribucion
function OpenPageDataFromDtr()
{
	if ( top.g_Page > -1 ) {
		top.g_PrevPage = top.g_Page;

        // Deshabilitamos las toolbar
        if (!top.g_FolderView){
			top.g_OpcAval=false;
            top.Main.Folder.ToolBarFrm.deshabilitar();
        }
        else {
            top.Main.Folder.ToolBarFrm.ToolBarDisabled();
        }

        top.g_ActivateTree = true;
        SelElem(5, top.g_Page.toString());

        var URL = top.g_URL + "/frmdata.jsp?SessionPId=" + top.g_SessionPId
			+ "&Row=" + Row
			+ "&Page=" + top.g_Page.toString()
			+ "&FdrQryPId=" + top.g_FdrQryPId.toString()
			+ "&FolderPId=" + top.g_FolderPId.toString()
			+ "&FolderId=" + top.g_FolderId.toString()
			+ "&ArchivePId=" + top.g_ArchivePId.toString()
			+ "&ArchiveId=" + top.g_ArchiveId.toString();

        top.XMLHTTPRequestGet(URL, top.ResponseFrmData, true);

        top.g_ActivateTree = false;
        top.g_PrevPage = -1;
	}
 }


function ReActivateMenuBar()
 {
	ReActivateSubMenuBar(top.Main.Folder.FolderBar.document.getElementById("MenuFolderBar"));

	if (top.Main.Folder.FolderBar.document.getElementById("botonesRegistro")){
		ReActivateSubMenuBar(top.Main.Folder.FolderBar.document.getElementById("botonesRegistro"));
	}

	if(top.Main.Folder.FolderBar.document.getElementById("botonesConfRegistro")){
		ReActivateSubMenuBar(top.Main.Folder.FolderBar.document.getElementById("botonesConfRegistro"));
	}
}

function ReActivateSubMenuBar(tabMenu){
	top.Main.Folder.ToolBarFrm.ToolBarEnabledEx();

	for (var i = 0; i < tabMenu.rows[0].cells.length; i++)
	{
		if (tabMenu.rows[0].cells[i].className == "SubOptionsDisabled")
		{
			if (tabMenu.rows[0].cells[i].id != "SaveMenuBtn")
			{
				tabMenu.rows[0].cells[i].className = "SubOptions";
			}
			else
			{
				if (top.g_SavePending == true)
				{
					tabMenu.rows[0].cells[i].className = "SubOptions";
				}
			}
		}
	}
}

function ReLoad() {

	//window.open(top.g_URL + "/FileScanCompulsa?SessionPId=" + top.g_SessionPId
	//		+ "&FolderId=" + top.g_FolderId
	//		+ "&IdFile=0"
	//		+ "&Cancel=1"
	//		+ "&blankPage=blank.htm"
	//		+ "&fileName=", "frmPage", "location=no",true);

	top.Main.Folder.FolderBar.CloseModify(new Object());

}

/*
 * Funcion que muestra el frame de visualizacion de ficheros
 */
function mostrarFrameFolderPageFile(){

	//cambiamos el tamaño de los frame
	top.Main.Folder.FolderData.document.getElementById('FolderFSet').cols =  "265px,0px,0px,*";
	//desactivamos el frame que contiene el formulario del registro
	top.Main.Folder.FolderData.document.getElementById('FolderFormData').tabIndex = "-1";
	top.Main.Folder.FolderData.document.getElementById('FolderFormTree').tabIndex = "-1";
	top.Main.Folder.FolderData.document.getElementById('FolderFormCompulsaData').tabIndex = "-1";
	//activamos como activo el frame que contiene la visualizacion de pagina/documento
	top.Main.Folder.FolderData.document.getElementById('FolderPageFile').tabIndex = "1";
}

/*
 * Funcion que oculta el frame de visualizacion de documentos adjuntos a un
 * registro y muestra el frame que contiene el formulario del registro
 */
function ocultarFrameFolderPageFile(){

	var frame = top.Main.Folder.FolderData.document.getElementById('FolderPageFile');
	//comprobamos si el frame esta activo
	if(frame.tabIndex == 1){
		//borramos el contenido del frame y lo ocultamos
		frame.src="blank.htm";
		frame.tabIndex = "-1";
		//activamos el fram que contiene el formulario del registro
		mostrarFrameFolderFormData();
	}
}

/*
 * Funcion que muestra el frame que contiene el formulario con los datos de los registros
 *
 */
function mostrarFrameFolderFormData(){
	//activamos el frame que contiene el formulario del registro
	top.Main.Folder.FolderData.document.getElementById('FolderFormData').tabIndex = "1";
	top.Main.Folder.FolderData.document.getElementById('FolderPageFile').tabIndex = "-1";
	top.Main.Folder.FolderData.document.getElementById('FolderFormTree').tabIndex = "-1";
	top.Main.Folder.FolderData.document.getElementById('FolderFormCompulsaData').tabIndex = "-1";
	//cambiamos el tamaño de los frames
	top.Main.Folder.FolderData.document.getElementById('FolderFSet').cols = "265px,*,0px,0px";
}
