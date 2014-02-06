package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.core.db.DbOutputStatement;
import ieci.tecdoc.idoc.admin.api.archive.BasicDirectory;

import org.apache.log4j.Logger;

public class BasicDirImpl implements BasicDirectory
{
   /**
    * Construye un objeto de la clase.
    *
    * @param id Identificador del directorio.
    * @param name Nombre del directorio.
    */
    
   protected BasicDirImpl(int id, String name)
   {
      _id = id;
      _name = name;
   }
   
   /**
    * Construye un objeto de la clase.
    *
    */
    
   public BasicDirImpl()
   {
      _id = Defs.NULL_ID;
      _name = "";
   }
   
   public int getId()
   {
      return _id;      
   }
   
   public String getName()
   {
      return _name;
   }

   /**
    * Recupera de la base de datos información asociada al directorio. 
    * 
    * @param statement
    * @param idx
    * @return 
    * @throws java.lang.Exception
    */
    
   public Integer loadValues(DbOutputStatement statement, Integer idx) 
                  throws Exception 
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("loadValues");

      _id = statement.getLongInteger(index++);
      _name = statement.getShortText(index++);

      return new Integer(index);
   }

   private String _name;
   private int _id;

   private static final Logger _logger = Logger.getLogger(BasicDirImpl.class);
}
