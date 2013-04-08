/**
 * @author Javier Septién Arceredillo
 * 
 * Fecha de Creación: 11-may-2007
 */

package ieci.tecdoc.sgm.nt.database;

import ieci.tecdoc.sgm.nt.database.datatypes.NotificacionImpl;

/**
 * Gestiona el acceso (inserciones, modificaciones, etc.) a la tabla de
 * expedientes.
 * 
 */
public class EstadosTabla extends NotificacionImpl {
	
	private static final String TABLE_NAME = "SGMNTINFO_ESTADO_NOTI";
        
        private static final String CN_ID = "CGUID";

	private static final String CN_IDSISNOT = "CUIDSISNOT";

	private static final String CN_DESCRI = "CDESCRIPCION";

	

	private static final String ALL_COLUMN_NAMES =  CN_ID + "," + 
                                                        CN_IDSISNOT + "," +  
                                                        CN_DESCRI  ;

	/**
	 * Constructor de la clase ExpedientesTable
	 */
	public EstadosTabla() {
	}
        
  

	/**
	 * Devuelve el nombre de la tabla
	 * 
	 * @return String Nombre de la tabla
	 */
	public String getTableName() {

		return TABLE_NAME;
	}      
        
        /**
	 * Devuelve el nombre de la columa nif destino
	 * 
	 * @return String Nombre de la columna nif destino
	 */
	public String getIdColumnName() {
		return CN_ID;
	}
        
        /**
	 * Devuelve el nombre de la columa nif destino
	 * 
	 * @return String Nombre de la columna nif destino
	 */
	public String getIdSisnotColumnName() {
		return CN_IDSISNOT;
	}
        
        /**
	 * Devuelve el nombre de la columa nif destino
	 * 
	 * @return String Nombre de la columna nif destino
	 */
	public String getDescripcionColumnName() {
		return CN_DESCRI;
	}
        
        
      

	/**
	 * Devuelve los nombres de las columnas
	 * 
	 * @return String Nombres de las columnas
	 */
	public String getAllColumnNames() {

		return ALL_COLUMN_NAMES;
	}
        
        
                                                              
	/**
	 * Devuelve la clausula de consulta por el numero de expediente
	 * 
	 * @param nif del destinatario
         * @param numero de expediente de la notifiacion
	 * @return String Clausula de consulta por numero
	 */
	public String getClausulaPorId(String id_) {
		String clausula;

		clausula = "WHERE " + CN_ID + " = " + id_;

		return clausula;
	}
        
        

}