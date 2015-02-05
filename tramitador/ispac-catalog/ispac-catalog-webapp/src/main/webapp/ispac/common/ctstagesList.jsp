<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<ispac:hasFunction var="editionMode" functions="FUNC_INV_STAGES_EDIT" />

<script>

	function redirect(url)
	{
		submit('<%=request.getContextPath()%>' + url);
	}
</script>

<div id="navmenutitle">
	<bean:message key="title.stageslist"/>
</div>
<div id="navSubMenuTitle">
</div>

<div id="navmenu">
	<c:if test="${editionMode}">
	<ul class="actionsMenuList">
		<li>
			<a href="javascript:redirect('/showCTStage.do?entityId=5&regId=-1')">
				<bean:message key="new.stage"/>
			</a>
		</li>
	</ul>
	</c:if>
</div>

<script>

	function submitForm(url) {
	
		var frm = document.getElementById('defaultForm');
		frm.action = '<%=request.getContextPath()%>' + url;
		frm.submit();
	}

</script>

<html:form styleId="defaultForm" action='/showCTStagesList.do'>

	<!-- BUSCADOR -->
	<tiles:insert page="ajax/ajaxSearchTile.jsp" ignore="true" flush="false" >	
		<tiles:put name="suggestEntityId" value="5"/>
	</tiles:insert>
	<!--  Botones de subir y bajar -->
	
	<c:set var="criterioBusqueda" value="${ctStageName}"></c:set>
	<c:set var="hayCriterio" value="${!empty criterioBusqueda}"/>
	<c:if test="${!hayCriterio && editionMode}">
		<div id="navmenu">
			<ul class="actionsMenuList">
				<li>
					<a href="javascript:submitForm('/manageCTStagesList.do?method=moveup')">
						<bean:message key="button.up"/>
					</a>
				</li>
				<li>
					<a href="javascript:submitForm('/manageCTStagesList.do?method=movedown')">
						<bean:message key="button.down"/>
					</a>
				</li>
			</ul>
		</div>
	</c:if>
	<!-- LISTA DE FASES -->
	<tiles:insert page="tiles/displaytagList.jsp" ignore="true" flush="false">
		<tiles:put name="ItemListAttr" value="CTStagesList"/>
		<tiles:put name="ItemFormatterAttr" value="CTStagesListFormatter"/>
		<tiles:put name="ItemActionAttr" value="/showCTStagesList.do"/>
	</tiles:insert>

</html:form>

