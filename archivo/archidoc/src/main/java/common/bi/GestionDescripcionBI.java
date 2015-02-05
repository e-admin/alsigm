package common.bi;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;

import transferencias.vos.MapDescUDocVO;
import transferencias.vos.RangoVO;
import xml.config.Busqueda;

import common.exceptions.ActionNotAllowedException;
import common.exceptions.CheckedArchivoException;
import common.exceptions.TooManyResultsException;
import common.pagination.PageInfo;
import common.vos.ResultadoRegistrosVO;

import descripcion.TipoObjetoUsado;
import descripcion.exceptions.DescriptorDuplicadoException;
import descripcion.model.TipoNorma;
import descripcion.model.xml.card.Ficha;
import descripcion.model.xml.card.TipoFicha;
import descripcion.model.xml.definition.DefFicha;
import descripcion.model.xml.format.DefFmtFicha;
import descripcion.vos.AreaVO;
import descripcion.vos.BusquedaDescriptoresVO;
import descripcion.vos.BusquedaGeneralAutVO;
import descripcion.vos.CampoDatoVO;
import descripcion.vos.CampoReferenciaVO;
import descripcion.vos.CampoTablaVO;
import descripcion.vos.DescriptorVO;
import descripcion.vos.FichaVO;
import descripcion.vos.FmtFichaVO;
import descripcion.vos.FmtPrefFichaVO;
import descripcion.vos.InfoBFichaVO;
import descripcion.vos.ListaDescrVO;
import descripcion.vos.TablaValidacionVO;
import descripcion.vos.TextoTablaValidacionVO;
import descripcion.vos.ValorCampoGenericoVO;
import fondos.vos.BusquedaElementosVO;
import fondos.vos.ElementoCuadroClasificacionVO;
import gcontrol.vos.ListaAccesoVO;

/**
 * Interfaz de negocio de descripción.
 */
public interface GestionDescripcionBI {

	/**
	 * Genera los valores automáticos de la ficha.
	 *
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param tipoFicha
	 *            Tipo de ficha ({@link TipoFicha}).
	 * @param parametros
	 *            Parámetros para el objeto descrito.
	 */
	public void generarAutomaticos(String id, int tipoFicha, Map parametros);

	/**
	 * Inserta o Actualiza el campo de la ficha.
	 *
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param campo
	 *            Información del campo.
	 */
	public void actualizaCampo(int tipoFicha, ValorCampoGenericoVO campo);

	/**
	 * Inserta campo de la ficha.
	 *
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param campo
	 *            Información del campo.
	 */
	public void insertaCampo(int tipoFicha, ValorCampoGenericoVO campo);

	/**
	 * Elimina todos los valores de un campo.
	 *
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param tipoCampo
	 *            Tipo de campo ({@link ValorCampoGenericoVO}).
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param idCampo
	 *            Identificador del campo.
	 */
	public void vaciaCampo(int tipoFicha, int tipoCampo, String id,
			String idCampo);

	/**
	 * Obtiene un CampoDatoVO perteneciente al id pasado por parámetro
	 *
	 * @param id
	 * @return CampoDatoVO
	 */
	public CampoDatoVO getCampoDato(String id);

	/**
	 * Obtiene un CampoDatoVO perteneciente al nombre pasado por parámetro
	 *
	 * @param nombre
	 * @return CampoDatoVO
	 */
	public CampoDatoVO getCampoDatoPorNombre(String nombre);

	/**
	 * Obtiene una lista de CampoDatoVO perteneciente al id de la tabla que se
	 * pasa por parámetro
	 *
	 * @param idTabla
	 * @return List
	 */
	public List getCamposDatoXIdTabla(String idTabla);

	/**
	 * Obtiene una lista de CampoTablaVO pertenecientes a los id de areas
	 * pasados por parámetro
	 *
	 * @param idAreas
	 * @return List
	 */
	public List getCamposTablaXArea(String[] idAreas);

	/**
	 * Obtiene una lista de CampoDatoVO pertenecientes a los id de areas pasados
	 * por parámetro
	 *
	 * @param idAreas
	 * @return List
	 */
	public List getCamposDatoXArea(String[] idAreas);

	/**
	 * Obtiene un CampoTablaVO perteneciente al id pasado por parámetro
	 *
	 * @param id
	 * @return CampoTablaVO
	 */
	public CampoTablaVO getCampoTabla(String id);

	/**
	 * Obtiene un CampoTablaVO perteneciente al nombre pasado por parámetro
	 *
	 * @param nombre
	 * @return CampoTablaVO
	 */
	public CampoTablaVO getCampoTablaPorNombre(String nombre);

	/**
	 * Obtiene los valores de un tipo de campo de un elemento del cuadro de
	 * clasificación.
	 *
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param tipoCampo
	 *            Tipo de campo.
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @return Lista de valores.
	 */
	public List getValues(int tipoFicha, int tipoCampo, String idElementoCF);

	/**
	 * Obtiene los valores de un tipo de campo de un elemento del cuadro de
	 * clasificación.
	 *
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param tipoCampo
	 *            Tipo de campo.
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param idCampo
	 *            Identificador del campo.
	 * @return Lista de valores.
	 */
	public List getValues(int tipoFicha, int tipoCampo, String id,
			String idCampo);

	/**
	 * Obtiene la información de una ficha.
	 *
	 * @param id
	 *            Identificador de la ficha.
	 * @return Información de la ficha.
	 */
	public FichaVO getFicha(String id);

	/**
	 * Obtiene la lista de fichas
	 *
	 * @return una lista de fichas
	 */
	public List getFichas();

	/**
	 * Obtiene la información básica de una ficha.
	 *
	 * @param id
	 *            Identificador de la ficha.
	 * @return Información básica de la ficha.
	 */
	public InfoBFichaVO getInfoBFicha(String id);

