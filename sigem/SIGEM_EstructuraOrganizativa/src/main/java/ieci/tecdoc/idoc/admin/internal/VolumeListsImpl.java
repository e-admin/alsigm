package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.core.xml.lite.XmlTextBuilder;
import ieci.tecdoc.idoc.admin.api.volume.VolumeList;
import ieci.tecdoc.idoc.admin.database.VolumesTable;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * Implementación de la clase VolumeLists. 
 * Maneja la lista de listas de volúmenes de invesDoc.
 */
public class VolumeListsImpl
{
	/**
    * Construye un objeto de la clase.
    *  
    */
	public VolumeListsImpl()
   {
      _list = new ArrayList();
   }

	/**
    * Devuelve el número de listas de volúmenes.
    * 
    * @return El número de listas de volúmenes mencionado.
    */
   public int count() 
   {
      return _list.size();
   }

   /**
    * Devuelve una lista de volúmenes de la lista.
    * 
    * @param index Indice del repositorio que se desea recuperar.
    * 
    * @return El repositorio mencionado.
    */
   public VolumeList get(int index) throws Exception 
   {
   	return (VolumeList)_list.get(index);
   }
   
   public void load(String entidad) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      VolumesTable table = new VolumesTable();
      int counter;
      VolumeListImpl volumeList;
   
      if (_logger.isDebugEnabled())
         _logger.debug("load");
      
      DbConnection dbConn=new DbConnection();  
		try
		{
			dbConn.open(DBSessionManager.getSession(entidad));

         tableInfo.setTableObject(table);
         tableInfo.setClassName(VolumesTable.class.getName());
         tableInfo.setTablesMethod("getListTableName");
         tableInfo.setColumnsMethod("getLoadListAllColumnNames");
         
         rowInfo.setClassName(VolumeListImpl.class.getName());
         rowInfo.setValuesMethod("loadListAllValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.selectMultiple(dbConn, "", false, tableInfo, 
                                 rowsInfo);
         
         for (counter = 0; counter < rowInfo.getRowCount(); counter++)
         {
         	volumeList = (VolumeListImpl)rowInfo.getRow(counter);
            add(volumeList);
         }
         
		}
		catch(Exception e)
		{
         _logger.error(e);
         throw e;
		}
      finally
      {
         dbConn.close();
      }
        
   }
   
   public String toXML(boolean header) 
   {
      XmlTextBuilder bdr;
      String tagName = "VolumeLists";
      VolumeListImpl volumeList;
      
      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      for (int i = 0; i < count(); i++)
      {
      	volumeList = getImpl(i);
         bdr.addFragment(volumeList.toXML(false));
      }

      bdr.addClosingTag(tagName);
      
      return bdr.getText();
   }

	public String toString()
	{
      return toXML(false);
   }

    /**
    * Devuelve una lista de volúmenes de la lista.
    * 
    * @param index Indice de la lista de volúmenes que se desea recuperar.
    * @return La lista de volúmenes mencionada.
    */
    
   private VolumeListImpl getImpl(int index)
   {
      return (VolumeListImpl)_list.get(index);  
   }
 
   /**
    * Añade una lista de volúmenes a la lista.
    * 
    * @param volumeList La lista de volúmenes mencionada.
    */

   protected void add(VolumeListImpl volumeList) 
   {
      _list.add(volumeList);
   }


   private ArrayList _list;
   private static final Logger _logger = Logger.getLogger(VolumeListsImpl.class);
}
