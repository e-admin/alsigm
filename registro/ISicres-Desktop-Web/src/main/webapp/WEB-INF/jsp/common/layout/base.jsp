<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="es.ieci.tecdoc.fwktd.web.html" prefix="html" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//ES"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="${pageContext.response.locale.language}" lang="${pageContext.response.locale.language}">
	<head>
		<html:base />

		<meta http-equiv="content-type" content="text/html;charset=iso-8859-1" />
		<meta http-equiv="Content-Style-Type" content="text/css" />

		<title><tiles:getAsString name="title" /></title>

 		<link rel="stylesheet" href="css/terceros/reset.css" type="text/css" media="screen" />
 		<link rel="stylesheet" href="css/terceros/form.css" type="text/css" media="screen" />
 		<link rel="stylesheet" href="css/terceros/table.css" type="text/css" media="screen" />
 		<link rel="stylesheet" href="css/terceros/estilos.css" type="text/css" media="screen" />
 		<link rel="stylesheet" href="css/terceros/terceros.css" type="text/css" media="screen" />
	 	<link rel="stylesheet" href="css/smoothness/jquery-ui-1.8.16.custom.css" type="text/css" media="screen" />
	 	<link rel="stylesheet" href="css/terceros/fancybox/jquery.fancybox-1.3.4.css" type="text/css" media="screen" />

	 	<script type="text/javascript" src="scripts/jquery-1.6.2.min.js"></script>
	 	<script type="text/javascript">jQuery.noConflict();</script>
	 	<script type="text/javascript" src="scripts/jquery-ui-1.8.16.custom.min.js"></script>
	 	<script type="text/javascript" src="scripts/terceros/jquery.fancybox-1.3.4.pack.js"></script>

	 	<script type="text/javascript" src="scripts/terceros/application.js"></script>

	 	<script type='text/javascript' src="scripts/dwr/interface/dwrService.js"></script>
  		<script type='text/javascript' src="scripts/dwr/engine.js"></script>
  		<script type='text/javascript' src="scripts/dwr/util.js"></script>


	</head>
	<body>
		<spring:htmlEscape defaultHtmlEscape="true" />
		<tiles:insertAttribute name="body"></tiles:insertAttribute>
	</body>
</html>