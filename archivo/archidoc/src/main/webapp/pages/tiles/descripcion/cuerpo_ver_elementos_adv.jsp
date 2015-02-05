<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="elementos" value="${requestScope[appConstants.descripcion.ELEMENTOS_KEY]}"/>

<script language="JavaScript1.2" type="text/JavaScript">
	function selectElement(id, name)
	{
		if (parent.selectElement)
			parent.selectElement(id, name);
	}
</script>

<div id="contenedor_ficha">
  <div class="ficha">
    <h1><span>
      <div class="w100">
        <table class="w98" cellpadding="0" cellspacing="0">
          <tr>
            <td class="etiquetaAzul12Bold" height="25px">
		  	  <bean:message key="archigest.archivo.descripcion.listado"/>
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
			<display:table name="pageScope.elementos"
				style="width:99%;margin-left:auto;margin-right:auto"
				id="elemento" 
				pagesize="10"
				sort="list"
				requestURI="/action/elementos"
				export="false">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.descripcion.busqueda.vacia"/>
				</display:setProperty>
				<display:column titleKey="archigest.archivo.cf.codReferencia" sortProperty="codReferencia" sortable="true" headerClass="sortable">
				  <a href="javascript:selectElement('<bean:write name="elemento" property="id"/>', '<bean:write name="elemento" property="titulo"/>')"
				    ><bean:write name="elemento" property="codReferencia"/></a>
				</display:column>
				<display:column titleKey="archigest.archivo.cf.titulo" property="titulo" maxLength="100" sortable="true" headerClass="sortable" />
				<display:column titleKey="archigest.archivo.cf.nivel" property="nombre" sortable="true" headerClass="sortable" />
				<display:column titleKey="archigest.archivo.fecha" property="valorFInicial" />
			</display:table>  
		 </div>
      </div><%-- cuerpo_izda --%>
    </div><%-- cuerpo_drcha --%>
    <h2><span>&nbsp;</span></h2>
  </div><%-- ficha --%>
</div><%-- contenedor_ficha --%>
