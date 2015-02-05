function none() {}

function UIHelper() {
	this.popups = new Array();
	this.showPopup = function(popupID) {
		var popupToShow = this.popups[popupID];
		popupToShow.show();
		//alert(this.popups[popupID].title);
	}
	this.registerPopup = function(popup) {
		this.popups[popup.id] = popup;
	}

	this.getPopup = function(popupID) {
		//alert('popup '+popupID+' - '+this.popups[popupID]);
		return this.popups[popupID];
	}

	this.closePopup = function(popupID) {
		var popupToClose = this.popups[popupID];
		popupToClose.close();
	}
	this.setDragingPopup = function(popupID) {
		this.dragingPopup = popupID!=null?this.popups[popupID]:null;
	}
	this.setResizingPopup = function(popupID) {
		this.resizingPopup = popupID!=null?this.popups[popupID]:null;
	}
}

var GraphicToolkit = new UIHelper();

/*
function attachListenersToPopup(popupID) {
	var popup = GraphicToolkit.popups[popupID];
	// hacer que solo se haga el delay cuando no se ha detectado ninguno de los elementos buscados ... o algo mas inteligente
	// anotar los no encontrados ... si al acabar hay no encontrados se hace un delay y si la siguiente vez coinciden los no
	// encontrados se da por finalizado el proceso
	for(var i=0; i<popup.listeners.length; i++) {
		//eval('popupWin.listener'+this.listeners[i].elementID+'= this.listeners[i]');
		var listener = popup.listeners[i];
		for (var j=0; j< listener.elements.length; j++) {
			//if (popup.getWindow() && popup.getWindow().document.getElementById(listener.elements[j])) {
				//var actionFirer = popup.getWindow().document.getElementById(listener.elements[j]);
				var actionFirer = document.getElementById(listener.elements[j]);
				//popup.getWindow().none = none;
				//actionFirer.href= 'javascript:none()';
				alert("Ataching "+actionFirer.tagName);
				if (actionFirer) {
					alert("dentro "+listener.onActionPerformed);
					actionFirer.onclick = listener.onActionPerformed;
				}
			//} else {
			//	setTimeout("attachListenersToPopup('"+popupID+"')",200);
			//	return;
			//}
		}
	}
}
*/
var POPUP_HALIGN = {'CENTER' : 0, 'LEFT' : 1, 'RIGHT' : 2 };
var POPUP_VALIGN = {'CENTER' : 0, 'TOP' : 1, 'BOTTOM' : 2};

var CONTROL_TYPE = {'LINK' : 0};

function ActionListener(sourceType, id, img, label) {
	this.sourceType = sourceType;
	this.id = id;
	this.label = label;
	this.img = img;

	this.getControlCode = function() {
		var controlCode = "<a class=etiquetaAzul12Normal href=\"javascript:eval('window."+this.callbackFunction+"()')\" id='"+this.id+"'>";
		controlCode += "<img src='"+this.img+"' border='0' alt='Aceptar' title='"+this.label+"' class='imgTextBottom'>";
		controlCode += this.label+"</a>";
		return controlCode;
	}

	this.setActionToPerform = function(callbackFunction) {
		this.callbackFunction = callbackFunction;
	}
	/*this.elements = new Array();
	for (var i=1;i<arguments.length; i++)
		this.elements[i-1] = arguments[i];*/
	/*this.performAction = function(p) {
		alert("performing action "+this.elementID);
		if (this.onActionPerformed)
			this.onActionPerformed();
	}*/
}

