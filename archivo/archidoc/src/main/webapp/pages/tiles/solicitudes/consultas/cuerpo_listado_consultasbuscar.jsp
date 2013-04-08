<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ page import="common.Constants" %>
<%@ page import="solicitudes.consultas.ConsultasConstants" %>

<% try {%>

<script src="<%=request.getContextPath()%>/js/sigia_form.js" type="text/JavaScript"></script>
<div id="contenedor_ficha">

<html:form action="/gestionConsultas">
<html:hidden property="method"/>
	
<div class="ficha">

	<h1><span>
	<div class="w100">
        <table class="w98" cellpadding="0" cellspacing="0">
          <tr>
            <td class="etiquetaAzul12Bold" height="25px"><bean:message key="archigest.archivo.consultas.consultas"/></td>
            <td align="right">
              <table cellpadding="0" cellspacing="0">
                <tr>
                  <td><html:link styleClass="etiquetaAzul12Bold" action="buscarConsultas?method=goBack">
                      <html:img page="/pages/images/close.gif" 
                              altKey="archigest.archivo.cerrar" 
                              titleKey="archigest.archivo.cerrar" 
                              styleClass="imgTextTop" />
						&nbsp;<bean:message key="archigest.archivo.cerrar"/></html:link></td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </div>
	</span></h1>
	
<div class="cuerpo_drcha">
<div class="cuerpo_izda">
	
	<DIV id="barra_errores"><archivo:errors /></DIV>

	<span class="separador1"></span>
	<DIV class="bloque">
		<c:set var="LISTA_NAME" value="requestScope.${appConstants.consultas.LISTA_CONSULTAS_KEY}"/>
		<jsp:useBean id="LISTA_NAME" type="java.lang.String" />
		<display:table name="<%=LISTA_NAME%>"
			style="width:99%;margin-left:auto;margin-right:auto"
			id="listaRegistros" 
			decorator="solicitudes.consultas.decorators.ViewConsultaDecorator"
			pagesize="15"
			sort="list"			
			requestURI="../../action/buscarConsultas"
			export="true">
			<display:setProperty name="basic.msg.empty_list">
				<bean:message key="archigest.archivo.consultas.noPrev"/>
			</display:setProperty>
		
			<display:column titleKey="archigest.archivo.consultas.consulta" sortProperty="codigo" sortable="true" headerClass="sortable" media="html">
				<c:url var="URL" value="/action/gestionConsultas">
					<c:param name="method" value="verconsulta" />
					<c:param name="id" value="${listaRegistros.id}" />
				</c:url>
				<a class="tdlink" href='<c:out value="${URL}" escapeXml="false" />' >
					<c:out value="${listaRegistros.codigoTransferencia}"/>
				</a>
			</display:column> 
			<display:column titleKey="archigest.archivo.consultas.consulta" media="csv excel xml pdf">
				<c:out value="${listaRegistros.codigoTransferencia}"/>
			</display:column> 
			<display:column titleKey="archigest.archivo.consultas.norgconsultor" sortable="true" headerClass="sortable" property="norgconsultor" decorator="solicitudes.consultas.decorators.ConsultaDecorator" media="html"/>
			<display:column titleKey="archigest.archivo.consultas.norgconsultor" media="csv excel xml pdf">
				<c:out value="${listaRegistros.norgconsultor}"/>
			</display:column> 
			<display:column titleKey="archigest.archivo.consultas.idusrsolicitante" sortable="true" headerClass="sortable" property="nusrconsultor" decorator="solicitudes.consultas.decorators.ConsultaDecorator" media="html"/>
			<display:column titleKey="archigest.archivo.consultas.idusrsolicitante" media="csv excel xml pdf">
				<c:out value="${listaRegistros.nusrconsultor}"/>
			</display:column> 
			<display:column titleKey="archigest.archivo.consultas.estado" sortable="true" headerClass="sortable" property="estado" decorator="solicitudes.consultas.decorators.ConsultaDecorator" media="html"/>
			<display:column titleKey="archigest.archivo.consultas.estado" media="csv excel xml pdf">
				<fmt:message key="archigest.archivo.solicitudes.consulta.estado.${listaRegistros.estado}"/>
			</display:column> 
			<display:column titleKey="archigest.archivo.consultas.festado" sortable="true" headerClass="sortable" property="festado" decorator="solicitudes.consultas.decorators.ConsultaDecorator" media="html"/>
			<display:column titleKey="archigest.archivo.consultas.festado" sortable="true" headerClass="sortable" media="csv excel xml pdf">
				<fmt:formatDate value="${listaRegistros.festado}" pattern="${appConstants.common.FORMATO_FECHA}" />
			</display:column> 
		</display:table> 
	</DIV> <%--bloque --%>

</div> <%--cuerpo_izda --%>
</div> <%--cuerpo_drcha --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>

</html:form>
<% } catch (Throwable t) { t.printStackTrace(); } %>
