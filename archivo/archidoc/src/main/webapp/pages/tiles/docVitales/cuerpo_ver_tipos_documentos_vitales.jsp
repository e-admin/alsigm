<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="tiposDocumentosVitales" value="${requestScope[appConstants.documentosVitales.TIPOS_DOCUMENTOS_VITALES_KEY]}"/>

<bean:struts id="actionMapping" mapping="/gestionTiposDocumentos" />

<script language="JavaScript1.2" type="text/JavaScript">
	function create(){
		<c:url var="createURL" value="/action/gestionTiposDocumentos">
				<c:param name="method" value="form" />
		</c:url>
		window.location.href = '<c:out value="${createURL}" escapeXml="false"/>';
	}
	function remove()
	{
		var form = document.forms["<c:out value="${actionMapping.name}" />"];
		if (form.idsSeleccionados && elementSelected(form.idsSeleccionados)) {
			if (confirm("<bean:message key="archigest.archivo.docvitales.tiposDocumentosVitales.delete.confirm.msg"/>"))
				form.submit();
		} else
			alert("<bean:message key="archigest.archivo.docvitales.tiposDocumentosVitales.delete.warning.msg"/>");
	}
</script>

<div id="contenedor_ficha">

<html:form action="/gestionTiposDocumentos">
<input type="hidden" name="method" value="removeTipos"/>

  <div class="ficha">
    <h1><span>
      <div class="w100">
        <table class="w98" cellpadding="0" cellspacing="0">
          <tr>
            <td class="etiquetaAzul12Bold" height="25px">
            	<bean:message key="archigest.archivo.docvitales.tiposDocumentosVitales.caption"/>
        	</td>
            <td align="right">
				<table cellpadding="0" cellspacing="0">
					<tr>
						<security:permissions action="${appConstants.documentosVitalesActions.EDICION_DOCUMENTOS_VITALES_ACTION}">
						<td nowrap>
							<a class="etiquetaAzul12Bold" 
								href="javascript:create()">
								<html:img page="/pages/images/newDoc.gif" 
								titleKey="archigest.archivo.crear" 
								altKey="archigest.archivo.crear"
								styleClass="imgTextMiddle"/>
								&nbsp;<bean:message key="archigest.archivo.crear"/>
							</a>
						</td>
						<td width="10">&nbsp;</td>
						<c:if test="${!empty tiposDocumentosVitales}">
						<td nowrap>
							<a class="etiquetaAzul12Bold" 
								href="javascript:remove()">
								<html:img page="/pages/images/deleteDoc.gif" 
								titleKey="archigest.archivo.eliminar" 
								altKey="archigest.archivo.eliminar"
								styleClass="imgTextMiddle"/>
								&nbsp;<bean:message key="archigest.archivo.eliminar"/>
							</a>
						</td>
						<td width="10">&nbsp;</td>
						</c:if>
						</security:permissions>
						<td nowrap>
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
			<display:table name="pageScope.tiposDocumentosVitales"
				style="width:99%;margin-left:auto;margin-right:auto"
				id="tipoDocumentoVital" 
				pagesize="15"
				requestURI="/action/gestionTiposDocumentos"
				export="true">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.docvitales.tiposDocumentosVitales.empty"/>
				</display:setProperty>
				<security:permissions action="${appConstants.documentosVitalesActions.EDICION_DOCUMENTOS_VITALES_ACTION}">
				<display:column headerClass="deleteFolderIcon" style="width:23px;" media="html">
					<input type="checkbox" name="idsSeleccionados" value="<c:out value="${tipoDocumentoVital.id}"/>"/>
				</display:column>
				</security:permissions>
				<display:column titleKey="archigest.archivo.docvitales.tipoDocumentoVital.nombre" sortProperty="nombre" sortable="true" headerClass="sortable" media="html">
					<c:url var="URL" value="/action/gestionTiposDocumentos">
						<c:param name="method" value="retrieve" />
						<c:param name="id" value="${tipoDocumentoVital.id}" />
					</c:url>
					<a class="tdlink" href="<c:out value="${URL}" escapeXml="false" />"><c:out value="${tipoDocumentoVital.nombre}"/></a>
				</display:column>
				<display:column titleKey="archigest.archivo.docvitales.tipoDocumentoVital.nombre" property="nombre" media="csv excel xml pdf"/>
				<display:column titleKey="archigest.archivo.docvitales.tipoDocumentoVital.descripcion" property="descripcion" maxLength="100"/>
			</display:table>  
		  </div>
	    </div>
	  </div>
    <h2><span>&nbsp;</span></h2>
  </div>
</html:form>
</div>
