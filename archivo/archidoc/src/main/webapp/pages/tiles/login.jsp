<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<c:set var="entidadRequerida" value="${appConstants.configConstants.entidadRequerida}" />

<c:choose>
	<c:when test="${entidadRequerida}" >
		<jsp:forward page="loginMultiEntity.jsp"/>
	</c:when>
	<c:otherwise>
		<jsp:forward page="loginSingleEntity.jsp"/>	
	</c:otherwise>
</c:choose>

