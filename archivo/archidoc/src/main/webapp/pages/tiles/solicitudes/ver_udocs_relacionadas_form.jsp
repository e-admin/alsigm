<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>

<c:set var="udocs" value="${sessionScope[appConstants.solicitudes.LISTA_UDOCS_RELACIONADAS]}" />

<html-el:form action="${actionPath}" >

	<input type="hidden" name="method" value="<c:out value="${actionMethod}"/>">

  		<div class="ficha">
	    <h1><span>
	      <div class="w100">
	        <table class="w98" cellpadding="0" cellspacing="0">
	        	<tr>
				    <td class="etiquetaAzul12Bold" height="25px">
				  	  <bean:message key="archigest.archivo.solicitudes.anadirUDocsRelacionadas"/>
		            </td>
					<td align="right">
	              		<table cellpadding="0" cellspacing="0">
	              			<tr>
								<c:if test="${!empty udocs}">
									<td>
										<a class="etiquetaAzul12Bold" href="javascript:aceptar()" >
											<html:img
												page="/pages/images/Ok_Si.gif" border="0"
												altKey="archigest.archivo.aceptar"
												titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
											&nbsp;<bean:message key="archigest.archivo.aceptar"/>
										</a>
									</td>
								</c:if>
								<TD width="10">&nbsp;</TD>
				                  <td><a class="etiquetaAzul12Bold"
				                         href="javascript:window.close();"
				                      ><html:img page="/pages/images/close.gif"
				                              altKey="archigest.archivo.cerrar"
				                              titleKey="archigest.archivo.cerrar"
				                              styleClass="imgTextMiddle" />&nbsp;<bean:message key="archigest.archivo.cerrar"/></a></td>
							</tr>
						</table>
					</TD>
			 </TR>
			</TABLE>
			</div>
		    </span></h1>



		    <div class="cuerpo_drcha">
				<div class="cuerpo_izda">

				 <div class="bloque">
				 	<c:if test="${!empty udocs}">
						<table class="formulario">
						<TR><TD align="right">
							<TABLE cellpadding=0 cellspacing=0>
							<TR>
								<TD>
									<a class="etiquetaAzul12Normal" href="javascript:seleccionarCheckboxSet(document.forms[0].detallesseleccionados);" >
										<html:img
											page="/pages/images/checked.gif" border="0"
											altKey="archigest.archivo.selTodos"
											titleKey="archigest.archivo.selTodos" styleClass="imgTextBottom" />
										<bean:message key="archigest.archivo.selTodos"/>&nbsp;
									</a>
								</TD>
								<TD width="20">&nbsp;</TD>
								<TD>
									<a class="etiquetaAzul12Normal" href="javascript:deseleccionarCheckboxSet(document.forms[0].detallesseleccionados);" >
										<html:img
											page="/pages/images/check.gif" border="0"
											altKey="archigest.archivo.quitarSel"
											titleKey="archigest.archivo.quitarSel" styleClass="imgTextBottom" />
										&nbsp;<bean:message key="archigest.archivo.quitarSel"/>
									</a>
								</TD>
								<TD width="10">&nbsp;</TD>
							</TR>
							</TABLE>
						</TD></TR>
						</TABLE>
					</c:if>
					<display:table name="pageScope.udocs"
							id="udoc"
							style="width:98%;margin-left:auto;margin-right:auto"
							sort="list"
							requestURI="/action/gestionDetallesPrestamos"
							export="false">

						<display:setProperty name="basic.msg.empty_list">
							<bean:message key="archigest.archivo.solicitudes.udocsRelacionadas.vacio"/>
						</display:setProperty>

						<display:column style="width:23px;">
							<c:choose>
								<c:when test="${udoc.subtipoCaja}">
									<input type="checkbox" name="detallesseleccionados"
										value='<c:out value="${udoc.identificacion}"/>|<c:out value="${udoc.id}"/>|null|<c:out value="${udoc.titulo}"/>|<c:out value="${udoc.signaturaudoc}"/>|<c:out value="${udoc.idFondo}"/>|<c:out value="${udoc.codsistproductor}">null</c:out>' >
								</c:when>
								<c:otherwise>
									<input type="checkbox" name="detallesseleccionados"
										value='<c:out value="${udoc.identificacion}"/>|<c:out value="${udoc.id}"/>|<c:out value="${udoc.numexp}">null</c:out>|<c:out value="${udoc.titulo}"/>|<c:out value="${udoc.signaturaudoc}"/>|<c:out value="${udoc.idFondo}"/>|<c:out value="${udoc.codsistproductor}">null</c:out>' >
								</c:otherwise>
							</c:choose>
						</display:column>

						<display:column titleKey="archigest.archivo.prestamos.expedienteudoc" style="width:18%;">
							<c:choose>
								<c:when test="${udoc.subtipoCaja}">
									<html:img page="/pages/images/rango2.gif" width="26" styleClass="imgTextMiddle" />
									<c:out value="${udoc.nombreRangos}"/>
								</c:when>
								<c:otherwise>
									<c:out value="${udoc.numexp}"/>
								</c:otherwise>
							</c:choose>
						</display:column>

						<c:if test="${beanBusqueda.mapSalida[appConstants.prestamos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_FONDO] != null}">
							<display:column titleKey="archigest.archivo.solicitudes.fondo"  sortable="true" headerClass="sortable" property="nombreFondo"/>
						</c:if>
						<c:if test="${beanBusqueda.mapSalida[appConstants.prestamos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_SIGNATURA] != null}">
							<display:column titleKey="archigest.archivo.solicitudes.signaturaudoc"  sortable="true" headerClass="sortable" property="signaturaudoc"/>
						</c:if>
						<c:if test="${beanBusqueda.mapSalida[appConstants.prestamos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_CODIGO] != null}">
							<display:column titleKey="archigest.archivo.codigo"  sortable="true" headerClass="sortable" property="codigo"/>
						</c:if>
						<c:if test="${beanBusqueda.mapSalida[appConstants.prestamos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_TITULO] != null}">
							<display:column titleKey="archigest.archivo.titulo" sortable="true" headerClass="sortable" property="titulo"/>
						</c:if>
						<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_BLOQUEO] != null}">
							<display:column style="text-align: center;"  media="html">
								<c:choose>
									<c:when test="${elemento.marcasBloqueoUnidad>0}" >
										<html:img
											page="/pages/images/udocBloqueada.gif"
											titleKey="archigest.archivo.bloqueada"
											altKey="archigest.archivo.bloqueada"
											styleClass="imgTextBottom" />
									</c:when>
									<c:otherwise>
										<html:img
											page="/pages/images/udocDesbloqueada.gif"
											titleKey="archigest.archivo.desbloqueada"
											altKey="archigest.archivo.desbloqueada"
											styleClass="imgTextBottom" />
									</c:otherwise>
								</c:choose>
							</display:column>
							<display:column titleKey="archigest.archivo.bloqueada" media="csv excel xml pdf">
								<c:choose>
									<c:when test="${elemento.marcasBloqueoUnidad>0}" >
										<bean:message key="archigest.archivo.si" />
									</c:when>
									<c:otherwise>
										<bean:message key="archigest.archivo.no" />
									</c:otherwise>
								</c:choose>
							</display:column>
						</c:if>
					</display:table>
				</div>
			</div>
		</div>
		<h2><span>&nbsp;</span></h2>
	</html-el:form>