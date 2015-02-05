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
	<title>Página de selección</title>
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
	 
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'> </script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/forms.js"/>'> </script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'> </script>
    <ispac:javascriptLanguage/>
	 <script type="text/javascript" src='<ispac:rewrite href="../scripts/utils.js"/>'> </script>
     <script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
 	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
 	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
    
</head>
<body>
<div id="contenido" class="move" >
	<div class="ficha">
	<bean:parameter id="tipo" name="tipo"/>
		<div class="encabezado_ficha">
			<div class="titulo_ficha">
				<h4><bean:write name="Caption"/></h4>
				<div class="acciones_ficha">
				
				<a id="btnCancel"
					href='javascript:cancel("<c:out value="${param['tipo']}"/>");'
					class="btnCancel"><bean:message key="common.message.close" /></a>
					
				</div><%--fin acciones ficha --%>
			</div><%--fin titulo ficha --%>
		</div><%--fin encabezado ficha --%>
	
	<div class="cuerpo_ficha">
	<div class="seccion_ficha">



<html:form action="report.do?method=generate&id=">

	<table cellpadding="0" cellspacing="1" border="0" class="" width="99%" style="margin-top:6px; margin-left:4px">

		<tr>
			<td width="100%" class="blank">
				<table border="0" cellspacing="2" cellpadding="2" width="100%">
					<tr><td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td></tr>
					<tr>
						<td valign="middle" align="center" class="formsTitle">
							<bean:write name="Form" filter='false'/>
						</td>
					</tr>
					<tr><td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td></tr>
				</table>
			</td>
		</tr>
		
		<%--
		<tr>
			<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="15px"/></td>
		</tr>
		<tr>
			<td height="20" align="center" >
				<html:submit onclick="javascript: enabledCheckboxes();" styleClass="form_button_white">
					<bean:message key="common.message.ok"/>
				</html:submit>
			</td>
		</tr>
		<tr>
			<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
		</tr>
		--%>
		
	</table>
	
	<script language='JavaScript' type='text/javascript'><!--
	
		//document.forms[0].action = document.forms[0].action + '<%=request.getAttribute("ID")%>';
		reportFormAction = document.forms[0].action + '<%=request.getAttribute("ID")%>';
		document.forms[0].action = reportFormAction;
		function submitform () {
			document.forms[0].action = reportFormAction;
			document.forms[0].submit();
			
			<%--
			<ispac:hideframe id="workframeselect" refresh="false"/>
			--%>
		}
		
		function submitformFilasColumnas () {
		
			document.forms[0].action= '<%= request.getContextPath()  + "/report.do?method=select&id="+request.getAttribute("ID")+"&filas="+request.getAttribute("FILAS")+"&columnas="+request.getAttribute("COLUMNAS")%>';
			document.forms[0].submit();
			
		}
		
	//--></script>

</html:form>
</div><%--fin cuerpo ficha --%>
</div><%--fin  ficha --%>



		</div>
		<%--seccion ficha --%> <ispac:layer /> <ispac:frame
			id="workframeDirectory" /></div>




<div><%--fin contenido --%> 
</body>
</html>


 <script>

function  showFramePosition(target, action, width, height, msgConfirm, doSubmit, needToConfirm, form) {
		//document.getElementById("contenido").style.visibility="hidden";
		
		//window.top.frames['workframe'].document.getElementById("workframeselect").style.display="block";
		var element=window.top.frames['workframe'].document.getElementById("workframeDirectory");
		if(element!=null && element!='undefined'){
			element.style.display="block";
			element.style.visibility="visible";
		}
		action=action+"&frame=window.top.frames[%27workframe%27]&workframeToReturn=workframe";
		
		showFrame(target, action, width, height, msgConfirm, doSubmit, needToConfirm, form) ;
	
		document.getElementById("contenido").style.top="0px";
		document.getElementById("contenido").style.left="0px";
		
		
		document.getElementById("contenido").style.visibility="hidden";
		document.getElementById("contenido").style.display="block";
		
}
	
positionMiddleScreen('contenido');
	$(document).ready(function(){
		$("#contenido").draggable();
	});
	
function cancel(tipo){
		 var action = '<ispac:rewrite action="showReports.do?tipo="/>';
		 document.reportForm.target=self.name;
		 document.reportForm.action=action+tipo;
		 document.reportForm.submit();
		 
		 

		 	
	}
</script>