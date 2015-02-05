package auditoria;

import common.Constants;

/**
 * Clase que encapsula los posible detalles de una traza de auditoria
 */
public class ArchivoDetails {
	// =============================================
	// DEPOSITO
	// =============================================
	public static final String LOGIN_FAIL = "auditoria.detalles.LOGIN_FAIL";

	// =============================================
	// DEPOSITO
	// =============================================
	public static final String DEPOSITO_ELEMENTO_CUADRO_ID_PADRE = "auditoria.detalles.DEPOSITO_ELEMENTO_CUADRO_ID_PADRE";
	public static final String DEPOSITO_ELEMENTO_CUADRO_NOMBRE_PADRE = "auditoria.detalles.DEPOSITO_ELEMENTO_CUADRO_NOMBRE_PADRE";
	public static final String DEPOSITO_ELEMENTO_CUADRO_ID = "auditoria.detalles.DEPOSITO_ELEMENTO_CUADRO_ID";
	public static final String DEPOSITO_ELEMENTO_CUADRO_NOMBRE = "auditoria.detalles.DEPOSITO_ELEMENTO_CUADRO_NOMBRE";
	public static final String DEPOSITO_ELEMENTO_CUADRO_TIPO = "auditoria.detalles.DEPOSITO_ELEMENTO_CUADRO_TIPO";
	public static final String DEPOSITO_ELEMENTO_CUADRO_UBICACION = "auditoria.detalles.DEPOSITO_ELEMENTO_CUADRO_UBICACION";
	public static final String DEPOSITO_ELEMENTO_NUM_HUECOS = "auditoria.detalles.DEPOSITO_ELEMENTO_NUM_HUECOS";
	public static final String DEPOSITO_ELEMENTO_LONGITUD = "archigest.archivo.deposito.longitud";
	public static final String DEPOSITO_ELEMENTO_TIPO_FORMATO = "archigest.archivo.deposito.formato";
	public static final String DEPOSITO_ELEMENTO_CUADRO_ID_DEPOSITO = "auditoria.detalles.DEPOSITO_ELEMENTO_CUADRO_ID_DEPOSITO";
	public static final String DEPOSITO_HUECO_ID_DEPOSITO = "auditoria.detalles.DEPOSITO_HUECO_ID_DEPOSITO";
	public static final String DEPOSITO_HUECOS_RESERVADOS = "auditoria.detalles.DEPOSITO_HUECOS_RESERVADOS";
	public static final String DEPOSITO_HUECO_RESERVADO = "auditoria.detalles.DEPOSITO_HUECO_RESERVADO";
	public static final String DEPOSITO_HUECOS_OCUPADOS = "auditoria.detalles.DEPOSITO_HUECOS_OCUPADOS";
	public static final String DEPOSITO_HUECO_OCUPADO = "auditoria.detalles.DEPOSITO_HUECO_OCUPADO";
	public static final String DEPOSITO_HUECO_ID_PADRE = "auditoria.detalles.DEPOSITO_HUECO_ID_PADRE";
	public static final String DEPOSITO_HUECO_NUMORDEN = "auditoria.detalles.DEPOSITO_HUECO_NUMORDEN";
	public static final String DEPOSITO_HUECO_ESTADO = "auditoria.detalles.DEPOSITO_HUECO_ESTADO";
	public static final String DEPOSITO_HUECO_ESTADO_ANTERIOR = "auditoria.detalles.DEPOSITO_HUECO_ESTADO_ANTERIOR";
	public static final String DEPOSITO_ELEMENTO_CUADRO_ID_DESTINO = "auditoria.detalles.DEPOSITO_ELEMENTO_CUADRO_ID_DESTINO";
	public static final String DEPOSITO_ELEMENTO_CUADRO_TIPO_DESTINO = "auditoria.detalles.DEPOSITO_ELEMENTO_CUADRO_TIPO_DESTINO";
	public static final String DEPOSITO_DEPOSITO_NOMBRE = "auditoria.detalles.DEPOSITO_DEPOSITO_NOMBRE";
	public static final String DEPOSITO_UIS_REUBICAR = "auditoria.detalles.DEPOSITO_UIS_REUBICAR";
	public static final String DEPOSITO_UDOCS_REUBICAR = "auditoria.detalles.DEPOSITO_UDOCS_REUBICAR";
	public static final String DEPOSITO_UI_ORIGEN = "auditoria.detalles.DEPOSITO_UI_ORIGEN";
	public static final String DEPOSITO_UI_DESTINO = "auditoria.detalles.DEPOSITO_UI_DESTINO";
	public static final String DEPOSITO_DEPOSITO_ID_EXT = "auditoria.detalles.DEPOSITO_DEPOSITO_ID_EXT";
	public static final String DEPOSITO_DEPOSITO_DESCRIPCION = "auditoria.detalles.DEPOSITO_DEPOSITO_DESCRIPCION";
	public static final String DEPOSITO_SIGNATURA_ORIGINAL = "auditoria.detalles.DEPOSITO_SIGNATURA_ORIGINAL";
	public static final String DEPOSITO_SIGNATURA_NUEVA = "auditoria.detalles.DEPOSITO_SIGNATURA_NUEVA";
	public static final String DEPOSITO_UINS_ELIMINADA = "auditoria.detalles.DEPOSITO_UINS_ELIMINADA";
	public static final String DEPOSITO_UNIDAD_INSTALACION = "archigest.archivo.deposito.unidInstalacion";
	public static final String DEPOSITO_SIGNATURA = "archigest.archivo.signatura";
	public static final String DEPOSITO_ORIGEN_REHUBICACION = "archigest.archivo.deposito.origReubicar";
	public static final String DEPOSITO_DESTINO_REHUBICACION = "archigest.archivo.deposito.destinoReubicar";
	public static final String DEPOSITO_UI_ARCHIVO = "archigest.archivo.archivo";
	public static final String DEPOSITO_TIPO_ELIMINACION = "archigest.archivo.deposito.tipoEliminacionUI";

