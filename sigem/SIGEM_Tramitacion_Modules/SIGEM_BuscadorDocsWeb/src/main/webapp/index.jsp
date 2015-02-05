<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>

	<%
		String url = ieci.tecdoc.sgm.core.admin.web.AutenticacionBackOffice.obtenerUrlLogin(request, 
            ieci.tecdoc.sgm.core.services.gestion_backoffice.ConstantesGestionUsuariosBackOffice.APLICACION_BUSCADOR_DOCUMENTOS);	
		response.sendRedirect("../" + url);
    %>

</body>
</html>