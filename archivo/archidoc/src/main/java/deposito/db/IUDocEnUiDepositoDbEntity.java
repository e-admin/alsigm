package deposito.db;

import java.util.HashMap;
import java.util.List;

import common.db.IDBEntity;

import deposito.vos.UDocEnUiDepositoVO;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.TablaTemporalFondosVO;

/**
 * Metodos de recuperacion y almacenamiento en la base de datos de la
 * informacion referente a las signaturas de las que se compone una unidad
 * documental y la ubicacion de cada una de estas signaturas en el depósito
 * físico. <br>
 * Entidad: <b>ASGDUDOCENUI</b>
 */
public interface IUDocEnUiDepositoDbEntity extends IDBEntity {

	public void insertUDocEnUiDeposito(UDocEnUiDepositoVO uDocEnUiDepositoVO);

	public void setIDEnCF(String idUnidadDocumental, String idElementoCF);

	public List getUDocsVOXIdUInstalacion(String idUInstalacion);

	/**
	 * Obtiene el conjunto de signaturas en las que se descompone una unidad
	 * documental
	 *
	 * @param idUnidadDocumental
	 *            Identificador de unidad documental en el cuadro de
	 *            clasificación de fondos documentales
	 * @return Lista de signaturas {@link UDocEnUiDepositoVO}
	 */
	public List getPartesUdocByIDElementoCF(String idUnidadDocumental);

	List getIdsUIsPartesUdocByIDElementoCF(String idUnidadDocumental);

	/**
	 * Obtiene el conjunto de signaturas en las que se descompone una unidad
	 * documental
	 *
	 * @param idUnidadDocumental
	 *            Identificador de unidad documental en la relación de entrega
	 *            mediante la que fue transferida al archivo
	 * @return Lista de signaturas {@link UDocEnUiDepositoVO}
	 */
	public List getPartesUdocByIDUdocEnRelacionEntrega(String idUnidadDocumental);

	public void bloquearUDoc(String idUdoc, String signaturaUdoc);

	/**
	 * Borra las entradas en la tabla de una unidad de instalacion
	 * dada(identificada por su id de udoc)
	 *
	 * @param idUdoc
	 *            Identificador de la udoc que se desea eliminar está duplicado.
	 *            Utilizar deleteUdoc(String iduinstalacion, String idUdoc,
	 *            String signaturaudoc);
	 */
	public abstract void deleteUdoc(String idUdoc);

	/**
	 * Borra las entradas en la tabla de una unidad de instalacion
	 * dada(identificada por su id de udoc y por su signatura)
	 *
	 * @param iduinstalacion
	 *            Identificador de la unidad de Instalación
	 * @param idUdoc
	 *            Identificador de la udoc que se desea eliminar
	 * @param signaturaudoc
	 *            Signatura de la unidad Documental.
	 */
	public abstract void deleteUdoc(String iduinstalacion, String idUdoc,
			String signaturaudoc);

	/**
	 * Recupera una unidad documental instalada en una determinada posición
	 * dentro de una unidad de instalacion
	 *
	 * @param idUInstalacion
	 *            Identificador de unidad de instalación instalada en un hueco
	 *            del depósito físico
	 * @param posUdoc
	 *            Posición de unidad documental dentro de la unidad de
	 *            instalación
	 */
	public UDocEnUiDepositoVO getUdocEnUI(String idUInstalacion, int posUdoc);

	/**
	 * Recupera una unidad documental instalada en una unidad de instalacion
	 *
	 * @param idUInstalacion
	 *            Identificador de unidad de instalación instalada en un hueco
	 *            del depósito físico
	 * @param idUdoc
	 *            Identificador de la unidad documental
	 */
	public UDocEnUiDepositoVO getUdocEnUIById(String idUInstalacion,
			String idUdoc);

	public List getUDocsVOValidadasXIdUInstalacion(String idUInstalacion);

	/**
	 * Recupera las signaturas que contiene una unidad de instalación del
	 * depósito físico
	 *
	 * @param idUInstalacion
	 *            Identificador de unidad de instalación
	 * @return Lista de signaturas que contiene la unidad de instalación
	 *         {@link UDocEnUiDepositoVO}
	 */
	public List getUDocsVOXId(String[] idUdocs);

