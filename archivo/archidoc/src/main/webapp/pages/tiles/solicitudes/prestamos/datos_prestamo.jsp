<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>

<c:url var="calendarImgDir" value="/pages/images/calendario/" />
<jsp:useBean id="calendarImgDir" type="java.lang.String" />
<archigest:calendar-config imgDir="<%=calendarImgDir%>" scriptFile="../scripts/calendar.js"/>
<c:set var="bPrestamo" value="${sessionScope[appConstants.prestamos.PRESTAMO_KEY]}"/>
<c:set var="motivo" value="${sessionScope[appConstants.solicitudes.MOTIVO_KEY]}"/>
<script language="javascript">
function modificarObservaciones(){

	var divLabelObservaciones="divEtiquetaObservaciones";
	var divCampoObservaciones="divCampoObservaciones";

	document.getElementById(divCampoObservaciones).className="visible";
	document.getElementById(divLabelObservaciones).className="hidden";


	/*var divAceptarCancelarCampo="divAceptarCancelarObservaciones";*/

	//al pulsar el boton de modificar, hay que salir del modo edicion en el resto de
	//campos en esa columna.
	for(i=1;;i++){
		divAceptarCancelarCampo="divAceptarCancelarCampoObservaciones"+i;
		divLabelCampo="divLabelCampoObservaciones"+i;
		elemento=document.getElementById(divAceptarCancelarCampo);

		if(elemento!=null){
			elemento.className="hidden";
			document.getElementById(divLabelCampo).className="visible";
		}
		else{
			break;
		}
	}

}


<c:if test="${requestScope[appConstants.prestamos.PERMITIR_EDITAR_OBSERVACIONES]}">
function cancelarModificarObservaciones(){
		var divLabelObservaciones="divEtiquetaObservaciones";
		var divCampoObservaciones="divCampoObservaciones";
		document.getElementById("observaciones").value = '<c:out value="${bPrestamo.observaciones}"/>';
		document.getElementById(divCampoObservaciones).className="hidden";
		document.getElementById(divLabelObservaciones).className="visible";
}

function aceptarModificarObservaciones(){
	document.forms[0].method.value="actualizarObservaciones";
	document.forms[0].submit();

}
</c:if>

