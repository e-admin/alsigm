<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-logic-el.tld" prefix="logic-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<%@ page import="solicitudes.prestamos.actions.GestionPrestamosAction" %>
<%@ page import="solicitudes.prestamos.actions.GestionDetallesPrestamoAction" %>
<%@ page import="solicitudes.prestamos.forms.DetallePrestamoForm" %>
<%@ page import="solicitudes.prestamos.forms.PrestamoForm" %>
<%@ page import="fondos.vos.FondoVO" %>
<%@ page import="fondos.model.Fondo" %>
<%@ page import="common.Constants" %>

<c:set var="bPrestamo" value="${sessionScope[appConstants.prestamos.PRESTAMO_KEY]}"/>
<c:set var="expediente" value="${sessionScope[appConstants.prestamos.EXPEDIENTE_BUSQUEDA_UDOCS]}"/>
<c:set var="signatura" value="${sessionScope[appConstants.prestamos.SIGNATURA_BUSQUEDA_UDOCS]}"/>
<c:set var="fondo" value="${sessionScope[appConstants.prestamos.FONDO_BUSQUEDA_UDOCS]}"/>
<c:set var="llamadaBuscar">
	javascript:buscar();
</c:set>
<c:set var="llamadaEliminar">
	javascript:eliminarUDocs()
</c:set>
<c:set var="llamadaAnadir">
	javascript:anadirUDocs()
</c:set>
<c:set var="llamadaAnadirSignatura">
	javascript:anadirUDoc(document.forms[0].signatura_buscar.value)
</c:set>

<div id="contenedor_ficha">

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

		function anadirUDoc(signatura){
			if ( signatura!=null && signatura!='') {
					<c:url var="envioURL" value="/action/devolverDetalles">
						<c:param name="method" value="anadirDetalles" />
						<c:param name="signatura" value=""/>
					</c:url>
					window.location = '<c:out value="${envioURL}" escapeXml="false"/>'+signatura;
			} else {
				alert("<bean:message key='archigest.archivo.prestamos.introducirSignatura'/>");
			}
		}

		function anadirUDocs(){
			if ( document.forms[0].elements.item('detallesseleccionados')!=null ) {
				var nSelected=FormsToolKit.getNumSelectedChecked(document.forms[0],"detallesseleccionados");
				if(nSelected>=1){
						document.forms[0].method.value="anadirDetalles";
						document.forms[0].submit();
				} else {
					alert("<bean:message key='archigest.archivo.prestamos.SelAlMenosUnaUDoc'/>");
				}
			} else {
				alert("<bean:message key='archigest.archivo.consultas.noUDocsSel'/>");
			}
		}

		function eliminarUDocs(){
			if ( document.forms[0].elements.item('detallesseleccionadosdevolver')!=null ) {
				var nSelected=FormsToolKit.getNumSelectedChecked(document.forms[0],"detallesseleccionadosdevolver");
				if(nSelected>=1){
						document.forms[0].method.value="eliminarDetalles";
						document.forms[0].submit();
				}else{
					alert("<bean:message key='archigest.archivo.prestamos.SelAlMenosUnaUDoc'/>");
				}
			} else {
				alert("<bean:message key='archigest.archivo.consultas.noUDocsSel'/>");
			}
		}

		function verPrestamo(idprestamo){
			document.forms[0].method.value="verPrestamo";
			document.getElementById("idPrestamo").value = idprestamo;
			document.forms[0].submit();
		}

		function verSolicitud(idsolicitud, tiposolicitud){
			document.forms[0].method.value="verSolicitud";
			document.getElementById("idsolicitud").value = idsolicitud;
			document.getElementById("tiposolicitud").value = tiposolicitud;
			document.forms[0].submit();
		}

		function imprimirEntrada() {
			var URLEnvio = <%="\""+request.getContextPath() + "/action/devolverDetalles?method=imprimirentrada\""%>;
			window.location=URLEnvio;
		}

		function buscar(){
			if (window.top.showWorkingDiv) {
				var title = '<bean:message key="archigest.archivo.buscando.realizandoBusqueda"/>';
				var message = '<bean:message key="archigest.archivo.buscando.msgPrestamos"/>';
				var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
				window.top.showWorkingDiv(title, message, message2);
			}
			buscarudocnumexp(document.forms[0].expediente_buscar.value,'',document.forms[0].signatura_buscar.value);
		}
</script>


