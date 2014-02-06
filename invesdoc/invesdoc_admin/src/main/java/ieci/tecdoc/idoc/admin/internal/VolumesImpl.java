package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.core.xml.lite.XmlTextBuilder;
import ieci.tecdoc.idoc.admin.api.volume.Volume;
import ieci.tecdoc.idoc.admin.database.VolumesTable;
import ieci.tecdoc.idoc.admin.internal.VolumeImpl;
import ieci.tecdoc.idoc.core.database.DynamicFns;
import ieci.tecdoc.idoc.core.database.DynamicRow;
import ieci.tecdoc.idoc.core.database.DynamicRows;
import ieci.tecdoc.idoc.core.database.DynamicTable;
import ieci.tecdoc.sbo.config.CfgMdoConfig;

import java.util.ArrayList;

import org.apache.log4j.Logger;

public class VolumesImpl {

	private DbConnectionConfig _dbCntConfig;
	private ArrayList _list;
	private static final Logger _logger = Logger.getLogger(VolumesImpl.class);

	public VolumesImpl() {
		_list = new ArrayList();
	}

	public void setConnectionConfig(DbConnectionConfig dbConnConfig)
			throws Exception {
		_dbCntConfig = dbConnConfig;
	}

	public int count() {
		return _list.size();
	}

	public Volume get(int index) throws Exception {
		return (Volume) _list.get(index);
	}

	public void loadByRep(int repId) throws Exception {

		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		VolumesTable table = new VolumesTable();
		if (_logger.isDebugEnabled())
			_logger.debug("loadByRep");
		try {
			DbConnection.open(getDbConfig());
			tableInfo.setTableObject(table);
			tableInfo.setClassName((ieci.tecdoc.idoc.admin.database.VolumesTable.class).getName());
			tableInfo.setTablesMethod("getVolumeTableName");
			tableInfo.setColumnsMethod("getLoadVolumesByRepColumnNames");
			rowInfo.setClassName(VolumeImpl.class.getName());
			rowInfo.setValuesMethod("loadVolumesByRepValues");
			rowsInfo.add(rowInfo);
			DynamicFns.selectMultiple(table.getLoadVolumesByRepQual(repId),
					false, tableInfo, rowsInfo);
			for (int counter = 0; counter < rowInfo.getRowCount(); counter++) {
				VolumeImpl volume = (VolumeImpl) rowInfo.getRow(counter);
				
				//==============================================
				// Se añade la información de conexión a la BD
				volume.setConnectionConfig(getDbConfig());
				//==============================================

				volume.setRepId(repId);
				add(volume);
			}

		} catch (Exception e) {
			_logger.error(e);
			throw e;
		} finally {
			DbConnection.close();
		}
	}

	public void loadByVolumeList(int listId) throws Exception {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		VolumesTable table = new VolumesTable();
		if (_logger.isDebugEnabled())
			_logger.debug("loadByVolumeList");
		try {
			DbConnection.open(getDbConfig());
			tableInfo.setTableObject(table);
			tableInfo.setClassName((ieci.tecdoc.idoc.admin.database.VolumesTable.class).getName());
			tableInfo.setTablesMethod("getListVolumeTableName");
			tableInfo.setColumnsMethod("getLoadVolumeIdColumnName");
			rowInfo.setClassName(VolumeImpl.class.getName());
			rowInfo.setValuesMethod("loadVolIdValue");
			rowsInfo.add(rowInfo);
			DynamicFns.selectMultiple(table.getLoadListVolQual(listId), false,
					tableInfo, rowsInfo);
			for (int counter = 0; counter < rowInfo.getRowCount(); counter++) {
				VolumeImpl volume = (VolumeImpl) rowInfo.getRow(counter);
				
				//==============================================
				// Se añade la información de conexión a la BD
				volume.setConnectionConfig(getDbConfig());
				//==============================================

				volume.load(volume.getId());
				add(volume);
			}

		} catch (Exception e) {
			_logger.error(e);
			throw e;
		} finally {
			DbConnection.close();
		}
	}

	public String toXML(boolean header) {
		String tagName = "Volumes";
		XmlTextBuilder bdr = new XmlTextBuilder();
		if (header)
			bdr.setStandardHeader();
		bdr.addOpeningTag(tagName);
		for (int i = 0; i < count(); i++) {
			VolumeImpl volume = getImpl(i);
			bdr.addFragment(volume.toXML(false));
		}

		bdr.addClosingTag(tagName);
		return bdr.getText();
	}

	public String toString() {
		return toXML(false);
	}

	public void add(VolumeImpl volume) {
		_list.add(volume);
	}

	public void remove(int volId) throws Exception {
		for (int i = 0; i < _list.size(); i++) {
			VolumeImpl volume = getImpl(i);
			if (volume.getId() == volId)
				_list.remove(i);
		}
	}

	private VolumeImpl getImpl(int index) {
		return (VolumeImpl) _list.get(index);
	}

	private DbConnectionConfig getDbConfig() throws Exception {
		if (_dbCntConfig == null)
			_dbCntConfig = CfgMdoConfig.getDbConfig();
		return _dbCntConfig;
	}

}