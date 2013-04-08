<!--
JSP para la validación de usuarios y entrada a la aplicación.
Esta JSP siempre crea una sesión nueva de usuario.
-->

<%@page import="com.ieci.tecdoc.common.exception.TecDocException"%>
<%@page import="com.ieci.tecdoc.common.keys.ServerKeys"%>
<%@page import="com.ieci.tecdoc.common.utils.ISicresGenPerms"%>
<%@page import="com.ieci.tecdoc.utils.cache.CacheBag"%>
<%@page import="com.ieci.tecdoc.utils.cache.CacheFactory"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html"%>
<%@ page isErrorPage="false" %>
<%@ page session="true"%>
<%@ page isThreadSafe="true" %>
<%@ page autoFlush="true" %>
<%@ page errorPage="__exception.jsp" %>


<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">

<%@ page import="com.ieci.tecdoc.isicres.desktopweb.Keys" %>
<%@ page import="com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils" %>
<%@ page import="com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils" %>
<%@ page import="com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil" %>
<%@ page import="com.ieci.tecdoc.isicres.usecase.UseCaseConf" %>
<%@ page import="com.ieci.tecdoc.isicres.usecase.security.SecurityUseCase" %>
<%@ page import="com.ieci.tecdoc.isicres.session.book.BookSession" %>
<%@ page import="com.ieci.tecdoc.common.isicres.SessionInformation" %>
<%@ page import="com.ieci.tecdoc.common.exception.SecurityException" %>
<%@ page import="com.ieci.tecdoc.common.exception.ValidationException" %>
<%@ page import="com.ieci.tecdoc.common.utils.Configurator"%>
<%@ page import="com.ieci.tecdoc.common.keys.ConfigurationKeys"%>
<%@ page import="org.apache.log4j.*"%>
<%@ page import="ieci.tecdoc.sgm.registropresencial.utils.AuthenticationHelper" %>


<%@ include file="__headerLocale.jsp" %>

<%!Logger _logger = Logger.getLogger(getClass());%>

<%!SecurityUseCase securityUseCase = new SecurityUseCase();%>

