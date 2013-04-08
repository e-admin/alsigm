<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<ispac:hasFunction var="editionMode" functions="FUNC_INV_SIGN_CIRCUITS_EDIT" />

<script language='JavaScript' type='text/javascript'><!--
		function sustitute(){
			var noSelect = true;
			var idCheck="";
		  	var circuitoId= '<%=request.getParameter("regId")%>';
	
			elementos = document.getElementsByName("multibox");
			masDeUno=false;
			for(var i=0; i<elementos.length; i++) {
				if(elementos[i].checked){
					if(!noSelect){
					masDeUno=true;
					}else{
					noSelect = false;
					idCheck=elementos[i].value;
					}
					break;
				}
			}
	
			if (noSelect) {
				jAlert('<bean:message key="error.users.noSelected"/>', '<bean:message key="common.alert"/>' , '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
			}
			else if(masDeUno){
				jAlert('<bean:message key="error.users.onlyOne"/>', '<bean:message key="common.alert"/>' , '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
			}
			else{
				auxi='viewUsersManager.do?mode=Simple&onlyselectusers=true&action=selectSigner.do&circuitoId='+circuitoId+'&replSigner='+idCheck;
				showFrame('workframe',auxi,640,480);
			}
		}
		
		function sustituteMpt(){
			var noSelect = true;
			var idCheck="";
		  	var circuitoId= '<%=request.getParameter("regId")%>';
		
			elementos = document.getElementsByName("multibox");
			masDeUno=false;
			for(var i=0; i<elementos.length; i++) {
				if(elementos[i].checked){
					if(!noSelect){
					masDeUno=true;
					}else{
					noSelect = false;
					idCheck=elementos[i].value;
					}
					break;
				}
			}
		
			if (noSelect) {
				jAlert('<bean:message key="error.signers.noSelected"/>', '<bean:message key="common.alert"/>' , '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
			}
			else if(masDeUno){
				jAlert('<bean:message key="error.users.onlyOne"/>', '<bean:message key="common.alert"/>' , '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
			}
			else{
				auxi='viewSignersAction.do?action=selectSigner.do&circuitoId='+circuitoId+'&replSigner='+idCheck;
				showFrame('workframe',auxi,640,480);
			}
		}
		
		function deleteFirmantes() {

		var noSelect = true;
		elementos = document.getElementsByName("multibox");
		for(var i=0; i<elementos.length; i++) {
			if(elementos[i].checked){
				noSelect = false;
				break;
			}
		}


		if (noSelect) {
			<c:choose>
				<c:when test="${!empty sessionScope['defaultPortafirmas']}">
					jAlert('<bean:message key="error.users.noSelected"/>', '<bean:message key="common.alert"/>' , '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
				</c:when>
				<c:otherwise>
				jAlert('<bean:message key="error.signers.noSelected"/>', '<bean:message key="common.alert"/>' , '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
				</c:otherwise>
			</c:choose>
		}
		else {
			var frm = document.forms[0];
			frm.action='<%=request.getContextPath()%>'+"/deleteSigner.do";
			frm.submit("/deleteSigner.do");
		}
	}

	function ordenar(url){

	var noSelect = true;
		elementos = document.getElementsByName("multibox");
		for(var i=0; i<elementos.length; i++) {
			if(elementos[i].checked){
				noSelect = false;
				break;
			}
		}

		if (noSelect) {
			<c:choose>
				<c:when test="${!empty sessionScope['defaultPortafirmas']}">
					jAlert('<bean:message key="error.users.noSelected"/>', '<bean:message key="common.alert"/>' , '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
				</c:when>
				<c:otherwise>
					jAlert('<bean:message key="error.signers.noSelected"/>', '<bean:message key="common.alert"/>' , '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
				</c:otherwise>
			</c:choose>
		}
		else {

			ispac_submit(url)
		}
	}




//--></script>

<c:url value="OrderSigner.do" var="actionSubir" >
	<c:param name="method" value="subir"></c:param>
	<c:param name="regId" value="${param.regId}"/>
	<c:param name="entityId" value="${param.entityId}"/>
</c:url>

<c:url value="OrderSigner.do" var="actionBajar" >
	<c:param name="method" value="bajar"></c:param>
	<c:param name="regId" value="${param.regId}"/>
	<c:param name="entityId" value="${param.entityId}"/>
</c:url>

<c:set var="regId" value="${param.regId}"/>
<c:set var="entityId" value="${param.entityId}"/>
<bean:define id="regId" name="regId"></bean:define>
<bean:define id="entityId" name="entityId"></bean:define>
<div id="navmenutitle">
	<bean:message key="form.signProcess.mainTitle"/>
</div>
<div id="navSubMenuTitle">
</div>

<div id="navmenu">
	<ul class="actionsMenuList">
		<logic:equal name="defaultForm" property="key" value="-1">
			<c:if test="${editionMode}">
			<li>
				<a href="javascript:submit('<%= request.getContextPath() + "/storeCTEntity.do"%>')">
					<nobr><bean:message key="forms.button.accept"/></nobr>
				</a>
			</li>
			</c:if>
			<li>
				<a href='<%=request.getContextPath() + "/showSignProcessList.do"%>'>
					<nobr><bean:message key="forms.button.cancel"/></nobr>
				</a>
		  	</li>
		</logic:equal>
		<logic:notEqual name="defaultForm" property="key" value="-1">
			<c:if test="${editionMode}">
			<li>
				<a href="javascript:submit('<%= request.getContextPath() + "/storeCTEntity.do"%>')">
					<nobr><bean:message key="forms.button.save"/></nobr>
				</a>
			</li>
			</c:if>

			<li>
			<a href="javascript:submit('<%= request.getContextPath() + "/eventsSingCircuit.do?method=events&regId="+regId+"&entityId="+entityId+""%>')">
					<nobr><bean:message key="forms.button.eventos"/></nobr>
				</a>
			</li>
			<li>
				<a href='<%=request.getContextPath() + "/showSignProcessList.do"%>'>
					<nobr><bean:message key="forms.button.close"/></nobr>
				</a>
		  	</li>
		  	<c:if test="${editionMode}">
			<li>
				<bean:define name="defaultForm" property="property(SPAC_CTOS_FIRMA_CABECERA:ID_CIRCUITO)" id="circuitoId"/>
				<%String URL = ""; %>
				<c:choose>
					<c:when test="${!empty sessionScope['defaultPortafirmas']}">
									<% URL = "viewUsersManager.do?mode=Multi&captionkey=usrmgr.select.signer&selVarios=true&onlyselectusers=true&action=selectSigner.do&circuitoId=" + circuitoId;%>
					</c:when>
					<c:otherwise>
									<% URL = "viewSignersAction.do?action=selectSigner.do&circuitoId=" + circuitoId;%>
					</c:otherwise>

				</c:choose>

				<ispac:linkframe id="SELECT_SIGNER"
							     target="workframe"
								 action='<%=URL%>'
								 titleKey="form.signProcess.button.select.signer"
								 width="640"
								 height="480"
								 showFrame="true">
				</ispac:linkframe>
			</li>
			</c:if>

			<logic:equal name="defaultForm" property="property(SPAC_CTOS_FIRMA_CABECERA:TIPO)" value="2">
				<li>
					<a href="javascript:submit('<%=request.getContextPath() + "/showComponentsUseList.do"%>')">
						<nobr><bean:message key="forms.button.use"/></nobr>
					</a>
				</li>
			</logic:equal>

			<c:if test="${editionMode}">
			<li>
				<a href="javascript:query('<%= request.getContextPath() + "/deleteSignProcess.do"%>', '<bean:message key="message.deleteConfirm"/>','<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>','<bean:message key="common.message.cancel"/>')">
					<nobr><bean:message key="forms.button.deletectofirmas"/></nobr>
				</a>
		  	</li>
		  	</c:if>
	  	</logic:notEqual>
	</ul>
</div>
<html:form action='/showCTEntity.do'>

	<%--
		Nombre de Aplicación.
		 Necesario para realizar la validación
 	--%>
	<html:hidden property="entityAppName"/>
	<!-- Identificador de la entidad -->
	<html:hidden property="entity"/>
	<!-- Identificador del registro -->
	<html:hidden property="key"/>
	<!-- Registro de solo lectura-->
	<html:hidden property="readonly"/>

	<table cellpadding="1" cellspacing="0" border="0" width="100%">
		<tr>
			<td>
				<table class="box" width="100%" border="0" cellspacing="1" cellpadding="0">
					<!-- FORMULARIO -->
					<tr>
						<td class="blank">
							<table width="100%" border="0" cellspacing="2" cellpadding="2">
								<tr>
									<td height="5px" colspan="3"></td>
								</tr>
								<tr>
									<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
									<td width="100%">
										<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
											<tr>
												<td>
												<logic:messagesPresent>
													<div id="formErrorsMessage">
														<bean:message key="forms.errors.formErrorsMessage"/>
													</div>
												</logic:messagesPresent>
												</td>
											</tr>
											<tr>
												<td>
													<div style="display:block" id="page1">
														<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
															<tr>
																<td>
																	<table border="0" cellspacing="0" cellpadding="0" width="100%">

																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<logic:notEqual name="defaultForm" property="key" value="-1">
																			<tr>
																				<td height="20" class="formsTitle" width="1%">
																					<ispac:tooltipLabel labelKey="form.signProcess.propertyLabel.id" tooltipKey="form.signProcess.propertyLabel.id.tooltip"/>
																				</td>
																				<td height="20">
																					&nbsp;&nbsp;<html:text property="property(SPAC_CTOS_FIRMA_CABECERA:ID_CIRCUITO)" styleClass="inputReadOnly" size="5" readonly="true" maxlength="10"/>
																					<div id="formErrors">
																						<html:errors property="property(SPAC_CTOS_FIRMA_CABECERA:ID_CIRCUITO)"/>
																					</div>
																				</td>
																			</tr>
																		</logic:notEqual>

																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.signProcess.propertyLabel.name" tooltipKey="form.signProcess.propertyLabel.name.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:text property="property(SPAC_CTOS_FIRMA_CABECERA:DESCRIPCION)" styleClass="inputSelectS" readonly="false" maxlength="256"/>&nbsp; <bean:message key="catalog.data.obligatory"/>
																				<div id="formErrors">
																					<html:errors property="property(SPAC_CTOS_FIRMA_CABECERA:DESCRIPCION)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																			<td height="20" class="formsTitle">
																				<ispac:tooltipLabel labelKey="form.signProcess.propertyLabel.type" tooltipKey="form.signProcess.propertyLabel.type.tooltip"/>
																			</td>
																			<td height="20">
																				&nbsp;&nbsp;<html:select property="property(SPAC_CTOS_FIRMA_CABECERA:TIPO)" styleClass="input">
																									<html:option value="1" key="form.signProcess.generic"/>
																									<html:option value="2" key="form.signProcess.specific"/>
																								</html:select>&nbsp;&nbsp;<bean:message key="catalog.data.obligatory"/>
																				<div id="formErrors">
																					<html:errors property="property(SPAC_CTOS_FIRMA_CABECERA:TIPO)"/>
																				</div>
																			</td>
																		</tr>
																		<tr>
																				<td height="20" class="formsTitle" width="1%">
																					<ispac:tooltipLabel labelKey="form.signProcess.propertyLabel.system" tooltipKey="form.signProcess.propertyLabel.system.tooltip"/>
																				</td>
																				<td height="20">
																					<bean:define name="defaultForm" property="property(SPAC_CTOS_FIRMA_CABECERA:SISTEMA)" id="sistema"/>
																					&nbsp;&nbsp;<input name="property(SPAC_CTOS_FIRMA_CABECERA:SISTEMA_DESC)" class="inputReadOnly" type="text" size="44" readOnly="true" value="<bean:message key='<%="portafirmas.sistema."+sistema %>'/>"/>
																					<html:hidden property="property(SPAC_CTOS_FIRMA_CABECERA:SISTEMA)"/>
																					<div id="formErrors">
																						<html:errors property="property(SPAC_CTOS_FIRMA_CABECERA:SISTEMA)"/>
																					</div>
																				</td>
																			</tr>																		
																		<tr>
																		<c:if test="${empty sessionScope['defaultPortafirmas']}">
																			<tr>
																				<td height="20" class="formsTitle">
																					<ispac:tooltipLabel labelKey="form.signProcess.propertyLabel.sequence" tooltipKey="form.signProcess.propertyLabel.sequence.tooltip"/>
																				</td>
																				<td height="20">
																					&nbsp;&nbsp;<html:select property="property(SPAC_CTOS_FIRMA_CABECERA:SECUENCIA)" styleClass="input">
																										<html:option value="1" key="portafirmas.secuencia.cascada"/>
																										<html:option value="2" key="portafirmas.secuencia.paralelo"/>
																										<html:option value="3" key="portafirmas.secuencia.primer.firmante"/>
																									</html:select>&nbsp;&nbsp;<bean:message key="catalog.data.obligatory"/>
																					<div id="formErrors">
																						<html:errors property="property(SPAC_CTOS_FIRMA_CABECERA:SECUENCIA)"/>
																					</div>
																				</td>
																			</tr>																			
																			<tr>
																				<td height="20" class="formsTitle" width="1%">
																					<ispac:tooltipLabel labelKey="form.signProcess.propertyLabel.application" tooltipKey="form.signProcess.propertyLabel.application.tooltip"/>
																				</td>
																				<td height="20">
																					&nbsp;&nbsp;<html:text property="property(SPAC_CTOS_FIRMA_CABECERA:APLICACION)" styleClass="inputReadOnly" size="44" readonly="true"/>
																					<div id="formErrors">
																						<html:errors property="property(SPAC_CTOS_FIRMA_CABECERA:APLICACION)"/>
																					</div>
																				</td>
																		</tr>	
																		</c:if>																																																			
																		<tr>
																			<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
																		</tr>

																		<logic:notEqual name="defaultForm" property="key" value="-1">
																			<logic:notEmpty name="defaultForm" property="entityApp.itemBeanList">
																				<tr>
																					<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="12px"/></td>
																				</tr>
																				<tr>
																					<td colspan="2">
<ul class="actionsMenuListWithoutBackground">											<c:set var="cont" value="1"/>
																						<bean:define id="cont" name="cont" ></bean:define>
																						<c:set var="paso" value="0"/>
																						<bean:define id="paso" name="paso" ></bean:define>
																							<li>
																								<a href="javascript:deleteFirmantes();">
																									<nobr><bean:message key="forms.button.delete"/></nobr>
																								</a>
																							</li>

																							<li>
																								<c:choose>
																									<c:when test="${!empty sessionScope['defaultPortafirmas']}">
																										<a href="javascript:sustitute();"  >
																											<nobr><bean:message key="forms.button.sustitute"/></nobr>
																								  		</a>
																									</c:when>
																									<c:otherwise>
																										<a href="javascript:sustituteMpt();"  >
																											<nobr><bean:message key="forms.button.sustitute"/></nobr>
																								  		</a>
																									</c:otherwise>
																								</c:choose>																				
																							</li>
																							<li>
																								<a href="javascript:ordenar('<c:out value="${actionSubir}"/>');"><nobr><bean:message key="forms.button.subir"/></nobr></a>
																							</li>

																							<li>
																								<a href="javascript:ordenar('<c:out value="${actionBajar}"/>');"><nobr><bean:message key="forms.button.bajar"/></nobr></a>
																							</li>

																							</ul>
																						<display:table name="defaultForm.entityApp.itemBeanList"
																									   id='itemobj'
																									   export="false"
																									   class="tableDisplay"
																						  			   sort="list"
																						  			   requestURI=''>

																							<logic:present name="itemobj">

																								<bean:define id='item' name='itemobj' type='ieci.tdw.ispac.ispaclib.bean.ObjectBean'/>
																								<bean:define id="tiposNotificacion" name="defaultForm" property="entityApp.tiposNotificacion" type="java.util.List"></bean:define>

																								<logic:iterate name="defaultForm" property="formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">


																									<logic:equal name="format" property="fieldType" value="CHECKBOX">

																									<display:column title='<%="<input type=\'checkbox\' onclick=\'javascript:checkAll(document.forms[0].multibox, this);\'/>"%>'

																														media='<%=format.getMedia()%>'
																														headerClass='<%=format.getHeaderClass()%>'
																														sortable='<%=format.getSortable()%>'
																														decorator='<%=format.getDecorator()%>'
																														class='<%=format.getColumnClass()%>' >
																													<input type="checkbox" name="multibox" value='<%=format.formatProperty(item)%>'/>
																										</display:column>

																								  	</logic:equal>

																									<logic:equal name="format" property="fieldType" value="DATA">
																										<display:column titleKey='<%=format.getTitleKey()%>'
																														media='<%=format.getMedia()%>'
																														headerClass='<%=format.getHeaderClass()%>'
																														class='<%=format.getColumnClass()%>'
																														sortable='<%=format.getSortable()%>'
																														decorator='<%=format.getDecorator()%>'>
																											<%=format.formatProperty(item)%>
																										</display:column>
																									</logic:equal>

																									<logic:equal name="format" property="fieldType" value="INPUT">
																										<display:column titleKey='<%=format.getTitleKey()%>'
																														media='<%=format.getMedia()%>'
																														headerClass='<%=format.getHeaderClass()%>'
																														class='<%=format.getColumnClass()%>'
																														sortable='<%=format.getSortable()%>'
																														decorator='<%=format.getDecorator()%>'>

																														<c:set var="propiedad" value="property(${cont}:DIR_NOTIF)"/>
																														<bean:define id="propiedad" name="propiedad" ></bean:define>
																														<html:text property='<%= propiedad.toString() %>' styleClass="inputSelectS" readonly="false" value='<%=format.formatProperty(item).toString()%>'></html:text>

																														<c:choose>
																															<c:when test="${paso=='1'}">
																																<c:set var="paso" value="0"/>
																																<c:set var="cont"><%= cont=Integer.parseInt(cont.toString()) + 1+"" %></c:set>
																															</c:when>
																															<c:otherwise>
																																<c:set var="paso" value="1"/>
																															</c:otherwise>
																														</c:choose>


																										</display:column>
																									</logic:equal>
																									<logic:equal name="format" property="fieldType" value="LISTBOX">
																										<display:column titleKey='<%=format.getTitleKey()%>'
																														media='<%=format.getMedia()%>'
																														headerClass='<%=format.getHeaderClass()%>'
																														class='<%=format.getColumnClass()%>'
																													>

																														<c:set var="propiedad" value="property(${cont}:TIPO_NOTIF)"/>
																														<bean:define id="propiedad" name="propiedad" ></bean:define>
																														<html:select property='<%= propiedad.toString() %>'  styleClass="inputSelectS">
																															<option value="">&nbsp;</option>
																															<html:options  collection="tiposNotificacion" property="property(VALOR)" labelProperty="property(SUSTITUTO)"/>
																														</html:select>

																														<c:choose>
																															<c:when test="${paso=='1'}">
																																<c:set var="paso" value="0"/>
																																<c:set var="cont"><%= cont=Integer.parseInt(cont.toString()) + 1+"" %></c:set>
																															</c:when>
																															<c:otherwise>
																																<c:set var="paso" value="1"/>
																															</c:otherwise>
																														</c:choose>

																										</display:column>
																									</logic:equal>


																								</logic:iterate>

																							</logic:present>

																						</display:table>
																					</td>
																				</tr>
																			</logic:notEmpty>
																		</logic:notEqual>

																	</table>
																</td>
															</tr>
														</table>
													</div>
												</td>
											</tr>
											<tr>
												<td height="15px"><img src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
											</tr>
										</table>
									</td>
									<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
								</tr>
								<tr>
									<td height="5px" colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>' height="5px"/></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</html:form>
