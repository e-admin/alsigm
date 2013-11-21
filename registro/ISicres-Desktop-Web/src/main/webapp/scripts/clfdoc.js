var g_Maximized = false;
var g_PrevFormTreeWidth = 500;

function Sel1stElem()
{
	var items = document.getElementsByTagName("*");
	var URL = top.g_URL + "/frmdata.jsp?SessionPId=" + top.g_SessionPId
		+ "&Row=" + top.g_FolderSel.toString()
		+ "&Page=" + top.g_Page.toString()
		+ "&FdrQryPId=" + top.g_FdrQryPId.toString()
		+ "&FolderPId=" + top.g_FolderPId.toString()
		+ "&FolderId=" + top.g_FolderId.toString()
	        + "&ArchivePId=" + top.g_ArchivePId.toString()
	        + "&ArchiveId=" + top.g_ArchiveId.toString()
		+ "&CopyFdr=" + top.g_CopyFdr.toString();

	for( var ii = 0; ii < items.length; ii++ ) {
		if( ( items[ii].className == "CL5" ) ){
			window.TreeElemSel  = items[ii].id;
			window.TreeType     = NumeroClase( items[ii].className );
			SelElem( 5, items[ii].id );
			top.g_Page = parseInt(items[ii].id);

			// restaura la parte del array de modificaciones que es persistente
			top.RestorePersistentArray(top.Main.Folder.FolderBar.FldDataArr);

			if (!top.g_bIsBucle){
				top.XMLHTTPRequestGet(URL, top.ResponseFrmData, true);
			}
			else {
				if (top.g_PrevPage != -1) {
					top.g_PrevPage = top.g_Page;
					top.XMLHTTPRequestGet(URL, top.ResponseFrmData, true);
				}
				else  {
					CleanFldsForm(top.Main.Folder.FolderData.FolderFormData.document.getElementById("FrmData"), top.Main.Folder.FolderData.FolderFormData.document.images);
					top.g_ActivateTree = true;
				}

				top.g_bIsBucle = false;
			}

			return;
		}
   }

   return;
}

function ObjId( PId, Pclase )
{
	var items = document.getElementsByTagName("*");

	for( var ii = 0; ii < items.length; ii++ )  {
		if( ( items[ii].id == PId )	&& ( items[ii].className == Pclase ) ) {
			return items[ii];
		}
	}

	return false;
}

function IsVisible( Obj )
{
	if( Obj.parentNode ) {
		if( Obj.id.search( '_0' ) != -1 ){
			return false;
		}
		else{
			return IsVisible( Obj.parentNode );
		}
	}
	else{
		return true;
	}
}


function SomeVisible( ULNode )
{
   for ( var ii = 0; ii < ULNode.childNodes.length; ii++ ) {
      if ( ULNode.childNodes[ii].nodeName == "LI" ) {
         if( IsVisible( ULNode.childNodes[ii] ) ) {
            return true;
         }
      }
   }

   return false;
}


function GetChildA( LINode )
{
	for ( var ii = 0; ii < LINode.childNodes.length; ii++ ) {
		if ( LINode.childNodes[ii].nodeName == "A" ){
			return( LINode.childNodes[ii] );
		}
	}

	return false;
}


function childImg( OLista )
{
	for ( var ii = 0; ii < OLista.childNodes.length; ii++ ) {
		var obj = OLista.childNodes[ii];

		if(obj.src){
			if ( ( obj.src.search( "datplus.gif" ) != -1 ) || ( obj.src.search( "datminus.gif" ) != -1 ) ) {
				return(obj);
			}
		}

	}

	return false;
}


function arbol( PId, Ptipo )
{
   var OLista   = ObjId( PId, ClaseNumero( Ptipo ) );
   if (OLista)
   {
      var OChildUl = GetChildUL( OLista );

      if( OChildUl && SomeVisible( OChildUl ) )
      {
         var OChildImg = childImg( OLista );
         if ( OChildImg )
         {
            if ( OChildImg.src.search( "datplus.gif" ) != -1 )
            {
               OChildImg.src          = "images/datminus.gif";
               OChildUl.style.display = 'list-item';
            }
            else
            {
               OChildImg.src          = "images/datplus.gif";
               OChildUl.style.display = 'none';
            }
         }
      }
   }
   return;
}

function SelElem( clase, PId )
{
   if ( (top.g_ActivateTree) && ( clase == 3 || clase == 5 ) )
   {
      var Obj = GetChildA( ObjId( window.TreeElemSel, ClaseNumero( window.TreeType ) ) );

      Obj.style.fontWeight      = "normal";
      Obj.style.backgroundColor = g_color1;
      Obj.style.color = g_color2;

      window.TreeElemSel  = PId;
      window.TreeType     = clase;

      Obj = GetChildA( ObjId( window.TreeElemSel, ClaseNumero( window.TreeType ) ) );

      Obj.style.fontWeight = "bold";
      Obj.style.backgroundColor = g_color1;
      Obj.style.color = g_color2;
   }

   return;
}

