<%@page import="ieci.tecdoc.sgm.admsistema.util.Defs"%>
<%
   String xml = (String)request.getSession().getAttribute(Defs.PARAMETRO_FICHERO_SERVIDOR_NUEVO);
   String nombre = (String)request.getSession().getAttribute(Defs.PARAMETRO_FICHERO_SERVIDOR_NOMBRE);
   
   try
   {	
   	  response.setContentType("text/xml");
	  response.setContentLength(xml.length()); 
	  response.setHeader("Content-disposition", "attachment; filename=" + nombre ); 
 	  javax.servlet.ServletOutputStream output = response.getOutputStream();
	  output.write(xml.getBytes(), 0, xml.length()); 
      output.flush();
	  output.close();
   }
   catch (Exception exc)
   {
   }
%>

<html>
	<head>
	</head>
	<body onload="window.close();">
	</body>
</html>
