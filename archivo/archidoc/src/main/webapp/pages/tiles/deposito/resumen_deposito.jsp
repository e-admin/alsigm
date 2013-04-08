<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.deposito.ubicacionesDisponibles"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<TD><tiles:insert definition="button.closeButton" /></TD>
		</TR>
		</TABLE>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">


	<div class="separador1">&nbsp;</div>
	<c:set var="ubicacionesBandeja" value="${requestScope[appConstants.deposito.LISTA_ELEMENTOS_BANDEJA_KEY]}"/>

	<c:choose>
		<c:when test="${empty ubicacionesBandeja}">
			<div class="bloque">
				<bean:message key="archigest.archivo.deposito.noUbicacion"/>
			</div>
		</c:when>

		<c:otherwise>
			<TABLE class="w98m1"> 
			<c:forEach items="${ubicacionesBandeja}" var="elementoBandeja">
				<TR>
					<TD>
					<c:choose>
						<c:when test="${elementoBandeja.tipoUbicacion}">
						<div class="cabecero_bloque">
							<TABLE cellpadding=0 cellspacing=0 class="w98">
							<TR>
								<TD class="etiquetaAzul12Bold" width="80%">
									<c:out value="${elementoBandeja.nombre}"/>
								</TD>
								<TD width="20%" align="right">
								<TABLE cellpadding=0 cellspacing=0>
								<TR>
									<TD>
									<a href="javascript:switchDivVisibility('<c:out value="${elementoBandeja.numorden}"/>');">
										<img id='img<c:out value="${elementoBandeja.numorden}"/>'
											src='<c:url value="/pages/images/up.gif" />' 
											class="imgTextBottom" />
									</a>
									</TD>
								</TR>
								</TABLE>
								</TD>
							</TR></TABLE>
						</div>
						<div class="bloque" id='div<c:out value="${elementoBandeja.numorden}"/>' isOpen="true">
							<table class="formulario">
								<tr>
									<td width="150px" class="tdTitulo">
										<bean:message key="archigest.archivo.deposito.total"/>
										<bean:message key="archigest.archivo.deposito.huecos"/>:&nbsp;
									</td>
									<td class="tdDatos">
										<c:out value="${elementoBandeja.informeOcupacion.totalHuecos}"/>
									</td>
								</tr>
								<tr>
									<td class="tdTitulo">
										<bean:message key="archigest.archivo.deposito.huecos"/>
										<bean:message key="archigest.archivo.deposito.libres"/>:&nbsp;
									</td>
									<td class="tdDatos">
										<c:out value="${elementoBandeja.informeOcupacion.totalHuecosLibres}"/>
									</td>
								</tr>
								<tr>
									<td class="tdTitulo">
										<bean:message key="archigest.archivo.deposito.huecos"/>
										<bean:message key="archigest.archivo.deposito.inutilizados"/>:&nbsp;
									</td>
									<td class="tdDatos">
										<c:out value="${elementoBandeja.informeOcupacion.totalHuecosInutilizados}"/>
									</td>
								</tr>
								<tr>
									<td class="tdTitulo">
										<bean:message key="archigest.archivo.deposito.porcentaje"/>
										<bean:message key="archigest.archivo.deposito.ocupacion"/>:&nbsp;
									</td>
									<td class="tdDatos">
										<c:out value="${elementoBandeja.informeOcupacion.porcentajeOcupacion}"/>
										<bean:message key="archigest.archivo.simbolo.porcentaje"/>
									</td>
								</tr>
								<tr>
									<td class="tdTitulo">
										<bean:message key="archigest.archivo.deposito.longitud"/>
										<bean:message key="archigest.archivo.deposito.total"/>:&nbsp;
									</td>
									<td class="tdDatos">
										<c:set var="metros" value="0"/>
										<c:choose>
											<c:when test="${elementoBandeja.informeOcupacion.totalLongitud gt 0}">
												<c:set var="metros" value="${elementoBandeja.informeOcupacion.longitudEnMetros}"/>
											</c:when>
										</c:choose>
										<c:out value="${metros}" />&nbsp;<bean:message key="archigest.archivo.m" />
									</td>
								</tr>
							</table>
							
							<c:set var="depositos" value="${elementoBandeja.informeOcupacion.entradasInforme}"/>
							<c:forEach items="${depositos}" var="elementoDeposito">
							<table class="its" align="center" style="width:99%; margin-bottom:10px;margin-top:10px;margin-left:auto;margin-right:auto" id="entradaInforme">
								<thead>
									<tr>
									<th><bean:message key="archigest.archivo.descripcion"/></th>
									<th><bean:message key="archigest.archivo.deposito.huecos"/></th>
									<th><bean:message key="archigest.archivo.deposito.huecos"/>&nbsp;<bean:message key="archigest.archivo.deposito.libres"/></th>
									<th><bean:message key="archigest.archivo.deposito.huecos"/>&nbsp;<bean:message key="archigest.archivo.deposito.inutilizados"/></th>
									<th><bean:message key="archigest.archivo.simbolo.porcentaje"/>&nbsp;<bean:message key="archigest.archivo.deposito.ocupacion"/></th>
									<th><bean:message key="archigest.archivo.deposito.longitud"/></th></tr>
								</thead>
								<tbody>
									<tr class="odd">
									<td>
										<c:url var="urlVerElemento" value="/action/manageVistaDeposito">
											<c:param name="actionToPerform" value="goHome"/>
											<c:param name="itemID" value="${elementoDeposito.idElemento}"/>
											<c:param name="itemTipo" value="${elementoDeposito.idTipoElemento}"/>
										</c:url>
										<a target="_self" class="tdlink12" href="javascript:window.document.location='<c:out value='${urlVerElemento}' escapeXml='false'/>'">
											<c:out value="${elementoDeposito.descripcion}"/>
										</a>
									</td>
									<td width="90"><c:out value="${elementoDeposito.numeroHuecos}"/></td>
									<td width="100"><c:out value="${elementoDeposito.huecosLibres}"/></td>
									<td width="120"><c:out value="${elementoDeposito.huecosInutilizados}"/></td>
									<td width="100"><c:out value="${elementoDeposito.porcentajeOcupacion}"/>&nbsp;<bean:message key="archigest.archivo.simbolo.porcentaje"/></td>
									<td width="90">
										<c:set var="metros" value="0"/>
										<c:choose>
											<c:when test="${elementoDeposito.longitud gt 0}">
												<c:set var="metros" value="${elementoDeposito.longitudEnMetros}"/>
											</c:when>
										</c:choose>
										<c:out value="${metros}" />&nbsp;<bean:message key="archigest.archivo.m" />
									</td></tr>
								</tbody>
							</table>
							</c:forEach>
						</div>
						</c:when>
					</c:choose>
					</TD>
				</TR>
			</c:forEach>
			</TABLE>
		</c:otherwise>
	</c:choose>

	<div class="separador8"></div>
	</tiles:put>
</tiles:insert>