<html:form action="/devolverDetalles">
<input type="hidden" name="method" value="devolverDetalles">
<html:hidden property="idPrestamo" styleId="idPrestamo"/>
<html:hidden property="idsolicitud" styleId="idsolicitud"/>
<html:hidden property="tiposolicitud" styleId="tiposolicitud"/>

<div class="ficha">

<h1><span>
	<div class="w100">
	<TABLE class="w98" cellpadding=0 cellspacing=0>
	  <TR>
	    <TD class="etiquetaAzul12Bold" height="25px">
			<bean:message key="archigest.archivo.prestamos.devolucionudocs" />
		</TD>
	    <TD align="right">
			<TABLE cellpadding=0 cellspacing=0>
			 <TR>
			 	<c:if test="${!empty sessionScope[appConstants.prestamos.LISTA_UDOC_ADEVOLVER]}">
				<TD>
					<a class="etiquetaAzul12Bold" href="javascript:document.forms[0].submit()" >
						<html:img page="/pages/images/devolver.gif" border="0"
						altKey="archigest.archivo.devolver"
						titleKey="archigest.archivo.devolver" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.devolver"/>
					</a>
				</TD>
				</c:if>
				<TD width="10">&nbsp;</TD>
			 	<TD>
			 		<c:url var="informeDevolucionesURL" value="/action/devolverSolicitudes">
			 			<c:param name="method" value="form"/>
			 		</c:url>
			 		<a class="etiquetaAzul12Bold" href="<c:out value="${informeDevolucionesURL}" escapeXml="false"/>">
			 			<html:img
							page="/pages/images/print.gif" border="0"
							altKey="archigest.archivo.solicitudes.informeDevoluciones"
							titleKey="archigest.archivo.solicitudes.informeDevoluciones"
							styleClass="imgTextMiddle" />
				   			&nbsp;<bean:message key="archigest.archivo.solicitudes.informeDevoluciones"/>&nbsp;&nbsp;
					</a>
			   </TD>
			   <TD width="10">&nbsp;</TD>
				<TD>
					<c:url var="cancelURL" value="/action/devolverDetalles">
						<c:param name="method" value="goBack" />
					</c:url>
					<a class=etiquetaAzul12Bold href="<c:out value="${cancelURL}" escapeXml="false"/>">
						<html:img
							page="/pages/images/close.gif"
							altKey="archigest.archivo.cerrar"
							titleKey="archigest.archivo.cerrar" styleClass="imgTextTop" />
						&nbsp;<bean:message key="archigest.archivo.cerrar"/>
					</a>
		   		</TD>
			 </TR>
			</TABLE>
		</td>
		</TR>
	</TABLE>
	</div>
</span></h1>

<div class="cuerpo_drcha">
<div class="cuerpo_izda">

<DIV id="barra_errores"><archivo:errors/></DIV>

