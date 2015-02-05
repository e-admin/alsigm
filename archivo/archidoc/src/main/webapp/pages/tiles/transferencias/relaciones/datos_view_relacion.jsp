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
		<c:if test="${!vRelacion.entreArchivos}">
			<TR>
				<TD class="tdTitulo" width="250px">
					<bean:message key="archigest.archivo.transferencias.orgRem"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${vRelacion.organoRemitente.nombreLargo}"/>
				</TD>
			</TR>
		</c:if>
		<TR>
			<TD class="tdTitulo" width="250px">
				<bean:message key="archigest.archivo.transferencias.tipoTransf"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<c:set var="keyTitulo">
					archigest.archivo.transferencias.tipooperacion<c:out value="${vRelacion.tipooperacion}"/>
				</c:set>
				<fmt:message key="${keyTitulo}" />
			</TD>
		</TR>
		<c:set var="prevision" value="${vRelacion.prevision}" />
		<TR>
			<TD class="tdTitulo">
				<bean:message key="archigest.archivo.transferencias.fIniTransf"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<TABLE cellpadding="0" cellspacing="0">
					<TR>
						<TD width="150px" class="td2Datos">
							<fmt:formatDate value="${prevision.fechainitrans}" pattern="${FORMATO_FECHA}"/>
						</TD>
						<TD width="20px">
						</TD>
						<TD width="230px" class="td2Titulo">
							<bean:message key="archigest.archivo.transferencias.fFinTransf"/>:&nbsp;
						</TD>
						<TD width="80px" class="td2Datos">
							<fmt:formatDate value="${prevision.fechafintrans}" pattern="${FORMATO_FECHA}" />
						</TD>
					</TR>
				</TABLE>
			</TD>
		</TR>
		<TR>
			<TD class="tdTitulo" >
			<bean:message key="archigest.archivo.transferencias.prevision"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<TABLE cellpadding="0" cellspacing="0">
					<TR>
						<TD width="150px" class="td2Datos">
						<c:url var="urlVerPrevision" value="/action/gestionPrevisiones">
							<c:param name="method" value="verprevision"/>
							<c:param name="idprevision" value="${prevision.id}"/>
							<c:param name="${appConstants.transferencias.SOLO_VISTA_PARAM_NAME}" value="true"/>
						</c:url>
						<a class="tdlink" href='<c:out value="${urlVerPrevision}" escapeXml="false"/>' >
							<c:out value="${vRelacion.codigoPrevision}"/>
						</a>
						</TD>
						<TD width="20px">
						</TD>
						<c:set var="detallePrevision" value="${vRelacion.detallePrevision}" />
						<c:if test="${detallePrevision.numeroOrden > 0}">
						<TD width="230px" class="td2Titulo">
							<bean:message key="archigest.archivo.transferencias.lineaDet"/>:&nbsp;
						</TD>
						<TD width="80px" class="td2Datos">
							<fmt:formatNumber value="${detallePrevision.numeroOrden}" pattern="000"/>
						</TD>
						</c:if>
					</TR>
				</TABLE>
			</TD>
		</TR>
		<c:if test="${!empty vRelacion.nombresistproductor}">
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.sistProd"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${vRelacion.nombresistproductor}"/>
				</TD>
			</TR>
		</c:if>
		<TR>
			<TD class="tdTitulo">
				<bean:message key="archigest.archivo.transferencias.procedimiento"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<c:out value="${vRelacion.idprocedimiento}"> -- </c:out> <c:out value="${vRelacion.procedimiento.nombre}"/>
			</TD>
		</TR>

		<c:if test="${!empty vRelacion.iddetprevision}">
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
		</c:if>
		<c:if test="${vRelacion.entreArchivos}">
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.archivoRemitente"/>:&nbsp;
				</TD>
				<TD class="tdDatos" id="nombreArchivoRemitente">
					<c:out value="${vRelacion.nombreArchivoRemitente}"/>
				</TD>
			</TR>
		</c:if>
		<TR>
			<TD class="tdTitulo">
				<bean:message key="archigest.archivo.transferencias.archRecep"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<c:out value="${vRelacion.nombreArchivoReceptor}"/>
			</TD>
		</TR>
        <security:permissions action="${appConstants.transferenciaActions.GESTION_RELACION_EN_ARCHIVO_RECEPTOR}">

		<c:set var="gestorEnArchivo" value="${vRelacion.gestorEnArchivo}" />
		<c:if  test="${!empty gestorEnArchivo}">
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.gestorArchivo"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${gestorEnArchivo.nombreCompleto}"/>
				</TD>
			</TR>
		</c:if>

		</security:permissions>

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
		<c:if test="${vRelacion.entreArchivos}">
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.cf.serieOrigen"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${vRelacion.serieOrigen.codigo}"/>
					<c:out value="${vRelacion.serieOrigen.titulo}"/>
				</TD>
			</TR>
		</c:if>
		<TR>
			<TD class="tdTitulo">
				<c:choose>
					<c:when test="${!vRelacion.entreArchivos}">
						<bean:message key="archigest.archivo.transferencias.serie"/>:&nbsp;
					</c:when>
					<c:otherwise>
						<bean:message key="archigest.archivo.cf.serieDestino"/>:&nbsp;
					</c:otherwise>
				</c:choose>
			</TD>
			<TD class="tdDatos">
				<c:out value="${vRelacion.serie.codigo}"/>
				<c:out value="${vRelacion.serie.titulo}"/>
			</TD>
		</TR>
        <security:permissions action="${appConstants.transferenciaActions.GESTION_RELACION_EN_ARCHIVO_RECEPTOR}">
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
					<c:out value="${vRelacion.nombreFormato}"/>
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
		<c:if test="${!empty vRelacion.regentrada}">
			<TR>
				<TD class="tdTitulo" >
					<bean:message key="archigest.archivo.transferencias.fechaRecepcion"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<TABLE cellpadding="0" cellspacing="0">
						<TR>
							<TD width="150px" class="td2Datos">
								<fmt:formatDate value="${vRelacion.fecharecepcion}" pattern="${FORMATO_FECHA}" />
							</TD>
							<TD width="20px">
							</TD>
							<TD width="230px" class="td2Titulo">
								<bean:message key="archigest.archivo.transferencias.regEntrada"/>:&nbsp;
							</TD>
							<TD width="100px" class="td2Datos">
								<c:out value="${vRelacion.regentrada}"/>
							</TD>
						</TR>
					</TABLE>
				</TD>
			</TR>
		</c:if>
		<c:if test="${vRelacion.conErroresCotejo || vRelacion.corregidaErrores}">
			<TR>
				<TD class="tdTitulo" width="300px">
					<bean:message key="archigest.archivo.transferencias.devFisicaErrores"/>&nbsp;
				</TD>
				<TD class="tdDatos">
				<c:if test="${vRelacion.devolucionui}">
					<bean:message key="archigest.archivo.si"/>
				</c:if>
				<c:if test="${!vRelacion.devolucionui}">
					<bean:message key="archigest.archivo.no"/>
				</c:if>
				</TD>
			</TR>
		</c:if>
		<c:set var="deposito" value="${vRelacion.deposito}" />
		<c:if test="${(!empty deposito) && (!vRelacion.uiendeposito)}">
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
		</security:permissions>
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