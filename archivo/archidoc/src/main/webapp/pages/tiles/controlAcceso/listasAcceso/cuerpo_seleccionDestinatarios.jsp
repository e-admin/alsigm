<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mappingGestionListaAcceso" mapping="/gestionListasAcceso" />
<c:url var="urlActionListaAcceso" value="/action/gestionListasAcceso" />
<c:set var="actionListaAcceso" value="/action/gestionListasAcceso" />
<c:set var="formBean" value="${sessionScope[mappingGestionListaAcceso.name]}" />
<c:set var="tipoElemento" value="${formBean.tipoElementoSeleccionado}" />
<c:set var="vListaAcceso" value="${sessionScope[appConstants.controlAcceso.INFO_LISTA_ACCESO]}" />


<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<c:choose>
			<c:when test="${formBean.tipoElementoUsuario}">
				<bean:message key="archigest.archivo.cacceso.asignarUsers"/>
			</c:when>
			<c:when test="${formBean.tipoElementoGrupo}">
				<bean:message key="archigest.archivo.cacceso.asignarGrupos"/>
			</c:when>
			<c:when test="${formBean.tipoElementoOrgano}">
				<bean:message key="archigest.archivo.cacceso.asignarOrganos"/>
			</c:when>
		</c:choose>			
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<script>
			function seleccionar() {
				var form = document.forms['<c:out value="${mappingGestionListaAcceso.name}" />'];
				form.method.value = 'verElementoLista';
				form.submit();
			}
		</script>
		<table><tr>
		<td nowrap>
			<a class="etiquetaAzul12Bold" href="javascript:seleccionar()" >
			<html:img page="/pages/images/Next.gif" altKey="archigest.archivo.siguiente" titleKey="archigest.archivo.siguiente" styleClass="imgTextMiddle" />
			&nbsp;<bean:message key="archigest.archivo.siguiente"/></a>
		</td>		
		<td width="10px"></td>
		<td nowrap>
			<c:url var="cancelURL" value="${actionListaAcceso}">
				<c:param name="method" value="goBack" />
			</c:url>

			<a class="etiquetaAzul12Bold" href="<c:out value="${cancelURL}" escapeXml="false"/>" >
			<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
			&nbsp;<bean:message key="archigest.archivo.cancelar"/></a>
		</td>
		</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<div class="separador8">&nbsp;</div>

		<div class="cabecero_bloque">
			<table class="w98" cellpadding=0 cellspacing=0 height="100%"><tr>
				<td class="etiquetaAzul12Bold" width="150px">
					<bean:message key="archigest.archivo.cacceso.listaAcceso"/>:&nbsp;
				</td>
				<td class="etiquetaNegra12Normal">
					<c:out value="${vListaAcceso.nombre}" />
				</td>
			</tr></table>
		</div>

		<div class="separador8">&nbsp;</div>

		<div class="cabecero_bloque">
			<table class="w98" cellpadding=0 cellspacing=0 height="100%"><tr>
				<TD class="etiquetaAzul12Bold" width="250px">
				<c:choose>
					<c:when test="${formBean.tipoElementoUsuario}">
						<bean:message key="archigest.archivo.buscar.usuario"/>
					</c:when>
					<c:when test="${formBean.tipoElementoGrupo}">
						<bean:message key="archigest.archivo.cacceso.busquedaGrupos"/>
					</c:when>
					<c:when test="${formBean.tipoElementoOrgano}">
						<bean:message key="archigest.archivo.cacceso.busquedaOrganos"/>:&nbsp;
					</c:when>
				</c:choose>						
				</TD>
				<td align="right">
					<a class="etiquetaAzul12Normal" href="javascript:document.forms[0].submit()">
						<html:img titleKey="archigest.archivo.buscar" altKey="archigest.archivo.buscar" page="/pages/images/buscar.gif" styleClass="imgTextMiddle"/>
						<bean:message key="archigest.archivo.buscar"/>
					</a>		
				</td>
			</tr></table>
		</div>
		<div class="bloque">
			<form name="busqueda" action='<c:out value="${urlActionListaAcceso}" escapeXml="false"/>' >
			<table  class="formulario" style="width:100%;margin-left:auto;margin-right:auto">			
					<tr>
						<td class="tdTitulo" width="150px">
							<bean:message key="archigest.archivo.nombre"/>:
						</td>
						<td class="tdDatos">
							<input type="text" size="50" name="nombreABuscar" value="<c:out value="${formBean.nombreABuscar}" />">
							<input type="hidden" name="method" value="busquedaDestinatarios">	
						</td>
					</tr>	
			</table>
			</form>
		</div>

		<c:set var="listaDestinatarios" value="${requestScope[appConstants.controlAcceso.LISTA_DESTINATARIOS_LISTA]}" />

		<div class="separador5">&nbsp;</div>
		<html:form action="/gestionListasAcceso">
		<div id="resultados">

			<div style="display:none;">
				<input type="hidden" name="method" value="agregarElementoALista">
			</div>		
			<c:choose>
				<c:when test="${listaDestinatarios != null}">
		
					<div class="cabecero_bloque">
						<table class="w98" cellpadding="0" cellspacing="0" height="100%">
						<tr>
							<td class="etiquetaAzul12Bold">
								<bean:message key="archigest.archivo.resultadosBusqueda"/>
							</td>		
						</tr>
						</table>
					</div>
		
					<div class="bloque" style="padding-top:8px;padding-bottom:8px">
		
						<c:choose>
			
							<c:when test="${formBean.tipoElementoUsuario}">
				
								<table class="w98m1">
								    <TR><TD class="etiquetaAzul12Bold">
										<bean:message key="archigest.archivo.cacceso.msgSelUserAsignar"/>:
									</TD></TR>
								</table>
					
								<display:table name="pageScope.listaDestinatarios" 
										id="usuario" 
										style="width:98%;margin-left:auto;margin-right:auto"
										sort="list">
									<display:setProperty name="basic.msg.empty_list">
										<bean:message key="archigest.archivo.cacceso.msgNoUserAsignar"/>
									</display:setProperty>
									<display:column style="width:15px">
										<input type="radio" name="idElementoSeleccionado" value="<c:out value="${usuario.id}" />"
										<c:if test="${usuario.id==formBean.idElementoSeleccionado}"> checked </c:if>
										>
									</display:column>
									<display:column titleKey="archigest.archivo.nombre">
										<c:out value="${usuario.nombreCompleto}" />
									</display:column>
								</display:table>
							</c:when>
			
							<c:when test="${formBean.tipoElementoGrupo}">
				
								<table class="w98m1">
								    <TR><TD class="etiquetaAzul12Bold">
										<bean:message key="archigest.archivo.cacceso.msgSelGrupoAsignar"/>:
									</TD></TR>
								</table>
				
								<display:table name="pageScope.listaDestinatarios" 
										id="grupo" 
										style="width:98%;margin-left:auto;margin-right:auto"
										sort="list">
									<display:setProperty name="basic.msg.empty_list">
										<bean:message key="archigest.archivo.cacceso.msgNoGrupoAsignar"/>
									</display:setProperty>
									<display:column style="width:15px">
										<input type="radio" name="idElementoSeleccionado" value="<c:out value="${grupo.id}" />"
										<c:if test="${grupo.id==formBean.idElementoSeleccionado}"> checked </c:if>
										>
									</display:column>
									<display:column titleKey="archigest.archivo.nombre">
										<c:out value="${grupo.nombre}" />
									</display:column>
								</display:table>
							</c:when>
			
							<c:when test="${formBean.tipoElementoOrgano}">
					
								<table class="w98m1">
								    <TR><TD class="etiquetaAzul12Bold">
										<bean:message key="archigest.archivo.cacceso.msgSelOrganoAsignar"/>:
									</TD></TR>
								</table>
					
								<display:table name="pageScope.listaDestinatarios" 
										id="organo" 
										style="width:98%;margin-left:auto;margin-right:auto"
										sort="list">
									<display:setProperty name="basic.msg.empty_list">
										<bean:message key="archigest.archivo.cacceso.msgNoOrganoAsignar"/>
									</display:setProperty>
									<display:column style="width:15px">
										<input type="radio" name="idElementoSeleccionado" value="<c:out value="${organo.idOrg}" />"
										<c:if test="${organo.idOrg==formBean.idElementoSeleccionado}"> checked </c:if>
										>
									</display:column>
									<display:column titleKey="archigest.archivo.nombre">
										<c:out value="${organo.nombreLargo}" />
									</display:column>
								</display:table>
							</c:when>
			
						</c:choose>			
					</div>
				</c:when>
			</c:choose>
		</div>
		<div style="display:none;">
		</div>
		</html:form>
	</tiles:put>
</tiles:insert>