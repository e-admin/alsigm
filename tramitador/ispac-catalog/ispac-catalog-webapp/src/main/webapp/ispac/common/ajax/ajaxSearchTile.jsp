<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<div id="buscador">

<tiles:useAttribute id="containsTextKey" name="containsTextKey" ignore="true"/>
<logic:empty name="containsTextKey">
	<bean:message key="catalog.search.generic.containsText"/>:
</logic:empty>
<logic:notEmpty name="containsTextKey">
	<bean:message name="containsTextKey"/>:
</logic:notEmpty>
&nbsp;

<tiles:useAttribute id="suggestStrutsAction" name="suggestStrutsAction" ignore="true"/>
<tiles:useAttribute id="suggestEntityId" name="suggestEntityId"/>
<tiles:useAttribute id="suggestSearchField" name="suggestSearchField" ignore="true"/>
<tiles:useAttribute id="suggestSqlQuery" name="suggestSqlQuery" ignore="true"/>

<bean:define id="finalAction" type="java.lang.String" value=""/>

<logic:notEmpty name="suggestStrutsAction">
	<%
		finalAction = suggestStrutsAction.toString();
	%>
</logic:notEmpty>

<logic:empty name="suggestStrutsAction">
	<%
		finalAction = "/ajaxSearchEntitySuggest.do";
	%>
</logic:empty>

<logic:notEmpty name="suggestEntityId">
	<%
		finalAction += "?extraParam=" + suggestEntityId.toString();
	%>
</logic:notEmpty>

<logic:notEmpty name="suggestSearchField">
	<%
		finalAction += "&searchField=" + suggestSearchField.toString(); 
	%>
</logic:notEmpty>

<logic:notEmpty name="suggestSqlQuery">
	<%
		finalAction += "&sqlQuery=" + java.net.URLEncoder.encode(suggestSqlQuery.toString()); 
	%>
</logic:notEmpty>

<ispac:suggestText property="property(criterio)" 
	styleId="suggestedSearchField" 
	suggestAction='<%=finalAction%>'
	paramName="filter"
	styleClass="input" 
	suggestListStyleClass="autocompleteList"
	numRows="10"
	minChars="1"
	showButton="false"
	size="35" readonly="false"/>

	&nbsp;&nbsp;
		
	<html:submit property="filtrar" styleClass="form_button_white">
		<bean:message key="search.button"/>
	</html:submit>

</div>