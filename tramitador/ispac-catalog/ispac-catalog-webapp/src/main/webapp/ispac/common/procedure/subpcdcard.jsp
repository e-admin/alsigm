<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:define id="pcd" name="SUBPCD_ITEM"/>
<bean:define id="pcdId" name="pcd" property="property(ID)"/>
<bean:define id="groupId" name="pcd" property="property(ID_GROUP)"/>

<div class="tabButtons">
	<ispac:hasFunction functions="FUNC_INV_SUBPROCESS_EDIT">
	<ul class="tabButtonList">
		<logic:equal name="pcd" property="property(ESTADO)" value="1">
		<li>
			<c:url var="promoteUrl" value="promoteDraft.do">
				<c:param name="entityId" value="${param.entityId}"/>
				<c:param name="regId" value="${param.regId}"/>
				<c:param name="groupId" value="${groupId}"/>
			</c:url>
			<a href="#" onclick="javascript:return messageConfirm('<c:out value="${promoteUrl}"/>','<bean:message key="subprocedure.card.confirm.promote"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' ,'<bean:message key="common.message.cancel"/>')">
				<nobr><bean:message key="subprocedure.versions.actions.promote"/></nobr>
			</a>
		</li>
		<li>
			<c:url var="deleteDraftUrl" value="deleteDraft.do">
				<c:param name="entityId" value="${param.entityId}"/>
				<c:param name="regId" value="${param.regId}"/>
				<c:param name="groupId" value="${groupId}"/>
			</c:url>
			<a href="#" onclick="javascript:return messageConfirm('<c:out value="${deleteDraftUrl}"/>','<bean:message key="subprocedure.card.confirm.deleteDraft"/>','<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' ,'<bean:message key="common.message.cancel"/>')">
				<nobr><bean:message key="subprocedure.versions.actions.deleteDraft"/></nobr>
			</a>
		</li>
		</logic:equal>
		<logic:notEqual name="pcd" property="property(ESTADO)" value="1">
		<li>
			<ispac:linkframe id="CLONESUBPCD"
				     target="workframe"
					 action='<%="newSubProcedure.do?action=begin&pcdtype=draft&pcdId=" + pcdId.toString()+"&groupId="+groupId.toString()%>'
					 titleKey="catalog.createdraft"
					 width="640"
					 height="480"
					 showFrame="true">
			</ispac:linkframe>
		</li>
		<li>
			<c:url var="deleteUrl" value="deleteSubProcedure.do">
				<c:param name="entityId" value="${param.entityId}"/>
				<c:param name="regId" value="${param.regId}"/>
				<c:param name="groupId" value="${groupId}"/>
			</c:url>
			<a href="#" onclick="javascript:return messageConfirm('<c:out value="${deleteUrl}"/>','<bean:message key="subprocedure.card.confirm.delete"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' ,'<bean:message key="common.message.cancel"/>')">
				<nobr><bean:message key="subprocedure.versions.actions.delete"/></nobr>
			</a>
		</li>
		</logic:notEqual>
	</ul>
	</ispac:hasFunction>
</div>

<tiles:insert page="../tiles/pcdBCTile.jsp" ignore="true" flush="false"/>

