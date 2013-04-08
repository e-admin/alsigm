<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mappingGestionUsuarios" mapping="/gestionUsuariosOrganizacionAction" />
<c:set var="formBean" value="${sessionScope[mappingGestionUsuarios.name]}"/>
<c:set var="vUsuario" value="${sessionScope[appConstants.organizacion.USUARIO_VER_USUARIO]}"/>

<c:choose>
	<c:when test="${!empty vUsuario}">
		<c:set var="editando" value="true"/>
	</c:when>
	<c:otherwise>
		<c:set var="editando" value="false"/>
	</c:otherwise>
</c:choose>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp" flush="false">

	<tiles:put name="boxTitle" direct="true">
		<c:choose>
			<c:when test="${editando}">
				<bean:message key="organizacion.user.editar"/>
			</c:when>
			<c:otherwise>
				<bean:message key="organizacion.user.crear"/>
			</c:otherwise>
		</c:choose>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">

		<script>
			function guardarUsuario() {
				var form = document.forms['<c:out value="${mappingGestionUsuarios.name}" />'];
				form.method.value = 'guardarUsuario';
				form.submit();
			}			
		</script>
		
		<table>
			<tr>
				<td nowrap>
					<a class="etiquetaAzul12Bold" href="javascript:guardarUsuario()" >
					<html:img page="/pages/images/Ok_Si.gif" 
						altKey="organizacion.aceptar" 
						titleKey="organizacion.aceptar" 
						styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="organizacion.aceptar"/></a>
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
		<html:form action="/gestionUsuariosOrganizacionAction" >
		<input type="hidden" name="method" value="buscarUsuarios">
		<html:hidden property="idUsrSisExtGestorSeleccionado"/>
		<html:hidden property="tipoSistema"/>
		<html:hidden property="idUsuario"/>
		
		<c:choose>
			<c:when test="${editando}">
				<input type="hidden" name="editando" value="true">
			</c:when>		
		</c:choose>
		
		<div class="cabecero_bloque">
			<table class="w98m1" cellpadding=0 cellspacing=0 height="100%">
			<tr>
		  		<td class="etiquetaAzul12Bold">
		  			<bean:message key="organizacion.user.infouser"/>&nbsp;
		  		</td>
			</tr>
			</table>
		</div>
		<c:set var="userInfo" value="${sessionScope[appConstants.organizacion.INFO_USUARIO_SELECCIONADO]}}"/>
		<div class="bloque">
			<div class="separador5">&nbsp;</div>

			<table class="formulario" cellpadding="0" cellspacing="0">
				<tr>
					<td class="tdTitulo" width="190px">
						<bean:message key="organizacion.user.nombre"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<html:text size="30" maxlength="254" property="nombreUsuario"/>						
					</td>
				</tr>
	
				<c:choose>
					<%--<c:when test="${editando}">
						<tr>
							<td class="tdTitulo">
								<bean:message key="organizacion.user.org"/>:&nbsp;
							</td>
							<td class="tdDatos"><c:out value="${vUsuario.nombreOrgano}"/></td>
						</tr> 
					</c:when>--%>
					<c:otherwise>
						<bean:define id="classTdTituloCampo" toScope="request">
							<bean:message key="organizacion.user.org"/>
						</bean:define>
						<bean:define id="classTitulo" toScope="request">
							<bean:message key="organizacion.user.selorguser"/>
						</bean:define>
						<tiles:insert page="../busquedas/campo_busqueda_organo.jsp" flush="false"/>
					</c:otherwise>
				</c:choose>
			</table>			  
		</div>				
			
		<div style="display:none;">
		</html:form>
		</div>
	</tiles:put>
</tiles:insert>