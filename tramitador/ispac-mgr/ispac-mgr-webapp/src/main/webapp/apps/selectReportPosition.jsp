<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
	<title>Página de búsqueda</title>
	<link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
	<link rel="stylesheet" href='<ispac:rewrite href="css/estilos.css"/>'/>
		<!--[if lte IE 5]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie5.css"/>'/>
		<![endif]-->	

		<!--[if IE 6]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie6.css"/>'>
		<![endif]-->	

		<!--[if gte IE 7]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie7.css"/>'>
		<![endif]-->
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
  	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'> </script>
    <ispac:javascriptLanguage/>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/forms.js"/>'> </script>
</head>
<body>


<html:form action="report.do?method=generate&id=">

<div id="contenido" class="move" >
	<div class="ficha">
		<div class="encabezado_ficha">
			<div class="titulo_ficha">
				<h4><bean:message key="select.reportPosition.title"/></h4>
				<div class="acciones_ficha">
				
					<html:submit styleId="btnOkSubmit" onclick="javascript: generateReport();" styleClass="btnOkSubmit">
							<bean:message key="common.message.ok"/>
					</html:submit>
			
				<a id="btnCancel"
					href='javascript:cancel();'
					class="btnCancel"><bean:message key="common.message.close" /></a>
					
				</div><%--fin acciones ficha --%>
			</div><%--fin titulo ficha --%>
		</div><%--fin encabezado ficha --%>

		<div class="cuerpo_ficha">
			<div class="seccion_ficha">
			
						<table border="1px" bordercolor="black" cellspacing="5px" cellpadding="5px" width="100%" class="boxGris">

											<%
												int position = 1;
											
												String strFilas = (String) request.getAttribute("FILAS");
												int filas = Integer.parseInt(strFilas);
											
												for (int i = 0; i < filas; i++) {
											%>

											<tr>

												<%
													String strColumnas = (String) request.getAttribute("COLUMNAS");
													int columnas = Integer.parseInt(strColumnas);
												
													for (int j = 0; j < columnas; j++) {
												%>
												
												<td align="center" valign="middle">
													<input type="radio" name="multibox" value='<%=position%>' id='radio<%=position%>'/>
												</td>
												
												<%
														position++;
													}
												%>
											</tr>
											
											<%
												}
											%>
										</table>
				
			</div>
		</div>
				
	</div>
</div>




	
	<script language='JavaScript' type='text/javascript'><!--
	
		checkRadioById("radio1");
		
		document.forms[0].action = document.forms[0].action + '<%=request.getAttribute("ID")%>';
		
		function generateReport() {
		
			position = 1;
			
			for (i = 0; i < document.forms[0].multibox.length; i++) {
			
				if (document.forms[0].multibox[i].checked == true) {
				
					position = document.forms[0].multibox[i].value;
					break;
				}
			}
			document.forms[0].target = "_self";
			document.forms[0].action = document.forms[0].action + "&position=" + position;
		
			
			
			hide();
		}
		
		function hide(){
		 	
		 	eval(element=window.top.frames['workframe'].document.getElementById("contenido"));
		   	eval(elementSearch=window.top.frames['workframe'].document.getElementById("workframeselect"));
		 	element.style.visibility="visible";
		 	element.style.top=document.getElementById("contenido").style.top;
		 	element.style.left=document.getElementById("contenido").style.left;
		 	if(elementSearch!=null && elementSearch!='undefined'){
		 	 	<ispac:hideframe id="workframeselect" refresh="false"/>
		 		elementSearch.style.display="none";
		 	}
		 	
		 	
	 	}
	 	function cancel(){
	 	 var action = '<ispac:rewrite action="showReports.do"/>';
		 document.reportForm.target=self.name;
		 document.reportForm.action=action;
		 document.reportForm.submit();
	 	}
		
	positionMiddleScreen('contenido');
	$(document).ready(function(){
		$("#contenido").draggable();
	});
		
	//--></script>

</html:form>

</body>
</html>