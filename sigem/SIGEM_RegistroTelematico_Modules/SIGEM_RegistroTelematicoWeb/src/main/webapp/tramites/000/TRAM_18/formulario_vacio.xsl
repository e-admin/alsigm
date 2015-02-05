<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="ISO-8859-1" method="html"/>

<!-- TEXTO  -->
		<xsl:variable name="lang.titulo" select="'Formulario de Solicitud de Certificado Urbanistico y autoliquidación de la tasa por licencia urbanística'"/>
			<xsl:variable name="lang.datosSolicitante" select="'Datos del Solicitante'"/>
			<xsl:variable name="lang.docIdentidad" select="'Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'Nombre'"/>
	<xsl:variable name="lang.domicilio" select="'Domicilio a efectos de notificación'"/>
				<xsl:variable name="lang.localidad" select="'Localidad'"/>
				<xsl:variable name="lang.provincia" select="'Provincia'"/>
				<xsl:variable name="lang.cp" select="'Código postal'"/>
				<xsl:variable name="lang.telefono" select="'Teléfono'"/>
				<xsl:variable name="lang.email" select="'Correo electrónico'"/>

<xsl:variable name="lang.representante" select="'Datos del Representante'"/>

		<xsl:variable name="lang.expone" select="'Expone: Necesita obtener información urbanística de:'"/>
			<xsl:variable name="lang.parcelaRustica" select="'Parcela Rústica'"/>
				<xsl:variable name="lang.numeroParcela" select="'Número de la parcela rústica'"/>			
				<xsl:variable name="lang.poligono" select="'Polígono'"/>
			<xsl:variable name="lang.parcelaUrbana" select="'Parcela Urbana'"/>
				<xsl:variable name="lang.sitoParcela" select="'Situación de la parcela'"/>
				<xsl:variable name="lang.refCatastral" select="'Referencia catastral'"/>
				<xsl:variable name="lang.otros" select="'Otro tipo de parcela'"/>
					<xsl:variable name="lang.indicar" select="'Indique'"/>

		<xsl:variable name="lang.anexar" select="'Anexar ficheros'"/>
			<xsl:variable name="lang.anexarInfo1" select="'1.- Para adjuntar un fichero, pulse el botón examinar.'"/>
			<xsl:variable name="lang.anexarInfo2" select="'2.- Seleccione el fichero que desea anexar a la solicitud.'"/>
				<xsl:variable name="lang.documento1" select="'Documento: Plano situación (pdf)'"/>
				<xsl:variable name="lang.documento2" select="'Documento: DNI  (doc)'"/>

		<xsl:variable name="lang.motivos" select="'Indique los motivos de la solicitud'"/>
		<xsl:variable name="lang.motivoSolicitud" select="'Indique los motivos de la solicitud'"/>

		<xsl:variable name="lang.solicita" select="'Solicita le sea expedida la certificación urbanística de la finca'"/>

		<xsl:variable name="lang.ayuntamiento" select="'Ilmo. Sr. Alcalde-Presidente del Excmo. Ayuntamiento'"/>
	

	<xsl:template match="/">
		<script language="Javascript">
			//ARRAY CON LOS CAMPOS OBLIGATORIOS -> ('id_campo','nombre_campo')
            var validar = new Array(11);
				validar[0] = new Array('documentoIdentidad', '<xsl:value-of select="$lang.docIdentidad"/>');
			validar[1] = new Array('nombreSolicitante','<xsl:value-of select="$lang.nombre"/>');
			validar[2] = new Array('domicilioNotificacion','<xsl:value-of select="$lang.domicilio"/>');
				validar[3] = new Array('localidad','<xsl:value-of select="$lang.localidad"/>');
				validar[4] = new Array('provincia','<xsl:value-of select="$lang.provincia"/>');
                validar[5] = new Array('codigoPostal','<xsl:value-of select="$lang.cp"/>');
				validar[6] = new Array('telefono','<xsl:value-of select="$lang.telefono"/>');
				
                validar[7] = new Array('emailSolicitante','<xsl:value-of select="$lang.email"/>');
				validar[8] = new Array('TRAM18D1','<xsl:value-of select="$lang.documento1"/>');
				validar[9] = new Array('TRAM18D2','<xsl:value-of select="$lang.documento2"/>');
				validar[10] = new Array('motivoSolicitud','<xsl:value-of select="$lang.motivoSolicitud"/>');


			//ARRAY CON LOS CAMPOS ESPECÍFICOS -> -> ('id_campo','tag_xml')
			var especificos = new Array(19);
				especificos[0] = new Array('telefono','Telefono');
				especificos[1] = new Array('numeroParcela','Numero_parcela');
				especificos[2] = new Array('poligono','Poligono');
				especificos[3] = new Array('sitoParcela','Sitio_parcela');
				especificos[4] = new Array('refCatastral','Ref_catastral');
				especificos[5] = new Array('otroTipoParcela','Otro_tipo_parcela');
				especificos[6] = new Array('motivoSolicitud','Motivo_solicitud');
				
			especificos[7] = new Array('domicilioNotificacion','Domicilio_Notificacion');
			especificos[8] = new Array('localidad','Localidad');
			especificos[9] = new Array('provincia','Provincia');
			especificos[10] = new Array('codigoPostal','Codigo_Postal');
		
			especificos[11] = new Array('nombreRepresentante','Nombre_Representante');
			especificos[12] = new Array('nifRepresentante','Nif_Representante');
			especificos[13] = new Array('domicilioRepresentante','Domicilio_Representante');
			especificos[14] = new Array('localidadRepresentante','Localidad_Representante');
			especificos[15] = new Array('provinciaRepresentante','Provincia_Representante');
			especificos[16] = new Array('codigoPostalRepresentante','Codigo_Postal_Representante');
			especificos[17] = new Array('telefonoRepresentante','Telefono_Representante');
			especificos[18] = new Array('emailRepresentante','Email_Representante');
			var validarNumero = new Array(1);
				validarNumero[0] = new Array('codigoPostal','<xsl:value-of select="$lang.cp"/>',5);
              
				
  
            function activarRustica() {
                var div = document.getElementById("rustica");
                if (div) {
                    if (div.style.visibility == "hidden") {
                        div.style.visibility = "visible";
                    } else {
                        div.style.visibility = "hidden";
                    }
                }
            }
              function activarUrbana() {
                var div = document.getElementById("urbana");
                if (div) {
                    if (div.style.visibility == "hidden") {
                        div.style.visibility = "visible";
                    } else {
                        div.style.visibility = "hidden";
                    }
                }
            }
              function activarOtroTipo() {
                var div = document.getElementById("otroTipo");
                if (div) {
                    if (div.style.visibility == "hidden") {
                        div.style.visibility = "visible";
                    } else {
                        div.style.visibility = "hidden";
                    }
                }
            }
			
			function verificacionesEspecificas() {
				return true;
			}
		</script>




