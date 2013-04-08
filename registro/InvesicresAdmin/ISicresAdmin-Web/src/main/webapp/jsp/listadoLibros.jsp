<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="es.ieci.tecdoc.isicres.admin.beans.Libro" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

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
	var idLibro;
	var nombreLibro;
	var accion;
	function selecIdLibro(id, nombre) {
		idLibro = id;
		nombreLibro = nombre;
	}

	function send(url, accion) {
		if( (idLibro == "" || idLibro == undefined) && accion != "NUEVO") {
		    alert("<bean:message key="ieci.tecdoc.sgm.rpadmin.error.mensaje.required.select.libro" />");
		} else {
			if(accion == "NUEVO") {
				idLibroE = "";
				idLibroS = "";
			}
			else if( accion == "EDITAR")
				url += "?idLibro=" + idLibro;
			else if( accion == "ELIMINAR") {
				llamadaDeleteLibro( url, idLibro, "<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.eliminar.libro" />", nombreLibro );
				return;
			}
			document.forms[0].action = url;
			document.forms[0].submit();
		}
	}
</script>
</head>

<body>
<form name="frmListadoLibros" action="" method="post">
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
				<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.listado" />
			</div>
			<div class="col" style="height: 30px;">
			<table cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="100%" align="left">
						<jsp:include page="includes/errores.jsp"/>
					</td>
					<td align="right"></td>
				</tr>
			</table></div>
			<div class="col" style="height: 20px;" align="right">
				<table cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td width="3"></td>
						<td bgcolor="#000000"><img src='<html:rewrite page="/img/dot.gif"/>' width="1" height="100%" /></td>
						<td width="5"></td>
						<td class="col_nuevo" onclick="send('<html:rewrite page="/nuevoLibro.do"/>', 'NUEVO')" ><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.nuevo"/></td>
						<td width="3"></td>
						<td bgcolor="#000000"><img src='<html:rewrite page="/img/dot.gif"/>' width="1" height="100%" /></td>
						<td width="5"></td>
						<td class="col_editar" onclick="send('<html:rewrite page="/editarLibro.do"/>', 'EDITAR')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.propiedadesEditar"/></td>
						<td width="3"></td>
						<td bgcolor="#000000"><img src='<html:rewrite page="/img/dot.gif"/>' width="1" height="100%" /></td>
						<td width="5"></td>
						<td class="col_eliminar" onclick="send('<html:rewrite page="/eliminarLibro.do"/>', 'ELIMINAR')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.eliminar"/></td>
					</tr>
				</table>
			</div>
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr><td height="20" colspan="3"></td></tr>
				<tr>
					<td width="48%" valign="top">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr><td height="10"></td></tr>
							<tr>
								<td class="txt" nowrap>
									<font color="#950101" ><b><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.entrada"/></b></font>
								</td>
								<td width="10">&nbsp;&nbsp;&nbsp;</td>
								<td class="col_config" nowrap onclick="llamadaAction('<html:rewrite page="/configurarNumeracion.do"/>?tipoLibro=1')" ><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.configurar.numeracion" /></td>
								<td width="100%"></td>
							</tr>
							<tr><td height="10" colspan="4"></td></tr>
						</table>
			 			<display:table name="librosEntrada.lista" uid="fila"
							 requestURI="/listadoLibros.do" class="tablaListado" sort="page" style="width:100%">
							 	<display:column titleKey="ieci.tecdoc.sgm.rpadmin.libros.asociacion.select"
									 sortable="false" style="width: 10%;">
									 <input type="radio" name="selLibro" id="selLibro" onclick="selecIdLibro('<bean:write name="fila" property="id" />','<bean:write name="fila" property="nombre" />')"/>
								</display:column>
								<display:column property="nombre" titleKey="ieci.tecdoc.sgm.rpadmin.libros.nombre"
									 sortable="false" style="width: 85%;" />
								<display:column>
									<table cellpadding="0" cellspacing="0" border="0" width="30">
										<tr>
											<td align="center">&nbsp;
											<logic:equal name="fila" property="estado" value="0">
												<c:set var="funcionllamadaUpdateEstadoLibro1">
													llamadaUpdateEstadoLibro("<html:rewrite page='/cambiarEstadoLibro.do'/>?idEstado=1","<bean:write name='fila' property='id' filter='false'/>","<bean:message key='ieci.tecdoc.sgm.rpadmin.libros.cerrar.libro'/>","<bean:write name='fila' property='nombre' filter='false'/>")
												</c:set>
												<a href="#" onclick="<c:out value="${funcionllamadaUpdateEstadoLibro1}"/>"><img src="<html:rewrite page="/img/abrir.gif"/>" alt="<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.cerrar"/>" border="0" /></a>
											</logic:equal>
											<logic:equal name="fila" property="estado" value="1">
												<c:set var="funcionllamadaUpdateEstadoLibro0">
													llamadaUpdateEstadoLibro("<html:rewrite page='/cambiarEstadoLibro.do'/>?idEstado=0","<bean:write name='fila' property='id' filter='false' />","<bean:message key='ieci.tecdoc.sgm.rpadmin.libros.abrir.libro'/>","<bean:write name='fila' property='nombre' filter='false'/>")
												</c:set>
												<a href="#" onclick="<c:out value="${funcionllamadaUpdateEstadoLibro0}"/>"><img src="<html:rewrite page="/img/cerrar.gif" />" alt="<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.abrir" />" border="0" /></a>
											</logic:equal>
											&nbsp;</td>
										</tr>
									</table>
								</display:column>
						</display:table>
					</td>
					<td width="4%"></td>
					<td width="48%" valign="top">
						<table cellpadding="0" cellspacing="0" border="0" width="100%" >
							<tr><td height="10"></td></tr>
							<tr>
								<td class="txt" nowrap><b><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.salida"/></b></td>
								<td width="10">&nbsp;&nbsp;&nbsp;</td>
								<td class="col_config" nowrap onclick="llamadaAction('<html:rewrite page="/configurarNumeracion.do"/>?tipoLibro=2')" ><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.configurar.numeracion" /></td>
								<td width="100%"></td>
							</tr>
							<tr><td height="10" colspan="4"></td></tr>
						</table>
			 			<display:table name="librosSalida.lista" uid="fila"
							 requestURI="/listadoLibros.do" class="tablaListado" sort="page" style="width:100%">
							 	<display:column titleKey="ieci.tecdoc.sgm.rpadmin.libros.asociacion.select"
									 sortable="false" style="width: 10%;">
									 <input type="radio" name="selLibro" id="selLibro" onclick="selecIdLibro('<bean:write name="fila" property="id" />', '<bean:write name="fila" property="nombre" />')" />
								</display:column>
								<display:column property="nombre" titleKey="ieci.tecdoc.sgm.rpadmin.libros.nombre"
									 sortable="false" style="width: 85%;"/>
								<display:column>
									<table cellpadding="0" cellspacing="0" border="0" width="30">
										<tr>
											<td align="center">&nbsp;
											<logic:equal name="fila" property="estado" value="0">
												<c:set var="llamadaUpdateEstadoLibro1">
													llamadaUpdateEstadoLibro("<html:rewrite page='/cambiarEstadoLibro.do'/>?idEstado=1","<bean:write name='fila' property='id' filter='false'/>","<bean:message key='ieci.tecdoc.sgm.rpadmin.libros.cerrar.libro'/>","<bean:write name='fila' property='nombre' filter='false'/>")
												</c:set>
												<a href="#" onclick="<c:out value="${llamadaUpdateEstadoLibro1}"/>"><img src="<html:rewrite page="/img/abrir.gif"/>" alt="<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.cerrar"/>" border="0" /></a>
											</logic:equal>
											<logic:equal name="fila" property="estado" value="1">
												<c:set var="llamadaUpdateEstadoLibro0">
													llamadaUpdateEstadoLibro("<html:rewrite page='/cambiarEstadoLibro.do'/>?idEstado=0","<bean:write name='fila' property='id' filter='false'/>","<bean:message key='ieci.tecdoc.sgm.rpadmin.libros.abrir.libro'/>","<bean:write name='fila' property='nombre' filter='false'/>")
												</c:set>
												<a href="#" onclick="<c:out value="${llamadaUpdateEstadoLibro0}"/>"><img src="<html:rewrite page="/img/cerrar.gif" />" alt="<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.abrir"/>" border="0" /></a>
											</logic:equal>
											&nbsp;</td>
										</tr>
									</table>
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
</form>
</body>
</html>
