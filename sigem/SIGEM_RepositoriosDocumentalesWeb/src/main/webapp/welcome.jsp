<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<html:html>
<head>
    <title><bean:message key="message.common.title"/></title>
    <ieci:baseInvesDoc/>
    <link rel="stylesheet" type="text/css" href="include/css/estilos.css"/>

</head>
<body>
	
	<div class="contenedora" align="center">
		<iframe name="header" src="<html:rewrite forward="/header"/>" scrolling="no" frameborder="no" width="100%" height="120px"> </iframe>
		<iframe name="menu" src="<html:rewrite forward="/menu"/>" scrolling="no" frameborder="no" width="100%" height="23px"></iframe>
		<iframe name="data" src="<html:rewrite forward="/home"/>" width="100%" frameborder="no" height="500px"> </iframe>
		
		<div class="cuerpobt" style="width:889px">
	    	<div class="cuerporightbt">
	      		<div class="cuerpomidbt"></div>
	    	</div>
		</div>
	</div>
<%--  
<table width="100%" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td colspan="2" height="120px" width="100%">
		 <iframe name="header" src="<html:rewrite forward="/header"/>" scrolling="no" width="100%" height="100%"> </iframe> 
    </td>
  </tr>
  <tr>
    <td height="390px" valign="top">
		<iframe name="menu" src="<html:rewrite forward="/menu"/>" frameborder="no" width="131px" scrolling="no"  height="390px"> </iframe> 
    </td>
    <td height="100%" valign="top" width="100%" >
		 <iframe name="data" src="<html:rewrite forward="/home"/>" frameborder="no" width="100%" height="100%"> </iframe> 
    </td>
  </tr>
</table>
--%>



</body>
</html:html>