function meterEnLista( pclase, ptipo, pvalor, pelemento )
{
   var strCad = "";
   switch (ptipo)
   {
      case 1:  strCad = "cdpclf.gif";break;
      case 2:  strCad = "folder_database.png";break;
      case 3:  strCad = "OpenPageFich";break;
      default: strCad = "OpenPageScan";
   }

   pvalor = pvalor.replace(/\\/g,"\\\\");
   if( ObjId( window.TreeElemAux, ClaseNumero( window.TreeTypeAux ) ).innerHTML.search( 'UL' ) == -1 )
   {
      pvalor=escape_especial_chars(pvalor);
      if ( (ptipo == 1) || (ptipo == 2) )
      {
         eval( "ObjId( window.TreeElemAux, ClaseNumero( window.TreeTypeAux ) ).innerHTML +="
            + "'<UL><LI class=" + '"' + pclase + '" id="' + pelemento + '">'
            + '<img src="images/datminus.gif" onclick="arbol( this.parentNode.id,' + ptipo.toString() + ');"'
            + ' onkeydown="if (top.GetKeyCode(event)==13){arbol( this.parentNode.id,' + ptipo + ');}" style="cursor:pointer" tabIndex="1">'
            + '<img src="images/' + strCad + '" onmouseover="CursorOver( this );"'
            + 'oncontextmenu="ShowMenu(event, ' + ptipo.toString() + ', this.parentNode.id); return false;">'
            + '<A onmouseover="CursorOver( this );" onmouseout="CursorOut( this );"'
            + ' onfocus="CursorOver( this );" onblur="CursorOut( this );"'
            + 'oncontextmenu="ShowMenu(event, ' + ptipo.toString() + ', this.parentNode.id); return false;" tabIndex="1">'
            + top.miTrim( pvalor ) + "</A></LI></UL>'" );
      }
      else
      {
         eval( "ObjId(window.TreeElemAux, ClaseNumero( window.TreeTypeAux ) ).innerHTML +="
            + "'<UL><LI class=" + '"' + pclase + '" id="' + pelemento + '">'
            + '<img src="images/datblank.gif">'
            + '<img src="images/page.png" style="cursor:pointer"'
            + 'oncontextmenu="ShowMenu(event, 3, this.parentNode.id); return false;">'
            + '<A style="cursor:pointer"'
            + 'oncontextmenu="ShowMenu(event, 3, this.parentNode.id); return false;"'
            + 'onmouseout="CursorOut( this );" tabIndex="1">' + top.miTrim( pvalor ) + "</A></LI></UL>'" );
      }
   }
   else
   {
      var pos=ObjId(window.TreeElemAux, ClaseNumero( window.TreeTypeAux ) ).innerHTML.indexOf( '>',ObjId(window.TreeElemAux, ClaseNumero( window.TreeTypeAux ) ).innerHTML.search('<UL'));
      if ( (ptipo == 1) || (ptipo == 2) )
      {
         eval( "ObjId(window.TreeElemAux, ClaseNumero( window.TreeTypeAux ) )" ).innerHTML=ObjId(window.TreeElemAux, ClaseNumero( window.TreeTypeAux ) ).innerHTML.substring( 0,pos+1)
            + "<LI class=" + '"' + pclase + '" id="' + pelemento + '">'
            + '<img src="images/datminus.gif" onclick="arbol( this.parentNode.id,' + ptipo.toString() + ');"'
            + ' onkeydown="if (top.GetKeyCode(event)==13){arbol( this.parentNode.id,' + ptipo.toString() + ');}" style="cursor:pointer" tabIndex="1">'
            + '<img src="images/' + strCad + '" onmouseover="CursorOver( this );"'
            + 'oncontextmenu="ShowMenu(event, ' + ptipo.toString() + ', this.parentNode.id); return false;">'
            + '<A onmouseover="CursorOver( this );" onmouseout="CursorOut( this );"'
            + ' onfocus="CursorOver( this );" onblur="CursorOut( this );"'
            + 'oncontextmenu="ShowMenu(event, ' + ptipo.toString() + ', this.parentNode.id); return false;" tabIndex="1">'
            + top.miTrim( pvalor ) + "</A></LI>"
            + ObjId(window.TreeElemAux, ClaseNumero( window.TreeTypeAux ) ).innerHTML.substr( pos+1);
      }
      else
      {
         var Item = ObjId(window.TreeElemAux, ClaseNumero( window.TreeTypeAux ) );
         var UL = GetChildUL(Item);

         UL.innerHTML = UL.innerHTML + "<LI class=" + '"' + pclase + '" id="' + pelemento + '">'
            + '<img src="images/datblank.gif">'
            + '<img src="images/page.png" style="cursor:pointer"'
            + 'oncontextmenu="ShowMenu(event, 3, this.parentNode.id); return false;">'
            + '<A style="cursor:pointer" '
            + 'oncontextmenu="ShowMenu(event, 3, this.parentNode.id); return false;" tabIndex="1">' + top.miTrim( pvalor )
            + "</A></LI>";
      }
   }

   top.Main.Folder.FolderBar.ActivateSave();

   return;
}

function escape_especial_chars(file){
	var special_chars=["'"];

	for(j=0;j<special_chars.length;j++){
		file=file.replace(special_chars[j],"\\"+special_chars[j]);
	}
    return file;
}

/**
 * Se valida si no existe el nombre del documento/fichero dentro del listado pasada como parametro
 *
 * retorna - FALSE: existe el nombre del fichero/documento en el listado
 *         - TRUE: el nombre del fichero/documento no existe en el listado
 *
 */
function chkNom( OLista, pnombre, pnivel, pclase )
{
   if( top.miTrim( pnombre ) == "" ) {
      return false;
   }
   else {
     if ((top.miTrim(top.GetInnerText(GetChildA(OLista))) == top.miTrim(pnombre))
		&& (OLista.className == pclase)	&& (OLista.style.display != 'none')){
        return false;
     }
     else {
        if ( pnivel < 2 ) {
           for( var ii=0; ii<OLista.childNodes.length; ii++ ) {
              if( !( chkNom( OLista.childNodes[ii], top.miTrim( pnombre ), pnivel + 1, pclase ) ) ) {
                 return false;
              }
           }
        }
     }

     return true;
   }
}

/*
 *
 * 2 documento
 * 4 pagina
 *
 *
 */
