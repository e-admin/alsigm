<%@ page contentType="text/html;charset=ISO-8859-1" language="java"%>
<% 
String datos = request.getParameter("datos");
datos = datos == null? new String(): datos;
String clave = request.getParameter("clave");
clave = clave == null? "0x11|0x11|0x11|0x11|0x11|0x11|0x11|0x11|0x11|0x11|0x11|0x11|0x11|0x11|0x11|0x11|0x11|0x11|0x11|0x11|0x11|0x11|0x11|0x11": clave;
%>
<jsp:forward page="/html/loginCert.do">
		 <jsp:param name="datos" value="<%=datos%>"/>
		 <jsp:param name="clave" value="<%=clave%>"/>
</jsp:forward>

