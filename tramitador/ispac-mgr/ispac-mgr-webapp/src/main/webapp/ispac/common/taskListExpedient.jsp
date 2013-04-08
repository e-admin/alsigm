<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>




<div id="contenido" class="move">
<div class="ficha">
<div class="encabezado_ficha">
<div class="titulo_ficha">
<h4><bean:message key="tasksExpedient.title" /></h4>
<div class="acciones_ficha"><a href="#" id="btnCancel"
	onclick='javascript:top.ispac_needToConfirm=false;<ispac:hideframe refresh="false"/>'
	class="btnCancel"><bean:message key="common.message.close" /></a></div>
<%--fin acciones ficha --%></div>
<%--fin titulo ficha --%></div>
<%--fin encabezado ficha --%>

<div class="cuerpo_ficha">
<div class="seccion_ficha"><html:form action="/showReports.do">

	<!-- displayTag con formateador -->
	<bean:define name="formatter" id="formatter"
		type="ieci.tdw.ispac.ispaclib.bean.BeanFormatter" />

	<display:table name='taskListExp' id="itemobj"
		requestURI="/showTaskListExpedient.do"
		export='<%=formatter.getExport()%>'
		class='<%=formatter.getStyleClass()%>' sort='<%=formatter.getSort()%>'
		pagesize='<%=formatter.getPageSize()%>'
		defaultorder='<%=formatter.getDefaultOrder()%>'
		defaultsort='<%=formatter.getDefaultSort()%>'>

		<logic:present name="itemobj">

			<bean:define id='item' name='itemobj'
				type='ieci.tdw.ispac.ispaclib.bean.ObjectBean' />
			<logic:iterate name="formatter" id="format"
				type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

				<logic:equal name="format" property="fieldType" value="LIST">

					<display:column titleKey='<%=format.getTitleKey()%>'
						media='<%=format.getMedia()%>'
						headerClass='<%=format.getHeaderClass()%>'
						sortable='<%=format.getSortable()%>'
						sortProperty='<%=format.getPropertyName()%>'
						decorator='<%=format.getDecorator()%>'
						comparator='<%=format.getComparator()%>'
						class='<%=format.getColumnClass()%>'>

						<%=format.formatProperty(item)%>

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

							<bean:define id="_link" value='<%=format.getUrl()%>'></bean:define>
							<c:url value="${_link}" var="link">
							<c:param name="stagePcdIdActual" value="${requestScope['stageId']}"/>
							<c:param name="key" ><bean:write name="item" property="property(SPAC_DT_TRAMITES:ID)"/></c:param>
							<c:param name="taskId" ><bean:write name="item" property="property(SPAC_DT_TRAMITES:ID_TRAM_EXP)"/></c:param>
						</c:url>
						<a href='<c:out value="${link}"/>' target="_top" class="displayLink">
							<%=format.formatProperty(item)%>
						</a>
					</display:column>

				</logic:equal>

				

				<logic:equal name="format" property="fieldType" value="STATETASK">
					<display:column titleKey='<%=format.getTitleKey()%>'
						media='<%=format.getMedia()%>'
						headerClass='<%=format.getHeaderClass()%>'
						class='<%=format.getColumnClass()%>'
						sortable='<%=format.getSortable()%>'
						decorator='<%=format.getDecorator()%>'>
						<c:set var="VALUE"><%=format.formatProperty(item)%></c:set>
						<c:choose>
							<c:when test="${VALUE=='1'}">
								<bean:message key="state.open" />
							</c:when>
							<c:when test="${VALUE=='2'}">
								<bean:message key="state.cancel" />
							</c:when>
							<c:otherwise>
								<bean:message key="state.close" />
							</c:otherwise>
						</c:choose>
					</display:column>
				</logic:equal>

			</logic:iterate>
		</logic:present>
	</display:table>
	<!-- displayTag -->


</html:form></div>
<%--seccion ficha --%></div>
<%--fin cuerpo ficha --%></div>
<%--fin  ficha --%>
<div><%--fin contenido --%> <script>

	
positionMiddleScreen('contenido');
	$(document).ready(function(){
		$("#contenido").draggable();
	});
	
function showTask(url){
<ispac:hideframe refresh="true"/>
top.window.location.href=url;

}
</script>