function addElem( ptipo )
{
	var Found = false;
	switch ( window.TreeTypeAux ) {
		case 0:
		case 1:
			if (childImg(ObjId( window.TreeElemAux, ClaseNumero(window.TreeTypeAux))).src.search("datplus.gif") != -1)	{
				arbol(window.TreeElemAux, window.TreeTypeAux);
			}

			if (ptipo == 4){
				var items = document.getElementsByTagName("*");

				for (var i = 0; (i < items.length) && !Found; i++){
					if (items[i].className == "CL2"){
						Found = true;
						window.TreeElemAux  = items[i].id;
						window.TreeTypeAux  = 2;
					}
				}

				if (!Found){
					meterEnLista( "CL2", 2, top.GetIdsLan("IDS_LABEL_DOCUMENT"), "LI" + parent.parent.FolderBar.elemento);
					window.TreeElemAux  = "LI" + parent.parent.FolderBar.elemento;
					window.TreeTypeAux  = 2;
					parent.parent.FolderBar.elemento = parseInt( parent.parent.FolderBar.elemento ) + 1;
				}

				addElem(ptipo);
			}
			else if ((ptipo == 1) || (ptipo == 2)) {
				if ((window.document.getElementById("texto").value != "")
					&& (chkNom(ObjId( window.TreeElemAux, ClaseNumero(window.TreeTypeAux)),
					window.document.getElementById("texto").value, 0 , "CL" + ptipo.toString() ) ) ) {
					meterEnLista( "CL" + ptipo.toString(), ptipo, window.document.getElementById("texto").value,
						"LI" + parent.parent.FolderBar.elemento );
					parent.parent.FolderBar.elemento = parseInt( parent.parent.FolderBar.elemento ) + 1;
				}
				else {
					window.alert( top.GetIdsLan( "IDS_ERRORNOMBRE" ) );
				}
			}

			break;
		case 2:
			if ( childImg( ObjId( window.TreeElemAux, ClaseNumero( window.TreeTypeAux ) ) ).src.search( "datplus.gif" ) != -1 ) {
				arbol( window.TreeElemAux, window.TreeTypeAux );
			}

			if ( (ptipo==3) || (ptipo==4) ) {
	            if ( ( window.document.getElementById("texto").value != "" )
					&& ( chkNom( ObjId( window.TreeElemAux, ClaseNumero( window.TreeTypeAux ) ),
                    window.document.getElementById("texto").value, 0, "CL3" ) ) ) {
					meterEnLista( "CL3", ptipo, window.document.getElementById("texto").value,
						"LI" + parent.parent.FolderBar.PageCnt );
					parent.parent.FolderBar.PageCnt = parseInt( parent.parent.FolderBar.PageCnt ) + 1;
				}
				else {
					window.alert( top.GetIdsLan( "IDS_ERRORNOMBRE" ) );
				}
			}
			else {
				window.alert( top.GetIdsLan( "IDS_ERRORTIPOELEM" ) );
			}
			break;
		default:
			window.alert( top.GetIdsLan( "IDS_ERRORTIPOELEM" ) );
			break;
	}
	return;
}


function RmvObjFile( ONode )
{
	if( ( ONode.className == "CL3" )&&( ONode.id.search( 'LI' ) != -1 ) ) {
		var ObjId = ONode.id.substring( 2, ONode.id.length );
		var idFicheroBorrar = "LI" + ObjId.toString();
		var ObjToRmv = parent.parent.FolderBar.document.getElementById("frmStrUpdate").document.getElementById(idFicheroBorrar);
		//Borramos los datos del fichero
		eliminarInputTreeUpdateFichero(idFicheroBorrar);

		parent.parent.FolderBar.document.getElementById("frmStrUpdate").removeChild(ObjToRmv);
   }

   for ( var ii = 0; ii < ONode.childNodes.length; ii++ ) {
		RmvObjFile( ONode.childNodes[ii] );
   }

   return;
}

//Función que borra un fichero adjuntado al registro
function deleteFile(){
	var TreeElemLocal = window.TreeElemAux;
	var TreeTypeLocal = window.TreeTypeAux;

	if ( window.confirm( top.GetIdsLan( "IDS_QRYBORRAELEM" ) ) ) {
		//obtenemos la información del fichero
		var ONode  = ObjId( TreeElemLocal, ClaseNumero( TreeTypeLocal ) );
		//obtenemos la información del documento
		var OPadre = ONode.parentNode.parentNode;

		//componemos la url con los datos
		var URL = top.g_URL + "/FileDelete?SessionPId=" + top.g_SessionPId
				+ "&RegId=" + top.g_FolderId.toString()
				+ "&BookId=" + top.g_ArchiveId.toString()
				+ "&DocId=" + OPadre.id
				+ "&PageId=" + ONode.id;

		//Realizamos la invocación al servidor
		top.XMLHTTPRequestGet(URL, top.ResponseFrmData, false);

		//Recargamos la pantalla
		top.Main.Folder.FolderBar.CloseModify(new Object());
	}
}

function rmvElem()
{
	var TreeElemLocal = window.TreeElemAux;
	var TreeTypeLocal = window.TreeTypeAux;

	IsClickElem = true;

	if ( TreeTypeLocal == 0 ) {
		if ( window.confirm( top.GetIdsLan( "IDS_ERRORBORRARAIZ" ) ) ){
			if ( childImg( window.R0 ).src.search( "datplus.gif" ) != -1 ) {
				arbol( "R0", 0 );
			}

			if ( window.R0.innerHTML.search( 'UL' ) != -1 ) {
				var OChild = GetChildUL( window.R0 );
				var ii = OChild.childNodes.length;

				for ( ; ii>0; ii-- ) {
					if ( OChild.childNodes[ii-1].id.search( 'LI' ) == -1 ) {
						if( OChild.childNodes[ii-1].id.search( '_0' ) == -1 ) {
							OChild.childNodes[ii-1].style.display = 'none';
							OChild.childNodes[ii-1].id += "_0";
						}
					}
					else {
						OChild.childNodes[ii-1].removeNode( true );
					}
				}

				if ( OChild.childNodes.length == 0 ) {
					OChild.removeNode( true );
				}

				parent.parent.FolderBar.document.getElementById("frmStrUpdate").innerHTML = "";
			}

			top.Main.Folder.FolderBar.ActivateSave();
		}
	}
	else{
		if ( window.confirm( top.GetIdsLan( "IDS_QRYBORRAELEM" ) ) ) {
			var ONode  = ObjId( TreeElemLocal, ClaseNumero( TreeTypeLocal ) );
			var OPadre = ONode.parentNode;
			var countLI = 0;

			RmvObjFile( ONode );

		    if ( TreeElemLocal.search( 'LI' ) == -1 ) {
				ONode.style.display = 'none';
				ONode.id += '_0';
			}
			else {
				ONode.removeNode( true );
			}

			for (var i = 0; i < OPadre.childNodes.length; i++) {
				if (OPadre.childNodes[i].nodeName == "LI") {
					countLI++;
				}
			}

			if (countLI == 0){
				OPadre.removeNode( true );
			}

	        top.Main.Folder.FolderBar.ActivateSave();
		}
	}

	IsClickElem = false;
	HideMenu();

	return;
}

