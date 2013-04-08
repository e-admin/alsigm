<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ page import="auditoria.AuditoriaConstants" %>
<%@ page import="common.definitions.ArchivoActions" %>
<%@ page import="common.definitions.ArchivoModules" %>
<%@ page import="auditoria.vos.TrazaVO" %>

<c:set var="pistas" value="${requestScope[appConstants.auditoria.LISTA_PISTAS_KEY]}"/>
<c:set var="FORMATO_FECHA_DETALLADA" value="${appConstants.common.FORMATO_FECHA_DETALLADA}"/>

<div id="contenedor_ficha">
  <div class="ficha">
    <h1><span>
      <div class="w100">
        <table class="w98" cellpadding="0" cellspacing="0">
          <tr>
            <td class="etiquetaAzul12Bold" height="25px"><bean:message key="archigest.archivo.auditoria.listado"/></td>
            <td align="right">
              <table cellpadding="0" cellspacing="0">
                <tr>
                  <td>
					<c:url var="cancelURL" value="/action/auditoriaBuscar">
						<c:param name="method" value="goBack" />
					</c:url>
					<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${cancelURL}" escapeXml="false" />'">
						<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.cerrar"/>
					</a>
				  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </div>
    </span></h1>
    
    <div class="cuerpo_drcha">
	  <div class="cuerpo_izda">
		<div id="barra_errores"><archivo:errors /></div>
	   	<div class="separador1">&nbsp;</div>

		<div class="bloque">
			<display:table name="pageScope.pistas"
				style="width:99%;margin-left:auto;margin-right:auto"
				id="pista" 
				pagesize="15"
				sort="list"
				defaultsort="2"
				requestURI='<%=request.getContextPath()+"/action/" + request.getAttribute(AuditoriaConstants.ACTION)%>'
				export="true"
				defaultorder="descending">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.auditoria.busqueda.vacia"/>
				</display:setProperty>

				<display:column titleKey="archigest.archivo.modulo" sortable="true" headerClass="sortable">
					<c:set var="moduloKeyName"><%=ArchivoModules.getModuleName(((TrazaVO)pista).getModulo())%></c:set>
					<fmt:message key="${moduloKeyName}"/>
				</display:column>

				<display:column titleKey="archigest.archivo.auditoria.accion" sortable="true" headerClass="sortable" media="html">
					<c:set var="accionKeyName"><%=ArchivoActions.getActionName(((TrazaVO)pista).getAccion())%></c:set>
					<c:url var="detalleURL" value="/action/auditoriaBuscar">
						<c:param name="method" value="detail" />
						<c:param name="id" value="${pista.id}" />
					</c:url>
					<a class="tdlink" href="javascript:window.location='<c:out value="${detalleURL}" escapeXml="false" />'">
						<fmt:message key="${accionKeyName}"/>
					</a>
				</display:column>

				<display:column titleKey="archigest.archivo.auditoria.accion" media="csv excel xml pdf">
					<c:set var="accionKeyName"><%=ArchivoActions.getActionName(((TrazaVO)pista).getAccion())%></c:set>
					<fmt:message key="${accionKeyName}"/>
				</display:column>
				
				<display:column titleKey="archigest.archivo.fecha" sortProperty="timeStamp" sortable="true" headerClass="sortable" >
					<fmt:formatDate value="${pista.timeStamp}" pattern="${FORMATO_FECHA_DETALLADA}" />
				</display:column>

				<display:column titleKey="archigest.archivo.auditoria.ip" property="dirIP" sortable="true" headerClass="sortable"/>
				<display:column titleKey="archigest.archivo.usuario" property="usuario" sortable="true" headerClass="sortable"/>
			</display:table>  
		  </div>

	    </div>
	  </div>
    <h2><span>&nbsp;</span></h2>
  </div>
</div>