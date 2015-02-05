<!--
Página devuelta al usuario cuando solicita una URL que no existe.
-->

<%@ page language="java" pageEncoding="ISO-8859-1" %>
<%@ page contentType="text/html"%>
<%@ page isErrorPage="true" %>
<%@ page session="false"%>
<%@ page isThreadSafe="true" %>
<%@ page autoFlush="true" %>


<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">

<%@ page import="com.ieci.tecdoc.isicres.desktopweb.Keys" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
	<script language="javascript">														
		document.write('<link REL=\"stylesheet\" TYPE=\"text/css\" HREF="' + top.urlSkinCSS + '"/>');									
	</script>
<link REL="stylesheet" TYPE="text/css" HREF="css/global.css" />
</head>
<body tabIndex="-1">


<div id="archivador" style="position:absolute; top:55; left:30">         
         <img border="0" src="images/archivador.gif" WIDTH="392" HEIGHT="351">
      </div> 
      



	<div id="archivador" style="position:absolute; top:100; left:400">         
         <h1><%= RBUtil.getInstance(Locale.getDefault()).getProperty(Keys.I18N_EXCEPTION_ERROR_JSP) %></h1>
      </div> 
      
</body>
</html>