package ieci.tecdoc.sbo.idoc.vldtable.base;

import ieci.tecdoc.core.textutil.UtilX;

import java.io.Serializable;
import java.text.StringCharacterIterator;
import org.apache.log4j.Logger;

/**
 * User: RobertoBas
 * Date: 18-nov-2004
 * Time: 13:46:26
 */
public class VldTblTokenVInfo implements Serializable
{

   private static Logger logger = Logger.getLogger( VldTblTokenVInfo.class );

   private String    m_info;
   private int       m_type;
   private String    m_name;
   private int       m_id;
   private int 		 m_bTblId;

   public VldTblTokenVInfo(String m_info, int m_type, String m_name, int m_id, int m_bTblId)
   {
      this.m_info = m_info;
      this.m_type = m_type;
      this.m_name = m_name;
      this.m_id = m_id;
      this.m_bTblId = m_bTblId;
   }


	/**
	 * @return Returns the m_bTblId.
	 */
	public int getBTblId() {
	    return m_bTblId;
	}
	
	/**
	 * @param tblId The m_bTblId to set.
	 */
	public void setBTblId(int tblId) {
	    m_bTblId = tblId;
	}
	
   public String getInfo()
   {
      return m_info;
   }

   public void setInfo(String info)
   {
      this.m_info = info;
   }

   public int getType()
   {
      return m_type;
   }

   public void setType(int type)
   {
      this.m_type = type;
   }

   public String getName()
   {
      return m_name;
   }

   public void setName(String m_name)
   {
      this.m_name = m_name;
   }

   public int getId()
   {
      return m_id;
   }

   public void setId(int m_id)
   {
      this.m_id = m_id;
   }

   public VldTblVInfo restoreInfo() throws Exception
   {
      if ( logger.isDebugEnabled() )
         logger.debug("entrando en el restoreInfo ");

      int type = getType() ;
      switch (type)
      {
         case VldTblUtil.VLD_TBL_SIMPLE:
            return restoreInfoSimple();
         case VldTblUtil.VLD_TBL_ID:
            return restoreInfoID();
         case VldTblUtil.VLD_TBL_SUST:
            return restoreInfoSust();
         case VldTblUtil.VLD_TBL_JERAR:
            return restoreInfoHierarquical();
      }
      return null;
   }

