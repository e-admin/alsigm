<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="ieci.tecdoc.isicres.rpadmin.struts.util.AutenticacionAdministracion" %>
<%@ page import="es.ieci.tecdoc.isicres.admin.core.services.ConstantesGestionUsuariosAdministracion" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="IECISA" />
<title><bean:message key="ieci.tecdoc.sgm.pgadmin.title.aplication"/> - <bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.titulo"/></title>
<link href="css/estilos.css" rel="stylesheet" type="text/css" />
<link href="css/adminApp.css" rel="stylesheet" type="text/css" />
<link href="css/calendar-win2k-1.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/tabsUnidad.js"></script>
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/calendar-es.js"></script>
<script>
	function init() {
		choosebox(1,2);
		tabout(2);
		tabout(3);
		nuevaUnidadClick();
	}

	var urlSessionExpired = '<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REGISTRO) %>';

	function chequear( url ) {
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false") {
			llamadaAction( url );
		} else {
			window.parent.redirect(urlSessionExpired);
		}
	}

	function chequearGuardar(url){
		var mensajeError = '<bean:message key="ieci.tecdoc.sgm.rpadmin.error.mensaje.intercambioRegistral.validate"/>';
		if(validateFormularioUnidadesAdm(mensajeError)){
			var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
			if( check == "false") {
				llamadaAction( url );
			} else {
				window.parent.redirect(urlSessionExpired);
			}
		}
	}

</script>
</head>

