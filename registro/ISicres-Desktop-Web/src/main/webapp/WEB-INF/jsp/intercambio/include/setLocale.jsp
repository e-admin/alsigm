<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setLocale value="<%=session.getAttribute(\"JLocale\")%>" scope="session"/>
<c:if test="${ message == null }">
    <fmt:setBundle  basename="resources.ISicres-IntercambioRegistral" scope="application"/>
</c:if>