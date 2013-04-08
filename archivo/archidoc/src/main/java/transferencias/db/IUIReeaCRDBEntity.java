/**
 *
 */
package transferencias.db;

import java.util.List;

import transferencias.vos.UIReeaCRVO;

import common.db.IDBEntity;
import common.util.IntervalOptions;

/**
 * Entidad: <b>ASGTUIREEACR</b>
 * 
 * @author IECISA
 * 
 */
public interface IUIReeaCRDBEntity extends IDBEntity {

	/**
	 * Inserta el registro en la tabla
	 * 
	 * @param uiReeaCRVO
	 */
	public void insert(UIReeaCRVO uiReeaCRVO);

	/**
	 * Actualiza el registro de la tabla
	 * 
	 * @param uiReeaCRVO
	 */
	public void update(UIReeaCRVO uiReeaCRVO);

	/**
	 * Actualiza los valores relacionados con el cotejo
	 * 
	 * @param idUI
	 *            Identificador de la Unidad Documental
	 * @param estadoCotejo
	 *            Identificador del estado de cotejo
	 * @param notasCotejo
	 *            Notas del cotejo
	 * @param devolucion
	 *            Indicador de Devolución de Unidad de Instalación.
	 */
	public void updateCotejo(String idUI, Integer estadoCotejo,
			String notasCotejo, String devolucion);

	/**
	 * Actualiza el Orden de la Unidad de Instalación
	 * 
	 * @param idUI
	 *            Identificador de la Unidad de Instalación
	 * @param orden
	 *            Orden a establecer
	 */
	public void updateOrden(String idUI, Integer orden);

	/**
	 * Elimina el registro por su id
	 * 
	 * @param idRelacion
	 *            Identificador de la Relación de Entrega
	 * @param idUI
	 *            Identificador de la Unidad de Instalación
	 */
	public void delete(String idRelacion, String idUI);

	/**
	 * Elimina las Unidades de Instalación pertenecientes a una relación de
	 * entrega entre archivo con reencajado.
	 * 
	 * @param idRelacion
	 *            Identificador de la Relación de Entrega.
	 */
	public void deleteByIdRelacion(String idRelacion);

	/**
	 * Elimina las Unidades de Instalación pertenecientes a una relación de
	 * entrega entre archivo con reencajado, por sus ids de uideposito
	 * 
	 * @param idRelacion
	 *            Identificador de la Relación de Entrega.
	 * @param idsUIDeposito
	 *            Identificadores de las unidades a elminar.
	 */
	public void deleteByIdUIDeposito(String idRelacion, String[] idsUIDeposito);

	/**
	 * Obtiene la unidad de Instalación por su Identificador
	 * 
	 * @param id
	 */
	public UIReeaCRVO getById(String id);

	/**
	 * Obtiene la Lista de Unidades de Instalación de una relación de entrega.
	 * 
	 * @param idRelacion
	 *            Identifcador de la Relación de Entrega.
	 * @return Lista de objetos {@link UIReeaCRVO}
	 */
	public List getByIdRelacion(String idRelacion);

	/**
	 * Permite obtener una unidad de instalacion reencajada a partir de su
	 * signatura e identificador de archivo
	 * 
	 * @param signatura
	 *            Signatura de la unidad de instalacion
	 * @param idArchivo
	 *            Identificador del archivo
	 * @return {@link UIReeaCRVO} Unidad de instalacion reencajada
	 */
	public UIReeaCRVO fetchRowBySignaturaYArchivo(String signatura,
			String idArchivo);

	/**
	 * Permite obtener una unidad de instalacion reencajada a partir de su
	 * signatura e identificador de archivo en relación de entrega no validada
	 * 
	 * @param signatura
	 *            Signatura de la unidad de instalacion
	 * @param idArchivo
	 *            Identificador del archivo
	 * @return {@link UIReeaCRVO} Unidad de instalacion reencajada
	 */
	public UIReeaCRVO fetchRowBySignaturaYArchivoEnRENoValidada(
			String signatura, String idArchivo);

	public UIReeaCRVO fetchRowBySignaturaEnRENoValidada(String signatura);

	/**
	 * Permite obtener una unidad de instalacion reencajada a partir de su
	 * signatura
	 * 
	 * @param signatura
	 *            Signatura de la unidad de instalacion
	 * @return {@link UIReeaCRVO} Unidad de instalacion reencajada
	 */
	public UIReeaCRVO fetchRowBySignatura(String signatura);

