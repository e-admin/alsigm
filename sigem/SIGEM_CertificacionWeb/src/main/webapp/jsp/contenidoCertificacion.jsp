<%

   try
   {	
   	  byte[] fichero = (byte[])session.getAttribute("datosFichero");
   	  String user = (String)session.getAttribute("idUsuario");
   	  String file = (String)session.getAttribute("idFichero");
   	  response.setContentType("application/pdf");
	  response.setContentLength(fichero.length); 
	  response.setHeader("Content-disposition", "attachment; filename=Certificacion_" + file + "_" + user + ".pdf"); 
 	  javax.servlet.ServletOutputStream output = response.getOutputStream();
	  output.write(fichero, 0, fichero.length); 
      output.flush();
	  output.close();
   }
   catch (Exception exc)
   {
   }
%>
