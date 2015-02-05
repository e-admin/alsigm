<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<bean:define name="pcdId" id="pcdId"/>

<div id="navmenutitle">
	<bean:message key="procedure.card.procedure.entities"/>
</div>

<div id="navmenu">
	<ul class="actionsMenuList">
		<li>
			<%String URL = "selectObject.do?codetable=SPAC_CT_ENTIDADES&codefield=ID&valuefield=NOMBRE&decorator=/formatters/entities/chooseentityformatter.xml&pcdId=" + pcdId;%>
			<a href='<%=URL%>'><bean:message key="catalog.add"/></a>
		</li>
	</ul>
</div>

<html:form action='/selectEntities.do'>

<div>

	<display:table name="ItemsList" id="item" export="true" class="tableDisplay"
  	sort="list" pagesize="45" requestURI='<%= request.getContextPath() + "/selectEntities.do" %>'>

		 <logic:iterate name="ItemsListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

			<logic:equal name="format" property="fieldType" value="LINK">
			  	<display:column titleKey='<%=format.getTitleKey()%>'
			  					media='<%=format.getMedia()%>'
			  					headerClass='<%=format.getHeaderClass()%>'
			  					sortable='<%=format.getSortable()%>'
			  					sortProperty='<%=format.getPropertyName()%>'
			  					decorator='<%=format.getDecorator()%>'>
			  		<html:link action='<%=format.getUrl() + "?pcdId=" + pcdId%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="item"
			  			paramProperty='<%=format.getPropertyLink() %>'>

					<%=format.formatProperty(item)%>

			  		</html:link>
			  	</display:column>
			 </logic:equal>

			<logic:equal name="format" property="fieldType" value="DATA">
				<display:column title='<%="<nobr>" + format.getTitleKey() + "</nobr>"%>'
								media='<%=format.getMedia()%>'
						 		headerClass='<%=format.getHeaderClass()%>'
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'>
					<nobr>
					<%=format.formatProperty(item)%>
					</nobr>
				</display:column>
			</logic:equal>

			<logic:equal name="format" property="fieldType" value="MODORDER">
				<display:column title='<%="<nobr>" + format.getTitleKey() + "</nobr>"%>' media='<%=format.getMedia()%>' headerClass='<%=format.getHeaderClass()%>'
					sortable='<%=format.getSortable()%>' decorator='<%=format.getDecorator()%>'>

						<center>
							<html:link
								action='<%=format.getUrl() + "?pcdId=" + pcdId + "&modOrder=INC"%>'
								styleClass="aOrderButton" paramId='<%=format.getId()%>'
								paramName="item" paramProperty='<%=format.getPropertyLink() %>'>+</html:link>
							<html:link
								action='<%=format.getUrl() + "?pcdId=" + pcdId + "&modOrder=DEC"%>'
								styleClass="aOrderButton" paramId='<%=format.getId()%>'
								paramName="item" paramProperty='<%=format.getPropertyLink() %>' >-</html:link>
						</center>
				</display:column>
			</logic:equal>

			<logic:equal name="format" property="fieldType" value="LINKFRM">
			  	<display:column titleKey='<%=format.getTitleKey()%>'
			  					media='<%=format.getMedia()%>' 
			  					headerClass='<%=format.getHeaderClass()%>'
			  					sortable='<%=format.getSortable()%>' 
			  					decorator='<%=format.getDecorator()%>'>
			  		<html:link action='selectObject.do?codetable=SPAC_CT_APLICACIONES&codefield=ID&valuefield=NOMBRE&decorator=/formatters/entities/entitiesappformatter.xml'
			  			styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="item"
			  			paramProperty='<%=format.getPropertyLink() %>'>

					<%=format.formatProperty(item)%>

			  		</html:link>

			  		<html:link action='<%=format.getUrl()+"?appId=" %>'
			  			styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="item"
			  			paramProperty='<%=format.getPropertyLink() %>'>
						<bean:message key="entity.button.defaultApp"/>
			  		</html:link>

			  	</display:column>
			 </logic:equal>

		</logic:iterate>
	</display:table>

</div>
</html:form>

