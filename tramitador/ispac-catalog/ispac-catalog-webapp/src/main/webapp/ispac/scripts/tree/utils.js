function arrangeWorkSpace(idArbol, idLayout, idBaseFrm)
{
	var workspaceHeight = Math.max(330,(xClientHeight()-80));
	xHeight('arbol', workspaceHeight);
	xHeight('layout', workspaceHeight);
	xHeight('basefrm', workspaceHeight + 22);
}

function arrangeInternalWorkSpace(id)
{
	var area = parent.document.getElementById(id);
	if (area) {
		area.style.width='100%';
	}
}

function showHideDivSimple(NDiv) 
{
  var divN="div"+NDiv;
  
  if ( (document.getElementById(divN).style.display == 'none') 
  		|| (document.getElementById(divN).style.display == '') )
    document.getElementById(divN).style.display = 'block';
  else
    document.getElementById(divN).style.display = 'none';
}

function joinArray(array, token) {
	var result = "";
	for (var i in array) {
		//alert(i);
		if (result.length == 0) 
			result += array[i];
		else
			result += token + array[i] ;
	}
	//alert(result);
	return result
}

function getUniqueId()
{
    return Math.floor(Math.random() * new Date().getTime());
}

function showSelects()
{
    var selectElements =document.getElementsByTagName("SELECT");
    for (i=0; i<selectElements.length; i++)
        xShow(selectElements[i]);
}

function hideSelects()
{
    var selectElements =document.getElementsByTagName("SELECT");
    for (i=0; i<selectElements.length; i++)
        xHide(selectElements[i]);
}

/***************************************************************/
/*******	Gestion de cookies		****************************/

function setCookie(nombre, valor) {
  document.cookie = nombre + "=" + escape(valor);
}
		
function getCookie(nombre) {
  var buscamos = nombre + "=";
  if (document.cookie.length > 0) {
    i = document.cookie.indexOf(buscamos);
    if (i != -1) {
      i += buscamos.length;
      j = document.cookie.indexOf(";", i);
      if (j == -1)
		j = document.cookie.length;
      return unescape(document.cookie.substring(i,j));
    }
  }
  return null;
}

function removeCookie(nombre) {
	var exp = new Date();  
	exp.setTime (exp.getTime() - 1);  
	var valor = getCookie (nombre)
	document.cookie = nombre + "=" + valor + "; expires=" + exp.toGMTString();
}

/***************************************************************/


/***************************************************************/
/*******	Contenido dividido en pestañas		****************/

// elem debe ser el id del div que contiene lo que 
// se debe mostrar en la pestaña
function Tab(name, elem) {
	this.name = name;
	this.tabContentElem = elem;

	this.setVisible = function(visible) {
		var tabObj = document.getElementById(this.tabContentElem);
		if (tabObj)
			tabObj.style.display = visible?'block':'none';
		tabObj = document.getElementById('p'+this.name);
		if (tabObj != null)
			tabObj.className= visible?'tabActual':'tabSel';
		tabObj = document.getElementById('t'+this.name);
		if (tabObj != null)
			tabObj.className= visible?'textoPestana':'textoPestanaSel';
	}
}


function TabPanel(name) {
	this.name = name;
	this.tabs = new Array();
	this.selectedTab = null;
	this.addTab = function (tab) {
		this.tabs[tab.name] = tab;
	}
	this.saveSelectedTab = function(tabName) {
		setCookie(this.name, tabName);	
	}
	this.showTab = function(tabName) {
		if (this.selectedTab && tabName == this.selectedTab.name) return;
		var tab = this.tabs[tabName];
		if (tab) {
			var tabContentObj = document.getElementById(tab.tabContentElem);
			if (tabContentObj)
				for (var aTab in this.tabs) {
					var isVisible = (aTab == tabName)?true:false;	
					this.tabs[aTab].setVisible(isVisible);
					if (isVisible) this.saveSelectedTab(tabName);
				}
			this.selectedTab = tab;
		}
	}
	this.getSelectedTab = function () {
		return this.selectedTab.name;
	}
}
/***************************************************************/


function switchDivVisibility(NDiv) {
	var divN="div"+NDiv;
	var imgN="img"+NDiv;
	var divObj = document.getElementById(divN);
	if (divObj != null) {
		var isOpen = false;
		var openAttribute = divObj.getAttribute("isOpen");
		if (openAttribute && openAttribute == "true")
			isOpen = true;
		isOpen = !isOpen;
		divObj.style.display = isOpen?'block':'none';
		var imgObj = document.getElementById(imgN);
		if (imgObj != null)
			imgObj.src = imgObj.src.substring(0,imgObj.src.lastIndexOf("/")) + (isOpen?'/up.gif':'/down.gif');
		divObj.setAttribute("isOpen",""+isOpen);
	}
}

/*******************************************************/
/*******	Activar/Desactivar checkboxes	************/

function activarCheckboxSet(checkboxSet) 
{
	if (!checkboxSet.length )
		checkboxSet.disabled = false;
	else
		for (var i=0;i<checkboxSet.length;i++)
			checkboxSet[i].disabled = false;
}

function desactivarCheckboxSet(checkboxSet) 
{
	if (!checkboxSet.length)
		checkboxSet.disabled = true;
	else
		for (var i=0;i<checkboxSet.length;i++)
			checkboxSet[i].disabled = true;
}

/***************************************************************/
/*******	Seleccionar/Deseleccionar checkboxes	************/

function seleccionarCheckboxSet(checkboxSet) 
{
	if (!checkboxSet.length )
		checkboxSet.checked = true;
	else
		for (var i=0;i<checkboxSet.length;i++)
			checkboxSet[i].checked = true;
}

