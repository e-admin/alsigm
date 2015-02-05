<%@ page contentType="text/html;charset=ISO-8859-1" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html:html>
<head>
<ieci:baseInvesDoc/>
<title><bean:message key="message.common.title"/></title>

<link rel="stylesheet" type="text/css" href="include/css/login.css"/>
<link rel="stylesheet" type="text/css" href="include/css/error.css"/>
<script type="text/javascript" src="include/js/validations.js"></script>
<c:choose>
	<c:when test="${!requestScope.IECI_TECDOC_UAS_MUST_CHANGE_PWD}">
	<script language="javascript">
		var pulse = false;
		function validateForm(objForm)
		{
			if(pulse)
				return false;
			var message = "<bean:message key="message.login.validation.error"/>";
			var cond = false;
			var errors = "";
			if(isWhitespace(trim(objForm.user.value)))
				errors += "<bean:message key="message.login.validation.error.userIsEmpty"/>";
			if(isWhitespace(trim(objForm.pwd.value)))
				errors += "<bean:message key="message.login.validation.error.pwdIsEmpty"/>";
			if(errors.length > 1)
			{
				message = message.concat(errors);
				alert(message);
				return false;
			} else {
				mask(objForm.maskPwd, objForm.pwd.value);
				reset(objForm.pwd);
				pulse = true;
				return true;
			}
		}
		function loginCert()
		{
			document.location.href = "<%=request.getContextPath()%>/acs/loginCert.jsp";
		}
	</script>
	</c:when>
	<c:otherwise>
	
	<script language="javascript">
		var pulse = false;
		function validateForm(objForm)
		{
			if(pulse)
				return false;
				
			var message = "<bean:message key="message.login.validation.error"/>";
			var cond = false;
			var errors = "";
		
		/*
		confNewPwd  // newPwd // pwd
		
		message.changePwd.pwd_is_empty=La contraseña es obligatoria.
		message.changePwd.newPwd_is_empty=La nueva contraseña no puede estar vacia.
		message.changePwd.confNewPwd_is_empty=Por favor, confirme la contraseña.
		message.changePwd.new_pwd_not_macth=La nueva Contraseña y su Confirmación no coinciden.
	*/
			if(isWhitespace(trim(objForm.pwd.value)))
				errors += "<bean:message key="message.changePwd.pwd_is_empty"/> \n";
			if(isWhitespace(trim(objForm.newPwd.value)))
				errors += "<bean:message key="message.changePwd.newPwd_is_empty"/>\n";
			if(isWhitespace(trim(objForm.confNewPwd.value)))
				errors += "<bean:message key="message.changePwd.confNewPwd_is_empty"/>\n";
			if ( trim(objForm.newPwd.value) != trim(objForm.confNewPwd.value) )
				errors += "<bean:message key="message.changePwd.new_pwd_not_macth"/>\n";
	
			if(errors.length > 1)
			{
				message = message.concat(errors);
				alert(message);
				return false;
			} else {
				mask(objForm.maskPwd, objForm.pwd.value);
				reset(objForm.pwd);
				
				mask(objForm.maskNewPwd, objForm.newPwd.value);
				reset(objForm.pwd);
				
				mask(objForm.maskConfNewPwd, objForm.confNewPwd.value);
				reset(objForm.pwd);
				pulse = true;
				return true;
			}
		}
	</script>
	
	</c:otherwise>

</c:choose>


</head>

