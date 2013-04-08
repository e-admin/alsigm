package ieci.tecdoc.sbo.idoc.datree;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.sbo.acs.base.AcsAccessToken;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.idoc.dao.DaoDirTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoDATNodeTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoArchHdrTbl;
import ieci.tecdoc.sbo.idoc.acs.AcsMdoArchive;
import ieci.tecdoc.sbo.idoc.acs.AcsInfo;
import ieci.tecdoc.sgm.base.dbex.DbConnection;

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
      	dbConn.open(DBSessionManager.getSession(entidad));
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
