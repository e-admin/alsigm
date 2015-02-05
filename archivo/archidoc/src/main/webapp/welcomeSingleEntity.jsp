<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<% response.addCookie(new javax.servlet.http.Cookie("actor", "employee")); %>
<logic:redirect forward="loginAction"/>