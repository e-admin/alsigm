package ws.batch;

import common.bi.GestionDocumentosVitalesBI;
import common.bi.GestionPrevisionesBI;
import common.bi.GestionUnidadDocumentalBI;
import common.webservices.WSBase;

/**
 * Servicio Web de acceso a los procesos batch.
 */
public class WSProcesosBatchImpl extends WSBase implements WSProcesosBatch {

	/**
	 * Constructor.
	 */
	public WSProcesosBatchImpl() {
		super();
	}

	/**
	 * Cierra las previsiones que hayan caducado.
	 * 
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public void cerrarPrevisiones() throws Exception {
		authenticate();

		GestionPrevisionesBI prevBI = getServiceRepository()
				.lookupGestionPrevisionesBI();

		prevBI.cerrarPrevisiones();
	}

	/**
	 * Publica las unidades documentales con acceso restringido que han
	 * sobrepasado la fecha de restricción.
	 * 
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public void publicarUnidadesDocumentales() throws Exception {
		authenticate();

		GestionUnidadDocumentalBI udocBI = getServiceRepository()
				.lookupGestionUnidadDocumentalBI();

		udocBI.publicarUnidadesDocumentales();
	}

	/**
	 * Elimina las unidades documentales prestadas que no han sido devueltas
	 * tras dos reclamaciones.
	 * 
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public void eliminarUnidadesDocumentalesPrestadasNoDevueltas()
			throws Exception {
		authenticate();

		GestionUnidadDocumentalBI udocBI = getServiceRepository()
				.lookupGestionUnidadDocumentalBI();

		udocBI.eliminarUnidadesDocumentalesPrestadasNoDevueltas();
	}

	/**
	 * Comprueba la validez de los documentos vitales vigentes y los pasa a
	 * histórico si han caducado.
	 * 
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public void pasarAHistoricoDocumentosVitalesCaducados() throws Exception {
		authenticate();

		GestionDocumentosVitalesBI docVitBI = getServiceRepository()
				.lookupGestionDocumentosVitalesBI();

		docVitBI.pasarAHistoricoDocumentosVitalesCaducados();
	}

	/**
	 * Elimina los documentos vitales históricos que no están referenciados en
	 * ningún expediente.
	 * 
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public void eliminarDocumentosVitalesObsoletos() throws Exception {
		authenticate();

		GestionDocumentosVitalesBI docVitBI = getServiceRepository()
				.lookupGestionDocumentosVitalesBI();

		docVitBI.eliminarDocumentosVitalesObsoletos();
	}

}