<%--Tabla para los dos columnas con display--%>
<TABLE cellpadding="0" cellspacing="0" class="w100">
<tr>
<td valign="top" width="50%">
	<div class="cabecero_bloque_m5" style="width:99%;margin-left:auto;margin-right:auto">
		<TABLE cellpadding=0 cellspacing=0 class="w98">
		<TR>
		    <TD class="etiquetaAzul12Bold" width="1%">
				&nbsp;
			</TD>
		    <TD width="99%" align="right">
				<TABLE cellpadding=0 cellspacing=0>
				  <TR>
				  	<TD id="addxsig">
						<a class="etiquetaAzul12Bold" href="<c:out value="${llamadaAnadirSignatura}" escapeXml="false"/>">
							<html:img
								page="/pages/images/addDoc.gif"
								altKey="archigest.archivo.solicitudes.anadirSignatura"
								titleKey="archigest.archivo.solicitudes.anadirSignatura" styleClass="imgTextMiddle" />
					   			&nbsp;<bean:message key="archigest.archivo.solicitudes.anadirSignatura"/>&nbsp;&nbsp;
						</a>
					</TD>
					<c:if test="${sessionScope[appConstants.prestamos.CHECKEXP]}">
					  	<script>
					  		FormsToolKit.setVisible('addxsig',false);
					  	</script>
				    </c:if>
					<TD>
				   		<a class="etiquetaAzul12Bold" href="<c:out value="${llamadaBuscar}" escapeXml="false"/>">
							<html:img
								page="/pages/images/buscar.gif"
								altKey="archigest.archivo.buscar"
								titleKey="archigest.archivo.buscar" styleClass="imgTextMiddle" />
				   		 	&nbsp;<bean:message key="archigest.archivo.buscar"/>
				   		</a>
					</TD>
			     </TR>
				</TABLE>
			</TD>
		</TR>
		</TABLE>
	</div>


	<div class="bloque_m5" style="width:99%;margin-left:auto;margin-right:auto"> <%--segundo bloque de datos BUSQUEDA--%>
		<script>
		function gestionBusqueda(criterio){
			if (criterio=='1'){
				FormsToolKit.setVisible('idExp',false);
				FormsToolKit.setVisible('idSig',true);
				/*document.forms[0].expediente_buscar.value = '';*/
				FormsToolKit.setVisible('addxsig',true);
			}
			if (criterio=='2'){
				FormsToolKit.setVisible('idExp',true);
				FormsToolKit.setVisible('idSig',false);
				/*document.forms[0].signatura_buscar.value = '';*/
				FormsToolKit.setVisible('addxsig',false);
			}
			FormsToolKit.setVisible('resultados',false);
		}

		function getCheckBoxSelectedValue(){
			var value=document.forms[0].buscarPor[0].checked?document.forms[0].buscarPor[0].value:-1;
			value=document.forms[0].buscarPor[1].checked?document.forms[0].buscarPor[1].value:value;
			return value;
		}

			function buscarudocnumexp(num_exp, fondo, signatura) {
				<c:url var="envioURL" value="/action/devolverDetalles">
					<c:param name="method" value="buscarDetalles" />
					<c:param name="numexp" value=""/>
				</c:url>
				var URLEnvio = '<c:out value="${envioURL}" escapeXml="false"/>' +num_exp
					+ "&fondo="+fondo+"&signatura="+signatura+"&numeroExpediente_like="
					+ document.forms[0].numeroExpediente_like.value+"&signatura_like="+document.forms[0].signatura_like.value
					+ "&buscarPor="+getCheckBoxSelectedValue();
				window.location = URLEnvio;
			}
		</script>

		<table class="formulario">
			<tr>
				<TD class="etiquetaAzul12Bold" width="120px" style="padding-left:10px">
					<bean:message key="archigest.archivo.buscarPor"/>:&nbsp;
				</TD>
				<TD class="etiquetaAzul11Normal">
					<input type="radio" class="radio" name="buscarPor" id="buscarPor" value="1" onClick="gestionBusqueda(this.value);"
						<c:if test="${sessionScope[appConstants.prestamos.CHECKFONDOYSIGNATURA]}">
							checked
						</c:if>
					/>&nbsp;<bean:message key="archigest.archivo.signatura"/>&nbsp;
					<input type="radio" class="radio" name="buscarPor" id="buscarPor" value="2" onClick="gestionBusqueda(this.value);"
						<c:if test="${sessionScope[appConstants.prestamos.CHECKEXP]}">
							checked
						</c:if>
					/>&nbsp;<bean:message key="archigest.archivo.solicitudes.expedienteudoc"/>
				</TD>
				<td width="10"></td>
			</tr>
		</table>

		<c:choose>
			<c:when test="${sessionScope[appConstants.prestamos.CHECKFONDOYSIGNATURA]}">
				<div id="idSig" style="display:block">
			</c:when>
			<c:otherwise>
				<div id="idSig" style="display:none">
			</c:otherwise>
		</c:choose>
					<table class="formulario">
						<tr>
							<td class="tdTitulo" width="100px">
								<bean:message key="archigest.archivo.prestamos.signaturaudoc"/>:
							</td>
							<td class="tdDatos">
								<html:select property="signatura_like">
									<html:option key="archigest.archivo.igual" value="igual"/>
									<html:option key="archigest.archivo.empiezaPor" value="empieza"/>
									<html:option key="archigest.archivo.terminaPor" value="termina"/>
									<html:option key="archigest.archivo.contiene" value="contiene"/>
								</html:select>
								&nbsp;&nbsp;
								<html:text property="signatura_buscar" size="30"/>
							</td>
						</tr>
					</table>
				</div>

		<c:choose>
			<c:when test="${sessionScope[appConstants.prestamos.CHECKEXP]}">
				<div id="idExp" style="display:block">
			</c:when>
			<c:otherwise>
				<div id="idExp" style="display:none">
			</c:otherwise>
		</c:choose>
					<table  class="formulario">
			              <tr>
							<td class="tdTitulo" width="80px">
								<bean:message key="archigest.archivo.solicitudes.nExp"/>:
							</td>
							<td class="tdDatos">
								<html:select property="numeroExpediente_like">
									<html:option key="archigest.archivo.igual" value="igual"/>
									<html:option key="archigest.archivo.empiezaPor" value="empieza"/>
									<html:option key="archigest.archivo.terminaPor" value="termina"/>
									<html:option key="archigest.archivo.contiene" value="contiene"/>
								</html:select>
								&nbsp;&nbsp;
								<html:text property="expediente_buscar" size="30"/>
							</td>
						</tr>
					</table>
				</div>

	<div class="separador5">&nbsp;</div>

	<div id="resultados">
	<%--Si hay udocs las mostramos--%>
	<c:if test="${sessionScope[appConstants.prestamos.MOSTRAR_LISTADO_BUSQUEDA_UDOCS]}">
		<c:set var="LISTA_BUSQUEDA" value="${sessionScope[appConstants.prestamos.LISTADO_BUSQUEDA_UDOCS]}"/>

		<div class="cabecero_bloque_m5" style="width:99%;margin-left:auto;margin-right:auto">
			<TABLE cellpadding=0 cellspacing=0 class="w98">
			<TR>
			    <TD class="etiquetaAzul12Bold" width="60%">
					<bean:message key="archigest.archivo.resultadosBusqueda"/>
				</TD>
				<c:if test="${not empty LISTA_BUSQUEDA}">
				    <TD width="40%" align="right">
						<TABLE cellpadding=0 cellspacing=0>
						  <TR>
							<TD>
								<a class="etiquetaAzul12Bold" href="<c:out value="${llamadaAnadir}" escapeXml="false"/>">
									<html:img
										page="/pages/images/add_sig.gif"
										altKey="archigest.archivo.anadir"
										titleKey="archigest.archivo.anadir" styleClass="imgTextMiddle" />
						   		 	&nbsp;<bean:message key="archigest.archivo.anadir"/>
						   		</a>
							</TD>
					     </TR>
						</TABLE>
					</TD>
				</c:if>
			</TR>
			</TABLE>
		</div>

		<div class="bloque_m5" style="width:99%;margin-left:auto;margin-right:auto"> <%--primer bloque de datos --%>
			<c:set var="LIST_ANIADIR" value="${sessionScope[appConstants.prestamos.LISTADO_BUSQUEDA_UDOCS]}"/>
			<c:if test="${not empty LIST_ANIADIR}">
				<TABLE class="formulario">
				    <TR><TD align="right">
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
									<a class=etiquetaAzul12Normal href="javascript:deseleccionarCheckboxSet(document.forms[0].detallesseleccionados);" >
										<html:img
											page="/pages/images/check.gif" border="0"
											altKey="archigest.archivo.quitarSel"
											titleKey="archigest.archivo.quitarSel" styleClass="imgTextBottom" />
									   	&nbsp;<bean:message key="archigest.archivo.quitarSel"/>
									</a>
							   </TD>
						       <TD width="10">&nbsp;</TD>
					     </TR>
						</TABLE>
					</TD></TR>
				</TABLE>
			</c:if>
			<c:set var="LISTA_NAME" value="sessionScope.${appConstants.prestamos.LISTADO_BUSQUEDA_UDOCS}"/>
			<jsp:useBean id="LISTA_NAME" type="java.lang.String" />

			<div style="width:100%">
				<display:table name="<%=LISTA_NAME%>"
					style="width:98%;margin-left:auto;margin-right:auto"
					id="udocBusqueda"
					decorator="solicitudes.prestamos.decorators.ViewDetalleBusquedaDecorator"
					pagesize="10"
					sort="list"
					excludedParams="method"
					requestURI="../../action/devolverDetalles?method=buscarDetalles"
					export="false">

					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.solicitudes.noPrev"/>
					</display:setProperty>

					<display:column>
						<input type="checkbox" name="detallesseleccionados"
						value='<c:out value="${udocBusqueda.idSolicitud}"/>|<c:out value="${udocBusqueda.idudoc}"/>|<c:out value="${udocBusqueda.signaturaudoc}"/>|<c:out value="${udocBusqueda.tiposolicitud}"/>'/>
					</display:column>
					<display:column titleKey="archigest.archivo.signatura"  sortable="true" headerClass="sortable" property="signaturaudoc" maxLength="15"/>
					<display:column titleKey="archigest.archivo.solicitudes.nExp"  sortable="true" headerClass="sortable" sortProperty="expedienteudoc">
						<c:choose>
							<c:when test="${udocBusqueda.subtipoCaja}">
								<html:img page="/pages/images/rango2.gif" width="25" styleClass="imgTextMiddle" />
								<c:if test="${!empty udocBusqueda.nombreRangos}">
									<c:out value="${udocBusqueda.nombreRangos}"/><br/>
								</c:if>
								<c:out value="${udocBusqueda.expedienteudoc}" />
							</c:when>
							<c:otherwise>
								<c:out value="${udocBusqueda.expedienteudoc}" />
							</c:otherwise>
						</c:choose>
					</display:column>
					<display:column titleKey="archigest.archivo.titulo" sortable="true" headerClass="sortable" property="titulo" maxLength="25"/>
					<display:column title="" sortable="true" headerClass="sortable" property="tipo"/>
					<display:column titleKey="archigest.archivo.solicitud" sortable="true" headerClass="sortable" property="codigoSolicitud"/>
					<display:column titleKey="archigest.archivo.prestamos.solicitante" sortable="true" headerClass="sortable" property="solicitante"/>
				</display:table>
			</div>
		</div> <%--bloque --%>
	</c:if>
	</div>
