package valoracion.view;

import org.apache.commons.collections.Transformer;

import common.bi.GestionSeriesBI;
import common.bi.GestionValoracionBI;
import common.bi.ServiceRepository;
import common.view.POUtils;

/**
 * Transforma value objects de eliminacion en objetos para presentacion
 */
public class EliminacionToPO implements Transformer {
	private GestionValoracionBI gvBI = null;
	private GestionSeriesBI gsBI = null;
	private ServiceRepository serviceRepository = null;

	EliminacionToPO(GestionSeriesBI gsBI, GestionValoracionBI gvBI,
			ServiceRepository serviceRepository) {
		this.gsBI = gsBI;
		this.gvBI = gvBI;
		this.serviceRepository = serviceRepository;
	}

	public Object transform(Object vo) {
		EliminacionPO po = new EliminacionPO(gsBI, gvBI, serviceRepository);
		POUtils.copyVOProperties(po, vo);

		return po;
	}

	public static EliminacionToPO getInstance(ServiceRepository services) {
		return new EliminacionToPO(services.lookupGestionSeriesBI(),
				services.lookupGestionValoracionBI(), services);
	}
}