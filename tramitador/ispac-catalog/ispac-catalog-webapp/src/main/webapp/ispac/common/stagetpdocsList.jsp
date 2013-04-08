<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<bean:parameter name="StageId" id="stageid"/>

<div id="navmenutitle">
	<bean:message key="procedure.stage.document.title"/>
</div>
<div id="navmenu">
	<ul class="actionsMenuList">
		<li>
			<% String URL = "/selectObject.do?codetable=SPAC_CT_TPDOC&codefield=ID&valuefield=NOMBRE&decorator=/formatters/procedure/tpdocslistformatter.xml&caption=menu.doctypes&StageId=" + stageid;%>
			<html:link action='<%=URL%>'><bean:message key="procedure.stage.document.add"/></html:link>
		</li>
	</ul>
</div>

<html:form action='/showStageTPDocsList.do'>

	<display:table name="ItemsList" id="item" export="true" class="tableDisplay"
  	sort="list" pagesize="45" requestURI='<%= request.getContextPath() + "/showStageTPDocsList.do?StageId=" + stageid %>'>

		 <logic:iterate name="ItemsListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

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

					<%=format.formatProperty(item)%>

				</display:column>
			</logic:equal>

		</logic:iterate>
	</display:table>

</html:form>

