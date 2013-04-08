<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<script src="<%=request.getContextPath()%>/js/sigia_form.js" type="text/JavaScript"></script>
<%@ page import="solicitudes.consultas.vos.TemaVO" %>
<%@ page import="solicitudes.vos.MotivoRechazoVO" %>

<c:set var="bConsulta" value="${sessionScope[appConstants.consultas.CONSULTA_KEY]}"/>

<h3>3. <bean:message key="archigest.archivo.consultas.entradaEnDeposito"/></h3>	
<c:forEach var="item" items="${sessionScope[appConstants.consultas.DETALLE_CONSULTA_KEY]}">
    <b><bean:message key="archigest.archivo.transferencias.signatura"/>:</b> <c:out value="${item.signaturaudoc}" />
    <br>
    <b><bean:message key="archigest.archivo.consultas.nConsulta"/>:</b> <c:out value="${sessionScope[appConstants.consultas.CONSULTA_KEY].codigoTransferencia}"/>	
   	         
    <b><bean:message key="archigest.archivo.cf.fechaSolicitud" />:</b>   
    <fmt:formatDate value="${bConsulta.fentrega}" pattern="${appConstants.common.FORMATO_FECHA}" />
    <b><bean:message key="archigest.archivo.solicitudes.fdevolucion"/>:</b>  
    <fmt:formatDate value="${bConsulta.fmaxfinconsulta}" pattern="${appConstants.common.FORMATO_FECHA}" />
    <br>
    <b><bean:message key="archigest.archivo.consultas.solicitante" />:</b><c:out value="${bConsulta.nusrconsultor}" /> 
    <br>
    <b><bean:message key="archigest.archivo.cf.productorTipoOrganismo" />:</b><c:out value="${bConsulta.norgconsultor}" /> 
    <br>
    <b><bean:message key="archigest.archivo.prestamos.expedienteudoc" />:</b> <c:out value="${item.expedienteudoc}" />
    <br>
    <b><bean:message key="archigest.archivo.ubicacion" />:</b> <c:out value="${item.ubicacion}" />    
    <p style="page-break-before: always"> 
</c:forEach> 

<br>
<h3>4. <bean:message key="archigest.archivo.consultas.entradaEnDeposito"/>: <bean:message key="archigest.archivo.consultas.listaUnidadesDevueltas"/></h3>
<c:forEach var="item" items="${sessionScope[appConstants.consultas.DETALLE_CONSULTA_KEY]}">
    <b><bean:message key="archigest.archivo.transferencias.signatura"/>:</b> <c:out value="${item.signaturaudoc}" />
    <br>
    <b><bean:message key="archigest.archivo.consultas.nConsulta"/>:</b> <c:out value="${sessionScope[appConstants.consultas.CONSULTA_KEY].codigoTransferencia}"/>	
   	         
    <b><bean:message key="archigest.archivo.cf.fechaSolicitud" />:</b>   
    <fmt:formatDate value="${bConsulta.fentrega}" pattern="${appConstants.common.FORMATO_FECHA}" />
    <b><bean:message key="archigest.archivo.solicitudes.fdevolucion"/>:</b>  
    <fmt:formatDate value="${bConsulta.fmaxfinconsulta}" pattern="${appConstants.common.FORMATO_FECHA}" />
    <br>
    <b><bean:message key="archigest.archivo.consultas.solicitante" />:</b><c:out value="${bConsulta.nusrconsultor}" /> 
    <br>
    <b><bean:message key="archigest.archivo.cf.productorTipoOrganismo" />:</b><c:out value="${bConsulta.norgconsultor}" /> 
    <br>
    <b><bean:message key="archigest.archivo.prestamos.expedienteudoc" />:</b> <c:out value="${item.expedienteudoc}" />
    <br><br>
</c:forEach> 

<script> window.print(); </script>