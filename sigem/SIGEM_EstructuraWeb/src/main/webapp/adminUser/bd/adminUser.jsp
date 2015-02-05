<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

<html>
<head>
<ieci:baseInvesDoc/>
<link rel="stylesheet" type="text/css" href="include/css/common.css"/>
<link rel="stylesheet" type="text/css" href="include/css/error.css"/>
<link rel="stylesheet" type="text/css" href="include/css/estilos.css"/>
<script>
	function cerrar(){
	   this.document.getElementById('errorDiv').style.visibility='hidden';
	   if ( this.data.document.getElementById('errorDiv')!=null ){
	      this.data.cerrar();
	   }
	}
	
	function redirect( url )  {
	  parent.document.location.href = url;
	}

	function setAyuda(){
		parent.header.linkAyuda = '/help/adminUser.htm';
	}

</script>
</head>
<body onload="setAyuda();">

<div id="error"></div>

	<div class="contenedora" align="center">
		<div class="cuerpoEO" style="width:840px;height:504px">
   			<div class="cuerporightEO">
     			<div class="cuerpomidEO"> 				
					<div class="cuadro" style="height: 504px;">
						<table><tr><td>
							
							<div id="treeDeptos" style="width:240px; height:185px; border: 1px solid #000000;">
								<iframe name="deptos" src='<html:rewrite page="/user/bd/deptTree.do"/>' frameborder="no" width="100%" height="100%"> </iframe>
							</div>
							
						</td><td>							
							
							<div id="properties" style="width:540px; height:185px; border: 1px none #000000;">
							    <iframe name="propiedades" src='<html:rewrite page="/blank.do"/>' frameborder="no" scrolling="no" width="100%px" height="100%"> </iframe>
							</div>
							
						</td></tr><tr><td>	
							
							<table style="height: 100%;width: 100%">
								<tr><td>
									<div id="treeGroup" style="width:240px; height:200px; z-index:4; border: 1px solid #000000;">
										<iframe name="grupos" src='<html:rewrite page="/user/bd/groupTree.do"/>' frameborder="no" width="100%" height="135px;"> </iframe>
									</div>	
								</td></tr>
								<tr><td>
									<div id="search" style="width:240px; height:75px; border: 1px none #000000;">
									  <iframe name="busqueda" src='<html:rewrite page="/user/bd/search.do"/>' frameborder="no" width="100%" height="53px;" scrolling="no" ></iframe>
									</div>									
								</td></tr>
							</table>	
											
						</td><td>	
							
							<div id="edition" style="width:540px; height:282px; z-index:3; border: 1px none #000000;"> 
							   <iframe name="edicion" src='<html:rewrite page="/blank.do"/>' frameborder="no" scrolling="no" width="100%" height="100%"> </iframe>
							</div>
							
						</td></tr>
						</table>

           			</div>     				
     			</div>
     		</div>
     	</div>
	</div>
				
</body>
</html>