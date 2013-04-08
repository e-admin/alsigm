<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<html>
<head>
	<link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
    <link rel="stylesheet" href='<ispac:rewrite href="css/menus.css"/>'/>
    <link rel="stylesheet" href='<ispac:rewrite href="css/nicetabs.css"/>'/>
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
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/menus.js"/>'></script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/sorttable.js"/>'> </script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'> </script>
    <ispac:javascriptLanguage/>
    <script>
		function CreateProcess( procedure) {
		
			<c:url var="url" value="/createIntrayProcess.do">
				<c:param name="register" value="${param.register}"/>
			</c:url>

			document.getElementById('move').style.visibility = "hidden"; 
			showLayer("waitInProgress");
			
			top.document.location.href = '<c:out value="${url}"/>&procedure=' + procedure;
		}
    </script>
</head>
<body>
<div id="move">


<div class="ficha">
	<div class="encabezado_ficha">
		<div class="titulo_ficha">
			<h4><bean:message key="tramites.seleccionProcedimiento.titulo"/></h4>
			<div class="acciones_ficha">
				<a href="#" id="btnCancel"
				onclick='javascript:top.ispac_needToConfirm=false;<ispac:hideframe refresh="true"/>'
				class="btnCancel"><bean:message key="common.message.cancel" /></a>
			</div><%--fin acciones ficha --%>
		</div><%--fin titulo ficha --%>
	</div><%--fin encabezado ficha --%>
	
	<div class="cuerpo_ficha">
	<div class="seccion_ficha">

<table cellpadding="0" cellspacing="1" border="0"  width="99%" style="margin-top:6px; margin-left:4px">

	<tr>
		<td width="100%" class="blank">
			<table border="0" cellspacing="2" cellpadding="2" width="100%">
				<tr><td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td></tr>
				<tr>
					<td valign="middle" align="center" class="formsTitle">
			    		<table cellpadding="0" cellspacing="1" border="0" width="98%" >
							<tr><td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td></tr>
					      	<tr>
					        	<td align="center">
								  <display:table name="InstProcedureList" id="procedure" class="tableDisplay" export="false">
				
								  		<display:column titleKey="formatter.createProcess.procedimiento" 
								  						headerClass="sortable">
								  			<bean:define name="procedure" id="identifier" property="property(ID)"/>
											<a href='<%="javascript:CreateProcess(\"" + identifier.toString() + "\");"%>' class="displayBullet">
												<bean:write name="procedure" property="property(NOMBRE)"/>
											</a>
									  	</display:column>
									  	
								  		<display:column titleKey="formatter.createProcess.version" 
								  						headerClass="headerDisplayLeft">
								  			<bean:write name="procedure" property="property(NVERSION)"/>
									  	</display:column>
									  	
								  </display:table>
			        			</td>
			      			</tr>
			    		</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>
</div>
</div>
</div>

<ispac:layer id="waitInProgress" msgKey="msg.layer.operationInProgress" showCloseLink="false" styleClassMsg="messageShowLayer" /> 

</body>
</html>

<script>
	positionMiddleScreen('move');
	$(document).ready(function(){
		$("#move").draggable();
	});
</script>
