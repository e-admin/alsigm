package fondos.actions;

import org.apache.commons.collections.Transformer;

import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionFraccionSerieBI;
import common.bi.GestionUnidadDocumentalBI;
import common.bi.ServiceRepository;
import common.view.POUtils;

public class UDocEnFraccionSerieToPO implements Transformer {
	GestionUnidadDocumentalBI udocBI = null;
	GestionFraccionSerieBI fraccionSerieBI = null;
	GestionCuadroClasificacionBI cuadroBI = null;

	public UDocEnFraccionSerieToPO(ServiceRepository services) {
		this.udocBI = services.lookupGestionUnidadDocumentalBI();
		this.fraccionSerieBI = services.lookupGestionFraccionSerieBI();
		this.cuadroBI = services.lookupGestionCuadroClasificacionBI();
	}

	public Object transform(Object vo) {
		UDocEnFraccionSeriePO po = new UDocEnFraccionSeriePO(udocBI,
				fraccionSerieBI, cuadroBI);
		POUtils.copyVOProperties(po, vo);
		return po;
	}

	public static UDocEnFraccionSerieToPO getInstance(ServiceRepository services) {
		return new UDocEnFraccionSerieToPO(services);
	}

}