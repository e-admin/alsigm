<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="IECISA" />
	<title><bean:message key="ieci.tecdoc.sgm.pgadmin.title.aplication"/> - <bean:message key="ieci.tecdoc.sgm.rpadmin.botones.transportes" /></title>
	<link href="css/estilos.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/common.js"></script>
</head>
<body>
<html:form action="/nuevoTransporte.do">
	<div id="contenedora">
		<!-- Inicio Cabecera -->
		<jsp:include page="includes/cabecera.jsp" />

		<!-- Inicio Contenido -->
	<div id="contenido">
	<div class="cuerpo">
	<div class="cuerporight">
	<div class="cuerpomid"><jsp:include page="includes/pestanas.jsp">
		<jsp:param name="pestana" value="transportes" />
	</jsp:include>
	<div class="cuadro">
		<div id="migas">
			<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.nuevo"/>
		</div>
	<div class="col" align="right">
	<table>
		<tr>
			<td width="100%" align="left">
				<jsp:include page="includes/errores.jsp"/>
			</td>
			<td class="col_guardar"	onclick="llamadaAction('<html:rewrite page="/guardarNuevoTransporte.do"/>')">
				<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.guardar" />
			</td>
			<td class="col_volver" onclick="llamadaAction('<html:rewrite page="/listadoTransportes.do"/>')">
				<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.volver"/>
			</td>
		</tr>
	</table>
	</div>
	<table cellpadding="0" cellspacing="0" border="0">
		<tr>
			<td width="10" rowspan="80"></td>
			<td class="col" height="20" colspan="2"></td>
			<td width="100" rowspan="30"></td>
			<td class="col" height="20" colspan="2"></td>
		</tr>
		<tr class="col">
			<td class="txt"><bean:message
				key="ieci.tecdoc.sgm.rpadmin.transportes.tipo" />&nbsp;&nbsp;</td>
			<td><html:text property="transport" styleClass="textInput"
				maxlength="31" /></td>
		</tr>
		<logic:equal value="true" scope="session" name="enabledIntercambioRegistral">
			<tr class="col">
				<td class="txt">
					<bean:message key="ieci.tecdoc.sgm.rpadmin.transportes.codigo.sir" />
				</td>
				<td>
					<html:select property="codigoIntercambioRegistral">
						<html:option value=""></html:option>
						<html:optionsCollection property="listaTiposTransportesIR" label="name" value="value"/>
					</html:select>
				</td>
			</tr>
		</logic:equal>
		<tr>
			<td height="100%" colspan="4"></td>
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
</body>
</html>
