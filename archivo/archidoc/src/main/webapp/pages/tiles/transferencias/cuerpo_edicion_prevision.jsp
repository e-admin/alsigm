<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="bPrevision" value="${sessionScope[appConstants.transferencias.PREVISION_KEY]}"/>
<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.transferencias.edicion"/>
		<bean:message key="archigest.archivo.transferencias.identificacion"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		 <TR>
			<TD>
				<c:url var="cancelURI" value="/action/gestionDetallesPrevision">
					<c:param name="method" value="cancelarAccion"  />
				</c:url>
				<a class="etiquetaAzul12Bold" href="javascript:document.forms[0].submit()" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</TD>
		   <TD width="10">&nbsp;</TD>
		   <TD>
				<c:url var="cancelURL" value="/action/gestionPrevisiones">
					<c:param name="method" value="verprevision" />
					<c:param name="idprevision" value="${bPrevision.id}" />
				</c:url>
				<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${cancelURL}" escapeXml="false"/>'">
					<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.cancelar"/>
				</a>
		   </TD>
		 </TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">


		<div class="separador1">&nbsp;</div>

		<DIV class="cabecero_bloque_sin_height"> <%--primer bloque de datos (resumen siempre visible) --%>
					<jsp:include page="cabeceracte_prevision.jsp" flush="true" />
		</DIV> <%--primer bloque de datos (resumen siempre visible) --%>

		<div class="bloque"> <%--primer bloque de datos --%>

		<html:form action="/gestionPrevisiones">
				<html:hidden property="method" value="guardaredicion"/>

				<TABLE class="formulario"> <%--para aspecto de formulario con lineas bottom de celda --%>
					<TR>
						<TD class="tdTitulo" width="250px">
							<bean:message key="archigest.archivo.transferencias.tipoTransf"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:set var="keyTitulo">
								archigest.archivo.transferencias.tipooperacion<c:out value="${bPrevision.tipooperacion}"/>
							</c:set>
							<fmt:message key="${keyTitulo}" />
						</TD>
					</TR>
					<c:if test="${!bPrevision.entreArchivos}">
						<TR>
							<TD class="tdTitulo">
								<bean:message key="archigest.archivo.transferencias.orgRem"/>:&nbsp;
							</TD>
							<TD class="tdDatos">
								<c:out value="${bPrevision.organoRemitente.nombreLargo}"/>
							</TD>
						</TR>
					</c:if>
					<c:if test="${bPrevision.entreArchivos}">
						<c:choose>
							<c:when test="${bPrevision.archivoRemitenteCanBeChanged}">
								<bean:struts id="mappingGestionPrevision" mapping="/gestionPrevisiones" />
								<TR>
									<TD class="tdTitulo">
										<bean:message key="archigest.archivo.archivoRemitente"/>:&nbsp;
									</TD>
									<TD class="tdDatos">
										<c:set var="listaArchivos" value="${sessionScope[appConstants.transferencias.LISTA_CODIGOSARCHIVO_KEY]}" />
										<c:if test="${!empty listaArchivos}">
											<script>
													function cargarArchivosReceptoresEdicion(archivo)
													{
														var method = document.getElementById("method");
														method.value = "cargarArchivosReceptoresEdicion";
														var idarchivoreceptor = document.getElementById("idarchivoreceptor");
														idarchivoreceptor.value='';
														document.forms['<c:out value="${mappingGestionPrevision.name}" />'].submit();
													}
											</script>
											<c:set var="bListaCodigosArchivoKey" value="${appConstants.transferencias.LISTA_CODIGOSARCHIVO_KEY}"/>
											<jsp:useBean id="bListaCodigosArchivoKey" type="java.lang.String"/>
											<html:select property='idarchivoremitente' size="1" styleClass="input" onchange="javascript:cargarArchivosReceptoresEdicion(this)" >
												<html:optionsCollection name="<%=bListaCodigosArchivoKey%>" label="nombre" value="id"/>
											</html:select>
										</c:if>
									</TD>
								</TR>
							</c:when>
							<c:otherwise>
								<c:if test="${bPrevision.entreArchivos}">
									<TR>
										<TD class="tdTitulo">
											<bean:message key="archigest.archivo.archivoRemitente"/>:&nbsp;
										</TD>
										<TD class="tdDatos" id="nombreArchivoRemitente">
											<html:hidden property="idarchivoremitente"/>
											<c:out value="${bPrevision.nombrearchivoremitente}"/>
										</TD>
									</TR>
								</c:if>
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:choose>
						<c:when test="${bPrevision.entreArchivos}">
							<TR>
								<TD class="tdTitulo">
									<bean:message key="archigest.archivo.archivoReceptor"/>:&nbsp;
								</TD>
								<TD class="tdDatos">
									<c:set var="bListaCodigosArchivoReceptorKey" value="${appConstants.transferencias.LISTA_CODIGOSARCHIVORECEPTORES_KEY}"/>
									<jsp:useBean id="bListaCodigosArchivoReceptorKey" type="java.lang.String"/>
									<c:set var="listaArchivosReceptores" value="${sessionScope[appConstants.transferencias.LISTA_CODIGOSARCHIVORECEPTORES_KEY]}" />
									<c:choose>
										<c:when test="${!empty listaArchivosReceptores}">
											<html:select property='idarchivoreceptor' size="1" styleClass="input" >
												<html:optionsCollection name="<%=bListaCodigosArchivoReceptorKey%>" label="nombre" value="id"/>
											</html:select>
										</c:when>
										<c:otherwise>
											<html:hidden property="idarchivoreceptor"/>
											<c:out value="${bPrevision.nombrearchivoreceptor}"/>
										</c:otherwise>
									</c:choose>
								</TD>
							</TR>
						</c:when>
						<c:otherwise>
							<TR>
								<TD class="tdTitulo">
									<bean:message key="archigest.archivo.transferencias.archRecep"/>:&nbsp;
								</TD>
								<TD class="tdDatos">
									<html:hidden property="idarchivoreceptor"/>
									<c:out value="${bPrevision.nombrearchivoreceptor}"/>
								</TD>
							</TR>
						</c:otherwise>
					</c:choose>

					<TR>
						<TD class="tdTitulo">
							<c:choose>
								<c:when test="${bPrevision.entreArchivos}">
									<bean:message key="archigest.archivo.cf.fondoDestino"/>:&nbsp;
								</c:when>
								<c:otherwise>
									<bean:message key="archigest.archivo.transferencias.fondo"/>:&nbsp;
								</c:otherwise>
							</c:choose>
						</TD>
						<TD class="tdDatos">
							<c:set var="listaFondos" value="${sessionScope[appConstants.transferencias.LISTA_CODIGOSFONDO_KEY]}" />
							<c:choose>
							<c:when test="${not empty listaFondos and !bPrevision.ordinaria}">
									<c:set var="bListaCodigosFondoKey" value="${appConstants.transferencias.LISTA_CODIGOSFONDO_KEY}"/>
									<jsp:useBean id="bListaCodigosFondoKey" type="java.lang.String"/>
									<html:select property='idfondo' size="1" styleClass="input">
										<html:optionsCollection name="<%=bListaCodigosFondoKey%>" label="label" value="id"/>
									</html:select>
							</c:when>
							<c:otherwise>
									<html:hidden property="idfondo"/>
									<c:out value="${bPrevision.fondo.codReferencia}"/>
									<c:out value="${bPrevision.fondo.titulo}"/>
							</c:otherwise>
							</c:choose>
						</TD>
					</TR>
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.nUndInstal"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:choose>
								<c:when test="${sessionScope[appConstants.transferencias.PREVISION_KEY].detallada}">
									<html:text name="PrevisionForm" size="5"  maxlength="5" property="numuinstalacion" styleClass="inputRO" readonly="true"/>
								</c:when>
								<c:otherwise>
									<html:text name="PrevisionForm" size="5"  maxlength="5" property="numuinstalacion" styleClass="input"/>
								</c:otherwise>
							</c:choose>
						</TD>
					</TR>

				<c:set var="estadoAceptada" value="${appConstants.transferencias.estadoPrevision.ACEPTADA.identificador}" />
				<c:set var="estadoCerrada" value="${appConstants.transferencias.estadoPrevision.CERRADA.identificador}" />
				<c:if test="${(bPrevision.estado==estadoAceptada) || (bPrevision.estado==estadoCerrada)}">
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.fIniTransf"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<TABLE cellpadding="0" cellspacing="0">
							<TR>
								<TD width="50px" class="td2Datos">
									<fmt:formatDate value="${bPrevision.fechainitrans}" pattern="${FORMATO_FECHA}"/>
								</TD>
								<TD width="20px">
								</TD>
								<TD width="250px" class="td2Titulo">
									<bean:message key="archigest.archivo.transferencias.fFinTransf"/>:&nbsp;
								</TD>
								<TD width="60px" class="td2Datos">
									<fmt:formatDate value="${bPrevision.fechafintrans}" pattern="${FORMATO_FECHA}" />
								</TD>
							</TR>
							</TABLE>
						</TD>
					</TR>
				</c:if>

				<c:if test="${not empty bPrevision.motivorechazo}">
					<TR>
						<TD class="tdTitulo" style="vertical-align:top">
							<bean:message key="archigest.archivo.transferencias.notas"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:out value="${bPrevision.motivorechazo}"/>
						</TD>
					</TR>
				</c:if>

					<TR>
						<TD class="tdTitulo" style="text-align:top">
							<bean:message key="archigest.archivo.transferencias.observaciones"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<html:textarea property="observaciones" rows="3" onkeypress="maxlength(this,254,false)" onchange="maxlength(this,254,true)"/>
						</TD>
					</TR>
				</TABLE>

		</html:form>
		</div> <%--bloque --%>
	</tiles:put>
</tiles:insert>