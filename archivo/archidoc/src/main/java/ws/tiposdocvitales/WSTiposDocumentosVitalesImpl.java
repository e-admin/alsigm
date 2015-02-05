package ws.tiposdocvitales;

import java.util.List;

import common.bi.GestionDocumentosVitalesBI;
import common.webservices.WSBase;

import docvitales.vos.TipoDocumentoVitalVO;
import docvitales.webservice.vos.TipoDocumentoVital;

/**
 * Servicio Web de acceso a los tipos de documentos vitales.
 */
public class WSTiposDocumentosVitalesImpl extends WSBase implements
		WSTiposDocumentosVitales {

	/**
	 * Constructor.
	 */
	public WSTiposDocumentosVitalesImpl() {
		super();
	}

	/**
	 * Obtiene la lista de tipos de documentos vitales.
	 * 
	 * @return Lista de documentos vitales.
	 */
	public TipoDocumentoVital[] getTiposDocVit() throws Exception {
		authenticate();

		GestionDocumentosVitalesBI docVitBI = getServiceRepository()
				.lookupGestionDocumentosVitalesBI();

		List tipos = docVitBI.getTiposDocumentosVitales();

		return getTipoDocumentoVitalArray(tipos);
	}

	/**
	 * Obtiene la lista de tipos de documentos vitales de un procedimiento.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 * @return Lista de documentos vitales.
	 */
	public TipoDocumentoVital[] getTiposDocVitPorProc(String idProc)
			throws Exception {
		authenticate();

		GestionDocumentosVitalesBI docVitBI = getServiceRepository()
				.lookupGestionDocumentosVitalesBI();

		List tipos = docVitBI.getTiposDocumentosVitales(idProc);

		return getTipoDocumentoVitalArray(tipos);
	}

	/**
	 * Obtiene el tipo de documento vital.
	 * 
	 * @param id
	 *            Identificador del tipo de documento vital.
	 * @return Tipo de documento vital.
	 */
	public TipoDocumentoVital getTipoDocVit(String id) throws Exception {
		authenticate();

		TipoDocumentoVital tipo = null;

		GestionDocumentosVitalesBI docVitBI = getServiceRepository()
				.lookupGestionDocumentosVitalesBI();

		TipoDocumentoVitalVO tipoDocVit = docVitBI.getTipoDocumentoVital(id);
		if (tipoDocVit != null)
			tipo = new TipoDocumentoVital(tipoDocVit);

		return tipo;
	}

	/**
	 * Crea una relación entre un procedimiento y un tipo de documento vital.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 * @param idTipoDocVit
	 *            Identificador del tipo de documento vital.
	 */
	public void addRelTipoDocVitProc(String idProc, String idTipoDocVit)
			throws Exception {
		authenticate();

		GestionDocumentosVitalesBI docVitBI = getServiceRepository()
				.lookupGestionDocumentosVitalesBI();

		docVitBI.insertTipoDocVitProc(idProc, idTipoDocVit);
	}

	/**
	 * Elimina la relación entre un procedimiento y un tipo de documento vital.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 * @param idTipoDocVit
	 *            Identificador del tipo de documento vital.
	 */
	public void removeRelTipoDocVitProc(String idProc, String idTipoDocVit)
			throws Exception {
		authenticate();

		GestionDocumentosVitalesBI docVitBI = getServiceRepository()
				.lookupGestionDocumentosVitalesBI();

		docVitBI.deleteTipoDocVit(idProc, idTipoDocVit);
	}

	/**
	 * Elimina todas las relaciones entre tipos de documentos vitales con un
	 * procedimiento.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 */
	public void removeRelsPorProc(String idProc) throws Exception {
		authenticate();

		GestionDocumentosVitalesBI docVitBI = getServiceRepository()
				.lookupGestionDocumentosVitalesBI();

		docVitBI.deleteTiposDocVitByIdProc(idProc);
	}

	/**
	 * Elimina la relación entre un procedimiento y una lista de tipos de
	 * documentos vitales.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 * @param idsTipoDocVit
	 *            Lista de identificadores de tipos de documentos vitales.
	 */
	public void removeRelsPorTiposDocProc(String idProc, String[] idsTipoDocVit)
			throws Exception {
		authenticate();

		GestionDocumentosVitalesBI docVitBI = getServiceRepository()
				.lookupGestionDocumentosVitalesBI();

		docVitBI.deleteTiposDocVit(idProc, idsTipoDocVit);
	}

	/**
	 * Convierte la lista de tipos a un array.
	 * 
	 * @param tipos
	 *            Lista de tipos de documentos vitales.
	 * @return Array de tipos de documentos vitales.
	 */
	private TipoDocumentoVital[] getTipoDocumentoVitalArray(List tipos) {
		TipoDocumentoVital[] array = new TipoDocumentoVital[tipos.size()];
		for (int i = 0; i < tipos.size(); i++)
			array[i] = new TipoDocumentoVital(
					(TipoDocumentoVitalVO) tipos.get(i));
		return array;
	}

}
