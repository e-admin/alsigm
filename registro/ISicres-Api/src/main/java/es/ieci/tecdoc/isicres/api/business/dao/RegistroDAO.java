package es.ieci.tecdoc.isicres.api.business.dao;

import es.ieci.tecdoc.isicres.api.business.vo.IdentificadorRegistroVO;

public interface RegistroDAO {

	public String getNumRegistroById(IdentificadorRegistroVO id);
	
}