// Función que borra del formulario frmStrUpdate los datos TREEUPDATE referentes a los
// ficheros
function eliminarInputTreeUpdateFichero(idFicheroBorrar){
	//obtenemos los datos del formulario
	var oForm = parent.parent.FolderBar.document.getElementById("frmStrUpdate");
	// recorremos todos los elementos del formulario
	for(var ii=0; ii < oForm.length; ii++){
		// obtenemos los que son de tipo TEXT y con nombre TREEUPDATE
		if((oForm[ii].type == "text") && (oForm[ii].name = "TreeUpdate")){
			// Descomponemos el valor de dichos inputs (Ejemplo del valor
			// para Ficheros: 1|CL3|LI0|nombre_fichero|LI2000|CL2)
			var value = oForm[ii].value.split("|");
			// Comprobamos si el elemento tratado coincide con el fichero
			// que llega como parametro para eliminarlo del formulario
			if(value[2] == idFicheroBorrar){
				parent.parent.FolderBar.document.getElementById("frmStrUpdate").removeChild(oForm[ii]);
			}
		}
	}
}


function addNewFilesToTree(files,mustDelete){

	for (i = 0; i < files.length; i = i + 2) {
		var OElement = parent.parent.FolderBar.document.createElement("input");

		OElement.setAttribute("type", "file");
		OElement.setAttribute("id", "LI" + parent.parent.FolderBar.PageCnt.toString());
		OElement.setAttribute("name", "LI" + parent.parent.FolderBar.PageCnt.toString());
		//OElement.setAttribute("idFile", i.toString())
		OElement.setAttribute("pathFile", files[i]);
		OElement.setAttribute("mustDelete", mustDelete);

		parent.parent.FolderBar.document.getElementById("frmStrUpdate").appendChild( OElement );
		window.document.getElementById("texto").value = getNameFile(files[i]);

		parent.parent.FolderBar.existFileScan = true;
		addElem( 4 );

	}
}

//Funcion que obtiene el nombre del fichero (el nombre del fichero esta limitado a 64 caracteres, contando la extension)
function getNameFile(file) {
	//Longitud maxima para los nombres de ficheros
	var maxLengthNameFile = 64;

	var nameFile;
	//obtenemos la extension del fichero
	var extension = top.getExtensionFile(file);

	if(extension.length>0){
		//si tiene extension, obtenemos lo que corresponde a nombre del fichero
		nameFile = top.GetNamePath(file).substring( 0, top.GetNamePath(file).lastIndexOf("."));
	}else{
		//sino tiene extension, obtenemos toda la cadena como nombre de fichero
		nameFile = top.GetNamePath(file);
	}

	//comprobamos que el nombre del fichero junto con la extension no excedan el tamaño maximo aceptado
	if((nameFile.length + extension.length)>maxLengthNameFile){
		//si exceden los caracteres, se recorta el nombre del fichero
		nameFile = nameFile.substring(0, (maxLengthNameFile - extension.length));
	}

	return nameFile;
}


//Escanea una imagen y mete el fichero dentro del menu de documentos
function examinar()
{
	IsClickElem = true; //Indicar que estamos trabajando sobre un elemento del menu, cuando salimos se deberia de establecer a false
                       //pero no hay forma de controlar cuando se sale, y si lo establecemos a "false" al final de la funcion entonces
                       //se pierde el tipo de elemento sobre el que hemos hecho "click" y daria un error al añadir los ficheros escaneados.

	var URL = top.g_URL + "/examinarFile.jsp?sessionPId=" + top.g_SessionPId+"&idLibro="+top.g_ArchiveId.toString()+"&idRegistro="+top.g_FolderId.toString();
	window.open(URL, "FolderFormCompulsaData","location=no",true);

	HideMenu();
}


//Funcion que se ejecuta desde el applet cuando se confirma el escaneado de ficheros.
function examinar_returnFiles(files,applet)
{
	if(files != null){
		files=files.split("|");
		//Indicamos que existen ficheros escaneados
		parent.parent.FolderBar.existFileScan = true;

		addNewFilesToTree(files,"0");
	}
	applet.stop();
	applet.destroy();
}

//Escanea una imagen y mete el fichero dentro del menu de documentos
function escanear()
{
	IsClickElem = true; //Indicar que estamos trabajando sobre un elemento del menu, cuando salimos se deberia de establecer a false
                       //pero no hay forma de controlar cuando se sale, y si lo establecemos a "false" al final de la funcion entonces
                       //se pierde el tipo de elemento sobre el que hemos hecho "click" y daria un error al añadir los ficheros escaneados.

	var URL = top.g_URL + "/escanear.jsp?sessionPId=" + top.g_SessionPId+"&idLibro="+top.g_ArchiveId.toString()+"&idRegistro="+top.g_FolderId.toString();
	window.open(URL, "FolderFormCompulsaData","location=no",true);
	HideMenu();
}



//Funcion que se ejecuta desde el applet cuando se confirma el escaneado de ficheros.
function scannedFiles(files,applet)
{

	//alert("scannedFiles");
	var stringArray=applet.toJavascriptString(files);
	files=stringArray.split("|");
	//Indicamos que existen ficheros escaneados
	parent.parent.FolderBar.existFileScan = true;

	addNewFilesToTree(files,"1");

	delete files;
	files = null;
	//paramos y destruimos el applet
	applet.stop();
	applet.destroy();
}






function ClaseNumero( Ptipo )
{


   if ( (Ptipo >= 0) && (Ptipo <= 5) )
   {
      return "CL" + Ptipo.toString();
   }
   else
   {
      return "";
   }
}

