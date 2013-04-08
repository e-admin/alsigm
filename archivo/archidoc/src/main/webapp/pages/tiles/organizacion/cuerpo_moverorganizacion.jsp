<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<archigest:calendar-config imgDir="../images/calendario/" scriptFile="../scripts/calendar.js"/>

<bean:struts id="mappingGestionOrganizacion" mapping="/gestionOrganizacionAction" />
<c:set var="organizacion" value="${sessionScope[appConstants.organizacion.ORGANIZACION_KEY]}" />	
<c:set var="vigente" value="${organizacion.estado==2}"/>
<c:set var="historico" value="${organizacion.estado==3}"/>
<c:set var="jerarquiaElementoOrg" value="${sessionScope[appConstants.organizacion.JERARQUIA_ELEMENTO_ORGANIZACION]}" />

<html:form action="/gestionOrganizacionAction" >
	<html:hidden property="id"/>
	<input type="hidden" name="method" value="moverOrganizacion">
	
	<tiles:insert template="/pages/tiles/PABoxLayout.jsp" flush="false">
			
		<tiles:put name="boxTitle" direct="true">
			<bean:message key="organizacion.org.seleccionarNuevoPadre"/>
		</tiles:put>
	
		<tiles:put name="buttonBar" direct="true">
			<script>
				function mover() {
					var form = document.forms['<c:out value="${mappingGestionOrganizacion.name}" />'];
					form.submit();
				}
			</script>
	
			<table>
			<tr>
				<td nowrap>
					<a class="etiquetaAzul12Bold" href="javascript:mover()" >
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
					<bean:message key="organizacion.org.datosorgano"/>					
				</tiles:put>
				
				<tiles:put name="blockContent" direct="true">	
					<table  class="formulario">									
						<c:if test="${not empty jerarquiaElementoOrg}">
							<tr>								
								<td class="tdTitulo" width="150px">
									<bean:message key="organizacion.org.ancestors"/>:&nbsp;
								</td>
								<td class="tdDatos">
									<tiles:insert definition="organizacion.jerarquiaElemento" flush="false" />
								</td>
							</tr>	
						</c:if>													
						<%--<tr width="150px"><td>&nbsp;</td></tr>--%>						
						<tr>
							<td class="tdTitulo">
								<bean:message key="organizacion.org.codigo"/>:&nbsp;
							</td>
							<td class="tdDatos">
								<c:out value="${organizacion.codigo}" />
							</td>
						</tr>							
						<tr>
							<td class="tdTitulo">
								<bean:message key="organizacion.org.nombre"/>:&nbsp;
							</td>
							<td class="tdDatos">
								<c:out value="${organizacion.nombre}" />
							</td>
						</tr>	
						
						<%--<c:choose>
							<c:when test="${organizacion.estado==1}">
								<bean:define id="estado" value="1" toScope="request"/>						
							</c:when>
							<c:otherwise>
								<bean:define id="estado" value="2" toScope="request"/>						
							</c:otherwise>
						</c:choose>	--%>
						<bean:define id="classTdTituloCampo" toScope="request">
							<bean:message key="organizacion.org.nuevopadre"/>
						</bean:define>
						<bean:define id="classTitulo" toScope="request">
							<bean:message key="organizacion.org.selorgpadre"/>
						</bean:define>
						<tiles:insert page="./campo_busqueda_organo.jsp" flush="false"/>
						
					</table>
				</tiles:put>
			</tiles:insert>				
		</tiles:put>
	</tiles:insert>
</html:form>