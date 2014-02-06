
package ieci.tecdoc.sbo.idoc.documental.search;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.core.dyndao.DynDaoRec;
import ieci.tecdoc.core.dyndao.DynDaoRs;
import ieci.tecdoc.sbo.acs.base.AcsAccessToken;
import ieci.tecdoc.sbo.fss.core.FssDaoFtsTbl;
import ieci.tecdoc.sbo.fss.core.FssDaoRepTbl;
import ieci.tecdoc.sbo.fss.core.FssDaoVolVolTbl;
import ieci.tecdoc.sbo.fss.core.FssMdoUtil;
import ieci.tecdoc.sbo.idoc.acs.AcsMdoFolder;
import ieci.tecdoc.sbo.idoc.api.Archive;
import ieci.tecdoc.sbo.idoc.api.ArchiveObject;
import ieci.tecdoc.sbo.idoc.datree.DATBnoManager;
import ieci.tecdoc.sbo.idoc.datree.DATNode;
import ieci.tecdoc.sbo.idoc.datree.DATNodeList;

import java.util.ArrayList;


public final class DocumentalMdoSearch
{     
  
   private static final DbColumnDef CD_EXTID1 = new DbColumnDef("EXTID1",
                                                       DbDataType.LONG_INTEGER,
                                                       false);

   private static final DbColumnDef CD_EXTID2 = new DbColumnDef("EXTID2",
                                                       DbDataType.LONG_INTEGER,
                                                       false);

   private static final DbColumnDef CD_EXTID4 = new DbColumnDef("EXTID4",
                                                       DbDataType.LONG_INTEGER,
                                                       false);

   public static final int    CABINET                            = 1;
   
   
   public DocumentalMdoSearch()
   {   
     
   }
   
   public static DocumentalSearchResult executeQuery(AcsAccessToken accToken,
                                                 DocumentalSearchQuery query,
                                                 DbConnectionConfig connectionConfig)
                                    throws Exception
   {
      DocumentalSearchResult  rs   = null;  
      String              ftsQual, dbQual;
       
      int [] idPermittedArchives = null;
      //if (accToken != null) {
         
         idPermittedArchives = getPermittedArchives (accToken);

				 // Si no tiene permisos sobre ningún archivador, no hay nada que mostrar.         
         if (idPermittedArchives == null || idPermittedArchives.length == 0) {
            return new DocumentalSearchResult ();
         }
      //}
      
      DbConnection.open(connectionConfig);   
      
      try 
      {
      
	      ftsQual = query.getFtsSqlQual(DbConnection.getEngine(), idPermittedArchives);
	      dbQual = query.getDbSqlQual(DbConnection.getEngine(), idPermittedArchives);
	      
	      rs =  executeQuery(accToken, ftsQual, dbQual);
	      
	      DbConnection.close();
      
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);
         throw e;
      }
      
