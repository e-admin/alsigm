<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.prestamos.gestion"/></tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td nowrap>
					<tiles:insert definition="button.closeButton" />
				</td>
			</tr>
		</table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">

		<security:permissions permission="${appConstants.permissions.ESTANDAR_SOLICITUD_PRESTAMOS}|${appConstants.permissions.AMPLIADO_SOLICITUD_PRESTAMOS}">
		<%--PRESTAMOS EN ELABORACION --%>
		<c:set var="prestamosEnElaboracion" value="${requestScope[appConstants.prestamos.LISTA_PRESTAMOS_EN_ELABORACION_KEY]}"/>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.prestamos.enElaboracion"/></tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<table cellpadding="0" cellspacing="0">
					<tr>
						<td nowrap>
						<c:url var="urlCrearPrestamo" value="/action/gestionPrestamos">
							<c:param name="method" value="nuevo"/>
						</c:url>
						<a class="etiquetaAzul12Bold" href='<c:out value="${urlCrearPrestamo}" escapeXml="false"/>'>
							<html:img 
								titleKey="archigest.archivo.prestamos.crear" 
								altKey="archigest.archivo.prestamos.crear" 
								page="/pages/images/new.gif" styleClass="imgTextMiddle"/>
							&nbsp;<bean:message key="archigest.archivo.prestamos.crear"/>
						</a>
						</td>
					</tr>
				</table>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
			<div class="separador8"></div>
			<display:table name="pageScope.prestamosEnElaboracion"
				style="width:99%;margin-left:auto;margin-right:auto"
				id="prestamo" 
				export="false"
				defaultsort="1"
				>
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.prestamos.noPrev"/>
				</display:setProperty>

			
				<display:column titleKey="archigest.archivo.prestamos.prestamo">
					<c:url var="verPrestamoURL" value="/action/gestionPrestamos">
						<c:param name="method" value="verprestamo" />
						<c:param name="id" value="${prestamo.id}" />
					</c:url>
					<a class="tdlink" href="<c:out value="${verPrestamoURL}" escapeXml="false"/>" >
						<c:out value="${prestamo.codigoTransferencia}"/>
					</a>
				</display:column>

				<display:column titleKey="archigest.archivo.prestamos.norgsolicitante" property="norgsolicitante" />
				<display:column titleKey="archigest.archivo.prestamos.nusrsolicitante" property="nusrsolicitante"  />
					
				<display:column titleKey="archigest.archivo.prestamos.estado" >
						<c:set var="keyEstado">
							archigest.archivo.solicitudes.estado.<bean:write name="prestamo" property="estado"/>
						</c:set>
						<fmt:message key="${keyEstado}" />
				</display:column>

				<display:column titleKey="archigest.archivo.prestamos.festado">
					<fmt:formatDate value="${prestamo.festado}" pattern="${FORMATO_FECHA}" />
				</display:column>
			
				<display:column titleKey="archigest.archivo.prestamos.fPrevDev">
					<fmt:formatDate value="${prestamo.fmaxfinprestamo}" pattern="${FORMATO_FECHA}" />
				</display:column>

				<display:column titleKey="archigest.archivo.prestamos.reclamaciones">
					<fmt:formatDate value="${prestamo.freclamacion1}" pattern="${FORMATO_FECHA}"/>
					<br>
					<fmt:formatDate value="${prestamo.freclamacion2}" pattern="${FORMATO_FECHA}"/>
				</display:column>
				
			</display:table>  
			<div class="separador8"></div>
			</tiles:put>
		</tiles:insert>
		<c:if test="${!empty prestamosEnElaboracion}">
		<div class="pie_bloque_right">
			<c:url var="prestamosEnElaboracionURL" value="/action/gestionPrestamos">
				<c:param name="method" value="listadoprestamosver" />
			</c:url>
			<a class="etiq_pie_bloque" href="<c:out value="${prestamosEnElaboracionURL}" escapeXml="false"/>">
				<html:img page="/pages/images/go_all.gif" styleClass="imgTextMiddle" /> <bean:message key="archigest.archivo.verMas"/>
			</a>
		</div>
		<div style="height:12px"></div>
		</c:if>

		<div class="separador20"></div>
		</security:permissions>

		<security:permissions permission="${appConstants.permissions.GESTION_PRESTAMOS_ARCHIVO}">
		<%--PRESTAMOS A GESTIONAR --%>
		<c:set var="prestamosAGestionar" value="${requestScope[appConstants.prestamos.LISTA_PRESTAMOS_A_GESTIONAR_KEY]}"/>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.prestamos.aGestionar"/></tiles:put>
			<tiles:put name="blockContent" direct="true">
			<div class="separador8"></div>
			<display:table name="pageScope.prestamosAGestionar"
				id="prestamo" 
				export="false"
				style="width:99%;margin-left:auto;margin-right:auto"
				defaultsort="1"
				>

				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.prestamos.noPrev"/>
				</display:setProperty>
				
				<display:column titleKey="archigest.archivo.prestamos.prestamo">
					<c:url var="verPrestamoURL" value="/action/gestionPrestamos">
						<c:param name="method" value="verprestamo" />
						<c:param name="id" value="${prestamo.id}" />
					</c:url>

					<a class="tdlink" href="<c:out value="${verPrestamoURL}" escapeXml="false"/>" >
						<c:out value="${prestamo.codigoTransferencia}"/>
					</a>
				</display:column>
				<display:column titleKey="archigest.archivo.prestamos.norgsolicitante"  property="norgsolicitante" />			
				<display:column titleKey="archigest.archivo.prestamos.nusrsolicitante" property="nusrsolicitante" />
				<display:column titleKey="archigest.archivo.prestamos.estado" >
						<c:set var="keyEstado">
							archigest.archivo.solicitudes.estado.<bean:write name="prestamo" property="estado"/>
						</c:set>
						<fmt:message key="${keyEstado}" />
				</display:column>
				<display:column titleKey="archigest.archivo.prestamos.festado">
					<fmt:formatDate value="${prestamo.festado}" pattern="${FORMATO_FECHA}" />
				</display:column>
				<display:column titleKey="archigest.archivo.prestamos.fPrevDev">
					<fmt:formatDate value="${prestamo.fmaxfinprestamo}" pattern="${FORMATO_FECHA}" />
				</display:column>
			</display:table>
			<div class="separador8"></div>
			</tiles:put>
		</tiles:insert>
		<c:if test="${!empty prestamosAGestionar}">
		<div class="pie_bloque_right">
			<c:url var="prestamosAGestionarURL" value="/action/gestionPrestamos">
				<c:param name="method" value="listadoprestamos" />
			</c:url>
			<a class="etiq_pie_bloque" href="<c:out value="${prestamosAGestionarURL}" escapeXml="false"/>">
				<html:img page="/pages/images/go_all.gif" styleClass="imgTextMiddle" /> <bean:message key="archigest.archivo.verMas"/>
			</a>
		</div>
		<div style="height:12px"></div>
		</c:if>

		<div class="separador20"></div>
		</security:permissions>

		<security:permissions permission="${appConstants.permissions.ENTREGA_UNIDADES_DOCUMENTALES}">
		<%--PRESTAMOS A ENTREGAR --%>
		<c:set var="prestamosAEntregar" value="${requestScope[appConstants.prestamos.LISTA_PRESTAMOS_A_ENTREGAR_KEY]}"/>

		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.prestamos.aEntregar"/></tiles:put>
			<tiles:put name="blockContent" direct="true">
			<div class="separador8"></div>

			<display:table name="pageScope.prestamosAEntregar"
				id="prestamo" 
				export="false"
				defaultsort="1"
				style="width:99%;margin-left:auto;margin-right:auto">

				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.prestamos.noPrev"/>
				</display:setProperty>
				
				<display:column titleKey="archigest.archivo.prestamos.prestamo">
					<c:url var="verPrestamoURL" value="/action/gestionPrestamos">
						<c:param name="method" value="verprestamo" />
						<c:param name="id" value="${prestamo.id}" />
					</c:url>

					<a class="tdlink" href="<c:out value="${verPrestamoURL}" escapeXml="false"/>" >
						<c:out value="${prestamo.codigoTransferencia}"/>
					</a>
				</display:column>

				<display:column titleKey="archigest.archivo.prestamos.norgsolicitante"  property="norgsolicitante" />			
				<display:column titleKey="archigest.archivo.prestamos.nusrsolicitante" property="nusrsolicitante" />
				<display:column titleKey="archigest.archivo.prestamos.festado">
					<fmt:formatDate value="${prestamo.festado}" pattern="${FORMATO_FECHA}" />
				</display:column>
			
				<display:column titleKey="archigest.archivo.prestamos.fPrevDev">
					<fmt:formatDate value="${prestamo.fmaxfinprestamo}" pattern="${FORMATO_FECHA}" />
				</display:column>
			</display:table>
			<div class="separador8"></div>
			</tiles:put>
		</tiles:insert>
		<c:if test="${!empty prestamosAEntregar}">
		<div class="pie_bloque_right">
			<c:url var="prestamosAEntregarURL" value="/action/gestionPrestamos">
				<c:param name="method" value="listadoprestamosentregar" />
			</c:url>
			<a class="etiq_pie_bloque" href="<c:out value="${prestamosAEntregarURL}" escapeXml="false"/>">
				<html:img page="/pages/images/go_all.gif" styleClass="imgTextMiddle" /> <bean:message key="archigest.archivo.verMas"/>
			</a>
		</div>
		<div style="height:12px"></div>
		</c:if>
		<div class="separador20"></div>
		</security:permissions>
		
	</tiles:put>
</tiles:insert>