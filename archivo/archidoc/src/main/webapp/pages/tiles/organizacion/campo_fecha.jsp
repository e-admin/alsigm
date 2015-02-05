<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<archigest:calendar-config imgDir="../images/calendario/" scriptFile="../scripts/calendar.js"/>

<bean:struts id="mappingGestionFechas" mapping="/gestionFechasOrganizacionAction" />
<c:set var="formName" value="${mappingGestionFechas.name}" />
<jsp:useBean id="formName" type="java.lang.String" />
<c:set var="organizacion" value="${sessionScope[appConstants.organizacion.ORGANIZACION_KEY]}" />	
<c:set var="vigente" value="${organizacion.estado==2}"/>
<c:set var="historico" value="${organizacion.estado==3}"/>

<html:form action="/gestionFechasOrganizacionAction" >
	<input type="hidden" name="method" value="guardarEstado">
	<c:if test="${historico}"><html:hidden property="inicio"/></c:if>
	
	<tiles:insert template="/pages/tiles/PABoxLayout.jsp" flush="false">
			
		<tiles:put name="boxTitle" direct="true">
			<c:choose>				
				<c:when test="${vigente}">
					<bean:message key="organizacion.pasoVigencia"/>
				</c:when>
				<c:otherwise>
					<bean:message key="organizacion.pasoHistorico"/>
				</c:otherwise>
			</c:choose>
		</tiles:put>
	
		<tiles:put name="buttonBar" direct="true">
			<script>
				function guardar() {
					var form = document.forms['<c:out value="${mappingGestionFechas.name}" />'];
					form.submit();
				}
			</script>
	
			<table>
			<tr>
				<td nowrap>
					<a class="etiquetaAzul12Bold" href="javascript:guardar()" >
						<html:img page="/pages/images/Ok_Si.gif" altKey="organizacion.aceptar" titleKey="organizacion.aceptar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="organizacion.aceptar"/>
					</a>
				</td>					
				<td width="10"></td>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="false">
						<tiles:put name="labelKey" direct="true">organizacion.cancelar</tiles:put>
						<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
					</tiles:insert>
				</td>
			</tr>
			</table>
		</tiles:put>
	
		<tiles:put name="boxContent" direct="true">
	
			<tiles:insert page="/pages/tiles/PABlockLayout.jsp" flush="false">
			
				<tiles:put name="blockTitle" direct="true">
					<bean:message key="organizacion.org.datos"/>					
				</tiles:put>
				
				<tiles:put name="buttonBar" direct="true">

				</tiles:put>
				
				<tiles:put name="blockContent" direct="true">	
					<table  class="formulario" align="center" style="width:95%">			
						<tr>
							<td class="tdTitulo" width="250px">
								<bean:message key="organizacion.org.codigo"/>:&nbsp;
							</td>
							<td class="tdDatos">
								<c:out value="${organizacion.codigo}" />
							</td>
						</tr>	
						<tr>
							<td class="tdTitulo" width="250px">
								<bean:message key="organizacion.org.nombre"/>:&nbsp;
							</td>
							<td class="tdDatos">
								<c:out value="${organizacion.nombre}" />
							</td>
						</tr>		
						<c:if test="${vigente}">
							<tr>					
								<td class="tdTitulo" width="250px"><bean:message key="organizacion.org.fechainicio"/>:&nbsp;</td>
								<td class="tdDatos">
									<html:text property="inicio" size="10" maxlength="10"/>
										&nbsp;<archigest:calendar 
											image="../images/calendar.gif"
				                            formId="<%=formName%>" 
				                            componentId="inicio" 
				                            format="dd/mm/yyyy" 
				                            enablePast="true" />
								</td>					
							</tr>
						</c:if>
						<c:if test="${historico}">
							<tr>
								<td class="tdTitulo" width="250px">
									<bean:message key="organizacion.org.fechainicio"/>:&nbsp;
								</td>
								<td class="tdDatos">
									<c:out value="${fechasForm.inicio}" />
								</td>
							</tr>		
							<tr>					
								<td class="tdTitulo" width="250px"><bean:message key="organizacion.org.fechafin"/>:&nbsp;</td>
								<td class="tdDatos">
									<html:text property="fin" size="10" maxlength="10"/>
										&nbsp;<archigest:calendar 
											image="../images/calendar.gif"
				                            formId="<%=formName%>" 
				                            componentId="fin" 
				                            format="dd/mm/yyyy" 
				                            enablePast="true" />
								</td>					
							</tr>	
						</c:if>	
						<tr>
							<td class="tdTitulo" width="250px">
								<bean:message key="organizacion.org.descripcion"/>:&nbsp;
							</td>
							<td class="tdDatos">
								<c:if test="${empty organizacion.descripcion}">-</c:if>
								<c:out value="${organizacion.descripcion}" />
							</td>
						</tr>					
					</table>
				</tiles:put>
			</tiles:insert>				
		</tiles:put>
	</tiles:insert>
</html:form>