<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<bean:define id="entapp" name="defaultForm" property="entityApp" />

<script type="text/javascript" src='<ispac:rewrite href="../scripts/ispac-forms.js"/>'> </script>

<script>
	var block = 1;

	function showTab(i)
	{
		document.getElementById('block'+ block).style.display='none';
		document.getElementById('tdlink'+ block).className="unselect";
		document.getElementById('block'+ i).style.display='block';
		document.getElementById('tdlink'+ i).className = 'select';
		block = i;
	}
</script>

<div id="navmenutitle">
	<bean:message key="form.pstage.mainTitle"/>
</div>

<!-- ENCABEZADO PROCEDIMIENTO -->
<div id="procTitle">
	<table width="95%" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td align="left" width="60%" id="procTitle">
			<bean:message key="form.pstage.propertyLabel.procedure"/>:&nbsp;&nbsp;
			<html:link action="showPProcedure.do?entityId=22" paramId="regId" paramName="defaultForm" paramProperty="property(SPAC_P_PCD:ID)">
				<bean:write name="defaultForm" property="property(SPAC_P_PCD:NOMBRE)" />
  			</html:link>
			</td>
			<td align="right" width="20%" id="procTitle">
				<bean:define id="sVersion" name="defaultForm" property="property(SPAC_P_PCD:NVERSION)" type="java.lang.String"/>
				<%
					int iVersion = Integer.parseInt(sVersion);
					iVersion++;
				%>

				<bean:message key="form.pproc.label.version"/>:
				<div id="procTitleText">
				&nbsp;&nbsp;&nbsp;<%=iVersion%>
				</div>
			</td>
			<td align="right" width="20%" id="procTitle">
				<bean:message key="form.pproc.label.status"/>:&nbsp;&nbsp;&nbsp;
				<logic:equal name="defaultForm" property="property(SPAC_P_PCD:ESTADO)" value="1">
				<div id="procBorradorTitle">
					<bean:message key="procedure.versions.draft"/>
				</div>
				</logic:equal>
				<logic:equal name="defaultForm" property="property(SPAC_P_PCD:ESTADO)" value="2">
				<div id="procVigenteTitle">
					<bean:message key="procedure.versions.current"/>
				</div>
				</logic:equal>
				<logic:equal name="defaultForm" property="property(SPAC_P_PCD:ESTADO)" value="3">
				<div id="procHistoricoTitle">
					<bean:message key="procedure.versions.archived"/>
				</div>
				</logic:equal>

			</td>
		</tr>
	</table>
</div>

<!-- BREAD CRUMBS -->
	<tiles:insert page="/ispac/common/tiles/breadCrumbsTile.jsp" ignore="true" flush="false"/>
<!-- ------------ -->

<div id="navmenu" >
	<ul class="actionsMenuList">
		<li>
			<bean:define id="regid" name="defaultForm" property="property(SPAC_P_FASES:ID_PCD)"/>
   		<%String actionURL = "showPProcedure.do?entityId=22&regId=" + regid;%>
   		<html:link action='<%=actionURL%>'>
				<bean:message key="forms.button.back"/>
  		</html:link>
   	</li>
	  <li>
			<a href="javascript:ispac_submit('<%= request.getContextPath() + "/storeCTEntity.do"%>')">
				<nobr><bean:message key="forms.button.save"/></nobr>
			</a>
	  </li>
		<li>
			<bean:define name="KeyId" id="idobj"/>
			<%actionURL = "showPEvents.do?TpObj=2&IdObj=" + idobj;%>
			<ispac:linkframe id="EVENTMANAGER"
					 styleClass=""
				     target="workframe"
					 action='<%=actionURL%>'
					 titleKey="catalog.events"
					 width="640"
					 height="480"
					 showFrame="true">
			</ispac:linkframe>
       		</li>
       		<li>
	       		<bean:define name="KeyId" id="stageid"/>
			<%actionURL = "showPFrmStages.do?StageId=" + stageid;%>
			<ispac:linkframe id="APPMANAGER"
					 styleClass=""
				     target="workframe"
					 action='<%=actionURL%>'
					 titleKey="catalog.frmapp"
					 width="640"
					 height="480"
					 showFrame="true">
			</ispac:linkframe>
       		</li>
	       	<%--
	       	<li>
	       		<bean:define id="ctstageid" name="defaultForm" property="property(SPAC_P_FASES:ID_CTFASE)"/>
	       		<%actionURL = "showCTStage.do?entityId=5&regId=" + ctstageid;%>
	       		<html:link action='<%=actionURL%>'>
				Ver ficha catalogr&aacute;fica
  			</html:link>
	       	</li>
	       	--%>
				<li>
					<%actionURL = "showStageTPDocsList.do?StageId=" + stageid;%>
					<ispac:linkframe id="TPDOCSMANAGER"
							 styleClass=""
						     target="workframe"
							 action='<%=actionURL%>'
							 titleKey="form.stage.button.documents"
							 width="640"
							 height="480"
							 showFrame="true">
					</ispac:linkframe>
				</li>
	</ul>
