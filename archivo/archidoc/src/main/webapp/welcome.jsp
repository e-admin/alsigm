<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<c:set var="entidadRequerida" value="${appConstants.configConstants.entidadRequerida}" />

<c:choose>
 	<c:when test="${entidadRequerida}" >
 		<jsp:forward  page="welcomeMultiEntity.jsp"/>
	</c:when>
	<c:otherwise>
		<jsp:forward page="welcomeSingleEntity.jsp"/>
	</c:otherwise>
</c:choose>
