<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@page import="ieci.tdw.ispac.api.item.IProcedure"%>

<c:set var="_procedureEstadoBorrador"><%=IProcedure.PCD_STATE_DRAFT%></c:set>
<jsp:useBean id="_procedureEstadoBorrador" type="java.lang.String" />

<script>
	function redirect(url)
	{
		submit('<%=request.getContextPath()%>' + url);
	}
</script>
<div id="navmenutitle">
	<bean:message key="title.pubRules"/>
</div>
<div id="navSubMenuTitle">
</div>
<div id="navmenu">
	<ispac:hasFunction functions="FUNC_PUB_RULES_EDIT">
	<ul class="actionsMenuList">
		<li>
			<a href="javascript:redirect('/showCTEntity.do?entityId=45&regId=-1')">
				<bean:message key="new.rule"/>
			</a>
		</li>
	</ul>
	</ispac:hasFunction>
</div>

<html:form action='/showPubRulesGroupList.do'>

	<display:table name="PubRulesGroupList"
				   id='itemobj'
				   export="false"
				   class="tableDisplay"
				   sort="list"
				   requestURI="/showPubRulesGroupList.do"
				   defaultsort="1">

	<logic:present name="itemobj">

		<bean:define id='item' name='itemobj' type='ieci.tdw.ispac.ispaclib.bean.ObjectBean'/>

		<logic:iterate name="PubRulesGroupListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

			<logic:equal name="format" property="fieldType" value="ID_PCD">
				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								class='<%=format.getColumnClass()%>'
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'
								group="1">

					<ispac:lookUp name="ProceduresMap" key='<%=(String)format.formatProperty(item)%>' property="NOMBRE" keyAll="-1" msgAllKey="form.pubRule.msg.all" />

				</display:column>
			</logic:equal>

			<logic:equal name="format" property="fieldType" value="VERSION_PCD">
				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								class='<%=format.getColumnClass()%>'
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'
								group="2">

					<c:set var="_procedureEstado"><ispac:lookUp name="ProceduresMap" key='<%=(String)format.formatProperty(item)%>' property="ESTADO" /></c:set>
					<jsp:useBean id="_procedureEstado" type="java.lang.String" />
					<c:choose>
						<c:when test="${_procedureEstado == _procedureEstadoBorrador}">
							<bean:message key="procedure.versions.draft" />
						</c:when>
						<c:otherwise>
							<ispac:lookUp name="ProceduresMap" key='<%=(String)format.formatProperty(item)%>' property="NVERSION" />
						</c:otherwise>
					</c:choose>

				</display:column>
			</logic:equal>

			<logic:equal name="format" property="fieldType" value="ID_FASE">
				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								class='<%=format.getColumnClass()%>'
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'
								group="3">

					<ispac:lookUp name="StagesMap" key='<%=(String)format.formatProperty(item)%>' property="NOMBRE" keyAll="-1" keyNoSelected="0" msgAllKey="form.pubRule.msg.all.a" msgNoSelectedKey="form.pubRule.msg.noSelected" />

				</display:column>
			</logic:equal>

			<logic:equal name="format" property="fieldType" value="ID_TRAMITE">
				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								class='<%=format.getColumnClass()%>'
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'
								group="4">

					<ispac:lookUp name="TasksMap" key='<%=(String)format.formatProperty(item)%>' property="NOMBRE" keyAll="-1" keyNoSelected="0" msgAllKey="form.pubRule.msg.all" msgNoSelectedKey="form.pubRule.msg.noSelected" />

				</display:column>
			</logic:equal>

			<logic:equal name="format" property="fieldType" value="TIPO_DOC">
				<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								class='<%=format.getColumnClass()%>'
								sortable='<%=format.getSortable()%>'
								decorator='<%=format.getDecorator()%>'
								group="5">

					<ispac:lookUp name="TypeDocsMap" key='<%=(String)format.formatProperty(item)%>' property="NOMBRE" keyAll="-1" keyNoSelected="0" msgAllKey="form.pubRule.msg.all" msgNoSelectedKey="form.pubRule.msg.noSelected" />

				</display:column>
			</logic:equal>

			<logic:equal name="format" property="fieldType" value="LINK">
			  	<display:column titleKey='<%=format.getTitleKey()%>'
			  					media='<%=format.getMedia()%>'
			  					headerClass='<%=format.getHeaderClass()%>'
			  					sortable='<%=format.getSortable()%>'
			  					decorator='<%=format.getDecorator()%>'
			  					class='<%=format.getColumnClass()%>'
								group="6"
								style="text-align: center;">

			  		<html:link action='<%=format.getUrl()+ "?pcdId=" + item.getProperty("ID_PCD") + "&stageId=" + item.getProperty("ID_FASE") + "&taskId=" + item.getProperty("ID_TRAMITE") + "&typeDoc=" + item.getProperty("TIPO_DOC")%>' styleClass='<%=format.getStyleClass()%>'>
						<bean:message key='<%=format.getPropertyValueKey()%>' />
			  		</html:link>

			  	</display:column>
			 </logic:equal>

		</logic:iterate>

	</logic:present>

	</display:table>
	<!-- displayTag -->

</html:form>