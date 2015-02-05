<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

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
<script>
	function activate() {
		choosebox(1,1);
		tabout(1);
	}

	function asignarRepositorio(id, nombre){
		document.forms[0].idLista.value = id;
		document.forms[0].nombreLista.value = nombre;
	}

	function verRepositorio(){
		abreListas('<html:rewrite page="/listadoListas.do"/>' +'?idLista=' + document.forms[0].idLista.value);
	}
</script>
</head>

<body onload="activate()">
<html:form action="/nuevoLibro">
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
				<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.nuevo"/>
			</div>
			<div class="col">
			<table cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="100%" align="left">
						<jsp:include page="includes/errores.jsp"/>
					</td>
					<td class="col_guardar" onclick="llamadaAction('<html:rewrite page="/guardarNuevoLibro.do"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.guardar"/></td>
					<td class="col_eliminar" onclick="llamadaAction('<html:rewrite page="/listadoLibros.do"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.cancelar"/></td>
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
				<table cellpadding="0" cellspacing="0" border="0" width="60%">
					<tr class="col">
						<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.nombre"/>&nbsp;&nbsp;</td>
						<td><html:text property="nombre" styleClass="textInput" maxlength="32"/></td>
					</tr>
					<tr class="col">
						<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.tipo.libro"/>&nbsp;&nbsp;</td>
						<td>
							<html:select property="tipo" styleClass="textInput" style="width:222px">
								<html:optionsCollection name="tipoLibro" property="lista" value="codigo" label="descripcion"/>
							</html:select>
						</td>
					</tr>
					<tr class="col">
						<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.numeracion"/>&nbsp;&nbsp;</td>
						<td>
							<html:select property="numeracion" styleClass="textInput" style="width:222px">
								<html:optionsCollection name="numeracionLibro" property="lista" value="codigo" label="descripcion"/>
							</html:select>
						</td>
					</tr>
					<tr class="col">
						<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.autenticacion.imagenes"/>&nbsp;&nbsp;</td>
						<td>
							<html:checkbox property="autenticacion" styleId="autenticacionId"/>
						</td>
					</tr>
					<tr class="col">
						<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.repositorio"/>&nbsp;&nbsp;</td>
						<td>
							<html:hidden property="idLista"/>
							<html:text property="nombreLista" styleClass="textInput" readonly="true" maxlength="32"/>
							<img alt="<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.editar.repositorio"/>" align="middle"
								 src="<html:rewrite page="/img/ico_edit.gif"/>" onclick="verRepositorio()"/>
						</td>
					</tr>
					<tr><td colspan="2" height="20"></td></tr>
				</table>
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
