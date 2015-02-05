<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

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
<html:form action="/usuariosOficina.do" styleId="formulario">
<html:hidden property="idOfic"/>
<div id="contenedora">
  <!-- Inicio Cabecera -->
  <jsp:include page="includes/cabecera.jsp"/>

  <!-- Inicio Contenido -->
  <div id="contenido" >
    <div class="cuerpo">
      <div class="cuerporight">
        <div class="cuerpomid">
	      <jsp:include page="includes/pestanas.jsp">
			<jsp:param name="pestana" value="oficinas"/>
		  </jsp:include>

          <div class="cuadro">
          	<div id="migas">
				<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.oficinas.usuarios.asociados"/>&nbsp;
				<span class="migasElementoSeleccionado"><bean:write name="nombreOficina"/></span>&nbsp;
			</div>
			<div class="col" align="right" style="height: 45px;">
			<table cellspacing="0" cellpadding="0" border="0">
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td width="100%" align="left">
						<html:checkbox property="usuarios" styleId="usuarios" onclick="llamadaActionUsuariosOficina()">
			          		<bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.titulo"/>
			          	</html:checkbox>
			          	<html:checkbox property="agregados" styleId="agregados" onclick="llamadaActionUsuariosOficina()">
			          		<bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.agreados"/>
			          	</html:checkbox>
					</td>
					<c:set var="funcionabreUsuariosOficina">
						abreUsuariosOficina("<html:rewrite page='/asociarUsuarioOficina.do'/>" + "?idOficina=" + "<bean:write name='idOficina' filter='false'/>" + "&nombreOficina=" + "<bean:write name='nombreOficina' filter='false'/>", <bean:write name='usuarioOficinaForm' property='usuariosDisponibles' filter='false'/>,"<bean:message key='ieci.tecdoc.sgm.rpadmin.oficinas.asociar.sin.usuarios'/>")
					</c:set>
					<td class="col_nuevo" onclick="<c:out value="${funcionabreUsuariosOficina}"/>">
						<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.anadir"/>
					</td>
					<td class="col_eliminar" onclick="llamadaAction('<html:rewrite page="/listadoOficinas.do"/>')">
						<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.cancelar"/>
					</td>
				</tr>
				<tr>
				<td width="100%" align="left">
						<jsp:include page="includes/errores.jsp"/>
				</td>
				</tr>
			</table></div>
 			<display:table name="usuarios.lista" uid="fila"
				 requestURI="/usuariosOficina.do" sort="page" class="tablaListado" style="width:100%" defaultsort="1">
				 	<display:setProperty name="basic.msg.empty_list">
						<bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.sin.usuarios.asociados"/>
					</display:setProperty>
					<display:column property="nombre" titleKey="ieci.tecdoc.sgm.rpadmin.usuarios.nombre"
						 sortable="false" style="width: 14%;"/>
					<display:column property="perfil" titleKey="ieci.tecdoc.sgm.rpadmin.usuarios.perfil"
						 sortable="false" style="width: 18%"/>
					<display:column property="nombreCompleto" titleKey="ieci.tecdoc.sgm.rpadmin.usuarios.nombreCompleto"
						 sortable="false" style="width: 20%;"/>
		<display:column style="width: 12%;">
			<table><tr>
			<td class="col_editar" onclick="llamadaActionIdUsuario('<html:rewrite page="/editarUsuarioOficina.do"/>','<bean:write name="fila" property="id" />')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.propiedadesEditar"/></td>
			</tr></table>
		</display:column>
					<display:column sortable="false" style="width: 10%;">
						<c:if test="${fila.agregado}">
							<table cellpadding="0" cellspacing="0" border="0" width="100%">
								<tr>
									<c:set var="funcionllamadaActionDesasociarOficinaIdUsuario">
										llamadaActionDesasociarOficinaIdUsuario("<html:rewrite page='/asociarUsuarioOficina.do'/>","<bean:write name='idOficina' filter='false'/>", "<bean:write name='fila' property='id' filter='false'/>", "<bean:message key='ieci.tecdoc.sgm.rpadmin.oficinas.desasociar.usuarios'/>","<bean:write name='fila' property='nombre' filter='false'/>")
									</c:set>
									<td class="col_eliminar" onclick="<c:out value="${funcionllamadaActionDesasociarOficinaIdUsuario}"/>"><bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.quitar.oficinas"/></td>
								</tr>
							</table>
						</c:if>
					</display:column>
					<display:column titleKey="ieci.tecdoc.sgm.rpadmin.usuarios.agregado" sortable="false" style="width: 5%;text-align:center">
						<c:if test="${fila.agregado}">
							<img src="<html:rewrite page="/img/guardar.gif"/>"/>
						</c:if>
					</display:column>
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
</html:form>
</body>
</html>