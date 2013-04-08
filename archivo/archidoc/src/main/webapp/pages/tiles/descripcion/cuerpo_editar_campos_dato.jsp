<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


<c:set var="fromCampoLista" value="${sessionScope.fromCampoLista}" />
<bean:struts id="actionMapping" mapping="/gestionCampoDato" />

<c:set var="formBean" value="${sessionScope[actionMapping.name]}" />

<script language="JavaScript1.2" type="text/JavaScript">

function create()
{
	if(document.forms["<c:out value="${actionMapping.name}" />"].id.value==null
	   || trim(document.forms["<c:out value="${actionMapping.name}" />"].id.value)=="")
		document.forms["<c:out value="${actionMapping.name}" />"].action+="?method=create";
	else document.forms["<c:out value="${actionMapping.name}" />"].action+="?method=update";

	document.forms["<c:out value="${actionMapping.name}" />"].submit();
}

</script>
<html:form action="/gestionCampoDato">
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
<tiles:put name="boxTitle" direct="true">
	<bean:message key="archigest.archivo.cacceso.gestionCampo"/>
</tiles:put>

<tiles:put name="buttonBar" direct="true">
	<table>
		<tr>
		   	<td>
				<a class="etiquetaAzul12Bold"
				   href="javascript:create()"
				><input type="image"
						src="../images/Ok_Si.gif"
				        alt="<bean:message key="archigest.archivo.aceptar"/>"
				        title="<bean:message key="archigest.archivo.aceptar"/>"
				        class="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.aceptar"/></a>
			</td>
			<td width="10">&nbsp;</td>
		   	<td>
				<a class="etiquetaAzul12Bold"
				   href="javascript:call(document.forms['camposDatoForm'],'goBack')"
				><html:img page="/pages/images/Ok_No.gif"
				        border="0"
				        altKey="archigest.archivo.cancelar"
				        titleKey="archigest.archivo.cancelar"
				        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.cancelar"/></a>
			</td>
		</tr>
	</table>
</tiles:put>

<c:url var="paginationURL" value="/action/gestionCampoDato" />
<jsp:useBean id="paginationURL" type="java.lang.String" />
<tiles:put name="boxContent" direct="true">
	<div id="barra_errores"><archivo:errors /></div>
	<div class="bloque"><%-- bloque datos tabla --%>
				<table class="formulario">

				<tr>
					<td class="tdTitulo" width="150"><bean:message
						key="archigest.archivo.identificador" />:&nbsp;</td>
					<td class="tdDatos"><html:hidden property="id" /> <c:choose>
						<c:when test="${empty formBean.id}">
							<html:text property="guid" maxlength="32" size="40" />
						</c:when>
						<c:otherwise>
							<c:out value="${formBean.id}" />
						</c:otherwise>
					</c:choose></td>
				</tr>



					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion.camposDato.form.nombre"/>:&nbsp;</td>
						<td class="tdDatos"><html:text property="nombre" size="64" maxlength="64"/>&nbsp;</td>
					</tr>

					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion.camposDato.form.tipo"/>:&nbsp;</td>
						<td class="tdDatos">
							<html:select property="tipo" styleClass="input">
								<html:optionsCollection name="DescripcionConstants_LISTA_TIPOS_CAMPO_KEY" value="value" label="label" />
							</html:select>
						</td>
					</tr>

					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion.camposDato.form.tipoNorma"/>:&nbsp;</td>
						<td class="tdDatos">
							<c:choose>
								<c:when test="${fromCampoLista==true}">
									<html:select property="tipoNorma" styleClass="input" onchange="javascript:changeTipoNorma(document.forms['camposDatoForm']);" disabled="true">
										<html:optionsCollection name="DescripcionConstants_LISTA_TIPO_NORMAS_KEY" value="value" label="label"/>
									</html:select>
								</c:when>
								<c:otherwise>
									<html:select property="tipoNorma" styleClass="input" onchange="javascript:changeTipoNorma(document.forms['camposDatoForm']);">
										<html:optionsCollection name="DescripcionConstants_LISTA_TIPO_NORMAS_KEY" value="value" label="label"/>
									</html:select>
								</c:otherwise>
							</c:choose>


						</td>
					</tr>

					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion.camposDato.form.area"/>:&nbsp;</td>
						<td class="tdDatos">
							<c:choose>
								<c:when test="${fromCampoLista==true}">
									<html:select property="idArea" styleClass="input" disabled="true">
										<html:optionsCollection name="DescripcionConstants_LISTA_AREAS_KEY" value="id" label="nombre" />
									</html:select>
								</c:when>
								<c:otherwise>
									<html:select property="idArea" styleClass="input">
										<html:optionsCollection name="DescripcionConstants_LISTA_AREAS_KEY" value="id" label="nombre" />
									</html:select>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>

					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion.camposDato.form.etiquetaXml"/>:&nbsp;</td>
						<td class="tdDatos"><html:text property="etiquetaXml" size="64" maxlength="64"/>&nbsp;</td>
					</tr>

					<tr>
						<td class="tdTitulo" width="150"><bean:message key="archigest.archivo.descripcion.camposDato.form.descripcion"/>:&nbsp;</td>
						<td class="tdDatos"><html:textarea property="descripcion" rows="3" onchange="maxlength(this,254,true)" onkeypress="maxlength(this,254,false)" />&nbsp;</td>
					</tr>

				</table>
			</div><%-- bloque datos tabla --%>
</tiles:put>
</tiles:insert>
</html:form>