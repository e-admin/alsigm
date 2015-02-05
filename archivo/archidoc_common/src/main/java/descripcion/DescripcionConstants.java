package descripcion;


/**
 * Constantes para el módulo de Descripción.
 */
public class DescripcionConstants
{

	/**
	 * Obtiene la clave del parámetro.
	 * @param innerKey Parámetro.
	 * @return Clave del parámetro.
	 */
	private final static String getKey(String innerKey)
	{
		return new StringBuffer().append("DescripcionConstants_").append(innerKey).toString();
	}

	/** Parámetro que guardará los errores producidos. */
	public static final String ERRORES_KEY = getKey("ERRORES_KEY");

	/** Parámetro que guardará el formulario de búsqueda. */
	public static final String BUSQUEDA_FORM_KEY = getKey("BUSQUEDA_FORM");

	/** Parámetro que guardará la lista de niveles de descripción. */
	public static final String NIVELES_KEY = getKey("NIVELES");

	/** Parámetro que guardará la lista de fondos vigentes. */
	public static final String FONDOS_KEY = getKey("FONDOS");

	/** Parámetro que guardará la lista de elementos encontrados en la búsqueda. */
	public static final String ELEMENTOS_KEY = getKey("ELEMENTOS");

	/** Acciones Permitidas sobre los elementos de una busqueda */
	public static final String ACCIONES_PERMITIDAS_KEY = getKey("ACCIONES_PERMITIDAS_KEY");

	/** Parámetro que guardará el objeto descrito. */
	public static final String OBJETO_DESCRITO_KEY = getKey("OBJETO_DESCRITO_KEY");

	/** Parámetro que guardará el XML de la ficha. */
	public static final String FICHA_XML_KEY = getKey("FICHA_XML");

	/** Parámetro que guardará el XSL de la ficha. */
	public static final String FICHA_XSL_KEY = getKey("FICHA_XSL");

	/** Parámetro que guardará el modo de visualización de la ficha. */
	public static final String MODO_VISUALIZACION_KEY = getKey("MODO_VISU");

	/** Modo de visualización de solo lectura. */
	public static final String MODO_VISUALIZACION_SOLO_LECTURA = getKey("MODO_VISU_RO");

	/** Modo de visualización de edición. */
	public static final String MODO_VISUALIZACION_EDICION = getKey("MODO_VISU_RW");

	/** Parámetro que guardará las listas descriptoras. */
	public static final String LISTAS_DESCRIPTORAS_KEY = getKey("LISTAS_DESCR");

	/** Parámetro que guardará las listas descriptoras. */
	public static final String MAP_OPERADORES_KEY = getKey("MAP_OPERADORES_KEY");

	/** Parámetro que guardará las listas descriptoras. */
	public static final String MAX_LENGTH_KEY = getKey("MAX_LENGTH_KEY");

	/** Parámetro que guardará los descriptores de una lista. */
	public static final String DESCRIPTORES_LISTA_KEY = getKey("DESCR_LISTA");

	/** Parámetro que indicará si una lista tiene descriptores. */
	public static final String TIENE_DESCRIPTORES_KEY = getKey("TIENE_DESCRIPTORES_KEY");

	/** Parámetro que guardará la lista de autoridades encontradas en la búsqueda. */
	public static final String AUTORIDADES_KEY = getKey("AUTORIDADES");

	/** Parámetro que guardará el tipo de ficha. */
	public static final String TIPO_FICHA_KEY = getKey("TIPO_FICHA");

	/** Parámetro que guardará la lista de formatos aplicables a la ficha. */
	public static final String LISTA_FORMATOS_FICHA_KEY = getKey("LISTA_FORMATOS_FICHA_KEY");

	/** Ficha de tipo ISAD(G). */
	public static final String TIPO_FICHA_ISADG = getKey("TIPO_FICHA_ISADG");

