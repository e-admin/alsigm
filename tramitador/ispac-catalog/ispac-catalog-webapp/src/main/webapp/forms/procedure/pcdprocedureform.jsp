<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<bean:define id="entapp" name="defaultForm" property="entityApp" />
<bean:define id="pcdId" name="KeyId" />

<script type="text/javascript" src='<ispac:rewrite href="../scripts/ispac-forms.js"/>'>
</script>


<script>
	function save()
	{
		document.defaultForm.target = "ParentWindow";
		document.defaultForm.action = "storeEntity.do"
		document.defaultForm.submit();
	}

	var block = 1;

	function showTab(i)
	{
		document.getElementById('block'+ block).style.display='none';
		document.getElementById('tdlink'+ block).className="unselect";
		document.getElementById('block'+ i).style.display='block';
		document.getElementById('tdlink'+ i).className = 'select';
		block = i;
<%--
		if (block==3)
		{
			<%String url = "showPcdGraph.do?pcdId=" + pcdId;%>
			frames["svgpcd"].location.href='<%=url%>';
		}
--%>
	}
</script>

<div id="navmenutitle">
	<bean:message key="procedure.properties.mainTitle"/>
</div>
<!-- ENCABEZADO PROCEDIMIENTO -->
<div id="procTitle">
	<table width="95%" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td align="left" width="60%" id="procTitle">
			<bean:message key="form.pstage.propertyLabel.procedure"/>:&nbsp;&nbsp;
			<html:link action="showPProcedure.do?entityId=22" paramId="regId" paramName="KeyId">
				<bean:write name="defaultForm" property="property(SPAC_P_PROCEDIMIENTOS:NOMBRE)" />
  			</html:link>
			</td>
			<td align="right" width="20%" id="procTitle">
				<bean:define id="sVersion" name="defaultForm" property="property(SPAC_P_PROCEDIMIENTOS:NVERSION)" type="java.lang.String"/>
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
				<logic:equal name="defaultForm" property="property(SPAC_P_PROCEDIMIENTOS:ESTADO)" value="1">
				<div id="procBorradorTitle">
					<bean:message key="procedure.versions.draft"/>
				</div>
				</logic:equal>
				<logic:equal name="defaultForm" property="property(SPAC_P_PROCEDIMIENTOS:ESTADO)" value="2">
				<div id="procVigenteTitle">
					<bean:message key="procedure.versions.current"/>
				</div>
				</logic:equal>
				<logic:equal name="defaultForm" property="property(SPAC_P_PROCEDIMIENTOS:ESTADO)" value="3">
				<div id="procHistoricoTitle">
					<bean:message key="procedure.versions.archived"/>
				</div>
				</logic:equal>

			</td>
		</tr>
	</table>
</div>

<!-- BREAD CRUMBS -->
	<!--tiles:insert page="/ispac/common/tiles/breadCrumbsTile.jsp" ignore="true" flush="false"/-->
<!-- ------------ -->

<div id="navmenutitle">
	<ul  class="actionsMenuList">
		<li>
			<a href="javascript:ispac_submit('<%= request.getContextPath() + "/storeCTEntity.do"%>')">
				<nobr><bean:message key="forms.button.save"/></nobr>
			</a>
		</li>
		<li>
			<bean:define name="KeyId" id="idobj"/>
			<%String URLaction = "showPEvents.do?TpObj=1&IdObj=" + idobj;%>
			<ispac:linkframe id="EVENTMANAGER"
					 styleClass=""
				     target="workframe"
					 action='<%=URLaction%>'
					 titleKey="catalog.events"
					 width="640"
					 height="480"
					 showFrame="true">
			</ispac:linkframe>
       	</li>
       	<li>
       		<%URLaction = "selectEntities.do?entityId=22&pcdId=" + idobj;%>
       		<ispac:linkframe id="ENTMANAGER"
					 styleClass=""
				     target="workframe"
					 action='<%=URLaction%>'
					 titleKey="catalog.ent"
					 width="640"
					 height="480"
					 showFrame="true">
			</ispac:linkframe>
       	</li>



		<li>
			 <html:link action="showPVersions.do" paramId="groupId" paramName="defaultForm" paramProperty="property(SPAC_P_PROCEDIMIENTOS:ID_GROUP)" >
				<bean:message key="procedure.button.showVersions"/>
  			</html:link>
       	</li>
       	<li>
  			<%URLaction = "newProcedure.do?action=begin&pcdtype=clone&pcdId=" + idobj;%>
			<ispac:linkframe id="CLONEPCD"
					 styleClass=""
				     target="workframe"
					 action='<%=URLaction%>'
					 titleKey="procedure.button.copy"
					 width="640"
					 height="480"
					 showFrame="true">
			</ispac:linkframe>
       	</li>
       	<li>
       		<html:link action="showPermissions.do" paramId="regId" paramName="KeyId">
				<bean:message key="menu.users.permisos"/>
  			</html:link>
       	</li>
       	<li>
       		<html:link action="showCTProcedure.do?entityId=8" paramId="regId" paramName="KeyId">
				<bean:message key="form.pproc.button.fichaCat"/>
  			</html:link>
       	</li>
	</ul>
</div>
<html:form action='/showPProcedure.do'>

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
	<html:hidden property="property(SPAC_P_PROCEDIMIENTOS:ID)"/>

	<html:hidden property="property(SPAC_P_PROCEDIMIENTOS:NVERSION)"/>

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
													<bean:message key="procedure.properties.subTitle"/>
												</td>
												<td width="5px" class="tabSeparator"><img height="1" width="5px" src="img/pixel.gif"/></td>
												<td class="unselect" id="tdlink2" align="center" onclick="showTab(2)">
													<bean:message key="procedure.tap.definirPlazos"/>
												</td>
