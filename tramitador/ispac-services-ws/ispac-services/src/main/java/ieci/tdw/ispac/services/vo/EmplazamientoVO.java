package ieci.tdw.ispac.services.vo;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbQuery;
import ieci.tdw.ispac.services.db.DbInitializable;
import ieci.tdw.ispac.services.dto.Emplazamiento;

/**
 * Información de un emplazamiento.
 */
public class EmplazamientoVO extends Emplazamiento implements DbInitializable {

	private static final long serialVersionUID = 2491712457305555338L;

	/**
	 * Constructor.
	 */
	public EmplazamientoVO() {
		super();
	}

	public void init(DbQuery dbq) throws ISPACException {
		setRegionpais(dbq.getString("REGIONPAIS"));
		setConcejo(dbq.getString("MUNICIPIO"));
		setLocalizacion(dbq.getString("LOCALIZACION"));
		setPoblacion(dbq.getString("POBLACION"));
	}

	private void setRegionpais(String regionPais){
		if ((regionPais != null) && (regionPais.trim().length() > 0)) {
			String[] tokens = regionPais.split("/");
			if (tokens.length>0) {
				setComunidad(tokens[0].trim());
			}
			if (tokens.length>1) {
				setPais(tokens[1].trim());
			}
		}
	}

}
