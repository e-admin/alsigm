package deposito.actions;

import org.apache.commons.collections.Transformer;

import common.bi.ServiceRepository;

import deposito.vos.ElementoNoAsignableVO;

/**
 * Utilidad para la transformación de los value objects para elemento del fondo
 * físico de tipo Deposito en sus correspondientes objetos de presentación
 */
public class TransformerToEspacioEnDepositoPO implements Transformer {

	ServiceRepository services = null;
	String formato = null;

	public TransformerToEspacioEnDepositoPO(ServiceRepository services,
			String formato) {
		this.services = services;
		this.formato = formato;
	}

	public Object transform(Object obj) {
		return new EspacioEnDepositoPO(services, (ElementoNoAsignableVO) obj,
				formato);
	}

	public static TransformerToEspacioEnDepositoPO getInstance(
			ServiceRepository services, String formato) {
		return new TransformerToEspacioEnDepositoPO(services, formato);
	}
}