	/** Ficha de tipo ISAAR. */
	public static final String TIPO_FICHA_ISAAR = getKey("TIPO_FICHA_ISAAR");

	/** Ficha de tipo UDOCRE. */
	public static final String TIPO_FICHA_UDOCRE = getKey("TIPO_FICHA_UDOCRE");

	/** Ficha de tipo UDOCFS. */
	public static final String TIPO_FICHA_UDOCFS = getKey("TIPO_FICHA_UDOCFS");

	/** Parámetro que guardará las tablas de validación. */
	public static final String TABLAS_VALIDACION_KEY = getKey("TABLAS_VALIDACION_KEY");

	/** Parámetro que guardará los valores de la tabla de validación. */
	public static final String VALORES_VALIDACION_KEY = getKey("VALORES_VALIDACION_KEY");

	/** Parámetro que guardará la lista de fichas de descripción. */
	public static final String FICHAS_DESCRIPCION_KEY = getKey("FICHAS_DESCRIPCION_KEY");

	/** Parámetro que guardará la lista de fichas de clasificadores documentales. */
	public static final String FICHAS_CLF_DOC_KEY = getKey("FICHAS_CLF_DOC_KEY");

	/** Parámetro que guardará las listas de volúmenes. */
	public static final String REPOSITORIOS_ECM_KEY = getKey("REPOSITORIOS_ECM_KEY");

	/** Parámetro que guardará las listas de control de acceso. */
	public static final String LISTAS_CONTROL_ACCESO_KEY = getKey("LISTAS_CONTROL_ACCESO_KEY");

	/** Parámetro que indica si hay que mostrar el botón de generación de automáticos. */
	public static final String MOSTRAR_BOTON_AUTOMATICOS_KEY = getKey("MOSTRAR_BOTON_AUTOMATICOS_KEY");

	/** Parámetro que indica si se trata de selección múltiple. */
	public static final String SELECCION_MULTIPLE_KEY = getKey("SELECCION_MULTIPLE_KEY");

	/** Parámetro que guardará el tipo de normas de las fichas. */
	public static final String LISTA_TIPO_NORMAS_KEY = getKey("LISTA_TIPO_NORMAS_KEY");

	/** Parámetro que guardará el tipo de niveles. */
	public static final String LISTA_TIPO_NIVELES_KEY = getKey("LISTA_TIPO_NIVELES_KEY");

	/** Parámetro que guardará el nombre de los formatos de las fichas. */
	public static final String LISTA_FORMATO_NOMBRE_KEY = getKey("LISTA_FORMATO_NOMBRE_KEY");

	/** Parámetro que guardará los tipos de los formatos de las fichas. */
	public static final String LISTA_TIPO_FORMATO_KEY = getKey("LISTA_TIPO_FORMATO_KEY");

	/** Parámetro que guardará los niveles de accesos de los formatos de las fichas. */
	public static final String LISTA_NIVEL_ACCESO_KEY = getKey("LISTA_NIVEL_ACCESO_KEY");

	/** Parámetro que guardará la lista de areas. */
	public static final String LISTA_AREAS_KEY=getKey("LISTA_AREAS_KEY");

	/** Parámetro que guardará la lista de campos. */
	public static final String LISTA_TIPOS_CAMPO_KEY=getKey("LISTA_TIPOS_CAMPO_KEY");

	/** Parámetro que guardará la lista de campos de entidad. */
	public static final String LISTA_TIPOS_CAMPO_ENTIDAD_KEY=getKey("LISTA_TIPOS_CAMPO_ENTIDAD_KEY");

	/** Parámetro que guardará la lista de campos de dato. */
	public static final String LISTA_CAMPOS_DATO_KEY=getKey("LISTA_CAMPOS_DATO_KEY");

	public static final String LISTA_IDS_ELEMENTOS_CF = getKey("LISTA_IDS_ELEMENTOS_CF");

