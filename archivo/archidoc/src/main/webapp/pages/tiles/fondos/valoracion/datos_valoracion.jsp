<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="valoracion" value="${sessionScope[appConstants.valoracion.VALORACION_KEY]}" />

<c:choose>
	<c:when test="${valoracion.tipoValorAdministrativo == 0}">
		<div style="padding-top:4px;padding-bottom:4px">
			<bean:message key="archigest.archivo.valoracion.datos_vacios"/>
		</div>
	</c:when>
	<c:otherwise>
		<TABLE class="formulario"> <%-- para aspecto de formulario con lineas bottom de celda --%>
			<TR>
				<TD class="tdTitulo" width="350px">
					<bean:message key="archigest.archivo.valoracion.titulo"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${valoracion.titulo}" />
				</TD>
			</TR>
			<TR><TD>&nbsp;</TD></TR>
			<TR>
				<TD class="tdTitulo" >
					<bean:message key="archigest.archivo.valoracion.ordenacionPrimerNivel"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:if test="${valoracion.ordenacionSerie1 > 0}">
						<fmt:message key="archigest.archivo.valoracion.ordenacion${valoracion.ordenacionSerie1}" />
					</c:if>
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo" >
					<bean:message key="archigest.archivo.valoracion.ordenacionSegundoNivel"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:choose>
						<c:when test="${valoracion.ordenacionSerie2 == 0}">
							<bean:message key="archigest.archivo.valoracion.sinOrden"/>
						</c:when>
						<c:otherwise>
							<fmt:message key="archigest.archivo.valoracion.ordenacion${valoracion.ordenacionSerie2}" />
						</c:otherwise>
					</c:choose>
				</TD>
			</TR>
			<TR><TD>&nbsp;</TD></TR>
			<TR>
				<TD class="tdTitulo" >
					<bean:message key="archigest.archivo.valoracion.documentosRecopilatorios"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${valoracion.documentosRecopilatorios}" />
				</TD>
			</TR>
			<TR><TD>&nbsp;</TD></TR>
				<TR>
				<TD class="tdTitulo" >
					<bean:message key="archigest.archivo.valoracion.valorAdministrativo"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<fmt:message key="archigest.archivo.valoracion.valorAdministrativo${valoracion.tipoValorAdministrativo}" />
				</TD>
			</TR>
			<c:if test="${valoracion.tipoValorAdministrativo == 1}">
			<TR>
				<TD class="tdTitulo" >
					<bean:message key="archigest.archivo.valoracion.periodoVigenciaAdm"/>:&nbsp;
				</TD>
				<TD class="tdDatos">		
					<c:out value="${valoracion.anosVigenciaAdministrativa}" />
					<bean:message key="archigest.archivo.anios"/>
				</TD>
			</TR>
			</c:if>
			<TR>
				<TD class="tdTitulo" >
					<bean:message key="archigest.archivo.valoracion.justificacion"/>:&nbsp;
				</TD>
				<TD class="tdDatos">		
					<c:out value="${valoracion.valorAdministrativo}" />
				</TD>
			</TR>
			<TR><TD>&nbsp;</TD></TR>
			<TR>
				<TD class="tdTitulo" >
					<bean:message key="archigest.archivo.valoracion.valorLegal"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<fmt:message key="archigest.archivo.valoracion.valorLegal${valoracion.tipoValorLegal}" />
				</TD>
			</TR>
			<c:if test="${valoracion.tipoValorLegal == 1}">
			<TR>
				<TD class="tdTitulo" >
					<bean:message key="archigest.archivo.valoracion.periodoVigenciaLegal"/>:&nbsp;
				</TD>
				<TD class="tdDatos">		
					<c:out value="${valoracion.anosVigenciaLegal}" />
					<bean:message key="archigest.archivo.anios"/>
				</TD>
			</TR>
			</c:if>
			<TR>
				<TD class="tdTitulo" >
					<bean:message key="archigest.archivo.valoracion.justificacion"/>:&nbsp;
				</TD>
				<TD class="tdDatos">		
					<c:out value="${valoracion.valorLegal}" />
				</TD>
			</TR>
			<TR><TD>&nbsp;</TD></TR>
			<TR>
				<TD class="tdTitulo" >
					<bean:message key="archigest.archivo.valoracion.valorInformativo"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<fmt:message key="archigest.archivo.valoracion.valorInformativo${valoracion.tipoValorInformativo}" />
				</TD>
			</TR>
			<c:choose>
			<c:when test="${valoracion.tipoValorInformativo == 1}">
			<TR>
				<TD class="tdTitulo" >
					<bean:message key="archigest.archivo.valoracion.valorCientifico"/>:&nbsp;
				</TD>
				<TD class="tdDatos">		
					<c:choose>
						<c:when test="${valoracion.tipoValorCientifico == 1}">
							<span class="etiquetaNegra12Normal"><bean:message key="archigest.archivo.si"/></span>
						</c:when>
						<c:otherwise>
							<span class="etiquetaNegra12Normal"><bean:message key="archigest.archivo.no"/></span>
						</c:otherwise>
					</c:choose>
					<br/>
					<c:out value="${valoracion.valorCientifico}" />
				</TD>
			</tr>
			<tr>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.valoracion.valorTecnologico"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:choose>
						<c:when test="${valoracion.tipoValorTecnologico == 1}">
							<span class="etiquetaNegra12Normal"><bean:message key="archigest.archivo.si"/></span>
						</c:when>
						<c:otherwise>
							<span class="etiquetaNegra12Normal"><bean:message key="archigest.archivo.no"/></span>
						</c:otherwise>
					</c:choose>
					<br/>
					<c:out value="${valoracion.valorTecnologico}" />
				</TD>
			</tr>
			<tr>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.valoracion.valorCultural"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:choose>
						<c:when test="${valoracion.tipoValorCultural == 1}">
							<span class="etiquetaNegra12Normal"><bean:message key="archigest.archivo.si"/></span>
						</c:when>
						<c:otherwise>
							<span class="etiquetaNegra12Normal"><bean:message key="archigest.archivo.no"/></span>
						</c:otherwise>
					</c:choose>
					<br/>
					<c:out value="${valoracion.valorCultural}" />
				</TD>
			</TR>
			</c:when>
			<c:otherwise>
			<TR>
				<TD class="tdTitulo" >
					<bean:message key="archigest.archivo.valoracion.justificacion"/>:&nbsp;
				</TD>
				<TD class="tdDatos">		
					<c:out value="${valoracion.valorInformativo}" />
				</TD>
			</TR>
			</c:otherwise>
			</c:choose>
			<TR><TD>&nbsp;</TD></TR>
			<TR>
				<TD class="tdTitulo" >
					<bean:message key="archigest.archivo.valoracion.regimenAcceso"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<fmt:message key="archigest.archivo.valoracion.regimenAcceso${valoracion.tipoRegimenAcceso}" />
					<c:if test="${valoracion.tipoRegimenAccesoTemporal == 21}">
						<bean:message key="archigest.archivo.valoracion.regimenAcceso.temporalParcial"/>
					</c:if>
					<c:if test="${valoracion.tipoRegimenAccesoTemporal == 22}">
						<bean:message key="archigest.archivo.valoracion.regimenAcceso.temporalTotal"/>
					</c:if>
				</TD>
			</TR>
			<c:if test="${valoracion.tipoRegimenAcceso == 2}">
			<TR>
				<TD class="tdTitulo" >
					<bean:message key="archigest.archivo.valoracion.periodoRegimenAcceso"/>:&nbsp;
				</TD>
				<TD class="tdDatos">		
					<c:out value="${valoracion.anosRegimenAcceso}" />
					<bean:message key="archigest.archivo.anios"/>
				</TD>
			</TR>
			</c:if>
			<TR>
				<TD class="tdTitulo" >
					<bean:message key="archigest.archivo.valoracion.justificacion"/>:&nbsp;
				</TD>
				<TD class="tdDatos">		
					<c:out value="${valoracion.regimenAcceso}" />
				</TD>
			</TR>
			<TR><TD>&nbsp;</TD></TR>
			<TR>
				<TD class="tdTitulo" >
					<bean:message key="archigest.archivo.valoracion.valoresDictamen"/>:&nbsp;
				</TD>
				<TD class="tdDatos">		
					<fmt:message key="archigest.archivo.valoracion.valorDictamen${valoracion.valorDictamen}" />
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo" >
					<bean:message key="archigest.archivo.valoracion.justificacion"/>:&nbsp;
				</TD>
				<TD class="tdDatos">		
					<c:out value="${valoracion.valorDictamenValue}" />
				</TD>
			</TR>
			<c:if test="${valoracion.valorDictamen == 2}">
			<TR>
				<TD class="tdTitulo" >
					<bean:message key="archigest.archivo.valoracion.tecnicasMuestreo"/>:&nbsp;
				</TD>
				<TD class="tdDatos">		
					<fmt:message key="archigest.archivo.valoracion.tecnicaMuestreo${valoracion.tecnicaMuestreo}" />
				</TD>
			</TR>
			</c:if>
			<c:set var="listaNivelesArchivo" value="${sessionScope[appConstants.controlAcceso.LISTA_NIVELES_ARCHIVO]}"/>
			<c:if test="${!empty valoracion.listaPlazos}">
			<TR id="trPlazosCambioFase">
				<TD class="tdTitulo" >
					<bean:message key="archigest.valoraciones.plazos.transferencias"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<table style="width:60%" id="tabla3" class="tablaFicha">
						<thead>
							<tr>
								<th width="" style="width:33%"><bean:message key="archigest.valoraciones.nivel.origen"/></th>
								<th width="" style="width:33%"><bean:message key="archigest.valoraciones.nivel.destino"/></th>
								<th width="" style="width:34%"><bean:message key="archigest.valoraciones.plazo.años"/></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="plazoVO" items="${valoracion.listaPlazos}" varStatus="status">
								<tr valign="top">
									<td style="width:33%">
										<c:forEach var="nivel" items="${pageScope.listaNivelesArchivo}">
											<c:if test="${nivel.id==plazoVO.idNivelOrigen}"><c:out value="${nivel.nombre}"/></c:if>											
										</c:forEach>
									</td>
									<td style="width:33%">
										<c:forEach var="nivel" items="${pageScope.listaNivelesArchivo}">
											<c:if test="${nivel.id==plazoVO.idNivelDestino}"><c:out value="${nivel.nombre}"/></c:if>											
										</c:forEach>
									</td>
									<td style="width:34%">
										<c:out value="${plazoVO.plazo}"/>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</TD>
			</TR>
			</c:if>
		</TABLE>
	</c:otherwise>
</c:choose>
