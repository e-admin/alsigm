<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<bean:struts id="actionMapping" mapping="/gestionMotivosConsulta" />
<c:set var="formName" value="${actionMapping.name}" />
<c:set var="form" value="${requestScope[formName]}" />
<c:set var="listaMotivosConsulta" value="${sessionScope[appConstants.solicitudes.LISTA_MOTIVOS_KEY]}" />
<c:set var="ciudadano" value="${sessionScope[appConstants.consultas.CHECK_CIUDADANO]}"/>
<c:set var="organo" value="${sessionScope[appConstants.consultas.CHECK_ORGEXTERNO]}"/>
<c:set var="investigador" value="${sessionScope[appConstants.consultas.CHECK_INVESTIGADOR]}"/>
<c:set var="consulta" value="${sessionScope[appConstants.solicitudes.MOTIVO_CONSULTA]}" />

	<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

		<tiles:put name="boxTitle" direct="true">
			<bean:message key="NavigationTitle_MOTIVOS_CONSULTA"/>
		</tiles:put>
		<tiles:put name="buttonBar" direct="true">
		
			<table cellpadding=0 cellspacing=0>
			<tr>
				<td>
					<c:url var="createURL" value="/action/gestionMotivosConsulta">
						<c:param name="method" value="nuevoMotivo" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${createURL}" escapeXml="false"/>">						
	                    <html:img page="/pages/images/newDoc.gif" styleClass="imgTextMiddle"
								altKey="archigest.archivo.crear" titleKey="archigest.archivo.crear"/>
						<bean:message key="archigest.archivo.crear"/>
	                </a>
	            </td>
				<td width="20">&nbsp;</td>
				<td>
					<tiles:insert definition="button.closeButton" flush="true"/>
				</td>
			</tr>
			</table>
		</tiles:put>
		
		<c:url var="paginationURL" value="/action/gestionMotivosConsulta" >
			<c:param name="method" value="init"/>
		</c:url>
		<jsp:useBean id="paginationURL" type="java.lang.String" />
		<tiles:put name="boxContent" direct="true">
			<div class="bloque">	
				<div class="separador10">&nbsp;</div>
				<display:table name="pageScope.listaMotivosConsulta" 
						id="motivo" 
						htmlId="listaMotivosConsulta"
						sort="list" 
						style="width:98%;margin-left:auto;margin-right:auto"
						requestURI='<%=paginationURL%>'
						excludedParams="*"
						export="true"
						pagesize="15">
						
					<display:setProperty name="basic.msg.empty_list">
						<bean-el:message key="archigest.archivo.solicitudes.motivos.listaVacia" arg0="${consulta}"/>
					</display:setProperty>											
																								
					<display:column titleKey="archigest.archivo.descripcion" sortProperty="motivo" sortable="true" headerClass="sortable" media="html">
						<c:url var="datosMotivoURL" value="/action/gestionMotivosConsulta">
							<c:param name="method" value="verMotivo"/>
							<c:param name="idMotivo" value="${motivo.id}" />
						</c:url>
					
						<a href='<c:out value="${datosMotivoURL}" escapeXml="false" />' class="tdlink">
							<c:out value="${motivo.motivo}"/>
						</a>																										
					</display:column>	
					<display:column titleKey="archigest.archivo.descripcion"  sortProperty="motivo" sortable="true" headerClass="sortable" media="csv excel xml pdf">
						<c:out value="${motivo.motivo}"/>
					</display:column>				
					<display:column titleKey="archigest.archivo.cf.TipoEntidad" sortProperty="tipoEntidad" sortable="true" headerClass="sortable">
						<c:choose>
			          		<c:when test="${motivo.tipoEntidad == ciudadano}">
			          			<bean:message key="archigest.archivo.consultas.ciudadano"/>
			          		</c:when>
			          		<c:when test="${motivo.tipoEntidad == organo}">
			          			<bean:message key="archigest.archivo.organo"/>
			          		</c:when>
			          		<c:when test="${motivo.tipoEntidad == investigador}">
			          			<bean:message key="archigest.archivo.consultas.investigador"/>
			          		</c:when>
			          	</c:choose>		
					</display:column>
					<display:column titleKey="archigest.archivo.consultas.tipoConsulta" sortProperty="tipoConsulta" sortable="true" headerClass="sortable">
						<c:choose>
			          		<c:when test="${motivo.tipoConsulta == 1}">
			          			<bean:message key="archigest.archivo.consultas.tipoConsulta.directa"/>
			          		</c:when>
			          		<c:when test="${motivo.tipoConsulta == 2}">
			          			<bean:message key="archigest.archivo.consultas.tipoConsulta.indirecta"/>
			          		</c:when>
			          	</c:choose>	
					</display:column>
					<display:column titleKey="archigest.archivo.solicitudes.visibilidad" sortProperty="visibilidad" sortable="true" headerClass="sortable">
						<c:choose>
				          		<c:when test="${motivo.visibilidad == 1}">
				          			<bean:message key="archigest.archivo.solicitudes.visibilidad.archivo"/>
				          		</c:when>
				          		<c:when test="${motivo.visibilidad == 2}">
				          			<bean:message key="archigest.archivo.solicitudes.visibilidad.noArchivo"/>
				          		</c:when>
				          		<c:when test="${motivo.visibilidad == 3}">
				          			<bean:message key="archigest.archivo.solicitudes.visibilidad.todos"/>
				          		</c:when>
				          	</c:choose>		
					</display:column>										
				</display:table>
				<div class="separador10">&nbsp;</div>
			</div>
		</tiles:put>
	</tiles:insert>