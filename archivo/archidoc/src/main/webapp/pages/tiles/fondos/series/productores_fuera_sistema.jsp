<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<html:form action="/gestionIdentificacionAction">
<input type="hidden" name="method" value="crearproductoresensistema">

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.cf.productoresNoAlta"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<TABLE cellpadding=0 cellspacing=0>
		<TR>
		   <TD>
				<a class=etiquetaAzul12Bold href="javascript:document.forms[0].submit()">
					<html:img page="/pages/images/Ok_Si.gif" border="0" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/>
				</a>
			</TD>
			<TD width="10">&nbsp;</TD>
			<TD>
				<c:url var="cancelURI" value="/action/gestionIdentificacionAction">
					<c:param name="method" value="goBack" />
				</c:url>
				<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${cancelURI}" escapeXml="false"/>'">
					<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
					&nbsp;<bean:message key="archigest.archivo.cancelar"/>
				</a>
			</TD>
		</TR>
		</TABLE>
	</tiles:put>
	
	<tiles:put name="boxContent" direct="true">

	<div class="bloque" id="divInfoFondo" > <%-- primer bloque de datos --%>

		<div class="separador8">&nbsp; <%-- 8 pixels de separacion --%></div>

		<TABLE >
			<TR >
				<td>&nbsp;</td>
			   <TD class="etiquetaAzul11Bold"><bean:message key ="archigest.archivo.cf.msg.noAsociar.procedimiento"/></TD>
			</TR>
			<TR>
				<td>&nbsp;</td>
			   <TD class="etiquetaAzul11Bold"><bean:message key="archigest.archivo.cf.msg.pulseAceptar.insercion"/></TD>
			</TR>
		</TABLE>

		<c:set var="vCaja" value="${requestScope[appConstants.transferencias.CAJA_KEY]}"/>
		<c:set var="vListaOrganos" 	value="${sessionScope[appConstants.fondos.LISTA_ORGANOS_KEY]}"/>

		<div class="separador8">&nbsp;</div>
		<div class="separador8">&nbsp;</div>

		<display:table name='pageScope.vListaOrganos' 
							style="width:99%;margin-left:auto;margin-right:auto"
							id="organo" 
							sort="list"
							export="false">

				<display:column titleKey="archigest.archivo.cf.nombre" >
					<c:out value="${organo.nombreLargo}"/>
				</display:column>

				<display:column titleKey="archigest.archivo.archivoReceptor" >
						<c:choose>
							<c:when test="${not empty sessionScope[appConstants.fondos.LISTA_ARCHIVOS_KEY][1]}">
								<SELECT id="archivoXProductor" name="archivoXProductor(<c:out value="${organo.id}"/>)" SIZE="1" >
									<c:forEach var="archivo" items="${sessionScope[appConstants.fondos.LISTA_ARCHIVOS_KEY]}" >
									<OPTION value='<c:out value="${archivo.codigo}"/>'
									<c:if test="${archivo.codigo == FondoForm.codigoarchivo}">SELECTED</c:if>
									><c:out value="${archivo.nombre}"/>
								</c:forEach>
								</SELECT>
							</c:when>
							<c:otherwise>
								<c:out value="${sessionScope[appConstants.fondos.LISTA_ARCHIVOS_KEY][0].nombre}" />
								<input type="hidden" name="archivoXProductor(<c:out value="${organo.id}"/>)" 
								value='<c:out value="${sessionScope[appConstants.fondos.LISTA_ARCHIVOS_KEY][0].codigo}"/>'>
							</c:otherwise>
						</c:choose>

				</display:column>


		</display:table>

		<div class="separador5">&nbsp;</div>
		</div>

	</tiles:put>
</tiles:insert>

</html:form>

