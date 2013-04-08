<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="prevision" value="${sessionScope[appConstants.transferencias.PREVISION_KEY]}" />

<div id="contenedor_ficha">
<div class="ficha">

<h1><span>
	<div class="w100">
	<TABLE class="w98" cellpadding=0 cellspacing=0>
	  <TR>
	    <TD class="etiquetaAzul12Bold" height="25px">
			<bean:message key="archigest.archivo.transferencias.creacion"/>&nbsp;
			<bean:message key="archigest.archivo.transferencias.prevision"/>&nbsp;
		</TD>
	    <TD align="right">
			<TABLE cellpadding=0 cellspacing=0>
			<TR>
			    <TD>
					<bean:struts id="mappingGestionPrevision" mapping="/gestionPrevisiones" />
					<a class="etiquetaAzul12Bold" href="javascript:document.forms['<c:out value="${mappingGestionPrevision.name}" />'].submit();" >
						<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.aceptar"/>
					</a>
				</TD>
				<TD width="10">&nbsp;</TD>
				<c:url var="cancelURI" value="/action/gestionPrevisiones">
					<c:param name="method" value="goBack"  />
				</c:url>
				<TD>
			   		<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${cancelURI}" escapeXml="false"/>'" >
						<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
			   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
			   		</a>
				</TD>
			</TR>
			</TABLE>
		</TD>
	  </TR>
	</TABLE>
	</div>
</span></h1>

<div class="cuerpo_drcha">
<div class="cuerpo_izda">

<DIV id="barra_errores">
		<archivo:errors />
</DIV>