	// /**
	// * Compone una ficha de descripción nueva
	// * @param id Identificador del objeto a describir.
	// * @param tipoFicha Tipo de ficha.
	// */
	// public Ficha createFichaNueva(String id, int tipoFicha);

	/**
	 * Compone una ficha de descripción nueva
	 *
	 * @param id
	 *            Identificador del objeto a describir.
	 * @param tipoFicha
	 *            Tipo de ficha.
	 * @param defFicha
	 *            Definición de la ficha.
	 * @param defFmtFicha
	 *            Formato de la ficha.
	 * @param idFicha
	 *            Identificador de la ficha
	 * @return ficha.
	 */
	public Ficha createFichaNueva(String id, int tipoFicha, DefFicha defFicha,
			DefFmtFicha defFmtFicha, String idFicha);

	/**
	 * Crea la ficha de descripción y la inicializa con los valores por defecto.
	 *
	 * @param Ficha
	 *            Ficha de descripción.
	 * @param setTieneDescripcion
	 *            Indica si se debe marcar como descrito el objeto al que se
	 *            refiere la descripción que se va a generar
	 */
	public void createFicha(Ficha ficha, boolean setTieneDescripcion,
			Locale locale);

	/**
	 * Crea una ficha de descripción y la inicializa con los valores por
	 * defecto.
	 *
	 * @param id
	 *            Identificador del objeto a describir.
	 * @param tipoFicha
	 *            Tipo de ficha.
	 */
	public void createFicha(String id, int tipoFicha);

	/**
	 * Inserta una nueva fichaVO y un nuevo formato FmtFichaVO. Actualiza la
	 * tabla de uso
	 *
	 * @param FichaVO
	 *            fichaVO
	 * @param FmtFichaVO
	 *            fmtFichaVO
	 * @param List
	 *            listaUsoObjeto
	 * @return FichaVO
	 */
	public FichaVO createFicha(FichaVO ficha, FmtFichaVO fmtFichaVO,
			List listaUsoObjeto) throws ActionNotAllowedException;

	/**
	 * Inserta una nueva formato FmtFichaVO
	 *
	 * @param FmtFichaVO
	 *            fmtFichaVO
	 * @return FmtFichaVO fmtFichaVO
	 */
	public FmtFichaVO createFmtFicha(FmtFichaVO fmtFichaVO)
			throws ActionNotAllowedException;

	/**
	 * Actualiza la ficha pasada por parametro.
	 *
	 * @param FichaVO
	 *            fichaVO
	 * @param List
	 *            listaUsoObjeto
	 * @return FichaVO
	 */
	public FichaVO updateFicha(FichaVO fichaVO, List listaUsoObjeto)
			throws ActionNotAllowedException;

	/**
	 * Actualiza el Formato de Ficha
	 *
	 * @param FmtFichaVO
	 *            fmtFichaVO
	 * @return FmtFichaVO
	 */
	public FmtFichaVO updateFmtFicha(FmtFichaVO fmtFichaVO)
			throws ActionNotAllowedException;

	/**
	 * Guarda los cambios de la ficha en base de datos.
	 *
	 * @param ficha
	 *            Ficha a guardar.
	 */
	public void updateFicha(Ficha ficha);

	/**
	 * Elimina la ficha de descripción de un objeto.
	 *
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 */
	public void deleteFicha(String id, int tipoFicha);

	/**
	 * Elimina el conjunto de fichas indicado del sistema, junto a sus formatos
	 * de ficha correspondientes
	 *
	 * @param idsFichas
	 *            Conjunto de identificadores de ficha
	 */
	public void deleteFichas(String[] idsFichas);

	/**
	 * Elimina el conjunto de formatos de fichas indicado del sistema
	 *
	 * @param idsFichas
	 *            Conjunto de identificadores de ficha
	 */
	public void deleteFormatos(String[] idsFormatos);

	/**
	 * Obtiene una lista de fichas de unos tipos de nivel determinados.
	 *
	 * @param tiposNivel
	 *            Tipos de nivel.
	 * @return Lista de fichas.
	 */
	public List getFichasByTiposNivel(int[] tiposNivel);

	/**
	 * Obtiene una lista de fichas de unos tipos de nivel y subtipos
	 * determinados.
	 *
	 * @param nivelesSeleccionados
	 *            Lista de niveles seleccionados
	 * @return Lista de fichas.
	 */
	public List getFichasByTiposNivelIdFichaPref(List nivelesSeleccionados);

	/**
	 * Obtiene la lista de fichas de un tipo de norma.
	 *
	 * @param tipoNorma
	 *            Tipo de norma ({@link TipoNorma}).
	 * @return Lista de fichas ({@link InfoBFichaVO}).
	 */
	public List getFichasByTipoNorma(int tipoNorma);

	/**
	 * Obtiene la lista de fichas de un tipo de norma.
	 *
	 * @param tipoNorma
	 *            Tipo de norma ({@link TipoNorma}).
	 * @param tiposNivel
	 *            Tipos de nivel.
	 * @return Lista de fichas ({@link InfoBFichaVO}).
	 */
	public List getFichasByTipoNormaYNiveles(int tipoNorma, int[] tiposNivel);

	/**
	 * Obtiene la lista de campos de una ficha.
	 *
	 * @param idFicha
	 *            Identificador de la ficha.
	 * @return Lista de campos ({@link CampoDatoVO}).
	 */
	public List getCamposBusquedaAvanzada(String idFicha);

	/**
	 * Obtiene la lista de valores de una tabla de validación.
	 *
	 * @param idTblVld
	 *            Identificador de la tabla de validación.
	 * @return Lista de valores.
	 */
	public List getValoresValidacion(String idTblVld);

