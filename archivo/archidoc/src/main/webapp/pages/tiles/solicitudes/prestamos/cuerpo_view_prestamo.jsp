<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-logic-el.tld" prefix="logic-el"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:url var="calendarImgDir" value="/pages/images/calendario/" />
<jsp:useBean id="calendarImgDir" type="java.lang.String" />
<archigest:calendar-config imgDir="<%=calendarImgDir%>" scriptFile="../scripts/calendar.js"/>
<bean:struts id="mappingGestionUdocs" mapping="/gestionDetallesPrestamos" />
<bean:struts id="mappingGestionPrestamos" mapping="/gestionPrestamos" />

<script>

	function conmutarEstadoDocRev(rowNum, check) {
		//var checkArray = document.forms[0].udocsarevisardoc;
		setVisibilityDocRev(rowNum, check.checked);
	}

	function setVisibilityDocRev(rowNum, mostrar)
	{
		var divDocRev = document.getElementById("divDocRev"+rowNum);
		if(mostrar){
			divDocRev.style.display='block';
      		var status = document.getElementById('divDocRevStatus'+rowNum);
      		if (status){
        		status.value="1";
      		}
		}
		else {
			divDocRev.style.display='none';
	      	var status = document.getElementById('divDocRevStatus'+rowNum);
			if (status) {
				status.value="0";
			}
		}
	}

	function popupUDocsRel(idudoc) {
		popup('<c:url value="/action/gestionDetallesPrestamos"/>?method=verudocsRelacionadas&idudoc=' + idudoc, "_blank");
	}

	function refrescar(idprestamo) {
		<c:url var="refrescarURL" value="/action/gestionPrestamos">
			<c:param name="method" value="verprestamo" />
			<c:param name="idprestamo" value=""/>
		</c:url>
		var direccion = '<c:out value="${refrescarURL}" escapeXml="false"/>'+idprestamo;
		window.location = direccion;
	}

	function comprobarDisponibilidadEntrega(idprestamo) {
			<c:url var="envioURL" value="/action/gestionPrestamos">
				<c:param name="method" value="comprobardisponibilidadentrega" />
				<c:param name="idprestamo" value=""/>
			</c:url>
			window.location = '<c:out value="${envioURL}" escapeXml="false"/>'+idprestamo;
	}

	function comprobarDisponibilidad(idprestamo) {
			<c:url var="envioURL" value="/action/gestionPrestamos">
				<c:param name="method" value="comprobardisponibilidad" />
				<c:param name="idprestamo" value=""/>
			</c:url>
			window.location = '<c:out value="${envioURL}" escapeXml="false"/>'+idprestamo;
	}

	function comprobarDisponibilidadProrroga(idprestamo) {
			<c:url var="envioURL" value="/action/gestionPrestamos">
				<c:param name="method" value="comprobardisponibilidadprorroga" />
				<c:param name="idprestamo" value=""/>
			</c:url>
			window.location = '<c:out value="${envioURL}" escapeXml="false"/>'+idprestamo;
	}

	function enviarPrestamo(idprestamo,identificadorPrestamo) {
			<c:url var="envioURL" value="/action/gestionPrestamos">
				<c:param name="method" value="enviardesdevista" />
				<c:param name="idprestamo" value=""/>
			</c:url>
			window.location = '<c:out value="${envioURL}" escapeXml="false"/>'+idprestamo;
	}

	function autorizardenegarPrestamo(idprestamo,identificadorPrestamo) {
			<c:url var="envioURL" value="/action/gestionPrestamos">
				<c:param name="method" value="autorizardenegardesdevista" />
				<c:param name="idprestamo" value=""/>
			</c:url>

			var fent = document.getElementById("fentrega");
			var fentvalue = "";
			if (fent!=null && fent != "")
				fentvalue = fent.value;
			window.location = '<c:out value="${envioURL}" escapeXml="false"/>'+idprestamo
				+"&fentrega="+fentvalue;
	}

	function entregarPrestamo(idprestamo,identificadorPrestamo) {
			<c:url var="envioURL" value="/action/gestionPrestamos">
				<c:param name="method" value="entregardesdevista" />
				<c:param name="idprestamo" value=""/>
			</c:url>

			var fechaDev= document.getElementById("fechaDevolucion");
			var direccion = '<c:out value="${envioURL}" escapeXml="false"/>'+idprestamo;

			if(fechaDev  && fechaDev.value != ""){
				direccion += "&fechaDevolucion=" + fechaDev.value;
			}

			//alert(direccion);
			window.location = direccion;
	}

	function solicitarProrroga(idprestamo,identificadorPrestamo) {
			<c:url var="envioURL" value="/action/gestionProrroga">
				<c:param name="method" value="nuevaProrroga" />
				<c:param name="idPrestamo" value=""/>
			</c:url>
			window.location = '<c:out value="${envioURL}" escapeXml="false"/>'+idprestamo;
	}

	function gestionProrrogaSolicitada(idprestamo,identificadorPrestamo) {
			<c:url var="envioURL" value="/action/gestionProrroga">
				<c:param name="method" value="gestionProrrogaSolicitada" />
				<c:param name="idPrestamo" value=""/>
			</c:url>
			window.location = '<c:out value="${envioURL}" escapeXml="false"/>'+idprestamo;
	}

	function denegarProrroga(idprestamo,identificadorPrestamo) {
			document.getElementById("motivoprorroga").style.display = 'block';
	}

	function reclamar(veces,idprestamo,identificadorPrestamo) {
			var URLEnvio="";
			if (veces ==1){
				<c:url var="envioURL" value="/action/gestionPrestamos">
					<c:param name="method" value="reclamarunadesdevista" />
					<c:param name="idprestamo" value=""/>
				</c:url>
				URLEnvio = '<c:out value="${envioURL}" escapeXml="false"/>'+idprestamo;
			}else{
				<c:url var="envioURL" value="/action/gestionPrestamos">
					<c:param name="method" value="reclamardosdesdevista" />
					<c:param name="idprestamo" value=""/>
				</c:url>
				URLEnvio = '<c:out value="${envioURL}" escapeXml="false"/>'+idprestamo;
			}
			window.location = URLEnvio;
	}

	function entregarReservaPrestamo(idprestamo,identificadorConsulta) {
			<c:url var="envioURL" value="/action/gestionPrestamos">
				<c:param name="method" value="solicitardesdereservaprestamo" />
				<c:param name="idprestamo" value=""/>
			</c:url>
			var direccion = '<c:out value="${envioURL}" escapeXml="false"/>'+idprestamo;
			window.location = direccion;
	}

	function imprimirEntrada(idprestamo) {
			<c:url var="envioURL" value="/action/gestionPrestamos">
				<c:param name="method" value="imprimirEntrada" />
				<c:param name="idprestamo" value=""/>
			</c:url>
			window.location = '<c:out value="${envioURL}" escapeXml="false"/>'+idprestamo;
	}

	function imprimirReclamacion(idprestamo,numReclamacion) {
			<c:url var="envioURL" value="/action/papeletaReclamacionPrestamo">
				<c:param name="idPrestamo" value=""/>
			</c:url>
			var URLEnvio = '<c:out value="${envioURL}" escapeXml="false"/>'+idprestamo
				+"&numReclamacion="+numReclamacion;
			window.location = URLEnvio;
	}


	function eliminar(idprestamo) {
			if (confirm("<bean:message key="archigest.archivo.prestamos.eliminacion.confirm"/>")) {
				<c:url var="envioURL" value="/action/gestionPrestamos">
					<c:param name="method" value="eliminarPrestamo"/>
					<c:param name="id" value=""/>
				</c:url>
				window.location = '<c:out value="${envioURL}" escapeXml="false"/>'+idprestamo;
			}
	}

	//Para editar campos en la columnas del displaytag

	function modificarCampo(nombre,pos){
		var divLabelCampo="divLabel"+nombre+pos;
		var textBoxCampo="textBox"+nombre+pos;
		var divAceptarCancelarCampo="divAceptarCancelar"+nombre+pos;
		//al pulsar el boton de modificar, hay que salir del modo edicion en el resto de
		//campos en esa columna.
		for(i=1;;i++){
			divAceptarCancelarCampo="divAceptarCancelar"+nombre+i;
			divLabelCampo="divLabel"+nombre+i;
			elemento=document.getElementById(divAceptarCancelarCampo);

			if(elemento!=null){
				elemento.className="hidden";
				document.getElementById(divLabelCampo).className="visible";
			}
			else{
				break;
			}
		}

		divAceptarCancelarCampo="divAceptarCancelar"+nombre+pos;
		divLabelCampo="divLabel"+nombre+pos;

		document.getElementById(divAceptarCancelarCampo).className="visible";
		document.getElementById(divLabelCampo).className="hidden";
		document.getElementById("valor"+nombre).value=textBoxCampo.value;
	}

	function cancelarModificacionCampo(nombre,pos){
		var divAceptarCancelarCampo="divAceptarCancelar"+nombre+pos;
		var divLabelCampo="divLabel"+nombre+pos;
		document.getElementById(divAceptarCancelarCampo).className="hidden";
		document.getElementById(divLabelCampo).className="visible";
	}

	function aceptarModificacionCampo(nombre,pos){
		var textBoxCampo="textBox"+nombre+pos;
		var method = "actualizar"+nombre;

		if(document.getElementById(textBoxCampo).value==document.getElementById("valor"+nombre).value){
			cancelarModificacionCampo(nombre,pos);
		}
		else{
			<c:url var="URL" value="/action/gestionPrestamos"></c:url>
			window.location = 	'<c:out value="${URL}"/>'+
								'?method='+method+
								'&position='+pos+
								'&valor'+nombre+'='+document.getElementById(textBoxCampo).value;
		}
	}