	/**
	 * Recupera las Unidades Documentales de por Id y signatura
	 *
	 * @param idUInstalacion
	 *            Identificador de unidad de instalación
	 * @param signaturas
	 *            Signaturas de las unidades documentales.
	 * @return Lista de signaturas que contiene la unidad de instalación
	 *         {@link UDocEnUiDepositoVO}
	 */
	public List getUDocsVOXId(String[] idUdocs, String[] signaturas);

	/**
	 * Actualiza la signatura y la identificacion de la unidad documental en la
	 * caja
	 *
	 * @param idUI
	 * @param id
	 *            Identificador de la Unidad Documental
	 * @param signatura
	 *            Signatura de la Unidad Documental
	 * @param nuevaSignatura
	 *            Nueva Signatura de la unidad Documental
	 * @param nuevaIdentificacion
	 *            Nueva Identificación de la Unidad Docuemntal
	 * @param posUdoc
	 *            Posición de la unidad documental
	 * @param idNuevaUI
	 *            Identificador de la nueva unidad de instalación
	 */
	public void updateSignaturaEIdentificacionYPosUdocYIdUIns(String idUI,
			String id, String signatura, String nuevaSignatura,
			String nuevaIdentificacion, int posUdoc, String idNuevaUI);

	public void updateIdentificacion(
			ElementoCuadroClasificacionVO elementoCuadroClasificacionVO,
			String idsUdoc);

	public void updateIdentificacion(String codRefFondo, String idUdoc);

	public void updateIdentificacion(String codRefFondo,
			TablaTemporalFondosVO tablaTemporalFondosVO) throws Exception;

	void updateIdentificacion(String codRefFondo, List idsUdoc) throws Exception;

	public void updateSignaturaEIdentificacionYPosUdoc(String idUI,
			String idUnidadDoc, String signatura, String nuevaSignatura,
			String nuevaIdentificacion, int posUdoc);

	/**
	 * Actualiza la posición de una Unidad Documental dentro de una caja.
	 *
	 * @param udoc
	 */
	public void updatePosicionUDoc(UDocEnUiDepositoVO udoc);

	/**
	 * Actualiza la signatura y la identificacion
	 *
	 * @param udoc
	 *            Unidad Documental
	 */
	public void updateIdentificacionYSignaturaUDoc(UDocEnUiDepositoVO udoc);

	/**
	 * Devuelve una Lista de UnidadInstalacionVO
	 *
	 * @param idUnidadInstalacion
	 *            Id de la Unidad de Instalación
	 * @return Lista de UnidadInstalacionVO
	 */
	// public List getUdocsUInst(String idUnidadInstalacion);

	/**
	 * Obtiene una lista de Unidades Documentales de una Relación Entre Arhivos
	 *
	 * @param idRelacion
	 *            Identificador de la Relación
	 * @return Lista de UnidadDocumentalVO
	 */
	public List getUdocsXidRelacionEntreArchivos(String idRelacion);

	/**
	 * Obtiene una lista de Unidades Documentales de una Unidad de Instalación
	 *
	 * @param idRelacion
	 *            Identificador de la Relación
	 * @return Lista de UnidadDocumentalVO
	 */
	public List getUdocsXIdUinstalacion(String idUnidadInstalacion);

	/**
	 * Obtiene las partes de las unidades documentales que faltan en la relación
	 * Para evitar que en relaciones entre archivos se envíen unidades
	 * documentales por partes pero incompletas.
	 *
	 * @param idRelacion
	 *            Identificador de la Relación entre Archivos
	 * @param idUdocsRelacion
	 *            String[] ids de las unidades documentales de la relación
	 * @param idsUinstRelacion
	 *            String[] ids de las unidades de instalación de la relación
	 * @return Lista de UDocEnUiDepositoVO
	 */
	public List getPartesUdocsNoIncluidasEnRelacion(String idRelacion,
			List idsUdocsRelacion, List idsUinstRelacion);

