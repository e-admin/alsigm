<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<table cellpadding="5" cellspacing="0" border="0" width="100%" align="center">
	<tr>
  	<td align="center">
    <!-- displayTag con formateador -->
		 <display:table name="RegEntityList" id="regentity" export="true" class="tableDisplay"
		  	sort="list" pagesize="15" requestURI="/showTabExpedient.do">
        <logic:iterate name="RegEntityListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
  				<logic:equal name="format" property="fieldType" value="LINK">
				  	<display:column titleKey='<%=format.getTitleKey()%>' 
				  					media="html" headerClass="sortable" 
				  					sortable="true">
				  		<html:link action="showTabExpedient" styleClass="displayLink" paramId="stageId" paramName="regentity"
				  			paramProperty='<%=format.getPropertyLink() %>'>
				  			
				  			<%=format.formatProperty(regentity)%>

				  		</html:link>
				  	</display:column>
   				</logic:equal>
   				<logic:equal name="format" property="fieldType" value="DATA">
			  		<display:column titleKey='<%=format.getTitleKey()%>' 
			  						headerClass="headerDisplay">
			  						
            	  		<%=format.formatProperty(regentity)%>
            	  		
			  		</display:column>
   				</logic:equal>
				</logic:iterate>
			</display:table>
		  <!-- displayTag -->
  	</td>
	</tr>
</table>