function PopupWindow(id, title, url) {
	this.id = id;
	this.title = title;
	this.url = url;
	this.dataCollector = null;
	this.listeners = new Array();
	this.setDataCollector = function (dataCollector) { 
			this.dataCollector = dataCollector; 
	}
	GraphicToolkit.registerPopup(this);

	this.show = function() {
		this.init();
		if (this.popupObj == null)
			this.create();
		this.displayPopupContent();
		//var popupWin = xGetElementById('loadWindowPopup'+this.id).contentWindow;
		//alert(popupWin.listenerBotonAceptar.elementID);
		this.align();
		this.setVisible(true);
		//attachListenersToPopup(this.id);
	}

	this.attachBehaviour = function(listener) {
		this.listeners[this.listeners.length] = listener;
	}
	this.collectPopupData = function() {
		if (this.dataCollector) {
			this.dataCollector.setSourceDocument(this.getWindow().document);
			this.dataCollector.collectData();
		}
	}
	this.close = function() {
		this.setVisible(false);
	}
	this.getWindow = function() {
		var popupWindow = xGetElementById('loadWindowPopup'+this.id);
		return popupWindow?popupWindow.contentWindow:null;
	}

	this.init = function() {
		if (!this.width)
			this.width = 250;//this.width?this.width:xClientWidth();
		if (!this.height)
			this.height = 150;//this.height?this.height:xClientHeight();
		//alert(this.width+'x'+this.height);
	}

	this.setVisible = function(visible) {
		if (visible) {
			xVisibility(this.popupObj, true);
			//xDisplay(this.popupObj, 'block');
		}  else {
			xVisibility(this.popupObj, false);
			//xDisplay(this.popupObj, 'none');
		}
	}
	this.create = function() {
		//var popupContainerCode = "<div id='containerPopup"+this.id+"' style='position:absolute;visibility:hidden;padding:2px;border:2px outset #eeeeee;background:#cccccc;width:"+this.width+"px;height:"+this.height+"px'>";
		//this.popupObj = document.createElement(popupContainerCode);
		var botonCancelar = "<a class=etiquetaAzul12Normal href='javascript:void(0)' id='BotonCancelarA'>";
		botonCancelar += "<img src='/archivo/pages/images/Ok_No.gif' border='0' alt='Aceptar' title='Cancelar' class='imgTextBottom'>";
		botonCancelar += " Cancelar</a>"

		this.popupObj = this.createPopupContainer();
		document.body.insertBefore(this.popupObj,null);
		//popupContainer = xGetElementById(containerId);
		var popupContent = "<div id='titlebarpopup"+this.id+"' style='width:100%;height:12px;padding:2px;background:#0A56B4;'><span style=';color:white;font-weight:bold;font-size:11px;font-family:Verdana,Geneva,sans-serif'>"+this.title+"</span><a href='javascript:GraphicToolkit.closePopup(\""+this.id+"\")'><img src='/archivo/pages/images/closeG.gif' border='0' align='top' style='position:absolute;top:4px;right:4px'></a></div>";
		popupContent += "<div id='clientareapopup"+this.id+"' style='width:100%;height:100%;margin:2px 2px 2px 2px'></div>";
		popupContent += "<div id='statusBarPopup' style='width:100%;bottom:0px;position:absolute'>"; //<table style='float:right;position:relative;right:20px'><tr><td>"+botonAceptar+"</td><td>"+botonCancelar+"</td></tr></table></div>";
		if (this.listeners.length > 0) {
			popupContent += "<table style='float:right;position:relative;right:20px'><tr>";
			for (var i=0; i<this.listeners.length; i++) {
				popupContent += "<td>"+this.listeners[i].getControlCode()+"</td>";
			}
			popupContent +="</tr></table>";
		}
		popupContent += "</div>";
		popupContent += "<div style='position:absolute;height:14px;width:14px;right:1px;bottom:1px'><img src='/archivo/pages/images/resize.gif' id='resizerPopup"+this.id+"' class='imgTextMiddle'></div>";
		this.popupObj.innerHTML = popupContent;
		var popupTitleBar = xGetElementById('titlebarpopup'+this.id);
		popupTitleBar.onmousedown = popupDragStart;
		var popupResizer = xGetElementById('resizerPopup'+this.id);
		popupResizer.onmousedown = popupResizeStart;
		//this.align();
		//xAddEventListener('titlebarpopup'+this.id, 'mousedown', popupDragStart, false);
		//xVisibility(this.popupObj, true);
	}
	this.createPopupContainer = function() {
		var popupContainer = null;
		if (xMoz) {
			popupContainer = document.createElement("div");
			popupContainer.setAttribute('id','titlebarpopup'+this.id);
			popupContainer.setAttribute('style',"z-index:10;position:absolute;visibility:hidden;padding:2px;border:2px outset #eeeeee;background:#cccccc;width:"+this.width+"px;height:"+this.height+"px");			
		} else {
			var popupContainerCode = "<div id='containerPopup"+this.id+"' style='position:absolute;visibility:hidden;padding:2px;border:2px outset #eeeeee;background:#cccccc;width:"+this.width+"px;height:"+this.height+"px'>";
			popupContainer = document.createElement(popupContainerCode);
		}
		alert(popupContainer);
		return popupContainer;
	}
	this.displayPopupContent = function() {
		var popupClientArea = xGetElementById('clientareapopup'+this.id);
		//var popupClientAreaContent = "<iframe marginheight='0' marginwidth='0' frameborder='no' style='width:100%;height:100% onload =\"alert('hola')\"><html><body>"; 
		var popupClientAreaContent = "<iframe id='loadWindowPopup"+this.id+"' src='"+this.url+"' marginheight='0' marginwidth='0' frameborder='no' style='width:98%;height:90%;border:2px inset #cccccc;background:white'></iframe>";
		//popupClientAreaContent += "</body></html></iframe>";
		popupClientArea.innerHTML = popupClientAreaContent;
		 //setTimeout("checkPopupLoaded('"+this.id+"')",200);
		/*loadWindowPopup.open();
		loadWindowPopup.write("<html><body onload=\"alert(document.all['contentPane'].document.body.offsetWidth)\"><iframe id='contentPane' src='"+this.url+"'  marginheight='0' marginwidth='0' frameborder='no' style='width:100%;height:100%'></iframe></body></html>");
		loadWindowPopup.close();*/
		//alert(loadWindowPopup.id+" size: "+loadWindowPopup.contentWindow+"x"+loadWindowPopup.contentWindow.height);
		//loadWindowPopup.onload = popupLoaded;
	}

	this.setAlignment = function (halignment, valignment) {
		this.halignment = halignment;
		this.valignment = valignment;
	}
	this.setPreferredSize = function (width, height) {
		var availableWidth = xClientWidth();
		this.width = availableWidth < width?availableWidth:width;
		var availableHeight = xClientHeight();
		this.height = availableHeight < height?availableHeight:height;
	}
	this.setSize = function (width, height) {
		//alert(width+'x'+height);
		this.setPreferredSize(width, height);
		if (this.popupObj)
			xResizeTo(this.popupObj,width,height);
	}

	this.align = function() {
		var availableWidth = xClientWidth();
		switch (this.halignment) {
			case POPUP_HALIGN.CENTER:
				this.left = (availableWidth - this.width)/2; break;
			case POPUP_HALIGN.LEFT:
				this.left = 0; break;
			case POPUP_HALIGN.RIGHT:
				this.left = (availableWidth - this.width); break;
		}
		var availableHeight = xClientHeight();
		switch (this.valignment) {
			case POPUP_VALIGN.CENTER:
				this.top = (availableHeight - this.height)/2; break;
			case POPUP_VALIGN.TOP:
				this.left = 0; break;
			case POPUP_VALIGN.BOTTOM:
				this.left = (availableHeight - this.height); break;
		}
		xMoveTo(this.popupObj,this.left,this.top);
	}
	this.move = function (dx, dy) {
		xMoveTo(this.popupObj, xLeft(this.popupObj)+dx, xTop(this.popupObj)+dy);
	}
}