	// =============================================
	// SOLICITUDES
	// =============================================
	public static final String SOLICITUDES_PRESTAMO_ID = "auditoria.detalles.solicitudes.prestamos.ID";
	public static final String SOLICITUDES_PRESTAMO = "auditoria.detalles.SOLICITUDES_PRESTAMO";
	public static final String SOLICITUDES_PRESTAMO_SOLICITANTE = "auditoria.detalles.SOLICITUDES_PRESTAMO_SOLICITANTE";
	public static final String SOLICITUDES_PRESTAMO_RESERVA = "auditoria.detalles.SOLICITUDES_PRESTAMO_RESERVA";
	public static final String SOLICITUDES_PRESTAMO_DETALLE_ACEPTADO = "auditoria.detalles.SOLICITUDES_PRESTAMO_DETALLE_ACEPTADO";
	public static final String SOLICITUDES_PRESTAMO_DETALLE_DENEGADO = "auditoria.detalles.SOLICITUDES_PRESTAMO_DETALLE_DENEGADO";
	public static final String SOLICITUDES_PRESTAMO_DETALLE_RESERVADO = "auditoria.detalles.SOLICITUDES_PRESTAMO_DETALLE_RESERVADO";
	public static final String SOLICITUDES_PRESTAMO_ACEPTADO = "auditoria.detalles.SOLICITUDES_PRESTAMO_ACEPTADO";
	public static final String SOLICITUDES_PRESTAMO_DENEGADO = "auditoria.detalles.SOLICITUDES_PRESTAMO_DENEGADO";
	public static final String SOLICITUDES_PRESTAMO_RESERVADO = "auditoria.detalles.SOLICITUDES_PRESTAMO_RESERVADO";
	public static final String SOLICITUDES_PRESTAMO_DETALLE_PRORROGADO = "auditoria.detalles.SOLICITUDES_PRESTAMO_DETALLE_PRORROGADO";
	public static final String SOLICITUDES_PRESTAMO_PRORROGA_DENEGADA = "auditoria.detalles.SOLICITUDES_PRESTAMO_PRORROGA_DENEGADA";
	public static final String SOLICITUDES_PRESTAMO_RECLAMACION_NUMBER = "auditoria.detalles.SOLICITUDES_PRESTAMO_RECLAMACION_NUMBER";
	public static final String SOLICITUDES_PRESTAMO_USUARIO_ANTERIOR = "auditoria.detalles.SOLICITUDES_PRESTAMO_USUARIO_ORIGEN";
	public static final String SOLICITUDES_PRESTAMO_USUARIO_NUEVO = "auditoria.detalles.SOLICITUDES_PRESTAMO_USUARIO_DESTINO";

	public static final String SOLICITUDES_CODIGO_PRESTAMO = "archigest.archivo.prestamos.busqueda.codigo";
	public static final String SOLICITUDES_ORGANO_PRESTAMO = "archigest.archivo.prestamos.busqueda.organo";
	public static final String SOLICITUDES_SOLICITANTE_PRESTAMO = "archigest.archivo.prestamos.busqueda.solicitante";
	public static final String SOLICITUDES_ESTADOS_PRESTAMO = "archigest.archivo.prestamos.busqueda.estado";
	public static final String SOLICITUDES_TIPOSNOTAS_PRESTAMO = "archigest.archivo.prestamos.busqueda.notas";
	public static final String SOLICITUDES_FECHA_INICIAL_RESERVA = "archigest.archivo.prestamos.edicion.fechaReserva";
	public static final String SOLICITUDES_ARCHIVO_PRESTAMO = "archigest.archivo.prestamos.archivo";
	public static final String SOLICITUDES_AUTORIZADO = "archigest.archivo.prestamos.autorizado";
	public static final String SOLICITUDES_TIPO_ENTREGA = "archigest.archivo.prestamos.tipoEntrega";
	public static final String SOLICITUDES_NUMERO_EXPEDIENTE = "archigest.archivo.prestamos.expedienteudoc";

	public static final String SOLICITUDES_CONSULTA_ID = "auditoria.detalles.solicitudes.consultas.ID";
	public static final String SOLICITUDES_CONSULTA = "auditoria.detalles.SOLICITUDES_CONSULTA";
	public static final String SOLICITUDES_CONSULTA_SOLICITANTE = "auditoria.detalles.SOLICITUDES_CONSULTA_SOLICITANTE";
	public static final String SOLICITUDES_CONSULTA_ACEPTADA = "auditoria.detalles.SOLICITUDES_CONSULTA_DETALLE_ACEPTADO";
	public static final String SOLICITUDES_CONSULTA_DENEGADA = "auditoria.detalles.SOLICITUDES_CONSULTA_DETALLE_DENEGADO";
	public static final String SOLICITUDES_CONSULTA_RESERVADA = "auditoria.detalles.SOLICITUDES_CONSULTA_DETALLE_RESERVADO";
	public static final String SOLICITUDES_CONSULTA_DETALLE_ACEPTADO = "auditoria.detalles.SOLICITUDES_CONSULTA_ACEPTADO";
	public static final String SOLICITUDES_CONSULTA_DETALLE_DENEGADO = "auditoria.detalles.SOLICITUDES_CONSULTA_DENEGADO";
	public static final String SOLICITUDES_CONSULTA_DETALLE_RESERVADO = "auditoria.detalles.SOLICITUDES_CONSULTA_RESERVADO";

	public static final String SOLICITUDES_CODIGO_CONSULTA = "archigest.archivo.consultas.busqueda.codigo";
	public static final String SOLICITUDES_ORGANO_CONSULTA = "archigest.archivo.consultas.busqueda.organo";
	public static final String SOLICITUDES_SOLICITANTE_CONSULTA = "archigest.archivo.consultas.busqueda.solicitante";
	public static final String SOLICITUDES_ESTADOS_CONSULTA = "archigest.archivo.consultas.busqueda.estado";

	public static final String SOLICITUDES_DETALLE_ID = "auditoria.detalles.SOLICITUDES_DETALLE_ID";
	public static final String SOLICITUDES_DETALLE_NUMEXP = "auditoria.detalles.SOLICITUDES_DETALLE_NUMEXP";

	// =============================================
	// FONDOS DOCUMENTALES
	// =============================================
	public static final String FONDO_CODIGO = "archigest.archivo.cf.codigo";
	public static final String FONDO_DENOMINACION = "archigest.archivo.cf.denominacion";