	public static final String LISTA_DESCRIPTORES = getKey("MAP_DESCRIPTORES");

	public static final String LISTA_DESCRIPTORES_CON_DOCUMENTOS = getKey("LISTA_DESCRIPTORES_CON_DOCUMENTOS");
	public static final String LISTA_DESCRIPTORES_CON_FICHA = getKey("LISTA_DESCRIPTORES_CON_FICHA");

	public static final String LISTA_DESCRIPTORES_A_REEMPLAZAR = getKey("LISTA_DESCRIPTORES_A_REEMPLAZAR");

	public static final String LISTA_DESCRIPTOR_PRINCIPAL = getKey("LISTA_DESCRIPTOR_PRINCIPAL");
	public static final String LISTA_DESCRIPTORES_A_UNIFICAR = getKey("LISTA_DESCRIPTORES_A_UNIFICAR");

	public static final String MAP_ELEMENTOS_CF_VISITADOS = getKey("MAP_ELEMENTOS_CF_VISITADOS");

	public static final String NOT_IS_POSTBACK = getKey("NOT_IS_POSTBACK");

	public static final String CAMPO_TABLA_USA_AREA="archigest.archivo.descripcion.areas.form.delete.campoTabla";
	public static final String CAMPO_DATO_USA_AREA="archigest.archivo.descripcion.areas.form.delete.campoDato";
	public static final String FICHA_USA_AREA="archigest.archivo.descripcion.areas.form.delete.ficha";
	public static final String DESCRIPCION_CAMPOSDATO_FORM_DELETE_FICHA="archigest.archivo.descripcion.camposDato.form.delete.ficha";
	public static final String DESCRIPCION_CAMPOSTABLA_FORM_DELETE_FICHA="archigest.archivo.descripcion.camposTabla.form.delete.ficha";
	public static final String DESCRIPCION_OBJETO_FORM_DELETE_FICHA="archigest.archivo.descripcion.objeto.form.delete.ficha";

	public static final String DESCRIPCION_BUSQUEDA_AUT_FECHA="archigest.archivo.busqueda.aut.form.fecha";

	public static final String DESCRIPCION_NOMBRE_DESCRIPTOR="archigest.archivo.descripcion.descriptor.form.nombre";
	public static final String DESCRIPCION_ESTADO_DESCRIPTOR="archigest.archivo.descripcion.descriptor.form.estado";
	public static final String DESCRIPCION_FICHAS_DESCR_DESCRIPTOR="archigest.archivo.descripcion.descriptor.form.fichasDescr";
	public static final String DESCRIPCION_NIVEL_ACCESO_DESCRIPTOR="archigest.archivo.descripcion.descriptor.form.nivelAcceso";
	public static final String DESCRIPCION_LISTA_CONTROL_ACCESO_DESCRIPTOR="archigest.archivo.descripcion.descriptor.form.listaControlAcceso";
	public static final String DESCRIPCION_LISTADESCRIPTORAS_NOMBRE="archigest.archivo.descripcion.listasDescriptoras.form.nombre";
	public static final String DESCRIPCION_LISTADESCRIPTORAS_TIPO="archigest.archivo.descripcion.listasDescriptoras.form.tipo";
	public static final String DESCRIPCION_LISTADESCRIPTORAS_TIPO_DESCRIPTOR="archigest.archivo.descripcion.listasDescriptoras.form.tipoDescriptor";
	public static final String DESCRIPCION_LISTADESCRIPTORAS_TIPO_NOMBRE_FICHA_DESCR_PREF="archigest.archivo.descripcion.listasDescriptoras.form.nombreFichaDescrPref";
	public static final String DESCRIPCION_TABLASVALIDACION_FORM_NOMBRE="archigest.archivo.descripcion.tablasValidacion.form.nombre";
	public static final String DESCRIPCION_TBLVLD_ERROR_MODIFICACION = "archigest.archivo.descripcion.tblvld.error.modificacion";

