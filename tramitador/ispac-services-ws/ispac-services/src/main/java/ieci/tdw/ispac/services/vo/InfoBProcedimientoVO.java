package ieci.tdw.ispac.services.vo;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbQuery;
import ieci.tdw.ispac.services.db.DbInitializable;
import ieci.tdw.ispac.services.dto.InfoBProcedimiento;

/**
 * Información básica de un procedimiento.
 */
public class InfoBProcedimientoVO extends InfoBProcedimiento implements
		DbInitializable {

	private static final long serialVersionUID = 500290971075799177L;

	/**
	 * Constructor.
	 */
	public InfoBProcedimientoVO() {
		super();
	}

	public void init(DbQuery dbq) throws ISPACException {
		setCodigo(dbq.getString("COD_PCD"));
		setNombre(dbq.getString("NOMBRE"));
		setCodSistProductor(dbq.getString("COD_SISTEMA_PRODUCTOR"));
		setNombreSistProductor(dbq.getString("NOMBRE_SISTEMA_PRODUCTOR"));
		setId(dbq.getString("COD_PCD"));
	}

}
