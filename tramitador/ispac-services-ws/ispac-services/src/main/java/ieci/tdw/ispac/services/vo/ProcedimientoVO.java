package ieci.tdw.ispac.services.vo;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbQuery;
import ieci.tdw.ispac.services.db.DbInitializable;
import ieci.tdw.ispac.services.dto.Procedimiento;


/**
 * Información de un procedimiento.
 */
public class ProcedimientoVO extends Procedimiento implements DbInitializable {
	
	private static final long serialVersionUID = 5347128453715661989L;

	/**
	 * Constructor.
	 */
	public ProcedimientoVO() {
		super();
	}

	public void init(DbQuery dbq) throws ISPACException {
		getInformacionBasica().setCodigo(dbq.getString("COD_PCD"));
		getInformacionBasica().setNombre(dbq.getString("NOMBRE"));
		getInformacionBasica().setCodSistProductor(dbq.getString("COD_SISTEMA_PRODUCTOR"));
		getInformacionBasica().setNombreSistProductor(dbq.getString("NOMBRE_SISTEMA_PRODUCTOR"));
		getInformacionBasica().setId(dbq.getString("COD_PCD"));
		setDocumentosBasicos(dbq.getString("DOCUMENTACION"));
		setNormativa(dbq.getString("NORMATIVA"));
		setObjeto(dbq.getString("OBJETO"));
		setTramites(dbq.getString("DSCR_TRAM"));
	}
}