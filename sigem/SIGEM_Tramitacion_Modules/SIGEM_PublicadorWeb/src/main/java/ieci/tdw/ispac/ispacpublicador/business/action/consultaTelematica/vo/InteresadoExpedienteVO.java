package ieci.tdw.ispac.ispacpublicador.business.action.consultaTelematica.vo;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbQuery;

/**
 * Información del interesado principal incluido en el expediente.
 *
 */
public class InteresadoExpedienteVO extends InteresadoVO {

	/**
	 * Constructor.
	 *
	 */
	public InteresadoExpedienteVO() {
		super();
		setCprincipal("S");
	}

    public void init(DbQuery dbq) throws ISPACException {
		if (dbq != null) {
			if (dbq != null) {
				setCnumexp(dbq.getString("NUMEXP"));
				setCnif(dbq.getString("NIFCIFTITULAR"));
				setCnom(dbq.getString("IDENTIDADTITULAR"));
				setCinfoaux(null);
			}
		}
    }

}
