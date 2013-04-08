<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<!--
JSP para la validación de usuarios y entrada a la aplicación.
Esta JSP siempre crea una sesión nueva de usuario.
-->

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html"%>
<%@ page isErrorPage="false"%>
<%@ page session="true"%>
<%@ page isThreadSafe="true"%>
<%@ page autoFlush="true"%>
<%@ page errorPage="__exception.jsp"%>


<%@ page import="com.ieci.tecdoc.common.exception.SecurityException"%>
<%@ page import="com.ieci.tecdoc.common.exception.ValidationException"%>
<%@ page import="com.ieci.tecdoc.common.keys.ConfigurationKeys"%>

<%@ page import="com.ieci.tecdoc.isicres.desktopweb.Keys"%>
<%@ page import="com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil"%>
<%@ page import="com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils"%>
<%@ page import="com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils"%>
<%@ page import="com.ieci.tecdoc.isicres.desktopweb.utils.IdiomaUtils"%>
<%@ page import="com.ieci.tecdoc.isicres.usecase.UseCaseConf"%>
<%@ page import="com.ieci.tecdoc.isicres.usecase.security.SecurityUseCase"%>
<%@ page import="com.ieci.tecdoc.common.utils.Configurator"%>
<%@ page import="com.ieci.tecdoc.isicres.usecase.book.BookUseCase"%>
<%@ page import="com.ieci.tecdoc.common.exception.BookException"%>
<%@ page import="com.ieci.tecdoc.isicres.session.book.BookSession"%>
<%@ page import="com.ieci.tecdoc.common.isicres.SessionInformation"%>
<%@ page import="com.ieci.tecdoc.isicres.servlets.core.LoginJspHelper" %>
<%@ page import="com.ieci.tecdoc.isicres.servlets.core.LoginFactory" %>

<%@ page import="org.apache.log4j.Logger"%>


<%@ include file="__headerLocale.jsp"%>

<%!Logger _logger = Logger.getLogger(getClass());%>

<%
//clase auxiliar que procesa las llamadas de login que se realizan desde el formulario de esta pagin
LoginJspHelper loginHelper= LoginFactory.getLoginJspHelperInstance();

//appId
Long appId = loginHelper.getAppId(request);

//idioma
String idioma = IdiomaUtils.getInstance().getIdioma(request);

//numIdioma
Long numIdioma = IdiomaUtils.getInstance().getNumIdioma(request);

//archiveId
Integer archiveId = loginHelper.getArchiveId(request);

//folderId
Integer folderId = loginHelper.getFolderId(request);
//distId
Integer distId = loginHelper.getDistId(request);
//useLdap
Boolean useLdap = loginHelper.getUseLdap(request);

//usingOSAuth
Boolean usingOSAuth = loginHelper.getUsingOSAuth(request);


//name
String name = loginHelper.getName(request);

//password (nuevo???)
String password =  "";

//error (nuevo???)
String error="1";

//tries
int tries = loginHelper.getTriesCtrl(request);

//llamamos al codigo para realizar el proceso de login
loginHelper.doWork(request,response);



%>


<%@page import="org.apache.commons.lang.StringUtils"%><html>
<head>
<script language="javascript">
	document.write('<link REL=\"stylesheet\" TYPE=\"text/css\" HREF="' + top.urlSkinCSS + '"/>');
</script>
<link REL="stylesheet" TYPE="text/css" HREF="css/global.css" />
<link REL="stylesheet" TYPE="text/css" HREF="css/font.css" />
<link REL="stylesheet" TYPE="text/css" HREF="css/frame.css" />


<link rel="stylesheet" type="text/css" href="css/estilos.css">

<!--[if lte IE 5]>
	<link rel="stylesheet" type="text/css" href="css/estilos_ie5.css"/>
<![endif]-->

<!--[if IE 6]>
	<link rel="stylesheet" type="text/css" href="css/estilos_ie6.css">
<![endif]-->

<!--[if gte IE 7]>
	<link rel="stylesheet" type="text/css" href="css/estilos_ie7.css">
<![endif]-->

<!--[if lte IE 6]>
	<script type="text/javascript" src="./scripts/iepngfix_tilebg.js"></script>
<![endif]-->
<script type="text/javascript" LANGUAGE="javascript" SRC="scripts/genmsg.js"></script>
<script language="javascript">document.write("<title>" + top.GetIdsLan("IDS_TITULO") + "</title>");</script>
</head>
<script language="javascript">
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

		document.getElementById("ChangePwd").className = "inactive";

		return(true);
   }

   function OnChangePwd()
   {
		top.SetOpacity(document.getElementById("wkspace"), 20);
		top.SetOpacity(document.getElementById("contenido_centrado"), 20);
		top.DisableEvents(window.top);
		document.getElementById("ChangePwdWnd").style.visibility = "visible";
		document.getElementById("ChangePwd").className = "inactive";
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
   	  //recorremos el combo de idiomas para marcar el idioma con el que estamos trabajando
      for(var i=0; i < document.getElementById("selIdioma").options.length; i++)
      {
         if (top.numIdioma == parseInt(document.getElementById("selIdioma").options.item(i).id) )
         {
            document.getElementById("selIdioma").options.item(i).selected = true;
            break;
         }
      }
      return;
   }

   function cambiarIdioma(){
   			var idioma = document.getElementById("selIdioma").value;

			var elem_array = idioma.split(",");
			var idioma_name = elem_array[0];
			var idioma_num = elem_array[1];

			top.location.href = "default.jsp?Idioma="+idioma_name+"&numIdioma="+idioma_num;
		}

   function getParameter(parameter){
	// Obtiene la cadena completa de URL
		var url = parent.location.href;
		/* Obtiene la posicion donde se encuentra el signo ?,
		ahi es donde empiezan los parametros */
		var index = url.indexOf("?");
		/* Obtiene la posicion donde termina el nombre del parametro
		e inicia el signo = */
		index = url.indexOf(parameter,index) + parameter.length;
		/* Verifica que efectivamente el valor en la posicion actual
		es el signo = */
		if (url.charAt(index) == "="){
			// Obtiene el valor del parametro
			var result = url.indexOf("&",index);
			if (result == -1){result=url.length;};
			// Despliega el valor del parametro
			//alert(url.substring(index + 1,result));
			return url.substring(index + 1,result);
		}
	}

   </script>

