<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="formulario_vacio.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.titulo" select="'Formulario de solicitud de Cédula Urbanística'"/>
	<xsl:variable name="lang.datosSolicitante" select="'Datos del Solicitante'"/>
	<xsl:variable name="lang.docIdentidad" select="'DNI, NIF, NIE, CIF'"/>
	<xsl:variable name="lang.nombre" select="'Nombre y apellidos o razón social'"/>
	<xsl:variable name="lang.domicilio" select="'Domicilio'"/>
	<xsl:variable name="lang.email" select="'Correo electrónico'"/>
	<xsl:variable name="lang.localidad" select="'Localidad'"/>
	<xsl:variable name="lang.provincia" select="'Provincia'"/>
	<xsl:variable name="lang.cp" select="'Código postal'"/>
	<xsl:variable name="lang.datosRepresentante" select="'Datos del Representante'"/>
	<xsl:variable name="lang.ubicacionInmueble" select="'Ubicación del Inmueble'"/>
	<xsl:variable name="lang.descripcionObras" select="'Descripción de las Obras'"/>			
	<xsl:variable name="lang.datosEfectoNotificacion" select="'Datos a efecto de notificación'"/>
	<xsl:variable name="lang.organoAlternativo" select="'Órgano alternativo'"/>
	<xsl:variable name="lang.servRelacionesCiudadano" select="'Servicio de Relaciones con el Ciudadano'"/>
	<xsl:variable name="lang.servSecretaria" select="'Servicio de Secretaría'"/>
	<xsl:variable name="lang.anexar" select="'Anexar ficheros'"/>
	<xsl:variable name="lang.anexarInfo1" select="'1.- Para adjuntar un fichero (*.pdf), pulse el botón examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'2.- Seleccione el fichero que desea anexar a la solicitud.'"/>
	<xsl:variable name="lang.documento1" select="'Documento'"/>
	<xsl:variable name="lang.envio" select="'Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'D.E.U.'"/>
	<xsl:variable name="lang.telefono" select="'Teléfonos'"/>
	<xsl:variable name="lang.tipovia" select="'Tipo de via'"/>
	<xsl:variable name="lang.acceso" select="'acceso'"/>
	<xsl:variable name="lang.arroyo" select="'arroyo'"/>
	<xsl:variable name="lang.avenida" select="'avenida'"/>
	<xsl:variable name="lang.barrio" select="'barrio'"/>
	<xsl:variable name="lang.bulevar" select="'bulevar'"/>
	<xsl:variable name="lang.calle" select="'calle'"/>
	<xsl:variable name="lang.callejon" select="'callejon'"/>
	<xsl:variable name="lang.camino" select="'camino'"/>
	<xsl:variable name="lang.cañada" select="'cañada'"/>
	<xsl:variable name="lang.canal" select="'canal'"/>
	<xsl:variable name="lang.canton" select="'canton'"/>
	<xsl:variable name="lang.carrera" select="'carrera'"/>
	<xsl:variable name="lang.colonia" select="'colonia'"/>
	<xsl:variable name="lang.complejo" select="'complejo'"/>
	<xsl:variable name="lang.costalina" select="'costalina'"/>
	<xsl:variable name="lang.cuesta" select="'cuesta'"/>
	<xsl:variable name="lang.edificio" select="'edificio'"/>
	<xsl:variable name="lang.escalinata" select="'escalinata'"/>
	<xsl:variable name="lang.estacion" select="'estacion'"/>
	<xsl:variable name="lang.finca" select="'finca'"/>
	<xsl:variable name="lang.galeria" select="'galeria'"/>
	<xsl:variable name="lang.glorieta" select="'glorieta'"/>
	<xsl:variable name="lang.paseo" select="'paseo'"/>
	<xsl:variable name="lang.plaza" select="'plaza'"/>
	<xsl:variable name="lang.poligono" select="'poligono'"/>
	<xsl:variable name="lang.travesia" select="'travesia'"/>
	<xsl:variable name="lang.urbanizacion" select="'urbanizacion'"/>
	<xsl:variable name="lang.via" select="'via'"/>
	<xsl:variable name="lang.numero" select="'Número'"/>
	<xsl:variable name="lang.portal" select="'Portal'"/>
	<xsl:variable name="lang.escalera" select="'Escalera'"/>
	<xsl:variable name="lang.planta" select="'Planta'"/>
	<xsl:variable name="lang.puerta" select="'Puerta'"/>
	<xsl:variable name="lang.Alava" select="'Alava'"/>
	<xsl:variable name="lang.Albacete" select="'Albacete'"/>
	<xsl:variable name="lang.Alicante" select="'Alicante'"/>
	<xsl:variable name="lang.Almeria" select="'Almeria'"/>
	<xsl:variable name="lang.Asturias" select="'Asturias'"/>
	<xsl:variable name="lang.Avila" select="'Avila'"/>
	<xsl:variable name="lang.Badajoz" select="'Badajoz'"/>
	<xsl:variable name="lang.Barcelona" select="'Barcelona'"/>
	<xsl:variable name="lang.Burgos" select="'Burgos'"/>
	<xsl:variable name="lang.Caceres" select="'Caceres'"/>
	<xsl:variable name="lang.Cadiz" select="'Cadiz'"/>
	<xsl:variable name="lang.Cantabria" select="'Cantabria'"/>
	<xsl:variable name="lang.Castellon" select="'Castellon'"/>
	<xsl:variable name="lang.Ceuta" select="'Ceuta'"/>
	<xsl:variable name="lang.CiudadReal" select="'CiudadReal'"/>
	<xsl:variable name="lang.Cordoba" select="'Cordoba'"/>
	<xsl:variable name="lang.Cuenca" select="'Cuenca'"/>
	<xsl:variable name="lang.Gerona" select="'Gerona'"/>
	<xsl:variable name="lang.Granada" select="'Granada'"/>
	<xsl:variable name="lang.Guadalajara" select="'Guadalajara'"/>
	<xsl:variable name="lang.Guipuzcoa" select="'Guipuzcoa'"/>
	<xsl:variable name="lang.Huelva" select="'Huelva'"/>
	<xsl:variable name="lang.Huesca" select="'Huesca'"/>
	<xsl:variable name="lang.IslasBaleares" select="'Islas Baleares'"/>
	<xsl:variable name="lang.Jaen" select="'Jaen'"/>
	<xsl:variable name="lang.Coruna" select="'La Coruña'"/>
	<xsl:variable name="lang.Rioja" select="'La Rioja'"/>
	<xsl:variable name="lang.Palmas" select="'Las Palmas'"/>
	<xsl:variable name="lang.Leon" select="'Leon'"/>
	<xsl:variable name="lang.Lerida" select="'Lerida'"/>
	<xsl:variable name="lang.Lugo" select="'Lugo'"/>
	<xsl:variable name="lang.Madrid" select="'Madrid'"/>
	<xsl:variable name="lang.Malaga" select="'Malaga'"/>
	<xsl:variable name="lang.Melilla" select="'Melilla'"/>
	<xsl:variable name="lang.Murcia" select="'Murcia'"/>
	<xsl:variable name="lang.Navarra" select="'Navarra'"/>
	<xsl:variable name="lang.Orense" select="'Orense'"/>
	<xsl:variable name="lang.Palencia" select="'Palencia'"/>
	<xsl:variable name="lang.Pontevedra" select="'Pontevedra'"/>
	<xsl:variable name="lang.Tenerife" select="'Tenerife'"/>
	<xsl:variable name="lang.Salamanca" select="'Salamanca'"/>
	<xsl:variable name="lang.Segovia" select="'Segovia'"/>
	<xsl:variable name="lang.Sevilla" select="'Sevilla'"/>
	<xsl:variable name="lang.Soria" select="'Soria'"/>
	<xsl:variable name="lang.Tarragona" select="'Tarragona'"/>
	<xsl:variable name="lang.Teruel" select="'Teruel'"/>
	<xsl:variable name="lang.Toledo" select="'Toledo'"/>
	<xsl:variable name="lang.Valencia" select="'Valencia'"/>
	<xsl:variable name="lang.Valladolid" select="'Valladolid'"/>
	<xsl:variable name="lang.Vizcaya" select="'Vizcaya'"/>
	<xsl:variable name="lang.Zamora" select="'Zamora'"/>
	<xsl:variable name="lang.Zaragoza" select="'Zaragoza'"/>
	<xsl:variable name="lang.datosFinca" select="'Datos de la finca (Conforme a la Ordenanza Especial Reguladora de la Cédula Urbanística)'"/>
	<xsl:variable name="lang.ubicacion" select="'Ubicación'"/>
	<xsl:variable name="lang.superficieFinca" select="'Superficie de la finca'"/>
	<xsl:variable name="lang.estadoActualEdificado" select="'Edificada'"/>
	<xsl:variable name="lang.indicarsuperficie" select="'Indique la superficie edificada'"/>
	<xsl:variable name="lang.indicarsuperficieNo" select="'No edificada'"/>
	<xsl:variable name="lang.uso" select="'Uso'"/>
	<xsl:variable name="lang.ocupantes" select="'Ocupantes'"/>
	<xsl:variable name="lang.servExistentes" select="'Servicios existentes'"/>
	<xsl:variable name="lang.cargas" select="'Cargas o servidumbres constitutivas'"/>
	<xsl:variable name="lang.datosRegistrales" select="'Datos registrales: Servicio de'"/>
       <xsl:variable name="lang.foleo" select="'Foleo'"/>
	<xsl:variable name="lang.archivo" select="'Archivo'"/>
	<xsl:variable name="lang.libro" select="'Libro'"/>
	<xsl:variable name="lang.deLaSeccion" select="'De la sección'"/>
	<xsl:variable name="lang.filaN" select="'Fila Número'"/>
	<xsl:variable name="lang.inscripcion" select="'Inscripción'"/>
	<xsl:variable name="lang.textoFin" select="'Solicito me sea expedido la cédula urbanistica de la finca reseñada, según determina la Ordenanza de Tramitacion de Cedula Urbanística, para lo cual acompaña la documentación establecida y declara que los datos y planos aportados son exactos y corresponden a la finca señalada'"/>

</xsl:stylesheet>