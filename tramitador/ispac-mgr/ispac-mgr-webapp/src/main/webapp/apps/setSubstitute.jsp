<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">

<c:set var="_parameters" value="${param['parameters']}"/>
	
	<jsp:useBean id="_parameters" type="java.lang.String"/>
	
<%--
	<c:set var="_formName" value="${param['formName']}"/>
	<jsp:useBean id="_formName" type="java.lang.String"/>
--%>	


	<logic:present name="Substitute">
		<c:choose>
			<c:when test="${!empty param['frame']}">
				<c:set var="_frame" value="${param['frame']}"/>
			</c:when>
			<c:otherwise>
				<c:set var="_frame" value=""/>
			</c:otherwise>
		</c:choose>
		<jsp:useBean id="_frame" type="java.lang.String"/>
		<c:choose>
			<c:when test="${!empty param['multivalueId']}">
				<c:set var="_multivalueId" value="${param['multivalueId']}"/>
				<jsp:useBean id="_multivalueId" type="java.lang.String"/>
				<logic:notEmpty scope="request" name="formName">
					<ispac:updatefields name="Substitute" parameters='<%= _parameters %>' multivalueId='<%= _multivalueId %>' frame='<%=_frame %>' formName='<%= ((String)request.getAttribute("formName"))%>'/>
				</logic:notEmpty>
				<logic:empty scope="request" name="formName">
					<ispac:updatefields name="Substitute" parameters='<%= _parameters %>' multivalueId='<%= _multivalueId %>'  frame='<%=_frame %>'/>
				</logic:empty>
			</c:when>
			<c:otherwise>
				<logic:notEmpty scope="request" name="formName">
				<ispac:updatefields name="Substitute" parameters='<%= _parameters %>'  frame='<%=_frame %>' formName='<%= ((String)request.getAttribute("formName")) %>'/>
				</logic:notEmpty>
				<logic:empty scope="request" name="formName">
					<ispac:updatefields name="Substitute" parameters='<%= _parameters %>' frame='<%=_frame %>'/>
				</logic:empty>
			</c:otherwise>
		</c:choose>
	</logic:present>
	<logic:notEmpty scope="request" name="workframe">
		<ispac:hideframe event="false" id='<%=((String)request.getAttribute("workframe")) %>'/>
	</logic:notEmpty>
	<logic:empty scope="request" name="workframe">
		<ispac:hideframe event="false" />
	</logic:empty>

</head>
<body/>
</html>