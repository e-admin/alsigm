var lastSelected = "";

function Expand(obj)
{
	var tabBooks = document.getElementById("tabBooks");
	var fila = obj.parentNode;
	var Found = false;
	var Expand = false;
	var ExpandAll = false;
	var disabled = obj.getAttribute("disabled");

	if ((disabled != null) && (disabled.toString() == "true")){return;}

	while (!Found){
		if (fila.nodeName == "TR"){
			break;
		}

		fila = fila.parentNode;
	}

	for (var i = 0; i < tabBooks.rows.length; i++) {
		if (fila == tabBooks.rows[i]) {
			Found = true;
		}
		else if (Found && (tabBooks.rows[i].id == "title")) {
			if (fila.id == "books"){
				if ((tabBooks.rows[i].style.display == "") || (tabBooks.rows[i].style.display == top.StyleDisplayBlock())) {
					tabBooks.rows[i].style.display = "none";
					ExpandAll = false;
				}
				else {
					tabBooks.rows[i].style.display = top.StyleDisplayBlock();
					for (j = 0; j < tabBooks.rows[i].childNodes.length; j++) {
						var child = tabBooks.rows[i].childNodes[j];

						for (k = 0; k < child.childNodes.length; k++) {
							var nodochild = child.childNodes[k];

							for (l = 0; l < nodochild.childNodes.length; l++) {
								var nododoschild = nodochild.childNodes[l];

								if (nododoschild.nodeName == "IMG"){
									if ((nododoschild.src.indexOf("menu_right.gif") != -1) || (nododoschild.src.indexOf("menu_down.gif") != -1)){
										nododoschild.src = "./images/menu_right.gif";
									}
								}
							}
						}
					}
					ExpandAll = true;

				}
			}else{
				break;
			}
		}
		else if (Found && (tabBooks.rows[i].id == "bookname")) {
			if (fila.id == "books"){
					tabBooks.rows[i].style.display = "none";
					Expand = false;
			}else{
				if (((tabBooks.rows[i].style.display == "") || (tabBooks.rows[i].style.display == top.StyleDisplayBlock()))) {
					tabBooks.rows[i].style.display = "none";
					Expand = false;
				}
				else {
					tabBooks.rows[i].style.display = top.StyleDisplayBlock();
					Expand = true;
				}
			}
		}
	}

	for (i = 0; i < obj.parentNode.childNodes.length; i++) {
		var nodo = obj.parentNode.childNodes[i];

		if (nodo.nodeName == "IMG"){
			if ((nodo.src.indexOf("menu_right.gif") != -1) || (nodo.src.indexOf("menu_down.gif") != -1)){
				if (Expand || ExpandAll) {
					nodo.src = "./images/menu_down.gif";
				}
				else {
					nodo.src = "./images/menu_right.gif";
				}
			}
		}
	}
}

function OverArchive(obj)
{
	if (obj){
		var disabled = obj.getAttribute("disabled");

		if (obj && (disabled != null) && (disabled.toString() != "true"))  {
			obj.style.textDecoration = "underline";
		}
	}
}


   function OutArchive(obj)
   {
      if (obj) {
		obj.style.textDecoration = "none";
	  }
   }


   function SetSelected(item)
   {
      if (item != null){
         if (lastSelected != ""){
		UnChangeBookImg(document.getElementById(lastSelected));
			document.getElementById(lastSelected).style.color = top.g_color2;
			document.getElementById(lastSelected).style.fontWeight = "normal";
         }

         item.style.fontWeight = "bold";
         ChangeBookImg(item);
         lastSelected = item.id;
      }
   }

   function ChangeBookImg(obj)
   {
		var fila = obj.parentNode;

		for (i = 0; i < fila.childNodes.length; i++) {
			var nodo = fila.childNodes[i];
			if (nodo == obj){
				if ( fila.childNodes[i-1].src.indexOf(getNameImgBookOpen(fila.childNodes[i-1].bookType)) != -1 ){
					fila.childNodes[i-1].src = getPathImgBookSelectedOpen(fila.childNodes[i-1].bookType);
				}else{
					if( fila.childNodes[i-1].src.indexOf(getNameImgBookClose(fila.childNodes[i-1].bookType)) != -1 ){
						fila.childNodes[i-1].src = getPathImgBookSelectedClose(fila.childNodes[i-1].bookType);
					}
				}
			}
		}
   }

   function UnChangeBookImg(obj)
   {
		var fila = obj.parentNode;

		for (i = 0; i < fila.childNodes.length; i++) {
			var nodo = fila.childNodes[i];
			if (nodo == obj){
				if ( fila.childNodes[i-1].src.indexOf(getNameImgBookSelected(fila.childNodes[i-1].bookType)) != -1 ){
					fila.childNodes[i-1].src = getPathImgBookOpen(fila.childNodes[i-1].bookType);
				}else{
					if( fila.childNodes[i-1].src.indexOf(getNameImgBookCloseSelected(fila.childNodes[i-1].bookType)) != -1 ){
						fila.childNodes[i-1].src = getPathImgBookClose(fila.childNodes[i-1].bookType);
					}
				}
			}
		}
   }

/**
 * Funcion que retorna la imagen(icono) de libro abierto correspondiente al tipo de libro
 * @param bookType - (1: Entrada, 2: Salida)
 * @returns {String}
 */
