package ieci.tdw.ispac.services.vo;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbQuery;
import ieci.tdw.ispac.services.db.DbInitializable;
import ieci.tdw.ispac.services.dto.DocElectronico;

/**
 * Información de un documento electrónico.
 */
public class DocElectronicoVO extends DocElectronico implements DbInitializable {

	private static final long serialVersionUID = 2761403183660690614L;

	/**
	 * Constructor.
	 */
	public DocElectronicoVO() {
		super();
	}

	public void init(DbQuery dbq) throws ISPACException {
		setTipoDocumento(dbq.getString("NOMBRE"));
		setAsunto(dbq.getString("DESCRIPCION"));
		setRepositorio(dbq.getString("REPOSITORIO"));
		setLocalizador(dbq.getString("INFOPAG_RDE"));
		setExtension(dbq.getString("EXTENSION_RDE"));
	}

}
