<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="bPrevision" value="${sessionScope[appConstants.transferencias.PREVISION_KEY]}"/>
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>


<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><fmt:message key="archigest.archivo.transferencias.datosPrevision" /></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table><tr>
			<td>
			<c:url var="atrasURL" value="/action/gestionRelaciones">
				<c:param name="method" value="goBack" />
			</c:url>
			<a class=etiquetaAzul12Bold href="<c:out value="${atrasURL}" escapeXml="false"/>" >
				<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextTop" />
				&nbsp;<bean:message key="archigest.archivo.cerrar"/>
			</a>
			</td>
		</tr></table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:definition id="prevision" extends="transferencias.previsiones.BloqueDatosPrevision">
			<tiles:put name="blockTitle" direct="true"><fmt:message key="archigest.archivo.transferencias.relacionesPrevision" /></tiles:put>
		</tiles:definition>

		<tiles:insert beanName="prevision" />
		<div class="separador5">&nbsp; <%-- 5 pixels de separacion --%></div>

		<c:set var="detallePrevision" value="${requestScope[appConstants.transferencias.DETALLEPREVISION_KEY]}" />
		<c:if test="${!empty detallePrevision}">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">Línea de detalle</tiles:put>

			<tiles:put name="buttonBar" direct="true">
			<TABLE cellpadding=0 cellspacing=0>
			  <TR>
				<TD>
					<html:img page="/pages/images/pixel.gif" styleClass="imgTextMiddle" height="18"/>
				</TD>
			 </TR>
			</TABLE>
			</tiles:put>

			<tiles:put name="blockContent" direct="true">
			<TABLE class="formulario" cellpadding=0 cellspacing=0>
					<TR>
						<TD class="tdTitulo" width="220px">
							Número línea detalle:&nbsp;
						</TD>
						<TD class="tdDatos">
							<fmt:formatNumber value="${detallePrevision.numeroOrden}" pattern="000"/>
						</TD>
					</TR>
					<c:if test="${bPrevision.ordinaria}">
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.sistProd"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${detallePrevision.nombreSistProductor}" />
						</TD>
					</TR>
					</c:if>
					<c:if test="${!bPrevision.entreArchivos}">
						<TR>
							<TD class="tdTitulo">
								<bean:message key="archigest.archivo.transferencias.procedimiento"/>:&nbsp;
							</TD>
							<TD class="tdDatos">
								<c:out value="${detallePrevision.procedimiento.codigo}" />&nbsp;<c:out value="${detallePrevision.procedimiento.nombre}" />
							</TD>
						</TR>
					</c:if>
					<c:if test="${bPrevision.entreArchivos}">
						<TR>
							<TD class="tdTitulo">
								<bean:message key="archigest.archivo.archivoRemitente"/>:&nbsp;
							</TD>
							<TD class="tdDatos" id="nombreArchivoRemitente">
								<c:out value="${bPrevision.nombrearchivoremitente}"/>
							</TD>
						</TR>
						<TR>
							<TD class="tdTitulo">
								<bean:message key="archigest.archivo.transferencias.archRecep"/>:&nbsp;
							</TD>
							<TD class="tdDatos">
								<c:out value="${bPrevision.nombrearchivoreceptor}"/>
							</TD>
						</TR>
					</c:if>
					<c:choose>
						<c:when test="${!bPrevision.entreArchivos}">
							<TR>
								<TD class="tdTitulo">
									<bean:message key="archigest.archivo.transferencias.serie"/>:&nbsp;
								</TD>
								<TD class="tdDatos">
									<c:out value="${detallePrevision.serieDestino.codigo}" />
									<c:out value="${detallePrevision.serieDestino.denominacion}" />
								</TD>
							</TR>
						</c:when>
						<c:otherwise>
							<TR>
								<TD class="tdTitulo">
									<bean:message key="archigest.archivo.transferencias.serieOrigen"/>:&nbsp;
								</TD>
								<TD class="tdDatos">
									<c:out value="${detallePrevision.serieOrigen.codReferencia}" />
									<c:out value="${detallePrevision.serieOrigen.denominacion}" />
								</TD>
							</TR>
							<TR>
								<TD class="tdTitulo">
									<bean:message key="archigest.archivo.transferencias.serieDestino"/>:&nbsp;
								</TD>
								<TD class="tdDatos">
									<c:out value="${detallePrevision.serieDestino.codReferencia}" />
									<c:out value="${detallePrevision.serieDestino.denominacion}" />
								</TD>
							</TR>
						</c:otherwise>
					</c:choose>
					<c:if test="${!bPrevision.entreArchivos}">
						<TR>
							<TD class="tdTitulo" width="220px">
								<bean:message key="archigest.archivo.transferencias.funcion"/>:&nbsp;
							</TD>
							<TD class="tdDatos">
								<c:out value="${detallePrevision.funcion.codReferencia}" />
								<c:out value="${detallePrevision.funcion.titulo}" />
							</TD>
						</TR>
					</c:if>
					<TR>
						<TD class="tdTitulo" colspan="2">
							<bean:message key="archigest.archivo.transferencias.rangoFechas"/>:&nbsp;
						</TD>
					</TR>
					<TR>
						<TD class="tdTitulo">
							&nbsp;
						</TD>
						<TD class="tdDatos">
							<TABLE cellpadding="0" cellspacing="0">
							<TR>
								<TD width="100px" class="td2Titulo">
									<bean:message key="archigest.archivo.transferencias.fechaInicioExps"/>:&nbsp;
								</TD>
								<TD width="50px" class="td2Datos">
									<c:out value="${detallePrevision.anoIniUdoc}" />
								</TD>
								<TD width="20px">
								</TD>
								<TD width="100px" class="td2Titulo">
									<bean:message key="archigest.archivo.transferencias.fechaFinExps"/>:&nbsp;
								</TD>
								<TD width="50px" class="td2Datos">
									<c:out value="${detallePrevision.anoFinUdoc}" />
								</TD>
							</TR>
							</TABLE>
						</TD>
					</TR>
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.nUndInstal"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${detallePrevision.numUInstalacion}" />
						</TD>
					</TR>
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.formato"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${detallePrevision.formato.nombre}" />
						</TD>
					</TR>
					<TR>
						<TD class="tdTitulo" style="vertical-align:top">
							<bean:message key="archigest.archivo.transferencias.observaciones"/>:
						</TD>
						<TD class="tdDatos">
							<c:out value="${detallePrevision.observaciones}" />
						</TD>
					</TR>
			</TABLE>
			</tiles:put>
		</tiles:insert>
		</c:if>

		<div class="separador5">&nbsp; <%-- 5 pixels de separacion --%></div>

		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
				<bean:message key="archigest.archivo.transferencias.relacionesAsociadas"/>
			</tiles:put>

			<tiles:put name="blockContent" direct="true">
				<c:set var="listaRelaciones" value="${requestScope[appConstants.transferencias.LISTA_RELACIONES_KEY]}"/>

				<c:url var="relacionesPrevisionPaginationURI" value="/action/gestionRelaciones" />
				<jsp:useBean id="relacionesPrevisionPaginationURI" type="java.lang.String" />

				<display:table name="pageScope.listaRelaciones"
					id="listaRegistros"
					pagesize="10"
					decorator="transferencias.decorators.ViewRelacionEntregaDecorator"
					requestURI='<%=relacionesPrevisionPaginationURI%>'
					sort="list"
					export="false"
					style="width:99%;margin-left:auto;margin-right:auto"
					defaultorder="descending"
					defaultsort="2"
					>

					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.transferencias.noRel"/>
					</display:setProperty>

					<display:column titleKey="archigest.archivo.relacion" sortable="true" headerClass="sortable" style="white-space: nowrap;">
						<c:url var="URL" value="/action/gestionRelaciones">
							<c:param name="method" value="verrelacion" />
							<c:param name="idrelacionseleccionada" value="${listaRegistros.id}" />
						</c:url>
						<a class="tdlink" href='<c:out value="${URL}" escapeXml="false" />' >
						<c:out value="${listaRegistros.codigoTransferencia}"/>
						</a>
					</display:column>

					<display:column titleKey="archigest.archivo.transferencias.tipoTransf" sortable="true" sortProperty="tipooperacion" headerClass="sortable" >
						<c:set var="keyTitulo">
							archigest.archivo.transferencias.tipooperacion<c:out value="${listaRegistros.tipooperacion}"/>
						</c:set>
						<fmt:message key="${keyTitulo}" />
					</display:column>

					<display:column titleKey="archigest.archivo.transferencias.estado" property="estado" sortable="true" headerClass="sortable"/>

					<display:column titleKey="archigest.archivo.transferencias.fEstado" property="fechaestado" sortable="true" headerClass="sortable" decorator="common.view.DateDecorator" />

					<display:column titleKey="archigest.archivo.transferencias.formato" sortable="true" headerClass="sortable">
						<c:out value="${listaRegistros.formato.nombre}"/>
					</display:column>

					<display:column titleKey="archigest.archivo.transferencias.procedimiento" headerClass="sortable" style="width:25%">
							<c:out value="${listaRegistros.procedimiento.id}"/>&nbsp;<c:out value="${listaRegistros.procedimiento.nombre}"/>
					</display:column>

					<display:column titleKey="archigest.archivo.transferencias.serie" >
						<c:out value="${listaRegistros.serie.codigo}" />
						<c:out value="${listaRegistros.serie.titulo}" />
					</display:column>

				</display:table>

			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>