</script>


<input type="hidden" id="valorCampoObservaciones" name="valorCampoObservaciones" />
<input type="hidden" id="valorCampoExpedienteFS" name="valorCampoExpedienteFS" />
<input type="hidden" name="method" value="actualizarCampoObservaciones" />
<input type="hidden" id="position" name="position" />
<c:set var="bPrestamo" value="${sessionScope[appConstants.prestamos.PRESTAMO_KEY]}"/>

<c:set var="VER_BOTON_SELECCIONAR" value="false"/>
<%--Establecemos variables para el displaytag --%>
<c:if test="${requestScope[appConstants.prestamos.VER_COLUMNA_DISPONIBILIDAD]}">
	<c:set var="VER_COLUMNA_DISPONIBILIDAD" value="true"/>
</c:if>



<html:form action="/gestionDetallesPrestamos">

<div id="contenedor_ficha">
<div class="ficha">

<h1><span>
<div class="w100">
<TABLE class="w98" cellpadding=0 cellspacing=0>
  <TR>
    <TD class="etiquetaAzul12Bold" height="25px">
		<bean:message key="archigest.archivo.prestamos.datosPrestamos"/>
    </TD>
    <TD align="right">
		<TABLE cellpadding=0 cellspacing=0>
		 <TR>
		 		<%--boton comprobar disponibilidad--%>
				<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_VER_DISPONIBILIDAD]}">
					<c:set var="llamadaEnviar">javascript:comprobarDisponibilidad('<c:out value="${bPrestamo.id}" />')</c:set>
				    <TD>
				   		<a class=etiquetaAzul12Bold href="<c:out value="${llamadaEnviar}" escapeXml="false"/>">
							<html:img
								page="/pages/images/disponibilidad.gif"
								altKey="archigest.archivo.solicitudes.disponibilidad"
								titleKey="archigest.archivo.solicitudes.disponibilidad" styleClass="imgTextMiddle" />
				   		 	&nbsp;<bean:message key="archigest.archivo.solicitudes.disponibilidad"/>
				   		</a>
				   </TD>
		       	   <TD width="10">&nbsp;</TD>
				</c:if>

				<%--boton comprobar disponibilidad entrega--%>
				<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_VER_DISPONIBILIDAD_ENTREGA]}">
					<c:set var="llamadaEnviar">javascript:comprobarDisponibilidadEntrega('<c:out value="${bPrestamo.id}" />')</c:set>
				    <TD>
				   		<a class=etiquetaAzul12Bold href="<c:out value="${llamadaEnviar}" escapeXml="false"/>">
							<html:img
								page="/pages/images/disponibilidad.gif"
								altKey="archigest.archivo.solicitudes.disponibilidad"
								titleKey="archigest.archivo.solicitudes.disponibilidad" styleClass="imgTextMiddle" />
				   		 	&nbsp;<bean:message key="archigest.archivo.solicitudes.disponibilidad"/>
				   		</a>
				   </TD>
		       	   <TD width="10">&nbsp;</TD>
				</c:if>

				<%--boton comprobar disponibilidad prorroga--%>
				<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_VER_DISPONIBILIDAD_PRORROGA]}">
					<c:set var="llamadaEnviar">javascript:comprobarDisponibilidadProrroga('<c:out value="${bPrestamo.id}" />')</c:set>
				    <TD>
				   		<a class=etiquetaAzul12Bold href="<c:out value="${llamadaEnviar}" escapeXml="false"/>">
							<html:img
								page="/pages/images/disponibilidad.gif"
								altKey="archigest.archivo.solicitudes.disponibilidad"
								titleKey="archigest.archivo.solicitudes.disponibilidad" styleClass="imgTextMiddle" />
				   		 	&nbsp;<bean:message key="archigest.archivo.solicitudes.disponibilidad"/>
				   		</a>
				   </TD>
		       	   <TD width="10">&nbsp;</TD>
				</c:if>

				<%--boton enviar a solicitar--%>
				<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_ENVIAR_SOLICITAR]}">
					<c:set var="llamadaEnviar">javascript:enviarPrestamo('<c:out value="${bPrestamo.id}" />','<c:out value="${bPrestamo.codigoTransferencia}"/>')</c:set>
				    <TD>
				   		<a class=etiquetaAzul12Bold href="<c:out value="${llamadaEnviar}" escapeXml="false"/>">
							<html:img
							page="/pages/images/enviar.gif"
								altKey="archigest.archivo.solicitar"
								titleKey="archigest.archivo.solicitar" styleClass="imgTextMiddle" />
				   		 	&nbsp;<bean:message key="archigest.archivo.solicitar"/>
				   		</a>
				   </TD>
		       	   <TD width="10">&nbsp;</TD>
				</c:if>

				<%--boton enviar a denegar o autorizar--%>
				<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_ENVIAR_DENEGAR_AUTORIZAR]}">
					<c:set var="llamadaEnviar">javascript:autorizardenegarPrestamo('<c:out value="${bPrestamo.id}" />','<c:out value="${bPrestamo.codigoTransferencia}"/>')</c:set>
				    <TD>
				   		<a class=etiquetaAzul12Bold href="<c:out value="${llamadaEnviar}" escapeXml="false"/>">
							<html:img
								page="/pages/images/finCorrecion.gif"
								altKey="archigest.archivo.solicitudes.finAutorizacion"
								titleKey="archigest.archivo.solicitudes.finAutorizacion" styleClass="imgTextMiddle" />
				   		 	&nbsp;<bean:message key="archigest.archivo.solicitudes.finAutorizacion"/>
				   		</a>

				   </TD>
			       	   <TD width="10">&nbsp;</TD>
				</c:if>

				<%--boton entregar--%>
				<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_ENTREGAR]}">
					<c:set var="llamadaEnviar">javascript:entregarPrestamo('<c:out value="${bPrestamo.id}" />','<c:out value="${bPrestamo.codigoTransferencia}"/>')</c:set>
				    <TD>
				   		<a class=etiquetaAzul12Bold href="<c:out value="${llamadaEnviar}" escapeXml="false"/>">
							<html:img
								page="/pages/images/devolver.gif"
								altKey="archigest.archivo.entregar"
								titleKey="archigest.archivo.entregar" styleClass="imgTextMiddle" />
				   		 	&nbsp;<bean:message key="archigest.archivo.entregar"/>
				   		</a>
				   </TD>
			       	   <TD width="10">&nbsp;</TD>
				</c:if>
				<%--boton solicitar prorroga--%>
				<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_SOL_PRORROGA]}">
					<c:set var="llamadaEnviar">javascript:solicitarProrroga('<c:out value="${bPrestamo.id}" />','<c:out value="${bPrestamo.codigoTransferencia}"/>')</c:set>
				    <TD>
				   		<a class=etiquetaAzul12Bold href="<c:out value="${llamadaEnviar}" escapeXml="false"/>">
							<html:img
								page="/pages/images/prorroga.gif"
								altKey="archigest.archivo.solicitudes.solicitarProrroga"
								titleKey="archigest.archivo.solicitudes.solicitarProrroga" styleClass="imgTextMiddle" />
				   		 	&nbsp;<bean:message key="archigest.archivo.solicitudes.solicitarProrroga"/>
				   		</a>
				   </TD>
			       	   <TD width="10">&nbsp;</TD>
				</c:if>
				<%--boton AUTORIZAR prorroga--%>
				<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_AUTORIZAR_PRORROGA]}">
					<c:set var="llamadaEnviar">javascript:gestionProrrogaSolicitada('<c:out value="${bPrestamo.id}" />','<c:out value="${bPrestamo.codigoTransferencia}"/>')</c:set>
				    <TD>
				   		<a class=etiquetaAzul12Bold href="<c:out value="${llamadaEnviar}" escapeXml="false"/>">
							<html:img
								page="/pages/images/pro_acep.gif"
								altKey="archigest.archivo.solicitudes.gestionarProrroga"
								titleKey="archigest.archivo.solicitudes.gestionarProrroga" styleClass="imgTextMiddle" />
				   		 	&nbsp;<bean:message key="archigest.archivo.solicitudes.gestionarProrroga"/>
				   		</a>
				   </TD>
			       	   <TD width="10">&nbsp;</TD>
				</c:if>
				<%--boton DENEGAR prorroga--%>
				<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_DENEGAR_PRORROGA]}">
					<c:set var="llamadaEnviar">javascript:denegarProrroga('<c:out value="${bPrestamo.id}" />','<c:out value="${bPrestamo.codigoTransferencia}"/>')</c:set>
				    <TD>
				   		<a class=etiquetaAzul12Bold href="<c:out value="${llamadaEnviar}" escapeXml="false"/>">
							<html:img
								page="/pages/images/pro_den.gif"
								altKey="archigest.archivo.solicitudes.denegarProrroga"
								titleKey="archigest.archivo.solicitudes.denegarProrroga" styleClass="imgTextMiddle" />
				   		 	&nbsp;<bean:message key="archigest.archivo.solicitudes.denegarProrroga"/>
				   		</a>
				   </TD>
			       	   <TD width="10">&nbsp;</TD>
				</c:if>
				<%--boton RECLAMA 1 --%>
				<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_RECLAMACION_1]}">
					<c:set var="llamadaEnviar">javascript:reclamar(1,'<c:out value="${bPrestamo.id}" />','<c:out value="${bPrestamo.codigoTransferencia}"/>')</c:set>
					<TD>
				   		<a class=etiquetaAzul12Bold href="<c:out value="${llamadaEnviar}" escapeXml="false"/>">
							<html:img
							page="/pages/images/enviar.gif"
							altKey="archigest.archivo.reclamar"
							titleKey="archigest.archivo.reclamar" styleClass="imgTextMiddle" />
				   		 	&nbsp;<bean:message key="archigest.archivo.reclamar"/>
				   		</a>
					</TD>
					<TD width="10">&nbsp;</TD>
				</c:if>
				<%--boton RECLAMA 2 --%>
				<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_RECLAMACION_2]}">
					<c:set var="llamadaEnviar">javascript:reclamar(2,'<c:out value="${bPrestamo.id}" />','<c:out value="${bPrestamo.codigoTransferencia}"/>')</c:set>
					<TD>
				   		<a class=etiquetaAzul12Bold href="<c:out value="${llamadaEnviar}" escapeXml="false"/>">
							<html:img
								page="/pages/images/enviar.gif"
								altKey="archigest.archivo.reclamar"
								titleKey="archigest.archivo.reclamar" styleClass="imgTextMiddle" />
				   		 	&nbsp;<bean:message key="archigest.archivo.reclamar"/>
				   		</a>
					</TD>
					<TD width="10">&nbsp;</TD>
				</c:if>
				<%--boton solicitar entrega de reserva --%>
				<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_SOLICITAR_RESERVA]}">
					<c:set var="llamadaEnviar">javascript:entregarReservaPrestamo('<c:out value="${bPrestamo.id}" />','<c:out value="${bPrestamo.codigoTransferencia}"/>')</c:set>
					<TD>
				   		<a class=etiquetaAzul12Bold href="<c:out value="${llamadaEnviar}" escapeXml="false"/>">
							<html:img
								page="/pages/images/enviar.gif"
								altKey="archigest.archivo.solicitarEntrega"
								titleKey="archigest.archivo.solicitarEntrega" styleClass="imgTextMiddle" />
				   		 	&nbsp;<bean:message key="archigest.archivo.solicitarEntrega"/>
				   		</a>
					</TD>
					<TD width="10">&nbsp;</TD>
				</c:if>

				<%--boton imprimir etiquetas--%>
				<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_IMPRIMIR_ETIQUETAS]}">
					<c:url var="etiquetasURL" value="/action/etiquetasPrestamo">
						<c:param name="id" value="${bPrestamo.id}" />
					</c:url>
				    <TD>
				   		<a class=etiquetaAzul12Bold href="<c:out value="${etiquetasURL}" escapeXml="false"/>">
							<html:img
								page="/pages/images/print.gif"
								altKey="archigest.archivo.solicitudes.imprimiretiquetas"
								titleKey="archigest.archivo.solicitudes.imprimiretiquetas"
								styleClass="imgTextMiddle" />
				   		 	&nbsp;<bean:message key="archigest.archivo.solicitudes.imprimiretiquetas"/>
				   		</a>
				   </TD>
		       	   <TD width="10">&nbsp;</TD>
				</c:if>

				<%--boton imprimir salida--%>
				<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_IMPRIMIR_SALIDA]}">
					<c:url var="paleletasURL" value="/action/papeletasPrestamo">
						<c:param name="id" value="${bPrestamo.id}" />
					</c:url>
				    <TD>
				   		<a class=etiquetaAzul12Bold href="<c:out value="${paleletasURL}" escapeXml="false"/>">
							<html:img
								page="/pages/images/print.gif"
								altKey="archigest.archivo.solicitudes.imprimirpapeletas"
								titleKey="archigest.archivo.solicitudes.imprimirpapeletas"
								styleClass="imgTextMiddle" />
				   		 	&nbsp;<bean:message key="archigest.archivo.solicitudes.imprimirpapeletas"/>
				   		</a>
				   </TD>
		       	   <TD width="10">&nbsp;</TD>
				</c:if>

				<%--boton imprimir entrada--%>
				<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_IMPRIMIR_ENTRADA]}">
					<c:set var="llamadaEnviar">javascript:imprimirEntrada('<c:out value="${bPrestamo.id}" />')</c:set>
				    <TD>
				   		<a class=etiquetaAzul12Bold href="<c:out value="${llamadaEnviar}" escapeXml="false"/>">
							<html:img
								page="/pages/images/print.gif"
								altKey="archigest.archivo.solicitudes.justificanteDevolucion"
								titleKey="archigest.archivo.solicitudes.justificanteDevolucion" styleClass="imgTextMiddle" />
				   		 	&nbsp;<bean:message key="archigest.archivo.solicitudes.justificanteDevolucion"/>
				   		</a>
				   </TD>
		       	   <TD width="10">&nbsp;</TD>
				   <td nowrap>
					   <c:url var="listadoDevolucionesURL" value="/action/listadoDevolucionesSolicitud">
					   	<c:param name="idPrestamo" value="${bPrestamo.id}"/>
					   </c:url>
						<a class="etiquetaAzul12Bold"
							href="<c:out value="${listadoDevolucionesURL}"/>">
							<html:img page="/pages/images/print.gif"
							titleKey="archigest.archivo.solicitudes.informeDevoluciones"
							altKey="archigest.archivo.solicitudes.informeDevoluciones"
							styleClass="imgTextMiddle"/>
							&nbsp;<bean:message key="archigest.archivo.solicitudes.informeDevoluciones"/>
						</a>
					</td>
					<TD width="10">&nbsp;</TD>
				</c:if>

				<%--boton imprimir reclamacion 1--%>
				<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_IMPRIMIR_RECLAMACION_1]}">
				    <TD>
				   		<a class="etiquetaAzul12Bold" href="javascript:imprimirReclamacion('<c:out value="${bPrestamo.id}" />',1)">
							<html:img
								page="/pages/images/print.gif"
								altKey="archigest.archivo.solicitudes.imprimirreclamacion"
								titleKey="archigest.archivo.solicitudes.imprimirreclamacion" styleClass="imgTextMiddle" />
				   		 	&nbsp;<bean:message key="archigest.archivo.solicitudes.imprimirreclamacion"/>
				   		</a>

				   </TD>
			       	   <TD width="10">&nbsp;</TD>

				</c:if>

				<%--boton imprimir reclamacion 2--%>
				<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_IMPRIMIR_RECLAMACION_2]}">
				    <TD>
				   		<a class="etiquetaAzul12Bold" href="javascript:imprimirReclamacion('<c:out value="${bPrestamo.id}" />',2)">
							<html:img
								page="/pages/images/print.gif"
								altKey="archigest.archivo.solicitudes.imprimirreclamacion"
								titleKey="archigest.archivo.solicitudes.imprimirreclamacion" styleClass="imgTextMiddle" />
				   		 	&nbsp;<bean:message key="archigest.archivo.solicitudes.imprimirreclamacion"/>
				   		</a>

				   </TD>
			       	   <TD width="10">&nbsp;</TD>
				</c:if>

				<%--boton eliminar --%>
				<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_ELIMINAR]}">
					<c:set var="llamadaEliminar">javascript:eliminar('<c:out value="${bPrestamo.id}" />')</c:set>
				    <td>
				   		<a class="etiquetaAzul12Bold" href="<c:out value="${llamadaEliminar}" escapeXml="false"/>">
							<html:img page="/pages/images/delete.gif"
								altKey="archigest.archivo.eliminar"
								titleKey="archigest.archivo.eliminar"
								styleClass="imgTextMiddle" />
				   		 	&nbsp;<bean:message key="archigest.archivo.eliminar"/>
				   		</a>

				   </td>
				   <td width="10">&nbsp;</td>
				</c:if>

				<%--boton Cerrar --%>
	      	  <TD>
				<c:url var="volver2URL" value="/action/gestionPrestamos">
					<c:param name="method" value="goBack" />
				</c:url>
		   		<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${volver2URL}" escapeXml="false"/>'">
					<html:img
						page="/pages/images/close.gif"
						altKey="archigest.archivo.cerrar"
						titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
		   		 	&nbsp;<bean:message key="archigest.archivo.cerrar"/>
		   		</a>
		   </TD>
		 </TR>
		</TABLE>
	</TD>
  </TR>