	public static final String FONDO_DEFINICION_FINAL = "archigest.archivo.cf.definicion.final";
	public static final String FONDO_DOCS_BASICOS_INICIAL = "archigest.archivo.cf.documentosbasicos.inicial";
	public static final String FONDO_DOCS_BASICOS_FINAL = "archigest.archivo.cf.documentosbasicos.final";
	public static final String FONDO_NORMATIVA_INICIAL = "archigest.archivo.cf.normativa.inicial";
	public static final String FONDO_NORMATIVA_FINAL = "Normativa Final";
	public static final String FONDO_CODIDO_INICIAL = "archigest.archivo.cf.codigo.inicial";
	public static final String FONDO_TITULO = "archigest.archivo.cf.denominacion";
	public static final String FONDO_FEXTREMAFINAL = "archigest.archivo.cf.fExtrema.final";
	public static final String FONDO_FEXTREMAINICIAL = "archigest.archivo.cf.fExtrema.inicial";
	public static final String FONDO_CODIGO_REF_FONDO = "archigest.archivo.cf.codReferenciaFondo";
	public static final String FONDO_DENOMINACION_INICIAL = "archigest.archivo.cf.denominacion.inicial";
	public static final String FONDO_CODIGO_MODIFICADO = "archigest.archivo.cf.codigo.modificado";
	public static final String FONDO_DENOMINACION_FINAL = "archigest.archivo.cf.denominacion.final";
	public static final String FONDO_TIPO_ELEMENTO = "archigest.archivo.cf.tipo.elemento";
	public static final String FONDO_CODIGO_REF_ASCENDENTE_JERARQUICO = "archigest.archivo.cf.codReferenciaPadre";
	public static final String FONDO_NOTIFICACION_ADMINISTRATIVA = "archigest.archivo.cf.notificacion.administrativa";
	public static final String FONDO_ESTADO_INICIAL = "archigest.archivo.cf.estado.inicial";
	public static final String FONDO_ESTADO_FINAL = "archigest.archivo.cf.estado.final";
	public static final String FONDO_ACCION_TOMADA = "archigest.archivo.auditoria.accion";
	public static final String FONDO_MOTIVO = "archigest.archivo.cf.motivoSolicitud";
	public static final String FONDO_USR_GESTOR_INICIAL = "archigest.archivo.usuarioGestor.inicial";
	public static final String FONDO_ID_USR_GESTOR_INICIAL = "archigest.archivo.id.usuarioGestor.inicial";
	public static final String FONDO_USR_GESTOR_FINAL = "archigest.archivo.usuarioGestor.final";
	public static final String FONDO_ID_USR_GESTOR_FINAL = "archigest.archivo.id.usuarioGestor.final";
	public static final String FONDO_PROCEDIMIENTO_NOMBRE = "archigest.archivo.cf.procedimiento.nombre";
	public static final String FONDO_PRODUCTORES_NOMBRE = "archigest.archivo.cf.productoresDeLaSerie";
	public static final String FONDO_PRODUCTORES_IDENTIFICADOR = "archigest.archivo.cf.identificadores.productores";
	public static final String FONDO_CODIGO_REF_NUEVO_ASCENDENTE_JERARQUICO = "archigest.archivo.cf.codReferenciaPadre.final";
	public static final String FONDO_CODIGO_MOVIDO = "archigest.archivo.cf.cod.elemento.movido";

	public static final String FONDO_CODIGO_INGRESO = "auditoria.detalles.FONDO_CODIGO_INGRESO";
	public static final String FONDO_UDOCS_DE_FRACCION_SERIE = "auditoria.detalles.FONDO_UDOCS_DE_FRACCION_SERIE";
	public static final String FONDO_SERIE_ACTUALIZADA = "auditoria.detalles.FONDO_SERIE_ACTUALIZADA";
	public static final String FONDO_FRACCION_DE_SERIE_ELIMINADA = "auditoria.detalles.FONDO_FRACCION_DE_SERIE_ELIMINADA";
	public static final String FONDO_ID_FRACCION_SERIE = "auditoria.detalles.FONDO_ID_FRACCION_SERIE";
	public static final String FONDO_IDELIMINACION = "auditoria.detalles.FONDO_IDELIMINACION";
	public static final String FONDO_CODIGO_REFERENCIA = "auditoria.detalles.FONDO_CODIGO_REFERENCIA";

	// =============================================
	// USUARIOS
	// =============================================
	public static final String USUARIOS_ID = "USUARIO_ID";
	public static final String USUARIOS_NOMBRE = "USUARIO_NOMBRE";
	public static final String USUARIOS_APELLIDOS = "USUARIO_APELLIDOS";
	public static final String USUARIOS_NOMBRE_ROL = "NOMBRE_ROL";
	public static final String USUARIOS_NOMBRE_GRUPO = "NOMBRE_GRUPO";

	// =============================================
	// DESCRIPCION
	// =============================================
	public static final String DESCRIPCION_BUSQUEDA_CODIGO_REFERENCIA = "archigest.archivo.descripcion.campo.codReferencia";
	public static final String DESCRIPCION_BUSQUEDA_TITULO = "archigest.archivo.descripcion.campo.titulo";
	public static final String DESCRIPCION_BUSQUEDA_TEXTO = "archigest.archivo.busqueda.form.texto";
	public static final String DESCRIPCION_BUSQUEDA_FECHA_INICIO = Constants.ETIQUETA_BUSQUEDA_FECHA_INICIO;
	public static final String DESCRIPCION_BUSQUEDA_FECHA_FIN = Constants.ETIQUETA_BUSQUEDA_FECHA_FINAL;
	public static final String DESCRIPCION_BUSQUEDA_AMBITO = "archigest.archivo.cf.seleccionAmbito";
	public static final String DESCRIPCION_BUSQUEDA_NUM_EXPEDIENTE = "archigest.archivo.busqueda.form.numeroExpediente";

	public static final String DESCRIPCION_BUSQUEDA_DESCRIPCION = "archigest.archivo.busqueda.aut.form.descripcion";
	public static final String DESCRIPCION_BUSQUEDA_DESCRIPCION_TODAS = "archigest.archivo.busqueda.aut.form.descripcion.todas";
	public static final String DESCRIPCION_BUSQUEDA_DESCRIPCION_CON = "archigest.archivo.busqueda.aut.form.descripcion.conDescripcion";
	public static final String DESCRIPCION_BUSQUEDA_DESCRIPCION_SIN = "archigest.archivo.busqueda.aut.form.descripcion.sinDescripcion";

