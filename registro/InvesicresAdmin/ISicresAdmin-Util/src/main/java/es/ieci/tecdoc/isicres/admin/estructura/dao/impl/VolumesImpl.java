package es.ieci.tecdoc.isicres.admin.estructura.dao.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicRow;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicRows;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicTable;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.xml.lite.XmlTextBuilder;
import es.ieci.tecdoc.isicres.admin.database.VolumesTable;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Volume;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Volumes;

/**
 * Implementación de la clase Volumes.
 * Maneja la lista de volúmenes invesDoc.
 */
public class VolumesImpl implements Volumes
{
	public VolumesImpl()
   {
      _list = new ArrayList();
   }

   public int count()
   {
      return _list.size();
   }

   public Volume get(int index) throws Exception
   {
   	return (Volume)_list.get(index);
   }

   public void loadByRep(int repId, String entidad) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      VolumesTable table = new VolumesTable();
      int counter;
      VolumeImpl volume;

      if (_logger.isDebugEnabled())
         _logger.debug("loadByRep");

      DbConnection dbConn=new DbConnection();
		try
		{
			dbConn.open(DBSessionManager.getSession());

         tableInfo.setTableObject(table);
         tableInfo.setClassName(VolumesTable.class.getName());
         tableInfo.setTablesMethod("getVolumeTableName");
         tableInfo.setColumnsMethod("getLoadVolumesByRepColumnNames");

         rowInfo.setClassName(VolumeImpl.class.getName());
         rowInfo.setValuesMethod("loadVolumesByRepValues");
         rowsInfo.add(rowInfo);

         DynamicFns.selectMultiple(dbConn, table.getLoadVolumesByRepQual(repId), false, tableInfo,
                                 rowsInfo);

         for (counter = 0; counter < rowInfo.getRowCount(); counter++)
         {
         	volume = (VolumeImpl)rowInfo.getRow(counter);
         	volume.setRepId(repId);
            add(volume);
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

   public void loadByVolumeList(int listId, String entidad) throws Exception
   {
   	DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      VolumesTable table = new VolumesTable();
      int counter;
      VolumeImpl volume;

      if (_logger.isDebugEnabled())
         _logger.debug("loadByVolumeList");

      DbConnection dbConn=new DbConnection();
		try
		{
			dbConn.open(DBSessionManager.getSession());

         tableInfo.setTableObject(table);
         tableInfo.setClassName(VolumesTable.class.getName());
         tableInfo.setTablesMethod("getListVolumeTableName");
         tableInfo.setColumnsMethod("getLoadVolumeIdColumnName");

         rowInfo.setClassName(VolumeImpl.class.getName());
         rowInfo.setValuesMethod("loadVolIdValue");
         rowsInfo.add(rowInfo);

         DynamicFns.selectMultiple(dbConn, table.getLoadListVolQual(listId), false, tableInfo,
                                 rowsInfo);

         for (counter = 0; counter < rowInfo.getRowCount(); counter++)
         {
         	volume = (VolumeImpl)rowInfo.getRow(counter);
         	volume.load(volume.getId(), entidad);
            add(volume);
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
      String tagName = "Volumes";
      VolumeImpl volume;

      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      for (int i = 0; i < count(); i++)
      {
      	volume = getImpl(i);
         bdr.addFragment(volume.toXML(false));
      }

      bdr.addClosingTag(tagName);

      return bdr.getText();
   }

	public String toString()
	{
      return toXML(false);
   }

	public String toXML()
	   {
	      return toXML(true);
	   }
	/**
    * Añade un volumen a la lista.
    *
    * @param volume El volumen mencionado.
    */

   public void add(VolumeImpl volume)
   {
      _list.add(volume);
   }

   public void add(Volume volume)
   {
      _list.add(volume);
   }
   /**
    * Elimina un volumen de la lista.
    *
    * @param volId Identificador del volumen a eliminar.
    */
   public void remove(int volId) throws Exception
	{
   	for (int i=0; i < _list.size(); i++)
   	{
   		VolumeImpl volume = getImpl(i);
   		if (volume.getId() == volId)
   			_list.remove(i);
   	}
   }

   public Volume getVolume(int index)
   {
      return (Volume)_list.get(index);
   }
    /**
    * Devuelve un volumen de la lista.
    *
    * @param index Indice del volumen que se desea recuperar.
    * @return El volumen mencionado.
    */

   private VolumeImpl getImpl(int index)
   {
      return (VolumeImpl)_list.get(index);
   }

   private ArrayList _list;
   private static final Logger _logger = Logger.getLogger(VolumesImpl.class);
}