<body id="body">
<div id="error"></div>
<ieci:errors bean='<%=request.getAttribute("errorbean") %>'></ieci:errors>

	<table class="tableBase">
		<tr>
			<td width="100%">
				<table class="tableBase">
					<tr>
						<td valign="top"  align="left" class="cabecera"><img hspace="10" src="include/images/logo_idoc.gif"/></td>
				                <td valign="top"  align="right" class="cabecera"><img hspace="10" src="include/images/logoJA.gif"/></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table class="tableBase" height="100%">
					<tr>
						<td width="50%" align="right">
							<table>
								<tr>
									<td><img src="include/images/desk.gif" border="0" width="392px" height="224px" alt="<bean:message key="message.login.image.logo.alt"/>"/>
									</td>
								</tr>
							</table>
						</td>
						<td width="50%" align="left"> 

							<table border="0" cellpadding="0" cellspacing="10">
									<c:choose>
										<c:when test="${!requestScope.IECI_TECDOC_UAS_MUST_CHANGE_PWD}">
											<html:form action="/html/login" onsubmit="return validateForm(this);" focus="user" method="post" >
											<tr>
												<td class="index" colspan="2"><br><bean:message key="message.login.fill"/><br>
												</td>
												<td align="center">&nbsp;
												</td>
											</tr>
											
											<tr>
												<td class="index"><bean:message key="message.login.name"/>:
												</td>
												<td  width="45%">	
													<html:text tabindex="1" property="user"/>
												</td>
												<td>&nbsp;
													<html:submit tabindex="3" styleClass="genericButton"><bean:message key="message.login.button.login"/></html:submit>
												</td>
											</tr>
											
											<tr>
												<td class="index"><bean:message key="message.login.pwd" />:
												</td>
												<td width="45%">	
													<html:password tabindex="2" property="pwd" redisplay="false"/>
													<html:hidden property="maskPwd" value=""/>
												</td>
												<td class="index">	&nbsp;
												</td>
											</tr>
											<tr>
												<td class="index">
												<%-- <bean:message key="message.login.language"/>: --%>
												</td>
												<td width="45%"> <%-- 
													<html:select property="language" onchange="changeLocale(this);" >
														<nested:iterate id ="item" name="app_languages" scope="application">
															<option value="<nested:write name="item" property="codeLanguage"/>"<c:if test="${requestScope.locale eq item.codeLanguage}"> selected</c:if>><nested:write name="item"  property="language"/></option>
														</nested:iterate>
													</html:select> --%>&nbsp;
												</td>
												<td class="index">	&nbsp;
												</td>
											</tr>
											</html:form>
										</c:when>
										<c:otherwise>
										<html:form action="/html/changePwd" onsubmit="return validateForm(this);" focus="pwd" method="post">
										
											<tr>
												<td class="index">Contraseña Actual:</td>
												<td width="45%"><html:password property="pwd" tabindex="1" redisplay="false"/><html:hidden property="maskPwd" value=""/></td>
												<td>&nbsp;</td> 
											</tr>
											<tr style="height: 2px;">
												<td colspan="3"><hr style="width: 90%;"></td>
											</tr>
											<tr>
												<td class="index">Contraseña:</td>
												<td  width="45%"> <html:password property="newPwd" tabindex="2" redisplay="false"/><html:hidden property="maskNewPwd" value=""/>
												</td>
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td class="index">Confirmación:	</td>
												<td width="45%">
													<html:password property="confNewPwd" tabindex="3" redisplay="false"/>	
													<html:hidden property="maskConfNewPwd" value=""/>
												</td>
												<td class="index">	<html:submit tabindex="4" styleClass="genericButton"><bean:message key="message.login.button.login"/></html:submit></td>
											</tr>
											<tr>
												<td class="index">
												<%-- <bean:message key="message.login.language"/>: --%>
												</td>
												<td width="45%"> <%-- 
													<html:select property="language" onchange="changeLocale(this);" >
														<nested:iterate id ="item" name="app_languages" scope="application">
															<option value="<nested:write name="item" property="codeLanguage"/>"<c:if test="${requestScope.locale eq item.codeLanguage}"> selected</c:if>><nested:write name="item"  property="language"/></option>
														</nested:iterate>
													</html:select> --%>&nbsp;
												</td>
												<td class="index">	&nbsp;
												</td>
											</tr>										
											<html-el:hidden property="user" value="${loginForm.user}"/>
										</html:form>
										</c:otherwise>
									</c:choose>
									
									<!-- Login para certificados -->
									<tr>
										<td colspan="2" width="82%" class="index">Si tiene certificado digital puede acceder a la aplicación con él desde aquí</td>
										<td class="index">&nbsp;&nbsp;<input type="button"	class="genericButton" value="&nbsp;Entrar&nbsp;" onclick="loginCert();" />
										</td>
									</tr>
									<!-- fin login para certificados -->
									
									<tr>
										<td colspan="3" class="message">
													   <logic:messagesPresent message="true">
														  <html-el:messages id="msg" message="true" bundle="${requestScope.bundle}">
															      <bean:write name="msg"/>
														  </html-el:messages>
														</logic:messagesPresent>
											<html:errors bundle="general_errors"  />
										</td>
									</tr>
									<tr>
										<td colspan="3" class="index" style="font-size: 7px;">
											&nbsp;
										</td>
									</tr>
									<tr>
										<!--<td colspan="3" class="index" style="font-size: 9px;">
											<bean:message key="message.login.copyright_1"/><img src="include/images/copyright.gif"> &nbsp;<bean:message key="message.login.copyright_2"/>
										</td>-->
									</tr>
								</table>
					
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>							
</body>

<script type="text/javascript">
	if(parent.frames.length)top.location=document.location;
</script>
</html:html>



<%--
<html:form action="/html/login" focus="user" onsubmit="return validateForm(this);" method="post" >
<table border="0" cellpadding="0" cellspacing="10">
		<tr>
			<td class="index" colspan="2"><br><bean:message key="message.login.fill"/><br>
			</td>
			<td align="center">&nbsp;
			</td>
		</tr>

		<tr>
			<td class="index"><bean:message key="message.login.name"/>:
			</td>
			<td  width="45%">	
				<html:text tabindex="1" property="user"/>									
			</td>
			<td>&nbsp;
				<html:submit tabindex="3" ><bean:message key="message.login.button.login"/></html:submit>
			</td>
		</tr>
		<tr>
			<td class="index"><bean:message key="message.login.pwd" />:
			</td>
			<td width="45%">	
				<html:password tabindex="2" property="pwd" redisplay="false"/>
				<html:hidden property="maskPwd" value=""/>
			</td>
			<td class="index">	&nbsp;
			</td>
		</tr>
		
		<tr>
			<td colspan="3" class="message"> 
   												   
			   <logic:messagesPresent message="true">
			  <html:messages id="msg" message="true">
			      <bean:write name="msg"/>
			  </html:messages>
			</logic:messagesPresent>

			</td>
		</tr>
		<tr>
			<td colspan="3" class="index" style="font-size: 7px;">
				&nbsp;
			</td>
		</tr>
		<tr>
			<td colspan="3" class="index" style="font-size: 9px;">
				<bean:message key="message.login.copyright_1"/><img src="include/images/copyright.gif"> &nbsp;<bean:message key="message.login.copyright_2"/>
			</td>
		</tr>
	</table>
</html:form>

</body>
</html:html>
--%>