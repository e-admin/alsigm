<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="ISO-8859-1" method="html"/>

<!-- CREAMOS LOS TEXTOS DEL PROPERTIES -->

	<xsl:variable name="lang.titulo" 							select="'Formulario de solicitud de Cédula Urbanística'"/>
	<xsl:variable name="lang.datosSolicitante" select="'Datos del Solicitante'"/>
	<xsl:variable name="lang.docIdentidad" select="'Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'Nombre'"/>
	<xsl:variable name="lang.domicilio" select="'Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.email" select="'Correo electrónico'"/>
	<xsl:variable name="lang.localidad" select="'Localidad'"/>
	<xsl:variable name="lang.provincia" select="'Provincia'"/>
	<xsl:variable name="lang.cp" select="'Código postal'"/>
	<xsl:variable name="lang.telefono" select="'Teléfono'"/>
<xsl:variable name="lang.representante" select="'Datos del Representante'"/>

		<xsl:variable name="lang.anexar" 						select="'Anexar ficheros'"/>
			<xsl:variable name="lang.anexarInfo1" 				select="'1.- Para adjuntar un fichero (*.pdf), pulse el botón examinar.'"/>
			<xsl:variable name="lang.anexarInfo2" 				select="'2.- Seleccione el fichero que desea anexar a la solicitud.'"/>
				<xsl:variable name="lang.documento1" 			select="'Documento'"/>


		<xsl:variable name="lang.envio" 						select="'Envío de notificaciones'"/>
			<xsl:variable name="lang.solicitoEnvio" 				select="'Solicito el envío de notificaciones por medios telemáticos'"/>
				<xsl:variable name="lang.deu" 				select="'D.E.U.'"/>
	
	
		<xsl:variable name="lang.datosFinca" 					select="'Datos de la finca (Conforme a la Ordenanza Especial Reguladora de la Cédula Urbanística)'"/>
			<xsl:variable name="lang.ubicacion" 				select="'Ubicación'"/>
			<xsl:variable name="lang.superficieFinca" 			select="'Superficie de la finca'"/>
			<xsl:variable name="lang.estadoActualEdificado" 			select="'Edificada'"/>
				<xsl:variable name="lang.indicarsuperficie" 		select="'Indique la superficie edificada'"/>
			<xsl:variable name="lang.indicarsuperficieNo" 			select="'No edificada'"/>
			<xsl:variable name="lang.uso" 					select="'Uso'"/>
			<xsl:variable name="lang.ocupantes" 				select="'Ocupantes'"/>
			<xsl:variable name="lang.servExistentes" 				select="'Servicios existentes'"/>
			<xsl:variable name="lang.cargas" 					select="'Cargas o servidumbres constitutivas'"/>
			<xsl:variable name="lang.datosRegistrales" 			select="'Datos registrales: Servicio de'"/>
       		<xsl:variable name="lang.foleo" 					select="'Folio'"/>
			<xsl:variable name="lang.archivo" 					select="'Archivo'"/>
			<xsl:variable name="lang.libro" 					select="'Libro'"/>
			<xsl:variable name="lang.deLaSeccion" 				select="'De la sección'"/>
			<xsl:variable name="lang.filaN" 					select="'Fila Número'"/>
			<xsl:variable name="lang.inscripcion" 				select="'Inscripción'"/>

	<xsl:variable name="lang.textoFin" 						select="'Solicito me sea expedido la cédula urbanistica de la finca reseñada, según determina la Ordenanza de Tramitacion de Cedula Urbanística, para lo cual acompaña la documentación establecida y declara que los datos y planos aportados son exactos y corresponden a la finca señalada'"/>

<xsl:template match="/">

		<script language="Javascript">