<body onload="OnWindowLoad(); SelectIdiom(); " tabIndex="-1">

<div id="usuariologin">
	<div id="barra_usuario">
		<h3><label id="textUser"></label></h3>
		<ul class="usuario_plegado">
			<li id="imgPoint" style="visibility:hidden"><label id="textOffice"></label></li>
		</ul>

		<div id="idiomas">
			<select id="selIdioma">
				<option value=",10" id="10">Espa&ntilde;ol</option>
				<option value="GL_,86" id="86">Gallego</option>
				<option value="EU_,45" id="45">Euskera</option>
				<option value="CT_,3" id="3">Catalan</option>
			</select>

			<a href="#">
				<img src="images/refresh.gif" id="Recargar" onclick="cambiarIdioma();"/>
				<script type="text/javascript">document.getElementById("Recargar").title = top.GetIdsLan("IDS_RECARGAR");</script>
				<script type="text/javascript">document.getElementById("Recargar").alt = top.GetIdsLan("IDS_RECARGAR");</script>
			</a>

			<a href="#">
				<img src="images/help.gif" id="helplogin" onclick="javascript:top.OpenHelp();"/>
				<script type="text/javascript">document.getElementById("helplogin").title = top.GetIdsLan("IDS_HELP");</script>
				<script type="text/javascript">document.getElementById("helplogin").alt = top.GetIdsLan("IDS_HELP");</script>
			</a>
		</div>
	</div>
</div>

<table width="100%" border="0" cellspacing="0" cellpadding="0"
	align="center" id="wkspace">
	<tr height="28" valign="middle" align="center" tabIndex="-1">
		<td width="10"></td>
		<td width="220">
		<div align="left" id="ArchiveName" class="ArchiveName"></div>
		</td>
		<td id="MenuSep" style="visibility: hidden" width="2"><img
			src="images/sep.gif" width="2" height="28" border="0"></td>
	</tr>
</table>
<form
	Action="login.jsp?AppId=<%=appId%>&Idioma=<%=idioma%>&numIdioma=<%=numIdioma%>&ArchiveId=<%=archiveId%>&FolderId=<%=folderId%>&DistId=<%=distId%>&UseLDAP=<%=useLdap%>&UsingOSAuth=<%=usingOSAuth%>"
	Method="post" id="Form1" Method="post" Name="LoginForm"
	OnSubmit="return(ValidateLoginForm());">

	<div id="contenido_centrado">

		<div class="ficha_fixed">
			<div class="encabezado"><div class="left_encabezado"><h3>&nbsp;</h3></div></div> <!-- fin encabezado -->

			<div class="cuerpo">
				<div class="contenido_cuerpo_login">
					<div class="seccion_login">
							  <p class="fila">&nbsp;</p>
				              <p class="fila_sub">
				                <label class="login">
				                	<script language="javascript">document.write(top.GetIdsLan("IDS_USER"));</script>
				                </label>
				                <input class="login" Name="NameCtrl" id="NameCtrl" value="<%=name%>" Maxlength="254">
				              </p>
				              <p class="fila_sub">
				                <label class="login"><script language="javascript">document.write( top.GetIdsLan("IDS_PASSWORD" ) )</script></label>
				                <input class="login" name="PasswordCtrl" id="PasswordCtrl" type="password" value="<%=password%>" maxlength="254">
				              </p>
				              <p class="fila">&nbsp;</p>
				              <p class="fila_right">
								<input class="botonFondo" id="SubmitCtrl" name="SubmitCtrl" Type="Submit" Value="">
									<script language="javascript">document.getElementById("SubmitCtrl").value = top.GetIdsLan("IDS_BUTTON_OK");</script>
								</input>
				              </p>

					</div> <!-- fin seccion -->
				</div> <!-- contenido_cuerpo -->
				<p class="filaChangePsw" align="right">
             		<a  href='#' onclick="if(this.className!='inactive'){OnChangePwd()}" id="ChangePwd" class="active">
						<SCRIPT language="javascript">document.write( top.GetIdsLan( "IDS_CHANGE_PWD") )</SCRIPT>
					</a>
            	</p>
			</div> <!-- fin cuerpo -->
		</div> <!-- fin ficha_fixed -->

		<div id="copyright">
		    <script	language="javascript">document.write(top.GetIdsLan("IDS_COPYRIGHT"));</script>
	    </div>
	</div>

	<input name="ErrorCtrl" id="ErrorCtrl" Type="Hidden" Value="<%=error%>"> <input
		id="PasswordCtrlCrypt" name="PasswordCtrlCrypt" Type="Hidden" Value=""> <input
		id="TriesCtrl" name="TriesCtrl" Type="Hidden" Value="<%=tries%>">
</form>
<iframe id="ChangePwdWnd" name="ChangePwdWnd" class="Style6"
	SRC="blank.htm" frameborder="0" scrolling="no"
	style="position: absolute; top: 30%; left: 30%; width: 445px; height: 200px; visibility: hidden;" />
</body>
</html>
<%
	//}
%>