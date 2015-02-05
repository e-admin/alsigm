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
	<title><bean:message key="forms.expRelacionados.title"/></title>
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
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/sorttable.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
  	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'></script>
    <ispac:javascriptLanguage/>
	
    <script>
  
   
    	function submitform() {
    		document.searchForm.submit();
    	}

	 	function hide(){
		 	
		 	eval(element=window.top.frames['workframe'].document.getElementById("contenido"));
		   	eval(elementSearch=window.top.frames['workframe'].document.getElementById("workframesearch"));
		    <ispac:hideframe id="workframesearch" refresh="false"/>
		 	element.style.visibility="visible";
		 	element.style.top=document.getElementById("contenido").style.top;
		 	element.style.left=document.getElementById("contenido").style.left;
		 	elementSearch.style.display="none";
		 
		 

		 	
	 	}
   		
    </script>
</head>
<body >
<html:form action="/relateExpedient.do?method=search">
	<div id="contenido" class="move framesAnidados">
		<div class="ficha">
			<div class="encabezado_ficha">
				<div class="titulo_ficha">
					<h4><bean:message key="forms.expRelacionar.title"/></h4>
					<div class="acciones_ficha">
						<a href="#" id="btnCancel" onclick="javascript:hide();" class="btnCancel">
							<bean:message key="common.message.close" />
						</a>
					</div>
				</div>
			</div>

			<div class="cuerpo_ficha">
				<div class="seccion_ficha">
					<logic:messagesPresent>
						<div class="infoError">
							<bean:message key="forms.errors.messagesPresent" />
						</div>
					</logic:messagesPresent> 
					<bean:write name="Form" filter='false'/>
				</div>
	   		</div>
		</div>
	</div>
</html:form>
	
</body>
</html>

<script>
	positionMiddleScreen('contenido');
	
	$(document).ready(function(){
		$("#contenido").draggable();
	});
	</script>