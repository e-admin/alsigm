package common.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbDataType;
import ieci.core.db.DbOutputRecord;
import ieci.core.db.DbOutputRecordSet;
import ieci.core.db.DbOutputStatement;

import java.util.HashMap;

import common.lang.MutableInt;

public class PairDBOutputRecordSet implements DbOutputRecordSet {

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
	protected static StmtValueGetter MUTABLE_INT_VALUE_GETTER = new StmtValueGetter() {
		public Object extractValue(DbOutputStatement stmt, int pos)
				throws Exception {
			return new MutableInt(stmt.getShortInteger(pos));
		}
	};

	final HashMap values = new HashMap();

	protected class PairOuputRecord implements DbOutputRecord {
		DbColumnDef colKey = null;
		DbColumnDef colValue = null;
		StmtValueGetter keyGetter = null;
		StmtValueGetter valueGetter = null;

		PairOuputRecord(DbColumnDef colKey, DbColumnDef colValue,
				StmtValueGetter keyGetter, StmtValueGetter valueGetter) {
			this.colKey = colKey;
			this.colValue = colValue;
			this.keyGetter = keyGetter;
			this.valueGetter = valueGetter;
		}

		public void getStatementValues(DbOutputStatement stmt) throws Exception {
			String key = (String) keyGetter.extractValue(stmt, 1);
			Object value = valueGetter.extractValue(stmt, 2);
			values.put(key, value);
		}
	}

	PairOuputRecord outputRecord = null;

	public PairDBOutputRecordSet(DbColumnDef colKey, DbColumnDef colValue) {
		StmtValueGetter keyGetter = null;
		StmtValueGetter valueGetter = null;

		switch (colKey.getDataType()) {
		case DbDataType.DATE_TIME:
			keyGetter = DATE_VALUE_GETTER;
			break;
		case DbDataType.SHORT_TEXT:
			keyGetter = SHORT_TEXT_VALUE_GETTER;
			break;
		case DbDataType.LONG_TEXT:
			keyGetter = LONG_TEXT_VALUE_GETTER;
			break;
		case DbDataType.SHORT_INTEGER:
			keyGetter = SHORT_INTEGER_VALUE_GETTER;
			break;
		case DbDataType.LONG_INTEGER:
			keyGetter = LONG_INTEGER_VALUE_GETTER;
			break;
		}

		switch (colValue.getDataType()) {
		case DbDataType.DATE_TIME:
			valueGetter = DATE_VALUE_GETTER;
			break;
		case DbDataType.SHORT_TEXT:
			valueGetter = SHORT_TEXT_VALUE_GETTER;
			break;
		case DbDataType.LONG_TEXT:
			valueGetter = LONG_TEXT_VALUE_GETTER;
			break;
		case DbDataType.SHORT_INTEGER:
			valueGetter = SHORT_INTEGER_VALUE_GETTER;
			break;
		case DbDataType.LONG_INTEGER:
			valueGetter = LONG_INTEGER_VALUE_GETTER;
			break;
		case DbDataType.MUTABLE_INT:
			valueGetter = MUTABLE_INT_VALUE_GETTER;
			break;
		}
		outputRecord = new PairOuputRecord(colKey, colValue, keyGetter,
				valueGetter);
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
