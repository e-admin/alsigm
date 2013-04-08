<html>
<head>
<META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
</head>
<body>
<%
String sessionID = request.getSession().getId();
String param = request.getParameter("addCertificate");
Boolean addCertificate = null;
if (param != null){
	addCertificate = new Boolean(param);
	session.setAttribute("addCertificate", addCertificate);
}
response.sendRedirect("https://10.228.69.200/servlet/com.telventi.autenticacion.ServletAutenticacion?ap=wardaadm&sesion="+sessionID);
%>
</body>
</html>
