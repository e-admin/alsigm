<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html"%>

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">

<%@ page import="ieci.tecdoc.sgm.registropresencial.utils.AuthenticationHelper" %>

<html>
<head>
</head>
<body>
<%
    out.write("<script>top.location.href='" + AuthenticationHelper.getWebAuthURL(request) + "';</script>");
out.flush();
%>
</body>