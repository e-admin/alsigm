<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<c:set var="sala" value="${requestScope[appConstants.salas.SALA_KEY]}" />
<c:set var="listaMesas" value="${requestScope[appConstants.salas.LISTA_MESAS_KEY]}" />

<bean:struts id="mappingGestionEstadoMesas" mapping="/gestionEstadoMesasAction" />

<c:set var="estadoAEstablecer" value="${requestScope[mappingGestionEstadoMesas.name].estadoAEstablecer}" />
<c:set var="estadoASeleccionar" value="${requestScope[mappingGestionEstadoMesas.name].estadoASeleccionar}" />
<c:set var="hayElementoASeleccionar" value="${requestScope[mappingGestionEstadoMesas.name].hayElementoASeleccionar}" />

<html:form action="/gestionEstadoMesasAction">
			<input type="hidden" name="method" value="setEstadoMesas">
			<html:hidden property="idSala" />
			<html:hidden property="estadoAEstablecer" />
			<html:hidden property="estadoASeleccionar" />

	<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
		<tiles:put name="boxTitle" direct="true">
			<c:choose>
				<c:when test="${estadoAEstablecer == appConstants.estadosMesa.LIBRE}">
					<bean:message key="archigest.archivo.deposito.habilitar" />
					<bean:message key="archigest.archivo.salas.mesas" />
				</c:when>
				<c:otherwise>
					<bean:message key="archigest.archivo.deposito.inutilizar" />
					<bean:message key="archigest.archivo.salas.mesas" />
				</c:otherwise>
			</c:choose>
		</tiles:put>
		<tiles:put name="buttonBar" direct="true">
			<table cellpadding=0 cellspacing=0>
				<tr>
					<c:if test="${hayElementoASeleccionar}">
					<td>
						<script>
							function go() {
								var form = document.forms['<c:out value="${mappingGestionEstadoMesas.name}" />'];
								if (form.idsMesa && elementSelected(form.idsMesa))
									form.submit();
								else
									alert("<bean:message key='archigest.archivo.sala.esNecesarioSeleccionarAlMenosUnaMesa'/>");
							}
						</script>
						<a class="etiquetaAzul12Bold" href="javascript:go()" >
							<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
							&nbsp;<bean:message key="archigest.archivo.aceptar"/>
						</a>
					</td>
					<td width="10">&nbsp;</td>
					</c:if>
					<TD noWrap>
						<tiles:insert definition="button.closeButton">
							<tiles:put name="imgIcon" value="/pages/images/Ok_No.gif" />
							<tiles:put name="labelKey" value="archigest.archivo.cancelar" />
						</tiles:insert>
					</TD>
				</tr>
			</table>
		</tiles:put>
		<tiles:put name="boxContent" direct="true">
			<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
				<tiles:put name="blockTitle" direct="true">
					<bean:message key="archigest.archivo.salas.ver.sala"/>
				</tiles:put>
				<tiles:put name="blockContent" direct="true">
					<tiles:insert name="salas.datos.sala" flush="true"/>
				</tiles:put>
			</tiles:insert>

			<div class="separador8">&nbsp;</div>

			<div class="cabecero_bloque">
				<table class="w98m1" cellpadding=0 cellspacing=0 height="100%">
					<tr>
						<td class="etiquetaAzul12Bold" width="40%">
							<bean:message key="archigest.archivo.deposito.configuracion" />
							<bean:message key="archigest.archivo.salas.mesas" />
						</td>
						<td align="right">
							<a class="etiquetaAzul12Bold"
								href="javascript:seleccionarCheckboxSet(document.forms['<c:out value="${mappingGestionEstadoMesas.name}" />'].idsMesa);"
				 			><html:img page="/pages/images/checked.gif"
								    altKey="archigest.archivo.selTodas"
								    titleKey="archigest.archivo.selTodas"
								    styleClass="imgTextBottom" />
						    &nbsp;<bean:message key="archigest.archivo.selTodas"/></a>
							&nbsp;
							<a class="etiquetaAzul12Bold"
								href="javascript:deseleccionarCheckboxSet(document.forms['<c:out value="${mappingGestionEstadoMesas.name}" />'].idsMesa);"
				 			><html:img page="/pages/images/check.gif"
								    altKey="archigest.archivo.quitarSel"
								    titleKey="archigest.archivo.quitarSel"
								    styleClass="imgTextBottom" />
						    &nbsp;<bean:message key="archigest.archivo.quitarSel"/></a>
							&nbsp;&nbsp;
					   </td>
					</tr>
				</table>
			</div> <%--cabecero bloque --%>

			<div class="bloque">
				<div class="separador8">&nbsp;</div>
				<div id="divTbl" style="width:98%;margin-left:5px;padding-left:5px;margin-bottom:5px;">
					<table class="tblMesas" style="width:98%;table-layout:auto;margin-left:5px">
					<tr>
					<c:forEach var="mesa" items="${listaMesas}" varStatus="loopStatus">
						<%--SALTO de FILA --%>
						<c:if test="${loopStatus.index !=0 && loopStatus.index % 8 == 0}"></tr><tr></c:if>
						<td class="tdTituloFichaSinBorde" noWrap>
							<c:if test="${mesa.estado == estadoASeleccionar}">
								<c:set var="idMesa" value="${mesa.id}" />
								<jsp:useBean id="idMesa" type="java.lang.String" />
								<c:set var="formulario" value="${requestScope[mappingGestionEstadoMesas.name]}"/>
								<input name="idsMesa" type="checkbox" value="<%=idMesa%>"
								<c:if test="${formulario.idsMesa==idMesa}">checked</c:if>
								>
							</c:if>
							<b><c:out value="${mesa.codigo}" /></b>
							<c:choose>
								<c:when test="${mesa.estado == appConstants.estadosMesa.OCUPADA}">
									<c:url var="imgMesa" value="/pages/images/mesaOcupada.gif" />
									<c:set var="altKey" value="archigest.archivo.salas.mesa.ocupada" />
								</c:when>
								<c:when test="${mesa.estado == appConstants.estadosMesa.INUTILIZADA}">
									<c:url var="imgMesa" value="/pages/images/mesaInutilizada.gif" />
									<c:set var="altKey" value="archigest.archivo.salas.mesa.inutilizada" />
								</c:when>
								<c:otherwise>
									<c:url var="imgMesa" value="/pages/images/mesaLibre.gif" />
									<c:set var="altKey" value="archigest.archivo.salas.mesa.libre" />
								</c:otherwise>
							</c:choose>
							<img src="<c:out value="${imgMesa}" />" alt="<fmt:message key="${altKey}" />" title="<fmt:message key="${altKey}" />"/>
						</td>
					</c:forEach>
					</tr>
					</table>
				</div>
			</div>
		</tiles:put>
	</tiles:insert>
</html:form>