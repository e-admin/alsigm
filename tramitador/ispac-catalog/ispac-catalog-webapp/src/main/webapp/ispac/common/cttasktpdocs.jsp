<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<script>
	function redirect(url)
	{
		submit('<%=request.getContextPath()%>' + url);
	}
</script>

<bean:define id="taskid" name="CTTask" property="property(ID)"/>

<div id="navmenutitle">
	<bean:message key="form.tasktpdocs.mainTitle"/>
</div>

<div id="navSubMenuTitle">
	<bean:message key="form.tasktpdocs.subtitle"/>&nbsp;<bean:write name="CTTask" property="property(NOMBRE)"/>
</div>

<div id="navmenu" >
	<ul class="actionsMenuList">
	       	<li>
			<html:link action="showCTTask.do?entityId=6" paramId="regId" paramName="KeyId">
				<bean:message key="forms.button.back"/>
  			</html:link>	       	
	       	</li>
	       	<ispac:hasFunction functions="FUNC_INV_TASKS_EDIT">	
	       	<li>
			<%String URL = "selectObject.do?codetable=SPAC_CT_TPDOC&codefield=ID&valuefield=NOMBRE&decorator=/formatters/choosecttpdocformatter.xml&cttaskId=" + taskid;%>
			<ispac:linkframe id="SPAC_CT_TPDOC"
						           target="workframe"
							         action='<%=URL%>'
							         titleKey="catalog.choose.cttpdoc"
							         width="640"
							         height="480"
							         showFrame="true"
							         captionKey="catalog.choose.cttpdoc">
			</ispac:linkframe>
	       	</li>
	       	</ispac:hasFunction>
	</ul>
</div>

<html:form action='<%="/showCTTPDocsList?cttaskId="+taskid%>'>
	<tiles:insert page="tiles/displaytagList.jsp" ignore="true" flush="false">
		<tiles:put name="ItemListAttr" value="ItemList"/>
		<tiles:put name="ItemFormatterAttr" value="Formatter"/>
		<tiles:put name="ItemActionAttr" value='<%="/showCTTaskTPDocs.do?cttaskId="+taskid%>'/>
	</tiles:insert>
</html:form>
