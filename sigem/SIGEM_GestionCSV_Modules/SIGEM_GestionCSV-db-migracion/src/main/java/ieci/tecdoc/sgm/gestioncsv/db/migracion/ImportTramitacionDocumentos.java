package ieci.tecdoc.sgm.gestioncsv.db.migracion;

import ieci.tdw.ispac.ScriptBase;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tecdoc.sgm.core.services.gestioncsv.CodigosAplicacionesConstants;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import es.ieci.tecdoc.fwktd.csv.core.service.ServicioDocumentos;
import es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV;

public class ImportTramitacionDocumentos extends ScriptBase {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(ImportTramitacionDocumentos.class);

	public static void main(String[] args) throws Exception {

		checkArguments(args);

		logger.info("Iniciando proceso de importacion de documentos de tramitacion en el modulo de gestion de CSV");

		// Contexto para la tramitación
		logger.info("Configurando la conexion para el modulo de tramitacion");
		ClientContext context = getClientContext(args);

		// Servicio de documentos del módulo de gestión de CSV
		ServicioDocumentos servicioDocumentosCSV = getServicioDocumentosCSV(args);

		try {
			// Importar los documentos de tramitación que tengan asignado código de cotejo
			// en las tablas del módulo de gestión de CSV
			importTramitacionDocumentos(context, servicioDocumentosCSV);

			logger.info("Proceso de importacion de documentos de tramitacion en el modulo de gestion de CSV finalizado");

		} catch (Throwable t) {

			logger.error("Error en el proceso de importacion de documentos de tramitacion en el modulo de gestion de CSV", t);
		}
	}

	protected static void checkArguments(String[] args) {
		if ((args == null) || (args.length != 7)) {
			logger.error("Argumentos incorrectos (driverClassName tramitadorUrl username password eTramitacionUrl username password).");
			System.exit(1);
		}

		try {
			Class.forName(args[0]);
		} catch (ClassNotFoundException cnfe) {
			logger.error("Driver JDBC '" + args[0] + "' no encontrado", cnfe);
			System.exit(1);
		}
	}

	protected static ServicioDocumentos getServicioDocumentosCSV(String args[]) {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/beans/fwktd-csv-api-applicationContext.xml", "/beans/fwktd-csv-api/fwktd-csv-api-datasource-custom-beans.xml");

		logger.info("Configurando la conexion para la gestion de CSV: driverClassName=[" +  args[0] + "] url=[" + args[4]	+ "] username=[" + args[5] + "]");
		DriverManagerDataSource driverManagerDataSource = (DriverManagerDataSource) applicationContext.getBean("fwktd_csv_dataSource");
		driverManagerDataSource.setDriverClassName(args[0]);
		driverManagerDataSource.setUrl(args[4]);
		driverManagerDataSource.setUsername(args[5]);
		driverManagerDataSource.setPassword(args[6]);

		return (ServicioDocumentos) applicationContext.getBean("fwktd_csv_api_servicioDocumentosImpl");
	}

	protected static void importTramitacionDocumentos(ClientContext context, ServicioDocumentos servicioDocumentosCSV) throws ISPACException {

		IEntitiesAPI entitiesAPI = context.getAPI().getEntitiesAPI();

		// Obtener los documentos de tramitación que tienen asignado Código de Cotejo
		String sqlquery = "WHERE COD_COTEJO IS NOT NULL";
		IItemCollection documents = entitiesAPI.queryEntities(SpacEntities.SPAC_DT_DOCUMENTOS, sqlquery);

		while (documents.next()) {

			IItem document = documents.value();
			String codCotejo = document.getString("COD_COTEJO");

			InfoDocumentoCSV infoDocumentoCSV = servicioDocumentosCSV.getInfoDocumentoByCSV(codCotejo);
			if (infoDocumentoCSV == null) {

				infoDocumentoCSV = new InfoDocumentoCSV();

				// Información del documento de tramitación para el módulo de gestion de CSV
				infoDocumentoCSV.setCsv(codCotejo);
				infoDocumentoCSV.setCodigoAplicacion(CodigosAplicacionesConstants.TRAMITACION_EXPEDIENTES_CODE);
				infoDocumentoCSV.setDisponible(true);
				//infoDocumentoCSV.setFechaCaducidad();
				infoDocumentoCSV.setFechaCreacion(document.getDate("FFIRMA"));
				infoDocumentoCSV.setFechaCSV(document.getDate("FFIRMA"));
				// La extensión de los documentos con Código de Cotejo en tramitación es simpre PDF
				infoDocumentoCSV.setTipoMime("application/pdf");
				// El nombre en el módulo de CSV es con la extensión incluida, que por lo anterior será siempre .pdf
				String nombre = document.getString("NOMBRE");
				int index = nombre.lastIndexOf(".");
				if (index != -1) {
					nombre = nombre.substring(0, index);
				}
				nombre += ".pdf";
				infoDocumentoCSV.setNombre(nombre);

				infoDocumentoCSV = servicioDocumentosCSV.saveInfoDocumento(infoDocumentoCSV);

				logger.info("El documento con Codigo de Cotejo [" + codCotejo + "] se ha importado como CSV asociado al documento [" + infoDocumentoCSV.getId() + " - " + infoDocumentoCSV.getNombre() + "]" );
			} else {
				logger.info("El documento con Codigo de Cotejo [" + codCotejo + "] ya existe como CSV asociado al documento [" + infoDocumentoCSV.getId() + " - " + infoDocumentoCSV.getNombre() + "]" );
			}
		}
	}

}