	public static final String DESCRIPCION_BUSQUEDA_DATO_NUMERICO = "archigest.archivo.busqueda.aut.form.numero";
	public static final String DESCRIPCION_BUSQUEDA_DATO_NUMERICO_LONGITUD = "archigest.archivo.medida.longitud";
	public static final String DESCRIPCION_BUSQUEDA_DATO_NUMERICO_PESO = "archigest.archivo.medida.peso";
	public static final String DESCRIPCION_BUSQUEDA_DATO_NUMERICO_VOLUMEN = "archigest.archivo.medida.volumen";

	public static final String DESCRIPCION_BUSQUEDA_CALIFICADOR = "archigest.archivo.busqueda.form.calificadores";
	public static final String DESCRIPCION_BUSQUEDA_FONDO = "archigest.archivo.busqueda.form.fondo";
	public static final String DESCRIPCION_BUSQUEDA_NIVELES = "archigest.archivo.busqueda.form.nivelesDescripcion";
	public static final String DESCRIPCION_BUSQUEDA_DESCRIPTORES = "archigest.archivo.descripcion.listasDescriptoras.form.descriptores.caption";
	public static final String DESCRIPCION_BUSQUEDA_NOMBRE = "archigest.archivo.busqueda.aut.form.nombre";
	public static final String DESCRIPCION_BUSQUEDA_LISTAS_DESCRIPTORAS = "archigest.archivo.busqueda.aut.form.listasDescriptoras";
	public static final String DESCRIPCION_CAMPO_ID_ELEMENTO = "auditoria.detalles.DESCRIPCION_ID_ELEMENTO";
	public static final String DESCRIPCION_CAMPO_ID = "auditoria.detalles.DESCRIPCION_ID";
	public static final String DESCRIPCION_CAMPO_ORDEN = "auditoria.detalles.DESCRIPCION_ORDEN";
	public static final String DESCRIPCION_CAMPO_TIMESTAMP = "archigest.archivo.auditoria.busqueda.form.fecha";
	public static final String DESCRIPCION_CAMPO_TIPO = "archigest.archivo.descripcion.camposDato.form.tipo";
	public static final String DESCRIPCION_CAMPO_VALOR = "archigest.archivo.descripcion.textoTablasValidacion.valor";
	public static final String DESCRIPCION_CAMPO_VALOR_ANTERIOR = " auditoria.detalles.DESCRIPCION_VALOR_ANTERIOR";
	public static final String DESCRIPCION_CAMPO_FECHA_INICIO = Constants.ETIQUETA_BUSQUEDA_FECHA_INICIO;
	public static final String DESCRIPCION_CAMPO_FECHA_FIN = Constants.ETIQUETA_BUSQUEDA_FECHA_FINAL;
	public static final String DESCRIPCION_CAMPO_CALIFICADOR = "archigest.archivo.busqueda.form.calificadores";
	public static final String DESCRIPCION_CAMPO_CALIFICADOR_ANTERIOR = "auditoria.detalles.DESCRIPCION_CALIFICADOR_ANTERIOR";
	public static final String DESCRIPCION_CAMPO_FORMATO = "auditoria.detalles.DESCRIPCION_FORMATO";
	public static final String DESCRIPCION_CAMPO_FORMATO_ANTERIOR = "auditoria.detalles.DESCRIPCION_FORMATO_ANTERIOR";
	public static final String DESCRIPCION_CAMPO_SEPARADOR = "auditoria.detalles.DESCRIPCION_SEPARADOR";
	public static final String DESCRIPCION_CAMPO_SEPARADOR_ANTERIOR = "auditoria.detalles.DESCRIPCION_SEPARADOR_ANTERIOR";
	public static final String DESCRIPCION_CAMPO_TIPO_MEDIDA = "auditoria.detalles.DESCRIPCION_TIPO_MEDIDA";
	public static final String DESCRIPCION_CAMPO_TIPO_MEDIDA_ANTERIOR = "auditoria.detalles.DESCRIPCION_TIPO_MEDIDA_ANTERIOR";
	public static final String DESCRIPCION_CAMPO_UNIDAD_MEDIDA = "auditoria.detalles.DESCRIPCION_UNIDAD_MEDIDA";
	public static final String DESCRIPCION_CAMPO_UNIDAD_MEDIDA_ANTERIOR = "auditoria.detalles.DESCRIPCION_UNIDAD_MEDIDA_ANTERIOR";
	public static final String DESCRIPCION_CAMPO_ID_OBJ_REF = "auditoria.detalles.ID_OBJ_REFERENCIA";
	public static final String DESCRIPCION_CAMPO_ID_OBJ_REF_ANTERIOR = "auditoria.detalles.ID_OBJ_REFERENCIA_ANTERIOR";
	public static final String DESCRIPCION_CAMPO_TIPO_OBJ_REF = "auditoria.detalles.TIPO_OBJ_REFERENCIA";
	public static final String DESCRIPCION_CAMPO_TIPO_OBJ_REF_ANTERIOR = "auditoria.detalles.TIPO_OBJ_REFERENCIA_ANTERIOR";
	public static final String DESCRIPCION_CAMPO_NOMBRE = "archigest.archivo.descripcion.fichas.form.nombre";
	public static final String DESCRIPCION_CAMPO_TIPO_NORMA = "archigest.archivo.descripcion.fichas.form.tipoNorma";
	public static final String DESCRIPCION_CAMPO_ELEMENTO_APLICADO = "archigest.archivo.descripcion.fichas.form.elementoAplica";
	public static final String DESCRIPCION_CAMPO_DESCRIPCION = "archigest.archivo.descripcion.fichas.form.descripcion";
	public static final String DESCRIPCION_CAMPO_DEFINICION = "archigest.archivo.descripcion.fichas.form.definicion";
	public static final String DESCRIPCION_CAMPO_AREA = "archigest.archivo.descripcion.camposDato.form.area";
	public static final String DESCRIPCION_CAMPO_ETIQUETA = "archigest.archivo.descripcion.camposDato.form.etiquetaXml";
	public static final String DESCRIPCION_CAMPO_TABLA_PADRE = "archigest.archivo.descripcion.campoTblPadre";
	public static final String DESCRIPCION_CAMPO_NUM_EXP = "archigest.archivo.descripcion.campo.numExp";
	public static final String DESCRIPCION_CAMPO_NIVEL_DESCRIPCION = "archigest.archivo.descripcion.campo.nivelDescripcion";
	public static final String DESCRIPCION_REEMPLAZO_CAMPO_NOMBRE = "archigest.descripcion.reeemplazo.auditoria.campo";
	public static final String DESCRIPCION_REEMPLAZO_VALOR_ANTERIOR = "archigest.descripcion.reeemplazo.auditoria.valorAnterior";
	public static final String DESCRIPCION_REEMPLAZO_NUEVO_VALOR = "archigest.descripcion.reeemplazo.auditoria.nuevoValor";