</td>
<td valign="top">
	<c:set var="LIST_DEVOLVER" value="${sessionScope[appConstants.prestamos.LISTA_UDOC_ADEVOLVER]}"/>
	<div class="cabecero_bloque">
		<TABLE cellpadding=0 cellspacing=0 class="w98">
		<TR>
		    <TD class="etiquetaAzul12Bold" width="50%">
				<bean:message key="archigest.archivo.solicitudes.uDocsSeleccionadas"/>
			</TD>
			<c:if test="${not empty LIST_DEVOLVER}">
			    <TD width="50%" align="right">
					<TABLE cellpadding=0 cellspacing=0>
					  <TR>
						<TD>
							<a class="etiquetaAzul12Bold" href="<c:out value="${llamadaEliminar}" />" >
								<html:img
									page="/pages/images/quitar.gif"
									altKey="archigest.archivo.quitar"
									titleKey="archigest.archivo.quitar" styleClass="imgTextMiddle" />
								&nbsp;<bean:message key="archigest.archivo.quitar"/>
							</a>
						</TD>
				     </TR>
					</TABLE>
				</TD>
			</c:if>
		</TR>
		</TABLE>
	</div>
	<div class="bloque"> <%--bloque de datos de UDocs a entrega--%>
	<c:if test="${not empty LIST_DEVOLVER}">
		<table class="formulario">
			<TR>
			<TD align="right">
				<TABLE cellpadding=0 cellspacing=0>
				<TR>
					<TD>
						<a class="etiquetaAzul12Normal" href="javascript:seleccionarCheckboxSet(document.forms[0].detallesseleccionadosdevolver);" >
						<html:img
							page="/pages/images/checked.gif" border="0"
							altKey="archigest.archivo.selTodos"
							titleKey="archigest.archivo.selTodos" styleClass="imgTextBottom" />
							<bean:message key="archigest.archivo.selTodos"/>&nbsp;
						</a>
					</TD>
				    <TD width="10">&nbsp;</TD>
					<TD>
						<a class=etiquetaAzul12Normal href="javascript:deseleccionarCheckboxSet(document.forms[0].detallesseleccionadosdevolver);" >
							<html:img
								page="/pages/images/check.gif" border="0"
								altKey="archigest.archivo.quitarSel"
								titleKey="archigest.archivo.quitarSel" styleClass="imgTextBottom" />
								&nbsp;<bean:message key="archigest.archivo.quitarSel"/>&nbsp;
						</a>
					</TD>
				</TR>
				</TABLE>
			</TD>
			</TR>
		</table>
	</c:if>

		<div class="separador5">&nbsp;</div>

		<c:set var="LIST_NAME" value="sessionScope.${appConstants.prestamos.LISTA_UDOC_ADEVOLVER}"/>
		<jsp:useBean id="LIST_NAME" type="java.lang.String" />

		<div style="width:99%">
			<display:table name="<%=LIST_NAME%>"
				style="width:98%;margin-left:auto;margin-right:auto"
				id="udocADevolver"
				decorator="solicitudes.prestamos.decorators.ViewDetalleBusquedaDecorator"
				pagesize="10"
				sort="list"
				requestURI='<%=request.getContextPath()+"/action/devolverDetalles"%>'
				export="false">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.prestamos.noudocsaeliminar"/>
					</display:setProperty>

					<display:column>
						<input type="checkbox" name="detallesseleccionadosdevolver"
						value='<c:out value="${udocADevolver.idSolicitud}"/>|<c:out value="${udocADevolver.idudoc}"/>|<c:out value="${udocADevolver.signaturaudoc}"/>|<c:out value="${udocADevolver.tiposolicitud}"/>'/>
					</display:column>
					<display:column titleKey="archigest.archivo.signatura" sortable="true" headerClass="sortable"  property="signaturaudoc" maxLength="15"/>
					<display:column titleKey="archigest.archivo.solicitudes.nExp" sortable="true" headerClass="sortable">
						<c:choose>
							<c:when test="${udocADevolver.subtipoCaja}">
								<html:img page="/pages/images/rango2.gif" width="26" styleClass="imgTextMiddle" />
								<c:if test="${!empty udocADevolver.nombreRangos}">
									&nbsp;<c:out value="${udocADevolver.nombreRangos}"/><br/>
								</c:if>
								<c:out value="${udocADevolver.expedienteudoc}"/>
							</c:when>
							<c:otherwise>
								<c:out value="${udocADevolver.expedienteudoc}"/>
							</c:otherwise>
						</c:choose>
					</display:column>
					<display:column titleKey="archigest.archivo.titulo" sortable="true" headerClass="sortable" property="titulo" maxLength="25"/>
					<display:column title="" sortable="true" headerClass="sortable" property="tipo"/>
					<display:column titleKey="archigest.archivo.solicitud" sortable="true" headerClass="sortable" property="codigoSolicitud"/>
					<display:column titleKey="archigest.archivo.prestamos.solicitante" sortable="true" headerClass="sortable" property="solicitante"/>
					<display:column titleKey="archigest.archivo.solicitudes.revisarDocCab">
						<c:if test="${udocADevolver.tiposolicitud == appConstants.prestamos.TIPO_SOLICITUD_PRESTAMO}">

							<div>
								<html-el:multibox property="udocsarevisardoc"
				                  onclick="javascript:conmutarEstadoDocRev('${udocADevolver_rowNum}', this)"
	               				  value="${udocADevolver.idudoc}|${udocADevolver.signaturaudoc}|${udocADevolver_rowNum}">
				                </html-el:multibox>

	                			<c:set var="divDocRevStatus" value="divDocRevStatus${udocADevolver_rowNum}"/>
	                			<html-el:text property="mapFormValues(${divDocRevStatus})" styleId="${divDocRevStatus}" style="display:none;"/>

				 				<c:set var="displayLinea" value="display:none;"/>
								<logic-el:present name="detallePrestamoForm" property="mapFormValues(divDocRevStatus${udocADevolver_rowNum})">
									<logic-el:match name="detallePrestamoForm" property="mapFormValues(divDocRevStatus${udocADevolver_rowNum})" value="1">
										<c:set var="displayLinea" value="display:block;"/>
									</logic-el:match>
								</logic-el:present>

								<div id="divDocRev<c:out value='${udocADevolver_rowNum}'/>" style="<c:out value="${displayLinea}"/>">
										<bean:message key="archigest.archivo.solicitudes.prestamos.gestorRevDoc"/>:
										<html-el:select property="mapFormValues(idusrgestorDocRev${udocADevolver_rowNum})">
	                     					 <c:forEach items="${udocADevolver.usuariosGestoresRevDocPosibles}" var="usuario" varStatus="status">
	                      					 	<option value="<c:out value="${usuario.id}"/>">
	                         				 		<c:out value="${usuario.nombreCompleto}"/>
	                       						 </option>
	                      					</c:forEach>
										</html-el:select>
										<br>
										<bean:message key="archigest.archivo.solicitudes.observaciones"/>:
										<html-el:textarea property="mapFormValues(observacionesdocrev${udocADevolver_rowNum})"
												onkeypress="maxlength(this,254,false)" onchange="maxlength(this,254,true)"
												style="width:100%;">
										</html-el:textarea>
								</div>
							</c:if>
               			</div>
					</display:column>
			</display:table>
		</div>
	</div>
</td>


</tr>
</table>

</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</html:form>

</div> <%--contenedor_ficha --%>