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
<c:set var="motivo" value="${sessionScope[appConstants.solicitudes.MOTIVO_KEY]}"/>

<c:set var="bConsulta" value="${sessionScope[appConstants.consultas.CONSULTA_KEY]}"/>
<c:if test="${requestScope[appConstants.prestamos.PERMITIR_EDITAR_OBSERVACIONES]}">
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

	function cancelarModificarObservaciones(){
		var divLabelObservaciones="divEtiquetaObservaciones";
		var divCampoObservaciones="divCampoObservaciones";

		document.getElementById("observaciones").value = '<c:out value="${bConsulta.observaciones}"/>';
		document.getElementById(divCampoObservaciones).className="hidden";
		document.getElementById(divLabelObservaciones).className="visible";
	}

	function aceptarModificarObservaciones(){
		document.forms[0].method.value="actualizarObservaciones";
		document.forms[0].submit();

	}
	</script>
</c:if>


<c:set var="tipoSolicitante" value="${sessionScope[appConstants.consultas.CONSULTA_KEY].tipoentconsultora}"/>

		<TABLE class="formulario"> <%-- para aspecto de formulario con lineas bottom de celda --%>
			<TR>
				<TD class="tdTitulo" width="200px">
					<bean:message key="archigest.archivo.consultas.archivoConsulta"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${bConsulta.nombrearchivo}"/>
				</TD>
			</TR>
		</table>
		<TABLE class="formulario">
			<TR>
				<TD class="tdTitulo" width="200px">
					<bean:message key="archigest.archivo.solicitudes.datosSolicitante"/>:&nbsp;
				</TD>
			</TR>
		</TABLE>
		<table class="formulario">
			<TR>
				<td width="200px">
					<img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" />
				</td>
				<TD class="tdTitulo" width="150px">
						<bean:message key="archigest.archivo.prestamos.tipoSolicitante"/>:&nbsp;
				</TD>
				<td class="tdDatos" colspan="3">
					<c:if test="${tipoSolicitante == appConstants.consultas.TIPO_ENTIDAD_CONSULTORA_CIUDADANO_INT}">
						<bean:message key="archigest.archivo.consultas.ciudadano"/>
					</c:if>
					<c:if test="${tipoSolicitante == appConstants.consultas.TIPO_ENTIDAD_CONSULTORA_ORGANO_EXTERNO_INT}">
						<bean:message key="archigest.archivo.organo"/>
					</c:if>
					<c:if test="${tipoSolicitante == appConstants.consultas.TIPO_ENTIDAD_CONSULTORA_INVESTIGADOR_INT}">
						<bean:message key="archigest.archivo.consultas.investigador"/>
					</c:if>
				</td>
			</TR>
			<TR>
				<td width="200px"><img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" /></td>
				<TD class="tdTitulo" width="150px">
					<bean:message key="archigest.archivo.consultas.solicitante"/>:&nbsp;
				</TD>
				<TD class="tdDatos" colspan="3">
					<c:out value="${bConsulta.nusrconsultor}"/>
					<c:if test="${not empty bConsulta.norgconsultor}">
					 -
					 </c:if>
					<c:out value="${bConsulta.norgconsultor}"/>
				</TD>
			</TR>
			<c:if test="${not empty bConsulta.telefonosolicitante}">
			<TR>
				<td width="200px"><img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" /></td>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.prestamos.telefonoSolicitante"/>:&nbsp;
				</TD>
				<TD class="tdDatos" colspan="3">
					<c:out value="${bConsulta.telefonosolicitante}"/>
				</TD>
			</TR>
			</c:if>
			<c:if test="${not empty bConsulta.faxsolicitante}">
			<TR>
				<td width="200px"><img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" /></td>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.prestamos.faxSolicitante"/>:&nbsp;
				</TD>
				<TD class="tdDatos" colspan="3">
					<c:out value="${bConsulta.faxsolicitante}"/>
				</TD>
			</TR>
			</c:if>
			<c:if test="${not empty bConsulta.emailsolicitante}">
			<TR>
				<td width="200px"><img src="../images/pixel.gif" class="imgTextMiddle" width="20px" height="1" /></td>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.prestamos.emailSolicitante"/>:&nbsp;
				</TD>
				<TD class="tdDatos" colspan="3">
					<c:out value="${bConsulta.emailsolicitante}"/>
				</TD>
			</TR>
			</c:if>
		</table>

		<table class="formulario">
			<c:if test="${not empty sessionScope[appConstants.consultas.TEMA_KEY]}">
				<TR>
					<TD class="tdTitulo" width="200px">
						<bean:message key="archigest.archivo.consultas.tema"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${sessionScope[appConstants.consultas.TEMA_KEY]}"/>
					</TD>
				</TR>
			</c:if>
			<TR>
				<TD class="tdTitulo" width="200px">
					<bean:message key="archigest.archivo.consultas.motivo"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:if test="${not empty bConsulta.idMotivo && not empty motivo}">
						<bean:write name="motivo" property="motivo"/>
					</c:if>
				</TD>
			</TR>
			<c:if test="${not empty bConsulta.datosautorizado}">
			<TR>
				<TD class="tdTitulo" width="200px">
					<bean:message key="archigest.archivo.prestamos.autorizado"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${bConsulta.datosautorizado}"/>
				</TD>
			</TR>
			</c:if>
			<c:if test="${not empty bConsulta.tipoentrega}">
				<TR>
					<TD class="tdTitulo" width="200px">
						<bean:message key="archigest.archivo.prestamos.tipoEntrega"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<c:out value="${bConsulta.tipoentrega}"/>
					</TD>
				</TR>
			</c:if>
					<TR>
						<TD class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.solicitudes.observaciones"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:choose>
							<c:when test="${requestScope[appConstants.prestamos.PERMITIR_EDITAR_OBSERVACIONES]}">
								<div id="divEtiquetaObservaciones">
							<c:out value="${bConsulta.observaciones}"/>
							<a class="etiquetaAzul12Bold" href="javascript:modificarObservaciones()">
								<html:img page="/pages/images/editDoc.gif" altKey="archigest.archivo.modificar" titleKey="archigest.archivo.modificar" styleClass="imgTextMiddle" />
							</a>

							</div>
							<div id="divCampoObservaciones" class="hidden">
							 <input type="text" name="observaciones" id="observaciones" class="input95"  maxlength="254" value="<c:out value="${bConsulta.observaciones}"/>"/>
								<a class="etiquetaAzul12Bold" href="javascript:aceptarModificarObservaciones()">
									<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
								</a>
								<a class="etiquetaAzul12Bold" href="javascript:cancelarModificarObservaciones()">
									<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
								</a>
							</div>
							</c:when>
							<c:otherwise>
								<c:out value="${bConsulta.observaciones}"/>
							</c:otherwise>
							</c:choose>
						</TD>
				</TR>
			<c:if test="${requestScope[appConstants.consultas.VER_FECHA_ENTREGA]}">
				<TR>
					<TD class="tdTitulo" width="200px">
						<bean:message key="archigest.archivo.consultas.fentrega"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
						<fmt:formatDate value="${requestScope[appConstants.consultas.FECHA_ENTREGA_KEY]}" pattern="${appConstants.common.FORMATO_FECHA}"/>
					</TD>
				</TR>
			</c:if>

			<c:if test="${requestScope[appConstants.consultas.VER_BOTON_ENVIAR_DENEGAR_AUTORIZAR]}">
						<TR>
							<TD class="tdTitulo" width="200px">
								<bean:message key="archigest.archivo.consultas.fentrega"/>:&nbsp;
							</TD>
							<TD class="tdDatos">
										<input type="text" class="inputRO" name="fentrega" id="fentrega" size="12" maxlength="10" readonly/>
											&nbsp;<archigest:calendar
												image="../images/calendar.gif"
					                            formId="detalleConsultaForm"
					                            componentId="fentrega"
					                            format="dd/mm/yyyy"
					                            enablePast="false"
										/>
							</TD>
			</c:if>
			<c:if test="${requestScope[appConstants.consultas.VER_RESERVA]}">
				<TR>
					<TD id="reservas" class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.consultas.solicitadaReserva"/>&nbsp;
					</TD>
					<TD id="reservas2" class="tdDatos">
						<c:if test="${requestScope[appConstants.consultas.VER_FECHA_INICIO_RESERVA]}">
							<bean:message key="archigest.archivo.si"/>&nbsp;
						</c:if>

						<c:if test="${!requestScope[appConstants.consultas.VER_FECHA_INICIO_RESERVA]}">
							<bean:message key="archigest.archivo.no"/>&nbsp;
						</c:if>
					</TD>
				</TR>
				<c:if test="${requestScope[appConstants.consultas.VER_FECHA_INICIO_RESERVA]}">
				<TR>
					<TD width="200px">
					</TD>
					<TD>
					<TABLE cellpadding="0" cellspacing="0">
					<TR>
						<TD class="tdTitulo" width="200px">
							<bean:message key="archigest.archivo.consultas.finicialreserva"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<fmt:formatDate value="${bConsulta.finicialreserva}" pattern="${appConstants.common.FORMATO_FECHA}"/>
						</TD>
					</TR>
					</TABLE>
					</TD>
				</TR>
				</c:if>
			</c:if>
		</TABLE>

