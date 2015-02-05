package ieci.tdw.ispac.services.vo;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbQuery;
import ieci.tdw.ispac.services.db.DbInitializable;
import ieci.tdw.ispac.services.dto.OrganoProductor;

import java.util.Date;



/**
 * Información de un órgano productor.
 */
public class OrganoProductorVO extends OrganoProductor implements DbInitializable {

	private static final long serialVersionUID = 8031906121504349059L;

	/**
	 * Constructor.
	 */
	public OrganoProductorVO() {
		super();
	}
	
	/**
	 * Constructor.
	 * @param id Identificador del órgano.
	 * @param inicioProduccion Fecha desde la que el órgano es productor.
	 */
	public OrganoProductorVO(String id, Date inicioProduccion) {
		this();
		setId(id);
		setInicioProduccion(inicioProduccion);
	}

	public void init(DbQuery dbq) throws ISPACException {
		
		// En invesDoc, la unidad tramitadora es un departamento y
		// el id viene precedido de: "2-"
		String id = dbq.getString("ID_UNIDAD_TRAMITADORA");
		if ((id != null) && (id.startsWith("2-"))) {
			id = id.substring(2);
		}
		
		setId(id);
		setInicioProduccion(dbq.getDate("FECHA_INI_PROD"));
	}

}
