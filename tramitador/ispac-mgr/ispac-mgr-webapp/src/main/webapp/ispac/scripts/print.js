/*
 * Funciones para la impresión de documentos.
 * Solo para IE.
 * 
 */

/**
 * Imprime un documento con MS Word
 * @param {} url
 */
function printWordDocument(url) {
	if (window.ActiveXObject) {
		var application = null;
		try {
			application = new ActiveXObject('Word.Application');
			var openDocument = application.Documents.Open('"' + url + '"', false, true);
			openDocument.PrintOut(false);
			openDocument.Close(0);
		} finally {
			if (application != null) { 
				application.Quit(); 
			}
		}
	}
}

/**
 * Imprime un documento con MS Excel
 * @param {} url
 */
function printExcelDocument(url) {
	if (window.ActiveXObject) {
		var application = null;
		try {
			application = new ActiveXObject('Excel.Application');
			var openDocument = application.Workbooks.Open('"' + url + '"', false, true);
			openDocument.PrintOut(false);
			openDocument.Close(0);
		} finally {
			if (application != null) { 
				application.Quit(); 
			}
		}
	}
}

/**
 * Imprime un documento con MS PowerPoint
 * @param {} url
 */
function printPowerPointDocument(url) {
	if (window.ActiveXObject) {
		var application = null;
		try {
			application = new ActiveXObject('PowerPoint.Application');
			var openDocument = application.Presentations.Open('"' + url + '"', false, true);
			openDocument.PrintOut(false);
			openDocument.Close(0);
		} finally {
			if (application != null) { 
				application.Quit(); 
			}
		}
	}
}

/**
 * Imprime un documento con Internet Explorer
 * @param {} url
 */
function printIEDocument(url) {
	if (window.ActiveXObject) {
		var application = null;
		try {
			
			var READYSTATE_COMPLETE 			= 4; // ready
			
			//OLECMDEXECOPT
			var OLECMDEXECOPT_DODEFAULT			= 0;
			var OLECMDEXECOPT_PROMPTUSER		= 1; // prompt user to do it
			var OLECMDEXECOPT_DONTPROMPTUSER	= 2; // just do it
			var OLECMDEXECOPT_SHOWHELP			= 3; 

			
			var OLECMDID_PRINT 					= 6; // print page
			var OLECMDID_PRINTPREVIEW 			= 7; // print preview
			
			var OLECMDF_SUPPORTED				= 1; //Command is supported by this object.
			var OLECMDF_ENABLED					= 2; //Command is available and enabled.
 
			var PRINT_DONTBOTHERUSER			= 1;
			var PRINT_WAITFORCOMPLETION 		= 2;
			
			application = new ActiveXObject('InternetExplorer.Application');
			application.navigate(url);
			
			// Esperar hasta que el documento esté cargado.
			while (application.Busy || (application.ReadyState != READYSTATE_COMPLETE));
			
			// Esperando a que esté listo para imprimir
			//while (application.QueryStatusWB(OLECMDID_PRINT) != (OLECMDF_SUPPORTED + OLECMDF_ENABLED));
			
			// Imprimir la página
			//application.ExecWB(OLECMDID_PRINT, OLECMDEXECOPT_DONTPROMPTUSER, PRINT_WAITFORCOMPLETION|PRINT_DONTBOTHERUSER, 0);
			application.ExecWB(OLECMDID_PRINT, OLECMDEXECOPT_DONTPROMPTUSER, PRINT_WAITFORCOMPLETION, 0);
			
			waitFor("false", 5000);

		} finally {
			if (application != null) { 
				application.Quit(); 
			}
		}
	}
}

function printPDFDocument(url) {
	if (window.ActiveXObject) {
		var application = null;
		
		try {
			// AcroPDF.PDF is used by version 7 and later
			application = new ActiveXObject('AcroPDF.PDF');
		} catch (e) {}

		if (!application) {
			try {
				// PDF.PdfCtrl is used by version 6 and earlier
				application = new ActiveXObject('PDF.PdfCtrl');
			} catch (e) {}
		}

		if (application) {
			
			try {
				//application.src = url;
				application.LoadFile(url);
				application.Print();

				alert("Ok");
				
			} finally {
				//application.Quit(); 
			}
		}
	}
}

/**
 * Imprimer un documento con la herramienta asociada.
 * @param {} url
 * @param {} mimetype
 * @param {} id
 * @param {} callbackFunction
 */
function printDocument(url, mimetype, id, callbackFunction) {
	try {
	
		if (mimetype == 'application/msword') {
			printWordDocument(url);					
		} else if (mimetype == 'application/vnd.ms-excel') {
			printExcelDocument(url);					
		} else if (mimetype == 'application/vnd.ms-powerpoint') {
			printPowerPointDocument(url);					
		//} else if (mimetype == 'application/pdf') {
			//printPDFDocument(url);					
		} else {
			printIEDocument(url);
		}

		if (callbackFunction) {
			eval(callbackFunction + "(true, id)");
		}

	} catch (e) {
		if (callbackFunction) {
			eval(callbackFunction + "(false, id, e)");
		} else {
			alert(e.name + ": " + e.message);
		}
	}
}
