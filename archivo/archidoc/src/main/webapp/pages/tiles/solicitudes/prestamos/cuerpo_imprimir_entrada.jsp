<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>

<%@ page import="solicitudes.vos.MotivoRechazoVO" %>
<script src="<%=request.getContextPath()%>/js/sigia_form.js" type="text/JavaScript"></script>

<h3>4. <bean:message key="archigest.archivo.consultas.entradaEnDeposito"/></h3>
<c:forEach var="item" items="${sessionScope[appConstants.prestamos.DETALLE_PRESTAMO_KEY]}">
    <b><bean:message key="archigest.archivo.transferencias.signatura"/>:</b> <c:out value="${item.signaturaudoc}" />
    <b><bean:message key="archigest.archivo.transferencias.titulo"/>:</b> <c:out value="${item.titulo}" />
    <br>
    <c:if test="${item.tiposolicitud==1}"><b><bean:message key="archigest.archivo.consultas.nPrestamo"/>:</b></c:if>
    <c:if test="${item.tiposolicitud==2}"><b><bean:message key="archigest.archivo.consultas.nConsulta"/>:</b></c:if>
    <c:out value="${item.codigoSolicitud}"/>
    <b><bean:message key="archigest.archivo.prestamos.fentrega" />:</b>   
    <fmt:formatDate value="${item.fentrega}" pattern="${appConstants.common.FORMATO_FECHA}" />
    <b><bean:message key="archigest.archivo.solicitudes.fdevolucion"/>:</b>  
    <fmt:formatDate value="${item.fmaxfinprestamo}" pattern="${appConstants.common.FORMATO_FECHA}" />
    <br>
    <b><bean:message key="archigest.archivo.consultas.solicitante" />:</b><c:out value="${item.nusrsolicitante}" /> 
    <br>
    <b><bean:message key="archigest.archivo.cf.productorTipoOrganismo" />:</b><c:out value="${item.norgsolicitante}" /> 
    <br>
    <b><bean:message key="archigest.archivo.prestamos.expedienteudoc" />:</b> <c:out value="${item.expedienteudoc}" />
    <br>
    <b><bean:message key="archigest.archivo.ubicacion" />:</b> <c:out value="${item.ubicacion}" />
    <p style="page-break-before: always"> 
</c:forEach> 

<h3>4. <bean:message key="archigest.archivo.consultas.entradaEnDeposito"/>: <bean:message key="archigest.archivo.consultas.listaUnidadesDevueltas"/></h3>
<c:forEach var="item" items="${sessionScope[appConstants.prestamos.DETALLE_PRESTAMO_KEY]}">
    <b><bean:message key="archigest.archivo.transferencias.signatura"/>:</b> <c:out value="${item.signaturaudoc}" />
    <b><bean:message key="archigest.archivo.transferencias.titulo"/>:</b> <c:out value="${item.titulo}" />
    <br>
    <c:if test="${item.tiposolicitud==1}"><b><bean:message key="archigest.archivo.consultas.nPrestamo"/>:</b></c:if>
    <c:if test="${item.tiposolicitud==2}"><b><bean:message key="archigest.archivo.consultas.nConsulta"/>:</b></c:if>
    <c:out value="${item.codigoSolicitud}"/>
    <b><bean:message key="archigest.archivo.prestamos.fentrega" />:</b>   
    <fmt:formatDate value="${item.fentrega}" pattern="${appConstants.common.FORMATO_FECHA}" />
    <b><bean:message key="archigest.archivo.solicitudes.fdevolucion"/>:</b>  
    <fmt:formatDate value="${item.fmaxfinprestamo}" pattern="${appConstants.common.FORMATO_FECHA}" />
    <br>
    <b><bean:message key="archigest.archivo.consultas.solicitante" />:</b><c:out value="${item.nusrsolicitante}" /> 
    <br>
    <b><bean:message key="archigest.archivo.cf.productorTipoOrganismo" />:</b><c:out value="${item.norgsolicitante}" /> 
    <br>
    <b><bean:message key="archigest.archivo.prestamos.expedienteudoc" />:</b> <c:out value="${item.expedienteudoc}" />
    <br><br>
</c:forEach> 
<h5>&nbsp;&nbsp;&nbsp;<bean:message key="archigest.archivo.deposito.electronico.usoFirma"/>:</h5>
<br><br><br>
<script> window.print(); </script>