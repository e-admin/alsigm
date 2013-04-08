<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<c:if test="${requestScope[appConstants.documentos.REFRESH_VIEW_KEY] || param[appConstants.documentos.REFRESH_VIEW_KEY]=='true'}">

	<c:set var="viewName"><c:out value="${param.viewName}"><c:out value="${appConstants.documentos.DOCUMENT_TREE_KEY}" /></c:out></c:set>
	<jsp:useBean id="viewName" type="java.lang.String" />
	
	<script>
		var pageFrames = window.top.frames;
		for (var i=0; i<pageFrames.length; i++) 
		{
			if (pageFrames[i] != window && pageFrames[i].refreshDocumentTreeWindow)
				pageFrames[i].refreshDocumentTreeWindow('<c:out value="${sessionScope[viewName].selectedNode.nodePath}" escapeXml="false"/>');
		}
	</script>
</c:if>