function deseleccionarCheckboxSet(checkboxSet) 
{
	if (!checkboxSet.length)
		checkboxSet.checked = false;
	else
		for (var i=0;i<checkboxSet.length;i++)
			checkboxSet[i].checked = false;
}

/***************************************************************/
/*******	Comprobación checkbox/radio seleccionado	********/

function elementSelected(buttonSet) {
	if (!buttonSet.length)
		return buttonSet.checked;
	else {
		for(var i=0;i<buttonSet.length;i++)
			if (buttonSet[i].checked) return true;
		return false;
	}
}

function numElementsSelected(buttonSet) {
	if (!buttonSet.length)
		return buttonSet.checked ? 1 : 0;
	else {
		var numSelected = 0;
		for(var i=0;i<buttonSet.length;i++)
			if (buttonSet[i].checked) numSelected ++;
		return numSelected;
	}
}

function valueElementsSelected(buttonSet) {
	if (!buttonSet.length)
		return buttonSet.checked ? buttonSet.value : null;
	else {
		var numSelected = 0;
		for(var i=0;i<buttonSet.length;i++)
			if (buttonSet[i].checked) buttonSet[i].value;
	}
}

/***************************************************************/

/***************************************************************/
/*******	Mostrar capa de trabajo en progreso		************/

function showWorkingDiv(title, content) {

	/* Fondo de pantalla */
	var bgwip = parent.document.getElementById('bg_work_in_progress');
	if (bgwip) {
		xShow(bgwip);
	}
	
	/* Pantalla 'modal' */
	var wip = parent.document.getElementById('work_in_progress');
	if (wip) {
		var html = "";

		if (title) {
			html += "<div class='block_title'>"
				+ title + "</div>";
		}
		
		if (content) {
			html += "<div class='block_content'>"
				+ content + "</div>";
		}

		if (html.length > 0) {
			var clientWidth = xClientWidth();
			var clientHeight = xClientHeight();
			wip.innerHTML = html;
			
			xHide(wip);
			xDisplay(wip, 'block');
			var top = (clientHeight - xHeight(wip))/2;
			var left = (clientWidth - xWidth(wip))/2;
			xMoveTo(wip, left, top);
			var selectElements =document.getElementsByTagName("SELECT");
			for (i=0; i<selectElements.length; i++)
					selectElements[i].style.visibility = "hidden";
			xShow(wip);
		}
	}
}

function hideWorkingDiv() {

	/* Fondo de pantalla */
	var bgwip = parent.document.getElementById('bg_work_in_progress');
	if (bgwip) {
		xHide(bgwip);
	}
	
	/* Pantalla 'modal' */
	var wip = parent.document.getElementById('work_in_progress');
	if (wip) {
		xHide(wip);
	}
}

/***************************************************************/


/***************************************************************/
/******* Comprobar cambios en las fechas	************/

function checkFechaComp(form)
{
	if (form.fechaComp.value == "[..]"
	        || form.fechaComp.value == "QCN"
	        || form.fechaComp.value == "EX" )
	{
		document.getElementById("idFecha").style.display = 'none';
		document.getElementById("idRangoFechas").style.display = 'block';

		checkFechaFormato(form.fechaIniFormato, "idFechaIni");
		checkFechaFormato(form.fechaFinFormato, "idFechaFin");
	}
	else
	{
		document.getElementById("idRangoFechas").style.display = 'none';
		document.getElementById("idFecha").style.display = 'block';

		checkFechaFormato(form.fechaFormato, "idFecha");
	}
}

function checkFechaFormato(formato, prefix)
{
	var campoA = document.getElementById(prefix + "AValor");
	var campoM = document.getElementById(prefix + "MValor");
	var campoD = document.getElementById(prefix + "DValor");
	var campoS = document.getElementById(prefix + "SValor");

	if (formato.value == 'AAAA')
	{
	    if (campoA) campoA.style.display = 'block';
		if (campoM) campoM.style.display = 'none';
		if (campoD) campoD.style.display = 'none';
		if (campoS) campoS.style.display = 'none';
	}
	else if (formato.value == 'MMAAAA')
	{
		if (campoA) campoA.style.display = 'block';
		if (campoM) campoM.style.display = 'block';
		if (campoD) campoD.style.display = 'none';
		if (campoS) campoS.style.display = 'none';
	}
	else if (formato.value == 'DDMMAAAA')
	{
		if (campoA) campoA.style.display = 'block';
		if (campoM) campoM.style.display = 'block';
		if (campoD) campoD.style.display = 'block';
		if (campoS) campoS.style.display = 'none';
    }
	else /*if (formato.value == 'S')*/
	{
		if (campoA) campoA.style.display = 'none';
		if (campoM) campoM.style.display = 'none';
		if (campoD) campoD.style.display = 'none';
		if (campoS) campoS.style.display = 'block';
	}
}
/***************************************************************/

function popup(url, name, width, height)
{
	if (width == undefined) width = 700;
	if (height == undefined) height = 400;
		
	var left = (screen.width-width)/2;
	if (left < 0) left = 0;
	
	var top = (screen.height-height)/2;
	if (top < 0) top = 0;

	return window.open(url, name, 
	    "height=" + height 
	    + ",width=" + width
	    + ",top=" + top 
	    + ",left=" + left 
	    + ",location=no,scrollbars=yes,menubars=no,toolbars=no,resizable=yes,status=yes");
}

function removeAllOptions(options)
{
    while (options.length > 0)
        options.remove(options.length-1);
    
}

function popup(url, width, height)
{
	if (!width) {
		width = self.screen.availWidth - 10;
	}
	
	if (!height) {
		height = self.screen.availHeight - 50;
	}
	
	window.open(url, "_blank", "status=yes,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,top=0,left=0,width="+width+",height="+height);
}