	public static final String DESCRIPCION_UNIFICAR_DESCRIPTORES_DESCRIPTOR_ORIGEN = "archigest.archivo.descripcion.descriptor.origen";
	public static final String DESCRIPCION_UNIFICAR_DESCRIPTORES_DESCRIPTOR_DESTINO = "archigest.archivo.descripcion.descriptor.destino";

	public static final String DESCRIPCION_NOMBRE_LISTA_DESCRIPTORES = "auditoria.detalles.NOMBRE_LISTA_DESCRIPTORES";
	public static final String DESCRIPCION_NOMBRE_DESCRIPTOR = "auditoria.detalles.NOMBRE_DESCRIPTOR";
	public static final String DESCRIPCION_NOMBRE_LISTA_VALORES = "auditoria.detalles.NOMBRE_LISTA_VALORES";
	public static final String DESCRIPCION_VALOR_VALIDACION = "auditoria.detalles.VALOR_VALIDACION";

	// =============================================
	// DOCUMENTOS VITALES
	// =============================================
	public static final String DOCUMENTOS_VITALES_ID_BD_TERCEROS = "ID_DB_TERCEROS";
	public static final String DOCUMENTOS_VITALES_ID_BD_TERCEROS_ANTERIOR = "ID_DB_TERCEROS_ANTERIOR";
	public static final String DOCUMENTOS_VITALES_NUM_IDENTIDAD = "NUM_IDENTIDAD";
	public static final String DOCUMENTOS_VITALES_NUM_IDENTIDAD_ANTERIOR = "NUM_IDENTIDAD_ANTERIOR";
	public static final String DOCUMENTOS_VITALES_IDENTIDAD = "IDENTIDAD";
	public static final String DOCUMENTOS_VITALES_IDENTIDAD_ANTERIOR = "IDENTIDAD_ANTERIOR";
	public static final String DOCUMENTOS_VITALES_ID_TIPO = "ID_TIPO_DOCUMENTO_VITAL";
	public static final String DOCUMENTOS_VITALES_ID_TIPO_ANTERIOR = "ID_TIPO_DOCUMENTO_VITAL_ANTERIOR";
	public static final String DOCUMENTOS_VITALES_NOMBRE_TIPO = "NOMBRE_TIPO_DOCUMENTO_VITAL";
	public static final String DOCUMENTOS_VITALES_NOMBRE_TIPO_ANTERIOR = "NOMBRE_TIPO_DOCUMENTO_VITAL_ANTERIOR";
	public static final String DOCUMENTOS_VITALES_DESCRIPCION_TIPO = "DESCRIPCION_TIPO_DOCUMENTO_VITAL";
	public static final String DOCUMENTOS_VITALES_DESCRIPCION_TIPO_ANTERIOR = "DESCRIPCION_TIPO_DOCUMENTO_VITAL_ANTERIOR";
	public static final String DOCUMENTOS_VITALES_FECHA_CAD = "FECHA_CADUCIDAD";
	public static final String DOCUMENTOS_VITALES_FECHA_CAD_ANTERIOR = "FECHA_CADUCIDAD_ANTERIOR";
	public static final String DOCUMENTOS_VITALES_ESTADO = "ESTADO";
	public static final String DOCUMENTOS_VITALES_FECHA_CR = "FECHA_CREACION";
	public static final String DOCUMENTOS_VITALES_ID_USUARIO_CR = "ID_USUARIO_CREACION";
	public static final String DOCUMENTOS_VITALES_FECHA_VIG = "FECHA_VIGENCIA";
	public static final String DOCUMENTOS_VITALES_ID_USUARIO_VIG = "ID_USUARIO_VIGENCIA";
	public static final String DOCUMENTOS_VITALES_ID_FICH = "ID_FICHERO";
	public static final String DOCUMENTOS_VITALES_TAMANO_FICH = "TAMAÑO_FICHERO";
	public static final String DOCUMENTOS_VITALES_NOMBRE_FICH = "NOMBRE_FICHERO";
	public static final String DOCUMENTOS_VITALES_EXT_FICH = "EXT_FICHERO";
	public static final String DOCUMENTOS_VITALES_OBSERVACIONES = "OBSERVACIONES";
	public static final String DOCUMENTOS_VITALES_OBSERVACIONES_ANTERIOR = "OBSERVACIONES_ANTERIOR";

	// =============================================
	// DOCUMENTOS ELECTRONICOS
	// =============================================
	public static final String DOCUMENTOS_ELECTRONICOS_NOMBRE_CLASIFICADOR = "NOMBRE_CLASIFICADOR";
	public static final String DOCUMENTOS_ELECTRONICOS_NOMBRE_DOCUMENTO = "NOMBRE_DOCUMENTO";
	public static final String DOCUMENTOS_ELECTRONICOS_ID_ELEMENTO_CF = "ID_ELEMENTO_CF";
	public static final String DOCUMENTOS_ELECTRONICOS_ID_DESCRIPTOR = "ID_DESCRIPTOR";
	public static final String DOCUMENTOS_ELECTRONICOS_ID_CLF_PADRE = "ID_CLF_PADRE";
	public static final String DOCUMENTOS_ELECTRONICOS_MARCAS = "MARCAS";
	public static final String DOCUMENTOS_ELECTRONICOS_ESTADO = "ESTADO";
	public static final String DOCUMENTOS_ELECTRONICOS_TAMANO_FICHERO = "TAMANO_FICHERO";
	public static final String DOCUMENTOS_ELECTRONICOS_NOMBRE_ORIGINAL_FICHERO = "NOMBRE_ORIGINAL_FICHERO";
	public static final String DOCUMENTOS_ELECTRONICOS_EXTENSION_FICHERO = "EXTENSION_FICHERO";
	public static final String DOCUMENTOS_ELECTRONICOS_ID_FICHERO = "ID_FICHERO";
	public static final String DOCUMENTOS_ELECTRONICOS_DESCRIPCION = "DESCRIPCION";
	public static final String DOCUMENTOS_ELECTRONICOS_TITULO_OBJETO = "TITULO_OBJETO";
	public static final String DOCUMENTOS_ELECTRONICOS_USUARIO_CAPTURA = "DOCUMENTOS_ELECTRONICOS_USUARIO_CAPTURA";
	public static final String DOCUMENTOS_ELECTRONICOS_ESTADO_ANTERIOR = "DOCUMENTOS_ELECTRONICOS_ESTADO_ANTERIOR";
	public static final String DOCUMENTOS_ELECTRONICOS_NUEVO_ESTADO = "DOCUMENTOS_ELECTRONICOS_NUEVO_ESTADO";

