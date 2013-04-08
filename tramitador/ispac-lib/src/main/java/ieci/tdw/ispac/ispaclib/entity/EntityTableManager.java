package ieci.tdw.ispac.ispaclib.entity;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.ispaclib.dao.dbmgr.DBColumn;
import ieci.tdw.ispac.ispaclib.dao.dbmgr.DBTableDesc;
import ieci.tdw.ispac.ispaclib.dao.entity.MultivalueTable;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbColDef;
import ieci.tdw.ispac.ispaclib.db.DbIndexDefinition;
import ieci.tdw.ispac.ispaclib.db.DbResultSet;
import ieci.tdw.ispac.ispaclib.db.DbTableFns;
import ieci.tdw.ispac.ispaclib.db.InternalDataType;
import ieci.tdw.ispac.ispaclib.entity.def.EntityDef;
import ieci.tdw.ispac.ispaclib.entity.def.EntityField;
import ieci.tdw.ispac.ispaclib.entity.def.EntityIndex;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.log4j.Logger;

public class EntityTableManager {
	
	/** Logger de la clase. */
	private static Logger logger = Logger.getLogger(EntityTableManager.class);

    public void createTable(DbCnt cnt, String tblName, List fields, EntityDef entityDefinition) throws ISPACException {

		// creacion de la tabla
		//DbColDef[] colsDefs = new DbColDef[fields.size()];
    	List list = new ArrayList();
		int i = 0;
		for (Iterator itEntityFields = fields.iterator(); itEntityFields.hasNext();i++) {
			EntityField columnDefinition = (EntityField) itEntityFields.next();
			//Si el campo es multivalor se crea la tabla correspondiente, si corresponde
			if (columnDefinition.isMultivalue()){
				createMultiValueTable(cnt, tblName, columnDefinition);
			}else{
				//colsDefs[i] = newDbColDef(columnDefinition);
				list.add(newDbColDef(columnDefinition));
			}
		}
		DbColDef[] colsDefs = new DbColDef[list.size()];
		colsDefs = (DbColDef[])list.toArray(colsDefs);
		createTable(cnt, tblName, colsDefs);

		// creacion de indices
		List indexes = entityDefinition.getIndexes();
		for (Iterator it = indexes.iterator(); it.hasNext();) {
			
			EntityIndex aIndex = (EntityIndex) it.next();
			// obtener campos del indice
			DbIndexDefinition indexDef = newDbIndexDef(fields, aIndex);
			
			try {
				DbTableFns.createIndex(cnt, tblName, indexDef);
			}
			catch (Exception e) {
				logger.error("Error al crear el índice de la tabla [" + tblName + "]", e);
				throw new ISPACInfo("exception.entities.createIndex", new Object[] {aIndex.getName()});
			}
		}
    }
    
    public void createTable(DbCnt cnt, String tblName, DbColDef[] colDefs) throws ISPACException {
    	
    	DbTableFns.createTable(cnt, tblName, colDefs);
    }
    
    public void createTable(DbCnt cnt, String tblName, DbColDef[] colDefs, String[]fieldsPrimaryKey) throws ISPACException {
    	
    	DbTableFns.createTable(cnt, tblName, colDefs, fieldsPrimaryKey);
    }    
    
    public void createMultiValueTable(DbCnt cnt, String tblName, EntityField multivalueField)throws ISPACException {
    	String multivalueTblName = MultivalueTable.getInstance().composeMultivalueTableName(tblName, multivalueField.getType().getJdbcType());
    	MultivalueTable.getInstance().setPrecision(multivalueField);
    	MultivalueTable.getInstance().setSize(multivalueField);
    	
    	//Si ya esta creada la tabla no se vuelve a crear
    	if (DbTableFns.isTableCreated(cnt, multivalueTblName))
    		return;
    	DbColDef[] colDefs = MultivalueTable.getInstance().getColDefs(multivalueField);
    	DbTableFns.createTable(cnt, multivalueTblName, colDefs);
    	String sequenceName = composeSequenceName(multivalueTblName);
    	DbTableFns.createSequence(cnt, sequenceName);
    }
    
    public void dropFieldTable(DbCnt cnt, String tblName, EntityField field) throws Exception{
    	
    	DbTableFns.dropField(cnt, tblName, newDbColDef(field));
    }
    
	public void dropMultiValueTable(DbCnt cnt, String multivalueTableName) throws Exception {
		DbTableFns.dropTable(cnt, multivalueTableName);
	}    
    
    
    
    public DbColDef newDbColDef(EntityField columnDefinition) {
    	
		return new DbColDef(columnDefinition.getPhysicalName(), columnDefinition.getType().getId(),
							columnDefinition.getSize(), columnDefinition.getPrecision(), columnDefinition.isNullable());

    }
    
