<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html"%>
<%@ page isErrorPage="false" %>
<%@ page session="true"%>
<%@ page isThreadSafe="true" %>
<%@ page autoFlush="true" %>
<%@ page errorPage="__exception.jsp" %>

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">

<%@ page import="com.ieci.tecdoc.common.keys.ConfigurationKeys"%>
<%@ page import="com.ieci.tecdoc.idoc.decoder.dbinfo.LDAPDef" %>
<%@ page import="com.ieci.tecdoc.isicres.desktopweb.Keys" %>
<%@ page import="com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil" %>
<%@ page import="com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils" %>
<%@ page import="com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils" %>
<%@ page import="com.ieci.tecdoc.isicres.usecase.security.SecurityUseCase" %>
<%@ page import="com.ieci.tecdoc.isicres.usecase.UseCaseConf" %>
<%@ page import="com.ieci.tecdoc.common.utils.Configurator"%>
<%@ page import="com.ieci.tecdoc.isicres.desktopweb.utils.IdiomaUtils" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@page import="com.ieci.tecdoc.common.exception.TecDocException"%>
<%@page import="org.apache.commons.lang.BooleanUtils"%>
<%@page import="com.ieci.tecdoc.common.keys.ServerKeys"%>
<%@page import="com.ieci.tecdoc.common.utils.ISicresGenPerms"%>
<%@page import="com.ieci.tecdoc.utils.cache.CacheFactory"%>
<%@page import="com.ieci.tecdoc.utils.cache.CacheBag"%>
<%@page import="org.springframework.web.servlet.LocaleResolver" %>
<%@page import="org.springframework.web.servlet.i18n.SessionLocaleResolver" %>
<%@page import="es.ieci.tecdoc.isicres.api.business.manager.LibroManager" %>
<%@page import="es.ieci.tecdoc.isicres.api.business.manager.impl.LibroManagerImpl"%>
<%@page import="es.ieci.tecdoc.isicres.api.business.manager.IsicresManagerProvider"%>
<%@page import="es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO"%>
<%@page import="es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO" %>
<%@page import="es.ieci.tecdoc.isicres.api.business.vo.ContextoAplicacionVO" %>
<%@page import="com.ieci.tecdoc.isicres.web.util.ContextoAplicacionUtil" %>

<%@ include file="__headerLocale.jsp" %>

<%! Logger _logger = Logger.getLogger(getClass()); %>

<%! SecurityUseCase securityUseCase = new SecurityUseCase(); %>


