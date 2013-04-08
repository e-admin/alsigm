<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="relaciones" value="${requestScope[appConstants.transferencias.LISTA_RELACIONES_KEY]}"/>
<div class="contenedor_ficha">
	<div class="ficha">
		<h1><span>
			<div class="w100">
				<table class="w98" cellpadding=0 cellspacing=0>
					<tr>
				    	<td class="etiquetaAzul12Bold" height="25px">
				    		<bean:message key="archigest.archivo.transferencias.relaciones"/>
				    	</td>
				    	<td align="right">
							<table cellpadding=0 cellspacing=0>
								<tr>
								   	<td nowrap="true">
										<tiles:insert definition="button.closeButton" flush="true" />
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
				
				<c:set var="udocs" value="${unidadInstalacion.contenido}" />
				<c:if test="${!empty udocs}">
					<TABLE cellpadding=0 cellspacing=0 class="w100">
					  <TR>
					   <TD align="right">
							<a class="etiquetaAzul12Normal" href="javascript:seleccionarCheckboxSet(document.forms[0].udocSeleccionada);" >
								<html:img page="/pages/images/checked.gif" border="0" altKey="archigest.archivo.selTodos" titleKey="archigest.archivo.selTodos" styleClass="imgTextBottom" />
								<bean:message key="archigest.archivo.selTodos"/>&nbsp;
							</a>&nbsp;
							<a class=etiquetaAzul12Normal href="javascript:deseleccionarCheckboxSet(document.forms[0].udocSeleccionada);" >
								<html:img page="/pages/images/check.gif" border="0" altKey="archigest.archivo.quitarSel" titleKey="archigest.archivo.quitarSel" styleClass="imgTextBottom" />
								&nbsp;<bean:message key="archigest.archivo.quitarSel"/>
							</a>&nbsp;&nbsp;
					   </TD>
					 </TR>
					</TABLE>
				</c:if>
				<div class="separador5">&nbsp;</div>
				
				
				<display:table name="pageScope.relaciones"
					style="width:99%;margin-left:auto;margin-right:auto"
					id="relacion" 
					export="false" excludedParams="method">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.transferencias.noRelsElemento"/>
					</display:setProperty>
					<display:column titleKey="archigest.archivo.transferencias.fEstado" property="fechaestado" decorator="common.view.DateDecorator" />
					<display:column titleKey="archigest.archivo.relacion" headerClass="sortable" style="white-space: nowrap;">
						<c:url var="verRelacionURL" value="/action/gestionRelaciones">
							<c:param name="method" value="verrelacion" />
							<c:param name="idrelacionseleccionada" value="${relacion.id}" />
						</c:url>
						<a class="tdlink" href="<c:out value="${verRelacionURL}" escapeXml="false"/>" target="_self">
						<c:out value="${relacion.codigoTransferencia}"/>
						</a>
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.tipoTransf" sortProperty="tipoTransferencia">
						<c:set var="keyTitulo">
							archigest.archivo.transferencias.tipooperacion<c:out value="${relacion.tipooperacion}"/>
						</c:set>
						<fmt:message key="${keyTitulo}" />		
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.procedimiento" sortProperty="procedimiento">
						<c:out value="${relacion.idprocedimiento}"/>&nbsp;<c:out value="${relacion.procedimiento.nombre}"/>
					</display:column>
					<display:column titleKey="archigest.archivo.transferencias.serie" sortProperty="serie">
						<c:out value="${relacion.codigoserie}"/>&nbsp;
						<c:out value="${relacion.tituloserie}"/>
					</display:column>
				</display:table> 
				<div class="separador5">&nbsp;</div> 
			 </div>

		</div> <%--cuerpo_izda --%>
		</div> <%--cuerpo_drcha --%>
		
		<h2><span>&nbsp;</span></h2>
	</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>
