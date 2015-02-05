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
<div id="contenedora">
  <!-- Inicio Cabecera -->
	<jsp:include page="cabecera.jsp"/>
		
  <div id="migas">
  <img src="img/<%=rutaImagenes%>flecha_migas.gif" width="13" height="9" class="margen" alt=""/><bean:message key="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.label"/> &gt;<span class="activo"> <bean:message key="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.pendientes"/></span> </div>
  <!-- Fin Cabecera -->
  <!-- Inicio Contenido -->
  <div id="contenido">
    <div class="cuerpo">
      <div class="cuerporight">
        <div class="cuerpomid">
          <h1><bean:message key="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.liquidacionesPendientes"/></h1>
          <div class="submenu3">
            <ul>
              <li class="submen1on"><img src="img/<%=rutaImagenes%>subme3_on.gif" alt=""/><bean:message key="ieci.tecdoc.sgm.pe.struts.common.menu.liquidacionesPendientes"/></li>
              <li class="submen1off"><img src="img/<%=rutaImagenes%>subme3_on_of.gif" alt=""/>
	              <a href="<html:rewrite page="/buscarLiquidaciones.do" />">
											<bean:message key="ieci.tecdoc.sgm.pe.struts.common.menu.listadoLiquidaciones"/>
											</a>
				  </li>
              <li class="submen1off"><img src="img/<%=rutaImagenes%>subme3_of_of.gif" alt=""/>
	              <a href="<html:rewrite page="/buscarTasas.do" />">
											<bean:message key="ieci.tecdoc.sgm.pe.struts.common.menu.autoLiquidaciones"/>
											</a>
	              <img src="img/<%=rutaImagenes%>subme3_of_0.gif" alt=""/>
              </li>
            </ul>
          </div>
          <div class="cuadro">
						<display:table name="listaPendientes" uid="fila" 
							pagesize="10" 
							requestURI="liquidacionesPendientes.do"
							export="true" 
							class="tablaListado"
							sort="list">
								<display:column property="referencia" titleKey="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.referencia" sortable="true" headerClass="cabeceraTabla"/>
								<display:column property="nombre" titleKey="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.concepto" sortable="true" headerClass="cabeceraTabla"/>								
								<display:column property="periodo" titleKey="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.periodo" sortable="true" headerClass="cabeceraTabla"/>
								<display:column property="importe" titleKey="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.importeeneuros" sortable="true" headerClass="cabeceraTabla"/>								
								<display:column sortProperty="vencimiento" media="html" titleKey="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.vencimiento" headerClass="cabeceraTabla" sortable="true">																								
										<logic:equal name="fila" property="vencimiento" value="00">
										 <p class="textoVerde">
											<bean:message key="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.vencimiento.enplazo"/>
										</p>
										</logic:equal>
										<logic:equal name="fila" property="vencimiento" value="01">
										<p class="textoRojo">
											<bean:message key="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.vencimiento.plazovencido"/>
										</p>
										</logic:equal>
										<logic:equal name="fila" property="vencimiento" value="02">
										<p class="textoAmarillo">
											<bean:message key="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.fields.vencimiento.menosdeunmes"/>
										</p>
										</logic:equal>										
								</display:column>
								<display:column media="html" titleKey="ieci.tecdoc.sgm.pe.struts.common.liquidaciones.formulario.title" headerClass="cabeceraTabla">		
										<a id="boton" href="<html:rewrite page="/cargarFormulario.do" paramName="fila" paramProperty="referencia" paramId="referencia"/>">
										<bean:message key="ieci.tecdoc.sgm.pe.struts.common.realizarPago"/>
										</a>														
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
