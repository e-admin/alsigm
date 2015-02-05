<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="listas" value="${sessionScope[appConstants.descripcion.LISTAS_DESCRIPTORAS_KEY]}"/>
<c:set var="descriptores" value="${requestScope[appConstants.descripcion.DESCRIPTORES_LISTA_KEY]}"/>
<bean:struts id="mapping" mapping="/descriptores" />

<script language="JavaScript1.2" type="text/JavaScript">
	function selectDescriptor(id, nombre)
	{
		if (parent.selectDescriptor)
			parent.selectDescriptor(id, nombre)
	}
	function search()
	{
		document.forms["<c:out value="${mapping.name}" />"].submit();
	}
</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="archigest.archivo.descripcion.descriptores.caption"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">	
      <table cellpadding="0" cellspacing="0">
        <tr>
          <td><a class="etiquetaAzul12Bold"
                 href="javascript:search()"
              ><html:img page="/pages/images/buscar.gif" 
                      altKey="archigest.archivo.buscar" 
                      titleKey="archigest.archivo.buscar"
                      styleClass="imgTextMiddle" />
               &nbsp;<bean:message key="archigest.archivo.buscar"/></a></td>
          <td width="10">&nbsp;</td>
        </tr>
      </table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
		<html:form action="/descriptores">
			<input type="hidden" name="method" value="buscar"/>
			<div class="bloque">
				<table class="w98">
					<tr>
						<td class="etiquetaAzul11Bold" width="120px">
							<bean:message key="archigest.archivo.descripcion.descriptor.form.nombreLista"/>:&nbsp;
						</td>
						<td>
							<html:select property="idLista">
								<html:optionsCollection name="listas" label="nombre" value="id"/>
							</html:select>
						</td>
					</tr>
					<tr>
						<td class="etiquetaAzul11Bold" width="120px">
							<bean:message key="archigest.archivo.descripcion.descriptor.form.nombre"/>:&nbsp;
						</td>
						<td>
							<html:text size="80" property="nombre"/>
						</td>
					</tr>
				</table>
				   
				<c:if test="${descriptores != null}">
				<display:table name="pageScope.descriptores"
					style="width:99%;margin-left:auto;margin-right:auto"
					id="descriptor" 
					pagesize="10"
					sort="list"
					requestURI="/action/descriptores"
					export="false">
					<display:setProperty name="basic.msg.empty_list">
					  <bean:message key="archigest.archivo.descripcion.descriptores.vacio"/>
					</display:setProperty>
					<display:column titleKey="archigest.archivo.nombre" sortProperty="nombre" sortable="true" headerClass="sortable">
						<a href="javascript:selectDescriptor('<c:out value="${descriptor.id}"/>', '<c:out value="${descriptor.nombre}"/>')">
							<c:out value="${descriptor.nombre}"/>
						</a>
				    </display:column>
				</display:table>  
				</c:if>
			</div>
		</html:form>	
	</tiles:put>
</tiles:insert>	