	/**
	 * Obtiene la lista de listas descriptoras.
	 *
	 * @return Listas descriptoras.
	 */
	public List getListasDescriptoras();

	/**
	 * Obtiene la lista de listas descriptoras.
	 *
	 * @param ids
	 *            Array de identificadores de listas descriptoras.
	 * @return Listas descriptoras.
	 */
	public List getListasDescriptoras(String[] ids);

	/**
	 * Obtiene la lista de listas descriptoras con información extendida.
	 *
	 * @return Listas descriptoras.
	 */
	public List getListasDescriptorasExt();

	/**
	 * Obtiene la lista descriptora.
	 *
	 * @param id
	 *            Identificador de la lista descriptora.
	 * @return Lista descriptora.
	 */
	public ListaDescrVO getListaDescriptora(String id);

	/**
	 * Obtiene la lista descriptora con información extendida.
	 *
	 * @param id
	 *            Identificador de la lista descriptora.
	 * @return Lista descriptora.
	 */
	public ListaDescrVO getListaDescriptoraExt(String id);

	/**
	 * Obtiene las listas descriptoras en función del tipo de descriptores.
	 *
	 * @param tipoDescriptor
	 *            Tipo de descriptores ({@link descripcion.model.TipoDescriptor}
	 *            ).
	 * @return Listas descriptoras.
	 */
	public List getListasDescrByTipoDescriptor(int tipoDescriptor);

	/**
	 * Obtiene las listas descriptoras en función del tipo de descriptores y la
	 * ficha.
	 *
	 * @param tipoDescriptor
	 * @param idFicha
	 * @return Listas descriptoras.
	 */
	public List getListasDescrByTipoDescrOFicha(int tipoDescriptor,
			String idFicha);

	/**
	 * Obtiene la definición del formato de ficha preferente del usuario.
	 *
	 * @param idFicha
	 *            Identificador de la definición de la ficha.
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @param tipo
	 *            Tipo de formato.
	 * @return Definición del formato de una ficha preferente.
	 */
	public FmtPrefFichaVO getFmtPrefFicha(String idFicha, String idUsuario,
			int tipo);

	/**
	 * Obtiene la definición del formato de una ficha.
	 *
	 * @param id
	 *            Identificador de la definición del formato de la ficha.
	 * @return Definición del formato de una ficha.
	 */
	public FmtFichaVO getFmtFicha(String id);

	/**
	 * Obtiene la lista de definiciones de formatos de fichas.
	 *
	 * @return Lista de definiciones de formatos de fichas.
	 */
	public List getFmtFichas(String idFicha, int tipo);

	/**
	 * Obtiene la lista de definiciones de formatos de fichas.
	 *
	 * @return Lista de definiciones de formatos de fichas.
	 */
	public List getFmtFichas();

	/**
	 * Busca los formatos de fichas, por nombre e idFicha asociada
	 *
	 * @param nombre
	 *            Nombre del Formato
	 * @param idFicha
	 *            Identificador de la Ficha
	 * @return Lista de {@link FmtFichaVO}
	 */
	public List findFmtFichas(String nombre, String idFicha);

	/**
	 * Obtiene las fichas de una lista de acceso
	 *
	 * @param idListaAcceso
	 * @return
	 */
	public List getFmtsFichasXListaAcceso(String idListaAcceso);

	/**
	 * Inserta un formato de ficha preferente para un usuario.
	 *
	 * @param fmtPrefFicha
	 *            Formato de ficha preferente.
	 */
	public void insertFmtPrefFicha(final FmtPrefFichaVO fmtPrefFicha);

	/**
	 * Modifica un formato de ficha preferente para un usuario.
	 *
	 * @param fmtPrefFicha
	 *            Formato de ficha preferente.
	 */
	public void updateFmtPrefFicha(final FmtPrefFichaVO fmtPrefFicha);

	/**
	 * Obtiene la definición de la ficha de descripción.
	 *
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param tipoFicha
	 *            Tipo de ficha ({@link TipoFicha}).
	 * @return Definición de la ficha de descripción.
	 */
	public DefFicha getDefFicha(String id, int tipoFicha);

	/**
	 * Compone una ficha de descripción.
	 *
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param tipoAcceso
	 *            Tipo de acceso ({@link descripcion.model.TipoAcceso}).
	 * @return Ficha.
	 */
	public Ficha componerFicha(String id, int tipoFicha, int tipoAcceso,
			String idFicha, boolean exportacion);

	/**
	 * Compone la ficha de descripción de un elemento del cuadro de
	 * clasificación.
	 *
	 * @param elementoCF
	 *            Elemento del cuadro.
	 * @param tipoAcceso
	 *            Tipo de acceso ({@link descripcion.model.TipoAcceso}).
	 * @return Ficha.
	 */
	public Ficha componerFichaElemento(
			ElementoCuadroClasificacionVO elementoCF, int tipoAcceso,
			boolean exportacion);

	/**
	 * Modifica los valores de la ficha.
	 *
	 * @param ficha
	 *            Ficha actual.
	 * @param parameters
	 *            Valores modificados.
	 * @param exportar
	 * @return Ficha modificada.
	 */
	public ActionErrors modificarValoresFicha(Ficha ficha, Map parameters);

	/*
	 * ============= BÚSQUEDAS =============
	 */

	/**
	 * Obtiene la lista de elementos que cumplan los requisitos de la búsqueda.
	 *
	 * @param busquedaGeneralVO
	 *            Información del formulario de búsqueda.
	 * @return Lista de elementos del cuadro de clasificación.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	/*
	 * public List searchElementosCuadroClasificacion( BusquedaGeneralVO
	 * busquedaGeneralVO) throws TooManyResultsException;
	 */
	public List searchElementosCuadroClasificacion(
			BusquedaElementosVO busquedaElementosVO)
			throws TooManyResultsException;

