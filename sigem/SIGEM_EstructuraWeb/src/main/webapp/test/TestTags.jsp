<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ page import="ieci.tecdoc.mvc.util.Constantes, ieci.tecdoc.idoc.admin.api.user.UserDefs" %> 
<%@ page import="org.apache.struts.Globals, ieci.tecdoc.mvc.util.MvcDefs"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<html> 
<body>

<logic:present name="<%=Constantes.TOKEN_USER_CONNECTED%>" > 


Id: <bean:write name="<%=Constantes.TOKEN_USER_CONNECTED%>" property="id"/> <br>
Name: <bean:write name="<%=Constantes.TOKEN_USER_CONNECTED%>" property="userName"/> <br>

Profiles: <bean:write name="<%=Constantes.TOKEN_USER_CONNECTED%>" property="profiles"/> <br>
Dept: <bean:write name="<%=Constantes.TOKEN_USER_CONNECTED%>" property="dept"/> <br>
<%--  casca
Manager de 4: <bean:write name="<%=Constantes.TOKEN_USER_CONNECTED%>" property="isManager(4)"/> <br>
--%>
Profiles2: <bean:write name="<%=Constantes.TOKEN_USER_CONNECTED%>" property="profile(6)"/> <br>

Manager: <bean:write name="<%=Constantes.TOKEN_USER_CONNECTED%>" property="isManager(24)"/> <br>


<%--
<c:set var="PRODUCT_USER" value="<%=UserDefs.PRODUCT_USER%>"></c:set>
<c:out value="Product_user: ${PRODUCT_USER}"></c:out>
Profile: <bean:write name="<%=Constantes.TOKEN_USER_CONNECTED%>" property="profiles()"/> <br> --%>
<br>
<c:if test="${user.isSystemSuperUser}" >
es super usuario
</c:if>
</logic:present>

<logic:notPresent name="<%=Constantes.TOKEN_USER_CONNECTED%>">
	Session espirada
</logic:notPresent>
</body>
</html>