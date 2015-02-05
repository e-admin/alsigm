<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<tiles:useAttribute id="ItemListName" name="ItemListAttr"/>
<tiles:useAttribute id="ItemFormatterName" name="ItemFormatterAttr"/>
<tiles:useAttribute id="ItemActionName" name="ItemActionAttr"/>

<c:set var="actionName"></c:set>
<logic:notEmpty name="ItemActionName">
<c:set var="actionName"><%= request.getContextPath()+ItemActionName.toString() %></c:set>
</logic:notEmpty>
<bean:define id='actionName' name='actionName' type='java.lang.String'/>


<!-- ISPAC  displayTag con formateador -->
<display:table name='<%=ItemListName.toString()%>' 
			   id='itemobj'
			   class="tableDisplay"
			   sort="list"
			   requestURI='<%= actionName %>'>

<logic:present name="itemobj">
		<bean:define id='item' name='itemobj' type='ieci.tdw.ispac.ispaclib.bean.ObjectBean'/>


	 <logic:iterate name='<%=ItemFormatterName.toString()%>' id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

		<logic:equal name="format" property="fieldType" value="LABELCHECKBOX">
			<display:column titleKey='<%=format.getTitleKey()%>' 
							media='<%=format.getMedia()%>'
							headerClass='<%=format.getHeaderClass()%>' 
							sortable='<%=format.getSortable()%>' 
							class='<%=format.getColumnClass()%>'
							decorator='<%=format.getDecorator()%>'>
				<div class="label"><nobr>
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
							decorator='<%=format.getDecorator()%>'
							style='text-align:center'>
				<html:multibox property="multibox">
					<%=format.formatProperty(item)%>
				</html:multibox>
			</display:column>
	  	</logic:equal>

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

		 <logic:equal name="format" property="fieldType" value="LINK_EX">
		  	<display:column titleKey='<%=format.getTitleKey()%>'
		  					media='<%=format.getMedia()%>'
		  					headerClass='<%=format.getHeaderClass()%>'
		  					class='<%=format.getColumnClass()%>'
		  					sortable='<%=format.getSortable()%>'
		  					sortProperty='<%=format.getPropertyName()%>'
		  					decorator='<%=format.getDecorator()%>'>

			  	<div id='<%=format.getStyleId() + item.getProperty(format.getFieldLink()).toString()%>'>
			  		<html:link action='<%=format.getUrl()%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="item"
			  			paramProperty='<%=format.getPropertyLink() %>' target='<%=format.getUrlTarget()%>' >

						<%=format.formatProperty(item)%>

			  		</html:link>
			  	</div>
		  		<logic:equal name="format" property="tooltip" value="true">
		  			 <div class='tooltip for_<%=format.getStyleId() + item.getProperty(format.getFieldLink()).toString()%>'>
     						<h1><bean:write name="format" property="tooltipTitle"/></h1>
     						<p><bean:write name="item" property='<%=format.getTooltipText()%>'/>
     						</p>
 					</div>
		  		</logic:equal>

		  	</display:column>
		 </logic:equal>

		<logic:equal name="format" property="fieldType" value="LINKPARAM">
		  	<display:column titleKey='<%=format.getTitleKey()%>'
		  					media='<%=format.getMedia()%>'
		  					headerClass='<%=format.getHeaderClass()%>'
		  					sortable='<%=format.getSortable()%>'
		  					sortProperty='<%=format.getPropertyName()%>'
		  					decorator='<%=format.getDecorator()%>'
		  					class='<%=format.getColumnClass()%>'>
		  					
		  		<html:link action='<%=format.getUrl()%>' styleClass='<%=format.getStyleClass()%>'
		  			name="format" property='<%=format.prepareLinkParams(item)%>'>

					<%=format.formatProperty(item)%>
		  		</html:link>
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
				 <%=itemobj_rowNum%>
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
					<%=itemobj_rowNum%>
		  		</html:link>
		  	</display:column>
		 </logic:equal>

		<logic:equal name="format" property="fieldType" value="MODORDER">
					<display:column titleKey='<%=format.getTitleKey()%>' media='<%=format.getMedia()%>' headerClass='<%=format.getHeaderClass()%>' class='<%=format.getColumnClass()%>'
						sortable='<%=format.getSortable()%>' decorator='<%=format.getDecorator()%>'>

							<center>
								<html:link
									action='<%=format.getUrl() + "?modOrder=INC&order=" + pageContext.getAttribute("itemobj_rowNum")%>'
									styleClass="aOrderButton" paramId='<%=format.getId()%>'
									paramName="item" paramProperty='<%=format.getPropertyLink() %>'>+</html:link>
								<html:link
									action='<%=format.getUrl() + "?modOrder=DEC&order=" + pageContext.getAttribute("itemobj_rowNum")%>'
									styleClass="aOrderButton" paramId='<%=format.getId()%>'
									paramName="item" paramProperty='<%=format.getPropertyLink() %>'>-</html:link>
							</center>
					</display:column>
		</logic:equal>
	</logic:iterate>

</logic:present>
</display:table>
<!-- displayTag -->
