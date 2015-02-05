<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="block" value="${param.block}"/>
<c:if test="${empty block}">
	<c:set var="block" value="1"/>
</c:if>

<script>
	function acceptRegister(register) {
		jConfirm('<bean:message key="intray.confirm.aceptar"/>', '<bean:message key="common.confirm"/>' , '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>', function(r) {
			if(r){
				window.location.href = "acceptIntray.do?register=" + register;
			}
		});	
	}

	function rejectRegister(register) {
		var url = "rejectIntrayConfirm.do?register=" + register;
		showFrame( "workframe", url, 640, 480);
	}

	function archive(register) {
		jConfirm('<bean:message key="intray.confirm.archivar"/>', '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>', function(r) {
			if(r){
				document.defaultForm.target = "ParentWindow";
				document.defaultForm.action = "archiveIntray.do?register=" + register;
				document.defaultForm.submit();
			}
		});	
	}

<%--
	function distribute(register) {
		var url = "distributeIntray.do?type=unit&register=" + register;
		showFrame( "workframe", url, 640, 480);
	}
--%>

	function initExpedient(register) {
		var url = "expedientIntray.do?register=" + register;
		showFrame( "workframe", url);
	}

	function attachExpedient(register) {
		var url = "processIntray.do?register=" + register;
		showFrame( "workframe", url);
	}

	function showTab(i) {
	
		if (document.getElementById('block'+ i) != undefined) {
			document.getElementById('block'+ block).style.display='none';
			document.getElementById('tdlink'+ block).className="unselect";
			document.getElementById('block'+ i).style.display='block';
			document.getElementById('tdlink'+ i).className = 'select';
			block = i;
		} else {
			document.getElementById('block'+ block).style.display='block';
			document.getElementById('tdlink'+ block).className = 'select';
		}
		
	}

	var block = '<c:out value="${block}"/>';
	
</script>

