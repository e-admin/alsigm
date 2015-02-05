<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%
String rutaEstilos = (String)session.getAttribute("PARAMETRO_RUTA_ESTILOS");
if (rutaEstilos == null) rutaEstilos = "";
String rutaImagenes = (String)session.getAttribute("PARAMETRO_RUTA_IMAGENES");
if (rutaImagenes == null) rutaImagenes = "";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="IECISA" />
<title>sigem</title>
<link href="css/<%=rutaEstilos%>estilos.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language="javascript" src="js/idioma.js"></script>
</head>
<body>
<script language="javascript">
function enviar(idtasa, identidad){
	var url = "<html:rewrite page="/cargarFormularioAutoliquidacion.do"/>";
	var formulario = document.getElementById("formulario");
	formulario.action = url;
	formulario.target="";
	var entrada = document.getElementById("idTasa");
	entrada.value = idtasa;
	entrada = document.getElementById("idEntidad");
	entrada.value = identidad;
	formulario.submit();
}

function consultar(referencia){
	location.href = 'consultaAutoliquidacion.do?referencia=' + referencia;
}
</script>
<form action="" id="formulario" method="post">
	<input name="idTasa" type="hidden" value="" id="idTasa"/>
	<input name="idEntidadEmisora" type="hidden" value="" id="idEntidad" />
</form>
<div id="contenedora">
  <!-- Inicio Cabecera -->
	<jsp:include page="cabecera.jsp"/>

  <div id="migas">
  <img src="img/<%=rutaImagenes%>flecha_migas.gif" width="13" height="9" class="margen" alt=""/><bean:message key="ieci.tecdoc.sgm.pe.struts.common.menu.autoLiquidaciones"/> &gt;<span class="activo"> <bean:message key="ieci.tecdoc.sgm.pe.struts.common.search.miga"/></span> </div>
  <!-- Fin Cabecera -->
  <!-- Inicio Contenido -->
  <div id="contenido">
    <div class="cuerpo">
      <div class="cuerporight">
        <div class="cuerpomid">
          <h1><bean:message key="ieci.tecdoc.sgm.pe.struts.common.autoliquidaciones.first.title"/></h1>
    <div class="submenu3">
      <ul>
        <li class="submen1off"><img src="img/<%=rutaImagenes%>subme3_off.gif" alt=""/>
<a href="<html:rewrite page="/login.do" />">
											<bean:message key="ieci.tecdoc.sgm.pe.struts.common.menu.liquidacionesPendientes"/>
											</a>
        </li>

        <li class="submen1off"><img src="img/<%=rutaImagenes%>subme3_of_of.gif" alt="" alt=""/>
											<a href="<html:rewrite page="/buscarLiquidaciones.do" />">
											<bean:message key="ieci.tecdoc.sgm.pe.struts.common.menu.listadoLiquidaciones"/>
											</a>
		  </li>
        <li class="submen1on"><img src="img/<%=rutaImagenes%>subme3_of_on.gif" alt=""/><bean:message key="ieci.tecdoc.sgm.pe.struts.common.menu.autoLiquidaciones"/><img src="img/<%=rutaImagenes%>subme3_on_0.gif" /></li>
      </ul>
    </div>
          <div class="cuadro">

	<div>
	<div class="submenu3"><h1><bean:message key="ieci.tecdoc.sgm.pe.struts.common.autoliquidaciones.title"/></h1>
      </div>
						<display:table name="tasas" uid="tasa"
							pagesize="10"
							requestURI="buscarTasas.do"
							export="true"
							class="tablaListado"
							sort="list">
								<display:column property="nombre" titleKey="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.concepto" sortable="true" headerClass="cabeceraTabla"/>
								<display:column property="modelo" titleKey="ieci.tecdoc.sgm.pe.struts.common.autoliquidaciones.fields.modelo" sortable="true" headerClass="cabeceraTabla"/>
								<display:column property="idEntidad" titleKey="ieci.tecdoc.sgm.pe.struts.common.autoliquidaciones.fields.entidadEmisora" sortable="true" headerClass="cabeceraTabla"/>
								<display:column media="html" titleKey="ieci.tecdoc.sgm.pe.struts.common.autoliquidaciones.formulario.title" headerClass="cabeceraTabla" sortable="false">
										<input id="boton" type="button" onclick="javascript:enviar('<bean:write name="tasa" property="idTasa"/>','<bean:write name="tasa" property="idEntidad"/>')" value="<bean:message key="ieci.tecdoc.sgm.pe.struts.common.realizarPago"/>"/>
								</display:column>
						</display:table>
	<div style="height:20px;">
	</div>
	<div class="submenu3"><h1> <bean:message key="ieci.tecdoc.sgm.pe.struts.common.autoliquidaciones.historioPagos"/> </h1>
      </div>
       <div class="cuadro">
       	<html:form action="/buscarTasas.do">
       		<table border="0" cellspacing="2" cellpadding="2">
       			<tr>
       				<td><bean:message key="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.referencia"/>:</td>
       				<td><html:text property="referencia"></html:text></td>
					<td><bean:message key="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.nrc"/>:</td>
					<td><html:text property="nrc"></html:text></td>
       				<td>&nbsp;</td>
       				<td><html:submit styleId="boton" ><bean:message key="ieci.tecdoc.sgm.pe.struts.common.search"/></html:submit></td>
       			</tr>
       		</table>
       	</html:form>
       </div>
						<display:table name="liquidaciones" uid="liquidacion"
							pagesize="10"
							requestURI="buscarTasas.do"
							export="true"
							class="tablaListado"
							sort="list">
								<display:column property="referencia" titleKey="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.referencia" sortable="true" headerClass="cabeceraTabla"/>
								<display:column property="nombre" titleKey="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.concepto" sortable="true" headerClass="cabeceraTabla"/>
								<display:column property="fechaPago" titleKey="ieci.tecdoc.sgm.pe.struts.common.autoliquidaciones.fechaPago" sortable="true" headerClass="cabeceraTabla"/>
								<display:column property="importe" titleKey="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.importeeneuros" sortable="true" headerClass="cabeceraTabla"/>
								<display:column media="html" headerClass="cabeceraTabla" titleKey="ieci.tecdoc.sgm.pe.struts.common.consultaPago">
										<!-- a id="boton" href="<html:rewrite page="/consultaAutoliquidacion.do" paramName="liquidacion" paramProperty="referencia" paramId="referencia"/>">
										<bean:message key="ieci.tecdoc.sgm.pe.struts.common.consultarPago"/>
										</a -->
										<input id="boton" type="button" onclick="javascript:consultar('<bean:write name="liquidacion" property="referencia"/>')" value="<bean:message key="ieci.tecdoc.sgm.pe.struts.common.consultarPago"/>"/>
								</display:column>
						</display:table>

          </div>
        </div>
      </div>
    </div>
    <div class="cuerpobt">
      <div class="cuerporightbt">
        <div class="cuerpomidbt"></div>
      </div>
    </div>
  </div>
  <!-- Fin Contenido -->
  <div id="pie"></div>
</div>
</body>
</html>