<body onload="init()">
<html:form action="/nuevaUnidad.do">
<table cellpadding="0" cellspacing="0" border="0">
<tr>
	<td>
 	 <!-- Inicio Contenido -->
		<div id="migas">
			<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.nueva"/>
		</div>
			<table cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="100%" align="left">
						<jsp:include page="includes/errores.jsp"/>
					</td>
					<td align="right" class="col_guardar" onclick="chequearGuardar('<html:rewrite page="/guardarNuevaUnidad.do"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.aceptar"/></td>
					<td class="col_volver" onclick="chequear('<html:rewrite page="/listadoUnidad.do"/>')">
						<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.volver"/>
					</td>
				</tr>
			</table>
			<div class="cuerpomidUsuarios">

				<div class="submenuUsuario" id="nuevaUnidadPestana" >
					<table cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td>
								<div id="tab1" onmouseover="tabover(1)" onmouseout="tabout(1)" onclick="choosebox(1,2)">
									<table summary="" border="0" cellpadding="0" cellspacing="0">
									<tbody><tr>
										<td class="tableft" height="17" width="7">
											<img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
										<td class="tabmiddle1" id="tabmiddle1" onclick="nuevaUnidadClick()"><bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.pestana"/></td>
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
										<td class="tabmiddle2" id="tabmiddle2" onclick="direccionUnidadClick()"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.oficina.direccion"/></td>
										<td class="tabright"><img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
									</tr>
									</tbody></table>
								</div>
							</td>
							<td>
								<logic:equal value="true" scope="session" name="enabledIntercambioRegistral">
									<div id="tab3" onmouseover="tabover(3)" onmouseout="tabout(3)" onclick="choosebox(3,2)">
								</logic:equal>
								<logic:notEqual value="true" scope="session" name="enabledIntercambioRegistral">
									<div id="tab3" onmouseover="tabover(3)" onmouseout="tabout(3)" onclick="choosebox(3,2)" style="visibility:hidden;">
								</logic:notEqual>

									<table summary="" border="0" cellpadding="0" cellspacing="0">
									<tbody><tr>
										<td class="tableft" height="17" width="7">
											<img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
										<td class="tabmiddle3" id="tabmiddle3" onclick="intercambioRegUnidad()"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.intercambioRegistral"/></td>
										<td class="tabright"><img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
									</tr>
									</tbody></table>
								</div>
							</td>
							<td width="10%"></td>
						</tr>
						</table>
				</div>
				<div class="cuadroUsuario">
					<div id="nuevaUnidad">
						<html:hidden property="idPadre"/>
						<html:hidden property="tipo"/>
						<table cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td width="10" rowspan="80"></td>
								<td class="col" height="20" colspan="2"></td>
								<td width="100" rowspan="30" ></td>
								<td class="col" colspan="2"></td>
							</tr>
							<tr class="col">
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.nueva.uid"/>&nbsp;&nbsp;</td>
								<td><html:text property="uid" styleClass="textInput" style="width:160px" maxlength="16"/></td>
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.nueva.cif"/>&nbsp;&nbsp;</td>
								<td><html:text property="cif" styleClass="textInput" style="width:160px" maxlength="15"/></td>
							</tr>
							<tr class="col">
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.nueva.abreviatura"/>&nbsp;&nbsp;</td>
								<td><html:text property="abreviatura" styleClass="textInput" style="width:160px" maxlength="12"/></td>
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.nueva.nombre"/>&nbsp;&nbsp;</td>
								<td><html:text property="nombre" styleClass="textInput" style="width:160px" maxlength="250"/></td>
							</tr>
							<tr><td height="20" colspan="2"></td></tr>
						</table>
					</div>

					<div id="direccionUnidad">
						<table cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td width="10" rowspan="80"></td>
								<td class="col" height="20" colspan="2"></td>
								<td width="100" rowspan="30" ></td>
								<td class="col" colspan="2"></td>
							</tr>
							<tr class="col">
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.direccion.domicilio"/>&nbsp;&nbsp;</td>
								<td><html:text property="direccion" styleClass="textInput" style="width:160px"  maxlength="255"/></td>
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.direccion.uri"/>&nbsp;&nbsp;</td>
								<td><html:text property="uri" styleClass="textInput" style="width:160px"  maxlength="255"/></td>
							</tr>
							<tr class="col">
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.direccion.ciudad"/>&nbsp;&nbsp;</td>
								<td><html:text property="ciudad" styleClass="textInput" style="width:160px"  maxlength="100"/></td>
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.direccion.fax"/>&nbsp;&nbsp;</td>
								<td><html:text property="fax" styleClass="textInput" style="width:160px"  maxlength="160"/></td>
							</tr>
							<tr class="col">
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.direccion.codigoPostal"/>&nbsp;&nbsp;</td>
								<td><html:text property="codigoPostal" styleClass="textInput" style="width:160px"  maxlength="10"/></td>
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.direccion.telefono"/>&nbsp;&nbsp;</td>
								<td><html:text property="telefono" styleClass="textInput" style="width:160px" maxlength="160"/></td>
							</tr>
							<tr>
								<td class="txtCol"><bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.direccion.provincia"/>&nbsp;&nbsp;</td>
								<td class="col"><html:text property="provincia" styleClass="textInput" style="width:160px"  maxlength="100"/></td>
								<td colspan="2"></td>
							</tr>
							<tr><td height="20" colspan="4"></td></tr>
						</table>
					</div>

					<div id="intercambioRegistralUnidad" style="display:none">
						<table cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td width="10" rowspan="80"></td>
								<td height="5" colspan="2"></td>
								<td width="100" rowspan="30" ></td>
								<td colspan="2"></td>
							</tr>
							<tr>
								<td colspan="4" class="col"><bean:message key="ieci.tecdoc.sgm.rpadmin.intercambioRegistral.entidad.pestana"/></td>
							</tr>
							<tr class="col">
								<td class="txt" width="220px"><bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.intercambio.codigo"/>&nbsp;&nbsp;</td>
								<td class="txt">
									<table cellpadding="0" cellspacing="0">
										<tr>
											<td>
												<html:hidden property="idUnidadTramit"/>
												<html:text property="codEntidadReg" styleClass="textInput" maxlength="21" style="width:190px"/>&nbsp;&nbsp;
											</td>
											<td>
												<a href="#" onclick="javascript:chequearSessionBusquedaDC('<html:rewrite page="/jsp/iFrameBusquedaOficinasDirectorioComun.jsp"/>', urlSessionExpired);"><img src="<html:rewrite page="/img/ico_buscar.gif"/>" border="0"/></a>
											</td>
										</tr>
									</table>
								</td>
								<td colspan="4"></td>
							</tr>
							<tr class="col">
								<td class="txt" width="220px"><bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.intercambio.nombre"/>&nbsp;&nbsp;</td>
								<td><html:text property="nameEntidadReg" styleClass="textInput" maxlength="80" style="width:380px;"/></td>
								<td colspan="4"></td>
							</tr>
							<tr>
								<td colspan="4" height="30"/>
							</tr>
							<tr>
								<td colspan="4" class="col"><bean:message key="ieci.tecdoc.sgm.rpadmin.intercambioRegistral.unidad.pestana"/></td>
							</tr>
							<tr class="col">
								<td class="txt" width="220px"><bean:message key="ieci.tecdoc.sgm.rpadmin.intercambioRegistral.listado.codigo"/>&nbsp;<bean:message key="ieci.tecdoc.sgm.rpadmin.intercambioRegistral.unidad.pestana"/>&nbsp;&nbsp;</td>
								<td class="txt">
									<table cellpadding="0" cellspacing="0">
										<tr>
											<td>
												<html:text property="codeUnidadTramit" styleClass="textInput" maxlength="21" style="width:190px"/>&nbsp;&nbsp;
											</td>
											<td>
												<a href="#" onclick="javascript:chequearSessionBusquedaDC('<html:rewrite page="/jsp/iFrameBusquedaUnidadesOrganicasDC.jsp"/>', urlSessionExpired);"><img src="<html:rewrite page="/img/ico_buscar.gif"/>" border="0"/></a>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr class="col">
								<td class="txt" width="220px"><bean:message key="ieci.tecdoc.sgm.rpadmin.intercambioRegistral.listado.nombre"/>&nbsp;<bean:message key="ieci.tecdoc.sgm.rpadmin.intercambioRegistral.unidad.pestana"/>&nbsp;&nbsp;</td>
								<td><html:text property="nameUnidadTramit" styleClass="textInput" maxlength="80" style="width:380px;"/></td>
								<td colspan="4"></td>
							</tr>
							<tr>
								<td height="100%" colspan="4"></td>
							</tr>
						</table>
					</div>
			</div>
		  </div>
	</td>
</tr>
</table>
</html:form>

</body>
</html>
