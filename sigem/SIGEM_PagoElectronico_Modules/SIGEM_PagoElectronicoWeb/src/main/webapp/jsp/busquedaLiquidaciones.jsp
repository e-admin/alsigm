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
<script language="Javascript">
function consultar(referencia){
	location.href = 'consultaLiquidacion.do?referencia=' + referencia;
}

function cargar(referencia){
	location.href = 'cargarFormulario.do?referencia=' + referencia;
}
</script>
</head>
<body>
<div id="contenedora">
  <!-- Inicio Cabecera -->
	<jsp:include page="cabecera.jsp"/>

  <div id="migas">
  	<img src="img/<%=rutaImagenes%>flecha_migas.gif" width="13" height="9" class="margen" alt=""/>
  	<bean:message key="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.label"/> &gt;<span class="activo"> <bean:message key="ieci.tecdoc.sgm.pe.struts.common.menu.listadoLiquidaciones"/></span>
  </div>
  <!-- Fin Cabecera -->
  <!-- Inicio Contenido -->
  <div id="contenido">
    <div class="cuerpo">
      <div class="cuerporight">
        <div class="cuerpomid">
          <h1><bean:message key="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.liquidacionesPendientes"/></h1>
    <div class="submenu3">
      <ul>
        <li class="submen1off"><img src="img/<%=rutaImagenes%>subme3_off.gif" alt=""/>
        <a href="<html:rewrite page="/login.do" />">
											<bean:message key="ieci.tecdoc.sgm.pe.struts.common.menu.liquidacionesPendientes"/>
											</a>
        </li>
        <li class="submen1on"><img src="img/<%=rutaImagenes%>subme3_of_on.gif" alt=""/><bean:message key="ieci.tecdoc.sgm.pe.struts.common.menu.listadoLiquidaciones"/></li>

        <li class="submen1off"><img src="img/<%=rutaImagenes%>subme3_on_of.gif" alt=""/>
        <a href="<html:rewrite page="/buscarTasas.do" />">
											<bean:message key="ieci.tecdoc.sgm.pe.struts.common.menu.autoLiquidaciones"/>
											</a>
        <img src="img/<%=rutaImagenes%>subme3_of_0.gif" alt=""/></li>
      </ul>
    </div>
          <div class="cuadro">

	<div>
	<div class="submenu3"><h1><bean:message key="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.search"/></h1>
      </div>
       <div class="cuadro">
       	<html:form action="/buscarLiquidaciones.do">
       		<table border="0" cellspacing="2" cellpadding="2">
       			<tr>
       				<td><bean:message key="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.referencia"/>:</td>
       				<td><html:text property="referencia"></html:text></td>
					<td><bean:message key="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.nrc"/>:</td>
       				<td><html:text property="nrc"></html:text></td>
					<td><bean:message key="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.estado"/>:</td>
       				<td>
       					<html:select property="estado">
       						<html:option value=""></html:option>
       						<html:option value="00">
	       						<bean:message key="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.estado.pendiente"/>
       						</html:option>
       						<html:option value="01">
	       						<bean:message key="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.estado.pagado"/>
							</html:option>
       					</html:select>
       				</td>
       				<td>&nbsp;</td>
       				<td><html:submit styleId="boton" >
		       				<bean:message key="ieci.tecdoc.sgm.pe.struts.common.search"/>
						</html:submit></td>
       			</tr>
       		</table>
       	</html:form>
       </div>
       <div width="100%" height="17px" style="border-bottom: 1px solid #c6c9d5;">&nbsp;</div>
       <div class="cuadro">
						<display:table name="liquidaciones" uid="liquidacion"
							pagesize="10"
							requestURI="buscarLiquidaciones.do"
							export="true"
							class="tablaListado"
							sort="list">
								<display:column property="referencia" titleKey="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.referencia" sortable="true" headerClass="cabeceraTabla"/>
								<display:column property="nombre" titleKey="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.concepto" sortable="true" headerClass="cabeceraTabla"/>
								<display:column property="periodo" titleKey="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.periodo" sortable="true" headerClass="cabeceraTabla"/>
								<display:column property="importe" titleKey="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.importeeneuros" sortable="true" headerClass="cabeceraTabla"/>
								<display:column sortProperty="estado" media="html" titleKey="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.estado" headerClass="cabeceraTabla" sortable="true">
										<logic:equal name="liquidacion" property="estado" value="01">
										 <p class="textoVerde">
											<bean:message key="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.estado.pagado"/>
										</p>
										</logic:equal>
										<logic:notEqual name="liquidacion" property="estado" value="01">
											<logic:equal name="liquidacion" property="vencimiento" value="00">
											<p class="textoRojo">
												<bean:message key="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.estado.pendiente"/>
											</p>
											</logic:equal>
											<logic:equal name="liquidacion" property="vencimiento" value="01">
											<p class="textoRojo">
												<bean:message key="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.vencimiento.plazovencido"/>
											</p>
											</logic:equal>
											<logic:equal name="liquidacion" property="vencimiento" value="02">
											<p class="textoAmarillo">
												<bean:message key="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.vencimiento.menosdeunmes"/>
											</p>
											</logic:equal>
										</logic:notEqual>
								</display:column>
								<display:column media="html" titleKey="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.consulta.title" headerClass="cabeceraTabla">
									<logic:equal name="liquidacion" property="estado" value="00">
										<!-- a id="boton" href="<html:rewrite page="/cargarFormulario.do" paramName="liquidacion" paramProperty="referencia" paramId="referencia"/>">
										<bean:message key="ieci.tecdoc.sgm.pe.struts.common.realizarPago"/>
										</a -->
										<input id="boton" type="button" onclick="javascript:cargar('<bean:write name="liquidacion" property="referencia"/>')" value="<bean:message key="ieci.tecdoc.sgm.pe.struts.common.realizarPago"/>"/>
									</logic:equal>
									<logic:equal name="liquidacion" property="estado" value="01">
										<!-- a id="boton" href="<html:rewrite page="/consultaLiquidacion.do" paramName="liquidacion" paramProperty="referencia" paramId="referencia"/>">
										<bean:message key="ieci.tecdoc.sgm.pe.struts.common.consultarPago"/>
										</a-->
										<input id="boton" type="button" onclick="javascript:consultar('<bean:write name="liquidacion" property="referencia"/>')" value="<bean:message key="ieci.tecdoc.sgm.pe.struts.common.consultarPago"/>"/>
									</logic:equal>
								</display:column>
						</display:table>
					</div>
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