var mouseX;
var mouseY;
function popupDragStart(oEvent) {
	//alert("start draging "+popupID);  
	var evt = new xEvent(oEvent);
	xPreventDefault(oEvent);
	mouseX = evt.pageX;
	mouseY = evt.pageY;
	xAddEventListener(document, 'mousemove', dragingPopup, false);
	xAddEventListener(document, 'mouseup', poppupDragEnd, false);
	GraphicToolkit.setDragingPopup(evt.target.id.substring(13));
}

function dragingPopup(oEvent) {
	var evt = new xEvent(oEvent);
	var dragingPopup = GraphicToolkit.dragingPopup;
	if (dragingPopup) {
	  xPreventDefault(oEvent);
	  var dx = evt.pageX - mouseX;
	  var debug = "mouseX: "+mouseX+" - evtx: "+evt.pageX+" - dx: "+dx+" - destino: "+(xLeft(dragingPopup.popupObj) + dx)
	  //alert(debug);
	  mouseX = evt.pageX;
	  var dy = evt.pageY - mouseY;
	  mouseY = evt.pageY;
	  xMoveTo(dragingPopup.popupObj, xLeft(dragingPopup.popupObj) + dx, xTop(dragingPopup.popupObj) + dy);
	  //alert(mouseX);
	  //dragingPopup.move(dx,dy);
	  //var currentWidth = xWidth('arbol');
	  //xWidth('arbol',currentWidth+dx);
	}
}
function poppupDragEnd(oEvent) {
	xPreventDefault(oEvent);
	mouseX = mouseY = 0;
	xRemoveEventListener(document, 'mouseup', poppupDragEnd, false);
	xRemoveEventListener(document, 'mousemove', dragingPopup, false);
	GraphicToolkit.setDragingPopup(null);
}

