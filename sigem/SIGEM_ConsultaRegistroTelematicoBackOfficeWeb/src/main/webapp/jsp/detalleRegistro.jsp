<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%
String rutaEstilos = (String)session.getAttribute("PARAMETRO_RUTA_ESTILOS");
if (rutaEstilos == null) rutaEstilos = "";
String rutaImagenes = (String)session.getAttribute("PARAMETRO_RUTA_IMAGENES");
if (rutaImagenes == null) rutaImagenes = "";
%>

<%@page import="ieci.tecdoc.sgm.consulta_telematico.utils.Defs"%>

<html:html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
		<title><bean:message key="titulo.aplicacion"/></title>

		<link href="css/<%=rutaEstilos%>estilos.css" rel="stylesheet" type="text/css" />

		<!--[if lte IE 5]>
			<link rel="stylesheet" type="text/css" href="css/<%=rutaEstilos%>estilos_ie5.css"/>
		<![endif]-->

		<!--[if IE 6]>
			<link rel="stylesheet" type="text/css" href="css/<%=rutaEstilos%>estilos_ie6.css"/>
		<![endif]-->

		<!--[if IE 7]>
			<link rel="stylesheet" type="text/css" href="css/<%=rutaEstilos%>estilos_ie7.css"/>
		<![endif]-->

		<script language="Javascript"">
			function mostrarListado() {
				parent.mostrarListado();
			}
		</script>
	</head>
	<body>

		<div class="contenedor_detalle">
			<div class="cuerpo">
      			<div class="cuerporight">
        			<div class="cuerpomid">
        			<div class="acciones"><a href="javascript:mostrarListado();" class="cerrar">&nbsp;</a></div>


				<div class="contenido_cuerpo clearfix" >

		          		<div class="seccion_det">

						<h1><bean:message key="detalle.informacionRegistro"/></h1>

		    				<p class="fila_sub">
							<label class="sml"><bean:message key="detalle.numeroRegistro"/>:</label>
							<label class="xlrg"><bean:write name="<%=Defs.NUMERO_REGISTRO%>"/></label>
						</p>
						<p class="fila_sub">
							<label class="sml"><bean:message key="detalle.fechaEfectiva"/>:</label>
							<label class="xlrg"><bean:write name="<%=Defs.FECHA_EFECTIVA%>"/></label>
						</p>
			    			<p class="fila_sub">
							<label class="sml"><bean:message key="detalle.fechaRegistro"/>:</label>
							<label class="xlrg"><bean:write name="<%=Defs.FECHA_REGISTRO%>"/></label>
						</p>
						<p class="fila_sub">
							<label class="sml"><bean:message key="detalle.destino"/>:</label>
							<label class="xlrg"><bean:write name="<%=Defs.ADDREESSE%>"/></label>
						</p>
						<p class="fila_sub">
							<label class="sml"><bean:message key="detalle.asuntoRegistro"/>:</label>
							<label class="xlrg"><bean:write name="<%=Defs.DESCRIPCION%>"/></label>
						</p>
			    		</div>
		          		<div class="seccion_det">
						<h1><bean:message key="detalle.datosSolicitud"/></h1>
		          			<logic:notEmpty name="<%=Defs.INFORMACION_REGISTRO%>">
			    				<%=request.getAttribute(Defs.INFORMACION_REGISTRO)%>
		    				</logic:notEmpty>
		    				<logic:empty name="<%=Defs.INFORMACION_REGISTRO%>">
		    					<bean:message key="mensaje.error.detalle"/>
		    					<logic:notEmpty name="<%=Defs.ERROR%>">
		    						<br/><br/>
		    						<label class="error_rojo"><%=request.getAttribute(Defs.ERROR)%></label>
		    					</logic:notEmpty>
		    				</logic:empty>
	        	   		</div>
		          		<div class="seccion_det">

   						<h1><bean:message key="detalle.documentos"/></h1>
							<display:table name="<%=Defs.DOCUMENTOS%>" class="table-display-tag detDocs" uid="documento"
								requestURI=''
								export="false"
								sort="list">
								<display:column  media="html" sortable="true" headerClass="cabeceraTabla" sortProperty="code">
									<table style="height: 100%">
										<tr style="height: 100%" valign="middle">
											<td height="100%">
												<a target="_blank" href="mostrarDocumento.do?<%=Defs.NUMERO_REGISTRO%>=<bean:write name="<%=Defs.NUMERO_REGISTRO%>"/>&<%=Defs.CODE%>=<bean:write name="documento" property="code"/>&<%=Defs.EXTENSION%>=<bean:write name="documento" property="extension"/>">
													<bean:write name="documento" property="name"/>
												</a>
											</td>
										</tr>
									</table>
								</display:column>
								<display:column  style="height: 100%" media="csv excel xml pdf" titleKey="tabla.nombre" sortable="true" headerClass="cabeceraTabla" sortProperty="code">
									<bean:write name="documento" property="name"/>
								</display:column>

								<display:setProperty name="basic.show.header">false</display:setProperty>

							</display:table>
		          		</div>

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


	</body>
</html:html>
