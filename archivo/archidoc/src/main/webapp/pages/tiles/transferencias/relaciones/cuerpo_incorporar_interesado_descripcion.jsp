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
					document.getElementById("linkPressed").value="incorporarInteresados";
					var selectionForm = document.forms['<c:out value="${mappingGestionInteresados.name}" />'];
						if ((document.getElementById('nombre').value != '') || 
							(selectionForm.seleccionTerceros && elementSelected(selectionForm.seleccionTerceros))) {
							if (numElementsSelected(selectionForm.seleccionTerceros)>1)	{
								alert('<bean:message key="archigest.archivo.msg.seleccion.simple"/>');								
							} else {
								selectionForm.method.value="incorporarInteresadosDescripcion";
								selectionForm.submit();
							}
						} else {
							alert('<bean:message key="archigest.archivo.transferencias.msgIncorporarInteresadoNoSeleccionado"/>');
						}
				}
				function incorporarInteresadoNoValidado(){
					var selectionForm = document.forms['<c:out value="${mappingGestionInteresados.name}" />'];
					selectionForm.method.value="crearInteresadoDescripcion";
					document.getElementById("linkPressed").value="incorporarInteresadoNoValidado";
					selectionForm.submit();
				}
				
				function cerrarVentana2(){
					document.getElementById("linkPressed").value="cerrarVentana";
					window.close();
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
		   		<a class=etiquetaAzul12Bold href="javascript:cerrarVentana2();">
					<html:img page="/pages/images/close.gif" border="0" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextBottom" />
		   		 	&nbsp;<bean:message key="archigest.archivo.cerrar"/>
		   		</a>
			</TD>
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">	
		<div id="barra_errores"><archivo:errors /></div>
		
		<bean:define id="isPopup" value="true" toScope="request"/>
		<bean:define id="methodBusqueda" value="buscarTercerosDescripcion" toScope="request"/>
		<tiles:insert page="./interesados/busqueda_edicion_interesados.jsp" flush="true"/>
	</tiles:put>
</tiles:insert>	

