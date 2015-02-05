<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<table cellpadding="5" cellspacing="0" border="0" width="100%">
	<tr>
	  <td>
	  	<table cellpadding="0" cellspacing="1" border="0" class="box" width="100%">
  	  	<tr>
				  <td class="title" height="18px" width="100%">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
			  			<tr>
			  				<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
			  				<td width="100%" class="menuhead"><bean:message key="upload.title"/></td>
			  			</tr>
						</table>
		  		</td>
				</tr>
  	  	<tr>
  	  	  <td class="blank">
  	  	  	<table cellpadding="1" cellspacing="1" border="0" align="center" width="100%">
  	  	  		<tr>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="20px"/></td>
							</tr>
							<tr>
								<td>
			            <table width="80%" border="0" cellspacing="1" cellpadding="0" class="boxform" align="center">
										<tr>
											<td class="titlebox" width="100%">
												<html:form action="uploadFile.do" method="post" enctype="multipart/form-data">
													<table border="0" cellspacing="0" cellpadding="0" width="100%">
														<tr>
															<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="10px"/></td>
														</tr>
														<tr>
															<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/></td>
															<td height="20px" class="formsTitle"><bean:message key="upload.text"/></td>
														</tr>
														<tr>
															<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="10px"/></td>
														</tr>
														<tr>
															<td colspan="2" align="center">
																<table border="0" cellspacing="0" cellpadding="0" width="100%">
																	<tr>
																		<td align="center" height="20px" class="formsTitle">
																			<html:file property="theFile" size="30" styleClass="input"/>
																		</td>
																	</tr>
																	<tr>
																		<td align="center"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/></td>
																	</tr>
																	<tr>
																		<td align="center">
																			<html:submit styleClass="form_button_white" onclick="javascript:return uploadValidate('<bean:message key="msg.upload.noFile"/>','<bean:message key="common.alert"/>' ,'<bean:message key="common.message.ok"/>','<bean:message key="common.message.cancel"/>');">
																				<bean:message key="upload.button"/>
																			</html:submit>
																		</td>
																	</tr>
																</table>
															</td>
														</tr>
														<tr>
															<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="10px"/></td>
														</tr>
													</table>
												</html:form>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="20px"/></td>
							</tr>
  	  	  	</table>
  	  	  </td>
  	  	</tr>
  	  </table>
	  </td>
	</tr>
</table>

