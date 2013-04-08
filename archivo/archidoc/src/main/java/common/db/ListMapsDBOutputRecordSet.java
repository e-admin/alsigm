package common.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbDataType;
import ieci.core.db.DbOutputRecord;
import ieci.core.db.DbOutputRecordSet;
import ieci.core.db.DbOutputStatement;
import ieci.core.types.IeciTdType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListMapsDBOutputRecordSet implements DbOutputRecordSet {

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

	protected static StmtValueGetter SHORT_DECIMAL_VALUE_GETTER = new StmtValueGetter() {
		public Object extractValue(DbOutputStatement stmt, int pos)
				throws Exception {
			return new Float(stmt.getShortDecimal(pos));
		}
	};

	protected static StmtValueGetter LONG_DECIMAL_VALUE_GETTER = new StmtValueGetter() {
		public Object extractValue(DbOutputStatement stmt, int pos)
				throws Exception {
			return new Double(stmt.getLongDecimal(pos));
		}
	};

	public static final int SHORT_DECIMAL = IeciTdType.SHORT_DECIMAL;
	public static final int LONG_DECIMAL = IeciTdType.LONG_DECIMAL;

	final List values = new ArrayList();

	protected class MapValueOuputRecord implements DbOutputRecord {
		DbColumnDef[] colDef = null;
		StmtValueGetter[] valueGetter = null;

		MapValueOuputRecord(DbColumnDef[] colDef, StmtValueGetter[] valueGetter) {
			this.colDef = colDef;
			this.valueGetter = valueGetter;
		}

		public void getStatementValues(DbOutputStatement stmt) throws Exception {
			HashMap valueHashMap = new HashMap();
			for (int i = 0; i < colDef.length; i++) {
				Object mapVal = valueGetter[i].extractValue(stmt, i + 1);
				valueHashMap.put(colDef[i].getAliasOrName(), mapVal);
			}
			values.add(valueHashMap);
		}
	}

	MapValueOuputRecord outputRecord = null;

	public ListMapsDBOutputRecordSet(DbColumnDef[] colDef) {
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
			case DbDataType.SHORT_DECIMAL:
				valueGetter[i] = SHORT_DECIMAL_VALUE_GETTER;
				break;
			case DbDataType.LONG_DECIMAL:
				valueGetter[i] = LONG_DECIMAL_VALUE_GETTER;
				break;
			}
		}
		outputRecord = new MapValueOuputRecord(colDef, valueGetter);
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

	public List getValues() {
		return this.values;
	}
}
