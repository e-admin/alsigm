<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<c:url var="verDocumentosURL" value="/action/documentos">
	<c:param name="actionToPerform" value="homeUDoc" />
	<c:param name="tipo" value="1"/>
	<c:param name="id" value=""/>
</c:url>

<c:url var="verDocumentoURL" value="/action/documentos">
	<c:param name="actionToPerform" value="viewDocumentCF" />
	<c:param name="id" value="" />
</c:url>

<script>
	function verDocumento(id){

		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
			var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
			window.top.showWorkingDiv(title, message);
		}

		window.location = '<c:out value="${verDocumentoURL}" escapeXml="false" />' + id;
	}

	function verDocumentos(id){
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
			var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
			window.top.showWorkingDiv(title, message);
		}

		window.location = '<c:out value="${verDocumentosURL}" escapeXml="false"/>' + id;
	}
</script>