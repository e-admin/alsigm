<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/calendarlibtag.tld" prefix="calendar" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>

<c:set var="beanMes" value="${sessionScope[appConstants.transferencias.CALENDAR_MES_KEY]}"/>
<c:set var="nombreMes" value="${beanMes[appConstants.transferencias.NOMBRE_MES_KEY]}"/>
<c:set var="indexMes" value="${beanMes[appConstants.transferencias.INDEX_MES_KEY]}"/>
<c:set var="numPrevisiones" value="${beanMes[appConstants.transferencias.NUM_PREVISIONES_MES_KEY]}"/>
<c:set var="numUnidades" value="${beanMes[appConstants.transferencias.NUM_UNIDADES_INSTALACION_MES_KEY]}"/>
<c:set var="posiblesDias" value="${beanMes[appConstants.transferencias.NUM_DIAS_OCUPADOS_MES_KEY]}"/>
<c:set var="previsionesMes" value="${beanMes[appConstants.transferencias.PREVISIONES_MES_KEY]}"/>

<div id="contenedor_ficha">

	<div class="ficha">

		<h1>
			<span>
				<div class="w100">
					<table class="w98" cellpadding=0 cellspacing=0>
						<tr>
					    	<td class="etiquetaAzul12Bold" height="25px">
					    		<bean:message key="archigest.archivo.calendario.previsiones"/>
				    		</td>
					    	<td align="right">
								<table cellpadding=0 cellspacing=0>
									<tr>
										<TD width="10">&nbsp;</TD>
										<td>
											<c:url var="volverURL" value="/action/recepcionPrevisiones">
												<c:param name="method" value="goBack" />
											</c:url>
											<a class="etiquetaAzul12Bold" href="<c:out value="${volverURL}" escapeXml="false"/>" >
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
			</span>
		</h1>
		
		<div class="cuerpo_drcha">
			<div class="cuerpo_izda">
					
				<div id="barra_errores"><archivo:errors /></div>

				<div class="bloque">
					<table class="formulario">
						<tr>
							<td class="tdTituloFicha" width="250"><bean:message key="archigest.archivo.mes"/>:&nbsp;</td>
							<td class="tdTitulo">
								<c:out value="${nombreMes}"/>
							</td>
						</tr>
						<tr>
							<td class="tdTituloFicha" width="250"><bean:message key="archigest.archivo.calendar.numero.previsiones"/>:&nbsp;</td>
							<td class="tdTitulo">
								<c:out value="${numPrevisiones}"/>
							</td>
						</tr>
						<tr>
							<td class="tdTituloFicha" width="250"><bean:message key="archigest.archivo.calendar.numero.unidades"/>:&nbsp;</td>
							<td class="tdTitulo">
								<c:out value="${numUnidades}"/>
							</td>
						</tr>
						<tr>
							<td class="tdTituloFicha" width="250"><bean:message key="archigest.archivo.calendar.dias.posibles.ocupados"/>:&nbsp;</td>
							<td class="tdTitulo">
								<c:out value="${posiblesDias}"/>
							</td>
						</tr>
						<tr>
							<td class="tdTituloFichaSinBorde" width="250"><bean:message key="archigest.archivo.calendar.previsiones"/>:&nbsp;</td>
						</tr>																									
					</table>
					
					<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
					
					<c:url var="calendarioPrevisionesMesURI" value="/action/recepcionPrevisiones">
						<c:param name="method" value="mostrarMes" />
						<c:param name="mes" value="${indexMes}" />						
					</c:url>
					<jsp:useBean id="calendarioPrevisionesMesURI" type="java.lang.String" />
	
	
	
					<div class="bloqueConScroll" id="capaConScroll">
						<display:table name="pageScope.previsionesMes"
							style="width:99%;margin-left:auto;margin-right:auto"
							id="prevision" 
							decorator="transferencias.decorators.ViewPrevisionDecorator"
							sort="page"
							defaultsort="1"
							requestURI="<%=calendarioPrevisionesMesURI%>"
							export="true"
							excludedParams="method">
													
						  	<display:column titleKey="archigest.archivo.transferencias.prevision" sortable="true" headerClass="sortable" media="html">
								<c:url var="verPrevisionURL" value="/action/gestionPrevisiones">
									<c:param name="method" value="verprevisionConsultaCalendarioPrevisiones" />
									<c:param name="idprevision" value="${prevision.id}" />
								</c:url>
								<a class="tdlink" href='<c:out value="${verPrevisionURL}" escapeXml="false"/>' >
									<c:out value="${prevision.codigoTransferencia}"/>
								</a>
							</display:column>
					
							<display:column titleKey="archigest.archivo.transferencias.prevision" media="csv excel xml pdf">
								<c:out value="${prevision.codigoTransferencia}"/>
							</display:column>
							
							<display:column titleKey="archigest.archivo.transferencias.FIT" sortable="true" headerClass="sortable">
								<fmt:formatDate value="${prevision.fechainitrans}" pattern="${FORMATO_FECHA}" />
							</display:column>
					
							<display:column titleKey="archigest.archivo.transferencias.FFT" sortable="true" headerClass="sortable">
								<fmt:formatDate value="${prevision.fechafintrans}" pattern="${FORMATO_FECHA}" />
							</display:column>
						
							<display:column property="numuinstalacion" titleKey="archigest.archivo.calendar.unidades" sortable="true" headerClass="sortable"/>
							
							<display:column titleKey="archigest.archivo.transferencias.tipoTransf" sortable="true" headerClass="sortable" >
								<c:set var="keyTitulo">
									archigest.archivo.transferencias.tipooperacion<c:out value="${prevision.tipooperacion}"/>
								</c:set>
								<fmt:message key="${keyTitulo}" />
							</display:column>
							
					
							<display:column titleKey="archigest.archivo.transferencias.estado" property="estado" sortable="true" headerClass="sortable" media="html"/>
							<display:column titleKey="archigest.archivo.transferencias.estado" media="csv excel xml pdf">
								<fmt:message key="archigest.archivo.transferencias.estadoPrevision.${prevision.estado}" />
							</display:column>
	
							<display:column titleKey="archigest.archivo.transferencias.previsiones.busqueda.organo" sortable="true" headerClass="sortable">
								<c:out value="${prevision.organoRemitente.nombre}"/>
								<c:set var="numFilas" value="${prevision_rowNum}"/>
							</display:column>
											
						</display:table> 
						
						<c:set var="masDeDiecinueveFilas" value="0"/>
						<c:if test="${pageScope.numFilas>19}">
							<c:set var="masDeDiecinueveFilas" value="1"/>
						</c:if>
					
						<c:if test="${masDeDiecinueveFilas<1}">
							<SCRIPT>
								var capaConScroll=document.getElementById("capaConScroll");
								capaConScroll.className="";
							</SCRIPT>
						</c:if> 
					</div>
				</div>
			
			</div> <%--cuerpo_izda --%>
			<h2><span>&nbsp;<br/></span></h2>
			
		</div> <%--cuerpo_drcha --%>
		
	</div> <%--ficha --%>

</div> <%--contenedor_ficha --%>