	// =============================================
	// AUDITORIA
	// =============================================
	public static final String AUDITORIA_NIVEL_LOG = "archigest.archivo.auditoria.nivelCriticidad";
	public static final String AUDITORIA_GRUPO = "archigest.archivo.auditoria.busqueda.form.grupo";
	public static final String AUDITORIA_ACTION = "archigest.archivo.auditoria.accion";
	public static final String AUDITORIA_MODULO = "archigest.archivo.auditoria.modulo";
	public static final String AUDITORIA_IP = "archigest.archivo.auditoria.ip";
	public static final String AUDITORIA_FECHA_INI = "auditoria.detalles.FECHA_INICIO";
	public static final String AUDITORIA_FECHA_FIN = "auditoria.detalles.FECHA_FIN";

	// =============================================
	// TRANSFERENCIAS
	// =============================================
	public static final String TRANSFERENCIAS_CODIGO_PREVISION = "auditoria.detalles.TRANSFERENCIAS_CODIGO_PREVISION";
	public static final String TRANSFERENCIAS_FONDO_PREVISION = "auditoria.detalles.TRANSFERENCIAS_FONDO_PREVISION";
	public static final String TRANSFERENCIAS_FONDO_PREVISION_PREVIO = "auditoria.detalles.TRANSFERENCIAS_FONDO_PREVISION_PREVIO";
	public static final String TRANSFERENCIAS_LINEA_DETALLE_PREVISION = "auditoria.detalles.TRANSFERENCIAS_LINEA_DETALLE_PREVISION";
	public static final String TRANSFERENCIAS_NUMERO_LINEA_DETALLE_PREVISION = "auditoria.detalles.TRANSFERENCIAS_NUMERO_LINEA_DETALLE_PREVISION";
	public static final String TRANSFERENCIAS_PROCEDIMIENTO_DETALLE_PREVISION = "auditoria.detalles.TRANSFERENCIAS_PROCEDIMIENTO_DETALLE_PREVISION";
	public static final String TRANSFERENCIAS_SERIE_DETALLE_PREVISION = "auditoria.detalles.TRANSFERENCIAS_SERIE_DETALLE_PREVISION";
	public static final String TRANSFERENCIAS_ANO_INICIO_DETALLE_PREVISION = "auditoria.detalles.TRANSFERENCIAS_ANO_INICIO_DETALLE_PREVISION";
	public static final String TRANSFERENCIAS_ANO_FIN_DETALLE_PREVISION = "auditoria.detalles.TRANSFERENCIAS_ANO_FIN_DETALLE_PREVISION";
	public static final String TRANSFERENCIAS_NUMERO_UIS_PREVISION = "auditoria.detalles.TRANSFERENCIAS_NUMERO_UIS_PREVISION";
	public static final String TRANSFERENCIAS_NUMERO_UIS_DETALLE_PREVISION = "auditoria.detalles.TRANSFERENCIAS_NUMERO_UIS_DETALLE_PREVISION";
	public static final String TRANSFERENCIAS_OBSERVACIONES_PREVISION = "auditoria.detalles.TRANSFERENCIAS_OBSERVACIONES_PREVISION";

	public static final String TRANSFERENCIAS_FECHA_INICIO_CALENDARIO = "auditoria.detalles.TRANSFERENCIAS_FECHA_INICIO_CALENDARIO";
	public static final String TRANSFERENCIAS_FECHA_FIN_CALENDARIO = "auditoria.detalles.TRANSFERENCIAS_FECHA_FIN_CALENDARIO";
	public static final String TRANSFERENCIAS_MOTIVO_RECHAZO = "auditoria.detalles.TRANSFERENCIAS_MOTIVO_RECHAZO";

	// nuevos campos añadidos para el detalle con los datos de las busquedas de
	// previsiones
	public static final String TRANSFERENCIAS_ORGANO_BUSQUEDA_PREVISION = "archigest.archivo.transferencias.previsiones.busqueda.organo";
	public static final String TRANSFERENCIAS_USUARIO_BUSQUEDA_PREVISION = "archigest.archivo.transferencias.previsiones.busqueda.usuario";
	public static final String TRANSFERENCIAS_TIPO_BUSQUEDA_PREVISION = "archigest.archivo.transferencias.previsiones.busqueda.tipo";
	public static final String TRANSFERENCIAS_ANIO_BUSQUEDA_PREVISION = "archigest.archivo.transferencias.previsiones.busqueda.anio";
	public static final String TRANSFERENCIAS_ESTADOS_BUSQUEDA_PREVISION = "archigest.archivo.transferencias.previsiones.busqueda.estado";

