<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag-el.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>


<html>
<head>
	<link rel="stylesheet" type="text/css" href="css/xtreeUnidades.css">
	<link href="css/estilos.css" rel="stylesheet" type="text/css" />
	<link href="css/adminApp.css" rel="stylesheet" type="text/css" />
	<link href="css/directorioComun.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript">

		function ocultarIframeDC(){
			window.parent.document.getElementById("iframeBusquedaUnidadesOrganicasDirectorioComun").style.display="none";

		}

		function returnToTop(idUnidadOrganica, nombre){
			window.parent.opener.document.forms[0].codeUnidadTramit.value = idUnidadOrganica;
			window.parent.opener.document.forms[0].nameUnidadTramit.value = nombre;
			window.parent.close();
		}

		function buscarUnidadesOrganicas(){
			var codigo = trim(document.getElementById("codigo").value);
			var nombre = trim(document.getElementById("nombre").value);
			if(codigo=="" && nombre=="")
			{
				alert('<bean:message key="ieci.tecdoc.sgm.rpadmin.error.mensaje.required.search.criterio"/>');
			}else{
				document.forms[0].submit();
			}
		}

		function trim (myString)
		{
			return myString.replace(/^\s+/g,'').replace(/\s+$/g,'');
		}

		function cargar(){
			document.getElementById("codEntity").value = window.parent.opener.document.forms[0].codEntidadReg.value;
		}
	</script>
</head>
<body onLoad="cargar()">


<html:form action="/busquedaUnidadesOrganicasDirectorioComun.do" method="post">
	<div id="search">
		<p>
			<label for="codigo" class="labelBusquedaDC"><bean:message key="ieci.tecdoc.sgm.rpadmin.intercambioRegistral.listado.codigo"/></label>
			<html:text styleId="codigo" styleClass="textinput" property="codigo" title="Código"/>
		</p>
		<p>
			<label for="nombre" class="labelBusquedaDC"><bean:message key="ieci.tecdoc.sgm.rpadmin.intercambioRegistral.listado.nombre"/></label>
			<html:text styleId="nombre" styleClass="textinput" property="nombre" title="Nombre"/>
		</p>
		<p>
			<table align="center">
				<tr>
					<td align="right" class="col_buscar" onclick="buscarUnidadesOrganicas();"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.buscar"/></td>
					<td align="right" class="col_cancelar" onclick="cancelarBusqueda();"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.cancelar"/></td>
				</tr>
			</table>
		</p>
		<html:hidden styleId="codEntity" property="codEntity" title="codEntity"/>
	</div>
</html:form>
<div id="results" style="overflow:auto; height:230px; width:100% ">
	<logic:present name="listaUnidadesOrganicas">
		<display:table id="unidadOrganica" name="listaUnidadesOrganicas"
			class="displaytable" pagesize="10"
			requestURI="/busquedaUnidadesOrganicasDirectorioComun.do"
			defaultsort="1" defaultorder="ascending">
			<display:column title="Codigo" sortable="true">
				<a href="#"
					onclick="javascript:returnToTop('<bean:write name="unidadOrganica" property="id" />','<bean:write name="unidadOrganica" property="nombre" />');return false;"><bean:write
					name="unidadOrganica" property="id" /></a>
			</display:column>
			<display:column title="Nombre" sortable="true">
				<a href="#"
					onclick="javascript:returnToTop('<bean:write name="unidadOrganica" property="id" />', '<bean:write name="unidadOrganica" property="nombre" />');return false;"><bean:write
					name="unidadOrganica" property="nombre" /></a>
			</display:column>
			<display:column title="Unidad Raiz" sortable="true">
				<a href="#"
					onclick="javascript:returnToTop('<bean:write name="unidadOrganica" property="id" />', '<bean:write name="unidadOrganica" property="nombre" />');return false;"><bean:write
					name="unidadOrganica" property="nombreUnidadOrganicaPrincipal" /></a>
			</display:column>
		</display:table>
	</logic:present>
</div>
</body>
</html>
