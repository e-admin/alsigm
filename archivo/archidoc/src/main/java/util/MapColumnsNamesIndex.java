package util;

import ieci.core.db.DbColumnDef;

import java.util.HashMap;

/**
 * Estructura a la que se pueden ir añadiendo elementos y que mantiene una
 * asociacion entre ese elemento añadido y el numero de orden del elemento
 * dentro del numero total de elementos contenidos en la estructura
 * 
 */
public class MapColumnsNamesIndex {
	HashMap mapColumnsIndex;

	int index = 1;

	public MapColumnsNamesIndex() {
		mapColumnsIndex = new HashMap();
	}

	public void put(String columnName) {
		mapColumnsIndex.put(columnName.trim(), new Integer(index++));
	}

	public int get(String columnName) {
		return ((Integer) mapColumnsIndex.get(columnName)).intValue();
	}

	public int get(DbColumnDef columnDef) {
		return ((Integer) mapColumnsIndex.get(columnDef.getName())).intValue();
	}

	public int getNumMappedColumns() {
		return mapColumnsIndex.size();
	}

}