<html:form action="showIntray.do">

	<bean:define name="Intray" property="id" id="register" />

	<table border="0" width="100%">
		<tr>
			<td align="right"><ispac:onlinehelp tipoObj="10" image="img/help.gif" titleKey="header.help" /></td>
		</tr>
	</table>

	<table cellpadding="5" cellspacing="0" border="0" width="100%" align="center">
		<tr>
			<td>
				<table cellpadding="0" cellspacing="1" border="0" width="100%"
					class="box">
					<tr>
						<td class="title" height="18px">
							<table cellpadding="0" cellspacing="0" border="0" width="100%">
								<tr>
									<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px" /></td>
									<td width="100%" class="menuhead"><bean:message key="intray.titulo" /></td>
									<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px" /></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="blank">
	
						<table cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td width="4px" height="28px"><img height="1" width="4px"
									src='<ispac:rewrite href="img/pixel.gif"/>' /></td>
									
								<bean:define id="estado" name="Intray" property="state"/>
								<c:choose>
									<c:when test="${estado == 1}"><%-- PENDIENTE --%>
										<td class="formaction" height="28px"><input type="button"
											value='<bean:message key="forms.button.aceptar"/>'
											onclick="javascript:acceptRegister('<bean:write name="register"/>');"
											class="form_button_white" /></td>
										<td class="formaction" height="28px"><input type="button"
											value='<bean:message key="forms.button.rechazar"/>'
											onclick="javascript:rejectRegister('<bean:write name="register"/>');"
											class="form_button_white" /></td>
									</c:when>
									<c:when test="${estado == 2}"><%-- ACEPTADO --%>
										<td class="formaction" height="28px"><input type="button"
											value='<bean:message key="forms.button.archivar"/>'
											onclick="javascript:archive('<bean:write name="register"/>');"
											class="form_button_white" /></td>
										<%--
										<td class="formaction" height="28px"><input type="button"
											value='<bean:message key="forms.button.distribuir"/>'
											onclick="javascript:distribute('<bean:write name="register"/>');"
											class="form_button_white" /></td>
										--%>
										<td class="formaction" height="28px"><input type="button"
											value='<bean:message key="forms.button.iniciarExpediente"/>'
											onclick="javascript:initExpedient('<bean:write name="register"/>');"
											class="form_button_white" /></td>
										<td class="formaction" height="28px"><input type="button"
											value='<bean:message key="forms.button.anexarExpediente"/>'
											onclick="javascript:attachExpedient('<bean:write name="register"/>');"
											class="form_button_white" /></td>
									</c:when>
								</c:choose>
							</tr>
						</table>
	
						<table width="100%" border="0" cellspacing="2" cellpadding="2">
							<tr>
								<td height="5px" colspan="3"><html:errors /></td>
							</tr>
							<tr>
								<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>' /></td>
								<td width="100%">
									<table border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td><img height="8px" src='<ispac:rewrite href="img/pixel.gif"/>' /></td>
										</tr>
										<tr>
											<td class="unselect" id="tdlink1" align="center" onclick="showTab(1)">
												<bean:message key="intray.tab.registroEntrada"/>
											</td>
											<td width="5px"><img height="1" width="5px" src='<ispac:rewrite href="img/pixel.gif"/>' /></td>
											<td class="unselect" id="tdlink2" align="center" onclick="showTab(2)">
												<bean:message key="intray.tab.documentos"/>
											</td>
											<td width="5px"><img height="1" width="5px" src='<ispac:rewrite href="img/pixel.gif"/>' /></td>
											<td class="unselect" id="tdlink3" align="center" onclick="showTab(3)">
												<bean:message key="intray.tab.distribucion"/>
											</td>

											<c:if test="${estado == 2}"><%-- ACEPTADO --%>
											<td width="5px"><img height="1" width="5px" src='<ispac:rewrite href="img/pixel.gif"/>' /></td>
											<td class="unselect" id="tdlink4" align="center" onclick="showTab(4)">
												<bean:message key="intray.tab.expedients"/>
											</td>
											</c:if>
										</tr>
									</table>
									<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
										<tr>
											<td><img height="8px" src='<ispac:rewrite href="img/pixel.gif"/>' /></td>
										</tr>
										<tr>
											<td>
												<img height="1px" width="10px" src='<ispac:rewrite href="img/pixel.gif"/>' />
												<html:errors />
											</td>
										</tr>
										<tr>
											<td>
												<div style="display:none" id="block1">
													<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
														<tr>
															<td>
																<table border="0" cellspacing="0" cellpadding="0" width="100%">
																	<tr>
																		<td class="formsTitle width20percent">
																			<nobr><bean:message key="intray.form.numReg"/>:</nobr>
																		</td>
																		<td height="20">
																			<html:text name="Intray" property="registerNumber"
																				styleClass="inputReadOnly" readonly="true" size="40" />
																			&nbsp;&nbsp;
																			<nobr class="formsTitle"><bean:message key="intray.form.fechaReg"/>:</nobr>
																			&nbsp;&nbsp;
																			<input type="text" name="registerDate" size="16" readonly="readonly" class="inputReadOnly"
																				value='<bean:write name="Intray" property="registerDate" format="dd/MM/yyyy"/>'/>
																			
																			&nbsp;&nbsp;
																		</td>
																	<tr>
																	<tr>
																		<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px" /></td>
																	</tr>
																	<tr valign="top">
																		<td class="formsTitle width20percent">
																			<nobr><bean:message key="intray.form.oficinaRegistro"/>:</nobr>
																		</td>
																		<td height="20">
																			<html:textarea name="Intray" property="registerOffice"
																				styleClass="inputReadOnly" readonly="true" rows="3" cols="80" />
																		</td>
																	<tr>
																	<tr>
																		<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px" /></td>
																	</tr>
																	<tr valign="top">
																		<td class="formsTitle width20percent">
																			<nobr><bean:message key="intray.form.origen"/>:</nobr>
																		</td>
																		<td height="20">
																			<html:textarea name="Intray" property="registerOrigin"
																				styleClass="inputReadOnly" readonly="true" rows="3" cols="80" />
																		</td>
																	<tr>
																	<tr>
																		<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px" /></td>
																	</tr>
																	<tr valign="top">
																		<td class="formsTitle width20percent">
																			<nobr><bean:message key="intray.form.remitente"/>:</nobr>
																		</td>
																		<td height="20">
																			<%
																				String registerSenders = "";
																				ieci.tdw.ispac.ispaclib.sicres.vo.Intray intray = (ieci.tdw.ispac.ispaclib.sicres.vo.Intray) request.getAttribute("Intray");
																				if (intray != null) {
																					ieci.tdw.ispac.ispaclib.sicres.vo.ThirdPerson[] senders = intray.getRegisterSender();
																					if (senders != null) {
																						for (int i = 0; i < senders.length; i++) {
																							if (i > 0) {
																								registerSenders += "\n";
																							}
																							registerSenders += senders[i].getName();
																						}
																					}
																				}
																			%>
																			<textarea name="registerSenders" class="inputReadOnly" readonly="true" rows="3" cols="80"><%=registerSenders%></textarea>
																		</td>
																	<tr>
																	<tr>
																		<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px" /></td>
																	</tr>
																	<tr valign="top">
																		<td class="formsTitle width20percent">
																			<nobr><bean:message key="intray.form.destino"/>:</nobr>
																		</td>
																		<td height="20">
																			<html:textarea name="Intray" property="registerDestination"
																			styleClass="inputReadOnly" readonly="true" rows="3" cols="80" />
																		</td>
																	<tr>
																	<tr>
																		<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px" /></td>
																	</tr>
																	<tr valign="top">
																		<td class="formsTitle width20percent">
																			<nobr><bean:message key="intray.form.tipoAsunto"/>:</nobr>
																		</td>
																		<td height="20">
																			<html:textarea name="Intray" property="matterTypeName"
																				styleClass="inputReadOnly" readonly="true" rows="3" cols="80" />
																		</td>
																	<tr>
																	<tr>
																		<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px" /></td>
																	</tr>
																	<tr valign="top">
																		<td class="formsTitle width20percent">
																			<nobr><bean:message key="intray.form.resumen"/>:</nobr>
																		</td>
																		<td height="20">
																			<html:textarea name="Intray" property="matter"
																				styleClass="inputReadOnly" readonly="true" rows="4" cols="80" />
																		</td>
																	<tr>
																</table>
															</td>
														</tr>
													</table>
												</div>
												
												<div style="display:none" id="block2">
													<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
														<tr>
															<td>
																<table border="0" cellspacing="0" cellpadding="0" width="100%">
																	<tr>
																		<td class="formsTitle width20percent">
																			<display:table name="documents" 
																							id="doc" 
																							requestURI="/showIntray.do?block=2"
																							export="true"
																							class="tableDisplay"
																							sort="list"
																							pagesize="15"
																							defaultorder="ascending"
																							defaultsort="0">
			
																				<display:column titleKey="formatter.documents.documento"
																								media="html" 
																								sortable="true"
																								sortProperty="property(NAME)"
																								headerClass="sortable">

																					<c:set var="intrayId"><bean:write name="doc" property="property(INTRAY_ID)"/></c:set>
																					<c:set var="docId"><bean:write name="doc" property="property(DOC_ID)"/></c:set>
																					<c:url value="showIntrayDocument.do" var="link">
																						<c:param name="intrayId" value="${intrayId}"/>
																						<c:param name="documentId" value="${docId}"/>
																					</c:url>
	
																					<c:set var="extension" value="unknown"/>
																					<logic:notEmpty name="doc" property="property(EXT)">
																						<c:set var="extension">
																							<bean:write name="doc" property="property(EXT)" />
																						</c:set>
																					</logic:notEmpty>
																					<bean:define id="extension" name="extension" type="java.lang.String"/>
																					
																					<nobr><a href='<c:out value="${link}"/>' target="_blank" class="tdlink">
																						<ispac:documenticon imageSrc="img/docs/" extension='<%=extension%>' styleClass="imgTextBottom"/>
																						<bean:write name="doc" property="property(NAME)" />
																					</a></nobr>
																				</display:column>
			
																				<display:column titleKey="formatter.documents.documento"
																								property="property(NAME)"
																								media="csv excel xml pdf"/>
																			</display:table>
																		</td>
																	</tr>
																</table>
															</td>
														</tr>
													</table>
												</div>

												<div style="display:none" id="block3">
													<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
														<tr>
															<td>
																<table border="0" cellspacing="0" cellpadding="0" width="100%">
																	<tr>
																		<td height="20" class="formsTitle width20percent">
																			<nobr><bean:message key="intray.form.fechaDistribucion"/>:</nobr>
																		</td>
																		<td height="20">
																			<input type="text" name="date" size="16" readonly="readonly" class="inputReadOnly"
																				value='<bean:write name="Intray" property="date" format="dd/MM/yyyy"/>'/>
																		</td>
																	</tr>
																	<tr>
																		<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px" /></td>
																	</tr>
																	<tr>
																		<td height="20" class="formsTitle width20percent">
																			<nobr><bean:message key="intray.form.enviadoA"/>:</nobr>
																		</td>
																		<td height="20">
																			<input type="text" name="destiny" size="80" readonly="readonly" class="inputReadOnly"
																				value='<bean:write name="Intray" property="destination" />'/>
																		</td>
																	</tr>
																	<tr>
																		<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px" /></td>
																	</tr>
																	<tr>
																		<td height="20" class="formsTitle width20percent">
																			<nobr><bean:message key="intray.form.estado"/>:</nobr>
																		</td>
																		<td height="20">
																			<bean:define id="estado">&nbsp;</bean:define>
																			<logic:equal name="Intray" property="state" value="1">
																				<bean:define id="estado"><bean:message key="intray.estado.pendiente"/></bean:define>
																			</logic:equal>
																			<logic:equal name="Intray" property="state" value="2">
																				<bean:define id="estado"><bean:message key="intray.estado.aceptado"/></bean:define>
																			</logic:equal>
																			<logic:equal name="Intray" property="state" value="3">
																				<bean:define id="estado"><bean:message key="intray.estado.archivado"/></bean:define>
																			</logic:equal>
																			<logic:equal name="Intray" property="state" value="4">
																				<bean:define id="estado"><bean:message key="intray.estado.devuelto"/></bean:define>
																			</logic:equal>
																			<input type="text" name="state" size="16" readonly="readonly" class="inputReadOnly"
																				value='<bean:write name="estado" />'/>
																			&nbsp;&nbsp;
																			<nobr class="formsTitle"><bean:message key="intray.form.fechaEstado"/>:</nobr>
																			&nbsp;&nbsp;
																			<input type="text" name="stateDate" size="16" readonly="readonly" class="inputReadOnly"
																				value='<bean:write name="Intray" property="stateDate" format="dd/MM/yyyy"/>'/>
																		</td>
																	</tr>
																	<tr>
																		<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0"height="8px" /></td>
																	</tr>
																	<tr valign="top">
																		<td height="20" class="formsTitle width20percent">
																			<nobr><bean:message key="intray.form.mensaje"/>:</nobr>
																		</td>
																		<td height="20">
																			<html:textarea name="Intray" property="message"
																				styleClass="inputReadOnly" readonly="true"
																				rows="4" cols="80" />
																		</td>
																	</tr>
																</table>
															</td>
														</tr>
													</table>
												</div>
												
												<div style="display:none" id="block4">
													<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
														<tr>
															<td>
																<display:table name="registers" 
																							id="register" 
																							requestURI="/showIntray.do?block=4"
																							export="true"
																							class="tableDisplay"
																							sort="list"
																							pagesize="15"
																							defaultorder="ascending"
																							defaultsort="0">

																	<display:setProperty name="basic.msg.empty_list">
																		<span class="emptybanner"><bean:message key="formatter.intray.noExps.msg"/></span>
																	</display:setProperty>
																							
																	<display:column titleKey="formatter.intray.numExp"
																					media="html" 
																					sortable="true"
																					sortProperty="property(NUMEXP)"
																					headerClass="sortable">
																		<html:link action="selectAnActivity.do" 
																					paramId="numexp" 
																					paramName="register"
																					paramProperty="property(NUMEXP)"
																					styleClass="displayLink">
																			<bean:write name="register" property="property(NUMEXP)" />
																		</html:link>
																	</display:column>

																	<display:column titleKey="formatter.intray.numExp"
																					property="property(NUMEXP)"
																					media="csv excel xml pdf"/>

																	<display:column titleKey="formatter.intray.asuntoExp"
																					sortable="true"
																					property="property(ASUNTO)"
																					sortProperty="property(ASUNTO)"
																					headerClass="sortable"/>

																	<display:column titleKey="formatter.intray.procedimientoExp"
																					sortable="true"
																					property="property(NOMBREPROCEDIMIENTO)"
																					sortProperty="property(NOMBREPROCEDIMIENTO)"
																					headerClass="sortable"/>

																	<display:column titleKey="formatter.intray.fechaExp"
																					sortable="true"
																					sortProperty="property(FAPERTURA)"
																					comparator="ieci.tdw.ispac.ispacweb.comparators.DateComparator"
																					headerClass="sortable">
																		<bean:write name="register" property="property(FAPERTURA)" format="dd/MM/yyyy"/>
																	</display:column>
																					
																</display:table>
															</td>
														</tr>
													</table>
												</div>
												
												</td>
										</tr>
										<tr>
											<td height="15px"><img src='<ispac:rewrite href="img/pixel.gif"/>' /></td>
										</tr>
									</table>
								</td>
								<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>' /></td>
							</tr>
							<tr>
								<td height="5px" colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>' height="5px" /></td>
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
	showTab(block);
</script>