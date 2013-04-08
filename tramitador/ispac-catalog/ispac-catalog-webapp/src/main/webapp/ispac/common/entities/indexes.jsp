<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="action">
   <c:out value="${requestScope['ActionDestino']}" default="/showEntityWizard.do"/>
</c:set>


<script>
function deleteIndexes(){
		if (checkboxElement(document.defaultForm.multibox) == "") {
			jAlert('<bean:message key="error.entity.noSelected"/>', '<bean:message key="common.alert"/>' , '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		}else {
			ispac_submit('<c:url value="${action}?method=deleteItems"/>')
		}
}
</script>

<jsp:useBean id="action" type="java.lang.String"/>
<html:form action='<%=action%>'>

	<html:hidden property="property(ITEMS)" value="INDEXES" />

	<c:if test="${requestScope.FIELDS_MODIFICABLES}">
		<div class="buttonList">
			<ul>
				<li>
					<a href='<c:url value="${action}?method=initIndex"/>'><nobr><bean:message key="forms.button.add"/></nobr></a>
				</li>

				<c:choose>
				<c:when test="${!empty requestScope['IndexList']}">
				<li>
					<a href="javascript:deleteIndexes()"><nobr><bean:message key="forms.button.delete"/></nobr></a>
				</li>
				</c:when>
				<c:otherwise>
					<li style="background-color: #ddd; color: #aaa; cursor: default;">
						<nobr><bean:message key="forms.button.delete"/></nobr>
					</li>
				</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</c:if>

	<%--
	<tiles:insert page="../tiles/displaytagList.jsp" ignore="true" flush="false">
		<tiles:put name="ItemListAttr" value="IndexList"/>
		<tiles:put name="ItemFormatterAttr" value="IndexListFormatter"/>
		<tiles:put name="ItemActionAttr" value="${action}"/>
	</tiles:insert>
	--%>

	<jsp:useBean id="checkboxDecorator" scope="page" class="ieci.tdw.ispac.ispacweb.decorators.CheckboxTableDecorator" />
	<jsp:setProperty name="checkboxDecorator" property="fieldName" value="multibox" />

	<display:table name="IndexList"
				   id='itemobj'
				   form="defaultForm"
				   excludedParams="multibox"
				   decorator="checkboxDecorator"
				   export="true"
				   class="tableDisplay"
				   sort="list"
				   pagesize="45"
				   requestURI='<%=action%>'>

		<logic:present name="itemobj">

			<bean:define id='item' name='itemobj' type='ieci.tdw.ispac.ispaclib.bean.ObjectBean'/>

			 <logic:iterate name="IndexListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

			 	<c:if test="${requestScope.FIELDS_MODIFICABLES}">

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

			  	</c:if>

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

			</logic:iterate>

		</logic:present>

	</display:table>

</html:form>