<!--FORMULARIO-->

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
		
		
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.representante"/></h1>
   		</div>
   		<div class="cuadro" style="">	
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.docIdentidad"/>:
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:400px;</xsl:attribute>
					<xsl:attribute name="name">nifRepresentante</xsl:attribute>
					<xsl:attribute name="id">nifRepresentante</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Nif_Representante"/></xsl:attribute>
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
					<xsl:attribute name="name">domicilioRepresentante</xsl:attribute>
					<xsl:attribute name="id">domicilioRepresentante</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/datos_especificos/Domicilio_Representante"/></xsl:attribute>
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
		<br />
		<br />
	<div class="submenu">
   			<h1><xsl:value-of select="$lang.expone"/></h1>
   	</div>

<div class="cuadro" style="">
		<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.parcelaRustica"/>
				</label>
					
				<input type="checkbox">
					<xsl:attribute name="style">position: relative; width:83px; </xsl:attribute>
					<xsl:attribute name="name">checkRustica</xsl:attribute>
					<xsl:attribute name="id">checkRustica</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
					<xsl:attribute name="onclick">activarRustica();</xsl:attribute>
					
				</input>
				</div>
				<div style="visibility: hidden" id="rustica" name="rustica">
				<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.numeroParcela"/>:*
				</label>

				<input type="text">
					<xsl:attribute name="style">position: relative; width:50px;</xsl:attribute>
					<xsl:attribute name="name">numeroParcela</xsl:attribute>
					<xsl:attribute name="id">numeroParcela</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
					<xsl:attribute name="ensabled"></xsl:attribute>
				</input>
				</div>
		
				<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.poligono"/>:*
				</label>

				<input type="text">
					<xsl:attribute name="style">position: relative; width:250px;</xsl:attribute>
					<xsl:attribute name="name">poligono</xsl:attribute>
					<xsl:attribute name="id">poligono</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
					<xsl:attribute name="ensabled"></xsl:attribute>
				</input>

				</div>
			</div>

		<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.parcelaUrbana"/>
				</label>
				<input type="checkbox">
					<xsl:attribute name="style">position: relative; width:83px; </xsl:attribute>
					<xsl:attribute name="name">checkUrbana</xsl:attribute>
					<xsl:attribute name="id">checkUrbana</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
					<xsl:attribute name="onclick">activarUrbana();</xsl:attribute>
					
				</input>
				</div>
				<div style="visibility: hidden" id="urbana" name="urbana">
				<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.sitoParcela"/>:*
				</label>

				<input type="text">
					<xsl:attribute name="style">position: relative; width:250px;</xsl:attribute>
					<xsl:attribute name="name">sitoParcela</xsl:attribute>
					<xsl:attribute name="id">sitoParcela</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
					<xsl:attribute name="ensabled"></xsl:attribute>
				</input>
				</div>
				<div class="col">
					<label class="gr">
						<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
						<xsl:value-of select="$lang.refCatastral"/>:*
					</label>
	
					<input type="text">
						<xsl:attribute name="style">position: relative; width:50px;</xsl:attribute>
						<xsl:attribute name="name">refCatastral</xsl:attribute>
						<xsl:attribute name="id">refCatastral</xsl:attribute>
						<xsl:attribute name="value"></xsl:attribute>
						<xsl:attribute name="ensabled"></xsl:attribute>
					</input>

				</div>
				</div>
		<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.otros"/>
				</label>
