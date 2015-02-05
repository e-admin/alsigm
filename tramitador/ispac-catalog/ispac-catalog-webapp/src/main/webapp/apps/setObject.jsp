<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html">

<%
	String parameters = request.getParameter("parameters");
	String Refresh = (String)request.getAttribute("Refresh");
	if (Refresh==null) Refresh="false";
	String formName = request.getParameter("formName");
	if (formName==null) formName="defaultForm";
	String nameFrame=request.getParameter("nameFrame");
	if (nameFrame==null) nameFrame="workframe";
	
%>

<logic:present name="Substitute">
	<ispac:updatefields name="Substitute" parameters='<%=parameters%>' formName='<%=formName%>'/>
</logic:present>

<ispac:hideframe id='<%=nameFrame.toString()%>' event="false" refresh='<%=Refresh.toString()%>'/>

</head>
<body/>
</html>