	// RELACIONES
	public static final String TRANSFERENCIAS_CODIGO_RELACION = "auditoria.detalles.TRANSFERENCIAS_CODIGO_RELACION";
	public static final String TRANSFERENCIAS_REGISTRO_ENTRADA_RELACION = "auditoria.detalles.TRANSFERENCIAS_REGISTRO_ENTRADA_RELACION";
	public static final String TRANSFERENCIAS_PRIMERA_SIGNATURA_RELACION = "auditoria.detalles.TRANSFERENCIAS_PRIMERA_SIGNATURA_RELACION";
	public static final String TRANSFERENCIAS_ULTIMA_SIGNATURA_RELACION = "auditoria.detalles.TRANSFERENCIAS_ULTIMA_SIGNATURA_RELACION";
	public static final String TRANSFERENCIAS_GESTOR_RELACION_ARCHIVO = "auditoria.detalles.TRANSFERENCIAS_GESTOR_RELACION_ARCHIVO";
	public static final String TRANSFERENCIAS_UBICACION_RELACION = "auditoria.detalles.TRANSFERENCIAS_UBICACION_RELACION";
	public static final String TRANSFERENCIAS_DEPOSITO_RESERVA_RELACION = "auditoria.detalles.TRANSFERENCIAS_DEPOSITO_RESERVA_RELACION";
	public static final String TRANSFERENCIAS_ID_UNIDAD_DOCUMENTAL = "auditoria.detalles.TRANSFERENCIAS_ID_UNIDAD_DOCUMENTAL";
	public static final String TRANSFERENCIAS_ASUNTO_UNIDAD_DOCUMENTAL = "auditoria.detalles.TRANSFERENCIAS_ASUNTO_UNIDAD_DOCUMENTAL";
	public static final String TRANSFERENCIAS_NUMEXP_UNIDAD_DOCUMENTAL = "auditoria.detalles.TRANSFERENCIAS_NUMEXP_UNIDAD_DOCUMENTAL";
	public static final String TRANSFERENCIAS_ELIMINAR_UNIDAD_DOCUMENTAL = "auditoria.detalles.TRANSFERENCIAS_ELIMINAR_UNIDAD_DOCUMENTAL";
	public static final String TRANSFERENCIAS_BLOQUEAR_UNIDAD_DOCUMENTAL = "auditoria.detalles.TRANSFERENCIAS_BLOQUEAR_UNIDAD_DOCUMENTAL";
	public static final String TRANSFERENCIAS_DESBLOQUEAR_UNIDAD_DOCUMENTAL = "auditoria.detalles.TRANSFERENCIAS_DESBLOQUEAR_UNIDAD_DOCUMENTAL";
	public static final String TRANSFERENCIAS_NUMERO_EXPEDIENTES_RELACION = "auditoria.detalles.TRANSFERENCIAS_NUMERO_EXPEDIENTES_RELACION";
	public static final String TRANSFERENCIAS_NUMERO_CAJAS_RELACION = "auditoria.detalles.TRANSFERENCIAS_NUMERO_CAJAS_RELACION";
	public static final String TRANSFERENCIAS_COTEJO_CON_ERRORES = "auditoria.detalles.TRANSFERENCIAS_COTEJO_CON_ERRORES";
	public static final String TRANSFERENCIAS_UDOC_CON_ERRORES = "auditoria.detalles.TRANSFERENCIAS_UDOC_CON_ERRORES";
	public static final String TRANSFERENCIAS_UDOCS_VALIDADAS = "auditoria.detalles.TRANSFERENCIAS_UDOCS_VALIDADAS";
	public static final String TRANSFERENCIAS_UI_COTEJADA = "auditoria.detalles.TRANSFERENCIAS_UI_COTEJADA";
	public static final String TRANSFERENCIAS_UI_NOTAS_COTEJO = "auditoria.detalles.TRANSFERENCIAS_UI_NOTAS_COTEJO";
	public static final String TRANSFERENCIAS_UI_DEVOLUCION_FISICA = "auditoria.detalles.TRANSFERENCIAS_UI_DEVOLUCION_FISICA";
	public static final String TRANSFERENCIAS_NUMERO_UDOCS_ELECTRONICAS = "auditoria.detalles.TRANSFERENCIAS_NUMERO_UDOCS_ELECTRONICAS";
	public static final String TRANSFERENCIAS_NUMERO_UNIDAD_INSTALACION = "auditoria.detalles.TRANSFERENCIAS_NUMERO_UNIDAD_INSTALACION";
	public static final String TRANSFERENCIAS_SIGNATURA_UNIDAD_INSTALACION = "auditoria.detalles.TRANSFERENCIAS_SIGNATURA_UNIDAD_INSTALACION";
	public static final String TRANSFERENCIAS_ID_UNIDAD_INSTALACION = "auditoria.detalles.TRANSFERENCIAS_ID_UNIDAD_INSTALACION";
	public static final String TRANSFERENCIAS_UINST_VALIDADAS = "auditoria.detalles.TRANSFERENCIAS_UINST_VALIDADAS";
	public static final String TRANSFERENCIAS_UDOCS_ELECTRONICOS_VALIDADAS = "auditoria.detalles.TRANSFERENCIAS_UDOCS_ELECTRONICOS_VALIDADAS";

	// nuevos campos añadidos para el detalle con los datos de las busquedas de
	// relaciones
	public static final String TRANSFERENCIAS_ORGANO_BUSQUEDA_RELACION = "archigest.archivo.transferencias.relaciones.busqueda.organo";
	public static final String TRANSFERENCIAS_GESTOR_OFICINA_BUSQUEDA_RELACION = "archigest.archivo.transferencias.relaciones.busqueda.gestorOficina";
	public static final String TRANSFERENCIAS_CODIGO_SERIE_BUSQUEDA_RELACION = "archigest.archivo.transferencias.relaciones.busqueda.Codserie";
	public static final String TRANSFERENCIAS_DENOMINACION_SERIE_BUSQUEDA_RELACION = "archigest.archivo.transferencias.relaciones.busqueda.serie";
	public static final String TRANSFERENCIAS_PROCEDIMIENTO_BUSQUEDA_RELACION = "archigest.archivo.transferencias.relaciones.busqueda.procedimiento";
	public static final String TRANSFERENCIAS_TIPO_BUSQUEDA_RELACION = "archigest.archivo.transferencias.relaciones.busqueda.tipo";
	public static final String TRANSFERENCIAS_ANIO_BUSQUEDA_RELACION = "archigest.archivo.transferencias.relaciones.busqueda.anio";
	public static final String TRANSFERENCIAS_ESTADOS_BUSQUEDA_RELACION = "archigest.archivo.transferencias.relaciones.busqueda.estado";

	public static final String TRANSFERENCIAS_ESTADO_RESERVA = "auditoria.detalles.TRANSFERENCIAS_ESTADO_RESERVA";

	public static final String TRANSFERENCIAS_TIPO_EDICION = "auditoria.detalles.TRANSFERENCIAS_TIPO_EDICION";
	public static final String TRANSFERENCIAS_TIPO_TRANSFERENCIA = "auditoria.detalles.TRANSFERENCIAS_TIPO_TRANSFERENCIA";
	public static final String TRANSFERENCIAS_GESTOR_ANTERIOR = "auditoria.detalles.TRANSFERENCIAS_GESTOR_ANTERIOR";
	public static final String TRANSFERENCIAS_GESTOR_NUEVO = "auditoria.detalles.TRANSFERENCIAS_GESTOR_NUEVO";

