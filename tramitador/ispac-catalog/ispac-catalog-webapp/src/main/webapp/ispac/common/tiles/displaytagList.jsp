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


<jsp:useBean id="checkboxDecorator" scope="page" class="ieci.tdw.ispac.ispacweb.decorators.CheckboxTableDecorator" />
<jsp:setProperty name="checkboxDecorator" property="fieldName" value="multibox" />

<!-- ISPAC  displayTag con formateador -->
<display:table name='<%=ItemListName.toString()%>'
			   form="'+document.forms[0].name+'"
			   excludedParams="multibox"
			   decorator="checkboxDecorator"
			   export="true"
			   class="tableDisplay"
  			   sort="list"
  			   pagesize="45"
  			   requestURI='<%= actionName %>'
  			   uid='<%="uid_" + ItemListName.toString()%>'>

	<logic:present name='<%="uid_" + ItemListName.toString()%>'>

		<bean:define id='item' name='<%="uid_" + ItemListName.toString()%>' type='ieci.tdw.ispac.ispaclib.bean.ObjectBean'/>

		 <logic:iterate name='<%=ItemFormatterName.toString()%>' id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

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

            	<jsp:setProperty name="checkboxDecorator" property="id" value='<%=format.getPropertyName()%>' />
            	<display:column title='<%="<input type=\'checkbox\' onclick=\'javascript:checkAll(document.forms[0].multibox, this);\'/>"%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'
								comparator='<%=format.getComparator()%>'
								class='<%=format.getColumnClass()%>'
								property="checkbox"/>
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

			<logic:equal name="format" property="fieldType" value="LINK_BT">

			  	<display:column titleKey='<%=format.getTitleKey()%>'
			  					media='<%=format.getMedia()%>'
			  					headerClass='<%=format.getHeaderClass()%>'
			  					sortable='<%=format.getSortable()%>'
			  					sortProperty='<%=format.getPropertyName()%>'
			  					decorator='<%=format.getDecorator()%>'
			  					class='<%=format.getColumnClass()%>'
			  					style="text-align: center;">

			  		<html:link action='<%=format.getUrl()%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="item"
			  			paramProperty='<%=format.getPropertyLink() %>'>

			  			<bean:message key='<%=format.getPropertyValueKey()%>' />

			  		</html:link>

			  	</display:column>

			 </logic:equal>

			 <logic:equal name="format" property="fieldType" value="LINK_JS">

			  	<display:column titleKey='<%=format.getTitleKey()%>'
			  					media='<%=format.getMedia()%>'
			  					headerClass='<%=format.getHeaderClass()%>'
			  					sortable='<%=format.getSortable()%>'
			  					sortProperty='<%=format.getPropertyName()%>'
			  					decorator='<%=format.getDecorator()%>'
			  					class='<%=format.getColumnClass()%>'>

			  		<a href="javascript: showFrame('workframe','<%=format.getUrl()%>?<%=format.getId()%>=<bean:write name="item" property='<%=format.getPropertyLink()%>'/>',640,480)"
			  			class='<%=format.getStyleClass()%>' >

						<%=format.formatProperty(item)%>

			  		</a>

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

			<logic:equal name="format" property="fieldType" value="REPORT_TYPE">

				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								class='<%=format.getColumnClass()%>'
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'>

						<% if ("1".equals(format.formatProperty(item))) { %>
							<bean:message key="form.report.propertyLabel.generico"/>
						<% } else if ("2".equals(format.formatProperty(item))) { %>
							<bean:message key="form.report.propertyLabel.especifico"/>
						<% } else if ("3".equals(format.formatProperty(item))) { %>
							<bean:message key="form.report.propertyLabel.global"/>
						<% } else{%>
							<bean:message key="form.report.propertyLabel.busqueda"/>
						<% }%>

				</display:column>

			</logic:equal>

			<logic:equal name="format" property="fieldType" value="MSG_RESOURCES_DATA">

				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								class='<%=format.getColumnClass()%>'
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'
								comparator='<%=format.getComparator()%>'>

						<bean:message key='<%=format.formatProperty(item).toString()%>' />

				</display:column>

			</logic:equal>

			<logic:equal name="format" property="fieldType" value="BOOLEAN">

				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								class='<%=format.getColumnClass()%>'
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'>
					<% if ("true".equals(format.formatProperty(item))) { %>
						<bean:message key="bool.yes"/>
					<% } else { %>
						<bean:message key="bool.no"/>
					<% } %>
				</display:column>

			</logic:equal>
				<logic:equal name="format" property="fieldType" value="BOOLEAN_SN">

				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								class='<%=format.getColumnClass()%>'
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'>
					<% if ("S".equals(format.formatProperty(item))) { %>
						<bean:message key="bool.yes"/>
					<% } else { %>
						<bean:message key="bool.no"/>
					<% } %>
				</display:column>

			</logic:equal>

			<logic:equal name="format" property="fieldType" value="BOOLEAN_NUMERIC">

				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								class='<%=format.getColumnClass()%>'
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'>
					<% if ("1".equals(format.formatProperty(item))) { %>
						<bean:message key="bool.yes"/>
					<% } else { %>
						<bean:message key="bool.no"/>
					<% } %>
				</display:column>

			</logic:equal>

			<logic:equal name="format" property="fieldType" value="ROWNUM">

				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								class='<%=format.getColumnClass()%>'
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'>

					<bean:write name='<%="uid_" + ItemListName.toString() + "_rowNum"%>' />

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
						<bean:write name='<%="uid_" + ItemListName.toString() + "_rowNum"%>' />
			  		</html:link>

			  	</display:column>

			 </logic:equal>

			<logic:equal name="format" property="fieldType" value="MODORDER">

				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								class='<%=format.getColumnClass()%>'
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'>

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

			</logic:equal>`

			<logic:equal name="format" property="fieldType" value="TIPO_OBJ_AYUDA">

				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								class='<%=format.getColumnClass()%>'
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'>

				<bean:message key='<%="help.tipo_obj."+format.formatProperty(item)%>' />

				</display:column>

			</logic:equal>


		</logic:iterate>

	</logic:present>

</display:table>
<!-- displayTag -->
