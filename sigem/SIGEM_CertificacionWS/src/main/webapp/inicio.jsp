<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="java.util.ArrayList"%>
<%@page import="ieci.tecdoc.sgm.core.services.idioma.LectorIdiomas"%>
<%@page import="ieci.tecdoc.sgm.core.services.idioma.Idioma"%>

<%
	ArrayList idiomas = LectorIdiomas.getIdiomas();
	session.setAttribute("IdiomasDisponibles", idiomas);
%>

<%@page import="java.util.Locale"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="IECISA" />
<title>sigem</title>
<link href="css/estilos.css" rel="stylesheet" type="text/css" />
</head>
<body>
<script language="javascript">
function enviar(){
	return;
}

function abrir1(){
	var idioma1 = document.getElementById('selIdioma1');
	var idIdioma1 = idioma1.options[idioma1.selectedIndex].value;
	window.open('descargar.jsp?idioma='+idIdioma1 , 'ventana1' , 'width=1000,height=700,scrollbars=NO')
}

function abrir2(){
	var idioma2 = document.getElementById('selIdioma2');
	var idIdioma2 = idioma2.options[idioma2.selectedIndex].value;
	window.open('descargar1.jsp?idioma='+idIdioma2 , 'ventana1' , 'width=1000,height=700,scrollbars=NO')
}

</script>
<div id="contenedora">
	<div id="cabecera">
   		<div id="logo">
   			<img src="img/minetur.jpg" alt="GOBIERNO DE ESPAÑA. MINISTERIO DE INDUSTRIA, ENERGÍA Y TURISMO " />
   			<img src="img/logo.gif" alt="sigem" />
   		</div>
		<div class="salir">
			
		</div>
 		</div>
 		<div class="usuario">
   		<div class="usuarioleft">
     		<p>Servicio Web de Certificaciones</p>
		</div>
   		<div class="usuarioright">
     			<div style="padding-top: 8px; padding-right: 24px;">
	     		<a href="#"><img src="img/help.gif" style="border: 0px" alt="ayuda" width="16" height="16" /></a>
	     	</div>
   		</div>
 	</div>
  	<!-- Fin Cabecera -->
	<div><br/><br/></div>
  	<!-- Inicio Contenido -->
  	<table align="center">
  	<tr>
  	<td>
  	<div id="contenedora">
		<div class="cuerpo" style="width:830px">
   			<div class="cuerporight">
     			<div class="cuerpomid">
     				<h1>Generador de certificaci&oacute;n</h1>
     				<br/>
					<div class="cuadro" style="height: 120px;">
						<table align="center" border=0>
							<tr>
								<td>
									<label class=popup>Se va a proceder a la generaci&oacute;n de una certificaci&oacute;n de pago de prueba con los siguientes datos:</label><br/>
								</td>
							</tr>
							<tr>
								<td>
									<label class=popup>ENTIDAD: Ayuntammiento de Alpedrete (COD: 00001)</label><br/>
								</td>
							</tr>
							<tr>
								<td>
									<label class=popup>USUARIO: Juan Romero L�pez</label><br/>
								</td>
							</tr>
							<tr>
								<td>
									<label class=popup>PAGOS: 4 pagos de 101.90, 221.30, 17.85 y 21.15 euros</label><br/>
								</td>
							</tr>
							<%
								Locale locale = (Locale)request.getSession().getAttribute("org.apache.struts.action.LOCALE");
								String strIdioma = "";
								if (locale != null)
									strIdioma = locale.getLanguage() + "_" + locale.getCountry();
							%>
							<tr>
								<td><label class=popup>Idioma&nbsp;</label>
									<select id="selIdioma1" class=gr>
									<%
									for(int indIdioma = 	0; indIdioma<idiomas.size(); indIdioma++){
										Idioma objIdioma = (Idioma)idiomas.get(indIdioma);
									%>
										<option value="<%=objIdioma.getCodigo()%>" <%=(objIdioma.getCodigo().equals(strIdioma) ? "selected" : "")%>><%=objIdioma.getDescripcion()%></option>
									<%}%>
									</select>
								</td>
							</tr>
							<tr><td>&nbsp;</td></tr>
				           	<tr>
					           	<td align="center">
				            		<input type="button" name="entrar" id="entrar" class="ok" value="Certificaci&oacute;n" onclick="javascript:abrir1();"/>
				            	</td>
				           	</tr>
						</table>							                    		
           			</div>
           			<br/>
					<div class="cuadro" style="height: 120px;">
						<table align="center" border=0>
							<tr>
								<td>
									<label class=popup>Se va a proceder a la generaci&oacute;n de una certificaci&oacute;n de pago de prueba con los siguientes datos:</label><br/>
								</td>
							</tr>
							<tr>
								<td>
									<label class=popup>ENTIDAD: Ayuntammiento del Pardillo (COD: 00002)</label><br/>
								</td>
							</tr>
							<tr>
								<td>
									<label class=popup>USUARIO: Juan Romero L�pez</label><br/>
								</td>
							</tr>
							<tr>
								<td>
									<label class=popup>PAGOS: 4 pagos de 101.90, 221.30, 17.85 y 21.15 euros</label><br/>
								</td>
							</tr>
							<tr>
								<td><label class=popup>Idioma&nbsp;</label>
									<select id="selIdioma2" class=gr>
									<%
									for(int indIdioma = 	0; indIdioma<idiomas.size(); indIdioma++){
										Idioma objIdioma = (Idioma)idiomas.get(indIdioma);
									%>
										<option value="<%=objIdioma.getCodigo()%>" <%=(objIdioma.getCodigo().equals(strIdioma) ? "selected" : "")%>><%=objIdioma.getDescripcion()%></option>
									<%}%>
									</select>
								</td>
							</tr>
							<tr><td>&nbsp;</td></tr>
				           	<tr>
					           	<td align="center">
				            		<input type="button" name="entrar" id="entrar" class="ok" value="Certificaci&oacute;n" onclick="javascript:abrir2();"/>
				            	</td>
				           	</tr>
						</table>							                    		
           			</div>
           		</div>
      		</div>
    	</div>
		<div class="cuerpobt" style="width:830px">
      		<div class="cuerporightbt">
        		<div class="cuerpomidbt"></div>
      		</div>
		</div>
		
  	</div>
  	</td>
  	</tr>
  	</table>
</div>
</body>
</html>