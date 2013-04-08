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

<script>
	function controllerBookClosed(action,id,msg,nombre,tipoLibro, idLibro, idEstado, nombre) {
		if( idEstado != "1") {
			llamadaActionIdEliminarAsociacion(action,id,msg,nombre,tipoLibro, idLibro, idEstado, nombre);
		}
	}
</script>

</head>

<body>
<html:form action="/listadoAsociacion.do" styleId="frmListado">
<div id="contenedora">
  <!-- Inicio Cabecera -->
	<jsp:include page="includes/cabecera.jsp"/>


  <!-- Inicio Contenido -->
  <div id="migas">
			  <img src="<html:rewrite page="/img/flecha_migas.gif"/>" width="13" height="9" class="margen"/>
			  <bean:message key="ieci.tecdoc.sgm.rpadmin.libros.titulo"/>
			  |
			  <bean:message key="ieci.tecdoc.sgm.rpadmin.botones.editar"/>
		  </div>

  <div id="contenido" >
    <div class="cuerpo">
      <div class="cuerporight">
        <div class="cuerpomid">
	      <jsp:include page="includes/pestanas.jsp">
			<jsp:param name="pestana" value="libros"/>
		  </jsp:include>

          <div class="cuadro">

			<div class="col" style="height: 30px;">
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
				<tr>
					<td align="left">
						<jsp:include page="includes/errores.jsp"/>
					</td>
					<td align="right">
						<table cellspacing="0" cellpadding="0" border="0">
							<tr align="right">
								<logic:notEqual name="estado" value="1">
									<td class="col_nuevo" onclick="llamadaActionOficinasTipoLibro('<html:rewrite page="/asociarOficinas.do"/>','<bean:write name="idLibro" />','<bean:write name="tipoLibro" />','<bean:write name="estado" />', '<bean:write name="nombre" />')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.asociar.oficinas"/></td>
									<td class="col_nuevo" onclick="llamadaAction('<html:rewrite page="/guardarAsociacion.do"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.guardar"/></td>
								</logic:notEqual>
								<td class="col_eliminar" onclick="llamadaActionForm1('<html:rewrite page="/listadoLibros.do"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.cancelar"/></td>
							</tr>
						</table>
					</td>
				</tr>
			</table></div>
			<table cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td height="40">
						<logic:equal name="tipoLibro" value="1">
							<font color="#950101" ><b>>> <bean:message key="ieci.tecdoc.sgm.rpadmin.libros.tipo.libro.entrada"/></b>:</font>&nbsp;&nbsp;<bean:write name="nombre" />
						</logic:equal>
						<logic:notEqual name="tipoLibro" value="1">
							<font color="#006699" ><b>>> <bean:message key="ieci.tecdoc.sgm.rpadmin.libros.tipo.libro.salida"/></b>:</font>&nbsp;&nbsp;<bean:write name="nombre" />
						</logic:notEqual>
					</td>
				</tr>
			</table>
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
						<display:table name="permisosSicres.lista" uid="fila"
							 requestURI="/listadoAsociacion.do" class="tablaListado" sort="page" style="width:100%">
								<display:column property="nombre" titleKey="ieci.tecdoc.sgm.rpadmin.libros.oficina"
									 sortable="false" style="width: 32%;"/>
								<display:column style="width: 70px;" titleKey="ieci.tecdoc.sgm.rpadmin.libros.asociacion.editar.consultar">
									<table cellspacing="0" cellpadding="0" border="0" align="center">
										<tr>
											<td>
												<input type="checkbox" name="consultarCheck_<c:out value="${fila_rowNum-1}"/>" style="width:20px" id="consultarCheck_<c:out value="${fila_rowNum-1}"/>"
													onclick="document.getElementById('permisos[<c:out value="${fila_rowNum-1}"/>].consultar').value=this.checked;"
													<logic:equal name="estado" value="1">disabled</logic:equal>
													<logic:equal name="fila" property="consultar" value="true">checked</logic:equal> />
												<html-el:hidden property="permisos[${fila_rowNum-1}].consultar" />
												<html-el:hidden property="permisos[${fila_rowNum-1}].tipo" value="2"/>
												<html-el:hidden property="permisos[${fila_rowNum-1}].id"/>
												<html-el:hidden property="permisos[${fila_rowNum-1}].idBook"/>
											</td>
										</tr>
									</table>
								</display:column>
								<display:column style="width: 60px;" titleKey="ieci.tecdoc.sgm.rpadmin.libros.asociacion.editar.crear">
									<table cellspacing="0" cellpadding="0" border="0" align="center">
										<tr>
											<td>
												<input type="checkbox" name="crearCheck_<c:out value="${fila_rowNum-1}"/>" style="width:20px" id="crearCheck_<c:out value="${fila_rowNum-1}"/>"
													onclick="checkCrear(this, <c:out value="${fila_rowNum-1}"/>);"
													<logic:equal name="estado" value="1">disabled</logic:equal>
													<logic:equal name="fila" property="crear" value="true">checked</logic:equal> />
												<html-el:hidden property="permisos[${fila_rowNum-1}].crear" />
											</td>
										</tr>
									</table>
								</display:column>
								<display:column style="width: 60px;" titleKey="ieci.tecdoc.sgm.rpadmin.libros.asociacion.editar.modificar">
									<table cellspacing="0" cellpadding="0" border="0" align="center">
										<tr>
											<td>
												<input type="checkbox" name="modificarCheck_<c:out value="${fila_rowNum-1}"/>" style="width:20px" id="modificarCheck_<c:out value="${fila_rowNum-1}"/>"
													onclick="checkModificar(this, <c:out value="${fila_rowNum-1}"/>);"
													<logic:equal name="estado" value="1">disabled</logic:equal>
													<logic:equal name="fila" property="modificar" value="true">checked</logic:equal> />
												<html-el:hidden  property="permisos[${fila_rowNum-1}].modificar" />
											</td>
										</tr>
									</table>
								</display:column>
								<display:column style="width: 18%;">
									<table>
										<tr>
											<c:set var="funcioncontrollerBookClosed">
												controllerBookClosed("<html:rewrite page='/eliminarAsociacion.do'/>","<bean:write name='fila' property='id' filter='false'/>","<bean:message key='ieci.tecdoc.sgm.rpadmin.oficinas.eliminar.asociacion'/>","<bean:write name='fila' property='nombre' filter='false'/>","<bean:write name='tipoLibro' filter='false'/>", "<bean:write name='idLibro' filter='false'/>", "<bean:write name='estado' filter='false'/>", "<bean:write name='nombre' filter='false'/>")
											</c:set>
											<td class="col_editar" onclick="<c:out value="${funcioncontrollerBookClosed}"/>">
												<logic:equal name="estado" value="1">
													<font color="808080"><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.asociacion.eliminar.oficina"/></font>
												</logic:equal>
												<logic:notEqual name="estado" value="1">
													<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.asociacion.eliminar.oficina"/>
												</logic:notEqual>
											</td>
										</tr>
									</table>
								</display:column>
								<display:column style="width: 20%;">
									<table><tr>
										<c:set var="llamadaActionIdEditarUsuarioLibro">
											llamadaActionIdEditarUsuarioLibro("<html:rewrite page='/asociarUsuarios.do'/>","<bean:write name='fila' property='id' filter='false'/>", "<bean:write name='idLibro' filter='false'/>", "<bean:write name='tipoLibro' filter='false'/>", "<bean:write name='estado' filter='false'/>", "<bean:write name='nombre' filter='false'/>")
										</c:set>
										<td class="col_editar" onclick="<c:out value="${llamadaActionIdEditarUsuarioLibro}"/>"><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.asociacion.editar.permisos.usuario"/></td>
									</tr></table>
								</display:column>
								<display:column style="width: 14%;">
									<table><tr>
										<c:set var="llamadaFiltrosOficina">
											llamadaFiltrosOficina("<html:rewrite page='/cargarFiltros.do'/>","<bean:write name='fila' property='id' filter='false'/>", "<bean:write name='idLibro' filter='false'/>", "<bean:write name='tipoLibro' filter='false'/>", "<bean:write name='estado' filter='false'/>", "<bean:write name='nombre' filter='false'/>", "<bean:write name='fila' property='nombre' filter='false'/>")
										</c:set>
										<td class="col_editar" onclick="<c:out value="${llamadaFiltrosOficina}"/>"><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.aplicar.filtros"/></td>
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
<form name="frmListado" method="post"></form>
</body>
</html>
