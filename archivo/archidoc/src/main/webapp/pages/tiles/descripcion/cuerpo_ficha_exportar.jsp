<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:struts id="actionMapping" mapping="/gestionFicha" />
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/gestion_ficha.js" type="text/JavaScript"></script>


<html:form action="/gestionFicha" method="POST" enctype="multipart/form-data">
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
<tiles:put name="boxTitle" direct="true">
	<bean:message key="archigest.archivo.descripcion.fichas.form.exportar.definicion"/>
</tiles:put>
	
<tiles:put name="buttonBar" direct="true">
	
<table cellpadding=0 cellspacing=0>
	<tr>
	   	<td>
				
			<a class="etiquetaAzul12Bold" 
			  href="javascript:acceptExport(document.forms['fichasForm'],'exportAccept','El campo nombre no puede estar vacío')"
			>
	
			<html:img page="/pages/images/Ok_Si.gif" 
			        border="0" 
			        altKey="archigest.archivo.aceptar" 
			        titleKey="archigest.archivo.aceptar"
			        onclick="javascript:acceptImport(document.forms['fichasForm'],'importAccept','El campo nombre no puede estar vacío')" 
			        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.aceptar"/></a>
			        
		</td>
	   	
	   	<td width="10">&nbsp;</td>
	   	<td>
			<a class="etiquetaAzul12Bold" 
			   href="javascript:call(document.forms['fichasForm'],'exportCancel')"
			><html:img page="/pages/images/Ok_No.gif" 
			        border="0" 
			        altKey="archigest.archivo.cancelar" 
			        titleKey="archigest.archivo.cancelar"
			        
			        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.cancelar"/></a>
		</td>
	</tr>
</table>
</tiles:put>
<tiles:put name="boxContent" direct="true">

<div class="bloque"><%-- bloque datos tabla --%>
	<table class="formulario">
		
		<tr height="10"><td>&nbsp;</td></tr>
		<tr>
			<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion.fichas.form.nombre"/>:&nbsp;</td>
			<td class="tdDatos">
					<html:file property="fichero" />
			</td>
		</tr>
		<tr height="10"><td>&nbsp;</td></tr>
		
	</table>
</div><%-- bloque datos tabla --%>

</tiles:put>
</tiles:insert>
</html:form>
</div>