   private VldTblVInfoSimple restoreInfoSimple() throws Exception
   {

      if ( logger.isDebugEnabled() )
         logger.debug("Tabla simple ");
      StringCharacterIterator iterator  = new StringCharacterIterator( getInfo() );
      //char                    sep       = '"';
      String logColName;
      String docColName;
      int fmt;
      String pkColName;
      String where;
      String orderBy;

      UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);
      //Nombre Logico de la columna
      logColName = UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);
      //Nombre Fisico de la columna
      docColName = UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);
      //Formato de la columna
      fmt = UtilX.parseInteger(iterator,',') ;
      UtilX.iteratorIncrementIndex(iterator, 1);
      //Nombre fisico de la columna Pk
      pkColName = UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);
      //from
      UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);
      //where
      where = UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);
      //orderBy
      orderBy = UtilX.parseString(iterator);


      if ( logger.isDebugEnabled() )
         logger.debug("Tabla simple "+new VldTblVInfoSimple(logColName ,docColName,fmt,pkColName,where,orderBy));

      return new VldTblVInfoSimple(logColName ,docColName,fmt,pkColName,where,orderBy);
   }

   private VldTblVInfoID restoreInfoID() throws Exception
   {

      if ( logger.isDebugEnabled() )
         logger.debug("Tabla ID ");
      StringCharacterIterator iterator  = new StringCharacterIterator( getInfo() );
      //char                    sep       = '"';
      String logColName;
      String docColName;
      int fmt;
      String idLogColName;
      String idDocColName;
      int idFmt;
      String pkColName;
      String where;
      String orderBy;

      UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);
      //Nombre Logico de la columna
      logColName = UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);
      //Nombre Fisico de la columna
      docColName = UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);
      //Formato de la columna
      fmt = UtilX.parseInteger(iterator,',') ;
      UtilX.iteratorIncrementIndex(iterator, 1);
      //Nombre Logico de la columna ID
      idLogColName = UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);
      //Nombre Fisico de la columna ID
      idDocColName = UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);
      //Formato de la columna ID
      idFmt = UtilX.parseInteger(iterator,',') ;
      UtilX.iteratorIncrementIndex(iterator, 1);
      //Nombre fisico de la columna Pk
      pkColName = UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);
      //from
      UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);
      //where
      where = UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);
      //orderBy
      orderBy = UtilX.parseString(iterator);

      if ( logger.isDebugEnabled() )
         logger.debug("Tabla ID "+new VldTblVInfoID(logColName ,docColName,fmt,idLogColName ,idDocColName ,idFmt ,pkColName,where,orderBy));

      return new VldTblVInfoID(logColName ,docColName,fmt,idLogColName ,idDocColName ,idFmt ,pkColName,where,orderBy);
   }


   private VldTblVInfoSust restoreInfoSust() throws Exception
   {

      if ( logger.isDebugEnabled() )
         logger.debug("Tabla Sust ");
      StringCharacterIterator iterator  = new StringCharacterIterator( getInfo() );
      //char                    sep       = '"';
      String logColName;
      String docColName;
      int fmt;
      String sustLogColName;
      String sustDocColName;
      int sustFmt;
      String pkColName;
      String where;
      String docOrderBy;
      String sustOrderBy;

      UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);
      //Nombre Logico de la columna
      logColName = UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);
      //Nombre Fisico de la columna
      docColName = UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);
      //Formato de la columna
      fmt = UtilX.parseInteger(iterator,',') ;
      UtilX.iteratorIncrementIndex(iterator, 1);
      //Nombre Logico de la columna sustituto
      sustLogColName = UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);
      //Nombre Fisico de la columna sustituto
      sustDocColName = UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);
      //Formato de la columna sustituto
      sustFmt = UtilX.parseInteger(iterator,',') ;
      UtilX.iteratorIncrementIndex(iterator, 1);
      //Nombre fisico de la columna Pk
      pkColName = UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);
      //from
      UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);
      //where
      where = UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);
      //orderBy
      docOrderBy = UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);
      //orderBy
      sustOrderBy = UtilX.parseString(iterator);

      VldTblVInfoSust vldTblVInfoSust = new VldTblVInfoSust(logColName ,docColName ,fmt,sustLogColName ,sustDocColName ,sustFmt ,pkColName,where,docOrderBy ,sustOrderBy );
      
      if ( logger.isDebugEnabled() )
         logger.debug("Tabla Sust "+ vldTblVInfoSust);

      return vldTblVInfoSust;
   }

   private VldTblVInfoHierarchical restoreInfoHierarquical () throws Exception 
   {
      if ( logger.isDebugEnabled() )
         logger.debug("Tabla Sust ");
      StringCharacterIterator iterator  = new StringCharacterIterator( getInfo() );
      //char                    sep       = '"';
      

      int parentVTblId, childVTblId;

      // "01.01"|13,6,"","IDOCPARENTKEY","","IDOCCHILDKEY","",""
      
      // Version
      UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      // identificador logico de la validación padre 
      parentVTblId = UtilX.parseInteger(iterator,',') ;
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      // identificador logico de la validación hija 
      childVTblId = UtilX.parseInteger(iterator,',') ;
      UtilX.iteratorIncrementIndex(iterator, 1);
      
      
      // Hay más campos, pero de momento no los leemos.
      VldTblVInfoHierarchical vldTblVInfoHierarchical = new VldTblVInfoHierarchical (parentVTblId, childVTblId);
      

      if ( logger.isDebugEnabled() )
         logger.debug("Tabla Sust "+ vldTblVInfoHierarchical);

      return vldTblVInfoHierarchical;      
   }

}