function NumeroClase( Ptipo )
{

   switch ( Ptipo )
   {
      case "CL0":	return 0;
	         break;
      case "CL1":	return 1;
	         break;
      case "CL2":	return 2;
	         break;
      case "CL3":	return 3;
	         break;
      case "CL4":	return 4;
	         break;
      case "CL5":	return 5;
	         break;
   }
   return;
}

function CursorOver(SrcObj)
{
	if ((!top.g_FdrReadOnly) || (SrcObj.parentNode.className == "CL3")) {
		if (top.g_VldSave == 0){
			SrcObj.style.textDecoration = 'none';
		}
		else {
			SrcObj.style.textDecoration = "underline";
		}
   }
   return;
}

function CursorOut(SrcObj)
{
   SrcObj.style.textDecoration = 'none';
   return;
}


function OpenPageFich( PagId )
{
	if( PagId.search( 'LI' )!= -1 ) {
		top.g_PrevPage = top.g_Page;
		PagId = PagId.substring( 2, PagId.length );
		window.open( eval( "parent.parent.FolderBar.document.getElementById('frmStrUpdate').LI" + PagId ).value,
			"FolderFormData","location=no",true);
	}

	return;
}


function OverMenu(obj)
{
	obj.className = "Selected";
	return;
}

function OutMenu(obj)
{
	obj.className = "Option";
	return;
}
/*
 *
 * 0 - R0 NODO RAIZ ANEXOS
 * 2 - TIPO DOCUMENTO
 * 3 - TIPO PAGINA
 */
function ShowMenu(aEvent, PTipo, PId )
{
	//alert("ShowMenu PTipo:"+PTipo+"--Pid"+PId+"---window.TreeElemSel:"+window.TreeElemSel);
	var e = (!aEvent)?window.event:aEvent;

	if ( (!top.g_FdrReadOnly ) && (top.g_VldSave == 1) )  {
		if( !window.IsMenu ) {
			window.IsMenu = true;
			window.TreeElemAux = window.TreeElemSel;
			window.TreeTypeAux = window.TreeType;
		}

		var Obj = GetChildA( ObjId( window.TreeElemAux, ClaseNumero( window.TreeTypeAux ) ) );

		Obj.style.fontWeight      = "normal";
		Obj.style.backgroundColor = g_color1;

		window.TreeElemAux  = PId;
		window.TreeTypeAux  = PTipo;

		Obj = GetChildA( ObjId( window.TreeElemAux, ClaseNumero( window.TreeTypeAux ) ) );

		Obj.style.fontWeight      = "bold";
		Obj.style.backgroundColor = g_color1;

		var divHTML = "";

		divHTML =
			'<img src="images/roundcorner7_topleft_blue.gif" style="position:absolute; top:0; left:0">'
			+ '<img src="images/roundcorner7_topright_blue.gif" style="position:absolute; top:0; left:113">'
			+ '<img src="images/roundcorner7_bottomleft_blue.gif" style="position:absolute; top:145; left:0">'
			+ '<img src="images/roundcorner7_bottomright_blue.gif" style="position:absolute; top:145; left:113">'
			+ '<div class="divMenu1" style="top: 9; left: 0; width: 120; height: 138; position: absolute; '
			+ 'overflow: hidden;">'
			+ '</div>'
			+ '<div class="divMenu2" style="top: 2; left: 7; width: 106; height: 152; position: absolute; overflow: hidden;">'
			+ '</div>'
			+ '<table style="position:absolute; top:5; left:10; width:100" border="0" cellspacing="0" cellpadding="0">'
			+ '<tr>';

		switch( PTipo ){
			case 0:
			case 1:
				divHTML += '<td id="tdAddDoc" class="Option" onmouseOver="OverMenu(this);" onmouseOut="OutMenu(this);" onfocus="OverMenu(this);" onblur="OutMenu(this);" onclick="PromptName(2);" onkeydown="if (top.GetKeyCode(event)==13){PromptName(2);}" tabIndex="1"';
				break;
			default:
				divHTML += '<td class="Disabled"';
		}

		divHTML += ' nowrap="true">'
			+ top.GetIdsLan( "IDS_MENUADDDOC" ) + '</td>'
			+ '</tr>'
			+ '<tr>';

		switch( PTipo ){
			case 0:
			case 2:
				divHTML += '<td id="tdAddPag" class="Option" onmouseOver="OverMenu(this);" onmouseOut="OutMenu(this);" onfocus="OverMenu(this);" onblur="OutMenu(this);" onclick="PromptName(3)" onkeydown="if (top.GetKeyCode(event)==13){PromptName(3);}" tabIndex="1"';
				break;
			default:
				divHTML += '<td class="Disabled"';
				break;
		}

		divHTML += ' nowrap="true">'
			+ top.GetIdsLan( "IDS_MENUADDPAG" ) + '</td>'
			+ '</tr>';

	      // Metemos la opcion del escaner
		  divHTML += '<tr>';

		switch( PTipo ){
			case 0:
			case 2:
				divHTML += '<td id="tdAddPagScan" class="Option" onmouseOver="OverMenu(this);" onmouseOut="OutMenu(this);" onfocus="OverMenu(this);" onblur="OutMenu(this);" onclick="PromptName(5)" onkeydown="if (top.GetKeyCode(event)==13){PromptName(5);}" tabIndex="1"';
				break;
			default:
				divHTML += '<td class="Disabled"';
				break;
		}

		divHTML += ' nowrap="true">'
			+ top.GetIdsLan( "IDS_MENUADDPAG_ESC" ) + '</td>'
			+ '</tr>'
			+ '<tr>'
		    + '<td nowrap="true">'
			+ '<hr class="Disabled" style="height:1px">'
			+ '</td>'
			+ '</tr>'
			+ '<tr>';

		// LI == elemento no subido todavía al servidor se puede borrar
		if( window.TreeElemAux.search( "LI" ) != -1 )  {
			divHTML += '<td id="tdRemove" class="Option" onmouseOver="OverMenu(this);" onmouseOut="OutMenu(this);" onfocus="OverMenu(this);" onblur="OutMenu(this);" onclick="rmvElem()" onkeydown="if (top.GetKeyCode(event)==13){rmvElem();}" tabIndex="1" nowrap="true">';
		}
		else {
			//se comprueba que el usuario tenga permisos y que sea una pagina del documento
			//PTipo == 0 - Raiz de documentos; PTipo == 2 - Documento; PTipo == 3 - Pagina
			if((top.g_deleteFilePerms == "true") && (PTipo == 3)){
				divHTML += '<td id="tdRemove" class="Option" onmouseOver="OverMenu(this);" onmouseOut="OutMenu(this);" onfocus="OverMenu(this);" onblur="OutMenu(this);" onclick="deleteFile()" onkeydown="if (top.GetKeyCode(event)==13){deleteFile();}" tabIndex="1" nowrap="true">';
			}else{
				divHTML += '<td class="Disabled" nowrap="true">';
			}
		}

		divHTML += top.GetIdsLan( "IDS_MENUBORRAR" )
			+ '</td>'
			+ '</tr>'
			+ '<tr>'
			+ '<td nowrap="true">'
			+ '<hr class="Disabled" style="height:1px"></td>'
			+ '</tr>'
			+ '<tr>';

	   //LI == elemento no subido todavía al servidor se puede renombrar
	   if( window.TreeElemAux.search( "LI" ) != -1 )   {
			divHTML += '<td id="tdRename" class="Option" onmouseOver="OverMenu(this);" onmouseOut="OutMenu(this);" onfocus="OverMenu(this);" onblur="OutMenu(this);" onclick="rnmElem()" onkeydown="if (top.GetKeyCode(event)==13){rnmElem();}" tabIndex="1" nowrap="true">';
		}
		else {
			divHTML += '<td class="Disabled" nowrap="true">';
		}

		divHTML += top.GetIdsLan( "IDS_MENUCHGNAME" ) + '</td>'
			+ '</tr>';


		divHTML += '<tr><td nowrap="true"><hr style="height: 1px;" class="Disabled"></td></tr>';


			//opcion propiedades de una pagina solo para aquellas paginas ya subidas al servidor
			if ( PTipo==3){
				divHTML +='<tr>';
				divHTML += '<td id="tdProperties" class="Option" onclick="showDocumentAttachmentInfo('+PId+')">';
				divHTML += top.GetIdsLan( "IDS_MENUINFODOCELECTRONICO" )+ '</td>'
					+ '</tr>';
			}

		divHTML += '</table>';

		document.getElementById("MenuClf").innerHTML = divHTML;
		document.getElementById("MenuClf").style.visibility = "visible";

		switch( PTipo ) {
			case 0:
			case 1:
				top.SetTableFocus(document.getElementById("tdAddDoc"));
				break;
			case 2:
				top.SetTableFocus(document.getElementById("tdAddPag"));
				break;
		}

		var posicion = posicionMenu(e, document.getElementById("MenuClf"), 120, 119);
   }

   return;
}