<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");


	// Identificador de aplicación
	Integer folderView = RequestUtils.parseRequestParameterAsInteger(request, "FolderView", new Integer(0));
    // identificador de archivo de usuario. (Libro).
    Integer archiveId = RequestUtils.parseRequestParameterAsInteger(request, "ArchiveId", new Integer(0));
    // Identificador de registro a copiar.
    Integer archivePId = RequestUtils.parseRequestParameterAsInteger(request, "ArchivePId", new Integer(0));
    // Nombre de archivo.
    String archiveName = RequestUtils.parseRequestParameterAsStringWithEmpty(request, "ArchiveName");
    // Identificador de la carpeta de consulta.
    Integer fdrQryPId = RequestUtils.parseRequestParameterAsInteger(request, "FdrQryPId", new Integer(-1));
    // Identificador de carpeta.
    Integer folderId = RequestUtils.parseRequestParameterAsInteger(request, "FolderId", new Integer(-1));
    // Identificador de distribución.
    Integer distId = RequestUtils.parseRequestParameterAsInteger(request, "DistId", new Integer(-1));
    // Carpeta de solo lectura.
    Integer fdrReadOnly = RequestUtils.parseRequestParameterAsInteger(request, "FdrReadOnly", new Integer(1));
    Integer folderPId = RequestUtils.parseRequestParameterAsInteger(request, "FolderPId", new Integer(-1));
	// Identificador de aplicación
	Integer appId = RequestUtils.parseRequestParameterAsInteger(request, "AppId", new Integer(1));
    //Sesión.
    String sessionPId = RequestUtils.parseRequestParameterAsStringWithEmpty(request, "SessionPId");
    // Identificador de registro a copiar.
    Integer vldSave = RequestUtils.parseRequestParameterAsInteger(request, "VldSave", new Integer(0));
	// Texto del idioma. Ej: EU_
	String idioma = IdiomaUtils.getInstance().getIdioma(request);
	// Número del idioma. Ej: 10
	Long numIdioma = IdiomaUtils.getInstance().getNumIdioma(request);

    Integer closeFolder = RequestUtils.parseRequestParameterAsInteger(request, "CloseFolder", new Integer(1));
    Integer openType = RequestUtils.parseRequestParameterAsInteger(request, "OpenType", new Integer(0));
    Integer copyFdr = RequestUtils.parseRequestParameterAsInteger(request, "CopyFdr", new Integer(0));
    Integer openFolderDtr = RequestUtils.parseRequestParameterAsInteger(request, "OpenFolderDtr", new Integer(0));
    Integer openFolderPenDtr = RequestUtils.parseRequestParameterAsInteger(request, "OpenFolderPenDtr", new Integer(0));
    Integer openEditDistr = RequestUtils.parseRequestParameterAsInteger(request, "OpenEditDistr", new Integer(0));
    Integer firstReg = RequestUtils.parseRequestParameterAsInteger(request, "FirstReg", new Integer(0));
    Integer lastReg = RequestUtils.parseRequestParameterAsInteger(request, "LastReg", new Integer(0));

    Boolean Form = RequestUtils.parseRequestParameterAsBoolean(request, "Form");

	int bUseLDAP = 0;
	int bUseOSAuth = 0;
	int bCanChangePassword = 0;

	UseCaseConf useCaseConf = null;

	Locale locale = (Locale) microsoftLocalesID2DefaultLocales.get(numIdioma);

	LocaleResolver localeResolver = new SessionLocaleResolver();
	localeResolver.setLocale(request, response, locale);
	localeResolver.resolveLocale(request);

	session.setAttribute(Keys.J_LOCALE, locale);

    useCaseConf = new UseCaseConf(locale);
    useCaseConf.setEntidadId(Keys.KEY_BUILD_TYPE_INVESICRES);
    if(session.getAttribute(Keys.J_USECASECONF) == null) {
    	session.setAttribute(Keys.J_USECASECONF, useCaseConf);
    }
    //Intercambio registral
    String enabledIntercambioRegistral = Configurator.getInstance().getProperty(ConfigurationKeys.KEY_INTERCAMBIO_ENABLE_INTERCAMBIO_REGISTRAL);
	boolean canSendToIntercambioRegistral = false;
	// Indica si el libro esta configurado para intercambio registral
	boolean bookEnabledIntercambioRegistral = false;
	try{
		CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
				sessionPId);
		ISicresGenPerms permisos = (ISicresGenPerms)cacheBag.get(ServerKeys.GENPERMS_USER);
		canSendToIntercambioRegistral =permisos.canAccessRegInterchange();
	}catch (TecDocException e) {
			//_logger.error("Error al obtener el permiso de intermcabio registral. Se deshabilita.",e);
	}

	boolean deleteFilePerms = false;
	try{
		CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
				sessionPId);
		ISicresGenPerms permisos = (ISicresGenPerms)cacheBag.get(ServerKeys.GENPERMS_USER);
		deleteFilePerms = permisos.isCanDeleteDocuments();
	}catch (TecDocException e) {
		if(_logger.isDebugEnabled()){
			_logger.debug("Se ha producido un error al obtener los permisos del usuario", e);
		}
	}

	//Comprobamos si el libro es de intercambio registral
	try{
		ContextoAplicacionVO contextoAplicacion = ContextoAplicacionUtil
				.getContextoAplicacion(request);
		UsuarioVO usuario = contextoAplicacion.getUsuarioActual();
		LibroManager libroManager = IsicresManagerProvider.getInstance().getLibroManager();
		if (archiveId!=null){
			BaseLibroVO libro = libroManager.getLibro(usuario, archiveId);
			if (libro!=null){
				bookEnabledIntercambioRegistral = libroManager.isLibroIntercambioRegistral(libro);
			}
		}
	}catch (Exception e){
		_logger.error("Error al comprobar si el libro está habilitado para el Intercambio Registral",e);
	}

    //} else {
    //    useCaseConf = (UseCaseConf)request.getSession().getAttribute(Keys.J_USECASECONF);
    //     useCaseConf.setLocale(locale);
    //}
	try {
		LDAPDef ldapDef = securityUseCase.getLDAPInfo(useCaseConf);
		if(ldapDef.getLdapEngine() != 0){
			bUseLDAP = 1;
		} else {
			bCanChangePassword = 1;
		}

		if(ldapDef.getUseOsAuthentication() == 1){
			bUseOSAuth = 1;
		}

	} catch (Exception e) {
		_logger.fatal("Error en las comunicaciones",e);
		ResponseUtils.generateJavaScriptLog(out, RBUtil.getInstance(locale).getProperty(Keys.I18N_EXCEPTION_REMOTEEXCEPTION));
	}

