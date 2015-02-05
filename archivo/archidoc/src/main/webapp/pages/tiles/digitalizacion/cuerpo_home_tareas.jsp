<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="listaTareasPendientes" value="${sessionScope[appConstants.documentos.LISTA_TAREAS_PENDIENTES_KEY]}"/>
<c:set var="listaTareasAGestionar" value="${sessionScope[appConstants.documentos.LISTA_TAREAS_A_GESTIONAR_KEY]}"/>		

<div id="contenedor_ficha" style="-moz-box-sizing: border-box;">

<div class="ficha">

<h1><span>
	<div class="w100">
		<TABLE class="w98" cellpadding=0 cellspacing=0 height="25px">
		  <TR>
			<TD class="etiquetaAzul12Bold">
				<bean:message key="archigest.archivo.documentos.digitalizacion.tareasDigitalizacion"/>
			</TD>
			<TD align="right">
				<table cellpadding="0" cellspacing="0">
					<tr>					
						<c:if test="${appConstants.configConstants.mostrarAyuda}">
							<td width="10">&nbsp;</td>
							<td>
								<c:set var="requestURI" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
								<c:set var="carpetaIdioma" value="${sessionScope[appConstants.commonConstants.LOCALEKEY]}" />
								<a class="etiquetaAzul12Bold" href="javascript:popupHelp('<c:out value="${requestURI}" escapeXml="false"/>/help/<c:out value="${carpetaIdioma}"/>/digitalizacion/GestionarTareaDigitalizacion.htm');">
								<html:img page="/pages/images/help.gif" 
									altKey="archigest.archivo.ayuda" 
									titleKey="archigest.archivo.ayuda" 
									styleClass="imgTextMiddle"/>
									&nbsp;<bean:message key="archigest.archivo.ayuda"/></a>
							</td>
							<td width="10">&nbsp;</td>
						</c:if>
						
						<td nowrap>
							<c:url var="cancelURI" value="/action/gestionTareasDigitalizacion">
								<c:param name="method" value="goBack"  />
							</c:url>
							<a  class="etiquetaAzul12Bold"  href="<c:out value="${cancelURI}" escapeXml="false"/>">
							<html:img  page="/pages/images/close.gif" styleClass="imgTextMiddle" titleKey="archigest.archivo.cerrar"  altKey="archigest.archivo.cerrar"/>
							&nbsp;<bean:message key="archigest.archivo.cerrar"/>
							</a>
						</td>
					</tr>
				</table>
			</TD>
		  </TR>
		</TABLE>
	</div>
</span></h1>


<div class="cuerpo_drcha">
<div class="cuerpo_izda" style="-moz-box-sizing: border-box;padding-left:4px;padding-right:12px">

<div id="barra_errores">
</div>
<div class="separador1"></div>

<security:permissions action="${appConstants.documentosActions.CAPTURA_DOCUMENTOS_ACTION}">
	<div class="cabecero_bloque"> 
	<TABLE class="w98m1" height="100%" cellpadding=0 cellspacing=0>
	  <TBODY>
	  <TR>
	    <TD class="etiquetaAzul12Bold">
			<bean:message key="archigest.archivo.documentos.digitalizacion.tareasPendientes"/>
		</TD>
	    <TD align="right">
		</TD>
	  </TR></TBODY></TABLE>
	</div> <%-- cabecero bloque --%>
	
	<div class="bloque"> <%-- bloque de datos --%>
		
	<div class="separador8"></div>


			<display:table name="pageScope.listaTareasPendientes"
				id="tarea" 
				style="width:99%;margin-left:auto;margin-right:auto">

				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.documentos.digitalizacion.noTareasPendientes"/>
				</display:setProperty>
				
				<display:column titleKey="archigest.archivo.documentos.digitalizacion.tipo">
					<fmt:message key="${tarea.nombreTipoObjeto}" />		
				</display:column>
				
				<display:column titleKey="archigest.archivo.documentos.digitalizacion.codReferencia" property="codRefObj"/>
				
				<display:column titleKey="archigest.archivo.titulo">
					<c:url var="verTareaURL" value="/action/gestionTareasDigitalizacion">
						<c:param name="method" value="verTarea" />
						<c:param name="idTarea" value="${tarea.id}" />
					</c:url>
					<a class="tdlink" href='<c:out value="${verTareaURL}" escapeXml="false"/>' >
						<c:out value="${tarea.tituloObj}"/>
					</a>
				</display:column>
				<display:column titleKey="archigest.archivo.estado">
					<fmt:message key="${tarea.nombreEstado}" />	
				</display:column>
				<display:column titleKey="archigest.archivo.documentos.digitalizacion.fechaEstado">
					<fmt:formatDate value="${tarea.fechaEstado}" pattern="${appConstants.common.FORMATO_FECHA}"/>
				</display:column>

			</display:table>
			<div class="separador8"></div>
	</div>
	
	<c:if test="${!empty listaTareasPendientes}">
		<div class="pie_bloque_right">
			<c:url var="tareasPendientesURI" value="/action/gestionTareasDigitalizacion">
				<c:param name="method" value="verTareasPendientes"  />
			</c:url>
			<a class="etiq_pie_bloque" href="<c:out value="${tareasPendientesURI}" escapeXml="false"/>">
				<html:img page="/pages/images/go_all.gif" styleClass="imgTextMiddle" /> <bean:message key="archigest.archivo.verMas"/>
			</a>
		</div>
		<div style="height:12px"></div>
	</c:if>
