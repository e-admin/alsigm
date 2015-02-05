<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>


<bean:struts id="mapping" mapping="/organizacionUDocsAction" />

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="vListaUdocs" value="${sessionScope[appConstants.deposito.LISTA_UDOCS_EN_CAJA]}"/>
<c:set var="vHueco" value="${sessionScope[appConstants.deposito.HUECO_KEY]}"/>

<script language="JavaScript1.2" type="text/JavaScript">
	function goOn(){
		var form = document.forms['<c:out value="${mapping.name}" />'];
		form.method.value="grabar";
		form.submit();
	}
	
	function subir(){
		var form = document.forms['<c:out value="${mapping.name}" />'];
		form.method.value="subirUDocs";
		form.submit();
	}
	
	function bajar(){
		var form = document.forms['<c:out value="${mapping.name}" />'];
		form.method.value="bajarUDocs";
		form.submit();
	}
	
	function unir(){
		var form = document.forms['<c:out value="${mapping.name}" />'];
		form.method.value="unirPartesUDoc";
		form.submit();	
	}
	
	function dividir(){
		var form = document.forms['<c:out value="${mapping.name}" />'];
		form.method.value="dividirUDoc";
		form.submit();	
	}
	
	function cerrar(){
		<c:url var="cancelURL" value="/action/reubicacionUdocsAction">
			<c:param name="method" value="goReturnPoint" />
		</c:url>
		<c:if test="${sessionScope[appConstants.deposito.HAY_CAMBIOS_SIN_GUARDAR]}">
		if(!window.confirm("<bean:message key='archigest.warning.formularioModificado'/>")){
			return;
		}
		</c:if>
		window.location = '<c:out value="${cancelURL}" escapeXml="false"/>';
	}

	//Para editar campos en la columnas del displaytag
	function modificarCampo(nombre,pos){
		var divLabelCampo="divLabel"+nombre+pos;
		var textBoxCampo="textBox"+nombre+pos;
		var divAceptarCancelarCampo="divAceptarCancelar"+nombre+pos;
		// Al pulsar el boton de modificar, hay que salir del modo edicion en el resto de campos en esa columna.
		for(i=1;;i++){
			divAceptarCancelarCampo="divAceptarCancelar"+nombre+i;
			divLabelCampo="divLabel"+nombre+i;
			elemento=document.getElementById(divAceptarCancelarCampo);
			
			if(elemento!=null){
				elemento.className="hidden";
				document.getElementById(divLabelCampo).className="visible";
			}
			else{
				break;
			}
		}		
		divAceptarCancelarCampo="divAceptarCancelar"+nombre+pos;
		divLabelCampo="divLabel"+nombre+pos;		
		document.getElementById(divAceptarCancelarCampo).className="visible";
		document.getElementById(divLabelCampo).className="hidden";
		document.getElementById("valor"+nombre).value=textBoxCampo.value;
	}
	
	function cancelarModificacionCampo(nombre,pos){
		var divAceptarCancelarCampo="divAceptarCancelar"+nombre+pos;
		var divLabelCampo="divLabel"+nombre+pos;
		document.getElementById(divAceptarCancelarCampo).className="hidden";
		document.getElementById(divLabelCampo).className="visible";
	}
	
	function aceptarModificacionCampo(nombre,pos){
		var textBoxCampo="textBox"+nombre+pos;
		var method = 'actualizarCampoDescripcion';
		if(document.getElementById(textBoxCampo).value==document.getElementById("valor"+nombre).value){
			cancelarModificacionCampo(nombre,pos);
		}
		else{
			<c:url var="URL" value="/action/organizacionUDocsAction"/>
			window.location = 	'<c:out value="${URL}"/>'+ 
								'?method='+method+
								'&position='+pos+
								'&valor'+nombre+'='+document.getElementById(textBoxCampo).value;
		}
	}