<div class="separador1">&nbsp;</div>
<html:form action="/gestionPrevisiones">
<div class="bloque">

		<TABLE class="formulario">
			<TR>

		<html:hidden property="method" styleId="method" value="crear"/>
		<html:hidden property="tipotransferencia" styleId="tipotransferencia"/>
		<html:hidden property="tipoprevision" styleId="tipoprevision"/>
				<TD class="tdTitulo" width="250px">
					<bean:message key="archigest.archivo.transferencias.tipoTransf"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:set var="keyTitulo">
						archigest.archivo.transferencias.tipooperacion<c:out value="${prevision.tipooperacion}"/>
					</c:set>
					<fmt:message key="${keyTitulo}" />
				</TD>
			</TR>
			<c:if test="${!prevision.entreArchivos}">
				<TR>
					<TD class="tdTitulo">
						<bean:message key="archigest.archivo.transferencias.orgRem"/>:&nbsp;
					</TD>
					<TD class="tdDatos">
					<c:out value="${prevision.organoRemitente.nombreLargo}"/>
					</TD>
				</TR>
			</c:if>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.gestor"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
				<c:out value="${prevision.gestor.nombreCompleto}"/>
				</TD>
			</TR>
			<c:choose>
				<c:when test="${!prevision.entreArchivos}">
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.archRecep"/>:&nbsp;
						</TD>
						<TD class="tdDatos" id="nombreArchivoReceptor">
							<c:out value="${prevision.nombrearchivoreceptor}"/>
						</TD>
					</TR>
				</c:when>
				<c:otherwise>

				</c:otherwise>
			</c:choose>

			<%--Poner archivo remitente y receptor --%>

			<c:if test="${prevision.entreArchivos}">
				<c:set var="bListaCodigosArchivoKey" value="${appConstants.transferencias.LISTA_CODIGOSARCHIVO_KEY}"/>
				<jsp:useBean id="bListaCodigosArchivoKey" type="java.lang.String"/>
				<c:set var="bListaCodigosArchivoReceptorKey" value="${appConstants.transferencias.LISTA_CODIGOSARCHIVORECEPTORES_KEY}"/>
				<jsp:useBean id="bListaCodigosArchivoReceptorKey" type="java.lang.String"/>

					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.archivoRemitente"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:set var="listaArchivos" value="${sessionScope[appConstants.transferencias.LISTA_CODIGOSARCHIVO_KEY]}" />
							<c:if test="${!empty listaArchivos}">
								<script>
										function cargarArchivosReceptoresCreacion(archivo)
										{
											var method = document.getElementById("method");
											method.value = "cargarArchivosReceptoresCreacion";
											var idarchivoreceptor = document.getElementById("idarchivoreceptor");
											if ((idarchivoreceptor!=null)&&(idarchivoreceptor!='')&&(idarchivoreceptor!='undefined'))
												idarchivoreceptor.value='';
											document.forms['<c:out value="${mappingGestionPrevision.name}" />'].submit();
										}
								</script>
								<html:select property='idarchivoremitente' size="1" styleClass="input" onchange="javascript:cargarArchivosReceptoresCreacion(this)" >
									<html:optionsCollection name="<%=bListaCodigosArchivoKey%>" label="nombre" value="id"/>
								</html:select>
							</c:if>
						</TD>
					</TR>
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.archivoReceptor"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:set var="listaArchivosReceptores" value="${sessionScope[appConstants.transferencias.LISTA_CODIGOSARCHIVORECEPTORES_KEY]}" />
							<c:if test="${!empty listaArchivosReceptores}">
								<html:select property='idarchivoreceptor' styleId="idarchivoreceptor" size="1" styleClass="input" >
									<html:optionsCollection name="<%=bListaCodigosArchivoReceptorKey%>" label="nombre" value="id"/>
								</html:select>
							</c:if>
						</TD>
					</TR>
			</c:if>

			<c:set var="bListaCodigosFondoKey" value="${appConstants.transferencias.LISTA_CODIGOSFONDO_KEY}"/>
			<jsp:useBean id="bListaCodigosFondoKey" type="java.lang.String"/>
			<c:choose>
				<c:when test="${not empty bListaCodigosFondoKey and prevision.entreArchivos}">
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.cf.fondoDestino"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:set var="listaFondos" value="${sessionScope[appConstants.transferencias.LISTA_CODIGOSFONDO_KEY]}" />
							<c:if test="${!empty listaFondos}">
								<html:select property='idfondo' size="1" styleClass="input" >
									<html:option value=""></html:option>
									<html:optionsCollection name="<%=bListaCodigosFondoKey%>" label="label" value="id"/>
								</html:select>
							</c:if>
						</TD>
					</TR>
				</c:when>
				<c:otherwise>
					<TR>
						<TD class="tdTitulo">
							<bean:message key="archigest.archivo.transferencias.fondo"/>:&nbsp;
						</TD>
						<TD class="tdDatos">
							<c:set var="listaFondos" value="${sessionScope[appConstants.transferencias.LISTA_CODIGOSFONDO_KEY]}" />
							<c:choose>
							<c:when test="${not empty listaFondos and !prevision.ordinaria}">
								<html:select property='idfondo' size="1" styleClass="input">
									<html:optionsCollection name="<%=bListaCodigosFondoKey%>" label="label" value="id"/>
								</html:select>
							</c:when>
							<c:otherwise>
									<input type="hidden" name="idfondo" id="idfondo" value="<c:out value="${prevision.fondo.id}"/>"/>
									<c:out value="${prevision.fondo.label}"/>
							</c:otherwise>
							</c:choose>
						</TD>
					</TR>
				</c:otherwise>
			</c:choose>
			<c:if test="${!prevision.detallada}">
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.nUndInstal"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<html:text name="PrevisionForm" size="5" maxlength="5" property="numuinstalacion" styleClass="input"/>
				</TD>
			</TR>
			</c:if>
			<TR>
				<TD class="tdTitulo" style="vertical-align:top">
					<bean:message key="archigest.archivo.transferencias.observaciones"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<html:textarea property="observaciones" rows="3" onkeypress="maxlength(this,254,false)" onchange="maxlength(this,254,true)"/>
				</TD>
			</TR>
		</TABLE>
		<c:if test="${!empty requestScope[appConstants.transferencias.LISTA_PREVISIONES_SINFINALIZAR_KEY]}">
			<c:set var="LISTA">requestScope.<c:out value="${appConstants.transferencias.LISTA_PREVISIONES_SINFINALIZAR_KEY}"/></c:set>
			<jsp:useBean id="LISTA" type="java.lang.String"/>

			<span class="separador5"></span>

			<display:table name='<%=LISTA%>'
					id="listaRegistros"
					style="width:99%;"
					decorator="transferencias.decorators.ViewPrevisionDecorator"
					export="false">

					<display:column titleKey="archigest.archivo.transferencias.prevision" sortable="true" headerClass="sortable" >
						<c:url var="URL" value="/action/gestionPrevisiones">
							<c:param name="method" value="verprevision" />
							<c:param name="idprevision" value="${listaRegistros.id}" />
						</c:url>
						<a class="tdlink" href='<c:out value="${URL}" escapeXml="false" />' >
							<c:out value="${listaRegistros.codigoTransferencia}"/>
						</a>
					</display:column>

					<display:column titleKey="archigest.archivo.transferencias.tipoTransf" property="tipotransferencia"/>
					<display:column titleKey="archigest.archivo.transferencias.estado" property="estado"/>

					<display:column titleKey="archigest.archivo.transferencias.fEstado" property="fechaestado" sortable="true" headerClass="sortable" decorator="common.view.DateDecorator" />

			</display:table>

		<span class="separador5"></span>

		</c:if>
</div> <%--bloque --%>
</html:form>
</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>
