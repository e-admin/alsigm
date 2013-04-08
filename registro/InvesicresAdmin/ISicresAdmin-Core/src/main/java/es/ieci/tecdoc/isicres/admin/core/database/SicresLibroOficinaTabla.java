package es.ieci.tecdoc.isicres.admin.core.database;
/*$Id*/

import org.apache.log4j.Logger;

public class SicresLibroOficinaTabla {

	private static final Logger logger = Logger.getLogger(SicresLibroOficinaTabla.class);	

	private static final String SEPARATOR = ", ";
	private static final String TABLE_NAME = "scr_bookofic";
	private static final String CN_ID = "id";
	private static final String CN_ID_WITH_OFICINA = "scr_bookofic.id";	
	private static final String CN_ID_BOOK = "id_book";
	private static final String CN_ID_OFIC = "id_ofic";
	private static final String CN_NUMERATION = "numeration";
	private static final String CN_NAME = "name";
	private static final String CN_DEPT_ID = "deptid";	
	private static final String ALL_COLUMN_NAMES = CN_ID + SEPARATOR + CN_ID_BOOK + SEPARATOR + CN_ID_OFIC + SEPARATOR + CN_NUMERATION;
	private static final String UPDATE_COLUMN_NAMES = CN_NUMERATION;
	private static final String ALL_COLUMN_NAMES_WITH_OFICINA = CN_ID_WITH_OFICINA + SEPARATOR + CN_ID_BOOK + SEPARATOR + CN_ID_OFIC + SEPARATOR + CN_NUMERATION + SEPARATOR + CN_NAME + SEPARATOR + CN_DEPT_ID;
	
	public SicresLibroOficinaTabla() {
	}

	public String getUpdateColumnNames() {
		return UPDATE_COLUMN_NAMES;
	}
	   
	public String getTableName() {
	   return TABLE_NAME;
	}

	public String getTableNameWithOficina() {
	   return TABLE_NAME + ", " + SicresOficinaTabla.TABLE_NAME;
	}
	
	public String getAllColumnNames() {
	   return ALL_COLUMN_NAMES;
	}
	public String getAllColumnNamesWithOficina() {
	   return ALL_COLUMN_NAMES_WITH_OFICINA;
	}
	public String getByIds(int bookId, int oficId) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_ID_BOOK).append(" = ").append(bookId).append(" AND ")
		.append(CN_ID_OFIC).append(" = ").append(oficId).append(" ");
		return sbAux.toString();
	}
	
	public String getByBook(int bookId) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_ID_BOOK).append(" = ").append(bookId).append(" ");
		return sbAux.toString();
	}
	
	public String getOficinaByBook(int bookId) {
		StringBuffer sbAux = new StringBuffer("WHERE id_ofic=scr_ofic.id AND ").append(CN_ID_BOOK).append(" = ").append(bookId).append(" ");
		return sbAux.toString();
	}
	
	public String getByOficina(int oficId) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_ID_OFIC).append(" = ").append(oficId).append(" ");
		return sbAux.toString();
	}

	public String getByLibro(int libroId) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_ID_BOOK).append(" = ").append(libroId).append(" ");
		return sbAux.toString();
	}
}
