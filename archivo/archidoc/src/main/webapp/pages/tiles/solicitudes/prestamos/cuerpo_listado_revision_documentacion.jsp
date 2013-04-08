<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>

<c:set var="listaRevisionDocumentacion" value="${sessionScope[appConstants.prestamos.LISTA_REVISION_DOCUMENTACION_KEY]}"/>
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<bean:struts id="mappingRevisionDocumentacion" mapping="/gestionRevDocAction" />

<script>	

	function finalizar() {
		var form = document.forms["<c:out value="${mappingRevisionDocumentacion.name}" />"];
		if (form.idRevDocSeleccionado && elementSelected(form.idRevDocSeleccionado)){
			form.method.value = 'finalizar';
			form.submit();
		} else
			alert("<bean:message key='archigest.archivo.prestamos.selElemento'/>");
	}

	function rechazar() {			
		var form = document.forms['<c:out value="${mappingRevisionDocumentacion.name}" />'];
		if (form.idRevDocSeleccionado && elementSelected(form.idRevDocSeleccionado)) 
			document.getElementById("motivoRechazo").style.display = 'block';
		else
			alert("<bean:message key='archigest.archivo.prestamos.selElemento'/>");
	}		

	function ocultarMotivoRechazo(){
		document.getElementById("motivoRechazo").style.display = 'none';
	}

	function rechazar2() {
		var form = document.forms['<c:out value="${mappingRevisionDocumentacion.name}" />'];
		form.method.value = 'rechazar';
		form.submit();
	}

</script>

<html:form action="/gestionRevDocAction">
<div id="contenedor_ficha">
<input type="hidden" name="method">
<html:hidden property="ocultarMotivo"/>

<div class="ficha">
<h1><span>
<div class="w100">
	<table class="w98" cellpadding=0 cellspacing=0>
		<tr>
		    <td class="etiquetaAzul12Bold" width="60%" height="25px">
				<bean:message key="archigest.archivo.descripcion.DocumentacionUdocsaRevisar"/>
			</td>
		    <td width="70%" align="right">
				<table cellpadding=0 cellspacing=0>
					<tr>					
				   		<td>
							<%--boton Cerrar --%>
							<c:url var="volverURL" value="/action/gestionRevDocAction">
								<c:param name="method" value="goBack" />
							</c:url>				
							<a class=etiquetaAzul12Bold href="<c:out value="${volverURL}" escapeXml="false"/>">
								<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextTop" />
								&nbsp;<bean:message key="archigest.archivo.cerrar"/>
							</a>
				   		</td>
			    	</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
</span></h1>

<div class="cuerpo_drcha">
<div class="cuerpo_izda">

<DIV id="barra_errores">
	<archivo:errors />
</DIV>

<span class="separador1"></span>

<div class="cabecero_bloque">
	<table class="w98m1" cellpadding="0" cellspacing="0" height="100%">
		<tr>
			<td class="etiquetaAzul12Bold">
				<bean:message key="archigest.archivo.unidadesDocumentales" />
			</td>
			<c:if test="${not empty listaRevisionDocumentacion}">
		    	<td align="right">
					<table cellpadding="0" cellspacing="0">
						<tr>
			    			<%--botones Finalizar y Rechazar --%>						    				
							<TD>
								<a class="etiquetaAzul12Bold" href="javascript:rechazar();" >
								<html:img page="/pages/images/rechazar.gif" border="0" altKey="archigest.archivo.rechazar" titleKey="archigest.archivo.rechazar" styleClass="imgTextMiddle" />
								&nbsp;<bean:message key="archigest.archivo.rechazar"/>
								</a>					
							</TD>	
				        	<TD width="10">&nbsp;</TD>
							<TD>									   		
								<a class="etiquetaAzul12Bold" href="javascript:finalizar();" >
									<html:img page="/pages/images/aceptar.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
									&nbsp;<bean:message key="archigest.archivo.finalizar"/>
								</a>
							</TD>				
						</tr>
					</table>
				</td>
			</c:if>
		</tr>
	</table>
</div>

<span class="separador1"></span>

