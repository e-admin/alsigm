package transferencias.actions;

import org.apache.commons.collections.Transformer;

import common.bi.ServiceRepository;

import deposito.vos.DepositoVO;

/**
 * Utilidad para la transformación de los value objects para elemento del fondo
 * físico de tipo Ubicación en sus correspondientes objetos de presentación
 */
public class TransformerToEspacioEnUbicacionPO implements Transformer {

	ServiceRepository services = null;
	String formato = null;

	public TransformerToEspacioEnUbicacionPO(ServiceRepository services,
			String formato) {
		this.services = services;
		this.formato = formato;
	}

	public Object transform(Object obj) {
		return new EspacioEnUbicacionPO(services, (DepositoVO) obj, formato);
	}

	public static TransformerToEspacioEnUbicacionPO getInstance(
			ServiceRepository services, String formato) {
		return new TransformerToEspacioEnUbicacionPO(services, formato);
	}
}