function showDocumentAttachmentInfo(PId){
	HideMenu();
	var URL = top.g_URL + "/documentoElectronico?sessionPId=" + top.g_SessionPId+"&idPagina="+PId;
	window.open(URL, "FolderFormData","location=no",true);

}

function posicionMenu(aEvent, oObject, anchoMenu, altoMenu)
{
	var iPosX = 0;
	var iPosY = 0;
	var mouseX = top.GetEventPositionX(aEvent);
	var mouseY = top.GetEventPositionY(aEvent);

	if ((mouseX + anchoMenu) > document.body.clientWidth)	{
		iPosX = document.body.clientWidth-anchoMenu+document.body.scrollLeft;
	}
	else  {
		iPosX = mouseX + document.body.scrollLeft;
	}

	if ((mouseY + altoMenu) > document.body.clientHeight)  {
		iPosY= document.body.clientHeight - altoMenu + document.body.scrollTop;
	}
	else  {
		iPosY = mouseY + document.body.scrollTop;
	}

	oObject.style.left = iPosX;
	oObject.style.top = iPosY;
}

function HideMenu()
{
   if( (window.TreeElemAux!="") && (window.TreeTypeAux!=null) && (window.TreeElemAux!=null) && (!IsClickElem) ) {
      var Obj = ObjId( window.TreeElemAux, ClaseNumero( window.TreeTypeAux ) );

      if( Obj ) {
         Obj = GetChildA( Obj );

         if( Obj ) {
            Obj.style.fontWeight      = "normal";
            Obj.style.backgroundColor = g_color1;
         }
      }

      window.TreeElemAux  = window.TreeElemSel;
      window.TreeTypeAux  = window.TreeType;
      Obj = ObjId( window.TreeElemAux, ClaseNumero( window.TreeTypeAux ) );

      if( Obj ) {
         Obj = GetChildA( Obj );

         if( ( Obj ) && ( IsVisible( Obj ) ) ){
            Obj.style.fontWeight      = "bold";
            Obj.style.backgroundColor = g_color1;
         }
         else {
            Sel1stElem();
         }
      }
      else {
         Sel1stElem();
      }
   }

   window.IsMenu = false;
   document.getElementById("MenuClf").style.visibility = "hidden";


   return;
}

/*
 * 3 EXAMINAR PAGINA
 * 5 ESCANEAR PAGINA
 */
