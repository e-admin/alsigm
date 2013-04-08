<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<bean:struts id="actionMapping" mapping="/asignacionCajasReencajado" />
<c:set var="formName" value="${actionMapping.name}" />
<c:set var="form" value="${requestScope[formName]}" />

<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}" />
<c:set var="unidadInstalacion" value="${sessionScope[appConstants.transferencias.CAJA_KEY]}" />
<c:set var="reencajadoView" value="${requestScope[appConstants.transferencias.REENCAJADO_VIEW_OBJECT_KEY]}" />
<c:set var="numCaja" value="${requestScope[appConstants.transferencias.NUM_CAJA]}" />
<c:set var="udocs" value="${reencajadoView.listaUdocs}" />

<script>
	function crearCaja(url){
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
			var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
			window.top.showWorkingDiv(title, message);
		}
		goTo(url);
	}

	function sacarExpediente(){
		var form = document.forms['<c:out value="${actionMapping.name}"/>'];
		if (form.udocSeleccionada && elementSelected(form.udocSeleccionada)) {
			form.method.value = "sacarExpediente";
			form.submit();
		} else
			alert("<bean:message key='archigest.archivo.transferencias.msgNoUDocSeleccionada'/>");
	}

	function incorporarExpediente(){
		var form = document.forms['<c:out value="${actionMapping.name}" />'];
		form.method.value = "incorporarACaja";
		form.submit();
	}

	function subirExpediente(){
		var form = document.forms['<c:out value="${actionMapping.name}" />'];
		if (form.udocSeleccionada && elementSelected(form.udocSeleccionada)) {
			form.method.value = "subirDentroDeCaja";
			form.submit();
		} else
			alert("<bean:message key='archigest.archivo.transferencias.msgNoUDocSeleccionada'/>");
	}

	function bajarExpediente(){
		var form = document.forms['<c:out value="${actionMapping.name}" />'];
		if (form.udocSeleccionada && elementSelected(form.udocSeleccionada)) {
			form.method.value = "bajarDentroDeCaja";
			form.submit();
		} else
			alert("<bean:message key='archigest.archivo.transferencias.msgNoUDocSeleccionada'/>");
	}

	function editarDescripcionContenido(){
		var form = document.forms['<c:out value="${actionMapping.name}" />'];
		if (form.udocSeleccionada && elementSelected(form.udocSeleccionada)) {
			form.method.value = "editarDescripcionUdoc";
			form.submit();
		} else
			alert("<bean:message key='archigest.archivo.transferencias.msgNoUDocSeleccionada'/>");
	}

	function editarDescripcionUI(){
		var form = document.forms['<c:out value="${actionMapping.name}" />'];
		form.method.value = "editarDescripcionUI";
		form.submit();
	}

	function dividirUdoc(){
		var form = document.forms['<c:out value="${actionMapping.name}" />'];
		if (form.udocSeleccionada && elementSelected(form.udocSeleccionada)) {
			form.method.value = "dividirUdoc";
			form.submit();
		} else
			alert("<bean:message key='archigest.archivo.transferencias.msgNoUDocSeleccionada'/>");
	}

	function eliminarParte(){
		var form = document.forms['<c:out value="${actionMapping.name}" />'];
		if (form.udocSeleccionada && elementSelected(form.udocSeleccionada)) {
			form.method.value = "eliminarParte";
			form.submit();
		} else
			alert("<bean:message key='archigest.archivo.transferencias.msgNoUDocSeleccionada'/>");
	}
