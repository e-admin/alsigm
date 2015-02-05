<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<html>
<head>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

<ieci:baseInvesDoc/>
<link rel="stylesheet" type="text/css" href="include/css/common.css"/>
<link rel="stylesheet" type="text/css" href="include/css/error.css"/>
<link rel="stylesheet" type="text/css" href="include/css/estilos.css"/>

<script>

function redirect( url )  {
  parent.document.location.href = url;
}

</script>
</head>

<body>

	<div class="contenedora" align="center">
		<div class="cuerpoEO" style="width:850px;height:494px">
   			<div class="cuerporightEO">
     			<div class="cuerpomidEO"> 				
					<div class="cuadro" style="height: 494px;">
<table><tr><td>
						<div id="carga_repositorio" name="carga_repositorio" style="position: relative; width:240px; height:195px; border: 1px solid #000000;" align="center">
							<table style="height: 100%;" align="center">
								<tr>
									<td>
										<img src="img/clockani.gif" />
									</td>
									<td>
										<label class="gr"><bean:message key="message.cargando"/></label>
									</td>
								</tr>
							</table>
						</div>
						<div id="divRepTree" name="divRepTree" style="position: absolute; width:0px; height:0px; border: 1px solid #000000; visibility: hidden">
						    <iframe name="repTree" src="<html:rewrite page="/volume/repositoriesTree.do"/>" frameborder="no" width="100%" height="100%"> </iframe>
						</div>
</td><td>
						<div id="properties" style="width:600px; height:195px; border: 1px none #000000;">
						    <iframe name="propiedades" src="<html:rewrite page="/blank.do"/>" frameborder="no" width="100%" height="100%"> </iframe>
						</div>
</td></tr><tr><td>
						<div id="carga_volumen" name="carga_volumen" style="position: relative; width:240px; height:260px; border: 1px solid #000000;" align="center">
							<table style="height: 100%;" align="center">
								<tr>
									<td>
										<img src="img/clockani.gif" />
									</td>
									<td>
										<label class="gr"><bean:message key="message.cargando"/></label>
									</td>
								</tr>
							</table>
						</div>
						<div id="divListTree" style="position: absolute; width:0px; height:0px; border: 1px solid #000000; visibility: hidden;">
						   <iframe name="listTree" src="<html:rewrite page="/volume/listTree.do"/>" frameborder="no" width="100%" height="100%"> </iframe>
						</div>
</td><td>
						<div id="edition" style="width:550px; height:260px; border: 1px none #000000; "> 
						   <iframe name="edicion" src="<html:rewrite page="/blank.do"/>" frameborder="no" width="100%" height="100%" scrolling="no"> </iframe>
						</div>
</td></tr></table>
           			</div>     				
     			</div>
     		</div>
     	</div>
	</div>

<%-- 
<!--  -->

asi estaba
<div id="repTree" style="position:absolute; left:5px; top:5px; width:300px; height:185px; z-index:1; border: 1px none #000000;">
    <iframe name="repTree" src="<html:rewrite page="/volume/repositoriesTree.do"/>" frameborder="no" width="100%" height="100%"> </iframe>
</div>

<div id="properties" style="position:absolute; left:310px; top:5px; width:330px; height:185px; z-index:2; border: 0px solid #000000;">
    <iframe name="propiedades" src="<html:rewrite page="/blank.do"/>" frameborder="no" width="330px" height="100%"> </iframe>
</div>
<div id="edition" style="position:absolute; left:310px; top:195px; width:330px; height:215px; z-index:3; border: 0px solid #000000;"> 
   <iframe name="edicion" src="<html:rewrite page="/blank.do"/>" frameborder="no"  width="100%" height="100%"> </iframe>
</div>
<div id="treeGroup" style="position:absolute; left:5px; top:196px; width:300px; height:135px; z-index:1; border: 0px solid #000000;">
   <iframe name="listTree" src="<html:rewrite page="/volume/listTree.do"/>" frameborder="no" width="100%" height="100%"> </iframe>
</div>

--%>
</body>
</html>