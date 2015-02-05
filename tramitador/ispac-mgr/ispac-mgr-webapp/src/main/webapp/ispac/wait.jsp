<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<title><bean:message key="head.title"/></title>
	<meta http-equiv="Content Type" content="text/html; charset=iso-8859-1" />
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
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'> </script>
	<ispac:javascriptLanguage/>
	
	<script type="text/javascript">

		$(document).ready(function() {
			$("#btnCancel").click(function() {
				if (parent.hideFrame) {
					parent.hideFrame("configFrame");
					parent.hideFrame("workframe");
					parent.hideFrame("workframesearch");
				} else if (top.hideFrame) {
					top.hideFrame("configFrame");
					top.hideFrame("workframe");
					top.hideFrame("workframesearch");
				}
			});
		});
		
		/*positionMiddleScreen('contenido');*/
	</script>
</head>
<body class="centrado">

	<div id="contenido" class="move">

		<div class="ficha"> 
		
			<div class="encabezado_ficha">
				<div class="titulo_ficha">
					<div class="acciones_ficha">
						<a href="#" id="btnCancel" class="btnCancel"><bean:message key="common.message.cancel"/></a>
					</div>
				</fiv>
			</div>

			<div class="cuerpo_ficha">
				<div class="seccion_ficha popUp">
					<p class="aviso">
						<span><bean:message key="wait.text"/></span>
					</p>
				</div> <!-- fin seccion ficha-->

			</div> <!-- fin cuerpo_ficha -->

		</div> <!-- fin ficha -->

	</div>  <!-- fin contenido -->

</body>
</html>