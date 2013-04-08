<?xml version="1.0" encoding="UTF-8"?>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="es.ieci.tecdoc.fwktd.web.html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<%
String rutaEstilos = (String)session.getAttribute("PARAMETRO_RUTA_ESTILOS");
if (rutaEstilos == null) rutaEstilos = "";
String rutaImagenes = (String)session.getAttribute("PARAMETRO_RUTA_IMAGENES");
if (rutaImagenes == null) rutaImagenes = "";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//ES"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="${pageContext.response.locale.language}" lang="${pageContext.response.locale.language}">
	<head>
		<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		%>
		<base href="<%= basePath %>" />

		<meta http-equiv="content-type" content="text/html;charset=iso-8859-1" />
		<meta http-equiv="Content-Style-Type" content="text/css" />

		<c:set var="title"><tiles:getAsString name="title" /></c:set>
		<title><spring:message code="${title}" /></title>

		<link rel="shortcut icon" href="img/favicon.ico" />

		<link rel="stylesheet" href="css/estilos.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="css/form.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="css/buttons.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="css/application.css" type="text/css" media="screen" />

		<!--[if lte IE 5]>
			<link rel="stylesheet" type="text/css" href="css/estilos_ie5.css"/>
		<![endif]-->

		<!--[if IE 6]>
			<link rel="stylesheet" type="text/css" href="css/estilos_ie6.css"/>
		<![endif]-->

		<!--[if IE 7]>
			<link rel="stylesheet" type="text/css" href="css/estilos_ie7.css"/>
		<![endif]-->
		<link rel="stylesheet" href="css/<%=rutaEstilos%>estilos_sigm.css" type="text/css" />

		<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
	</head>
	<body>
		<spring:htmlEscape defaultHtmlEscape="true" />

		<div id="contenedora">

			<!--Header-->
			<div id="header">
				<tiles:insertAttribute name="header"></tiles:insertAttribute>
			</div>
			<%--
			<div id="context">
				<tiles:insertAttribute name="context"></tiles:insertAttribute>
			</div>
			--%>
			<!--Main Content-->
			<div class="centered">
				<div class="contenedor_centrado">
					<div class="cuerpo">
			      		<div class="cuerporight">
			        		<div class="cuerpomid">
								<div id="container">
									<tiles:insertAttribute name="body"></tiles:insertAttribute>
								</div>
							</div>
						</div>
					</div>
	    			<div class="cuerpobt">
	     				<div class="cuerporightbt">
	       					<div class="cuerpomidbt"></div>
	    				</div>
					</div>
				</div>
			</div>

			<!--Footer-->
			<%-->
			<div id="footer">
				<tiles:insertAttribute name="footer"></tiles:insertAttribute>
			</div>
			--%>

		</div>

	</body>
</html>