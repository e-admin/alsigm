<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="numDocumentos" value="0" />


<tiles:importAttribute name="idElemento" ignore="true" />
<tiles:importAttribute name="numDocumentos" ignore="true" />

<center>
<c:if test="${numDocumentos > 0}">

<c:choose>
	<c:when test="${numDocumentos == 1}">

   		<a class="etiquetaAzul12Normal"
   		   target="_self"
		   href="javascript:verDocumento('<c:out value="${idElemento}" escapeXml="false"/>');" >
		   <html:img page="/pages/images/docsElectronicos/docElectronico.gif"
		        altKey="archigest.archivo.ver.documento.electronico"
		        titleKey="archigest.archivo.ver.documento.electronico"
		        styleClass="imgTextMiddle"/>
			
		 </a>
		 (<c:out value="${numDocumentos}" escapeXml="false"/>)
	</c:when>
	<c:otherwise>
   		<a class="etiquetaAzul12Normal"
   		   target="_self"
		   href="javascript:verDocumentos('<c:out value="${idElemento}" escapeXml="false"/>');" >
		   <html:img page="/pages/images/docsElectronicos/docsElectronicos.gif"
		        altKey="archigest.archivo.descripcion.descriptor.button.verDocumentos"
		        titleKey="archigest.archivo.descripcion.descriptor.button.verDocumentos"
		        styleClass="imgTextMiddle"/>
		 
		 </a>
		(<c:out value="${numDocumentos}" escapeXml="false"/>)
	</c:otherwise>
</c:choose>
</c:if>
</center>