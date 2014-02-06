package ieci.tecdoc.sbo.idoc.vldtable.base;

import ieci.tecdoc.core.textutil.UtilX;

import java.io.Serializable;
import java.text.StringCharacterIterator;
import java.util.ArrayList;


/**
 * User: RobertoBas
 * Date: 18-nov-2004
 * Time: 13:46:42
 */
public class VldTblTokenBInfo implements Serializable
{
   private String m_info;
   private String m_name;

   public VldTblTokenBInfo(String info, String name)
   {
      this.m_info = info;
      this.m_name = name;
   }

   public String getInfo()
   {
      return m_info;
   }

   public void setInfo(String info)
   {
      this.m_info = info;
   }

   public String getName()
   {
      return m_name;
   }

   public void setName(String m_name)
   {
      this.m_name = m_name;
   }

   public int getColType( String colName ) throws Exception
   {
      StringCharacterIterator iterator  = new StringCharacterIterator(m_info);
      char                    sep       = '|';
      int numCols;

      //versión
      UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);

      //Número de campos
      numCols = UtilX.parseInteger(iterator , sep) ;
      int typeFound = 0;
      for(int i = 0; i < numCols; i++)
      {

         typeFound = findColType(colName,iterator,sep);
         if ( typeFound!=0 )
         {
            break;
         }
      }
      return typeFound;
   }

   public ArrayList getColsName( int tableType ) throws Exception
   {
      ArrayList colsName = new ArrayList();
      StringCharacterIterator iterator  = new StringCharacterIterator(m_info);
      char                    sep       = '|';
      int numCols;

      //versión
      UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);

      //Número de campos
      numCols = UtilX.parseInteger(iterator , sep) ;
      for(int i = 0; i < numCols; i++)
      {

         colsName.add( getColName(iterator,sep) );

      }
      return colsName;
   }
   private String getColName (StringCharacterIterator iterator, char sep) throws Exception
   {


      UtilX.iteratorIncrementIndex(iterator, 1);
      String colName =  UtilX.parseString(iterator) ;
      UtilX.iteratorIncrementIndex(iterator, 1);
      UtilX.parseInteger(iterator, ',') ;
      UtilX.iteratorIncrementIndex(iterator, 1);
      UtilX.parseInteger(iterator, ',') ;
      UtilX.iteratorIncrementIndex(iterator, 1);
      UtilX.parseInteger(iterator, sep);
      return colName;
   }
   private int findColType (String paramColName , StringCharacterIterator iterator, char sep) throws Exception
   {


      UtilX.iteratorIncrementIndex(iterator, 1);
      String colName =  UtilX.parseString(iterator) ;
      if ( colName.equals(paramColName) )
      {
         UtilX.iteratorIncrementIndex(iterator, 1);
         return UtilX.parseInteger(iterator, ',') ;
      }else
      {
         UtilX.iteratorIncrementIndex(iterator, 1);
         UtilX.parseInteger(iterator, ',') ;
      }
      UtilX.iteratorIncrementIndex(iterator, 1);
      UtilX.parseInteger(iterator, ',') ;
      UtilX.iteratorIncrementIndex(iterator, 1);
      UtilX.parseInteger(iterator, sep);
      return 0;
   }
}
