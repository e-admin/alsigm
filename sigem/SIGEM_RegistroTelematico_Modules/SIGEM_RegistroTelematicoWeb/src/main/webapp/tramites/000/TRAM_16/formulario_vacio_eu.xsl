<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="formulario_vacio.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.titulo" select="'EUS-Formulario de Contratación administrativa mediante el procedimiento negociado'"/>
	<xsl:variable name="lang.datosContratante" select="'EUS-Datos del Contratante'"/>
	<xsl:variable name="lang.datosContratado" select="'EUS-Datos del Contratado'"/>
	<xsl:variable name="lang.docIdentidad" select="'EUS-Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'EUS-Nombre'"/>
	<xsl:variable name="lang.relacion" select="'EUS-Relación'"/>
	<xsl:variable name="lang.domicilio" select="'EUS-Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.localidad" select="'EUS-Localidad'"/>
	<xsl:variable name="lang.provincia" select="'EUS-Provincia'"/>
	<xsl:variable name="lang.cp" select="'EUS-Código postal'"/>
	<xsl:variable name="lang.datosContrato" select="'EUS-Datos del Contrato'"/>
	<xsl:variable name="lang.tipoContrato" select="'EUS-Tipo de Contrato'"/>
	
	<xsl:variable name="lang.TColaboracion" select="'EUS-Colaboración entre el sector público y el sector privado'" />
	<xsl:variable name="lang.TConcesion" select="'EUS-Concesión de obras públicas'" />
	<xsl:variable name="lang.TGestion" select="'EUS-Gestión de servicios públicos'" />
	<xsl:variable name="lang.TObras" select="'EUS-Obras'" />
	<xsl:variable name="lang.TServicio" select="'EUS-Servicio'" />
	<xsl:variable name="lang.TSuministros" select="'EUS-Suministros'" />
	
	<xsl:variable name="lang.formaAdjudicacion" select="'EUS-Forma de Adjudicación'"/>
	<xsl:variable name="lang.TConcurso" select="'EUS-Concurso'"/>
	<xsl:variable name="lang.TSubasta" select="'EUS-Subasta'"/>
	
	<xsl:variable name="lang.presupuestoMax" select="'EUS-Presupuesto máximo'"/>
	<xsl:variable name="lang.precioAdjudicacion" select="'EUS-Precio de la Adjudicación'"/>
	<xsl:variable name="lang.aplicacion" select="'EUS-Aplicación presupuestaria'"/>
	<xsl:variable name="lang.plazo" select="'EUS-Plazo de Ejecución'"/>
	<xsl:variable name="lang.unidades" select="'EUS-Unidades de Plazo'"/>
	<xsl:variable name="lang.garantiaProv" select="'EUS-Garantía Provisional'"/>
	<xsl:variable name="lang.garantiaDef" select="'EUS-Garantía Definitiva'"/>
	<xsl:variable name="lang.clasificacion" select="'EUS-Clasificación'"/>
	<xsl:variable name="lang.fechaFinEjecucion" select="'EUS-Fecha final de Ejecución'"/>
	<xsl:variable name="lang.fechaFinGarantia" select="'EUS-Fecha final de Garantía'"/>
	
	<xsl:variable name="lang.TDiasNaturales" select="'EUS-Días naturales'"/>
	<xsl:variable name="lang.TDiasLaborales" select="'EUS-Días Laborales'"/>
	<xsl:variable name="lang.TMeses" select="'EUS-Meses'"/>
	<xsl:variable name="lang.TAnos" select="'EUS-Años'"/>
	
	<xsl:variable name="lang.anexar" select="'EUS-Anexar ficheros'"/>
	<xsl:variable name="lang.anexarInfo1" select="'EUS-1.- Para adjuntar un fichero (*.pdf), pulse el botón examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'EUS-2.- Seleccione el fichero que desea anexar al contrato'"/>
	
	<xsl:variable name="lang.escritura" select="'EUS-Escritura o documento de constitución'"/>
	<xsl:variable name="lang.obrar" select="'EUS-Acreditación de la capacidad de obrar'"/>
	<xsl:variable name="lang.estatutos" select="'EUS-Estatutos'"/>
	<xsl:variable name="lang.solvencia" select="'EUS-Acreditación de solvencia'"/>
	<xsl:variable name="lang.noConcurrencia" select="'EUS-Prueba de la no concurrencia de una prohibición de contratar'"/>
	<xsl:variable name="lang.seguro" select="'EUS-Declaraciones apropiadas de entidades financieras o, en su caso, justificante de la existencia de un seguro de indemnización por riesgos profesionales'"/>
	<xsl:variable name="lang.cuentas" select="'EUS-Las cuentas anuales presentadas en el Registro Mercantil o en el Registro oficial que corresponda'"/>
	<xsl:variable name="lang.volumen" select="'EUS-Declaración sobre el volumen global de negocios'"/>
	<xsl:variable name="lang.5anos" select="'EUS-Relación de las obras ejecutadas en el curso de los cinco últimos años'"/>
	<xsl:variable name="lang.unidadesTecnicas" select="'EUS-Declaración indicando los técnicos o las unidades técnicas de los que ésta disponga para la ejecución de las obras'"/>
	<xsl:variable name="lang.titulos" select="'EUS-Títulos académicos y profesionales del emepresario y de los directivos de la empresa y, en particular, del responsable o responsables de las obras'"/>
	<xsl:variable name="lang.medidas" select="'EUS-Indicación de las medidas de gestión medioambientales'"/>
	<xsl:variable name="lang.plantilla" select="'EUS-Declaración sobre la plantilla media anual de la empresa y la importancia de su personal directivo durante los tres últimos años, acompañada de la documentación justificada correspondiente'"/>
	<xsl:variable name="lang.material" select="'EUS-Declaración indicando la maquinaria, material y equipo técnico del que se dispondrá para la ejecución de las obras'"/>
	
	<xsl:variable name="lang.envio" select="'EUS-Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'EUS-Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'EUS-D.E.U.'"/>
	<xsl:variable name="lang.telefono" select="'EUS-Teléfono'"/>
	<xsl:variable name="lang.email" select="'EUS-Email'"/>	
	<xsl:variable name="lang.importante" select="'EUS-IMPORTANTE'"/>	
	<xsl:variable name="lang.nota" select="' EUS-Sólo podrán solicitar el contrato con el sector público aquellas personas naturales o jurídicas, españolas o extranjeras, que tengan plena capacidad de obrar, no estén incursas en una prohibición de contratar, y acrediten su solvencia económica, financiera y técnica o profesional o, en los casos en que así lo exija esta Ley, se encuentren debidamente clasificadas.'"/>	
	<xsl:variable name="lang.unidades" select="'EUS-Unidades Plazo'"/>
	
	
	<xsl:variable name="lang.TResponsable" select="'EUS-Responsable del contrato'"/>
	<xsl:variable name="lang.TContratante" select="'EUS-Contratante'"/>
	<xsl:variable name="lang.TLicitador" select="'EUS-Licitador'"/>
	<xsl:variable name="lang.TAdjudicatario" select="'EUS-Adjudicatario'"/>
	
</xsl:stylesheet>