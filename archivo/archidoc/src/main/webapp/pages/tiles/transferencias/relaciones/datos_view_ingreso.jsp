<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="FORMAT_ORDEN" value="${appConstants.transferencias.FORMAT_ORDEN}"/>
<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>
<c:if test="${empty vRelacion}">
	<c:set var="vRelacion" value="${sessionScope[appConstants.deposito.RELACION_KEY]}"/>
</c:if>

<c:set var="reservaEstadoSinReserva"><c:out value="${appConstants.transferencias.reservaPrevision.NO_RESERVADA.identificador}"/></c:set>
<c:set var="reservaEstadoPendiente"><c:out value="${appConstants.transferencias.reservaPrevision.PENDIENTE.identificador}"/></c:set>
<c:set var="reservaReservada"><c:out value="${appConstants.transferencias.reservaPrevision.RESERVADA.identificador}"/></c:set>
<c:set var="reservaNoSeHaPodido"><c:out value="${appConstants.transferencias.reservaPrevision.NO_SE_HA_PODIDO.identificador}"/></c:set>

	<TABLE class="formulario"> <%-- para aspecto de formulario con lineas bottom de celda --%>
		<TR>
			<TD class="tdTitulo" width="250px">
				<bean:message key="archigest.archivo.transferencias.orgRem"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<c:out value="${vRelacion.organoRemitente.nombreLargo}"/>
			</TD>
		</TR>

		<TR>
			<TD class="tdTitulo">
				<bean:message key="archigest.archivo.archivoCustodia"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<c:out value="${vRelacion.nombreArchivoReceptor}"/>
			</TD>
		</TR>
        

		<c:set var="gestorEnArchivo" value="${vRelacion.gestorEnArchivo}" />
		<c:if  test="${!empty gestorEnArchivo}">
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.gestor"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${gestorEnArchivo.nombreCompleto}"/>
				</TD>
			</TR>
		</c:if>
		<TR>
			<TD class="tdTitulo">
				<bean:message key="archigest.archivo.transferencias.fondo"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<c:out value="${vRelacion.fondo.codReferencia}"/>
				<c:out value="${vRelacion.fondo.titulo}"/>
			</TD>
		</TR>
		<TR>
			<TD class="tdTitulo">
				<bean:message key="archigest.archivo.transferencias.funcion"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<c:out value="${vRelacion.funcion.codReferencia}"/>
				<c:out value="${vRelacion.funcion.titulo}"/>
			</TD>
		</TR>
		
		<TR>
			<TD class="tdTitulo">
				<bean:message key="archigest.archivo.cf.serieDestino"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<c:out value="${vRelacion.serie.codigo}"/>
				<c:out value="${vRelacion.serie.titulo}"/>
			</TD>
		</TR>
		<TR>
			<TD class="tdTitulo">
	 			<bean:message key="archigest.archivo.transferencias.soporteDocumental"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<c:out value="${vRelacion.tipoDocumental}"/>
			</TD>
		</TR>
		<TR>
			<TD class="tdTitulo">
	 			<bean:message key="archigest.archivo.transferencias.formato"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<c:out value="${vRelacion.formato.nombre}"/>
			</TD>
		</TR>
		<c:if test="${vRelacion.masDeUnNivelUDoc}">
			<TR>
				<TD class="tdTitulo">
		 			<bean:message key="archigest.archivo.transferencias.nivelDocumental"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${vRelacion.nivelDocumental.nombre}"/>
				</TD>
			</TR>
		</c:if>		
		<c:set var="deposito" value="${vRelacion.deposito}" />
		<c:if test="${(!empty deposito)&&(vRelacion.reservadeposito != reservaReservada)}">
			<TR>
				<TD class="tdTitulo" >
					<bean:message key="archigest.archivo.transferencias.reservaDeposito"/>&nbsp;
				</TD>
				<td class="tdDatos">
				<table cellpadding="0" cellspacing="0"><tr>
					<TD class="td2Datos" width="150px">
					<c:if test="${vRelacion.reservadeposito == reservaEstadoSinReserva}">
						<bean:message key="archigest.archivo.transferencias.estadoReservaSinReserva"/>
					</c:if>
					<c:if test="${vRelacion.reservadeposito == reservaEstadoPendiente}">
						<bean:message key="archigest.archivo.transferencias.estadoReservaPendiente"/>
					</c:if>
					<c:if test="${vRelacion.reservadeposito == reservaReservada}">
						<bean:message key="archigest.archivo.transferencias.estadoReservaReservada"/>
					</c:if>
					<c:if test="${vRelacion.reservadeposito == reservaNoSeHaPodido}">
						<bean:message key="archigest.archivo.transferencias.estadoReservaNoSeHaPodido"/>
					</c:if>
					</TD>
					<TD width="20px">
					</TD>
					<TD class="td2Titulo" width="230px">
						<bean:message key="archigest.archivo.transferencias.deposito"/>:&nbsp;
					</TD>
					<TD class="td2Datos">
						<c:choose>
						<c:when test="${(vRelacion.reservadeposito == reservaReservada)}">
							<c:url var="urlVerReservas" value="/action/gestionRelaciones">
								<c:param name="method" value="verInformeReservas"/>
								<c:param name="idRelacion" value="${vRelacion.id}"/>
							</c:url>
							<a class="tdlink" href='<c:out value="${urlVerReservas}" escapeXml="false"/>' >  
								<c:out value="${deposito.nombre}"/>
							</a>
						</c:when>
						<c:otherwise>
							<c:out value="${deposito.nombre}"/>
						</c:otherwise>
						</c:choose>
					</TD>
				</tr></table>
				</td>
			</TR>
		</c:if>
		<c:set var="ubicacion" value="${vRelacion.ubicacion}" />
		<c:if test="${!empty ubicacion}">
			<TR>
				<TD class="tdTitulo" >
					<bean:message key="archigest.archivo.transferencias.ubicDeposito"/>:&nbsp;
				</TD>
				<td class="tdDatos">
				<table cellpadding="0" cellspacing="0"><tr>
				<TD class="td2Datos" width="150px">
					<c:if test="${vRelacion.uiendeposito}">
						<bean:message key="archigest.archivo.si" />
					</c:if>
					<c:if test="${!vRelacion.uiendeposito}">
						<bean:message key="archigest.archivo.no" />
					</c:if>
				</TD>
				<TD width="20px">
				</TD>
				<TD class="td2Titulo" width="230px">
					<bean:message key="archigest.archivo.deposito.ubicacion"/>:&nbsp;
				</TD>
				<TD class="td2Datos">
					<c:choose>
					<c:when test="${vRelacion.uiendeposito}">
						<c:url var="urlVerUbicacion" value="/action/gestionRelaciones">
							<c:param name="method" value="verInformeUbicacion"/>
							<c:param name="idRelacion" value="${vRelacion.id}"/>
						</c:url>
						<a class="tdlink" href='<c:out value="${urlVerUbicacion}" escapeXml="false"/>' >  
							<c:out value="${ubicacion.nombre}"/>
						</a>
					</c:when>
					<c:otherwise>
						<c:out value="${ubicacion.nombre}"/>
					</c:otherwise>
					</c:choose>
				</TD>
				</tr></table>
				</td>
			</TR>
		</c:if>
		<c:if test="${!empty vRelacion.idFicha}">
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.ficha"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${vRelacion.nombreFicha}"/>
				</TD>
			</TR>
		</c:if>	
		<TR>
			<TD class="tdTitulo" style="vertical-align:top">
				<bean:message key="archigest.archivo.transferencias.observaciones"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<c:out value="${vRelacion.observaciones}"> -- </c:out>
			</TD>
		</TR>
	</TABLE>	