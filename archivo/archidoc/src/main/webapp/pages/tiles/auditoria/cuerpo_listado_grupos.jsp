<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ page import="common.Constants" %>

<script src="<%=request.getContextPath()%>/js/sigia_form.js" type="text/JavaScript"></script>

<c:set var="llamadaAsignar">
	javascript:asignarNivel() 
</c:set>
<c:set var="llamadaAnadir">
	javascript:anadirGrupo() 
</c:set>

<div id="contenedor_ficha">

<script>
	function asignarNivel() {
		var form = document.forms[0];
		if (elementSelected(form.detallesseleccionadosasignar)) {
			form.method.value="establecerNivel";
			form.submit();
		} else {
			alert("<bean:message key="archigest.archivo.auditoria.msg.seleccionGrupos"/>");
		}
	}
	function anadirGrupo() {
		var form = document.forms[0];
		if (elementSelected(form.detallesseleccionadosanadir)) {
			form.method.value="anadirGrupo";
			form.submit();
		} else {
			alert("<bean:message key="archigest.archivo.auditoria.msg.seleccionGrupos"/>");
		}
	}
</script>


<html:form action="/auditoriaUsuarios">
	<input type="hidden" name="method" value="establecerNivel">

<div class="ficha">

<h1><span>
	<div class="w100">
	<TABLE class="w98" cellpadding=0 cellspacing=0>
	  <TR>
	    <TD class="etiquetaAzul12Bold" height="25px">
			<bean:message key="archigest.archivo.auditoria.criticidadUsers"/>&nbsp;
		</TD>
	    <TD align="right">
			<TABLE cellpadding=0 cellspacing=0>			
			 <TR>
			 <c:if test="${appConstants.configConstants.mostrarAyuda}">
					<td>
						<c:set var="requestURI" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
						<c:set var="carpetaIdioma" value="${sessionScope[appConstants.commonConstants.LOCALEKEY]}" />
						<a class="etiquetaAzul12Bold" href="javascript:popupHelp('<c:out value="${requestURI}" escapeXml="false"/>/help/<c:out value="${carpetaIdioma}"/>/auditoria/CriticidadUsuarios.htm');">
						<html:img page="/pages/images/help.gif" 
						        altKey="archigest.archivo.ayuda" 
						        titleKey="archigest.archivo.ayuda" 
						        styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.ayuda"/></a>
					</td>
				</c:if>		
				<td width="10px"></td>	    
			 	<TD>
					<c:url var="cancelURL" value="/action/auditoriaUsuarios">
						<c:param name="method" value="goBack" />
					</c:url>
					<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${cancelURL}" escapeXml="false" />'">
						<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.cerrar"/>
					</a>
				</TD>
			 </TR>
			</TABLE>
		</td>
		</TR>
	</TABLE>
	</div>
</span></h1>

<div class="cuerpo_drcha">
<div class="cuerpo_izda">

<DIV id="barra_errores"><archivo:errors/></DIV>

<%--Tabla para los dos columnas con display--%>
<TABLE cellpadding="0" cellspacing="0" class="w100"> 
<tr>
<td valign="top" width="50%">
	
	<div id="resultados" style="text-align:center">	
	
	 	<div class="cabecero_bloque_m5" align="center" style="width:98%;margin-left:auto;margin-right:auto">
			<TABLE cellpadding=0 cellspacing=0 class="w98" height="100%">
			<TR>
			    <TD class="etiquetaAzul12Bold" width="80%">
					<bean:message key="archigest.archivo.auditoria.gruposUsr"/>
				</TD>
			</TR>
			</TABLE>
		</div> 

		<div class="bloque_m5" style="margin-left:auto;margin-right:auto;width:98%"> <%-- primer bloque de datos --%>
			<c:set var="LIST_NAME" value="requestScope.${appConstants.auditoria.LISTA_GRUPOS_KEY}"/>
			<jsp:useBean id="LIST_NAME" type="java.lang.String" />

			<TABLE cellpadding=0 cellspacing=0 class="w98">
			<TR>
			  	<td style="text-align:right; vertical-align:middle; width:100%; height:45px;">
					<a class="etiquetaAzul12Bold" href="<c:out value="${llamadaAnadir}" escapeXml="false"/>">
						<html:img page="/pages/images/add.gif" altKey="archigest.archivo.anadir" titleKey="archigest.archivo.anadir" styleClass="imgTextMiddle" />
			   		 	&nbsp;<bean:message key="archigest.archivo.anadir"/>
			   		</a>
				</TD>
			</TR>
			</TABLE>
	
			<display:table name="<%=LIST_NAME%>"
				style="width:98%;margin-left:auto;margin-right:auto"
				id="listaRegistros" 
				sort="list"				
				requestURI='<%=request.getContextPath()+"/action/auditoriaUsuarios?method=listadoGrupos"%>'
				export="false">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.auditoria.noappgroups"/>
					</display:setProperty>
						
					<display:column style="width:20px" headerClass="addIcon">					
						<input type="checkbox" name="detallesseleccionadosanadir" value='<c:out value="${listaRegistros.id}"/>|<c:out value="${listaRegistros.nombre}"/>' >
					</display:column>
	
					<display:column titleKey="archigest.archivo.grupo"  property="nombre" sortable="true" headerClass="sortable"/>
			</display:table>  
			<div class="separador5">&nbsp;</div>
		</div> <%-- bloque --%>
	</div>
