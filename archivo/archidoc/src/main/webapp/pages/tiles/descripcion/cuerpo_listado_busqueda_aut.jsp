<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="autoridades" value="${requestScope[appConstants.descripcion.AUTORIDADES_KEY]}"/>

<div id="contenedor_ficha">
  <div class="ficha">
    <h1><span>
      <div class="w100">
        <table class="w98" cellpadding="0" cellspacing="0">
          <tr>
            <td class="etiquetaAzul12Bold" height="25px"><bean:message key="archigest.archivo.descripcion.listado"/></td>
            <td align="right">
              <table cellpadding="0" cellspacing="0">
                <tr>
                  <td><html:link styleClass="etiquetaAzul12Bold" action="descripcionAut?method=goBack"
                      ><html:img page="/pages/images/close.gif" 
                              altKey="archigest.archivo.cerrar" 
                              titleKey="archigest.archivo.cerrar" 
                              styleClass="imgTextMiddle" />&nbsp;<bean:message key="archigest.archivo.cerrar"/></html:link></td>
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
			<display:table name="pageScope.autoridades"
				style="width:99%;margin-left:auto;margin-right:auto"
				id="autoridad" 
				pagesize="15"
				sort="list"
				requestURI="/action/descripcionAut"
				export="true">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.descripcion.busqueda.vacia"/>
				</display:setProperty>
				<display:column titleKey="archigest.archivo.nombre" sortProperty="nombre" sortable="true" headerClass="sortable" media="html">
					<c:url var="URL" value="/action/isaar">
						<c:param name="method" value="retrieve" />
						<c:param name="id" value="${autoridad.id}" />
					</c:url>
					<a class="tdlink" href="<c:out value="${URL}" escapeXml="false" />">
					<bean:write name="autoridad" property="nombre"/></a>
				</display:column>
				<display:column titleKey="archigest.archivo.nombre" property="nombre" media="csv excel xml pdf"/>
				<display:column titleKey="archigest.archivo.lista" property="nombreLista" sortable="true" headerClass="sortable" />
			</display:table>  
		  </div>

	    </div>
	  </div>
    <h2><span>&nbsp;</span></h2>
  </div>
</div>