</script>
<tiles:definition id="infoUInstalacion" template="/pages/tiles/PABlockLayout.jsp">
	<tiles:put name="blockTitle" direct="true">
		<fmt:message key="archigest.archivo.transferencias.unidadInst"/>:&nbsp;
	</tiles:put>
	<tiles:put name="blockContent" direct="true">

		<TABLE class="formulario" cellpadding=0 cellspacing=0>
			<TR>
				<TD class="tdTitulo" width="150px">
					<bean:message key="archigest.archivo.ruta"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${vHueco.path}"/>
				</TD>
			</TR>

			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.signatura"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${vHueco.unidInstalacion.signaturaui}"/>
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.formato"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${vHueco.nombreformato}"/>
				</TD>
			</TR>
		</TABLE>
	</tiles:put>
</tiles:definition>

<tiles:definition id="listadoUdocs" template="/pages/tiles/PABlockLayout.jsp" >
	<tiles:put name="blockTitle" direct="true">
		<fmt:message key="archigest.archivo.unidadesDocumentales"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<td width="10">&nbsp;</td>			
			<td>
				<a class="etiquetaAzul12Bold" href="javascript:unir()" >
				<html:img 
					page="/pages/images/unir.gif" 
					titleKey="archigest.archivo.deposito.unir" 
					styleClass="imgTextBottom" />
				&nbsp;<bean:message key="archigest.archivo.deposito.unir"/>
				</a>
			</td>
			<td width="10">&nbsp;</td>			
			<td>
				<a class="etiquetaAzul12Bold" href="javascript:dividir()" >
				<html:img 
					page="/pages/images/dividir.gif" 
					titleKey="archigest.archivo.transferencias.dividirUDoc" 
					styleClass="imgTextBottom" />
				&nbsp;<bean:message key="archigest.archivo.transferencias.dividir"/>
				</a>
			</td>						
			
			<td width="10">&nbsp;</td>
			<td>									
				<a class="etiquetaAzul12Bold" href="javascript:subir()" >
				<html:img 
					page="/pages/images/caja_subir.gif" 
					altKey="archigest.archivo.subir" 
					titleKey="archigest.archivo.subir" 
					styleClass="imgTextBottom" />
				&nbsp;<bean:message key="archigest.archivo.subir"/>
				</a>
		   	</td>						
			<td width="10">&nbsp;</td>			
			<td>
				<a class="etiquetaAzul12Bold" href="javascript:bajar()" >
				<html:img 
					page="/pages/images/caja_bajar.gif" 
					altKey="archigest.archivo.bajar" 
					titleKey="archigest.archivo.bajar" 
					styleClass="imgTextBottom" />
				&nbsp;<bean:message key="archigest.archivo.bajar"/>
				</a>
			</td>
		</TR>
		</TABLE>
	</tiles:put>	
	
	<tiles:put name="blockContent" direct="true">

	<html:form action="/organizacionUDocsAction">
	<input type="hidden" id="valorCampoDescripcion" name="valorCampoDescripcion" />
	<input type="hidden" name="method" id="method" value=""/>
	<html:hidden property="signaturaui" styleId="signaturaui"/>
		<div class="separador8">&nbsp;</div>
		<display:table name="pageScope.vListaUdocs"
			style="width:99%;margin-left:auto;margin-right:auto"
			id="udoc" 
			sort="list"
			export="false">
			
			<display:column style="width:22px">
				<c:if test="${udoc.posudocenui != udoc_rowNum}">
					<html:img 
						page="/pages/images/treeMover.gif" 
						titleKey="archigest.archivo.posicion.nueva" 
						styleClass="imgTextBottom" />
				</c:if>	
			</display:column>

			<display:column style="width:10px">
				<c:if test="${udoc.validada}">
					<c:set var="idUnidad" value="${udoc.idunidaddoc}:${udoc.posudocenui}:${udoc.signaturaudoc}:${udoc_rowNum-1}"/>
					<jsp:useBean id="idUnidad" class="java.lang.String"/>
					<html:multibox style="border:0" value="<%=idUnidad%>" property="udocsSeleccionadas" />
				</c:if>
			</display:column>
			<display:column titleKey="archigest.archivo.documentos.digitalizacion.estado.4" style="width:10">
				<c:choose>
					<c:when test="${udoc.validada}"><html:img page="/pages/images/checkbox-yes.gif" /></c:when>
					<c:otherwise><html:img page="/pages/images/checkbox-no.gif" /></c:otherwise>
				</c:choose>
			</display:column>
			<display:column titleKey="archigest.archivo.posicion" style="width:10">
				<c:if test="${udoc.posudocenui != -1}">
					<fmt:formatNumber value="${udoc.posudocenui}" pattern="000"/>
				</c:if>
			</display:column>
			<display:column titleKey="archigest.archivo.posicion.nueva" style="width:10">
					<fmt:formatNumber value="${udoc_rowNum}" pattern="000"/>				
			</display:column>						
			<display:column titleKey="archigest.archivo.signatura" style="width:15">
				<c:out value="${udoc.signaturaudoc}"/>
			</display:column>
			<display:column titleKey="archigest.archivo.identificacion" >
				<c:if test="${udoc.posudocenui != -1}">
					<c:out value="${udoc.identificacion}"/>
				</c:if>
			</display:column>			
			<display:column titleKey="archigest.archivo.titulo" property="titulo" />
			<display:column titleKey="archigest.archivo.solicitudes.numExp" property="numExp"/>
			<display:column titleKey="archigest.archivo.descripcion">
				<div id="divLabelCampoDescripcion<c:out value='${udoc_rowNum}' />" 
					>
					<bean:write name="udoc" property="descripcion"/>
					<a class="etiquetaAzul12Bold" href="javascript:modificarCampo('CampoDescripcion',<c:out value='${udoc_rowNum}' />)">
						<html:img page="/pages/images/editDoc.gif" altKey="archigest.archivo.modificar" titleKey="archigest.archivo.modificar" styleClass="imgTextMiddle" />
					</a>
				</div>
				<div id="divAceptarCancelarCampoDescripcion<c:out value='${udoc_rowNum}' />" class="hidden">
					<input type="text" id="textBoxCampoDescripcion<c:out value='${udoc_rowNum}' />"
						  class="inputCampo" maxlength="254" value="<bean:write name="udoc" property="descripcion"/>">
					<a class="etiquetaAzul12Bold" href="javascript:aceptarModificacionCampo('CampoDescripcion',<c:out value='${udoc_rowNum}' />)">
						<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
					</a>	
					<a class="etiquetaAzul12Bold" href="javascript:cancelarModificacionCampo('CampoDescripcion',<c:out value='${udoc_rowNum}' />)">
						<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
					</a>
				</div>
			</display:column>
		</display:table>
		<div class="separador8">&nbsp;</div>
	</html:form>
	</tiles:put>