</TABLE>
</div>
</span></h1>

<div class="cuerpo_drcha">
<div class="cuerpo_izda">

<DIV id="barra_errores"><archivo:errors/></DIV>

<DIV class="separador1">&nbsp;</DIV>

<script>
	function denegarProrroga2(idprestamo,identificadorPrestamo)
	{
		<c:url var="envioURL" value="/action/gestionPrestamos">
			<c:param name="method" value="denegarprorrogadesdevista"/>
			<c:param name="idMotivo" value=""/>
		</c:url>

		window.location = '<c:out value="${envioURL}" escapeXml="false"/>'+
			document.getElementById('motivorechazoprorroga').value+'&idprestamo='+idprestamo;


	}

	function ocultarMotivoProrroga(){
			document.getElementById("motivoprorroga").style.display = 'none';
		}
</script>

	<div class="bloque" id="motivoprorroga">
		<TABLE class="w100" cellpadding="4" cellspacing="4">
		<TR>
				   	<TD width="100%" style="text-align:right;">
						<a class="etiquetaAzul12Bold" href="javascript:denegarProrroga2('<c:out value="${bPrestamo.id}" />','<c:out value="${bPrestamo.codigoTransferencia}"/>');" >
							<html:img
								page="/pages/images/Ok_Si.gif" border="0"
								altKey="archigest.archivo.aceptar"
								titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
							&nbsp;<bean:message key="archigest.archivo.aceptar"/>
						</a>&nbsp;&nbsp;&nbsp;
				   		<a class="etiquetaAzul12Bold" href="javascript:ocultarMotivoProrroga();">
							<html:img
								page="/pages/images/Ok_No.gif" border="0"
								altKey="archigest.archivo.cancelar"
								titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
				   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
				   		</a>&nbsp;
				   	</TD>
		</TR>
	  	<TR>
			<TD class="etiquetaAzul12Bold" style="text-align:center;">
				<bean:message key="archigest.archivo.prestamos.anadirMotivoRechazo" />&nbsp;&nbsp;
				<select id="motivorechazoprorroga" name="idmotivorechazoprorroga">
							<c:forEach items="${sessionScope[appConstants.prestamos.LISTA_MOTIVO_RECHAZO_PRORROGA_KEY]}" var="motivo" varStatus="status">
								<option  value="<c:out value="${motivo.id}"/>">
									<c:out value="${motivo.motivo}"/>
								</option>
							</c:forEach>
				</select>
			</TD>
		</TR>
	  	<TR><TD height="10px">&nbsp;</TD>
	  	</TR>
		</TABLE>
	</div>

	<script>
			ocultarMotivoProrroga();
	</script>

	<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
				<tiles:put name="blockName" direct="true">prestamoInfo</tiles:put>
				<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.prestamos.identificacion"/></tiles:put>
				<tiles:put name="buttonBar" direct="true">
					<TABLE cellpadding="0" cellspacing="0">
					  <TR>
						<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_EDITAR]}">
							<TD>
								<c:url var="editarURL" value="/action/gestionPrestamos">
									<c:param name="method" value="edicion" />
								</c:url>
						   		<a class="etiquetaAzul12Normal" href="javascript:window.location='<c:out value="${editarURL}" escapeXml="false"/>'">
									 <html:img
										titleKey="archigest.archivo.editar"
										altKey="archigest.archivo.editar"
										page="/pages/images/editDoc.gif" styleClass="imgTextMiddle"/>
									 &nbsp;<bean:message key="archigest.archivo.editar"/>
						   		</a>
							</TD>
						</c:if>
				     </TR>
					</TABLE>
				</tiles:put>
				<tiles:put name="visibleContent" direct="true">
					<tiles:insert page="/pages/tiles/solicitudes/prestamos/cuerpo_cabeceracte_prestamo.jsp" />
				</tiles:put>

				<tiles:put name="dockableContent" direct="true">
					<tiles:insert page="/pages/tiles/solicitudes/prestamos/datos_prestamo.jsp" flush="true"/>
				</tiles:put>
			</tiles:insert>

	<div class="separador8">&nbsp;</div>

	<div class="cabecero_bloque"> <%--cabecero segundo bloque de datos --%>

	<script>
		function eliminarSelectedItems(selectionFormName) {
		var nSelected=FormsToolKit.getNumSelectedChecked(document.forms[0],"detallesseleccionados");
			if(nSelected>=1){

				if (confirm("<bean:message key='archigest.archivo.prestamos.eliminacion.msg'/>")) {
					var selectionForm = document.forms[selectionFormName];
					if (selectionForm)
						selectionForm.submit();
				}
			}
			else{
				alert("<bean:message key='archigest.archivo.prestamos.SelAlMenosUnaUDoc'/>");
			}
		}

		function autorizarDetallesPrestamos(){
			var nSelected=FormsToolKit.getNumSelectedChecked(document.forms[0],"detallesseleccionados");
			if(nSelected>=1){

					document.forms[0].method.value="autorizardetallesprestamos";
					document.forms[0].submit();

			}else{
					alert("<bean:message key='archigest.archivo.prestamos.SelAlMenosUnaUDoc'/>");
			}
		}

		function devolverDetallesPrestamos(){

			var nSelected=FormsToolKit.getNumSelectedChecked(document.forms[0],"detallesseleccionados");
			if(nSelected>=1){


					document.forms[0].method.value="devolverdetallesprestamos";
					document.forms[0].submit();


			}else{
					alert("<bean:message key='archigest.archivo.prestamos.SelAlMenosUnaUDoc'/>");
			}
		}

		function ocultarMotivo(){
			document.getElementById("motivo").style.display = 'none';
		}
		function denegarDetallesPrestamos(){
			var nSelected=FormsToolKit.getNumSelectedChecked(document.forms[0],"detallesseleccionados");
			if(nSelected>=1){
			document.getElementById("motivo").style.display = 'block';
			}else{
					alert("<bean:message key='archigest.archivo.prestamos.SelAlMenosUnaUDoc'/>");
			}
		}
		function denegarDetallesPrestamos2(){
			var nSelected=FormsToolKit.getNumSelectedChecked(document.forms[0],"detallesseleccionados");
			if(nSelected>=1){
				document.forms[0].method.value="denegardetallesprestamos";
				document.forms[0].submit();
			}else{
					alert("<bean:message key='archigest.archivo.prestamos.SelAlMenosUnaUDoc'/>");
			}
		}
	</script>

