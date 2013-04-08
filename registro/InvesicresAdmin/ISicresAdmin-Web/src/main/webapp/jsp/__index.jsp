<!--
JSP de entrada a la aplicación.
Redirecciona al usuario al login.jsp. 
Captura el lenguaje del navegador.
-->
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<logic:redirect page="/login.do"/>