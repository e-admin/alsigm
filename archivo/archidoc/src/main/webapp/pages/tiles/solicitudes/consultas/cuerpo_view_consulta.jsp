<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>

<c:url var="calendarImgDir" value="/pages/images/calendario/" />
<jsp:useBean id="calendarImgDir" type="java.lang.String" />
<archigest:calendar-config imgDir="<%=calendarImgDir%>" scriptFile="../scripts/calendar.js"/>

<%@ page import="solicitudes.consultas.ConsultasConstants" %>

<c:set var="consulta" value="${sessionScope[appConstants.consultas.CONSULTA_KEY]}"/>

<SCRIPT>

	function popupUDocsRel(idudoc) {
		popup('<c:url value="/action/gestionDetallesConsultas"/>?method=verudocsRelacionadas&idudoc=' + idudoc, "_blank");
	}

	function refrescar(idconsulta) {
		<c:url var="refrescarURL" value="/action/gestionConsultas">
			<c:param name="method" value="verconsulta" />
			<c:param name="idconsulta" value=""/>
		</c:url>
		var direccion = '<c:out value="${refrescarURL}" escapeXml="false"/>'+idconsulta;
		window.location = direccion;
	}

	function enviarConsulta(idconsulta,identificadorConsulta) {
		<c:url var="envioURL" value="/action/gestionConsultas">
					<c:param name="method" value="enviardesdevista" />
					<c:param name="idconsulta" value=""/>
		</c:url>
		window.location = '<c:out value="${envioURL}" escapeXml="false"/>'+idconsulta;
	}

	function autorizardenegarConsulta(idconsulta,identificadorConsulta) {
		<c:url var="envioURL" value="/action/gestionConsultas">
					<c:param name="method" value="autorizardenegardesdevista" />
					<c:param name="idconsulta" value=""/>
		</c:url>

		var fent = document.getElementById("fentrega");
		var fentvalue = "";

		if (fent!=null && fent != "")
			fentvalue = fent.value;

		window.location = '<c:out value="${envioURL}" escapeXml="false"/>'+idconsulta
			+"&fentrega="+fentvalue;
	}

	function comprobarDisponibilidad(idconsulta) {
			<c:url var="envioURL" value="/action/gestionConsultas">
					<c:param name="method" value="comprobardisponibilidad" />
					<c:param name="idConsulta" value=""/>
			</c:url>
			window.location = '<c:out value="${envioURL}" escapeXml="false"/>'+idconsulta;
	}

	function entregarConsulta(idconsulta,identificadorConsulta) {
			<c:url var="envioURL" value="/action/gestionConsultas">
					<c:param name="method" value="entregardesdevista" />
					<c:param name="idconsulta" value=""/>
			</c:url>
			window.location = '<c:out value="${envioURL}" escapeXml="false"/>'+idconsulta;
	}

	function volverASolicitada(idconsulta,identificadorConsulta) {
		<c:url var="envioURL" value="/action/gestionConsultas">
				<c:param name="method" value="volverASolicitada" />
				<c:param name="idconsulta" value=""/>
		</c:url>
		window.location = '<c:out value="${envioURL}" escapeXml="false"/>'+idconsulta;
}


	//function imprimirEntrada(idconsulta,identificadorConsulta) {
	function imprimirEntrada(idconsulta) {
			<c:url var="envioURL" value="/action/gestionConsultas">
					<c:param name="method" value="imprimirEntrada" />
					<c:param name="idconsulta" value=""/>
			</c:url>
			window.location = '<c:out value="${envioURL}" escapeXml="false"/>'+idconsulta;

	}

	function entregarReservaConsulta(idconsulta,identificadorConsulta) {
			<c:url var="envioURL" value="/action/gestionConsultas">
					<c:param name="method" value="solicitardesdereservaconsulta" />
					<c:param name="idconsulta" value=""/>
			</c:url>
			window.location = '<c:out value="${envioURL}" escapeXml="false"/>'+idconsulta;
	}

	function eliminar(idconsulta) {
		if (confirm("<bean:message key="archigest.archivo.consultas.eliminacion.confirm"/>")) {
			<c:url var="eliminacionURL" value="/action/gestionConsultas">
					<c:param name="method" value="eliminarConsulta" />
					<c:param name="id" value=""/>
			</c:url>
			window.location = '<c:out value="${eliminacionURL}" escapeXml="false"/>'+idconsulta;
		}
	}

		function eliminarSelectedItems(selectionFormName) {
			var nSelected=FormsToolKit.getNumSelectedChecked(document.forms[0],"detallesseleccionados");
			if(nSelected>=1){
				if (confirm("<bean:message key='archigest.archivo.consultas.eliminacion.msg'/>")) {
					var selectionForm = document.forms[selectionFormName];
					if (selectionForm)
						selectionForm.submit();
				}
			}
			else{
				alert("<bean:message key='archigest.archivo.prestamos.SelAlMenosUnaUDoc'/>");
			}
		}

		function autorizarDetallesConsultas(){
			var nSelected=FormsToolKit.getNumSelectedChecked(document.forms[0],"detallesseleccionados");
			if(nSelected>=1){

					document.forms[0].method.value="autorizardetallesconsultas";
					document.forms[0].submit();

			}else{
					alert("<bean:message key='archigest.archivo.prestamos.SelAlMenosUnaUDoc'/>");
			}
		}

		function devolverDetallesConsultas(){
			var nSelected=FormsToolKit.getNumSelectedChecked(document.forms[0],"detallesseleccionados");
			if(nSelected>=1){
					document.forms[0].method.value="devolverdetallesconsultas";
					document.forms[0].submit();

			}else{
					alert("<bean:message key='archigest.archivo.prestamos.SelAlMenosUnaUDoc'/>");
			}
		}

		function ocultarMotivo(){
			document.getElementById("motivo").style.display = 'none';
		}
		function denegarDetallesConsultas(){
			var nSelected=FormsToolKit.getNumSelectedChecked(document.forms[0],"detallesseleccionados");
			if(nSelected>=1){
			document.getElementById("motivo").style.display = 'block';
			}else{
					alert("<bean:message key='archigest.archivo.prestamos.SelAlMenosUnaUDoc'/>");
			}

		}
		function denegarDetallesConsultas2(){
			var nSelected=FormsToolKit.getNumSelectedChecked(document.forms[0],"detallesseleccionados");
			if(nSelected>=1){
				document.forms[0].method.value="denegardetallesconsultas";
				document.forms[0].submit();
			}else{
					alert("<bean:message key='archigest.archivo.prestamos.SelAlMenosUnaUDoc'/>");
			}
		}


	//Funciones para edicion de campos en columnas

	function modificarCampo(nombre,pos){
		var divLabelCampo="divLabel"+nombre+pos;
		var divBotonCampo="divBoton"+nombre+pos;
		var textBoxCampo="textBox"+nombre+pos;
		var divAceptarCancelarCampo="divAceptarCancelar"+nombre+pos;
		//al pulsar el boton de modificar, hay que salir del modo edicion en el resto de
		//campos en esa columna.
		for(i=1;;i++){
			divAceptarCancelarCampo="divAceptarCancelar"+nombre+i;
			divBotonCampo="divBoton"+nombre+i;
			divLabelCampo="divLabel"+nombre+i;
			elemento=document.getElementById(divAceptarCancelarCampo);

			if(elemento!=null){
				elemento.className="hidden";
				document.getElementById(divLabelCampo).className="visible";
				if(document.getElementById(divBotonCampo))
					document.getElementById(divBotonCampo).className="visible";
			}
			else{
				break;
			}
		}

		divAceptarCancelarCampo="divAceptarCancelar"+nombre+pos;
		var divBotonCampo="divBoton"+nombre+pos;
		divLabelCampo="divLabel"+nombre+pos;

		document.getElementById(divAceptarCancelarCampo).className="visible";
		if(document.getElementById(divBotonCampo))
			document.getElementById(divBotonCampo).className="hidden";
		document.getElementById(divLabelCampo).className="hidden";

		document.getElementById("valor"+nombre).value=textBoxCampo.value;
	}

	function cancelarModificacionCampo(nombre,pos){
		var divAceptarCancelarCampo="divAceptarCancelar"+nombre+pos;
		var divBotonCampo="divBoton"+nombre+pos;
		var divLabelCampo="divLabel"+nombre+pos;
		document.getElementById(divAceptarCancelarCampo).className="hidden";
		document.getElementById(divLabelCampo).className="visible";
		if(document.getElementById(divBotonCampo))
			document.getElementById(divBotonCampo).className="visible";
	}

	function aceptarModificacionCampoXml(nombre,pos){
		var textBoxCampo="textBox"+nombre+pos;

		if(document.getElementById(textBoxCampo).value==document.getElementById("valor"+nombre).value){
			cancelarModificacionCampo(nombre,pos);
		}
		else{
			<c:url var="URL" value="/action/gestionConsultas">
			  	<c:param name="method" value="actualizarCampos"/>
			</c:url>
			window.location = '<c:out value="${URL}"/>'+
				'&position='+pos+'&'+'valor'+nombre+'='+document.getElementById(textBoxCampo).value;
		}
	}

	function aceptarModificacionCampo(nombre,pos){
		var textBoxCampo="textBox"+nombre+pos;
		var method = "actualizar"+nombre;

		if(document.getElementById(textBoxCampo).value==document.getElementById("valor"+nombre).value){
			cancelarModificacionCampo(nombre,pos);
		}
		else{
			<c:url var="URL" value="/action/gestionConsultas"></c:url>
			window.location = 	'<c:out value="${URL}"/>'+
								'?method='+method+
								'&position='+pos+
								'&valor'+nombre+'='+document.getElementById(textBoxCampo).value;
		}
	}

