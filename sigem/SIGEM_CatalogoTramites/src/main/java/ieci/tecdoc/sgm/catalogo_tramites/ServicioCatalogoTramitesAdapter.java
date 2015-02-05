package ieci.tecdoc.sgm.catalogo_tramites;

import ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesCodigosError;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.catalogo.CatalogoTramitesExcepcion;
import ieci.tecdoc.sgm.core.services.catalogo.Conector;
import ieci.tecdoc.sgm.core.services.catalogo.ConectorAutenticacion;
import ieci.tecdoc.sgm.core.services.catalogo.Conectores;
import ieci.tecdoc.sgm.core.services.catalogo.ConectoresAutenticacion;
import ieci.tecdoc.sgm.core.services.catalogo.Documento;
import ieci.tecdoc.sgm.core.services.catalogo.DocumentoExtendido;
import ieci.tecdoc.sgm.core.services.catalogo.DocumentoTramite;
import ieci.tecdoc.sgm.core.services.catalogo.Documentos;
import ieci.tecdoc.sgm.core.services.catalogo.OrganoDestinatario;
import ieci.tecdoc.sgm.core.services.catalogo.OrganosDestinatarios;
import ieci.tecdoc.sgm.core.services.catalogo.ServicioCatalogoTramites;
import ieci.tecdoc.sgm.core.services.catalogo.TipoConector;
import ieci.tecdoc.sgm.core.services.catalogo.TiposConectores;
import ieci.tecdoc.sgm.core.services.catalogo.Tramite;
import ieci.tecdoc.sgm.core.services.catalogo.TramiteConsulta;
import ieci.tecdoc.sgm.core.services.catalogo.Tramites;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.registro.OfficeInfo;
import ieci.tecdoc.sgm.core.services.registro.RegistroException;
import ieci.tecdoc.sgm.core.services.registro.ServicioRegistro;
import ieci.tecdoc.sgm.core.services.registro.UserInfo;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class ServicioCatalogoTramitesAdapter implements
		ServicioCatalogoTramites {

	private static final Logger logger = Logger
			.getLogger(ServicioCatalogoTramitesAdapter.class);


	/**
	 * Locale de usuario presencial
	 */
	private Locale registryUserLocale = new Locale("es");
	
	
	/**
	 * Añade un nuevo trámite al catálogo.
	 *
	 * @param procedure
	 *            Información del trámite.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public void addProcedure(Tramite procedure, Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			// Comprobar que el código de asunto asignado al trámite
			// existe como tipo de asunto asociado a la oficina asignada
			// en registro presencial para el usuario configurado que
			// realizará la consolidación
			checkTopicOfficeCodes(procedure, entidad);

			CatalogoTramites.addProcedure(getTramiteImpl(procedure), entidad
					.getIdentificador());
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.info("Error al crear un tramite.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al insertar un tramite.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Recupera la información de un trámite del catálogo de trámites.
	 *
	 * @param procedureId
	 *            Identificador del trámite.
	 * @param loadDocuments
	 *            Si hay que cargar la información de los documentos.
	 * @return La información del trámite.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public Tramite getProcedure(String procedureId, boolean loadDocuments,
			Entidad entidad) throws CatalogoTramitesExcepcion {
		try {
			return getTramiteServicio((ieci.tecdoc.sgm.catalogo_tramites.util.TramiteImpl) CatalogoTramites
					.getProcedure(procedureId, loadDocuments, entidad
							.getIdentificador()));
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener un tramite.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener un tramite.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Recupera un conjunto de trámites del catálogo de trámites a partir de
	 * unos valores de búsqueda.
	 *
	 * @param query
	 *            Información con los valores de búsqueda.
	 * @return La lista de trámites que cumplen los criterios de búsqueda.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public Tramites query(TramiteConsulta query, Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			return getTramitesServicio(CatalogoTramites.query(
					getTramiteConsultaImpl(query), entidad.getIdentificador()));
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener tramites.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener tramites.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Elimina un trámite del catálogo de trámites.
	 *
	 * @param procedureId
	 *            Identificador del trámite.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public void deleteProcedure(String procedureId, Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			CatalogoTramites.deleteProcedure(procedureId, entidad
					.getIdentificador());
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al eliminar tramites.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al eliminar tramites.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Actualiza el obejto trámite que se pasa como parámtro.
	 *
	 * @param procedure
	 *            Trámite con los nuevos datos.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public void updateProcedure(Tramite procedure, Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			// Comprobar que el código de asunto asignado al trámite
			// existe como tipo de asunto asociado a la oficina asignada
			// en registro presencial para el usuario configurado que
			// realizará la consolidación
			checkTopicOfficeCodes(procedure, entidad);

			CatalogoTramites.updateProcedure(getTramiteImpl(procedure), entidad
					.getIdentificador());
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.info("Error al actualizar un tramite.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al actualizar un tramite.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Indica si un documento está siendo referenciado por algún trámite
	 * definido en el catálogo de trámites.
	 *
	 * @param documentId
	 *            Identificador del documento.
	 * @return Si el documento está referenciado.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public boolean isDocumentReferenced(String documentId, Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			return CatalogoTramites.isDocumentReferenced(documentId, entidad
					.getIdentificador());
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al comprobar referencia a tramite.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error(
					"Error inesperado al comprobar referencia al tramite.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Asocia un documento a un trámite.
	 *
	 * @param procedureDocument
	 *            Relación de asociación entre el documento y el trámite
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public void addProcedureDocument(DocumentoTramite procedureDocument,
			Entidad entidad) throws CatalogoTramitesExcepcion {
		try {
			CatalogoTramites.addProcedureDocument(
					getDocumentoTramiteImpl(procedureDocument), entidad
							.getIdentificador());
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al asociar documento a tramite.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al asociar documento a tramite.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Elimina una asociación de documento a un trámite.
	 *
	 * @param procedureDocument
	 *            Relación de asociación entre el documento y el trámite
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public void deleteProcedureDocument(DocumentoTramite procedureDocument,
			Entidad entidad) throws CatalogoTramitesExcepcion {
		try {
			CatalogoTramites.deleteProcedureDocument(
					getDocumentoTramiteImpl(procedureDocument), entidad
							.getIdentificador());
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al eliminar documento a tramite.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger
					.error("Error inesperado al eliminar documento a tramite.",
							e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Recupera la información de un documento.
	 *
	 * @param documentId
	 *            Identificador del documento.
	 * @return El documento.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public Documento getDocument(String documentId, Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			return getDocumentoServicio((ieci.tecdoc.sgm.catalogo_tramites.util.DocumentoImpl) CatalogoTramites
					.getDocument(documentId, entidad.getIdentificador()));
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener documento.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener documento.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Añade un nuevo tipo de documento al catálogo.
	 *
	 * @param document
	 *            Información del documento.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public void addDocument(Documento document, Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			CatalogoTramites.addDocument(getDocumentoImpl(document), entidad
					.getIdentificador());
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al insertar documento.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al insertar documento.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Elimina un documento.
	 *
	 * @param documentId
	 *            Identificador del documento.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public void deleteDocument(String documentId, Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			CatalogoTramites.deleteDocument(documentId, entidad
					.getIdentificador());
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al eliminar documento.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al eliminar documento.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Actualiza el documento que se pasa como parámetro.
	 *
	 * @param document
	 *            Documento con los nuevos datos.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public void updateDocument(Documento document, Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			CatalogoTramites.updateDocument(getDocumentoImpl(document), entidad
					.getIdentificador());
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al actualizar documento.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al actualizar documento.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Recupera la lista de documentos.
	 *
	 * @return La lista mencionada.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public Documentos getDocuments(Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			return getDocumentosServicio(CatalogoTramites.getDocuments(entidad
					.getIdentificador()));
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener documentos.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener documentos.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Devuelve una colección con los documentos asociados a un determinado
	 * procedimientos.
	 *
	 * @param procedureId
	 *            Identificador del procedimiento del que se quieren recuperar
	 *            los documentos.
	 * @return Documents Colección de documentos.
	 * @throws CatalogoTramitesExcepcion
	 *             En caso de producirse algún error.
	 */
	public Documentos getProcedureDocuments(String procedureId, Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			return getDocumentosServicio(CatalogoTramites
					.getProcedureDocuments(procedureId, entidad
							.getIdentificador()));
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener documentos de un tramite.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error(
					"Error inesperado al obtener documentos de un tramite.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Devuelve el documento que corresponde a los datos que se pasan como
	 * parámetros.
	 *
	 * @param procedureId
	 *            Identificador del procedimiento del que se quiere recuperar el
	 *            documento.
	 * @param documentId
	 *            Identificador del documento.
	 * @return ProcedureDocument Documento.
	 * @throws CatalogoTramitesExcepcion
	 *             En caso de producirse algún error.
	 */
	public DocumentoTramite getProcedureDocument(String procedureId,
			String documentId, Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			return getDocumentoTramiteServicio(CatalogoTramites
					.getProcedureDocument(procedureId, documentId, entidad
							.getIdentificador()));
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener documento de un tramite.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error(
					"Error inesperado al obtener documento de un tramite.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Devuelve el documento que corresponde a los datos que se pasan como
	 * parámetros.
	 *
	 * @param procedureId
	 *            Identificador del procedimiento del que se quiere recuperar el
	 *            documento.
	 * @param documentId
	 *            Identificador del documento.
	 * @param code
	 *            Codigo del documento.
	 * @return ProcedureDocument Documento.
	 * @throws CatalogoTramitesExcepcion
	 *             En caso de producirse algún error.
	 */
	public DocumentoTramite getProcedureDocument(String procedureId,
			String documentId, String code, Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			return getDocumentoTramiteServicio(CatalogoTramites
					.getProcedureDocument(procedureId, documentId, code,
							entidad.getIdentificador()));
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener documento de un tramite.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error(
					"Error inesperado al obtener documento de un tramite.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Actualiza el objeto trámite-documento que se pasa como parámtro.
	 *
	 * @param procedure
	 *            Trámite-Documento con los nuevos datos.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public void updateProcedureDocument(DocumentoTramite procedure,
			Entidad entidad) throws CatalogoTramitesExcepcion {
		try {
			CatalogoTramites.updateProcedureDocument(
					getDocumentoTramiteImpl(procedure), entidad
							.getIdentificador());
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al actualizar documentos de un tramite.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error(
					"Error inesperado al actualizar documentos de un tramite.",
					e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Recupera una colección con los procedimientos almacenados en el sistema.
	 *
	 * @return Procedures Colección de procedimientos.
	 * @throws CatalogoTramitesExcepcion
	 *             En caso de producirse algún error.
	 */
	public Tramites getProcedures(Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			return getTramitesServicio(CatalogoTramites.getProcedures(entidad
					.getIdentificador()));
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener tramites.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener tramites.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Recupera un documento a partir de su código.
	 *
	 * @param code
	 *            Código del documento a recuperar.
	 * @return Document Documento
	 * @throws CatalogoTramitesExcepcion
	 *             En caso de producirse algún error.
	 */
	public Documento getDocumentfromCode(String code, Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			return getDocumentoServicio((ieci.tecdoc.sgm.catalogo_tramites.util.DocumentoImpl) CatalogoTramites
					.getDocumentfromCode(code, entidad.getIdentificador()));
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener documento.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener documento.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Recupera la información de un órgano destinatario.
	 *
	 * @param addresseeId
	 *            Identificador del órgano destinatario.
	 * @return El órgano destinatario.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public OrganoDestinatario getAddressee(String addresseeId, Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			return getOrganoDestinatarioServicio((ieci.tecdoc.sgm.catalogo_tramites.util.OrganoDestinatarioImpl) CatalogoTramites
					.getAddressee(addresseeId, entidad.getIdentificador()));
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener organo destinatario.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener organo destinatario.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Añade un nuevo órgano destinatario al catálogo.
	 *
	 * @param addressee
	 *            Información del órgano destinatario.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public void addAddressee(OrganoDestinatario addressee, Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			CatalogoTramites.addAddressee(getOrganoDestinatarioImpl(addressee),
					entidad.getIdentificador());
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al insertar organo destinatario.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger
					.error("Error inesperado al insertar organo destinatario.",
							e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Elimina un órgano destinatario.
	 *
	 * @param addresseeId
	 *            Identificador del órgano destinatario.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public void deleteAddressee(String addresseeId, Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			CatalogoTramites.deleteAddressee(addresseeId, entidad
					.getIdentificador());
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al eliminar organo destinatario.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error eliminar al obtener organo destinatario.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Actualiza el órgano destinatario que se pasa como parámetro.
	 *
	 * @param addressee
	 *            Órgano destinatario con los nuevos datos.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public void updateAddressee(OrganoDestinatario addressee, Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			CatalogoTramites.updateAddressee(
					getOrganoDestinatarioImpl(addressee), entidad
							.getIdentificador());
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al actualizar organo destinatario.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al actualizar organo destinatario.",
					e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Recupera la lista de órganos destinatarios.
	 *
	 * @return La lista mencionada.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public OrganosDestinatarios getAddressees(Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			return getOrganosDestinatariosServicio(CatalogoTramites
					.getAddressees(entidad.getIdentificador()));
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener organos destinatarios.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener organos destinatarios.",
					e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Recupera la información de un conector.
	 *
	 * @param hookId
	 *            Identificador del conector.
	 * @return El conector.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public Conector getHook(String hookId, Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			return getConectorServicio((ieci.tecdoc.sgm.catalogo_tramites.util.ConectorImpl) CatalogoTramites
					.getHook(hookId, entidad.getIdentificador()));
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener conector.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener conector.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Añade un nuevo conector al catálogo.
	 *
	 * @param hook
	 *            Información del conector.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public void addHook(Conector hook, Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			CatalogoTramites.addHook(getConectorImpl(hook), entidad
					.getIdentificador());
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al insertar conector.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al insertar conector.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Elimina un conector.
	 *
	 * @param hookId
	 *            Identificador del conector.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public void deleteHook(String hookId, Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			CatalogoTramites.deleteHook(hookId, entidad.getIdentificador());
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al eliminar conector.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al eliminar conector.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Actualiza el conector que se pasa como parámetro.
	 *
	 * @param hook
	 *            Conector con los nuevos datos.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public void updateHook(Conector hook, Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			CatalogoTramites.updateHook(getConectorImpl(hook), entidad
					.getIdentificador());
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al actualizar conector.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al actualizar conector.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Recupera la lista de conectores.
	 *
	 * @return La lista mencionada.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public Conectores getHooks(Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			return getConectoresServicio(CatalogoTramites.getHooks(-1, entidad
					.getIdentificador()));
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener conectores.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener conectores.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Recupera la lista de conectores.
	 *
	 * @return La lista mencionada.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public Conectores getHooksByType(int hookType, Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			return getConectoresServicio(CatalogoTramites.getHooks(hookType,
					entidad.getIdentificador()));
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener conectores por tipo.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener conectores por tipo.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Recupera la información de un tipo de conector.
	 *
	 * @param typeId
	 *            Identificador del tipo de conector.
	 * @return El tipo de conector.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public TipoConector getHookType(int typeId, Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			return getTipoConectorServicio((ieci.tecdoc.sgm.catalogo_tramites.util.TipoConectorImpl) CatalogoTramites
					.getHookType(typeId, entidad.getIdentificador()));
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener tipo de conector.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener tipo de conector.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Añade un nuevo tipo de conector al catálogo.
	 *
	 * @param hookType
	 *            Información del tipo de conector.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public void addHookType(TipoConector hookType, Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			CatalogoTramites.addHookType(getTipoConectorImpl(hookType), entidad
					.getIdentificador());
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al insertar tipo de conector.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al insertar tipo de conector.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Elimina un tipo de conector.
	 *
	 * @param typeId
	 *            Identificador del tipo de conector.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public void deleteHookType(int typeId, Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			CatalogoTramites.deleteHookType(typeId, entidad.getIdentificador());
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al eliminar tipo de conector.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al eliminar tipo de conector.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Actualiza el tipo de conector que se pasa como parámetro.
	 *
	 * @param hookType
	 *            Tipo de conector con los nuevos datos.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public void updateHookType(TipoConector hookType, Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			CatalogoTramites.updateHookType(getTipoConectorImpl(hookType),
					entidad.getIdentificador());
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al actualizar tipo de conector.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al actualizar tipo de conector.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Recupera la lista de tipos de conectores.
	 *
	 * @return La lista mencionada.
	 * @throws CatalogoTramitesExcepcion
	 *             Si se produce algún error.
	 */
	public TiposConectores getHookTypes(Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			return getTiposConectoresServicio(CatalogoTramites
					.getHookTypes(entidad.getIdentificador()));
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener tipos de conectores.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener tipos de conectores.", e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	private CatalogoTramitesExcepcion getCatalogoTramitesExcepcion(
			ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion poException) {
		if (poException == null) {
			return new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION);
		}
		StringBuffer cCodigo = new StringBuffer(
				ConstantesServicios.SERVICE_PROCEDURES_ERROR_PREFIX);
		cCodigo.append(String.valueOf(poException.getErrorCode()));
		return new CatalogoTramitesExcepcion(Long.valueOf(cCodigo.toString())
				.longValue(), poException);

	}

	private ieci.tecdoc.sgm.catalogo_tramites.util.TramiteImpl getTramiteImpl(
			Tramite poTramite) {
		ieci.tecdoc.sgm.catalogo_tramites.util.TramiteImpl oTramite = new ieci.tecdoc.sgm.catalogo_tramites.util.TramiteImpl();
		if (poTramite == null)
			return oTramite;

		oTramite.setAddressee(poTramite.getAddressee());
		oTramite.setDescription(poTramite.getDescription());
		oTramite.setDocuments(getDocumentosImpl(poTramite.getDocuments()));
		oTramite.setFirma(poTramite.getFirma());
		oTramite.setId(poTramite.getId());
		oTramite.setLimitDocs(poTramite.getLimitDocs());
		oTramite.setOficina(poTramite.getOficina());
		oTramite.setTopic(poTramite.getTopic());
		oTramite.setIdProcedimiento(poTramite.getIdProcedimiento());

		return oTramite;
	}

	private Tramite getTramiteServicio(
			ieci.tecdoc.sgm.catalogo_tramites.util.TramiteImpl poTramite) {
		Tramite oTramite = new Tramite();
		if (poTramite == null)
			return oTramite;

		oTramite.setAddressee(poTramite.getAddressee());
		oTramite.setDescription(poTramite.getDescription());
		oTramite.setDocuments(getDocumentosServicio(poTramite.getDocuments()));
		oTramite.setFirma(poTramite.getFirma());
		oTramite.setId(poTramite.getId());
		oTramite.setLimitDocs(poTramite.getLimitDocs());
		oTramite.setOficina(poTramite.getOficina());
		oTramite.setTopic(poTramite.getTopic());
		oTramite.setIdProcedimiento(poTramite.getIdProcedimiento());

		return oTramite;
	}

	private ieci.tecdoc.sgm.catalogo_tramites.util.TramiteConsulta getTramiteConsultaImpl(
			TramiteConsulta poTramiteConsulta) {
		ieci.tecdoc.sgm.catalogo_tramites.util.TramiteConsulta oTramiteConsulta = new ieci.tecdoc.sgm.catalogo_tramites.util.TramiteConsulta();
		if (poTramiteConsulta == null)
			return oTramiteConsulta;

		oTramiteConsulta.setAddressee(poTramiteConsulta.getAddressee());
		oTramiteConsulta.setId(poTramiteConsulta.getId());
		oTramiteConsulta.setTopic(poTramiteConsulta.getTopic());
		oTramiteConsulta.setSubject(poTramiteConsulta.getSubject());
		oTramiteConsulta.setSubtype(poTramiteConsulta.getSubtype());
		oTramiteConsulta.setType(poTramiteConsulta.getType());

		return oTramiteConsulta;
	}

	private Tramites getTramitesServicio(
			ieci.tecdoc.sgm.catalogo_tramites.util.Tramites poTramites) {
		Tramites oTramites = new Tramites();
		if (poTramites == null)
			return oTramites;

		for (int i = 0; i < poTramites.count(); i++)
			oTramites
					.add(getTramiteServicio((ieci.tecdoc.sgm.catalogo_tramites.util.TramiteImpl) poTramites
							.get(i)));

		return oTramites;
	}

	private ieci.tecdoc.sgm.catalogo_tramites.util.DocumentoTramiteImpl getDocumentoTramiteImpl(
			DocumentoTramite poDocumentoTramite) {
		ieci.tecdoc.sgm.catalogo_tramites.util.DocumentoTramiteImpl oDocumentoTramite = new ieci.tecdoc.sgm.catalogo_tramites.util.DocumentoTramiteImpl();
		if (poDocumentoTramite == null)
			return oDocumentoTramite;

		oDocumentoTramite.setCode(poDocumentoTramite.getCode());
		oDocumentoTramite.setDocumentId(poDocumentoTramite.getDocumentId());
		oDocumentoTramite.setMandatory(poDocumentoTramite.isMandatory());
		oDocumentoTramite.setProcedureId(poDocumentoTramite.getProcedureId());

		return oDocumentoTramite;
	}

	private Documento getDocumentoServicio(
			ieci.tecdoc.sgm.catalogo_tramites.util.DocumentoImpl poDocumento) {
		Documento oDocumento = new Documento();
		if (poDocumento == null)
			return oDocumento;

		oDocumento.setDescription(poDocumento.getDescription());
		oDocumento.setExtension(poDocumento.getExtension());
		oDocumento.setId(poDocumento.getId());
		oDocumento.setSignatureHook(poDocumento.getSignatureHook());
		oDocumento.setValidationHook(poDocumento.getValidationHook());

		return oDocumento;
	}

	private ieci.tecdoc.sgm.catalogo_tramites.util.DocumentoImpl getDocumentoImpl(
			Documento poDocumento) {
		ieci.tecdoc.sgm.catalogo_tramites.util.DocumentoImpl oDocumento = new ieci.tecdoc.sgm.catalogo_tramites.util.DocumentoImpl();
		if (poDocumento == null)
			return oDocumento;

		oDocumento.setDescription(poDocumento.getDescription());
		oDocumento.setExtension(poDocumento.getExtension());
		oDocumento.setId(poDocumento.getId());
		oDocumento.setSignatureHook(poDocumento.getSignatureHook());
		oDocumento.setValidationHook(poDocumento.getValidationHook());

		return oDocumento;
	}

	private Documentos getDocumentosServicio(
			ieci.tecdoc.sgm.catalogo_tramites.util.Documentos poDocumentos) {
		Documentos oDocumentos = new Documentos();
		if (poDocumentos == null)
			return oDocumentos;

		for (int i = 0; i < poDocumentos.count(); i++)
			oDocumentos
					.add(getDocumentoExtendidoServicio((ieci.tecdoc.sgm.catalogo_tramites.util.DocumentoExtendido) poDocumentos
							.get(i)));

		return oDocumentos;
	}

	private ieci.tecdoc.sgm.catalogo_tramites.util.Documentos getDocumentosImpl(
			Documentos poDocumentos) {
		ieci.tecdoc.sgm.catalogo_tramites.util.Documentos oDocumentos = new ieci.tecdoc.sgm.catalogo_tramites.util.Documentos();
		if (poDocumentos == null)
			return oDocumentos;

		for (int i = 0; i < poDocumentos.count(); i++)
			oDocumentos
					.add(getDocumentoExtendidoImpl((DocumentoExtendido) poDocumentos
							.get(i)));

		return oDocumentos;
	}

	private DocumentoExtendido getDocumentoExtendidoServicio(
			ieci.tecdoc.sgm.catalogo_tramites.util.DocumentoExtendido poDocumento) {
		DocumentoExtendido oDocumento = new DocumentoExtendido();
		if (poDocumento == null)
			return oDocumento;

		oDocumento.setDescription(poDocumento.getDescription());
		oDocumento.setExtension(poDocumento.getExtension());
		oDocumento.setId(poDocumento.getId());
		oDocumento.setSignatureHook(poDocumento.getSignatureHook());
		oDocumento.setValidationHook(poDocumento.getValidationHook());
		oDocumento.setCode(poDocumento.getCode());
		oDocumento.setMandatory(poDocumento.isMandatory());

		return oDocumento;
	}

	private ieci.tecdoc.sgm.catalogo_tramites.util.DocumentoExtendido getDocumentoExtendidoImpl(
			DocumentoExtendido poDocumento) {
		ieci.tecdoc.sgm.catalogo_tramites.util.DocumentoExtendido oDocumento = new ieci.tecdoc.sgm.catalogo_tramites.util.DocumentoExtendido();
		if (poDocumento == null)
			return oDocumento;

		oDocumento.setDescription(poDocumento.getDescription());
		oDocumento.setExtension(poDocumento.getExtension());
		oDocumento.setId(poDocumento.getId());
		oDocumento.setSignatureHook(poDocumento.getSignatureHook());
		oDocumento.setValidationHook(poDocumento.getValidationHook());
		oDocumento.setCode(poDocumento.getCode());
		oDocumento.setMandatory(poDocumento.isMandatory());

		return oDocumento;
	}

	private DocumentoTramite getDocumentoTramiteServicio(
			ieci.tecdoc.sgm.catalogo_tramites.util.DocumentoTramite poDocumentoTramite) {
		DocumentoTramite oDocumentoTramite = new DocumentoTramite();
		if (poDocumentoTramite == null)
			return oDocumentoTramite;

		oDocumentoTramite.setCode(poDocumentoTramite.getCode());
		oDocumentoTramite.setDocumentId(poDocumentoTramite.getDocumentId());
		oDocumentoTramite.setMandatory(poDocumentoTramite.isMandatory());
		oDocumentoTramite.setProcedureId(poDocumentoTramite.getProcedureId());

		return oDocumentoTramite;
	}

	private OrganoDestinatario getOrganoDestinatarioServicio(
			ieci.tecdoc.sgm.catalogo_tramites.util.OrganoDestinatarioImpl poOrganoDestinatario) {
		OrganoDestinatario oOrganoDestinatario = new OrganoDestinatario();
		if (poOrganoDestinatario == null)
			return oOrganoDestinatario;

		oOrganoDestinatario.setDescription(poOrganoDestinatario
				.getDescription());
		oOrganoDestinatario.setId(poOrganoDestinatario.getId());

		return oOrganoDestinatario;
	}

	private ieci.tecdoc.sgm.catalogo_tramites.util.OrganoDestinatarioImpl getOrganoDestinatarioImpl(
			OrganoDestinatario poOrganoDestinatario) {
		ieci.tecdoc.sgm.catalogo_tramites.util.OrganoDestinatarioImpl oOrganoDestinatario = new ieci.tecdoc.sgm.catalogo_tramites.util.OrganoDestinatarioImpl();
		if (poOrganoDestinatario == null)
			return oOrganoDestinatario;

		oOrganoDestinatario.setDescription(poOrganoDestinatario
				.getDescription());
		oOrganoDestinatario.setId(poOrganoDestinatario.getId());

		return oOrganoDestinatario;
	}

	private OrganosDestinatarios getOrganosDestinatariosServicio(
			ieci.tecdoc.sgm.catalogo_tramites.util.OrganosDestinatarios poOrganosDestinatarios) {
		OrganosDestinatarios oOrganosDestinatarios = new OrganosDestinatarios();
		if (poOrganosDestinatarios == null)
			return oOrganosDestinatarios;

		for (int i = 0; i < poOrganosDestinatarios.count(); i++)
			oOrganosDestinatarios
					.add(getOrganoDestinatarioServicio((ieci.tecdoc.sgm.catalogo_tramites.util.OrganoDestinatarioImpl) poOrganosDestinatarios
							.get(i)));

		return oOrganosDestinatarios;
	}

	private Conector getConectorServicio(
			ieci.tecdoc.sgm.catalogo_tramites.util.ConectorImpl poConector) {
		Conector oConector = new Conector();
		if (poConector == null)
			return oConector;

		oConector.setDescription(poConector.getDescription());
		oConector.setId(poConector.getId());
		oConector.setType(poConector.getType());

		return oConector;
	}

	private ieci.tecdoc.sgm.catalogo_tramites.util.ConectorImpl getConectorImpl(
			Conector poConector) {
		ieci.tecdoc.sgm.catalogo_tramites.util.ConectorImpl oConector = new ieci.tecdoc.sgm.catalogo_tramites.util.ConectorImpl();
		if (poConector == null)
			return oConector;

		oConector.setDescription(poConector.getDescription());
		oConector.setId(poConector.getId());
		oConector.setType(poConector.getType());

		return oConector;
	}

	private Conectores getConectoresServicio(
			ieci.tecdoc.sgm.catalogo_tramites.util.Conectores poConectores) {
		Conectores oConectores = new Conectores();
		if (poConectores == null)
			return oConectores;

		for (int i = 0; i < poConectores.count(); i++)
			oConectores
					.add(getConectorServicio((ieci.tecdoc.sgm.catalogo_tramites.util.ConectorImpl) poConectores
							.get(i)));

		return oConectores;
	}

	private TipoConector getTipoConectorServicio(
			ieci.tecdoc.sgm.catalogo_tramites.util.TipoConectorImpl poTipoConector) {
		TipoConector oTipoConector = new TipoConector();
		if (poTipoConector == null)
			return oTipoConector;

		oTipoConector.setDescription(poTipoConector.getDescription());
		oTipoConector.setId(poTipoConector.getId());

		return oTipoConector;
	}

	private ieci.tecdoc.sgm.catalogo_tramites.util.TipoConectorImpl getTipoConectorImpl(
			TipoConector poTipoConector) {
		ieci.tecdoc.sgm.catalogo_tramites.util.TipoConectorImpl oTipoConector = new ieci.tecdoc.sgm.catalogo_tramites.util.TipoConectorImpl();
		if (poTipoConector == null)
			return oTipoConector;

		oTipoConector.setDescription(poTipoConector.getDescription());
		oTipoConector.setId(poTipoConector.getId());

		return oTipoConector;
	}

	private TiposConectores getTiposConectoresServicio(
			ieci.tecdoc.sgm.catalogo_tramites.util.TiposConectores poTiposConectores) {
		TiposConectores oTiposConectores = new TiposConectores();
		if (poTiposConectores == null)
			return oTiposConectores;

		for (int i = 0; i < poTiposConectores.count(); i++)
			oTiposConectores
					.add(getTipoConectorServicio((ieci.tecdoc.sgm.catalogo_tramites.util.TipoConectorImpl) poTiposConectores
							.get(i)));

		return oTiposConectores;
	}

	public ConectoresAutenticacion getAuthHooks(String tramiteId,
			Entidad entidad) throws CatalogoTramitesExcepcion {
		try {
			return getConectoresAutenticacionServicio(CatalogoTramites
					.getAuthHooks(tramiteId, entidad.getIdentificador()));
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener conectores de autenticacion.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger.error(
					"Error inesperado al obtener conectores de autenticacion.",
					e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void addAuthHooks(ConectorAutenticacion conectorAutenticacion,
			Entidad entidad) throws CatalogoTramitesExcepcion {
		try {
			CatalogoTramites.addAuthHooks(
					getConectorAutenticacionImpl(conectorAutenticacion),
					entidad.getIdentificador());
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al insertar conectores de autenticacion.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger
					.error(
							"Error inesperado al insertar conectores de autenticacion.",
							e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void deleteAuthHooks(String tramiteId, String conectorId,
			Entidad entidad) throws CatalogoTramitesExcepcion {
		try {
			CatalogoTramites.deleteAuthHooks(tramiteId, conectorId, entidad
					.getIdentificador());
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al eliminar conectores de autenticacion.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger
					.error(
							"Error inesperado al eliminar conectores de autenticacion.",
							e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void updateAuthHooks(ConectorAutenticacion conectorAutenticacion,
			String oldHookId, Entidad entidad) throws CatalogoTramitesExcepcion {
		try {
			CatalogoTramites.updateAuthHooks(
					getConectorAutenticacionImpl(conectorAutenticacion),
					oldHookId, entidad.getIdentificador());
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al actualizar conectores de autenticacion.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger
					.error(
							"Error inesperado al actualizar conectores de autenticacion.",
							e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public ConectorAutenticacion getAuthHook(String tramiteId,
			String conectorId, Entidad entidad)
			throws CatalogoTramitesExcepcion {
		try {
			return getConectorAutenticacionServicio((ieci.tecdoc.sgm.catalogo_tramites.util.ConectorAutenticacionImpl) CatalogoTramites
					.getAuthHook(tramiteId, conectorId, entidad
							.getIdentificador()));
		} catch (ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion e) {
			logger.error("Error al obtener conector de autenticacion.", e);
			throw getCatalogoTramitesExcepcion(e);
		} catch (Throwable e) {
			logger
					.error(
							"Error inesperado al obtener conector de autenticacion.",
							e);
			throw new CatalogoTramitesExcepcion(
					CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Comprueba si el código de asunto asignado al trámite existe como tipo de
	 * asunto asociado a la oficina asignada en registro presencial para el
	 * usuario configurado que realizará la consolidación
	 *
	 * @param procedure
	 *            Información del trámite
	 * @param entidad
	 *            Entidad
	 * @throws Exception
	 */
	protected void checkTopicOfficeCodes(Tramite procedure, Entidad entidad)
			throws Exception {
		ieci.tecdoc.sgm.consolidacion.config.ConsolidacionConfig consolidacionConfig=
				ieci.tecdoc.sgm.consolidacion.config.ConfigLoader.getInstance().getConfig(
						entidad.getIdentificador());
		
		// Si el usuario que se configura para la consolidación en registro
		// presencial
		// no existe se supone que la consolidación no se va a realizar por lo
		// que
		// las validaciones contra registro presencial no se realizan
		if (consolidacionConfig!=null && StringUtils.isNotBlank(consolidacionConfig.getUserName())) {

			// Información del usuario de registro presencial
			UserInfo userInfo = new UserInfo();
			userInfo.setUserName(consolidacionConfig.getUserName());
			userInfo.setPassword(consolidacionConfig.getPassword());

			if (userInfo.getLocale() == null) {
				userInfo.setLocale(Locale.getDefault());
			} else {
				userInfo.setLocale(getRegistryUserLocale());
			}

			try {
				ServicioRegistro oServicioRegistro = LocalizadorServicios
						.getServicioRegistro();

				boolean userCanCreateRegisterInOffice = false;
				OfficeInfo[] offices = oServicioRegistro
						.getOfficeCanCreateRegisterByUser(userInfo, Integer
								.valueOf(consolidacionConfig.getBookId()), entidad);
				if (offices != null) {
					int i = 0;
					while ((i < offices.length)
							&& !userCanCreateRegisterInOffice) {

						OfficeInfo office = offices[i];
						if (office.getCode().equals(procedure.getOficina())) {
							userCanCreateRegisterInOffice = true;
						}
						i++;
					}
				}

				if (!userCanCreateRegisterInOffice) {
					throw new ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion(
							CatalogoTramitesCodigosError.EC_OFFICE_CODE_NOT_ASSIGNED_CONSOLIDATION_USER_RP);
				}

				Boolean existMatterTypeInOffice = oServicioRegistro
						.existMatterTypeInOffice(userInfo,
								procedure.getTopic(), procedure.getOficina(),
								entidad);

				if (!existMatterTypeInOffice.booleanValue()) {
					throw new ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion(
							CatalogoTramitesCodigosError.EC_TOPIC_CODE_NOT_ASSIGNED_OFFICE_CODE_RP);
				}
			} catch (RegistroException re) {
				logger
						.info(
								"Error al comprobar si el codigo de asunto esta asociado a la oficina de registro presencial.",
								re);
				throw new ieci.tecdoc.sgm.catalogo_tramites.exception.CatalogoTramitesExcepcion(
						CatalogoTramitesCodigosError.EC_OFFICE_CODE_NOT_ASSIGNED_CONSOLIDATION_USER_RP);
			}
		} else {
			logger
					.info("El usuario de consolidacion de registro presencial no se ha configurado. Si la consolidacion se va a realizar, revise la configuracion y establezca el usuario para que se ejecuten las validaciones de codigo de oficina y codigo de asunto");
		}
	}

	private ConectorAutenticacion getConectorAutenticacionServicio(
			ieci.tecdoc.sgm.catalogo_tramites.util.ConectorAutenticacionImpl poConectorAutenticacion) {
		ConectorAutenticacion oConectorAutenticacion = new ConectorAutenticacion();
		if (poConectorAutenticacion == null)
			return oConectorAutenticacion;

		oConectorAutenticacion.setConectorId(poConectorAutenticacion
				.getConectorId());
		oConectorAutenticacion.setTramiteId(poConectorAutenticacion
				.getTramiteId());

		return oConectorAutenticacion;
	}

	private ieci.tecdoc.sgm.catalogo_tramites.util.ConectorAutenticacionImpl getConectorAutenticacionImpl(
			ConectorAutenticacion poConectorAutenticacion) {
		ieci.tecdoc.sgm.catalogo_tramites.util.ConectorAutenticacionImpl oConectorAutenticacion = new ieci.tecdoc.sgm.catalogo_tramites.util.ConectorAutenticacionImpl();
		if (poConectorAutenticacion == null)
			return oConectorAutenticacion;

		oConectorAutenticacion.setConectorId(poConectorAutenticacion
				.getConectorId());
		oConectorAutenticacion.setTramiteId(poConectorAutenticacion
				.getTramiteId());

		return oConectorAutenticacion;
	}

	private ConectoresAutenticacion getConectoresAutenticacionServicio(
			ieci.tecdoc.sgm.catalogo_tramites.util.ConectoresAutenticacion poConectoresAutenticacion) {
		ConectoresAutenticacion oConectoresAutenticacion = new ConectoresAutenticacion();
		if (poConectoresAutenticacion == null)
			return oConectoresAutenticacion;

		for (int i = 0; i < poConectoresAutenticacion.count(); i++)
			oConectoresAutenticacion
					.add(getConectorAutenticacionServicio((ieci.tecdoc.sgm.catalogo_tramites.util.ConectorAutenticacionImpl) poConectoresAutenticacion
							.get(i)));

		return oConectoresAutenticacion;
	}

	public Locale getRegistryUserLocale() {
		return registryUserLocale;
	}

	public void setRegistryUserLocale(Locale registryUserLocale) {
		this.registryUserLocale = registryUserLocale;
	}

}
