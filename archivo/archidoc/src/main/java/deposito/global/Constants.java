/*
 * Created on 29-dic-2004
 *
 */
package deposito.global;

/**
 * @author ABELRL
 * 
 */
public class Constants {
	public final static String TIPO_ELEMENTO_KEY = "tipoelemento";
	public final static String LISTA_TIPO_PADRES_KEY = "listaTipoPadres";
	public final static String LISTA_DEPOSITOS_KEY = "listaDepositos";
	public final static String LISTA_ASIGNABLES_KEY = "listaAsignables";
	public final static String LISTA_FORMATOS_REGULARES_KEY = "listaFormatosRegulares";
	public final static String LISTA_FORMATOS_IRREGULARES_KEY = "listaFormatosIrregulares";
	public final static String LISTA_FORMATOS_KEY = "listaFormatos";
	public final static String LISTA_HUECOS_ORIGEN_KEY = "listaHuecosOrigen";
	public final static String LISTA_HUECOS_DESTINO_KEY = "listaHuecosDestino";
	public final static String WIZARD_ALTABEAN_KEY = "WizardAltaForm";
	public final static String LISTA_RESULTADO_KEY = "listaResultado";
	public final static String SALIR_RESULTADO_KEY = "salirresultado";
	public final static String DELIMITER_PARAMETER_FORMBEAN = "#";
	public final static String DELIMITER_IDS = ":";
	public final static String ERROR_GENERAL_MESSAGE = "errors.general";
	public final static String ERROR_INT = "errors.integer";
	public final static String ERROR_CERONUMBER = "errors.ceroNumber";
	public final static String ERROR_NEGATIVENUMBER = "errors.negativeNumber";

	public final static String ERROR_NOT_EMPTY_ELEMENT = "error.edicion.elementoNoVacio";
	public final static String ERROR_NUMERO_ORDEN_DUPLICADO = "error.edicion.numeroOrdenDuplicado";
	public final static String DELETE_ERROR_NOT_EMPTY_ELEMENT = "error.borrado.elementoNoVacio";
	public final static String ERROR_NOT_AVAILABLE_SPACE = "error.reubicacion.espacioInsuficiente";
	public final static String DELETE_ERROR_DEPOSITO_NOT_EMPTY = "error.borrado.depositoNoVacio";
	public static final String DELETE_ERROR_NOT_LAST_CHILD = "error.borrado.noUltimoElemento";
	public static final String CONTIENE_HUECOS_OCUPADOS_O_RESERVADOS = "error.borrado.contieneHuecosOcupadosOReservados";
	public static final String EL_HUECO_CONTIENE_UNIDADES_DOCS_DE_OTRO_FONDO = "error.deposito.noEsPosibleLaReubicacionDeUdocXElHuecoContieneUdocDeOtroFondo";
	public static final String NO_SE_PUEDE_REUBICAR_EN_LA_MISMA_UNIDADINSTALACION = "error.deposito.noSePuedeReubicarEnLaMismaUnidadDeInstalacion";
	public static final String LONGITUD_DE_ELEMENTO_MENOR_QUE_LONGITUD_FORMATO = "error.longitudElementoMenorQueLongitudFormato";
	public static final String NO_SE_PUEDE_ELIMINAR_EL_FORMATO = "error.deposito.noSePuedeEliminarElFormato";
	public static final String NO_SE_PUEDE_REUBICAR_EN_UI_NO_MULTIDOC = "error.deposito.noSePuedeReubicarUDocEnUINoMultidoc";
	public static final String NUMERACION_HUECO_EN_USO = "error.deposito.numeracionEnUso";
	public static final String NO_SE_PUEDE_RENUMERAR_HUECOS_ALFANUMERICOS = "error.deposito.noSePuedeRenumerarHuecos.alfanumericos";
	public static final String NO_SE_PUEDE_RENUMERAR_A_HUECOS_OCUPADOS = "error.deposito.noSePuedeRenumerarHuecos.ocupados";
	public static final String NO_SE_PUEDE_RENUMERAR_A_HUECOS_CREADOS = "error.deposito.noSePuedeRenumerarHuecos.creados";
	public static final String NO_SE_PUEDE_RENUMERAR_MAXIMO_NUMERACION_EXCEDIDO = "error.deposito.noSePuedeRenumerarHuecos.maximoExcedido";
	public static final String NO_SE_PUEDE_UBICAR_A_HUECOS_NO_LIBRES = "error.deposito.noSePuedeUbicarHuecosNoLibres";
	public static final String NO_SE_PUEDE_RESERVAR_A_HUECOS_NO_LIBRES = "error.deposito.noSePuedeReservarHuecosNoLibres";
	public static final String NO_SE_PUEDE_REUBICAR_A_HUECOS_NO_LIBRES = "error.deposito.noSePuedeReubicarHuecosNoLibres";
	public static final String NO_SE_PUEDE_RESERVAR_A_HUECOS_NO_LIBRES_SIGNATURA_ASOCIADA_A_HUECO = "error.deposito.noSePuedeReservarHuecosNoLibresSignaturaAsociadaHueco";
	public static final String COMPACTAR_HUECO_OCUPADO_PROCESO_COMPACTACION = "error.deposito.compactar.hueco.ocupado.proceso.compactacion";
	public static final String NO_SE_PUEDE_CAMBIAR_ESTADO_HUECO_MODIFICACION = "error.deposito.noSePuedeCambiarEstadoHuecoModificacion";
	public static final String SIGNATURA_HUECO_OCUPADA = "error.deposito.signaturaHuecoOcupada";

	public static final String LABEL_FRACCION_SERIE = "archigest.archivo.transferencias.fraccionSerie";
	public static final String LABEL_UNIDAD_DOCUMENTAL_SERIE = "archigest.archivo.unidadDoc";

	public static final String LABEL_PREFIJO_NIVEL_DOCUMENTAL = "archigest.archivo.nivel.tipo.";

	public static final String ERROR_DEPOSITO_ELEMENTO_NO_VACIO = "error.deposito.elementoNoVacio";
	public static final String ERROR_DEPOSITO_ELEMENTO_BORRADO = "error.deposito.elementoBorrado";

	public static final String ERROR_AL_MOSTRAR_ELEMENTO_ORGANIZACION_KEY = "organizacion.organo.error.mostrar.elemento";
	public static final String ERROR_ELEMENTO_NO_ENCONTRADO_KEY = "organizacion.organo.error.elemento.no.encontrado";
	public static final String ERROR_SIN_PERMISOS_GESTION_UDOCS_CONSERVADAS_KEY = "error.sin.permisos.gestion.udocs.conservadas";

}
