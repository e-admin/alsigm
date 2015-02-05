<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="listadoTerceros" value="${sessionScope[appConstants.transferencias.RESULTADOS_BUSQUEDA_INTERESADOS]}" />
<bean:struts id="mappingGestionInteresados" mapping="/gestionInteresados" />

<c:set var="interesadoFormName" value="${mappingGestionInteresados.name}" />
<c:set var="interesadoForm" value="${requestScope[interesadoFormName]}" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.transferencias.incorporarInteresado" /></tiles:put>
	<tiles:put name="buttonBar" direct="true">	
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<script>
				function incorporarInteresados() {
					var selectionForm = document.forms['<c:out value="${mappingGestionInteresados.name}" />'];
						if ((document.getElementById('nombre').value != '') || 
							(selectionForm.seleccionTerceros && elementSelected(selectionForm.seleccionTerceros))) {
							selectionForm.method.value="incorporarInteresados";
							selectionForm.submit();
						} else {
							alert('<bean:message key="archigest.archivo.transferencias.msgIncorporarInteresadoNoSeleccionado"/>');
						}
				}
				
				function incorporarInteresadoNoValidado(){
					var selectionForm = document.forms['<c:out value="${mappingGestionInteresados.name}" />'];
					selectionForm.method.value="crearInteresado";
					selectionForm.submit();
				}
			</script>
			<TD id="tdIncorporarInteresado"
				<c:choose>
				<c:when test="${!empty listadoTerceros && !noValidado}">
					style="display:block;"
				</c:when>
				<c:otherwise>
					style="display:none;"
				</c:otherwise>
				</c:choose>
			>
				<a class="etiquetaAzul12Bold" href="javascript:incorporarInteresados()">
					<html:img titleKey="archigest.archivo.aceptar" altKey="archigest.archivo.aceptar" page="/pages/images/Ok_Si.gif" styleClass="imgTextMiddle"/>
					<bean:message key="archigest.archivo.aceptar"/>
				</a>		
			</TD>
			<TD width="10">&nbsp;</TD>
		   <TD>
				<c:url var="goBackURI" value="/action/gestionUdocsRelacion">
					<c:param name="method" value="volverAvistaUnidadDocumental" />
				</c:url>
		   		<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${goBackURI}" escapeXml="false"/>'">
					<html:img page="/pages/images/Ok_No.gif" border="0" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
		   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
		   		</a>
			</TD>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">	
		<div id="barra_errores"><archivo:errors /></div>
	
		<tiles:insert definition="transferecias.unidadDocumental.contextoUdoc" />
		
		<bean:define id="isPopup" value="false" toScope="request"/>
		<bean:define id="methodBusqueda" value="buscarTerceros" toScope="request"/>
		<tiles:insert page="./interesados/busqueda_edicion_interesados.jsp" flush="true"/>
	</tiles:put>
</tiles:insert>	

