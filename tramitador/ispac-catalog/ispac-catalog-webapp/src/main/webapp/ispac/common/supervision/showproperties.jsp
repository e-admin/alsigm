<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page import="java.util.*" %>



<c:choose>
<c:when test="${!empty param.block}">
	<c:set var="actualTab" value="${param.block}"/>
</c:when>
<c:otherwise>
	<c:set var="actualTab" value="1"/>
</c:otherwise>
</c:choose>
<c:url value="showInfoEntry.do" var="_viewtab">
	<c:param name="view">
		<bean:write name="view"/>
	</c:param>
	<c:param name="uid">
		<bean:write name="uid"/>
	</c:param>
	<logic:present name="uidGroup">
		<c:param name="uidGroup">
			<bean:write name="uidGroup" />
		</c:param>
	</logic:present>
</c:url>
<jsp:useBean id="_viewtab" type="java.lang.String"/> 

	<script>
		var block = '<c:out value="${actualTab}"/>';
	
		function hideTabs() {
			for(i=1;i<4;i++){
				if (document.getElementById('block'+ i)){
					if (document.getElementById('block'+ i).style.display=='block'){
						document.getElementById('block'+ i).style.display='none';
						document.getElementById('tdlink'+ i).className="unselect";
					}
				}
		    }
		}
	
		function showTab(i) {
		
			if (document.getElementById('block'+ i) != undefined) {
				document.getElementById('block'+ block).style.display='none';
				document.getElementById('tdlink'+ block).className="unselect";
				document.getElementById('block'+ i).style.display='block';
				document.getElementById('tdlink'+ i).className = 'select';
				block = i;
			}
			else {
				document.getElementById('block'+ block).style.display='block';
				document.getElementById('tdlink'+ block).className = 'select';
			}
			
			// Guardar la solapa que se muestra
	    	//document.defaultForm.block.value = block;
		}
		function loadTab(i){
			window.document.location='<%=_viewtab%>&block='+i;
		}
		function submit1(myform)
		{
	    	var checked = false;
			if (typeof myform.uids == 'undefined')
			{
				jAlert('<bean:message key="catalog.supervision.alert.noResponsibles"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
				return;
			}

			mymultibox = myform.uids;

			// cuando tenemos un checkbox
			if (typeof mymultibox.length == 'undefined')
			{
	      		if (mymultibox.checked)
	      			checked = true;
	      	}
	      	else
		    {
		    	for (var i=0; i < mymultibox.length; i++)
			        if (myform[i].checked)
			        	checked = true;
		    }
		    if (checked)
		    	myform.submit();
		    else
				jAlert('<bean:message key="catalog.supervision.alert.selResponsibles"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		}
		function submitFormSustitucion(url){
			form =	document.getElementById("formSustitucion");
			form.action = url;
			form.submit();
		}
		
		function submitFormById(idForm){	
			var	myform = document.getElementById(idForm);
			var checked = false;
			if (typeof myform.uids == 'undefined')
			{
			jAlert('<bean:message key="catalog.supervision.alert.noResponsibles"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
				return;
			}

			mymultibox = myform.uids;

			// cuando tenemos un checkbox
			if (typeof mymultibox.length == 'undefined')
			{
	      		if (mymultibox.checked)
	      			checked = true;
	      	}
	      	else
		    {
		    	for (var i=0; i < mymultibox.length; i++)
			        if (myform[i].checked)
			        	checked = true;
		    }
		    if (checked)
		    	myform.submit();
		    else
				jAlert('<bean:message key="catalog.supervision.alert.selResponsibles"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		}
		
		function submitFormByIdMultibox(idForm){
		jConfirm('<bean:message key="form.sustitucion.confirm.delete"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>', function(r) {
		if(r){
					var	myform = document.getElementById(idForm);
			var checked = false;
			if (typeof myform.multibox == 'undefined')
			{
			jAlert('<bean:message key="catalog.supervision.alert.noResponsibles"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
				return;
			}

			mymultibox = myform.multibox;

			// cuando tenemos un checkbox
			if (typeof mymultibox.length == 'undefined')
			{
	      		if (mymultibox.checked)
	      			checked = true;
	      	}
	      	else
		    {
		      //<= xq sino no tenemos en cuenta el ultimo elemento de la tabla 
		    	for (var i=0; i <= mymultibox.length; i++)
			        if (myform[i].checked)
			        	checked = true;
		    }
		    if (checked)
		    	myform.submit();
		    else
				jAlert('<bean:message key="catalog.supervision.alert.selResponsibles"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		}
							
		});	
		
	
		}
		
		function anyadir (url)
		{
			document.location.href = url;
		}
		
		function checkAdminPermission(clean) {
		
			( function($) {
			
				if ($("#enterCatalog").is(':checked')) {
					checkCatalogEditPermissions();
					updateCatalogPermissions(true);
				} else {
					updateCatalogPermissions(false);
					if (clean) {
						uncheckCatalogEditPermissions();
					}
				}
			} ) ( jQuery );
		}
		
		function checkCatalogEditPermissions() {
		
			( function($) {
				jQuery.each(catalogReadPermissions, function() {
					this.attr('checked', false);
				});
				jQuery.each(catalogEditPermissions, function() {
					this.attr('checked', true);
				});
			} ) ( jQuery );
		}
		
		function uncheckCatalogEditPermissions() {

			( function($) {
				jQuery.each(catalogEditPermissions, function() {
					this.attr('checked', false);
				});
			} ) ( jQuery );
		}
		
		function updateCatalogPermissions(disabled) {
			( function($) {
				jQuery.each(catalogReadPermissions, function() {
					this.attr('disabled', disabled);
				});
				jQuery.each(catalogEditPermissions, function() {
					this.attr('disabled', disabled);
				});
			} ) ( jQuery );
		}

		var catalogReadPermissions;
		var catalogEditPermissions;
		
		( function($) {
		
			$(document).ready( function() { 
				
				catalogReadPermissions = [ 
					$("#readProcedures"),
					$("#readStages"),
					$("#readTasks"),
					$("#readDocTypes"),
					$("#readTemplates"),
					$("#readSubprocesses"),
					$("#readSignCircuits"),
					$("#readCompEntities"),
					$("#readCompValidationTables"),
					$("#readCompHierarchicalTables"),
					$("#readCompRules"),
					$("#readCompSearchForms"),
					$("#readCompCalendars"),
					$("#readCompReports"),
					$("#readCompSystemVars"),
					$("#readCompHelps"),
					$("#readPubActions"),
					$("#readPubApplications"),
					$("#readPubConditions"),
					$("#readPubRules"),
					$("#readPubMilestones"),
					$("#readPermissions")
				];

				catalogEditPermissions = [ 
					$("#editProcedures"),
					$("#editStages"),
					$("#editTasks"),
					$("#editDocTypes"),
					$("#editTemplates"),
					$("#editSubprocesses"),
					$("#editSignCircuits"),
					$("#editCompEntities"),
					$("#editCompValidationTables"),
					$("#editCompHierarchicalTables"),
					$("#editCompRules"),
					$("#editCompSearchForms"),
					$("#editCompCalendars"),
					$("#editCompReports"),
					$("#editCompSystemVars"),
					$("#editCompHelps"),
					$("#editPubActions"),
					$("#editPubApplications"),
					$("#editPubConditions"),
					$("#editPubRules"),
					$("#editPubMilestones"),
					$("#editPermissions")
				];

				checkAdminPermission(false);
			});
		} ) ( jQuery );

	</script>

<logic:notEqual name="isGroups" value="true">
<logic:equal name="entryinfo" value="privileges"> 

	<c:choose>
	<c:when test="${RESPONSIBLE.user}">
		<c:set var="elementType" value="catalog.supervision.user"/>
	</c:when>
	<c:when test="${RESPONSIBLE.orgUnit}">
		<c:set var="elementType" value="catalog.supervision.organizationUnit"/>
	</c:when>
	<c:when test="${RESPONSIBLE.domain}">
		<c:set var="elementType" value="catalog.supervision.domain"/>
	</c:when>
	<c:when test="${RESPONSIBLE.group}">
		<c:set var="elementType" value="catalog.supervision.group"/>
	</c:when>
	<c:otherwise>
		<c:set var="elementType">&nbsp;</c:set>
	</c:otherwise>
	</c:choose>
	<jsp:useBean id="elementType" type="java.lang.String" />

<table cellspacing="5" border="0" width="100%"><tr><td>
	<table class="box" width="99%" border="0" cellspacing="1" cellpadding="6">
	<tr>
		<td class="divCabecera">
			<div class="divTexto">
				<span class="label"><bean:message key='<%=elementType%>'/> <bean:message key="catalog.supervision.selected"/>:</span>
				<c:out value="${RESPONSIBLE.respName}"/>
			</div>
		</td> 
	</tr>
	<tr>
	<td class="blank">
		<table width="100%" border="0" cellspacing="2" cellpadding="2">
		<tr>
			<td width="100%">
			<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="select" id="tdlink1" align="center" onclick="loadTab(1)">
							<NOBR><bean:message key="catalog.supervision.functions"/></NOBR>
						</td>
				<!--  Si no es la vista de grupos ni la de estr de organizacion -->		
				<logic:notEqual name='isGroups' value='true'>
<%-- 
				<logic:notEqual name='isOrgUnit' value='true'>
--%>					
						<td width="5px" class="tabSeparator"><img height="1" width="5px" src="img/pixel.gif"/></td>
						<td class="unselect" id="tdlink2" align="center" onclick="loadTab(2)">
							<NOBR><bean:message key="catalog.supervision"/></NOBR>
						</td>
						<td width="5px" class="tabSeparator"><img height="1" width="5px" src="img/pixel.gif"/></td>
						<td class="unselect" id="tdlink3" align="center" onclick="loadTab(3)">
							<NOBR><bean:message key="catalog.sustitucion"/></NOBR>
						</td>
						
<%-- 
				</logic:notEqual>
--%>
				</logic:notEqual>
						<td width="100%" class="tabSeparator"><img height="1" width="5px" src="img/pixel.gif"/></td>
					</tr>
				</table>
				<!-- BLOQUE DE CAMPOS -->
				<div style="display:block" id="block1" class="tabFormtable">
					<table cellspacing="0" border="0" width="100%">
						<tr>
							<td>
								<table border="0" width="95%" align="center">
								<tr>
					        		<td align="left" class="error" colspan="2"><html:errors/></td>
					    		</tr>
								<!-- FUNCIONES -->
								<tr>
									<td>
										<c:url value="saveFunctions.do" var="_saveFunctions">
											<c:param name="view">
												<bean:write name="view"/>
											</c:param>
											<c:param name="uid">
												<bean:write name="uid"/>
											</c:param>
											<logic:present name="uidGroup">
												<c:param name="uidGroup">
													<bean:write name="uidGroup" />
												</c:param>
											</logic:present>
											<c:param name="block" value="1"/>
										</c:url>
										<jsp:useBean id="_saveFunctions" type="java.lang.String"/> 
														
										<html:form action='<%=_saveFunctions%>'>

											<ispac:hasFunction functions="FUNC_PERM_EDIT">
											<div class="onecol">
												<html:submit styleClass="form_button_white">
													<bean:message key="forms.button.save"/>
												</html:submit>
											</div>
											</ispac:hasFunction>
										
											<%-- SUPERVISOR --%>
											<c:if test="${RESPONSIBLE.user}">
											<div class="onecol">
												<h2><bean:message key="catalog.supervision.function.section.mgr"/></h2>
												<div class="twocol">
													<div class="column first">
														<table>
															<tr>
																<th></th>
																<th><bean:message key="catalog.supervision.function.types.read"/></th>
																<th><bean:message key="catalog.supervision.function.types.edit"/></th>
															</tr>
															<tr>
																<td class="label" align="left">
																	<bean:message key="catalog.supervision.function.manageSupervisor"/>
																</td>
																<td class="checkbox"><html:checkbox property="monitoringSupervisor" /></td>
																<td class="checkbox"><html:checkbox property="totalSupervisor" /></td>
															</tr>
														</table>
													</div>
												</div>
											</div>
											</c:if>
											
											<div class="onecol">
												<h2><bean:message key="catalog.supervision.function.section.catalog"/></h2>
												<div class="twocol">
													<div class="column first">
														<table>
															<tr>
																<td class="label" align="left">
																	<bean:message key="catalog.supervision.function.enterCatalog"/>
																</td>
																<td class="checkbox">&nbsp;</td>
																<td class="checkbox"><html:checkbox styleId="enterCatalog" property="enterCatalog" onclick="javascrip:checkAdminPermission(true);"/></td>
															</tr>
														</table>
													</div>
												</div>

												<div class="twocol">
													<div class="column first">
														<h3><bean:message key="catalog.supervision.function.group.inventary"/></h3>
														<table>
															<tr>
																<th></th>
																<th><bean:message key="catalog.supervision.function.types.read"/></th>
																<th><bean:message key="catalog.supervision.function.types.edit"/></th>
															</tr>
															
															<tr>
																<td class="label" align="left">
																	<bean:message key="catalog.supervision.function.manageProcedures"/>
																</td>
																<td class="checkbox"><html:checkbox styleId="readProcedures" property="readProcedures" /></td>
																<td class="checkbox"><html:checkbox styleId="editProcedures" property="editProcedures" /></td>
															</tr>
															<tr>
																<td class="label" align="left">
																	<bean:message key="catalog.supervision.function.manageStages"/>
																</td>
																<td class="checkbox"><html:checkbox styleId="readStages" property="readStages" /></td>
																<td class="checkbox"><html:checkbox styleId="editStages" property="editStages" /></td>
															</tr>
															<tr>
																<td class="label" align="left">
																	<bean:message key="catalog.supervision.function.manageTasks"/>
																</td>
																<td class="checkbox"><html:checkbox styleId="readTasks" property="readTasks" /></td>
																<td class="checkbox"><html:checkbox styleId="editTasks" property="editTasks" /></td>
															</tr>
															<tr>
																<td class="label" align="left">
																	<bean:message key="catalog.supervision.function.manageDocTypes"/>
																</td>
																<td class="checkbox"><html:checkbox styleId="readDocTypes" property="readDocTypes" /></td>
																<td class="checkbox"><html:checkbox styleId="editDocTypes" property="editDocTypes" /></td>
															</tr>
															<tr>
																<td class="label" align="left">
																	<bean:message key="catalog.supervision.function.manageTemplates"/>
																</td>
																<td class="checkbox"><html:checkbox styleId="readTemplates" property="readTemplates" /></td>
																<td class="checkbox"><html:checkbox styleId="editTemplates" property="editTemplates" /></td>
															</tr>
															<tr>
																<td class="label" align="left">
																	<bean:message key="catalog.supervision.function.manageSubprocesses"/>
																</td>
																<td class="checkbox"><html:checkbox styleId="readSubprocesses" property="readSubprocesses" /></td>
																<td class="checkbox"><html:checkbox styleId="editSubprocesses" property="editSubprocesses" /></td>
															</tr>
															<tr>
																<td class="label" align="left">
																	<bean:message key="catalog.supervision.function.manageSignCircuits"/>
																</td>
																<td class="checkbox"><html:checkbox styleId="readSignCircuits" property="readSignCircuits" /></td>
																<td class="checkbox"><html:checkbox styleId="editSignCircuits" property="editSignCircuits" /></td>
															</tr>
														</table>
													</div>
													<div class="column last">
														<h3><bean:message key="catalog.supervision.function.group.components"/></h3>
														<table>
															<tr>
																<th></th>
																<th><bean:message key="catalog.supervision.function.types.read"/></th>
																<th><bean:message key="catalog.supervision.function.types.edit"/></th>
															</tr>
															
															<tr>
																<td class="label" align="left">
																	<bean:message key="catalog.supervision.function.manageCompEntities"/>
																</td>
																<td class="checkbox"><html:checkbox styleId="readCompEntities" property="readCompEntities" /></td>
																<td class="checkbox"><html:checkbox styleId="editCompEntities" property="editCompEntities" /></td>
															</tr>
															<tr>
																<td class="label" align="left">
																	<bean:message key="catalog.supervision.function.manageCompValidationTables"/>
																</td>
																<td class="checkbox"><html:checkbox styleId="readCompValidationTables" property="readCompValidationTables" /></td>
																<td class="checkbox"><html:checkbox styleId="editCompValidationTables" property="editCompValidationTables" /></td>
															</tr>
															<tr>
																<td class="label" align="left">
																	<bean:message key="catalog.supervision.function.manageCompHierarchicalTables"/>
																</td>
																<td class="checkbox"><html:checkbox styleId="readCompHierarchicalTables" property="readCompHierarchicalTables" /></td>
																<td class="checkbox"><html:checkbox styleId="editCompHierarchicalTables" property="editCompHierarchicalTables" /></td>
															</tr>
															<tr>
																<td class="label" align="left">
																	<bean:message key="catalog.supervision.function.manageCompRules"/>
																</td>
																<td class="checkbox"><html:checkbox styleId="readCompRules" property="readCompRules" /></td>
																<td class="checkbox"><html:checkbox styleId="editCompRules" property="editCompRules" /></td>
															</tr>
															<tr>
																<td class="label" align="left">
																	<bean:message key="catalog.supervision.function.manageCompSearchForms"/>
																</td>
																<td class="checkbox"><html:checkbox styleId="readCompSearchForms" property="readCompSearchForms" /></td>
																<td class="checkbox"><html:checkbox styleId="editCompSearchForms" property="editCompSearchForms" /></td>
															</tr>
															<tr>
																<td class="label" align="left">
																	<bean:message key="catalog.supervision.function.manageCompCalendars"/>
																</td>
																<td class="checkbox"><html:checkbox styleId="readCompCalendars" property="readCompCalendars" /></td>
																<td class="checkbox"><html:checkbox styleId="editCompCalendars" property="editCompCalendars" /></td>
															</tr>
															<tr>
																<td class="label" align="left">
																	<bean:message key="catalog.supervision.function.manageCompReports"/>
																</td>
																<td class="checkbox"><html:checkbox styleId="readCompReports" property="readCompReports" /></td>
																<td class="checkbox"><html:checkbox styleId="editCompReports" property="editCompReports" /></td>
															</tr>
															<tr>
																<td class="label" align="left">
																	<bean:message key="catalog.supervision.function.manageCompSystemVars"/>
																</td>
																<td class="checkbox"><html:checkbox styleId="readCompSystemVars" property="readCompSystemVars" /></td>
																<td class="checkbox"><html:checkbox styleId="editCompSystemVars" property="editCompSystemVars" /></td>
															</tr>
															<tr>
																<td class="label" align="left">
																	<bean:message key="catalog.supervision.function.manageCompHelps"/>
																</td>
																<td class="checkbox"><html:checkbox styleId="readCompHelps" property="readCompHelps" /></td>
																<td class="checkbox"><html:checkbox styleId="editCompHelps" property="editCompHelps" /></td>
															</tr>
														</table>
													</div>
												</div>
												
												<div class="twocol">
													<div class="column first">
														<h3><bean:message key="catalog.supervision.function.group.publisher"/></h3>
														<table>
															<tr>
																<th></th>
																<th><bean:message key="catalog.supervision.function.types.read"/></th>
																<th><bean:message key="catalog.supervision.function.types.edit"/></th>
															</tr>
															
															<tr>
																<td class="label" align="left">
																	<bean:message key="catalog.supervision.function.managePubActions"/>
																</td>
																<td class="checkbox"><html:checkbox styleId="readPubActions" property="readPubActions" /></td>
																<td class="checkbox"><html:checkbox styleId="editPubActions" property="editPubActions" /></td>
															</tr>
															<tr>
																<td class="label" align="left">
																	<bean:message key="catalog.supervision.function.managePubApplications"/>
																</td>
																<td class="checkbox"><html:checkbox styleId="readPubApplications" property="readPubApplications" /></td>
																<td class="checkbox"><html:checkbox styleId="editPubApplications" property="editPubApplications" /></td>
															</tr>
															<tr>
																<td class="label" align="left">
																	<bean:message key="catalog.supervision.function.managePubConditions"/>
																</td>
																<td class="checkbox"><html:checkbox styleId="readPubConditions" property="readPubConditions" /></td>
																<td class="checkbox"><html:checkbox styleId="editPubConditions" property="editPubConditions" /></td>
															</tr>
															<tr>
																<td class="label" align="left">
																	<bean:message key="catalog.supervision.function.managePubRules"/>
																</td>
																<td class="checkbox"><html:checkbox styleId="readPubRules" property="readPubRules" /></td>
																<td class="checkbox"><html:checkbox styleId="editPubRules" property="editPubRules" /></td>
															</tr>
															<tr>
																<td class="label" align="left">
																	<bean:message key="catalog.supervision.function.managePubMilestones"/>
																</td>
																<td class="checkbox"><html:checkbox styleId="readPubMilestones" property="readPubMilestones" /></td>
																<td class="checkbox"><html:checkbox styleId="editPubMilestones" property="editPubMilestones" /></td>
															</tr>

														</table>
													</div>
													<div class="column last">
														<h3><bean:message key="catalog.supervision.function.group.permissions"/></h3>
														<table>
															<tr>
																<th></th>
																<th><bean:message key="catalog.supervision.function.types.read"/></th>
																<th><bean:message key="catalog.supervision.function.types.edit"/></th>
															</tr>
															
															<tr>
																<td class="label" align="left">
																	<bean:message key="catalog.supervision.function.managePermissions"/>
																</td>
																<td class="checkbox"><html:checkbox styleId="readPermissions" property="readPermissions" /></td>
																<td class="checkbox"><html:checkbox styleId="editPermissions" property="editPermissions" /></td>
															</tr>
														</table>
													</div>
												</div>
											</div>
										</html:form>
									</td>
								</tr>
								<tr>
									<td width="20px">
										<img src='<ispac:rewrite href="img/pixel.gif"/>'/>
									</td>
								</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				<!-- FIN BLOQUE 1 DE CAMPOS -->

		<logic:notEqual name='isGroups' value='true'>
				<!-- BLOQUE 2 DE CAMPOS -->

				<div style="display:none" id="block2" class="tabFormtable">
					<c:url value="showResponsiblesForSupervision.do" var="_showResponsiblesForSupervision">
						<c:param name="view">
							<bean:write name="view"/>
						</c:param>
						<c:param name="uid">
							<bean:write name="uid"/>
						</c:param>
						<logic:present name="uidGroup">
							<c:param name="uidGroup">
								<bean:write name="uidGroup" />
							</c:param>
						</logic:present>
					</c:url>
					<jsp:useBean id="_showResponsiblesForSupervision" type="java.lang.String"/>

					<table border="0" width="95%" align="center">
					<!-- SUPERVISION SEGUIMIENTO -->
					<tr><td height="10px"><img src='<ispac:rewrite href="img/pixel.gif"/>' height="10px"/></td></tr>
					<tr>
						<td class="boldlabel"><bean:message key="catalog.supervision.monitoring.type"/>:</td>
					</tr>
					<tr>
						<td>
							<div class="buttonList">
							<ispac:hasFunction functions="FUNC_PERM_EDIT">
							<ul>
								<li>
									<a href="javascript:showFrame('workframe','<ispac:rewrite action='<%=_showResponsiblesForSupervision%>'/>'+ '&captionkey=catalog.supervision.title.monitoring.type&kindOfSuperv=1',640,480)"><nobr><bean:message key="forms.button.add"/></nobr></a>
								</li>
								
							<c:choose>
								<c:when test="${!empty followedModeSuperviseds}">
									<li>
										<a href="javascript:submit1(document.deleteFollowedModeSupervisedsForm);" > 
											<nobr><bean:message key="forms.button.delete"/></nobr>
										</a>
									</li>
								</c:when>
								<c:otherwise>
									<li style="background-color: #ddd; color: #aaa; cursor: default;">
										<nobr><bean:message key="forms.button.delete"/></nobr>
									</li>
							</c:otherwise>
							</c:choose>
							</ul>
							</ispac:hasFunction>
							</div>
						</td>
					</tr>
						<c:url value="deleteFollowedModeSuperviseds.do" var="_deleteFollowedModeSuperviseds">
							<c:param name="view">
								<bean:write name="view"/>
							</c:param>
							<c:param name="uid">
								<bean:write name="uid"/>
							</c:param>
							<logic:present name="uidGroup">
								<c:param name="uidGroup">
									<bean:write name="uidGroup" />
								</c:param>
							</logic:present>
							<c:param name="block" value="2"/>
							
						</c:url>
						<jsp:useBean id="_deleteFollowedModeSuperviseds" type="java.lang.String"/>

						<html:form action='<%=_deleteFollowedModeSuperviseds%>'>
							
						<tr>
							<td width="85%">
								<table width="100%" class="tableborder">
									<tr>
										<td>
											<div class="scrollsupervision">
												<table width="100%">
													<logic:iterate id="supervised" name="followedModeSuperviseds" type="ieci.tdw.ispac.ispaclib.bean.ItemBean">
														<logic:iterate id="format" name="SupervisionFormatter" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
															<logic:equal name="format" property="property" value="RESPNAME">
																<tr>
																	<ispac:hasFunction functions="FUNC_PERM_EDIT">
																	<td width="20px">
																		<html:multibox property="uids">
																			<bean:write name='supervised' property='property(UID)' />
																		</html:multibox>
																	</td>
																	</ispac:hasFunction>
																	<c:set var="isUser"><bean:write name='supervised' property='property(USER)' /></c:set>
																	<c:set var="isGroup"><bean:write name='supervised' property='property(GROUP)' /></c:set>
																	<c:set var="isOrguni"><bean:write name='supervised' property='property(ORGUNIT)' /></c:set>
																	<td width="20px">
																		<c:choose>
																			<c:when test="${isUser=='true'}">
																				<img src='<ispac:rewrite href="img/user.gif"/>'/>
																			</c:when>
																			<c:when test="${isGroup=='true'}">
																				<img src='<ispac:rewrite href="img/group.gif"/>'/>
																			</c:when>
																			<c:when test="${isOrguni=='true'}">
																				<img src='<ispac:rewrite href="img/org.gif"/>'/>
																			</c:when>
																		</c:choose>
																	</td>
																	<td class="ldapentry" title='<%= supervised.getProperty("RESPNAME") %>'>
																		<%=format.formatProperty(supervised)%>
																	</td>
																</tr>
															</logic:equal>
														</logic:iterate>
													</logic:iterate>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr><td height="10px"><img src='<ispac:rewrite href="img/pixel.gif"/>' height="10px"/></td></tr>
								
						</html:form>
		<%--
		<c:if test="${RESPONSIBLE.user}">
		--%>
					<c:url value="deleteTotalModeSuperviseds.do" var="_deleteTotalModeSuperviseds">
							<c:param name="view">
								<bean:write name="view"/>
							</c:param>
							<c:param name="uid">
								<bean:write name="uid"/>
							</c:param>
							<logic:present name="uidGroup">
								<c:param name="uidGroup">
									<bean:write name="uidGroup" />
								</c:param>
							</logic:present>
							<c:param name="block" value="2"/>
					</c:url>
					<jsp:useBean id="_deleteTotalModeSuperviseds" type="java.lang.String"/>
							<!-- SUPERVISION TOTAL -->
							<tr>
								<td class="boldlabel"><bean:message key="catalog.supervision.total.type"/>:</td>
							</tr>
							<tr>
								<td>
									<div class="buttonList">
									<ispac:hasFunction functions="FUNC_PERM_EDIT">
									<ul>
										<li>
											<a href="javascript:showFrame('workframe','<ispac:rewrite action='<%=_showResponsiblesForSupervision%>'/>'+ '&captionkey=catalog.supervision.title.total.type&kindOfSuperv=2',640,480)"><nobr><bean:message key="forms.button.add"/></nobr></a>
										</li>
										<c:choose>
										<c:when test="${!empty totalModeSuperviseds}">
										<li>
											<a href="javascript:submit1(document.deleteTotalModeSupervisedsForm);" > 
												<nobr><bean:message key="forms.button.delete"/></nobr>
											</a>
										</li>
										</c:when>
										<c:otherwise>
											<li style="background-color: #ddd; color: #aaa; cursor: default;">
												<nobr><bean:message key="forms.button.delete"/></nobr>
											</li>
										</c:otherwise>
										</c:choose>
									</ul>
									</ispac:hasFunction>
									</div>
								</td>
							</tr>

							<html:form action='<%=_deleteTotalModeSuperviseds%>'>
							
								<tr>
									<td width="85%">
										<table width="100%" class="tableborder">
												<tr>
													<td>
														<div class="scrollsupervision">
															<table width="100%">
																<logic:iterate id="supervised" name="totalModeSuperviseds" type="ieci.tdw.ispac.ispaclib.bean.ItemBean">
																	<logic:iterate id="format" name="SupervisionFormatter" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
																		<logic:equal name="format" property="property" value="RESPNAME">
																			<tr>
																				<ispac:hasFunction functions="FUNC_PERM_EDIT">
																				<td width="20px">
																					<html:multibox property="uids" >
																						<bean:write name='supervised' property='property(UID)' />
																					</html:multibox>
																				</td>
																				</ispac:hasFunction>
																	<c:set var="isUser"><bean:write name='supervised' property='property(USER)' /></c:set>
																	<c:set var="isGroup"><bean:write name='supervised' property='property(GROUP)' /></c:set>
																	<c:set var="isOrguni"><bean:write name='supervised' property='property(ORGUNIT)' /></c:set>
																	<td width="20px">
																		<c:choose>
																			<c:when test="${isUser=='true'}">
																				<img src='<ispac:rewrite href="img/user.gif"/>'/>
																			</c:when>
																			<c:when test="${isGroup=='true'}">
																				<img src='<ispac:rewrite href="img/group.gif"/>'/>
																			</c:when>
																			<c:when test="${isOrguni=='true'}">
																				<img src='<ispac:rewrite href="img/org.gif"/>'/>
																			</c:when>
																		</c:choose>
																	</td>
																				<td class="ldapentry" title='<%= supervised.getProperty("RESPNAME") %>'>
																					<%=format.formatProperty(supervised)%>
																				</td>
																			</tr>
																		</logic:equal>
																	</logic:iterate>
																</logic:iterate>
															</table>
														</div>
													</td>
												</tr>
										</table>
									</td>
								</tr>
								<tr><td height="10px"><img src='<ispac:rewrite href="img/pixel.gif"/>' height="10px"/></td></tr>
							</html:form>
				
				<%--
				</c:if>
				--%>
				</table>
				
			</div>
			<!-- FIN BLOQUE 2 DE CAMPOS -->

			<!-- BLOQUE 3 DE CAMPOS -->
			<!-- SUSTITUCION -->
			<div style="display:none" id="block3" class="tabFormtable">
			
				<table border="0" width="95%" align="center">
							<tr><td height="10px"><img src='<ispac:rewrite href="img/pixel.gif"/>' height="10px"/></td></tr>
							
							<!-- SUSTITUTOS -->
							<tr>
								<td class="boldlabel"><bean:message key="catalog.sustitucion.sustituidoPor"/>:</td>
							</tr>
							
							<c:url value="gestionSustituciones.do" var="_deleteSustituto">
									<c:param name="method" value="deleteSubstitute"/>
								<c:param name="view">
									<bean:write name="view"/>
								</c:param>
								<c:param name="uid">
									<bean:write name="uid"/>
								</c:param>
								<logic:present name="uidGroup">
									<c:param name="uidGroup">
										<bean:write name="uidGroup" />
									</c:param>
								</logic:present>
								<c:param name="block" value="3"/>
							</c:url>
							<jsp:useBean id="_deleteSustituto" type="java.lang.String"/>
							
							<%-- Enlace a la pantalla de añadir sustitutos --%>
							<c:url value="gestionSustituciones.do" var="_showSustitutos">
								<c:param name="method" value="showSustitutes"/>
								<c:param name="view">
									<bean:write name="view"/>
								</c:param>
								<c:param name="uid">
									<bean:write name="uid"/>
								</c:param>
								<logic:present name="uidGroup">
									<c:param name="uidGroup">
										<bean:write name="uidGroup" />
									</c:param>
								</logic:present>
								<c:param name="block" value="3"/>
							</c:url>
							
							<c:url value="gestionSustituciones.do" var="_showSustitutosHistoric">
								<c:param name="method" value="showSustitutesHistoric"/>
								<c:param name="view">
									<bean:write name="view"/>
								</c:param>
								<c:param name="uid">
									<bean:write name="uid"/>
								</c:param>
								<logic:present name="uidGroup">
									<c:param name="uidGroup">
										<bean:write name="uidGroup" />
									</c:param>
								</logic:present>
								<c:param name="block" value="3"/>
							</c:url>
							<jsp:useBean id="_showSustitutos" type="java.lang.String"/>	
							<jsp:useBean id="_showSustitutosHistoric" type="java.lang.String"/>											
							<tr>
									<td>
										<div class="buttonList">
										<ul>
											<ispac:hasFunction functions="FUNC_PERM_EDIT">
											<li>
												<a href="javascript:showFrame('workframe','<ispac:rewrite action='<%=_showSustitutos%>'/>' + '&captionkey=catalog.sustitucion.title',640,480)"><nobr><bean:message key="forms.button.add"/></nobr></a>
											</li>
											</ispac:hasFunction>
											
											<ispac:hasFunction functions="FUNC_PERM_EDIT">
											<c:choose>
											<c:when test="${!empty SUSTITUTOS_LIST}">
											<li>
												<a href="javascript:submitFormByIdMultibox('formDeleteSustituto');" > 
													<nobr><bean:message key="forms.button.delete"/></nobr>
												</a>
											</li>
											</c:when>
											<c:otherwise>
												<li style="background-color: #ddd; color: #aaa; cursor: default;">
													<nobr><bean:message key="forms.button.delete"/></nobr>
												</li>
											</c:otherwise>
											</c:choose>
											</ispac:hasFunction>
											
											<li>
												<a href="javascript:showFrame('workframe','<ispac:rewrite action='<%=_showSustitutosHistoric%>'/>' + '&captionkey=catalog.sustitucionHistoric.title',640,480)"><nobr><bean:message key="forms.button.showHistoric"/></nobr></a>
											</li>
										</ul>
										</div>
									</td>
							</tr>
							
							<html:form styleId="formDeleteSustituto" action='<%=_deleteSustituto%>'>
								<tr>
									<td width="85%">
									<table width="100%" class="tableborder">
											<tr>
												<td>
												
													<%-- Enlace a la pantalla para modificar el periodo de sustitucion --%>
													<c:url value="gestionSustituciones.do" var="_modifyFechSustitucion">
														<c:param name="method" value="viewFechaSustitucion"/>
														<c:param name="view">
															<bean:write name="view"/>
														</c:param>
														<c:param name="uid">
															<bean:write name="uid"/>
														</c:param>
														<logic:present name="uidGroup">
															<c:param name="uidGroup">
																<bean:write name="uidGroup" />
															</c:param>
														</logic:present>
														<c:param name="block" value="3"/>
													</c:url>
													<jsp:useBean id="_modifyFechSustitucion" type="java.lang.String"/>	
												
													<display:table name="SUSTITUTOS_LIST" 
																   id='itemobj' 
																   export="false" 
																   class="tableDisplay"
																   sort="list"
																   requestURI=''>
																   
														<logic:present name="itemobj">
													
															<bean:define id='item' name='itemobj' type='ieci.tdw.ispac.ispaclib.bean.ObjectBean'/>
															
															<logic:iterate name="SustitutoFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
															
																<logic:equal name="format" property="fieldType" value="CHECKBOX">
																
																	<ispac:hasFunction functions="FUNC_PERM_EDIT">
																	<display:column title='<%="<input type=\'checkbox\' onclick=\'javascript:checkAll(document.sustitutionsForm.multibox, this);\'/>"%>'
																					media='<%=format.getMedia()%>'
																					headerClass='<%=format.getHeaderClass()%>' 
																					sortable='<%=format.getSortable()%>'
																					decorator='<%=format.getDecorator()%>'
																					class='<%=format.getColumnClass()%>'
																					style="text-align:center">
																					
																		<html:multibox property="multibox">
																			<%=format.formatProperty(item)%>
																		</html:multibox>
																		
																	</display:column>
																	</ispac:hasFunction>
																	
															  	</logic:equal>
														
																<logic:equal name="format" property="fieldType" value="RESPONSIBLE">
																
																	<display:column titleKey='<%=format.getTitleKey()%>'
																					media='<%=format.getMedia()%>'
																					headerClass='<%=format.getHeaderClass()%>'
																					class='<%=format.getColumnClass()%>'
																					sortable='<%=format.getSortable()%>'
																					decorator='<%=format.getDecorator()%>'
																					comparator='<%=format.getComparator()%>'>
																					
																		<c:set var="isUser"><bean:write name='item' property='property(USER)' /></c:set>
																		<c:set var="isGroup"><bean:write name='item' property='property(GROUP)' /></c:set>
																		<c:set var="isOrguni"><bean:write name='item' property='property(ORGUNIT)' /></c:set>
																		<span width="20px">
																			<c:choose>
																				<c:when test="${isUser}">
																					<img src='<ispac:rewrite href="img/user.gif"/>'/>
																				</c:when>
																				<c:when test="${isGroup}">
																					<img src='<ispac:rewrite href="img/group.gif"/>'/>
																				</c:when>
																				<c:when test="${isOrguni}">
																					<img src='<ispac:rewrite href="img/org.gif"/>'/>
																				</c:when>
																			</c:choose>
																		</span>
																		
																		<%=format.formatProperty(item)%>
																					
																	</display:column>
																	
																</logic:equal>
																
																<logic:equal name="format" property="fieldType" value="DATA">
																
																	<display:column titleKey='<%=format.getTitleKey()%>'
																					media='<%=format.getMedia()%>'
																					headerClass='<%=format.getHeaderClass()%>'
																					class='<%=format.getColumnClass()%>'
																					sortable='<%=format.getSortable()%>'
																					sortProperty='<%=format.getPropertyName()%>'
																					decorator='<%=format.getDecorator()%>'
																					comparator='<%=format.getComparator()%>'>
																					
																			<%=format.formatProperty(item)%>
																			
																	</display:column>
																	
																</logic:equal>
																
																<logic:equal name="format" property="fieldType" value="GROUP">
																
																	<display:column titleKey='<%=format.getTitleKey()%>'
																					media='<%=format.getMedia()%>'
																					headerClass='<%=format.getHeaderClass()%>'
																					class='<%=format.getColumnClass()%>'
																					sortable='<%=format.getSortable()%>'
																					sortProperty='<%=format.getPropertyName()%>'
																					decorator='<%=format.getDecorator()%>'
																					comparator='<%=format.getComparator()%>'>
																					
																			<%=format.formatProperty(item)%>
																			
																	</display:column>
																	
																</logic:equal>
																
																 <logic:equal name="format" property="fieldType" value="GROUP_LINK_JS">
																 
																  	<display:column titleKey='<%=format.getTitleKey()%>'
																  					media='<%=format.getMedia()%>'
																  					headerClass='<%=format.getHeaderClass()%>'
																  					class='<%=format.getColumnClass()%>'
																  					sortable='<%=format.getSortable()%>'
																  					sortProperty='<%=format.getPropertyName()%>'
																  					decorator='<%=format.getDecorator()%>'
																					comparator='<%=format.getComparator()%>'>
																		
																		<%--	
																  		<a href="javascript: showFrame('workframe','<%=format.getUrl()%>&<%=format.getId()%>=<bean:write name="item" property='<%=format.getPropertyLink()%>'/>',640,480)" class='<%=format.getStyleClass()%>' >
																  		--%>
																  		<a href="javascript: showFrame('workframe','<ispac:rewrite action='<%=_modifyFechSustitucion%>'/>' + '&<%=format.getId()%>=<bean:write name="item" property='<%=format.getPropertyLink()%>'/>',640,480)" class='<%=format.getStyleClass()%>' >
																  		
																			<%=format.formatProperty(item)%>
																			
																  		</a>
																  		
																  	</display:column>
																  	
																</logic:equal>
																
															</logic:iterate>
															
														</logic:present>
														
													</display:table>
																																
													<%--
													<div class="scrollsupervision">
														<table width="100%">
															<logic:iterate id="sustituto" name="SUSTITUTOS_LIST" type="ieci.tdw.ispac.ispaclib.bean.ItemBean">
																<logic:iterate id="format" name="SustitutoFormatter" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
																	<logic:equal name="format" property="property" value="NAME">
																		<tr>
																			<td width="20px">
																				<html:multibox property="multibox" >
																					<bean:write name='sustituto' property='property(ID)' />
																				</html:multibox>
																			</td>
																			<c:set var="isUser"><bean:write name='sustituto' property='property(USER)' /></c:set>
																			<c:set var="isGroup"><bean:write name='sustituto' property='property(GROUP)' /></c:set>
																			<c:set var="isOrguni"><bean:write name='sustituto' property='property(ORGUNIT)' /></c:set>
																			<td width="20px">
																				<c:choose>
																					<c:when test="${isUser}">
																						<img src='<ispac:rewrite href="img/user.gif"/>'/>
																					</c:when>
																					<c:when test="${isGroup}">
																						<img src='<ispac:rewrite href="img/group.gif"/>'/>
																					</c:when>
																					<c:when test="${isOrguni}">
																						<img src='<ispac:rewrite href="img/org.gif"/>'/>
																					</c:when>
																				</c:choose>
																			</td>
																			<td class="ldapentry" title='<%= sustituto.getProperty("NAME") %>'>
																				<%=format.formatProperty(sustituto)%>
																			</td>
																		</tr>
																	</logic:equal>
																</logic:iterate>
															</logic:iterate>
														</table>
													</div>
													--%>
													
												</td>
											</tr>
										</table>
									</td> 
								</tr>
							</html:form>
							<tr><td height="10px"><img src='<ispac:rewrite href="img/pixel.gif"/>' height="10px"/></td></tr>

						</table>
				</div>
				<!-- FIN BLOQUE 3 DE DATOS -->

		</logic:notEqual>
	</td>
	</tr>
</table>


</logic:equal>

	<table cellspacing="5" border="0" width="100%">
		<tr>
			<td>
				<logic:equal name="entryinfo" value="properties">
					<table class="tableborder" cellpadding='0' cellspacing='0' width="100%">
						<tr>
							<td>
								<table border="0" width="90%" align="center">
									<tr><td colspan='2'><img src='<ispac:rewrite href="img/pixel.gif"/>' height='14px'></td></tr>
									<logic:iterate id="attribute" name="attributes" type="java.util.Map.Entry">
										<%
												String attrName = (String) attribute.getKey();
												List attrValues = (List) attribute.getValue();
												pageContext.setAttribute("attrValues", attrValues);
										%>
										<tr>
											<td class="ldapentry" valign='top'><%= attrName %>:</td>
											<td>
												<table cellspacing='0 'valign='top'>
													<logic:iterate id="attrValue" name="attrValues" scope="page">
														<tr>
															<td class="ldapentry"><%= attrValue %></td>
														</tr>
													</logic:iterate>
												</table>
											</td>
										</tr>
									</logic:iterate>
									<tr><td colspan='2'><img src='<ispac:rewrite href="img/pixel.gif"/>' height='14px'></td></tr>
								</table>
							</td>
						</tr>
					</table>
				</logic:equal>
				
			</td>
		</tr>
	</table>
</td></tr></table>	

<script>
hideTabs();
showTab(block);
</script>
</logic:notEqual>