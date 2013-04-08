<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<?xml version="1.0" encoding="iso-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Ayuda</title>
		<meta http-equiv="" content="text/html; charset=iso-8859-1" />
	
		
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilosHelp.css"/>'/>
		<!--[if lte IE 5]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilosHelp_ie5.css"/>'/>
		<![endif]-->	
	
		<!--[if IE 6]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilosHelp_ie6.css"/>'/>
		<![endif]-->	
	
		<!--[if gte IE 7]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilosHelp_ie7.css"/>'/>
		<![endif]-->	
	

		<script language="JavaScript" type="text/JavaScript">
		<!--
		function close_window() {
		    window.close();
		}
		//-->
		</script>
	</head>
	<body>
		<div class="ficha">
			<div class="encabezado_ficha">
				<div class="titulo_ficha">
					<h4><bean:message key="header.help" /></h4>
					<div class="acciones_ficha"><a href="#" id="btnCancel"
					onclick="close_window()"
					class="btnCancel"><bean:message key="common.message.close" /></a></div>

				</div><%--fin titulo ficha --%>
			</div><%--fin encabezado ficha --%>
		<div class="cuerpo_ficha">
			<div class="seccion_ficha">
				<logic:present name="contenido">
					<c:out value="${requestScope['contenido']}" escapeXml="false"/>
				</logic:present>
				<logic:notPresent name="contenido">
					<div class="infoError">
						<c:set var="tipoObj"><bean:message key='<%="help.tipo_obj."+request.getParameter("tipoObj") %>'/></c:set>
						<bean:define id="tipoObj" name="tipoObj" type="java.lang.String"></bean:define>
						<bean:message key="error.loading.help" arg0="<%=tipoObj%>"   />
					</div>
				</logic:notPresent>	
			</div>
		</div>
	</div>
		
	</body>
</html>