	/**
	 * Obtiene el número de unidades que tienen alguno de los estados que se le
	 * pasan por parámetro.
	 * 
	 * @param idRelacion
	 *            Identificador de la Relación de Entrega
	 * @param estadoCotejo
	 *            Estados de Cotejo.
	 * @return
	 */
	public int getCountByEstadosCotejo(String idRelacion, int[] estadosCotejo);

	/**
	 * Obtiene el número de unidades documentales de una unidad de instalación
	 * 
	 * @param idUI
	 *            Identificador de la unidad de instalación
	 * @return Número de Unidades Documentales.
	 */
	public int getCountUDocs(String idRelacion, String idUI);

	/**
	 * Obtiene las unidades de instalación no multidoc, que tienen asignadas más
	 * de una unidad documental
	 * 
	 * @param idRelEntrega
	 *            Identificador de la Relación de Entrega
	 * @return Número de unidades de instalación
	 */
	public int getCountUIsWithUDocsCorrectas(String idRelEntrega);

	/**
	 * Obtiene el número de unidades de instalación no multidoc, que tienen
	 * asignadas más de una unidad documental
	 * 
	 * @param idRelEntrega
	 *            Identificador de la Relación de Entrega
	 * @return Lista de {@link UIReeaCRVO}
	 */
	public List getUIsWithUDocsCorrectas(String idRelEntrega);

	/**
	 * Obtiene el número de unidades de instalación de la relación de entrega
	 * entre archivos con reencajado.
	 * 
	 * @param idRelacion
	 *            Identificador de la Relación de Entrega
	 * @return Número de Unidades de Instalacion de la relación de entrega.
	 */
	public int getCountByIdRelacion(String idRelacion);

	/**
	 * Obtiene el numero de unidades de instalación de la relación
	 * 
	 * @param idRelEntrega
	 *            Identificador de la relación
	 * @return Numero de unidades de instalación
	 */
	public int getCountByRelacion(String idRelEntrega);

	/**
	 * Actualiza la descripción de la unidad de instalación
	 * 
	 * @param idUI
	 *            Identificador de la unidad de instalación
	 * @param descripcion
	 */
	public void updateDescripcion(String idUI, String descripcion);

	/**
	 * Obtiene la lista de unidades de instalación de la relación que están
	 * vacías.
	 * 
	 * @param idRelEntrega
	 *            Identificador de la relación de entrega
	 * @return Lista de objetos {@link UIReeaCRVO}
	 */
	public List getUIsVacias(String idRelEntrega);

	/**
	 * Actualiza la signatura de la unidad de instalación
	 * 
	 * @param idUI
	 *            Identificador de la unidad de instalación
	 * @param signaturaUI
	 */
	public void updateSignatura(String idUI, String signaturaUI);

	/**
	 * Actualiza el estado de las unidades de instalación seleccionadas
	 * 
	 * @param idsUI
	 *            Identficadores de las unidades de instalación
	 * @param estado
	 * @param devuelta
	 */
	public void updateFieldEstado(String[] idsUI, int estado, boolean devuelta);

	/**
	 * Actualiza el estado de cotejo de la unidad de instalación indicada
	 * 
	 * @param idUI
	 *            Identificador de la unidad de instalación
	 * @param estado
	 *            Estado
	 */
	public void updateFieldEstado(String idUI, int estado);

	/**
	 * Obtiene las unidades de instalación por el orden para la generación de
	 * cartelas
	 * 
	 * @param idRelacion
	 * @param ordenes
	 * @return
	 */
	public List getByIdRelacion(String idRelacion, IntervalOptions ordenes);

	/**
	 * Actualiza la unidad de instalación reencajada con el identificador de
	 * unidad de instalacion en deposito asignado
	 * 
	 * @param idUI
	 * @param idUIDeposito
	 */
	public void updateFieldIdUIDeposito(String idUI, String idUIDeposito);

	public UIReeaCRVO fetchRowBySignaturaYArchivoEnRENoValidadaOtraRelacion(
			String signatura, String idArchivoReceptor, String idRelacion);

	public UIReeaCRVO fetchRowBySignaturaEnRENoValidadaOtraRelacion(
			String signatura, String idRelacion);
}