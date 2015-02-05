<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<c:set var="archivos" value="${sessionScope[appConstants.deposito.LISTA_ARCHIVOS_USUARIO_KEY]}" />
<c:set var="archivoDefault" value="${sessionScope[appConstants.deposito.ARCHIVO_DEFAULT]}"/>
<tr>
	<td class="<c:out value="${classTdTituloCampo}"/>" width="<c:out value="${sizeCampo}"/>">
		<bean:message key="archigest.archivo.prestamos.archivoPrestamo"/>:&nbsp;
	</td>
	<td class="<c:out value="${classTdCampo}"/>">
		<c:choose>
			<c:when test="${!empty archivos}">
				<html:select property="idArchivo">
					<html:option key="archigest.archivo.todos" value=""/>
					<html:options collection="archivos" labelProperty="nombre" property="id" />
				</html:select>
			</c:when>
			<c:otherwise>
				<input type="text" name="nombreArchivo" value="<c:out value="${archivoDefault.value}"/>" readonly="readonly" class="inputRO95"/>
				<input type="hidden" name="idArchivo" value="<c:out value="${archivoDefault.key}"/>" class=""/>
			</c:otherwise>
		</c:choose>
	</td>
</tr>