</SCRIPT>
<input type="hidden" id="valorObservaciones" name="valorObservaciones" />
<input type="hidden" id="valorNumCopiasSimples" name="valorNumCopiasSimples" />
<input type="hidden" id="valorNumCopiasCertificadas" name="valorNumCopiasCertificadas" />
<input type="hidden" id="valorCampoExpedienteFS" name="valorCampoExpedienteFS" />
<input type="hidden" id="position" name="position" />
<c:set var="VER_BOTON_SELECCIONAR" value="false"/>


<html:form action="/gestionDetallesConsultas">
<div id="contenedor_ficha">
<div class="ficha">

<h1><span>
<div class="w100">
	<TABLE class="w98" cellpadding="0" cellspacing="0">
	<TR>
	    <TD class="etiquetaAzul12Bold" height="25px">
			<bean:message key="archigest.archivo.consultas.datosconsultas"/>
	    </TD>
	    <TD align="right">
			<TABLE cellpadding="0" cellspacing="0">
			 <TR>

			<%--boton comprobar disponibilidad--%>
				<c:if test="${requestScope[appConstants.consultas.VER_BOTON_VER_DISPONIBILIDAD]}">
					<c:set var="llamadaEnviar">javascript:comprobarDisponibilidad('<c:out value="${consulta.id}" />','<c:out value="${consulta.codigoTransferencia}"/>')</c:set>
				    <TD>
				   		<a class=etiquetaAzul12Bold href="<c:out value="${llamadaEnviar}" escapeXml="false"/>">
							<html:img
								page="/pages/images/disponibilidad.gif" border="0"
								altKey="archigest.archivo.solicitudes.disponibilidad"
								titleKey="archigest.archivo.solicitudes.disponibilidad"
								styleClass="imgTextMiddle" />
				   		 	&nbsp;<bean:message key="archigest.archivo.solicitudes.disponibilidad"/>
				   		</a>
				   </TD>
		       	   <TD width="10">&nbsp;</TD>
				</c:if>
			<%--boton enviar a solicitar--%>

				<c:if test="${requestScope[appConstants.consultas.VER_BOTON_ENVIAR_SOLICITAR]}">
					<c:set var="llamadaEnviar">javascript:enviarConsulta('<c:out value="${consulta.id}" />','<c:out value="${consulta.codigoTransferencia}"/>')</c:set>
					<TD>
				   		<a class="etiquetaAzul12Bold" href="<c:out value="${llamadaEnviar}" escapeXml="false"/>">
							<html:img
								page="/pages/images/enviar.gif" border="0"
								altKey="archigest.archivo.enviar"
								titleKey="archigest.archivo.enviar"
								styleClass="imgTextMiddle" />
				   		 	&nbsp;<bean:message key="archigest.archivo.enviar"/>
				   		</a>
					</TD>
			       	<TD width="10">&nbsp;</TD>
				</c:if>

			<%--boton enviar a denegar o autorizar--%>
				<c:if test="${requestScope[appConstants.consultas.VER_BOTON_ENVIAR_DENEGAR_AUTORIZAR]}">
				<c:set var="llamadaEnviar">javascript:autorizardenegarConsulta('<c:out value="${consulta.id}" />','<c:out value="${consulta.codigoTransferencia}"/>')</c:set>
			    	<TD>
			   		<a class=etiquetaAzul12Bold href="<c:out value="${llamadaEnviar}" escapeXml="false"/>">
						<html:img
							page="/pages/images/finCorrecion.gif" border="0"
							altKey="archigest.archivo.solicitudes.finAutorizacion"
							titleKey="archigest.archivo.solicitudes.finAutorizacion"
							styleClass="imgTextMiddle" />
			   		 	&nbsp;<bean:message key="archigest.archivo.solicitudes.finAutorizacion"/>
			   		</a>
				   	</TD>
		       	   	<TD width="10">&nbsp;</TD>
				</c:if>
			<%--boton imprimir salida--%>
				<c:if test="${requestScope[appConstants.consultas.VER_BOTON_IMPRIMIR_SALIDA]}">
					<c:url var="paleletasURL" value="/action/papeletasConsulta">
						<c:param name="id" value="${consulta.id}" />
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
				<c:if test="${requestScope[appConstants.consultas.VER_BOTON_IMPRIMIR_ENTRADA]}">
					<c:set var="llamadaEnviar">javascript:imprimirEntrada('<c:out value="${consulta.id}" />')</c:set>
				    <TD>
				   		<a class=etiquetaAzul12Bold href="<c:out value="${llamadaEnviar}" escapeXml="false"/>">
							<html:img
								page="/pages/images/print.gif" border="0"
								altKey="archigest.archivo.solicitudes.justificanteDevolucion"
								titleKey="archigest.archivo.solicitudes.justificanteDevolucion"
								styleClass="imgTextMiddle" />
				   		 	&nbsp;<bean:message key="archigest.archivo.solicitudes.justificanteDevolucion"/>
				   		</a>

				   </TD>
		       	   <TD width="10">&nbsp;</TD>
		       	   <td nowrap>
					   <c:url var="listadoDevolucionesURL" value="/action/listadoDevolucionesSolicitud">
					   	<c:param name="idConsulta" value="${consulta.id}"/>
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

			<%--boton entregar--%>
				<c:if test="${requestScope[appConstants.consultas.VER_BOTON_ENTREGAR]}">
					<c:set var="llamadaEnviar">javascript:entregarConsulta('<c:out value="${consulta.id}" />','<c:out value="${consulta.codigoTransferencia}"/>')</c:set>
			    	<TD>
			   		<a class=etiquetaAzul12Bold href="<c:out value="${llamadaEnviar}" escapeXml="false"/>">
						<html:img
							page="/pages/images/enviar.gif" border="0"
							altKey="archigest.archivo.entregar"
							titleKey="archigest.archivo.entregar"
							styleClass="imgTextMiddle" />
			   		 	&nbsp;<bean:message key="archigest.archivo.entregar"/>
			   		</a>
				   	</TD>
			       	<TD width="10">&nbsp;</TD>

				</c:if>

				<c:if test="${requestScope[appConstants.consultas.VER_BOTON_VOLVER_A_SOLICITADA]}">
					<c:set var="llamadaEnviar">javascript:volverASolicitada('<c:out value="${consulta.id}" />','<c:out value="${consulta.codigoTransferencia}"/>')</c:set>
					<TD>
					<a class=etiquetaAzul12Bold href="<c:out value="${llamadaEnviar}" escapeXml="false"/>">
						<html:img
							page="/pages/images/enviar.gif" border="0"
							altKey="archidoc.archivo.volver.a.solicitar"
							titleKey="archidoc.archivo.volver.a.solicitar"
							styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archidoc.archivo.volver.a.solicitar"/>
					</a>
					</TD>
					<TD width="10">&nbsp;</TD>

				</c:if>

			<%--boton entrega de reserva--%>

				<c:if test="${requestScope[appConstants.consultas.VER_BOTON_SOLICITAR_RESERVA]}">
					<c:set var="llamadaEnviar">javascript:entregarReservaConsulta('<c:out value="${consulta.id}" />','<c:out value="${consulta.codigoTransferencia}"/>')</c:set>
			    	<TD>
			   		<a class=etiquetaAzul12Bold href="<c:out value="${llamadaEnviar}" escapeXml="false"/>">
						<html:img
							page="/pages/images/enviar.gif" border="0"
							altKey="archigest.archivo.solicitarEntrega"
							titleKey="archigest.archivo.solicitarEntrega"
							styleClass="imgTextMiddle" />
			   		 	&nbsp;<bean:message key="archigest.archivo.solicitarEntrega"/>
			   		</a>
				   	</TD>
			       	<TD width="10">&nbsp;</TD>
				</c:if>

				<%--boton eliminar --%>
				<c:if test="${requestScope[appConstants.consultas.VER_BOTON_ELIMINAR]}">
					<c:set var="llamadaEliminar">javascript:eliminar('<c:out value="${consulta.id}" />')</c:set>
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
					<c:url var="volver2URL" value="/action/gestionConsultas">
					<c:param name="method" value="goBack" />
					</c:url>
			   		<a class="etiquetaAzul12Bold" href="javascript:window.location='<c:out value="${volver2URL}" escapeXml="false"/>'">
						<html:img
							page="/pages/images/close.gif" border="0"
							altKey="archigest.archivo.cerrar"
							titleKey="archigest.archivo.cerrar"
							styleClass="imgTextMiddle" />
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
		<tiles:insert page="/pages/tiles/PADockableBlockLayout.jsp">
			<tiles:put name="blockName" direct="true">consultaInfo</tiles:put>
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.consultas.identificacion"/></tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<TABLE cellpadding=0 cellspacing=0>
				  <TR>
					<c:if test="${requestScope[appConstants.consultas.VER_BOTON_EDITAR]}">
						<TD>
							<c:url var="editarURL" value="/action/gestionConsultas">
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
				<tiles:insert page="/pages/tiles/solicitudes/consultas/cuerpo_cabeceracte_consulta.jsp" />
			</tiles:put>
			<tiles:put name="dockableContent" direct="true">
				<tiles:insert page="/pages/tiles/solicitudes/consultas/datos_consulta.jsp" flush="true"/>
			</tiles:put>
		</tiles:insert>

	<div class="separador8">&nbsp;</div>

	<div class="cabecero_bloque"> <%--cabecero segundo bloque de datos --%>

		<c:if test="${requestScope[appConstants.consultas.VER_BOTON_ELIMINAR_DETALLE]}">
			<c:set var="VER_BOTON_SELECCIONAR" value="true"/>
		</c:if>
		<c:if test="${requestScope[appConstants.consultas.VER_BOTON_AUTORIZAR_DETALLE]}">
			<c:set var="VER_BOTON_SELECCIONAR" value="true"/>
		</c:if>
		<c:if test="${requestScope[appConstants.consultas.VER_BOTON_DENEGAR_DETALLE]}">
			<c:set var="VER_BOTON_SELECCIONAR" value="true"/>
		</c:if>
		<c:if test="${requestScope[appConstants.consultas.VER_BOTON_DEVOLVER]}">
			<c:set var="VER_BOTON_SELECCIONAR" value="true"/>
		</c:if>
		<TABLE class="w98m1" cellpadding=0 cellspacing=0>
	  	<TBODY>
	  		<TR>
	   			<TD class="etiquetaAzul12Bold" width="25%">
					<bean:message key="archigest.archivo.consultas.contenido"/>
				</TD>
	   			<TD width="75%" align="right">
					<TABLE cellpadding=0 cellspacing=0>
		  			<TR>
					<c:if test="${requestScope[appConstants.consultas.VER_BOTON_ANADIR_DETALLE]}">
		    			<TD>
							<c:url var="addDetalleURL" value="/action/gestionDetallesConsultas?method=nuevoDetalle" />
							<a href="<c:out value="${addDetalleURL}" escapeXml="false"/>" class="etiquetaAzul12Normal">
								<html:img titleKey="archigest.archivo.anadir" altKey="archigest.archivo.anadir" page="/pages/images/addDoc.gif" styleClass="imgTextMiddle"/>
								&nbsp;<bean:message key="archigest.archivo.consultas.anadirDetalle"/>
							</a>
	    				</TD>
			   			<TD width="10">&nbsp;</TD>
	    			</c:if>
		   			<c:if test="${requestScope[appConstants.consultas.VER_BOTON_ELIMINAR_DETALLE]}">
						<TD>
							<bean:struts id="mappingGestionUdocs" mapping="/gestionDetallesConsultas" />
							<a class="etiquetaAzul12Normal" href="javascript:eliminarSelectedItems('<c:out value="${mappingGestionUdocs.name}" />')">
								<html:img titleKey="archigest.archivo.eliminar" altKey="archigest.archivo.eliminar" page="/pages/images/delDoc.gif" styleClass="imgTextMiddle"/>
								&nbsp;<bean:message key="archigest.archivo.consultas.eliminarDetalle"/>
							</a>
						</TD>
	    			</c:if>
		    		<%--botones autorizar y denegar --%>
					<c:if test="${requestScope[appConstants.consultas.VER_BOTON_AUTORIZAR_DETALLE]}">
						<TD>
					   		<c:set var="llamadaAutorizarDetallesConsultas">javascript:autorizarDetallesConsultas()</c:set>
							<a class="etiquetaAzul12Bold" href='<c:out value="${llamadaAutorizarDetallesConsultas}" escapeXml="false"/>' >
								<html:img page="/pages/images/aceptar.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
								&nbsp;<bean:message key="archigest.archivo.autorizar"/>
							</a>
						</TD>
			        	<TD width="10">&nbsp;</TD>
					</c:if>
					<%--BOTON DENEGAR CON PROMPT PARA EL MOTIVO --%>
			        <c:if test="${requestScope[appConstants.consultas.VER_BOTON_DENEGAR_DETALLE]}">
						<TD>
							<a class="etiquetaAzul12Bold" href="javascript:denegarDetallesConsultas();" >
							<html:img page="/pages/images/rechazar.gif" border="0" altKey="archigest.archivo.rechazar" titleKey="archigest.archivo.rechazar" styleClass="imgTextMiddle" />
							&nbsp;<bean:message key="archigest.archivo.denegar"/>
							</a>
						</TD>
					</c:if>
					<%--boton devolver --%>
					<c:if test="${requestScope[appConstants.consultas.VER_BOTON_DEVOLVER]}">
						<TD>
					   		<c:set var="llamadaDevolverDetallesConsultas">javascript:devolverDetallesConsultas()</c:set>
							<a class="etiquetaAzul12Bold" href='<c:out value="${llamadaDevolverDetallesConsultas}" escapeXml="false"/>' >
							<html:img page="/pages/images/aceptar.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
							&nbsp;<bean:message key="archigest.archivo.devolver"/>
							</a>
						</TD>
					</c:if>
		        	<TD width="10">&nbsp;</TD>
		  			</TR>
					</TABLE>
				</TD>
	  		</TR>
	  	</TBODY>
	  	</TABLE>
	</div> <%--cabecero bloque --%>

	<DIV class="bloque"> <%--segundo bloque de datos --%>

	<input type="hidden" name="method" value="eliminarDetalles" />
	<c:set var="idConsulta">
		<c:out value ="${consulta.id}"/>
	</c:set>
	<input type="hidden" name="idsolicitud" value="<c:out value="${idConsulta}"/>" />

	<c:if test="${VER_BOTON_SELECCIONAR}">
		<div class="separador1">&nbsp;</div>
		<div class="w100">
		<TABLE class="w98" cellpadding="0" cellspacing="0">
		  <TR>
		    <TD width="100%" align="right">
				<TABLE cellpadding="0" cellspacing="0">
				  <TR>
					<TD>
						<a class="etiquetaAzul12Normal" href="javascript:seleccionarCheckboxSet(document.forms[0].detallesseleccionados);" >
							<html:img page="/pages/images/checked.gif" border="0" altKey="archigest.archivo.selTodos" titleKey="archigest.archivo.selTodos" styleClass="imgTextBottom" />
							<bean:message key="archigest.archivo.selTodos"/>&nbsp;
						</a>
					</TD>
				    <TD width="20">&nbsp;</TD>
				    <TD>
						<a class="etiquetaAzul12Normal" href="javascript:deseleccionarCheckboxSet(document.forms[0].detallesseleccionados);" >
							<html:img page="/pages/images/check.gif" border="0" altKey="archigest.archivo.quitarSel" titleKey="archigest.archivo.quitarSel" styleClass="imgTextBottom" />
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

		<TABLE class="w98" cellpadding="4" cellspacing="4" style="border:1px solid #999999;">
			<TR>
				<TD width="100%" style="text-align:right;">
					<a class="etiquetaAzul12Bold" href="javascript:denegarDetallesConsultas2();" >
						<html:img page="/pages/images/Ok_Si.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>&nbsp;&nbsp;&nbsp;
			   		<a class="etiquetaAzul12Bold" href="javascript:ocultarMotivo();">
						<html:img page="/pages/images/Ok_No.gif" border="0" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
			   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
			   		</a>
				</TD>
			</TR>
		  	<TR>
				<TD class="etiquetaAzul12Bold" style="text-align:center;">
					<bean:message key="archigest.archivo.consultas.anadirMotivoRechazo" />&nbsp;&nbsp;
					<select id="motivorechazo" name="idMotivoRechazo">
						<c:forEach items="${sessionScope[appConstants.consultas.LISTA_MOTIVO_RECHAZO_KEY]}" var="motivo" varStatus="status">
							<option  value="<c:out value="${motivo.id}"/>">
								<c:out value="${motivo.motivo}"/>
							</option>
						</c:forEach>
					</select>
				</TD>
			</TR>
		  	<TR><TD height="20px">&nbsp;</TD></TR>
		</TABLE>
		<div class="separador5">&nbsp;</div>
	</div>

	<script>
		ocultarMotivo();
	</script>

	 <c:set var="LISTA_NAME" value="sessionScope.${appConstants.consultas.DETALLE_CONSULTA_KEY}"/>
	 <jsp:useBean id="LISTA_NAME" type="java.lang.String" />

	<display:table name="<%= LISTA_NAME%>"
		id="detalleConsulta"
		style="width:98%;margin-left:auto;margin-right:auto"
		pagesize="10"
		decorator="solicitudes.consultas.decorators.ViewDetalleConsultaDecorator"
		requestURI='<%=request.getContextPath()+"/action/gestionConsultas?method=" + request.getAttribute(ConsultasConstants.METHOD) %>'>
		<display:setProperty name="basic.msg.empty_list">
			<bean:message key="archigest.archivo.consultas.noDetallesConsultas"/>
		</display:setProperty>

		<c:if test="${VER_BOTON_SELECCIONAR}">
			<display:column style="width:23px;" headerClass="minusDocIcon">
				<%--Sacamos el botón de seleccionar si el detalle se puede eliminar, autorizar, denegar
					o si se puede devolver (si no está denegado o ya devuelto) --%>
				<c:if test="${!( (detalleConsulta.estado==4 || detalleConsulta.estado==6) && requestScope[appConstants.consultas.VER_BOTON_DEVOLVER])}">
					<input type="checkbox" name="detallesseleccionados" value="<c:out value="${detalleConsulta.idudoc}|${detalleConsulta.signaturaudoc}" />">
				</c:if>
			</display:column>
		</c:if>

		<display:column titleKey="archigest.archivo.num" sortable="true" headerClass="sortable">
			<c:url var="URL" value="/action/gestionDetallesConsultas">
				<c:param name="method" value="verudoc" />
				<c:param name="ids" value="${detalleConsulta.idsolicitud}|${detalleConsulta.idudoc}|${detalleConsulta.signaturaudoc}"/>
			</c:url>
			<a class="tdlink" href='<c:out value="${URL}" escapeXml="false" />'>
				<fmt:formatNumber value="${detalleConsulta_rowNum}" pattern="000"/>
			</a>
		</display:column>
		<display:column titleKey="archigest.archivo.estado" sortable="true" headerClass="sortable">
			<fmt:message key="archigest.archivo.solicitudes.detalle.estado.${detalleConsulta.estado}" />
			<c:if test="${empty detalleConsulta.idElementoCF}">
				<html:img
					page="/pages/images/deleteDoc.gif"
					altKey="archigest.archivo.solicitudes.detalle.eliminado"
					titleKey="archigest.archivo.solicitudes.detalle.eliminado"
					styleClass="imgTextMiddle" />
			</c:if>
		</display:column>
		<display:column titleKey="archigest.archivo.signatura" sortable="true" headerClass="sortable" >
			<c:out value="${detalleConsulta.signaturaudoc}" />
		</display:column>
		<display:column titleKey="archigest.archivo.solicitudes.expedienteudoc" sortProperty="expedienteudoc" sortable="true" headerClass="sortable" >
			<c:choose>
				<c:when test="${detalleConsulta.subtipoCaja}">
					<html:img page="/pages/images/rango2.gif" width="26" styleClass="imgTextMiddle" />
					<c:if test="${!empty detalleConsulta.nombreRangos}">
						<c:out value="${detalleConsulta.nombreRangos}"/>
					</c:if>
					<div id="divLabelCampoExpedienteFS<c:out value='${detalleConsulta_rowNum}' />"
						>
						<bean:write name="detalleConsulta" property="expedienteudoc"/>
						<c:if test="${requestScope[appConstants.consultas.VER_BOTON_MODIFICAR_COLUMNA_EXPEDIENTEFS]}">
							<a class="etiquetaAzul12Bold" href="javascript:modificarCampo('CampoExpedienteFS',<c:out value='${detalleConsulta_rowNum}' />)">
								<html:img page="/pages/images/editDoc.gif" altKey="archigest.archivo.modificar" titleKey="archigest.archivo.modificar" styleClass="imgTextMiddle" />
							</a>
						</c:if>
					</div>
					<div id="divAceptarCancelarCampoExpedienteFS<c:out value='${detalleConsulta_rowNum}' />" class="hidden">
						<input type="text" id="textBoxCampoExpedienteFS<c:out value='${detalleConsulta_rowNum}' />"
							  class="inputCampo" maxlength="254" value="<bean:write name="detalleConsulta" property="expedienteudoc"/>">
						<a class="etiquetaAzul12Bold" href="javascript:aceptarModificacionCampo('CampoExpedienteFS',<c:out value='${detalleConsulta_rowNum}' />)">
							<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
						</a>
						<a class="etiquetaAzul12Bold" href="javascript:cancelarModificacionCampo('CampoExpedienteFS',<c:out value='${detalleConsulta_rowNum}' />)">
							<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
						</a>
					</div>
				</c:when>
				<c:otherwise>
					<c:out value="${detalleConsulta.expedienteudoc}" />
				</c:otherwise>
			</c:choose>
		</display:column>
		<display:column titleKey="archigest.archivo.asunto" sortable="true" headerClass="sortable" sortProperty="titulo">
			<c:if test="${!empty detalleConsulta.idElementoCF}">
			<security:permissions action="${appConstants.descripcionActions.CONSULTAR_FICHA_ELEMENTO_ACTION}">
				<c:url var="URL" value="/action/isadg">
					<c:param name="method" value="retrieve" />
					<c:param name="id" value="${detalleConsulta.idudoc}" />
				</c:url>
				<a class="tdlink" href='<c:out value="${URL}" escapeXml="false" />'>
			</security:permissions>
			</c:if>
				<bean:write name="detalleConsulta" property="titulo"/>
				<c:if test="${!empty detalleConsulta.idElementoCF}">
					<security:permissions action="${appConstants.descripcionActions.CONSULTAR_FICHA_ELEMENTO_ACTION}">
						</a>
					</security:permissions>
			</c:if>
		</display:column>
		<c:if test="${VER_BOTON_INFORMACION}">
			<display:column titleKey="archigest.archivo.informacion">
				<table><tr><td class="tdDatos">
					<div id="divLabelNumCopiasSimples<c:out value='${detalleConsulta_rowNum}' />" name="divLabelCampoNumCopiasSimples">
						<bean:write name="detalleConsulta" property="numeroCopiasSimples"/>
						&nbsp;<bean:message key="archigest.archivo.consultas.copiasSimples"/>
					</div>
					<div id="divAceptarCancelarNumCopiasSimples<c:out value='${detalleConsulta_rowNum}' />" class="hidden" name="divAceptarCancelarNumCopiasSimples">
						<bean:message key="archigest.archivo.consultas.copiasSimples"/>&nbsp;
						<input type="text" id="textBoxNumCopiasSimples<c:out value='${detalleConsulta_rowNum}' />"
							  class="inputCampo" size="5" maxlength="5" value="<bean:write name="detalleConsulta" property="numeroCopiasSimples"/>">
						<a class="etiquetaAzul12Bold" href="javascript:aceptarModificacionCampoXml('NumCopiasSimples',<c:out value='${detalleConsulta_rowNum}' />)">
							<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
						</a>
						<a class="etiquetaAzul12Bold" href="javascript:cancelarModificacionCampo('NumCopiasSimples',<c:out value='${detalleConsulta_rowNum}' />)">
							<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
						</a>
					</div>
				  </td><td class="tdDatos" style="border:0">
					<div id="divBotonNumCopiasSimples<c:out value='${detalleConsulta_rowNum}' />" name="divBotonCampoNumCopiasSimples">
						<c:if test="${requestScope[appConstants.consultas.VER_BOTONES_MODIFICAR_COLUMNAS]}">
							<a class="etiquetaAzul12Bold" href="javascript:modificarCampo('NumCopiasSimples',<c:out value='${detalleConsulta_rowNum}' />)">
								<html:img page="/pages/images/editDoc.gif" altKey="archigest.archivo.modificar" titleKey="archigest.archivo.modificar" styleClass="imgTextMiddle" />
							</a>
						</c:if>
					</div>
				</td></tr><tr><td class="tdDatos">
					<div id="divLabelNumCopiasCertificadas<c:out value='${detalleConsulta_rowNum}' />" name="divLabelCampoNumCopiasCertificadas">
						<bean:write name="detalleConsulta" property="numeroCopiasCertificadas"/>
						&nbsp;<bean:message key="archigest.archivo.consultas.copiasCertificadas"/>
					</div>
					<div id="divAceptarCancelarNumCopiasCertificadas<c:out value='${detalleConsulta_rowNum}'/>" class="hidden" name="divAceptarCancelarNumCopiasCertificadas">
						<bean:message key="archigest.archivo.consultas.copiasCertificadas"/>&nbsp;
						<input type="text" id="textBoxNumCopiasCertificadas<c:out value='${detalleConsulta_rowNum}' />"
							  class="inputCampo" size="5" maxlength="5" value="<bean:write name="detalleConsulta" property="numeroCopiasCertificadas"/>">
						<a class="etiquetaAzul12Bold" href="javascript:aceptarModificacionCampoXml('NumCopiasCertificadas',<c:out value='${detalleConsulta_rowNum}' />)">
							<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
						</a>
						<a class="etiquetaAzul12Bold" href="javascript:cancelarModificacionCampo('NumCopiasCertificadas',<c:out value='${detalleConsulta_rowNum}' />)">
							<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
						</a>
					</div>
				  </td><td class="tdDatos" style="border:0">
				  	<div id="divBotonNumCopiasCertificadas<c:out value='${detalleConsulta_rowNum}' />" name="divBotonCampoNumCopiasCertificadas">
						<c:if test="${requestScope[appConstants.consultas.VER_BOTONES_MODIFICAR_COLUMNAS]}">
							<a class="etiquetaAzul12Bold" href="javascript:modificarCampo('NumCopiasCertificadas',<c:out value='${detalleConsulta_rowNum}' />)">
								<html:img page="/pages/images/editDoc.gif" altKey="archigest.archivo.modificar" titleKey="archigest.archivo.modificar" styleClass="imgTextMiddle" />
							</a>
						</c:if>
					</div>

	        	</td></tr></table>
			</display:column>
		</c:if>
		<display:column titleKey="archigest.archivo.solicitudes.observaciones">
			<div id="divLabelObservaciones<c:out value='${detalleConsulta_rowNum}' />" name="divLabelObservaciones">
				<bean:write name="detalleConsulta" property="observaciones"/>
				<c:if test="${requestScope[appConstants.consultas.VER_BOTON_MODIFICAR_COLUMNA_OBSERVACIONES]}">
					<a class="etiquetaAzul12Bold" href="javascript:modificarCampo('Observaciones',<c:out value='${detalleConsulta_rowNum}' />)">
						<html:img page="/pages/images/editDoc.gif" altKey="archigest.archivo.modificar" titleKey="archigest.archivo.modificar" styleClass="imgTextMiddle" />
					</a>
				</c:if>
			</div>
			<div id="divAceptarCancelarObservaciones<c:out value='${detalleConsulta_rowNum}' />" class="hidden" name="divAceptarCancelarObservaciones">
				<input type="text" id="textBoxObservaciones<c:out value='${detalleConsulta_rowNum}' />"
					  class="inputCampo" maxlength="254" value="<bean:write name="detalleConsulta" property="observaciones"/>">
				<a class="etiquetaAzul12Bold" href="javascript:aceptarModificacionCampoXml('Observaciones',<c:out value='${detalleConsulta_rowNum}' />)">
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
				</a>
				<a class="etiquetaAzul12Bold" href="javascript:cancelarModificacionCampo('Observaciones',<c:out value='${detalleConsulta_rowNum}' />)">
					<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
				</a>
			</div>
		</display:column>
		<c:if test="${consulta.estado == appConstants.consultas.ESTADO_CONSULTA_ABIERTA}">
			<display:column titleKey="archigest.archivo.solicitudes.udocsRelacionadas" style="width:40px;">
				<c:if test="${detalleConsulta.tieneUDocsRelacionadas}">
					<a class="tdlink" href="javascript:popupUDocsRel('<c:out value='${detalleConsulta.idudoc}'/>');">
						<html:img page="/pages/images/buscar.gif" border="0" altKey="archigest.archivo.verUDocsRel" titleKey="archigest.archivo.verUDocsRel" styleClass="imgTextBottom" />
					</a>
				</c:if>
			</display:column>
		</c:if>
		<c:if test="${VER_COLUMNA_DISPONIBILIDAD}">
			<display:column titleKey="archigest.archivo.solicitudes.disponibleCkeck">
				<c:choose>
					<c:when test="${detalleConsulta.disponibilidad}">
						<c:if test="${detalleConsulta.estadoDisponibilidad == appConstants.solicitudes.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE_PARCIAL}">
							<html:img page="/pages/images/right.gif"
								altKey="archigest.archivo.solicitudes.detalle.estado.disponibleParcial"
								titleKey="archigest.archivo.solicitudes.detalle.estado.disponibleParcial"
								styleClass="imgTextMiddle" />
							<c:url var="detalleURL" value="/action/gestionDetallesConsultas">
								<c:param name="method" value="verudoc" />
								<c:param name="ids" value="${detalleConsulta.idsolicitud}|${detalleConsulta.idudoc}|${detalleConsulta.signaturaudoc}" />
							</c:url>
							<a class="tdlink" href="<c:out value="${detalleURL}" escapeXml="false"/>">
								<bean:message key="archigest.archivo.solicitudes.detalle.estado.disponibleParcial"/>
							</a>
						</c:if>
						<c:if test="${detalleConsulta.estadoDisponibilidad == appConstants.solicitudes.ESTADO_DISPONIBILIDAD_DETALLE_DISPONIBLE}">
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
						<c:url var="detalleURL" value="/action/gestionDetallesConsultas">
							<c:param name="method" value="verudoc" />
							<c:param name="ids" value="${detalleConsulta.idsolicitud}|${detalleConsulta.idudoc}|${detalleConsulta.signaturaudoc}" />
						</c:url>
						<a class="tdlink" href="<c:out value="${detalleURL}" escapeXml="false"/>">
							<bean:message key="archigest.archivo.solicitudes.detalle.estado.nodisponible"/>
						</a>
					</c:otherwise>
				</c:choose>
			</display:column>
		</c:if>
	</display:table>
 </div> <%--bloque --%>


</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

 </div> <%--ficha --%>
</div> <%--contenedor_ficha --%>

</html:form>