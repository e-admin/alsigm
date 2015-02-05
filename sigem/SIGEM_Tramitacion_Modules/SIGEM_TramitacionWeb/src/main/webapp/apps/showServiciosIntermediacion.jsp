<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page import="ieci.tecdoc.sgm.core.config.ports.PortsConfig"%>
<%@ page import="ieci.tdw.ispac.ispacmgr.intermediacion.services.Constantes"%>


<div id="contenido" class="move" >
<div class="ficha">
<div class="encabezado_ficha">
<div class="titulo_ficha">
<h4><bean:message key="svd.servicios" /></h4>

<script language='JavaScript' type='text/javascript'><!--
	function SelectValue(servicio, nombre) {
		document.defaultForm.action += "?codigoCertificado="+servicio+"&nombreCertificado="+nombre;
		document.defaultForm.submit();
	}
	
	function closeFrame(){
		//Resituar en http, saliendo de https para poder invocar al top.hideFrame
		document.location.href = '<%="http://"+request.getServerName()+":"+PortsConfig.getHttpPort()+request.getContextPath()+"/apps/closeFrameHttps.jsp?refresh=true"%>';		
	}
//--></script>

<div class="acciones_ficha">
	<a href="#" id="btnCancel" onclick='closeFrame()' class="btnCancel"><bean:message key="common.message.close" /></a>
	
</div>
<%--fin acciones ficha --%></div>
<%--fin titulo ficha --%></div>
<%--fin encabezado ficha --%>


	
<div class="cuerpo_ficha">
<div class="seccion_ficha">
<html:form action="peticionSincrona.do">
				
				<!-- displayTag con formateador -->
				<bean:define name="Formatter" id="formatter"
					type="ieci.tdw.ispac.ispaclib.bean.BeanFormatter" />
				<display:table
					name="ValueList" id="value" export='<%=formatter.getExport()%>'
					class='<%=formatter.getStyleClass()%>' sort='<%=formatter.getSort()%>'
					pagesize='<%=formatter.getPageSize()%>'
					defaultorder='<%=formatter.getDefaultOrder()%>'
					defaultsort='<%=formatter.getDefaultSort()%>' requestURI=''>
				
					<logic:iterate name="Formatter" id="format"
						type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
				
						<!-- ENLACE -->
						<logic:equal name="format" property="fieldType" value="LINK">
				
							<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								sortable='<%=format.getSortable()%>'
								sortProperty='<%=format.getPropertyName()%>'
								class='<%=format.getColumnClass()%>'
								decorator='<%=format.getDecorator()%>'
								comparator='<%=format.getComparator()%>'>
				
								<bean:define name="value" property="property(NOMBRE)" id="nombre" />
								<bean:define name="value" property="property(SERVICIO)" id="servicio" />
								<bean:define name="value" property="property(EMISOR)" id="emisor" />
								<bean:define name="value" property="property(DNI)" id="dni" />
								<bean:define name="value" property="property(NOMBREDNI)" id="nombreDni" />
								<bean:define name="value" property="property(DATOSESPECIFICOS)" id="datosEspecificos" /> 
				
								<html:hidden property="dniValue" value="<%=dni.toString()%>" />
								<html:hidden property="nombreDniValue" value="<%=nombreDni.toString()%>" />
								<html:hidden property="emisorServicio" value="<%=emisor.toString()%>" />
				
								<c:set var="datosEspecificos"><%=datosEspecificos.toString()%></c:set>
								
								<c:choose>
									<c:when test="${ datosEspecificos == 'SI'}">
										<a class="element" href='<%=request.getAttribute(Constantes.CLIENTE_LIGERO_WEB_URL_PARAM) %>' target="blank">[Cliente Ligero]<%=format.formatProperty(value)%></a>
									</c:when>
									<c:otherwise>
										<a class="element" href="javascript:SelectValue('<%=servicio.toString()%>', '<%=nombre.toString()%>')"><%=format.formatProperty(value)%></a>
									</c:otherwise>
								</c:choose>
				
							</display:column>
				
						</logic:equal>
				
						<!-- DATO DE LA LISTA -->
						<logic:equal name="format" property="fieldType" value="LIST">
				
							<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								sortable='<%=format.getSortable()%>'
								sortProperty='<%=format.getPropertyName()%>'
								class='<%=format.getColumnClass()%>'
								decorator='<%=format.getDecorator()%>'
								comparator='<%=format.getComparator()%>'>
				
								<%=format.formatProperty(value)%>
				
							</display:column>
				
						</logic:equal>
				
					</logic:iterate>
				
				</display:table>
</html:form>
</div>
<%--seccion ficha --%> 
</div>
<%--fin cuerpo ficha --%>
</div>
<%--fin  ficha --%>
<div>
<%--fin contenido --%>

<script>
	positionMiddleScreen('contenido');
	$(document).ready(function(){
		$("#contenido").draggable();
	});
</script>
