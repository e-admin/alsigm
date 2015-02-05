<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<div id="navmenutitle">
	<bean:message key="title.subprocedureslist"/>
</div>

<div id="navSubMenuTitle">
</div>

<div id="navmenu">
	<ispac:hasFunction functions="FUNC_INV_SUBPROCESS_EDIT">
	<ul class="actionsMenuList">
		 <li>
       		<ispac:linkframe id="CREATENEWSUBPCD"
					 styleClass=""
				     target="workframerefresh"
					 action="newSubProcedure.do?action=begin&pcdtype=new"
					 titleKey="catalog.createnewsubpcd"
					 width="640"
					 height="380"
					 showFrame="true">
			</ispac:linkframe>
       	</li>
	</ul>
	</ispac:hasFunction>
</div>

<html:form styleId="form" action="/showSubProceduresList.do">

	<!-- BUSCADOR -->
	<tiles:insert page="../ajax/ajaxSearchTile.jsp" ignore="true" flush="false" >	
		<tiles:put name="suggestEntityId" value="22"/>
		<tiles:put name="containsTextKey" value="catalog.search.generic.containsText"/>
		<tiles:put name="suggestSqlQuery" value="TIPO=2"/>
	</tiles:insert>

	<!-- ISPAC  displayTag con formateador -->
	<display:table name="SubProceduresList" id='item' export="true" class="tableDisplay"
	  	sort="list" pagesize="45" requestURI="showSubProceduresList.do">
	
		<logic:iterate name="SubProceduresListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
	
			<logic:equal name="format" property="fieldType" value="LINK">
			  	<display:column titleKey='<%=format.getTitleKey()%>'
			  					media='<%=format.getMedia()%>'
			  					headerClass='<%=format.getHeaderClass()%>'
			  					class='<%=format.getColumnClass()%>'
			  					sortable='<%=format.getSortable()%>'
			  					sortProperty='<%=format.getPropertyName()%>'
			  					decorator='<%=format.getDecorator()%>'>
	
				<logic:equal name="format" property="tooltip" value="true">
			  		<div id='<%=format.getStyleId() + ((ieci.tdw.ispac.ispaclib.bean.ItemBean)item).getString(format.getFieldLink())%>'>
					  	</logic:equal>
					  		<html:link action='<%=format.getUrl()%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="item"
					  			paramProperty='<%=format.getPropertyLink() %>' target='<%=format.getUrlTarget()%>' >
									<%=format.formatProperty(item)%>
					  		</html:link>
					  	<logic:equal name="format" property="tooltip" value="true">
				  	</div>
		  			 <div class='tooltip for_<%=format.getStyleId() + ((ieci.tdw.ispac.ispaclib.bean.ItemBean)item).getString(format.getFieldLink())%>'>
     						<h1><bean:write name="format" property="tooltipTitle"/></h1>
     						<p><bean:write name="item" property='<%=format.getTooltipText()%>'/></p>
 					</div>
		  		</logic:equal>
	
			  	</display:column>
			 </logic:equal>
	
	
			<logic:equal name="format" property="fieldType" value="DATA">
				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
						 		headerClass='<%=format.getHeaderClass()%>'
						 		class='<%=format.getColumnClass()%>'
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'>
	
					<%=format.formatProperty(item)%>
	
				</display:column>
			</logic:equal>
		</logic:iterate>

		<display:column titleKey='form.pproc.label.status' sortable='true' media='html' class="width10percent">
				<%
					String htmlState = (String)((ieci.tdw.ispac.ispaclib.bean.ItemBean)item).getString("ESTADO");
					int iHtmlState = Integer.parseInt(htmlState);
					if(iHtmlState == ieci.tdw.ispac.api.item.IProcedure.PCD_STATE_DRAFT){
				%>
					<div id="procBorrador">
						<bean:message key="procedure.versions.draft"/>
					</div>
				<%
					}else if(iHtmlState == ieci.tdw.ispac.api.item.IProcedure.PCD_STATE_CURRENT){
				%>
					<div id="procVigente">
						<bean:message key="procedure.versions.current"/>
					</div>
				<%
					}else if(iHtmlState == ieci.tdw.ispac.api.item.IProcedure.PCD_STATE_OLD){
				%>
					<div id="procHistorico">
						<bean:message key="procedure.versions.archived"/>
					</div>
				<%
					}
				%>
		</display:column>
		<display:column titleKey='form.pproc.label.status' sortable='true' media='excel xml' class="width10percent">
				<%
					String otherState = (String)((ieci.tdw.ispac.ispaclib.bean.ItemBean)item).getString("ESTADO");
					int iOtherState = Integer.parseInt(otherState);
					if(iOtherState == ieci.tdw.ispac.api.item.IProcedure.PCD_STATE_DRAFT){
				%>
						<bean:message key="procedure.versions.draft"/>
				<%
					}else if(iOtherState == ieci.tdw.ispac.api.item.IProcedure.PCD_STATE_CURRENT){
				%>
						<bean:message key="procedure.versions.current"/>
				<%
					}else if(iOtherState == ieci.tdw.ispac.api.item.IProcedure.PCD_STATE_OLD){
				%>
						<bean:message key="procedure.versions.archived"/>
				<%
					}
				%>
		</display:column>
	
	</display:table>
</html:form>
