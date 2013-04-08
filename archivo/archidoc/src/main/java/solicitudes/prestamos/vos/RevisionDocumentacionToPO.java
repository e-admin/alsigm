package solicitudes.prestamos.vos;

import java.util.Locale;

import org.apache.commons.collections.Transformer;

import common.bi.GestionControlUsuariosBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.ServiceRepository;
import common.view.POUtils;

public class RevisionDocumentacionToPO implements Transformer {
	GestionControlUsuariosBI controlAccesoBI = null;
	GestionRelacionesEntregaBI relacionBI = null;
	Locale locale = null;

	RevisionDocumentacionToPO(Locale locale,
			GestionControlUsuariosBI controlAccesoBI,
			GestionRelacionesEntregaBI relacionBI) {
		this.controlAccesoBI = controlAccesoBI;
		this.relacionBI = relacionBI;
		this.locale = locale;
	}

	public Object transform(Object vo) {
		RevisionDocumentacionPO po = new RevisionDocumentacionPO(locale,
				controlAccesoBI, relacionBI);
		POUtils.copyVOProperties(po, vo);
		return po;
	}

	public static RevisionDocumentacionToPO getInstance(Locale locale,
			ServiceRepository services) {
		return new RevisionDocumentacionToPO(locale,
				services.lookupGestionControlUsuariosBI(),
				services.lookupGestionRelacionesBI());
	}
}
