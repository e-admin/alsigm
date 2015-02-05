<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@page import="ieci.tecdoc.sgm.core.services.telematico.Registro"%>
<%@page import="ieci.tecdoc.sgm.consulta_telematico.utils.Defs"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
String rutaEstilos = (String)session.getAttribute("PARAMETRO_RUTA_ESTILOS");
if (rutaEstilos == null) rutaEstilos = "";
String rutaImagenes = (String)session.getAttribute("PARAMETRO_RUTA_IMAGENES");
if (rutaImagenes == null) rutaImagenes = "";
%>




<html:html locale="true">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
		<title><bean:message key="titulo.aplicacion"/></title>

		<link href="css/<%=rutaEstilos%>estilos.css" rel="stylesheet" type="text/css" />

		<!--[if lte IE 5]>
			<link rel="stylesheet" type="text/css" href="css/estilos_ie5.css"/>
		<![endif]-->

		<!--[if IE 6]>
			<link rel="stylesheet" type="text/css" href="css/estilos_ie6.css"/>
		<![endif]-->

		<!--[if IE 7]>
			<link rel="stylesheet" type="text/css" href="css/estilos_ie7.css"/>
		<![endif]-->

		<script type="text/javascript" language="javascript" src="js/idioma.js"></script>
		<script type="text/javascript" language="javascript" src="js/utilsTabla.js"></script>

		<script language="Javascript">

			function mostrarRegistro(numeroRegistro, asunto, fecha, fechaEfectiva,destino, descripcion) {
				var iframe = document.getElementById("detalleRegistro");
				iframe.style.height=size() - 180 + "px";
				iframe.style.width="880px";
				iframe.style.left="50%";
				iframe.style.top="130px";
				iframe.style.marginLeft="-440px";
				iframe.style.visibility="visible";

				iframe.src="detalleRegistro.do?<%=Defs.NUMERO_REGISTRO%>=" + numeroRegistro + "&<%=Defs.DESCRIPCION%>=" + descripcion + "&<%=Defs.FECHA_REGISTRO%>=" + fecha + "&<%=Defs.FECHA_EFECTIVA%>=" + fechaEfectiva+ "&<%=Defs.ADDREESSE%>=" + destino+ "&<%=Defs.ASUNTO%>=" + asunto;
				//window.scroll();
			}

			function mostrarListado() {
				var iframe = document.getElementById("detalleRegistro");
				iframe.style.height="0px";
				iframe.style.width="0px";
				iframe.style.left="0px";
				iframe.style.top="0px";
				iframe.style.marginLeft="0px";
				iframe.style.marginTop="0px";
				iframe.style.visibility="hidden";
				iframe.src="jsp/blank.html";
			}

			function size() {
  				var tamanyo = 0;
				if (typeof window.innerWidth != 'undefined') {
					tamanyo = window.innerHeight;
				} else if (typeof document.documentElement != 'undefined' && typeof document.documentElement.clientWidth != 'undefined' && document.documentElement.clientWidth != 0) {
					tamanyo = document.documentElement.clientHeight;
				} else {
					tamanyo = document.getElementsByTagName('body')[0].clientHeight;
				}
				return tamanyo;
			}

		</script>
	</head>

	<body>


	<div id="contenedora">
		<jsp:include flush="true" page="cabecera.jsp"></jsp:include>

		<div class="centered">
		<div class="contenedor_centrado">


			<div class="cuerpo">
      			<div class="cuerporight">
        			<div class="cuerpomid">

				<h1><bean:message key="listado.titulo"/></h1>

				<div class="tabs_submenus">
					<ul>
						<li class="subOn">
							<h3><a><bean:message key="menu.listaRegistros"/></a></h3>
						</li>
						<li class="subOff">
							<h3><a href="busquedaRegistros.do"><bean:message key="menu.busquedaRegistros"/></a></h3>
						</li>
					</ul>
				</div>

				<div class="cuerpo_subs clearfix" >

							<% int i = 0; %>
							<display:table name="<%=Defs.REGISTROS%>" class="table-display-tag" uid="registro"
								pagesize="10"
								requestURI="listadoRegistros.do"
								export="true"
								sort="list"  >

								<%
  									String mostrarRegistro = "";
  									Registro registry = (Registro) registro;
  									if (registry != null) {

  										mostrarRegistro = "javascript:mostrarRegistro('"
  												  + registry.getRegistryNumber() + "', '"
  												  + registry.getTopic().replaceAll("'", "\\\\'") + "', '"
  												  + new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss").format(registry.getRegistryDate()) + "', '"
  												  + new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss").format(registry.getEffectiveDate()) + "', '"
  													+ registry.getAddressee() + "', '"
  												  + registry.getName().replaceAll("'", "\\\\'") + "');";
  									}
  								%>

								<display:column media="html" titleKey="tabla.cabeceraDescargarJustificante" sortable="true" headerClass="cabeceraTabla anchoImg">
									<acronym title='<bean:message key="tabla.descargarJustificante"/>'>
										<table id="tabla0<%=i%>" width="100%" height="100%"
											onmouseover="javascript:changeColor(<%=i%>, 'true');"
											onmouseout="javascript:changeColor(<%=i%>, 'false');">
											<tr style="height: 100%" valign="middle">
												<td width="100%" height="100%">
													<a target="_blank" href="mostrarJustificante.do?<%=Defs.JUSTIFICANTE%>=<bean:write name="registro" property="registryNumber"/>"><img src="img/pdf.gif" border="0px" alt="Pdf"/></a>
												</td>
											</tr>
										</table>
									</acronym>
								</display:column>
								<display:column  media="html" titleKey="tabla.numeroRegistro" sortable="true" headerClass="cabeceraTabla anchoNReg" sortProperty="registryNumber" >
									<acronym title='<bean:message key="tabla.detalleRegistro"/>'>
										<table id="tabla1<%=i%>" width="100%" height="100%" style="cursor: pointer"
											onclick="<%=mostrarRegistro%>"
											onmouseover="javascript:changeColor(<%=i%>, 'true');"
											onmouseout="javascript:changeColor(<%=i%>, 'false');">
											<tr style="height: 100%">
												<td width="100%" height="100%">
													<bean:write name="registro" property="registryNumber"/>
												</td>
											</tr>
										</table>
									</acronym>
								</display:column>

								<display:column  media="html" titleKey="tabla.organoDestinatario" sortable="true" headerClass="cabeceraTabla anchoNReg" sortProperty="addressee">
									<acronym title='<bean:message key="tabla.detalleRegistro"/>'>
										<table id="tabla2<%=i%>" width="100%" height="100%" style="cursor: pointer"
											onclick="<%=mostrarRegistro%>"
											onmouseover="javascript:changeColor(<%=i%>, 'true');"
											onmouseout="javascript:changeColor(<%=i%>, 'false');">
											<tr style="height: 100%">
												<td width="100%" height="100%">
													<bean:write name="registro" property="addressee"/>
												</td>
											</tr>
										</table>
									</acronym>
								</display:column>

								<display:column  media="csv excel xml pdf" titleKey="tabla.numeroRegistroExport" sortable="true" headerClass="cabeceraTabla anchoNReg" sortProperty="registryNumber"  property="registryNumber" decorator="ieci.tecdoc.sgm.consulta_telematico.web.decorators.NumRegistroExcelDecorator">

								</display:column>
								<display:column  media="csv excel xml pdf" titleKey="tabla.organoDestinatario" sortable="true" headerClass="cabeceraTabla anchoNReg" sortProperty="addressee">
									<bean:write name="registro" property="addressee"/>
								</display:column>
								<display:column  media="html" titleKey="tabla.asuntoRegistro" sortable="true" headerClass="cabeceraTabla anchoSbjt" sortProperty="name">
									<acronym title='<bean:message key="tabla.detalleRegistro"/>'>
										<table id="tabla3<%=i%>" width="100%" height="100%" style="cursor: pointer"
											onclick="<%=mostrarRegistro%>"
											onmouseover="javascript:changeColor(<%=i%>, 'true');"
											onmouseout="javascript:changeColor(<%=i%>, 'false');">
											<tr style="height: 100%">
												<td width="100%" height="100%">
													<bean:write name="registro" property="name" />
												</td>
											</tr>
										</table>
									</acronym>
								</display:column>
								<display:column  media="csv excel xml pdf" titleKey="tabla.asuntoRegistro" sortable="true" headerClass="cabeceraTabla anchoSbjt" sortProperty="name">
									<bean:write name="registro" property="name" />
								</display:column>
								<display:column  media="html" titleKey="tabla.fechaRegistro" sortable="true" headerClass="cabeceraTabla anchoFch" sortProperty="registryDate" >
									<acronym title='<bean:message key="tabla.detalleRegistro"/>'>
										<table id="tabla4<%=i%>" width="100%" height="100%" style="cursor: pointer"
											onclick="<%=mostrarRegistro%>"
											onmouseover="javascript:changeColor(<%=i%>, 'true');"
											onmouseout="javascript:changeColor(<%=i%>, 'false');">
											<tr style="height: 100%">
												<td width="100%" height="100%">
													<bean:write name="registro" property="registryDate" format="dd/MM/yyyy, HH:mm:ss" />
												</td>
											</tr>
										</table>
									</acronym>
								</display:column>
								<display:column  media="csv excel xml pdf" titleKey="tabla.fechaRegistroExport" sortable="true" headerClass="cabeceraTabla anchoFch" sortProperty="registryDate" >
									<bean:write name="registro" property="registryDate" format="dd/MM/yyyy, HH:mm:ss" />
								</display:column>
								<display:column  media="html" titleKey="tabla.fechaEfectiva" sortable="true" headerClass="cabeceraTabla anchoFch" sortProperty="effectiveDate" >
									<acronym title='<bean:message key="tabla.detalleRegistro"/>'>
										<table id="tabla5<%=i%>" width="100%" height="100%" style="cursor: pointer"
											onclick="<%=mostrarRegistro%>"
											onmouseover="javascript:changeColor(<%=i%>, 'true');"
											onmouseout="javascript:changeColor(<%=i%>, 'false');">
											<tr style="height: 100%">
												<td width="100%" height="100%">
													<bean:write name="registro" property="effectiveDate" format="dd/MM/yyyy, HH:mm:ss" />
												</td>
											</tr>
										</table>
									</acronym>
								</display:column>
								<display:column  media="csv excel xml pdf" titleKey="tabla.fechaEfectiva" sortable="true" headerClass="cabeceraTabla anchoFch" sortProperty="effectiveDate">
									<bean:write name="registro" property="effectiveDate" format="dd/MM/yyyy, HH:mm:ss" />
								</display:column>
								<% i++; %>
							</display:table>

				</div>
			</div>
      			</div>
    		</div>
    		<div class="cuerpobt">
      			<div class="cuerporightbt">
        			<div class="cuerpomidbt">&nbsp;</div>
      			</div>
		</div>


	    	</div>
    		</div>
    	</div>

	<iframe id="detalleRegistro" name="detalleRegistro" src="blank.html" class="frame_barra" style="visibility: hidden; position: absolute; height: 0px; width: 0px;" frameborder='0' title='<bean:message key="detalle.informacionRegistro"/>'></iframe>

	</body>

</html:html>