</security:permissions>

<div class="separador8" style="clear:both;"></div>

<security:permissions action="${appConstants.documentosActions.ADMINISTRACION_TAREAS_CAPTURA_ACTION}">

	<div class="cabecero_bloque"> 
	
		<TABLE class="w98m1" height="100%" cellpadding=0 cellspacing=0>
		  <TBODY>
		  <TR>
		    <TD class="etiquetaAzul12Bold">
				<bean:message key="archigest.archivo.documentos.digitalizacion.tareasAGestionar" />
			</TD>
		    <TD align="right">
				<table cellpadding=0 cellspacing=0><tr>
					<td width="10px"></td>
					<td nowrap>
						<c:url var="urlAnadirTarea" value="/action/gestionTareasDigitalizacion">
							<c:param name="method" value="initAnadirTarea"/>
						</c:url>
						<a  class="etiquetaAzul12Bold"  href="<c:out value="${urlAnadirTarea}" escapeXml="false"/>">
							<html:img  page="/pages/images/new.gif" styleClass="imgTextMiddle" titleKey="archigest.archivo.crear"  altKey="archigest.archivo.crear"/>
							&nbsp;<bean:message key="archigest.archivo.crear"/>
						</a>
				</tr></table>
			</TD>
		  </TR></TBODY></TABLE>
	</div> 

	<div class="bloque"> 
			<div class="separador8"></div>
			<display:table name="pageScope.listaTareasAGestionar"
				id="tarea" 
				style="width:99%;margin-left:auto;margin-right:auto">
	
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.documentos.digitalizacion.noTareasAGestionar"/>
				</display:setProperty>
				
				<display:column titleKey="archigest.archivo.documentos.digitalizacion.tipo">
					<fmt:message key="${tarea.nombreTipoObjeto}" />		
				</display:column>
				
				<display:column titleKey="archigest.archivo.documentos.digitalizacion.codReferencia" property="codRefObj"/>
				
				<display:column titleKey="archigest.archivo.documentos.digitalizacion.titulo">
					<c:url var="verTareaURL" value="/action/gestionTareasDigitalizacion">
						<c:param name="method" value="verTarea" />
						<c:param name="idTarea" value="${tarea.id}" />
					</c:url>
					<a class="tdlink" href='<c:out value="${verTareaURL}" escapeXml="false"/>' >
						<c:out value="${tarea.tituloObj}"/>
					</a>
				</display:column>
				<display:column titleKey="archigest.archivo.documentos.digitalizacion.usuarioCaptura">
						<c:out value="${tarea.usuarioCaptura.nombreCompleto}"/>
				</display:column>
				<display:column titleKey="archigest.archivo.documentos.digitalizacion.estado">
					<fmt:message key="${tarea.nombreEstado}" />	
				</display:column>
				<display:column titleKey="archigest.archivo.documentos.digitalizacion.fechaEstado">
					<fmt:formatDate value="${tarea.fechaEstado}" pattern="${appConstants.common.FORMATO_FECHA}"/>
				</display:column>
			</display:table>
			<div class="separador8"></div>
	</div>
	
	<c:if test="${!empty listaTareasAGestionar}">
		<div class="pie_bloque_right">
			<c:url var="tareasGestionarURI" value="/action/gestionTareasDigitalizacion">
				<c:param name="method" value="verTareasAGestionar"  />
			</c:url>
			<a class="etiq_pie_bloque" href="<c:out value="${tareasGestionarURI}" escapeXml="false"/>">
				<img src="..\images\go_all.gif" class="imgTextMiddle" /> 
				<bean:message key="archigest.archivo.verMas"/>
			</a>
		</div>
		<div style="height:12px"></div>
	</c:if>
	
	<div class="separador1" style="clear:both;"></div>
</security:permissions>

</div> <%-- cuerpo_izda --%>
</div> <%-- cuerpo_drcha --%>

<h2><span>&nbsp;</span></h2>

</div> <%-- ficha --%>
</div> <%-- contenedor_ficha --%>	

