<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>

<c:set var="LISTA_NAME" value="requestScope.${appConstants.consultas.LISTA_CONSULTAS_KEY}"/>

<c:if test="${requestScope[appConstants.consultas.ACCION_ANIADIR_A_CONSULTA_KEY]}">
	<c:set var="IS_ANIADIR_A_CONSULTA" value="true"/>
</c:if>

<%@ page import="common.Constants" %>
<%@ page import="solicitudes.consultas.ConsultasConstants" %>

<c:choose>
	<c:when test="${IS_ANIADIR_A_CONSULTA}">
	<script>
	function aceptarAniadir(){
		var form = document.forms[0];
		if (form && form.id && elementSelected(form.id)){
			if (confirm("<bean:message key='archigest.archivo.consultas.aniadir.udocs.busquedas.msg.confirm'/>")){
				document.forms[0].method.value="aniadirUdocsAConsulta";
				document.forms[0].submit();
			}
		} else {
			alert("<bean:message key='archigest.archivo.consultas.aniadir.udocs.busquedas.obligatorio'/>");
		}
	}
	</script>
	</c:when>

	<c:otherwise>
<script>
	function eliminarConsultas(){
		if ( document.forms[0].elements.item('consultasseleccionadas')!=null ) {
			var nSelected=FormsToolKit.getNumSelectedChecked(document.forms[0],"consultasseleccionadas");
			if(nSelected>=1){
				if (confirm("<bean:message key='archigest.archivo.consultas.eliminacion.confirm'/>")){
					document.forms[0].method.value="eliminarconsultas";
					document.forms[0].submit();
				}
			}else{
				alert("<bean:message key='archigest.archivo.consultas.eliminacion.warningMsg'/>");
			}
		} else alert("<bean:message key='archigest.archivo.consultas.eliminacion.noConsultas'/>");
	}
</script>
</c:otherwise>
</c:choose>
<div id="contenedor_ficha">

<html:form action="/gestionConsultas">
<html:hidden property="method"/>