<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_ELIMINAR_DETALLE]}">
	<c:set var="VER_BOTON_SELECCIONAR" value="true"/>
</c:if>
<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_AUTORIZAR_DETALLE]}">
	<c:set var="VER_BOTON_SELECCIONAR" value="true"/>
</c:if>
<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_DENEGAR_DETALLE]}">
	<c:set var="VER_BOTON_SELECCIONAR" value="true"/>
</c:if>
<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_DEVOLVER]}">
	<c:set var="VER_BOTON_SELECCIONAR" value="true"/>
</c:if>

<TABLE class="w98m1" cellpadding="0" cellspacing="0">
  <TBODY>
  <TR>
    <TD class="etiquetaAzul12Bold" width="25%">
		<bean:message key="archigest.archivo.prestamos.udocs"/>
	</TD>
    <TD width="45%" align="right">
	<TABLE cellpadding="0" cellspacing="0">
	  <TR>
		<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_ANADIR_DETALLE]}">
		    <TD>
				<c:url var="addDetalleURL" value="/action/gestionDetallesPrestamos?method=nuevoDetalle" />
				<a href="<c:out value="${addDetalleURL}" escapeXml="false"/>" class="etiquetaAzul12Normal">
					<html:img
						titleKey="archigest.archivo.prestamos.anadirDetalle"
						altKey="archigest.archivo.prestamos.anadirDetalle"
						page="/pages/images/addDoc.gif" styleClass="imgTextMiddle"/>
					&nbsp;<bean:message key="archigest.archivo.prestamos.anadirDetalle"/>
				</a>
	    	</TD>
		    <TD width="10">&nbsp;</TD>
    	</c:if>
	    <c:if test="${requestScope[appConstants.prestamos.VER_BOTON_ELIMINAR_DETALLE]}">
	    	<TD>
				<a class="etiquetaAzul12Normal" href="javascript:eliminarSelectedItems('<c:out value="${mappingGestionUdocs.name}" />')">
					<html:img
						titleKey="archigest.archivo.prestamos.eliminarDetalle"
						altKey="archigest.archivo.prestamos.eliminarDetalle"
						page="/pages/images/delDoc.gif" styleClass="imgTextMiddle"/>
					&nbsp;<bean:message key="archigest.archivo.prestamos.eliminarDetalle"/>
				</a>
		    </TD>
	    </c:if>
    	<%--botones autorizar y denegar --%>
		<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_AUTORIZAR_DETALLE]}">
			<TD>
			<c:set var="llamadaAutorizarDetallesPrestamos">javascript:autorizarDetallesPrestamos()</c:set>
				<a class="etiquetaAzul12Bold" href='<c:out value="${llamadaAutorizarDetallesPrestamos}" escapeXml="false"/>' >
					<html:img
						page="/pages/images/aceptar.gif" border="0"
						altKey="archigest.archivo.autorizar"
						titleKey="archigest.archivo.autorizar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.autorizar"/>
				</a>
			</TD>
		    <TD width="10">&nbsp;</TD>
		</c:if>
        <%--BOTON DENEGAR CON PROMPT PARA EL MOTIVO --%>
	    <c:if test="${requestScope[appConstants.prestamos.VER_BOTON_DENEGAR_DETALLE]}">
	    	<TD>
				<a class="etiquetaAzul12Bold" href="javascript:denegarDetallesPrestamos();" >
					<html:img
						page="/pages/images/rechazar.gif" border="0"
						altKey="archigest.archivo.denegar"
						titleKey="archigest.archivo.denegar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.denegar"/>
				</a>
			</TD>
		</c:if>
		<%--boton devolver --%>
		<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_DEVOLVER]}">
			<TD>
				<c:set var="llamadaDevolverDetallesPrestamos">javascript:devolverDetallesPrestamos()</c:set>
				<a class="etiquetaAzul12Bold" href='<c:out value="${llamadaDevolverDetallesPrestamos}" escapeXml="false"/>' >
					<html:img
						page="/pages/images/devolver.gif" border="0"
						altKey="archigest.archivo.solicitudes.recibirDevolucion"
						titleKey="archigest.archivo.solicitudes.recibirDevolucion" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.solicitudes.recibirDevolucion"/>
				</a>
			</TD>
		</c:if>
		<TD width="10">&nbsp;</TD>
	  </TR>
	</TABLE>
	</TD>
  </TR></TBODY></TABLE>