	/**
	 * Realiza la búsqueda de autoridades en función de los parámetros del
	 * formulario de búsquedas.
	 *
	 * @param busquedaGeneralAutVO
	 *            Parámetros del formulario de búsquedas.
	 * @return Lista de autoridades.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List searchAutoridades(BusquedaGeneralAutVO busquedaGeneralAutVO)
			throws TooManyResultsException;

	/*
	 * ====================== TABLAS DE VALIDACION ======================
	 */

	/**
	 * Obtiene la lista de tablas de validación.
	 *
	 * @return Lista de tablas de validación.
	 */
	public List getTablasValidacion();

	/**
	 * Obtiene la tabla de validación.
	 *
	 * @param id
	 *            Identificador de la tabla de validación.
	 * @return Tabla de validación.
	 */
	public TablaValidacionVO getTablaValidacion(String id);

	TablaValidacionVO getTablaValidacionByNombre(String nombre);

	/**
	 * Crea una tabla de validación.
	 *
	 * @param tablaValidacion
	 *            Tabla de validación.
	 * @return Tabla de validación insertada.
	 */
	public TablaValidacionVO insertTablaValidacion(
			TablaValidacionVO tablaValidacion);

	/**
	 * Modifica la tabla de validación.
	 *
	 * @param tablaValidacion
	 *            Tabla de validación.
	 */
	public void updateTablaValidacion(TablaValidacionVO tablaValidacion);

	/**
	 * Elimina las tablas de validación.
	 *
	 * @param listaIds
	 *            Lista de identificadores de tablas de validación.
	 */
	public void deleteTablasValidacion(String[] listaIds);

	/*
	 * ================================= VALORES DE TABLAS DE VALIDACION
	 * =================================
	 */

	/**
	 * Obtiene el valor de la tabla de validación.
	 *
	 * @param id
	 *            Identificador del valor de la tabla de validación.
	 * @return Valor de la tabla de validación.
	 */
	public TextoTablaValidacionVO getValorTablaValidacion(String id);

	/**
	 * Crea un valor de la tabla de validación.
	 *
	 * @param valor
	 *            Valor de la tabla de validación.
	 * @return Valor insertado.
	 */
	public TextoTablaValidacionVO insertValorTablaValidacion(
			final TextoTablaValidacionVO valor);

	/**
	 * Modifica el valor de la tabla de validación.
	 *
	 * @param valor
	 *            Valor de la tabla de validación.
	 * @param valor_antiguo
	 *            String con el valor que tenia antes de actualizarse.
	 */
	// public void updateValorTablaValidacion(TextoTablaValidacionVO valor,
	// String valor_antiguo);
	public void updateValorTablaValidacion(TextoTablaValidacionVO valor);

	/**
	 * Elimina los valores de una tabla de validación.
	 *
	 * @param listaIds
	 *            Lista de identificadores de valores de una tabla de
	 *            validación.
	 */
	public void deleteValoresTablaValidacion(String[] listaIds);

	/*
	 * ===================== LISTAS DESCRIPTORAS =====================
	 */

	/**
	 * Indica si una lista descriptora tiene descriptores.
	 *
	 * @param id
	 *            Identificador de la lista descriptora.
	 * @return Si tiene descriptores.
	 */
	public boolean tieneDescriptores(String id);

	/**
	 * Crea una lista descriptora.
	 *
	 * @param listaDescriptora
	 *            lista descriptora.
	 * @return Lista descriptora insertada.
	 */
	public ListaDescrVO insertListaDescriptora(ListaDescrVO listaDescriptora);

	/**
	 * Modifica la lista descriptora.
	 *
	 * @param listaDescriptora
	 *            Lista descriptora.
	 */
	public void updateListaDescriptora(ListaDescrVO listaDescriptora);

	/**
	 * Elimina las listas descriptoras.
	 *
	 * @param listaIds
	 *            Lista de identificadores de listas descriptoras.
	 * @return Información de la eliminación.
	 */
	public ResultadoRegistrosVO deleteListasDescriptoras(String[] listaIds);

	/*
	 * ============== DESCRIPTORES ==============
	 */

	/**
	 * Obtiene los descriptores de una lista de descriptores.
	 *
	 * @param idListaDescriptores
	 *            Identificador de la lista de descriptores.
	 * @return Descriptores.
	 */
	public List getDescriptores(String idListaDescriptores);

	/**
	 * Cambia el estado a validado(1) a los descritptores seleccionados.
	 *
	 * @param idsDescriptores
	 *            Identificadores de los descriptores a validar.
	 */
	public void validarDescriptores(String[] idsDescriptores);

	/**
	 * Obtiene los descriptores de una lista de descriptores con información
	 * extendida.
	 *
	 * @param idListaDescriptores
	 *            Identificador de la lista de descriptores.
	 * @param pageInfo
	 *            Información de la paginación.
	 * @return Descriptores.
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getDescriptoresExt(String idListaDescriptores, PageInfo pageInfo)
			throws TooManyResultsException;

	/**
	 * Obtiene la información de un descriptor con información extendida.
	 *
	 * @param idDescriptor
	 *            Identificador del descriptor.
	 * @return Descriptor.
	 */
	public DescriptorVO getDescriptorExt(String idDescriptor);

	/**
	 * Obtiene la información de un descriptor.
	 *
	 * @param idDescriptor
	 *            Identificador del descriptor.
	 * @return Descriptor.
	 */
	public DescriptorVO getDescriptor(String idDescriptor);

	/**
	 * Obtiene una lista de descriptores a partir de su nombre.
	 *
	 * @param nombre
	 *            Nombre del descriptor.
	 * @return Lista de descriptores.
	 */
	public List getDescriptoresByNombre(String nombre);

