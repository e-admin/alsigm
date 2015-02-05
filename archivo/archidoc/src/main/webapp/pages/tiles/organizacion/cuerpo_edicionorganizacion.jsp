<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>

<bean:struts id="mappingGestionOrganizacion" mapping="/gestionOrganizacionAction" />
<c:set var="gestionOrganizacionFormName" value="${mappingGestionOrganizacion.name}" />
<c:set var="gestionOrganizacionForm" value="${requestScope[gestionOrganizacionFormName]}" />	
<c:set var="formName" value="${mappingGestionOrganizacion.name}" />
<jsp:useBean id="formName" type="java.lang.String" />
<c:set var="borrador" value="${gestionOrganizacionForm.estado==1}" />
<c:set var="vigente" value="${gestionOrganizacionForm.estado==2}" />
<c:set var="institucion" value="${gestionOrganizacionForm.tipo==1}" />
<c:set var="organo" value="${gestionOrganizacionForm.tipo==2}" />
<c:set var="jerarquiaElementoOrg" value="${sessionScope[appConstants.organizacion.JERARQUIA_ELEMENTO_ORGANIZACION]}" />
<archigest:calendar-config imgDir="../images/calendario/" scriptFile="../scripts/calendar.js"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

	<tiles:put name="boxTitle" direct="true">
		<bean:message key="organizacion.editar" />
		<c:choose>
			<c:when test="${institucion}">
				<bean:message key="organizacion.institucion"/>
			</c:when>
			<c:when test="${organo}">
				<bean:message key="organizacion.organo"/>
			</c:when>
			<c:otherwise>
				<bean:message key="organizacion.org"/>
			</c:otherwise>
		</c:choose>

	</tiles:put>
	
	<tiles:put name="buttonBar" direct="true">	

		<script>
			function guardarOrganizacion() {
				var form = document.forms['<c:out value="${mappingGestionOrganizacion.name}" />'];
				form.submit();
			}			
			
		</script>
		<table cellpadding=0 cellspacing=0>
		<tr>
			<td>
				<a class="etiquetaAzul12Bold" href="javascript:guardarOrganizacion()" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="organizacion.aceptar" titleKey="organizacion.aceptar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="organizacion.aceptar"/>
				</a>
			</td>
			<td width="10">&nbsp;</td>
			<td noWrap>
				<tiles:insert definition="button.closeButton" flush="false">
					<tiles:put name="imgIcon" value="/pages/images/Ok_No.gif" />
					<tiles:put name="labelKey" value="organizacion.cancelar" />
				</tiles:insert>
			</td>
		</tr>
		</table>
	</tiles:put>
	
	<tiles:put name="boxContent" direct="true">
	
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp" flush="false">
		
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="organizacion.org.datos"/>
			</tiles:put>
			
			<tiles:put name="blockContent" direct="true">				
				<html:form action="/gestionOrganizacionAction">
					<html:hidden property="id" />					
					<html:hidden property="estado" />
					<html:hidden property="vigente" />
					<input type="hidden" name="method" value="guardarOrganizacion">								
					
					<table class="formulario">	
						<c:if test="${not empty jerarquiaElementoOrg}">
							<tr>
								<td class="tdTitulo" width="200px">
									<bean:message key="organizacion.org.ancestors"/>:&nbsp;
								</td>
								<td class="tdDatos">
									<tiles:insert definition="organizacion.jerarquiaElemento" flush="false" />
								</td>
							</tr>	
						</c:if>
						<c:if test="${borrador}">
							<tr>
								<td class="tdTitulo" width="200px"><bean:message key="organizacion.org.codigo"/>:&nbsp;</td>
								<td class="tdDatos">
									<html:text property="codigo" styleClass="input99" maxlength="64" />
								</td>
							</tr>
						</c:if>
						<c:if test="${vigente}">
							<tr>
								<td class="tdTitulo" width="200px"><bean:message key="organizacion.org.codigo"/>:&nbsp;</td>
								<td class="tdDatos">
									<c:out value="${gestionOrganizacionForm.codigo}"/>
									<html:hidden property="codigo" />
								</td>
							</tr>
						</c:if>
						<tr>
							<td class="tdTitulo" width="200px"><bean:message key="organizacion.org.nombre"/>:&nbsp;</td>
							<td class="tdDatos">
								<html:text property="nombre" styleClass="input99" size="254" maxlength="254"/>
							</td>
						</tr>	
						<c:if test="${vigente}">														
							<tr>					
								<td class="tdTitulo" width="200px">
									<bean:message key="organizacion.org.fechainicio"/>:&nbsp;
								</td>
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
						<tr>
							<td class="tdTitulo" width="200px" style="vertical-align:top;"><bean:message key="organizacion.org.descripcion"/>:&nbsp;</td>
							<td class="tdDatos">
								<html:textarea property="descripcion" rows="6" onchange="maxlength(this,254,true)" onkeypress="maxlength(this,254,false)"/>&nbsp;
							</td>
						</tr>	
					</table>				
				</html:form>				
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>