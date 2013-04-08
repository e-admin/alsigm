<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="eliminacion" value="${sessionScope[appConstants.valoracion.ELIMINACION_KEY]}" />
<c:set var="archivo" value="${sessionScope[appConstants.transferencias.ARCHIVO_KEY]}" />
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>

<c:set var="numCajasParciales" value="${requestScope[appConstants.valoracion.ELIMINACION_CON_UI_PARCIALES_KEY]}" />

<c:set var="showCajasParciales" value="${requestScope[appConstants.valoracion.VER_UI_PARCIALES_KEY]}" />


<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.eliminacion.uDocsSeleccion"/></tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table>
			<tr>
				<c:if test="${!param.ocultarInforme}">
	                <security:permissions action="${appConstants.fondosActions.IMPRIMIR_SELECCION_ACTION}">
					<td width="10">&nbsp;</td>
					<td nowrap>
						<c:url var="URL" value="/action/informeSeleccion">
							<c:param name="id" value="${eliminacion.id}" />
							<c:if test="${!empty archivo}">
								<c:param name="idArchivo" value="${archivo.id}" />
							</c:if>
						</c:url>
						<a class="etiquetaAzul12Bold"
						   href="<c:out value="${URL}" escapeXml="false"/>"
						><html:img page="/pages/images/documentos/doc_pdf.gif"
						        border="0"
						        altKey="archigest.archivo.informe"
						        titleKey="archigest.archivo.informe"
						        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.informe"/></a>
					</td>
					</security:permissions>
					<td width="10">&nbsp;</td>
				</c:if>
				<td nowrap>
					<c:url var="cerrarURL" value="/action/gestionEliminacion">
						<c:param name="method" value="goBack" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${cerrarURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.cerrar"/></a>
				</td>
			</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">

	<%--datos de eliminación--%>
	<div id="datosEliminacion" class="bloque">

	<TABLE class="formulario"> <%--para aspecto de formulario con lineas bottom de celda --%>
	<TR>
		<TD class="tdTitulo" width="250px">
			<bean:message key="archigest.archivo.eliminacion.titulo"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<c:out value="${eliminacion.titulo}" />
		</TD>
	</TR>
	<TR>
		<TD class="tdTitulo" width="250px">
			<bean:message key="archigest.archivo.eliminacion.valoracion.titulo"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<c:url var="infoValoracionURL" value="/action/gestionValoracion">
				<c:param name="method" value="verValoracion" />
				<c:param name="id" value="${eliminacion.valoracion.id}" />
			</c:url>
			<a href="<c:out value="${infoValoracionURL}" escapeXml="false"/>" >
				<c:out value="${eliminacion.valoracion.titulo}" />
			</a>
		</TD>
	</TR>
	<TR>
		<TD class="tdTitulo" width="250px">
			<bean:message key="archigest.archivo.eliminacion.maxvigencia"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<c:out value="${eliminacion.maxAnosVigencia}" />
		</TD>
	</TR>
	<TR>
		<TD class="tdTitulo" width="250px">
			<bean:message key="archigest.archivo.eliminacion.notas"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<c:out value="${eliminacion.notas}" />
		</TD>
	</TR>
		<TR>
		<TD class="tdTitulo" width="250px">
			<bean:message key="archigest.archivo.eliminacion.tipoEliminacion"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<fmt:message key="archigest.archivo.eliminacion.tipoEliminacion${eliminacion.tipoEliminacion}" />
		</TD>
	</TR>

	<c:if test="${showCajasParciales}">

	<TR>
		<TD class="tdTitulo" width="250px">
			<bean:message key="archigest.archivo.uis.parciales.label"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<c:choose>
			<c:when test="${not empty numCajasParciales}">
				<c:if test="${numCajasParciales > 0}">
					<c:out value="${numCajasParciales}"/>
					<html:img page="/pages/images/eliminaciones/cajaParcial.gif"
										        border="0"
										        altKey="archigest.archivo.ui.parcial"
										        titleKey="archigest.archivo.ui.parcial"
										        styleClass="imgTextMiddle"/>
				</c:if>

				<c:if test="${numCajasParciales == 0}">
					<html:img page="/pages/images/eliminaciones/cajaCompleta.gif"
										        border="0"
										        altKey="archigest.archivo.ui.completa"
										        titleKey="archigest.archivo.ui.completa"
										        styleClass="imgTextMiddle"/>
				</c:if>
			</c:when>
			</c:choose>
		</TD>
	</TR>
	</c:if>

	</TABLE>

	</div>

	<%--Display con las series a seleccionar --%>
	<div class="separador5"></div>

	<%--datos udocs asociadas a la eliminacion--%>
	<div id="datosUdocs" class="bloque" style="padding-top:6px;padding-bottom:6px">

	<c:set var="listaUdocs" value="${requestScope[appConstants.valoracion.LISTA_UDOCS_KEY]}" />

		<c:set var="LISTA_UDOCS_KEY" value="pageScope.listaUdocs"/>
		<jsp:useBean id="LISTA_UDOCS_KEY" type="java.lang.String" />
		<display:table name="<%=LISTA_UDOCS_KEY%>"
				style="width:99%;margin-left:auto;margin-right:auto"
				id="udoc"
				pagesize="15"
				sort="list"
				requestURI="../../action/gestionEliminacion?method=verUdocs"
				export="true">
			<display:setProperty name="basic.msg.empty_list">
				<bean:message key="archigest.archivo.eliminacion.noUDocsSeleccion"/>
			</display:setProperty>
			<display:column titleKey="archigest.archivo.num">
				<fmt:formatNumber value="${udoc_rowNum}" pattern="000"/>
			</display:column>
			<display:column titleKey="archigest.archivo.codigo" property="codigo" />
			<display:column titleKey="archigest.archivo.signatura" property="signaturaudoc" />
			<display:column titleKey="archigest.archivo.titulo" property="titulo"/>
			<display:column titleKey="archigest.archivo.ubicacion">
				<c:out value="${udoc.ubicacion}"/>
			</display:column>
			<display:column titleKey="archigest.archivo.fechaInicial">
				<fmt:formatDate value="${udoc.fechaIniUdoc}" pattern="${FORMATO_FECHA}" />
			</display:column>
			<display:column titleKey="archigest.archivo.fechaFinal">
				<fmt:formatDate value="${udoc.fechaFinUdoc}" pattern="${FORMATO_FECHA}" />
			</display:column>

			<c:if test="${showCajasParciales}">
			<display:column style="width:20px" >
				<c:choose>
					<c:when test="${udoc.cajaParcial}">
						<html:img page="/pages/images/eliminaciones/cajaParcial.gif"
											        border="0"
											        altKey="archigest.archivo.caja.parcial"
											        titleKey="archigest.archivo.caja.parcial"
											        styleClass="imgTextMiddle"/>
					</c:when>
					<c:when test="${udoc.cajaCompleta}">
						<html:img page="/pages/images/eliminaciones/cajaCompleta.gif"
											        border="0"
											        altKey="archigest.archivo.ui.completa"
											        titleKey="archigest.archivo.ui.completa"
											        styleClass="imgTextMiddle"/>
					</c:when>
				</c:choose>

			</display:column>
			</c:if>
		</display:table>
	</div>

	</tiles:put>
</tiles:insert>