<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="divisionFraccionSerie" value="${sessionScope[appConstants.fondos.DIVISION_FRACCION_SERIE]}" />
<c:set var="udocEnFS" value="${sessionScope[appConstants.fondos.UNIDAD_DOCUMENTAL_EN_FS]}"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.fondos.datosUnidadDocumental"/> 
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table>
			<tr>
				<c:if test="${divisionFraccionSerie.estado == appConstants.fondos.estadosDivisionFS.VALIDADA && !empty udocEnFS.udocEnCuadroClasificacion}">
					<security:permissions action="${appConstants.fondosActions.CONSULTA_ACTION}">
					<TD>
						<c:url var="showCFURL" value="/action/gestionUdocsCF">
							<c:param name="method" value="verEnFondos" />
							<c:param name="unidadDocumental" value="${udocEnFS.udocEnCuadroClasificacion.id}" />
						</c:url>
						<a class="etiquetaAzul12Bold" href="<c:out value="${showCFURL}" escapeXml="false" />" target="_self">
							<html:img page="/pages/images/tree.gif" altKey="archigest.archivo.cf.verEnFondos" titleKey="archigest.archivo.cf.verEnFondos" styleClass="imgTextMiddle" />
							&nbsp;<bean:message key="archigest.archivo.cf.verEnFondos"/>
						</a>
					</TD>
					<TD width="10px">&nbsp;</TD>
					</security:permissions>
				</c:if>
				<TD nowrap="nowrap">
					<tiles:insert definition="button.closeButton" />
				</TD>
			</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
				<tiles:put name="blockName" direct="true">divisionFSContexto</tiles:put>
				<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.fondos.divisionFS"/></tiles:put>
				<tiles:put name="visibleContent" direct="true">
					<tiles:insert page="/pages/tiles/fondos/fraccionSerie/cabeceracte_divisionFS.jsp"/>
				</tiles:put>
				<tiles:put name="dockableContent" direct="true">
					<tiles:insert page="/pages/tiles/fondos/fraccionSerie/datos_divisionFS.jsp" />
				</tiles:put>
		</tiles:insert>
		<div class="separador8">&nbsp;</div>
		<div class="cabecero_bloque">
			<TABLE class="w98m1" cellpadding=0 cellspacing=0 height="100%">
				<TR>
					<TD class="etiquetaAzul12Bold">
						<c:choose>
							<c:when test="${divisionFraccionSerie.nivelDocumental.subtipo == appConstants.fondos.subtiposNivel.CAJA.identificador}">
								<bean:message key="archigest.archivo.transferencias.fraccionSerie"/>
							</c:when>
							<c:otherwise>
								<bean:message key="archigest.archivo.transferencias.unidadDoc"/>
							</c:otherwise>
						</c:choose>
					</TD>
				</TR>
			</TABLE>
		</div>

		<div class="bloque">
			<TABLE class="formulario" cellpadding=0 cellspacing=0>			
				<TR>
					<TD class="tdTitulo" width="20%">
						<bean:message key="archigest.archivo.transferencias.asunto"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${udocEnFS.asunto}" />
					</TD>
				</TR>
				<TR>
					<TD class="tdTitulo">
						<c:choose>
							<c:when test="${divisionFraccionSerie.nivelDocumental.subtipo == appConstants.fondos.subtiposNivel.CAJA.identificador}">
								<bean:message key="archigest.archivo.transferencias.rangoExpedientes"/>:&nbsp;
							</c:when>
							<c:otherwise>
								<bean:message key="archigest.archivo.transferencias.numExp"/>:&nbsp;
							</c:otherwise>
						</c:choose>
					</TD>
					<TD class="tdDatos">
					<c:choose>
							<c:when test="${divisionFraccionSerie.nivelDocumental.subtipo == appConstants.fondos.subtiposNivel.CAJA.identificador}">
								<table class="tablaFicha">
									<thead>
										<tr>
											<th style="">Desde</th>
											<th style="">Hasta</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="rangosUdoc" items="${udocEnFS.rangos}">
											<tr class="odd">
												<td style=""><c:out value="${rangosUdoc.desde}" /></td>
												<td style=""><c:out value="${rangosUdoc.hasta}" /></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</c:when>
							<c:otherwise>
								<c:out value="${udocEnFS.numExp}" />
							</c:otherwise>
						</c:choose>	
					</TD>
				</TR>
				<TR>
					<TD class="tdTitulo" width="220px">
						<bean:message key="archigest.archivo.transferencias.fIni"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<fmt:formatDate value="${udocEnFS.fechaExtIni}" pattern="${FORMATO_FECHA}"/>
					</TD>
				</TR>
				<TR>
					<TD class="tdTitulo" width="220px">
						<bean:message key="archigest.archivo.transferencias.fFin"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<fmt:formatDate value="${udocEnFS.fechaExtFin}" pattern="${FORMATO_FECHA}"/>
					</TD>
				</TR>				
			</TABLE>
		</div>
	</tiles:put>
</tiles:insert>

