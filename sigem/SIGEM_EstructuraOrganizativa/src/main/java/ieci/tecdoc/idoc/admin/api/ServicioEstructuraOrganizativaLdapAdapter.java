package ieci.tecdoc.idoc.admin.api;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.sbo.config.CfgDefs;
import ieci.tecdoc.sbo.config.CfgLdapConfig;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.EstructuraOrganizativaException;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.ServicioEstructuraOrganizativaLdap;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.DatosUsuario;

public class ServicioEstructuraOrganizativaLdapAdapter implements ServicioEstructuraOrganizativaLdap{

	public DatosUsuario getDatosUsuarioServicio(String id, String user) throws EstructuraOrganizativaException {
		if (id == null)
			return null;
		
		DatosUsuario poUsuario = new DatosUsuario();
		
		poUsuario.setEmail(null);
		poUsuario.setId(id);
		poUsuario.setLastname(null);
		poUsuario.setName(null);
		poUsuario.setPassword(null);
		poUsuario.setUser(user);
		
		return poUsuario;
	}

	public boolean esEntidadLdap(String entidad) throws EstructuraOrganizativaException {
		DbConnection dbConn=new DbConnection();
		try {
			
			  dbConn.open(DBSessionManager.getSession(entidad));
		      CfgMdoConfig cfgLdapConfig=new CfgMdoConfig();
		      CfgLdapConfig ldapCfg = cfgLdapConfig.loadDbLdapCfg(dbConn);	
			  
	          if( ldapCfg.getEngine() == CfgDefs.LDAP_ENGINE_NONE) {
	        	  return false; 
	          } else {
	        	  return true;
	          }
	          
		} catch (Exception e) {
			try {
				throw e;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} 
		
		finally {
			if( dbConn != null) {
				try {
					dbConn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	
}
