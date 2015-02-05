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

<html:form action="/gestionFormatoFicha" method="POST" enctype="multipart/form-data">
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
<tiles:put name="boxTitle" direct="true">
	<bean:message key="archigest.archivo.descripcion.formatos.fichas.form.importar.definicion"/>
</tiles:put>
	
<tiles:put name="buttonBar" direct="true">
	
<table cellpadding=0 cellspacing=0>
	<tr>
	   	<td>
			<a class="etiquetaAzul12Bold" 
			  href="javascript:acceptImport(document.forms['formatoFichasForm'],'importAccept','<bean:message key='archigest.archivo.descripcion.fichas.form.nombre.empty.msg'/>')"
			>

			<c:url var="imgAceptarURL" value="/pages/images/Ok_Si.gif"/>
			<img src="<c:out value='${imgAceptarURL}'/>" style="border:0"
			     alt="<bean:message key="archigest.archivo.aceptar"/>" 
			     title="<bean:message key="archigest.archivo.aceptar"/>"
			     onclick="javascript:acceptImport(document.forms['formatoFichasForm'],'importAccept','<bean:message key='archigest.archivo.descripcion.fichas.form.nombre.empty.msg'/>')" 
			     class="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.aceptar"/></a>
			        
		</td>
	   	
	   	<td width="10">&nbsp;</td>
	   	<td>
			<a class="etiquetaAzul12Bold" 
			   href="javascript:call(document.forms['formatoFichasForm'],'importCancel')"
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
			<td class="tdTitulo"><bean:message key="archigest.archivo.descripcion.fichas.form.nombre"/>:
					<html:file size="60" property="fichero" />
			</td>
		</tr>
		<tr height="10"><td>&nbsp;</td></tr>
		
	</table>
</div><%-- bloque datos tabla --%>

</tiles:put>
</tiles:insert>
</html:form>
</div>