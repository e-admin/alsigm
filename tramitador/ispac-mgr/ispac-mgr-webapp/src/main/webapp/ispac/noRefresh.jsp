<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<html>
<head>
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
		

	
	
	
	<c:set var="wait"><ispac:rewrite page="wait.jsp"/></c:set>
	<jsp:useBean id="wait" type="java.lang.String"/>
	

  
<script>
	
	function hideFrameNoRefresh(){
		top.hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>');
	}


	if(top.document.getElementById("layer")!=null && top.document.getElementById("layer").style.display!='none' ){
	
			<ispac:message  generateTagScript="false" jsExecute="hideFrameNoRefresh" noRemove="true"/>	
	}


	else{
			<ispac:message  location="top"  generateTagScript="false"/>
	}
	
	
	
		
	
	
</script>
</head>
<body></body>
</html>