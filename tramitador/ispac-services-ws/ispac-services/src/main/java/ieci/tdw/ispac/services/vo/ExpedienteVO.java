package ieci.tdw.ispac.services.vo;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbQuery;
import ieci.tdw.ispac.services.db.DbInitializable;
import ieci.tdw.ispac.services.dto.Expediente;

/**
 * Información de un expediente.
 */
public class ExpedienteVO extends Expediente implements DbInitializable {

	private static final long serialVersionUID = 2027034167163536893L;

	/**
	 * Constructor.
	 */
	public ExpedienteVO() {
		super();
	}

	public void init(DbQuery dbq) throws ISPACException {
		getInformacionBasica().setId(dbq.getString("NUMEXP"));
		getInformacionBasica().setNumExp(dbq.getString("NUMEXP"));
		getInformacionBasica().setDatosIdentificativos(dbq.getString("ASUNTO"));
		setFechaInicio(dbq.getDate("FAPERTURA"));
		setFechaFinalizacion(dbq.getDate("FCIERRE"));
		setIdOrgProductor(dbq.getString("IDUNIDADTRAMITADORA"));
		setNombreOrgProductor(dbq.getString("UTRAMITADORA"));
		setAsunto(dbq.getString("ASUNTO"));
	}

}
