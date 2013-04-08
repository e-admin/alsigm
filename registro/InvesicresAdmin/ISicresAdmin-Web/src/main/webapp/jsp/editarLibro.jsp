<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page import="es.ieci.tecdoc.isicres.admin.beans.Libro" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="IECISA" />
<title><bean:message key="ieci.tecdoc.sgm.pgadmin.title.aplication"/> - <bean:message key="ieci.tecdoc.sgm.rpadmin.libros.titulo"/></title>
<link href="css/estilos.css" rel="stylesheet" type="text/css" />
<link href="css/adminApp.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/tabsLibro.js"></script>
<script type="text/javascript" src="js/controlCambios.js"></script>
<script>
	function activate() {
		choosebox(1,1);
		tabout(1);
		addControlCambios();
		actualizaEstado();
	}

	function guardarEdicion(action) {
		/*var tipo;
		for(var i = 0; i < 3; i++) {
			if( document.forms[0].tipo[i].selected) {
				tipo = document.forms[0].tipo[i].value;
				break;
			}
		}*/
		llamadaAction(action);
	}

	function asignarRepositorio(id, nombre){
		document.forms[0].idLista.value = id;
		document.forms[0].nombreLista.value = nombre;
		cambio();
	}

	function controllerBookClosed(action, idLibro, idOficina, nombre, idEstado, tipo, msg) {
		if( idEstado != "1"){
			//llamadaActionIdEliminarAsociacion(action, idLibro, idOficina, nombre, msg);
			var accion = action + "?idLibro="+idLibro+"&idOficina="+idOficina;
			if(confirm( msg + " '" + nombre + "' ?" )) {
				llamadaActionComprobarCambios(accion, true,"<bean:message key="ieci.tecdoc.sgm.rpadmin.cambios.sin.guardar"/>");
			}
		}
	}

	function abrirListaVolumenes(){
		var idLista = document.forms[0].idLista.value;
		var idLibro = document.forms[0].id.value;
		abreListas('<html:rewrite page="/listadoListas.do"/>' +'?id='+ idLibro + '&idLista=' + idLista);
	}

	function llamadaFiltrosOficina(accion, idLibro, idObjeto, nombreObjeto){
		var action = accion + "?idLibro="+idLibro+"&idObjeto="+idObjeto+"&nombreObjeto="+nombreObjeto+"&tipoFiltro=2";
		llamadaActionComprobarCambios(action, true,"<bean:message key="ieci.tecdoc.sgm.rpadmin.cambios.sin.guardar"/>");
	}

	function actualizarLibro(msg, action){
		if(confirm(msg)){
			llamadaAction(action);
		}
	}

</script>
</head>

