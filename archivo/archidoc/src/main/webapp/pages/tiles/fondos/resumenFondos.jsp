<%@ taglib uri="/WEB-INF/struts-menu-el.tld" prefix="menu" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>


	<script>
		function showHideDiv(NDiv) {
			var divN="div"+NDiv;
			var imgN="img"+NDiv;
			if ((document.getElementById(divN).style.display == 'none')|| (document.getElementById(divN).style.display == ''))
			{
			document.getElementById(divN).style.display = 'block';
			document.getElementById(imgN).src = '../images/up.gif';
			}
			else
			{
			document.getElementById(divN).style.display = 'none';
			document.getElementById(imgN).src = '../images/down.gif';
			}
		}
	</script>
		<div class="content_container">

		
		<div class="ficha">
		<h1><span>
		<div class="w100"> 
		<TABLE class="w98" cellpadding=0 cellspacing=0>
		  <TR>
		    <TD class="etiquetaAzul12Bold" height="25px">
				<bean:message key="archigest.archivo.cf.cfFondosDocumentales"/>
			</TD>
		    <TD align="right">
			<TABLE cellpadding=0 cellspacing=0>
			  <TR>
				<jsp:include page="./accionesElementoCF.jsp" flush="true" />
				<c:if test="${appConstants.configConstants.mostrarAyuda}">
					<td nowrap="nowrap">
						<c:set var="requestURI" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
						<c:set var="carpetaIdioma" value="${sessionScope[appConstants.commonConstants.LOCALEKEY]}" />
						<a class="etiquetaAzul12Bold" href="javascript:popupHelp('<c:out value="${requestURI}" />/help/<c:out value="${carpetaIdioma}"/>/fondos/fondosHome.html');">
						<html:img page="/pages/images/help.gif" 
						        altKey="archigest.archivo.ayuda" 
						        titleKey="archigest.archivo.ayuda" 
						        styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.ayuda"/></a>
					</td>
					<td width="10">&nbsp;</td>					
				</c:if>	
		       <TD>
					<c:set var="action" value="goBackTwice" />
					<c:set var="imgIcon" value="/pages/images/close.gif" />
					<c:set var="labelKey" value="archigest.archivo.cerrar" />
					
					<tiles:importAttribute name="action" ignore="true" />
					<tiles:importAttribute name="imgIcon" ignore="true" />
					<tiles:importAttribute name="labelKey" ignore="true" />
					
					<archivo:closeButton action="${action}" imgIcon="${imgIcon}" labelKey="${labelKey}" titleKey="${labelKey}" />	
			   </TD>
		     </TR>
			</TABLE>

			</TD>
		  </TR>
		</TABLE>
		</div>
		
		</span></h1>
		<div class="cuerpo_drcha">
		<div class="cuerpo_izda">
		<div id="barra_errores"><archivo:errors/></div>		
		<div class="separador1">&nbsp;</div>
		
		<DIV class="cabecero_bloque"> <%--cabecero primer bloque de datos --%>
		<TABLE class="w98m1" cellpadding=0 cellspacing=0>
		  <TBODY>
		  <TR>
		    <TD class="etiquetaAzul12Bold" width="80%">
				<bean:message key="archigest.archivo.cf.fondosDocumentales"/>
			</TD>
		    <TD width="20%" align="right">
			<TABLE cellpadding=0 cellspacing=0>
				<TR>
					<TD>
				   		<a href="javascript:showHideDiv('Fondos');">
							<html:img styleId="imgFondos" page="/pages/images/up.gif" styleClass="imgTextBottom" />
				   		</a>
					</TD>
				</TR>
			</TABLE>
			</TD>
		  </TR></TBODY></TABLE>
		</div> <%--cabecero bloque --%>
		
		<div class="bloque" id="divFondos" style="display:block;">
			<TABLE class="formulario"> 
			<c:set var="fondosBandeja" value="${requestScope[appConstants.fondos.LISTA_ELEMENTOS_BANDEJA_KEY]}"/>
			<c:forEach items="${fondosBandeja}" var="fondoBandeja">
				<TR>
					<TD class="tdTitulo">
						<c:set var="width" value="${fondoBandeja.nivelEnBandeja * 25}px"/>
						
						<html-el:img page="/pages/images/pixel.gif" width='${width}' height="1"/>

						<c:url var="urlVerFondo" value="/action/manageVistaCuadroClasificacion">
							<c:param name="actionToPerform" value="goHome"/>
							<c:param name="itemID" value="${fondoBandeja.id}"/>
						</c:url>

						<c:choose>
							<c:when test="${fondoBandeja.tipoFondo}">
								<a class="tdlinkBold" href="<c:out value="${urlVerFondo}" escapeXml="false"/>" target="_self">
									<c:out value="${fondoBandeja.codigo}"/>&nbsp;<c:out value="${fondoBandeja.titulo}"/>
								</a>
							</c:when>
							<c:otherwise>
								<a class="tdlink12" href="<c:out value="${urlVerFondo}" escapeXml="false"/>" target="_self">
									<c:out value="${fondoBandeja.codigo}"/>&nbsp;<c:out value="${fondoBandeja.titulo}"/>
								</a>
							</c:otherwise>
						</c:choose>
					</TD>
				</TR>
			</c:forEach>

			</TABLE>
		</div>
		
		
		<div class="separador5">&nbsp;</div>
	
		
		</div> <%--contenido --%>
		</div> <%--cuerpo --%>
		
		<h2><span>&nbsp;
		</span></h2>
		
		</div> <%--ficha --%>
		</div> <%--contenedor --%>
