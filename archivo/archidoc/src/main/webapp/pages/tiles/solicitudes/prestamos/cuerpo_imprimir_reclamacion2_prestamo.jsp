<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>

<%@ page import="solicitudes.vos.MotivoRechazoVO" %>
<script src="<%=request.getContextPath()%>/js/sigia_form.js" type="text/JavaScript"></script>

<c:set var="bPrestamo" value="${sessionScope[appConstants.prestamos.PRESTAMO_KEY]}"/>
	
<c:forEach var="item" items="${sessionScope[appConstants.prestamos.DETALLE_PRESTAMO_KEY]}">
   
   <h3>5. <bean:message key="archigest.archivo.consultas.2reclamacionUDoc"/> </h3><br>
    <b><bean:message key="archigest.archivo.transferencias.signatura"/>:</b> <c:out value="${item.signaturaudoc}" />
    <br>
    <b><bean:message key="archigest.archivo.consultas.nPrestamo"/>:</b> <c:out value="${sessionScope[appConstants.prestamos.PRESTAMO_KEY].codigoTransferencia}"/>
   	         
    <b><bean:message key="archigest.archivo.cf.fechaSolicitud" />:</b>   
    <fmt:formatDate value="${bPrestamo.fentrega}" pattern="${appConstants.common.FORMATO_FECHA}" />
   
    <b><bean:message key="archigest.archivo.solicitudes.fdevolucion"/>:</b>  
    <fmt:formatDate value="${bPrestamo.fmaxfinprestamo}" pattern="${appConstants.common.FORMATO_FECHA}" />
  
    <br>
    <b><bean:message key="archigest.archivo.consultas.solicitante" />:</b><c:out value="${bPrestamo.nusrsolicitante}" /> 
    <br>
    <b><bean:message key="archigest.archivo.cf.productorTipoOrganismo" />:</b><c:out value="${bPrestamo.norgsolicitante}" /> 
    <br>
    <b><bean:message key="archigest.archivo.prestamos.expedienteudoc" />:</b> <c:out value="${item.expedienteudoc}" />
    <p style="page-break-before: always"> 
    
</c:forEach> 

<script> window.print(); </script>