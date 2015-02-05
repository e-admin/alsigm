<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<%-- Ejemplo
<logic:present name="ThirdParty">
<ispac:updatefields name="ThirdParty" parameters="workframe"/>
</logic:present>
--%>
<%-- Ejemplo
<logic:present name="ThirdParty">
	<logic:iterate name="workframe" id="parameter">
  	<bean:define name="parameter" id="field" property="key"/>

  	<bean:define name="parameter" id="property" property="value"/>

  	<bean:define id="name" value='<%="property(" + property.toString() + ")"%>'/>

		<logic:notEmpty name="ThirdParty" property='<%= name.toString()%>'>
  		<bean:define name="ThirdParty" id="value" property='<%= name.toString()%>'/>
			<ispac:updatefield field='<%= field.toString()%>' value='<%= value.toString()%>'/>
		</logic:notEmpty>

		<logic:empty name="ThirdParty" property='<%= name.toString()%>'>
			<ispac:updatefield field='<%= field.toString()%>' value=""/>
		</logic:empty>

		
	</logic:iterate>
</logic:present>
--%>
<%
	String parameters = request.getParameter("parameters");
%>
<logic:present name="ThirdParty">
	<logic:present name='<%= parameters %>'>
		<logic:iterate name='<%= parameters %>' id="parameter">
	  	<bean:define name="parameter" id="field" property="key"/>
	  	<bean:define name="parameter" id="property" property="value"/>
			<ispac:updatefield name="ThirdParty" field='<%= field.toString()%>' property='<%= property.toString()%>'/>
		</logic:iterate>
	</logic:present>
</logic:present>
<ispac:hideframe event="false"/>
</head>
<body/>
</html>