<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<table align="center" cellpadding="0" cellspacing="0" border="0" width="99%">
	<tr>
		<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="10px"/></td>
	</tr>
	<tr>
		<td>
			<div id="header">
 			  <ul id="menuList">
					<%-- 
						 Si se recibe por parametro el identificador de procedimineto, 
					     se añade a los enlaces. Esto se debe a que el pcdId se usa como
					     filtro para obtener los grupos de delegacion
					--%>
					<li class="selected">
						<c:if test="${!empty param.pcdId}">
							<c:set var="queryString" value="&pcdId=${param.pcdId}"/>
						</c:if>
						<c:if test="${empty param.pcdId}">
							<c:set var="queryString" value=""/>
						</c:if>
						<a href="javascript:delegate('delegateGroup.do?group=1<c:out value="${queryString}"/>');" title='<bean:message key="delegate.group"/>'>
							<bean:message key="delegate.group"/>
						</a>
					</li>
					<li class="unselected">
						<c:if test="${!empty param.pcdId}">
							<c:set var="queryString" value="?pcdId=${param.pcdId}"/>
						</c:if>
						<c:if test="${empty param.pcdId}">
							<c:set var="queryString" value=""/>
						</c:if>
						<a href="javascript:delegate('delegateOrg.do<c:out value="${queryString}"/>');" title='<bean:message key="delegate.org"/>'>
							<bean:message key="delegate.org"/>
						</a>
				    </li>
			  </ul>
			</div>
		</td>
	</tr>

	<tr>
		<td>
			<table cellpadding="0" cellspacing="0" width="100%" class="tab">
				<tr>
					<td class="blank" align="center" width="100%">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0" class="box">
										<tr>
											<td class="title" height="32px">
												<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
													<tr>
														<td>
															<table align="center" cellpadding="0" cellspacing="0" border="0" width="100%">
																<tr>
																	<td>
																	<!-- COMIENZO DE LAS ACCIONES -->
																		<table cellpadding="0" cellspacing="0" border="0" width="100%">
																			<tr>
																				<td width="4px" height="8px"><img height="1" width="4px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
																				<logic:notPresent name="groups">
																					<td class="titleTab">
																						<bean:write name="group" />
																					</td>
																				</logic:notPresent>
																				<td height="8px" width="10px"><img height="1" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
																			</tr>
																		</table>
																	<!-- FINAL DE LAS ACCIONES -->
																	</td>
																</tr>
																<tr>
																	<td><html:errors/>
																	</td>
																</tr>
																<logic:present name="userLock" scope="session">
																	<tr>
																		<td style="text-align:right;" class="errorRojo">
																			<bean:message key="msg.expedient.lock.user" arg0='<%=(String)request.getSession().getAttribute("userLock")%>'/>
																		</td>
																	</tr>
																</logic:present>					
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td class="blank">
												<table width="100%" border="0" cellspacing="2" cellpadding="2">
													<tr>
														<td height="5px" colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>' height="5px"/></td>
													</tr>
													<tr>
														<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
														<td width="100%">
															<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
															<table border="0" cellspacing="0" cellpadding="0">
																<tr>
																	<td class="select" id="tdlink1" align="center">
																		<bean:message key="delegate.actionData"/>
																	</td>
																</tr>
															</table>
															<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
																<tr>
																	<td><img height="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
																</tr>
																<tr>
																	<td><img height="1px" width="10px" src='<ispac:rewrite href="img/pixel.gif"/>'/><!-- aqui van los errores--></td>
																</tr>
																<tr>
																	<td>
																		<!-- BLOQUE1 DE CAMPOS -->
																		<div style="display:block" id="block1">
																			<!-- TABLA DE CAMPOS -->
																			<table border="0" cellspacing="0" cellpadding="0" align="center" width="100%">
																				<tr>
																					<td>
																						<table cellpadding="4" cellspacing="0" border="0" width="90%" align="center">
																							<tr><td><img src='<ispac:rewrite href="img/pixel.gif"/>'></td></tr>
																							<tr>
																								<td>
																									<div class="scroll">
																										<table>
																											<logic:notPresent name="groups">
																												<logic:empty name="users">
																													<tr>
																														<td colspan="4" class="elementTab"><bean:message key="delegate.message.nouser"/></td>
																													</tr>
																												</logic:empty>
																												<logic:iterate id="user" name="users" type="ieci.tdw.ispac.ispaclib.bean.ItemBean">
																													<%
																														String _uid = user.getProperty("UID").toString();
																														String _url = "javascript:delegate(\"delegateResp.do?uid="+_uid+"\");";
																														pageContext.setAttribute("url",_url);
																													%>
																													<tr>
																														<td width="5px"><img src='<ispac:rewrite href="img/pixel.gif"/>' align="center" valign="top" border="0"/></td>
																														<td width='17px'>
																															<img src='<ispac:rewrite href="img/user.gif"/>' align="center" valign="top" width="17px" height="15px" border="0"/></td>
																														<td align="left" class="elementTab" title='<%= user.getProperty("NAME") %>'>
																															<logic:iterate id="format" name="Formatter" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
																																<logic:equal name="format" property="property" value="NAME">
																																	<%=format.formatProperty(user)%>
																																</logic:equal>
																															</logic:iterate>
																														</td>
																
																														<td>
																														<%--
																															<html:link href='<%= _url %>' >
																																<img src='<ispac:rewrite href="img/delegar.gif"/>' width="44px" height="11px" border="0"/>
																															</html:link>
																														--%>
																															<img src='<ispac:rewrite href="img/pixel.gif"/>' width="10px" border="0"/>
																															<html:link href='<%= _url %>' styleClass="btLisoThin">
																																<bean:message key="delegateInfo.title"/>
																															</html:link>
																														</td>
																													</tr>
																												</logic:iterate>
																											</logic:notPresent>
																											<logic:present name="groups">
																												<logic:empty name="groups">
																													<tr>
																														<td colspan="4" class="elementTab"><bean:message key="delegate.message.nogroup"/></td>
																													</tr>
																												</logic:empty>
																												<logic:iterate id="groupId" name="groups" type="ieci.tdw.ispac.ispaclib.bean.ItemBean">
																													<%
																														String uid = groupId.getProperty("UID").toString();
																														String url = "javascript:delegate(\"delegateGroup.do?uid="+uid+"\");";
																														String delegateGroup = "javascript:delegate(\"delegateResp.do?uid="+uid+"\");";
																														pageContext.setAttribute("url",url);
																														pageContext.setAttribute("delegateGroup",delegateGroup);
																													%>
																													<tr>
																														<td width="5px"><img src='<ispac:rewrite href="img/pixel.gif"/>' align="center" valign="top" border="0"/></td>
																														<td width='17px'><img src='<ispac:rewrite href="img/group.gif"/>' align="center" valign="top" width="17px" height="15px" border="0"/></td>
																														<td align="left" class="elementTab" title='<%= groupId.getProperty("NAME") %>'>
																															<logic:iterate id="format" name="Formatter" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
																																<logic:equal name="format" property="property" value="NAME">
																																	<html:link href='<%= url %>' styleClass="tdlink">
																																		<%=format.formatProperty(groupId)%>
																																	</html:link>
																																</logic:equal>
																															</logic:iterate>
																														</td>
																														<td>
																														<%--
																															<html:link href='<%= delegateGroup %>'>
																																<img src='<ispac:rewrite href="img/delegar.gif"/>' width="44px" height="11px" border="0"/>
																															</html:link>
																														--%>
																															<img src='<ispac:rewrite href="img/pixel.gif"/>' width="10px" border="0"/>
																															<html:link href='<%= delegateGroup %>' styleClass="btLisoThin">
																																<bean:message key="delegateInfo.title"/>
																															</html:link>
																														</td>
																													</tr>
																												</logic:iterate>
																											</logic:present>
																										</table>
																									</div>
																								</td>
																							</tr>
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
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>