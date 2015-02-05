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
<%! java.util.List listField = new java.util.ArrayList(); %>

<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");

    UseCaseConf useCaseConf = new UseCaseConf(new java.util.Locale("ES","es"));
    
    securityUseCase.login(useCaseConf, "luismi", "l");
    
    out.println("Usuario sesion: " + useCaseConf.getSessionID());
    
    //Integer bookId = new Integer(request.getParameter("book"));
    Integer bookId = new Integer("25");

%>

	<hr/>
    
<%
    dataField.setFldid(7);
    dataField.setValue("D384");
    dataField.setCtrlid(1014);
    
    out.println(" " + dataField + " ");
    
    listField.add(dataField);
    
    dataField = new FlushFdrField();
    dataField.setFldid(8);
    dataField.setValue("COD_DESTINO");
    dataField.setCtrlid(1016);
    
    out.println(" " + dataField + " ");
    
    listField.add(dataField);

    dataField = new FlushFdrField();
    dataField.setFldid(16);
    dataField.setValue("DFCDSF");
    dataField.setCtrlid(1048);
    
    out.println(" " + dataField + " ");
    
    listField.add(dataField);
    
    
    bookUseCase.getBooks(useCaseConf);
    bookUseCase.openBook(useCaseConf, bookId);
    int id = bookUseCase.saveOrUpdateFolder(useCaseConf, bookId, -1, new java.util.ArrayList(), listField, new java.util.ArrayList(), new java.util.HashMap());
    
    out.println("Libro creado: " + id);
    
    bookUseCase.closeBook(useCaseConf, bookId);
    securityUseCase.logout(useCaseConf);
%>