<body onload="activate()">
<html:form action="/editarLibro">
<html:hidden property="id"/>
<html:hidden property="cambios"/>
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
				<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.propiedadesEditar"/>&nbsp;
				<span class="migasElementoSeleccionado"><bean:write name="libroForm" property="nombre"/></span>&nbsp;
			</div>
			<div class="col">
			<table cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="100%" align="left">
						<jsp:include page="includes/errores.jsp"/>
					</td>
					<%--<td class="col_eliminar" onclick="llamadaAction('<html:rewrite page="/listadoLibros.do"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.close"/></td>--%>
					<c:set var="funcionllamadaActionComprobarCambios">
						llamadaActionComprobarCambios("<html:rewrite page='/listadoLibros.do'/>",true,"<bean:message key='ieci.tecdoc.sgm.rpadmin.cambios.sin.guardar'/>")
					</c:set>
					<td class="col_volver" onclick="<c:out value="${funcionllamadaActionComprobarCambios}"/>">
						<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.volver" />
					</td>
				</tr>
			</table>
			</div>
			<table cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td height="12"></td>
				</tr>
			</table>
			 <div class="submenuUsuario" id="nuevoUsuarioPestana" >
				<table cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td>
							<div id="tab1" onmouseover="tabover(1)" onmouseout="tabout(1)" onclick="choosebox(1,2)">
								<table summary="" border="0" cellpadding="0" cellspacing="0">
								<tbody><tr>
									<td class="tableft" height="17" width="7">
										<img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
									<td class="tabmiddle1" id="tabmiddle1"><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.libro.registro"/></td>
									<td class="tabright"><img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
								</tr>
								</tbody></table>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div class="cuadroUsuario">
				<div class="col" style="height: 30px; padding-top:4px;">
					<table cellspacing="0" cellpadding="0" border="0" width="100%">
						<tr>
							<td align="left">
								<b><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.informacion.libro"/></b>
							</td>
							<td align="right">
								<table cellspacing="0" cellpadding="0" border="0">
									<tr align="right">
										<logic:notEqual name="estado" value="1">
											<td class="col_guardar" onclick="guardarEdicion('<html:rewrite page="/guardarEdicionLibro.do"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.guardar"/></td>
										</logic:notEqual>

										<logic:equal name="libroForm" property="actualizableASicres3" value="true">
											<td class="col_config" onclick="actualizarLibro('<bean:message key="ieci.tecdoc.sgm.rpadmin.update.sicres3"/>' ,'<html:rewrite page="/actualizarLibroASicres3.do"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.actualizarASicres3"/></td>
										</logic:equal>

										<html:hidden property="actualizableASicres3"/>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				<table cellpadding="0" cellspacing="0" border="0" width="60%">
					<tr class="col">
						<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.nombre"/>&nbsp;&nbsp;</td>
						<td><html:text property="nombre" styleClass="textInput" maxlength="32" /></td>
					</tr>
					<tr class="col">
						<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.tipo.libro"/>&nbsp;&nbsp;</td>
						<td>
							<html:hidden property="tipo"/>
							<html:select property="tipo" styleClass="textInput" style="width:222px" disabled="true" >
								<html:optionsCollection name="tipoLibro" property="lista" value="codigo" label="descripcion"/>
							</html:select>
						</td>
					</tr>
					<tr class="col">
						<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.numeracion"/>&nbsp;&nbsp;</td>
						<td>
								<logic:equal name="estado" value="1">
									<html:select property="numeracion" styleClass="textInput" style="width:222px" disabled="true">
										<html:optionsCollection name="numeracionLibro" property="lista" value="codigo" label="descripcion"/>
									</html:select>
								</logic:equal>
								<logic:notEqual name="estado" value="1">
									<html:select property="numeracion" styleClass="textInput" style="width:222px">
										<html:optionsCollection name="numeracionLibro" property="lista" value="codigo" label="descripcion"/>
									</html:select>
								</logic:notEqual>
						</td>
					</tr>
					<tr class="col">
						<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.autenticacion.imagenes"/>&nbsp;&nbsp;</td>
						<td>
							<html:hidden property="autenticacion" styleId="autenticacion"/>
							<input type="checkbox" name="autenticacionId" id="autenticacionId"
								onclick="document.getElementById('autenticacion').value=this.checked"
								<logic:equal name="libroForm" property="autenticacion" value="1">checked</logic:equal>
								<logic:equal name="libroForm" property="autenticacion" value="true">checked</logic:equal>
								<logic:equal name="estado" value="1">disabled</logic:equal> />
						</td>
					</tr>
					<tr class="col">
						<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.repositorio"/>&nbsp;&nbsp;</td>
						<td>
							<html:hidden property="idLista" styleId="idLista"/>
							<html:text property="nombreLista" styleClass="textInput" readonly="true" maxlength="32"/>
							<img alt="<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.editar.repositorio"/>" align="middle"
								 src="<html:rewrite page="/img/ico_edit.gif"/>" onclick="abrirListaVolumenes();"/>
						</td>
					</tr>
				</table>
			</div>
			<div class="cuadroOficinas">
				<jsp:include page="listadoOficinasAsociadas.jsp"/>
			</div>
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
