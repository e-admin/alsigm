/**
 *
 */
package transferencias.db;

import java.util.List;

import transferencias.vos.UDocEnUIReeaCRVO;
import transferencias.vos.UnidadDocumentalVO;

import common.db.IDBEntity;
import common.vos.MinMaxVO;

/**
 * Entidad: <b>ASGTUDOCENUIREEACR</b>
 * 
 * @author IECISA
 * 
 */
public interface IUDocEnUIReeaCRDBEntity extends IDBEntity {

	/**
	 * Obtiene la unidad documental por su id
	 * 
	 * @param idUDoc
	 *            Identificador de la unidad documental
	 * @return
	 */
	public UDocEnUIReeaCRVO getById(String idUDoc);

	/**
	 * Obtiene las unidades documentales de una unidad de instalación
	 * 
	 * @param idUI
	 *            Identificador de la Unidad de Instalación
	 * @return Lista de {@link UDocEnUIReeaCRVO}
	 */
	public List getByIdUI(String idUI);

	/**
	 * Obtiene las unidades documentales de pertenecientes a una unidad
	 * documental
	 * 
	 * @param idRelEntrega
	 *            Identificador de la Relación de Entrega
	 * @param idUnidadDoc
	 *            Identificador de la unidad documental
	 * @return Lista de {@link UDocEnUIReeaCRVO}
	 */
	public List getByIdUDoc(String idRelEntrega, String idUnidadDoc);

	/**
	 * Elimina la unidad documental por su identificador
	 * 
	 * @param idUDoc
	 *            Identificador de la Unidad Documental
	 */
	public void deleteById(String idUDoc);

	/**
	 * Elimna las unidades documentales pertenecientes a una unidad de
	 * instalación
	 * 
	 * @param idUI
	 *            Identificador de la Unidad de Instalación
	 */
	public void deleteByIdUI(String idUI);

	/**
	 * Elimina un las unidades de una relación de entrega
	 * 
	 * @param idRelacion
	 *            Identificador de la relación de entrega.
	 */
	public void deleteByIdRelacion(String idRelacion);

	/**
	 * Inserta un registro en la tabla.
	 * 
	 * @param uDocEnUIReeaCRVO
	 *            Datos a Insertar.
	 */
	public void insert(UDocEnUIReeaCRVO uDocEnUIReeaCRVO);

	/**
	 * Actualiza el campo POSUDOCENUI de la unidad documental.
	 * 
	 * @param idUdoc
	 *            Identificador de la Unidad Documental
	 * @param posicion
	 *            Posicion a establecer
	 */
	public void updatePosicion(String idUdoc, int posicion);

	/**
	 * Obtiene las unidades documentales que no están asignadas a ninguna unidad
	 * documental
	 * 
	 * @param idRelacion
	 *            Identificador de la Relación de Entrega entre Archivos con
	 *            Reinstalación
	 * @return Lista de {@link UDocEnUIReeaCRVO}
	 */
	public List getUDocsSinUI(String idRelEntrega);

	public List getUDocsSinUIOrderBySignatura(String idRelEntrega);

	/**
	 * Obtiene el número de unidades documentales que no están asignadas a
	 * ninguna unidad documental
	 * 
	 * @param idRelacion
	 *            Identificador de la Relación de Entrega entre Archivos con
	 *            Reinstalación
	 * @return Lista de {@link UDocEnUIReeaCRVO}
	 */
	public int getCountUDocsSinUI(String idRelEntrega);

	/**
	 * Obtiene el Máximo y el Mínimo de
	 * 
	 * @param idUI
	 *            Identificador de la unidad de instalación
	 * @param idsUdocs
	 *            Identificador de las Unidades Documentales
	 */
	public MinMaxVO getPosicionesMinMax(String idUI, String[] idsUdocs);

	/**
	 * Actualiza el campo posicion, con el incremente especificado, puede ser
	 * negativo.
	 * 
	 * @param idUI
	 * @param idsUdocs
	 * @param incremento
	 */
	public void updatePosiciones(String idUI, String[] idsUdocs,
			final int incremento);

	/**
	 * Actualiza la posición del elemento
	 * 
	 * @param idUI
	 *            Identificador de la Unidad de Instalación
	 * @param idsUdocsSeleccionadas
	 *            Identificadores de las unidades documentales seleccionadas
	 * @param posicionAnterior
	 *            Posicion Anterior
	 * @param posicionNueva
	 *            Posicion Nueva a establecer
	 */
	public void updatePosicionElemento(String idUI,
			String[] idsUdocsSeleccionadas, int posicionAnterior,
			int posicionNueva);

	/**
	 * Obtiene el número de partes en las que está dividida una unidad
	 * documental.
	 * 
	 * @param idRelEntrega
	 *            Identificador de la Relación de Entrega
	 * @param idUnidadDoc
	 *            IDUNIDADDOC de la unidad documental.
	 * @return
	 */
	public int getCountPartes(String idRelEntrega, String idUnidadDoc);