</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.transferencias.organizacionCaja"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
			<TR>
				<c:if test="${!empty reencajadoView.udocsSinAsignar}">
				<TD>
					<c:url var="creacionCajaURL" value="/action/asignacionCajasReencajado">
						<c:param name="method" value="nuevaCaja" />
						<c:param name="idRelacion" value="${vRelacion.id}" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="javascript:crearCaja('<c:out value="${creacionCajaURL}" escapeXml="false"/>');">
						<html:img page="/pages/images/caja_new.gif"
							altKey="archigest.archivo.transferencias.crearCaja"
							titleKey="archigest.archivo.transferencias.crearCaja"
							styleClass="imgTextBottom"/>
						&nbsp;<bean:message key="archigest.archivo.transferencias.crearCaja"/>
					</a>
				</TD>
				</c:if>
				<TD width="10">&nbsp;</TD>
				<TD>
					<tiles:insert definition="button.closeButton" flush="true"/>
				</TD>
			</TR>
		</TABLE>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<html:form action="/asignacionCajasReencajado">
		<html:hidden property="ordenCaja"/>
		<html:hidden property="idRelacion"/>
		<html:hidden property="idUnidadInstalacion"/>
		<input type="hidden" name="method" value="" />

		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">relacionContextoUdoc</tiles:put>
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.transferencias.relacionEntrega"/></tiles:put>
			<tiles:put name="visibleContent" direct="true">
				<tiles:insert page="/pages/tiles/transferencias/relaciones/cabeceracte_relacion.jsp" />
			</tiles:put>
			<tiles:put name="dockableContent" direct="true">
				<tiles:insert page="/pages/tiles/transferencias/relaciones/datos_view_relacion.jsp" />
			</tiles:put>
		</tiles:insert>

		<div class="separador8">&nbsp;</div>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.transferencias.informacionUI"/> <c:out value="${numCaja}"/>
			</tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<TABLE cellpadding=0 cellspacing=0>
				 <TR>
					<c:if test="${reencajadoView.editarDescripcionUI}">
						<td>
							<a class="etiquetaAzul11Normal" href="javascript:editarDescripcionUI()">
								<html:img page="/pages/images/editDoc.gif" styleClass="imgTextMiddle"
									altKey="archigest.archivo.descripcion" titleKey="archigest.archivo.descripcion"/>
								<bean:message key="archigest.archivo.descripcion"/>
							</a>
						</td>
					</c:if>
				</TR>
				</TABLE>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<TABLE class="formulario">
					<TR>
						<TD class="tdTitulo" width="100px">
							<bean:message key="archigest.archivo.descripcion"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:if test="${!empty unidadInstalacion.descripcion}">
								<c:out value="${unidadInstalacion.descripcion}"/>
							</c:if>
							<c:if test="${empty unidadInstalacion.descripcion}">--</c:if>
						</TD>
					</TR>
				</TABLE>
			</tiles:put>
		</tiles:insert>

		<div class="separador8">&nbsp;</div>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.transferencias.cotejoCaja.contenido"/> <c:out value="${numCaja}"/>
			</tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<TABLE cellpadding=0 cellspacing=0>
				 <TR>
					<c:if test="${reencajadoView.editarDescripcionUdoc}">
						<td>
							<a class="etiquetaAzul11Normal" href="javascript:editarDescripcionContenido()"  onclick="">
								<html:img page="/pages/images/editDoc.gif" styleClass="imgTextMiddle"
									altKey="archigest.archivo.descripcion" titleKey="archigest.archivo.descripcion"/>
								<bean:message key="archigest.archivo.descripcion"/>
							</a>
						</td>
					</c:if>
					<c:if test="${reencajadoView.mostrarBotonSubir}">
						<td width="10">&nbsp;</td>
						<td>
							<a class="etiquetaAzul11Normal" href="javascript:subirExpediente()" >
								<html:img page="/pages/images/caja_subir.gif" styleClass="imgTextMiddle"
									altKey="archigest.archivo.subirUDoc" titleKey="archigest.archivo.subirUDoc"/>
								&nbsp;<bean:message key="archigest.archivo.subir"/>
							</a>
						</td>
					</c:if>
					<c:if test="${reencajadoView.mostrarBotonBajar}">
						<td width="10">&nbsp;</td>
						<td>
							<a class="etiquetaAzul11Normal" href="javascript:bajarExpediente()">
								<html:img page="/pages/images/caja_bajar.gif" styleClass="imgTextMiddle"
									altKey="archigest.archivo.bajarUDoc" titleKey="archigest.archivo.bajarUDoc"/>
								&nbsp;<bean:message key="archigest.archivo.bajar"/>
							</a>
						</td>
					</c:if>
					<c:if test="${reencajadoView.mostrarBotonExtraer}">
						<td width="10">&nbsp;</td>
						<td>
							<a class="etiquetaAzul11Normal" href="javascript:sacarExpediente();" >
								<html:img page="/pages/images/caja_quitar.gif" styleClass="imgTextMiddle"
									altKey="archigest.archivo.extraer" titleKey="archigest.archivo.extraer"/>
								&nbsp;<bean:message key="archigest.archivo.extraer"/>
							</a>
						</td>
					</c:if>
					<c:if test="${reencajadoView.mostrarBotonIncorporar}">
						<td width="10">&nbsp;</td>
						<td>
							<a class="etiquetaAzul11Normal" href="javascript:incorporarExpediente();" >
								<html:img page="/pages/images/addDoc.gif" styleClass="imgTextMiddle"
									altKey="archigest.archivo.incorporar" titleKey="archigest.archivo.incorporar"/>
								&nbsp;<bean:message key="archigest.archivo.incorporar"/>
							</a>
						</td>
					</c:if>
					<c:if test="${reencajadoView.mostrarBotonDividir && !vRelacion.nivelDocumentalFraccionSerie}">
						<td width="10">&nbsp;</td>
						<td>
							<a class="etiquetaAzul11Normal" href="javascript:dividirUdoc();" >
								<html:img page="/pages/images/dividir.gif" styleClass="imgTextMiddle"
									altKey="archigest.archivo.transferencias.dividir" titleKey="archigest.archivo.transferencias.dividir"/>
								&nbsp;<bean:message key="archigest.archivo.transferencias.dividir"/>
							</a>
						</td>
					</c:if>
					<c:if test="${reencajadoView.mostrarBotonEliminarParte}">
						<td width="10">&nbsp;</td>
						<td>
							<a class="etiquetaAzul11Normal" href="javascript:eliminarParte();" >
								<html:img page="/pages/images/deleteParte.gif" styleClass="imgTextMiddle"
									altKey="archigest.archivo.transferencias.eliminarParte" titleKey="archigest.archivo.transferencias.eliminarParte"/>
								&nbsp;<bean:message key="archigest.archivo.transferencias.eliminarParte"/>
							</a>
						</td>
					</c:if>
				 </TR>
				</TABLE>
			</tiles:put>

			<tiles:put name="blockContent" direct="true">
				<div class="separador8"></div>
				<c:if test="${!empty udocs}">
					<TABLE cellpadding=0 cellspacing=0 class="w100">
					  <TR>
					   <TD align="right">
							<a class="etiquetaAzul12Normal" href="javascript:seleccionarCheckboxSet(document.forms[0].udocSeleccionada);" >
								<html:img page="/pages/images/checked.gif" border="0" altKey="archigest.archivo.selTodos" titleKey="archigest.archivo.selTodos" styleClass="imgTextBottom" />
								<bean:message key="archigest.archivo.selTodos"/>&nbsp;
							</a>&nbsp;
							<a class=etiquetaAzul12Normal href="javascript:deseleccionarCheckboxSet(document.forms[0].udocSeleccionada);" >
								<html:img page="/pages/images/check.gif" border="0" altKey="archigest.archivo.quitarSel" titleKey="archigest.archivo.quitarSel" styleClass="imgTextBottom" />
								&nbsp;<bean:message key="archigest.archivo.quitarSel"/>
							</a>&nbsp;&nbsp;
					   </TD>
					 </TR>
					</TABLE>
				</c:if>

				<div class="separador8"></div>
				<display:table name="pageScope.udocs" id="udoc"
					style="width:99%;margin-left:auto;margin-right:auto">

					<display:setProperty name="basic.show.header" value="false" />
					<display:setProperty name="basic.msg.empty_list">
						<div style="width:99%;border-bottom:1px dotted #999999;text-align:left;">
							&nbsp;&nbsp;<bean:message key="archigest.archivo.transferencias.noUDCaja"/>
						</div>
					</display:setProperty>
					<display:column style="width:10px">
						<html-el:multibox property="udocSeleccionada" value="${udoc.id}" ></html-el:multibox>
					</display:column>
					<c:if test="${vRelacion.formato.multidoc}">
						<display:column title="" style="width:40px;text-align:right;">
							<c:out value="${udoc_rowNum}" />
						</display:column>
					</c:if>
					<display:column titleKey="archigest.archivo.transferencias.nExp" style="width:13%">
						<c:out value="${udoc.numExp}" /><c:out value="${udoc.parteExp}"/>
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.asunto">
						<c:url var="infoUdocURL" value="/action/gestionUdocsCF">
							<c:param name="method" value="verEnFondos" />
							<c:param name="unidadDocumental" value="${udoc.idUnidadDoc}" />
						</c:url>
						<a href="<c:out value="${infoUdocURL}" escapeXml="false"/>" class="tdlink">
							<c:out value="${udoc.asunto}" />
						</a>
						<c:if test="${!empty udoc.descripcion}">
							<br><span class="etiquetaNegra11Normal"><br><b><i><bean:message key="archigest.archivo.transferencias.descContenido"/>:</i></b> <c:out value="${udoc.descripcion}" /></span>
						</c:if>
						<c:if test="${!empty unidadInstalacion.notasCotejo}">
							<br><span class="etiquetaNegra11Normal"><br><b><i><bean:message key="archigest.archivo.transferencias.notasCotejo"/>:</i></b><c:out value="${unidadInstalacion.notasCotejo}" /></span>
						</c:if>
					</display:column>
				</display:table>
				<div class="separador8">&nbsp;</div>
			</tiles:put>
		</tiles:insert>
		</html:form>
	</tiles:put>
</tiles:insert>