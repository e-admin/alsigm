package es.ieci.tecdoc.isicres.admin.core.beans.definicion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.core.database.IDocFmtDatos;
import es.ieci.tecdoc.isicres.admin.core.database.IdsGeneratorIDoc;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Archive;
import es.ieci.tecdoc.isicres.admin.estructura.beans.ArchiveFlds;
import es.ieci.tecdoc.isicres.admin.estructura.beans.ArchiveIdxs;
import es.ieci.tecdoc.isicres.admin.exception.ISicresAdminEstructuraException;

/*$Id*/

public class DefinicionLibroSalida implements DefinicionLibroRegistro {
	private static final Logger logger = Logger
	.getLogger(DefinicionLibroSalida.class);

	public static int FECHA_DOCUMENTO = 15;


	public Archive getBookDefinition(String nombre) {
		Archive archivador = new Archive();
		archivador.setFldsDef(getArchiveFlds());
		archivador.setIdxsDef(getArchiveIdxs());
		archivador.setName(nombre);
		archivador.setTypeId(LIBRO_SALIDA);
		return archivador;
	}

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
			logger.error("ERROR creando indices para libro de salida",e);
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
			fields.add(9, "Destinatarios", 1, 80, true, false, false, "");
			fields.add(10, "Tipo de transporte", 1, 31, true, false, false, "");
			fields.add(11, "Número de transporte", 1, 30, true, false, false,
					"");
			fields.add(12, "Tipo de asunto", 4, 0, true, false, false, "");
			fields.add(13, "Resumen", 1, 240, true, false, false, "");
			fields.add(14, "Comentario", 2, 65535, true, false, false, "");
			//Fecha de documento
			getFieldFechaDocumento(fields);

			//se añaden campos reservados adicionales que estaran en el rango 500-1000
			DefinicionLibroSicres3Utils.addAditionalReservedAndSicres3Fields(fields);


		} catch (ISicresAdminEstructuraException e) {
			logger.error("ERROR creando campos para libro de entrada",e);
		}

		return fields;

	}

	public void getFieldFechaDocumento(ArchiveFlds fields)
			throws ISicresAdminEstructuraException {
		fields.add(FECHA_DOCUMENTO, "Fecha del documento", 7, 0, true, false, false, "");
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
				+ "SF12 FOREIGN KEY (FLD12) REFERENCES SCR_CA (ID)");
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
			fmt.setData(bundle.getString("formatoSalida.1.3.1"));
		}else{
			fmt.setData(bundle.getString("formatoSalida.1.3"));
		}
		fmt.add(db);

		fmt.setId(IdsGeneratorIDoc.generateNextId(IdsGeneratorIDoc.IDOCFMT, entidad));
		fmt.setType(2);
		fmt.setData(bundle.getString("formatoSalida.2.3"));
		fmt.add(db);

		fmt.setId(IdsGeneratorIDoc.generateNextId(IdsGeneratorIDoc.IDOCFMT, entidad));
		fmt.setType(3);
		//tenemos dos tipos de formularios segun este sicres3 habilitado o no
		if (DefinicionLibroSicres3Utils.isSicres3Enabled()){
			fmt.setData(bundle.getString("formatoSalida.3.3.1"));
		}else{
			fmt.setData(bundle.getString("formatoSalida.3.3"));
		}
		fmt.add(db);
	}
}