	// =============================================
	// VALORACION
	// =============================================
	public static final String VALORACION_COD_UDOC = "COD_UDOC";
	public static final String VALORACION_ID_VALORACION = "ID_VALORACION";
	public static final String VALORACION_COD_VALORACION = "COD_VALORACION";
	public static final String VALORACION_COD_SERIE = "COD_SERIE_VALORACION";
	public static final String VALORACION_RECHAZO_VALIDACION = "MOTIVO_RECHAZO_VALIDACION";
	public static final String VALORACION_RECHAZO_EVALUACION = "MOTIVO_RECHAZO_EVALUACION";
	public static final String VALORACION_FECHA_DICTAMEN = "FECHA_DICTAMEN";
	public static final String VALORACION_BOLETIN_DICTAMEN = "BOLETIN_DICTAMEN";
	public static final String VALORACION_FECHA_BOLETIN_DICTAMEN = "FECHA_BOLETIN_DICTAMEN";
	public static final String VALORACION_FECHA_RESOLUCION_DICTAMEN = "FECHA_RESOLUCION_DICTAMEN";
	public static final String VALORACION_MOTIVO_RECHAZO_DICTAMEN = "MOTIVO_RECHAZO_DICTAMEN";

	// =============================================
	// SELECCION
	// =============================================
	public static final String SELECCION_COD_UDOC = "COD_UDOC";
	public static final String SELECCION_ID_SELECCION = "ID_SELECCION";
	public static final String SELECCION_COD_SELECCION = "COD_SELECCION";
	public static final String SELECCION_COD_SERIE = "COD_SERIE_SELECCION";
	public static final String SELECCION_FECHA_EJECUCION = "FECHA_EJECUCION_SELECCION";
	public static final String SELECCION_FECHA_APROBACION = "FECHA_APROBACION_SELECCION";
	public static final String SELECCION_MOTIVO_RECHAZO = "MOTIVO_RECHAZO_SELECCION";
	public static final String SELECCION_FECHA_DESTRUCCION = "FECHA_DESTRUCCION_SELECCION";

	// =============================================
	// SALAS
	// =============================================
	public static final String SALAS_EDIFICIO_ID = "auditoria.detalles.SALAS_EDIFICIO_ID";
	public static final String SALAS_EDIFICIO_NOMBRE = "archigest.archivo.salas.edificio.nombre.label";
	public static final String SALAS_EDIFICIO_UBICACION = "archigest.archivo.salas.edificio.ubicacion.label";
	public static final String SALAS_EDIFICIO_IDARCHIVO = "auditoria.detalles.SALAS_EDIFICIO_IDARCHIVO";
	public static final String SALAS_EDIFICIO_NOMBREARCHIVO = "archigest.archivo.salas.edificio.archivo.label";

	public static final String SALAS_SALA_ID = "auditoria.detalles.SALAS_SALA_ID";
	public static final String SALAS_SALA_NOMBRE = "auditoria.detalles.salas.sala.nombre.label";
	public static final String SALAS_SALA_DESCRIPCION = "auditoria.detalles.salas.sala.descripcion.label";
	public static final String SALAS_SALA_IDEDIFICIO = "archigest.archivo.SALAS_SALA_IDEDIFICIO";
	public static final String SALAS_SALA_EQUIPOINFORMATICO = "auditoria.detalles.salas.sala.equipoinformatico.label";

	public static final String SALAS_MESA_ID = "auditoria.detalles.SALAS_MESA_ID";
	public static final String SALAS_MESA_CODIGO = "auditoria.detalles.salas.mesa.codigo.label";
	public static final String SALAS_MESA_NUM_ORDEN = "auditoria.detalles.salas.mesa.numorden.label";
	public static final String SALAS_MESA_IDSALA = "archigest.archivo.SALAS_MESA_IDSALA";
	public static final String SALAS_MESA_ESTADO = "auditoria.detalles.salas.mesa.estado.label";
	public static final String SALAS_MESA_FECHAESTADO = "auditoria.detalles.salas.mesa.fechaestado.label";
	public static final String SALAS_MESA_IDSCAUSR = "archigest.archivo.SALAS_MESA_IDSCAUSR";

	public static final String SALAS_USUARIO_ID = "auditoria.detalles.SALAS_ID_USUARIO";
	public static final String SALAS_USUARIO_TIPO_DOC_IDENTIFICACION = "archigest.archivo.tipodoc";
	public static final String SALAS_USUARIO_NUM_DOC_IDENTIFICACION = "archigest.archivo.numdoc";
	public static final String SALAS_USUARIO_NOMBRE = "archigest.archivo.nombre";
	public static final String SALAS_USUARIO_APELLIDOS = "archigest.archivo.apellidos";
	public static final String SALAS_USUARIO_NACIONALIDAD = "archigest.archivo.nacionalidad";
	public static final String SALAS_USUARIO_TELEFONOS = "archigest.archivo.telefonos";
	public static final String SALAS_USUARIO_EMAIL = "archigest.archivo.email";
	public static final String SALAS_USUARIO_DIRECCION = "archigest.archivo.direccion";
	public static final String SALAS_USUARIO_VIGENTE = "archigest.archivo.salas.usuario.vigente";
	public static final String SALAS_USUARIO_IDUSUARIO_EXTERNO = "auditoria.detalles.SALAS_ID_USUARIO_EXTERNO";

	public static final String SALAS_REGISTRO_ID = "auditoria.detalles.SALAS_REGISTRO_ID";
	public static final String SALAS_REGISTRO_ID_USRCSALA = "archigest.archivo.SALAS_REGISTRO_IDUSRCSALA";
	public static final String SALAS_REGISTRO_FECHA_ENTRADA = "auditoria.detalles.salas.registro.fechaentrada.label";
	public static final String SALAS_REGISTRO_USUARIO_NOMBRE_APELLIDOS = "auditoria.detalles.salas.registro.usuario.nombre.label";
	public static final String SALAS_REGISTRO_USUARIO_NUMDOC = "auditoria.detalles.salas.registro.usuario.numdoc.label";
	public static final String SALAS_REGISTRO_ARCHIVO = "archigest.archivo.SALAS_REGISTRO_IDARCHIVO";
	public static final String SALAS_REGISTRO_MESA_ASIGNADA = "auditoria.detalles.salas.registro.mesaasignada.label";

	public static final String FONDO_NUMERO_EXPEDIENTE = null;
}