//array de campos obligatorios Comprueba que los campos están rellenos si no muestra alerta -> ('id_campo','nombre_campo')
			
			var validar = new Array(20);
			validar[0] = new Array('documentoIdentidad', '<xsl:value-of select="$lang.docIdentidad"/>');
			validar[1] = new Array('nombreSolicitante','<xsl:value-of select="$lang.nombre"/>');
			validar[2] = new Array('domicilioNotificacion','<xsl:value-of select="$lang.domicilio"/>');
			validar[3] = new Array('localidad','<xsl:value-of select="$lang.localidad"/>');
			validar[4] = new Array('provincia','<xsl:value-of select="$lang.provincia"/>');
			validar[5] = new Array('codigoPostal','<xsl:value-of select="$lang.cp"/>');
		

				validar[6] = new Array('ubicacion','<xsl:value-of select="$lang.ubicacion"/>');
				validar[7] = new Array('superficieFinca','<xsl:value-of select="$lang.superficieFinca"/>');
				validar[8] = new Array('uso','<xsl:value-of select="$lang.uso"/>');
				validar[9] = new Array('ocupantes','<xsl:value-of select="$lang.ocupantes"/>');
				validar[10] = new Array('servExistentes','<xsl:value-of select="$lang.servExistentes"/>');
				validar[11] = new Array('cargas','<xsl:value-of select="$lang.cargas"/>');
				validar[12] = new Array('datosRegistrales','<xsl:value-of select="$lang.datosRegistrales"/>');
				validar[13] = new Array('folio','<xsl:value-of select="$lang.foleo"/>');
				validar[14] = new Array('archivo','<xsl:value-of select="$lang.archivo"/>');
				validar[15] = new Array('libro','<xsl:value-of select="$lang.libro"/>');
				validar[16] = new Array('deLaSeccion','<xsl:value-of select="$lang.deLaSeccion"/>');
				validar[17] = new Array('filaN','<xsl:value-of select="$lang.filaN"/>');
				validar[18] = new Array('inscripcion','<xsl:value-of select="$lang.inscripcion"/>');
				validar[19] = new Array('TRAM17D1','<xsl:value-of select="$lang.documento1"/>');
				
			

//Array con los datos especificos del formulario -> -> ('id_campo','tag_xml')
			
			var especificos = new Array(29);
				
			especificos[0] = new Array('domicilioNotificacion','Domicilio_Notificacion');
			especificos[1] = new Array('localidad','Localidad');
			especificos[2] = new Array('provincia','Provincia');
			especificos[3] = new Array('codigoPostal','Codigo_Postal');
			especificos[4] = new Array('telefono','Telefono');
			especificos[5] = new Array('nombreRepresentante','Nombre_Representante');
			especificos[6] = new Array('nifRepresentante','Nif_Representante');
			especificos[7] = new Array('domicilioRepresentante','Domicilio_Representante');
			especificos[8] = new Array('localidadRepresentante','Localidad_Representante');
			especificos[9] = new Array('provinciaRepresentante','Provincia_Representante');
			especificos[10] = new Array('codigoPostalRepresentante','Codigo_Postal_Representante');
			especificos[11] = new Array('telefonoRepresentante','Telefono_Representante');
			especificos[12] = new Array('emailRepresentante','Email_Representante');

				especificos[13] = new Array('ubicacion','Ubicacion');
				especificos[14] = new Array('superficieFinca','Superficie_finca');
				especificos[15] = new Array('superficie','Superficie');
				especificos[16] = new Array('indicarsuperficie','Superficie_indicada');
				especificos[17] = new Array('superficieFincaNo','Superficie_finca_no');
				especificos[18] = new Array('uso','Uso');
				especificos[19] = new Array('ocupantes','Ocupantes');
				especificos[20] = new Array('servExistentes','Serv_Existentes');
				especificos[21] = new Array('cargas','Cargas');
				especificos[22] = new Array('datosRegistrales','Datos_registrales');				
				especificos[23] = new Array('folio','Folio');
				especificos[24] = new Array('archivo','Archivo');
				especificos[25] = new Array('libro','Libro');
				especificos[26] = new Array('deLaSeccion','Seccion');
				especificos[27] = new Array('filaN','Fila');				
				especificos[28] = new Array('inscripcion','Inscripcion');						
				
				
			var validarNumero = new Array(1);
			validarNumero[0] = new Array('codigoPostal','<xsl:value-of select="$lang.cp"/>',5);

			
			function verificacionesEspecificas() {
				return true;
			}
            
            function activaSuperficie() {
                var div = document.getElementById("divSuperficie");
                if (div) {
                    if (div.style.visibility == "hidden") {
                        div.style.visibility = "visible";
                    } else {
                        div.style.visibility = "hidden";
                    }
                }
            }
		</script>


		<h1><xsl:value-of select="$lang.titulo"/></h1>
   		<br/>

