<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<bean:struts id="mappingComposicionEmplazamiento" mapping="/composicionEmplazamiento" />
<c:set var="formName" value="${mappingComposicionEmplazamiento.name}" />
<c:set var="form" value="${requestScope[formName]}" />

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.transferencias.edicion"/>
		<bean:message key="archigest.archivo.transferencias.los"/>
		<bean:message key="archigest.archivo.transferencias.emplazamientos"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">	
			<TABLE cellpadding=0 cellspacing=0>
			<TR>
			    <TD>
					<a class="etiquetaAzul12Bold" href="javascript:document.forms['<c:out value="${mappingComposicionEmplazamiento.name}" />'].submit()" >
						<html:img page="/pages/images/Ok_Si.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</TD>
			   <TD width="10">&nbsp;</TD>
			   <TD>
				<c:url var="goBackURI" value="/action/gestionUdocsRelacion">
					<c:param name="method" value="volverAvistaUnidadDocumental"  />
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

		<tiles:insert definition="transferecias.unidadDocumental.contextoUdoc" />					

		<bean:define id="isPopup" value="false" toScope="request"/>
		<bean:define id="soportaBusquedaExtendida" name="SOPORTA_BUSQUEDA_EXTENDIDA" toScope="session"/>
		<tiles:insert page="./geograficos/busqueda_edicion_geograficos.jsp" flush="true"/>
	
	</tiles:put>
</tiles:insert>