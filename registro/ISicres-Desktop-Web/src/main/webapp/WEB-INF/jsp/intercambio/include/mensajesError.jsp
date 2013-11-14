<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${requestScope.msg!=null}">
	<div style="color:blue;">
		<c:out value="${requestScope.msg}" ></c:out>
	</div>
	</c:if>
	<c:if test="${requestScope.error!=null}">
	<div style="color:red;">
		<c:out value="${requestScope.error}" ></c:out>
	</div>
</c:if>