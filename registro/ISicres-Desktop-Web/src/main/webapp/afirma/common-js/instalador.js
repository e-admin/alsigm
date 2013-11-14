/*
 * Este fichero forma parte del Cliente @firma.
 * El Cliente @firma es un aplicativo de libre distribucion cuyo codigo fuente puede ser consultado
 * y descargado desde www.ctt.map.es.
 * Copyright 2009,2010,2011 Gobierno de Espana
 * Este fichero se distribuye bajo licencia GPL version 3 segun las
 * condiciones que figuran en el fichero 'licence' que se acompana. Si se distribuyera este
 * fichero individualmente, deben incluirse aqui las condiciones expresadas alli.
 */

/**
 *
 * Version: 2.0.0
 *
 * Depende de deployJava.js y de constantes.js [opcional].
 *
 * Si se ha definido baseDownloadURL se usa como URL base para la descarga de los instalables.
 *
 * cargarAppletFirma(build):
 *      Carga el applet de firma en la variable "clienteFirma". El applet no esta cargado hasta que clienteFirmaCargado==true.
 *
 * instalar(build, jsMethodName, jsMethodParams):
 *      Carga el applet instalador (si no se ha cargado), e instala las dependencias del cliente en local.
 *      build          - Tipo de ckliente para el cual deben integrarse las dependencias: 'LITE', 'MEDIA' o 'COMPLETA'
 *      jsMethodName   - Metodo JavaScript a invocar una vez terminado el proceso de instalacion
 *      jsMethodParams - Parametros del metodo JavaScript a invocar una vez terminado el proceso de instalacion
 *	Este metodo debe ser llamado por los integradores unicamente en el caso de que deseen tener control sobre los procesos de
 *      instalacion, y no debe nunca combinarse manualmente con la carga del Applet de Firma en una misma pagina Web.
 *
 * desinstalar():
 *      Carga el applet instalador (si no se ha cargado), desinstala el cliente de firma de local y devuelve true si el proceso
 *      finaliza correctamente (false si no).
 *	Este metodo debe ser llamado por los integradores unicamente en el caso de que deseen tener control sobre los procesos de
 *      instalacion, y no debe nunca combinarse manualmente con la carga del Applet de Firma en una misma pagina Web.
 *
 * getVersion():
 *      Carga el applet instalador (si no se ha cargado) y devuelve la version del instalador.
 *	Este metodo debe ser llamado por los integradores unicamente en el caso de que deseen tener control sobre los procesos de
 *      instalacion, y no debe nunca combinarse manualmente con la carga del Applet de Firma en una misma pagina Web.
 *
 */

var instalador;
var instaladorCargado = false;
var clienteFirma;

function cargarAppletFirma(build)
{
	/* Si ya esta cargado, no continuamos con el proceso */
	if (clienteFirma != undefined) {
		return;
	}

	/* Definimos las contruccion que se va a utilizar */
	var confBuild = configureBuild(build);

	/* Cargamos el instalador e instalamos en cualquier caso menos con Google Chrome, que no deja tener dos
           Applets en la misma pagina cuando Java esta desactualizado. Revisaremos esta parte cuando salga
           Chrome 64 bits para Windows, entorno que requerira instalacion. */
        /* Por ahora tampoco cargamos el BootLoader en Internet Explorer 10. */
	if (deployJava.browserName2 != 'Chrome' && parseFloat(navigator.appVersion.split("MSIE")[1]) < 10) {
		instalar(confBuild, null, null);
	}

	var jarArchive = (baseDownloadURL != undefined ? baseDownloadURL : '.') + '/' + confBuild + "_j6_afirma5_core__V3.2.jar";
	if (deployJava.versionCheck('1.6.0+') == false) {
                jarArchive = (baseDownloadURL != undefined ? baseDownloadURL : '.') + '/' + confBuild + "_j5_afirma5_core__V3.2.jar";
	}

	var installercodeBase = base;
	if (installercodeBase == undefined || installercodeBase == null) {
		installercodeBase = '.';
	}
	var codeBase = baseDownloadURL;
	if (codeBase == undefined || codeBase == null) {
		codeBase = '.';
	}

	var attributes = {
		id: 'firmaApplet',
		width: 1,
		height: 1
	};
	var parameters = {
		jnlp_href: installercodeBase + "/" + confBuild + '_afirma.jnlp',
		userAgent: window.navigator.userAgent,
		appName: window.navigator.appName,
		showExpiratedCertificates: showExpiratedCertificates,
		showMozillaSmartCardWarning: showMozillaSmartCardWarning,
		code: 'es.gob.afirma.cliente.SignApplet.class',
		archive: jarArchive,
		codebase: codeBase,
		java_arguments: '-Djnlp.versionEnabled=true -Djnlp.packEnabled=true -Xms512M -Xmx512M',
		separate_jvm: true
	};

	deployJava.runApplet(attributes, parameters, '1.5');

	clienteFirma = document.getElementById("firmaApplet");

	/* Realizamos una espera para que de tiempo a cargarse el applet */
	for (var i = 0; i < 100; i++) {
		try {
			setTimeout("clienteFirma != undefined && clienteFirma.isInitialized()", 100);
			break;
		} catch (e) {
			/*
			 * Capturamos la excepcion que se produciria si no se hubiese cargado aun el applet, aunque no se lanzaria
			 * una vez estuviese cargado aunque no iniciado
			 */
		}
	}
}


