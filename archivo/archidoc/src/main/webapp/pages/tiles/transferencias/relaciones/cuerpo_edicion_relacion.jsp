<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>

<c:set var="reservaEstadoSinReserva"><c:out value="${appConstants.transferencias.reservaPrevision.NO_RESERVADA.identificador}"/></c:set>
<c:set var="reservaEstadoPendiente"><c:out value="${appConstants.transferencias.reservaPrevision.PENDIENTE.identificador}"/></c:set>
<c:set var="reservaReservada"><c:out value="${appConstants.transferencias.reservaPrevision.RESERVADA.identificador}"/></c:set>
<c:set var="reservaNoSeHaPodido"><c:out value="${appConstants.transferencias.reservaPrevision.NO_SE_HA_PODIDO.identificador}"/></c:set>

<div id="contenedor_ficha">
<div class="ficha">
	<h1><span>
	<div class="w100">
	<TABLE class="w98" cellpadding=0 cellspacing=0>
	  <TR>
	    <TD class="etiquetaAzul12Bold" height="25px">
			<bean:message key="archigest.archivo.transferencias.datosRelacion"/>
		</TD>
	    <TD align="right">
			<TABLE cellpadding=0 cellspacing=0>
			 <TR>
			 	<TD>
				<TABLE cellpadding=0 cellspacing=0>
				 <TR>
				    <TD>
						<a class="etiquetaAzul12Bold" href="javascript:document.forms[0].submit()" >
							<html:img page="/pages/images/Ok_Si.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
							&nbsp;<bean:message key="archigest.archivo.aceptar"/>
						</a>
					</TD>
				   <TD width="10">&nbsp;</TD>
				   <TD>
						<c:url var="cancelURL" value="/action/gestionRelaciones">
							<c:param name="method" value="goBack" />
						</c:url>
				   		<a class=etiquetaAzul12Bold href="<c:out value="${cancelURL}" escapeXml="false"/>">
							<html:img page="/pages/images/Ok_No.gif" border="0" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
				   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
				   		</a>
				   </TD>
				 </TR>
				</TABLE>
			 </TR>
			</TABLE>
		</TD>
	  </TR>
	</TABLE>
	</div>
	</span></h1>

	<div class="cuerpo_drcha">
		<div class="cuerpo_izda">
			<DIV id="barra_errores">
				<archivo:errors />
			</DIV>
			<div class="separador1">&nbsp;</div>
			<DIV class="cabecero_bloque_sin_height"> <%--cabecero primer bloque de datos --%>
				<jsp:include page="cabeceracte_relacion.jsp" flush="true" />
			</DIV> <%--primer bloque de datos (resumen siempre visible) --%>
			<div class="bloque"> <%--primer bloque de datos --%>
			<html:form action= "/gestionRelaciones">
			<input type="hidden" name="method" value="guardaredicion">
				<c:set var="prevision" value="${vRelacion.prevision}" />
				<TABLE class="formulario"> <%--para aspecto de formulario con lineas bottom de celda --%>
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
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.tipoTransf"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:set var="keyTitulo">
								archigest.archivo.transferencias.tipooperacion<c:out value="${prevision.tipooperacion}"/>
							</c:set>
							<fmt:message key="${keyTitulo}" />
						</TD>
					</TR>
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.fIniTransf"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<TABLE cellpadding="0" cellspacing="0">
								<TR>
									<TD width="100px" class="td2Datos">
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
									<c:url var="verPrevisionURL" value="/action/gestionPrevisiones">
										<c:param name="method" value="verprevision" />
										<c:param name="idprevision" value="${vRelacion.idprevision}" />
									</c:url>
									<c:out value="${prevision.codigoTransferencia}"/>
									</TD>
									<c:set var="detallePrevision" value="${vRelacion.detallePrevision}" />
									<c:if test="${detallePrevision.numeroOrden > 0}">
									<TD width="20px">
									</TD>
									<TD width="150px" class="td2Titulo">
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
							<c:out value="${vRelacion.idprocedimiento}"/>
							<c:out value="${vRelacion.procedimiento.nombre}"/>
						</TD>
					</TR>
					<c:if test="${!empty vRelacion.iddetprevision}">
						<c:set var="detallePrevision" value="${vRelacion.detallePrevision}" />
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

					<c:set var="gestor" value="${vRelacion.gestorEnArchivo}" />
					<c:if test="${!empty gestor}">
						<TR>
							<TD class="tdTitulo">
								<bean:message key="archigest.archivo.transferencias.gestorArchivo"/>:&nbsp;
							</TD>
							<TD class="tdDatos">
								<c:out value="${gestor.nombre}"/> <c:out value="${gestor.apellidos}"/>
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
					<%--Si hay más de un nivel documental y la relación NO ES ORDINARIA, mostramos la información de niveles documentales --%>
					<c:if test="${vRelacion.masDeUnNivelUDoc && !vRelacion.ordinaria}">
						<TR>
							<TD class="tdTitulo">
					 			<bean:message key="archigest.archivo.transferencias.nivelDocumental"/>:&nbsp;
							</TD>
							<TD class="tdDatos">
								<c:out value="${vRelacion.nivelDocumental.nombre}"/>
							</TD>
						</TR>
					</c:if>
					<c:if test="${!empty vRelacion.fecharecepcion}">
						<TR>
							<TD class="tdTitulo" >
								<bean:message key="archigest.archivo.transferencias.fechaRecepcion"/>:&nbsp;
							</TD>
							<TD class="tdDatos">
								<TABLE cellpadding="0" cellspacing="0">
									<TR>
										<TD width="100px" class="td2Datos">
											<fmt:formatDate value="${vRelacion.fecharecepcion}" pattern="${FORMATO_FECHA}" />
										</TD>
										<TD width="20px">
										</TD>
										<TD width="150px" class="td2Titulo">
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
					<c:set var="deposito" value="${vRelacion.deposito}" />
					<c:if test="${!empty deposito}">
						<TR>
							<TD class="tdTitulo" >
								<bean:message key="archigest.archivo.transferencias.reservaDeposito"/>:&nbsp;
							</TD>
							<TD class="tdDatos">
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
									<c:out value="${deposito.nombre}"/>
								</TD>
							</tr></table>
							</TD>
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
								<c:out value="${ubicacion.nombre}"/>
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
							<html:textarea property="observaciones" rows="3" onchange="maxlength(this,254,true)" onkeypress="maxlength(this,254,false)"/>
						</TD>
					</TR>
				</TABLE>
			</html:form>
			</div> <%--primer bloque de datos --%>
		</div> <%--cuerpo_drcha --%>
	</div> <%--cuerpo_izda --%>
	<h2><span>&nbsp;</span></h2>
</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>
