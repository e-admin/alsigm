<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<html>
<head>
<ieci:baseInvesDoc/>
<link rel="stylesheet" type="text/css" href="include/css/common.css"/>
<link rel="stylesheet" type="text/css" href="include/css/estilos.css"/>

<script type="text/javascript">

	function refresca(){
		var appBase = '<c:out value="${pageContext.request.contextPath}"/>';
		deptos.location.href = appBase + '/user/bd/groupTree.do';
	}

</script>

</head>
<div id="contenedora">
<body>
</body>
</div>
</html>