	/**
	 * Obtiene las unidades documentales a las cuales les falta alguna parte en
	 * una relación
	 *
	 * @param idsUdocs
	 *            Ids de Unidades de Documental Ids de las Unidades Incompletas
	 * @param idsUInst
	 *            Ids de las Unidades de Instalación de la Relación
	 * @return List de UDocEnUiDepositoVO
	 */
	public List getPartesUdocsIncompletasEnRelacion(List idsUdocs, List idsUInst);

	public boolean isBloqueada(String idUnidadDocumental);

	public UDocEnUiDepositoVO getUdocByIDUdocEnDivisionFS(
			String idUdocEnDivisionFS);

	/**
	 * Obtiene el conjunto de signaturas en las que se descompone una unidad
	 * documental
	 *
	 * @param idUnidadDocumental
	 *            Identificador de unidad documental en la división de fracción
	 *            de serie mediante la que fue transferida al archivo
	 * @return Lista de signaturas {@link UDocEnUiDepositoVO}
	 */
	public List getPartesUdocByIDUdocEnDivisionFS(String idUDoc);

	HashMap getUINumPartesUdocs(TablaTemporalFondosVO tablaTemporalFondosVO);

	List checkValidMoverUdocsMismoFondo(List idsElementosAMover);

	public void updateDescripcionUDoc(UDocEnUiDepositoVO udoc);

	public UDocEnUiDepositoVO getUDocByIdAndSignatura(String idUdoc,
			String signaturaUdoc);

	/**
	 * Obtiene las unidades documentales del deposito a partir de las unidades
	 * en el cuadro de la relacion de entrega
	 *
	 * @param idsUdocRe
	 * @return
	 */
	public List getUDocsEnUIByUdocsRe(String[] idsUdocRe);

	/**
	 * Borra las entradas de la tabla de unidades de instalacion por los
	 * identificadores de unidades documentales
	 *
	 * @param idsUdoc
	 *            identificadores de las unidades documentales
	 */
	public abstract void deleteByIdsUdoc(String[] idsUdoc);

	/**
	 * Elimina las entradas que se corresponden con el identificador de unidad
	 * de instalación pasado como parámetro
	 *
	 * @param idUi
	 *            Identificador de la unidad de instalación
	 */
	public void deleteByIdUi(String idUi);

	/**
	 * Obtiene la lista de unidades documentales pertenecientes a una unidad de
	 * instalación del depósito.
	 *
	 * @param idUinstalacion
	 * @return Lista de {@link UDocEnUiDepositoVO}
	 */
	public List getUdocsEnUIWithAsuntoAndExpediente(String idUinstalacion);

	/**
	 * Obtiene el número de unidades de instalación de una serie.
	 *
	 * @param idSerie
	 *            Identificador de la Serie.
	 * @return
	 */
	public int getCountUnidadesInstalacionByIdSerie(String idSerie);

	/**
	 * Obtiene el Volumen en centímetros de Una Serie
	 *
	 * @param idSerie
	 *            Identificador de la Serie
	 * @return
	 */
	public double getVolumenSerie(String idSerie);

	/**
	 * Obtiene el número de unidades documentales que están en una unidad de
	 * instalación donde hay más unidades documentales que no están incluidas
	 * dentro de los identificadores
	 *
	 * @param idsElementos
	 *            Array que contiene las cadenas con los identificadores de los
	 *            elementos seleccionados
	 * @return Número de unidades documentales
	 */
	public int getCountUInstalacionIncompletas(String[] idsElementos);

	/**
	 * Obtiene el número de unidades documentales que están en una unidad de
	 * instalación donde hay más unidades documentales que no están incluidas
	 * dentro de los identificadores
	 *
	 * @param idsElementos
	 *            Array que contiene las cadenas con los identificadores de los
	 *            elementos seleccionados
	 * @return Lista de Elementos {@link UDocEnUiDepositoVO}
	 */
	public List getUInstalacionIncompletas(String[] idsElementos);

	/**
	 * Obtiene una unidad documental por su identificador de unidad documental
	 *
	 * @param idUnidadDoc
	 * @return {@link UDocEnUiDepositoVO}
	 */
	public UDocEnUiDepositoVO getUdocByIdUnidadDoc(String idUnidadDoc);

}