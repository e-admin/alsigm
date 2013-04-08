package ieci.tecdoc.sgm.core.services.catalogo;

import ieci.tecdoc.sgm.core.services.dto.Entidad;

/**
 * EJB sin estado que proporciona las operaciones sobre el catálogo de trámites
 * 
 * @author IECISA
 * 
 */
public interface ServicioCatalogoTramites {
	
		/**
		 * Método que da de alta un trámite
		 * @param procedure Datos del trámite
		 * @throws CatalogoTramitesExcepcion
		 */
		public void addProcedure(Tramite procedure, Entidad entidad) throws CatalogoTramitesExcepcion;
	
		/**
		 * Método que obtiene un trámite
		 * @param procedureId Identificador del trámite
		 * @param loadDocuments Determina si deben cargarse tipos de documentos asociados al trámite
		 * @return Datos del trámite
		 * @throws CatalogoTramitesExcepcion
		 */
		public Tramite getProcedure(String procedureId, boolean loadDocuments, Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que realiza un consulta sobre los trámites
		 * @param query Parámetros de la consulta
		 * @return Trámites obtenidos en la consulta a partir de los parámtros definidos
		 * @throws CatalogoTramitesExcepcion
		 */
		public Tramites query(TramiteConsulta query, Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que elimina un trámite
		 * @param procedureId Identificador del trámite
		 * @throws CatalogoTramitesExcepcion
		 */
		public void deleteProcedure(String procedureId, Entidad entidad) throws CatalogoTramitesExcepcion;

		/**
		 * Método que actualiza la información de un trámite
		 * @param procedure Datos actualizados del trámite
		 * @throws CatalogoTramitesExcepcion
		 */
		public void updateProcedure(Tramite procedure, Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que informa si un tipo de documento está asociado a algún trámite
		 * @param documentId Identificador de documento
		 * @return true si está asociado el documento a algún trámite, false si no lo está
		 * @throws CatalogoTramitesExcepcion
		 */
		public boolean isDocumentReferenced(String documentId, Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que da de alta la asociación entre un trámite y un tipo documento
		 * @param procedureDocument Datos de la asociación a dar de alta
		 * @throws CatalogoTramitesExcepcion
		 */
		public void addProcedureDocument(DocumentoTramite procedureDocument, Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que elimina la sociación entre un trámite y un tipo de documento
		 * @param procedureDocument Datos de la asociación a eliminar
		 * @throws CatalogoTramitesExcepcion
		 */
		public void deleteProcedureDocument(DocumentoTramite procedureDocument, Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que obtiene un tipo de documento
		 * @param documentId Identiicador del documento
		 * @return Datos del tipo de documento
		 * @throws CatalogoTramitesExcepcion
		 */
		public Documento getDocument(String documentId, Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que da de alta un tipo de documento
		 * @param document Datos del documento a dar de alta
		 * @throws CatalogoTramitesExcepcion
		 */
		public void addDocument(Documento document, Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que elimina un documento
		 * @param documentId Identificador del documento
		 * @throws CatalogoTramitesExcepcion
		 */
		public void deleteDocument(String documentId, Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que actualiza los datos de un tipo de documento
		 * @param document Datos actualizados del documento
		 * @throws CatalogoTramitesExcepcion
		 */
		public void updateDocument(Documento document, Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Métodos que obtiene los tipos de documentos dados de alta
		 * @return Documentos dados de alta en el sistema
		 * @throws CatalogoTramitesExcepcion
		 */
		public Documentos getDocuments(Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que obtiene todos los tipos de documento asociados a un trámite
		 * @param procedureId Identificador del trámite
		 * @return Documentos asociados a un trámite
		 * @throws CatalogoTramitesExcepcion
		 */
		public Documentos getProcedureDocuments(String procedureId, Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que obtiene los datos de la asociación de un trámite con un tipo de documento
		 * @param procedureId Identificador del trámite
		 * @param documentId Identificador del documento
		 * @return Datos de la asociación de un documento a un trámie
		 * @throws CatalogoTramitesExcepcion
		 */
		public DocumentoTramite getProcedureDocument(String procedureId, String documentId, Entidad entidad) throws CatalogoTramitesExcepcion;
	
		/**
		 * Método que obtiene los datos de la asociación de un trámite con un tipo de documento
		 * @param procedureId Identificador del trámite
		 * @param documentId Identificador del documento
		 * @param code Código de documento
		 * @return Datos de la asociación de un documento a un trámie
		 * @throws CatalogoTramitesExcepcion
		 */
		public DocumentoTramite getProcedureDocument(String procedureId, String documentId, String code, Entidad entidad) throws CatalogoTramitesExcepcion;
	
		/**
		 * Método que actualiza la asociación entre un trámite y un tipo de documento
		 * @param procedure Datos actualizados de la asociación
		 * @throws CatalogoTramitesExcepcion
		 */
		public void updateProcedureDocument(DocumentoTramite procedure, Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que obtiene la lista de trámites dados de alta en el sistema
		 * @return Lista de trámites
		 * @throws CatalogoTramitesExcepcion
		 */
		public Tramites getProcedures(Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que obtiene un documento a partir del código de documento (código único que asoci
		 * un trámite con un tipo de documento
		 * @param code Código de documento
		 * @return Datos del documento
		 * @throws CatalogoTramitesExcepcion
		 */
		public Documento getDocumentfromCode (String code, Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que obtiene un órgano destinatario
		 * @param addresseeId Identificador del órgano destinatario
		 * @return Datos del órgano destinatario
		 * @throws CatalogoTramitesExcepcion
		 */
		public OrganoDestinatario getAddressee(String addresseeId, Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que da de alta un órgano destinatario
		 * @param addressee Datos del órgano destinatario
		 * @throws CatalogoTramitesExcepcion
		 */
		public void addAddressee(OrganoDestinatario addressee, Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que elimina un órgano destinatario
		 * @param addresseeId Identificador del órgano destinatario
		 * @throws CatalogoTramitesExcepcion
		 */
		public void deleteAddressee(String addresseeId, Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que actualiza un órgano destinatario
		 * @param addressee Datos actualizados del órgano destinatario
		 * @throws CatalogoTramitesExcepcion
		 */
		public void updateAddressee(OrganoDestinatario addressee, Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que obtiene una lista de órrganos destinatarios
		 * @return Lista de órganos destinatarios
		 * @throws CatalogoTramitesExcepcion
		 */
		public OrganosDestinatarios getAddressees(Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que obtiene un conector
		 * @param hookId Identiicador de conector
		 * @return Datos del conector
		 * @throws CatalogoTramitesExcepcion
		 */
		public Conector getHook(String hookId, Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que da de alta un conector
		 * @param hook Datos del conector
		 * @throws CatalogoTramitesExcepcion
		 */
		public void addHook(Conector hook, Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que elimina un conector
		 * @param hookId Identificador de conector
		 * @throws CatalogoTramitesExcepcion
		 */
		public void deleteHook(String hookId, Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que actualiza un conector
		 * @param hook Datos actualizados del conector
		 * @throws CatalogoTramitesExcepcion
		 */
		public void updateHook(Conector hook, Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que obtiene una lista de conectores
		 * @return Lista de conectores
		 * @throws CatalogoTramitesExcepcion
		 */
		public Conectores getHooks(Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que obtiene una lista de conectores de un tipo determinado
		 * @param hookType Identificador de tipo de conector
		 * @return Lista de conectores del tipo despecificado
		 * @throws CatalogoTramitesExcepcion
		 */
		public Conectores getHooksByType(int hookType, Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que obtiene un tipo de conector
		 * @param typeId Identificador del tipo de conector
		 * @return Datos del tipo de conector
		 * @throws CatalogoTramitesExcepcion
		 */
		public TipoConector getHookType(int typeId, Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que da de alta un tipo de conector
		 * @param hookType Datos del tipo de conector
		 * @throws CatalogoTramitesExcepcion
		 */
		public void addHookType(TipoConector hookType, Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que elimina un tipo de conector
		 * @param typeId Identificador del tipo de conector
		 * @throws CatalogoTramitesExcepcion
		 */
		public void deleteHookType(int typeId, Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que actualiza un tipode conector
		 * @param hookType Datos actualizados del tipo de conector
		 * @throws CatalogoTramitesExcepcion
		 */
		public void updateHookType(TipoConector hookType, Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Mátodo que obtiene una lista de tipos de conectores
		 * @return Lista de tipos de conectores dados de alta en el sistema
		 * @throws CatalogoTramitesExcepcion
		 */
		public TiposConectores getHookTypes(Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que obtiene los conectores de autenticación asociados a un trámite
		 * @param tramiteId Identificador del trámite
		 * @return Datos de los conectores de autenticación asociados a un trámite
		 * @throws CatalogoTramitesExcepcion
		 */
		public ConectoresAutenticacion getAuthHooks (String tramiteId, Entidad entidad) throws CatalogoTramitesExcepcion;
		
		/**
		 * Método que da de alta la asociación de un conector de auteticación a un trámite
		 * @param conectorAutenticacion Datos del conector de autenticación
		 * @throws CatalogoTramitesExcepcion
		 */
		public void addAuthHooks (ConectorAutenticacion conectorAutenticacion, Entidad entidad) throws CatalogoTramitesExcepcion;
		   
		/**
		 * Método que elimina la asociación de un conector de autenticación con un trámite
		 * @param tramiteId Identificador del trámite
		 * @param conectorId Identificador del conector
		 * @throws CatalogoTramitesExcepcion
		 */
		public void deleteAuthHooks (String tramiteId, String conectorId, Entidad entidad) throws CatalogoTramitesExcepcion;
		   
		/**
		 * Método que actualiza la asociación entre un conector de autenticación y un trámite
		 * @param conectorAutenticacion Datos actualizados de la asociación
		 * @param oldHookId Identificador del conector que se quiere modificar antes de la modificación
		 * @throws CatalogoTramitesExcepcion
		 */
		public void updateAuthHooks (ConectorAutenticacion conectorAutenticacion, String oldHookId, Entidad entidad) throws CatalogoTramitesExcepcion;
		   
		/**
		 * Métodoque obtiene un conector de autenticación
		 * @param tramiteId Identificador del trámite
		 * @param conectorId Identificador del conector
		 * @return Datos del conector de autenticación
		 * @throws CatalogoTramitesExcepcion
		 */
		public ConectorAutenticacion getAuthHook (String tramiteId, String conectorId, Entidad entidad) throws CatalogoTramitesExcepcion;
}