<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<c:set var="elementoAsignable" value="${sessionScope[appConstants.deposito.ELEMENTO_ASIGNABLE_KEY]}" />
<c:set var="EDITABLE" value="${requestScope[appConstants.deposito.EDITABLE_KEY]}" />
<c:set var="EDITABLE_NUMERACION" value="${requestScope[appConstants.deposito.EDITABLE_NUMERACION_KEY]}" />

<bean:struts id="mappingGestionTipoAsignable" mapping="/gestionTipoAsignableAction" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.deposito.elementoDeposito"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<td>
				<c:url var="viewElementURL" value="/action/manageVistaDeposito">
					<c:param name="actionToPerform" value="verPadre" />
					<c:param name="node" value="${elementoAsignable.itemPath}" />
					<c:param name="refreshView" value="true" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${viewElementURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/treePadre.gif" altKey="archigest.archivo.cf.verPadre" titleKey="archigest.archivo.cf.verPadre" styleClass="imgTextMiddle" />
					<bean:message key="archigest.archivo.cf.verPadre"/>
				</a>
			</td>
			<TD width="10">&nbsp;</TD>
			<c:if test="${EDITABLE}">
			<security:permissions action="${appConstants.depositoActions.MODIFICAR_ELEMENTO_ACTION}">
			<TD>
				<c:url var="editarElementoURL" value="/action/gestionTipoAsignableAction">
					<c:param name="method" value="editarAsignable" />
					<c:param name="idAsignable" value="${elementoAsignable.id}" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${editarElementoURL}" escapeXml="false"/>">
					<html:img page="/pages/images/edit.gif" altKey="archigest.archivo.editar" titleKey="archigest.archivo.editar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.editar"/>
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>
			</security:permissions>
			</c:if>
			<security:permissions action="${appConstants.depositoActions.ELIMINACION_ELEMENTO_ACTION}">
			<TD>
				<c:url var="eleminarElementoURL" value="/action/gestionTipoAsignableAction">
					<c:param name="method" value="eliminarAsignable" />
					<c:param name="idAsignable" value="${elementoAsignable.id}" />
					<c:param name="idTipoElemento" value="${elementoAsignable.idTipoElemento}" />
				</c:url>
				<script>
					function eliminar()
					{
						if (confirm("<bean:message key="archigest.archivo.deposito.eliminarElementoAsigMsg"/>"))
							window.location = '<c:out value="${eleminarElementoURL}" escapeXml="false"/>';
					}
				</script>
				<a class=etiquetaAzul12Bold href="javascript:eliminar()">
					<html:img page="/pages/images/delete.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.eliminar"/>
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>
			</security:permissions>
			<td nowrap>
				<tiles:insert definition="button.closeButton" />
			</td>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert page="/pages/tiles/deposito/tipoasignable/datos_tipoasignable.jsp" />

	<div class="separador8">&nbsp;</div>

	 <div class="cabecero_bloque_sin_height">
		<TABLE class="w98m1" cellpadding=0 cellspacing=0>
			<TR>
			<TD class="etiquetaAzul12Bold" width="30%">
				<bean:message key="archigest.archivo.deposito.configuracion" />
				<bean:message key="archigest.archivo.deposito.huecos" />
			</TD>
			<TD width="70%" align="right">
			<TABLE cellpadding=0 cellspacing=0>
			<TR>
				<c:if test="${EDITABLE_NUMERACION}">
					<TD>
						<c:url var="editarNumeracionURL" value="/action/gestionEstadoHuecosAction">
							<c:param name="method" value="editarNumeracion"/>
							<c:param name="idAsignable" value="${elementoAsignable.id}" />
						</c:url>
						<a class="etiquetaAzul12Normal" href="<c:out value="${editarNumeracionURL}" escapeXml="false"/>" >
							<html:img titleKey="archigest.archivo.deposito.editarNumeracion" altKey="archigest.archivo.deposito.editarNumeracion" page="/pages/images/edit.gif" styleClass="imgTextMiddle" />
							 &nbsp;<bean:message key="archigest.archivo.deposito.editarNumeracion"/>
						</a>
					</TD>
					<TD width="10">&nbsp;</TD>
				</c:if>
				<TD>
					<c:url var="urlImprimir" value="/action/gestionTipoAsignableAction">
						<c:param name="method" value="selCartelas"/>
					</c:url>
					<a class="etiquetaAzul12Normal" href="<c:out value="${urlImprimir}" escapeXml="false"/>" >
						<html:img titleKey="archigest.archivo.deposito.generarCartelas" altKey="archigest.archivo.deposito.generarCartelas" page="/pages/images/cartela.gif" styleClass="imgTextMiddle" />
						 &nbsp;<bean:message key="archigest.archivo.deposito.generarCartelas"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>

				<security:permissions action="${appConstants.depositoActions.MODIFICAR_ELEMENTO_ACTION}">
				<TD nowrap>
					<c:url var="habilitarHuecosURL" value="/action/gestionEstadoHuecosAction">
						<c:param name="method" value="habilitarHuecos" />
						<c:param name="idAsignable" value="${elementoAsignable.id}" />
					</c:url>
					<a class="etiquetaAzul12Normal" href="<c:out value="${habilitarHuecosURL}" escapeXml="false"/>">
						<html:img titleKey="archigest.archivo.deposito.habilitar" altKey="archigest.archivo.deposito.habilitar" page="/pages/images/huecoBlanco.gif" styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.deposito.habilitar"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
				</security:permissions>

				<security:permissions action="${appConstants.depositoActions.MODIFICAR_ELEMENTO_ACTION}">
				<TD nowrap>
					<c:url var="inhabilitarHuecosURL" value="/action/gestionEstadoHuecosAction">
						<c:param name="method" value="inhabilitarHuecos" />
						<c:param name="idAsignable" value="${elementoAsignable.id}" />
					</c:url>
					<a class="etiquetaAzul12Normal" href="<c:out value="${inhabilitarHuecosURL}" escapeXml="false"/>">
						<html:img titleKey="archigest.archivo.deposito.inutilizar" altKey="archigest.archivo.deposito.inutilizar" page="/pages/images/huecoNegro.gif" styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.deposito.inutilizar"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
				</security:permissions>

				<security:permissions action="${appConstants.depositoActions.REUBICACION_UNIDADES_INSTALACION}">
				<TD nowrap>
					<c:url var="reubicacionUIsURL" value="/action/reubicacionAction">
						<c:param name="method" value="seleccionUIs" />
						<c:param name="idAsignable" value="${elementoAsignable.id}" />
					</c:url>
					<a class="etiquetaAzul12Normal" href="<c:out value="${reubicacionUIsURL}" escapeXml="false"/>">
					<html:img titleKey="archigest.archivo.deposito.reubicar" altKey="archigest.archivo.deposito.reubicar" page="/pages/images/huecoVerde.gif" styleClass="imgTextMiddle"/> &nbsp;<bean:message key="archigest.archivo.deposito.reubicar"/>
					</a>
				</TD>
				</security:permissions>
			</TR>
			</TABLE>
			</TD>
			</TR>
		</TABLE>
	</div> <%-- cabecero bloque --%>

	<div class="bloque">
	<div id="divTbl" style="width:98%;margin-left:5px;padding-left:5px;margin-bottom:5px;">
		<TABLE class="tblHuecos" style="width:98%;table-layout:auto;margin-left:5px">

		<div class="separador5">&nbsp;</div>
		<TR>
		<c:set var="listaHuecos" value="${sessionScope[appConstants.deposito.LISTA_HUECOS_KEY]}" />
		<c:forEach var="hueco" items="${listaHuecos}" varStatus="loopStatus">
			<%--SALTO de FILA --%>
			<c:if test="${loopStatus.index !=0 && loopStatus.index % 8 == 0}"></TR><TR></c:if>
			<TD class="tdTituloFichaSinBorde" nowrap>
				<bean:message key="archigest.archivo.deposito.inicialHueco" />
				<b><c:out value="${hueco.numorden}" /></b>

				<c:choose>
					<c:when test="${hueco.estado == appConstants.estadosHueco.OCUPADO_STATE}">
						<c:url var="imgHueco" value="/pages/images/huecoAzul.gif" />
						<c:set var="altKey" value="archigest.archivo.deposito.ocupado" />
					</c:when>
					<c:when test="${hueco.estado == appConstants.estadosHueco.RESERVADO_STATE}">
						<c:url var="imgHueco" value="/pages/images/huecoRojo.gif" />
						<c:set var="altKey" value="archigest.archivo.deposito.reservado" />
					</c:when>
					<c:when test="${hueco.estado == appConstants.estadosHueco.INUTILIZADO_STATE}">
						<c:url var="imgHueco" value="/pages/images/huecoNegro.gif" />
						<c:set var="altKey" value="archigest.archivo.deposito.inutilizado" />
					</c:when>
					<c:otherwise>
						<c:url var="imgHueco" value="/pages/images/huecoBlanco.gif" />
						<c:set var="altKey" value="archigest.archivo.deposito.libre" />
					</c:otherwise>
				</c:choose>

				<img src="<c:out value="${imgHueco}" escapeXml="false"/>" alt="<fmt:message key="${altKey}" />" title="<fmt:message key="${altKey}" />"/>
				<c:if test="${hueco.estado == appConstants.estadosHueco.OCUPADO_STATE}">
					<br>
					<c:url var="urlVerUdocs" value="/action/verHuecoAction">
						<c:param name="method" value="listadoudocs"/>
						<c:param name="idHueco" value="${hueco.idElemAPadre}:${hueco.numorden}"/>
					</c:url>
					<a href="javascript:window.location='<c:out value="${urlVerUdocs}" escapeXml="false"/>'" class="hueco">
						<c:out value="${hueco.signaturaUI}"/>
					</a>
				</c:if>
				<c:if test="${(hueco.estado == appConstants.estadosHueco.RESERVADO_STATE)}">
					<br>
					<c:url var="urlVerRelacion" value="/action/verHuecoAction">
						<c:param name="method" value="verrelacion"/>
						<c:param name="idHueco" value="${hueco.idElemAPadre}:${hueco.numorden}"/>
					</c:url>
					<a href="javascript:window.location='<c:out value="${urlVerRelacion}" escapeXml="false"/>'" class="huecoReservado">
						<c:out value="${hueco.codigoTransferencia}"/>
					</a>
				</c:if>
				<c:if test="${EDITABLE_NUMERACION && hueco.estado != appConstants.estadosHueco.OCUPADO_STATE}">
					<br><b><c:out value="${hueco.numeracion}" /></b>
				</c:if>
			</TD>
		</c:forEach>

		<c:if test="${elementoAsignable.espacioSobrante > 0}">
			<td class="sobrante">
				<bean:message key="archigest.archivo.deposito.sobrante" />:<br>
				<c:out value="${elementoAsignable.espacioSobrante}" /> <bean:message key="archigest.archivo.cm" />
			</td>
		</c:if>

		</TR>

		</TABLE>
	</div>
	</div>




</tiles:put>
</tiles:insert>