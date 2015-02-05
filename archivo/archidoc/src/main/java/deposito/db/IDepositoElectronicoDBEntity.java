package deposito.db;

import java.util.List;

import common.db.IDBEntity;

import deposito.vos.DepositoElectronicoVO;

/**
 * Interfaz para el acceso a los depósitos electrónicos. <br>
 * Entidad: <b>ASGDDEPOSITOELECTRONICO</b>
 */
public interface IDepositoElectronicoDBEntity extends IDBEntity {
	/**
	 * Obtiene la lista de depósitos electrónicos.
	 * 
	 * @return Lista de depósitos electrónicos ({@link DepositoElectronicoVO}).
	 */
	public List getDepositosElectronicos();

	/**
	 * Obtiene la información de un depósito electrónico.
	 * 
	 * @param id
	 *            Identificador del depósito electrónico.
	 * @return Depósito electrónico.
	 */
	public DepositoElectronicoVO getDepositoElectronico(String id);

	/**
	 * Obtiene la información de un depósito electrónico.
	 * 
	 * @param idExt
	 *            Identificador externo del depósito electrónico.
	 * @return Depósito electrónico.
	 */
	public DepositoElectronicoVO getDepositoElectronicoByIdExt(String idExt);

	/**
	 * Crea un depósito electrónico.
	 * 
	 * @param deposito
	 *            Información del depósito electrónico.
	 * @return Depósito electrónico creado.
	 */
	public DepositoElectronicoVO insertDepositoElectronico(
			DepositoElectronicoVO deposito);

	/**
	 * Modifica la información de un depósito electrónico.
	 * 
	 * @param deposito
	 *            Información del depósito electrónico.
	 */
	public void updateDepositoElectronico(DepositoElectronicoVO deposito);

	/**
	 * Elimina un depósito.
	 * 
	 * @param id
	 *            Identificador del depósito.
	 */
	public void deleteDepositoElectronico(String id);
}