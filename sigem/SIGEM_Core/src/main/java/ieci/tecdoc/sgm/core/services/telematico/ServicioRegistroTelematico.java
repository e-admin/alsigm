package ieci.tecdoc.sgm.core.services.telematico;

import java.io.ByteArrayOutputStream;

import ieci.tecdoc.sgm.core.services.catalogo.Documentos;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.repositorio.ContenedorDocumentos;

public interface ServicioRegistroTelematico {

		/**
		 * M�todo que genera una petici�n de registro
		 * @param sessionId Identificador de sesi�n
		 * @param requestInfo Datos de la solicitud
		 * @param idiom Idioma de la solicitud
		 * @param organismo Organismo al que se le realiza la solicitud
		 * @param numeroExpediente N�mero de expediente asociado (en caso de subsanaci�n)
		 * @return Petici�n de registro
		 * @throws RegistroTelematicoException
		 */
		public byte[] crearPeticionRegistro (String sessionId, RegistroPeticion requestInfo, String idiom, String organismo, String numeroExpediente, Entidad entidad) throws RegistroTelematicoException;

		/**
		 * M�todo que establece los documentos adjunto a una solicitud de subsanaci�n
		 * @param sessionId Identificador de sesi�n
		 * @param procedureDocuments Documen
		 * @param requestDocuments
		 * @return Trozo de xml con los datos correspondiente a los ficheros anexados
		 * @throws RegistroTelematicoException
		 */
		public String establecerDocumentosSubsanacion (String sessionId, Documentos procedureDocuments, PeticionDocumentos requestDocuments, Entidad entidad) throws RegistroTelematicoException;

		/**
		 * M�todo que registra una solicitud
		 * @param sessionId Identificador de sesi�n
		 * @param registryRequest Datos de la solicitud a registrar
		 * @param additionalInfo Datos espec�ficos
		 * @param idiom Idioma de la solicitud
		 * @param oficina N�mero de oficina
		 * @param plantilla Ruta del justificante del registro que se usar� como plantilla
		 * @param certificado Ruta del certificado del servidor
		 * @return Registro de la solicitud
		 * @throws RegistroTelematicoException
		 */
		public byte[] registrar (String sessionId, byte[] registryRequest, String additionalInfo, String idiom, String oficina, String plantilla, String certificado, Entidad entidad) throws RegistroTelematicoException;


		/**
		 * M�todo que registra una solicitud
		 * @param sessionId Identificador de sesi�n
		 * @param registryRequest Datos de la solicitud a registrar
		 * @param additionalInfo Datos espec�ficos
		 * @param idiom Idioma de la solicitud
		 * @param oficina N�mero de oficina
		 * @param plantilla contenido del justificante del registro que se usar� como plantilla
		 * @param certificado Ruta del certificado del servidor
		 * @return Registro de la solicitud
		 * @throws RegistroTelematicoException
		 */
		public byte[] registrar (String sessionId, byte[] registryRequest, String additionalInfo, String idiom, String oficina, byte[] plantilla, String certificado, Entidad entidad) throws RegistroTelematicoException;

		/**
		 * M�todo que obtiene un nuevo n�mero de registro (secuencial)
		 * @return N�mero de registro
		 * @throws RegistroTelematicoException
		 */
		public String obtenerNumeroRegistro(Entidad entidad) throws RegistroTelematicoException;

		/**
		 * M�todo que elimina los documentos temporales asociados a una solicitud
		 * @param sessionId Identificador de sesi�n
		 * @param registryNumber N�mero de registro
		 * @throws RegistroTelematicoException
		 */
		public void eliminarDocumentosTemporales (String sessionId, String registryNumber, Entidad entidad) throws RegistroTelematicoException;

		/**
		 * M�todo que realiza una b�squeda de registro a partir de determinados par�metros
		 * @param sessionId Identificador de sesi�n
		 * @param query Par�metros de b�sqeda
		 * @return Lista de registros
		 * @throws RegistroTelematicoException
		 */
		public Registros query (String sessionId, RegistroConsulta query, Entidad entidad) throws RegistroTelematicoException;

		/**
		 * M�todo que obtiene un registro
		 * @param sessionId Identificador de sesi�n
		 * @param registryNumber N�mero de registro a obtener
		 * @return Datos del registro
		 * @throws RegistroTelematicoException
		 */
		public Registro obtenerRegistro(String sessionId, String registryNumber, Entidad entidad) throws RegistroTelematicoException;

		/**
		 * M�todo que obtiene informaci�n de los  documentos asociados a un registro (anexos, solicitud, justificante)
		 * @param registryNumber N�mero de registro
		 * @return Informaci�n de los documentos asociados a un registro
		 * @throws RegistroTelematicoException
		 */
		public RegistroDocumentos obtenerDocumentosRegistro(String registryNumber, Entidad entidad) throws RegistroTelematicoException;

		/**
		 * M�todo que obtiene los documentos asociados a un registro (anexos, solicitud, justificante)
		 * @param registryNumber N�mero de registro
		 * @return Documentos asociados a un registro
		 * @throws RegistroTelematicoException
		 */
		public ContenedorDocumentos obtenerDatosDocumentosRegistro (String registryNumber, Entidad entidad) throws RegistroTelematicoException;

		/**
		 * M�todo que obtiene el justificante de registro
		 * @param sessionId Identificador de sesi�n
		 * @param registryNumber N�mero de registro
		 * @return Justificante
		 * @throws RegistroTelematicoException
		 */
		public byte[] obtenerJustificanteRegistro(String sessionId, String registryNumber, Entidad entidad) throws RegistroTelematicoException;

		/**
		 * M�todo que obtiene la solicitud de registro
		 * @param registryNumber N�mero de registro
		 * @return Solicitud de registro
		 * @throws RegistroTelematicoException
		 */
		public byte[] obtenerPeticionRegistro (String registryNumber, Entidad entidad) throws RegistroTelematicoException;

