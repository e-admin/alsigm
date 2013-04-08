package common.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbDataType;
import ieci.core.db.DbOutputRecord;
import ieci.core.db.DbOutputRecordSet;
import ieci.core.db.DbOutputStatement;

import java.util.HashMap;
import java.util.HashSet;

import util.CollectionUtils;

public class MapsDBOutputRecordSet implements DbOutputRecordSet {

	private interface StmtValueGetter {
		Object extractValue(DbOutputStatement stmt, int pos) throws Exception;
	}

	protected static StmtValueGetter SHORT_TEXT_VALUE_GETTER = new StmtValueGetter() {
		public Object extractValue(DbOutputStatement stmt, int pos)
				throws Exception {
			return stmt.getShortText(pos);
		}
	};
	protected static StmtValueGetter LONG_TEXT_VALUE_GETTER = new StmtValueGetter() {
		public Object extractValue(DbOutputStatement stmt, int pos)
				throws Exception {
			return stmt.getLongText(pos);
		}
	};
	protected static StmtValueGetter SHORT_INTEGER_VALUE_GETTER = new StmtValueGetter() {
		public Object extractValue(DbOutputStatement stmt, int pos)
				throws Exception {
			return new Short(stmt.getShortInteger(pos));
		}
	};
	protected static StmtValueGetter LONG_INTEGER_VALUE_GETTER = new StmtValueGetter() {
		public Object extractValue(DbOutputStatement stmt, int pos)
				throws Exception {
			return new Integer(stmt.getLongInteger(pos));
		}
	};
	protected static StmtValueGetter DATE_VALUE_GETTER = new StmtValueGetter() {
		public Object extractValue(DbOutputStatement stmt, int pos)
				throws Exception {
			return stmt.getDateTime(pos);
		}
	};

	final HashMap values = new HashMap();

	protected class MapValueOuputRecord implements DbOutputRecord {
		DbColumnDef[] colDef = null;
		StmtValueGetter[] valueGetter = null;
		int[] posColsIds = null;
		String separador = null;

		MapValueOuputRecord(DbColumnDef[] colDef,
				StmtValueGetter[] valueGetter, int[] posColsId, String separador) {
			this.colDef = colDef;
			this.valueGetter = valueGetter;
			this.posColsIds = posColsId;
			this.separador = separador;
		}

		public void getStatementValues(DbOutputStatement stmt) throws Exception {
			HashMap valueHashMap = new HashMap();

			Integer[] posColsIdsInteger = new Integer[posColsIds.length];
			for (int i = 0; i < posColsIds.length; i++)
				posColsIdsInteger[i] = new Integer(posColsIds[i]);
			HashSet colsIds = new HashSet(
					CollectionUtils.createList(posColsIdsInteger));

			String[] cadKeyMap = new String[posColsIds.length];
			for (int i = 0; i < colDef.length; i++) {
				Object mapVal = valueGetter[i].extractValue(stmt, i + 1);
				valueHashMap.put(colDef[i].getBindPropertyVO(), mapVal);
				if (colsIds.contains("" + i))
					cadKeyMap[i] = mapVal.toString();
			}
			String key = "";
			for (int i = 0; i < posColsIds.length; i++) {
				key += cadKeyMap[i];
				if (i + 1 > posColsIds.length)
					key += separador;
			}
			values.put(key, valueHashMap);
		}
	}

	MapValueOuputRecord outputRecord = null;

	public MapsDBOutputRecordSet(DbColumnDef[] colDef, int[] posColsIds,
			String separador) {
		StmtValueGetter[] valueGetter = new StmtValueGetter[colDef.length];
		for (int i = 0; i < colDef.length; i++) {
			switch (colDef[i].getDataType()) {
			case DbDataType.DATE_TIME:
				valueGetter[i] = DATE_VALUE_GETTER;
				break;
			case DbDataType.SHORT_TEXT:
				valueGetter[i] = SHORT_TEXT_VALUE_GETTER;
				break;
			case DbDataType.LONG_TEXT:
				valueGetter[i] = LONG_TEXT_VALUE_GETTER;
				break;
			case DbDataType.SHORT_INTEGER:
				valueGetter[i] = SHORT_INTEGER_VALUE_GETTER;
				break;
			case DbDataType.LONG_INTEGER:
				valueGetter[i] = LONG_INTEGER_VALUE_GETTER;
				break;
			}
		}
		outputRecord = new MapValueOuputRecord(colDef, valueGetter, posColsIds,
				separador);
	}

	public DbOutputRecord newRecord() throws Exception {
		return outputRecord;
	}

	public int getMaxNumItems() {
		return Integer.MAX_VALUE;
	}

	public boolean hasMoreItems() {
		return false;
	}

	public HashMap getValues() {
		return this.values;
	}
}
