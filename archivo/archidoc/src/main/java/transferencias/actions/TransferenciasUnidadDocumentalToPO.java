package transferencias.actions;

import org.apache.commons.collections.Transformer;

import common.bi.GestionRelacionesEntregaBI;
import common.bi.GestionUnidadDocumentalBI;
import common.bi.ServiceRepository;
import common.view.POUtils;

/**
 * Transformer para la conversion de value objects de unidad documental en
 * objetos de presentacion
 */
public class TransferenciasUnidadDocumentalToPO implements Transformer {

	static TransferenciasUnidadDocumentalToPO instance = null;

	GestionRelacionesEntregaBI relacionBI = null;
	GestionUnidadDocumentalBI udocBI = null;

	TransferenciasUnidadDocumentalToPO(GestionRelacionesEntregaBI relacionBI,
			GestionUnidadDocumentalBI udocBI) {
		this.relacionBI = relacionBI;
		this.udocBI = udocBI;
	}

	public Object transform(Object vo) {
		TransferenciasUnidadDocumentalPO po = new TransferenciasUnidadDocumentalPO(
				relacionBI, udocBI);
		POUtils.copyVOProperties(po, vo);
		return po;
	}

	public static TransferenciasUnidadDocumentalToPO getInstance(
			ServiceRepository services) {
		if (instance == null)
			instance = new TransferenciasUnidadDocumentalToPO(
					services.lookupGestionRelacionesBI(),
					services.lookupGestionUnidadDocumentalBI());
		return instance;
	}
}
