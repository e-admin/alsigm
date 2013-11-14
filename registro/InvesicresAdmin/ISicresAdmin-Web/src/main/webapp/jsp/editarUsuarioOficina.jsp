
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
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/tabsUsuario.js"></script>
<script>
	function init() {
		choosebox(1,2);
		tabout(2);
		tabout(3);
		tabout(4);
		tabout(5);
	}

	function cargarPermisos(){
		var idPerfil = <bean:write name="usuarioForm" property="idPerfil"/>;
		cargarChecksSelected(idPerfil);
	}
</script>
</head>
<body onload="init(); cargarPermisos();">
<html:form action="/editarUsuario.do">
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
				<span class="migasElementoSeleccionado"><bean:write name="nombreOficina"/></span>
				<span class="migasElementoSeleccionado"><bean:write name="usuarioForm" property="nombre"/></span>&nbsp;
			</div>
			<div class="col" align="right">
				<table>
				<tr>
					<td width="100%" align="left">
						<jsp:include page="includes/errores.jsp"/>
					</td>
					<td class="col_guardar" onclick="llamadaAction('<html:rewrite page="/guardarEdicionUsuarioOficina.do"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.guardar"/></td>
					<td class="col_eliminar" onclick="llamadaActionUsersOficina('<html:rewrite page="/usuariosOficina.do"/>','<bean:write name='idOficina' filter='false'/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.cancelar"/></td>
				</tr>
				</table></div>
 			<div class="cuerpomidUsuarios">
		      <div class="submenuUsuario" id="nuevoUsuarioPestana" >
					<table cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td>
								<div id="tab1" onmouseover="tabover(1)" onmouseout="tabout(1)" onclick="choosebox(1,2)">
									<table summary="" border="0" cellpadding="0" cellspacing="0">
									<tbody><tr>
										<td class="tableft" height="17" width="7">
											<img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
										<td class="tabmiddle1" id="tabmiddle1" onclick="nuevoUsuarioClick()"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.usuario.nuevo"/></td>
										<td class="tabright"><img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
									</tr>
									</tbody></table>
								</div>
							</td>
							<td>
								<div id="tab2" onmouseover="tabover(2)" onmouseout="tabout(2)" onclick="choosebox(2,2)">
									<table summary="" border="0" cellpadding="0" cellspacing="0">
									<tbody><tr>
										<td class="tableft" height="17" width="7">
											<img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
										<td class="tabmiddle2" id="tabmiddle2" onclick="permisosUsuarioClick()"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.usuario.permisos"/></td>
										<td class="tabright"><img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
									</tr>
									</tbody></table>
								</div>
							</td>
							<td>
								<div id="tab3" onmouseover="tabover(3)" onmouseout="tabout(3)" onclick="choosebox(3,2)">
									<table summary="" border="0" cellpadding="0" cellspacing="0">
									<tbody><tr>
										<td class="tableft" height="17" width="7">
											<img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
										<td class="tabmiddle3" id="tabmiddle3" onclick="permisosAdministracionClick()"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.usuario.permisos.administracion"/></td>
										<td class="tabright"><img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
									</tr>
									</tbody></table>
								</div>
							</td>
							<td>
								<div id="tab4" onmouseover="tabover(4)" onmouseout="tabout(4)" onclick="choosebox(4,2)">
									<table summary="" border="0" cellpadding="0" cellspacing="0">
									<tbody><tr>
										<td class="tableft" height="17" width="7">
											<img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
										<td class="tabmiddle4" id="tabmiddle4" onclick="identificacionUsuarioClick()"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.usuario.identificacion"/></td>
										<td class="tabright"><img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
									</tr>
									</tbody></table>
								</div>
							</td>
							<td>
								<div id="tab5" onmouseover="tabover(5)" onmouseout="tabout(5)" onclick="choosebox(5,2)">
								<table summary="" border="0" cellpadding="0" cellspacing="0">
								<tbody><tr>
									<td class="tableft" height="17" width="7">
										<img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
									<td class="tabmiddle5" id="tabmiddle5" onclick="localizacionUsuarioClick()"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.usuario.localizacion"/></td>
									<td class="tabright"><img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
								</tr>
								</tbody></table>
								</div>
							</td>
							<td width="40%"></td>
						</tr>
						</table>
				</div>

		      	<html:hidden property="id"/>
	          <div class="cuadroUsuario">
				<div style="height: 200px;">
					<div id="nuevoUsuario">
					<table cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td width="10" rowspan="80"></td>
							<td class="col" height="20" colspan="4"></td>
						</tr>
						<tr class="col">
							<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.nombre"/>&nbsp;&nbsp;</td>
							<td><html:text property="nombre" styleClass="textInput" style="color:#808080" readonly="true" maxlength="32"/></td>
						</tr>
						<tr class="col">
							<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.perfil"/>&nbsp;&nbsp;</td>
							<td>
								<html:select property="idPerfil" styleId="idPerfil" styleClass="textInput"  onchange="checkSuperUser(this.value, 'EDICION', false)">
									<html:option value=""><bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.seleccione.perfil" /></html:option>
									<html:optionsCollection name="perfil" property="lista" value="codigo" label="descripcion"/>
								</html:select>
							</td>
						</tr>
						<tr><td height="8" colspan="4"></td></tr>
						<tr><td height="100%" colspan="4"></td></tr>
					</table>
					</div>

					<div id="permisosUsuario" style="display:none">
						<table cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td width="10" rowspan="80"></td>
								<td class="col" height="20"></td>
								<td width="100" rowspan="30" ></td>
								<td class="col"></td>
							</tr>
							<tr class="col">
								<td class="txt">
									<input type="checkbox" name="altaPersonasCheck" style="width:20px" id="altaPersonasCheck"
										onclick="document.forms[0].altaPersonas.value=this.checked;"
										<logic:equal name="usuarioForm" property="altaPersonas" value="true">checked</logic:equal> />
									&nbsp;<bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.permisos.alta.personas.fisicas"/>
									<html:hidden property="altaPersonas"/>
								</td>
								<td class="txt">
									<input type="checkbox" name="adaptacionRegistrosCheck" style="width:20px" id="adaptacionRegistrosCheck"
										onclick="document.forms[0].adaptacionRegistros.value=this.checked;"
										<logic:equal name="usuarioForm" property="adaptacionRegistros" value="true">checked</logic:equal> />
									&nbsp;<bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.permisos.aceptacion.registros.distribuidos"/>
									<html:hidden property="adaptacionRegistros"/>
								</td>
							</tr>
							<tr class="col">
								<td class="txt">
									<input type="checkbox" name="modificaPersonasCheck" style="width:20px" id="modificaPersonasCheck"
										onclick="document.forms[0].modificaPersonas.value=this.checked;"
										<logic:equal name="usuarioForm" property="modificaPersonas" value="true">checked</logic:equal> />
									&nbsp;<bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.permisos.modificacion.personas.fisicas"/>
									<html:hidden property="modificaPersonas"/>
								</td>
								<td class="txt">
									<input type="checkbox" name="rechazoRegistrosCheck" style="width:20px" id="rechazoRegistrosCheck"
										onclick="document.forms[0].rechazoRegistros.value=this.checked;"
										<logic:equal name="usuarioForm" property="rechazoRegistros" value="true">checked</logic:equal> />
									&nbsp;<bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.permisos.rechazo.registros.distribuidos"/>
									<html:hidden property="rechazoRegistros"/>
								</td>
							</tr>
							<tr class="col">
								<td class="txt">
									<input type="checkbox" name="introduccionFechaCheck" style="width:20px" id="introduccionFechaCheck"
										onclick="document.forms[0].introduccionFecha.value=this.checked;"
										<logic:equal name="usuarioForm" property="introduccionFecha" value="true">checked</logic:equal> />
									&nbsp;<bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.permisos.introduccion.fecha.registro"/>
									<html:hidden property="introduccionFecha"/>
								</td>
								<td class="txt">
									<input type="checkbox" name="archivoRegistrosCheck" style="width:20px" id="archivoRegistrosCheck"
										onclick="document.forms[0].archivoRegistros.value=this.checked;"
										<logic:equal name="usuarioForm" property="archivoRegistros" value="true">checked</logic:equal> />
									&nbsp;<bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.permisos.archivo.registros.distribuidos"/>
									<html:hidden property="archivoRegistros" />
								</td>
							</tr>
							<tr class="col">
								<td class="txt">
									<input type="checkbox" name="modificacionFechaCheck" style="width:20px" id="modificacionFechaCheck"
										onclick="document.forms[0].modificacionFecha.value=this.checked;"
										<logic:equal name="usuarioForm" property="modificacionFecha" value="true">checked</logic:equal> />
									&nbsp;<bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.permisos.modificacion.fecha.registro"/>
									<html:hidden property="modificacionFecha"/>
								</td>
								<td class="txt">
									<input type="checkbox" name="cambioDestinoDistribuidosCheck" style="width:20px" id="cambioDestinoDistribuidosCheck"
										onclick="document.forms[0].cambioDestinoDistribuidos.value=this.checked;"
										<logic:equal name="usuarioForm" property="cambioDestinoDistribuidos" value="true">checked</logic:equal> />
									&nbsp;<bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.permisos.cambio.destino.distribuidos"/>
									<html:hidden property="cambioDestinoDistribuidos"/>
								</td>
							</tr>
							<tr class="col">
								<td class="txt">
									<input type="checkbox" name="modificacionCamposCheck" style="width:20px" id="modificacionCamposCheck"
										onclick="document.forms[0].modificacionCampos.value=this.checked;"
										<logic:equal name="usuarioForm" property="modificacionCampos" value="true">checked</logic:equal> />
									&nbsp;<bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.permisos.modificacion.campos.protegidos"/>
									<html:hidden property="modificacionCampos"/>
								</td>
								<td class="txt">
									<input type="checkbox" name="cambioDestinoRechazadosCheck" style="width:20px" id="cambioDestinoRechazadosCheck"
										onclick="document.forms[0].cambioDestinoRechazados.value=this.checked;"
										<logic:equal name="usuarioForm" property="cambioDestinoRechazados" value="true">checked</logic:equal> />
									&nbsp;<bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.permisos.cambio.destino.rechazados"/>
									<html:hidden property="cambioDestinoRechazados"/>
								</td>
							</tr>
							<tr class="col">
								<td class="txt">
									<input type="checkbox" name="accesoOperacionesCheck" style="width:20px" id="accesoOperacionesCheck"
										onclick="document.forms[0].accesoOperaciones.value=this.checked;"
										<logic:equal name="usuarioForm" property="accesoOperaciones" value="true">checked</logic:equal> />
									&nbsp;<bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.permisos.acceso.operaciones.intercambio"/>
									<html:hidden property="accesoOperaciones"/>
								</td>
								<td class="txt">
									<input type="checkbox" name="distribucionManualCheck" style="width:20px" id="distribucionManualCheck"
										onclick="document.forms[0].distribucionManual.value=this.checked;"
										<logic:equal name="usuarioForm" property="distribucionManual" value="true">checked</logic:equal> />
									&nbsp;<bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.permisos.distribucion.manual"/>
									<html:hidden property="distribucionManual"/>
								</td>
							</tr>
							<tr class="col">
								<td class="txt">
									<input type="checkbox" name="verDocumentosCheck" style="width:20px" id="verDocumentosCheck"
										onclick="document.forms[0].verDocumentos.value=this.checked;"
										<logic:equal name="usuarioForm" property="verDocumentos" value="true">checked</logic:equal> />
									&nbsp;<bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.permisos.ver.documentacion"/>
									<html:hidden property="verDocumentos"/>
								</td>
								<td class="txt">
									<input type="checkbox" name="deleteDocumentosCheck" style="width:20px" id="deleteDocumentosCheck"
										onclick="document.forms[0].deleteDocumentos.value=this.checked;"
										<logic:equal name="usuarioForm" property="deleteDocumentos" value="true">checked</logic:equal> />
									&nbsp;<bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.permisos.delete.documentacion"/>
									<html:hidden property="deleteDocumentos"/>
								</td>
							</tr>
							<tr><td height="100%" colspan="4"></td></tr>
						</table>
					</div>

					<div id="permisosAdministracion" style="display:none">
						<table cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td width="10" rowspan="80"></td>
								<td class="col" height="20"></td>
								<td width="100" rowspan="30" ></td>
								<td class="col"></td>
							</tr>
							<tr class="col">
								<td class="txt">
									<input type="checkbox" name="gestionUnidadesAdministrativasCheck" style="width:20px" id="gestionUnidadesAdministrativasCheck"
										onclick="document.forms[0].gestionUnidadesAdministrativas.value=this.checked;"
										<logic:equal name="usuarioForm" property="gestionUnidadesAdministrativas" value="true">checked</logic:equal> />
									&nbsp;<bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.permisos.administracion.unidadesAdministrativas"/>
									<html:hidden property="gestionUnidadesAdministrativas"/>
								</td>
								<td class="txt">
									<input type="checkbox" name="gestionInformesCheck" style="width:20px" id="gestionInformesCheck"
										onclick="document.forms[0].gestionInformes.value=this.checked;"
										<logic:equal name="usuarioForm" property="gestionInformes" value="true">checked</logic:equal> />
									&nbsp;<bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.permisos.administracion.informes"/>
									<html:hidden property="gestionInformes"/>
								</td>
							</tr>
							<tr class="col">
								<td class="txt">
									<input type="checkbox" name="gestionTiposAsuntoCheck" style="width:20px" id="gestionTiposAsuntoCheck"
										onclick="document.forms[0].gestionTiposAsunto.value=this.checked;"
										<logic:equal name="usuarioForm" property="gestionTiposAsunto" value="true">checked</logic:equal> />
									&nbsp;<bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.permisos.administracion.tiposAsunto"/>
									<html:hidden property="gestionTiposAsunto"/>
								</td>
								<td class="txt">
									<input type="checkbox" name="gestionUsuariosCheck" style="width:20px" id="gestionUsuariosCheck"
										onclick="document.forms[0].gestionUsuarios.value=this.checked;"
										<logic:equal name="usuarioForm" property="gestionUsuarios" value="true">checked</logic:equal> />
									&nbsp;<bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.permisos.administracion.usuarios"/>
									<html:hidden property="gestionUsuarios"/>
								</td>
							</tr>
							<tr class="col">
								<td class="txt">
									<input type="checkbox" name="gestionTiposTransporteCheck" style="width:20px" id="gestionTiposTransporteCheck"
										onclick="document.forms[0].gestionTiposTransporte.value=this.checked;"
										<logic:equal name="usuarioForm" property="gestionTiposTransporte" value="true">checked</logic:equal> />
									&nbsp;<bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.permisos.administracion.tiposTransporte"/>
									<html:hidden property="gestionTiposTransporte"/>
								</td>
								<td class="txt">

								</td>
							</tr>

						</table>
					</div>


					<div id="identificacionUsuario" style="display:none">
						<table cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td width="10" rowspan="80"></td>
								<td class="col" height="20" colspan="4"></td>
							</tr>
							<tr class="col">
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.identificacion.primer.apellido"/>&nbsp;&nbsp;</td>
								<td><html:text property="primerApellido" styleClass="textInput" maxlength="25"/></td>
							</tr>
							<tr class="col">
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.identificacion.segundo.apellido"/>&nbsp;&nbsp;</td>
								<td><html:text property="segundoApellido" styleClass="textInput" maxlength="25"/></td>
							</tr>
							<tr class="col">
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.identificacion.nombre"/>&nbsp;&nbsp;</td>
								<td><html:text property="nombreIdentificacion" styleClass="textInput" maxlength="20"/></td>
							</tr>
							<tr><td height="100%" colspan="4"></td></tr>
						</table>
					</div>
					<div id="localizacionUsuario" style="display:none">
						<table cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td width="10" rowspan="80"></td>
								<td class="col" height="20" colspan="2"></td>
								<td width="50" rowspan="15"></td>
								<td class="col" height="20" colspan="2"></td>
							</tr>
							<tr class="col">
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.localizacion.direccion"/>&nbsp;&nbsp;</td>
								<td><html:text property="direccion" styleClass="textInput" maxlength="255"/></td>
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.localizacion.email"/>&nbsp;&nbsp;</td>
								<td><html:text property="email" styleClass="textInput" maxlength="255"/></td>
							</tr>
							<tr class="col">
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.localizacion.ciudad"/>&nbsp;&nbsp;</td>
								<td><html:text property="ciudad" styleClass="textInput" maxlength="100"/></td>
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.localizacion.fax"/>&nbsp;&nbsp;</td>
								<td><html:text property="fax" styleClass="textInput" maxlength="160"/></td>
							</tr>
							<tr class="col">
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.localizacion.codigoPostal"/>&nbsp;&nbsp;</td>
								<td><html:text property="codigoPostal" styleClass="textInput" maxlength="10"/></td>
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.localizacion.telefono"/>&nbsp;&nbsp;</td>
								<td><html:text property="telefono" styleClass="textInput" maxlength="160"/></td>
							</tr>
							<tr>
								<td class="txtCol"><bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.localizacion.provincia"/>&nbsp;&nbsp;</td>
								<td class="col"><html:text property="provincia" styleClass="textInput" maxlength="100"/></td>
								<td colspan="2"></td>
							</tr>
							<tr><td height="100%" colspan="4"></td></tr>
						</table>
					</div>
				</div>
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