</div> <%--cabecero bloque --%>



<DIV class="bloque"> <%--segundo bloque de datos --%>

	<input type="hidden" name="method" value="eliminarDetalles" />
	<c:set var="idPrestamo">
		<c:out value ="${bPrestamo.id}"/>
	</c:set>
	<input type="hidden" name="idsolicitud" value="<c:out value="${idPrestamo}"/>" />

	 <c:if test="${VER_BOTON_SELECCIONAR}">
		<div class="separador1">&nbsp;</div>
		<div class="w100">
		<TABLE class="w98" cellpadding="0" cellspacing="0">
		  <TR>
			<TD width="100%" align="right">
				<TABLE cellpadding=0 cellspacing=0>
				  <TR>
			 		<TD>
			 			<a class="etiquetaAzul12Normal" href="javascript:seleccionarCheckboxSet(document.forms[0].detallesseleccionados);" >
							<html:img
								page="/pages/images/checked.gif" border="0"
								altKey="archigest.archivo.selTodos"
								titleKey="archigest.archivo.selTodos" styleClass="imgTextBottom" />
							<bean:message key="archigest.archivo.selTodos"/>&nbsp;
						</a>
					</TD>
				    <TD width="20">&nbsp;</TD>
				    <TD>
						<a class="etiquetaAzul12Normal" href="javascript:deseleccionarCheckboxSet(document.forms[0].detallesseleccionados);" >
							<html:img
								page="/pages/images/check.gif" border="0"
								altKey="archigest.archivo.quitarSel"
								titleKey="archigest.archivo.quitarSel" styleClass="imgTextBottom" />
							&nbsp;<bean:message key="archigest.archivo.quitarSel"/>
						</a>
					</TD>
			     </TR>
				</TABLE>
			</TD>
		  </TR>
		</TABLE>
		</div>
	</c:if>

	<div class="w100" id="motivo">
		<div class="separador5">&nbsp;</div>
		<center>
		<TABLE class="w98" cellpadding="4" cellspacing="4" style="border:1px solid #999999;">
			<TR>
				<TD width="100%" style="text-align:right;">
						<a class="etiquetaAzul12Bold" href="javascript:denegarDetallesPrestamos2();" >
							<html:img
								page="/pages/images/Ok_Si.gif" border="0"
								altKey="archigest.archivo.aceptar"
								titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
							&nbsp;<bean:message key="archigest.archivo.aceptar"/>
						</a>&nbsp;&nbsp;&nbsp;
				   		<a class="etiquetaAzul12Bold" href="javascript:ocultarMotivo();">
							<html:img
								page="/pages/images/Ok_No.gif" border="0"
								altKey="archigest.archivo.cancelar"
								titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
				   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
				   		</a>
				</TD>
			</TR>
		  	<TR>
				<TD class="etiquetaAzul12Bold" style="text-align:center;">
					<bean:message key="archigest.archivo.prestamos.anadirMotivoRechazo" />&nbsp;&nbsp;
					<select id="motivorechazo" name="idMotivoRechazo">
						<c:forEach items="${sessionScope[appConstants.prestamos.LISTA_MOTIVO_RECHAZO_KEY]}" var="motivo" varStatus="status">
							<option  value="<c:out value="${motivo.id}"/>">
								<c:out value="${motivo.motivo}"/>
							</option>
						</c:forEach>
					</select>
				</TD>
			</TR>
		  	<TR><TD height="20px">&nbsp;</TD></TR>
		</TABLE>
		</center>
		<div class="separador5">&nbsp;</div>
	</div> <%--Motivo --%>

	<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_ENTREGAR]}">
	<div class="w100" id="fechaEntrega">
		<div class="separador5">&nbsp;</div>
		<center>
		<TABLE class="w98" cellpadding="4" cellspacing="4" style="border:1px solid #999999;">
		  	<TR><TD height="20px">&nbsp;</TD></TR>
			<TR>
				<TD class="etiquetaAzul12Bold" style="text-align:center;">
					<bean:message key="archigest.archivo.busqueda.form.fecha.devolucion" />&nbsp;&nbsp;
				 	<input type="text" name="fechaDevolucion" id="fechaDevolucion" class="input" size="12" maxlength="10" value='<c:out value="${requestScope['fechaFinPrestamo']}"/>'/>
														&nbsp;<archigest:calendar
															image="../images/calendar.gif"
										                    formId="detallePrestamoForm"
										                    componentId="fechaDevolucion"
										                    format="dd/mm/yyyy"
										                    enablePast="false" />
				</TD>
			</TR>
		  	<TR><TD height="20px">&nbsp;</TD></TR>
		</TABLE>
		</center>
		<div class="separador5">&nbsp;</div>
	</div> <%--Motivo --%>
	</c:if>


	<script>
		ocultarMotivo();
	</script>

	<c:set var="LISTA_NAME" value="sessionScope.${appConstants.prestamos.DETALLE_PRESTAMO_KEY}"/>
	<jsp:useBean id="LISTA_NAME" type="java.lang.String" />

			<display:table name="<%= LISTA_NAME%>"
				id="detallePrestamo"
				style="width:98%;margin-left:auto;margin-right:auto"
				sort="list"
				pagesize="10"
				decorator="solicitudes.prestamos.decorators.ViewDetallePrestamoDecorator"
				requestURI="../../action/gestionPrestamos">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.prestamos.noDetallesPrestamo"/>
				</display:setProperty>

				<c:if test="${VER_BOTON_SELECCIONAR}">
					<display:column style="width:20px;">
						<%--Sacamos el botón de seleccionar si el detalle
							se puede eliminar, autorizar, denegar o devolver --%>
						<c:if test="${!( (detallePrestamo.estado==4 || detallePrestamo.estado==6) && requestScope[appConstants.prestamos.VER_BOTON_DEVOLVER])}">
							<html-el:multibox property="detallesseleccionados" styleId="detallesseleccionados"
               				  value="${detallePrestamo.idudoc}|${detallePrestamo.signaturaudoc}">
			                </html-el:multibox>
						</c:if>
					</display:column>
				</c:if>

				<display:column titleKey="archigest.archivo.num" sortable="true" headerClass="sortable">
					<c:url var="URL" value="/action/gestionDetallesPrestamos">
						<c:param name="method" value="verudoc" />
						<c:param name="ids" value="${detallePrestamo.idsolicitud}|${detallePrestamo.idudoc}|${detallePrestamo.signaturaudoc}" />
					</c:url>
					<a class="tdlink" href='<c:out value="${URL}" escapeXml="false" />'>
						<fmt:formatNumber value="${detallePrestamo_rowNum}" pattern="000"/>
					</a>
				</display:column>
				<display:column titleKey="archigest.archivo.solicitudes.estado" sortable="true" headerClass="sortable">
					<fmt:message key="archigest.archivo.solicitudes.detalle.estado.${detallePrestamo.estado}" />
					<c:if test="${empty detallePrestamo.idElementoCF}">
						<html:img
							page="/pages/images/deleteDoc.gif"
							altKey="archigest.archivo.solicitudes.detalle.eliminado"
							titleKey="archigest.archivo.solicitudes.detalle.eliminado"
							styleClass="imgTextMiddle" />
					</c:if>
				</display:column>
				<display:column titleKey="archigest.archivo.solicitudes.signaturaudoc" sortable="true" headerClass="sortable" >
					<c:out value="${detallePrestamo.signaturaudoc}" />
				</display:column>

				<display:column titleKey="archigest.archivo.solicitudes.expedienteudoc" sortable="true" headerClass="sortable" sortProperty="expedienteudoc">
					<c:choose>
						<c:when test="${detallePrestamo.subtipoCaja}">
							<html:img page="/pages/images/rango2.gif" width="26" styleClass="imgTextMiddle" />
							<c:if test="${!empty detallePrestamo.nombreRangos}">
								<c:out value="${detallePrestamo.nombreRangos}"/>
							</c:if>
							<div id="divLabelCampoExpedienteFS<c:out value='${detallePrestamo_rowNum}' />"
								>
								<bean:write name="detallePrestamo" property="expedienteudoc"/>
								<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_MODIFICAR_COLUMNA_EXPEDIENTEFS]}">
									<a class="etiquetaAzul12Bold" href="javascript:modificarCampo('CampoExpedienteFS',<c:out value='${detallePrestamo_rowNum}' />)">
										<html:img page="/pages/images/editDoc.gif" altKey="archigest.archivo.modificar" titleKey="archigest.archivo.modificar" styleClass="imgTextMiddle" />
									</a>
								</c:if>
							</div>
							<div id="divAceptarCancelarCampoExpedienteFS<c:out value='${detallePrestamo_rowNum}' />" class="hidden">
								<input type="text" id="textBoxCampoExpedienteFS<c:out value='${detallePrestamo_rowNum}' />"
									  class="inputCampo" maxlength="254" value="<bean:write name="detallePrestamo" property="expedienteudoc"/>">
								<a class="etiquetaAzul12Bold" href="javascript:aceptarModificacionCampo('CampoExpedienteFS',<c:out value='${detallePrestamo_rowNum}' />)">
									<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
								</a>
								<a class="etiquetaAzul12Bold" href="javascript:cancelarModificacionCampo('CampoExpedienteFS',<c:out value='${detallePrestamo_rowNum}' />)">
									<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
								</a>
							</div>
						</c:when>
						<c:otherwise>
							<c:out value="${detallePrestamo.expedienteudoc}" />&nbsp;
						</c:otherwise>
					</c:choose>
				</display:column>
				<display:column titleKey="archigest.archivo.solicitudes.titulo" sortable="true" headerClass="sortable" sortProperty="titulo">
					<c:if test="${!empty detallePrestamo.idElementoCF}">
					<security:permissions action="${appConstants.descripcionActions.CONSULTAR_FICHA_ELEMENTO_ACTION}">
						<c:url var="URL" value="/action/isadg">
							<c:param name="method" value="retrieve" />
							<c:param name="id" value="${detallePrestamo.idudoc}" />
						</c:url>
						<a class="tdlink" href='<c:out value="${URL}" escapeXml="false" />'>
					</security:permissions>
					</c:if>
						<bean:write name="detallePrestamo" property="titulo"/>
					<c:if test="${!empty detallePrestamo.idElementoCF}">
					<security:permissions action="${appConstants.descripcionActions.CONSULTAR_FICHA_ELEMENTO_ACTION}">
					</a>
					</security:permissions>
					</c:if>
				</display:column>
				<display:column titleKey="archigest.archivo.solicitudes.observaciones">
					<div id="divLabelCampoObservaciones<c:out value='${detallePrestamo_rowNum}' />"
						>
						<bean:write name="detallePrestamo" property="observaciones"/>
						<c:if test="${requestScope[appConstants.prestamos.VER_BOTON_MODIFICAR_COLUMNA_OBSERVACIONES]}">
							<a class="etiquetaAzul12Bold" href="javascript:modificarCampo('CampoObservaciones',<c:out value='${detallePrestamo_rowNum}' />)">
								<html:img page="/pages/images/editDoc.gif" altKey="archigest.archivo.modificar" titleKey="archigest.archivo.modificar" styleClass="imgTextMiddle" />
							</a>
						</c:if>
					</div>
					<div id="divAceptarCancelarCampoObservaciones<c:out value='${detallePrestamo_rowNum}' />" class="hidden">
						<input type="text" id="textBoxCampoObservaciones<c:out value='${detallePrestamo_rowNum}' />"
							  class="inputCampo" maxlength="254" value="<bean:write name="detallePrestamo" property="observaciones"/>">
						<a class="etiquetaAzul12Bold" href="javascript:aceptarModificacionCampo('CampoObservaciones',<c:out value='${detallePrestamo_rowNum}' />)">
							<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
						</a>
						<a class="etiquetaAzul12Bold" href="javascript:cancelarModificacionCampo('CampoObservaciones',<c:out value='${detallePrestamo_rowNum}' />)">
							<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
						</a>
					</div>
				</display:column>
				<c:if test="${bPrestamo.estado == appConstants.prestamos.ESTADO_PRESTAMO_ABIERTO}">
					<display:column titleKey="archigest.archivo.solicitudes.udocsRelacionadas" style="width:40px;">
						<c:if test="${detallePrestamo.tieneUDocsRelacionadas}">
							<a class="tdlink" href="javascript:popupUDocsRel('<c:out value='${detallePrestamo.idudoc}'/>');">
								<html:img page="/pages/images/buscar.gif" border="0" altKey="archigest.archivo.verUDocsRel" titleKey="archigest.archivo.verUDocsRel" styleClass="imgTextBottom" />
							</a>
						</c:if>
					</display:column>
				</c:if>


				<c:if test="${bPrestamo.estado == appConstants.prestamos.ESTADO_PRESTAMO_ENTREGADO
							|| bPrestamo.estado == appConstants.prestamos.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO}">
					<display:column titleKey="archigest.archivo.solicitudes.revisarDocCabecera">
						<c:if test="${detallePrestamo.estado == appConstants.prestamos.ESTADO_DETALLE_ENTREGADA}" >
							<div>
								<html-el:multibox property="udocsarevisardoc"
				                  onclick="javascript:conmutarEstadoDocRev('${detallePrestamo_rowNum}', this)"
                				  value="${detallePrestamo.idudoc}|${detallePrestamo.signaturaudoc}|${detallePrestamo_rowNum}">
				                </html-el:multibox>

	                			<c:set var="divDocRevStatus" value="divDocRevStatus${detallePrestamo_rowNum}"/>
	                			<html-el:text property="mapFormValues(${divDocRevStatus})" styleId="${divDocRevStatus}" style="display:none;"/>

				 				<c:set var="displayLinea" value="display:none;"/>
								<logic-el:present name="detallePrestamoForm" property="mapFormValues(divDocRevStatus${detallePrestamo_rowNum})">
									<logic-el:match name="detallePrestamoForm" property="mapFormValues(divDocRevStatus${detallePrestamo_rowNum})" value="1">
										<c:set var="displayLinea" value="display:block;"/>
									</logic-el:match>
								</logic-el:present>

								<div id="divDocRev<c:out value='${detallePrestamo_rowNum}'/>" style="<c:out value="${displayLinea}"/>">
										<bean:message key="archigest.archivo.solicitudes.prestamos.gestorRevDoc"/>:
										<html-el:select property="mapFormValues(idusrgestorDocRev${detallePrestamo_rowNum})">
	                     					 <c:forEach items="${sessionScope[appConstants.prestamos.LISTA_GESTORESREVDOC_KEY]}" var="usuario" varStatus="status">
	                      					 	<option value="<c:out value="${usuario.id}"/>">
	                         				 		<c:out value="${usuario.nombreCompleto}"/>
	                       						 </option>
	                      					</c:forEach>
										</html-el:select>
										<br>
										<bean:message key="archigest.archivo.solicitudes.observaciones"/>:
										<html-el:textarea property="mapFormValues(observacionesdocrev${detallePrestamo_rowNum})"
												onkeypress="maxlength(this,254,false)" onchange="maxlength(this,254,true)"
												style="width:100%;">
										</html-el:textarea>
								</div>
                			</div>
						</c:if>
						<c:if test="${detallePrestamo.estado == appConstants.prestamos.ESTADO_DETALLE_DEVUELTA
										&& detallePrestamo.revDocUdocAbierta}" >
							<c:url var="verRevUdoc" value="/action/gestionRevDocAction">
								<c:param name="method" value="mostrarRevisionDoc" />
								<c:param name="idUdoc" value="${detallePrestamo.idudoc}" />
							</c:url>
							<a class="tdlink" href='<c:out value="${verRevUdoc}" escapeXml="false" />'>
								<html:img titleKey="archigest.archivo.ver" altKey="archigest.archivo.ver" page="/pages/images/verDoc.gif" styleClass="imgTextMiddle" />
							</a>
						</c:if>
					</display:column>
				</c:if>

				<c:if test="${VER_COLUMNA_DISPONIBILIDAD}">
					<display:column titleKey="archigest.archivo.solicitudes.disponibleCkeck" style="width:20px">
						<c:choose>
							<c:when test="${detallePrestamo.disponibilidad}">
								<c:if test="${detallePrestamo.estadoDisponibilidad == appConstants.solicitudes.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE_PARCIAL}">
									<html:img page="/pages/images/right.gif"
										altKey="archigest.archivo.solicitudes.detalle.estado.disponibleParcial"
										titleKey="archigest.archivo.solicitudes.detalle.estado.disponibleParcial"
										styleClass="imgTextMiddle" />
									<c:url var="detalleURL" value="/action/gestionDetallesPrestamos">
										<c:param name="method" value="verudoc" />
										<c:param name="ids" value="${detallePrestamo.idsolicitud}|${detallePrestamo.idudoc}|${detallePrestamo.signaturaudoc}" />
									</c:url>
									<a class="tdlink" href="<c:out value="${detalleURL}" escapeXml="false"/>">
										<bean:message key="archigest.archivo.solicitudes.detalle.estado.disponibleParcial"/>
									</a>
								</c:if>
								<c:if test="${detallePrestamo.estadoDisponibilidad == appConstants.solicitudes.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE}">
									<html:img page="/pages/images/right.gif"
										altKey="archigest.archivo.solicitudes.detalle.estado.disponible"
										titleKey="archigest.archivo.solicitudes.detalle.estado.disponible"
										styleClass="imgTextMiddle" />
									<bean:message key="archigest.archivo.solicitudes.detalle.estado.disponible"/>
								</c:if>
							</c:when>
							<c:otherwise>
								<html:img page="/pages/images/wrong.gif"
									altKey="archigest.archivo.solicitudes.detalle.estado.nodisponible"
									titleKey="archigest.archivo.solicitudes.detalle.estado.nodisponible"
									styleClass="imgTextMiddle" />
								<c:url var="detalleURL" value="/action/gestionDetallesPrestamos">
									<c:param name="method" value="verudoc" />
									<c:param name="ids" value="${detallePrestamo.idsolicitud}|${detallePrestamo.idudoc}|${detallePrestamo.signaturaudoc}" />
								</c:url>
								<a class="tdlink" href="<c:out value="${detalleURL}" escapeXml="false"/>">
									<bean:message key="archigest.archivo.solicitudes.detalle.estado.nodisponible"/>
								</a>
							</c:otherwise>
						</c:choose>
					</display:column>
				</c:if>
			</display:table>

</DIV> <%--bloque --%>

</div> <%--cuerpo_izda --%>
</div> <%--cuerpo_drcha --%>


<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>


</html:form>