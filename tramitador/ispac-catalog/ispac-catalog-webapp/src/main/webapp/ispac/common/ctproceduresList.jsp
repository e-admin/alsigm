<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<script>
	function redirect(url)
	{
		submit('<%=request.getContextPath()%>' + url);
	}
</script>
<div id="navmenutitle">
	<bean:message key="title.procedurelist"/>
</div>
<div id="navSubMenuTitle">
</div>
<div id="navmenu">
	<ispac:hasFunction functions="FUNC_INV_PROCEDURES_EDIT">
	<ul class="actionsMenuList">
		 <li>
       		<ispac:linkframe id="CREATENEWPCD"
					 styleClass=""
				     target="workframe"
					 action="newProcedure.do?forward=new&pcdtype=new"
					 titleKey="catalog.createnewpcd"
					 width="640"
					 height="380"
					 showFrame="true">
			</ispac:linkframe>
       	</li>
	</ul>
	</ispac:hasFunction>
</div>
<html:form styleId="form" action="/showCTProceduresList.do">

	<!-- BUSCADOR -->
	<tiles:insert page="ajax/ajaxSearchTile.jsp" ignore="true" flush="false" >
		<tiles:put name="suggestStrutsAction" value="/ajaxSearchProcedureSuggest.do"/>
		<tiles:put name="suggestEntityId" value="22"/>
	</tiles:insert>

<!-- ISPAC  displayTag con formateador -->
<display:table name="CTProceduresList" id='item' export="true" class="tableDisplay"
  	sort="list" pagesize="45" requestURI='<%= request.getContextPath()+ "/showCTProceduresList.do" %>'>

	<display:column titleKey='form.pproc.label.status' sortable='true' media='html' class="width10percent">
			<%
				String estado_html = (String)((ieci.tdw.ispac.ispaclib.bean.ItemBean)item).getString("PPROC:ESTADO");
				int iestado_html = Integer.parseInt(estado_html);
				if(iestado_html == ieci.tdw.ispac.api.item.IProcedure.PCD_STATE_DRAFT){
			%>
				<div id="procBorrador">
					<bean:message key="procedure.versions.draft"/>
				</div>
			<%
				}else if(iestado_html == ieci.tdw.ispac.api.item.IProcedure.PCD_STATE_CURRENT){
			%>
				<div id="procVigente">
					<bean:message key="procedure.versions.current"/>
				</div>
			<%
				}else if(iestado_html == ieci.tdw.ispac.api.item.IProcedure.PCD_STATE_OLD){
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
				String estado_export = (String)((ieci.tdw.ispac.ispaclib.bean.ItemBean)item).getString("PPROC:ESTADO");
				int iestado_export = Integer.parseInt(estado_export);
				if(iestado_export == ieci.tdw.ispac.api.item.IProcedure.PCD_STATE_DRAFT){
			%>
					<bean:message key="procedure.versions.draft"/>
			<%
				}else if(iestado_export == ieci.tdw.ispac.api.item.IProcedure.PCD_STATE_CURRENT){
			%>
					<bean:message key="procedure.versions.current"/>
			<%
				}else if(iestado_export == ieci.tdw.ispac.api.item.IProcedure.PCD_STATE_OLD){
			%>
					<bean:message key="procedure.versions.archived"/>
			<%
				}
			%>
	</display:column>
	 <logic:iterate name="CTProceduresListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

		<logic:equal name="format" property="fieldType" value="LABELCHECKBOX">
			<display:column titleKey='<%=format.getTitleKey()%>'
							media='<%=format.getMedia()%>'
							headerClass='<%=format.getHeaderClass()%>'
							sortable='<%=format.getSortable()%>'
							class='<%=format.getColumnClass()%>'
							decorator='<%=format.getDecorator()%>'>
				<div class="label">
					<nobr>
						<html:multibox property="multibox">
							<bean:write name="item" property='<%=format.getPropertyLink() %>'/>
						</html:multibox>
						<%=format.formatProperty(item)%>
					</nobr>
				</div>
			</display:column>
	    </logic:equal>

		<logic:equal name="format" property="fieldType" value="CHECKBOX">
			<display:column titleKey='<%=format.getTitleKey()%>'
							media='<%=format.getMedia()%>'
							headerClass='<%=format.getHeaderClass()%>'
							sortable='<%=format.getSortable()%>'
							class='<%=format.getColumnClass()%>'
							decorator='<%=format.getDecorator()%>'>
				<html:multibox property="multibox">
				<%=format.formatProperty(item)%>
				</html:multibox>
			</display:column>
	  	</logic:equal>

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
		  	</logic:equal>
		  		<logic:equal name="format" property="tooltip" value="true">
		  			 <div class='tooltip for_<%=format.getStyleId() + ((ieci.tdw.ispac.ispaclib.bean.ItemBean)item).getString(format.getFieldLink())%>'>
     						<h1><bean:write name="format" property="tooltipTitle"/></h1>
     						<p><bean:write name="item" property='<%=format.getTooltipText()%>'/>
     						</p>
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

		<logic:equal name="format" property="fieldType" value="ROWNUM">
			<display:column titleKey='<%=format.getTitleKey()%>'
							media='<%=format.getMedia()%>'
							headerClass='<%=format.getHeaderClass()%>'
							class='<%=format.getColumnClass()%>'
							sortable='<%=format.getSortable()%>'
							decorator='<%=format.getDecorator()%>'>

				 <%=item_rowNum%>

			</display:column>
		</logic:equal>

		<logic:equal name="format" property="fieldType" value="ROWNUMLINK">
		  	<display:column titleKey='<%=format.getTitleKey()%>'
		  					media='<%=format.getMedia()%>'
		  					headerClass='<%=format.getHeaderClass()%>'
		  					class='<%=format.getColumnClass()%>'
		  					sortable='<%=format.getSortable()%>'
		  					decorator='<%=format.getDecorator()%>'>
		  		<html:link action='<%=format.getUrl()%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="item"
		  			paramProperty='<%=format.getPropertyLink() %>'>
					<%=item_rowNum%>
		  		</html:link>
		  	</display:column>
		 </logic:equal>

		<logic:equal name="format" property="fieldType" value="MODORDER">
					<display:column titleKey='<%=format.getTitleKey()%>' media='<%=format.getMedia()%>' headerClass='<%=format.getHeaderClass()%>'  class='<%=format.getColumnClass()%>'
						sortable='<%=format.getSortable()%>' decorator='<%=format.getDecorator()%>'>

							<center>
								<html:link
									action='<%=format.getUrl() + "?modOrder=INC&order=" + pageContext.getAttribute("item_rowNum")%>'
									styleClass="imgup" paramId='<%=format.getId()%>'
									paramName="item" paramProperty='<%=format.getPropertyLink() %>' />
								<html:link
									action='<%=format.getUrl() + "?modOrder=DEC&order=" + pageContext.getAttribute("item_rowNum")%>'
									styleClass="imgdown" paramId='<%=format.getId()%>'
									paramName="item" paramProperty='<%=format.getPropertyLink() %>' />
							</center>
					</display:column>
		</logic:equal>
	</logic:iterate>


</display:table>
<!-- displayTag -->
</html:form>
<br/>
