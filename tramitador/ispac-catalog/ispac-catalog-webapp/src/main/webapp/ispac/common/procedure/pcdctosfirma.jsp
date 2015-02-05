<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<bean:define name="pcdId" id="pcdId"/>
<bean:define name="entityId" id="entityId"/>
<bean:define name="sqlquery" id="sqlquery"/>

<div class="tabButtons">
	<ispac:hasFunction functions="FUNC_INV_PROCEDURES_EDIT">
	<ul class="tabButtonList">
		<li>
			<ispac:linkframe id="CTOSFIRMAMANAGER"
				styleClass=""
     			target="workframe"
				action='<%="selectObject.do?codetable=SPAC_CTOS_FIRMA_CABECERA&codefield=ID&caption=select.signProcess.specific.caption&sqlquery="+sqlquery+"&valuefield=DESCRIPCION&decorator=/formatters/ctosfirma/choosectofirmaformatter.xml&pcdId=" + pcdId %>'
				titleKey="catalog.add"
				width="640"
				height="480"
				showFrame="true">
			</ispac:linkframe>
		</li>
	</ul>
	</ispac:hasFunction>
</div>
 
<div class="tabContent">

	<display:table name="CtosFirmaList" id="item" export="true" class="tableDisplay"
  	sort="list" pagesize="45" requestURI="/showProcedureEntity.do">

		 <logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

			<logic:equal name="format" property="fieldType" value="LINK">
			  	<display:column titleKey='<%=format.getTitleKey()%>'
			  					media='<%=format.getMedia()%>'
			  					headerClass='<%=format.getHeaderClass()%>'
			  					sortable='<%=format.getSortable()%>'
			  					sortProperty='<%=format.getPropertyName()%>'
			  					decorator='<%=format.getDecorator()%>'
			  					class='<%=format.getColumnClass()%>'>
			  					
			  		<html:link action='<%=format.getUrl()%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="item"
			  			paramProperty='<%=format.getPropertyLink() %>'>

						<%=format.formatProperty(item)%>
	
			  		</html:link>
			  	</display:column>
			 </logic:equal>
			 
			 <logic:equal name="format" property="fieldType" value="LINK_BT">
			  	<display:column titleKey='<%=format.getTitleKey()%>'
			  					media='<%=format.getMedia()%>'
			  					headerClass='<%=format.getHeaderClass()%>'
			  					sortable='<%=format.getSortable()%>'
			  					sortProperty='<%=format.getPropertyName()%>'
			  					decorator='<%=format.getDecorator()%>'
			  					class='<%=format.getColumnClass()%>'>
			  					
			  		<html:link action='<%=format.getUrl() + "&regId=" + pcdId + "&entityId=" + entityId%>'
			  			styleClass='<%=format.getStyleClass()%>' 
			  			paramId='<%=format.getId()%>' paramName="item"
			  			paramProperty='<%=format.getPropertyLink() %>'>
	
			  			<bean:message key='<%=format.getPropertyValueKey()%>' />
	
			  		</html:link>
			  	</display:column>
			 </logic:equal>
	
			<logic:equal name="format" property="fieldType" value="DATA">
				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								class='<%=format.getColumnClass()%>'
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'
								comparator='<%=format.getComparator()%>'>
						<%=format.formatProperty(item)%>
				</display:column>
			</logic:equal>
			
			<logic:equal name="format" property="fieldType" value="TYPE">
				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								class='<%=format.getColumnClass()%>'
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'>
						
						<% if ("1".equals(format.formatProperty(item))) { %>
							<bean:message key="form.signProcess.generic"/>							
						<% } else { %>							
							<bean:message key="form.signProcess.specific"/>
						<% } %>
	
				</display:column>
			</logic:equal>

		</logic:iterate>
	</display:table>

</div> 
