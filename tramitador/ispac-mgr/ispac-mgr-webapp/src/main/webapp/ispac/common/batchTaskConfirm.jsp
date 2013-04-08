<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

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

	<script type="text/javascript">
		$(document).ready(function(){
			positionMiddleScreen('contenido');
			$("#contenido").draggable();
		});
		
	    function closeFrame()  {
	      <c:url var="url" value="showBatchTask.do">
	        <c:param name="batchTaskId" value="${param.batchTaskId}"/>
	      </c:url>
	      parent.window.location.href = '<c:out value="${url}"/>';
	    }
	</script>
	
  </head>
  <body>

	<div id="contenido" class="move" >
		<div class="ficha">
			<div class="encabezado_ficha">
				<div class="titulo_ficha">
					<h4><bean:message key="prepare.documents.title"/></h4>
					<div class="acciones_ficha">
						<input type="button" value='<bean:message key="common.message.ok"/>' class="btnOk" onclick="javascript:closeFrame();"/>			
					</div><%--fin acciones ficha --%>
				</div><%--fin titulo ficha --%>
			</div><%--fin encabezado ficha --%>
	
			<div class="cuerpo_ficha">
				<div class="seccion_ficha">
				
					<html:errors/>

					<logic:notPresent name="errors">
						<p class="center"><bean:message key="forms.batchTask.confirm.text"/></p>
					</logic:notPresent>
				</div>
			</div>
		</div>
	</div>

  </body>
</html>

