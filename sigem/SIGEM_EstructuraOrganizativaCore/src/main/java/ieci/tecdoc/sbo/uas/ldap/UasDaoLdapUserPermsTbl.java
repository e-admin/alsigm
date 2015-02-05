package ieci.tecdoc.sbo.uas.ldap;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.sbo.util.nextid.NextId;
import ieci.tecdoc.sgm.base.dbex.DbColumnDef;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDataType;
import ieci.tecdoc.sgm.base.dbex.DbIndexDef;
import ieci.tecdoc.sgm.base.dbex.DbInputRecord;
import ieci.tecdoc.sgm.base.dbex.DbInsertFns;
import ieci.tecdoc.sgm.base.dbex.DbSelectFns;
import ieci.tecdoc.sgm.base.dbex.DbTableFns;
import ieci.tecdoc.sgm.base.dbex.DbUtil;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.administracion.Aplicacion;
import ieci.tecdoc.sgm.core.services.administracion.ServicioAdministracion;
import ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion;

public final class UasDaoLdapUserPermsTbl
{

	//~ Static fields/initializers ---------------------------------------------
/* 
 *@SF-SEVILLA 
 *02-may-2006 / antmaria
 */
   public static final String	        TN			    = "IUSERUSERTYPE";
	
   public static final DbColumnDef    CD_USERID		    = new DbColumnDef("USERID",
																		  DbDataType.LONG_INTEGER,
																		  false);
   public static final DbColumnDef    CD_PRODID	    	= new DbColumnDef("PRODID",
																		  DbDataType.LONG_INTEGER,
																		  36,
																		  false);
   public static final DbColumnDef    CD_TYPE = new DbColumnDef("TYPE",
																		  DbDataType.LONG_INTEGER,
																		  254,
																		  false);	
	
   public static final DbColumnDef [] ACD			    = { CD_USERID, CD_PRODID, CD_TYPE };
	
   public static final String		    ACN			    = DbUtil.getColumnNames(ACD);
	
	//~ Constructors -----------------------------------------------------------

   private UasDaoLdapUserPermsTbl(){}

	//~ Methods ----------------------------------------------------------------

   
   public static void setDefaultPerms(DbConnection dbConn, int userId, String entidad) throws Exception
   {  

      UasDaoLdapUserPermsRecA colValues = new UasDaoLdapUserPermsRecA();
      colValues.m_userId = userId;
      
      ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
      Aplicacion[] aplicaciones = oServicio.getAplicaciones();
      int app = 0;
      
      if (aplicaciones != null){
    	  for(int i=0; i<aplicaciones.length; i++) {
    		  app = new Integer(aplicaciones[i].getIdentificador()).intValue();
    		  colValues.m_prodId = app;
    		  if (app != APP_CATALOGO_PROCEDIMIENTOS && app != APP_ARCHIVO && app != APP_REGISTRO) {
    			  colValues.m_type = 0;
    		  } else {
    			  colValues.m_type = 1;
    		  }
    		  DbInsertFns.insert(dbConn, TN, ACN, colValues);
    	  }
      }
   }
   
   private static final int APP_CATALOGO_PROCEDIMIENTOS = new Integer(ConstantesGestionUsuariosAdministracion.APLICACION_CATALOGO_PROCEDIMIENTOS).intValue();
   private static final int APP_ARCHIVO = new Integer(ConstantesGestionUsuariosAdministracion.APLICACION_ARCHIVO).intValue();
   private static final int APP_REGISTRO = new Integer(ConstantesGestionUsuariosAdministracion.APLICACION_REGISTRO).intValue();
}// class
