<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<div class="pcdbreadcrumb">

	<logic:present name="PCD_ITEM">
		<bean:define id="pcd" name="PCD_ITEM"/>
		<dl>
			<dt><bean:message key="procedure.breadcrumb.procedure"/>:&nbsp;</dt>
			<dd>
				<bean:write name="pcd" property="property(NOMBRE)"/>

				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				
				<bean:message key="form.pproc.label.version"/>:
				<div id="procTitleText">
					<bean:define id="version" name="pcd" property="property(NVERSION)"/>
					<%
						int iversionpcd = Integer.parseInt(version.toString());
						//iversion++;
					%>
					&nbsp;&nbsp;&nbsp;<%=iversionpcd%>
				</div>

				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				
				<bean:message key="form.pproc.label.status"/>:&nbsp;&nbsp;&nbsp;
				<logic:equal name="pcd" property="property(ESTADO)" value="1">
				<div id="procBorradorTitle">
					<bean:message key="procedure.versions.draft"/>
				</div>
				</logic:equal>
				<logic:equal name="pcd" property="property(ESTADO)" value="2">
				<div id="procVigenteTitle">
					<bean:message key="procedure.versions.current"/>
				</div>
				</logic:equal>
				<logic:equal name="pcd" property="property(ESTADO)" value="3">
				<div id="procHistoricoTitle">
					<bean:message key="procedure.versions.archived"/>
				</div>
				</logic:equal>
			</dd>
		</dl>
	</logic:present>

	<logic:present name="SUBPCD_ITEM">
		<bean:define id="subpcd" name="SUBPCD_ITEM"/>
		<dl>
			<dt><bean:message key="procedure.breadcrumb.subprocedure"/>:&nbsp;</dt>
			<dd>
				<bean:write name="subpcd" property="property(NOMBRE)"/>

				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				
				<bean:message key="form.pproc.label.version"/>:
				<div id="procTitleText">
					<bean:define id="version" name="subpcd" property="property(NVERSION)"/>
					<%
						int iversionsubpcd = Integer.parseInt(version.toString());
						//iversion++;
					%>
					&nbsp;&nbsp;&nbsp;<%=iversionsubpcd%>
				</div>

				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				
				<bean:message key="form.pproc.label.status"/>:&nbsp;&nbsp;&nbsp;
				<logic:equal name="subpcd" property="property(ESTADO)" value="1">
				<div id="procBorradorTitle">
					<bean:message key="procedure.versions.draft"/>
				</div>
				</logic:equal>
				<logic:equal name="subpcd" property="property(ESTADO)" value="2">
				<div id="procVigenteTitle">
					<bean:message key="procedure.versions.current"/>
				</div>
				</logic:equal>
				<logic:equal name="subpcd" property="property(ESTADO)" value="3">
				<div id="procHistoricoTitle">
					<bean:message key="procedure.versions.archived"/>
				</div>
				</logic:equal>
			</dd>
		</dl>
	</logic:present>

	<logic:present name="STAGE_ITEM">
		<bean:define id="stage" name="STAGE_ITEM"/>
		<dl>
			<dt><bean:message key="procedure.breadcrumb.stage"/>:&nbsp;</dt>
			<dd><bean:write name="stage" property="property(NOMBRE)"/></dd>
		</dl>
	</logic:present>

	<logic:present name="ACTIVITY_ITEM">
		<bean:define id="activity" name="ACTIVITY_ITEM"/>
		<dl>
			<dt><bean:message key="procedure.breadcrumb.activity"/>:&nbsp;</dt>
			<dd><bean:write name="activity" property="property(NOMBRE)"/></dd>
		</dl>
	</logic:present>

	<logic:present name="SINCNODE_ITEM">
		<bean:define id="syncnodeId" name="SINCNODE_ITEM" property="property(ID)"/>
		<bean:define id="syncnodeType" name="SINCNODE_ITEM" property="property(TIPO)"/>
		<dl>
			<dt><bean:message key="procedure.breadcrumb.syncnode"/>:&nbsp;</dt>
			<dd>
				
				<bean:message key="procedure.breadcrumb.syncnode.name" arg0='<%=String.valueOf(syncnodeId)%>'/>

				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				
				<bean:message key="procedure.breadcrumb.syncnode.type"/>:
				<div id="procTitleText">
					<c:set var="syncnodeTypeDescr" value="syncnode.type.${syncnodeType}"/>
					<bean:define id="syncnodeTypeDescr" name="syncnodeTypeDescr" type="java.lang.String"/>
					<bean:message key='<%=syncnodeTypeDescr%>'/>
				</div>
			</dd>
		</dl>
	</logic:present>

	<logic:present name="FLOW_ITEM">
		<dl>
			<dt><bean:message key="procedure.breadcrumb.flows"/>:&nbsp;</dt>
			<dd>
				<logic:equal name="FLOW_ITEM" value="IN">
					<bean:message key="procedure.breadcrumb.flows.in"/>
				</logic:equal>
				<logic:equal name="FLOW_ITEM" value="OUT">
					<bean:message key="procedure.breadcrumb.flows.out"/>
				</logic:equal>
			</dd>
		</dl>
	</logic:present>

	<logic:present name="TASK_ITEM">
		<bean:define id="task" name="TASK_ITEM"/>
		<dl>
			<dt><bean:message key="procedure.breadcrumb.task"/>:&nbsp;</dt>
			<dd><bean:write name="task" property="property(NOMBRE)"/></dd>
		</dl>
	</logic:present>

	<logic:present name="DOCTYPE_ITEM">
		<bean:define id="doctype" name="DOCTYPE_ITEM"/>
		<dl>
			<dt><bean:message key="procedure.breadcrumb.doctype"/>:&nbsp;</dt>
			<dd><bean:write name="doctype" property="property(NOMBRE)"/></dd>
		</dl>
	</logic:present>

	<logic:present name="TEMPLATE_ITEM">
		<bean:define id="template" name="TEMPLATE_ITEM"/>
		<dl>
			<dt><bean:message key="procedure.breadcrumb.template"/>:&nbsp;</dt>
			<c:if test="${PLANT_ESPECIFICA=='false'}">
				<dd><bean:write name="template" property="property(NOMBRE)"/><bean:message key="generic.template"/></dd>
			</c:if>
			<c:if test="${PLANT_ESPECIFICA=='true'}">
				<dd><bean:write name="template" property="property(NOMBRE)"/></dd>
			</c:if>
		</dl>
	</logic:present>

</div>
