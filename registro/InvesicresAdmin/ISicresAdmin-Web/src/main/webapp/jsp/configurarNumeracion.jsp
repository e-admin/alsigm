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
<script type="text/javascript" src="js/tabsLibro.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script>
	function activate() {
		choosebox(1,1);
		tabout(1);
	}

	function dameNumeracion( action ) {
		var valor;
		var field = document.forms[0].anyo;
		for( i = 0; i < field.length; i++) {
			if( field.options[i].selected ) {
				valor = field.options[i].value;
				break;
			}
		}
		document.forms[0].action = '<html:rewrite page="/configurarNumeracion.do"/>' + '?anyo='+valor+'&tipoLibro=' + '<bean:write name="tipoLibro" />';
		document.forms[0].submit();
	}

	function validarContadores(action, tipoLibro) {
		var tamanio = '<bean:write name="contadoresSize" />';
		var valorActual;
		var valorDefault;
		var oficina;
		var error = false;
		// Validamos que los contadores no sean inferiores al valor por predeterminado
		for( i = 0; i < tamanio; i++) {
			// valorActual = document.getElementById('contador_'+i).value;
			valorActual = document.getElementById('contadores['+i+'].contador').value;
			valorDefault = document.getElementById('contadorDefault_'+i).value;
			oficina = document.getElementById('oficinaDefault_'+i).value;
			if (/^([0-9])*$/.test(valorActual)){
				if( parseInt(valorDefault) > parseInt(valorActual)) {
					var msgO = "<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.validacion.contadores.oficina" />";
					var msgC = "<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.validacion.contadores.contador" />";
					alert(msgO + "'"+oficina+"' " + msgC + " " + valorDefault);
					error = true;
					return;
				}
			}else{
				var msgO = "<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.validacion.contadores.oficina" />";
				var msgC = "<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.validacion.contadores.numero" />";
				alert(msgO + "'"+oficina+"' " + msgC);
				error = true;
				return;
			}
		}

		valorActual = document.getElementById('central').value;
		valorDefault = document.getElementById('centralDefault').value;
		//validamos que el valor central sea numerico
		if (/^([0-9])*$/.test(valorActual)){
			// Validamos que central no sea inferior a valor predeterminado
		if( parseInt(valorDefault) > parseInt(valorActual)) {
			var msgO = '<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.validacion.contadores.central" />';
			alert(msgO + " " + valorDefault);
			document.forms[0].central.focus();
			error = true;
			return;
		}
		}else{
			alert('<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.validacion.contadores.central.numero" />');
			document.forms[0].central.focus();
			error = true;
			return;
		}
			llamadaActionGuardarNumeracion(action, tipoLibro);
	}
</script>
</head>

<body onload="activate()">
<html:form action="/configurarNumeracion.do">
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
				<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.configurar.numeracion"/>
			</div>
			<div class="col" style="height: 30px;">
			<table cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="100%" align="left">
						<jsp:include page="includes/errores.jsp"/>
					</td>
					<td align="right" class="col_nuevo" onclick="validarContadores('<html:rewrite page="/guardarNumeracion.do"/>','<bean:write name="tipoLibro" />')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.guardar"/></td>
					<td align="right" class="col_eliminar" onclick="llamadaAction('<html:rewrite page="/listadoLibros.do"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.cancelar"/></td>
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
									<td class="tabmiddle1" id="tabmiddle1">
										<logic:equal name="tipoLibro" value="1">
											<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.configurar.numeracion.entrada"/>
										</logic:equal>
										<logic:notEqual name="tipoLibro" value="1">
											<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.configurar.numeracion.salida"/>
										</logic:notEqual>
									</td>
									<td class="tabright"><img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
								</tr>
								</tbody></table>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div class="cuadroUsuario">
				<table cellpadding="0" cellspacing="0" border="0" width="95%">
					<tr>
						<td>
							<table cellpadding="0" cellspacing="0" border="0" width="100%">
								<tr><td colspan="5" height="20"></td></tr>
								<tr class="col">
									<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.configurar.ano" />&nbsp;&nbsp;</td>
									<td width="100%" valign="top">
										<html:select property="anyo" styleClass="textInput" style="width:100px" onchange="dameNumeracion()" >
											<html:optionsCollection name="anos" property="lista" value="codigo" label="descripcion"/>
										</html:select>
									</td>
								</tr>
								<tr class="col">
									<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.configurar.central" />&nbsp;&nbsp;</td>
									<td width="100%" valign="top">
										<html:text property="central" styleId="central" styleClass="textInput" style="width:100px" />
										<input type="hidden" id="centralDefault" value='<bean:write name="numeracionForm" property="central" />'/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr><td colspan="5" height="20"></td></tr>
							<tr>
								<td>
									<fieldset>
										<legend style="color:#cococo"><font color="#000000" ><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.configurar.por.oficina" /></font></legend>
										<table cellpadding="0" cellspacing="0" border="0" width="100%">
											<tr><td height="10" colspan="3"></td></tr>
											<tr>
												<td width="10" rowspan="40"></td>
												<td>
													<display:table name="contadores.lista" uid="fila"
														 requestURI="/configurarNumeracion.do" class="tablaListado" sort="page" style="width:100%">
													 	<display:column property="nombre" titleKey="ieci.tecdoc.sgm.rpadmin.botones.oficina.nuevo"
															 sortable="false" style="width: 80%;" />
														<display:column titleKey="ieci.tecdoc.sgm.rpadmin.libros.configurar.contador"
															 sortable="false" style="width: 20%;">
															 <html-el:text property="contadores[${fila_rowNum-1}].contador"  styleId="contadores[${fila_rowNum-1}].contador" styleClass="textInput" style="width:150px"
															 	/>
															 <html-el:hidden property="contadores[${fila_rowNum-1}].anyo" />
															 <html-el:hidden property="contadores[${fila_rowNum-1}].id" />
															 <input type="hidden" id="contadorDefault_<c:out value="${fila_rowNum-1}"/>" value='<bean:write name="fila" property="contador" />'/>
															 <input type="hidden" id="oficinaDefault_<c:out value="${fila_rowNum-1}"/>" value='<bean:write name="fila" property="nombre" />'/>

														</display:column>
													</display:table>
												</td>
												<td width="10" rowspan="40"></td>
											</tr>
											<tr><td height="10" colspan="3"></td></tr>
										</table>
									</fieldset>
								</td>
							</tr>
							</table>
						</td>
					</tr>
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