<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");

	// Identificador de aplicación
	Long appId = RequestUtils.parseRequestParameterAsLong(request,
			"AppId");
	// True se usa LDAP.
	Boolean useLdap = RequestUtils.parseRequestParameterAsBoolean(
			request, "UseLDAP", Boolean.FALSE);
	// True se usa LDAP SSO.
	Boolean usingOSAuth = RequestUtils.parseRequestParameterAsBoolean(
			request, "UsingOSAuth", Boolean.FALSE);

	// Texto del idioma. Ej: EU_
	String idioma = RequestUtils.parseRequestParameterAsString(request,
			"Idioma");
	// Número del idioma. Ej: 10
	Long numIdioma = RequestUtils.parseRequestParameterAsLong(request,
			"numIdioma");
	// Obtenemos el locale java para el código de idioma seleccionado por el usuario.
	Locale locale = (Locale) microsoftLocalesID2DefaultLocales
			.get(numIdioma);
	String password = "";
	String passwordCrypt = "";
	// El campo error nos va a indicar el tipo de error ha mostar al usuario.
	// Los tipos de errores pueden ser: usuario bloqueado, password incorrecto, login invalido, etc.
	String error = "0";
	String USERBL = ": ";
	String OFICUSER = "Oficina: ";
	String ofic = "";
	int tries = 0;
	UseCaseConf useCaseConf = null;
	String jndiName = Configurator.getInstance().getProperty(
			ConfigurationKeys.KEY_DESKTOP_REPORTSDATASOURCEJNDINAME);
	jndiName = jndiName.substring(jndiName.lastIndexOf("/") + 1,
			jndiName.length());
	// Nombre del usuario.
	String name = null;
	// Dn del usuario conectado en Windows.
	String userDn = null;
	if (jndiName.equals(Keys.KEY_BUILD_TYPE_INVESICRES)) {
		name = RequestUtils.parseRequestParameterAsStringWithEmpty(
				request, "NameCtrl");
		userDn = RequestUtils.parseRequestParameterAsStringWithEmpty(
				request, "UserDn");
	} else {
		useCaseConf = (UseCaseConf) request.getSession().getAttribute(
				Keys.J_USECASECONF);
		name = useCaseConf.getUserName();
		userDn = useCaseConf.getUserName();
	}
	String user = USERBL + name;

	if (useLdap.booleanValue() && usingOSAuth.booleanValue()) {
		if (jndiName.equals(Keys.KEY_BUILD_TYPE_INVESICRES)) {
			useCaseConf = new UseCaseConf(locale, userDn, useLdap,
					usingOSAuth);
		} else {
			useCaseConf = (UseCaseConf) request.getSession()
					.getAttribute(Keys.J_USECASECONF);
			useCaseConf.setLocale(locale);
			useCaseConf.setUserDn(userDn);
			useCaseConf.setUseLdap(useLdap);
			useCaseConf.setUsingOSAuth(usingOSAuth);
		}
	} else if (name.length() != 0) {
		// Obtenemos el objeto de configuración del servidor de aplicaciones y el locale
		// para este usuario en el servidor de aplicaciones.
		if (jndiName.equals(Keys.KEY_BUILD_TYPE_INVESICRES)) {
			useCaseConf = new UseCaseConf(locale);
			useCaseConf.setEntidadId(jndiName);
			// Contraseña del usuario.
			passwordCrypt = RequestUtils.parseRequestParameterAsString(
					request, "PasswordCtrl");
		} else {
			useCaseConf.setLocale(locale);
		}

		useCaseConf.setUseLdap(useLdap);

		// Número de intentos.
		tries = RequestUtils.parseRequestParameterAsint(request,
				"TriesCtrl");
	}

	String caseSensitive = "";

	if ((useLdap.booleanValue() && usingOSAuth.booleanValue())
			|| name.length() != 0) {

		Map persistFields = new HashMap();
		try {
			// Validación del usuario.
			securityUseCase.login(useCaseConf, name, passwordCrypt);
			// Oficina del usuario.
			if (securityUseCase.getCurrentUserOficName(useCaseConf) != null) {
				ofic = OFICUSER
						+ securityUseCase
								.getCurrentUserOficName(useCaseConf);
			}

			// Una vez validado el usuario se introduce en la sesión diversos parámetros.
			//_logger.info("login.jsp useCaseConf1: " +  session);
			session = request.getSession(true);
			session.setAttribute(Keys.J_USECASECONF, useCaseConf);
			session.setAttribute(Keys.J_USERNAME, name);
			useCaseConf.setUserName(name);
			session.setAttribute(Keys.J_IDIOMA, idioma);
			session.setAttribute(Keys.J_NUM_IDIOMA, numIdioma);
			session.setAttribute(Keys.J_PERSISTFIELDS, persistFields);
			//_logger.info("login.jsp useCaseConf2: " +  session);



			if ((useCaseConf != null)
					&& (useCaseConf.getSessionID() != null)
					&& (useCaseConf.getEntidadId() != null)) {
				SessionInformation sessionInformation = BookSession
						.getSessionInformation(
								useCaseConf.getSessionID(),
								useCaseConf.getLocale(),
								useCaseConf.getEntidadId());
				caseSensitive = sessionInformation.getCaseSensitive();
			}

			String enabledIntercambioRegistral = Configurator
					.getInstance()
					.getProperty(
							ConfigurationKeys.KEY_INTERCAMBIO_ENABLE_INTERCAMBIO_REGISTRAL);
			boolean canSendToIntercambioRegistral = false;
			try{
				CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
						useCaseConf.getSessionID());
				ISicresGenPerms permisos = (ISicresGenPerms)cacheBag.get(ServerKeys.GENPERMS_USER);
				canSendToIntercambioRegistral =permisos.canAccessRegInterchange();
			}catch (TecDocException e) {
					//logger.error("Error al obtener el permiso de intermcabio registral. Se deshabilita.",e);
				}
			// Acceso a la pagina principal (Lista de Libros).
			out.write("<script language=\"javascript\">");
			out.write("top.g_SessionPId = \""
					+ useCaseConf.getSessionID() + "\";");
			out.write("top.g_CaseSensitive = \"" + caseSensitive
					+ "\";");
			out.write("top.g_EnabledIntercambioRegistral = \""
					+ enabledIntercambioRegistral + "\";");
			out.write("top.g_canSendIntercambioRegistral = \""
					+ canSendToIntercambioRegistral + "\";");
			out.write("window.open(top.g_URL + \"/mainfrm_iframes.htm\", \"Main\",\"location=no\",true);");
			out.write("</script>");
		} catch (SecurityException e) {
			_logger.fatal("Error de seguridad", e);
			error = "1";
			ResponseUtils.generateJavaScriptError(out, e);
			if (e.getCode().equals(
					SecurityException.ERROR_PASSWORD_INCORRECT)) {
				_logger.error("Password incorrecta", e);
				tries++;
			}
			if (!jndiName.equals(Keys.KEY_BUILD_TYPE_INVESICRES)) {
				session.invalidate();
				out.write("<script>top.location.href='"
						+ AuthenticationHelper
								.getWebAuthDesconectURL(request)
						+ "?desconectar=true" + "';</script>");
			}
		} catch (ValidationException e) {
			_logger.fatal("Error de validacion", e);
			error = "1";
			ResponseUtils.generateJavaScriptLog(
					out,
					RBUtil.getInstance(locale).getProperty(
							Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
			if (!jndiName.equals(Keys.KEY_BUILD_TYPE_INVESICRES)) {
				session.invalidate();
				out.write("<script>top.location.href='"
						+ AuthenticationHelper
								.getWebAuthDesconectURL(request)
						+ "?desconectar=true" + "';</script>");
			}
		} catch (Exception e) {
			_logger.fatal("Error en las comunicaciones", e);
			error = "1";
			ResponseUtils.generateJavaScriptLog(
					out,
					RBUtil.getInstance(locale).getProperty(
							Keys.I18N_EXCEPTION_REMOTEEXCEPTION));
			if (!jndiName.equals(Keys.KEY_BUILD_TYPE_INVESICRES)) {
				session.invalidate();
				out.write("<script>top.location.href='"
						+ AuthenticationHelper
								.getWebAuthDesconectURL(request)
						+ "?desconectar=true" + "';</script>");
			}
		}
	}
%>
<%
	if (jndiName.equals(Keys.KEY_BUILD_TYPE_INVESICRES)) {
%>

<html>
   <head>
      <script language="javascript">
			document.write('<link REL=\"stylesheet\" TYPE=\"text/css\" HREF="' + top.urlSkinCSS + '"/>');
	  </script>
      <link REL="stylesheet" TYPE="text/css" HREF="css/global.css" />
      <link REL="stylesheet" TYPE="text/css" HREF="css/font.css" />
      <link REL="stylesheet" TYPE="text/css" HREF="css/frame.css" />
      <script language="javascript">document.write("<title>" + top.GetIdsLan("IDS_TITULO") + "</title>");</script>
   </head>
   <script language="javascript">
   //login.jsp de SIGEM_RegistroPresencialWeb
   function OnWindowLoad()
   {
      var Error = document.getElementById("ErrorCtrl").value;

      if (Error <= 1) {
	  document.getElementById("NameCtrl").focus();
	  document.getElementById("NameCtrl").select();
      }
      else {
			document.getElementById("PasswordCtrl").focus();
          document.getElementById("PasswordCtrl").select();
      }
	  if (top.g_CanChangePassword == false){
				document.getElementById("ChangePwd").style.visibility = "hidden";
	  }

      document.getElementById("ErrorCtrl").value = 0;      // cargamos la toolbar
      return;
   }

   function ValidateLoginForm()
   {
		if (document.LoginForm.NameCtrl.value == "") {
			alert( top.GetIdsLan( "IDS_MUST_FILL_USER" ) );
			document.getElementById("NameCtrl").focus();

			return(false);
		}
		else if (document.LoginForm.PasswordCtrl.value == "") {
			alert( top.GetIdsLan( "IDS_MUST_FILL_PASSWORD" ) );
			document.getElementById("PasswordCtrl").focus();

			return(false);
		}

		document.getElementById("ChangePwd").className = "SubOptionsDisabled";

		return(true);
   }


   function changeIdiom()
   {
      var strParams = "";
      var index = document.getElementById("oSelectIdiom").selectedIndex;
      var pIdioma = document.getElementById("oSelectIdiom").options.item(index).strIdiom;
      var pNumIdioma = document.getElementById("oSelectIdiom").options.item(index).numIdiom;

      if ( pIdioma != top.Idioma.toString())
      {
         top.Idioma = pIdioma;
         top.numIdioma = pNumIdioma;
         // quitamos el parámetro idioma
         strParams = top.removeParam(top.document.location.search,"Idioma");
         // quitamos el parámetro nº de idioma
         strParams = top.removeParam(strParams,"numIdioma");
         if (strParams == "")
         {
            strParams = "?Idioma=" + top.Idioma.toString() + "&numIdioma=" + top.numIdioma.toString();
         }
         else
         {
            strParams += "&Idioma=" + top.Idioma.toString() + "&numIdioma=" + top.numIdioma.toString();
         }
         window.open(top.g_URL + "/default.jsp" + strParams, "_top","location=no",true);
      }
      return;
   }


   function OnChangePwd()
   {
		top.SetOpacity(document.getElementById("wkspace"), 20);
		top.SetOpacity(document.getElementById("login"), 20);
		top.DisableEvents(window.top);
		document.getElementById("ChangePwdWnd").style.visibility = "visible";
		document.getElementById("ChangePwd").className = "SubOptionsDisabled";
		document.getElementById("HelpBtn").className = "SubOptionsDisabled";

	  window.open( top.g_URL + "/chgpwdmain.htm?Idioma=" + top.Idioma.toString(), "ChangePwdWnd", "location=no",true);

	  return;
   }


   function mouseOver(obj)
   {
      obj.style.cursor = "pointer";
      window.status = "";
      return;
   }


   function mouseOut(obj)
   {
      obj.style.cursor = "default";
      window.status = window.defaultStatus;
      return;
   }


   function SelectIdiom()
   {
      for(var ii=0; ii < document.getElementById("oSelectIdiom").options.length; ii++)
      {
         if (top.numIdioma == parseInt(document.getElementById("oSelectIdiom").options.item(ii).numIdiom) )
         {
            document.getElementById("oSelectIdiom").options.item(ii).selected = true;
            break;
         }
      }

      return;
   }


   </script>
   <body onload="OnWindowLoad();SelectIdiom();" tabIndex="-1">
   	<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="Resalt" id="wkspace">
		<tr height="28" valign="middle" align="center" tabIndex="-1">
			<td width="10"></td>
			<td width="220"><div align="left" id="ArchiveName" class="ArchiveName"></div>
			</td>
			<td id="MenuSep" style="visibility:hidden" width="2"><img src="images/sep.gif" width="2" height="28" border="0"></td>
			<td>
				<table width="100%" height="24" border="0" cellspacing="0" cellpadding="0">
					<tr tabIndex="-1">
						<td><div align="center">&nbsp;</div>
						</td>
						<td id="ChangePwd" width="150" class="Options" onmouseover="top.Over( this )" tabIndex="1" onmouseout="top.Out( this )" onclick="if(this.className!='SubOptionsDisabled'){OnChangePwd()}" onkeydown="if ((top.GetKeyCode(event)==13)&&(this.className!='SubOptionsDisabled')){OnChangePwd();}">
							<div align="center"><SCRIPT language="javascript">document.write( top.GetIdsLan( "IDS_CHANGE_PWD" ) )</SCRIPT></div>
						</td>
						<td id="HelpBtn" width="80" class="Options" onmouseover="top.Over(this)" onmouseout="top.Out(this)" onclick="if(this.className!='SubOptionsDisabled'){top.OpenHelp()}" onkeydown="if ((top.GetKeyCode(event)==13)&&(this.className!='SubOptionsDisabled')){top.OpenHelp();}" tabIndex="1">
							<div align="center"><SCRIPT language="javascript">document.write( top.GetIdsLan( "IDS_HELP" ) )</SCRIPT></div>
						</td>
					</tr>
				</table>
			</td>
			<td width="5"></td>
		</tr>
	</table>
          <form Action="" Method="post" Name="LoginForm" OnSubmit="return(ValidateLoginForm());">
       <div id="login">
            <table height="230">
                <tr>
					<td colspan="3">
						<h1 class="title"><script language="javascript">document.write(top.GetIdsLan("IDS_FILL_USER_PASSWORD"));</script></h1>
                    </td>
                </tr>
                <tr><td height="60" colspan="3"></td></tr>
                <tr>
					<td width="60%"></td>
					<td>
						<label class="label"><script language="javascript">document.write(top.GetIdsLan("IDS_USER"));</script></label>
                    </td>
                    <td align="right">
						<input class="input" Name="NameCtrl" value="<%=name%>" Maxlength="254" ID="Text1">
                    </td>
                </tr>
                <tr height="10"><td colspan="3"></td></tr>
				<tr>
					<td width="60%"></td>
					<td>
						<label class="label"><script language="javascript">document.write( top.GetIdsLan( "IDS_PASSWORD" ) )</script></label>
                    </td>
                    <td align="right">
						<input class="input" name="PasswordCtrl" type="Password" value="<%=password%>" maxlength="254" ID="Password1">
                    </td>
                </tr>
                <tr height="15"><td colspan="3"></td></tr>
                <tr>
					<td width="60%"></td>
					<td colspan="2" align="right" style="padding-right:10">
						<input class="button" id="Submit1" name="SubmitCtrl" Type="Submit" Value="" style="width:80;">
							<script language="javascript">document.getElementById("SubmitCtrl").value = top.GetIdsLan("IDS_BUTTON_OK");</script>
                        </input>
                    </td>
                </tr>
                <tr height="15"><td colspan="3"></td></tr>
                <tr height="15" style="display:none"><td colspan="3"></td></tr>
                <tr style="display:none">
					<td width="60%"></td>
					<td>
						<label class="label"><script language="javascript">document.write(top.GetIdsLan("IDS_IDIOMA"));</script></label>
                    </td>
                    <td>
						<select class="combo" name="oSelectIdiom" id="Select1" onchange="changeIdiom();"></select>
                    </td>
                </tr>
                <!--<tr height="30"><td colspan="2"></td></tr>-->
                <!--<tr>
					<td colspan="3" align="center" width="100%">
						<label class="label"><script language="javascript">
						document.write(top.GetIdsLan("IDS_COPYRIGHT"));</script></label>
                    </td>
                </tr>-->
	        </table>
        </div>
        <input name="ErrorCtrl" Type="Hidden" Value="<%=error%>">
        <input name="PasswordCtrlCrypt" Type="Hidden" Value="">
        <input name="TriesCtrl" Type="Hidden" Value="<%=tries%>">
      </form>
       <iframe id="ChangePwdWnd" name="ChangePwdWnd" class="Style6" SRC="blank.htm" frameborder="0" scrolling="no"
			style="HEIGHT:200px;LEFT:300px;POSITION:absolute;TOP:15px;WIDTH:445px;visibility:hidden;"/>

    </body>
</html>
<%
	}
%>