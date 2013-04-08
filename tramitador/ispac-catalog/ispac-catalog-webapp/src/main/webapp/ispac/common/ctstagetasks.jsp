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
<bean:define id="stageid" name="CTStage" property="property(ID)"/>

<div id="navmenutitle">
	<bean:message key="form.fase.mainTitle"/>&nbsp;>&nbsp;<bean:message key="form.fase.buttonLabel.tasks"/>
</div>
<div id="navSubMenuTitle">
	<bean:message key="form.fase.buttonLabel.stageTasks"/>&nbsp;'<bean:write name="CTStage" property="property(NOMBRE)"/>'
</div>
<div id="navmenu" >
	<ul class="actionsMenuList">
		<li>
			<html:link action="showCTStage.do?entityId=5" paramId="regId" paramName="KeyId">
				<bean:message key="forms.button.back"/>
  			</html:link>
       	</li>
       	
       	<ispac:hasFunction var="editionMode" functions="FUNC_INV_STAGES_EDIT">
		<li>
			<%String URL = "selectObject.do?codetable=SPAC_CT_TRAMITES&codefield=ID&valuefield=NOMBRE&decorator=/formatters/choosecttaskformatter.xml&ctstageId=" + stageid;%>
			<ispac:linkframe id="SPAC_CT_TRAMITES"
			                 target="workframe"
				               action='<%=URL%>'
				               titleKey="catalog.choose.cttask"
				               width="640"
				               height="480"
				               showFrame="true"
				               captionKey="catalog.choose.cttask">
			</ispac:linkframe>
		</li>
		</ispac:hasFunction>
	</ul>
</div>

<html:form action='<%="/showCTStageTasks.do?ctstageId="+stageid%>'>
	<tiles:insert page="tiles/displaytagList.jsp" ignore="true" flush="false">
		<tiles:put name="ItemListAttr" value="ItemList"/>
		<tiles:put name="ItemFormatterAttr" value="Formatter"/>
		<tiles:put name="ItemActionAttr" value='<%="/showCTStageTasks.do?ctstageId="+stageid%>'/>
	</tiles:insert>
</html:form>


