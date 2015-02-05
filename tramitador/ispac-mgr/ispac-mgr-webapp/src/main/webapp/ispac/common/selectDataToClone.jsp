<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<ispac:layer id="wait" msgKey="msg.layer.operationInProgress" styleClassMsg="messageShowLayer" />

<script language='JavaScript' type='text/javascript'><!--

	function clone() {
	
		document.defaultForm.target = "ParentWindow";
		document.defaultForm.action = "cloneExpedient.do";
		document.defaultForm.name = "Clonacion";
		
		if (validateClonacion(document.defaultForm)) {
		
			showLayer("wait");
			document.defaultForm.action = document.defaultForm.action + "?unidades=" + document.defaultForm.unidades.value;
			document.defaultForm.submit();
		}
	}
	
//--></script>

<html:form action="cloneExpedient.do">

<table cellpadding="5" cellspacing="0" border="0" width="100%" align="center">

	<%-- 
	<tr>
		<td align="right"><ispac:onlinehelp fileName="clone" image="img/help.gif" titleKey="header.help"/></td>
	</tr>
	--%>

	<tr>
		<td>

		<table cellpadding="0" cellspacing="1" border="0" width="100%"
			class="box">
			<tr>
				<td class="title" height="18px">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<!-- OK 1 -->
					<tr>
						<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px" /></td>
						<td width="100%" class="menuhead"><!--<bean:message key="msg.clone.data.select"/>-->

						<html:link styleClass="formaction" href="javascript:clone();">
							<bean:message key="common.message.clone" />
						</html:link></td>
						<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px" /></td>
					</tr>
				</table>
				<!-- OK 1 --></td>
			</tr>
			<tr>
				<td class="blank">
				<table width="100%" border="0" cellspacing="2" cellpadding="2">
					<tr>
						<td height="5px" colspan="3"></td>
					</tr>
					<tr>
						<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
						<td width="100%">
						<table width="100%" border="0" class="formtable" cellpadding="2px" cellspacing="2px">
							<tr>
								<td><img height="8" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
							</tr>
							<tr>
								<td class="schema">

									<!-- Nombre de Aplicación. Necesario para realizar la validación -->
									<html:javascript formName="Clonacion" />
									<html:hidden property="method" value="clone" />

									<bean:message key="msg.clone.units" />: 
									&nbsp;&nbsp;<input type="text" name="unidades" value="1" size="5" maxlength="3" class="input"/>
								</td>
							</tr>
							<tr>
								<td><img height="8" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
							</tr>
						</table>
						</td>
						<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
					</tr>
					<tr>
						<td height="5px" colspan="3"></td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="2" cellpadding="2">
					<tr>
						<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
						<td width="100%">
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="selectNoCursor" id="tdlink1" align="center">
									<nobr><bean:message key="msg.clone.entidadesGenerales" /></nobr>
								</td>
							</tr>
						</table>

						<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
							<tr>
								<td><img height="8" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
							</tr>
							<tr>
								<td>
								<table cellpadding="2px" cellspacing="2px" border="0" width="100%">

									<%-- Iteramos sobre las entidades asociadas al expediente, registros de SPAC_CT_ENTIDADES --%>
									<logic:iterate id="entity" name="SchemeList">
										<bean:define id="entityId" name="entity"
											property="property(ID)" />
										<!-- Cuando la entidad no sea un TRAMITE o un DOCUMENTO-->
										<c:if
											test="${entityId != appConstants.entities.SPAC_DT_DOCUMENTOS && entityId != appConstants.entities.SPAC_DT_TRAMITES}">
											<tr>
												<td class="schema">
													
													<bean:write name="entity" property="property(TAB_LABEL)" />
													<bean:size name="entity" property="regs" id="regsEntity" />
													<c:if test="${regsEntity != 0}">
														<c:url value="selectEntityFieldsToClone.do" var="linkSelectEntityFieldsToClone">
															<c:param name="entityId">
																<bean:write name="entity" property="property(ID)" />
															</c:param>
														</c:url>
														<c:set var="_linkSelectEntityFieldsToClone" value="${linkSelectEntityFieldsToClone}"/>
														<jsp:useBean id="_linkSelectEntityFieldsToClone" type="java.lang.String"/>
														
														<ispac:imageframe id="SELECT_ENTITY_FIELDS_TO_CLONE" 
																		  target="workframe"
																		  action='<%=_linkSelectEntityFieldsToClone%>'
																		  titleKey="select.entityFieldsToClone.title"
																		  showFrame="true"
																		  styleClass="tdlink"
																		  image="img/search-mg.gif"
																		  width="640"
																		  height="480">
														</ispac:imageframe>
													</c:if>
													(<c:out	value="${regsEntity}" /> 
													<c:choose>
														<c:when test="${regsEntity == 1}">
															<bean:message key="forms.title.register" />)
														</c:when>
														<c:otherwise>
															<bean:message key="forms.title.registers" />)
														</c:otherwise>
													</c:choose>
													
													<c:if test="${regsEntity == 0}">
														<img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="15px" width="15px"/>
													</c:if>
													
												</td>
											</tr>
										</c:if>
									</logic:iterate>
								</table>
								</td>
							</tr>
							<tr>
								<td><img height="8" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
							</tr>
						</table>
						</td>
						<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
					</tr>
					<tr>
						<td height="5px" colspan="3"></td>
					</tr>
					<tr>
						<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
						<td width="100%">
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="selectNoCursor" id="tdlink1" align="center">
									<nobr> <bean:message key="msg.clone.entidadesEspecificas" /></nobr>
								</td>
							</tr>
						</table>

						<table width="100%" border="0" class="formtable" cellpadding="2px" cellspacing="2px">
							<tr>
								<td><img height="8" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
							</tr>
							<tr>
								<td class="schema">
									<bean:message key="select.entity" /> 
									<ispac:imageframe id="SELECT_ENTITY_TO_CLONE" target="workframe"
													  action='selectEntityToClone.do'
													  titleKey="select.entityToClone.title"
													  showFrame="true"
													  styleClass="tdlink"
													  image="img/search-mg.gif"
													  width="640"
													  height="480">
									</ispac:imageframe>
								</td>
							</tr>

							<logic:notEmpty name="SchemeEntityList">

								<tr>
									<td class="blank" width="100%">

									<table cellpadding="1" cellspacing="0" border="0">
										<tr><td height="10px" colspan="2"></td></tr>

										<%-- Iteramos sobre las entidades  --%>
										<logic:iterate id="entity" name="SchemeEntityList">
										
											<tr>
												<td class="schema">
												
													<bean:write name="entity" property="property(TAB_LABEL)" />
													<bean:size name="entity" property="regs" id="regsEntity" />
													<c:if test="${regsEntity != 0}">
														<c:url value="selectEntityFieldsToClone.do" var="linkSelectEntityFieldsToClone2">
															<c:param name="entityId">
																<bean:write name="entity" property="property(ID)" />
															</c:param>
														</c:url>
														<c:set var="_linkSelectEntityFieldsToClone2" value="${linkSelectEntityFieldsToClone2}"/>
														<jsp:useBean id="_linkSelectEntityFieldsToClone2" type="java.lang.String"/>
														
														<ispac:imageframe id="SELECT_ENTITY_FIELDS_TO_CLONE" 
																		  target="workframe"
																		  action='<%=_linkSelectEntityFieldsToClone2%>'
																		  titleKey="select.entityFieldsToClone.title"
																		  showFrame="true"
																		  styleClass="tdlink"
																		  image="img/search-mg.gif"
																		  width="640"
																		  height="480">
														</ispac:imageframe>
													</c:if>
													(<c:out	value="${regsEntity}" />
													<c:choose>
														<c:when test="${regsEntity == 1}">
															<bean:message key="forms.title.register" />)
														</c:when>
														<c:otherwise>
															<bean:message key="forms.title.registers" />)
														</c:otherwise>
													</c:choose>
													
													<c:url value="cloneExpedient.do" var="_linkDeleteEntity">
														<c:param name="method" value="deleteEntity" />
														<c:param name="entityId">
															<bean:write name="entity" property="property(ID)" />
														</c:param>
													</c:url>
													<jsp:useBean id="_linkDeleteEntity" type="java.lang.String"/>
													&nbsp;
													<a href='<c:out value="${_linkDeleteEntity}"/>'	class="tdlink">
														<img border="0" src='<ispac:rewrite href="img/borrar.gif"/>' title='<bean:message key="title.delete.data.selection"/>'>
													</a>
												
												</td>
												<td>
													<%--
													<c:url value="cloneExpedient.do" var="_linkDeleteEntity">
														<c:param name="method" value="deleteEntity" />
														<c:param name="entityId">
															<bean:write name="entity" property="property(ID)" />
														</c:param>
													</c:url>
													<jsp:useBean id="_linkDeleteEntity" type="java.lang.String"/>
													
													<html:link action='<%=_linkDeleteEntity%>' styleClass="form_button" style="text-decoration: none;" >
											  			<bean:message key="title.delete.data.selection" />
											  		</html:link>
											  		--%>
												</td>
											</tr>
											
										</logic:iterate>

									</table>

									</td>
								</tr>

							</logic:notEmpty>
							<tr>
								<td><img height="8" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
							</tr>

						</table>

						</td>
						<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
					</tr>
					<tr>
						<td height="15"><img src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
					</tr>
				</table>

				</td>
			</tr>
		</table>
		</td>
	</tr>

</table>

</html:form>