<div class="bloque">

	<div class="w100" id="motivoRechazo">
		<div class="separador5">&nbsp;</div>
		<center>
		<TABLE class="w98" cellpadding="4" cellspacing="4" style="border:1px solid #999999;">			
			<TR>
				<TD width="100%" style="text-align:right;" colspan="2">
					<a class="etiquetaAzul12Bold" href="javascript:rechazar2();" >
						<html:img 
							page="/pages/images/Ok_Si.gif" border="0" 
							altKey="archigest.archivo.aceptar"
							titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>&nbsp;&nbsp;
			   		<a class="etiquetaAzul12Bold" href="javascript:ocultarMotivoRechazo();">
						<html:img 
							page="/pages/images/Ok_No.gif" border="0" 
							altKey="archigest.archivo.cancelar" 
							titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
			   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
			   		</a>&nbsp;
				</TD>
			</TR>
		  	<TR>
				<TD class="etiquetaAzul11Bold" style="text-align:left;vertical-align:top" width="15%">
					<bean:message key="archigest.archivo.solicitudes.motivorechazo"/>:&nbsp;&nbsp;
				</td>
				<td width="80%" class="tdDatos">
					<html:textarea property="motivoRechazo" style="width:100%;" rows="3" onkeypress="maxlength(this,1024,false)" onchange="maxlength(this,1024,true)"/> 									
				</TD>				
			</TR>
		  	<TR><TD height="20px" colspan="2">&nbsp;</TD></TR>
		</TABLE>
		</center>
		<div class="separador5">&nbsp;</div>
	</div> <%--Motivo --%>

	<c:url var="listaRevisionDocumentacionPaginationURI" value="/action/gestionRevDocAction">
		<c:param name="method" value="${param.method}" />
	</c:url>
	<jsp:useBean id="listaRevisionDocumentacionPaginationURI" type="java.lang.String" />

	<display:table name="pageScope.listaRevisionDocumentacion"
		id="udoc" 
		pagesize="10"
		requestURI="<%=listaRevisionDocumentacionPaginationURI%>"
		sort="list"
		export="true"
		style="width:99%;margin-left:auto;margin-right:auto;">

		<display:setProperty name="basic.msg.empty_list">
			<bean:message key="archigest.archivo.prestamos.noDocumentacionUdocsRevisar"/>
		</display:setProperty>
		
		<display:column media="html" style="width:1%;">
			<html-el:radio property="idRevDocSeleccionado" styleId="idRevDocSeleccionado" value="${udoc.idRevDoc}"/>			
		</display:column>
		
		<c:url var="verUDocURL" value="/action/gestionRevDocAction">
			<c:param name="method" value="verUnidadDocumental" />
			<c:param name="idUdoc" value="${udoc.idUdoc}" />
		</c:url>

		<display:column titleKey="archigest.archivo.solicitudes.signaturaudoc" sortProperty="signaturaUdoc" sortable="true" headerClass="sortable" style="white-space: nowrap;" media="csv excel xml pdf">			
			<c:out value="${udoc.signaturaUdoc}"/>
		</display:column>

		<display:column titleKey="archigest.archivo.solicitudes.signaturaudoc" sortProperty="signaturaUdoc" sortable="true" headerClass="sortable" style="white-space: nowrap;" media="html">
			<a class="tdlink" href="<c:out value="${verUDocURL}" escapeXml="false"/>">
				<c:out value="${udoc.signaturaUdoc}"/>
			</a>
		</display:column>
		
		<display:column titleKey="archigest.archivo.solicitudes.expedienteudoc" sortProperty="expedienteUdoc" sortable="true" headerClass="sortable" style="white-space: nowrap;">		
			<c:out value="${udoc.expedienteUdoc}"/>
		</display:column>
		
		<display:column titleKey="archigest.archivo.solicitudes.titulo" sortProperty="titulo" sortable="true" headerClass="sortable" media="csv excel xml pdf">
			<c:out value="${udoc.titulo}"/>
		</display:column>

		<display:column titleKey="archigest.archivo.solicitudes.titulo" sortProperty="titulo" sortable="true" headerClass="sortable" media="html">
			<a class="tdlink" href="<c:out value="${verUDocURL}" escapeXml="false"/>" >
				<c:out value="${udoc.titulo}"/>
			</a>
		</display:column>

		<display:column titleKey="archigest.archivo.solicitudes.observaciones" sortProperty="observaciones" sortable="true" headerClass="sortable">
			<c:out value="${udoc.observaciones}"/>
		</display:column>

		<display:column titleKey="archigest.archivo.prestamos.gestor" sortable="true" headerClass="sortable" style="white-space: nowrap;">
			<c:out value="${udoc.nombreGestor}"/>
		</display:column>

		<display:column titleKey="archigest.archivo.prestamos.descripcion" media="html" style="text-align: center;" >
			<c:choose>
				<c:when test="${udoc.estado == appConstants.prestamos.estadoRevDoc.FINALIZADA.identificador}">
					<c:set var="accion" value="retrieve"/>
				</c:when>
				<c:otherwise>
					<c:set var="accion" value="edit"/>
				</c:otherwise>
			</c:choose>			
			<c:url var="verDescripcionURL" value="/action/isadg">
				<c:param name="method" value="${accion}" />				
				<c:param name="id" value="${udoc.idUdoc}" />
			</c:url>
	   		<a class="etiquetaAzul12Bold" href="<c:out value="${verDescripcionURL}" escapeXml="false" />">
   				<html:img titleKey="archigest.archivo.ver" altKey="archigest.archivo.ver" page="/pages/images/verDoc.gif" styleClass="imgTextMiddle" />&nbsp;
			</a>
		</display:column>
			
		<display:column titleKey="archigest.archivo.prestamos.altaUdocs" media="html" style="text-align: center;">			
			<c:if test="${udoc.mostrarAgregar}">
				<c:url var="addUdocURL" value="/action/gestionRevDocAction">
					<c:param name="method" value="addAltaUdoc" />
					<c:param name="idRevDoc" value="${udoc.idRevDoc}" />
				</c:url>				
				<a class="etiquetaAzul12Normal" href="<c:out value="${addUdocURL}" escapeXml="false" />">
					<html:img titleKey="archigest.archivo.anadir" altKey="archigest.archivo.anadir" page="/pages/images/altaDoc.gif" styleClass="imgTextMiddle"/>&nbsp;
				</a>
			</c:if>
			<c:if test="${udoc.mostrarVer}">
				<c:url var="verUdocURL" value="/action/gestionRelaciones">
					<c:param name="method" value="veringreso" />
					<c:param name="idingresoseleccionado" value="${udoc.idAlta}" />
				</c:url>							
				<a class="etiquetaAzul12Normal" href="<c:out value="${verUdocURL}" escapeXml="false"/>">
					 <html:img titleKey="archigest.archivo.ver" altKey="archigest.archivo.ver" page="/pages/images/verDoc.gif" styleClass="imgTextMiddle" />&nbsp;
				</a>
			</c:if>
			<c:if test="${udoc.mostrarEditar}">
				<c:url var="editarUdocURL" value="/action/gestionRelaciones">
					<c:param name="method" value="veringreso" />
					<c:param name="idingresoseleccionado" value="${udoc.idAlta}" />
				</c:url>							
				<a class="etiquetaAzul12Normal" href="<c:out value="${editarUdocURL}" escapeXml="false"/>">
					 <html:img titleKey="archigest.archivo.editar" altKey="archigest.archivo.editar" page="/pages/images/editarDoc.gif" styleClass="imgTextMiddle" />&nbsp;
				</a>
			</c:if>		
			<c:if test="${udoc.mostrarEliminar}">									
				<c:url var="removeUdocURL" value="/action/gestionRevDocAction">
					<c:param name="method" value="removeAltaUdoc" />
					<c:param name="idRevDoc" value="${udoc.idRevDoc}" />
				</c:url>			
				<a class="etiquetaAzul12Normal" href="<c:out value="${removeUdocURL}" escapeXml="false" />">
					<html:img titleKey="archigest.archivo.eliminar" altKey="archigest.archivo.eliminar" page="/pages/images/eliminarDoc.gif" styleClass="imgTextMiddle"/>&nbsp;
				</a>				
			</c:if>									
		</display:column>			
	</display:table>  
</div> <%--bloque --%>
</div> <%--cuerpo_izda --%>
</div> <%--cuerpo_drcha --%>
<h2><span>&nbsp;
</span></h2>
</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>
</html:form>

<script>
	<c:if test="${revisionDocumentacionForm.ocultarMotivo}">	
		ocultarMotivoRechazo();
	</c:if>		
</script>