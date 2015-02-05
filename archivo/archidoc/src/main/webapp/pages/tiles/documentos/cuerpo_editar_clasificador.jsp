<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<bean:struts id="actionMapping" mapping="/clasificador" />

<c:set var="unidadDocumental" value="${requestScope[appConstants.documentos.UNIDAD_DOCUMENTAL_KEY]}"/>
<c:set var="serie" value="${sessionScope[appConstants.documentos.SERIE_KEY]}"/>
<c:set var="descriptor" value="${requestScope[appConstants.documentos.DESCRIPTOR_KEY]}"/>
<c:set var="clasificador" value="${requestScope[appConstants.documentos.FOLDER_KEY]}"/>
<c:set var="infoFichero" value="${requestScope[appConstants.documentos.FILE_INFO_KEY]}"/>

<script language="JavaScript1.2" type="text/JavaScript">
	function save()
	{
		document.forms["<c:out value="${actionMapping.name}" />"].submit();
	}
</script>

<div class="content_container">
<html:form action="/clasificador" enctype="multipart/form-data">

	<html:hidden property="id"/>
	<html:hidden property="idClfPadre"/>
	<html:hidden property="idObjeto"/>
	<html:hidden property="tipoObjeto"/>
	<html:hidden property="tienePadreFijo"/>
	<html:hidden property="tieneHijoFijo"/>
	<html:hidden property="estado"/>
	<html:hidden property="marcas"/>
	<input type="hidden" name="method" value="save"/>
	
	<div class="ficha">
		<h1><span>
			<div class="w100">
				<table class="w98" cellpadding=0 cellspacing=0>
					<tr>
				    	<td class="etiquetaAzul12Bold" height="25px">
				    		<bean:message key="archigest.archivo.documentos.clasificador.form.caption"/>
				    	</td>
				    	<td align="right">
							<table cellpadding=0 cellspacing=0>
								<tr>
									<td nowrap>
										<a class="etiquetaAzul12Bold" 
										   href="javascript:save()"
										><html:img
												page="/pages/images/Ok_Si.gif" 
										        border="0" 
										        altKey="archigest.archivo.aceptar"
										        titleKey="archigest.archivo.aceptar"
										        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.aceptar"/></a>
									</td>
									<td width="10">&nbsp;</td>
									<td nowrap>
										<tiles:insert definition="button.closeButton" flush="false">
											<tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
											<tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
										</tiles:insert>
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

			<c:set var="tarea" value="${sessionScope[appConstants.documentos.TAREA_KEY]}"/>
			<c:choose> 
			<c:when test="${!empty tarea}">
				<tiles:insert template="info_tarea.jsp" flush="false"/>
			</c:when>
			<c:otherwise>
				<c:if test="${!empty unidadDocumental}">
					<tiles:insert template="info_udoc.jsp" flush="false"/>
				</c:if>
	
				<c:if test="${!empty serie}">
					<tiles:insert template="info_serie.jsp" flush="false"/>
				</c:if>
	
				<c:if test="${!empty descriptor}">
					<tiles:insert template="info_descriptor.jsp" flush="false"/>
				</c:if>
			</c:otherwise>
			</c:choose>
			
			<div class="cabecero_bloque">
				<table class="w98m1" cellpadding="0" cellspacing="0" height="100%">
					<tbody>
						<tr>
							<td class="etiquetaAzul12Bold">
								<bean:message key="archigest.archivo.documentos.clasificador.caption"/>
							</td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="bloque"><%--bloque datos clasificador --%>
				<table class="formulario">
					<tr>
						<td class="tdTitulo" width="200px"><bean:message key="archigest.archivo.documentos.clasificador.form.nombre"/>:&nbsp;</td>
						<td class="tdDatos"><html:text property="nombre" size="64" maxlength="128"/></td>
					</tr>
					<tr>
						<td class="tdTitulo"><bean:message key="archigest.archivo.documentos.clasificador.form.descripcion"/>:&nbsp;</td>
						<td class="tdDatos"><html:textarea property="descripcion" cols="50" rows="3" onkeypress="maxlength(this,512,false)" onchange="maxlength(this,512,true)"/></td>
					</tr>
				</table>
			</div><%--bloque datos clasificador --%>

			<div class="separador5">&nbsp;</div>
	
		</div> <%--cuerpo_izda --%>
		</div> <%--cuerpo_drcha --%>
		
		<h2><span>&nbsp;</span></h2>
	</div> <%--ficha --%>
</html:form>
</div> <%--contenedor_ficha --%>
