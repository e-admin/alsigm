<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<c:set var="navegador" value="${requestScope[appConstants.documentos.NAVEGADOR_DOCUMENTOS_KEY]}"/>

<c:if test="${not empty navegador}">
<c:url var="verDocumentoURL" value="/action/documento">
	<c:param name="method" value="retrieve" />
	<c:param name="idObjeto" value="${documentoForm.idObjeto}" />
	<c:param name="tipoObjeto" value="${documentoForm.tipoObjeto}" />
	<c:param name="id" value="" />
</c:url>
<script>
	function verDocumento(id){
		window.location = '<c:out value="${verDocumentoURL}" escapeXml="false"/>' + id;
	}
</script>
<c:out value="" escapeXml=""></c:out>
<table cellpadding="2" cellspacing="2">
<tr>
<td class="etiquetaAzul11Bold" width="15px">
	<c:choose>

	<c:when test="${not empty navegador.idFirst}">
		<a href="javascript:verDocumento('<c:out value="${navegador.idFirst}"/>')">
			<html:img page="/pages/images/navegador/first.gif"
		        border="0"
		        altKey="archigest.archivo.botonera.primero"
		        titleKey="archigest.archivo.botonera.primero"
		        styleClass="imgTextMiddle"/>
		</a>
	</c:when>
	<c:otherwise>
		<html:img page="/pages/images/navegador/first_disabled.gif"
		        border="0"
		        altKey="archigest.archivo.botonera.primero"
		        titleKey="archigest.archivo.botonera.primero"
		        styleClass="imgTextMiddle"/>

	</c:otherwise>
	</c:choose>
</td>
<td class="etiquetaAzul11Bold" width="15px">
<c:choose>
	<c:when test="${not empty navegador.idPrevious}">
		<a href="javascript:verDocumento('<c:out value="${navegador.idPrevious}"/>')">
			<html:img page="/pages/images/navegador/prev.gif"
		        border="0"
		        altKey="archigest.archivo.botonera.anterior"
		        titleKey="archigest.archivo.botonera.anterior"
		        styleClass="imgTextMiddle"/>
		</a>
	</c:when>
	<c:otherwise>
			<html:img page="/pages/images/navegador/prev_disabled.gif"
		        border="0"
		        altKey="archigest.archivo.botonera.anterior"
		        titleKey="archigest.archivo.botonera.anterior"
		        styleClass="imgTextMiddle"/>
	</c:otherwise>
</c:choose>

</td>
<td class="etiquetaAzul11Bold">
	<c:out value="${navegador.resumen}"/>&nbsp;<bean:message key="archigest.archivo.botonera.docsElectronicos"/>&nbsp;
</td>
<td class="etiquetaAzul11Bold" width="15px">
<c:choose>
	<c:when test="${not empty navegador.idNext}">
		<a href="javascript:verDocumento('<c:out value="${navegador.idNext}"/>')">
			<html:img page="/pages/images/navegador/next.gif"
	        border="0"
	        altKey="archigest.archivo.botonera.siguiente"
	        titleKey="archigest.archivo.botonera.siguiente"
	        styleClass="imgTextMiddle"/>

		</a>
	</c:when>
	<c:otherwise>
			<html:img page="/pages/images/navegador/next_disabled.gif"
	        border="0"
	        altKey="archigest.archivo.botonera.siguiente"
	        titleKey="archigest.archivo.botonera.siguiente"
	        styleClass="imgTextMiddle"/>
	</c:otherwise>
</c:choose>

</td>
<td class="etiquetaAzul11Bold" width="15px">

<c:choose>
	<c:when test="${not empty navegador.idLast}">
		<a href="javascript:verDocumento('<c:out value="${navegador.idLast}"/>')">
			<html:img page="/pages/images/navegador/last.gif"
        border="0"
        altKey="archigest.archivo.botonera.ultimo"
        titleKey="archigest.archivo.botonera.ultimo"
        styleClass="imgTextMiddle"/>

		</a>
	</c:when>
	<c:otherwise>
		<html:img page="/pages/images/navegador/last_disabled.gif"
		        border="0"
		        altKey="archigest.archivo.botonera.ultimo"
		        titleKey="archigest.archivo.botonera.ultimo"
		        styleClass="imgTextMiddle"/>
	</c:otherwise>
</c:choose>

</td>
</tr>
</table>
</c:if>
