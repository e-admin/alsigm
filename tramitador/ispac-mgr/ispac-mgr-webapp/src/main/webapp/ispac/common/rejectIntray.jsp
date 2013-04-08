<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:define id="regNum" name="INTRAY" property="registerNumber" type="java.lang.String"/>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html">
    <link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>	
    <link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos.css"/>'/>
	<!--[if lte IE 5]>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie5.css"/>'/>
	<![endif]-->	

	<!--[if IE 6]>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie6.css"/>'/>
	<![endif]-->	

	<!--[if gte IE 7]>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie7.css"/>'/>
	<![endif]-->	
    
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
 	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'></script>
	<ispac:javascriptLanguage/>
    <script>
    	function closePage() {
    		top.hideFrame("workframe",'<ispac:rewrite page="wait.jsp"/>');
    		parent.window.location.href = '<ispac:rewrite action="showIntrayList.do" />';
    	}
    	$(document).ready(function() {
			$("#contenido").draggable();
		});
		
	
		positionMiddleScreen('contenido');
	
    </script>
</head>
<body>
<div id="contenido" class="move" >
	<div class="ficha">
		<div class="encabezado_ficha">
			<div class="titulo_ficha">
				<h4><bean:message key="intray.reject.titulo"/></h4>
				<div class="acciones_ficha">
					<input type="button" value='<bean:message key="common.message.close"/>' class="btnCancel" onclick="javascript:closePage()"/>
								
				</div><%--fin acciones ficha --%>
			</div><%--fin titulo ficha --%>
		</div><%--fin encabezado ficha --%>

		<div class="cuerpo_ficha">
			<div class="seccion_ficha">
			
			<p>
				<label class="popUp"><nobr><bean:message key="intray.reject.rejected" arg0='<%=regNum%>'/></nobr></label>
				</p>
			</div>
		</div>
	</div>
</div>
</body>
</html>
