<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html"%>
<%@ page isErrorPage="false" %>
<%@ page session="false"%>
<%@ page isThreadSafe="true" %>
<%@ page autoFlush="true" %>
<%@ page errorPage="__exception.jsp" %>


<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">

<%@ page import="com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils" %>
<%@ page import="com.ieci.tecdoc.isicres.desktopweb.Keys" %>
<%@ page import="com.ieci.tecdoc.isicres.usecase.UseCaseConf" %>
<%@ page import="com.ieci.tecdoc.isicres.usecase.security.SecurityUseCase" %>
<%@ page import="com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils" %>
<%@ page import="com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil" %>
<%@ page import="com.ieci.tecdoc.common.exception.SecurityException" %>
<%@ page import="com.ieci.tecdoc.common.exception.ValidationException" %>
<%@ page import="org.apache.log4j.*"%>

<%!
Logger _logger = Logger.getLogger(getClass());
%>

<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");

	String oldPassword = RequestUtils.parseRequestParameterAsString(request, "PasswordCtrl");;
	String newPassword = RequestUtils.parseRequestParameterAsString(request, "NewPwdCtrl");;
	String name = RequestUtils.parseRequestParameterAsString(request, "NameCtrlP");;
	String error = "0";

		UseCaseConf useCaseConf = (UseCaseConf)request.getSession().getAttribute(Keys.J_USECASECONF);
		SecurityUseCase securityUseCase = new SecurityUseCase();
		try {
			securityUseCase.changePassword(useCaseConf, name, oldPassword, newPassword);
		} catch (SecurityException sE) {
			error = "1";	
			_logger.fatal("Error de seguridad",sE);
			ResponseUtils.generateJavaScriptError(out, sE);
			if (sE.getCode().equals(SecurityException.ERROR_PASSWORD_INCORRECT)) {
			    _logger.fatal("Password Incorrecta",sE);
			    error="2";
			}
		} catch (ValidationException vE) {	
			_logger.fatal("Error de validacion",vE);
			error = "1";		
			ResponseUtils.generateJavaScriptLog(out, RBUtil.getInstance(request.getLocale()).getProperty(Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
		} catch (Exception vE) {	
			_logger.fatal("Error en las comunicaciones",vE);
			error = "1";		
			ResponseUtils.generateJavaScriptLog(out, RBUtil.getInstance(request.getLocale()).getProperty(Keys.I18N_EXCEPTION_REMOTEEXCEPTION));
		}
	
    	out.write("<script language=javascript>");
    	out.write("top.Main.WorkspaceLog.ChangePwdWnd.ProcError(\"" + error + "\");");
    	out.write("</script>");
%>