      return rs;
   }
   


   
   /**
    * @return
    * @throws Exception
    */
   private static int[] getPermittedArchives(AcsAccessToken accToken) throws Exception
   {
      ArrayList ids = new ArrayList ();
      
      DATNodeList nodeList = DATBnoManager.getRootChildren (accToken);
      DATNode node;
      for (int i = 0; i < nodeList.count(); i++) {
         node = nodeList.get (i);
         exploreDATNode (accToken, node, ids);
      }
      
      int [] permitted = new int [ids.size ()];
      for (int i = 0; i < permitted.length; i++) {
         permitted [i] = ((Integer) ids.get (i)).intValue ();
      }
      return permitted;
   }
   
   private static void exploreDATNode (AcsAccessToken accToken, DATNode node, ArrayList permittedArchives) throws Exception {
      if (node.getType() != CABINET) {
    	  
    	 Archive archiveApi = new Archive ();
    	 ArchiveObject archive = archiveApi.loadArchive (null, node.getId ());
    	 if (archive.getArchiveToken ().isFTSInContent ()) {
    		 permittedArchives.add (new Integer (node.getId ()));
    	 }
      } else {
         DATNodeList nodeList = DATBnoManager.getDirChildren (accToken, node.getId ());
         for (int i = 0; i < nodeList.count(); i++) {
            node = nodeList.get (i);
            exploreDATNode (accToken, node, permittedArchives);
         }
      }
   }

   private static boolean canViewFolder (AcsAccessToken accToken, int archiveId, int folderId)
                          throws Exception
   { 
      boolean  can       = true;      
      
      if (accToken != null) {
         can = AcsMdoFolder.hasAuthView (accToken, archiveId, folderId); 
      }
      return can;      
   }  

   public static DocumentalSearchResult executeQuery (AcsAccessToken accToken,
                                                                String ftsQual,
                                                                String dbQual)
                                            throws Exception
   {           
      
      DocumentalSearchResult documentalSearchResult = new DocumentalSearchResult ();
      
      boolean                       canViewFdr;
      int                           archId, fdrId;
      int                           numFdrs, i;                                         
      DynDaoRec  row          	 		= null;
      DynDaoRs rows          				= new DynDaoRs (new DbColumnDef [] {CD_EXTID1, CD_EXTID2, CD_EXTID4});
      String                        tblNames,colNames;

      
 
      String        ftsTblName       = FssDaoFtsTbl.getTblName();
      String        ftsArchIdColName = FssDaoFtsTbl.getExtId1ColName(false);
      String        ftsFdrIdColName  = FssDaoFtsTbl.getExtId2ColName(false);
      String        ftsDocIdColName  = FssDaoFtsTbl.getExtId4ColName(false);
      
      colNames = ftsArchIdColName + "," + ftsFdrIdColName + "," + ftsDocIdColName;
      tblNames = ftsTblName;
      
      DbSelectFns.select (tblNames, colNames, ftsQual, false, rows);

      numFdrs = rows.count(); 
      
      for(i = 0; i < numFdrs; i++)
      {
         canViewFdr = true;

         row   = rows.getRecord(i);
         
         archId = row.getLongIntegerColData (ftsArchIdColName);
         fdrId = row.getLongIntegerColData (ftsFdrIdColName);
        

         canViewFdr = canViewFolder(accToken, archId, fdrId);
         
         if (canViewFdr) {
            documentalSearchResult.addDocument (archId, fdrId, row.getLongIntegerColData (ftsDocIdColName));
         }
      }
      
      if (FssDaoRepTbl.rowExists(FssMdoUtil.RT_DB)) {
      
	      rows = new DynDaoRs (new DbColumnDef [] {
	               										FssDaoVolVolTbl.CD_EXTID1, 
	               										FssDaoVolVolTbl.CD_EXTID2, 
	               										FssDaoVolVolTbl.CD_EXTID4
	               									});
	     
	 
	      ftsTblName       = FssDaoVolVolTbl.TN;
	      ftsArchIdColName = FssDaoVolVolTbl.CD_EXTID1.getName ();
	      ftsFdrIdColName  = FssDaoVolVolTbl.CD_EXTID2.getName ();
	      ftsDocIdColName  = FssDaoVolVolTbl.CD_EXTID4.getName ();
	      
	      colNames = ftsArchIdColName + "," + ftsFdrIdColName + "," + ftsDocIdColName;
	      tblNames = ftsTblName;
	      
	      DbSelectFns.select (tblNames, colNames, dbQual, false, rows);
	
	      numFdrs = rows.count(); 
	      
	      for(i = 0; i < numFdrs; i++)
	      {
	         canViewFdr = true;
	
	         row   = rows.getRecord(i);
	         
	         archId = row.getLongIntegerColData (ftsArchIdColName);
	         fdrId = row.getLongIntegerColData (ftsFdrIdColName);
	        
	
	         canViewFdr = canViewFolder(accToken, archId, fdrId);
	         
	         if (canViewFdr) {
	            documentalSearchResult.addDocument (archId, fdrId, row.getLongIntegerColData (ftsDocIdColName));
	         }
	      }   
      
      }
      
      documentalSearchResult.sort ();
      
      return documentalSearchResult;
   }
      
} // class
