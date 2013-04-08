package es.ieci.tecdoc.isicres.admin.sbo.idoc.api;

import java.util.ArrayList;

import es.ieci.tecdoc.isicres.admin.sbo.idoc.folder.search.FolderSearchCondition;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.folder.search.FolderSearchConditionFactory;

public final class FolderSearchCondObjFactory
{   
   
   private FolderSearchConditionFactory m_factory;
   private int                          m_dbEngine;


   public FolderSearchCondObjFactory(int dbEngine, ArchiveObject arch)
   {   
      m_factory  = new FolderSearchConditionFactory(arch.getArchiveToken());
      m_dbEngine = dbEngine;  
   }  
  
   public FolderSearchCondObject createSearchCondition(int fldId, String opr, ArrayList vals)
                                 throws Exception
   {      
      FolderSearchCondition  cond       = null;
      FolderSearchCondObject condObject = null;

      cond = m_factory.createSearchCondition(fldId, opr, vals);

      condObject = new FolderSearchCondObject(m_dbEngine, cond);

      return condObject;
   }
     
   public FolderSearchCondObject createSearchCondition(int fldId, String opr, Object val)
                                 throws Exception
   {
      FolderSearchCondition  cond       = null;
      FolderSearchCondObject condObject = null;

      cond = m_factory.createSearchCondition(fldId, opr, val);

      condObject = new FolderSearchCondObject(m_dbEngine, cond);

      return condObject;
   } 

   public FolderSearchCondObject createSearchCondition(FolderSearchCondObject cond1, 
                                                       FolderSearchCondObject cond2,
                                                       String opr)
                                throws Exception
   {      
      FolderSearchCondition  cond       = null;
      FolderSearchCondObject condObject = null;

      cond = m_factory.createSearchCondition(cond1.getSearchCondition(),
                                             cond2.getSearchCondition(),
                                             opr);

      condObject = new FolderSearchCondObject(m_dbEngine, cond);

      return condObject;
   }      
   
      
   

} // class
