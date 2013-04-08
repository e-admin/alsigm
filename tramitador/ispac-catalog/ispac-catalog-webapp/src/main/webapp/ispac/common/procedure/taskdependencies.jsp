<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
 
<bean:define name="entityId" id="entityId"/>
<bean:define name="regId" id="regId"/>
<bean:define name="sqlquery" id="sqlquery"/>

<div class="tabButtons">
	<ispac:hasFunction functions="FUNC_INV_PROCEDURES_EDIT">
	<ul class="tabButtonList">
		<li>
			<ispac:linkframe id="DEPSMANAGER"
				styleClass=""
				target="workframe"
				action='<%="selectObject.do?codetable=SPAC_P_TRAMITES&codefield=ID&caption=select.dependencies.caption&sqlquery="+sqlquery+"&valuefield=NOMBRE&decorator=/formatters/dependencies/choosedependencyformatter.xml&ptaskId=" + regId %>'
				titleKey="procedure.tabs.dependencies.add"
				width="640"
				height="480"
				showFrame="true">
			</ispac:linkframe>
		</li>
	</ul>
	</ispac:hasFunction>
</div>

<div class="tabContent">
	<display:table name="dependencyList" id="itemobj" export="false" class="tableDisplay" sort="list">

		<logic:present name="itemobj">
		
			<bean:define id='item' name='itemobj' type='ieci.tdw.ispac.ispaclib.bean.ObjectBean'/>
		
			<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
			
				<logic:equal name="format" property="fieldType" value="DATA">
				
					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>'
							 		headerClass='<%=format.getHeaderClass()%>'
									sortable='<%=format.getSortable()%>'
									decorator='<%=format.getDecorator()%>'
									class='<%=format.getColumnClass()%>'>									
						<%=format.formatProperty(item)%>
					</display:column>
				</logic:equal>
		
		 		<logic:equal name="format" property="fieldType" value="BUTTON">
				  	<display:column titleKey='<%=format.getTitleKey()%>'
				  					media='<%=format.getMedia()%>'
				  					headerClass='<%=format.getHeaderClass()%>'
				  					sortable='<%=format.getSortable()%>'
				  					sortProperty='<%=format.getPropertyName()%>'
				  					decorator='<%=format.getDecorator()%>'
				  					class='<%=format.getColumnClass()%>'>
				  		<html:link action='<%=format.getUrl() + "&regId=" + regId + "&entityId=" + entityId%>'
				  				styleClass='<%=format.getStyleClass()%>' 
				  				paramId='<%=format.getId()%>' 
				  				paramName="item"
				  				paramProperty='<%=format.getPropertyLink() %>'>
				  			<bean:message key='<%=format.getPropertyValueKey()%>' />
				  		</html:link>
				  	</display:column>
				</logic:equal>

				
			</logic:iterate>
		</logic:present>
	</display:table>
</div>
