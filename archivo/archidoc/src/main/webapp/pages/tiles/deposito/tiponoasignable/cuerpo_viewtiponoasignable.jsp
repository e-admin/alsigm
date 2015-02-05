<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="elementoNoAsignable" value="${sessionScope[appConstants.deposito.ELEMENTO_NO_ASIGNABLE_KEY]}" />

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
					<c:param name="node" value="${elementoNoAsignable.itemPath}" />
					<c:param name="refreshView" value="true" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${viewElementURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/treePadre.gif" altKey="archigest.archivo.cf.verPadre" titleKey="archigest.archivo.cf.verPadre" styleClass="imgTextMiddle" />
					<bean:message key="archigest.archivo.cf.verPadre"/>
				</a>
			</td>
			<TD width="10">&nbsp;</TD>
			<security:permissions action="${appConstants.depositoActions.MODIFICAR_ELEMENTO_ACTION}">
			<TD>
				<c:url var="editarElementoURL" value="/action/gestionTipoNoAsignableAction">
					<c:param name="method" value="editarNoAsignable" />
					<c:param name="idNoAsignable" value="${elementoNoAsignable.id}" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${editarElementoURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/edit.gif" altKey="archigest.archivo.editar" titleKey="archigest.archivo.editar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.editar"/>
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>
			</security:permissions>

			<security:permissions action="${appConstants.depositoActions.ELIMINACION_ELEMENTO_ACTION}">
			<TD>
				<c:url var="eleminarElementoURL" value="/action/gestionTipoNoAsignableAction">
					<c:param name="method" value="eliminarNoAsignable" />
					<c:param name="idNoAsignable" value="${elementoNoAsignable.id}" />
					<c:param name="idTipoElemento" value="${elementoNoAsignable.idTipoElemento}" />
				</c:url>
				<script>
					function eliminar()
					{
						if (confirm("<bean:message key="archigest.archivo.deposito.eliminarElementoNoAsigMsg"/>"))
							window.location = "<c:out value="${eleminarElementoURL}" escapeXml="false" />";
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
		<bean:define id="informeOcupacion" value="true" toScope="request"/>
		<tiles:insert page="/pages/tiles/deposito/tiponoasignable/datos_tipoNoAsignable.jsp" />

		<div class="separador8"></div>

		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.deposito.descendientes" />
			</tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<TABLE cellpadding=0 cellspacing=0>
				<TR>
					<TD>
						<c:url var="urlImprimir" value="/action/gestionTipoNoAsignableAction">
							<c:param name="method" value="selCartelas"/>
						</c:url>
						<a class="etiquetaAzul12Normal" href="<c:out value="${urlImprimir}" escapeXml="false"/>" >
							<html:img titleKey="archigest.archivo.deposito.generarCartelas" altKey="archigest.archivo.deposito.generarCartelas" page="/pages/images/cartela.gif" styleClass="imgTextMiddle" />
							 &nbsp;<bean:message key="archigest.archivo.deposito.generarCartelas"/>
						</a>
					</TD>
					<TD width="10">&nbsp;</TD>
					<security:permissions action="${appConstants.depositoActions.ALTA_ELEMENTO_ACTION}">
					<TD>
						<c:url var="URICrearDescendientes" value="/action/gestionTipoNoAsignableAction">
							<c:param name="method" value="altaDescendientes"/>
							<c:param name="idPadre" value="${elementoNoAsignable.id}"/>
							<c:param name="tipoElementoPadre" value="${elementoNoAsignable.idTipoElemento}"/>
						</c:url>
						<a class="etiquetaAzul12Bold" href='<c:out value="${URICrearDescendientes}" escapeXml="false"/>'>
							<html:img page="/pages/images/new.gif" altKey="archigest.archivo.deposito.crearDescendientes" titleKey="archigest.archivo.deposito.crearDescendientes" styleClass="imgTextMiddle" />
							&nbsp;<bean:message key="archigest.archivo.deposito.crearDescendientes"/>
						</a>
					</TD>
					</security:permissions>
				</TR>
				</TABLE>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<div class="separador8"></div>

				<c:set var="listaDescendientes" value="${requestScope[appConstants.deposito.LISTA_DESCENDIENTES_KEY]}" />
				<display:table name="pageScope.listaDescendientes"
					id="descendiente"
					style="width:99%;margin-left:auto;margin-right:auto">

					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.deposito.noDescendientes"/>
					</display:setProperty>

					<display:column titleKey="archigest.archivo.nombre">
						<c:url var="viewElementURL" value="/action/manageVistaDeposito">
							<c:param name="actionToPerform" value="verNodo" />
							<c:param name="node" value="${descendiente.itemPath}" />
							<c:param name="refreshView" value="true" />
						</c:url>
						<a class="tdlink" href='<c:out value="${viewElementURL}" escapeXml="false"/>' >
							<c:out value="${descendiente.nombre}"/>
						</a>&nbsp;
						<c:if test="${descendiente.asignable && descendiente.signaturacionAHueco}">
							[<c:out value="${descendiente.menorNumeracionHueco}"/>...<c:out value="${descendiente.mayorNumeracionHueco}"/>]
						</c:if>
					</display:column>

				</display:table>

				<div class="separador8"></div>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>