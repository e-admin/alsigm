<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html"%>
<%@ page isErrorPage="false" %>
<%@ page session="true"%>
<%@ page isThreadSafe="true" %>
<%@ page autoFlush="true" %>
<%@ page errorPage="__exception.jsp" %>

<%@ page import="com.ieci.tecdoc.isicres.usecase.security.SecurityUseCase" %>
<%@ page import="com.ieci.tecdoc.isicres.usecase.book.BookUseCase" %>
<%@ page import="com.ieci.tecdoc.isicres.usecase.UseCaseConf" %>
<%@ page import="com.ieci.tecdoc.idoc.flushfdr.FlushFdrFile" %>
<%@ page import="com.ieci.tecdoc.idoc.flushfdr.FlushFdrDocument" %>
<%@ page import="com.ieci.tecdoc.idoc.flushfdr.FlushFdrPage" %>
<%@ page import="es.ieci.tecdoc.fwktd.core.config.web.ContextUtil" %>

<%! SecurityUseCase securityUseCase = new SecurityUseCase(); %>
<%! BookUseCase bookUseCase = new BookUseCase(); %>



<!--

http://localhost:8080/isicres/__test_file_complex.jsp

-->


<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");

    UseCaseConf useCaseConf = new UseCaseConf(new java.util.Locale("ES","es"));
    
    securityUseCase.login(useCaseConf, "luismi", "l");
    
    out.println("Usuario sesion: " + useCaseConf.getSessionID());
    

    Integer bookId = new Integer("25");
    
    String path = application.getRealPath("/");
    String pathTemp = application.getRealPath("/") + "tmp/";
    
    
    
%>

	<hr/>
    
<%

	FlushFdrFile fileUp = new FlushFdrFile(); 
	FlushFdrDocument documentUp = new FlushFdrDocument(); 
	FlushFdrPage pageUp = new FlushFdrPage(); 
	java.util.List filesInfo = new java.util.ArrayList(); 



    bookUseCase.getBooks(useCaseConf);
    bookUseCase.openBook(useCaseConf, bookId);
    int id = bookUseCase.saveOrUpdateFolder(useCaseConf, bookId, -1, new java.util.ArrayList(), new java.util.ArrayList(), new java.util.ArrayList(), new java.util.HashMap());
    
    out.println("Libro creado: " + id);
    
    
    
    fileUp.setOrder(new Integer("0").intValue());
    fileUp.setExtension("xsl");
    fileUp.setFileNameFis(ContextUtil.getRealPath(session.getServletContext(),"/xsl/dtrlist.xsl"));
    fileUp.setFileNameLog("file.xsl");
	filesInfo.add(fileUp);    
    
    out.println("fileup: " + fileUp.toString());
    
    documentUp.setTreeId("LI2000");
    documentUp.setDocumentName("Doc");
    documentUp.setFatherId("R0");
    documentUp.setFatherClassName("CL0");
    
    out.println("documentUp: " + documentUp.toString());
    
    pageUp.setTreeId("LI0");
    pageUp.setPageName("file.xsl");
    pageUp.setFatherId("LI2000");
    pageUp.setFatherClassName("CL2");
    
    pageUp.setFile(fileUp);
    pageUp.getFile().loadFile();
    
    out.println("pageUp: " + pageUp.toString());

    java.util.Map documents = new java.util.HashMap();
   	java.util.List resultPages = null;
    FlushFdrDocument docExist = null;
    String clavedocument = null;
    clavedocument = documentUp.getTreeId();
    resultPages = new java.util.ArrayList();
    resultPages.add(pageUp);

    documentUp.setPages(resultPages);
    documents.put(clavedocument, documentUp);
    
    id = bookUseCase.saveOrUpdateFolder(useCaseConf, bookId, id, filesInfo, new java.util.ArrayList(), new java.util.ArrayList(), documents);
    
    out.println("Fichero subido: " + id);
    
    
    bookUseCase.closeBook(useCaseConf, bookId);
    securityUseCase.logout(useCaseConf);
%>