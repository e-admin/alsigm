package deposito.actions.deposito;

import org.apache.commons.collections.Transformer;

import common.bi.ServiceRepository;
import common.view.POUtils;

public class DepositoElectronicoToPO implements Transformer {

	/** Repositorio de servicios */
	ServiceRepository services = null;

	/**
	 * Constructor.
	 * 
	 * @param services
	 *            Repositorio de servicios
	 */
	protected DepositoElectronicoToPO(ServiceRepository services) {
		this.services = services;
	}

	/**
	 * Crea una instancia de la clase.
	 * 
	 * @param services
	 *            Repositorio de servicios
	 * @return Instancia de la clase.
	 */
	public static DepositoElectronicoToPO getInstance(ServiceRepository services) {
		return new DepositoElectronicoToPO(services);
	}

	/**
	 * Transforma un objeto en otro.
	 * 
	 * @param vo
	 *            Objeto origen.
	 * @return Objeto transformado.
	 */
	public Object transform(Object vo) {
		DepositoElectronicoPO po = new DepositoElectronicoPO(services);
		POUtils.copyVOProperties(po, vo);
		return po;
	}
}