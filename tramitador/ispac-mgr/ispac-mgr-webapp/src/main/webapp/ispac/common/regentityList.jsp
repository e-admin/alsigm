<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<html:form action="showRegEntityList.do">
	<table cellpadding="5" cellspacing="0" border="0" width="100%" align="center">
		<tr>
	  		<td align="center">

        		<!-- displayTag con formateador -->
            <display:table name="RegEntityList"
							id="regentity"
							export="true"
			  				   class="tableDisplay"
								sort="list"
								pagesize="10"
						  	   requestURI="/showRegEntityList.do">

               		<logic:iterate name="RegEntityListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

	  					<logic:equal name="format" property="fieldType" value="CHECKBOX">

							<display:column titleKey='<%=format.getTitleKey()%>'
											media="html"
											headerClass="headerDisplay">

					            <table cellpadding="1" cellspacing="1" border="0" width="100%">
						            <tr>
			  							<td align="center" valign="middle">
			  								<img src='<ispac:rewrite href="img/folder.gif"/>' align="center" valign="top" width="16" height="16" border="0"/>
			  							</td>
			  							<td align="center" valign="middle">

			    							<html:multibox property="multibox">
				    							<%=format.formatProperty(regentity) %>
			    							</html:multibox>

			  							</td>
									</tr>
						        </table>
							</display:column>

					    </logic:equal>
	  					<logic:equal name="format" property="fieldType" value="LINK">

						  	<display:column titleKey='<%=format.getTitleKey()%>'
						  					media="html"
						  					headerClass="sortable"
						  					sortable="true"
						  					sortProperty='<%=format.getPropertyName()%>'>

						  		<html:link action="showExpedient" styleClass="displayLink" paramId="stageId" paramName="regentity"
						  			paramProperty='<%=format.getPropertyLink() %>'>
	    							<%=format.formatProperty(regentity) %>
						  		</html:link>

						  	</display:column>
   						</logic:equal>

   					    <logic:equal name="format" property="fieldType" value="DATA">

							<display:column titleKey='<%=format.getTitleKey()%>'
						  					headerClass="headerDisplay">

    							<%=format.formatProperty(regentity) %>

						  	</display:column>
   					   	</logic:equal>

					</logic:iterate>

				</display:table>
				<!-- displayTag -->
	  		</td>
		</tr>
	</table>
</html:form>
