/**
 *
 */
package common.bi;

import java.util.List;

import common.util.IntervalOptions;

import transferencias.exceptions.ParteUnidadDocumentalNoEliminable;
import transferencias.exceptions.RelacionEntregaConUDocsSinAsingarAUIException;
import transferencias.exceptions.RelacionEntregaNoEnviableUIsConVariasFSException;
import transferencias.exceptions.RelacionEntregaNoEnviableUIsConVariasUDocsException;
import transferencias.exceptions.UnidadDocumentalNoPermitidaDivisionException;
import transferencias.exceptions.UnidadInstalacionConUnidadesDocumentalesException;
import transferencias.vos.UDocEnUIReeaCRVO;
import transferencias.vos.UIReeaCRVO;

/**
 * Gestion de Relaciones Entre Archivos con Reencajado
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public interface GestionRelacionesEACRBI {

	/**
	 * Se encarga de crear las unidades de instalación junto con las unidades
	 * documentales del reencajado con el nuevo formato seleccionado.
	 * 
	 * @param idRelEntrega
	 * @param listaUIs
	 * @param idFormato
	 */
	public void crearUIsReeaConReencajadoNoTransaccional(String idRelEntrega,
			List listaUIs, String idFormato);

	/**
	 * Activa El Reencajado
	 * 
	 * @param idRelEntrega
	 *            Identificador de la Relación de Entrega
	 * @param idFormato
	 *            Identificador
	 */
	public void activarReencajado(String idRelEntrega, String idFormato);

	/**
	 * Cancela los cambios referentes al reencajado iniciando una transaccion
	 * 
	 * @param idRelEntrega
	 */
	public void cancelarReencajadoTransaccional(String idRelEntrega);

	/**
	 * Cancela los cambios referentes al reencajado sin iniciar transaccion se
	 * utiliza para cuando se llamao desde otro Servicio que ya tiene su propia
	 * transacción.a
	 * 
	 * @param idRelEntrega
	 */
	public void cancelarReencajadoNoTransaccional(String idRelEntrega);

	/**
	 * Añade una nueva unidadad de instalación a la pestaña de reencajado.
	 * 
	 * @param idRelEntrega
	 *            Identificador de la Relación de Entrega
	 * @param idFormato
	 *            Identificador del Formato Nuevo de la Caja.
	 */
	public UIReeaCRVO addUIReencajado(String idRelEntrega, String idFormato);

	/**
	 * Crea una unidad de instalación vacía en la transferencia entre archivos
	 * con reencajado.
	 * 
	 * @param idRelEntrega
	 *            Identificador de la Relación de Entrega
	 * @param idFormatoRe
	 *            Identificador del Formato del Reencajado
	 * @param udocs
	 *            Identificadores de las unidades de instalación
	 */
	public UIReeaCRVO crearNuevaUICR(String idRelEntrega, String idFormatoRe,
			String[] udocs);

	/**
	 * Elimina una unidad de instalación vacía en la transferencia entre
	 * archivos con reencajado.
	 * 
	 * @param idRelEntrega
	 *            Identificador de la Relación de Entrega
	 * @param idUiReeaCR
	 *            Identificador de la Unidad de Instalación con Reencajado.
	 * @throws UnidadInstalacionConUnidadesDocumentalesException
	 */
	public void eliminarUICRVacia(String idRelEntrega, String idUiReeaCR)
			throws UnidadInstalacionConUnidadesDocumentalesException;

	/**
	 * 
	 * @param idRelEntrega
	 *            Identificador de la Relación de Entrega
	 * @param idUI
	 *            Identificador de la Unidad de Instalación
	 * @param idsUdocs
	 *            Identificador de las Unidades Documentales Afectadas
	 */
	public void subirUdocsEnUIReeaCR(String idRelEntrega, String idUI,
			String[] idsUdocs);

	/**
	 * @param idRelEntrega
	 *            Identificador de la Relación de Entrega
	 * @param idUI
	 *            Identificador de la Unidad de Instalación
	 * @param idsUdocs
	 *            Identificadores de las unidades docuementales.
	 */
	public void bajarUdocsEnUI(String idRelEntrega, String idUI,
			String[] idsUdocs);

	/**
	 * Obtiene las unidades documentales que están sin unidad de instalación en
	 * una relación de entrega entre archivos con reencajado.
	 * 
	 * @param idRelEntrega
	 *            Identificador de la relación de entrega.
	 * @return Lista de {@link UDocEnUIReeaCRVO}
	 */
	public List getUDocsSinUIReeaCR(String idRelEntrega);

	/**
	 * Incorpora las unidades seleccionadas a la Unidad de Instalación
	 * 
	 * @param idUI
	 *            Identificador de la unidad de instalación
	 * @param udocs
	 *            Identificador de las unidades documentales
	 */
	public void incorporarUdocsAUIReeaCR(String idRelEntrega, String idUI,
			String[] udocs);

	/**
	 * Extrae las unidades documentales seleccionadas de la unidad de
	 * instalación
	 * 
	 * @param idUI
	 *            Identificador de la unidad de instalación
	 * @param idUDoc
	 *            Identificador de las unidades documentales seleccionadas.
	 */
	public void extraerUDocsDeUIReeaCR(String idRelEntrega, String idUI,
			String[] idUDoc);

	/**
	 * Divide una unidad documental
	 * 
	 * @param idUI
	 *            Identificador de la unidad de instalación
	 * @param idUDoc
	 *            Identificador de la unidad documental
	 * @param idFormatoRe
	 *            Identificador del Formato del Reencajado.
	 * @throws UnidadDocumentalNoPermitidaDivisionException
	 */
	public void dividirUDocReeaCR(String idRelEntrega, String idUI,
			String idUDoc, String idFormatoRe)
			throws UnidadDocumentalNoPermitidaDivisionException;

	/**
	 * Elimina un parte de una unidad documental
	 * 
	 * @param idUI
	 *            Identificador de la unidad de instalación
	 * @param idUDoc
	 *            Identificador de la unidad documental
	 * @throws ParteUnidadDocumentalNoEliminable
	 */
	public void eliminarPartesUDocReeaCR(String idUI, String[] idUDoc)
			throws ParteUnidadDocumentalNoEliminable;

	/**
	 * Comprueba si una relación de entrega entre archivos con reencajado puede
	 * ser enviada. Se comprueba lo siguiente: - Todas las unidades documentales
	 * deberán estar asociadas a una unidad de instalación - Si el formato es no
	 * multidocumento, por cada unidad de instalación solo deberá existir una
	 * unidad documental
	 * 
	 * @param idRelacion
	 *            Identificador de la Relación de Entrega
	 * @return true si cumple los requisitos anteriores
	 */
	public void checkPermtirEnviarRelacionEACR(String idRelEntrega)
			throws RelacionEntregaNoEnviableUIsConVariasFSException,
			RelacionEntregaNoEnviableUIsConVariasUDocsException,
			RelacionEntregaConUDocsSinAsingarAUIException;

	/**
	 * Obtiene la lista de unidades de instalación del reencajado para dicha
	 * relación
	 * 
	 * @param idRelacion
	 *            Identificador de la relación de entrega entre archivos
	 * @return Lista de objetos de tipo {@link UIReeaCRVO}
	 */
	public List getUIsReencajado(String idRelEntrega);

	/**
	 * Obtiene la unidades de Instalación de una relacion entre archivos con
	 * reencajado
	 * 
	 * @param idRelacionEntrega
	 *            Identificador de la relación de entrega.
	 * @return Lista de {@link UnidadInstalacionReeaVO}
	 */
	public List getUIsReencajado(String idRelEntrega, IntervalOptions ordenes);

	/**
	 * Obtiene la lista de unidades documentales de la unidad de instalación de
	 * reencajado
	 * 
	 * @param idUI
	 *            Identificador de la unidad de instalación
	 * @return Lista de objetos de tipo {@link UDocEnUIReeaCRVO}
	 */
	public List getUDocsByUIReencajado(String idUI);

	/**
	 * Obtiene la lista de unidades documentales de la relación sin asignar a
	 * una caja
	 * 
	 * @param idRelacion
	 *            Identificador de la relación
	 * @return Lista de objetos de tipo {@link UDocEnUIReeaCRVO}
	 */
	public List getUDocsSinAsignarByIdRelacion(String idRelEntrega);

	/**
	 * Obtiene la lista de unidades documentales de la relación de entrega
	 * 
	 * @param idRelacion
	 *            Identificador de la relación
	 * @return Lista de objetos de tipo {@link UDocEnUIReeaCRVO}
	 */
	public List getUDocsByIdRelacion(String idRelEntrega);

	/**
	 * Obtiene al unidad de instalación del reencajado por su identificador
	 * 
	 * @param idUIReeacr
	 *            identificador de la unidad de instalación de reencajado
	 * @return Objeto de tipo {@link UIReeaCRVO}
	 */
	public UIReeaCRVO getUIReencajadoById(String idUIReeacr);

	/**
	 * Obtiene el número de unidades de instalación de la relación de entrega
	 * entre archivos con reencajado
	 * 
	 * @param idRelacion
	 *            Identificador de la relación de entrega
	 * @return
	 */
	public int getCountUIs(String idRelacion);

	/**
	 * Obtiene el número de unidades de instalación con estado pendiente de
	 * cotejo
	 * 
	 * @param idRelacion
	 * @return
	 */
	public int getCountUIsEstadoPendiente(String idRelacion);

	/**
	 * Obtiene la unidad documental de la caja de reencajado por su
	 * identificador
	 * 
	 * @param idUdocEnUI
	 *            Identificador de la unidad documental de la caja de reencajado
	 * @return Objeto de tipo {@link UDocEnUIReeaCRVO}
	 */
	public UDocEnUIReeaCRVO getUdocEnUIById(String idUdocEnUI);

	/**
	 * Obtiene el numero de unidades de instalación de la relación con
	 * reencajado
	 * 
	 * @param idRelEntrega
	 *            Identificador de la relación
	 * @return Numero de unidades de instalacion
	 */
	public int getNumUIsReeaCr(String idRelEntrega);

	/**
	 * Actualiza la descripción de la unidad documental seleccionada
	 * 
	 * @param idUdoc
	 *            identificador de la unidad documental
	 * @param descripcion
	 *            Descripcion que se le asigna a la unidad documental
	 */
	public void updateDescripcionUdoc(String idUdoc, String descripcion);

	/**
	 * Actualiza la descripción de la unidad de instalación seleccionada
	 * 
	 * @param idUI
	 *            Identificador de la unidad de instalación
	 * @param descripcion
	 *            Descripción que se le asigna a la unidad de instalación
	 */
	public void updateDescripcionUI(String idUI, String descripcion);

	/**
	 * Actualiza la signatura de la unidad de instalación
	 * 
	 * @param idUI
	 *            Identificador de la unidad de instalación
	 * @param signaturaUI
	 */
	public void updateSignaturaUI(String idUI, String signaturaUI);

	/**
	 * Actualiza la signatura de las unidades reencajadas de una unidad de
	 * instalacion
	 * 
	 * @param idUI
	 *            Identificador de la unidad de instalacion
	 */
	public void updateSignaturaUdocsByIdUinstalacion(String idUI);

	/**
	 * Actualiza el estado de cotejo de la unidad de instalación
	 * 
	 * @param idUI
	 *            Identificador de la unidad de instalación
	 * @param estado
	 *            Nuevo estado de cotejo
	 */
	public void updateEstadoCotejo(String idUI, int estado);

	/**
	 * Añade las unidades de instalación al reencajado
	 * 
	 * @param idRelEntrega
	 *            Identificador de la relación de entrega
	 * @param idsUIs
	 *            Identficador de las Unidades de Instalación.
	 * @param idFormatoRe
	 *            Identificador del Formato de Reencajado
	 */
	public void addUIReencajadoNoTransaccional(String idRelEntrega,
			List listaUIs, String idFormatoRe);

	/**
	 * Elmina las unidades de instalación de una relación entre archivos con
	 * reencajado
	 * 
	 * @param idRelEntrega
	 *            Identificador de la relación de entrega
	 * @param idsUIS
	 *            Identificadores de las unidades de instalación a eliminar.
	 */
	public void eliminarUIsReencajadoNoTransaccional(String idRelEntrega,
			String[] idsUIS);

	/**
	 * Crea los datos correspondientes en las siguientes tablas: -
	 * ASGDUINSTALACION - ASGDUDOCNEUI Actualiza los huecos ocupados por la
	 * relación de entrega con el id correspondiente de la unidad de instalación
	 * creada.
	 * 
	 * @param idRelEntrega
	 *            Identificador de la Relación de Entrega
	 */
	public void crearDatosEnDepositoNoTransaccional(String idRelEntrega);

	/**
	 * Actualizar el estado de las unidades de instalación
	 * 
	 * @param ids
	 *            Identificadores de las unidades de instalación
	 */
	public void marcarUIsRevisadas(String[] ids);

	/**
	 * Obtiene las unidades de instalacion por los ordenes indicados.
	 * 
	 * @param idRelacionEntrega
	 * @param ordenes
	 * @return
	 */
	public List getUnidadesInstalacion(String idRelacionEntrega,
			IntervalOptions ordenes);

	/**
	 * Obtiene las unidades documentales de agrupadas por unidadoc
	 * 
	 * @param idRelacion
	 * @return
	 */
	public List getUDocsByIdRelacionGroupByUnidadDoc(String idRelacion);
}
