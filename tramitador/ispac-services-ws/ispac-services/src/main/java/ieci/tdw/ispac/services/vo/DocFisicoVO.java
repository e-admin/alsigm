package ieci.tdw.ispac.services.vo;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbQuery;
import ieci.tdw.ispac.services.db.DbInitializable;
import ieci.tdw.ispac.services.dto.DocFisico;

/**
 * Información de un documento físico.
 */
public class DocFisicoVO extends DocFisico implements DbInitializable {

	private static final long serialVersionUID = 834894198643924370L;

	/**
	 * Constructor.
	 */
	public DocFisicoVO() {
		super();
	}

	public void init(DbQuery dbq) throws ISPACException {
		setTipoDocumento(dbq.getString("NOMBRE"));
		setAsunto(dbq.getString("DESCRIPCION"));
	}
}