	/**
	 * Obtiene una lista de descriptores a partir de su nombre y el id de la
	 * lista.
	 *
	 * @param nombre
	 *            , idLista
	 * @param idLista
	 * @return {@link DescriptorVO}
	 */
	public DescriptorVO getDescriptorByNombreYIdLista(String nombre,
			String idLista);

	/**
	 * Busca los descriptores en función de unos criterios.
	 *
	 * @param criterios
	 *            Criterios de búsqueda.
	 * @return Lista de descriptores.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getDescriptores(BusquedaDescriptoresVO criterios)
			throws TooManyResultsException;

	/**
	 * Obtiene los descriptores de una lista de descriptores y en cuyo valor
	 * está contenido el patrón que se suministra
	 *
	 * @param idListaDescriptores
	 *            Identificador de lista de descriptores
	 * @param pattern
	 *            Patrón a buscar
	 * @return Lista de descriptores {@link DescriptorVO}
	 */
	public List findDescriptores(String idListaDescriptores, String pattern);

	/**
	 * @param idListaAcceso
	 * @return descriptores asociados a una lista de acceso
	 */
	public List getDescriptoresXListaAcceso(String idListaAcceso);

	/**
	 * Inserta un descriptor.
	 *
	 * @param descriptor
	 *            Descriptor.
	 * @return Descriptor insertado.
	 */
	public DescriptorVO insertDescriptor(DescriptorVO descriptor);

	/**
	 * Modifica un descriptor.
	 *
	 * @param descriptor
	 *            Descriptor.
	 */
	public void updateDescriptor(DescriptorVO descriptor);

	/**
	 * Establece si un descriptor tiene ficha de descripción.
	 *
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @param tieneDescr
	 *            Si el descriptor tiene ficha de descripción.
	 */
	public void setTieneDescrDescriptor(String idDescr, boolean tieneDescr);

	/**
	 * Establece si se han creado los clasificadores por defecto de un
	 * descriptor.
	 *
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @param editClfDocs
	 *            Si se han creado los clasificadores por defecto.
	 */
	public void setEditClfDocs(String idDescr, boolean editClfDocs);

	/**
	 * Elimina los descriptores.
	 *
	 * @param listaIds
	 *            Lista de identificadores de descriptores.
	 * @return Información de la eliminación.
	 */
	public ResultadoRegistrosVO deleteDescriptores(String[] listaIds);

	/**
	 * Unifica varios descriptores en uno.
	 *
	 * @param descriptorVO
	 *            Descriptor a Mantener
	 * @param idsAReemplazar
	 *            Identificadores de Descriptores a reemplazar
	 * @param nombresAReemplazar
	 *            Nombres de los Identificadores a Reemplazar.
	 * @since 2.9.7
	 */
	public void unificarDescriptores(DescriptorVO descriptorVO,
			String[] idsAReemplazar, String[] nombresAReemplazar)
			throws Exception;

	/**
	 * Comprueba si el descriptor seleccionado tiene descripción.
	 *
	 * @param idDescriptor
	 *            Identificador del Descriptor
	 * @return <b>true</b> si el descriptor tiene valores en la fichas.<br>
	 *         <b>false</b> si el descriptor no tiene valores en las fichas.
	 *
	 */
	public boolean isConDescripcion(String idDescriptor);

	/*
	 * ============== AREAS ==============
	 */

	/**
	 * Obtiene un AreaVO perteneciente al id pasado por parámetro
	 *
	 * @param id
	 * @return un AreaVO
	 */
	public AreaVO getArea(String id);

	/**
	 * Obtiene un AreaVO perteneciente al nombre pasado por parámetro
	 *
	 * @param nombre
	 * @return un AreaVO
	 */
	public AreaVO getAreaPorNombre(String nombre);

	/**
	 * Obtiene la lista de areas de un tipo de norma.
	 *
	 * @param tipoNorma
	 *            Tipo de norma.
	 * @return Lista de areas.
	 */
	public List getAreasByTipoNorma(int tipoNorma);

	/**
	 * Obtiene la lista de areas
	 *
	 * @return una lista de areas
	 */
	public List getAreas();

	/**
	 * Inserta un nuevo area.
	 *
	 * @param AreaVO
	 * @return AreaVO
	 */
	public AreaVO createArea(AreaVO areaVO);

	/**
	 * Elimina el conjunto de areas indicado por parámetro
	 *
	 * @param idsAreas
	 *            Conjunto de identificadores de area
	 */
	public void deleteAreas(String[] idsAreas);

	/**
	 * Actualiza el area pasada por parametro.
	 *
	 * @param areaVO
	 * @return AreaVO
	 */
	public AreaVO updateArea(AreaVO areaVO);

	/**
	 * Obtiene la lista de CamposDatoVO que no pertenecen a ninguna tabla
	 *
	 * @return una lista de CamposDatoVO
	 */
	public List getCamposDatoSinTabla();

	/**
	 * Obtiene la lista de CamposDatoVO que pertenecen a un Area con sus datos
	 *
	 * @param idArea
	 *            Cadena que contiene el identificador del área
	 * @return una lista de CamposDatoVO
	 */
	public List getCamposDatoByArea(String idArea, String nombreArea);

	/**
	 * Obtiene la lista de CampoVO que pertenecen a un Area con sus datos
	 *
	 * @param idArea
	 *            Cadena que contiene el identificador del área
	 * @return una lista de CamposDatoVO
	 */
	public List getCamposByArea(String idArea, String nombreArea);

	/**
	 * Obtiene la lista de CamposDatoVO
	 *
	 * @param String
	 *            idTabla
	 * @return una lista de CamposDatoVO
	 */
	public List getCamposDatoOrderByPosEnTbl(String idTabla);