<%--
												<td width="5px" class="tabSeparator"><img height="1" width="5px" src="img/pixel.gif"/></td>
												<td class="unselect" id="tdlink3" align="center" onclick="showTab(3)">
													Diagrama del procedimiento
												</td>
--%>
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
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.pproc.propertyLabel.name" tooltipKey="form.pproc.propertyLabel.name.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(SPAC_P_PROCEDIMIENTOS:NOMBRE)" styleClass="inputReadOnly" size="40" readonly="true" maxlength="250"/>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.pproc.propertyLabel.responsable" tooltipKey="form.pproc.propertyLabel.responsable.tooltip"/>
																			</td>
																			<td height="20">
																				<html:hidden property="property(SPAC_P_PROCEDIMIENTOS:ID_RESP)"/>
																				&nbsp;&nbsp;<html:text name="entapp" property="property(SPAC_P_PROCEDIMIENTOS:RESP_NOMBRE)" styleClass="inputReadOnly" size="40" readonly="true" styleId="tooltipIdNombreResp"/>
																				<div class='tooltip for_tooltipIdNombreResp'>
																				     <h1><bean:message key="form.pproc.tooltip.header.nombreCrt"/></h1>
																				     <p><bean:write name="defaultForm" property="property(SPAC_P_PROCEDIMIENTOS:ID_RESP)"/></p>
																				 </div>
																				<%String URL = "viewUsersManager.do?captionkey=usrmgr.responsable.caption";%>
																				&nbsp;&nbsp;<ispac:linkframe id="USRMANAGER"
																											 styleClass="form_button_white"
																										     target="workframe"
																											 action='<%=URL%>'
																											 titleKey="catalog.mod"
																											 width="640"
																											 height="480"
																											 showFrame="true">
																								<ispac:parameter name="USRMANAGER"
																						 						 id="property(SPAC_P_PROCEDIMIENTOS:ID_RESP)"
																						 						 property="UID"/>
																						 		<ispac:parameter name="USRMANAGER"
																						 						 id="property(SPAC_P_PROCEDIMIENTOS:RESP_NOMBRE)" property="RESPNAME"/>
																							</ispac:linkframe>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="30%">
																				<ispac:tooltipLabel labelKey="form.pproc.propertyLabel.procId" tooltipKey="form.pproc.propertyLabel.procId.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(SPAC_P_PROCEDIMIENTOS:ID)" styleClass="inputReadOnly" size="5" readonly="true" maxlength="10"/>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="30%">
																				<ispac:tooltipLabel labelKey="form.pproc.propertyLabel.nombreCrt" tooltipKey="form.pproc.propertyLabel.nombreCrt.tooltip"/>
																			</td>
																			<td height="20">
																				<html:hidden property="property(SPAC_P_PROCEDIMIENTOS:ID_CRT)"/>
																				&nbsp;&nbsp;<html:text name="entapp" property="property(SPAC_P_PROCEDIMIENTOS:NOMBRE_CRT)" styleClass="inputReadOnly" size="40" readonly="true" styleId="tooltipIdNombreCRT"/>
																			</td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="30%">
																				<ispac:tooltipLabel labelKey="form.pproc.propertyLabel.creationDate" tooltipKey="form.pproc.propertyLabel.creationDate.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(SPAC_P_PROCEDIMIENTOS:TS_CRT)" styleClass="inputReadOnly" size="12" readonly="true" maxlength="10"/>
																			</td>
																		</tr>
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="30%">
																				<ispac:tooltipLabel labelKey="form.pproc.propertyLabel.nombreUpd" tooltipKey="form.pproc.propertyLabel.nombreUpd.tooltip"/>
																			</td>
																			<td height="20">
																				<html:hidden property="property(SPAC_P_PROCEDIMIENTOS:ID_UPD)"/>
																				&nbsp;&nbsp;<html:text name="entapp" property="property(SPAC_P_PROCEDIMIENTOS:NOMBRE_UPD)" styleClass="inputReadOnly" size="40" readonly="true" styleId="tooltipIdNombreUpd"/>
																				<div class='tooltip for_tooltipIdNombreUpd'>
																				     <h1><bean:message key="form.pproc.tooltip.header.nombreUpd"/></h1>
																				     <p><bean:write name="defaultForm" property="property(SPAC_P_PROCEDIMIENTOS:ID_UPD)"/></p>
																				 </div>
																			</td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle" width="30%">
																				<ispac:tooltipLabel labelKey="form.pproc.propertyLabel.dataLastUpd" tooltipKey="form.pproc.propertyLabel.dataLastUpd.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(SPAC_P_PROCEDIMIENTOS:TS_UPD)" size="12" styleClass="inputReadOnly" readonly="true" maxlength="10"/>
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
																	<h4><bean:message key="form.pproc.stages.list"/></h4>
																	<bean:define id="ItemsList" toScope="request" name="entapp" property="property(listStagesPcd)" />
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


													<!-- PESTAÑA GRAFO PROCEDIMIENTO -->
<%-- 													<div style="display:none" id="block3" class="tabFormtable">
														<%URLaction = "showPcdGraph.do?pcdId=" + idobj;%>
														<iframe name="svgpcd" frameborder="0" height="480" width="100%"  />

													</div>
--%>													
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