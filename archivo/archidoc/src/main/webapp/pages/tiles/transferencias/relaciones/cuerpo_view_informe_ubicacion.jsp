<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="idRelacion" value="${param['idRelacion']}"/>
<c:set var="ubicaciones" value="${requestScope[appConstants.transferencias.LISTA_UBICACIONES]}"/>

<div id="contenedor_ficha">

	<div class="ficha">
		<h1><span>
			<div class="w100">
				<table class="w98" cellpadding=0 cellspacing=0>
					<tr>
				    	<td class="etiquetaAzul12Bold" height="25px">
				    		<bean:message key="archigest.archivo.transferencias.informeUbicacion"/>
				    	</td>
				    	<td align="right">
							<table cellpadding=0 cellspacing=0>
								<tr>
								   	<td>
										<c:url var="URL" value="/action/informeUbicacion">
											<c:param name="idRelacion" value="${idRelacion}" />
								   		</c:url>
										<a class="etiquetaAzul12Bold" 
										   href="<c:out value="${URL}" escapeXml="false"/>"
										><html:img page="/pages/images/documentos/doc_pdf.gif" 
										        border="0" 
										        altKey="archigest.archivo.informe"
										        titleKey="archigest.archivo.informe"
										        styleClass="imgTextBottom"/>&nbsp;<bean:message key="archigest.archivo.informe"/></a>
									</td>
									<td width="10">&nbsp;</td>
								   	<td nowrap="nowrap">
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

			<div class="cabecero_bloque">
				<table class="w98m1" cellpadding="0" cellspacing="0" height="100%">
					<tbody>
						<tr>
							<td class="etiquetaAzul12Bold">
								<bean:message key="archigest.archivo.transferencias.ubicaciones"/>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		
			<div class="bloque">
				<display:table name="pageScope.ubicaciones"
					style="width:99%;margin-left:auto;margin-right:auto"
					id="ubicacion" 
					pagesize="15"
					requestURI='<%=request.getContextPath()+"/action/gestionRelaciones?method=verInformeUbicacion"%>'
					export="false">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.transferencias.ubicaciones.vacias"/>
					</display:setProperty>
					<display:column titleKey="archigest.archivo.transferencias.signatura" property="signaturaUI" sortable="true" headerClass="sortable"/>
					<display:column titleKey="archigest.archivo.ubicacion" property="path" sortable="true" headerClass="sortable">
					</display:column>
				</display:table>  
			 </div>
		
			<div class="separador5">&nbsp;</div>
	
		</div> <%--cuerpo_izda --%>
		</div> <%--cuerpo_drcha --%>
		
		<h2><span>&nbsp;</span></h2>
	</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>
