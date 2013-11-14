package es.ieci.tecdoc.isicres.admin.core.beans.definicion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.keys.IDocKeys;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbEngine;
import es.ieci.tecdoc.isicres.admin.core.database.IDocFmtDatos;
import es.ieci.tecdoc.isicres.admin.core.database.IdsGeneratorIDoc;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Archive;
import es.ieci.tecdoc.isicres.admin.estructura.beans.ArchiveFlds;
import es.ieci.tecdoc.isicres.admin.estructura.beans.ArchiveIdxs;
import es.ieci.tecdoc.isicres.admin.exception.ISicresAdminEstructuraException;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminDAOException;

/*$Id*/

public class DefinicionLibroEntrada implements DefinicionLibroRegistro {

	private static final Logger logger = Logger
			.getLogger(DefinicionLibroEntrada.class);

	private static final int LENGTH_REFERENCIA_EXPEDIENTE = 80;

	public static int REFERENCIA_EXPEDIENTE = 19;
	public static int FECHA_DOCUMENTO = 20;


	protected ArchiveIdxs getArchiveIdxs(){
		ArchiveIdxs indices = new ArchiveIdxs();
		try{
			ArrayList ereg1 = new ArrayList();
			ereg1.add(new Integer(1));
			indices.add("EREG1", true, ereg1);
			ArrayList ereg2 = new ArrayList();
			ereg2.add(new Integer(2));
			indices.add("EREG2", false, ereg2);
			ArrayList ereg3 = new ArrayList();
			ereg3.add(new Integer(4));
			indices.add("EREG3", false, ereg3);
			ArrayList ereg4 = new ArrayList();
			ereg4.add(new Integer(6));
			indices.add("EREG4", false, ereg4);
			ArrayList ereg5 = new ArrayList();
			ereg5.add(new Integer(7));
			indices.add("EREG5", false, ereg5);
			ArrayList ereg6 = new ArrayList();
			ereg6.add(new Integer(8));
			indices.add("EREG6", false, ereg6);
			ArrayList ereg7 = new ArrayList();
			ereg7.add(new Integer(9));
			indices.add("EREG7", false, ereg7);
			ArrayList ereg8 = new ArrayList();
			ereg8.add(new Integer(5));
			indices.add("EREG8", false, ereg8);

		} catch (ISicresAdminEstructuraException e) {
			logger.error("ERROR creando indices para libro de entrada",e);
		}


		return indices;
	}
	public ArchiveFlds getArchiveFlds(){
		ArchiveFlds fields = new ArchiveFlds();
		try{
			fields.add(1, "Número de registro", 1, 20, true, false, false, "");
			fields.add(2, "Fecha de registro", 9, 0, true, false, false, "");
			fields.add(3, "Usuario", 1, 32, true, false, false, "");
			fields.add(4, "Fecha de trabajo", 7, 0, true, false, false, "");
			fields.add(5, "Oficina de registro", 4, 0, true, false, false, "");
			fields.add(6, "Estado", 4, 0, true, false, false, "");
			fields.add(7, "Origen", 4, 0, true, false, false, "");
			fields.add(8, "Destino", 4, 0, true, false, false, "");
			fields.add(9, "Remitentes", 1, 80, true, false, false, "");
			fields.add(10, "Nº. registro original", 1, 20, true, false, false,
					"");
			fields.add(11, "Tipo de registro original", 4, 0, true, false,
					false, "");
			fields.add(12, "Fecha de registro original", 7, 0, true, false,
					false, "");
			fields.add(13, "Registro original", 4, 0, true, false, false, "");
			fields.add(14, "Tipo de transporte", 1, 31, true, false, false, "");
			/*
			 * Si el intercambio registral está activado el campo "Número de transporte"
			 * debe de ser de 20 caracteres, si no está activado entonces el tamaño por defecto es 30
			 */
			int defaultNumTransporteFieldSize = IDocKeys.FIELD_NUM_TRANSPORTE_SIZE_IR_DISABLED;
			if (DefinicionLibroSicres3Utils.isSicres3Enabled()){
				defaultNumTransporteFieldSize = IDocKeys.FIELD_NUM_TRANSPORTE_SIZE_IR_ENABLED;
			}
			fields.add(15, "Número de transporte", 1, defaultNumTransporteFieldSize, true, false, false,
					"");
			fields.add(16, "Tipo de asunto", 4, 0, true, false, false, "");
			fields.add(17, "Resumen", 1, 240, true, false, false, "");
			fields.add(18, "Comentario", 2, 65535, true, false, false, "");
			//Añadimos los campos Ref. Expediente y Fecha de Documento
			getFieldRefExpediente(fields);
			getFieldFechaDocumento(fields);

			//se añaden campos reservados adicionales que estaran en el rango 500-1000
			DefinicionLibroSicres3Utils.addAditionalReservedAndSicres3Fields(fields);


		} catch (ISicresAdminEstructuraException e) {
			logger.error("ERROR creando campos para libro de entrada",e);
		}

		return fields;
	}