</script>
		<TABLE class="formulario"> <%-- para aspecto de formulario con lineas bottom de celda --%>
				<TR>
					<TD class="tdTitulo" width="230px">
						<bean:message key="archigest.archivo.prestamos.archivoPrestamo"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${bPrestamo.archivo.nombre}"/>
					</TD>
				</TR>
		</TABLE>
		<TABLE class="formulario">
				<TR>
					<TD class="tdTitulo">
						<bean:message key="archigest.archivo.solicitudes.datosSolicitante"/>:&nbsp;
					</TD>
				</TR>
		</TABLE>

		<TABLE class="formulario">
				<TR>
					<td width="230px"><img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" /></td>
					<TD class="tdTitulo" width="200px">
						<bean:message key="archigest.archivo.prestamos.tipoSolicitante"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:choose>
							<c:when test="${empty bPrestamo.idorgsolicitante}">
								<bean:message key="archigest.archivo.prestamos.externo"/>
							</c:when>
							<c:otherwise>
								<bean:message key="archigest.archivo.prestamos.interno"/>
							</c:otherwise>
						</c:choose>
					</TD>
				</TR>

				<TR>
					<td width="230px"><img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" /></td>
					<TD class="tdTitulo" width="200px">
						<bean:message key="archigest.archivo.prestamos.solicitante"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${bPrestamo.nusrsolicitante}"/>
						<c:if test="${not empty bPrestamo.norgsolicitante}">
						 -
						</c:if>
						<c:out value="${bPrestamo.norgsolicitante}"/>
					</TD>
				</TR>
				<c:if test="${not empty bPrestamo.telefonosolicitante}">
				<TR>
					<td width="230px"><img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" /></td>
					<TD class="tdTitulo">
						<bean:message key="archigest.archivo.prestamos.telefonoSolicitante"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${bPrestamo.telefonosolicitante}"/>
					</TD>
				</TR>
				</c:if>
				<c:if test="${not empty bPrestamo.faxsolicitante}">
				<TR>
					<td width="230px"><img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" /></td>
					<TD class="tdTitulo">
						<bean:message key="archigest.archivo.prestamos.faxSolicitante"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${bPrestamo.faxsolicitante}"/>
					</TD>
				</TR>
				</c:if>
				<c:if test="${not empty bPrestamo.emailsolicitante}">
				<TR>
					<td width="230px"><img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" /></td>
					<TD class="tdTitulo">
						<bean:message key="archigest.archivo.prestamos.emailSolicitante"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${bPrestamo.emailsolicitante}"/>
					</TD>
				</TR>
				</c:if>
		</TABLE>

		<table class="formulario">
				<tr>
					<td class="tdTitulo" width="200px">
						<bean:message key="archigest.archivo.consultas.motivo"/>:&nbsp;
					</td>
					<td class="tdDatos">
						<c:if test="${not empty bPrestamo.idMotivo && not empty motivo}">
							<c:out value="${motivo.motivo}"/>
						</c:if>
					</td>
				</tr>
				<c:if test="${not empty bPrestamo.datosautorizado}">
					<TR>
						<TD class="tdTitulo" width="230px">
							<bean:message key="archigest.archivo.prestamos.autorizado"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${bPrestamo.datosautorizado}"/>
						</TD>
					</TR>
				</c:if>
				<c:if test="${not empty bPrestamo.tipoentrega}">
					<TR>
						<TD class="tdTitulo" width="230px">
							<bean:message key="archigest.archivo.prestamos.tipoEntrega"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${bPrestamo.tipoentrega}"/>
						</TD>
					</TR>
				</c:if>
					<c:if test="${requestScope[appConstants.prestamos.VER_FECHA_ENTREGA]}">
						<TR>
							<TD class="tdTitulo" width="230px">
								<bean:message key="archigest.archivo.prestamos.fentrega"/>:&nbsp;
							</TD>
							<TD class="tdDatos">
								<fmt:formatDate value="${requestScope[appConstants.prestamos.FECHA_ENTREGA_KEY]}" pattern="${appConstants.common.FORMATO_FECHA}"/>
							</TD>
						</TR>
					</c:if>
					<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_ENVIAR_DENEGAR_AUTORIZAR]}">
						<TR>
							<TD class="tdTitulo" width="230px">
								<bean:message key="archigest.archivo.prestamos.fentrega"/>:&nbsp;
							</TD>
							<TD class="tdDatos">
										<input type="text" class="inputRO" name="fentrega" id="fentrega" size="12" maxlength="10" readonly/>
											&nbsp;<archigest:calendar
												image="../images/calendar.gif"
					                            formId="detallePrestamoForm"
					                            componentId="fentrega"
					                            format="dd/mm/yyyy"
					                            enablePast="false"
										/>
							</TD>
					</c:if>
					<c:if test="${requestScope[appConstants.prestamos.VER_FECHA_MAX_FIN_PRESTAMOS]}">
						<TR>
							<TD class="tdTitulo" width="230px">
								<bean:message key="archigest.archivo.prestamos.fmaxfinprestamo"/>:&nbsp;
							</TD>
							<TD class="tdDatos">
								<fmt:formatDate value="${requestScope[appConstants.prestamos.FECHA_MAX_FIN_PRESTAMO_KEY]}" pattern="${appConstants.common.FORMATO_FECHA}"/>
							</TD>
						</TR>
					</c:if>
					<c:if test="${requestScope[appConstants.prestamos.VER_FECHA_DEVOLUCION]}">
						<TR>
							<TD class="tdTitulo" width="230px">
								<bean:message key="archigest.archivo.prestamos.fdevolucion"/>:&nbsp;
							</TD>
							<TD class="tdDatos">
								<fmt:formatDate value="${requestScope[appConstants.prestamos.FECHA_DEVOLUCION_KEY]}" pattern="${appConstants.common.FORMATO_FECHA}"/>
							</TD>
						</TR>
					</c:if>


					<TR>
						<TD class="tdTitulo" width="230px">
							<bean:message key="archigest.archivo.solicitudes.observaciones"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:choose>
							<c:when test="${requestScope[appConstants.prestamos.PERMITIR_EDITAR_OBSERVACIONES]}">
								<div id="divEtiquetaObservaciones">
							<c:out value="${bPrestamo.observaciones}"/>
							<a class="etiquetaAzul12Bold" href="javascript:modificarObservaciones()">
								<html:img page="/pages/images/editDoc.gif" altKey="archigest.archivo.modificar" titleKey="archigest.archivo.modificar" styleClass="imgTextMiddle" />
							</a>

							</div>
							<div id="divCampoObservaciones" class="hidden">
							 <input type="text" name="observaciones" id="observaciones" class="input95"  maxlength="254" value="<c:out value="${bPrestamo.observaciones}"/>"/>
								<a class="etiquetaAzul12Bold" href="javascript:aceptarModificarObservaciones()">
									<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
								</a>
								<a class="etiquetaAzul12Bold" href="javascript:cancelarModificarObservaciones()">
									<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
								</a>
							</div>
							</c:when>
							<c:otherwise>
								<c:out value="${bPrestamo.observaciones}"/>
							</c:otherwise>
							</c:choose>
						</TD>
					</TR>
	</table>



		<c:if test="${requestScope[appConstants.prestamos.VER_RESERVA]}">
			<table class="formulario">
				<TR>
					<TD id="reservas" class="tdTitulo" width="230px">
						<bean:message key="archigest.archivo.prestamos.solicitadaReserva"/>&nbsp;
					</TD>
					<TD id="reservas2" class="tdDatos">
						<c:if test="${requestScope[appConstants.prestamos.VER_FECHA_INICIO_RESERVA]}">
							<bean:message key="archigest.archivo.si"/>&nbsp;
						</c:if>
						<c:if test="${!requestScope[appConstants.prestamos.VER_FECHA_INICIO_RESERVA]}">
							<bean:message key="archigest.archivo.no"/>&nbsp;
						</c:if>
					</TD>
				</TR>
			</table>
			<c:if test="${requestScope[appConstants.prestamos.VER_FECHA_INICIO_RESERVA]}">
				<table class="formulario">
					<TR>
						<TD width="230px"><img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1"/></TD>
						<TD width="100px" class="tdTitulo">
							<bean:message key="archigest.archivo.prestamos.desde"/>:&nbsp;
						</TD>
						<TD width="150px" class="tdDatos">
							<fmt:formatDate  value="${bPrestamo.finicialreserva}" pattern="${appConstants.common.FORMATO_FECHA}"/>
						</TD>
						<TD width="100px" class="tdTitulo">
							<bean:message key="archigest.archivo.prestamos.hasta"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<fmt:formatDate value="${bPrestamo.ffinalreserva}" pattern="${appConstants.common.FORMATO_FECHA}" />
						</TD>
					</TR>
				</table>
			</c:if>
		</c:if>

		<c:if test="${requestScope[appConstants.prestamos.VER_PRORROGAS_SOLICITADAS]}">
				<TABLE class="formulario">
					<TR>
						<TD class="tdTitulo" width="150px">
							<bean:message key="archigest.archivo.prestamos.solicitadaProrroga"/>:&nbsp;
						</TD>
						<td class="tdDatos">
								<c:if test="${not empty bPrestamo.prorrogas}">
									<c:set var="prorrogas" value="${bPrestamo.prorrogas}"/>

									<display:table name="pageScope.prorrogas"
										id="prorroga"
										style="width:98%;margin-left:auto;margin-right:auto"
										sort="list"
										>
										<display:column style="width:30px">
												<c:choose>
												<c:when test="${prorroga.solicitada}">
													<html:img page="/pages/images/pro_sol.gif"
														altKey="archigest.archivo.prestamo.estadoSolictudProrroga.1"
														titleKey="archigest.archivo.prestamo.estadoSolictudProrroga.1"
														styleClass="imgTextMiddle" />
												</c:when>
												<c:when test="${prorroga.autorizada}">
													<html:img page="/pages/images/pro_acep.gif"
														altKey="archigest.archivo.prestamo.estadoSolictudProrroga.2"
														titleKey="archigest.archivo.prestamo.estadoSolictudProrroga.2"
														styleClass="imgTextMiddle" />
												</c:when>

												<c:when test="${prorroga.denegada}">
													<html:img page="/pages/images/pro_den.gif"
														altKey="archigest.archivo.prestamo.estadoSolictudProrroga.3"
														titleKey="archigest.archivo.prestamo.estadoSolictudProrroga.3"
														styleClass="imgTextMiddle" />
												</c:when>
												</c:choose>
										</display:column>
										<display:column titleKey="archigest.archivo.estado" style="width:100px">
												<fmt:message key="archigest.archivo.prestamo.estadoSolictudProrroga.${prorroga.estado}"></fmt:message>
										</display:column>
										<display:column titleKey="archigest.archivo.fEstado" property="fechaEstadoAsString" style="width:50px"/>
										<display:column titleKey="archigest.archivo.prestamo.fecha.fin.prorroga" property="fechaFinProrrogaAsString" style="width:50px;text-align:center"/>
										<display:column titleKey="archigest.archivo.prestamo.prorroga.motivo" property="motivoProrroga"/>
										<c:if test="${prorroga.denegada}">
											<display:column titleKey="archigest.archivo.prestamos.motivoDenegacion" property="motivoRechazo"/>
										</c:if>
									</display:table>
								</c:if>
								<span class="separador5">
								</span>
						</td>
					</TR>
				</TABLE>
			</c:if>
			<c:if test="${bPrestamo.numreclamaciones > 0}">
				<TABLE class="formulario">
					<TR>
						<TD class="tdTitulo" width="230px">
							<bean:message key="archigest.archivo.prestamos.reclamaciones"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${bPrestamo.numreclamaciones}"/>
						</TD>
					</TR>
					<TR>
						<td width="230px"><img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" /></td>
						<TD>
							<TABLE class="formulario" cellpadding="0" cellspacing="0">
								<TR>
									<TD class="tdTitulo" width="300px">
										<bean:message key="archigest.archivo.prestamos.fechaprimerareclamacion"/>:&nbsp;
									</TD>
									<TD class="tdDatos">
										<fmt:formatDate value="${bPrestamo.freclamacion1}" pattern="${appConstants.common.FORMATO_FECHA}"/>
									</TD>
								</TR>
								<c:if test="${bPrestamo.numreclamaciones == 2}">
									<TR>
										<TD class="tdTitulo" width="300px">
											<bean:message key="archigest.archivo.prestamos.fechasegundareclamacion"/>:&nbsp;
										</TD>
										<TD class="tdDatos">
											<fmt:formatDate value="${bPrestamo.freclamacion2}" pattern="${appConstants.common.FORMATO_FECHA}"/>
										</TD>
									</TR>
								</c:if>
							</TABLE>
						</TD>
					</TR>
				</TABLE>
			</c:if>
