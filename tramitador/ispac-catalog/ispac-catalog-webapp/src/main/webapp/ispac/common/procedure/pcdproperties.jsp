<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<bean:define id="entapp" name="defaultForm" property="entityApp" />
<bean:define id="entityId" name="defaultForm" property="entity" />



<div class="tabButtons">
	<ispac:hasFunction functions="FUNC_INV_PROCEDURES_EDIT">
	<ul class="tabButtonList">
		<li>
			<a href="javascript:submit('<c:url value="/storeProcedure.do"/>')">
				<nobr><bean:message key="forms.button.save"/></nobr>
			</a>
		</li>
	</ul>
	</ispac:hasFunction>
</div>

<div class="tabContent">

<html:form action='/showProcedureEntity.do'>

	<!-- Identificador de la entidad -->
	<html:hidden property="entity"/>
	<!-- Identificador del registro -->
	<html:hidden property="key"/>
	<!-- Registro de solo lectura-->
	<html:hidden property="readonly"/>
	<!-- Registro de distribución -->
	<html:hidden property="property(SPAC_P_PROCEDIMIENTOS:ID)"/>

	<html:hidden property="property(SPAC_P_PROCEDIMIENTOS:NVERSION)"/>
	<html:hidden property="property(SPAC_P_PROCEDIMIENTOS:ID_PCD_BPM)"/>

	<!-- Información sobre los plazos -->
	<html:hidden property="property(SPAC_P_PLAZO:ID)"/>
	<html:hidden name="entapp" property="property(SPAC_P_PLAZO:UNITS)"/>
	<html:hidden name="entapp" property="property(SPAC_P_PLAZO:MAGNITUDE)"/>
	<html:hidden name="entapp" property="property(SPAC_P_PLAZO:BASEDATE_TYPE)"/>
	<html:hidden name="entapp" property="property(SPAC_P_PLAZO:BASEDATE_ENTITY_ID)"/>
	<html:hidden name="entapp" property="property(SPAC_P_PLAZO:BASEDATE_ENTITY_FIELD)"/>
	<html:hidden name="entapp" property="property(SPAC_P_PLAZO:BASEDATE_RULE_ID)"/>
	
	<input type="hidden" name="method" value="properties"/>

	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="blank">

				<table width="100%" border="0" cellspacing="2" cellpadding="2">
					<tr>
						<td height="5px" colspan="3"><html:errors/></td>
					</tr>
					<tr>
						<td width="4"><img height="1" width="4px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
						<td width="99%">
							<div id="page1" class="formtable">
								<!-- TABLA DE CAMPOS -->
								<table border="0" cellspacing="0" cellpadding="0" align="center" width="95%">
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
														<bean:define id="_valueNombreResp" type="java.lang.String" name="entapp" property="property(SPAC_P_PROCEDIMIENTOS:RESP_NOMBRE)" toScope="request"/>
														&nbsp;<nobr>
														<ispac:htmlTextareaImageFrame property="property(SPAC_P_PROCEDIMIENTOS:RESP_NOMBRE)"
																				readonly="true"
																				readonlyTag="false"
																				propertyReadonly="readonly"
																				styleClass="input"
																				styleClassReadonly="inputReadOnly"
																				rows="2"
																			  	cols="70"
																				id="USRMANAGER"
																				target="workframe"
																				action='<%="viewUsersManager.do?mode=Simple&captionkey=usrmgr.responsable.caption"%>'
																				image="img/search-mg.gif" 
																				titleKeyLink="usrmgr.responsable" 
																				showFrame="true"
																				value='<%=_valueNombreResp%>'
																				>
															<ispac:parameter name="USRMANAGER" id="property(SPAC_P_PROCEDIMIENTOS:ID_RESP)" property="UID"/>
									 						<ispac:parameter name="USRMANAGER" id="property(SPAC_P_PROCEDIMIENTOS:RESP_NOMBRE)" property="RESPNAME"/>
														</ispac:htmlTextareaImageFrame>
														
													
														</nobr>
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
														<%--
														<div class='tooltip for_tooltipIdNombreUpd'>
														     <h1><bean:message key="form.pproc.tooltip.header.nombreUpd"/></h1>
														     <p><bean:write name="defaultForm" property="property(SPAC_P_PROCEDIMIENTOS:ID_UPD)"/></p>
														 </div>
														 --%>
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
										<td class="formsTitleB">
											<bean:message key="procedure.button.showVersions"/>
										</td>
									</tr>
									<tr>
										<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
									</tr>
									<tr>
										<td class="formsTitle">
											<display:table name="versionsList" 
														   id="item" 
														   export="false" 
														   class="tableDisplay"
											  			   sort="list" 
											  			   requestURI="/showProcedureEntity.do">
											
											  	<display:column titleKey="form.pproc.label.version" 
											  					headerClass="headerDisplay"
											  					sortable="false">
														<%
															String _version = ((ieci.tdw.ispac.ispaclib.bean.ItemBean)item).getString("NVERSION");
															String _pcdId = ((ieci.tdw.ispac.ispaclib.bean.ItemBean)item).getString("ID");
														%>
														<a href='<%="manageVistaCuadroProcedimiento.do?method=inithome&entityId=" + entityId + "&regId=" + _pcdId%>' 
															target="_parent"
															class="aActionButton">
															<bean:message key="form.pproc.label.version"/>&nbsp;
															<%=Integer.parseInt(_version)%>
												  		</a>
											  	</display:column>

												<display:column titleKey="form.pproc.label.status" 
																headerClass="headerDisplay" 
																sortable="false">
											
													<logic:equal name='item' property='property(ESTADO_TXT)' value="DRAFT" >
														<div id="procBorrador">
															<bean:message key="procedure.versions.draft"/>
														</div>
													</logic:equal>
													<logic:equal name='item' property='property(ESTADO_TXT)' value="CURRENT" >
														<div id="procVigente">
															<bean:message key="procedure.versions.current"/>
														</div>
													</logic:equal>
													<logic:equal name='item' property='property(ESTADO_TXT)' value="OLD" >
														<div id="procHistorico">
															<bean:message key="procedure.versions.archived"/>
														</div>
													</logic:equal>
											
												</display:column>

											</display:table>
										</td>
									</tr>
									<tr>
										<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="15px"/></td>
									</tr>
								</table>
							</div>
						</td>
						<td width="4"><img height="1" width="4px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
					</tr>
					<tr>
						<td height="5px" colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
					</tr>
				</table>
			</td>
		</tr>
</html:form>
	</table>
</div>