%>


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript" LANGUAGE="javascript" SRC="scripts/genmsg.js"></script>
		<script type="text/javascript" LANGUAGE="javascript" SRC="scripts/cons_perm.js"></script>
		<script type="text/javascript" LANGUAGE="javascript" SRC="scripts/global.js"></script>
		<script type="text/javascript" language="javascript" src="./scripts/jquery-1.6.2.min.js"></script>
    	<script type="text/javascript" language="javascript" src="./scripts/jquery.hotkeys-0.8.js"></script>
    	<script type="text/javascript" language="javascript" src="./scripts/custom_hotkeys.js"></script>
		<script>
			var _frames = new Array();
		</script>

		<title>&nbsp;</title>
	</head>

	<script language="javascript">
		function GetUserDn()
		{
			var e;
			var bPluginInstall = false;
			var bCancelInstall = false;
			var oActiveXLDAP = null;
			var sUserDn = "";

			while ( (!oActiveXLDAP) && (!bPluginInstall) )
			{
				try
				{
					oActiveXLDAP = new ActiveXObject("IDocLDAPClient.IDocLDAP");
					bPluginInstall = true;
				}
				catch(e)
				{
					if (bCancelInstall)
					{
						alert(top.GetIdsLan("IDS_MUST_INSTALL_PLUGIN_LDAP"));
						bPluginInstall = true;
					}
					else
					{
						var URLPlugin = "./plugins/idocldap.cab";

						bCancelInstall = true;
						var URL = "install.htm?URLPlugin=" + URLPlugin + "&CLSID=13180912-3296-40DF-9335-88D3E00A2E53&VerPlugin=1,0,0,1&Title=" + top.GetIdsLan("IDS_TITLE_INST_SCAN") + "&BgText=Installing IdocLDAP ActiveX";
						var strRet = top.ShowModalDialog(URL, "", 150, 150, "edge:raised");
					}
				}
			}

			if (oActiveXLDAP)
			{
				try
				{
					sUserDn = oActiveXLDAP.GetUserDn();
				}
				catch(e)
				{
					alert(top.GetIdsLan("IDS_ERR_GET_USERDN_LDAP"));
				}
			}

			return sUserDn;
		}
	</script>


	<script language="javascript">
		// titulo
		top.g_FolderView = ( <%=folderView.toString()%> == "1" )? true : false;

		top.g_ArchiveId = parseInt("<%=archiveId.toString()%>");
		top.g_FolderId = parseInt("<%=folderId.toString()%>");
		top.g_DistId = parseInt("<%=distId.toString()%>");
		if (top.g_FolderView)	{
			top.g_ArchivePId = parseInt("<%=archivePId.toString()%>");
			top.g_ArchiveName = "<%=archiveName%>";
			top.g_FdrQryPId = parseInt("<%=fdrQryPId.toString()%>");
			top.g_FdrReadOnly = "<%=fdrReadOnly.toString()%>" == "1";
			top.g_FolderPId = parseInt("<%=folderPId.toString()%>");
		}

		top.g_AppId = parseInt("<%=appId.toString()%>");
 	    top.g_SessionPId  = "<%=sessionPId%>";

		top.g_URL = top.getURL();
		top.g_VldPath = "";
		top.g_VldSave = parseInt("<%=vldSave.toString()%>");

		// Identificativi de idioma, el por defecto = ""
		top.Idioma = "<%=idioma%>";
		// Nº de Idioma en Microsoft, po defecto el 10 (Español)
		top.numIdioma = "<%=numIdioma.toString()%>";
 	      top.g_UseOSAuth   = "<%=bUseOSAuth%>" == "1";
            top.g_UseLDAP     = "<%=bUseLDAP%>" == "1";
            top.g_CanChangePassword = "<%=bCanChangePassword%>" == "1";

		// Para saber si estamos en modificación o acabamos de abrir la carpeta
		top.g_CloseFolder = parseInt("<%=closeFolder.toString()%>");
		// Para controlar si abrimos una carpeta, si es nueva carpeta o si es un bucle
		top.g_OpenType = parseInt("<%=openType.toString()%>");
		top.g_CopyFdr = parseInt("<%=copyFdr.toString()%>");

		// para saber si se abre la carpeta desde la distribucion
		top.g_OpenFolderDtr = "<%=openFolderDtr.toString()%>" == "1";
		// para saber si se abre la carpeta desde url
		top.g_OpenFolderPenDtr = "<%=openFolderPenDtr.toString()%>" == "1";
		// para saber si se abre la carpeta desde distribución en modo edición
		top.g_OpenEditDistr = "<%=openEditDistr.toString()%>" == "1";

		// para navegación en vista de formulario
		top.g_FirstReg = parseInt("<%=firstReg.toString()%>");
		top.g_LastReg = parseInt("<%=lastReg.toString()%>");

		//Intercambio Registral
		top.g_canSendIntercambioRegistral= "<%=canSendToIntercambioRegistral%>";
		top.g_EnabledIntercambioRegistral= "<%=enabledIntercambioRegistral%>";
		top.g_isBookIntercambioRegistral  = "<%=bookEnabledIntercambioRegistral%>";
		if ( (top.g_UseLDAP) && (top.g_UseOSAuth) )	{
			top.g_UserDN = GetUserDn();
		}
		// para abrir el registro en vista formulario
		top.g_Form = "<%=Form%>";

		// para saber si debe mostrar la etiqueta de borrar ficheros adjuntados al registro
		top.g_deleteFilePerms = "<%=deleteFilePerms%>";

		// Empieza el documento HTML
		if (top.g_FolderView)	{
			document.write('<frameset rows="0px,*" name="frSetMain" id="frSetMain" scrolling="no" border="0" onunload="top.CloseWindow();">');
			document.write('<frame name="frBlank" id="frBlank" SRC="blank.htm" scrolling="no" noresize tabIndex="-1">');
			document.write('<frame src="frfoldermain.htm" id="Main" name="Main" scrolling="no" noresize>');
		}
		else {
			document.write('<frameset rows="0px,68px, *" name="frSetMain" id="frSetMain" scrolling="no" border="0" onbeforeunload="top.CloseWindow();">');
			document.write('<frame name="frBlank" id="frBlank" SRC="blank.htm" scrolling="no" noresize tabIndex="-1">');
			document.write('<frame name="Title" id="Title" src="titlebar.htm" scrolling="no" noresize tabIndex="-1">');
			document.write('<frame src="frlogin.htm" id="Main" name="Main" scrolling="no" noresize>');
		}

		document.title = top.GetIdsLan("IDS_TITULO");
		document.write('</frameset>');
	</script>
	<body tabindex="-1">
	</body>
</html>
