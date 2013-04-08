<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html"%>
<%@ page isErrorPage="false" %>
<%@ page session="false"%>
<%@ page isThreadSafe="true" %>
<%@ page autoFlush="true" %>
<%@ page errorPage="__exception.jsp" %>

<%@ page import="com.ieci.tecdoc.isicres.usecase.security.SecurityUseCase" %>
<%@ page import="com.ieci.tecdoc.isicres.usecase.book.BookUseCase" %>
<%@ page import="com.ieci.tecdoc.isicres.usecase.UseCaseConf" %>
<%@ page import="com.ieci.tecdoc.idoc.flushfdr.FlushFdrField" %>

<%! SecurityUseCase securityUseCase = new SecurityUseCase(); %>
<%! BookUseCase bookUseCase = new BookUseCase(); %>
<%! FlushFdrField dataField = new FlushFdrField(); %>
<%! java.util.Map tranlations = new java.util.HashMap(); %>

<!--

http://localhost:8080/isicres/__test_buscar.jsp?1006=01-02-2005;18-02-2005&1005=..

-->

<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");

    UseCaseConf useCaseConf = new UseCaseConf(new java.util.Locale("ES","es"));

    securityUseCase.login(useCaseConf, "luismi", "l");

    out.println("Usuario sesion: " + useCaseConf.getSessionID());

    Integer bookID = new Integer(25);

%>

	<hr/>

<%
	bookUseCase.openBook(useCaseConf, bookID);
    tranlations = bookUseCase.translateQueryParams(useCaseConf, bookID, request);
    int size = bookUseCase.openTableResults(useCaseConf, bookID, request, tranlations, null);

    out.println("query realizada, size " + size);

    //bookUseCase.getTableResults(useCaseConf, bookID, com.ieci.tecdoc.common.isicres.Keys.QUERY_FIRST_PAGE);

    out.println("tabla obtenida ");

    bookUseCase.closeBook(useCaseConf, bookID);
    securityUseCase.logout(useCaseConf);
%>