function getNameImgBookOpen(bookType){
	if (bookType == '1'){
		return "book-open-in.gif";
	}else{
		return "book-open-out.gif";
	}
}

/**
 * Funcion que retorna la imagen(icono) de libro cerrado correspondiente al tipo de libro
 * @param bookType - (1: Entrada, 2: Salida)
 * @returns {String}
 */
function getNameImgBookClose(bookType){
	if(bookType == '1'){
		return "book-close-in.gif";
	}else{
		return "book-close-out.gif";
	}
}

/**
 * Funcion que retorna la imagen(icono) de libro abierto seleccionado correspondiente al tipo de libro
 * @param bookType - (1: Entrada, 2: Salida)
 * @returns {String}
 */
function getNameImgBookSelected(bookType){
	if(bookType == '1'){
		return "book-selected-in.gif";
	}else{
		return "book-selected-out.gif";
	}
}

/**
 * Funcion que retorna la imagen(icono) de libro cerrado seleccionado correspondiente al tipo de libro
 * @param bookType - (1: Entrada, 2: Salida)
 * @returns {String}
 */
function getNameImgBookCloseSelected(bookType){
	if(bookType == '1'){
		return "book-selectedClose-in.gif";
	}else{
		return "book-selectedClose-out.gif";
	}
}

/**
 * Funcion que retorna el path de la imagen(icono) de libro abierto seleccionado correspondiente al tipo de libro
 * @param bookType - (1: Entrada, 2: Salida)
 * @returns {String}
 */
function getPathImgBookSelectedOpen(bookType){
	if(bookType == '1'){
		return "./images/book-selected-in.gif";
	}else{
		return "./images/book-selected-out.gif";
	}
}

/**
 * Funcion que retorna el path de la imagen(icono) de libro cerrado seleccionado correspondiente al tipo de libro
 * @param bookType - (1: Entrada, 2: Salida)
 * @returns {String}
 */
function getPathImgBookSelectedClose(bookType){
	if(bookType == '1'){
		return "./images/book-selectedClose-in.gif";
	}else{
		return "./images/book-selectedClose-out.gif";
	}
}

/**
 * Funcion que retorna el PATH de la imagen(icono) de libro abierto correspondiente al tipo de libro
 * @param bookType - (1: Entrada, 2: Salida)
 * @returns {String}
 */
function getPathImgBookOpen(bookType){
	if(bookType == '1'){
		return "./images/book-open-in.gif";
	}else{
		return "./images/book-open-out.gif";
	}
}

/**
 * Funcion que retorna la PATH de la imagen(icono) de libro cerrado correspondiente al tipo de libro
 * @param bookType - (1: Entrada, 2: Salida)
 * @returns {String}
 */
function getPathImgBookClose(bookType){
	if(bookType == '1'){
		return "./images/book-close-in.gif";
	}else{
		return "./images/book-close-out.gif";
	}
}

function OnLESTLoad(OtherOffice, SessionId)
{
	try {
		top.g_SessionPId = SessionId;

		top.Main.Workspace.Query.document.body.innerHTML = "";
		top.Main.Workspace.document.getElementById("seccion_tab").style.display="none";
		top.Main.Workspace.document.getElementById("seccion_tab_advanced").style.display="none";
		top.Main.Workspace.Reset();

		if (parseInt(OtherOffice, 10) > 0){
			top.Main.Workspace.document.getElementById("ChangeOfficeBtn").style.display = "block";
		}

		top.g_TreeFunc = true;
	}
	catch(e){}
}


function GetChildUL(LINode)
{
	for ( var ii = 0; ii < LINode.childNodes.length; ii++ ){
		if ( LINode.childNodes[ii].nodeName == "UL" ) {
			return( LINode.childNodes[ii] );
        }
    }

    return false;
}


function DeleteUL(Node)
{
	var OHijo = GetChildUL(Node);

    if (OHijo){
		var NoLIElem = true;

        for (var i = 0; i < OHijo.childNodes.length; i++) {
			if (OHijo.childNodes[i].nodeName == "LI"){
				NoLIElem = false;
				break;
			}
        }

        if (NoLIElem){
			Node.removeChild(OHijo);
            Node.childNodes[0].src = "images/datminus.gif";

            return;
        }
    }

    for (var ii = 0; ii < Node.childNodes.length; ii++){
		DeleteUL(Node.childNodes[ii]);
    }

    return;
}


function OnKeyDownExpand(aEvent, obj)
{
	if (top.GetKeyCode(aEvent) == 13) {
		Expand(obj);
	}
}


function SetIdentification(User, Username, OfficeCode, Officename)
{
	var userText;

	if ((Username == "") || (Username == User)) {
		userText = User;
	}
	else {
		userText = Username + " (" + User + ")";
	}

	with (parent.parent.parent.Title.document) {
		if (getElementById("appHeaderLogin") != null){
			getElementById("appHeaderLogin").style.display = "none";
		}

		getElementById("appHeader").style.visibility = "visible";

		if (getElementById("usuario") != null){
			getElementById("usuario").style.display="block";
		}

		getElementById("imgPoint").style.visibility = "visible";
		getElementById("textUser").innerHTML = userText;
		getElementById("textOffice").innerHTML = OfficeCode + " - " + Officename;
	}
}