<br/>
<br/>
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.indicar"/>:*
				</label>

				<input type="text">
					<xsl:attribute name="style">position: relative; width:250px;</xsl:attribute>
					<xsl:attribute name="name">otroTipoParcela</xsl:attribute>
					<xsl:attribute name="id">otroTipoParcela</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
					<xsl:attribute name="ensabled"></xsl:attribute>
				</input>

			</div>
			</div>
			<br />
			
			<div class="submenu">
   			<h1><xsl:value-of select="$lang.motivos"/></h1>
   		</div>

		<div class="cuadro" style="">
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.motivoSolicitud"/>:*
				</label>
				<textarea class="gr">
					<xsl:attribute name="style">position: relative; width:390px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
					<xsl:attribute name="name">motivoSolicitud</xsl:attribute>
					<xsl:attribute name="id">motivoSolicitud</xsl:attribute>
					<xsl:attribute name="rows">3</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</textarea>
			</div>



		</div>
			
			
<br/>
<div class="submenu">
   			<h1><xsl:value-of select="$lang.anexar"/></h1>
   		</div>

<div class="cuadro" style="">
			<label class="gr">
			   		<xsl:attribute name="style">position: relative; width:650px;</xsl:attribute>
		   			<xsl:value-of select="$lang.anexarInfo1"/><br/>
		   			<xsl:value-of select="$lang.anexarInfo2"/><br/>
			</label>
   			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.documento1"/>:*
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:300px; </xsl:attribute>
					<xsl:attribute name="name">TRAM18D1</xsl:attribute>
					<xsl:attribute name="id">TRAM18D1</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM18D1_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM18D1_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf</xsl:attribute>
				</input>
			</div>
			<div class="col">
	   			<label class="gr">
					<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
					<xsl:value-of select="$lang.documento2"/>:*
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:300px; </xsl:attribute>
					<xsl:attribute name="name">TRAM18D2</xsl:attribute>
					<xsl:attribute name="id">TRAM18D2</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM18D2_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM18D2_Tipo</xsl:attribute>
					<xsl:attribute name="value">doc</xsl:attribute>
				</input>
			</div>

   		</div>
   		
   		
   		<br/>
   		<input type="hidden">
			<xsl:attribute name="name">datosEspecificos</xsl:attribute>
			<xsl:attribute name="id">datosEspecificos</xsl:attribute>
			<xsl:attribute name="value"></xsl:attribute>
		</input>
   		<br/>
        
	</xsl:template>
</xsl:stylesheet>