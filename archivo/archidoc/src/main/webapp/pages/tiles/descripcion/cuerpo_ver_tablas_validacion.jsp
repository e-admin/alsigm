<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="tablas" value="${requestScope[appConstants.descripcion.TABLAS_VALIDACION_KEY]}"/>
<bean:struts id="actionMapping" mapping="/tablasVld" />

<script language="JavaScript1.2" src="../../js/utils.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" type="text/JavaScript">
	function create(){
		<c:url var="createURL" value="/action/tablasVld">
				<c:param name="method" value="form" />
		</c:url>
		window.location.href = '<c:out value="${createURL}" escapeXml="false"/>';
	}
	
	function remove()
	{
		var form = document.forms["<c:out value="${actionMapping.name}" />"];
		if (form.eliminar && elementSelected(form.eliminar)) {
			if (confirm("<bean:message key="archigest.archivo.descripcion.tablasValidacion.delete.confirm.msg"/>"))
				form.submit();
		} else
			alert("<bean:message key='archigest.archivo.descripcion.tablasValidacion.delete.warning.msg'/>");
	}
	
	function close(){
		<c:url var="closeURL" value="/action/tablasVld">
				<c:param name="method" value="goBack" />
		</c:url>
		window.location.href = '<c:out value="${closeURL}" escapeXml="false"/>';
	}
</script>

<div id="contenedor_ficha">
  <div class="ficha">
    <html:form action="tablasVld">
    <input type="hidden" name="method" value="remove"/>
    <h1><span>
      <div class="w100">
        <table class="w98" cellpadding="0" cellspacing="0">
          <tr>
            <td class="etiquetaAzul12Bold" height="25px">
              <bean:message key="archigest.archivo.descripcion.tablasValidacion.caption"/>
            </td>
            <td align="right">
              <table cellpadding="0" cellspacing="0">
                <tr>
                  <security:permissions action="${appConstants.descripcionActions.ADMINISTRAR_TABLAS_VALIDACION_ACTION}">
                  <td><a class="etiquetaAzul12Bold" 
                         href="javascript:create()"
                      ><html:img page="/pages/images/new.gif" 
                              altKey="archigest.archivo.crear" 
                              titleKey="archigest.archivo.crear" 
                              styleClass="imgTextMiddle" />&nbsp;<bean:message key="archigest.archivo.crear"/></a></td>
                  <td width="10"></td>
                  <td><a class="etiquetaAzul12Bold" 
                         href="javascript:remove()"
                      ><html:img page="/pages/images/delete.gif" 
                              altKey="archigest.archivo.eliminar" 
                              titleKey="archigest.archivo.eliminar" 
                              styleClass="imgTextMiddle" />&nbsp;<bean:message key="archigest.archivo.eliminar"/></a></td>
                  <td width="10"></td>
                  </security:permissions>
                  <td><a class="etiquetaAzul12Bold" 
                         href="javascript:close()"
                      ><html:img page="/pages/images/close.gif" 
                              altKey="archigest.archivo.cerrar" 
                              titleKey="archigest.archivo.cerrar" 
                              styleClass="imgTextMiddle" />&nbsp;<bean:message key="archigest.archivo.cerrar"/></a></td>
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

			<display:table name="pageScope.tablas"
				style="width:99%;margin-left:auto;margin-right:auto"
				id="tabla" 
				pagesize="15"
				requestURI="/action/tablasVld"
				export="true">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.descripcion.tablasValidacion.listaVacia"/>
				</display:setProperty>
				<security:permissions action="${appConstants.descripcionActions.ADMINISTRAR_TABLAS_VALIDACION_ACTION}">
				<display:column style="width:23px" headerClass="deleteFolderIcon" media="html">
					<c:if test="${!tabla.tablaDeSistema}">
					<input type="checkbox" name="eliminar" value="<c:out value="${tabla.id}"/>"/>
					</c:if>
				</display:column>
				</security:permissions>
				<display:column titleKey="archigest.archivo.nombre" sortProperty="nombre" sortable="true" headerClass="sortable" media="html">
					<c:url var="URL" value="/action/tablasVld">
						<c:param name="method" value="retrieve" />
						<c:param name="id" value="${tabla.id}" />
					</c:url>
					<a class="tdlink" href="<c:out value="${URL}" escapeXml="false" />"><c:out value="${tabla.nombre}"/></a>
				</display:column>
				<display:column titleKey="archigest.archivo.nombre" property="nombre" media="csv excel xml pdf"/>
				<display:column titleKey="archigest.archivo.descripcion" property="descripcion" maxLength="100"/>
			</display:table>  
		 </div>
      </div><%--cuerpo_izda --%>
    </div><%--cuerpo_drcha --%>
    <h2><span>&nbsp;<br/></span></h2>
    </html:form>
  </div><%--ficha --%>
</div><%--contenedor_ficha --%>