<div class="ficha">

	<h1><span>
	<div class="w100">
		<TABLE class="w98" cellpadding=0 cellspacing=0>
			<tr>
		    <TD class="etiquetaAzul12Bold" width="30%" height="25px">
				<bean:message key="archigest.archivo.consultas.consultas"/>
			</TD>
		    <TD width="70%" align="right">
			<TABLE cellpadding=0 cellspacing=0>
			 <TR>
			 	<c:if test="${requestScope[appConstants.consultas.VER_BOTON_ELIMINAR]}">
					 <td nowrap="nowrap">
						<c:url var="nuevaConsultaURL" value="/action/gestionConsultas">
							<c:param name="method" value="nuevo" />
						</c:url>
						<a class="etiquetaAzul12Bold" href="<c:out value="${nuevaConsultaURL}" escapeXml="false"/>" >
						<html:img page="/pages/images/new.gif" altKey="archigest.archivo.crear" titleKey="archigest.archivo.crear" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.crear"/></a>
					</td>
				 	<TD width="20">&nbsp;</TD>

				 	<c:if test="${!empty LISTA_NAME}">
			       	<TD>
						<c:set var="llamadaEliminarConsultas">javascript:eliminarConsultas()</c:set>
						<a class="etiquetaAzul12Bold" href='<c:out value="${llamadaEliminarConsultas}" escapeXml="false"/>' >
							<html:img page="/pages/images/delete.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
							&nbsp;<bean:message key="archigest.archivo.eliminar"/>
						</a>
				   	</TD>
			        <TD width="10">&nbsp;</TD>
			        </c:if>
				</c:if>
			     <c:if test="${IS_ANIADIR_A_CONSULTA}">
				 <TD>
						<a class="etiquetaAzul12Bold" href="javascript:aceptarAniadir()" >
							<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
			     			&nbsp;<bean:message key="archigest.archivo.aceptar"/>
			     		</a>
			     </TD>
			      <TD width="10">&nbsp;</TD>
			     </c:if>

		      	<TD>
					<c:url var="cancelURL" value="/action/gestionConsultas">
						<c:param name="method" value="goBack" />
					</c:url>
					<a class="etiquetaAzul12Bold" href='<c:out value="${cancelURL}" escapeXml="false"/>'>
						<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
						&nbsp;<bean:message key="archigest.archivo.cerrar"/>
					</a>
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

	<DIV id="barra_errores"><archivo:errors /></DIV>

	<span class="separador1"></span>
	<DIV class="bloque">
		<jsp:useBean id="LISTA_NAME" type="java.lang.String" />
		<display:table name="<%=LISTA_NAME%>"
			style="width:99%;margin-left:auto;margin-right:auto"
			id="listaRegistros"
			decorator="solicitudes.consultas.decorators.ViewConsultaDecorator"
			pagesize="15"
			sort="list"
			requestURI='<%=request.getContextPath()+"/action/gestionConsultas?method=" + request.getAttribute(ConsultasConstants.METHOD) %>'
			excludedParams="*"
			export="true">
			<display:setProperty name="basic.msg.empty_list">
				<bean:message key="archigest.archivo.consultas.noPrev"/>
			</display:setProperty>

			<c:choose>
			<c:when test="${IS_ANIADIR_A_CONSULTA}">
				<display:column style="width:20px" media="html">
					<input type="radio" name="id" value='<c:out value="${listaRegistros.id}"/>' >
				</display:column>
			</c:when>
			<c:otherwise>
				<c:if test="${requestScope[appConstants.consultas.VER_BOTON_ELIMINAR]}">
				<display:column style="width:23px;" headerClass="deleteFolderIcon" media="html">
					<c:if test="${listaRegistros.estado==1 || listaRegistros.estado==3}">
						<input type="checkbox" name="consultasseleccionadas" value='<c:out value="${listaRegistros.id}"/>' >
					</c:if>
				</display:column>
				</c:if>
			</c:otherwise>
			</c:choose>
			<display:column titleKey="archigest.archivo.consultas.consulta" sortable="true" headerClass="sortable" sortProperty="codigoTransferencia" media="html">
				<c:url var="URL" value="/action/gestionConsultas">
					<c:param name="method" value="verconsulta" />
					<c:param name="id" value="${listaRegistros.id}" />
				</c:url>
				<a class="tdlink" href='<c:out value="${URL}" escapeXml="false" />' >
					<c:out value="${listaRegistros.codigoTransferencia}"/>
				</a>
			</display:column>
			<display:column titleKey="archigest.archivo.consultas.consulta" media="csv excel xml pdf">
				<c:out value="${listaRegistros.codigoTransferencia}"/>
			</display:column>

			<display:column titleKey="archigest.archivo.consultas.norgconsultor" sortable="true" headerClass="sortable" property="norgconsultor" decorator="solicitudes.consultas.decorators.ConsultaDecorator" media="html"/>
			<display:column titleKey="archigest.archivo.consultas.norgconsultor" property="norgconsultor" media="csv excel xml pdf"/>
			<display:column titleKey="archigest.archivo.consultas.idusrsolicitante" sortable="true" headerClass="sortable" property="nusrconsultor" decorator="solicitudes.consultas.decorators.ConsultaDecorator" media="html"/>
			<display:column titleKey="archigest.archivo.consultas.idusrsolicitante" property="nusrconsultor" media="csv excel xml pdf"/>
			<display:column titleKey="archigest.archivo.consultas.estado" sortable="true" headerClass="sortable" property="estado" decorator="solicitudes.consultas.decorators.ConsultaDecorator" media="html"/>
			<display:column titleKey="archigest.archivo.consultas.estado" media="csv excel xml pdf">
				<fmt:message key="archigest.archivo.solicitudes.consulta.estado.${listaRegistros.estado}"/>
			</display:column>
			<display:column titleKey="archigest.archivo.consultas.festado" sortable="true" headerClass="sortable" property="festado" decorator="solicitudes.consultas.decorators.ConsultaDecorator" media="html"/>
			<display:column titleKey="archigest.archivo.consultas.festado" media="csv excel xml pdf">
				<fmt:formatDate value="${listaRegistros.festado}" pattern="${appConstants.common.FORMATO_FECHA}" />
			</display:column>
		</display:table>
	</DIV> <%--bloque --%>
			<span class="separador1"></span>
			<c:if test="${IS_ANIADIR_A_CONSULTA}">
				<tiles:insert page="/pages/tiles/solicitudes/consultas/detalles_consultas_udoc_seleccionadas_busqueda.jsp" />
			</c:if>
</div> <%--cuerpo_izda --%>
</div> <%--cuerpo_drcha --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</html:form>
</div> <%--contenedor_ficha --%>