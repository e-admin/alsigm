<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="infoGrupo" value="${sessionScope[appConstants.controlAcceso.INFO_GRUPO]}" />
<bean:struts id="mappingGestionGrupos" mapping="/gestionGrupos" />

<script>
  <c:url var="eliminarURL" value="/action/gestionGrupos">
    <c:param name="method" value="eliminarGrupo" />
    <c:param name="idGrupo" value="${infoGrupo.id}" />
  </c:url>
  function eliminarGrupo() {
    if (confirm("<bean:message key='archigest.archivo.cacceso.msgConfirmGrupoEliminar'/>"))
      window.location = '<c:out value="${eliminarURL}" escapeXml="false"/>';
  }
</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
  <tiles:put name="boxTitle" direct="true">
    <bean:message key="archigest.archivo.cacceso.verGrupo"/>
  </tiles:put>

  <tiles:put name="buttonBar" direct="true">
    <table><tr>
    <security:permissions action="${appConstants.controlAccesoActions.MODIFICACION_GRUPO}">
    <td nowrap>
      <a class="etiquetaAzul12Bold" href="javascript:eliminarGrupo()" >
      <html:img page="/pages/images/deleteDoc.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
      <bean:message key="archigest.archivo.eliminar"/></a>
    </td>
    <td width="10px"></td>
    </security:permissions>
    <td nowrap>
      <tiles:insert definition="button.closeButton" />
    </td>
    </tr></table>
  </tiles:put>

  <tiles:put name="boxContent" direct="true">
    <tiles:insert flush="false" template="/pages/tiles/PABlockLayout.jsp">
    <tiles:put name="blockTitle" direct="true">
      <bean:message key="archigest.archivo.informacion"/>
    </tiles:put>
    <tiles:put name="buttonBar" direct="true">
    <security:permissions action="${appConstants.controlAccesoActions.MODIFICACION_GRUPO}">
      <table cellpadding=0 cellspacing=0><tr>
        <td nowrap>
          <c:url var="edicionURL" value="/action/gestionGrupos">
            <c:param name="method" value="edicionGrupo" />
            <c:param name="idGrupo" value="${infoGrupo.id}" />
          </c:url>

          <a class="etiquetaAzul12Bold" href="<c:out value="${edicionURL}" escapeXml="false"/>" >
          <html:img page="/pages/images/editDoc.gif" altKey="archigest.archivo.editar" titleKey="archigest.archivo.editar" styleClass="imgTextMiddle" />
          <bean:message key="archigest.archivo.editar"/></a>
        </td>
      </tr></table>
    </security:permissions>
    </tiles:put>

    <tiles:put name="blockContent" direct="true">
      <table class="formulario" width="99%">
        <tr>
          <td class="tdTitulo" width="400px">
            <bean:message key="archigest.archivo.nombre"/>:&nbsp;
          </td>
          <td class="tdDatos"><c:out value="${infoGrupo.nombre}" /></td>
        </tr>
      </table>
      <table class="formulario" width="99%">
        <tr>
          <td class="tdTitulo" width="400px">
            <bean:message key="archigest.archivo.descripcion"/>:&nbsp;
          </td>
          <td class="tdDatos"><c:out value="${infoGrupo.descripcion}" /></td>
        </tr>
      </table>
      <table class="formulario" width="99%">
        <tr>
          <td class="tdTitulo" width="400px">
            <c:choose>
              <c:when test="${not empty infoGrupo.archivoCustodia.nombre}">
                <bean:message key="archigest.archivo.grupos.perteneceAlArchivo"/>:&nbsp;
              </c:when>
              <c:otherwise>
                <bean:message key="archigest.archivo.grupos.noPerteneceAArchivo"/>&nbsp;
              </c:otherwise>
            </c:choose>
          </td>
          <td class="tdDatos"><c:out value="${infoGrupo.archivoCustodia.nombre}" /></td>
        </tr>
      </table>
      <c:if test="${infoGrupo.ocultarPaisProvincia || infoGrupo.ocultarArchivoCodigoClasificadores}">
      <table class="formulario" width="99%">
        <tr>
          <td class="tdTitulo" width="400px">
				<bean:message key="archigest.archivo.grupos.personalizar.codigo"/>
          </td>

          <td class="tdDatos">
			<c:if test="${infoGrupo.ocultarPaisProvincia}">			
				<html:img page="/pages/images/check-yes.gif" titleKey="archigest.archivo.grupos.pais.y.provincia" styleClass="imgTextMiddle" />
				<bean:message key="archigest.archivo.grupos.pais.y.provincia"/>&nbsp;&nbsp;
			</c:if>
			<c:if test="${infoGrupo.ocultarArchivoCodigoClasificadores}">
				<html:img page="/pages/images/check-yes.gif" titleKey="archigest.archivo.grupos.archivo.y.codigo.clasificadores" styleClass="imgTextMiddle" />
				<bean:message key="archigest.archivo.grupos.archivo.y.codigo.clasificadores"/>
			</c:if>				
		  </td>
        </tr>
      </table>
      </c:if>
    </tiles:put>
    </tiles:insert>

    <div class="separador8">&nbsp;</div>

    <tiles:insert flush="false" template="/pages/tiles/PABlockLayout.jsp">
    <tiles:put name="blockTitle" direct="true">
      <bean:message key="archigest.archivo.cacceso.usuariosGrupo"/>
    </tiles:put>
    <tiles:put name="buttonBar" direct="true">
    <security:permissions action="${appConstants.controlAccesoActions.MODIFICACION_USUARIO}">
        <table cellpadding="0" cellspacing="0"><tr>
          <td nowrap="nowrap">
            <c:url var="agregarUsuarioAGrupoURL" value="/action/gestionGrupos">
              <c:param name="method" value="busquedaUsuario" />
            </c:url>
            <a class="etiquetaAzul12Bold" href="<c:out value="${agregarUsuarioAGrupoURL}" escapeXml="false"/>" >
            <html:img page="/pages/images/addDoc.gif" altKey="archigest.archivo.anadir" titleKey="archigest.archivo.anadir" styleClass="imgTextMiddle" />
            <bean:message key="archigest.archivo.anadir"/></a>
          </td>
          <td width="10px"></td>
          <td nowrap>
            <a class="etiquetaAzul12Bold" href="javascript:document.forms['<c:out value="${mappingGestionGrupos.name}" />'].submit()" >
            <html:img page="/pages/images/delDoc.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
            <bean:message key="archigest.archivo.eliminar"/></a>
          </td>
        </tr></table>
    </security:permissions>
    </tiles:put>
    <tiles:put name="blockContent" direct="true">
      <html:form action="/gestionGrupos">
        <input type="hidden" name="method" value="quitarUsuariosDeGrupo">
        <c:set var="listaUsuarios" value="${requestScope[appConstants.controlAcceso.LISTA_USUARIOS]}" />
        <display:table name="pageScope.listaUsuarios"
            id="usuario"
            style="width:99%; margin-top:10px; margin-bottom:10px;margin-left:auto;margin-right:auto"
            sort="list"
            >
          <display:setProperty name="basic.msg.empty_list">
            <div class="separador8">&nbsp;</div>
            <bean:message key="archigest.archivo.cacceso.msgNoUsuariosGrupo"/>
            <div class="separador8">&nbsp;</div>
          </display:setProperty>
          <security:permissions action="${appConstants.controlAccesoActions.MODIFICACION_USUARIO}">
            <display:column title="" style="width:15px">
              <input type="checkbox" name="usuarioSeleccionado" value="<c:out value="${usuario.id}" />">
            </display:column>
          </security:permissions>
          <display:column titleKey="archigest.archivo.nombre">
            <c:out value="${usuario.nombre}" /> <c:out value="${usuario.apellidos}" />
          </display:column>
        </display:table>
      </html:form>
      <div style="display:none;"></div>
    </tiles:put>
    </tiles:insert>

  </tiles:put>
</tiles:insert>