<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="listasDescriptoras" value="${requestScope[appConstants.descripcion.LISTAS_DESCRIPTORAS_KEY]}"/>
<bean:struts id="actionMapping" mapping="/listasDescriptoras" />

<script language="JavaScript1.2" src="../../js/utils.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" type="text/JavaScript">
	function create(){
		<c:url var="createURL" value="/action/listasDescriptoras">
				<c:param name="method" value="form" />
		</c:url>
		window.location.href = '<c:out value="${createURL}" escapeXml="false"/>';
	}
	function remove()
	{
		var form = document.forms["<c:out value="${actionMapping.name}" />"];
		if (form.ids && elementSelected(form.ids)) {
			if (confirm("<bean:message key="archigest.archivo.descripcion.listasDescriptoras.delete.confirm.msg"/>"))
				form.submit();
		} else
			alert("<bean:message key='archigest.archivo.descripcion.listasDescriptoras.delete.warning.msg'/>");
	}

	function close(){
		<c:url var="closeURL" value="/action/listasDescriptoras">
				<c:param name="method" value="goBack" />
		</c:url>
		window.location.href = '<c:out value="${closeURL}" escapeXml="false"/>';
	}
</script>

<div id="contenedor_ficha">
  <div class="ficha">
    <html:form action="listasDescriptoras">
    <input type="hidden" name="method" value="remove"/>
    <h1><span>
      <div class="w100">
        <table class="w98" cellpadding="0" cellspacing="0">
          <tr>
            <td class="etiquetaAzul12Bold" height="25px">
              <bean:message key="archigest.archivo.descripcion.listasDescriptoras.caption"/>
            </td>
            <td align="right">
              <table cellpadding="0" cellspacing="0">
                <tr>
                  <security:permissions action="${appConstants.descripcionActions.ADMINISTRAR_DESCRIPTORES_ACTION}">
                  <td><a class="etiquetaAzul12Bold"
                         href="javascript:create()"
                      ><html:img page="/pages/images/new.gif"
                              altKey="archigest.archivo.crear"
                              titleKey="archigest.archivo.crear"
                              styleClass="imgTextMiddle" />&nbsp;<bean:message key="archigest.archivo.crear"/></a></td>
                  <td width="10">&nbsp;</td>
                  <td><a class="etiquetaAzul12Bold"
                         href="javascript:remove()"
                      ><html:img page="/pages/images/delete.gif"
                              altKey="archigest.archivo.eliminar"
                              titleKey="archigest.archivo.eliminar"
                              styleClass="imgTextMiddle" />&nbsp;<bean:message key="archigest.archivo.eliminar"/></a></td>
                  <td width="10">&nbsp;</td>
                  </security:permissions>
					<td>
						<c:url var="busquedaDescriptoresURL" value="/action/descriptor">
							<c:param name="method" value="verBuscador" />
						</c:url>
						<a class="etiquetaAzul12Bold"
							href="<c:out value="${busquedaDescriptoresURL}" escapeXml="false"/>" >
							<html:img
								page="/pages/images/buscar_go.gif"
								altKey="archigest.archivo.buscar"
								titleKey="archigest.archivo.buscar"
								styleClass="imgTextMiddle" />
							<bean:message key="archigest.archivo.buscar"/></a>
					</td>
					<td width="10px"></td>
					<td nowrap>
						<tiles:insert definition="button.closeButton" flush="true"/>
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
			<div class="separador1">&nbsp;</div>

			<display:table name="pageScope.listasDescriptoras"
				style="width:99%;margin-left:auto;margin-right:auto"
				id="listaDescriptora"
				pagesize="15"
				requestURI="/action/listasDescriptoras"
				export="true">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.descripcion.listasDescriptoras.listaVacia"/>
				</display:setProperty>
				<security:permissions action="${appConstants.descripcionActions.ADMINISTRAR_DESCRIPTORES_ACTION}">
				<display:column headerClass="deleteFolderIcon" style="width:23px;" media="html">
					<input type="checkbox" name="ids" value="<c:out value="${listaDescriptora.id}"/>#<c:out value="${listaDescriptora.nombre}"/>" >
				</display:column>
				</security:permissions>
				<display:column titleKey="archigest.archivo.nombre" sortProperty="nombre" sortable="true" headerClass="sortable" media="html">
					<c:url var="URL" value="/action/listasDescriptoras">
						<c:param name="method" value="retrieve" />
						<c:param name="id" value="${listaDescriptora.id}" />
					</c:url>
					<a class="tdlink" href="<c:out value="${URL}" escapeXml="false" />"><c:out value="${listaDescriptora.nombre}"/></a>
				</display:column>
				<display:column titleKey="archigest.archivo.nombre" property="nombre" media="csv excel xml pdf"/>
				<display:column titleKey="archigest.archivo.tipo" sortable="true" headerClass="sortable" style="width:50px;">
					<c:choose>
						<c:when test="${listaDescriptora.tipo == 1}">
							<bean:message key="archivo.listaDescriptora.tipo.abierta"/>
						</c:when>
						<c:when test="${listaDescriptora.tipo == 2}">
							<bean:message key="archivo.listaDescriptora.tipo.cerrada"/>
						</c:when>
						<c:otherwise></c:otherwise>
					</c:choose>
				</display:column>
				<display:column titleKey="archigest.archivo.descripcion.descriptor.form.tipo" sortable="true" headerClass="sortable" style="width:50px;">
					<c:choose>
						<c:when test="${listaDescriptora.tipoDescriptor == 0}">
							<bean:message key="archivo.descriptores.tipo.sin_tipo_especifico"/>
						</c:when>
						<c:when test="${listaDescriptora.tipoDescriptor == 1}">
							<bean:message key="archivo.descriptores.tipo.entidad"/>
						</c:when>
						<c:when test="${listaDescriptora.tipoDescriptor == 2}">
							<bean:message key="archivo.descriptores.tipo.geografico"/>
						</c:when>
						<c:when test="${listaDescriptora.tipoDescriptor == 3}">
							<bean:message key="archivo.descriptores.tipo.materia"/>
						</c:when>
						<c:otherwise></c:otherwise>
					</c:choose>
				</display:column>
				<display:column titleKey="archigest.archivo.descripcion.descriptor.form.fichasDescr" property="nombreFichaDescrPref" sortable="true" headerClass="sortable" style="width:100px;"/>
				<display:column titleKey="archigest.archivo.descripcion.fichaClasifDoc" property="nombreFichaClfDocPref" sortable="true" headerClass="sortable" style="width:120px;"/>
				<display:column titleKey="archigest.archivo.repositorio.ecm" property="nombreRepEcmPref" sortable="true" headerClass="sortable" style="width:100px;"/>
				<display:column titleKey="archigest.archivo.descripcion" property="descripcion" maxLength="100"/>
			</display:table>
		 </div>
      </div><%--cuerpo_izda --%>
    </div><%--cuerpo_drcha --%>
    <h2><span>&nbsp;<br/></span></h2>
    </html:form>
  </div><%--ficha --%>
</div><%--contenedor_ficha --%>
