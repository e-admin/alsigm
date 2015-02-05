<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<bean:define name="StageId" id="stageid"/>


<div id="navmenutitle">
	<bean:message key="form.pstage.stageEntities.title"/>
</div>

<div id="navmenu">
	<ul class="actionsMenuList">
		<li>
			<% String URL="/showEntPFrmStage.do?StageId=" + stageid;%>
			<html:link action='<%=URL%>'><bean:message key="form.pstage.stageEntities.add"/></html:link>
		</li>
	</ul>
</div>

<html:form action='/showPFrmStages.do'>
	<html:hidden property="itemId" value='<%=stageid.toString()%>'/>

	<display:table name="ItemsList" id="item" export="true" class="tableDisplay"
  	sort="list" pagesize="45" requestURI='<%= request.getContextPath() + "/showPFrmStages.do" %>'>

		 <logic:iterate name="ItemsListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

			<logic:equal name="format" property="fieldType" value="LINKFRM">
			  	<display:column titleKey='<%=format.getTitleKey()%>' 
			  					media='<%=format.getMedia()%>' 
			  					headerClass='<%=format.getHeaderClass()%>'
			  					sortable='<%=format.getSortable()%>' 
			  					decorator='<%=format.getDecorator()%>'>
			  		<html:link action='selectObject.do?codetable=SPAC_CT_APLICACIONES&codefield=ID&valuefield=NOMBRE&decorator=/formatters/frmstages/frmappstageformatter.xml'
			  			styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="item"
			  			paramProperty='<%=format.getPropertyLink() %>'>

							<%=format.formatProperty(item)%>

			  		</html:link>

			  		<html:link action='<%=format.getUrl()+"?AppId=" %>'
			  			styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="item"
			  			paramProperty='<%=format.getPropertyLink() %>'>
								<bean:message key="form.pstage.stageEntities.default"/>
			  		</html:link>

			  	</display:column>
			 </logic:equal>

			 <logic:equal name="format" property="fieldType" value="LINK">
			  	<display:column titleKey='<%=format.getTitleKey()%>'
			  					media='<%=format.getMedia()%>'
			  					headerClass='<%=format.getHeaderClass()%>'
			  					sortable='<%=format.getSortable()%>'
			  					sortProperty='<%=format.getPropertyName()%>'
			  					decorator='<%=format.getDecorator()%>'>
			  		<html:link action='<%=format.getUrl() + "?StageId=" + stageid%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="item"
			  			paramProperty='<%=format.getPropertyLink() %>'>

							<%=format.formatProperty(item)%>

			  		</html:link>
			  	</display:column>
			 </logic:equal>

			<logic:equal name="format" property="fieldType" value="DATA">
				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
						 		headerClass='<%=format.getHeaderClass()%>'
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'>
					<nobr>
							<%=format.formatProperty(item)%>
					</nobr>
				</display:column>
			</logic:equal>

		</logic:iterate>
	</display:table>

</html:form>

