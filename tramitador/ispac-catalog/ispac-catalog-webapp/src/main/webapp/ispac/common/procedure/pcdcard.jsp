<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>



<script type="text/javascript" src='<ispac:rewrite href="../../dwr/interface/CatalogAPI.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../../dwr/engine.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../../dwr/util.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>

	
<bean:define id="pcd" name="PCD_ITEM"/>
<bean:define id="pcdId" name="pcd" property="property(ID)"/>
<bean:define id="groupId" name="pcd" property="property(ID_GROUP)"/>


<c:set var="selectedBlock"><c:out value="${param.block}">1</c:out></c:set>
<script>
//<!--

	function showTab(i) {
		var block = document.defaultForm.block;
		document.getElementById('block'+ block.value).style.display='none';
		document.getElementById('tdlink'+ block.value).className="unselect";
		document.getElementById('block'+ i).style.display='block';
		document.getElementById('tdlink'+ i).className = 'select';
		block.value = i;
	}
	function removeProducers() {
		if (checkboxElement(document.defaultForm.multibox) == "") {
			jAlert('<bean:message key="procedure.card.removeProducers.empty"/>', '<bean:message key="common.alert"/>' , '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		} 
		else{
		jConfirm('<bean:message key="procedure.card.removeProducers.confirm"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>', function(r) {
		if(r){
			document.defaultForm.method.value = "remove";
			submit('<c:url value="/producers.do"/>');
		}
							
		});	}
	}
	function save() {
		<c:url var="reloadUrl" value="/showProcedureEntity.do">
			<c:param name="method" value="card"/>
			<c:param name="entityId" value="${param.entityId}"/>
			<c:param name="regId" value="${param.regId}"/>
		</c:url>
		window.location.href = '<c:out value="${reloadUrl}" escapeXml="false"/>&block=' + document.defaultForm.block.value;
	}
	function truncateDate(field){
		obj = document.getElementById(field);
		obj.value = obj.value.substring(0, 10);
	}
	
	function str_replace(de,a,str) {
	return str.split(de).join(a);
	}
	
	
	$(document).ready(function() {
		top.hideLayer("waiting");
		$("#deleteProcVigente").click(function() {
			CatalogAPI.countExpsByPcd('<c:out value="${pcdId}"/>', function(data) {
				if (data) {
					var msg='<bean:message key="procedure.version.action.numExpsDeleteProc"/>';
					msg=str_replace("###",data.value,msg);				
					jConfirm(msg, 
						'<bean:message key="common.alert"/>' , 
						'<bean:message key="common.message.ok"/>' , 
						'<bean:message key="common.message.cancel"/>', function(r) {
							if(r){ 	top.showLayer("waiting");document.defaultForm.method.value = "delete";submit('<c:url value="/actionsProcedure.do"/>');}
							//else no eliminamos el procedimiento 
							
					});	
					}
				}	
			);}
		);
		
		$("#cleanProcVigente").click(function() {
			CatalogAPI.countExpsByPcd('<c:out value="${pcdId}"/>', function(data) {
				if (data) {
					var msg='<bean:message key="procedure.version.action.numExpsCleanProc"/>';
					msg=str_replace("###",data.value,msg);				
					jConfirm(msg, 
						'<bean:message key="common.alert"/>' , 
						'<bean:message key="common.message.ok"/>' , 
						'<bean:message key="common.message.cancel"/>', function(r) {
							if(r){top.showLayer("waiting"); document.defaultForm.method.value = "clean";submit('<c:url value="/actionsProcedure.do"/>');}
							//else no eliminamos el procedimiento 
							
					});	
					}
				}	
			);}
		);

        // Establecer el gestor de errores
        dwr.engine.setErrorHandler(errorHandler);
        
	});
	
	function errorHandler(message, exception) {
        jAlert(message, '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
    }
	

//-->
</script>

<ispac:rewrite id="imgcalendar" href="img/calendar/"/>
<ispac:rewrite id="jscalendar" href="../scripts/calendar.js"/>
<ispac:rewrite id="buttoncalendar" href="img/calendar/calendarM.gif"/>

<ispac:calendar-config imgDir='<%= imgcalendar %>' scriptFile='<%= jscalendar %>'/>

<div class="tabButtons">
	<ispac:hasFunction functions="FUNC_INV_PROCEDURES_EDIT">
	<ul class="tabButtonList">
		<li>
			<a href="javascript:submit('<c:url value="/storeProcedure.do"/>')">
				<nobr><bean:message key="forms.button.save"/></nobr>
			</a>
		</li>
		<logic:equal name="pcd" property="property(ESTADO)" value="1">
		<li>
			<c:url var="promoteUrl" value="promoteDraft.do">
				<c:param name="entityId" value="${param.entityId}"/>
				<c:param name="regId" value="${param.regId}"/>
				<c:param name="groupId" value="${groupId}"/>
			</c:url>
			<a href="#" onclick="javascript:return messageConfirm('<c:out value="${promoteUrl}"/>','<bean:message key="procedure.card.confirm.promote"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' ,'<bean:message key="common.message.cancel"/>')">
				<nobr><bean:message key="procedure.versions.actions.promote"/></nobr>
			</a>
		</li>
		<li>
			<c:url var="deleteUrl" value="deleteDraft.do">
				<c:param name="entityId" value="${param.entityId}"/>
				<c:param name="regId" value="${param.regId}"/>
				<c:param name="groupId" value="${groupId}"/>
			</c:url>
			<a href="#" onclick="javascript:return messageConfirm('<c:out value="${deleteUrl}"/>','<bean:message key="procedure.card.confirm.deleteDraft"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' ,'<bean:message key="common.message.cancel"/>')">
				<nobr><bean:message key="procedure.versions.actions.deleteDraft"/></nobr>
			</a>
		</li>
			
		</logic:equal>
		<logic:notEqual name="pcd" property="property(ESTADO)" value="1">
		<li>
			<ispac:linkframe id="CLONEPCD"
				     target="workframe"
					 action='<%="newProcedure.do?action=begin&pcdtype=draft&pcdId=" + pcdId.toString()+"&groupId="+groupId.toString()%>'
					 titleKey="catalog.createdraft"
					 width="640"
					 height="480"
					 showFrame="true">
			</ispac:linkframe>
		</li>
		
		</logic:notEqual>	
		<logic:equal name="pcd" property="property(ESTADO)" value="2">
				<li>
					<a id="deleteProcVigente" href="#">
						<nobr><bean:message key="procedure.versions.actions.deleteProc"/></nobr>
					</a>
				</li>
				<li>
					<a id="cleanProcVigente" href="#">
						<nobr><bean:message key="procedure.versions.actions.cleanProc"/></nobr>
					</a>
				</li>
		</logic:equal>
	</ul>
	</ispac:hasFunction>
</div>

<tiles:insert page="../tiles/pcdBCTile.jsp" ignore="true" flush="false"/>

<div class="tabContent">

<html:form action='/showProcedureEntity.do'>

	<!-- Identificador de la entidad -->
	<html:hidden property="entity"/>
	<!-- Identificador del registro -->
	<html:hidden property="key"/>
	<!-- Registro de solo lectura-->
	<html:hidden property="readonly"/>
	<!-- Registro de distribución -->
	<html:hidden property="property(ID)"/>
	<html:hidden property="property(ID_GROUP)"/>
	<input type="hidden" name="method" value="card" />
	<input type="hidden" name="block" value='<c:out value="${selectedBlock}"/>' />
	<input type="hidden" name="retEntityId" value='<c:out value="${requestScope['RET_ENTITY_ID']}"/>' />
	<input type="hidden" name="retKeyId" value='<c:out value="${requestScope['RET_KEY_ID']}"/>' />
	<input type="hidden" name="groupId" value='<c:out value="${groupId}"/>' />


	<table width="100%" cellspacing="1" cellpadding="0">
		<tr>
			<td class="blank">
				<table width="100%" border="0" cellspacing="2" cellpadding="2">
					<tr>
						<td height="5px" colspan="3"><html:errors/></td>
					</tr>
					<tr>
						<td width="100%">
						<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td <c:choose><c:when test="${selectedBlock==1}">class="select"</c:when><c:otherwise>class="unselect"</c:otherwise></c:choose> id="tdlink1" align="center" onclick="showTab(1)">
										<bean:message key="forms.proc.div1"/>
									</td>
									<td width="5px" class="tabSeparator"><img height="1" width="5px" src="img/pixel.gif"/></td>
<%--
									<td <c:choose><c:when test="${selectedBlock==2}">class="select"</c:when><c:otherwise>class="unselect"</c:otherwise></c:choose> id="tdlink2" align="center" onclick="showTab(2)">
										<bean:message key="forms.proc.div2"/>
									</td>
									<td width="5px" class="tabSeparator"><img height="1" width="5px" src="img/pixel.gif"/></td>
									<td <c:choose><c:when test="${selectedBlock==3}">class="select"</c:when><c:otherwise>class="unselect"</c:otherwise></c:choose> id="tdlink3" align="center" onclick="showTab(3)">
										<bean:message key="forms.proc.div3"/>
									</td>
									<td width="5px" class="tabSeparator"><img height="1" width="5px" src="img/pixel.gif"/></td>
--%>									
									<td <c:choose><c:when test="${selectedBlock==4}">class="select"</c:when><c:otherwise>class="unselect"</c:otherwise></c:choose> id="tdlink4" align="center" onclick="showTab(4)">
										<bean:message key="forms.proc.div4"/>
									</td>
									<td width="5px" class="tabSeparator"><img height="1" width="5px" src="img/pixel.gif"/></td>
									<td <c:choose><c:when test="${selectedBlock==5}">class="select"</c:when><c:otherwise>class="unselect"</c:otherwise></c:choose> id="tdlink5" align="center" onclick="showTab(5)">
										<bean:message key="forms.proc.div5"/>
									</td>
									<td width="5px" class="tabSeparator"><img height="1" width="5px" src="img/pixel.gif"/></td>
									<td <c:choose><c:when test="${selectedBlock==6}">class="select"</c:when><c:otherwise>class="unselect"</c:otherwise></c:choose> id="tdlink6" align="center" onclick="showTab(6)">
										<bean:message key="forms.proc.div6"/>
									</td>
									<td width="5px" class="tabSeparator"><img height="1" width="5px" src="img/pixel.gif"/></td>
									<td <c:choose><c:when test="${selectedBlock==7}">class="select"</c:when><c:otherwise>class="unselect"</c:otherwise></c:choose> id="tdlink7" align="center" onclick="showTab(7)">
										<bean:message key="forms.proc.div7"/>
									</td>
<%--
								
									<td width="5px" class="tabSeparator"><img height="1" width="5px" src="img/pixel.gif"/></td>
									<td <c:choose><c:when test="${selectedBlock==8}">class="select"</c:when><c:otherwise>class="unselect"</c:otherwise></c:choose> id="tdlink8" align="center" onclick="showTab(8)">
										<bean:message key="forms.proc.div8"/>
									</td>
--%>									
									<td width="5px" class="tabSeparator"><img height="1" width="5px" src="img/pixel.gif"/></td>
									<td <c:choose><c:when test="${selectedBlock==9}">class="select"</c:when><c:otherwise>class="unselect"</c:otherwise></c:choose> id="tdlink9" align="center" onclick="showTab(9)">
										<bean:message key="forms.proc.div9"/>
									</td>
									<td width="5px" class="tabSeparator"><img height="1" width="5px" src="img/pixel.gif"/></td>
									<td <c:choose><c:when test="${selectedBlock==10}">class="select"</c:when><c:otherwise>class="unselect"</c:otherwise></c:choose> id="tdlink10" align="center" onclick="showTab(10)">
										<bean:message key="forms.proc.div10"/>
									</td>
									<td width="5px" class="tabSeparator"><img height="1" width="5px" src="img/pixel.gif"/></td>
									<td <c:choose><c:when test="${selectedBlock==11}">class="select"</c:when><c:otherwise>class="unselect"</c:otherwise></c:choose> id="tdlink11" align="center" onclick="showTab(11)">
										<bean:message key="forms.proc.div11"/>
									</td>
								</tr>
							</table>
							<!-- BLOQUE DE CAMPOS -->
							<div style="display:<c:choose><c:when test="${selectedBlock==1}">block</c:when><c:otherwise>none</c:otherwise></c:choose>" id="block1" class="tabFormtable">
								<!-- TABLA DE CAMPOS -->
								<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
									<tr>
										<td>
											<table border="0" cellspacing="0" cellpadding="0" width="100%">
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle" width="1%">
														<ispac:tooltipLabel labelKey="procedure.card.identif.codigo" tooltipKey="procedure.card.identif.codigo.tooltip"/>
													</td>
													<td height="20">
														<logic:present name="PCD_DRAFT">
															&nbsp;&nbsp;<html:text property="property(COD_PCD)" styleClass="inputSelectS" readonly="false" maxlength="100"/>&nbsp; <bean:message key="catalog.data.obligatory"/>
														</logic:present>
														<logic:notPresent name="PCD_DRAFT">
															&nbsp;&nbsp;<html:text property="property(COD_PCD)" styleClass="inputSelectS inputReadOnly" readonly="true"/>
														</logic:notPresent>

													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="procedure.card.identif.tituloCorto" tooltipKey="procedure.card.identif.tituloCorto.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:text property="property(TITULO)" styleClass="inputSelectS" readonly="false" maxlength="254"/>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr valign="top">
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="procedure.card.identif.objeto" tooltipKey="procedure.card.identif.objeto.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:textarea property="property(OBJETO)" styleClass="input" readonly="false" cols="72" rows="2"
															styleId="objeto" onkeypress="javascript:maxlength('objeto', 254)"/>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="procedure.card.identif.asunto" tooltipKey="procedure.card.identif.asunto.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:text property="property(ASUNTO)" styleClass="inputSelectS" readonly="false" maxlength="512"/>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="procedure.card.identif.actFunc" tooltipKey="procedure.card.identif.actFunc.tooltip"/>
													</td>
													<td height="20">
														<html:hidden property="property(ACT_FUNC)"/>
														&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(SPAC_VLDTBL_ACTS_FUNCS:SUSTITUTO)"
																				readonly="true"
																				readonlyTag="false"
																				propertyReadonly="readonly"
																				styleClass="input"
																				styleClassReadonly="inputReadOnly"
																				size="40"
																				id="SELECT_ACT_FUNC"
																				target="workframe"
																				action="selectObject.do?codetable=SPAC_VLDTBL_ACTS_FUNCS&codefield=ID&valuefield=SUSTITUTO&caption=select.actFunc.caption&sqlquery=where vigente = 1&decorator=/formatters/entities/selectSustitutoformatter.xml"
																				image="img/search-mg.gif" 
																				titleKeyLink="select.actFunc" 
																				showFrame="true">
																										  	  
															<ispac:parameter name="SELECT_ACT_FUNC" id="property(ACT_FUNC)" property="VALOR"/>
				                                        	<ispac:parameter name="SELECT_ACT_FUNC" id="property(SPAC_VLDTBL_ACTS_FUNCS:SUSTITUTO)" property="SUSTITUTO"/>
																						
														</ispac:htmlTextImageFrame></nobr>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="procedure.card.identif.matComp" tooltipKey="procedure.card.identif.matComp.tooltip"/>
													</td>
													<td height="20">
														<html:hidden property="property(MTRS_COMP)"/>
														&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(SPAC_VLDTBL_MATS_COMP:SUSTITUTO)"
																				readonly="true"
																				readonlyTag="false"
																				propertyReadonly="readonly"
																				styleClass="input"
																				styleClassReadonly="inputReadOnly"
																				size="40"
																				id="SELECT_MTRS_COMP"
																				target="workframe"
																				action="selectObject.do?codetable=SPAC_VLDTBL_MATS_COMP&codefield=ID&valuefield=SUSTITUTO&caption=select.matComp.caption&sqlquery=where vigente = 1&decorator=/formatters/entities/selectSustitutoformatter.xml"
																				image="img/search-mg.gif" 
																				titleKeyLink="select.matComp" 
																				showFrame="true">
																										  	  
															<ispac:parameter name="SELECT_MTRS_COMP" id="property(MTRS_COMP)" property="VALOR"/>
				                                        	<ispac:parameter name="SELECT_MTRS_COMP" id="property(SPAC_VLDTBL_MATS_COMP:SUSTITUTO)" property="SUSTITUTO"/>
																						
														</ispac:htmlTextImageFrame></nobr>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle" width="1%">
														<ispac:tooltipLabel labelKey="procedure.card.identif.sitTele" tooltipKey="procedure.card.identif.sitTele.tooltip"/>
													</td>
													<td height="20">
														<html:hidden property="property(SIT_TLM)"/>
														&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(SPAC_VLDTBL_SIT_TLM:SUSTITUTO)"
																				readonly="true"
																				readonlyTag="false"
																				propertyReadonly="readonly"
																				styleClass="input"
																				styleClassReadonly="inputReadOnly"
																				size="40"
																				id="SELECT_SIT_TLM"
																				target="workframe"
																				action="selectObject.do?codetable=SPAC_VLDTBL_SIT_TLM&codefield=ID&valuefield=SUSTITUTO&caption=select.sitTele.caption&sqlquery=where vigente = 1&decorator=/formatters/entities/selectSustitutoformatter.xml"
																				image="img/search-mg.gif" 
																				titleKeyLink="select.sitTele" 
																				showFrame="true">
																										  	  
															<ispac:parameter name="SELECT_SIT_TLM" id="property(SIT_TLM)" property="VALOR"/>
				                                        	<ispac:parameter name="SELECT_SIT_TLM" id="property(SPAC_VLDTBL_SIT_TLM:SUSTITUTO)" property="SUSTITUTO"/>
																						
														</ispac:htmlTextImageFrame></nobr>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="procedure.card.identif.urlProc" tooltipKey="procedure.card.identif.urlProc.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:text property="property(URL)" styleClass="inputSelectS" readonly="false" maxlength="254"/>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</div>
							<!-- BLOQUE DE CAMPOS SAC-->
<%--							
							<div style="display:<c:choose><c:when test="${selectedBlock==2}">block</c:when><c:otherwise>none</c:otherwise></c:choose>" id="block2" class="tabFormtable">
							</div>
--%>							
							<!-- BLOQUE DE CAMPOS ARCHIVO-->
<%--
							<div style="display:<c:choose><c:when test="${selectedBlock==3}">block</c:when><c:otherwise>none</c:otherwise></c:choose>" id="block3" class="tabFormtable">
							</div>
--%>							
							<div style="display:<c:choose><c:when test="${selectedBlock==4}">block</c:when><c:otherwise>none</c:otherwise></c:choose>" id="block4" class="tabFormtable">
								<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
									<tr>
										<td>
											<table border="0" cellspacing="0" cellpadding="0" width="100%">
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle" width="1%">
														<ispac:tooltipLabel labelKey="procedure.card.sujetos.interesados" tooltipKey="procedure.card.sujetos.interesados.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:text property="property(INTERESADO)" styleClass="inputSelectS" readonly="false" maxlength="64"/>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="procedure.card.sujetos.tipoRel" tooltipKey="procedure.card.sujetos.tipoRel.tooltip"/>
													</td>
													<td height="20">
														<html:hidden property="property(TP_REL)"/>
														&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(SPAC_TBL_002:SUSTITUTO)"
																				readonly="true"
																				readonlyTag="false"
																				propertyReadonly="readonly"
																				styleClass="input"
																				styleClassReadonly="inputReadOnly"
																				size="40"
																				id="SELECT_TP_REL"
																				target="workframe"
																				action="selectObject.do?codetable=SPAC_TBL_002&codefield=ID&valuefield=SUSTITUTO&caption=select.tipoRel.caption&sqlquery=where vigente = 1&decorator=/formatters/entities/selectSustitutoformatter.xml"
																				image="img/search-mg.gif" 
																				titleKeyLink="select.tipoRel" 
																				showFrame="true">
																										  	  
															<ispac:parameter name="SELECT_TP_REL" id="property(TP_REL)" property="VALOR"/>
				                                        	<ispac:parameter name="SELECT_TP_REL" id="property(SPAC_TBL_002:SUSTITUTO)" property="SUSTITUTO"/>
																						
														</ispac:htmlTextImageFrame></nobr>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="procedure.card.sujetos.orgRes" tooltipKey="procedure.card.sujetos.orgRes.tooltip"/>
													</td>
													<td height="20">
													
														<html:hidden property="property(ORG_RSLTR)"/>
														&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(ORG_RSLTR:NAME)"
																				readonly="true"
																				readonlyTag="false"
																				propertyReadonly="readonly"
																				styleClass="input"
																				styleClassReadonly="inputReadOnly"
																				size="50"
																				id="selectOrgRsltr"
																				target="workframe"
																				action="viewUsersManager.do?captionkey=select.orgRes.caption&noviewusers=true&noviewgroups=true"											
																				image="img/search-mg.gif" 
																				titleKeyLink="select.orgRes" 
																				showFrame="true">
																										  	  
															<ispac:parameter name="selectOrgRsltr" id="property(ORG_RSLTR)" property="UID"/>
				                                        	<ispac:parameter name="selectOrgRsltr" id="property(ORG_RSLTR:NAME)" property="NAME"/>
				                                        	
																						
														</ispac:htmlTextImageFrame></nobr>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle" width="1%">
														<ispac:tooltipLabel labelKey="procedure.card.sujetos.formaIni" tooltipKey="procedure.card.sujetos.formaIni.tooltip"/>
													</td>
													<td height="20">
														<html:hidden property="property(FORMA_INIC)"/>
														&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(SPAC_VLDTBL_FORMA_INIC:SUSTITUTO)"
																				readonly="true"
																				readonlyTag="false"
																				propertyReadonly="readonly"
																				styleClass="input"
																				styleClassReadonly="inputReadOnly"
																				size="40"
																				id="SELECT_FORMA_INIC"
																				target="workframe"
																				action="selectObject.do?codetable=SPAC_VLDTBL_FORMA_INIC&codefield=ID&valuefield=SUSTITUTO&caption=select.formaIni.caption&sqlquery=where vigente = 1&decorator=/formatters/entities/selectSustitutoformatter.xml"
																				image="img/search-mg.gif" 
																				titleKeyLink="select.formaIni" 
																				showFrame="true">
																										  	  
															<ispac:parameter name="SELECT_FORMA_INIC" id="property(FORMA_INIC)" property="VALOR"/>
				                                        	<ispac:parameter name="SELECT_FORMA_INIC" id="property(SPAC_VLDTBL_FORMA_INIC:SUSTITUTO)" property="SUSTITUTO"/>
																						
														</ispac:htmlTextImageFrame></nobr>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</div>
							<div style="display:<c:choose><c:when test="${selectedBlock==5}">block</c:when><c:otherwise>none</c:otherwise></c:choose>" id="block5" class="tabFormtable">
								<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
									<tr>
										<td>
											<table border="0" cellspacing="0" cellpadding="0" width="100%">
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle" width="1%">
														<ispac:tooltipLabel labelKey="procedure.card.tramit.plazo" tooltipKey="procedure.card.tramit.plazo.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:text property="property(PLZ_RESOL)" styleClass="input" size="5" readonly="false" maxlength="10"/>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="procedure.card.tramit.udPlazo" tooltipKey="procedure.card.tramit.udPlazo.tooltip"/>
													</td>
													<td height="20">
														<html:hidden property="property(UNID_PLZ)"/>
														&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(SPAC_TBL_001:SUSTITUTO)"
																				readonly="true"
																				readonlyTag="false"
																				propertyReadonly="readonly"
																				styleClass="input"
																				styleClassReadonly="inputReadOnly"
																				size="40"
																				id="SELECT_UNID_PLZ"
																				target="workframe"
																				action="selectObject.do?codetable=SPAC_TBL_001&codefield=ID&valuefield=SUSTITUTO&caption=select.udPlazo.caption&sqlquery=where vigente = 1&decorator=/formatters/entities/selectSustitutoformatter.xml"
																				image="img/search-mg.gif" 
																				titleKeyLink="select.udPlazo" 
																				showFrame="true">
																										  	  
															<ispac:parameter name="SELECT_UNID_PLZ" id="property(UNID_PLZ)" property="VALOR"/>
				                                        	<ispac:parameter name="SELECT_UNID_PLZ" id="property(SPAC_TBL_001:SUSTITUTO)" property="SUSTITUTO"/>

														</ispac:htmlTextImageFrame></nobr>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="procedure.card.tramit.fechaIni" tooltipKey="procedure.card.tramit.fechaIni.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<ispac:htmlTextCalendar styleId="FINICIO" property="property(FINICIO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image='<%= buttoncalendar %>' format="dd/mm/yyyy" enablePast="true" />
														<!-- &nbsp;&nbsp;<html:text property="property(FINICIO)" styleClass="inputSelectS" readonly="false" maxlength="23"/>-->
														<script type="text/javascript">
															truncateDate('FINICIO');
														</script>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="procedure.card.tramit.fechaFin" tooltipKey="procedure.card.tramit.fechaFin.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<ispac:htmlTextCalendar styleId="FFIN" property="property(FFIN)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image='<%= buttoncalendar %>' format="dd/mm/yyyy" enablePast="true" />
														<script type="text/javascript">
															truncateDate('FFIN');
														</script>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="procedure.card.tramit.efSilencio" tooltipKey="procedure.card.tramit.efSilencio.tooltip"/>
													</td>
													<td height="20">
														<html:hidden property="property(EFEC_SILEN)"/>
														&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(SPAC_VLDTBL_EFEC_SLNCIO:SUSTITUTO)"
																				readonly="true"
																				readonlyTag="false"
																				propertyReadonly="readonly"
																				styleClass="input"
																				styleClassReadonly="inputReadOnly"
																				size="40"
																				id="SELECT_EFEC_SILEN"
																				target="workframe"
																				action="selectObject.do?codetable=SPAC_VLDTBL_EFEC_SLNCIO&codefield=ID&valuefield=SUSTITUTO&caption=select.efSilencio.caption&sqlquery=where vigente = 1&decorator=/formatters/entities/selectSustitutoformatter.xml"
																				image="img/search-mg.gif" 
																				titleKeyLink="select.efSilencio" 
																				showFrame="true">
																										  	  
															<ispac:parameter name="SELECT_EFEC_SILEN" id="property(EFEC_SILEN)" property="VALOR"/>
				                                        	<ispac:parameter name="SELECT_EFEC_SILEN" id="property(SPAC_VLDTBL_EFEC_SLNCIO:SUSTITUTO)" property="SUSTITUTO"/>

														</ispac:htmlTextImageFrame></nobr>

													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="procedure.card.tramit.finViaAdm" tooltipKey="procedure.card.tramit.finViaAdm.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(FIN_VIA_ADMIN)"
																				readonly="true"
																				readonlyTag="false"
																				propertyReadonly="readonly"
																				styleClass="input"
																				styleClassReadonly="inputReadOnly"
																				size="10"
																				id="SELECT_FIN_VIA_ADMIN"
																				target="workframe"
																				action="selectObject.do?codetable=SPAC_TBL_009&codefield=ID&valuefield=VALOR&caption=select.finViaAdm.caption&sqlquery=where vigente = 1&decorator=/formatters/entities/selectSimpleformatter.xml"
																				image="img/search-mg.gif" 
																				titleKeyLink="select.finViaAdm" 
																				showFrame="true">
																										  	  
															<ispac:parameter name="SELECT_FIN_VIA_ADMIN" id="property(FIN_VIA_ADMIN)" property="VALOR"/>

														</ispac:htmlTextImageFrame></nobr>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr valign="top">
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="procedure.card.tramit.recursos" tooltipKey="procedure.card.tramit.recursos.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:textarea property="property(RECURSOS)" styleClass="input" readonly="false" cols="72" rows="3"
															styleId="recursos" onkeypress="javascript:maxlength('recursos', 500)"/>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</div>
							<div style="display:<c:choose><c:when test="${selectedBlock==6}">block</c:when><c:otherwise>none</c:otherwise></c:choose>" id="block6" class="tabFormtable">
								<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
									<tr>
										<td>
											<table border="0" cellspacing="0" cellpadding="0" width="100%">
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle" width="1%">
														<ispac:tooltipLabel labelKey="procedure.card.disenio.fechaCatalog" tooltipKey="procedure.card.disenio.fechaCatalog.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<ispac:htmlTextCalendar styleId="FCATALOG" property="property(FCATALOG)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image='<%= buttoncalendar %>' format="dd/mm/yyyy" enablePast="true" />
														<script type="text/javascript">
															truncateDate('FCATALOG');
														</script>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="procedure.card.disenio.autor" tooltipKey="procedure.card.disenio.autor.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:text property="property(AUTOR)" styleClass="inputSelectS" readonly="false" maxlength="64"/>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle" style="vertical-align:top">
														<ispac:tooltipLabel labelKey="procedure.card.disenio.observ" tooltipKey="procedure.card.disenio.observ.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:textarea property="property(OBSERVACIONES)" styleClass="input" readonly="false" cols="72" rows="5"
															styleId="observ" onkeypress="javascript:maxlength('observ', 500)"/>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</div>
							<div style="display:<c:choose><c:when test="${selectedBlock==7}">block</c:when><c:otherwise>none</c:otherwise></c:choose>" id="block7" class="tabFormtable">
								<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
									<tr>
										<td>
											<table border="0" cellspacing="0" cellpadding="0" width="100%">
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle" width="1%">
														<ispac:tooltipLabel labelKey="procedure.card.info.lugPres" tooltipKey="procedure.card.info.lugPres.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:text property="property(LUGAR_PRESENT)" styleClass="inputSelectS" readonly="false" maxlength="500"/>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="procedure.card.info.condEcon" tooltipKey="procedure.card.info.condEcon.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:textarea property="property(CNDS_ECNMCS)" styleClass="input" readonly="false" cols="72" rows="2"
															styleId="condecon" onkeypress="javascript:maxlength('condecon', 500)"/>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="procedure.card.info.ingPub" tooltipKey="procedure.card.info.ingPub.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:textarea property="property(INGRESOS)" styleClass="input" readonly="false" cols="72" rows="2"
															styleId="ingresos" onkeypress="javascript:maxlength('ingresos', 1024)"/>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="procedure.card.info.formPagCobro" tooltipKey="procedure.card.info.formPagCobro.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:textarea property="property(F_CBR_PGO)" styleClass="input" readonly="false" cols="72" rows="2"
															styleId="cobrpag" onkeypress="javascript:maxlength('cobrpag', 1024)"/>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
<%-- 
											</table>
										</td>
									</tr>
								</table>
							</div>
							<div style="display:<c:choose><c:when test="${selectedBlock==8}">block</c:when><c:otherwise>none</c:otherwise></c:choose>" id="block8" class="tabFormtable">
								<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
									<tr>
										<td>
											<table border="0" cellspacing="0" cellpadding="0" width="100%">
--%>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle" width="1%">
														<ispac:tooltipLabel labelKey="procedure.card.info.infSanc" tooltipKey="procedure.card.info.infSanc.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:textarea property="property(INFR_SANC)" styleClass="input" readonly="false" cols="72" rows="2"
															styleId="infrac" onkeypress="javascript:maxlength('infrac', 1024)"/>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="procedure.card.info.calend" tooltipKey="procedure.card.info.calend.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:textarea property="property(CALENDARIO)" styleClass="input" readonly="false" cols="72" rows="2"
															styleId="calen" onkeypress="javascript:maxlength('calen', 1024)"/>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle">
														<ispac:tooltipLabel labelKey="procedure.card.info.descTram" tooltipKey="procedure.card.info.descTram.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:textarea property="property(DSCR_TRAM)" styleClass="input" readonly="false" cols="72" rows="2"
															styleId="desctram" onkeypress="javascript:maxlength('desctram', 1024)"/>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</div>
							<div style="display:<c:choose><c:when test="${selectedBlock==9}">block</c:when><c:otherwise>none</c:otherwise></c:choose>" id="block9" class="tabFormtable">
								<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
									<tr>
										<td>
											<table border="0" cellspacing="0" cellpadding="0" width="100%">
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle" width="1%" style="vertical-align:top;">
														<ispac:tooltipLabel labelKey="procedure.card.datosNorm.normativa" tooltipKey="procedure.card.datosNorm.normativa.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:textarea property="property(NORMATIVA)" styleClass="input" readonly="false" cols="72" rows="2"
															styleId="norm" onkeypress="javascript:maxlength('norm', 1024)"/>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle" style="vertical-align:top;">
														<ispac:tooltipLabel labelKey="procedure.card.datosNorm.condPartic" tooltipKey="procedure.card.datosNorm.condPartic.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:textarea property="property(CND_PARTICIP)" styleClass="input" readonly="false" cols="72" rows="2"
															styleId="partic" onkeypress="javascript:maxlength('partic', 500)"/>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle" style="vertical-align:top;">
														<ispac:tooltipLabel labelKey="procedure.card.datosNorm.docAportar" tooltipKey="procedure.card.datosNorm.docAportar.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:textarea property="property(DOCUMENTACION)" styleClass="input" readonly="false" cols="72" rows="6"
															styleId="texta" onkeypress="javascript:maxlength('texta', 16000)"/>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</div>
							<div style="display:<c:choose><c:when test="${selectedBlock==10}">block</c:when><c:otherwise>none</c:otherwise></c:choose>" id="block10" class="tabFormtable">
								<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
									<tr>
										<td>
											<table border="0" cellspacing="0" cellpadding="0" width="100%">
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="16px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle" width="1%">
														<ispac:tooltipLabel labelKey="procedure.card.producers.producerSystem" tooltipKey="procedure.card.producers.producerSystem.tooltip"/>
													</td>
													<td height="20">
														<html:hidden property="property(COD_SISTEMA_PRODUCTOR)"/>
														&nbsp;&nbsp;<nobr><ispac:htmlTextImageFrame property="property(SPAC_VLDTBL_SIST_PRODUCTORES:SUSTITUTO)"
																				readonly="true"
																				readonlyTag="false"
																				propertyReadonly="readonly"
																				styleClass="input"
																				styleClassReadonly="inputReadOnly"
																				size="40"
																				id="sistemaProductor"
																				target="workframe"
																				action="selectObject.do?codetable=SPAC_VLDTBL_SIST_PRODUCTORES&codefield=ID&valuefield=SUSTITUTO&caption=select.producerSystem.caption&decorator=/formatters/entities/selectSustitutoformatter.xml"
																				image="img/search-mg.gif" 
																				titleKeyLink="select.producerSystem" 
																				showDelete="false"
																				showFrame="true">
																										  	  
															<ispac:parameter name="sistemaProductor" id="property(COD_SISTEMA_PRODUCTOR)" property="VALOR"/>
				                                        	<ispac:parameter name="sistemaProductor" id="property(SPAC_VLDTBL_SIST_PRODUCTORES:SUSTITUTO)" property="SUSTITUTO"/>
																						
														</ispac:htmlTextImageFrame>
														</nobr>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td>
												</tr>
												<tr>
													<td colspan="2"><hr/></td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td class="formsTitleB">
											<bean:message key="procedure.card.producers.list.caption"/>
										</td>
									</tr>
									<tr>
										<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
									</tr>
									<tr>
										<td class="formsTitle" style="padding:5px;">
											<ispac:linkframe id="PCDORGMANAGER"
													 styleClass="form_button_white"
												     target="workframe"
													 action='<%="producers.do?method=form&retEntityId=" + request.getAttribute("RET_ENTITY_ID") + "&retKeyId=" + request.getAttribute("RET_KEY_ID") + "&block=10"%>'
													 titleKey="forms.button.add"
													 width="640"
													 height="480"
													 showFrame="true">
											</ispac:linkframe>
											&nbsp;
											<a href="javascript:removeProducers();" class='form_button_white'>
												<nobr><bean:message key="forms.button.delete"/></nobr>
											</a>
										</td>
									</tr>
									<tr>
										<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td>
									</tr>
									<tr>
										<td class="caja">
											<display:table name="PRODUCER_LIST" 
														   id="producer" 
														   export="false" 
														   class="tableDisplay"
														   style="width:100%;margin:0px;padding:0px;"														   
											  			   sort="list">
												<display:column title='<input type="checkbox" onclick="javascript:checkAll(document.forms[0].multibox, this);"/>' 
																headerClass="headerDisplay" 
																sortable="false"
																style="text-align:center;width:30px;">
													<html:multibox property="multibox">
														<bean:write name="producer" property="property(SPAC_CT_PCDORG:ID)"/>
													</html:multibox>
												</display:column>
												<display:column titleKey="procedure.card.producers.list.name" 
																headerClass="headerDisplay" 
																sortable="false">
													<bean:write name="producer" property="property(NOMBRE_UNIDAD_TRAMITADORA)"/>
												</display:column>
												<display:column titleKey="procedure.card.producers.list.date"
											  					headerClass="headerDisplay"
											  					sortable="false">
											  		<bean:write name="producer" property="property(SPAC_CT_PCDORG:FECHA_INI_PROD)" format="dd/MM/yyyy"/>
												</display:column>
												<display:column title=""
																headerClass="headerDisplay"
											  					sortable="false">
								  					<bean:define id="pcdorgId" name="producer" property="property(SPAC_CT_PCDORG:ID)"/>
													<c:url var="url" value="/producers.do">
														<c:param name="method" value="reorder"/>
														<c:param name="pcdorgId" value="${pcdorgId}"/>
														<c:param name="retEntityId" value="${requestScope['RET_ENTITY_ID']}"/>
														<c:param name="retKeyId" value="${requestScope['RET_KEY_ID']}"/>
														<c:param name="block" value="10"/>
													</c:url>
													<center>
														<a href='<c:out value="${url}"/>&modOrder=INC'
															class="aOrderButton">+</a>
														<a href='<c:out value="${url}"/>&modOrder=DEC'
															class="aOrderButton">-</a>
													</center>
												</display:column>
											</display:table>
										</td>
									</tr>
									<tr>
										<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
									</tr>
								</table>
							</div>
							<div style="display:<c:choose><c:when test="${selectedBlock==11}">block</c:when><c:otherwise>none</c:otherwise></c:choose>" id="block11" class="tabFormtable">
								<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
									<tr>
										<td>
											<table border="0" cellspacing="0" cellpadding="0" width="100%">
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr valign="top">
													<td height="20" class="formsTitle" width="1%" style="vertical-align:top;">
														<ispac:tooltipLabel labelKey="procedure.card.rt.map" tooltipKey="procedure.card.rt.map.tooltip"/>
													</td>
													<td height="20">
														&nbsp;&nbsp;<html:textarea property="property(MAPEO_RT)" styleClass="input" readonly="false" cols="72" rows="10"
															styleId="norm"/>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</html:form>
</div>
