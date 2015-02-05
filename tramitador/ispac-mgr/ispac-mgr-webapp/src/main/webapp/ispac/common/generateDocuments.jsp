<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<html>
  	<head>
	    <link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
	    <link rel="stylesheet" href='<ispac:rewrite href="css/menus.css"/>'/>
	    <link rel="stylesheet" href='<ispac:rewrite href="css/nicetabs.css"/>'/>
	    <script type="text/javascript" src='<ispac:rewrite href="../scripts/menus.js"/>'></script>
	    <script type="text/javascript" src='<ispac:rewrite href="../scripts/sorttable.js"/>'> </script>
	    <script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'> </script>
	    <ispac:javascriptLanguage/>

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
		<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>

		<%-- Imprime los documentos generados --%>
		<logic:present name="DocumentsList">
			<logic:iterate name="DocumentsList"
										 id="document"
										 type="ieci.tdw.ispac.ispaclib.bean.ItemBean">
				<ispac:printdocument document='<%= document.getKeyProperty().toString() %>' />
			</logic:iterate>
		</logic:present>
		<logic:present name="FilesList">
			<logic:iterate name="FilesList"
										 id="file"
										 type="java.lang.String">
				<ispac:printfile file='<%= file %>' delete="true"/>
			</logic:iterate>
		</logic:present>
		
		<script type="text/javascript">
			$(document).ready(function(){
				positionMiddleScreen('contenido');
				$("#contenido").draggable();
			});
		</script>
  	</head>
  	<body>
  	
		<div id="contenido" class="move" >
			<div class="ficha">
				<div class="encabezado_ficha">
					<div class="titulo_ficha">
						<h4><bean:message key="prepare.documents.title"/></h4>
						<div class="acciones_ficha">
							<input type="button" value='<bean:message key="common.message.ok"/>' class="btnOk" onclick='<ispac:hideframe refresh="true"/>'/>			
						</div><%--fin acciones ficha --%>
					</div><%--fin titulo ficha --%>
				</div><%--fin encabezado ficha --%>
		
				<div class="cuerpo_ficha">
					<div class="seccion_ficha">
						<label class="popUpInfo"><bean:message key="generate.documents.text"/>:</label>
						<ul>
							<logic:iterate name="MessageList" id="message" type="java.lang.String">
							<li class="linkRight"><%= message.toString()%></li>
							</logic:iterate>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
