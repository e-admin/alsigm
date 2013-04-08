<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="ISO-8859-1" method="html"/>

	<xsl:variable name="lang.titulo" select="'Formulario de Solicitud de Tarjeta de Estacionamiento para Minusválidos.'"/>
	<xsl:variable name="lang.datosSolicitante" select="'Datos del Solicitante'"/>
	<xsl:variable name="lang.datosRepresentante" select="'Conductor Autorizado'"/>
	<xsl:variable name="lang.docIdentidad" select="'Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'Nombre'"/>
	<xsl:variable name="lang.domicilio" select="'Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.email" select="'Correo electrónico'"/>
	<xsl:variable name="lang.localidad" select="'Localidad'"/>
	<xsl:variable name="lang.provincia" select="'Provincia'"/>
	<xsl:variable name="lang.cp" select="'Código postal'"/>
	<xsl:variable name="lang.telefono" select="'Teléfono'"/>
	<xsl:variable name="lang.anexar" select="'Anexar ficheros'"/>
	<xsl:variable name="lang.anexarInfo1" select="'1.- Para adjuntar un fichero (*.pdf), pulse el botón examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'2.- Seleccione el fichero que desea anexar a la solicitud.'"/>
	<xsl:variable name="lang.envio" select="'Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'D.E.U.'"/>

	<xsl:variable name="lang.documentoPDF" select="'Documento PDF'"/>
	<xsl:variable name="lang.documentoJPG" select="'Documento Imagen'"/>
	<xsl:variable name="lang.documento1" select="'Una solicitud debidamente cumplimentada, en impreso normalizado que será facilitado en el departamento correspondiente (servicios sociales, circulación, vehículos y transportes, etc.) del ayuntamiento.'"/>
	<xsl:variable name="lang.documento2" select="'DNI del solicitante'"/>
	<xsl:variable name="lang.documento3" select="'Certificado de minusvalía expedido por el equipo de valoración de la Xunta de Galicia, o por los equipos autorizados en otras CC.AA.'"/>
	<xsl:variable name="lang.documento4" select="'Certificado de empadronamiento.'"/>
	<xsl:variable name="lang.documento5" select="'Fotografía tamaño carnet.'"/>
	<xsl:variable name="lang.documento6" select="'Permiso de conducir bien del minusválido, en el caso de que sea éste el conductor del vehículo o bien de la persona que lo transporte habitualmente.'"/>
	<xsl:variable name="lang.documento7" select="'DNI de la persona que lo represente(según caso).'"/>
	<xsl:variable name="lang.documento11" select="'Declaración jurada del conductor habitual del vehículo alegando dicha condición, en el supuesto de que no sea el propio minusválido. '"/>
	<xsl:variable name="lang.documento8" select="'Permiso de circulación del vehículo.'"/>
	<xsl:variable name="lang.documento9" select="'Justificante de pago del último recibo del impuesto municipal sobre vehículos de tracción mecánica.'"/>
	<xsl:variable name="lang.documento10" select="'En los casos de invalidez temporal: informe médico que acredite su problema de movilidad, su evolución y pronóstico, así como la necesidad de utilizar silla de ruedas, muletas, bastones o cualquier otra ayuda técnica para minusválidos.'"/>
	<xsl:variable name="lang.tipo" select="'Tipo de Tarjeta solicitada'"/>
	<xsl:variable name="lang.tipo1" select="'Tarjeta de Estacionamiento'"/>
	<xsl:variable name="lang.tipo2" select="'Tarjeta de Accesibilidad'"/>
	<xsl:variable name="lang.tipo1_" select="'Tarjeta de Estacionamiento'"/>
	<xsl:variable name="lang.tipo2_" select="'Tarjeta de Accesibilidad'"/>
	<xsl:variable name="lang.solicita" select="'Solicita'"/>
	<xsl:variable name="lang.nota" select="'*NOTA: A rellenar en el caso de ser conductor autorizado (distinto del solicitante)'"/>
	<xsl:variable name="lang.required" select="' Campos obligatorios'"/>
	<xsl:variable name="lang.datosSolicitud" select="'Datos de la Solicitud'"/>

	<xsl:template match="/">
		<script language="Javascript">
			//array de campos obligatorios -> ('id_campo','nombre_campo')
			var validar = new Array(16);
			validar[0] = new Array('documentoIdentidad', '<xsl:value-of select="$lang.docIdentidad"/>');
			validar[1] = new Array('nombreSolicitante','<xsl:value-of select="$lang.nombre"/>');
			validar[2] = new Array('domicilioNotificacion','<xsl:value-of select="$lang.domicilio"/>');
			validar[3] = new Array('localidad','<xsl:value-of select="$lang.localidad"/>');
			validar[4] = new Array('provincia','<xsl:value-of select="$lang.provincia"/>');
			validar[5] = new Array('codigoPostal','<xsl:value-of select="$lang.cp"/>');
			validar[6] = new Array('telefono','<xsl:value-of select="$lang.telefono"/>');
			validar[7] = new Array('tipoTarjeta','<xsl:value-of select="$lang.tipo"/>');
			validar[8] = new Array('TRAM14D1','<xsl:value-of select="$lang.documentoPDF"/>');
			validar[9] = new Array('TRAM14D2','<xsl:value-of select="$lang.documentoPDF"/>');
			validar[10] = new Array('TRAM14D3','<xsl:value-of select="$lang.documentoPDF"/>');
			validar[11] = new Array('TRAM14D4','<xsl:value-of select="$lang.documentoPDF"/>');
			validar[12] = new Array('TRAM14D5','<xsl:value-of select="$lang.documentoJPG"/>');
			validar[13] = new Array('TRAM14D6','<xsl:value-of select="$lang.documentoPDF"/>');
			validar[14] = new Array('TRAM14D8','<xsl:value-of select="$lang.documentoPDF"/>');
			validar[15] = new Array('TRAM14D9','<xsl:value-of select="$lang.documentoPDF"/>');
			
			//Array con los datos especificos del formulario -> -> ('id_campo','tag_xml')
			var especificos = new Array(16);
			especificos[0] = new Array('domicilioNotificacion','Domicilio_Notificacion');
			especificos[1] = new Array('localidad','Localidad');
			especificos[2] = new Array('provincia','Provincia');
			especificos[3] = new Array('codigoPostal','Codigo_Postal');
			especificos[4] = new Array('telefono','Telefono');
			especificos[5] = new Array('documentoIdentidadRepresentante','Documento_Identidad_Representante');
			especificos[6] = new Array('nombreRepresentante','Nombre_Representante');
			especificos[7] = new Array('telefonoRepresentante','Telefono_Representante');
			especificos[8] = new Array('domicilioNotificacionRepresentante','Domicilio_Notificacion_Representante');
			especificos[9] = new Array('localidadRepresentante','Localidad_Representante');
			especificos[10] = new Array('provinciaRepresentante','Provincia_Representante');
			especificos[11] = new Array('codigoPostalRepresentante','Codigo_Postal_Representante');
			especificos[12] = new Array('emailRepresentante','Email_Representante');
			especificos[13] = new Array('tipoTarjeta','Tipo_Tarjeta');
			especificos[14] = new Array('solicitarEnvio','Solicitar_Envio');
			especificos[15] = new Array('direccionElectronicaUnica','Direccion_Electronica_Unica');
			especificos[16] = new Array('emailSolicitante','Email_Solicitante');
			
			var validarNumero = new Array(1);
			validarNumero[0] = new Array('codigoPostal','<xsl:value-of select="$lang.cp"/>',5);
			
			function verificacionesEspecificas() {
				var valor = document.forms[0].tipoTarjeta.value;
				var valorRep = document.forms[0].nombreRepresentante.value;
				if(!validarCamposSolicitante(valor,valorRep)) {
					return false;
				}
				
				if(!validarCampoNumerico(document.getElementById("telefono"), '<xsl:value-of select="$lang.telefono"/>')) {
					return false;
				}
				
				var email = document.getElementById("emailSolicitante");
				if (validarEmail(email) == false){
					alert("El campo '<xsl:value-of select="$lang.email"/>' no es válido");
					email.focus();
					return false;
				}
			
				if(valor == '<xsl:value-of select="$lang.tipo1_"/>') {
				
					var tlf = document.getElementById("telefonoRepresentante");
					if( tlf.value != "") {
						if(!validarCampoNumerico(tlf, '<xsl:value-of select="$lang.telefono"/>')) {
							return false;
						}
					}
					
					var email = document.getElementById("emailRepresentante");
					if (validarEmail(email) == false){
						alert("El campo '<xsl:value-of select="$lang.email"/>' no es válido");
						email.focus();
						return false;
					}
					
					var nif = document.getElementById("documentoIdentidadRepresentante");
					
				    if( !validarNIF(nif) ) {
						alert("El campo '<xsl:value-of select="$lang.docIdentidad"/>' no es válido");
						nif.focus();
						return false;
					}
					
					var cpRes = document.getElementById("codigoPostalRepresentante");
					if( cpRes.value != "") {
						if(!validarCampoNumerico(cpRes, '<xsl:value-of select="$lang.cp"/>')) {
							return false;
						}
					}
					
					if( cpRes.value != "") {
						if(cpRes.value.length != 5) {
							alert("La longitud del campo '<xsl:value-of select="$lang.cp"/>' no es correcta");
							cpRes.focus();
							return false;
						}
					}
					
					var tipoTrj = document.getElementById("tipoTarjeta");
					if( tipoTrj.value == '<xsl:value-of select="$lang.tipo2_"/>') {
						document.forms[0].documentoIdentidadRepresentante.value = "";
						document.forms[0].nombreRepresentante.value = "";
						document.forms[0].domicilioNotificacionRepresentante.value = "";
						document.forms[0].localidadRepresentante.value = "";
						document.forms[0].provinciaRepresentante.value = "";
						document.forms[0].codigoPostalRepresentante.value = "";
						document.forms[0].telefonoRepresentante.value = "";
						document.forms[0].emailRepresentante.value = "";
					} else {
						//document.forms[0].TRAM14D9.value = "";
					}
				}
				
				return true;
			}
			
			function mostrarSolicitante(valor) {
				if(valor == '<xsl:value-of select="$lang.tipo1_"/>') {
					document.getElementById('datosSolic').style.display = "block";
					document.getElementById('documentosRep').style.display = "block";
				} else {
					document.getElementById('datosSolic').style.display = "none";
					document.getElementById('documentosRep').style.display = "none";
				}
			}
			
			function validarCamposSolicitante(valor,valorRep) {
				if(valor == '<xsl:value-of select="$lang.tipo1_"/>'){
					if( valorRep != "") {
						var formu = document.forms[0];
						if( formu.TRAM14D7.value == "") {
							alert("El campo '<xsl:value-of select="$lang.documentoPDF"/>' es obligatorio");
							var campo = document.getElementById('TRAM14D7');
							campo.focus();
							return false;
						}
						if( formu.TRAM14D11.value == "") {
							alert("El campo '<xsl:value-of select="$lang.documentoPDF"/>' es obligatorio");
							var campo = document.getElementById('TRAM14D11');
							campo.focus();
							return false;
						}
					}
				}
				return true;
			}

			
		</script>
		<h1><xsl:value-of select="$lang.titulo"/></h1>
   		<br/>
   		<div class="submenu">
   			<h1><xsl:value-of select="$lang.datosSolicitante"/></h1>
   		</div>
   		<div class="cuadro" style="">	
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.docIdentidad"/>:<font color="#950000">*</font>
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px;</xsl:attribute>
					<xsl:attribute name="name">documentoIdentidad</xsl:attribute>
					<xsl:attribute name="id">documentoIdentidad</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/Remitente/Documento_Identificacion/Numero"/></xsl:attribute>
					<xsl:attribute name="disabled"></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.nombre"/>:<font color="#950000">*</font>
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">nombreSolicitante</xsl:attribute>
					<xsl:attribute name="id">nombreSolicitante</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/Remitente/Nombre"/></xsl:attribute>
					<xsl:attribute name="disabled"></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.domicilio"/>:<font color="#950000">*</font>	
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">domicilioNotificacion</xsl:attribute>
					<xsl:attribute name="id">domicilioNotificacion</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Domicilio_Notificacion"/></xsl:attribute>
					<xsl:attribute name="maxlength">128</xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.localidad"/>:<font color="#950000">*</font>	
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">localidad</xsl:attribute>
					<xsl:attribute name="id">localidad</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Localidad"/></xsl:attribute>
					<xsl:attribute name="maxlength">256</xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.provincia"/>:<font color="#950000">*</font>
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">provincia</xsl:attribute>
					<xsl:attribute name="id">provincia</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Provincia"/></xsl:attribute>
					<xsl:attribute name="maxlength">64</xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.cp"/>:<font color="#950000">*</font>
				</label>
				<input type="text" onkeypress="return permitirSoloNumericos(event)">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">codigoPostal</xsl:attribute>
					<xsl:attribute name="id">codigoPostal</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Codigo_Postal"/></xsl:attribute>
					<xsl:attribute name="maxlength">5</xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.telefono"/>:<font color="#950000">*</font>	
				</label>
				<input type="text" onkeypress="return permitirSoloNumericos(event)">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">telefono</xsl:attribute>
					<xsl:attribute name="id">telefono</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Telefono"/></xsl:attribute>
					<xsl:attribute name="maxlength">32</xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.email"/>:
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">emailSolicitante</xsl:attribute>
					<xsl:attribute name="id">emailSolicitante</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Email_Solicitante"/></xsl:attribute>
					<xsl:attribute name="maxlength">256</xsl:attribute>
				</input>
			</div>
		</div>
		<br/>
		<!--  -->
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.datosSolicitud"/></h1>
   		</div>
   		<div class="cuadro" style="">
   			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.tipo"/>:<font color="#950000">*</font>
				</label>
				<xsl:variable name="clas" select="Datos_Registro/datos_especificos/Tipo_Tarjeta"/>
				<select class="gr" onchange="mostrarSolicitante(this.value)">
					<xsl:attribute name="style">position: relative; width:400px;height:20px </xsl:attribute>
					<xsl:attribute name="name">tipoTarjeta</xsl:attribute>
					<xsl:attribute name="id">tipoTarjeta</xsl:attribute>
					<option></option>
					<xsl:choose>
				       <xsl:when test="$clas=$lang.tipo1_">
				           <option value="Tarjeta de Estacionamiento" selected="selected"><xsl:value-of select="$lang.tipo1"/></option>
				       </xsl:when>
				       <xsl:otherwise>
				           <option value="Tarjeta de Estacionamiento"><xsl:value-of select="$lang.tipo1"/></option> 
				       </xsl:otherwise>
				    </xsl:choose>
				    <xsl:choose>
				       <xsl:when test="$clas=$lang.tipo2_">
				           <option value="Tarjeta de Accesibilidad" selected="selected"><xsl:value-of select="$lang.tipo2"/></option>
				       </xsl:when>
				       <xsl:otherwise>
				           <option value="Tarjeta de Accesibilidad"><xsl:value-of select="$lang.tipo2"/></option> 
				       </xsl:otherwise>
				    </xsl:choose>
				</select>
			</div>
   		</div>
		
		<div style="display:none" id="datosSolic" name="datosSolic">
			<br/>
			<div class="submenu">
	   			<h1><xsl:value-of select="$lang.datosRepresentante"/></h1>
	   		</div>
	   		<div class="cuadro" style="">
	   			<div class="gr">
	   				<label>
	   					<xsl:attribute name="style">position: relative; width:200px;color:#006699;</xsl:attribute>
						<b><xsl:value-of select="$lang.nota"/></b>
					</label>
				</div>
				<br/>	
				<div class="col">
					<label class="gr">
						<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
						<xsl:value-of select="$lang.docIdentidad"/>:
					</label>
					<input type="text">
						<xsl:attribute name="style">position: relative; width:400px;</xsl:attribute>
						<xsl:attribute name="name">documentoIdentidadRepresentante</xsl:attribute>
						<xsl:attribute name="id">documentoIdentidadRepresentante</xsl:attribute>
						<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Documento_Identidad_Representante"/></xsl:attribute>
						<xsl:attribute name="maxlength">12</xsl:attribute>
					</input>
				</div>
				<div class="col">
					<label class="gr">
						<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
						<xsl:value-of select="$lang.nombre"/>:
					</label>
					<input type="text">
						<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
						<xsl:attribute name="name">nombreRepresentante</xsl:attribute>
						<xsl:attribute name="id">nombreRepresentante</xsl:attribute>
						<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Nombre_Representante"/></xsl:attribute>
						<xsl:attribute name="maxlength">250</xsl:attribute>
					</input>
				</div>
				<div class="col">
					<label class="gr">
						<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
						<xsl:value-of select="$lang.domicilio"/>:
					</label>
					<input type="text">
						<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
						<xsl:attribute name="name">domicilioNotificacionRepresentante</xsl:attribute>
						<xsl:attribute name="id">domicilioNotificacionRepresentante</xsl:attribute>
						<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Domicilio_Notificacion_Representante"/></xsl:attribute>
						<xsl:attribute name="maxlength">128</xsl:attribute>
					</input>
				</div>
				<div class="col">
					<label class="gr">
						<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
						<xsl:value-of select="$lang.localidad"/>:	
					</label>
					<input type="text">
						<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
						<xsl:attribute name="name">localidadRepresentante</xsl:attribute>
						<xsl:attribute name="id">localidadRepresentante</xsl:attribute>
						<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Localidad_Representante"/></xsl:attribute>
						<xsl:attribute name="maxlength">256</xsl:attribute>
					</input>
				</div>
				<div class="col">
					<label class="gr">
						<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
						<xsl:value-of select="$lang.provincia"/>:
					</label>
					<input type="text">
						<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
						<xsl:attribute name="name">provinciaRepresentante</xsl:attribute>
						<xsl:attribute name="id">provinciaRepresentante</xsl:attribute>
						<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Provincia_Representante"/></xsl:attribute>
						<xsl:attribute name="maxlength">64</xsl:attribute>
					</input>
				</div>
				<div class="col">
					<label class="gr">
						<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
						<xsl:value-of select="$lang.cp"/>:
					</label>
					<input type="text" onkeypress="return permitirSoloNumericos(event)">
						<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
						<xsl:attribute name="name">codigoPostalRepresentante</xsl:attribute>
						<xsl:attribute name="id">codigoPostalRepresentante</xsl:attribute>
						<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Codigo_Postal_Representante"/></xsl:attribute>
						<xsl:attribute name="maxlength">5</xsl:attribute>
					</input>
				</div>
				<div class="col">
					<label class="gr">
						<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
						<xsl:value-of select="$lang.telefono"/>:	
					</label>
					<input type="text" onkeypress="return permitirSoloNumericos(event)">
						<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
						<xsl:attribute name="name">telefonoRepresentante</xsl:attribute>
						<xsl:attribute name="id">telefonoRepresentante</xsl:attribute>
						<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Telefono_Representante"/></xsl:attribute>
						<xsl:attribute name="maxlength">32</xsl:attribute>
					</input>
				</div>
				<div class="col">
					<label class="gr">
						<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
						<xsl:value-of select="$lang.email"/>:
					</label>
					<input type="text">
						<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
						<xsl:attribute name="name">emailRepresentante</xsl:attribute>
						<xsl:attribute name="id">emailRepresentante</xsl:attribute>
						<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Email_Representante"/></xsl:attribute>
						<xsl:attribute name="maxlength">256</xsl:attribute>
					</input>
				</div>
			</div>
		</div>
		<br/>
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.anexar"/></h1>
   		</div>
   		<div class="cuadro" style="">
   			<div>
				<label class="gr">
			   		<xsl:attribute name="style">position: relative; width:650px;</xsl:attribute>
		   			<xsl:value-of select="$lang.anexarInfo1"/><br/>
		   			<xsl:value-of select="$lang.anexarInfo2"/><br/>
				</label>
			</div>
			<br/><br/><br/>
			<div>
				<label class="gr">
			   		<xsl:attribute name="style">position: relative; width:650px;</xsl:attribute>
		   			<ul><li><xsl:value-of select="$lang.documento1"/></li></ul>
				</label>
			</div>
   			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:<font color="#950000">*</font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">TRAM14D1</xsl:attribute>
					<xsl:attribute name="id">TRAM14D1</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM14D1_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM14D1_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
				</input>
			</div>
			<br/>
			<div>
				<label class="gr">
			   		<xsl:attribute name="style">position: relative; width:650px;</xsl:attribute>
		   			<ul><li><xsl:value-of select="$lang.documento2"/></li></ul>
				</label>
			</div>
   			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:<font color="#950000">*</font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">TRAM14D2</xsl:attribute>
					<xsl:attribute name="id">TRAM14D2</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM14D2_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM14D2_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
				</input>
			</div>
			<br/>
			<div>
				<label class="gr">
			   		<xsl:attribute name="style">position: relative; width:650px;</xsl:attribute>
		   			<ul><li><xsl:value-of select="$lang.documento3"/></li></ul>
				</label>
			</div>
   			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:<font color="#950000">*</font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">TRAM14D3</xsl:attribute>
					<xsl:attribute name="id">TRAM14D3</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM14D3_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM14D3_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
				</input>
			</div>
			<br/>
			<div>
				<label class="gr">
			   		<xsl:attribute name="style">position: relative; width:650px;</xsl:attribute>
		   			<ul><li><xsl:value-of select="$lang.documento4"/></li></ul>
				</label>
			</div>
   			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:<font color="#950000">*</font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">TRAM14D4</xsl:attribute>
					<xsl:attribute name="id">TRAM14D4</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM14D4_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM14D4_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
				</input>
			</div>
			<br/>
			<div>
				<label class="gr">
			   		<xsl:attribute name="style">position: relative; width:650px;</xsl:attribute>
		   			<ul><li><xsl:value-of select="$lang.documento5"/></li></ul>
				</label>
			</div>
   			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoJPG"/>:<font color="#950000">*</font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">TRAM14D5</xsl:attribute>
					<xsl:attribute name="id">TRAM14D5</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM14D5_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM14D5_Tipo</xsl:attribute>
					<xsl:attribute name="value">jpg,jpeg</xsl:attribute>
				</input>
			</div>
			<br/>
			<div>
				<label class="gr">
			   		<xsl:attribute name="style">position: relative; width:650px;</xsl:attribute>
		   			<ul><li><xsl:value-of select="$lang.documento6"/></li></ul>
				</label>
			</div>
   			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:<font color="#950000">*</font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">TRAM14D6</xsl:attribute>
					<xsl:attribute name="id">TRAM14D6</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM14D6_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM14D6_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
				</input>
			</div>
			<br/>
			<div>
				<label class="gr">
			   		<xsl:attribute name="style">position: relative; width:650px;</xsl:attribute>
		   			<ul><li><xsl:value-of select="$lang.documento8"/></li></ul>
				</label>
			</div>
   			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:<font color="#950000">*</font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">TRAM14D8</xsl:attribute>
					<xsl:attribute name="id">TRAM14D8</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM14D8_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM14D8_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
				</input>
			</div>
			<br/>
   			<div>
				<label class="gr">
			   		<xsl:attribute name="style">position: relative; width:650px;</xsl:attribute>
		   			<ul><li><xsl:value-of select="$lang.documento9"/></li></ul>
				</label>
			</div>
   			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:<font color="#950000">*</font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">TRAM14D9</xsl:attribute>
					<xsl:attribute name="id">TRAM14D9</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM14D9_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM14D9_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
				</input>
			</div>
			<div style="display:none;" id="documentosRep" name="documentosRep">
				<br/>
				<div>
					<label class="gr">
				   		<xsl:attribute name="style">position: relative; width:650px;</xsl:attribute>
			   			<ul><li><xsl:value-of select="$lang.documento11"/></li></ul>
					</label>
				</div>
	   			<div class="col">
		   			<label class="gr">
						<xsl:attribute name="style">position: relative; width:200px;text-indent:40px</xsl:attribute>
						<b><xsl:value-of select="$lang.documentoPDF"/>:<font color="#950000">*</font></b>
					</label>
					<input type="file">
						<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
						<xsl:attribute name="name">TRAM14D11</xsl:attribute>
						<xsl:attribute name="id">TRAM14D11</xsl:attribute>
						<xsl:attribute name="value"></xsl:attribute>
					</input>
					<input type="hidden">
						<xsl:attribute name="name">TRAM14D11_Tipo</xsl:attribute>
						<xsl:attribute name="id">TRAM14D11_Tipo</xsl:attribute>
						<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
					</input>
				</div>
			</div>
			<br/>
			<div>
				<label class="gr">
			   		<xsl:attribute name="style">position: relative; width:650px;</xsl:attribute>
		   			<ul><li><xsl:value-of select="$lang.documento7"/></li></ul>
				</label>
			</div>
   			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:<font color="#950000">*</font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">TRAM14D7</xsl:attribute>
					<xsl:attribute name="id">TRAM14D7</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM14D7_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM14D7_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
				</input>
			</div>
			<br/>
			<div>
	   			<label class="gr">
			   		<xsl:attribute name="style">position: relative; width:650px;</xsl:attribute>
		   			<ul><li><xsl:value-of select="$lang.documento10"/></li></ul>
				</label>
			</div>
   			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;text-indent:40px</xsl:attribute>
					<b><xsl:value-of select="$lang.documentoPDF"/>:<font color="#950000">*</font></b>
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">TRAM14D10</xsl:attribute>
					<xsl:attribute name="id">TRAM14D10</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM14D10_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM14D10_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf,jpg,jpeg,gif,tif,tiff,bmp</xsl:attribute>
				</input>
			</div>
   		</div>
   		
   		<input type="hidden">
			<xsl:attribute name="name">datosEspecificos</xsl:attribute>
			<xsl:attribute name="id">datosEspecificos</xsl:attribute>
			<xsl:attribute name="value"></xsl:attribute>
		</input>
   		<br/>
   		<div class="submenu">
   			<h1><xsl:value-of select="$lang.envio"/></h1>
   		</div>
   		<script language="Javascript">
			if(document.getElementById("tipoTarjeta").value!="")
				mostrarSolicitante((document.getElementById("tipoTarjeta")).value);
		</script>
   		<div class="cuadro">
   			<xsl:variable name="deu" select="Datos_Registro/datos_especificos/Direccion_Electronica_Unica"/>
   			<input class="gr" type="checkbox" id="solicitarEnvio" onclick="activarDEU();" style="border:0px; width:20px;" >
   				<xsl:choose>
			       <xsl:when test="$deu!=''">
			           <xsl:attribute name="checked">true</xsl:attribute>
			       </xsl:when>
			    </xsl:choose>
   			</input>
   			<label for="solicitarEnvio" onclick="activarDEU();">  <xsl:value-of select="$lang.solicitoEnvio"/></label>
   			<br/><br/>
	   		<div class="col" style="visibility: hidden" id="DEU" name="DEU">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.deu"/>:<font color="#950000">*</font>
				</label>
				<input type="text" class="gr">
					<xsl:attribute name="style">position: relative; width:400px; </xsl:attribute>
					<xsl:attribute name="name">direccionElectronicaUnica</xsl:attribute>
					<xsl:attribute name="id">direccionElectronicaUnica</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Direccion_Electronica_Unica"/></xsl:attribute>
				</input>
			</div>
			<xsl:choose>
			   <xsl:when test="$deu!=''">
			       <script language="Javascript">activarDEU();</script>
			   </xsl:when>
		    </xsl:choose>
		</div>
		<div>
			<table cellpadding="0" cellspacing="0" border="0" width="95%">
				<tr><td height="5" colspan="2"></td></tr>
				<tr>
					<td align="right">
			   			<font style="color:#950000; font-size:8pt">(*)</font><font style="color:#006699; font-size:8pt"> <xsl:value-of select="$lang.required"/></font>
					</td>
					<td width="10"></td>
				</tr>
			</table>
   		</div>
	</xsl:template>
</xsl:stylesheet>