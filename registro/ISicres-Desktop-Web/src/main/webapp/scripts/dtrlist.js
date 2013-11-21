function onrowover(row)
{
	row.style.color="#e11d03"
	row.style.cursor="pointer";
}


function onrowout(row)
{
    row.style.color= g_color3;
    row.style.cursor="";

}


function onrowsel(row)
{
	row.style.color="#e11d03";
}


function onrowunsel(row)
{
    row.style.color= g_color3;
}


function CheckSel()
{
	var chkAll = true;
	var bHasRech = true;
	var ExistS = false;
	var checks = document.getElementsByName("checkrow");
	var SelAll = top.Main.Distr.document.getElementById("SelAll");
	var checkSel = top.Main.Distr.document.getElementById("checkSel");

	if (checks.length){
		for (var i = 0; i < checks.length; i++) {
			if (!checks[i].checked)	{
				chkAll =false;
			}
			else {
				if (checks[i].getAttribute("State") != top.IDS_DISTR_RECH){
					bHasRech = false;
				}

				if (checks[i].getAttribute("DistType") == "0") {
					ExistS = true;
				}
			}
		}
	}
	else {
		chkAll = false;
		bHasRech = false;
		ExistS = false;
	}

	if (chkAll){
		checkSel.checked = true;
	}
	else {
		checkSel.checked = false;
	}

	top.Main.Distr.VerifyMenu();
}


// Habilita aceptar y si el evento es el intro lanza el cambio de distribucion
function OnPropertyChange(oField)
{
   if (top.miTrim(oField.value) != "")
   {
      document.getElementById("BtnAceptar").disabled = false;
   }
   else
   {
      document.getElementById("BtnAceptar").disabled = true;
   }
}

function nextSibling(node){
	var Sibling = node.nextSibling;

	if (Sibling.nodeType != 1){
		Sibling = nextSibling(Sibling);
	}

	return Sibling;
}

// expande los nodos de las minutas (desde la imagen)
function Expand(oElemImg)
{
	var oElemTR =  nextSibling (oElemImg.parentNode.parentNode);

	if (document.getElementsByTagName("*")){
		if (oElemImg.src.indexOf ("datplus") > 0) {
			oElemTR.style.display = "";
			oElemImg.src = "./images/datminus.gif";
		}else{
			oElemTR.style.display = "none";
			oElemImg.src = "./images/datplus.gif";
		}
	}else{
	    oElemTR.style.display = "";
		if (oElemImg.src.indexOf ("datplus") > 0) {
           oElemTR.style.visibility = "visible";
		   oElemImg.src = "./images/datminus.gif";

		}else{
			oElemTR.style.visibility = "collapse";
			oElemImg.src = "./images/datplus.gif";
		}
	}
}


// expande todos los nodos
function ExpandAll(oTableParent)
{
   for(var ii=0; ii < oTableParent.rows.length; ii++)
   {
      if (oTableParent.rows[ii].name)
      {
         oTableParent.rows[ii].style.display = "block";
      }
   }
   // Se cambian todas las imagenes al "-" de arbol abierto
   for(var jj=0; jj < oTableParent.getElementsByTagName("IMG").length; jj++)
   {
      oTableParent.getElementsByTagName("IMG")[jj].src = "./images/datminus.gif";
   }
}


// abre una carpeta de la distribucion
function OpenFolderDtr(strNameArch, iFolderId, iArchiveId, viewEditDistAcept) {
   var strArchivePId = top.g_ArchivePId.toString();

   if (top.g_ArchiveId != iArchiveId) {
      strArchivePId = 0;
   }

	var URL = top.g_URL + "/default.jsp?AppId=" + top.g_AppId.toString()
			+ "&SessionPId=" + top.g_SessionPId + "&FolderView=1&ArchiveId="
			+ iArchiveId.toString() + "&ArchiveName=" + strNameArch
			+ "&ArchivePId=" + strArchivePId + "&FolderId="
			+ iFolderId.toString() + "&VldSave=1" + "&Idioma="
			+ top.Idioma.toString() + "&numIdioma=" + top.numIdioma.toString()
			+ "&FdrQryPId=" + top.g_FdrQryPId.toString()
			+ "&OpenType=0&OpenFolderDtr=1";

	if (viewEditDistAcept) {
		URL += "&OpenEditDistr=1";
}

	top.OpenNewWindow(URL, "", "10000", "10000", "auto", "yes");

}

function ShowRemarksEx(msg, isAceptar, caseSensitive)
{
	var args = new Array(), resp;

	args[0] = unescape(msg);
	args[1] = 0;
	args[2] = top.GetIdsLan( "IDS_LABEL_REMARKS" );
	args[3] = top.Idioma;
	args[4] = unescape(caseSensitive);
	args[5] = isAceptar;

	resp = top.ShowModalDialog(top.g_URL + "/showremarks.htm", args, 230, 370, "");

	return (resp);
}
