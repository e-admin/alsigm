<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="ISO-8859-1" method="html"/>

	<xsl:variable name="lang.titulo" select="'Solicitud de Ocupación del Dominio Público'"/>

	<xsl:variable name="lang.datosSolicitante" select="'Datos del Solicitante'"/>
		<xsl:variable name="lang.docIdentidad" select="'Documento de identidad'"/>
		<xsl:variable name="lang.nombre" select="'Nombre'"/>
		<xsl:variable name="lang.telefono" select="'Teléfono'"/>
		<xsl:variable name="lang.domicilio" select="'Domicilio a efectos de notificación'"/>
		<xsl:variable name="lang.email" select="'Correo electrónico'"/>
		<xsl:variable name="lang.localidad" select="'Localidad'"/>
		<xsl:variable name="lang.provincia" select="'Provincia'"/>
		<xsl:variable name="lang.cp" select="'Código postal'"/>

	<xsl:variable name="lang.datosSolicitud" select="'Expone: Que está interesado en ocupar temporal el dominio público, sito en:'"/>	
		<xsl:variable name="lang.situacion" select="'Situación'"/>
		<xsl:variable name="lang.contenido" select="'Contenido'"/>
		<xsl:variable name="lang.duracion" select="'Duración'"/>
	   <xsl:variable name="lang.fechas" select="'Fechas que se solicitan: Fecha de inicio y fecha fin de ocupación'"/>
		<xsl:variable name="lang.metros" select="'Extensión a ocupar'"/>
		<xsl:variable name="lang.periodo" select="'Periodo'"/>	
		<xsl:variable name="lang.otros" select="'Otros'"/>

	<xsl:variable name="lang.ocupacion" select="'La ocupación se realizara por: (marque según proceda)'"/>
		<xsl:variable name="lang.materiales" select="'Materiales de construcción (gruas, andamios,...)'"/>
		<xsl:variable name="lang.materialesVarios" select="'Barracas, casetas de venta, atracciones,...'"/>
		<xsl:variable name="lang.instalaciones" select="'Instalación de rejas de pisos, lucernarios o accesos de personas o artículos a sotanos o a semisotanos'"/>
		<xsl:variable name="lang.conducciones" select="'Conducciones de energia eléctrica, agua, gas o análogos'"/>	
		<xsl:variable name="lang.analogos" select="'Mesas, sillas y análogos con finalidad lucrativa'"/>
 		<xsl:variable name="lang.instalacionesVarias" select="'Instalación de quioscos de prensa, golosinas y análogos'"/>
		<xsl:variable name="lang.puestos" select="'Puestos de mercadillo'"/>
		<xsl:variable name="lang.anuncios" select="'Anuncios'"/>
		<xsl:variable name="lang.otrosa" select="'Otros'"/>
		<xsl:variable name="lang.indicarotrosa" select="'Indicar'"/>

				<xsl:variable name="lang.indicaractividad" select="'Actividad de la venta ambulante'"/>
				<xsl:variable name="lang.atraccionferia" select="'Atracción de feria'"/>
					

	<xsl:variable name="lang.organoAsignado" select="'Órgano al que se dirige: Servicio de Secretaría'"/>
		<xsl:variable name="lang.organoAlternativo" select="'Órgano alternativo'"/>
		<xsl:variable name="lang.servRelacionesCiudadano" select="'Servicio de Relaciones con el Ciudadano'"/>
	<xsl:variable name="lang.servSecretaria" select="'Servicio de Secretaría'"/>
	<xsl:variable name="lang.servTramitacionLicencias" select="'Servicio de Tramitación de Licencias'"/>
	<xsl:variable name="lang.anexar" select="'Anexar ficheros: (si es actividad empresarial o comercial)'"/>
		<xsl:variable name="lang.anexarInfo1" select="'1.- Para adjuntar un fichero (*.pdf, *.doc,...), pulse el botón examinar.'"/>
		<xsl:variable name="lang.anexarInfo2" select="'2.- Seleccione el fichero que desea anexar a la solicitud.'"/>
		<xsl:variable name="lang.documento" select="'Documento'"/>
	<xsl:variable name="lang.envio" select="'Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'D.E.U.'"/>
	
	

		
	<xsl:template match="/">
		<script language="Javascript">
			//array de campos obligatorios -> ('id_campo','nombre_campo')
			//valida los campos rellenos-----------------------------------------------------------------
			var validar = new Array(15);
			validar[0] = new Array('documentoIdentidad', '<xsl:value-of select="$lang.docIdentidad"/>');
			validar[1] = new Array('nombreSolicitante','<xsl:value-of select="$lang.nombre"/>');
			validar[2] = new Array('telefono','<xsl:value-of select="$lang.telefono"/>');
			validar[3] = new Array('domicilioNotificacion','<xsl:value-of select="$lang.domicilio"/>');
			validar[4] = new Array('localidad','<xsl:value-of select="$lang.localidad"/>');
			validar[5] = new Array('provincia','<xsl:value-of select="$lang.provincia"/>');
			validar[6] = new Array('codigoPostal','<xsl:value-of select="$lang.cp"/>');
			validar[7] = new Array('metros','<xsl:value-of select="$lang.metros"/>');
			validar[8] = new Array('periodo','<xsl:value-of select="$lang.periodo"/>');
			validar[9] = new Array('TRAM6D1','<xsl:value-of select="$lang.documento"/>');
			validar[10] = new Array('situacion','<xsl:value-of select="$lang.situacion"/>');
			validar[11] = new Array('contenido','<xsl:value-of select="$lang.contenido"/>');
			validar[12] = new Array('duracion','<xsl:value-of select="$lang.duracion"/>');
			validar[13] = new Array('fechaDesde','<xsl:value-of select="$lang.fechas"/>');
			validar[14] = new Array('fechaHasta','<xsl:value-of select="$lang.fechas"/>');




			
			
			//Array con los datos especificos del formulario -> -> ('id_campo','tag_xml')
			var especificos = new Array(25);
			especificos[0] = new Array('telefono','Telefono');
			especificos[1] = new Array('localidad','Localidad');
			especificos[2] = new Array('provincia','Provincia');
			especificos[3] = new Array('codigoPostal','Codigo_Postal');
			especificos[4] = new Array('solicitarEnvio','Solicitar_Envio');
			especificos[5] = new Array('direccionElectronicaUnica','Direccion_Electronica_Unica');
			especificos[6] = new Array('domicilioNotificacion','Domicilio_Notificacion');
			
			especificos[7] = new Array('situacion','Situacion');
			especificos[8] = new Array('contenido','Contenido');
			especificos[9] = new Array('duracion','Duracion');
			especificos[10] = new Array('fechaDesde','Fecha_Desde');
			especificos[11] = new Array('fechaHasta','Fecha_Hasta');
			especificos[12] = new Array('metros','Metros');
			especificos[13] = new Array('periodo','Periodo');
			especificos[14] = new Array('otros','Otros');
			especificos[15] = new Array('materiales','Materiales');
			especificos[16] = new Array('materialesVarios','Materiales_Varios');
			especificos[17] = new Array('instalaciones','Instalaciones');
			especificos[18] = new Array('conducciones','Conducciones');
			especificos[19] = new Array('analogos','Analogos');
			especificos[20] = new Array('instalacionesVarias','Instalaciones_Varias');
			especificos[21] = new Array('puestos','Puestos');
			especificos[22] = new Array('anuncios','Anuncios');
			especificos[23] = new Array('otrosA','OtrosA');
			especificos[24] = new Array('indicarOtrosA','Otros_Indicados');
			
			var validarNumero = new Array(1);
			validarNumero[0] = new Array('codigoPostal','<xsl:value-of select="$lang.cp"/>',5);
			
			function verificacionesEspecificas() {
				return true;
			}
            
            function activarOtrosA() {
                var div = document.getElementById("divOtrosA");
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
   		<div class="submenu">
   			<h1><xsl:value-of select="$lang.datosSolicitante"/></h1>
   		</div>
   		<div class="cuadro" style="">	
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:160px;</xsl:attribute>
					<xsl:value-of select="$lang.docIdentidad"/>:*
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:70px;</xsl:attribute>
					<xsl:attribute name="name">documentoIdentidad</xsl:attribute>
					<xsl:attribute name="id">documentoIdentidad</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/Remitente/Documento_Identificacion/Numero"/></xsl:attribute>
					<xsl:attribute name="disabled"></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:160px;</xsl:attribute>
					<xsl:value-of select="$lang.nombre"/>:*
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:200px; </xsl:attribute>
					<xsl:attribute name="name">nombreSolicitante</xsl:attribute>
					<xsl:attribute name="id">nombreSolicitante</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/Remitente/Nombre"/></xsl:attribute>
					<xsl:attribute name="disabled"></xsl:attribute>
				</input>
			</div>
			<div class="col" style="height: 19px;">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:160px;</xsl:attribute>
					<xsl:value-of select="$lang.telefono"/>:*	
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:100px; </xsl:attribute>
					<xsl:attribute name="name">telefono</xsl:attribute>
					<xsl:attribute name="id">telefono</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
			<div class="col" style="height: 29px;">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:160px;</xsl:attribute>
					<xsl:value-of select="$lang.domicilio"/>:*	
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">domicilioNotificacion</xsl:attribute>
					<xsl:attribute name="id">domicilioNotificacion</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:160px;</xsl:attribute>
					<xsl:value-of select="$lang.email"/>:
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:200px; </xsl:attribute>
					<xsl:attribute name="name">emailSolicitante</xsl:attribute>
					<xsl:attribute name="id">emailSolicitante</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="Datos_Registro/Remitente/Correo_Electronico"/></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:160px;</xsl:attribute>
					<xsl:value-of select="$lang.localidad"/>:*	
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">localidad</xsl:attribute>
					<xsl:attribute name="id">localidad</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:160px;</xsl:attribute>
					<xsl:value-of select="$lang.provincia"/>:*
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">provincia</xsl:attribute>
					<xsl:attribute name="id">provincia</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
			<div class="col">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:160px;</xsl:attribute>
					<xsl:value-of select="$lang.cp"/>:*
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">codigoPostal</xsl:attribute>
					<xsl:attribute name="id">codigoPostal</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
		</div>
		<br/>
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.datosSolicitud"/></h1>
   		</div>
   		<div class="cuadro" style="">
			<div class="col" style="height: 66px;">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:160px;</xsl:attribute>
					<xsl:value-of select="$lang.situacion"/>:*
				</label>
				<textarea class="gr">
					<xsl:attribute name="style">position: relative; width:350px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
					<xsl:attribute name="name">situacion</xsl:attribute>
					<xsl:attribute name="id">situacion</xsl:attribute>
					<xsl:attribute name="rows">3</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</textarea>
			</div>
			<div class="col" style="height: 66px;">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:160px;</xsl:attribute>
					<xsl:value-of select="$lang.contenido"/>:*
				</label>
				<textarea class="gr">
					<xsl:attribute name="style">position: relative; width:350px; font:normal 100%/normal 'Arial', Tahoma, Helvetica, sans-serif;</xsl:attribute>
					<xsl:attribute name="name">contenido</xsl:attribute>
					<xsl:attribute name="id">contenido</xsl:attribute>
					<xsl:attribute name="rows">3</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</textarea>
			</div>

			
 			<div class="col" >
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:160px;</xsl:attribute>
					<xsl:value-of select="$lang.duracion"/>:*
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">duracion</xsl:attribute>
					<xsl:attribute name="id">duracion</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
			<div class="col" >
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:160px;</xsl:attribute>
					<xsl:value-of select="$lang.fechas"/>:*
				</label>
<br/>
<br/>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:60px; </xsl:attribute>
					<xsl:attribute name="name">fechaDesde</xsl:attribute>
					<xsl:attribute name="id">fechaDesde</xsl:attribute>
					<xsl:attribute name="value">dd/mm/aaaa</xsl:attribute>
				</input>


				<input type="text">
					<xsl:attribute name="style">position: relative; width:60px; </xsl:attribute>
					<xsl:attribute name="name">fechaHasta</xsl:attribute>
					<xsl:attribute name="id">fechaHasta</xsl:attribute>
					<xsl:attribute name="value">dd/mm/aaaa</xsl:attribute>
				</input>

			</div>

			<div class="col" >
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:160px;</xsl:attribute>
					<xsl:value-of select="$lang.metros"/>:*
				</label>

				<input type="text">
					<xsl:attribute name="style">position: relative; width:60px; </xsl:attribute>
					<xsl:attribute name="name">metros</xsl:attribute>
					<xsl:attribute name="id">metros</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
			<div class="col" >
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:160px;</xsl:attribute>
					<xsl:value-of select="$lang.periodo"/>:*
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:60px; </xsl:attribute>
					<xsl:attribute name="name">periodo</xsl:attribute>
					<xsl:attribute name="id">periodo</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
			<div class="col" >
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:160px;</xsl:attribute>
					<xsl:value-of select="$lang.otros"/>:
				</label>
				<input type="text">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">otros</xsl:attribute>
					<xsl:attribute name="id">otros</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>


   		</div>
   		<br/>
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.ocupacion"/></h1>
   		</div>
		<div class="cuadro" style="">
		
			
			<div class="col" >
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:300px;</xsl:attribute>
					<xsl:value-of select="$lang.materiales"/>:*
				</label>
				<input type="checkbox">
					<xsl:attribute name="class">checkbox</xsl:attribute>
					<xsl:attribute name="style">position: relative; width:83px; </xsl:attribute>
					<xsl:attribute name="name">materiales</xsl:attribute>
					<xsl:attribute name="id">materiales</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
			<div class="col" >
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:300px;</xsl:attribute>
					<xsl:value-of select="$lang.materialesVarios"/>:*
				</label>
				<input type="checkbox">
					<xsl:attribute name="class">checkbox</xsl:attribute>
					<xsl:attribute name="style">position: relative; width:83px; </xsl:attribute>
					<xsl:attribute name="name">materialesVarios</xsl:attribute>
					<xsl:attribute name="id">materialesVarios</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			</div>
			<div class="col" >
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:300px;</xsl:attribute>
					<xsl:value-of select="$lang.instalaciones"/>:*
				</label>
				<br/>
				<input type="checkbox">
					<xsl:attribute name="style">position: relative; width:83px; </xsl:attribute>
					<xsl:attribute name="name">instalaciones</xsl:attribute>
					<xsl:attribute name="id">instalaciones</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			
			</div>
			<div class="col" >
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:300px;</xsl:attribute>
					<xsl:value-of select="$lang.conducciones"/>:*
				</label>
				
				<input type="checkbox">
					<xsl:attribute name="style">position: relative; width:83px; </xsl:attribute>
					<xsl:attribute name="name">conducciones</xsl:attribute>
					<xsl:attribute name="id">conducciones</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			
			</div>
			<div class="col" >
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:300px;</xsl:attribute>
					<xsl:value-of select="$lang.analogos"/>:*
				</label>
				
				<input type="checkbox">
					<xsl:attribute name="style">position: relative; width:83px; </xsl:attribute>
					<xsl:attribute name="name">analogos</xsl:attribute>
					<xsl:attribute name="id">analogos</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			
			</div>
			<div class="col" >
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:300px;</xsl:attribute>
					<xsl:value-of select="$lang.instalacionesVarias"/>:*
				</label>
				
				<input type="checkbox">
					<xsl:attribute name="style">position: relative; width:83px; </xsl:attribute>
					<xsl:attribute name="name">instalacionesVarias</xsl:attribute>
					<xsl:attribute name="id">instalacionesVarias</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			
			</div>
			<div class="col" >
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:300px;</xsl:attribute>
					<xsl:value-of select="$lang.puestos"/>:*
				</label>
				
				<input type="checkbox">
					<xsl:attribute name="style">position: relative; width:83px; </xsl:attribute>
					<xsl:attribute name="name">puestos</xsl:attribute>
					<xsl:attribute name="id">puestos</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			
			</div>
			<div class="col" >
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:300px;</xsl:attribute>
					<xsl:value-of select="$lang.anuncios"/>:*
				</label>
				
				<input type="checkbox">
					<xsl:attribute name="style">position: relative; width:83px; </xsl:attribute>
					<xsl:attribute name="name">anuncios</xsl:attribute>
					<xsl:attribute name="id">anuncios</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
			
			</div>
			
			
			<div class="col" >
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:300px;</xsl:attribute>
					<xsl:value-of select="$lang.otrosa"/>:*
				</label>
				
				<input type="checkbox">
					<xsl:attribute name="style">position: relative; width:83px; </xsl:attribute>
					<xsl:attribute name="name">otrosA</xsl:attribute>
					<xsl:attribute name="id">otrosA</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
					<xsl:attribute name="onclick">activarOtrosA();</xsl:attribute>
					
				</input>
			
			</div>

	   		<div class="col" style="visibility: hidden" id="divOtrosA" name="divOtrosA">
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:163px;</xsl:attribute>
					<xsl:value-of select="$lang.indicarotrosa"/>:*
				</label>
				<input type="text" class="gr">
					<xsl:attribute name="style">position: relative; width:350px; </xsl:attribute>
					<xsl:attribute name="name">indicarOtrosA</xsl:attribute>
					<xsl:attribute name="id">indicarOtrosA</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
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
					<xsl:attribute name="style">position: relative; width:250px;</xsl:attribute>
					<xsl:value-of select="$lang.documento"/>:*
				</label>
				<input type="file">
					<xsl:attribute name="style">position: relative; width:500px; </xsl:attribute>
					<xsl:attribute name="name">TRAM6D1</xsl:attribute>
					<xsl:attribute name="id">TRAM6D1</xsl:attribute>
					<xsl:attribute name="value"></xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="name">TRAM6D1_Tipo</xsl:attribute>
					<xsl:attribute name="id">TRAM6D1_Tipo</xsl:attribute>
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