function PromptName( PTipo )
{
	//alert("PromptName:"+PTipo);
	var divHTML = "";
	var Incleft = 0;

	if ( (PTipo == 3) || (PTipo == 5) ) {
		Incleft = 35;
	}

	divHTML = '<img src="images/roundcorner7_topleft_blue.gif" style="position:absolute; top:0; left:0">'
		+ '<img src="images/roundcorner7_topright_blue.gif" style="position:absolute; top:0; left:' + (145 + Incleft ) + '">'
		+ '<img src="images/roundcorner7_bottomleft_blue.gif" style="position:absolute; top:55; left:0">'
		+ '<img src="images/roundcorner7_bottomright_blue.gif" style="position:absolute; top:55; left:' + (145 + Incleft ) + '">'
		+ '<div class="divMenu1" style="top: 9; left: 0; width: ' + (152 + Incleft ) + '; height: 48; position: absolute; '
		+ 'overflow: hidden;">'
		+ '</div>'
		+ '<div class="divMenu2" style="top: 2; left: 7; width: ' + (138 + Incleft ) + '; height: 62; position: absolute; overflow: hidden;">'
		+ '</div>'
		+ '<table style="position:absolute; top:10; left:10; width:100" border="0" cellspacing="2" cellpadding="0">'
		+ '<form id="frmFich" name="frmFich" style="position:absolute; top:10; left:10;" onsubmit="validarfrmFich(' + PTipo
		+ ');return false;">';

	switch( PTipo ) {
		case 1:
			divHTML += '<tr><td class="Option">' + top.GetIdsLan( "IDS_MENUNAMECLSF" ) + ':</td></tr>';
			break;
		case 2:
			divHTML += '<tr><td class="Option">' + top.GetIdsLan( "IDS_MENUNAMEDOC" ) + ':</td></tr>';
			break;
		case 3:
			divHTML += '<tr><td class="Option">' + top.GetIdsLan( "IDS_MENUNAMEPAG" ) + ':</td></tr>'
				+ '<tr><td><input type="text" id="texto" name="texto" class="Option" maxlength="64"></td>'
                + '<td><a href="javascript:examinar()" class="Option" style="cursor:pointer" tabIndex="1">' + top.GetIdsLan( "IDS_MENUNEXAMINAR" ) + '</a>'
                + '<input type="file" style="visibility:hidden" name="fichero"></td>';
			break;
		case 4:
			divHTML += '<tr><td class="Option">' + top.GetIdsLan( "IDS_MENUNAMELINK" ) + ':</td></tr>';
			break;
		case 5:
			divHTML += '<tr><td class="Option">' + top.GetIdsLan( "IDS_MENUNAMEPAG_ESC" ) + ':</td></tr>'
				+ '<tr><td><input type="text" id="texto" name="texto" class="Option" maxlength="64"></td>'
                + '<td><a href="javascript:escanear()" class="Option" style="cursor:pointer" tabIndex="1">' + top.GetIdsLan( "IDS_MENUNESCANEAR" ) + '</a>'
                + '<input type="file" style="visibility:hidden" name="fichero"></td>';
	}

	if ( (PTipo != 3) && (PTipo != 5) ) {
		divHTML += '<tr><td><input type="text" id="texto" name="texto" class="Option" maxlength="32"></td>';
	}

	divHTML += '</tr></form></table>';

	document.getElementById("MenuClf").innerHTML = divHTML;

//	// le damos el foco al campo editable
//	top.setFocus(document.getElementById("texto"));

	if ( ( PTipo == 3 ) || ( PTipo == 5 ) ) {
		document.getElementById("MenuClf").style.display = 'none';

		if (PTipo == 3) {
			examinar();
		}
		else {
			escanear();
		}


		document.getElementById("MenuClf").style.display = 'block';
	}else{
		top.setFocus(document.getElementById("texto"));
	}

	return;
}

function rnmElem()
{
   if( ClaseNumero( window.TreeTypeAux ) == "CL0" )  {
      window.alert( top.GetIdsLan( "IDS_ERRORRENELEM" ) );
   }
   else  {
      var divHTML = "";

	  var nameLong=32;
		if(window.TreeTypeAux==3 || window.TreeTypeAux==5) {
			nameLong=64;
		}


      divHTML =
      '<img src="images/roundcorner7_topleft_blue.gif" style="position:absolute; top:0; left:0">'
      + '<img src="images/roundcorner7_topright_blue.gif" style="position:absolute; top:0; left:145">'
      + '<img src="images/roundcorner7_bottomleft_blue.gif" style="position:absolute; top:55; left:0">'
      + '<img src="images/roundcorner7_bottomright_blue.gif" style="position:absolute; top:55; left:145">'
      + '<div class="divMenu1" style="top: 9; left: 0; width: 152; height: 48; position: absolute; '
      + 'overflow: hidden;">'
      + '</div>'
      + '<div class="divMenu2" style="top: 2; left: 7; width: 138; height: 62; position: absolute; overflow: hidden;">'
      + '</div>'
      + '<table style="position:absolute; top:10; left:10; width:100" border="0" cellspacing="2" cellpadding="0">'
      + '<form id="frmName" name="frmName" style="position:absolute; top:10; left:10;" onsubmit="RenameNode();return false;">'
      + '<tr><td class="Option">' + top.GetIdsLan( "IDS_MENUNUEVONOMBRE" ) + ':</td></tr>'
      + '<tr><td><input type="text" id="texto" name="texto" class="Option" maxlength="' + nameLong + '"></td>'
      + '</tr></form></table>';

      document.getElementById("MenuClf").innerHTML = divHTML;
      window.document.getElementById("texto").value = top.miTrim(top.GetInnerText(GetChildA(ObjId(window.TreeElemAux, ClaseNumero(window.TreeTypeAux)))));
      window.document.getElementById("texto").select();
      window.document.getElementById("texto").focus();
      top.Main.Folder.FolderBar.ActivateSave();
   }
   return;
}

function validarfrmFich( PTipo )
{
   addElem( PTipo );
   HideMenu();
   return;
}

function NoRenombrado()
{
	for( var ii=0; ii<parent.parent.FolderBar.FldRnmArr.length; ii++ )  {
		if( ( parent.parent.FolderBar.FldRnmArr[ii].Id == window.TreeElemAux )
			&& ( parent.parent.FolderBar.FldRnmArr[ii].Clase == window.TreeTypeAux ) )  {
			parent.parent.FolderBar.FldRnmArr[ii].Value  = top.miTrim( window.document.getElementById("texto").value );
			return false;
		}
	}

	return true;
}

