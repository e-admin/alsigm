<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="es.ieci.tecdoc.isicres.admin.core.locale.LocaleFilterHelper"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
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

<title>
	<bean:message key="ieci.tecdoc.isicres.rpadmin.titulo"/>
</title>
</head>
<script language="javascript">
   	function OnWindowLoad()
   	{

		//obtenemos variable del idioma
		var idIdioma = getParameter('numIdioma');

		//comprobamos la variable de IdIdioma venga con datos
		if((idIdioma != null) && (idIdioma != 'undefined')){
			document.getElementById("LoginForm").action = "login.do?numIdioma="+idIdioma;
		}else{
			//cogemos español por defecto
			document.getElementById("LoginForm").action = "login.do?numIdioma=10";
		}

		return;
   	}

   	function ValidateLoginForm()
   	{
		if (document.LoginForm.NameCtrl.value == "") {
			alert("<bean:message key='ieci.tecdoc.isicres.rpadmin.mensaje.must_fill_user'/>");
			document.getElementById("NameCtrl").focus();

			return(false);
		}
		else if (document.LoginForm.PasswordCtrl.value == "") {
			alert("<bean:message key='ieci.tecdoc.isicres.rpadmin.mensaje.must_fill_password'/>");
			document.getElementById("PasswordCtrl").focus();

			return(false);
		}

		return(true);
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
   	  //obtenemos variable del idioma
   	  var idIdioma = getParameter('numIdioma');
	  //comprobamos la variable de IdIdioma venga con datos
   	  if((idIdioma != null) && (idIdioma != 'undefined')){
	   	  //recorremos el combo de idiomas para marcar el idioma con el que estamos trabajando
	      for(var i=0; i < document.getElementById("selIdioma").options.length; i++)
	      {
	         if (idIdioma == parseInt(document.getElementById("selIdioma").options.item(i).id) )
	         {
	            document.getElementById("selIdioma").options.item(i).selected = true;
	            break;
	         }
	      }
   	  }

      return;
   }

   function cambiarIdioma(){
   			var idioma = document.getElementById("selIdioma").value;

			var elem_array = idioma.split(",");
			var idioma_name = elem_array[0];
			var idioma_num = elem_array[1];

			top.location.href = "login.do?Idioma="+idioma_name+"&numIdioma="+idioma_num;
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

	function abreAyuda( url ) {

		var features = "directories=0,top=50px,left=50px,height=520px,width=930px,location=0,menubar=0";
		features +="resizable=0,scrollbars=0,status=no,titlebar=no,toolbar=no";

		window.open(url, "ventanaAyuda", features);
	}

   </script>

<body onload="OnWindowLoad(); SelectIdiom(); " tabIndex="-1">

	<!--<div id="cabecera_left">
		<h1>Informatica el Corte Inglés</h1>
	</div>
	<div id="cabecera_right">
		<h2>invesSicres</h2>
	</div> -->

	<div id="cabecera_login">
	<div id="appHeaderLogin" cellpadding="0">
			<div id="logo_app_login">
				<img  src="./img/inicio-ieci.png" id="logo_app_img_login"/>
			</div>
			<div id="logo_cia_login">
				<img  src="./img/inicio-nombre.png" id="logo_app_img2_login" align="right"/>
			</div>
	</div>
	</div>
	<div id="usuariologin">
		<div id="barra_usuario">
			<h3><bean:message key="ieci.tecdoc.sgm.rpadmin.titulo"/></h3>
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
					<img src="img/refresh_2.gif" id="Recargar" onclick="cambiarIdioma();"
						alt="<bean:message key='ieci.tecdoc.isicres.rpadmin.recarga'/>"
						title="<bean:message key='ieci.tecdoc.isicres.rpadmin.recarga'/>"/>
				</a>

				<a href="#">
					<% String ayuda = "/ayuda/" + LocaleFilterHelper.getCurrentLocale(request).getLanguage() + "/index.html";%>

					<img src="img/help.gif" id="helplogin" onclick="abreAyuda('<html:rewrite page="<%=ayuda%>" />')"
						alt="<bean:message key='ieci.tecdoc.isicres.rpadmin.help'/>"
						title="<bean:message key='ieci.tecdoc.isicres.rpadmin.help'/>"/>
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
				src="img/sep.gif" width="2" height="28" border="0"></td>
		</tr>
	</table>

	<form
		Action="login.do?numIdioma=10"
		Method="post" id="LoginForm" Method="post" Name="LoginForm"
		OnSubmit="return(ValidateLoginForm());">

		<div id="contenido_centrado">

			<div class="ficha_fixed">
				<div class="encabezado"><div class="left_encabezado"><h3>&nbsp;</h3></div></div> <!-- fin encabezado -->

				<div class="cuerpo">

					<div class="contenido_cuerpo_login">
						<div class="seccion_login">
								  <p class="fila_error">
								  	<label class="error"><html:errors/></label>
			            		  </p>
					              <p class="fila_sub">
					                <label class="login">
					                	<bean:message key="ieci.tecdoc.isicres.rpadmin.usuario"/>
					                </label>
					                <input class="login" Name="NameCtrl" id="NameCtrl" value="" Maxlength="254">
					              </p>
					              <p class="fila_sub">
					                <label class="login">
					                	<bean:message key="ieci.tecdoc.isicres.rpadmin.password"/>
					                </label>
					                <input class="login" name="PasswordCtrl" id="PasswordCtrl" type="password" value="" maxlength="254">
					              </p>
					              
					              <%--
					              Solo se muestra si esta configurada autenticacion por LDAP 
					              --%>
					              <logic:equal value="true" name="isLdapAuthenticationPolicy" scope="session">					              
						              <p class="fila_sub">
						                <label class="login">
											<bean:message key="ieci.tecdoc.isicres.rpadmin.conectarse.superusuario"/>
						                </label>
						              	<input type="checkbox" name="connectAsSuperUser" id="connectAsSuperUser"/>
						              </p>					              
					              </logic:equal>
					              
					              <p class="fila_right">
									<input class="botonFondo" id="SubmitCtrl" name="SubmitCtrl"
											Type="Submit" Value="<bean:message key='ieci.tecdoc.isicres.rpadmin.botones.aceptar'/>">
					              </p>
						</div> <!-- fin seccion -->
					</div> <!-- contenido_cuerpo -->
					<br/>
				</div> <!-- fin cuerpo -->
			</div> <!-- fin ficha_fixed -->

			<div id="copyright">
				<bean:message key="ieci.tecdoc.isicres.rpadmin.copyright"/>
		    </div>
		</div>
	</form>
</body>
</html>
<%
	//}
%>