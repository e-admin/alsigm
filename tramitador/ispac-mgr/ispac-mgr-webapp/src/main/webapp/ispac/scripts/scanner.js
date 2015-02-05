/*******************************************
	Funcionalidad del escáner
********************************************/

var	scan			= null;

function init() {
	scan = document.getElementById("scanner");
	scan.isVisible = false;
	scan.css  = scan.style;
	
	scan.showMessage = function(message) { 
			alert(message);
		};
	scan.show = function () { 
			this.style.visibility = "visible"; 
			this.isVisible = true;
		};
	scan.hide = function () { 
			this.style.visibility = "hidden"; 
			this.isVisible = false; 
		};
	scan.move = function (x, y) { 
			this.style.top  = y; 
			this.style.left = x; 
		};
	scan.top  = function () { 
			return this.css.top 
		};
	scan.left = function () { 
			return this.css.left 
		};
	scan.close = function () {	
			var myapplet = this.getApplet ();
			myapplet.jsRemoveAllFile();
			this.hide();
		};
	scan.isReady = function () {	
			var myapplet = this.getApplet ();
			return myapplet.jsIsReady();
		};
	scan.scanDocument = function () {	
			this.move(80, 100);
			this.show();
		
			var myapplet = this.getApplet();
			myapplet.jsStartScanner();
			
			if (displayFileQueue) {
				displayFileQueue();
			}
		};
	scan.configScanner = function () {	
			var myapplet = this.getApplet();
			myapplet.jsConfigureScanner();
			
			if (displayFileQueue) {
				displayFileQueue();
			}
		};
	scan.uploadFiles = function () {
			var myapplet = this.getApplet ();
			return myapplet.jsUploadFiles();
		};
	scan.removeFile = function (pos) {
			var myapplet = this.getApplet ();
			myapplet.jsRemoveFile(pos);
		};
	scan.getApplet = function () {
			try {
			    document.invesdoc_ntsc.name = document.invesdoc_ntsc.name;
				return document.invesdoc_ntsc;			
			} catch (exception) {			
				return document.invesdoc_ms;			
			}
		};
	scan.getAppletName = function () {
			try {
			    document.invesdoc_ntsc.name = document.invesdoc_ntsc.name;
		    	return document.invesdoc_ntsc;			
			} catch (exception) {			
		    	return document.invesdoc_ms;			
			}
		};
}
