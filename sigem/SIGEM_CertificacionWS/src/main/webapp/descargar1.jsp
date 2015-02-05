<%@page import="ieci.tecdoc.sgm.certificacion.ws.server.RetornoPdf"%>
<%@page import="ieci.tecdoc.sgm.certificacion.ws.server.test.Demo1"%>
<%
   try
   {	
	  String idioma = request.getParameter("idioma");
   	  RetornoPdf certificacion = Demo1.obtenerCertificacion(idioma);
   	  response.setContentType("application/pdf");
	  response.setContentLength(certificacion.getContenido().length); 
	  response.setHeader("Content-disposition", "attachment; filename=CertificacionPagos.pdf" ); 
 	  javax.servlet.ServletOutputStream output = response.getOutputStream();
	  output.write(certificacion.getContenido(), 0, certificacion.getContenido().length); 
      output.flush();
	  output.close();
   }
   catch (Exception exc)
   {
%>
	   No se ha podido generar la certificaci&oacute;n de pagos
<%
   }
%>