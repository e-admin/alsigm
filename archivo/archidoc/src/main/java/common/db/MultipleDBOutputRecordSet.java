package common.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbDataType;
import ieci.core.db.DbOutputRecord;
import ieci.core.db.DbOutputRecordSet;
import ieci.core.db.DbOutputStatement;

import java.util.ArrayList;
import java.util.List;

public class MultipleDBOutputRecordSet implements DbOutputRecordSet {

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

	final List values = new ArrayList();

	protected class MultipleValueOuputRecord implements DbOutputRecord {
		DbColumnDef[] colDef = null;
		StmtValueGetter[] valueGetter = null;

		MultipleValueOuputRecord(DbColumnDef[] colDef,
				StmtValueGetter[] valueGetter) {
			this.colDef = colDef;
			this.valueGetter = valueGetter;
		}

		public void getStatementValues(DbOutputStatement stmt) throws Exception {
			Object[] value = new Object[colDef.length];
			for (int i = 0; i < colDef.length; i++) {
				value[i] = valueGetter[i].extractValue(stmt, i + 1);
			}
			values.add(value);
		}
	}

	MultipleValueOuputRecord outputRecord = null;

	public MultipleDBOutputRecordSet(DbColumnDef[] colDef) {
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
		outputRecord = new MultipleValueOuputRecord(colDef, valueGetter);
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
