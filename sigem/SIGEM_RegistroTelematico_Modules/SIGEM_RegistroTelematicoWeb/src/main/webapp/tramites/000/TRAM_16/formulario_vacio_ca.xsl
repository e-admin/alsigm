<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="formulario_vacio.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.titulo" select="'CAT-Formulario de Contratación administrativa mediante el procedimiento negociado'"/>
	<xsl:variable name="lang.datosContratante" select="'CAT-Datos del Contratante'"/>
	<xsl:variable name="lang.datosContratado" select="'CAT-Datos del Contratado'"/>
	<xsl:variable name="lang.docIdentidad" select="'CAT-Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'CAT-Nombre'"/>
	<xsl:variable name="lang.relacion" select="'CAT-Relación'"/>
	<xsl:variable name="lang.domicilio" select="'CAT-Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.localidad" select="'CAT-Localidad'"/>
	<xsl:variable name="lang.provincia" select="'CAT-Provincia'"/>
	<xsl:variable name="lang.cp" select="'CAT-Código postal'"/>
	<xsl:variable name="lang.datosContrato" select="'CAT-Datos del Contrato'"/>
	<xsl:variable name="lang.tipoContrato" select="'CAT-Tipo de Contrato'"/>
	
	<xsl:variable name="lang.TColaboracion" select="'CAT-Colaboración entre el sector público y el sector privado'" />
	<xsl:variable name="lang.TConcesion" select="'CAT-Concesión de obras públicas'" />
	<xsl:variable name="lang.TGestion" select="'CAT-Gestión de servicios públicos'" />
	<xsl:variable name="lang.TObras" select="'CAT-Obras'" />
	<xsl:variable name="lang.TServicio" select="'CAT-Servicio'" />
	<xsl:variable name="lang.TSuministros" select="'CAT-Suministros'" />
	
	<xsl:variable name="lang.formaAdjudicacion" select="'CAT-Forma de Adjudicación'"/>
	<xsl:variable name="lang.TConcurso" select="'CAT-Concurso'"/>
	<xsl:variable name="lang.TSubasta" select="'CAT-Subasta'"/>
	
	<xsl:variable name="lang.presupuestoMax" select="'CAT-Presupuesto máximo'"/>
	<xsl:variable name="lang.precioAdjudicacion" select="'CAT-Precio de la Adjudicación'"/>
	<xsl:variable name="lang.aplicacion" select="'CAT-Aplicación presupuestaria'"/>
	<xsl:variable name="lang.plazo" select="'CAT-Plazo de Ejecución'"/>
	<xsl:variable name="lang.unidades" select="'CAT-Unidades de Plazo'"/>
	<xsl:variable name="lang.garantiaProv" select="'CAT-Garantía Provisional'"/>
	<xsl:variable name="lang.garantiaDef" select="'CAT-Garantía Definitiva'"/>
	<xsl:variable name="lang.clasificacion" select="'CAT-Clasificación'"/>
	<xsl:variable name="lang.fechaFinEjecucion" select="'CAT-Fecha final de Ejecución'"/>
	<xsl:variable name="lang.fechaFinGarantia" select="'CAT-Fecha final de Garantía'"/>
	
	<xsl:variable name="lang.TDiasNaturales" select="'CAT-Días naturales'"/>
	<xsl:variable name="lang.TDiasLaborales" select="'CAT-Días Laborales'"/>
	<xsl:variable name="lang.TMeses" select="'CAT-Meses'"/>
	<xsl:variable name="lang.TAnos" select="'CAT-Años'"/>
	
	<xsl:variable name="lang.anexar" select="'CAT-Anexar ficheros'"/>
	<xsl:variable name="lang.anexarInfo1" select="'CAT-1.- Para adjuntar un fichero (*.pdf), pulse el botón examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'CAT-2.- Seleccione el fichero que desea anexar al contrato'"/>
	
	<xsl:variable name="lang.escritura" select="'CAT-Escritura o documento de constitución'"/>
	<xsl:variable name="lang.obrar" select="'CAT-Acreditación de la capacidad de obrar'"/>
	<xsl:variable name="lang.estatutos" select="'CAT-Estatutos'"/>
	<xsl:variable name="lang.solvencia" select="'CAT-Acreditación de solvencia'"/>
	<xsl:variable name="lang.noConcurrencia" select="'CAT-Prueba de la no concurrencia de una prohibición de contratar'"/>
	<xsl:variable name="lang.seguro" select="'CAT-Declaraciones apropiadas de entidades financieras o, en su caso, justificante de la existencia de un seguro de indemnización por riesgos profesionales'"/>
	<xsl:variable name="lang.cuentas" select="'CAT-Las cuentas anuales presentadas en el Registro Mercantil o en el Registro oficial que corresponda'"/>
	<xsl:variable name="lang.volumen" select="'CAT-Declaración sobre el volumen global de negocios'"/>
	<xsl:variable name="lang.5anos" select="'CAT-Relación de las obras ejecutadas en el curso de los cinco últimos años'"/>
	<xsl:variable name="lang.unidadesTecnicas" select="'CAT-Declaración indicando los técnicos o las unidades técnicas de los que ésta disponga para la ejecución de las obras'"/>
	<xsl:variable name="lang.titulos" select="'CAT-Títulos académicos y profesionales del emepresario y de los directivos de la empresa y, en particular, del responsable o responsables de las obras'"/>
	<xsl:variable name="lang.medidas" select="'CAT-Indicación de las medidas de gestión medioambientales'"/>
	<xsl:variable name="lang.plantilla" select="'CAT-Declaración sobre la plantilla media anual de la empresa y la importancia de su personal directivo durante los tres últimos años, acompañada de la documentación justificada correspondiente'"/>
	<xsl:variable name="lang.material" select="'CAT-Declaración indicando la maquinaria, material y equipo técnico del que se dispondrá para la ejecución de las obras'"/>
	
	<xsl:variable name="lang.envio" select="'CAT-Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'CAT-Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'CAT-D.E.U.'"/>
	<xsl:variable name="lang.telefono" select="'CAT-Teléfono'"/>
	<xsl:variable name="lang.email" select="'CAT-Email'"/>	
	<xsl:variable name="lang.importante" select="'CAT-IMPORTANTE'"/>	
	<xsl:variable name="lang.nota" select="' CAT-Sólo podrán solicitar el contrato con el sector público aquellas personas naturales o jurídicas, españolas o extranjeras, que tengan plena capacidad de obrar, no estén incursas en una prohibición de contratar, y acrediten su solvencia económica, financiera y técnica o profesional o, en los casos en que así lo exija esta Ley, se encuentren debidamente clasificadas.'"/>	
	<xsl:variable name="lang.unidades" select="'CAT-Unidades Plazo'"/>
	
	<xsl:variable name="lang.TResponsable" select="'CAT-Responsable del contrato'"/>
	<xsl:variable name="lang.TContratante" select="'CAT-Contratante'"/>
	<xsl:variable name="lang.TLicitador" select="'CAT-Licitador'"/>
	<xsl:variable name="lang.TAdjudicatario" select="'CAT-Adjudicatario'"/>
	
</xsl:stylesheet>