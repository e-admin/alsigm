<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>

<c:set var="vListaUdocs" value="${sessionScope[appConstants.deposito.LISTA_UDOCS_KEY]}"/>
<c:set var="vHueco" value="${sessionScope[appConstants.deposito.HUECO_KEY]}"/>
<c:set var="vUInstalacionDeposito" value="${sessionScope[appConstants.deposito.UNIDAD_INSTALACION_DEPOSITO_KEY]}"/>
<c:set var="vUInstalacionBloqueada" value="${appConstants.marcaUInstalacion.MARCA_BLOQUEADA}"/>

<script language="javascript">
 function aceptarCambioSignatura(valor, posicion, tipo){
	<c:url var="URL" value="/action/verHuecoAction">
	  	<c:param name="method" value="actualizarCampoSignatura"/>
	</c:url>
	window.location = '<c:out value="${URL}"/>' + '&valorSignatura='+ valor;
 }
</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.deposito.contenidoHueco"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<td>
				<c:url var="viewElementURL" value="/action/verHuecoAction">
					<c:param name="method" value="verPadre" />
					<c:param name="itemID" value="${vHueco.idElemAPadre}" />
					<c:param name="itemTipo" value="${sessionScope[appConstants.deposito.TIPO_ELEMENTO_PADRE_HUECO]}" />
					<c:param name="refreshView" value="true" />
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${viewElementURL}" escapeXml="false"/>" target="_self">
					<html:img page="/pages/images/treePadre.gif" altKey="archigest.archivo.cf.verPadre" titleKey="archigest.archivo.cf.verPadre" styleClass="imgTextMiddle" />
					<bean:message key="archigest.archivo.cf.verPadre"/>
				</a>
			</td>
			<TD width="10">&nbsp;</TD>
		   <TD>
				<tiles:insert definition="button.closeButton" />
			</TD>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.deposito.unidInstalacion"/>:&nbsp;
			</tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<c:if test="${appConstants.configConstants.mantenerUInstalacionAlCompactar and empty vListaUdocs and not empty vHueco.unidInstalacion.signaturaui}">
					<TABLE cellpadding=0 cellspacing=0>
					<TR>
						<td>
							<c:url var="eliminarCajaURL" value="/action/reubicacionAction">
								<c:param name="method" value="eliminarCajaVacia" />
							</c:url>
							<a class="etiquetaAzul12Bold" href="<c:out value="${eliminarCajaURL}" escapeXml="false"/>">
								<html:img titleKey="archigest.archivo.transferencias.eliminarCaja" altKey="archigest.archivo.transferencias.eliminarCaja" page="/pages/images/caja_delete.gif" styleClass="imgTextMiddle"/>
								<bean:message key="archigest.archivo.transferencias.eliminarCaja"/>
							</a>
						</td>
						<TD width="10">&nbsp;</TD>
					</TR>
					</TABLE>
				</c:if>
			</tiles:put>

			<tiles:put name="blockContent" direct="true">

				<TABLE class="formulario" cellpadding=0 cellspacing=0>
					<TR>
						<TD class="tdTitulo" width="150px">
							<bean:message key="archigest.archivo.ruta"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${vHueco.path}"/>
						</TD>
					</TR>

					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.signatura"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<tiles:insert definition="campo.fecha.editable" flush="true">
								<tiles:put name="editable" value="true" />
								<tiles:put name="nombreFuncionAceptar">aceptarCambioSignatura</tiles:put>
								<tiles:put name="suffix"></tiles:put>
								<tiles:put name="nombreCampo" value="signatura" />
								<tiles:put name="valorCampo"><c:out value="${vHueco.unidInstalacion.signaturaui}"/></tiles:put>
								<tiles:put name="tipo" value=""/>
							</tiles:insert>
						</TD>
					</TR>
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.formato"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${vHueco.nombreformato}"/>
						</TD>
					</TR>
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.documentos.clasificador.form.fechaCreacion"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${vHueco.unidInstalacion.fcreacionString}"/>
						</TD>
					</TR>
				</TABLE>
			</tiles:put>
		</tiles:insert>

		<div class="separador8">&nbsp;</div>

		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.deposito.contenidoUnidInstalacion"/>:&nbsp;
			</tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<c:if test="${(not vUInstalacionDeposito.bloqueada) and (not empty vListaUdocs)}">
					<TABLE cellpadding=0 cellspacing=0>
					<TR>
						<c:set var="udoc2" value="${vListaUdocs[0]}"/>
						<c:if test="${udoc2.unidadDocumental.subtipo != appConstants.fondos.subtiposNivel.CAJA.identificador && vUInstalacionDeposito.multiDoc}">
							<td>
								<c:url var="organizarUdocsURL" value="/action/organizacionUDocsAction">
									<c:param name="method" value="initOrganizarUDocs" />
									<c:param name="idUinstalacion" value="${vHueco.unidInstalacion.id}"/>
									<c:param name="signaturaui" value="${vHueco.unidInstalacion.signaturaui}"/>
									<c:param name="idHueco" value="${vHueco.idElemAPadre}:${vHueco.numorden}"/>
								</c:url>
									<a class="etiquetaAzul12Bold" href="<c:out value="${organizarUdocsURL}" escapeXml="false"/>">
										<html:img titleKey="archigest.archivo.transferencias.organizarCajas" altKey="archigest.archivo.transferencias.organizarCajas" page="/pages/images/org_caja.gif" styleClass="imgTextMiddle"/>
										<bean:message key="archigest.archivo.transferencias.organizarCajas"/>
									</a>
							</td>
							<TD width="10">&nbsp;</TD>
						</c:if>
						<td>
							<c:url var="moverUdocsURL" value="/action/reubicacionUdocsAction">
								<c:param name="method" value="initReubicarUDocs" />
								<c:param name="idUinstalacionOrigen" value="${vHueco.unidInstalacion.id}"/>
								<c:param name="idHuecoOrigen" value="${vHueco.idElemAPadre}:${vHueco.numorden}"/>
							</c:url>
							<a class="etiquetaAzul12Bold" href="<c:out value="${moverUdocsURL}" escapeXml="false"/>" >
								<html:img page="/pages/images/compactar.gif" altKey="archigest.archivo.cf.mover"
								titleKey="archigest.archivo.cf.mover" styleClass="imgTextBottom" />
								<bean:message key="archigest.archivo.deposito.compactar"/>&nbsp;
							</a>
						</td>
						<TD width="10">&nbsp;</TD>
					</TR>
					</TABLE>
				</c:if>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<div class="separador8">&nbsp;</div>
				<display:table name="pageScope.vListaUdocs"
					style="width:99%;margin-left:auto;margin-right:auto"
					id="udoc"
					sort="list"
					export="false">
					<display:column titleKey="archigest.archivo.documentos.digitalizacion.estado.4" style="width:10">
						<c:choose>
							<c:when test="${udoc.validada}"><html:img page="/pages/images/checkbox-yes.gif" /></c:when>
							<c:otherwise><html:img page="/pages/images/checkbox-no.gif" /></c:otherwise>
						</c:choose>
					</display:column>
					<display:column titleKey="archigest.archivo.posicion" style="width:10">
						<fmt:formatNumber value="${udoc_rowNum}" pattern="000"/>
					</display:column>

					<display:column titleKey="archigest.archivo.signatura" >
						<c:url var="verUDocURL" value="/action/verHuecoAction">
							<c:param name="method" value="verUdocEnUI" />
							<c:param name="idUInstalacion" value="${vHueco.iduinstalacion}" />
							<c:param name="posUDoc" value="${udoc.posudocenui}" />
						</c:url>
						<a href="<c:out value="${verUDocURL}" escapeXml="false"/>" class="tdlink" target="_self">
							<c:out value="${udoc.signaturaudoc}"/>
						</a>
					</display:column>
					<display:column titleKey="archigest.archivo.titulo" property="titulo"/>
					<display:column titleKey="archigest.archivo.solicitudes.numExp" property="numExp"/>
					<display:column titleKey="archigest.archivo.descripcion" property="descripcion"/>
				</display:table>
				<div class="separador8">&nbsp;</div>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>