	/**
	 * Actualiza el identificador de la caja y la posición en la misma
	 * 
	 * @param idUdoc
	 *            Identificador de la unidad documental
	 * @param idUI
	 *            Identificador de la unidad de instalación
	 */
	public void updateUIReaCRAndPosUdoc(String idUdoc, String idUI);

	/**
	 * Actualiza la signatura de la unidad reencajada
	 * 
	 * @param idUdoc
	 *            Identificador de la unidad reencajada
	 * @param signatura
	 *            Signatura a actualizar
	 */
	public void updateSignatura(String idUdoc, String signatura);

	/**
	 * Obtiene las unidades documentales de la relación de entrega que no están
	 * en ninguna caja
	 * 
	 * @param idRelacion
	 *            Identificador de la Relación
	 * @return Lista de {@link UDocEnUIReeaCRVO}
	 */
	public List getUdocsSinAsignarByIdRelacion(String idRelEntrega);

	/**
	 * Permite obtener una lista de unidades documentales en el cuadro a partir
	 * de la unidad de instalación
	 * 
	 * @param idUiCr
	 *            identificador de la unidad de instalacion reencajada
	 * @return Lista de {@link UnidadDocumentalVO}
	 */
	public List getUdocsXIdUinstalacion(String idUiCr);

	/**
	 * Obtiene las unidades documentales de la relación de entrega
	 * 
	 * @param idRelacion
	 *            Identificador de la Relación
	 * @return Lista de {@link UDocEnUIReeaCRVO}
	 */
	public List getByIdRelacion(String idRelEntrega);

	/**
	 * Obtiene el número de unidades documentales de una unidad de instalación
	 * de reencajado
	 * 
	 * @param idUIReeaCr
	 *            Identificador de la unidad de instalación de reencajado
	 * @return Número de Unidades Documentales.
	 */
	public int getCountUDocsUI(String idUIReeaCr);

	/**
	 * Obtiene las unidades documentales de la relación de entrega
	 * 
	 * @param idRelEntrega
	 *            Identificador de la relación
	 * @return Número de unidades documentales
	 */
	public int getCountUDocs(String idRelEntrega);

	/**
	 * Actualiza la descripcion de la unidad documental
	 * 
	 * @param idUdoc
	 *            Identificador de la unidad documental
	 * @param descripcion
	 */
	public void updateDescripcion(String idUdoc, String descripcion);

	/**
	 * Obtiene el número de parte correspondiente para la relación de entrega
	 * actual
	 * 
	 * @param idRelEntrega
	 *            Identificador de la Relación de Entrega
	 * @param idunidaddoc
	 *            Identificador de la Unidad Documental
	 * @return Número de parte correspondiente
	 */
	public int getNumParteSiguiente(String idRelEntrega, String idunidaddoc);

	/**
	 * Obtiene el número de partes en las que se compone una unidad de
	 * documental
	 * 
	 * @param idRelEntrega
	 *            Identificador de la relación de entrega
	 * @param idUndiadDoc
	 *            Identificador de la unidad documental
	 * @param idsEliminar
	 * @return Número total de Partes
	 */
	public int getTotalPartesRestantes(String idRelEntrega, String idUnidadDoc,
			String[] idsEliminar);

	/**
	 * Actualiza el valor del campo udoccompleta
	 * 
	 * @param idRelEntrega
	 *            Identificador de la Relación de entrega
	 * @param idunidaddoc
	 *            Identificador de la unidad documental
	 * @param udocCompleta
	 *            campo a actualizar.
	 */
	public void updateIndicadorCompleta(String idRelEntrega,
			String idunidaddoc, boolean udocCompleta);

	/**
	 * Actualiza el campo numParte
	 * 
	 * @param idRelEntrega
	 *            Identificador de la relación de entreaga
	 * @param id
	 *            Identificador
	 * @param numParte
	 *            Numero de Parte
	 */
	public void updateNumParte(String id, int numParte, boolean udocCompleta);

	/**
	 * Elimina las unidades documentales por sus ids.
	 * 
	 * @param idsDoc
	 *            Identificadores de las unidades documentales a eliminar.
	 */
	public void deleteByIds(String[] idsDoc);

	/**
	 * Obtiene el número de partes pertencientes a las unidades de instalación
	 * origen.
	 * 
	 * @param idRelEntrega
	 * @param idsUIs
	 */
	public List getPartesUDocByIdsUinstalacion(String idRelEntrega,
			String[] idsUIs);

	/**
	 * Obte
	 * 
	 * @param idRelEntrega
	 * @return
	 */
	public List getUDocsByIdRelacionOrderBySignatura(String idRelEntrega);

	/**
	 * Obtiene las Unidades Documentales de una Transnferencia entre archivos
	 * con reencajado
	 * 
	 * @param idRelacion
	 *            Identificador de la Relación de Entrega
	 * @return List de {@link UDocEnUIReeaCRVO}
	 */
	public List getUDocsByIdRelacionGroupByUnidadDoc(String idRelacion);

}
