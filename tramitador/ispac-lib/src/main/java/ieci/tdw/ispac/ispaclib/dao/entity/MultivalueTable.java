package ieci.tdw.ispac.ispaclib.dao.entity;

import ieci.tdw.ispac.ispaclib.dao.dbmgr.DBTableMgrFactory;
import ieci.tdw.ispac.ispaclib.db.DbColDef;
import ieci.tdw.ispac.ispaclib.db.InternalDataType;
import ieci.tdw.ispac.ispaclib.entity.def.EntityField;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultivalueTable {

	private Map mapSuffixType = null;
	static MultivalueTable mInstance = null;
	
	private static final String SEPARATOR = "_";
	private static final String STRING_TABLE_SUFFIX 		= "S";
	private static final String LONG_STRING_TABLE_SUFFIX 	= "C";
	private static final String INTEGER_TABLE_SUFFIX 		= "I";
	private static final String FLOAT_TABLE_SUFFIX 			= "F";
	private static final String DATE_TABLE_SUFFIX 			= "D";
	private static final String TIMESTAMP_TABLE_SUFFIX 		= "T";
	private static final String BLOB_TABLE_SUFFIX 			= "B";

	//Tamaños de campos y precisión considerados a utilizar en los campos de valores de las tablas multivalor
	private static final int STRING_MAXLENGTH = 1024;
	private static final int INTEGER_MAXLENGTH = 38;
	private static final int FLOAT_MAXLENGTH = 20;
	private static final int FLOAT_PRECISSION = 4;
	

	public static final String FIELD_ID 					= "ID";
	public static final String FIELD_FIELD 					= "FIELD";
	public static final String FIELD_REG 					= "REG_ID";
	public static final String FIELD_VALUE 					= "VALUE";
	
	private String[] columns = null;
	
	private MultivalueTable() {
		mapSuffixType = new HashMap();
		mapSuffixType.put(new Integer(Types.VARCHAR),		STRING_TABLE_SUFFIX);
		mapSuffixType.put(new Integer(Types.LONGVARCHAR),	LONG_STRING_TABLE_SUFFIX);
		mapSuffixType.put(new Integer(Types.SMALLINT), 		INTEGER_TABLE_SUFFIX); 
		mapSuffixType.put(new Integer(Types.INTEGER),	  	INTEGER_TABLE_SUFFIX);
		mapSuffixType.put(new Integer(Types.REAL),			FLOAT_TABLE_SUFFIX);
		mapSuffixType.put(new Integer(Types.FLOAT),			FLOAT_TABLE_SUFFIX);
		mapSuffixType.put(new Integer(Types.DATE),			DATE_TABLE_SUFFIX);
		mapSuffixType.put(new Integer(Types.TIMESTAMP),		TIMESTAMP_TABLE_SUFFIX);
		mapSuffixType.put(new Integer(Types.LONGVARBINARY),	BLOB_TABLE_SUFFIX);
		
		columns = new String[]{FIELD_ID, FIELD_FIELD, FIELD_REG, FIELD_VALUE};
		
	}




	public static MultivalueTable getInstance(){
		if (mInstance == null)
			mInstance = new MultivalueTable();
		return mInstance;
	}
	
	public String getSuffix(int type){
		return SEPARATOR+(String)mapSuffixType.get(new Integer(type));
	}
	
	public String[] getColumns(){
		return columns;
	}



	public String composeMultivalueTableName(String tableName, int type) {
		if (tableName.length() > DBTableMgrFactory.TABLENAME_MAXLENGTH - 2)
			tableName = tableName.substring(0, DBTableMgrFactory.TABLENAME_MAXLENGTH - 2);
		tableName += getSuffix(type);
		return tableName;
	}




	public DbColDef[] getColDefs(EntityField multivalueField) {
		DbColDef[] colDefs = new DbColDef[columns.length];
		colDefs[0] = new DbColDef(FIELD_ID, InternalDataType.SHORT_INTEGER.getId(), INTEGER_MAXLENGTH, false);
		colDefs[1] = new DbColDef(FIELD_FIELD, InternalDataType.SHORT_TEXT.getId(), DBTableMgrFactory.COLUMNNAME_MAXLENGTH, false);
		colDefs[2] = new DbColDef(FIELD_REG, InternalDataType.SHORT_INTEGER.getId(), INTEGER_MAXLENGTH, false);
		
		switch (multivalueField.getType().getJdbcType()){
		case Types.VARCHAR:
			colDefs[3] = new DbColDef(FIELD_VALUE, multivalueField.getType().getId(), STRING_MAXLENGTH, true);
			break;
		case Types.LONGVARCHAR:
			colDefs[3] = new DbColDef(FIELD_VALUE, multivalueField.getType().getId(), true);
			break;
		case Types.INTEGER:
		case Types.SMALLINT:
			colDefs[3] = new DbColDef(FIELD_VALUE, multivalueField.getType().getId(), INTEGER_MAXLENGTH, true);
			break;
		case Types.REAL:
		case Types.FLOAT:
			colDefs[3] = new DbColDef(FIELD_VALUE, multivalueField.getType().getId(), FLOAT_MAXLENGTH, FLOAT_PRECISSION, true);
			break;

		case Types.DATE:
		case Types.TIMESTAMP:
			colDefs[3] = new DbColDef(FIELD_VALUE, multivalueField.getType().getId(), true);
			break;
		default:
			break;
		}
		return colDefs;
	}




	/**
	 * @param type tipo a uscar como base para obtener los compatibles
	 * @return lista de tipos internos que serán almacenados en la misma tabla multivalor según '<code>type</code>'
	 */
	public static List getCompatibleInternalDataType(InternalDataType type) {
		ArrayList list = new ArrayList();
		switch (type.getJdbcType()){
			case Types.INTEGER:
			case Types.SMALLINT:
				list.add(InternalDataType.SHORT_INTEGER);
				list.add(InternalDataType.LONG_INTEGER);
				break;
			case Types.REAL:
			case Types.FLOAT:
				list.add(InternalDataType.SHORT_DECIMAL);
				list.add(InternalDataType.LONG_DECIMAL);
				break;
			default:
				list.add(type);
				break;
		}
		
		return list;
	}




	public void setSize(EntityField field) {
		switch (field.getType().getJdbcType()){
			case Types.INTEGER:
			case Types.SMALLINT:
				field.setSize(INTEGER_MAXLENGTH);
				return;
			case Types.REAL:
			case Types.FLOAT:
				field.setSize(FLOAT_MAXLENGTH);
				return;
			case Types.VARCHAR:
				field.setSize(STRING_MAXLENGTH);
				return;
			default:
				return;
		}
	}


	public void setPrecision(EntityField field) {
		switch (field.getType().getJdbcType()){
			case Types.REAL:
			case Types.FLOAT:
				field.setPrecision(FLOAT_PRECISSION);
				return;
			default:
				return;
		}
	}
}
