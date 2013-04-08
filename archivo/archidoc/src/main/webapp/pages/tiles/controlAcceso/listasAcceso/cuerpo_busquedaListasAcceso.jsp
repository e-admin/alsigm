<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="mappingGestionListasAcceso" mapping="/gestionListasAcceso" />
<c:set var="tiposLista" value="${appConstants.tiposListasAcceso}" />

<c:set var="listaListasAcceso" value="${requestScope[appConstants.controlAcceso.LISTA_LISTAS_ACCESO]}" />
<c:set var="formBean" value="${sessionScope[mappingGestionListasAcceso.name]}" />


<html:form action="/gestionListasAcceso">
<input type="hidden" name="method" value="buscar">

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.cacceso.busquedaListasAcceso"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
	<table cellspacing=0 cellpadding=0><tr>
		<td nowrap>
			<script>
				function buscar() {
					var form = document.forms['<c:out value="${mappingGestionListasAcceso.name}" />'];
					form.submit();
				}
			</script>
			<a class="etiquetaAzul12Bold" href="javascript:buscar()" >
			<html:img page="/pages/images/buscar.gif" altKey="archigest.archivo.buscar" titleKey="archigest.archivo.buscar" styleClass="imgTextMiddle" />
			<bean:message key="archigest.archivo.buscar"/></a>
		</td>
		<td nowrap width="15px">&nbsp;</td>
		<td nowrap>
			<tiles:insert definition="button.closeButton" flush="true"/>
		</td>
	</tr></table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">

		<div class="bloque">
				<table class="formulario">
					<tr>
					<td class="tdTituloFichaSinBorde" width="10%"><bean:message key="archigest.archivo.nombre"/>:</td>
					<td class="tdDatosFichaSinBorde">
						<html:text property="nombre" styleClass="input99" maxlength="254" />
					</td>
					</tr>
					<tr>
						<td class="tdTitulo">
							<bean:message key="archigest.archivo.tipo"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<table>
							<tr>
								<td width="10px"  valign="middle">
									<html-el:multibox property="tiposLista" value="${tiposLista['ELEMENTO_CUADRO_CLASIFICACION']}" styleClass="checkbox"/>
								</td>
								<td class="etiquetaAzul11Bold" valign="middle">								
									<bean:message key="archigest.archivo.elementoDelCuadro"/>
								</td>
								<td width="10px">&nbsp;</td>
								
								<td width="10px"  valign="middle">
									<html-el:multibox property="tiposLista" value="${tiposLista['DESCRIPTOR']}" styleClass="checkbox"/>
								</td>
								<td class="etiquetaAzul11Bold" valign="middle">
									<bean:message key="archigest.archivo.descriptor"/>
								</td>
								<td width="10px">&nbsp;</td>

								<td width="10px"  valign="middle">
									<html-el:multibox property="tiposLista" value="${tiposLista['FORMATO_FICHA']}" styleClass="checkbox"/>
								</td>								
								<td class="etiquetaAzul11Bold" valign="middle">
									<bean:message key="archigest.archivo.formatoFicha"/>
								</td>
							</tr>
							</table>
						</td>
					</tr>					
				</table>
		</div>

		<div class="separador8">&nbsp;</div>

		<c:if test="${listaListasAcceso != null}">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.resultadosBusqueda"/></tiles:put>
			<tiles:put name="blockContent" direct="true">
				<c:url var="paginationURL" value="/action/gestionListasAcceso" />
				<jsp:useBean id="paginationURL" type="java.lang.String" />

				<display:table name="pageScope.listaListasAcceso" 
					id="listaAcceso" 
					style="width:98%;margin-left:auto;margin-right:auto"
					sort="list"
					requestURI="/action/gestionListasAcceso"
					pagesize="10">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.cacceso.msgNoListasAcceso"/>
					</display:setProperty>
					<display:column titleKey="archigest.archivo.nombre">
						<c:url var="verURL" value="/action/gestionListasAcceso">
							<c:param name="method" value="verListaAcceso" />
							<c:param name="idListaAcceso" value="${listaAcceso.id}" />
						</c:url>
						<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" >
						<c:out value="${listaAcceso.nombre}" />
						</a>
					</display:column>
					<display:column titleKey="archigest.archivo.tipo">
						<c:choose>
							<c:when test="${listaAcceso.tipo==tiposLista['ELEMENTO_CUADRO_CLASIFICACION']}">
								<bean:message key="archigest.archivo.elementoDelCuadro"/>
							</c:when> 
							<c:when test="${listaAcceso.tipo==tiposLista['DESCRIPTOR']}">
								<bean:message key="archigest.archivo.descriptor"/>
							</c:when> 
							<c:when test="${listaAcceso.tipo==tiposLista['FORMATO_FICHA']}">
								<bean:message key="archigest.archivo.formatoFicha"/>
							</c:when> 
						</c:choose>
					</display:column>					
					<display:column titleKey="archigest.archivo.descripcion">
						<c:out value="${listaAcceso.descripcion}" />
					</display:column>
				</display:table>
			</tiles:put>
		</tiles:insert>
		</c:if>

	</tiles:put>
</tiles:insert>
</html:form>