	/**
	 * Método que actualiza la longitud del campo Ref. Expediente del archivador
	 *
	 * @param archiveId
	 *            - Id del archivador
	 *
	 * @throws Exception
	 */
	public void updateFieldRefExpediente(int archiveId)
			throws ISicresRPAdminDAOException {

		DbConnection db = new DbConnection();

		try {
			db.open(DBSessionManager.getSession());
			db.beginTransaction();

			Connection conn = db.getJdbcConnection();

			StringBuffer sb = new StringBuffer();
			sb.append("ALTER TABLE A").append(archiveId).append("SF ");

			if (db.getEngine() == DbEngine.DB2) {
				sb.append("ALTER COLUMN FLD").append(REFERENCIA_EXPEDIENTE)
						.append(" SET DATA TYPE VARCHAR(")
						.append(LENGTH_REFERENCIA_EXPEDIENTE).append(")");
			} else if (db.getEngine() == DbEngine.POSTGRESQL) {
				sb.append("ALTER COLUMN FLD").append(REFERENCIA_EXPEDIENTE)
						.append(" TYPE CHARACTER VARYING(")
						.append(LENGTH_REFERENCIA_EXPEDIENTE).append(")");
			} else if (db.getEngine() == DbEngine.ORACLE) {
				sb.append("MODIFY (FLD").append(REFERENCIA_EXPEDIENTE)
						.append(" VARCHAR2 (")
						.append(LENGTH_REFERENCIA_EXPEDIENTE).append("CHAR))");
			} else {
				sb.append("ALTER COLUMN FLD").append(REFERENCIA_EXPEDIENTE)
						.append(" VARCHAR(")
						.append(LENGTH_REFERENCIA_EXPEDIENTE).append(")");
			}

			if(logger.isDebugEnabled()){
				logger.debug(sb.toString());
			}

			PreparedStatement stmt = conn.prepareStatement(sb.toString());
			stmt.execute();
			db.endTransaction(true);

		} catch (Exception e) {
			// Deshacer transaccion
			if (db != null && db.inTransaction()) {
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			}
			logger.error("Error actualizando campo Referencia Expediente");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.ERROR_UPDATE_BOOK_SICRES3, e);
		} finally {
			try {
				if (db != null && db.existConnection()) {
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
	}

	public void getFieldRefExpediente(ArchiveFlds fields)
			throws ISicresAdminEstructuraException {
		fields.add(REFERENCIA_EXPEDIENTE, "Referencia de Expediente", 1, LENGTH_REFERENCIA_EXPEDIENTE, true, false, false,
				"");
	}

	public void getFieldFechaDocumento(ArchiveFlds fields)
			throws ISicresAdminEstructuraException {
		fields.add(FECHA_DOCUMENTO, "Fecha del documento", 7, 0, true, false, false, "");
	}


	public Archive getBookDefinition(String nombre) {
		Archive archivador = new Archive();
		archivador.setFldsDef(getArchiveFlds());
		archivador.setIdxsDef(getArchiveIdxs());
		archivador.setName(nombre);
		archivador.setTypeId(LIBRO_ENTRADA);
		return archivador;
	}

	public void makeConstraints(int archiveId, DbConnection db)
			throws Exception {
		Connection conn = db.getJdbcConnection();

		PreparedStatement stmt = conn.prepareStatement("ALTER TABLE A"
				+ archiveId + "SF ADD CONSTRAINT FK_A" + archiveId
				+ "SF5 FOREIGN KEY (FLD5) REFERENCES SCR_OFIC (ID)");
		stmt.execute();

		stmt = conn.prepareStatement("ALTER TABLE A" + archiveId
				+ "SF ADD CONSTRAINT FK_A" + archiveId
				+ "SF7 FOREIGN KEY (FLD7) REFERENCES SCR_ORGS (ID)");
		stmt.execute();

		stmt = conn.prepareStatement("ALTER TABLE A" + archiveId
				+ "SF ADD CONSTRAINT FK_A" + archiveId
				+ "SF8 FOREIGN KEY (FLD8) REFERENCES SCR_ORGS (ID)");
		stmt.execute();

		stmt = conn.prepareStatement("ALTER TABLE A" + archiveId
				+ "SF ADD CONSTRAINT FK_A" + archiveId
				+ "SF13 FOREIGN KEY (FLD13) REFERENCES SCR_ORGS (ID)");
		stmt.execute();

		stmt = conn.prepareStatement("ALTER TABLE A" + archiveId
				+ "SF ADD CONSTRAINT FK_A" + archiveId
				+ "SF16 FOREIGN KEY (FLD16) REFERENCES SCR_CA (ID)");
		stmt.execute();
	}

	public void makeFormats(int archiveId, DbConnection db, String entidad) throws Exception {
		IDocFmtDatos fmt = new IDocFmtDatos();
		fmt.setAccesstype(1);
		fmt.setAcsid(2147483646);
		fmt.setArchId(archiveId);
		fmt.setCrtnDate(new Date());
		fmt.setCrtrid(0);
		if (DefinicionLibroSicres3Utils.isSicres3Enabled()){
			fmt.setName("PorDefectoSicres.3");
		}else{
			fmt.setName("PorDefectoSicres");
		}
		fmt.setSubtype(3);

		fmt.setId(IdsGeneratorIDoc.generateNextId(IdsGeneratorIDoc.IDOCFMT, entidad));
		fmt.setType(1);
		//tenemos dos tipos de formularios segun este sicres3 habilitado o no
		if (DefinicionLibroSicres3Utils.isSicres3Enabled()){
			fmt.setData(bundle.getString("formatoEntrada.1.3.1"));
		}else{
			fmt.setData(bundle.getString("formatoEntrada.1.3"));
		}
		fmt.add(db);

		fmt.setId(IdsGeneratorIDoc.generateNextId(IdsGeneratorIDoc.IDOCFMT, entidad));
		fmt.setType(2);
		fmt.setData(bundle.getString("formatoEntrada.2.3"));
		fmt.add(db);

		fmt.setId(IdsGeneratorIDoc.generateNextId(IdsGeneratorIDoc.IDOCFMT, entidad));
		fmt.setType(3);
		//tenemos dos tipos de formularios segun este sicres3 habilitado o no
		if (DefinicionLibroSicres3Utils.isSicres3Enabled()){
			fmt.setData(bundle.getString("formatoEntrada.3.3.1"));
		}else{
			fmt.setData(bundle.getString("formatoEntrada.3.3"));
		}

		fmt.add(db);
	}
}