function RenameNode()
{
	if( chkNom( ObjId( window.TreeElemAux, ClaseNumero( window.TreeTypeAux ) ).parentNode.parentNode,
               window.document.getElementById("texto").value, 0 , ClaseNumero( window.TreeTypeAux ) ) )  {

      if( window.TreeElemAux.search( 'LI' ) == -1 )  {
         if( NoRenombrado() ){
            var cantidad = parent.parent.FolderBar.FldRnmArr.length;

            parent.parent.FolderBar.FldRnmArr[cantidad] =
               new parent.parent.FolderBar.FldRnm( window.TreeElemAux, window.TreeTypeAux,
                  top.miTrim( window.document.getElementById("texto").value ) );
         }
      }

      top.SetInnerText(GetChildA(ObjId(window.TreeElemAux, ClaseNumero(window.TreeTypeAux))), top.miTrim(window.document.getElementById("texto").value));
   }
   else {
      window.alert( top.GetIdsLan( "IDS_ERRORNOMBRE" ) );
   }

   HideMenu();

   return;
}


function ChkClick()
{
   if(!window.IsClickMenu )
   {
      HideMenu();
   }
   return;
}

// Abre una pagina escaneada (se le pasa directamente una ruta)
function OpenPageScan(PagId)
{
	if( PagId.search( 'LI' )!= -1 ) {
		top.g_PrevPage = top.g_Page;
		PagId = PagId.substring( 2, PagId.length );
		window.open( eval( "parent.parent.FolderBar.document.getElementById('frmStrUpdate').LI" + PagId ).pathFile,
			"FolderFormData","location=no",true);
	}

	return;
}


// Borra todos los campos del formulario
function CleanFldsForm(oForm, oColImages)
{
	var strValue = "";
	var FormData = top.Main.Folder.FolderData.FolderFormData.document.getElementById("FrmData");
	var Fields = top.Main.Folder.FolderData.FolderFormTree.document.getElementById("Fields").value;
	var arrFields = Fields.split(";");

	for (var ii=0; ii < oForm.length; ii++)  {
		if ((oForm[ii].type.toUpperCase() == "TEXT") || (oForm[ii].nodeName.toUpperCase() == "TEXTAREA")){
			// No borramos los campos marcados como persistentes
			if (!top.existInArray(oForm[ii].getAttribute("FldId"), arrFields)){
				oForm[ii].value = "";
			}
			else {
				if (oForm[ii].getAttribute("Sustituto")) {
					for (var jj=0; jj < top.Main.Folder.FolderBar.FldDataArr.length; jj++)	{
						if (top.Main.Folder.FolderBar.FldDataArr[jj].Id == oForm[ii].getAttribute("FldId"))	{
							oForm[ii].value = top.Main.Folder.FolderBar.FldDataArr[jj].valueSust;
							break;
						}
					}
				}
			}

			// ponemos el campo a readonly o como editable
			if (top.existInArray(oForm[ii].name, top.g_ArrFldsReadOnly)){
				oForm[ii].setAttribute("readOnly", true);
				oForm[ii].style.backgroundColor = "#DDDDDD";
				oForm[ii].tabIndex = "-1";
			}
			else {
				oForm[ii].removeAttribute("readOnly");
				oForm[ii].style.backgroundColor = "#FFFFFF";
				oForm[ii].tabIndex = "1";
			}
		}
	}

	try {
		if (oForm.document.getElementById("Interesados") != null)	{
			var FrmInt = oForm.document.getElementById("Interesados");

			if (!top.existInArray(FrmInt.getAttribute("FldId"), arrFields)){
				FrmInt.value = "";
			}
			else {
				top.Main.Folder.FolderData.FolderFormData.cambioValor(FrmInt);
			}

			if (top.existInArray(FrmInt.name, top.g_ArrFldsReadOnly)){
				FrmInt.setAttribute("readOnly",true);
				FrmInt.tabIndex = "-1";
			}
			else {
				FrmInt.setAttribute("readOnly", false);
				FrmInt.tabIndex = "1";
			}

			FrmInt.document.Interesados.LoadFrame();
		}
	}
	catch(e){}

	// Borramos las imagenes (disparos) del formulario
	for (var i = 0; i < oColImages.length; i++) {
		if ((oColImages[i].src.indexOf("buscar2.gif") == -1) && (oColImages[i].src.indexOf("calendarM.gif") == -1) && (oColImages[i].name != "thumbnail") && (!oColImages[i].getAttribute("isObli")) && (oColImages[i].id !="imgValInter"))	{
			oColImages[i].style.display = "none";
		}
		else {
			if ( !top.existInArray(oColImages[i].id, top.g_ArrFldsReadOnly) ){
				oColImages[i].style.display = "block";
			}
		}
	}

	top.Main.Folder.document.getElementById("FolderData").tabIndex = "0";

	if (top.Main.Folder.FolderData.FolderFormData.document.getElementById("AddInfo") != null){
		FormData.AddInfo.style.visibility = "hidden";
	}

	// Ponemos el foco en el primer campo de la pagina que estamos visualizando
	if ( (top.g_FolderView) && (!top.g_FdrReadOnly) ){
		top.Main.Folder.FolderData.FolderFormData.SetFormFocus();
	}

	return;
}

function Maximized(){
	if (g_Maximized) {
        top.Main.Folder.FolderData.document.getElementById('FolderFSet').cols = g_PrevFormTreeWidth.toString()- 26 + "px, *";
        //top.Main.Folder.FolderData.FolderFormTree.document.getElementById("img_menu_on").src = "images/menu_off.gif";
        top.Main.Folder.FolderData.FolderFormTree.document.getElementById("img_menu_on").className = "img_menu_on";
        top.Main.Folder.FolderData.FolderFormTree.document.getElementById("tree").style.display = "block";
    }else{
	g_PrevFormTreeWidth = top.Main.Folder.FolderData.document.getElementById("FolderFormTree").offsetWidth + 26;
	top.Main.Folder.FolderData.FolderFormTree.document.getElementById("tree").style.display = "none";
	//top.Main.Folder.FolderData.FolderFormTree.document.getElementById("img_menu_on").src = "images/menu_on.gif";
	top.Main.Folder.FolderData.FolderFormTree.document.getElementById("img_menu_on").className = "img_menu_off";
	top.Main.Folder.FolderData.document.getElementById('FolderFSet').cols = "26, *";
    }

    g_Maximized = !g_Maximized;
}