	/**
	 * Obtiene un CamposDatoVO
	 *
	 * @param String
	 *            idTabla
	 * @param int posicion
	 * @return CampoDatoVO
	 */
	public CampoDatoVO getCampoDatoByPosEnTbl(String idTabla, int pos);

	/**
	 * Obtiene la lista de CamposDatoVO
	 *
	 * @return una lista de CamposDatoVO
	 */
	public List getCamposDato();

	/**
	 * Obtiene la lista de CamposTablaVO
	 *
	 * @return una lista de CamposTablaVO
	 */
	public List getCamposTabla();

	/**
	 * Inserta un nuevo CampoDatoVO.
	 *
	 * @param CampoDatoVO
	 * @return CampoDatoVO
	 */
	public CampoDatoVO createCampoDato(CampoDatoVO campoDatoVO);

	/**
	 * Inserta un nuevo CampoTablaVO.
	 *
	 * @param CampoTablaVO
	 * @return CampoTablaVO
	 */
	public CampoTablaVO createCampoTabla(CampoTablaVO campoTablaVO);

	/**
	 * Elimina el conjunto de campos indicado por parámetro
	 *
	 * @param idsCamposDato
	 *            Conjunto de identificadores de campos
	 */
	public void deleteCamposDato(String[] idsCamposDato);

	/**
	 * Elimina el conjunto de campos indicado por parámetro
	 *
	 * @param idsCamposDato
	 *            Conjunto de identificadores de campos
	 */
	public void deleteCamposTabla(String[] idsCamposDato);

	/**
	 * Elimina el campo de tabla perteneciente al id pasado por parámetro
	 *
	 * @param String
	 *            id
	 */
	public void deleteCampoTabla(String id);

	/**
	 * Actualiza el campo pasado por parametro.
	 *
	 * @param CampoDatoVO
	 * @return CampoDatoVO
	 */
	public CampoDatoVO updateCampoDato(CampoDatoVO campoDatoVO);

	/**
	 * Actualiza el campo pasado por parametro.
	 *
	 * @param CampoTablaVO
	 * @return CampoTablaVO
	 */
	public CampoTablaVO updateCampoTabla(CampoTablaVO campoTablaVO);

	/**
	 * Construye una lista con los tipos de normas
	 *
	 * @return lista de tipos de normas;
	 */
	public List makeListTipoNorma();

	/**
	 * Construye una lista con los tipos de niveles para ninguna norma
	 *
	 * @return lista de tipos de niveles;
	 */
	public List makeListTipoNivelesNinguno();

	/**
	 * Construye una lista con los tipos de niveles para la norma isaar
	 *
	 * @return lista de tipos de niveles;
	 */
	public List makeListTipoNivelesIsaar();

	/**
	 * Construye una lista con los tipos de niveles para la norma isad
	 *
	 * @return lista de tipos de niveles;
	 */
	public List makeListTipoNivelesIsad();

	/**
	 * Construye una lista con los subtipos de niveles para la norma isad de
	 * tipo unidad documental
	 *
	 * @param tipoNivel
	 * @return lista de tipos de niveles;
	 */
	public List makeListSubTipoNivelesIsad(String tipoNivel);

	/**
	 * Construye una lista de los tipos de campos
	 *
	 * @return lista de tipos de campos;
	 */
	public List makeListTipoCampo();

	/**
	 * Construye una lista de los tipos de campos de entidad (Campo, Tabla).
	 *
	 * @return lista de tipos de campos;
	 */
	public List makeListTipoCampoEntidad();

	/**
	 * Obtiene el texto de un tipo para un id de tipo
	 *
	 * @param ficha
	 * @return FichaVO
	 */
	public String getTipoText(int tipo);

	/**
	 * Obtiene el texto de un tipo de norma para un id de tipo de norma
	 *
	 * @param tipo
	 * @return String
	 */
	public String getTipoNormaText(int tipoNorma);

	/**
	 * Obtiene una lista de elementos en uso pertenecientes al id del objeto
	 * pasado por parámetro
	 *
	 * @param String
	 *            idObj
	 * @return List lista de elementos en uso
	 */
	public List getElementosEnUsoXIdObj(String idObj);

	/**
	 * Obtiene una lista de elementos en uso pertenecientes a los id's del
	 * objeto pasado por parámetro
	 *
	 * @param String
	 *            [] idObjs
	 * @return List lista de elementos en uso
	 */
	public List getElementosEnUsoXIdsObj(String[] idObjs);

	/**
	 * Obtiene una lista de identificadores de elementos que cumplen los
	 * parámetros de búsqueda
	 *
	 * @param busquedaElementosVO
	 * @param numMaxResults
	 * @return
	 */
	public List getIdsElementos(BusquedaElementosVO busquedaElementosVO,
			int numMaxResults, Busqueda busqueda)
			throws TooManyResultsException;

	/**
	 * Obtiene una lista de identificadores de elementos que cumplen los
	 * parámetros de búsqueda
	 *
	 * @param busquedaElementosVO
	 * @param numMaxResults
	 * @return
	 */
	public List getElementos(String[] idsToShow,
			BusquedaElementosVO busquedaGeneralVO, Busqueda busqueda)
			throws TooManyResultsException;

	/**
	 * Obtiene en una lista de CampoValorVO el valor, formato y calificador de
	 * una fecha cuyo idCampo correspondiente se indica como parámetro
	 *
	 * @param idElementocf
	 *            Identificador del elemento del cuadro
	 * @param idCampo
	 *            Identificador del campo de fecha correspondiente
	 * @return Lista de rangos.
	 */
	public List getFechaElemento(String idElementocf, String idCampo);

