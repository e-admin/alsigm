package ieci.tdw.ispac.ispaclib.invesicres.registro.api;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.invesicres.registro.vo.DireccionVO;
import ieci.tdw.ispac.ispaclib.invesicres.registro.vo.TerceroVO;

/**
 * @author RAULHC
 *
 */
public interface ITerceros {

	public TerceroVO getTercero(String strId) throws ISPACException;
	public DireccionVO getDireccion(String strId) throws ISPACException;
}
