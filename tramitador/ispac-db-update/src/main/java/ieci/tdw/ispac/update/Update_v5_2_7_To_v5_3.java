package ieci.tdw.ispac.update;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.dao.security.FechSustitucionesDAO_v5_2_7;
import ieci.tdw.ispac.ispaclib.dao.security.SustitucionDAO_Update_v5_2_7_To_v5_3;
import ieci.tdw.ispac.ispaclib.dao.security.SustitucionFechaDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Update_v5_2_7_To_v5_3 {
	
	public static void main(String[] args) throws Exception {
		
		if ((args == null) ||
			(args.length != 4)) {
			
			System.out.println("ERROR: argumentos incorrectos (driverClassName url username password).");
			System.exit(1);
		}

		try {
			Class.forName(args[0]);
		}
		catch (ClassNotFoundException cnfe) {
			System.out.println("ERROR: driver 'org.postgresql.Driver' no encontrado - " + cnfe.getMessage());
			cnfe.printStackTrace();
			System.exit(1);
		}
		
		Connection cnt = null;
		  
		try {
			cnt = DriverManager.getConnection(args[1], args[2], args[3]);
		}
		catch (SQLException se) {
			System.out.println("ERROR: no se ha establecido la conexion con la BD - " + se.getMessage());
			se.printStackTrace();
			System.exit(1);
		}
		
		DbCnt dbCnt = new DbCnt();
		dbCnt.setConnection(cnt);
		
		try {
			dbCnt.openTX();
			
			Map substitutionDatesByUid = new HashMap();
			
			// Obtener todos los registros de SPAC_SS_SUSTITUCION
			IItemCollection substitutions = SustitucionDAO_Update_v5_2_7_To_v5_3.getSubstitutions(dbCnt);
			while (substitutions.next()) {
				
				IItem substitution = (IItem) substitutions.value();
				Integer idSubstitution = substitution.getKeyInteger();
				
				// Sustituido
				String uidSubstituted = substitution.getString("UID_SUSTITUIDO");
				List substitutionDatesIds = (List) substitutionDatesByUid.get(uidSubstituted);
				if (substitutionDatesIds == null) {
				
					// Fechas del sustituido (v5.2.7)
					IItemCollection substitutionDates = FechSustitucionesDAO_v5_2_7.getFechas(dbCnt, uidSubstituted).disconnect();
					substitutionDatesIds = new ArrayList();
					while (substitutionDates.next()) {
						
						IItem substitutionDate = (IItem) substitutionDates.value();
						Integer idSubstituionDate = substitutionDate.getKeyInteger();
						
						// Relacionar sustituciones con fechas
						createSustitucionFechaDAO(dbCnt, idSubstitution, idSubstituionDate);
						
				    	substitutionDatesIds.add(idSubstituionDate);
					}
					
					substitutionDatesByUid.put(uidSubstituted, substitutionDatesIds);
				}
				else {
					
					Iterator it = substitutionDatesIds.iterator();
					while (it.hasNext()) {
					
						Integer idSubstituionDate = (Integer) it.next();
						
						// Relacionar sustituciones con fechas
						createSustitucionFechaDAO(dbCnt, idSubstitution, idSubstituionDate);
					}
				}
			}
			
			// Commit de la transacción
			dbCnt.closeTX(true);
			
			System.out.println("LOG: actualizacion ejecutada correctamente.");
		}
		catch (Exception e) {
			// Rollback de la transacción
			dbCnt.closeTX(false);
			
			System.out.println("ERROR: " + e.getMessage());
			e.printStackTrace();
		}
		finally {
			
			if (cnt != null) {
				cnt.close();
			}
		}
	}
	
	private static void createSustitucionFechaDAO(DbCnt cnt, 
												  Integer idSubstitution, 
												  Integer idSubstituionDate) throws ISPACException {
		
    	SustitucionFechaDAO sustitucionFechaDAO = new SustitucionFechaDAO(cnt);
    	sustitucionFechaDAO.createNew(cnt);
    	sustitucionFechaDAO.set("ID_SUSTITUCION", idSubstitution);
    	sustitucionFechaDAO.set("ID_FECHSUSTITUCION", idSubstituionDate);
    	sustitucionFechaDAO.store(cnt);
	}
		
}