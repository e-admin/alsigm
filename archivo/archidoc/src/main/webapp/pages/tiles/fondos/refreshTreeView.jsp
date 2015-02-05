<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<c:if test="${requestScope[appConstants.fondos.REFRESH_VIEW_KEY] || param[appConstants.fondos.REFRESH_VIEW_KEY]=='true'}">

	<c:set var="viewName"><c:out value="${param.viewName}"><c:out value="${appConstants.fondos.CUADRO_CLF_VIEW_NAME}" /></c:out></c:set>
	<jsp:useBean id="viewName" type="java.lang.String" />
	
	<script>
		var pageFrames = window.top.frames;
		for (var i=0; i<pageFrames.length; i++) {
			if (pageFrames[i] != window && pageFrames[i].refreshWindow) {
				pageFrames[i].refreshWindow('<c:out value="${sessionScope[viewName].selectedNode.nodePath}" />');
			}
		}
	</script>
</c:if>