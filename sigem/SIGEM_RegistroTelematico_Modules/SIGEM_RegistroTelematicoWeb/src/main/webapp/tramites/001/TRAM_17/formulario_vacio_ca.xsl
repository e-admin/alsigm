<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="formulario_vacio.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.titulo" select="'Formulari de sol.licitud de Cèdula Urbanística'"/>
	<xsl:variable name="lang.datosSolicitante" select="'Dades del Sol.licitant'"/>
	<xsl:variable name="lang.docIdentidad" select="'DNI, NIF, NIE, CIF'"/>
	<xsl:variable name="lang.nombre" select="'Nom i cognoms o raó social'"/>
	<xsl:variable name="lang.domicilio" select="'Domicili'"/>
	<xsl:variable name="lang.email" select="'Correu electrònic'"/>
	<xsl:variable name="lang.localidad" select="'Localitat'"/>
	<xsl:variable name="lang.provincia" select="'Província'"/>
	<xsl:variable name="lang.cp" select="'Codi postal'"/>
	<xsl:variable name="lang.datosRepresentante" select="'Dades del Representant'"/>
	<xsl:variable name="lang.ubicacionInmueble" select="'Ubicació de l´Immoble'"/>
	<xsl:variable name="lang.descripcionObras" select="'Descripció de les obres'"/>			
	<xsl:variable name="lang.datosEfectoNotificacion" select="'Dades a efecte de notificació'"/>
	<xsl:variable name="lang.organoAlternativo" select="'Órgan alternatiu'"/>
	<xsl:variable name="lang.servRelacionesCiudadano" select="'Servei de Relacions amb el Ciutadá'"/>
	<xsl:variable name="lang.servSecretaria" select="'Servei de Secretaria'"/>
	<xsl:variable name="lang.anexar" select="'Annexar fitxers'"/>
	<xsl:variable name="lang.anexarInfo1" select="'1.- Per adjuntar un fitxer (*. pdf), premeu el botó examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'2.- Seleccioneu el fitxer que voleu annexar a la sol.licitud.'"/>
	<xsl:variable name="lang.documento1" select="'Document'"/>
	<xsl:variable name="lang.envio" select="'Enviament de notificacions'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'Demano l´enviament de notificacions per mitjans telemátics'"/>
	<xsl:variable name="lang.deu" select="'D.E.U.'"/>
	<xsl:variable name="lang.telefono" select="'Telèfons'"/>
	<xsl:variable name="lang.tipovia" select="'Tipus de via'"/>
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
	<xsl:variable name="lang.estacion" select="'estación'"/>
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
	<xsl:variable name="lang.datosFinca" select="'Dades de la finca (D´acord amb l´Ordenança Especial Reguladora de la Cédula Urbanística)'"/>
	<xsl:variable name="lang.ubicacion" select="'Ubicació'"/>
	<xsl:variable name="lang.superficieFinca" select="'Superficie de la finca'"/>
	<xsl:variable name="lang.estadoActualEdificado" select="'Edificada'"/>
	<xsl:variable name="lang.indicarsuperficie" select="'Indiqueu la superfície edificada'"/>
	<xsl:variable name="lang.indicarsuperficieNo" select="'No edificada'"/>
	<xsl:variable name="lang.uso" select="'Ús'"/>
	<xsl:variable name="lang.ocupantes" select="'Ocupants'"/>
	<xsl:variable name="lang.servExistentes" select="'Serveis existents'"/>
	<xsl:variable name="lang.cargas" select="'Cárregues o servituds constitutives'"/>
	<xsl:variable name="lang.datosRegistrales" select="'Dades registrals: Servei d´'"/>
       <xsl:variable name="lang.foleo" select="'Foleo'"/>
	<xsl:variable name="lang.archivo" select="'Arxiu'"/>
	<xsl:variable name="lang.libro" select="'Llibre'"/>
	<xsl:variable name="lang.deLaSeccion" select="'De la secció'"/>
	<xsl:variable name="lang.filaN" select="'Fila Número'"/>
	<xsl:variable name="lang.inscripcion" select="'Inscripció'"/>
	<xsl:variable name="lang.textoFin" select="'Demano em sigui expedit la cédula urbanistica de la finca ressenyada, segons determina l´Ordenança de Tramitació de Cedula Urbanística, per a aixó acompanya la documentació establerta i declara que les dades i plánols aportats són exactes i corresponen a la finca assenyalada'"/>
</xsl:stylesheet>