</td>

<td valign="top" style="text-align:center">
	<div class="cabecero_bloque" style="width:98%;margin-left:auto;margin-right:auto">
		<TABLE cellpadding=0 cellspacing=0 class="w98" height="100%">
		<TR>
		    <TD class="etiquetaAzul12Bold" width="80%">
				<bean:message key="archigest.archivo.auditoria.gruposAuditoria"/>
			</TD>
		    <TD width="20%" align="right">
				<html:img page="/pages/images/pixel.gif" styleClass="imgTextMiddle"/>
			</TD>			
		</TR>
		</TABLE>
	</div> 
	<div class="bloque" style="width:98%;margin-left:auto;margin-right:auto"> 
		<c:set var="LISTA_NAME" value="sessionScope.${appConstants.auditoria.LISTA_GRUPOS_WITH_LEVEL_KEY}"/>
		<jsp:useBean id="LISTA_NAME" type="java.lang.String" />


		<TABLE cellpadding=0 cellspacing=0 class="w98" style="margin-left:auto;margin-right:auto">
		<TR>
		  	<td style="text-align:right; vertical-align:middle; width:100%; height:45px;">
		  		<select name="nivel" styleClass="input">
					<c:forEach items="${requestScope[appConstants.auditoria.LISTA_NIVELES_KEY]}" var="nivel" varStatus="status">
					    <option value="<c:out value="${nivel.id}"/>">
					      <c:out value="${nivel.name}"/> 
					    </option>
				  	</c:forEach>
				</select>&nbsp;
				<a class="etiquetaAzul12Bold" href="<c:out value="${llamadaAsignar}" escapeXml="false"/>" >
					<html:img page="/pages/images/nivel.gif" altKey="Asignar Nivel" titleKey="Asignar Nivel" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.auditoria.establecerNivel"/>&nbsp;&nbsp;
				</a>
			</TD>
		</TR>
		</TABLE>
				
		<display:table name="<%=LISTA_NAME%>"
			style="width:98%;margin-left:auto;margin-right:auto"
			id="listaGrupos" 
			decorator="auditoria.decorators.ViewGrupoDecorator"
			sort="list"				
			requestURI='<%=request.getContextPath()+"/action/auditoriaUsuarios?method=listadoGrupos"%>'
			export="false">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.auditoria.nogroups"/>
				</display:setProperty>
					
				<display:column style="width:20px" headerClass="establecerNivelIcon">					
					<input type="checkbox" name="detallesseleccionadosasignar" value='<c:out value="${listaGrupos.idAuditado}"/>' >
				</display:column>

				<display:column titleKey="archigest.archivo.grupo"  property="name" sortable="true" headerClass="sortable"/>
				<display:column titleKey="archigest.archivo.auditoria.nivelCriticidad" property="nivel" sortable="true" headerClass="sortable" style="width:150px"/>
		</display:table>  
		<div class="separador5">&nbsp;</div>
	</div>
</td>


</tr>
</table>
						
</div> <%-- cuerpo_drcha --%>
</div> <%-- cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%-- ficha --%>

</html:form>
</div> <%-- contenedor_ficha --%>
