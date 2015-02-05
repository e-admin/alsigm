<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ page import="ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr" %>

<ispac:hasFunction var="inventario" functions="FUNC_INV_PROCEDURES_READ, FUNC_INV_PROCEDURES_EDIT, FUNC_INV_STAGES_READ, FUNC_INV_STAGES_EDIT, FUNC_INV_TASKS_READ, FUNC_INV_TASKS_EDIT, FUNC_INV_DOCTYPES_READ, FUNC_INV_DOCTYPES_EDIT, FUNC_INV_TEMPLATES_READ, FUNC_INV_TEMPLATES_EDIT, FUNC_INV_SUBPROCESS_READ, FUNC_INV_SUBPROCESS_EDIT, FUNC_INV_SIGN_CIRCUITS_READ, FUNC_INV_SIGN_CIRCUITS_EDIT"/>
<ispac:hasFunction var="componentes" functions="FUNC_COMP_ENTITIES_READ, FUNC_COMP_ENTITIES_EDIT, FUNC_COMP_VALIDATION_TABLES_READ, FUNC_COMP_VALIDATION_TABLES_EDIT, FUNC_COMP_HIERARCHICAL_TABLES_READ, FUNC_COMP_HIERARCHICAL_TABLES_EDIT, FUNC_COMP_RULES_READ, FUNC_COMP_RULES_EDIT, FUNC_COMP_SEARCH_FORMS_READ, FUNC_COMP_SEARCH_FORMS_EDIT, FUNC_COMP_CALENDARS_READ, FUNC_COMP_CALENDARS_EDIT, FUNC_COMP_REPORTS_READ, FUNC_COMP_REPORTS_EDIT, FUNC_COMP_SYSTEM_VARS_READ, FUNC_COMP_SYSTEM_VARS_EDIT, FUNC_COMP_HELPS_READ, FUNC_COMP_HELPS_EDIT"/>
<ispac:hasFunction var="publicador" functions="FUNC_PUB_ACTIONS_READ, FUNC_PUB_ACTIONS_EDIT, FUNC_PUB_APPLICATIONS_READ, FUNC_PUB_APPLICATIONS_EDIT, FUNC_PUB_CONDITIONS_READ, FUNC_PUB_CONDITIONS_EDIT, FUNC_PUB_RULES_READ, FUNC_PUB_RULES_EDIT, FUNC_PUB_MILESTONES_READ, FUNC_PUB_MILESTONES_EDIT"/>
<ispac:hasFunction var="permisos" functions="FUNC_PERM_READ, FUNC_PERM_EDIT"/>

<script type="text/javascript"> 
//<!--
	window.onload = function() {
		<c:if test="${inventario}">initializeMenu("inventoryMenu", "inventoryActuator","menutile");</c:if>
		<c:if test="${componentes}">initializeMenu("componentesMenu", "componentesActuator","menutile");</c:if>
		<c:if test="${publicador}">initializeMenu("publisherMenu", "publisherActuator","menutile");</c:if>
		<c:if test="${permisos}">initializeMenu("usersMenu", "usersActuator","menutile");</c:if>
	}
//-->
</script>

<div id="usr">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
	      <td>
		<div id="usuario">
			<div id="barra_usuario">
				<p class="usuario">
			            <bean:write name="User"/>
				</p>
			</div>
		</div>
	      </td>
	</tr>
</table>
</div>