	/**
	 * Obtiene la fecha máxima del tipo indicado en idCampo de las unidades
	 * documentales contenidas en una serie correspondiente se indica como
	 * parámetro
	 *
	 * @param idSerie
	 *            Identificador de la Serie a la que pertenecen las unidades
	 *            documentales
	 * @param idCampo
	 *            Identificador del campo de fecha correspondiente
	 * @return Fecha máxima.
	 */
	public Date getFechaMaximaUDocs(String idSerie, String idCampo);

	public Date getFechaMinimaUDocs(String idSerie, String idCampo);

	/**
	 * Obtiene la definición de la ficha de descripción.
	 *
	 * @param idFicha
	 *            Identificador de la ficha descriptiva
	 * @return Definición de la ficha de descripción.
	 */
	public DefFicha getDefFichaById(String idFicha);

	public List getCamposFicha(String idFicha);

	/**
	 * Permite copiar los campos descriptivos de una unidad documental en
	 * relación de entrega a los campos de ficha de unidad documental en cuadro
	 *
	 * @param idUDocRE
	 *            identificador de la unidad documental en la relación de
	 *            entrega
	 * @param idUDocCF
	 *            identificador de la unidad documental en el cuadro de
	 *            clasificación
	 */
	public void copiarCamposUDocREaUDocCF(String idUDocRE, String idUDocCF,
			int tipoElementoOrigen);

	/**
	 * Permite eliminar de las tablas de descripción de unidad documental en
	 * relación de entrega los valores de los campos de descripción
	 *
	 * @param idUDocRE
	 *            Identificador de la unidad documental en la relación de
	 *            entrega
	 */
	public void eliminarValoresCamposUDocRE(String idUDocRE, int tipoElemento);

	/**
	 * Permite copiar los campos descriptivos en relación de entrega de una
	 * unidad documental origen a otra destino
	 *
	 * @param idUDocREOrigen
	 *            identificador de la unidad documental origen
	 * @param idUDocREDestino
	 *            identificador de la unidad documental destino
	 */
	public void copiarCamposUDocREaUDocRE(String idUDocREOrigen,
			String idUDocREDestino, int tipoElemento);

	/**
	 * Permite copiar los campos descriptivos en relación de entrega de una
	 * unidad documental origen a otra destino
	 *
	 * @param idUDocREOrigen
	 *            identificador de la unidad documental origen
	 * @param idUDocREDestino
	 *            identificador de la unidad documental destino
	 * @param tipoElemento
	 * @param camposAIgnorar
	 */
	public void copiarCamposUDocREaUDocRE(String idUDocREOrigen,
			String idUDocREDestino, int tipoElemento, Map camposAIgnorar);

	/**
	 * Devolver el interesado principal de una unidad documental en división de
	 * fracción de serie o relación de entrega
	 *
	 * @param idUDoc
	 * @param tipoUDoc
	 * @return transferencias.vos.InteresadoVO
	 */
	public transferencias.vos.InteresadoVO getInteresadoPrincipal(
			String idUDoc, int tipoUDoc);

	/**
	 * Devolver los interesado principal de una unidad documental en división de
	 * fracción de serie o relación de entrega
	 *
	 * @param idUDoc
	 * @param tipoUDoc
	 * @return transferencias.vos.InteresadoVO
	 */

	/**
	 * Devolver los interesados principales de varias unidades documentales en
	 * división de fracción de serie o relación de entrega
	 *
	 * @param idsUDoc
	 * @param tipoUDoc
	 * @return Map con clave el identificador de la unidad y valor el interesado
	 *         principal
	 */
	public Map getInteresadoPrincipal(String[] idsUDoc, int tipoUDoc);

	public HashMap getValoresFichaConDatos(int tipoFicha, String id);

	/**
	 * Obtiene los valores de un tipo de campo de un elemento del cuadro de
	 * clasificación.
	 *
	 * @param tipoFicha
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 * @param tipoCampo
	 *            Tipo de campo.
	 * @param id
	 *            Identificador del objeto descrito.
	 * @param idCampo
	 *            Identificador del campo.
	 * @return número de valores encontrados.
	 */
	public int countValues(int tipoFicha, int tipoCampo, String id,
			String idCampo);

	/**
	 * Obtiene el título del elemento
	 *
	 * @param tipoFicha
	 *            Tipo de Ficha
	 * @param idElemento
	 *            Identificador del Elemento
	 * @return Título del Documento.
	 */
	public String getTituloDocumento(String idElemento);

	/**
	 * Obtiene el Asunto de un Expediente
	 *
	 * @param tipoFicha
	 *            Tipo de Ficha
	 * @param idElemento
	 *            Identficador del Elemento
	 * @return Asunto del Elemento
	 */
	public String getAsunto(String idElementoCF);

	/**
	 * Obtiene el campo Fecha Inicial de Un Elemento
	 *
	 * @param tipoFicha
	 *            Tipo de Ficha
	 * @param idElemento
	 *            Identificador del Elemento
	 * @return Fecha Extrema Inicial
	 */
	public Date getFechaInicial(int tipoFicha, String idElemento);

	/**
	 * Obtiene el valor del campo Condiciones de Acceso
	 *
	 * @param tipoFicha
	 *            Tipo de Ficha
	 * @param idElemento
	 *            Identificador del Elemento
	 * @return Condiciones de Acceso del Elemento
	 */
	public String getCondicionesAcceso(String idElemento);

	/**
	 * Obtiene el campo Fecha Final de Un Elemento
	 *
	 * @param tipoFicha
	 *            Tipo de Ficha
	 * @param idElemento
	 *            Identificador del Elemento
	 * @return Fecha Extrema Final
	 */
	public Date getFechaFinal(int tipoFicha, String idElemento);

