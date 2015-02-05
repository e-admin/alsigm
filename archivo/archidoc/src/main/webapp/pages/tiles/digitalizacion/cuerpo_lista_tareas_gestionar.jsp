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
	<archivo:errors />
</div>
<div class="separador1"></div>


<div class="separador8" style="clear:both;"></div>

<div class="cabecero_bloque"> 

<%-- cabecero segundo bloque de datos --%>
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
</div> <%-- cabecero bloque --%>
<div class="bloque"> <%-- bloque de datos --%>
	
<div class="separador8"></div>

		<display:table name="pageScope.listaTareasAGestionar"
			id="tarea" 
			style="width:99%;margin-left:auto;margin-right:auto"
			pagesize="15"
			requestURI="/action/gestionTareasDigitalizacion"
			>

			<display:setProperty name="basic.msg.empty_list">
				<bean:message key="archigest.archivo.documentos.digitalizacion.noTareasAGestionar"/>
			</display:setProperty>
			
			<display:column titleKey="archigest.archivo.documentos.digitalizacion.tipo" sortable="true" headerClass="sortable">
				<fmt:message key="${tarea.nombreTipoObjeto}" />		
			</display:column>
			
			<display:column titleKey="archigest.archivo.documentos.digitalizacion.codReferencia" property="codRefObj" sortable="true" headerClass="sortable"/>
			
			<display:column titleKey="archigest.archivo.documentos.digitalizacion.titulo" sortable="true" headerClass="sortable">
				<c:url var="verTareaURL" value="/action/gestionTareasDigitalizacion">
					<c:param name="method" value="verTarea" />
					<c:param name="idTarea" value="${tarea.id}" />
				</c:url>
				<a class="tdlink" href='<c:out value="${verTareaURL}" escapeXml="false"/>' >
					<c:out value="${tarea.tituloObj}"/>
				</a>
			</display:column>
			<display:column titleKey="archigest.archivo.documentos.digitalizacion.usuarioCaptura" sortable="true" headerClass="sortable">
					<c:out value="${tarea.usuarioCaptura.nombreCompleto}"/>
			</display:column>
			<display:column titleKey="archigest.archivo.documentos.digitalizacion.estado" sortable="true" headerClass="sortable">
				<fmt:message key="${tarea.nombreEstado}" />	
			</display:column>
			<display:column titleKey="archigest.archivo.documentos.digitalizacion.fechaEstado" sortable="true" >
				<fmt:formatDate value="${tarea.fechaEstado}" pattern="${appConstants.common.FORMATO_FECHA}"/>
			</display:column>

		</display:table>
		<div class="separador8"></div>
</div>

<div class="separador1" style="clear:both;"></div>
</div> <%-- cuerpo_izda --%>
</div> <%-- cuerpo_drcha --%>

<h2><span>&nbsp;</span></h2>

</div> <%-- ficha --%>
</div> <%-- contenedor_ficha --%>	

