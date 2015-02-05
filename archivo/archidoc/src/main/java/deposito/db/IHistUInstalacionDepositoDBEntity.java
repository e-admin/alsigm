package deposito.db;

import java.util.List;

import common.db.IDBEntity;

import deposito.vos.BusquedaHistUInstDepositoVO;
import deposito.vos.HistUInstDepositoVO;

/**
 * Entidad: <b>ASGDHISTUINSTALACION</b>
 * 
 * @author IECISA
 * 
 */
public interface IHistUInstalacionDepositoDBEntity extends IDBEntity {
	/**
	 * Inserta un registro en la tabla ASGDHISTUINSTALACION
	 * 
	 * @param histUInstDepositoVO
	 *            Datos de la Unidad de Instalación Historica
	 */
	public void insert(HistUInstDepositoVO histUInstDepositoVO);

	/**
	 * Obtiene las Unidades de Instalación filtrados por los campos del vo
	 * 
	 * @param histUInstDepositoVO
	 *            Filtros de la búsqueda de Unidad de Instalación Historica
	 * @return Lista de {@link HistUInstDepositoVO}
	 */
	public List find(BusquedaHistUInstDepositoVO busquedaHistUInstDepositoVO);

	/**
	 * Obtiene la Unidad de Instalación Instalación por el Identificador
	 * 
	 * @param id
	 *            Identificador de la Unidad de Instalación Histórica
	 * @return Unidad de Instalacion Histórica
	 */
	public HistUInstDepositoVO getById(String id);
}
