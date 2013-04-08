<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="previsiones" value="${requestScope[appConstants.transferencias.LISTA_PREVISIONES_KEY]}"/>
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>

<div id="contenedor_ficha">
	<div class="ficha">
		<h1><span>
		<div class="w100">
	        <table class="w98" cellpadding="0" cellspacing="0">
	          <tr>
	            <td class="etiquetaAzul12Bold" height="25px">
	            	<bean:message key="archigest.archivo.transferencias.previsiones"/>
	            </td>
	            <td align="right">
	              <table cellpadding="0" cellspacing="0">
	                <tr>
						<td><html:link styleClass="etiquetaAzul12Bold" action="buscarPrevisiones?method=goBack">
	                    	<html:img page="/pages/images/close.gif" 
	                              altKey="archigest.archivo.cerrar" 
	                              titleKey="archigest.archivo.cerrar" 
	                              styleClass="imgTextMiddle" />&nbsp;
	                              <bean:message key="archigest.archivo.cerrar"/>
							</html:link>
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
				<display:table name="pageScope.previsiones"
					style="width:99%;margin-left:auto;margin-right:auto"
					id="prevision" 
					pagesize="15"
					sort="list"			
					requestURI="/action/buscarPrevisiones"
					export="true">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.transferencias.previsiones.listado.vacio"/>
					</display:setProperty>

					<display:column titleKey="archigest.archivo.transferencias.previsiones.busqueda.codigo" sortProperty="codigo" sortable="true" headerClass="sortable" media="html">
						<c:url var="verURL" value="/action/gestionPrevisiones">
							<c:param name="method" value="verprevision" />
							<c:param name="idprevision" value="${prevision.id}" />
						</c:url>
						<a class="tdlink" href="<c:out value="${verURL}" escapeXml="false"/>" >
							<c:out value="${prevision.codigoTransferencia}"/>
						</a>
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.previsiones.busqueda.codigo" media="csv excel xml pdf">
						<c:out value="${prevision.codigoTransferencia}"/>
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.previsiones.busqueda.tipo" sortProperty="tipoTransferencia" sortable="true" headerClass="sortable">
						<c:set var="keyTitulo">
							archigest.archivo.transferencias.tipotrans<c:out value="${prevision.tipotransferencia}"/>
						</c:set>
						<fmt:message key="${keyTitulo}" />
					</display:column>
					<display:column titleKey="archigest.archivo.fInicio" sortProperty="fechainitrans" sortable="true" headerClass="sortable">
						<fmt:formatDate value="${prevision.fechainitrans}" pattern="${FORMATO_FECHA}" />
					</display:column>
					<display:column titleKey="archigest.archivo.fFin" sortProperty="fechafintrans" sortable="true" headerClass="sortable">
						<fmt:formatDate value="${prevision.fechafintrans}" pattern="${FORMATO_FECHA}" />
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.previsiones.busqueda.usuario" property="nombreCompletoUsuario" sortProperty="usuario" sortable="true" headerClass="sortable"/>
					<display:column titleKey="archigest.archivo.transferencias.previsiones.busqueda.organo" property="nombreOrgano" sortProperty="organo" sortable="true" headerClass="sortable"/>
					<display:column titleKey="archigest.archivo.transferencias.estado" sortProperty="estado" sortable="true" headerClass="sortable">
						<c:set var="keyTituloEstado">
							archigest.archivo.transferencias.estadoPrevision.<c:out value="${prevision.estado}"/>
						</c:set>
						<fmt:message key="${keyTituloEstado}" />
					</display:column>
				</display:table> 
			</div> <%--bloque --%>
		
		</div> <%--cuerpo_izda --%>
		</div> <%--cuerpo_drcha --%>
		
		<h2><span>&nbsp;</span></h2>
	
	</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>
