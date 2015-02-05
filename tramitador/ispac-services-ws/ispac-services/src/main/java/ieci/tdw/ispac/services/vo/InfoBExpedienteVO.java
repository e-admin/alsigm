package ieci.tdw.ispac.services.vo;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbQuery;
import ieci.tdw.ispac.services.db.DbInitializable;
import ieci.tdw.ispac.services.dto.InfoBExpediente;

/**
 * Información básica de un expediente.
 */
public class InfoBExpedienteVO extends InfoBExpediente implements
		DbInitializable {

	private static final long serialVersionUID = -6258509065924337951L;

	/**
	 * Constructor.
	 */
	public InfoBExpedienteVO() {
		super();
	}

	public void init(DbQuery dbq) throws ISPACException {
		setId(dbq.getString("NUMEXP"));
		setNumExp(dbq.getString("NUMEXP"));
		setDatosIdentificativos(dbq.getString("ASUNTO"));
	}

}
