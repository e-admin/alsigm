<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<c:set var="idOficPref" value="${requestScope['idOficPref']}"/>
<c:set var="idUsuario" value="${requestScope['idUsuario']}"/>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="IECISA" />
<title><bean:message key="ieci.tecdoc.sgm.pgadmin.title.aplication"/> - <bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.titulo"/></title>
<link href="css/estilos.css" rel="stylesheet" type="text/css" />
<link href="css/adminApp.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/common.js"></script>
</head>

<body>
<form name="frmListadoUsuarios" action="" method="post">
<div id="contenedora">
  <!-- Inicio Cabecera -->
  <jsp:include page="includes/cabecera.jsp"/>


  <!-- Inicio Contenido -->
  <div id="contenido" >
    <div class="cuerpo">
      <div class="cuerporight">
        <div class="cuerpomid">
	      <jsp:include page="includes/pestanas.jsp">
			<jsp:param name="pestana" value="usuarios"/>
		  </jsp:include>

          <div class="cuadro">
			<div id="migas">
				<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.oficinas"/>&nbsp;
				<span class="migasElementoSeleccionado"><bean:write name="nombreUsuario"/></span>&nbsp;
			</div>
			<div class="col" style="height: 30px;">
			<table cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="100%" align="left">
						<jsp:include page="includes/errores.jsp"/>
					</td>
					<td align="right" class="col_eliminar" onclick="llamadaAction('<html:rewrite page="/listadoUsuarios.do"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.cancelar"/></td>
				</tr>
				<tr>
					<td colspan="2" class="txt">
						<c:if test="${isLdap == false}">
							<b><bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.oficina.asociada.depto"/></b>
						</c:if>
						<c:if test="${isLdap == true}">
							<b><bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.oficinas.userldap"/></b>
						</c:if>
					</td>
				</tr>
			</table></div>

 			<display:table name="oficinasDepto" uid="fila"
				 requestURI="/oficinas.do" sort="page" class="tablaListado" style="width:100%">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.departamento.sin.oficina"/>
					</display:setProperty>
					<display:column titleKey="ieci.tecdoc.sgm.rpadmin.usuarios.nombre"
						 sortable="false" style="width: 90%;">
						 <table cellpadding="0" cellspacing="0" border="0" width="100%">
						 	<tr>
						 		<td width="30" align="center"><img src="<html:rewrite page="/img/departamento.gif"/>" /></td>
						 		<td><bean:write name="fila" property="nombre" /></td>
						 	</tr>
						 </table>
					</display:column>
					<display:column titleKey="ieci.tecdoc.sgm.rpadmin.usuarios.preferente" style="text-align:center;">
						<c:choose>
							<c:when test="${idOficPref == fila.id}">
								&nbsp;<img src="<html:rewrite page="/img/guardar.gif"/>"/>
							</c:when>
							<c:otherwise>
								<input type="radio" value='<c:out value="${fila.id}"/>' onclick="llamadaActionAsociarOficPrefUsuario('<html:rewrite page="/asociarOficinaPreferenteAUsuario.do"/>','<bean:write name="fila" property="id" />', '<bean:write name="idUsuario"/>')"/>
							</c:otherwise>
						</c:choose>
					</display:column>
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr><td height="8" class="col" width="100%"></td></tr>
					</table>
			</display:table>

          </div>

          <div class="cuadro">

			<div class="col" style="height: 30px;">
			<table cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="100%" align="left">
						<jsp:include page="includes/errores.jsp"/>
					</td>
					<td align="right" class="col_nuevo" onclick="llamadaActionAsociarUsuarioIdOficina('<html:rewrite page="/asociarOficinaNuevaAUsuario.do"/>', '<bean:write name="idUsuario" />')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.anadir"/></td>
				</tr>
				<tr>
					<td colspan="2" class="txt">
						<b><bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.oficina.asociadas.usuario"/></b>
					</td>
				</tr>
			</table></div>
 			<display:table name="oficinas.lista" uid="fila"
				 requestURI="/oficinas.do" sort="page" class="tablaListado" style="width:100%">
					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.usuario.sin.oficinas"/>
					</display:setProperty>
					<display:column titleKey="ieci.tecdoc.sgm.rpadmin.usuarios.nombre"
						 sortable="false" style="width: 90%;">
						 <table cellpadding="0" cellspacing="0" border="0" width="100%">
						 	<tr>
						 		<td width="30" align="center"><img src="<html:rewrite page="/img/departamento.gif"/>" /></td>
						 		<td><bean:write name="fila" property="nombre" /></td>
						 	</tr>
						 </table>
					</display:column>
					<display:column sortable="false" style="width: 10%;">
						 <table cellpadding="0" cellspacing="0" border="0" width="100%">
						 	<tr>
						 		<c:set var="funcionllamadaActionDesasociarOficinaIdUsuario">
									llamadaActionDesasociarOficinaIdUsuario("<html:rewrite page='/asociarOficinaUsuario.do'/>","<bean:write name='fila' property='id' filter='false'/>", "<bean:write name='idUsuario' filter='false'/>", "<bean:message key='ieci.tecdoc.sgm.rpadmin.usuarios.desasociar.oficina'/>","<bean:write name='fila' property='nombre' filter='false'/>")
						 		</c:set>
						 		<td class="col_eliminar" onclick="<c:out value="${funcionllamadaActionDesasociarOficinaIdUsuario}"/>"><bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.quitar.oficinas"/></td>
						 	</tr>
						 </table>
					</display:column>
					<display:column titleKey="ieci.tecdoc.sgm.rpadmin.usuarios.preferente" style="text-align:center;">
						<c:choose>
							<c:when test="${idOficPref == fila.id}">
								&nbsp;<img src="<html:rewrite page="/img/guardar.gif"/>" />
							</c:when>
							<c:otherwise>
								<input type="radio" value='<c:out value="${fila.id}"/>' onclick="llamadaActionAsociarOficPrefUsuario('<html:rewrite page="/asociarOficinaPreferenteAUsuario.do"/>','<bean:write name="fila" property="id" />', '<bean:write name="idUsuario"/>')"/>
							</c:otherwise>
						</c:choose>
					</display:column>
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr><td height="8" class="col" width="100%"></td></tr>
					</table>
			</display:table>

          </div>

        </div>
      </div>
    </div>
    <div class="cuerpobt">
      <div class="cuerporightbt">
        <div class="cuerpomidbt"></div>
      </div>
    </div>
  </div>
  <!-- Fin Contenido -->
  <div id="pie"></div>
</div>
</form>
</body>
</html>
