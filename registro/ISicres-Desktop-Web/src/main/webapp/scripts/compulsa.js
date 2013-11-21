/*
 * Clase javascript encargada de realizar las operaciones relacionadas con el proceso de compulsa
 *
 *
 */
var CompulsarClass= function (appletScan, appletConverter, appletFirma,appletSendFiles,idLibro,idRegistro,sessionPId) {


    //applet que se encarca de escanear
    this.appletScan=appletScan;
    //applet de firma
    this.appletFirma=appletFirma;

    //applet de conversion de formatos a pdf
    this.appletConverter=appletConverter;

    this.appletSendFiles=appletSendFiles;

    // id de registro
    this.idRegistro=idRegistro;
    //id de libro
    this.idLibro=idLibro;
    //id de session de usurio isicres
    this.sessionPId=sessionPId;

    //formato xades firma
    this.firma;
    //certificado usado en la firma, parte publica
    this.certificate;

    //usuario del certificado con el que se esta firmando
    this.user;


    //fichero escaneado a compulsar
    this.fileScan;
    // hash del fichero escaneado
    this.fileScanHash;

    //fichero xades del
    this.fileFirma;
    // hash del fichero xades
    this.fileFirmaHash;

    this.urlToUpload="compulsa";

    /*
     * metodo para lanzar el proceso de escaneo
     *
     */
   this.escanear= function(){
	this.appletScan.startScanApplet(true, true, true);
    }

   /*
    *Metodo encargado de realizar el proceso de compulsa
    * Recoge los ficheros obtenidos del applet de escaneo
    *
    */
   this.compulsarFicheros=function(filesScan){

	   //bloqueamos las pantallas del navegador
	    this.blockUI();

	    //transformamos el array de java.lang.String a un array de javascript
	    //esto es debido a que algunos navegadores no saben interpretar arrays de java.lang.String
	    var stringArray=this.appletScan.toJavascriptString(filesScan);
	    var arrayFromJava = stringArray.split("|");
	    this.fileScan=arrayFromJava[0];



	    //generamos el fichero pdf
	    this.fileScan=generatePdfFromFile(this.fileScan);
	    if(this.fileScan){
		//firmar
		if(this.firmarFileScan()){
			//subirlo al servidor
			this.uploadFiles();
		}
	    }

	   //destruimos applets
	   this.destroy();

	    //desbloqueo de la pantalla
	    this.unblockUI();

	    //actualizacion del arbol de anexos
	    top.Main.Folder.FolderData.FolderFormTree.ReLoad();
    }

    /*
     * metodo privado que se encarga de coger el path del fichero escaneado y convertirlo a pdf en caso de se trate de un bmp o un tiff soportado
     *
     */
    function generatePdfFromFile(filePath) {
		var result=false;
		var badFormat=false;
		var ext = filePath.substring(filePath.indexOf(".") + 1).toUpperCase();

		try{
			if ((ext == "TIF") || (ext == "TIFF")){
				result = this.appletConverter.Tiff2Pdf(filePath);
			}else if (ext == "BMP"){
				result = this.appletConverter.Bmp2Pdf(filePath);
			}else if (ext == "PDF"){
				result = filePath;
			} else {
				//hay que usar top, para que se utilize la codificación correcta en los caracteres.
				alert(top.GetIdsLan("IDS_MSG_INCORRECT_FORMAT"));
				badFormat=true;
			}

			if(!result && !badFormat){
				alert(top.GetIdsLan("IDS_ERROR_CONVERSION_PDF"));
			}
		}catch(e){
			alert(e.description);
			result=false;
		}
		return result;

    }

    /*
     * metodo encargado de realizar la firma del fichero pdf
     *
     */
     this.firmarFileScan= function (){

				//Firma
				var resultOk = false;

				try{
					//asignamos nombre del fichero donde se dejara la firma
					this.fileFirma= this.fileScan.substring(0, this.fileScan.indexOf(".") + 1) + "xsig";
					if(this.appletFirma&& (this.fileScan != "")){

						//variable que indica si en caso de error se repite el proceso
						var repetirProcesoEnCasoError = false;
						do{
							// Inicializamos la configuración para asegurar que no hay preestablecido
							// ningún valor de operaciones anteriores
							this.appletFirma.initialize();

							// Configuramos todos los parámetros del cliente de firma.
							this.appletFirma.setFileuri(this.fileScan);
							this.appletFirma.setSignatureFormat('XADES');
							this.appletFirma.setSignatureMode('EXPLICIT');
							this.appletFirma.setOutFilePath(this.fileFirma);

							// Ejecutamos la operación de firma
							this.appletFirma.sign();

							//Si no hay error guardamos los datos de la firma.
							if(!this.appletFirma.isError()){

								//se guarda el fichero de firma
								this.appletFirma.saveSignToFile();


								this.firma = this.appletFirma.getSignatureText(); //Firma
								this.certificate = this.appletFirma.getSignCertificate().toString(); //Certificado
								this.user = this.appletFirma.getSignCertificate().getSubjectDN().getName(); //Nombre del firmante
								this.fileScanHash = this.appletFirma.getFileHashBase64Encoded(false); //Has del fichero firmado.



								//calculamos la hash del fichero firma
								this.appletFirma.initialize();
								this.appletFirma.setFileuri(this.fileFirma);
								this.fileFirmaHash= this.appletFirma.getFileHashBase64Encoded(false); //Has del fichero xades

								//**********************************************************************************
								resultOk = true;

								//todo el proceso finalizo correctamente por tanto salimos del bucle
								repetirProcesoEnCasoError=false;

							} else {
								//se ha producido error, preguntamos al usuario si desea reitentar la operacion
								repetirProcesoEnCasoError = confirm(GetIdsLan("IDS_QRYREITENTARCOMPULSA"));
								//si no se desea continuar
								if(!repetirProcesoEnCasoError){
									//Mostramos por pantalla el error producido al compulsar y salimos del bucle
									alert(GetIdsLan("IDS_ERROR_FIRMA") + "\n\n" + this.appletFirma.getErrorMessage());
								}
							}
						//comprobamos si se repite el proceso si se ha producido algún error al compulsar y el usuario lo indica
						} while(repetirProcesoEnCasoError == true);

					} else {

						alert(GetIdsLan("IDS_ERROR_CLIENTE_FIRMA"));
					}
				}catch (err) {

					alert(GetIdsLan("IDS_ERROR_CLIENTE_FIRMA"));

				}
				//this.debugValues();
				return resultOk;
			}


    /*
     * Metodo encargado de subir el fichero pdf, firma xades y los parametros necesarios hacia el servidor
     * con los datos necesarios para realizar el proceso de compulsa en el servidor
     *
     */
    this.uploadFiles=function(){
	try{
		    var paramsCompulsa= new Array();
		    paramsCompulsa["idRegistro"]=this.idRegistro;
		    paramsCompulsa["idLibro"]=this.idLibro;
		    paramsCompulsa["sessionPId"]=this.sessionPId;
		    paramsCompulsa["firma"]=this.firma;
		    paramsCompulsa["certificate"]=this.certificate;
		    paramsCompulsa["user"]=this.user;


		    paramsCompulsa["fileScanHash"]=this.fileScanHash;
		    paramsCompulsa["fileFirmaHash"]=this.fileFirmaHash;

		    var fileParamsCompulsa=new Array();
		    fileParamsCompulsa["fileScan"]=this.fileScan;
		    fileParamsCompulsa["fileFirma"]=this.fileFirma;


		    this.appletSendFiles.configure(this.urlToUpload, "30000",true);

		    this.appletSendFiles.addParam("idRegistro",this.idRegistro);
		    this.appletSendFiles.addParam("idLibro",this.idLibro);
		    this.appletSendFiles.addParam("sessionPId",this.sessionPId);
		    this.appletSendFiles.addParam("firma",this.firma);
		    this.appletSendFiles.addParam("certificate",this.certificate);
		    this.appletSendFiles.addParam("user",this.user);
		    this.appletSendFiles.addParam("fileScanHash",this.fileScanHash);
		    this.appletSendFiles.addParam("fileFirmaHash",this.fileFirmaHash);

		    this.appletSendFiles.addFile("fileScan",this.fileScan);
		    this.appletSendFiles.addFile("fileFirma",this.fileFirma);
		    var result=this.appletSendFiles.sendFiles();
			if(!result){
				alert(top.GetIdsLan("IDS_ERR_SAVE_SCAN_FILES"));
			}
	}catch (err) {
			alert(GetIdsLan("IDS_ERR_SAVE_SCAN_FILES"));
		}

    }

    this.destroy=function(){
	this.appletScan.stop();
	this.appletScan.destroy();

	this.appletSendFiles.stop();
	this.appletSendFiles.destroy();
    }

    /*
     *
     * Metodo para bloquear la pantalla
     *
     */
	this.blockUI=function() {

		$.blockUI.defaults.css.border = 'none';
		$.blockUI.defaults.css.backgroundColor = "#ffffff";
		$.blockUI.defaults.overlayCSS.backgroundColor = "#ffffff";
		$.blockUI({ message: null });

		top.Main.Folder.FolderData.FolderFormTree.$.blockUI({ message: null });
		top.Main.Folder.FolderData.FolderFormData.$.blockUI({ message: '<img src="./images/loading.gif" /><br><nobr>'+ top.GetIdsLan("IDS_WAIT_PLEASE") +'</nobr>'})
		top.Main.Folder.FolderBar.$.blockUI({ message: null });

	}



    /*
     *metodo para desbloquear la pantalla del navegador durante el proce
     *
     *
     */
     this.unblockUI=function() {
	 top.Main.Folder.FolderData.FolderFormTree.$.unblockUI();
	 top.Main.Folder.FolderData.FolderFormData.$.unblockUI();
	 top.Main.Folder.FolderBar.$.unblockUI();
      }



	/*
     this.debugValues=function() {


	   cadena="idRegistro:"+this.idRegistro+"\n"+
	     "idLibro:"+this.idLibro+"\n"+
	    "sessionPId:"+this.sessionPId+"\n"+
	    "firma:"+this.firma+"\n"+
	    "certificate:"+this.certificate+"\n"+
	    "fileScan:"+this.fileScan+"\n"+
	    "fileScanHash"+this.fileScanHash+"\n"+
	    "user:"+this.user+"\n"+
	    "fileFirma:"+this.fileFirma+"\n"+
	    "fileFirmaHash:"+this.fileFirmaHash;

	    alert(cadena);

     }
        */

}





//*******************************************************************
			// Ruta al directorio de los instalables de @firma
			// "base" esta declarado en el fichero "constantes.js" de @firma
			function getBase()
			{
				var baseHREF= document.location.href;
				baseHREF= baseHREF.substring(0, baseHREF.lastIndexOf('/'));
				return baseHREF;
			}

//*******************************************************************



/*
function post_to_url(path, paramsCompulsa,fileParamsCompulsa, method,enctype) {
    method = method || "post";

    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);
    form.setAttribute("enctype", enctype);


    for(var key in paramsCompulsa) {

        var hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", key);
        hiddenField.setAttribute("value", paramsCompulsa[key]);

        form.appendChild(hiddenField);
    }

    for(var fileKey in fileParamsCompulsa) {

        var fileField = document.createElement("input");
        fileField.setAttribute("type", "file");
        fileField.setAttribute("name", fileKey);

        fileField.setAttribute("value", fileParamsCompulsa[fileKey]);
        form.appendChild(fileField);
    }

    document.body.appendChild(form);
    form.encoding = "multipart/form-data";

    form.submit();
}
*/
