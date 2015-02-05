<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
	<script>
		function submitform(){
			document.uploadExternalForm.submit();
		}
   	</script>
	<table cellpadding="0" cellspacing="0" border="0" width="100%"> 
		<tr>
			<td>
				<table class="box" width="100%" border="0" cellspacing="1" cellpadding="0">
					<tr>
						<td height="28px" class="title">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td>
										<table align="center" cellpadding="0" cellspacing="0" border="0" width="100%">
											<tr valign="middle" height="28">
												<td>
													<table cellpadding="0" cellspacing="0" border="0">
														<tr>
															<td height="28px" class="formaction">
																<bean:message key="msg.app.Gestion"/>
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
					<tr>
						<td class="blank">
							<table  width="100%" border="0" cellspacing="2" cellpadding="2" style="border:0px solid red;">
								<tr>
									<td height="5px" colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>' height="5px"/></td>
								</tr>
								<tr>
									<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
									<td width="100%">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td>
													<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
														<tr>
															<td><img height="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
														</tr>
														<tr>
															<td><img height="1px" width="10px" src='<ispac:rewrite href="img/pixel.gif"/>'/>
														</tr>
														<tr>
															<td>
																<table width="100%" border="0" cellspacing="0" cellpadding="0">
																	<tr>
																		<td colspan="3" align="center">
																			<table width="95%" cellpadding="0" cellspacing="0">
																				<tr>			 
																					<td width="95%" class="formsTitleB" align="center">
																						<OBJECT name="object" title="Gestion" classid='<%="CLSID:"+request.getParameter("classId")%>' 
																							<c:if test="${ !empty param.codeBase}">
																								codebase='<%=request.getContextPath()+request.getParameter("codeBase")%>'
																							</c:if>
																						>
																						</OBJECT>
																						<c:if test="${!empty requestScope.numExp}">
																							<script VBSCRIPT>
																							  object.numExpediente("<c:out value='${requestScope.numExp}'/>");
																							</script>							
																						</c:if>
																						</br></br>
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
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<div id="layer" style="visibility:hidden;z-index:1000;background:white;filter:alpha(opacity=50);-moz-opacity:.50;opacity:.50;"/></div>
	<ispac:rewrite id="waitPage" page="wait.jsp"/>
    <iframe src='<%=waitPage%>' id="workframe" name="workframe" scrolling="auto" style="visibility:hidden;z-index:1024"></iframe>
    <iframe src='<%=waitPage%>' id="workframeSearch" name="workframeSearch" scrolling="auto" style="visibility:hidden;z-index:1024"></iframe>
      <%-- Formulario utilizado por la funcion que muestra el iframe--%>
    <form name="form"></form>