<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="formulario_vacio.xsl" />
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.titulo" select="'GAL-Formulario de Contratación administrativa mediante el procedimiento negociado'"/>
	<xsl:variable name="lang.datosContratante" select="'GAL-Datos del Contratante'"/>
	<xsl:variable name="lang.datosContratado" select="'GAL-Datos del Contratado'"/>
	<xsl:variable name="lang.docIdentidad" select="'GAL-Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'GAL-Nombre'"/>
	<xsl:variable name="lang.relacion" select="'GAL-Relación'"/>
	<xsl:variable name="lang.domicilio" select="'GAL-Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.localidad" select="'GAL-Localidad'"/>
	<xsl:variable name="lang.provincia" select="'GAL-Provincia'"/>
	<xsl:variable name="lang.cp" select="'GAL-Código postal'"/>
	<xsl:variable name="lang.datosContrato" select="'GAL-Datos del Contrato'"/>
	<xsl:variable name="lang.tipoContrato" select="'GAL-Tipo de Contrato'"/>
	
	<xsl:variable name="lang.TColaboracion" select="'GAL-Colaboración entre el sector público y el sector privado'" />
	<xsl:variable name="lang.TConcesion" select="'GAL-Concesión de obras públicas'" />
	<xsl:variable name="lang.TGestion" select="'GAL-Gestión de servicios públicos'" />
	<xsl:variable name="lang.TObras" select="'GAL-Obras'" />
	<xsl:variable name="lang.TServicio" select="'GAL-Servicio'" />
	<xsl:variable name="lang.TSuministros" select="'GAL-Suministros'" />
	
	<xsl:variable name="lang.formaAdjudicacion" select="'GAL-Forma de Adjudicación'"/>
	<xsl:variable name="lang.TConcurso" select="'GAL-Concurso'"/>
	<xsl:variable name="lang.TSubasta" select="'GAL-Subasta'"/>
	
	<xsl:variable name="lang.presupuestoMax" select="'GAL-Presupuesto máximo'"/>
	<xsl:variable name="lang.precioAdjudicacion" select="'GAL-Precio de la Adjudicación'"/>
	<xsl:variable name="lang.aplicacion" select="'GAL-Aplicación presupuestaria'"/>
	<xsl:variable name="lang.plazo" select="'GAL-Plazo de Ejecución'"/>
	<xsl:variable name="lang.unidades" select="'GAL-Unidades de Plazo'"/>
	<xsl:variable name="lang.garantiaProv" select="'GAL-Garantía Provisional'"/>
	<xsl:variable name="lang.garantiaDef" select="'GAL-Garantía Definitiva'"/>
	<xsl:variable name="lang.clasificacion" select="'GAL-Clasificación'"/>
	<xsl:variable name="lang.fechaFinEjecucion" select="'GAL-Fecha final de Ejecución'"/>
	<xsl:variable name="lang.fechaFinGarantia" select="'GAL-Fecha final de Garantía'"/>
	
	<xsl:variable name="lang.TDiasNaturales" select="'GAL-Días naturales'"/>
	<xsl:variable name="lang.TDiasLaborales" select="'GAL-Días Laborales'"/>
	<xsl:variable name="lang.TMeses" select="'GAL-Meses'"/>
	<xsl:variable name="lang.TAnos" select="'GAL-Años'"/>
	
	<xsl:variable name="lang.anexar" select="'GAL-Anexar ficheros'"/>
	<xsl:variable name="lang.anexarInfo1" select="'GAL-1.- Para adjuntar un fichero (*.pdf), pulse el botón examinar.'"/>
	<xsl:variable name="lang.anexarInfo2" select="'GAL-2.- Seleccione el fichero que desea anexar al contrato'"/>
	
	<xsl:variable name="lang.escritura" select="'GAL-Escritura o documento de constitución'"/>
	<xsl:variable name="lang.obrar" select="'GAL-Acreditación de la capacidad de obrar'"/>
	<xsl:variable name="lang.estatutos" select="'GAL-Estatutos'"/>
	<xsl:variable name="lang.solvencia" select="'GAL-Acreditación de solvencia'"/>
	<xsl:variable name="lang.noConcurrencia" select="'GAL-Prueba de la no concurrencia de una prohibición de contratar'"/>
	<xsl:variable name="lang.seguro" select="'GAL-Declaraciones apropiadas de entidades financieras o, en su caso, justificante de la existencia de un seguro de indemnización por riesgos profesionales'"/>
	<xsl:variable name="lang.cuentas" select="'GAL-Las cuentas anuales presentadas en el Registro Mercantil o en el Registro oficial que corresponda'"/>
	<xsl:variable name="lang.volumen" select="'GAL-Declaración sobre el volumen global de negocios'"/>
	<xsl:variable name="lang.5anos" select="'GAL-Relación de las obras ejecutadas en el curso de los cinco últimos años'"/>
	<xsl:variable name="lang.unidadesTecnicas" select="'GAL-Declaración indicando los técnicos o las unidades técnicas de los que ésta disponga para la ejecución de las obras'"/>
	<xsl:variable name="lang.titulos" select="'GAL-Títulos académicos y profesionales del emepresario y de los directivos de la empresa y, en particular, del responsable o responsables de las obras'"/>
	<xsl:variable name="lang.medidas" select="'GAL-Indicación de las medidas de gestión medioambientales'"/>
	<xsl:variable name="lang.plantilla" select="'GAL-Declaración sobre la plantilla media anual de la empresa y la importancia de su personal directivo durante los tres últimos años, acompañada de la documentación justificada correspondiente'"/>
	<xsl:variable name="lang.material" select="'GAL-Declaración indicando la maquinaria, material y equipo técnico del que se dispondrá para la ejecución de las obras'"/>
	
	<xsl:variable name="lang.envio" select="'GAL-Envío de notificaciones'"/>
	<xsl:variable name="lang.solicitoEnvio" select="'GAL-Solicito el envío de notificaciones por medios telemáticos'"/>
	<xsl:variable name="lang.deu" select="'GAL-D.E.U.'"/>
	<xsl:variable name="lang.telefono" select="'GAL-Teléfono'"/>
	<xsl:variable name="lang.email" select="'GAL-Email'"/>	
	<xsl:variable name="lang.importante" select="'GAL-IMPORTANTE'"/>	
	<xsl:variable name="lang.nota" select="' GAL-Sólo podrán solicitar el contrato con el sector público aquellas personas naturales o jurídicas, españolas o extranjeras, que tengan plena capacidad de obrar, no estén incursas en una prohibición de contratar, y acrediten su solvencia económica, financiera y técnica o profesional o, en los casos en que así lo exija esta Ley, se encuentren debidamente clasificadas.'"/>	
	<xsl:variable name="lang.unidades" select="'GAL-Unidades Plazo'"/>
	
	<xsl:variable name="lang.TResponsable" select="'GAL-Responsable del contrato'"/>
	<xsl:variable name="lang.TContratante" select="'GAL-Contratante'"/>
	<xsl:variable name="lang.TLicitador" select="'GAL-Licitador'"/>
	<xsl:variable name="lang.TAdjudicatario" select="'GAL-Adjudicatario'"/>
	
</xsl:stylesheet>