<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">

	<c:set var="_refresh" value="${param['refresh']}"/>
	<jsp:useBean id="_refresh" type="java.lang.String"/>

	<ispac:hideframe event="false" refresh='<%=_refresh%>'/>

</head>
<body/>
</html>