	/**
	 * Obtiene el campo Productor de Un Elemento
	 *
	 * @param tipoFicha
	 *            Tipo de Ficha
	 * @param idElemento
	 *            Identificador del Elemento
	 * @return Productor
	 */
	public CampoReferenciaVO getProductor(int tipoFicha, String idElemento);

	/**
	 * Obtiene los rangos de la fracción de serie
	 *
	 * @param tipoFicha
	 *            Tipo de Ficha
	 * @param idElemento
	 *            Identificador del Elemento
	 * @return Lista de {@link RangoVO}
	 */
	public List getRangosFS(int tipoFicha, String idElemento);

	/**
	 * Comprueba si la ficha esta o no en uso por algun elemento de la
	 * aplicación.
	 *
	 * @param idsFichas
	 * @return
	 */
	public boolean isFichaEnUso(String[] idsFichas);

	/**
	 * Obtiene las listas de Control de Acceso de una Serie
	 *
	 * @param idSerie
	 *            Identificador de la Serie
	 * @return {@link ListaAccesoVO}
	 */
	public List getListasControlAccesoProductoresSerie(String idSerie);

	/**
	 * Obtiene los Descriptores de Productores de las Relaciones de Entrega.
	 *
	 * @param busquedaVO
	 *            Parámetros de la Búsqueda
	 * @param tiposRelacion
	 *            Tipos de Relación de Entrega
	 * @return Lista de {@link DescriptorVO}
	 * @throws TooManyResultsException
	 */
	public List getDescriptoresProductoresRelacion(
			BusquedaDescriptoresVO busquedaVO, Integer[] tiposRelacion)
			throws TooManyResultsException;

	public TextoTablaValidacionVO getValorTablaValidacionByValor(String valor,
			String idTblvld);

	public List getListasDescriptorasAbiertas();

	/**
	 * Añade un descriptor a la lista seleccionada.
	 *
	 * @param idLista
	 *            Identificador de la lista.
	 * @param nombre
	 *            Nombre de la lista.
	 * @throws CheckedArchivoException
	 */
	public DescriptorVO addDescriptor(String idLista, String nombre)
			throws DescriptorDuplicadoException, CheckedArchivoException;

	public List getInteresadosByIdsElementoCF(String[] idsElementos);

	/**
	 * Comprueba si existe un registro por su key
	 *
	 * @param id
	 *            Cadena que contiene el identificador del elemento
	 * @param codigoTabla
	 *            Cadena que contiene el codigo de tabla
	 * @return
	 */
	public boolean existeRegistroByKey(String key, int codigoTabla);

	/**
	 * Comprueba si existe un registro por su value
	 *
	 * @param key
	 *            Cadena que contiene el identificador del elemento
	 * @param value
	 *            Cadena que contiene el value del elemento
	 * @param codigoTabla
	 *            Cadena que contiene el codigo de tabla
	 * @return
	 */
	public boolean existeRegistroByValue(String key, String value,
			int codigoTabla);

	/**
	 * Obtiene una lista de elementos en uso pertenecientes a los id's del
	 * objeto pasado por parámetro y de un tipo determinado
	 *
	 * @param idsObjetos
	 *            Conjunto de cadenas que contienen los identificadores de los
	 *            objetos
	 * @param idTipoObjeto
	 *            Tipo de Objeto {@link TipoObjetoUsado}
	 * @return
	 */
	public List getElementosEnUsoXIdObjYTipo(String[] idsObjetos,
			int idTipoObjeto);

	/**
	 * Obtiene un campo dato por su etiqueta xml
	 *
	 * @param etiquetaXml
	 *            Cadena que contiene el valor de la etiqueta xml
	 * @return {@link CampoDatoVO}
	 */
	public CampoDatoVO getCampoDatoByEtiqueta(String etiquetaXml);

	/**
	 * Obtiene un campo dato por su etiqueta xml
	 *
	 * @param etiquetaXml
	 *            Cadena que contiene el valor de la etiqueta xml
	 * @param idTabla
	 *            Cadena que contiene el identificador de la tabla
	 * @return {@link CampoDatoVO}
	 */
	public CampoDatoVO getCampoDatoByEtiqueta(String etiquetaXml, String idTabla);

	/**
	 * Obtiene un campo tabla por su etiqueta xml
	 *
	 * @param etiquetaXml
	 *            Cadena que contiene el valor de la etiqueta xml
	 * @return {@link CampoTablaVO}
	 */
	public CampoTablaVO getCampoTablaByEtiqueta(String etiquetaXml);

	/**
	 * Obtiene un campo tabla por su etiqueta xml
	 *
	 * @param etiquetaXml
	 *            Cadena que contiene el valor de la etiqueta xml de la fila
	 * @return {@link CampoTablaVO}
	 */
	public CampoTablaVO getCampoTablaByEtiquetaFila(String etiquetaXml);

	/**
	 * Obtiene el mapeo de descripción asociado a una ficha determinada.
	 *
	 * @param idFicha
	 *            Cadena que contiene el identificador de la ficha.
	 * @return {@link MapDescUDocVO}
	 */
	public MapDescUDocVO getMapeoDescripcion(String idFicha);

	/**
	 * Actualizar el mapeo de descripción asociado a una ficha determinada.
	 *
	 * @param mapDescUDocVO
	 *            Objeto que contiene la información del mapeo
	 * @return {@link MapDescUDocVO}
	 */
	public void updateMapeoDescripcion(MapDescUDocVO mapDescUDocVO);

	/**
	 * Duplica una ficha, sus formatos y su mapeo de unidades
	 * @param idFichaOrigen
	 * @param fichaVO
	 * @return
	 */
	public FichaVO duplicarFicha(String idFichaOrigen, FichaVO fichaVO);


}