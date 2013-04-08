package fondos.db;

import ieci.core.exception.IeciTdException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.db.IDBEntity;
import common.exceptions.TooManyResultsException;
import common.vos.ConsultaConnectBy;

import deposito.vos.BusquedaUIAnioSerieVO;
import deposito.vos.UIAnioSerieVO;
import fondos.vos.BusquedaUdocsVO;
import fondos.vos.DocumentoAntecedenteVO;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.TablaTemporalFondosVO;
import fondos.vos.UnidadDocumentalVO;
import fondos.vos.VolumenSerieVO;

/**
 * Métodos de acceso a datos referidos a unidad documental incorporada al cuadro
 * de clasificación. <br>
 * Entidad: <b>ASGFUNIDADDOC</b>
 */
public interface IUnidadDocumentalDbEntity extends IDBEntity {
	public UnidadDocumentalVO insertUnidadDocumental(
			UnidadDocumentalVO unidadDocumental);

	/**
	 * Recupera las unidades documentales que se corresponden con los números de
	 * expediente indicados
	 * 
	 * @param numeroExpediente
	 *            Conjunto de números de expediente
	 * @return Lista de unidades documentales {@link UnidadDocumentalVO}
	 */
	public List getUnidadDocumentalXNumeroExpediente(String[] numeroExpediente);

	/**
	 * Obtiene la información de una unidad documental en función del
	 * identificador del elemento del cuadro de clasificación.
	 * 
	 * @param id
	 *            Identificador del elemento del cuadro de clasificación.
	 * @return Unidad documental.
	 */
	public UnidadDocumentalVO getUnidadDocumental(String id);

	UnidadDocumentalVO getTuplaUnidadDocumental(String id);

	HashMap getUdocNumCambiosVolumenSerie(List idsUDos);

	/**
	 * 
	 * @param ids
	 * @return Lista de @link UnidadDocumentalVO
	 */
	public List getUnidadesDocumentales(String[] ids);

	/**
	 * Realiza el borrado de una unidad documental dada por su identificador.
	 * 
	 * @param id
	 *            Identificador de la unidad documental a eliminar.
	 */
	public void deleteUnidadDocumental(String id);

	/**
	 * Cuenta el número de unidades documentales que contiene un fondo
	 * 
	 * @param idFondo
	 *            Identificador de fondo
	 * @return Número de unidades documentales que contiene el fondo
	 */
	public int countUdocsEnFondo(String idFondo);

	/**
	 * Obtiene los documentos antecedentes de un tercero.
	 * 
	 * @param idTercero
	 *            Identificador de tercero.
	 * @param idSerie
	 *            Identificador de la serie.
	 * @return Documentos antecedentes ({@link DocumentoAntecedenteVO}).
	 */
	public List getDocumentosAntecedentes(String idTercero, String idSerie);

	/**
	 * Obtiene la lista de unidades documentales con acceso restringido que han
	 * sobrepasado la fecha de restricción.
	 * 
	 * @return Lista de unidades documentales.
	 */
	public List getUnidadesDocumentalesPublicacion();

	/**
	 * Busqueda de unidades documentales
	 * 
	 * @param vo
	 * @return
	 * @throws TooManyResultsException
	 */
	public List findUnidadesDocumentales(BusquedaUdocsVO vo)
			throws TooManyResultsException;

	/**
	 * Obtiene la lista de unidades documentales prestadas que no han sido
	 * devueltas tras dos reclamaciones.
	 */
	public List getUnidadesDocumentalesPrestadasNoDevueltas();

	public void updateSerieUDoc(String nuevaSerie, String[] idsUdocs);

	public void updateSerieUDoc(
			ElementoCuadroClasificacionVO elementoCuadroClasificacionVO,
			String[] idsUdocs);

	void updateSerieFondoUDocs(List idsUdocs);

	public void updateSerieFondoUDocs(
			ElementoCuadroClasificacionVO elementoCuadroClasificacionVO,
			TablaTemporalFondosVO tablaTemporal);

	public int getNumUdocsUISerie(String idSerie, String idUnidadInstalacion);

	/**
	 * Obtiene la lista de números de expedientes que están en el cuadro de
	 * clasificación de la lista que se pasa como parámetro
	 * */
	public List findNumerosExpediente(String[] numerosExpediente);

	/**
	 * Actualiza un Map de campos de la tabla de Unidades Documentales en el
	 * cuadro de clasificación
	 * 
	 * @param idUnidadDocumental
	 * @param columnsToUpdate
	 */

	public void updateFieldsUDocCF(String idUnidadDocumental,
			final Map columnsToUpdate);

	public List getRangosUDoc(String idUDoc, String idCampoInicial,
			String idCampoFinal);

	/**
	 * Actualiza los campos de la tabla de Unidades Documentales en el cuadro de
	 * clasificación que es necesario modificar durante la validación
	 * 
	 * @param idUnidadDocumental
	 * @param columnsToUpdate
	 */
	public void updateFieldsUDocCFVEA(UnidadDocumentalVO uDoc);

	/**
	 * Actualiza el numero de Expediente de la unidad documental
	 * 
	 * @param idUnidadDocumental
	 * @param numExpediente
	 */
	public void updateNumeroExpediente(String idUnidadDocumental,
			String numExpediente);

	/**
	 * Permite establecer la marca de bloqueo de una unidad documental
	 * 
	 * @param udocID
	 *            Identificador de unidad documental
	 * @param marca
	 *            valor de la marca a establecer
	 */
	public void updateMarcaBloqueo(String udocID, int marca);

	/**
	 * Realiza el borrado de las unidades documentales
	 * 
	 * @param idsUdoc
	 *            Identificadores de las unidades documentales a eliminar.
	 */
	public void deleteUnidades(String[] idsUdoc);

	/**
	 * Obtiene las Unidades de Instalación que cumplen los criterios de la
	 * Búsqueda
	 * 
	 * @param busquedaUIAnioSerieVO
	 * @return Lista de {@link UIAnioSerieVO}
	 * @throws IeciTdException
	 * @throws TooManyResultsException
	 */
	public List getUnidadesInstalacionPorAniosYSerie(
			ConsultaConnectBy consultaConnectBy) throws IeciTdException,
			TooManyResultsException;

	/**
	 * Obtiene el objeto {@link ConsultaConnectBy} correspondiente a la búsqueda
	 * de unidades de instalación por año y serie.
	 * 
	 * @param busquedaUIAnioSerieVO
	 * @return
	 * @throws IeciTdException
	 */
	public ConsultaConnectBy getConsultaConnectByUnidadesInstalacionPorAniosYSerie(
			BusquedaUIAnioSerieVO busquedaUIAnioSerieVO) throws IeciTdException;

	/**
	 * Obtiene el volumen y soporte de la serie
	 * 
	 * @param idSerie
	 *            Identificador de la Serie
	 * @return Lista de {@link VolumenSerieVO}
	 */
	public List getCalculoVolumenYSoporteByIdSerie(String idSerie);

	/**
	 * Obtiene el número de unidades documentales de una serie.
	 * 
	 * @param idSerie
	 *            Identificador de la Serie
	 * @return Número de Unidades Documentales de la Serie.
	 */
	public int getCountUDocsByIdSerie(String idSerie);

}