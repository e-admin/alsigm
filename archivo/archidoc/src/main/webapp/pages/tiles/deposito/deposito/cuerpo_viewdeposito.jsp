<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="ubicacion" value="${requestScope[appConstants.deposito.EDIFICIO_KEY]}" />
<c:set var="archivos" value="${sessionScope[appConstants.controlAcceso.LISTA_ARCHIVOS]}" />
<bean:struts id="mappingGestionDeposito" mapping="/gestionDepositoAction" />

<script language="javascript">
	function verInformeOcupacion(url){
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
			var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
			window.top.showWorkingDiv(title, message);
		}
		window.location = url;
	}

</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.deposito.elementoDeposito"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">

		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<security:permissions action="${appConstants.depositoActions.MODIFICAR_ELEMENTO_ACTION}">
			<TD>
				<c:url var="editarUbicacionURL" value="/action/gestionDepositoAction">
					<c:param name="method" value="editarUbicacion" />
					<c:param name="idUbicacion" value="${ubicacion.id}" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${editarUbicacionURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/edit.gif" altKey="archigest.archivo.editar" titleKey="archigest.archivo.editar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.editar"/>
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>
			</security:permissions>
			<security:permissions action="${appConstants.depositoActions.ELIMINACION_ELEMENTO_ACTION}">
			<TD>
				<c:url var="eliminarUbicacionURL" value="/action/gestionDepositoAction">
					<c:param name="method" value="eliminarUbicacion" />
					<c:param name="idUbicacion" value="${ubicacion.id}" />
				</c:url>
				<script>
					function eliminar() {
						if (confirm("<bean:message key="archigest.archivo.deposito.eliminarUbicacionMsg"/>"))
							window.location = "<c:out value="${eliminarUbicacionURL}" escapeXml="false" />";
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

		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.deposito.datos.ubicacion"/>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
			<table class="formulario">
				<tr>
					<td width="100px" class="tdTitulo">
						<bean:message key="archigest.archivo.deposito.nombre"/>:&nbsp;
					</td>
					<td class="tdDatosBold">
						<c:out value="${ubicacion.nombre}" />
					</td>
				</tr>
				<tr>
					<td class="tdTitulo">
						<bean:message key="archigest.archivo.deposito.ubicacion"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<c:out value="${ubicacion.ubicacion}" />
					</td>
				</tr>
				<tr>
					<td class="tdTitulo">
						<bean:message key="archigest.archivo.archivo"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<c:out value="${ubicacion.nombreArchivo}" />
					</td>
				</tr>



			</table>
			</tiles:put>
		</tiles:insert>

		<div class="separador8"></div>

		<c:set var="resumenOcupacion" value="${requestScope[appConstants.deposito.RESUMEN_OCUPACION]}" />
		<c:if test="${resumenOcupacion.totalHuecos > 0}">
			<tiles:insert definition="deposito.datosOcupacion">
				<tiles:put name="blockTitle" direct="true">
					<bean:message key="archigest.archivo.deposito.resumen.ocupacion"/>
				</tiles:put>
				<tiles:put name="buttonBar" direct="true">
					<TABLE cellpadding=0 cellspacing=0>
					<TR>
						<TD>
						<c:url var="verOcupacionURL" value="/action/gestionDepositoAction">
							<c:param name="method" value="verOcupacion" />
							<c:param name="idUbicacion" value="${ubicacion.id}" />
						</c:url>
						<a class="etiquetaAzul12Bold" href="javascript:verInformeOcupacion('<c:out value="${verOcupacionURL}" escapeXml="false"/>');" >
							<html:img page="/pages/images/ocupacion.gif" altKey="archigest.archivo.deposito.informeOcupacion" titleKey="archigest.archivo.deposito.informeOcupacion" styleClass="imgTextMiddle" />
							<bean:message key="archigest.archivo.deposito.informeOcupacion" />
						</a>
						</TD>
					</TR>
					</TABLE>
				</tiles:put>
			</tiles:insert>
			<div class="separador8"></div>
		</c:if>


		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.deposito.descendientes"/>
			</tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<TABLE cellpadding=0 cellspacing=0>
				<TR>
					<TD>
						<c:url var="urlImprimir" value="/action/gestionTipoNoAsignableAction">
							<c:param name="method" value="selCartelas"/>
							<c:param name="idUbicacion" value="${ubicacion.id}"/>
						</c:url>
						<a class="etiquetaAzul12Normal" href="<c:out value="${urlImprimir}" escapeXml="false"/>" >
							<html:img titleKey="archigest.archivo.deposito.generarCartelas" altKey="archigest.archivo.deposito.generarCartelas" page="/pages/images/cartela.gif" styleClass="imgTextMiddle" />
							 &nbsp;<bean:message key="archigest.archivo.deposito.generarCartelas"/>
						</a>
					</TD>
					<TD width="10">&nbsp;</TD>
					<security:permissions action="${appConstants.depositoActions.ALTA_ELEMENTO_ACTION}">
					<TD>
						<c:url var="URICrearDescendientes" value="/action/gestionTipoNoAsignableAction	">
							<c:param name="method" value="altaDescendientes"/>
							<c:param name="idUbicacion" value="${ubicacion.id}"/>
							<c:param name="tipoElementoPadre" value="${ubicacion.idTipoElemento}"/>
						</c:url>
						<a class="etiquetaAzul12Bold" href="<c:out value="${URICrearDescendientes}" escapeXml="false"/>">
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

					<display:column titleKey="archigest.archivo.cf.nombre">
						<c:url var="viewElementURL" value="/action/manageVistaDeposito">
							<c:param name="actionToPerform" value="verNodo" />
							<c:param name="node" value="${descendiente.itemPath}" />
							<c:param name="refreshView" value="true" />
						</c:url>
						<a class="tdlink" href='<c:out value="${viewElementURL}" escapeXml="false"/>' >
							<c:out value="${descendiente.nombre}"/>
						</a>
					</display:column>

				</display:table>

				<div class="separador8"></div>
			</tiles:put>
		</tiles:insert>

	</tiles:put>
</tiles:insert>