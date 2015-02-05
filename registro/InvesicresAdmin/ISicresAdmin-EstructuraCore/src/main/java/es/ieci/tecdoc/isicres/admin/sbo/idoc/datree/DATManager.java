package es.ieci.tecdoc.isicres.admin.sbo.idoc.datree;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.sbo.acs.base.AcsAccessToken;
import es.ieci.tecdoc.isicres.admin.sbo.config.CfgMdoConfig;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.acs.AcsInfo;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.acs.AcsMdoArchive;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoArchHdrTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoDATNodeTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoDirTbl;

public final class DATManager
{

   private DATManager()
   {
   }
   
   public static String getRootNodeName(String entidad)
                        throws Exception
   {
      
      String name = null;
      DbConnection dbConn=new DbConnection();
      try{
      	dbConn.open(DBSessionManager.getSession());
        name = CfgMdoConfig.getDbInfoName(dbConn);
      }catch(Exception e){
      	throw e;
      }finally{
      	dbConn.close();
      }
      
      return name;

   }

   public static DATNodeList getRootChildren(DbConnection dbConn, AcsAccessToken accToken)
                                throws Exception
   {    
      int dirId = 0;
      
      return getDirChildren(dbConn, accToken, dirId);
            
   }
   
   public static DATNodeList getDirChildren(DbConnection dbConn, AcsAccessToken accToken, int dirId)
                                throws Exception
   { 
      DATDaoDirNodeRows        dirRows  = null;
      DATDaoDirNodeOutputRow   dirRow   = null;
      DATDaoArchNodeRows       archRows = null;
      DATDaoArchNodeOutputRow  archRow  = null;
      DATNodeList              nodeList = null;
      int                      count, i;
      int                      id, type, accessType;
      String                   name;
      boolean                  canViewArch;
      
      nodeList = new DATNodeList();
      
      dirRows = new DATDaoDirNodeRows();
      
      DaoDirTbl.selectChildrenRows(dbConn, dirId, dirRows);
      
      count = dirRows.count();
      
      for (i = 0; i < count; i++)
      {    
         dirRow = dirRows.getRecord(i);
         
         id   = dirRow.m_id;
         name = dirRow.m_name; 
         
         nodeList.add(id, name, DATNodeType.DIR); 
              
      }
      
      archRows = new DATDaoArchNodeRows();
           
      DaoArchHdrTbl.selectChildrenRows(dbConn, dirId, archRows);
      
      count = archRows.count();
      
      for (i = 0; i < count; i++)
      {         
         archRow = archRows.getRecord(i);
         
         id   = archRow.m_id;
         name = archRow.m_name; 
         
         canViewArch = true;
                 
         if ( accToken != null) 
         {
           canViewArch = canViewArch(dbConn, accToken, archRow); 
         }
         
         if (canViewArch)
         { 
            nodeList.add(id, name, DATNodeType.ARCH);    
         }
                 
      }      
      
      return nodeList;            
   }
   
   public static boolean dirHasChildren(DbConnection dbConn, int dirId)
                         throws Exception
   { 
      int count;
                 
      count = DaoDATNodeTbl.selectChildCount(dbConn, dirId);
      
      if (count > 0)
         return true;
      else
         return false;
   }
   
   //***********************************************************
   
   private static boolean canViewArch(DbConnection dbConn, AcsAccessToken accToken, DATDaoArchNodeOutputRow archRow)
                          throws Exception
   { 
      boolean can = false;
      AcsInfo acs = new AcsInfo( archRow.m_accessType, archRow.m_acsId);
      
      can = AcsMdoArchive.hasAuthView(dbConn, accToken, acs);

      return can;
   }
   

} // class
