<!--
JSP para la salida de la aplicación.
Esta JSP siempre invalida la sesión actual.
Redirecciona a la página de entrada a la aplicación.
-->


<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html"%>
<%@ page isErrorPage="false"%>
<%@ page session="true"%>
<%@ page isThreadSafe="true"%>
<%@ page autoFlush="true"%>
<%@ page errorPage="__exception.jsp"%>


<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">

<%@ page import="com.ieci.tecdoc.common.keys.ConfigurationKeys"%>
<%@ page import="com.ieci.tecdoc.isicres.desktopweb.Keys"%>
<%@ page import="com.ieci.tecdoc.isicres.usecase.UseCaseConf"%>
<%@ page import="com.ieci.tecdoc.isicres.usecase.security.SecurityUseCase"%>
<%@ page import="com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils"%>
<%@page import="com.ieci.tecdoc.common.utils.Configurator"%>
<%@ page import="com.ieci.tecdoc.isicres.servlets.core.LoginFactory" %>

<%@ include file="__headerLocale.jsp"%>


<%@page import="com.ieci.tecdoc.isicres.servlets.core.LogoutJspHelper"%><html>
<head>
</head>
<body>
<%
	LogoutJspHelper logoutHelper= LoginFactory.getLogoutJspHelperInstance();
	logoutHelper.doWork(request,response);
%>
</body>