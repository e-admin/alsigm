<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="IECISA" />
<title><bean:message key="ieci.tecdoc.sgm.pgadmin.title.aplication"/> - <bean:message key="ieci.tecdoc.sgm.rpadmin.libros.titulo"/></title>
<link href="css/estilos.css" rel="stylesheet" type="text/css" />
<link href="css/adminApp.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/common.js"></script>
</head>

<body>
<html:form action="/asociarUsuarios.do" styleId="frmListado">
<div id="contenedora">
  <!-- Inicio Cabecera -->
  <jsp:include page="includes/cabecera.jsp"/>

  <!-- Inicio Contenido -->
  <div id="contenido" >
    <div class="cuerpo">
      <div class="cuerporight">
        <div class="cuerpomid">
	      <jsp:include page="includes/pestanas.jsp">
			<jsp:param name="pestana" value="libros"/>
		  </jsp:include>

          <div class="cuadro">
			<div id="migas">
				<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.asociacion.editar.permisos.usuario"/>
			</div>
			<div class="col">
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
				<tr>
					<td align="left">
						<jsp:include page="includes/errores.jsp"/>
					</td>
					<td align="right">
						<table cellspacing="0" cellpadding="0" border="0">
							<tr align="right">
								<logic:notEqual name="estado" value="1">
									<td class="col_nuevo" onclick="llamadaActionUsuariosLibro('<html:rewrite page="/asociarUsuarios.do"/>', '<bean:write name="libro" property="id"/>', 'ASOCIAR')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.guardar"/></td>
								</logic:notEqual>
								<td class="col_eliminar" onclick="llamadaActionUsuariosLibro('<html:rewrite page="/asociarUsuarios.do"/>', '<bean:write name="libro" property="id"/>', 'CANCELAR')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.cancelar"/></td>
							</tr>
						</table>
					</td>
				</tr>
			</table></div>
			<table cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td height="40">
						<logic:equal name="libro" property="tipo" value="1">
							<font color="#950101" ><b>>> <bean:message key="ieci.tecdoc.sgm.rpadmin.libros.tipo.libro.entrada"/></b>:</font>&nbsp;&nbsp;<bean:write name="libro" property="nombre"/>
						</logic:equal>
						<logic:notEqual name="libro" property="tipo" value="1">
							<font color="#006699" ><b>>> <bean:message key="ieci.tecdoc.sgm.rpadmin.libros.tipo.libro.salida"/></b>:</font>&nbsp;&nbsp;<bean:write name="libro" property="nombre"/>
						</logic:notEqual>
					</td>
				</tr>
			</table>
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr><td height="5"></td></tr>
				<tr>
					<td>
						<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.sinSuperUsuarios"/>
					</td>
				</tr>
				<tr><td height="5"></td></tr>
				<tr>
					<td>
						<display:table name="permisosSicres.lista" uid="fila"
							 requestURI="/asociarUsuarios.do" class="tablaListado" sort="page" style="width:100%">
								<display:column property="nombre" titleKey="ieci.tecdoc.sgm.rpadmin.libros.nombre"
									 sortable="false" style="width: 66%;"/>
								<display:column style="width: 70px;" titleKey="ieci.tecdoc.sgm.rpadmin.libros.asociacion.editar.consultar">
									<table cellspacing="0" cellpadding="0" border="0" width="70">
										<tr>
											<td align="center">
												<input type="checkbox" name="consultarCheck_<c:out value="${fila_rowNum-1}"/>" style="width:20px" id="consultarCheck_<c:out value="${fila_rowNum-1}"/>"
													onclick="changePermisoConsultar(this)"
													<logic:equal name="fila" property="consultar" value="true">checked</logic:equal>
													<logic:equal name="estado" value="1">disabled</logic:equal> />
												<html-el:hidden property="permisos[${fila_rowNum-1}].consultar" />
											</td>
										</tr>
									</table>
								</display:column>
								<display:column style="width: 60px;" titleKey="ieci.tecdoc.sgm.rpadmin.libros.asociacion.editar.crear">
									<table cellspacing="0" cellpadding="0" width="60">
										<tr>
											<td align="center">
												<input type="checkbox" name="crearCheck_<c:out value="${fila_rowNum-1}"/>" style="width:20px" id="crearCheck_<c:out value="${fila_rowNum-1}"/>"
													onclick="changePermisoCrear(this);"
													<logic:equal name="fila" property="crear" value="true">checked</logic:equal>
													<logic:equal name="estado" value="1">disabled</logic:equal> />
												<html-el:hidden property="permisos[${fila_rowNum-1}].crear" />
											</td>
										</tr>
									</table>
								</display:column>
								<display:column style="width: 60px; align:center" titleKey="ieci.tecdoc.sgm.rpadmin.libros.asociacion.editar.modificar">
									<table cellspacing="0" cellpadding="0" border="0" width="70">
										<tr>
											<td align="center">
												<input type="checkbox" name="modificarCheck_<c:out value="${fila_rowNum-1}"/>" style="width:20px" id="modificarCheck_<c:out value="${fila_rowNum-1}"/>"
													onclick="changePermisoModificar(this)"
													<logic:equal name="fila" property="modificar" value="true">checked</logic:equal>
													<logic:equal name="estado" value="1">disabled</logic:equal> />
												<html-el:hidden property="permisos[${fila_rowNum-1}].modificar" />

												<html-el:hidden property="permisos[${fila_rowNum-1}].id"/>
												<html-el:hidden property="permisos[${fila_rowNum-1}].idBook"/>
												<html-el:hidden property="permisos[${fila_rowNum-1}].tipo" value="1"/>
											</td>
										</tr>
									</table>
								</display:column>
								<display:column style="width: 14%;">
									<table><tr>
										<c:set var="funcionllamadaFiltrosUsuario">
											llamadaFiltrosUsuario("<html:rewrite page='/cargarFiltros.do'/>","<bean:write name='fila' property='id' filter='false'/>", "<bean:write name='libro' property='id' filter='false'/>", "<bean:write name='fila' property='nombre' filter='false'/>")
										</c:set>
										<td class="col_editar" onclick="<c:out value="${funcionllamadaFiltrosUsuario}"/>"><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.aplicar.filtros"/></td>
									</tr></table>
								</display:column>
						</display:table>
					</td>
				</tr>
			</table>

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
<form name="frmAsociacion" method="post"></form>
</body>
</html>
