<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
	<title><fmt:message key="head.title"/></title>
	<link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon"/>
	<link rel="icon" href="img/favicon.ico" type="image/x-icon"/>
	<link rel="stylesheet" type="text/css" href="css/styles.css"/>

	<!--[if lte IE 5]>
		<link rel="stylesheet" type="text/css" href="css/styles_ie5.css"/>
	<![endif]-->	
	
	<!--[if IE 6]>
		<link rel="stylesheet" type="text/css" href="css/styles_ie6.css"/>
	<![endif]-->	
	
	<!--[if gte IE 7]>
		<link rel="stylesheet" type="text/css" href="css/styles_ie7.css"/>
	<![endif]-->	
</head>
<body>

	<div id="cabecera">
		<div id="cabecera_left">
			<h1><fmt:message key="main.company"/></h1>
		</div>
		<div id="cabecera_right">
			<h2><fmt:message key="main.title"/></h2>
		</div>
	</div>

	<div id="usuario">
		<div id="barra_usuario">
			<p class="usuario"><fmt:message key="main.title"/></p>
		</div>
	</div>
	
	<div id="contenido">
		<span class="index_title">
            <a href="<c:url value="/services/TramitacionWebService?wsdl"/>">
            	<img src="img/wsdl.gif" border="0" style="vertical-align:middle;"/>
            	<fmt:message key="index.wsdl.link"/>
            </a>
	    </span>
	</div>
	
	<div id="copyright">
		<fmt:message key="main.copyright"/>
	</div>

</body>
</html>

