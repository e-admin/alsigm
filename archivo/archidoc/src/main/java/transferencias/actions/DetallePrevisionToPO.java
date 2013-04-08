package transferencias.actions;

import org.apache.commons.collections.Transformer;

import se.procedimientos.GestorCatalogo;
import se.procedimientos.exceptions.GestorCatalogoException;

import common.bi.GestionArchivosBI;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionPrevisionesBI;
import common.bi.GestionSeriesBI;
import common.bi.ServiceRepository;
import common.exceptions.UncheckedArchivoException;
import common.view.POUtils;

import deposito.model.GestorEstructuraDepositoBI;

class DetallePrevisionToPO implements Transformer {
	GestionSeriesBI serieBI = null;
	GestionCuadroClasificacionBI cclfBI = null;
	GestorCatalogo gestorCatalogo = null;
	GestorEstructuraDepositoBI depositoBI = null;
	GestionArchivosBI archivosBI = null;
	GestionPrevisionesBI previsionBI = null;

	DetallePrevisionToPO(GestionSeriesBI serieBI,
			GestionCuadroClasificacionBI cclfBI, GestorCatalogo gestorCatalogo,
			GestorEstructuraDepositoBI depositoBI,
			GestionArchivosBI archivosBI, GestionPrevisionesBI previsionBI) {
		this.serieBI = serieBI;
		this.cclfBI = cclfBI;
		this.gestorCatalogo = gestorCatalogo;
		this.depositoBI = depositoBI;
		this.archivosBI = archivosBI;
		this.previsionBI = previsionBI;
	}

	public Object transform(Object vo) {
		DetallePrevisionPO po = new DetallePrevisionPO(serieBI, cclfBI,
				gestorCatalogo, depositoBI, archivosBI, previsionBI);
		POUtils.copyVOProperties(po, vo);
		return po;
	}

	public static DetallePrevisionToPO getInstance(ServiceRepository services) {
		try {
			return new DetallePrevisionToPO(services.lookupGestionSeriesBI(),
					services.lookupGestionCuadroClasificacionBI(),
					services.lookupGestorCatalogo(),
					services.lookupGestorEstructuraDepositoBI(),
					services.lookupGestionArchivosBI(),
					services.lookupGestionPrevisionesBI());
		} catch (GestorCatalogoException gce) {
			throw new UncheckedArchivoException(gce);
		}
	}
}