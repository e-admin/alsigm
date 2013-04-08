<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
  <title><fmt:message key="head.title"/></title>
  <link rel="stylesheet" href="<c:url value="/ispac/css/styles.css"/>"/>
  <link rel="shortcut icon" href="<c:url value="/ispac/img/favicon.ico"/>" type="image/x-icon"/>
  <link rel="icon" href="<c:url value="/ispac/img/favicon.ico"/>" type="image/x-icon"/>
</head>
<body>
	<div id="cabecera">
		<div id="logo">
			<img src="<c:url value="/ispac/img/minetur.jpg"/>" alt="GOBIERNO DE ESPAÑA. MINISTERIO DE INDUSTRIA, ENERGÍA Y TURISMO " />
			<img src="<c:url value="/ispac/img/logo.gif"/>" border="0" />
		</div>
		<div id="app">
		    <fmt:message key="main.title"/>
		</div>
	</div>
	
	<div id="contenido">
		<span class="index_title">
	        <fmt:message key="index.title"/><br/>
	        <fmt:message key="index.message"/><br/><br/>
	    </span>
	</div>
	
	<div id="pie">
		&nbsp;
	</div>
</body>
</html>