function popupResizeStart(oEvent) {
	//alert("start resize");
	var evt = new xEvent(oEvent);
	xPreventDefault(oEvent);
	mouseX = evt.pageX;
	mouseY = evt.pageY;
	xAddEventListener(document, 'mousemove', resizingPopup, false);
	xAddEventListener(document, 'mouseup', popupResizeEnd, false);
	GraphicToolkit.setResizingPopup(evt.target.id.substring(12));
}

function resizingPopup(oEvent) {
	var evt = new xEvent(oEvent);
	var resizingPopup = GraphicToolkit.resizingPopup;
	if (resizingPopup) {
	  xPreventDefault(oEvent);
	  var dx = evt.pageX - mouseX;
	  mouseX = evt.pageX;
	  var dy = evt.pageY - mouseY;
	  mouseY = evt.pageY;
	  //alert(mouseX);
	  resizingPopup.setSize(resizingPopup.width+dx,resizingPopup.height+dy);
	  //var currentWidth = xWidth('arbol');
	  //xWidth('arbol',currentWidth+dx);
	}
}

function popupResizeEnd(oEvent) {
	xPreventDefault(oEvent);
	GraphicToolkit.setResizingPopup(null);
	mouseX = mouseY = 0;
	xRemoveEventListener(document, 'mouseup', popupResizeEnd, false);
	xRemoveEventListener(document, 'mousemove', resizingPopup, false);
	//alert("resize end");
}

function DataCollector() {
	this.doc = null;
	this.collectedValues = null;
	this.sourceElements = new Array();
	this.setSourceDocument = function(sourceDoc) {
		this.doc = sourceDoc;
	}

	this.addSourceElement = function (property,elementID) {
		this.sourceElements[property] = elementID;
	}

	this.collectData = function () {
		this.collectedValues = new Array();
		for(var i in this.sourceElements) {
			targetElement = this.doc.getElementById(this.sourceElements[i]);
			this.collectedValues[i] = targetElement?targetElement.innerHTML:null;
		}
		if (this.onDataCollected)
			this.onDataCollected();
	}
	this.getProperty = function (property) {
		return this.collectedValues[property];
	}
}