package fondos.db;

import java.util.List;

import common.db.IDBEntity;

import fondos.vos.VolumenSerieVO;

/**
 * Interfaz para el acceso a los datos de los volúmenes de una serie.
 */
public interface IVolumenSerieDBEntity extends IDBEntity {
	/**
	 * Obtiene el volumen de una serie.
	 * 
	 * @param idSerie
	 *            Identificador de una serie.
	 * @param tipoDocumental
	 *            Tipo documental.
	 * @return Volumen de una serie.
	 */
	public VolumenSerieVO getVolumenSerie(String idSerie, String tipoDocumental);

	/**
	 * Obtiene la lista de volúmenes de una serie.
	 * 
	 * @param idSerie
	 *            Identificador de una serie.
	 * @return Lista de volúmenes de una serie.
	 */
	public List getVolumenesSerie(String idSerie);

	/**
	 * Incrementa el número de unidades documentales de un determinado tipo
	 * documental que contiene una serie del cuadro de clasificación de fondos
	 * 
	 * @param idSerie
	 *            Identificador de serie
	 * @param tipoDocumental
	 *            Tipo documental
	 * @param incrementNumUdocs
	 *            Número en el que se incrementa el volumen de la serie
	 */
	public void incrementVolumenSerie(String idSerie, String tipoDocumental,
			int incrementNumUdocs);

	/**
	 * Inserta o actualiza el volumen y soporte de una serie
	 * 
	 * @param volumenSerieVO
	 */
	public void insertVolumenSerie(final VolumenSerieVO volumenSerieVO);

	/**
	 * Elimina la Información de volumen y soporte de una serie.
	 * 
	 * @param idSerie
	 */
	public void deleteVolumenesByIdSerie(String idSerie);
}