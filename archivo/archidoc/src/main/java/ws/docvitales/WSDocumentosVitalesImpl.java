package ws.docvitales;

import fondos.vos.DocumentoAntecedenteVO;
import fondos.vos.SerieDocAntVO;
import gcontrol.model.TipoAcceso;

import java.util.List;

import common.bi.GestionDescripcionBI;
import common.bi.GestionDocumentosVitalesBI;
import common.webservices.WSBase;

import descripcion.model.xml.card.Ficha;
import descripcion.model.xml.card.TipoFicha;
import docvitales.vos.DocumentoVitalExtVO;
import docvitales.vos.InfoBDocumentoVitalExtVO;
import docvitales.webservice.utils.IsadgHelper;
import docvitales.webservice.vos.DocumentoAntecedente;
import docvitales.webservice.vos.DocumentoVital;
import docvitales.webservice.vos.FormDocumentoVital;
import docvitales.webservice.vos.InfoBDocumentoVital;
import docvitales.webservice.vos.Serie;

/**
 * Servicio Web de acceso a los documentos vitales.
 */
public class WSDocumentosVitalesImpl extends WSBase implements
		WSDocumentosVitales {

	/**
	 * Constructor.
	 */
	public WSDocumentosVitalesImpl() {
		super();
	}

	/**
	 * Obtiene la lista de series que contienen documentos antecedentes de un
	 * tercero.
	 * 
	 * @param idTercero
	 *            Identificador del tercero.
	 * @return Lista de series.
	 */
	public Serie[] getSeriesTercero(String idTercero) throws Exception {
		authenticate();

		GestionDocumentosVitalesBI docVitBI = getServiceRepository()
				.lookupGestionDocumentosVitalesBI();

		List series = docVitBI.getSeriesDocumentosAntecedentes(idTercero);

		return getSerieArray(series);
	}

	/**
	 * Recupera la lista de documentos antecedentes de un tercero pertenecientes
	 * a una serie determinada.
	 * 
	 * @param idTercero
	 *            Identificador del tercero.
	 * @param idSerie
	 *            Identificador de la serie.
	 * @return Lista de documentos antecedentes.
	 */
	public DocumentoAntecedente[] getDocumentosAntecedentes(String idTercero,
			String idSerie) throws Exception {
		authenticate();

		GestionDocumentosVitalesBI docVitBI = getServiceRepository()
				.lookupGestionDocumentosVitalesBI();

		List documentos = docVitBI
				.getDocumentosAntecedentes(idTercero, idSerie);

		return getDocumentoAntecedenteArray(documentos);
	}

	/**
	 * Recupera la lista de documentos antecedentes de un tercero.
	 * 
	 * @param idTercero
	 *            Identificador del tercero.
	 * @return Lista de documentos antecedentes.
	 */
	public DocumentoAntecedente[] getDocumentosAntecedentesTercero(
			String idTercero) throws Exception {
		authenticate();

		GestionDocumentosVitalesBI docVitBI = getServiceRepository()
				.lookupGestionDocumentosVitalesBI();

		List documentos = docVitBI.getDocumentosAntecedentes(idTercero);

		return getDocumentoAntecedenteArray(documentos);
	}

	/**
	 * Obtiene la ficha ISAD(G) de una unidad documental.
	 * 
	 * @param idDocAnt
	 *            Identificador de la unidad documental.
	 * @return Ficha ISAD(G).
	 */
	public String getISADG(String idDocAnt) throws Exception {
		authenticate();

		GestionDescripcionBI descripcionBI = getServiceRepository()
				.lookupGestionDescripcionBI();

		// Ficha ISAD(G)
		Ficha ficha = descripcionBI.componerFicha(idDocAnt,
				TipoFicha.FICHA_ELEMENTO_CF, TipoAcceso.CONSULTA, null, false);

		return IsadgHelper.toString(ficha);
	}

	/**
	 * Recupera la lista de documentos vitales de un tercero.
	 * 
	 * @param idTercero
	 *            Identificador del tercero.
	 * @return Lista de documentos vitales.
	 */
	public InfoBDocumentoVital[] getDocumentosVitalesTercero(String idTercero)
			throws Exception {
		authenticate();

		GestionDocumentosVitalesBI docVitBI = getServiceRepository()
				.lookupGestionDocumentosVitalesBI();

		List documentos = docVitBI
				.getDocumentosVitalesVigentes(idTercero, null);

		return getInfoBDocumentoVitalArray(documentos);
	}

	/**
	 * Recupera la lista de documentos vitales de un tercero asociados a un
	 * procedimiento.
	 * 
	 * @param idTercero
	 *            Identificador del tercero.
	 * @param idProc
	 *            Identificador del procedimiento.
	 * @return Lista de documentos vitales.
	 */
	public InfoBDocumentoVital[] getDocumentosVitalesTerProc(String idTercero,
			String idProc) throws Exception {
		authenticate();

		GestionDocumentosVitalesBI docVitBI = getServiceRepository()
				.lookupGestionDocumentosVitalesBI();

		List documentos = docVitBI.getDocumentosVitalesVigentes(idTercero,
				idProc);

		return getInfoBDocumentoVitalArray(documentos);
	}

	/**
	 * Recupera un documento vital.
	 * 
	 * @param id
	 *            Identificador del documento vital.
	 * @return Documento vital.
	 */
	public DocumentoVital getDocumentoVital(String id) throws Exception {
		authenticate();

		DocumentoVital doc = null;

		GestionDocumentosVitalesBI docVitBI = getServiceRepository()
				.lookupGestionDocumentosVitalesBI();

		DocumentoVitalExtVO docVit = docVitBI.getDocumentoVital(id);
		if (docVit != null)
			doc = new DocumentoVital(docVit);

		return doc;
	}

	/**
	 * Crea un documento vital.
	 * 
	 * @param formDocVit
	 *            Información del documento vital.
	 * @return Identificador del documento vital creado.
	 */
	public InfoBDocumentoVital altaDocumentoVital(FormDocumentoVital formDocVit)
			throws Exception {
		authenticate();

		GestionDocumentosVitalesBI docVitBI = getServiceRepository()
				.lookupGestionDocumentosVitalesBI();

		return new InfoBDocumentoVital(docVitBI.insertDocumentoVital(formDocVit
				.getVO()));
	}

	/**
	 * Vincula un expediente a un documento vital.
	 * 
	 * @param idDocVit
	 *            Identificador del documento vital.
	 * @param idExp
	 *            Identificador del expediente de backoffice.
	 * @param idSist
	 *            Identificador del sistema de backoffice.
	 * @param usuario
	 *            Nombre completo del usuario de backoffice.
	 */
	public void vinculaExpediente(String idDocVit, String idExp, String idSist,
			String usuario) throws Exception {
		authenticate();

		GestionDocumentosVitalesBI docVitBI = getServiceRepository()
				.lookupGestionDocumentosVitalesBI();

		docVitBI.vinculaExpediente(idDocVit, idExp, idSist, usuario);
	}

	/**
	 * Elimina los vínculos de un expediente.
	 * 
	 * @param idExp
	 *            Identificador del expediente de backoffice.
	 * @param idSist
	 *            Identificador del sistema de backoffice.
	 */
	public void eliminaVinculosExpediente(String idExp, String idSist)
			throws Exception {
		authenticate();

		GestionDocumentosVitalesBI docVitBI = getServiceRepository()
				.lookupGestionDocumentosVitalesBI();

		docVitBI.eliminaVinculosExpediente(idExp, idSist);
	}

	/**
	 * Convierte la lista de series a un array.
	 * 
	 * @param series
	 *            Lista de series.
	 * @return Array de series.
	 */
	private Serie[] getSerieArray(List series) {
		Serie[] array = new Serie[series.size()];
		for (int i = 0; i < series.size(); i++)
			array[i] = new Serie((SerieDocAntVO) series.get(i));
		return array;
	}

	/**
	 * Convierte la lista de documentos antecedentes a un array.
	 * 
	 * @param documentos
	 *            Lista de documentos antecedentes.
	 * @return Array de documentos antecedentes.
	 */
	private DocumentoAntecedente[] getDocumentoAntecedenteArray(List documentos) {
		DocumentoAntecedente[] docs = new DocumentoAntecedente[documentos
				.size()];
		for (int i = 0; i < documentos.size(); i++)
			docs[i] = new DocumentoAntecedente(
					(DocumentoAntecedenteVO) documentos.get(i));
		return docs;
	}

	/**
	 * Convierte la lista de documentos a un array.
	 * 
	 * @param documentos
	 *            Lista de documentos vitales.
	 * @return Array de documentos vitales.
	 */
	private InfoBDocumentoVital[] getInfoBDocumentoVitalArray(List documentos) {
		InfoBDocumentoVital[] docs = new InfoBDocumentoVital[documentos.size()];
		for (int i = 0; i < documentos.size(); i++)
			docs[i] = new InfoBDocumentoVital(
					(InfoBDocumentoVitalExtVO) documentos.get(i));
		return docs;
	}

}