</tiles:definition>

<%-- COMPOSICION DE PAGINA A MOSTRAR --%>
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<fmt:message key="archigest.archivo.transferencias.organizarCajas"/> 
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
			<c:if test="${sessionScope[appConstants.deposito.HAY_CAMBIOS_SIN_GUARDAR]}">
			<td width="10">&nbsp;</td>			
			<td>
				<a class="etiquetaAzul12Bold" href="javascript:goOn()" >
				<html:img 
					page="/pages/images/save.gif" 
					titleKey="archigest.archivo.guardar" 
					styleClass="imgTextMiddle" />
				&nbsp;<bean:message key="archigest.archivo.guardar"/>
				</a>
			</td>
			</c:if>			
			<td width="10">&nbsp;</td>									
			<TD>
				<c:url var="cancelURL" value="/action/reubicacionUdocsAction">
					<c:param name="method" value="goReturnPoint" />
				</c:url>
				<a class=etiquetaAzul12Bold href="javascript:cerrar()">
				<html:img page="/pages/images/close.gif" 
					titleKey="archigest.archivo.cerrar" 
					styleClass="imgTextMiddle" />
				&nbsp;<bean:message key="archigest.archivo.cerrar"/></a>
		   	</TD>
			
		</TR>
		</TABLE>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<tiles:insert beanName="infoUInstalacion" />
		<div class="separador8">&nbsp;</div>
		<tiles:insert beanName="listadoUdocs" />
	</tiles:put>
</tiles:insert>