	public static final String DESCRIPCION_LISTA_SERIES_AFECTADAS="lista.series.afectadas.reemplazo";
	public static final String DESCRIPCION_LISTA_SERIES_FUERA_RANGO="lista.series.fuera.rango.eemplazo";
	public static final String ERROR_DESCRIPCION_REEEMPLAZO_SELECCION_CAMPO_OBLIGATORIA="archigest.descripcion.reemplazo.seleccionCampo.obligatoria";
	public static final String ERROR_DESCRIPCION_REEMPLAZO_SIN_EFECTO="archigest.descripcion.reemplazo.sin.efecto";
	public static final String ERROR_DESCRIPCION_REEMPLAZO_ERROR_GENERICO="archigest.descripcion.reemplazo.error.generico";
	public static final String ERROR_DESCRIPCION_REEMPLAZO_CORRECTO_VARIOS="archigest.descripcion.reemplazo.correcto.varios";
	public static final String ERROR_DESCRIPCION_REEMPLAZO_CORRECTO_UNO="archigest.descripcion.reemplazo.correcto.uno";
	public static final String DESCRIPCION_NUM_REG_MODIFICADOS="regModificados";
	public static final String ERROR_DESCRIPCION_REEMPLAZO_ERROR_SIN_SELECCIONAR_ELEMS="archigest.descripcion.reemplazo.avanzado.sin.efecto";
	public static final String ERROR_REEMPLAZO_AVANZADO_NO_ELEMENTOS_CON_CAMPOS_FICHA_VACIOS="archigest.error.reemplazoAvanzado.soloEnCamposVacios.sinElementosValidos";
	public static final String ERROR_DESCRIPCION_UDOC_FUERA_NUEVO_RANGO_SERIE="errors.archivo.descripcion.udoc.fuera.nuevo.rango.serie";
	public static final String ERROR_REEMPLAZOS_VALORACTUAL_VACIO="errors.archivo.detalles.reemplazoSimple.valorActualVacio";

	//Unificar Descriptores
	public static final String INFORME_UNIFICAR_DESCRIPTORES_TITULO = "archigest.archivo.descripcion.informe.unificar.titulo";
	public static final String INFORME_UNIFICAR_DESCRIPTORES_DESCRIPTOR_FINAL = "archigest.archivo.descripcion.informe.unificar.descriptor.final";
	public static final String INFORME_UNIFICAR_DESCRIPTORES_LISTA_REEMPLAZADOS = "archigest.archivo.descripcion.informe.unificar.descriptores.reemplazados";

	//No hay descriptores a Reeemplazar
	public static final String ERROR_NO_DESCRIPTORES_PARA_REEMPLAZAR = "archigest.archivo.descripcion.listasDescriptoras.form.listaAUnificarVacia";

	//No hay descriptor principal
	public static final String ERROR_NO_DESCRIPTOR_PRINCIPAL = "errors.archivo.unificar.descriptores.no.seleccionado.principal";

	public static final String ERROR_AL_UNIFICAR_DESCRIPTORES = "archigest.archivo.descripcion.descriptores.error.unificar";

	public static final String MSG_DESCRIPTOR_CON_DOCUMENTOS ="archigest.archivo.descripcion.descriptores.unificar.warning.con.documentos";

	public static final String MSG_DESCRIPTOR_CON_FICHA ="archigest.archivo.descripcion.descriptores.unificar.warning.con.ficha";

	public static final String MSG_UNIFICAR_DESCRIPTORES_FINALIZADO = "archigest.archivo.descripcion.descriptores.message.unificar";
	public static final String REEMPLAZO_SIMPLE_SOLO_CAMPOS_VACIOS = "archigest.reemplazoSimple.soloEnCamposVacios";

	public static final String LISTA_PRODUCTORES_RELACION = getKey("LISTA_PRODUCTORES_RELACION");
}
