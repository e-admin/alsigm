package fondos.db;

import java.util.Date;
import java.util.List;

import common.db.IDBEntity;

import fondos.vos.ProductorSerieVO;

/**
 * Productores de las series del cuadro de clasificación de fondos documentales. <br>
 * Entidad: <b>ASGFPRODSERIE</b>
 */
public interface IProductorSerieDbEntity extends IDBEntity {
	public List getProductoresXSerie(String idSerie);

	public ProductorSerieVO getProductorXId(String idSerie, String idProductor);

	public ProductorSerieVO getProductorXIdSerieYIdProcYIdProductorYTipo(
			final String idSerie, final String idProc,
			final String idProductor, final Integer tipo);

	public List getProductoresXIdSerieYIdProc(final String idSerie,
			final String idProc);

	/**
	 * Recupera los productores de una serie
	 * 
	 * @param idSerie
	 *            Identificador de serie documental
	 * @param procedimiento
	 *            Procedimiento al que debe corresponder la documentación
	 *            producida para la serie
	 * @return Lista de productores
	 */
	public List getProductoresXIdSerie(String idSerie, String procedimiento);

	/**
	 * Recupera los productores de una serie ordenados por fecha
	 * 
	 * @param idSerie
	 *            Identificador de serie documental
	 * @param procedimiento
	 *            Procedimiento al que debe corresponder la documentación
	 *            producida para la serie
	 * @param orderByDate
	 *            Indica si se ordena o no por fecha
	 * @return Lista de productores
	 */
	public List getProductoresXIdSerie(String idSerie, String procedimiento,
			boolean orderByDate);

	public List getProductoresVigentesBySerie(String idSerie);

	public List getProductoresHistoricosBySerie(String idSerie);

	public ProductorSerieVO getProductorVigenteBySerie(String idSerie,
			String idProductor);

	public ProductorSerieVO getProductorHistoricoBySerie(String idSerie,
			String idProductor);

	public ProductorSerieVO getProductorSerieVO(String idSerie,
			String idProcedimiento, String idProductor);

	/**
	 * Recupera los productores de un clasificador de series
	 * 
	 * @param idClfSeries
	 *            Identificador del clasificador de series
	 * @return Lista de productores
	 */
	public List getProductoresXIdClasificadorSeries(String idClfSeries);

	public ProductorSerieVO insertProductorVGSerieVO(
			final ProductorSerieVO productorVO);

	public void updateIdProcOrgProductor(final ProductorSerieVO productorVO,
			final String newIdProcedimiento);

	public void updateOrgProductor(final ProductorSerieVO productorVO);

	public void delete(ProductorSerieVO productorVGVO);

	public List getProcedimientosXProductor(String idProductor,
			int tipoProductor);

	/**
	 * Elimina los productores vigentes que han sido definidos para una serie
	 * 
	 * @param idSerie
	 *            Identificador de serie
	 */
	public void deleteProductoresVigentesSerie(String idSerie);

	/**
	 * Elimina los productores que han sido definidos para una serie
	 * 
	 * @param idSerie
	 *            Identificador de serie
	 */
	public void deleteProductoresSerie(String idSerie);

	/**
	 * Establece / actualiza la lista de control de acceso del productor de una
	 * serie
	 * 
	 * @param idSerie
	 * @param idListaControlAcceso
	 */
	public void setListaControlAcceso(String idProductor, String idSerie,
			String idListaControlAcceso);

	/**
	 * Obtiene el productor de la serie a partir de su id y el id del productor
	 * 
	 * @param idSerie
	 * @param idProductor
	 * @return
	 */
	public ProductorSerieVO getProductorXIdSerieEIdDescriptorOrgano(
			String idSerie, String idProductor);

	public List getProductoresSerieXIdProductor(String idProductor);

	public void delete(String idSerie, String idProductor);

	public List getProductoresVigentesOriginalesBySerie(String idSerie);

	public List getProductoresHistoricosBySerieFechaFinalMayorQueFecha(
			String idSerie, Date date);

	public void updateByIdSerieIdProductor(ProductorSerieVO productorVO);

	public void setMarcaProductores(String idSerie, int marca);

	/**
	 * Obtiene los Ids de las Series que tiene un productor
	 * 
	 * @param idProductor
	 *            Identificador del Productor
	 * @param tipoProductor
	 *            Tipo de Productor.
	 * @return
	 */
	public List getIdsSeriesXProductor(String idProductor, int tipoProductor);

	public int countProductoresByIdListaAcceso(String idListaAcceso);

	/**
	 * Obtiene un productor serie por su nombre e identificador de la serie.
	 * 
	 * @param idSerie
	 * @param nombreProductor
	 * @return
	 */
	public ProductorSerieVO getProductorXIdSerieYNombreProductor(
			String idSerie, String nombreProductor);

}