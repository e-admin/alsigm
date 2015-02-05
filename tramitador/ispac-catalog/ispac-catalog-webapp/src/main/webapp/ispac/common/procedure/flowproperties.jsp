<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<div class="tabButtons">
	&nbsp;
</div>

<div class="tabContent">

<html:form action='/showProcedureEntity.do'>

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
														<nobr><bean:message key="form.pflujo.propertyLabel.origNode"/>:</nobr>
													</td>
													<td height="20">
														<bean:define id="origNodeType" name="defaultForm" property="property(ONODE:TIPO)"/>
														<c:choose>
															<c:when test="${origNodeType == '1'}">
																<bean:define id="origNodeName" name="defaultForm" property="property(OSTAGE:NOMBRE)"/>
															</c:when>
															<c:otherwise>
																<bean:define id="origNodeId" name="defaultForm" property="property(ONODE:ID)"/>
																<bean:define id="origNodeName">
																	<bean:message key="procedure.tree.syncnode" arg0='<%=String.valueOf(origNodeId)%>'/>
																</bean:define>
															</c:otherwise>
														</c:choose>
														&nbsp;&nbsp;<input type="text" class="inputReadOnly" 
															value='<c:out value="${origNodeName}" />'
															size="100" readonly="true"/>
													</td>
												</tr>
												<tr>
													<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
												</tr>
												<tr>
													<td height="20" class="formsTitle">
														<nobr><bean:message key="form.pflujo.propertyLabel.destNode"/>:</nobr>
													</td>
													<td height="20">
														<bean:define id="destNodeType" name="defaultForm" property="property(DNODE:TIPO)"/>
														<c:choose>
															<c:when test="${destNodeType == '1'}">
																<bean:define id="destNodeName" name="defaultForm" property="property(DSTAGE:NOMBRE)"/>
															</c:when>
															<c:otherwise>
																<bean:define id="destNodeId" name="defaultForm" property="property(DNODE:ID)"/>
																<bean:define id="destNodeName">
																	<bean:message key="procedure.tree.syncnode" arg0='<%=String.valueOf(destNodeId)%>'/>
																</bean:define>
															</c:otherwise>
														</c:choose>
														&nbsp;&nbsp;<input type="text" class="inputReadOnly" 
															value='<c:out value="${destNodeName}" />'
															size="100" readonly="true"/>
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
						<td width="4"><img height="1" width="4px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
					</tr>
					<tr>
						<td height="5px" colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</html:form>	
</div>