<!-- DATOS DEL SOLICITANTE -->

   	
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
   	
   	
   	
		<br/>
					
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.datosFinca"/></h1>
   		</div>
		<div class="cuadro" style="">
		<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:180px;</xsl:attribute>
					<xsl:value-of select="$lang.ubicacion"/>:*	
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">ubicacion</xsl:attribute>
					<xsl:attribute name="id">ubicacion</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
		</div>
		<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:180px;</xsl:attribute>
					<xsl:value-of select="$lang.superficieFinca"/>:*	
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:60px; </xsl:attribute>
					<xsl:attribute name="name">superficieFinca</xsl:attribute>
					<xsl:attribute name="id">superficieFinca</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
		</div>
		<div class="col">
			<label class="gr">
					<xsl:attribute name="style">position: relative; width:180px;</xsl:attribute>
					<xsl:value-of select="$lang.estadoActualEdificado"/>:*
				</label>
				<input type="checkbox" class="gr">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">superficie</xsl:attribute>
					<xsl:attribute name="id">superficie</xsl:attribute>
					<xsl:attribute name="onclick">activaSuperficie();</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			
   		</div>
   		
	   		<div  style="visibility: hidden" id="divSuperficie" name="divSuperficie" class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:180px;</xsl:attribute>
					<xsl:value-of select="$lang.indicarsuperficie"/>:*
				</label>
				<input type="text" class="gr">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">indicarsuperficie</xsl:attribute>
					<xsl:attribute name="id">indicarsuperficie</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:180px;</xsl:attribute>
					<xsl:value-of select="$lang.indicarsuperficieNo"/>:*	
				</label>
				<input type="checkbox">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">superficieFincaNo</xsl:attribute>
					<xsl:attribute name="id">superficieFincaNo</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
			<div class="col">
			
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:180px;</xsl:attribute>
					<xsl:value-of select="$lang.uso"/>:*	
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">uso</xsl:attribute>
					<xsl:attribute name="id">uso</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			
			</div>
			<div class="col">
			
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:180px;</xsl:attribute>
					<xsl:value-of select="$lang.ocupantes"/>:*	
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:50px; </xsl:attribute>
					<xsl:attribute name="name">ocupantes</xsl:attribute>
					<xsl:attribute name="id">ocupantes</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
			
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:180px;</xsl:attribute>
					<xsl:value-of select="$lang.servExistentes"/>:*	
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">servExistentes</xsl:attribute>
					<xsl:attribute name="id">servExistentes</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
			
			<div class="col">
			
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:180px;</xsl:attribute>
					<xsl:value-of select="$lang.cargas"/>:*	
				</label>
			
				<input type="text">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">cargas</xsl:attribute>
					<xsl:attribute name="id">cargas</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
			
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:180px;</xsl:attribute>
					<xsl:value-of select="$lang.datosRegistrales"/>:*	
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">datosRegistrales</xsl:attribute>
					<xsl:attribute name="id">datosRegistrales</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:180px;</xsl:attribute>
					<xsl:value-of select="$lang.foleo"/>:*	
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">folio</xsl:attribute>
					<xsl:attribute name="id">folio</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:180px;</xsl:attribute>
					<xsl:value-of select="$lang.archivo"/>:*	
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">archivo</xsl:attribute>
					<xsl:attribute name="id">archivo</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:180px;</xsl:attribute>
					<xsl:value-of select="$lang.libro"/>:*	
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">libro</xsl:attribute>
					<xsl:attribute name="id">libro</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:180px;</xsl:attribute>
					<xsl:value-of select="$lang.deLaSeccion"/>:*	
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">deLaSeccion</xsl:attribute>
					<xsl:attribute name="id">deLaSeccion</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:180px;</xsl:attribute>
					<xsl:value-of select="$lang.filaN"/>:*	
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">filaN</xsl:attribute>
					<xsl:attribute name="id">filaN</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:180px;</xsl:attribute>
					<xsl:value-of select="$lang.inscripcion"/>:*	
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">inscripcion</xsl:attribute>
					<xsl:attribute name="id">inscripcion</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
				<br/>
				<br/>
				<br/>
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:590px;</xsl:attribute>
					<xsl:value-of select="$lang.textoFin"/>	
				</label>
				<br/>
				<br/>
				<br/>
				<br/>

		</div>



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
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.documento1"/>:*
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:500px; </xsl:attribute>
					<xsl:attribute name="name">TRAM17D1</xsl:attribute>
					<xsl:attribute name="id">TRAM17D1</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM17D1_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM17D1_Tipo</xsl:attribute>
					<xsl:attribute name="value">pdf</xsl:attribute>
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
   		<div class="submenu">
   			<h1><xsl:value-of select="$lang.envio"/></h1>
   		</div>
   		<div class="cuadro">
   			<input class="gr" type="checkbox" id="solicitarEnvio" onclick="activarDEU();" style="border:0px; width:20px;" />
   			<label for="solicitarEnvio" onclick="activarDEU();">  <xsl:value-of select="$lang.solicitoEnvio"/></label>
   			<br/><br/>
	   		<div class="col" style="visibility: hidden" id="DEU" name="DEU">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:150px;</xsl:attribute>
					<xsl:value-of select="$lang.deu"/>:*
				</label>
				<input type="text" class="gr">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">direccionElectronicaUnica</xsl:attribute>
					<xsl:attribute name="id">direccionElectronicaUnica</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
		</div>
	</xsl:template>
</xsl:stylesheet>