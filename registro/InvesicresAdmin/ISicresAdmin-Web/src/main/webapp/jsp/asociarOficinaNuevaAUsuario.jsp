<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
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
<script>
	var idOficina;
	function getCodOfic(idOfic) {
		idOficina = idOfic;
	}

	function llamadaActionAsociarOficinaAUsuario(action, idUsuario) {

		if( idOficina == undefined) {
			alert("<bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.required.asociar.oficina" />");
			return;
		}
		document.forms[0].action = action + "?idUsuario=" +idUsuario + "&idOficina=" + idOficina + "&accion=ASOCIAR";
		document.forms[0].submit();
	}
</script>
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
				<bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.asociar.oficinas"/>
				<span class="migasElementoSeleccionado"><bean:write name="usuario" property="nombre"/></span>&nbsp;
			</div>
			<div class="col" style="height: 30px;">
			<table cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="100%" align="left">
						<jsp:include page="includes/errores.jsp"/>
					</td>
					<td align="right" class="col_nuevo" onclick="llamadaActionAsociarOficinaAUsuario('<html:rewrite page="/asociarOficinaNuevaAUsuario.do"/>', '<bean:write name="usuario" property="id"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.aceptar"/></td>
					<td align="right" class="col_eliminar" onclick="llamadaActionAsociarUsuarioIdOficina('<html:rewrite page="/asociarOficinaUsuario.do"/>','<bean:write name="usuario" property="id"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.cancelar"/></td>
				</tr>
			</table></div>
 			<display:table name="oficinas.lista" uid="fila"
				 requestURI="/oficinas.do" sort="page" class="tablaListado" style="width:100%">
				 <display:column titleKey="ieci.tecdoc.sgm.rpadmin.libros.asociacion.select"
						 sortable="false" style="width: 5%;">
						 	<table cellpadding="0" cellspacing="0" border="0" width="100%">
						 		<tr>
						 			<td align="center"><input type="radio" name="id" onclick="getCodOfic('<bean:write name="fila" property="id" />')"/></td>
						 		</tr>
						 	</table>
					</display:column>
					<display:column titleKey="ieci.tecdoc.sgm.rpadmin.usuarios.nombre"
						 sortable="false" style="width: 95%;">
						 <table cellpadding="0" cellspacing="0" border="0" width="100%">
						 	<tr>
						 		<td width="30" align="center"><img src="<html:rewrite page="/img/departamento.gif"/>" /></td>
						 		<td><bean:write name="fila" property="nombre" /></td>
						 	</tr>
						 </table>
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