		/**
		 * M�todo que obtiene los registros consolidados
		 * @return Registro consolidados
		 * @throws RegistroTelematicoException
		 */
		public Registros obtenerRegistrosConsolidados(Entidad entidad)throws RegistroTelematicoException;

		/**
		 * M�todo que obtiene los registros a mostrar a un usuario
		 * @return Registro a mostrar
		 * @throws RegistroTelematicoException
		 */
		public Registros obtenerRegistrosParaMostrar(RegistroConsulta query, Entidad entidad)throws RegistroTelematicoException;

		/**
		 * M�todo que comprueba si existen documentos anexos a un registro
		 * @param registryNumber
		 * @return true si existen documentos anexos a un registro, false si no
		 * @throws RegistroTelematicoException
		 */
		public boolean tieneDocumentos (String registryNumber, Entidad entidad) throws RegistroTelematicoException;

		/**
		 * M�todo que obtiene el contenido de un documento asociado a un registro
		 * @param sessionId Identificador de sesi�n
		 * @param registryNumber N�mero de registro
		 * @param code C�digo de documento
		 * @return Documento
		 * @throws RegistroTelematicoException
		 */
		public ByteArrayOutputStream obtenerContenidoDocumento (String sessionId, String registryNumber, String code, Entidad entidad) throws RegistroTelematicoException;

		/**
		 * M�todo que obtiene el contenido de un documento asociado a un registro
		 * @param registryNumber N�mero de registro
		 * @param code C�digo de documento
		 * @return Documento
		 * @throws RegistroTelematicoException
		 */
		public byte[] obtenerDocumento (String registryNumber, String code, Entidad entidad) throws RegistroTelematicoException;

		/**
		 * M�todo que actualiza el estado de un registro
		 * @param registryNumber N�mero de registro
		 * @param status Nuegvo estado para el registro
		 * @throws RegistroTelematicoException
		 */
		public void establecerEstadoRegistro (String registryNumber, int status, Entidad entidad) throws RegistroTelematicoException;

		/**
		 * M�todo que obtiene la asociaci�n entre un registro y un documento
		 * @param sessionId Identificador de sesi�n
		 * @param registryNumber N�mero de registro
		 * @param code C�digo de documento
		 * @return Datos de la asociaci�n del documento con el registro
		 * @throws RegistroTelematicoException
		 */
		public RegistroDocumento obtenerDocumentoRegistro (String sessionId, String registryNumber, String code, Entidad entidad) throws RegistroTelematicoException;

		/**
		 * M�todo que da de alta la asociaci�n entre un registro y un documento
		 * @param registryDocument Datos de la asociaci�n
		 * @return Datos de la asociaci�n
		 * @throws RegistroTelematicoException
		 */
		public RegistroDocumento insertarDocumentoRegistro (RegistroDocumento registryDocument, Entidad entidad) throws RegistroTelematicoException;

		/**
		 * M�todo que actualiza la asociaci�n entre un registro y un documento
		 * @param registryDocument Datos de la asociaci�n
		 * @throws RegistroTelematicoException
		 */
		public void actualizarDocumentoRegistro (RegistroDocumento registryDocument, Entidad entidad) throws RegistroTelematicoException;

		/**
		 * M�todo que elimina la asociaci�n entre un registro y sus documentos
		 * @param registryNumber N�mero de registro
		 * @throws RegistroTelematicoException
		 */
		public void eliminarDocumentoRegistro (String registryNumber, Entidad entidad) throws RegistroTelematicoException;

		/**
		 * M�todo que registra una solicitud telem�tica e iniciar un expediente
		 * @param sessionId Identificador de sesi�n
		 * @param registryRequest Datos de la solicitud a registrar
		 * @param additionalInfo Datos espec�ficos
		 * @param idiom Idioma de la solicitud
		 * @param oficina N�mero de oficina
		 * @param plantilla Ruta del justificante del registro que se usar� como plantilla
		 * @param certificado Ruta del certificado del servidor
		 * @param tramiteId Identificador del tramite de la solicitud
		 * @return Registro de la solicitud
		 * @throws RegistroTelematicoException
		 */
		public byte[] registrarTelematicoAndIniciarExpediente (String sessionId, byte[] registryRequest, String additionalInfo,
				String idiom, String oficina, String plantilla, String certificado, String tramiteId, Entidad entidad) throws RegistroTelematicoException;

		/**
		 * M�todo que registra una solicitud telem�tica e iniciar un expediente
		 * @param sessionId Identificador de sesi�n
		 * @param registryRequest Datos de la solicitud a registrar
		 * @param additionalInfo Datos espec�ficos
		 * @param idiom Idioma de la solicitud
		 * @param oficina N�mero de oficina
		 * @param plantilla contenido del justificante del registro que se usar� como plantilla
		 * @param certificado Ruta del certificado del servidor
		 * @param tramiteId Identificador del tramite de la solicitud
		 * @return Registro de la solicitud
		 * @throws RegistroTelematicoException
		 */
		public byte[] registrarTelematicoAndIniciarExpediente (String sessionId, byte[] registryRequest, String additionalInfo,
				String idiom, String oficina, byte[] plantilla, String certificado, String tramiteId, Entidad entidad) throws RegistroTelematicoException;

		/**
		 * M�todo que elimina un registro (en caso de producirse alg�n error al iniciar expediente)
		 * @param sessionId Identificador de sesi�n
		 * @param registryNumber N�mero de registro asociado
		 * @throws RegistroTelematicoException
		 */
		public void deshacerRegistro (String sessionId, String registryNumber, Entidad entidad) throws RegistroTelematicoException;

}