</div>
<html:form action='/showPStage.do'>

	<%--
		Nombre de Aplicación.
		 Necesario para realizar la validación

	<html:hidden property="entityAppName"/> --%>

	<!-- Identificador de la entidad -->
	<html:hidden property="entity"/>
	<!-- Identificador del registro -->
	<html:hidden property="key"/>
	<!-- Registro de solo lectura-->
	<html:hidden property="readonly"/>
	<!-- Registro de distribución -->
	<html:hidden property="property(ID)"/>

	<table cellpadding="1" cellspacing="0" border="0" width="100%">
		<tr>
			<td>
				<table class="box" width="100%" border="0" cellspacing="1" cellpadding="0">
					<!-- FORMULARIO -->
					<tr>
						<td class="blank">
							<table width="100%" border="0" cellspacing="2" cellpadding="2">
								<tr>
									<td height="5px" colspan="3"><html:errors/></td>
								</tr>
								<tr>
									<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
									<td width="100%">
										<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td class="select" id="tdlink1" align="center" onclick="showTab(1)">
													<bean:message key="form.pstage.tab.properties"/>
												</td>
												<td width="5px" class="tabSeparator"><img height="1" width="5px" src="img/pixel.gif"/></td>
												<td class="unselect" id="tdlink2" align="center" onclick="showTab(2)">
													<bean:message key="form.pstage.tab.setDeadline"/>
												</td>
											</tr>
										</table>
										<div style="display:block" id="block1" class="tabFormtable">
											<!-- TABLA DE CAMPOS -->
														<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
															<tr>
																<td>
																	<table border="0" cellspacing="0" cellpadding="0" width="100%">
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="16px"/></td>
																		</tr>

																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pstage.propertyLabel.procedure" tooltipKey="form.pstage.propertyLabel.procedure.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(SPAC_P_PCD:NOMBRE)" styleClass="inputReadOnly" size="40" readonly="true" maxlength="250"/>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.pstage.propertyLabel.name" tooltipKey="form.pstage.propertyLabel.name.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(SPAC_P_FASES:NOMBRE)" styleClass="inputReadOnly" size="40" readonly="true" maxlength="250"/>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.pstage.propertyLabel.responsable" tooltipKey="form.pstage.propertyLabel.responsable.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text name="entapp" property="property(PRESP_NAME:NOMBRE)" styleClass="inputReadOnly" size="40" readonly="true"/>
																				<%String URL = "viewUsersManager.do?mode=Simple&captionkey=usrmgr.responsable.caption";%>
																				&nbsp;&nbsp;<ispac:linkframe id="USRMANAGER"
																											 styleClass="form_button_white"
																										     target="workframe"
																											 action='<%=URL%>'
																											 titleKey="catalog.mod"
																											 width="640"
																											 height="480"
																											 showFrame="true">
																								<ispac:parameter name="USRMANAGER"
																						 						 id="property(SPAC_P_FASES:ID_RESP)"
																						 						 property="UID"/>
																						 		<ispac:parameter name="USRMANAGER"
																						 						 id="property(PRESP_NAME:NOMBRE)" property="RESPNAME"/>
																							</ispac:linkframe>
																			</td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pstage.propertyLabel.idResponsable" tooltipKey="form.pstage.propertyLabel.idResponsable.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(SPAC_P_FASES:ID_RESP)" size="40" styleClass="inputReadOnly" readonly="true" maxlength="250"/>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><hr/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pstage.propertyLabel.procedureId" tooltipKey="form.pstage.propertyLabel.procedureId.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(SPAC_P_PCD:ID)" styleClass="inputReadOnly" size="5" readonly="true" maxlength="10"/>
																			</td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pstage.propertyLabel.stageId" tooltipKey="form.pstage.propertyLabel.stageId.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(SPAC_P_FASES:ID)" styleClass="inputReadOnly" size="5" readonly="true" maxlength="10"/>
																			</td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="1%">
																				<ispac:tooltipLabel labelKey="form.pstage.propertyLabel.catalogStageId" tooltipKey="form.pstage.propertyLabel.catalogStageId.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(SPAC_P_FASES:ID_CTFASE)" styleClass="inputReadOnly" size="5" readonly="true" maxlength="10"/>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><hr/></td>
																		</tr>
																	</table>
																</td>
															</tr>
															<tr>
																<td>
																	<h4><bean:message key="form.pstage.stageTasks.title"/></h4>

															   		<%URL = "selectObject.do?codetable=SPAC_CT_TRAMITES&codefield=ID&valuefield=NOMBRE&decorator=/formatters/pcdstage/choosepcdstagecttaskformatter.xml&pstageId=" + stageid;%>
																	<ispac:linkframe id="SPAC_CT_TRAMITES"
																	                 target="workframe"
																		               action='<%=URL%>'
																		               styleClass="form_button_white"
																		               titleKey="catalog.choose.cttask"
																		               width="640"
																		               height="480"
																		               showFrame="true"
																		               captionKey="catalog.choose.cttask">
																	</ispac:linkframe>


																	<bean:define id="ItemsList" toScope="request" name="entapp" property="listTasksStage" />
																	<bean:define id="ItemsListFormatter" toScope="request" name="defaultForm" property="formatter" />
																	<tiles:insert page="/ispac/common/tiles/displaytag.jsp" ignore="true" flush="false">
																		<tiles:put name="ItemListAttr" value="ItemsList"/>
																		<tiles:put name="ItemFormatterAttr" value="ItemsListFormatter"/>
																		<tiles:put name="ItemActionAttr" value="/showPProcedure.do"/>
																	</tiles:insert>
																</td>
															</tr>
														</table>
													</div>
													<!-- PESTAÑA DEFINICION DE PLAZOS -->
													<tiles:insert page="/ispac/common/tiles/deadLineTile.jsp" ignore="true" flush="false"/>
												</td>
											</tr>
											<tr>
												<td height="15px"><img src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
											</tr>
										</table>
									</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</html:form>


<script>
	ispac_formobserver_install(this);
	ispac_load_formvalues();
</script>