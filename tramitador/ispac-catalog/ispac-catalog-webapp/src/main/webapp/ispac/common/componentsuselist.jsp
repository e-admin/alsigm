<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<div id="navmenutitle">
	<bean:message key="component.title"/>
</div>

<div id="navSubMenuTitle">
  <bean:define id="title" name="title"/>
  <bean:define id="argument" name="argument"/>
	<bean:message key='<%=title.toString()%>' arg0='<%=argument.toString()%>' />
</div>

<div id="navmenu">
	<ul class="actionsMenuList">
		<li>
			<a href='<bean:write name="action"/>'>
				<nobr><bean:message key="forms.button.back"/></nobr>
			</a>
	  </li>
	</ul>
</div>

<logic:present name="entities">
	<div id="componenttitle">
		<bean:message key="component.entities"/>
	</div>
	<tiles:insert page="tiles/displaytagList.jsp" ignore="true" flush="false">
		<tiles:put name="ItemListAttr" value="entities"/>
		<tiles:put name="ItemFormatterAttr" value="entitiesformatter"/>
		<tiles:put name="ItemActionAttr" value=""/>
	</tiles:insert>
</logic:present>

<logic:present name="procedures">
	<div id="componenttitle">
		<bean:message key="component.procedures"/>
	</div>
	<tiles:insert page="tiles/displaytagList.jsp" ignore="true" flush="false">
		<tiles:put name="ItemListAttr" value="procedures"/>
		<tiles:put name="ItemFormatterAttr" value="proceduresformatter"/>
		<tiles:put name="ItemActionAttr" value=""/>
	</tiles:insert>
</logic:present>

<logic:present name="stages">
	<div id="componenttitle">
		<bean:message key="component.stages"/>
	</div>
	<tiles:insert page="tiles/displaytagList.jsp" ignore="true" flush="false">
		<tiles:put name="ItemListAttr" value="stages"/>
		<tiles:put name="ItemFormatterAttr" value="stagesformatter"/>
		<tiles:put name="ItemActionAttr" value=""/>
	</tiles:insert>
</logic:present>

<logic:present name="tasks">
	<div id="componenttitle">
		<bean:message key="component.tasks"/>
	</div>
	<tiles:insert page="tiles/displaytagList.jsp" ignore="true" flush="false">
		<tiles:put name="ItemListAttr" value="tasks"/>
		<tiles:put name="ItemFormatterAttr" value="tasksformatter"/>
		<tiles:put name="ItemActionAttr" value=""/>
	</tiles:insert>
</logic:present>

<logic:present name="activities">
	<div id="componenttitle">
		<bean:message key="component.activities"/>
	</div>
	<tiles:insert page="tiles/displaytagList.jsp" ignore="true" flush="false">
		<tiles:put name="ItemListAttr" value="activities"/>
		<tiles:put name="ItemFormatterAttr" value="activitiesformatter"/>
		<tiles:put name="ItemActionAttr" value=""/>
	</tiles:insert>
</logic:present>

<logic:present name="flows">
	<div id="componenttitle">
		<bean:message key="component.flows"/>
	</div>
	<tiles:insert page="tiles/displaytagList.jsp" ignore="true" flush="false">
		<tiles:put name="ItemListAttr" value="flows"/>
		<tiles:put name="ItemFormatterAttr" value="flowsformatter"/>
		<tiles:put name="ItemActionAttr" value=""/>
	</tiles:insert>
</logic:present>

<logic:present name="procedureentities">
	<div id="componenttitle">
		<bean:message key="component.entities"/>
	</div>
	<tiles:insert page="tiles/displaytagList.jsp" ignore="true" flush="false">
		<tiles:put name="ItemListAttr" value="procedureentities"/>
		<tiles:put name="ItemFormatterAttr" value="procedureentitiesformatter"/>
		<tiles:put name="ItemActionAttr" value=""/>
	</tiles:insert>
</logic:present>

