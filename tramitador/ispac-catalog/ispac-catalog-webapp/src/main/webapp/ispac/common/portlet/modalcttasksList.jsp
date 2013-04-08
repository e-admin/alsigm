<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<!-- displayTag con formateador -->
<div id=itembeanlist>
<display:table name="CTTaskList" id="ctentity" export="false" class="tableDisplay"
	sort="list" pagesize="45" requestURI='<%=request.getContextPath() + "/showCTTasksList.do"%>'>

	<logic:iterate name="CTTaskListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

		<logic:equal name="format" property="fieldType" value="LINK">
		  	<display:column titleKey='<%=format.getTitleKey()%>'
		  					media='<%=format.getMedia()%>'
		  					headerClass='<%=format.getHeaderClass()%>'
		  					sortable='<%=format.getSortable()%>'
		  					sortProperty='<%=format.getPropertyName()%>'
		  					decorator='<%=format.getDecorator()%>'>
		  					
		  		<html:link action='<%=format.getUrl()%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="ctentity"
		  			paramProperty='<%=format.getPropertyLink() %>'>
					<%=format.formatProperty(ctentity)%>
		  		</html:link>
		  	</display:column>
		 </logic:equal>

		<logic:equal name="format" property="fieldType" value="DATA">
			<display:column titleKey='<%=format.getTitleKey()%>' 
							media='<%=format.getMedia()%>' 
							headerClass='<%=format.getHeaderClass()%>'
							sortable='<%=format.getSortable()%>' 
							decorator='<%=format.getDecorator()%>'>
					<%=format.formatProperty(ctentity)%>
			</display:column>
		</logic:equal>
	</logic:iterate>
</display:table>
</div>
