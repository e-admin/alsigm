package fondos.actions;

import common.bi.ServiceRepository;
import common.view.POUtils;

import fondos.vos.SolicitudSerieExtended;

public class SolicitudSerieExtendedPO extends SolicitudSerieExtended {

	ServiceRepository rep = null;

	public SolicitudSerieExtendedPO(ServiceRepository rep,
			SolicitudSerieExtended solicitud) {
		POUtils.copyVOProperties(this, solicitud);
		this.rep = null;
	}
}