<c:if test="${(procedureentityforms != null) or(stageentityforms != null) or (taskentityforms != null)}">
	<div id="componenttitle">
		<bean:message key="component.forms"/>
	</div>
	<logic:present name="procedureentityforms">
		<tiles:insert page="tiles/displaytagList.jsp" ignore="true" flush="false">
			<tiles:put name="ItemListAttr" value="procedureentityforms"/>
			<tiles:put name="ItemFormatterAttr" value="procedureentityformsformatter"/>
			<tiles:put name="ItemActionAttr" value=""/>
		</tiles:insert>
	</logic:present>
	<logic:present name="stageentityforms">
		<tiles:insert page="tiles/displaytagList.jsp" ignore="true" flush="false">
			<tiles:put name="ItemListAttr" value="stageentityforms"/>
			<tiles:put name="ItemFormatterAttr" value="stageentityformsformatter"/>
			<tiles:put name="ItemActionAttr" value=""/>
		</tiles:insert>
	</logic:present>
	<logic:present name="taskentityforms">
		<tiles:insert page="tiles/displaytagList.jsp" ignore="true" flush="false">
			<tiles:put name="ItemListAttr" value="taskentityforms"/>
			<tiles:put name="ItemFormatterAttr" value="taskentityformsformatter"/>
			<tiles:put name="ItemActionAttr" value=""/>
		</tiles:insert>
	</logic:present>
</c:if>

<c:if test="${(procedureentityvisible != null) or(stageentityvisible != null) or (taskentityvisible != null)}">
	<div id="componenttitle">
		<bean:message key="component.entities.visible"/>
	</div>
	<logic:present name="procedureentityvisible">
		<tiles:insert page="tiles/displaytagList.jsp" ignore="true" flush="false">
			<tiles:put name="ItemListAttr" value="procedureentityvisible"/>
			<tiles:put name="ItemFormatterAttr" value="procedureentityvisibleformatter"/>
			<tiles:put name="ItemActionAttr" value=""/>
		</tiles:insert>
	</logic:present>
	<logic:present name="stageentityvisible">
		<tiles:insert page="tiles/displaytagList.jsp" ignore="true" flush="false">
			<tiles:put name="ItemListAttr" value="stageentityvisible"/>
			<tiles:put name="ItemFormatterAttr" value="stageentityvisibleformatter"/>
			<tiles:put name="ItemActionAttr" value=""/>
		</tiles:insert>
	</logic:present>
	<logic:present name="taskentityvisible">
		<tiles:insert page="tiles/displaytagList.jsp" ignore="true" flush="false">
			<tiles:put name="ItemListAttr" value="taskentityvisible"/>
			<tiles:put name="ItemFormatterAttr" value="taskentityvisibleformatter"/>
			<tiles:put name="ItemActionAttr" value=""/>
		</tiles:insert>
	</logic:present>
</c:if>

<logic:present name="cttasks">
	<div id="componenttitle">
		<bean:message key="component.tasks"/>
	</div>
	<tiles:insert page="tiles/displaytagList.jsp" ignore="true" flush="false">
		<tiles:put name="ItemListAttr" value="cttasks"/>
		<tiles:put name="ItemFormatterAttr" value="cttasksformatter"/>
		<tiles:put name="ItemActionAttr" value=""/>
	</tiles:insert>
</logic:present>

<logic:present name="searchforms">
	<div id="componenttitle">
		<bean:message key="component.searchfrm"/>
	</div>
	<tiles:insert page="tiles/displaytagList.jsp" ignore="true" flush="false">
		<tiles:put name="ItemListAttr" value="searchforms"/>
		<tiles:put name="ItemFormatterAttr" value="searchformformatter"/>
		<tiles:put name="ItemActionAttr" value=""/>
	</tiles:insert>
</logic:present>

<logic:present name="circuits">
	<div id="componenttitle">
		<bean:message key="component.circuitsInstance"/>
	</div>
	<tiles:insert page="tiles/displaytagList.jsp" ignore="true" flush="false">
		<tiles:put name="ItemListAttr" value="circuits"/>
		<tiles:put name="ItemFormatterAttr" value="circuitsIntanceformatter"/>
		<tiles:put name="ItemActionAttr" value=""/>
	</tiles:insert>
</logic:present>