function cargarAppletInstalador()
{
	if(instalador == undefined)
	{
		/* Definicion de las constantes necesarias */
		var codeBaseVar = '.';
		if(base != undefined) {
			if(base.toLowerCase().substring(0, 7) != "file://" &&
					base.toLowerCase().substring(0, 7) != "http://" &&
					base.toLowerCase().substring(0, 8) != "https://") {
				codeBaseVar = './' + base;
			}
			else {
				codeBaseVar = base;
			}
		}

		var attributes = {id:'instaladorApplet',
					code:'es.gob.afirma.install.AfirmaBootLoader.class',
					archive:codeBaseVar+'/afirmaBootLoader.jar',
					width:1, height:1};
		var version = '1.5';

		try {
			deployJava.runApplet(attributes, null, version);
		} catch(e) {}

		instalador = document.getElementById("instaladorApplet");

		for(var i=0; i<100; i++) {
			try {
				setTimeout("instalador != undefined && (instalador.getVersion() != null)", 100);
				instaladorCargado = true;
				break;
			} catch(e) {
				instaladorCargado = false;
				// Capturamos la excepcion que se produciria si no se hubiese cargado aun el applet, aunque no se lanzaria
				// una vez estuviese cargado aunque no iniciado
			}
		}


		// Si hay definida una URL desde la que descargar los instalables, la establecemos
		if(baseDownloadURL != undefined) {
			if(baseDownloadURL.toLowerCase().substring(0, 7) != "file://" &&
					baseDownloadURL.toLowerCase().substring(0, 7) != "http://" &&
					baseDownloadURL.toLowerCase().substring(0, 8) != "https://") {

				var url = document.location.toString();
				instalador.setBaseDownloadURL(url.substring(0, url.lastIndexOf("/")) + '/' + baseDownloadURL);
			}
			else {
				instalador.setBaseDownloadURL(baseDownloadURL);
			}
		}
	}
}


function instalar(build, jsMethodName, jsMethodParams)
{

	// Definimos las contruccion que se va a utilizar
	var confBuild = configureBuild(build);

	if(instalador == undefined)
	{
		cargarAppletInstalador();
	}

	// Si se ha indicado una construccion de alguna manera (por defecto o por parametro), se instala esa
	var allOK;
	if(jsMethodName == undefined || jsMethodName == null)
	{
		allOK = instalador.instalar(confBuild);
	} else {
		if(jsMethodParams == undefined)
		{
			jsMethodParams = null;
		}

		allOK = instalador.instalar(confBuild, jsMethodName, jsMethodParams);
	}

	return allOK;
}

function desinstalar()
{
	if(instalador == undefined)
	{
		cargarAppletInstalador();
	}

	return instalador.desinstalar();
}

function getVersion()
{
	if(instalador == undefined)
	{
		cargarAppletInstalador();
	}

	return instalador.getVersion();
}

/**
 * Si no se ha indicado una construccion por parametro, ni hay establecida una por defecto en "constantes.js", se instala la 'LITE'
 */
function configureBuild(build)
{
	var confBuild = null;
	if(build != null && build != undefined)
	{
		confBuild = build;
	}
	else if(defaultBuild != null && defaultBuild != undefined) {
		confBuild = defaultBuild;
	}
	else {
		confBuild = 'LITE';
	}
	return confBuild;
}
