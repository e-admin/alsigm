package ieci.tdw.ispac.services.vo;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbQuery;
import ieci.tdw.ispac.services.db.DbInitializable;
import ieci.tdw.ispac.services.dto.Interesado;

/**
 * Información de un interesado.
 */
public class InteresadoVO extends Interesado implements DbInitializable {
	
	private static final long serialVersionUID = -2877921366302671131L;

	/**
	 * Constructor.
	 */
	public InteresadoVO() {
		super();
	}

	public void init(DbQuery dbq) throws ISPACException {
		setNombre(dbq.getString("NOMBRE"));
		setIdEnTerceros(dbq.getString("ID_EXT"));
		setNumIdentidad(dbq.getString("NDOC"));
		setRol(dbq.getString("SUSTITUTO"));
	}

}