<div id="mainMenu">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
			<ul id="menuList">
				<c:if test="${inventario}">
				<li class="menubar">
					<a href="#"
            			id="inventoryActuator"
             			class="actuator"
             			accesskey='<bean:message key="menu.inventory.sectiontitle.accesskey"/>'><bean:message key="menu.inventory.sectiontitle"/></a>
	           		<ul id="inventoryMenu" class="menu">
					
						<ispac:hasFunction functions="FUNC_INV_PROCEDURES_READ, FUNC_INV_PROCEDURES_EDIT">
	           			<li>
	           				<html:link action="showCTProceduresTree">
	           					<bean:message key="menu.procedures"/>
	       					</html:link>
	           			</li>
	           			<%--
	           			<li>
	           				<html:link action="showCTProceduresList">
	          					<bean:message key="menu.searchProcedures"/>
	      					</html:link>
	           			</li>
	           			--%>
						</ispac:hasFunction>

						<ispac:hasFunction functions="FUNC_INV_STAGES_READ, FUNC_INV_STAGES_EDIT">
	           			<li>
							<html:link action="showCTStagesList">
								<bean:message key="menu.stages"/>
				  			</html:link>
            			</li>
						</ispac:hasFunction>
						
						<ispac:hasFunction functions="FUNC_INV_TASKS_READ, FUNC_INV_TASKS_EDIT">
            			<li>
							<html:link action="showCTTasksList">
								<bean:message key="menu.tasks"/>
				  			</html:link>
            			</li>
						</ispac:hasFunction>
						
						<ispac:hasFunction functions="FUNC_INV_DOCTYPES_READ, FUNC_INV_DOCTYPES_EDIT">
            			<li>
							<html:link action="showCTTPDocsList">
								<bean:message key="menu.doctypes"/>
				  			</html:link>
            			</li>
						</ispac:hasFunction>

						<ispac:hasFunction functions="FUNC_INV_TEMPLATES_READ, FUNC_INV_TEMPLATES_EDIT">
            			<li>
							<html:link action="showCTTemplatesList">
								<bean:message key="menu.templates"/>
				  			</html:link>
            			</li>
						</ispac:hasFunction>
						
						<ispac:hasFunction functions="FUNC_INV_SUBPROCESS_READ, FUNC_INV_SUBPROCESS_EDIT">
            			<li>
							<html:link action="showSubProceduresList">
								<bean:message key="menu.subprocedures"/>
				  			</html:link>
            			</li>
						</ispac:hasFunction>
		
						<ispac:hasFunction functions="FUNC_INV_SIGN_CIRCUITS_READ, FUNC_INV_SIGN_CIRCUITS_EDIT">
						<c:set var="digitalSignManagementActive" value="${ISPACConfiguration.DIGITAL_SIGN_CONNECTOR_CLASS}"/>
						<c:if test="${!empty digitalSignManagementActive}">
            			<li>
							<html:link action="showSignProcessList">
								<bean:message key="menu.signprocess"/>
				  			</html:link>
            			</li>
            			</c:if>
						</ispac:hasFunction>
            			
	          		</ul>
				</li>
				</c:if>
				
				<c:if test="${componentes}">
				<li class="menubar">
					<a href="#"
             			id="componentesActuator"
             			class="actuator"
             			accesskey='<bean:message key="menu.components.sectiontitle.accesskey"/>'><bean:message key="menu.components.sectiontitle"/></a>
	             	<ul id="componentesMenu" class="menu">
					
						<ispac:hasFunction functions="FUNC_COMP_ENTITIES_READ, FUNC_COMP_ENTITIES_EDIT">
            			<li>
							<html:link action="showCTEntitiesList">
								<bean:message key="menu.entities"/>
				  			</html:link>
            			</li>
						</ispac:hasFunction>

						<ispac:hasFunction functions="FUNC_COMP_VALIDATION_TABLES_READ, FUNC_COMP_VALIDATION_TABLES_EDIT">
            			<li>
							<html:link action="showCTValueTablesList">
								<bean:message key="menu.valueTables"/>
				  			</html:link>
            			</li>
						</ispac:hasFunction>

						<ispac:hasFunction functions="FUNC_COMP_HIERARCHICAL_TABLES_READ, FUNC_COMP_HIERARCHICAL_TABLES_EDIT">
						<c:set var="hierarchicalTablesManagementActive" value="${ISPACConfiguration.HIERARCHICAL_TABLES_MANAGEMENT_ACTIVE}"/>
						<c:if test="${hierarchicalTablesManagementActive == 'true'}">
            			<li>
							<html:link action="showCTHierarchicalTablesList">
								<bean:message key="menu.hierarchicalTables"/>
				  			</html:link>
            			</li>
						</c:if>
						</ispac:hasFunction>
						
            			<%--
            			<li>
							<html:link action="showCTAplicationsList">
								<bean:message key="menu.applications"/>
							</html:link>
            			</li>
            			--%>
						
						<ispac:hasFunction functions="FUNC_COMP_RULES_READ, FUNC_COMP_RULES_EDIT">
            			<li>
							<html:link action="showCTRulesList">
								<bean:message key="menu.rules"/>
				  			</html:link>
            			</li>
						</ispac:hasFunction>
						
						<ispac:hasFunction functions="FUNC_COMP_SEARCH_FORMS_READ, FUNC_COMP_SEARCH_FORMS_EDIT">
            			<li>
							<html:link action="showCTFrmSearchList">
								<bean:message key="menu.searchForms"/>
				  			</html:link>
            			</li>
						</ispac:hasFunction>

						<ispac:hasFunction functions="FUNC_COMP_CALENDARS_READ, FUNC_COMP_CALENDARS_EDIT">
            			<li>
							<html:link action="showCTCalendarsList">
								<bean:message key="menu.calendars"/>
				  			</html:link>
            			</li>
						</ispac:hasFunction>

						<ispac:hasFunction functions="FUNC_COMP_REPORTS_READ, FUNC_COMP_REPORTS_EDIT">
            			<li>
							<html:link action="showCTReportsList">
								<bean:message key="menu.reports"/>
				  			</html:link>
            			</li>
						</ispac:hasFunction>

						<ispac:hasFunction functions="FUNC_COMP_SYSTEM_VARS_READ, FUNC_COMP_SYSTEM_VARS_EDIT">
            			<li>
							<html:link action="showCTVbleSystemList">
								<bean:message key="menu.vbleSystem"/>
				  			</html:link>
            			</li>
						</ispac:hasFunction>
            			
						<ispac:hasFunction functions="FUNC_COMP_HELPS_READ, FUNC_COMP_HELPS_EDIT">
            			<li>
							<html:link action="showCTHelpList">
								<bean:message key="menu.Help"/>
				  			</html:link>
            			</li>
						</ispac:hasFunction>
						
	          		</ul>
				</li>
				</c:if>
				
				<c:set var="publisherManagementActive" value="${ISPACConfiguration.PUBLISHER_MANAGEMENT_ACTIVE}"/>
				<c:if test="${publicador && (empty publisherManagementActive || publisherManagementActive != 'false')}">
				<li class="menubar">
					<a href="#"
             			id="publisherActuator"
             			class="actuator"
             			accesskey='<bean:message key="menu.publisher.sectiontitle.accesskey"/>'><bean:message key="menu.publisher.sectiontitle"/></a>
	             	<ul id="publisherMenu" class="menu">
					
						<ispac:hasFunction functions="FUNC_PUB_ACTIONS_READ, FUNC_PUB_ACTIONS_EDIT">
            			<li>
							<html:link action="showPubActionsList">
								<bean:message key="menu.publisher.actions"/>
				  			</html:link>
            			</li>
						</ispac:hasFunction>
						
						<ispac:hasFunction functions="FUNC_PUB_APPLICATIONS_READ, FUNC_PUB_APPLICATIONS_EDIT">
            			<li>
							<html:link action="showPubApplicationsList">
								<bean:message key="menu.publisher.applications"/>
				  			</html:link>
            			</li>
						</ispac:hasFunction>
						
						<ispac:hasFunction functions="FUNC_PUB_CONDITIONS_READ, FUNC_PUB_CONDITIONS_EDIT">
            			<li>
							<html:link action="showPubConditionsList">
								<bean:message key="menu.publisher.conditions"/>
				  			</html:link>
            			</li>
						</ispac:hasFunction>
						
						<ispac:hasFunction functions="FUNC_PUB_RULES_READ, FUNC_PUB_RULES_EDIT">
            			<li>
							<html:link action="showPubRulesGroupList">
								<bean:message key="menu.publisher.rules"/>
				  			</html:link>
            			</li>
						</ispac:hasFunction>
						
						<ispac:hasFunction functions="FUNC_PUB_MILESTONES_READ, FUNC_PUB_MILESTONES_EDIT">
            			<li>
							<html:link action="showPubErrorMilestonesList">
								<bean:message key="menu.publisher.milestones.error"/>
				  			</html:link>
            			</li>
						</ispac:hasFunction>
						
	          		</ul>
				</li>
 				</c:if>

				<c:if test="${permisos}">
				<li class="menubar">
					<html:link styleId="usersActuator" styleClass="actuator" action="showInfoEntry">
	           				<bean:message key="menu.users.sectiontitle"/>
	       			</html:link>
				</li>
				</c:if>

			</ul>
		</td>
		<td><nobr>
			
			
			<c:set var="useOdtTemplates"><%=ConfigurationMgr.getVarGlobalBoolean((ieci.tdw.ispac.ispaclib.context.ClientContext)request.getAttribute("ClientContext"), ConfigurationMgr.USE_ODT_TEMPLATES, false)%></c:set>
			
			<div id="configLink">
				<ispac:rewrite id="configPage" page="config.jsp"/>
				<a href='javascript:;' onclick="javascript:window.open('<%=configPage%>','configFrame','status=no,scrollbars=no,location=no,toolbar=no,');">
					<img src='<ispac:rewrite href="img/config.gif"/>' style='vertical-align:middle' border='0'/>
					<bean:message key="title.config"/>
				</a>
			</div>
			<img height="1" width="5px" src="img/pixel.gif"/>
				<c:if test="${useOdtTemplates != 'true'}">
					<script>
						if (!isIEWord()) {
							document.getElementById("configLink").style.display = "block";
						}else{
							document.getElementById("configLink").style.display = "none";
						}
					</script>				
				</c:if>			
			</nobr>
		</td>
		<td>
			<div id="helpLink">
				<ispac:onlinehelp tipoObj="31" image="img/help.gif" titleKey="title.help"/>
			</div>
			<img height="1" width="5px" src="img/pixel.gif"/>
		</td>
	</tr>
</table>
</div>
<iframe src='' id='configFrame' name='configFrame' style='visibility:hidden;width:0px;height:0px;margin:0px;padding:0px;'></iframe>