	public DbIndexDefinition newDbIndexDef(List fields, EntityIndex aIndex) {
		
		List fieldIds = aIndex.getFieldIds();
		List entityFieldsOfIndex = (List) CollectionUtils.select(
				fields, new PredicateFindFieldById(fieldIds));
		StringBuffer sqlIndexFieldsNames = new StringBuffer();
		for (Iterator itIndexFields = entityFieldsOfIndex.iterator(); itIndexFields.hasNext();) {
			EntityField field = (EntityField) itIndexFields.next();
			if (sqlIndexFieldsNames.length() > 0)
				sqlIndexFieldsNames.append(",");
			sqlIndexFieldsNames.append(field.getPhysicalName());
		}
		//creacion de indice
		DbIndexDefinition indexDef = new DbIndexDefinition(aIndex.getName(), sqlIndexFieldsNames
				.toString(), aIndex.isUnique());
		return indexDef;
	}
	
	public String createSequence(DbCnt cnt, String tblName, String sqPkName) throws ISPACException {
		
		if (StringUtils.isBlank(sqPkName)) {
			sqPkName = composeSequenceName(tblName);
		}
		
        DbTableFns.createSequence(cnt, sqPkName);
        
        return sqPkName;
	}
	
	/*
	public void deleteSequence(DbCnt cnt, String tblName) throws Exception {
		
		String sqPkName = composeSequenceName(tblName);
		DbTableFns.dropSequence(cnt, sqPkName);
	}
	*/
	
	public void deleteSequence(DbCnt cnt, String sqPkName) throws Exception {
		
		DbTableFns.dropSequence(cnt, sqPkName);
	}
	
	public boolean isTableCreated(DbCnt cnt, String tblName) throws ISPACException {
		
		return DbTableFns.isTableCreated(cnt, tblName);
	}
	
	public boolean checkValidationTableColumns(DbCnt cnt, String tblName, List fields) throws ISPACException {
		
		DbResultSet dbrs = null;
		
		try {
			
			dbrs = cnt.getColumns(tblName);
			DBTableDesc tabledesc = new DBTableDesc(tblName, dbrs.getResultSet(), null);
			
			if (tabledesc.isEmpty()) {
				throw new ISPACInfo("exception.entities.tableBD.noExists", new Object[] {tblName});
			}
			
			// Comprobar si la definición de los campos se corresponde con la tabla en BD
			Iterator it = fields.iterator();
			while (it.hasNext()) {
				
				EntityField field = (EntityField) it.next();
				DBColumn column = tabledesc.getColumn(field.getPhysicalName().toUpperCase());
				if (column != null) {
					
					// Tipo de dato
					InternalDataType fieldDataType = field.getType();
					if (InternalDataType.getJdbcNativeDbTypeSQL(cnt, fieldDataType) == column.getType()) {
						
						// Tamaño para los campos de texto (valor y sustituto)
						if ((fieldDataType.equals(InternalDataType.SHORT_TEXT)) &&
							(field.getSize() != column.getWidth())) {
							
							return false;
						}	
					}
					else {
						return false;
					}
				}
				else {
					return false;
				}
			}
		} finally {
			if (dbrs != null) {
				dbrs.close();
			}
		}
		
		return true;
	}
	
	public boolean isTableFilled(DbCnt cnt, String tblName) throws ISPACException {
		
		return DbTableFns.isTableFilled(cnt, tblName);
	}
	
	public void dropTable(DbCnt cnt, String tblName) throws Exception {
	
		DbTableFns.dropTable(cnt, tblName);
	}
	
	public void addField(DbCnt cnt, String tblName, DbColDef colDef) throws Exception {
		
		DbTableFns.addField(cnt, tblName, colDef);
	}
	
	public void createIndex(DbCnt cnt, String tblName, DbIndexDefinition indexDef) throws ISPACException {
		
		DbTableFns.createIndex(cnt, tblName, indexDef);
	}
	
	public void dropIndex(DbCnt cnt, String tblName, String indexName) throws Exception {
	
		DbTableFns.dropIndex(cnt, tblName, indexName);
	}
	
	public static String composeSequenceName(String tblName) {
		
		String sqPkName = tblName;

//    	if (sqPkName.length() > DBTableMgrFactory.SEQUENCENAME_MAXLENGTH)
//    		sqPkName = sqPkName.substring(0, DBTableMgrFactory.SEQUENCENAME_MAXLENGTH);
//		sqPkName = IdSequenceMgr.SEQUENCE_NAME_PREFIX + sqPkName;
		
		sqPkName = IdSequenceMgr.SEQUENCE_NAME_PREFIX + Math.abs(sqPkName.hashCode());
		return sqPkName;
	}
	
    private class PredicateFindFieldById implements Predicate {
		List idsToSelect;

		PredicateFindFieldById(List idsToSelect) {
			this.idsToSelect = idsToSelect;
		}

		public boolean evaluate(Object arg0) {
			for (Iterator itIdsToSelect = idsToSelect.iterator(); itIdsToSelect.hasNext();) {
				Integer id = (Integer) itIdsToSelect.next();
				if (id.equals((new Integer(((EntityField)arg0).getId())))){
					return true;
				}
			}
			return false;
		}
	}


    
}