<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:define id="urlparams"  name="URLParams"/>

<logic:present parameter="captionkey">
<div id="navmenutitle">
  <bean:parameter id="caption" name="captionkey"/>
	<bean:message name="caption"/>
</div>
</logic:present>
<logic:present name="ancestors">
	<div id="navSubMenuTitle">
		<logic:iterate id="ancestor" name="ancestors" type="ieci.tdw.ispac.ispaclib.bean.ItemBean" indexId="cnt">
			<logic:iterate id="format" name="Formatter" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
				<logic:equal name="format" property="fieldType" value="ANCESTOR">
					<logic:equal name="cnt" value="0">
						<%=format.formatProperty(ancestor)%>
					</logic:equal>
					<logic:notEqual name="cnt" value="0">
						<c:out value=","/>
						<html:link action='<%=format.getUrl() + "&" + urlparams%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="ancestor"
				  			paramProperty='<%=format.getPropertyLink() %>'>
							<%=format.formatProperty(ancestor)%>
						</html:link>
					</logic:notEqual>
				</logic:equal>
			</logic:iterate>
		</logic:iterate>
	</div>
</logic:present>
<div id="navmenu">
	<br/>
	<tiles:insert page="../tiles/AppErrorsTile.jsp" ignore="true" flush="false"/>
</div>
<div class="stdform">
	<c:if test="${!empty requestScope.users}">
		<display:table name="users" id="user" export="false" class="tableDisplay"
	  		sort="list" requestURI='<%= request.getContextPath()+"/selectSigner.do" %>'>

			<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

				<logic:equal name="format" property="fieldType" value="IMGTXTUSR">
					<display:column titleKey='<%=format.getTitleKey()%>' 
									media='<%=format.getMedia()%>' 
									headerClass='<%=format.getHeaderClass()%>'
									sortable='<%=format.getSortable()%>' 
									decorator='<%=format.getDecorator()%>'>
						<%=format.formatProperty(user)%>
					</display:column>
				</logic:equal>

				<logic:equal name="format" property="fieldType" value="IMGEXITLINKUSR">
					<display:column titleKey='<%=format.getTitleKey()%>' 
									media='<%=format.getMedia()%>' 
									headerClass='<%=format.getHeaderClass()%>'
									sortable='<%=format.getSortable()%>' 
									decorator='<%=format.getDecorator()%>'>
							<html:link action='<%=format.getUrl() + "?" + urlparams%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="user"
				  			paramProperty='<%=format.getPropertyLink() %>'>

								<bean:message key='<%=format.getPropertyValueKey()%>' />
								<%--=format.formatProperty(user)--%>
								
							</html:link>
					</display:column>
				</logic:equal>
			</logic:iterate>
		</display:table>
	</c:if>

	<c:if test="${!empty requestScope.responsibles}">
		<display:table name="responsibles" id="responsible" export="false" class="tableDisplay"
	  		sort="list" requestURI='<%= request.getContextPath()+"/selectSigner.do" %>'>

			<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

           		<logic:equal name="format" property="fieldType" value="IMGTXTGRPLINK">
					<display:column titleKey='<%=format.getTitleKey()%>' 
									media='<%=format.getMedia()%>' 
									headerClass='<%=format.getHeaderClass()%>'
									sortable='<%=format.getSortable()%>' 
									decorator='<%=format.getDecorator()%>'>
						<html:link action='<%=format.getUrl() + "&" + urlparams%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName="responsible"
				  			paramProperty='<%=format.getPropertyLink() %>'>

							<%=format.formatProperty(responsible)%>

						</html:link>
					</display:column>
				</logic:equal>
			</logic:iterate>
		</display:table>
	</c:if>
</div>