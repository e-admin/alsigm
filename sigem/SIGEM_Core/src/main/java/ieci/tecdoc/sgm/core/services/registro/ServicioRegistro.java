package ieci.tecdoc.sgm.core.services.registro;

import ieci.tecdoc.sgm.core.services.dto.Entidad;

public interface ServicioRegistro {

	/**
	 * Método para buscar registros.
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param bookId:
	 *            Identificador del Libro sobre el que realizaremos la operación
	 *            (Required)
	 * @param atts:
	 *            Criterios de busqueda (Opcional)
	 * @param entidad
	 *            (Required)
	 * @return
	 * @throws RegistroException
	 */
	public RegisterInfo[] findFolder(UserInfo user, Integer bookId,
			FieldInfoQuery[] atts, Entidad entidad) throws RegistroException;

	/**
	 * Método para crear un registro
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param bookId:
	 *            Identificador del Libro sobre el que realizaremos la operación
	 *            (Required)
	 * @param atts:
	 *            Datos de registro que deseamos crear (Required)
	 * @param inter:
	 *            Interesados (Remitentes o Destinatarios) (Opcional)
	 * @param documents:
	 *            Documentos adjuntos del registro (Opcional)
	 * @param entidad
	 *            (Required)
	 * @return
	 * @throws RegistroException
	 */
	public RegisterInfo createFolder(UserInfo user, Integer bookId,
			FieldInfo[] atts, PersonInfo[] inter, DocumentInfo[] documents,
			Entidad entidad) throws RegistroException;

	/**
	 * Método para actualizar un registro.
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param bookId:
	 *            Identificador del Libro sobre el que realizaremos la operación
	 *            (Required)
	 * @param folderId:
	 *            Identificador del registro que deseamos actualizar (Required)
	 * @param atts:
	 *            Atributos que deseamos actualizar (Opcional)
	 * @param inter:
	 *            Interesados (Remitentes o Destinarios) asociados al registro
	 *            con sus datos actualizados (Opcional)
	 * @param documents:
	 *            Documentos que deseamos adjuntar al registro (Opcional)
	 * @param entidad
	 *            (Required)
	 * @return
	 * @throws RegistroException
	 */
	public RegisterInfo updateFolder(UserInfo user, Integer bookId,
			Integer folderId, FieldInfo[] atts, PersonInfo[] inter,
			DocumentInfo[] documents, Entidad entidad) throws RegistroException;

	/**
	 * Método para importar un registro.
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param bookId:
	 *            Identificador del Libro sobre el que realizaremos la operación
	 *            (Required)
	 * @param atts:
	 *            Valores de los campos del registro (Required)
	 * @param inter:
	 *            Valor de los interesados (Remitentes o Destinatarios)
	 *            asociados al registro (Opcional)
	 * @param documents:
	 *            Documentos adjuntos al registro (Opcional)
	 * @param entidad
	 *            (Required)
	 * @return
	 * @throws RegistroException
	 */
	public RegisterInfo importFolder(UserInfo user, Integer bookId,
			FieldInfo[] atts, PersonInfo[] inter, DocumentInfo[] documents,
			Entidad entidad) throws RegistroException;

	/**
	 * Método para buscar un registro de entrada por el "Número de registro"
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param bookId:
	 *            Identificador del Libro sobre el que realizaremos la operación
	 *            (Required)
	 * @param folderNumber:
	 *            Número del registro que buscamos (Required)
	 * @param entidad
	 *            (Required)
	 * @return
	 * @throws RegistroException
	 */
	public RegisterWithPagesInfo getInputFolderForNumber(UserInfo user,
			Integer bookId, String folderNumber, Entidad entidad)
			throws RegistroException;

	/**
	 * Método para buscar un registro de salida por el "Número de registro"
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param bookId:
	 *            Identificador del Libro sobre el que realizaremos la operación
	 *            (Required)
	 * @param folderNumber:
	 *            Número del registro que buscamos (Required)
	 * @param entidad
	 *            (Required)
	 * @return
	 * @throws RegistroException
	 */
	public RegisterWithPagesInfo getOutputFolderForNumber(UserInfo user,
			Integer bookId, String folderNumber, Entidad entidad)
			throws RegistroException;

	/**
	 * Método para obtener un documento que concuerda con los criterios de
	 * busqueda.
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param bookId:
	 *            Identificador del Libro sobre el que realizaremos la operación
	 *            (Required)
	 * @param folderId:
	 *            Identificador del registro al que está adjuntado el documento
	 *            que queremos recuperar (Required)
	 * @param docID:
	 *            Identificador del documento que buscamos (Required)
	 * @param pageID:
	 *            Identificador de la página del documento (Required)
	 * @param entidad:
	 *            (Required)
	 * @return
	 * @throws RegistroException
	 */
	public DocumentQuery getDocumentFolder(UserInfo user, Integer bookID,
			Integer folderId, Integer docID, Integer pageID, Entidad entidad)
			throws RegistroException;

	/**
	 * Método para obtener los datos de la distribución de un registros
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param distributionId:
	 *            Identificador de la distribución del registro (Required)
	 * @param entidad
	 *            (Required)
	 * @return
	 * @throws RegistroException
	 */
	public DistributionInfo getDistribution(UserInfo user,
			Integer distributionId, Entidad entidad) throws RegistroException;

	/**
	 * Obtenemos la lista de registros distribuidos recibidos en el bandeja de
	 * entrada
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param state:
	 *            Estado de la distribucion (1.-Pendiente, 2.-Aceptador,
	 *            3.-Archivado, 4.-Rechazado , 5.-Redistribuido) (Required)
	 * @param firstRow:
	 *            Primer registro de la lista de resultados (Required - Default:
	 *            0)
	 * @param maxResults:
	 *            Número máximos de resultados devueltos (Required - Default: 0)
	 * @param typeBookRegisterDist:
	 *            0.- Registros de entrada y de salida que son distribuciones de
	 *            entrada; 1.- Registros de entrada que son distribuciones de
	 *            entrada; 2.- Registros de salida que son distribuciones de
	 *            entrada (Required)
	 * @param oficAsoc:
	 *            este parametro indica si se muestran los registros
	 *            distribuidos a los departamentos de las oficinas asociadas al
	 *            usuario. - true: se muestran; false: no se muestran;
	 *            (Required)
	 * @param entidad
	 *            (Required)
	 * @return
	 * @throws RegistroException
	 */
	public DistributionInfo[] getInputDistribution(UserInfo user,
			Integer state, Integer firstRow, Integer maxResults,
			Integer typeBookRegisterDist, Boolean oficAsoc, Entidad entidad)
			throws RegistroException;

	/**
	 * Obtenemos la lista de registros distribuidos recibidos en el bandeja de
	 * salida
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param state:
	 *            Estado de la distribucion (1.-Pendiente, 2.-Aceptador,
	 *            3.-Archivado, 4.-Rechazado , 5.-Redistribuido) (Required)
	 * @param firstRow:
	 *            Primer registro de la lista de resultados (Required - Default:
	 *            0)
	 * @param maxResults:
	 *            Número máximos de resultados devueltos (Required - Default: 0)
	 * @param typeBookRegisterDist:
	 *            0.- Registros de entrada y de salida que son distribuciones de
	 *            entrada; 1.- Registros de entrada que son distribuciones de
	 *            entrada; 2.- Registros de salida que son distribuciones de
	 *            entrada (Required)
	 * @param oficAsoc:
	 *            este parametro indica si se muestran los registros
	 *            distribuidos a los departamentos de las oficinas asociadas al
	 *            usuario. - true: se muestran; false: no se muestran;
	 *            (Required)
	 * @param entidad
	 *            (Required)
	 * @return
	 * @throws RegistroException
	 */
	public DistributionInfo[] getOutputDistribution(UserInfo user,
			Integer state, Integer firstRow, Integer maxResults,
			Integer typeBookRegisterDist, Boolean oficAsoc, Entidad entidad)
			throws RegistroException;

	/**
	 * Método que devuelve el número de registros distribuidos en la bandeja de
	 * entrada en función de los criterios espeficados
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param state:
	 *            Estado de la distribucion (1.-Pendiente, 2.-Aceptador,
	 *            3.-Archivado, 4.-Rechazado , 5.-Redistribuido) (Required)
	 * @param typeBookRegisterDist:
	 *            0.- Registros de entrada y de salida que son distribuciones de
	 *            entrada; 1.- Registros de entrada que son distribuciones de
	 *            entrada; 2.- Registros de salida que son distribuciones de
	 *            entrada (Required)
	 * @param oficAsoc:
	 *            este parametro indica si se muestran los registros
	 *            distribuidos a los departamentos de las oficinas asociadas al
	 *            usuario. - true: se muestran; false: no se muestran;
	 *            (Required)
	 * @param entidad
	 *            (Required)
	 * @return
	 * @throws RegistroException
	 */
	public Integer countInputDistribution(UserInfo user, Integer state,
			Integer typeBookRegisterDist, Boolean oficAsoc, Entidad entidad)
			throws RegistroException;

	/**
	 * Método que devuelve el número de registros distribuidos en la bandeja de
	 * salida en función de los criterios espeficados
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param state:
	 *            Estado de la distribucion (1.-Pendiente, 2.-Aceptador,
	 *            3.-Archivado, 4.-Rechazado , 5.-Redistribuido) (Required)
	 * @param typeBookRegisterDist:
	 *            0.- Registros de entrada y de salida que son distribuciones de
	 *            entrada; 1.- Registros de entrada que son distribuciones de
	 *            entrada; 2.- Registros de salida que son distribuciones de
	 *            entrada (Required)
	 * @param oficAsoc:
	 *            este parametro indica si se muestran los registros
	 *            distribuidos a los departamentos de las oficinas asociadas al
	 *            usuario. - true: se muestran; false: no se muestran;
	 *            (Required)
	 * @param entidad
	 *            (Required)
	 * @return
	 * @throws RegistroException
	 */
	public Integer countOutputDistribution(UserInfo user, Integer state,
			Integer typeBookRegisterDist, Boolean oficAsoc, Entidad entidad)
			throws RegistroException;

	/**
	 * Método para aceptar una distribución. Este método utiliza como criterio
	 * de busqueda el número del registro. Se comprueban los permisos del
	 * usuario que realiza la operación. Es necesario que sea un usuario de
	 * Registro Presencial
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param registerNumber:
	 *            Número de registro del registro distribuido (Required)
	 * @param bookId:
	 *            Identificador del Libro sobre el que realizaremos la operación
	 *            (Required)
	 * @param entidad:
	 *            (Required)
	 * @throws RegistroException
	 */
	public void acceptDistribution(UserInfo user, String registerNumber,
			Integer bookId, Entidad entidad) throws RegistroException;

	/**
	 * Método para aceptar una distribución. Este método esta realizado
	 * cumpliendo los requisitos fijados por la Tramitacion de Expedientes
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param distributionId:
	 *            Identificador de la distribucion (Required)
	 * @param entidad
	 *            (Required)
	 * @throws RegistroException
	 */
	public void acceptDistribution(UserInfo user, Integer distributionId,
			Entidad entidad) throws RegistroException;

	/**
	 * Método para rechazar una distribución. ste método utiliza como criterio
	 * de busqueda el número del registro. Se comprueban los permisos del
	 * usuario que realiza la operación. Es necesario que sea un usuario de
	 * Registro Presencial
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param registerNumber:
	 *            Número de registro del registro distribuido (Required)
	 * @param remarks:
	 *            Comentario sobre el porqué del rechazo (Required)
	 * @param entidad:
	 *            (Required)
	 * @throws RegistroException
	 */
	public void rejectDistribution(UserInfo user, String registerNumber,
			String remarks, Entidad entidad) throws RegistroException;

	/**
	 * Método para rechazar una distribución. Este método esta realizado
	 * cumpliendo los requisitos fijados por la Tramitacion de Expedientes
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param distributionId:
	 *            Identificador de la distribucion (Required)
	 * @param remarks:
	 *            Comentario sobre el porqué del rechazo (Required)
	 *
	 * @param entidad
	 *            (Required)
	 * @throws RegistroException
	 */
	public void rejectDistribution(UserInfo user, Integer distributionId,
			String remarks, Entidad entidad) throws RegistroException;

	/**
	 * Método para archivar una distribución. ste método utiliza como criterio
	 * de busqueda el número del registro. Se comprueban los permisos del
	 * usuario que realiza la operación. Es necesario que sea un usuario de
	 * Registro Presencial
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param registerNumber:
	 *            Número de registro del registro distribuido (Required)
	 * @param bookId:
	 *            Identificador del Libro sobre el que realizaremos la operación
	 *            (Required)
	 * @param entidad:
	 *            (Required)
	 * @throws RegistroException
	 */
	public void archiveDistribution(UserInfo user, String registerNumber,
			Integer bookId, Entidad entidad) throws RegistroException;

	/**
	 * Método para archivar una distribución. Este método esta realizado
	 * cumpliendo los requisitos fijados por la Tramitacion de Expedientes
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param distributionId:
	 *            Identificador de la distribucion (Required)
	 * @param entidad
	 *            (Required)
	 * @throws RegistroException
	 */
	public void archiveDistribution(UserInfo user, Integer distributionId,
			Entidad entidad) throws RegistroException;

	/**
	 * Método para la redistribución de un registro que está en la bandeja de
	 * entrada de distribuciones
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param registerNumber:
	 *            Número de registro del registro distribuido (Required)
	 * @param code:
	 *            Código del organismo al que se redistribuye el registro
	 *            (Required)
	 * @param entidad
	 *            (Required)
	 * @throws RegistroException
	 */
	public void changeInputDistribution(UserInfo user, String registerNumber,
			String code, Entidad entidad) throws RegistroException;

	/**
	 * Método para la redistribución de un registro que está en la bandeja de
	 * salida de distribuciones
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param registerNumber:
	 *            Número de registro del registro distribuido (Required)
	 * @param code:
	 *            Código del organismo al que se redistribuye el registro
	 *            (Required)
	 * @param entidad
	 *            (Required)
	 * @throws RegistroException
	 */
	public void changeOutputDistribution(UserInfo user, String registerNumber,
			String code, Entidad entidad) throws RegistroException;

	/**
	 * Método para añadir documentos a un registro en concreto.
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param bookId:
	 *            Identificador del Libro sobre el que realizaremos la operación
	 *            (Required)
	 * @param folderId:
	 *            Identificador del registro que deseamos actualizar (Required)
	 * @param documents:
	 *            Documentos que vamos a añadir (Required)
	 * @param entidad
	 *            (Required)
	 * @throws RegistroException
	 */
	public void addDocument(UserInfo user, Integer bookId, Integer folderId,
			DocumentInfo[] documents, Entidad entidad) throws RegistroException;

	/**
	 * Método para realizar la consolidación de un registro telematico en el
	 * registro persencial.
	 *
	 * Este método es similar al <code>importFolder</code>. Con la
	 * particularidad de que este cumple los requisitos de la consolidacion
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param bookId:
	 *            Identificador del Libro sobre el que realizaremos la operación
	 *            (Required)
	 * @param atts:
	 *            Valores de los campos del registro (Required)
	 * @param inter:
	 *            Valor de los interesados (Remitentes o Destinatarios)
	 *            asociados al registro (Opcional)
	 * @param documents:
	 *            Documentos adjuntos al registro (Opcional)
	 * @param entidad
	 *            (Required)
	 * @return
	 * @throws RegistroException
	 */
	public RegisterInfo consolidateFolder(UserInfo user, Integer bookId,
			FieldInfo[] atts, PersonInfo[] inter, DocumentInfo[] documents,
			Entidad entidad) throws RegistroException;

	/**
	 * Método para obtener los datos de los destinatarios de un registro de
	 * salida
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param folderNumber:
	 *            Identificador del registro sobre el que relizamos la operacion
	 *            (Required)
	 * @param entidad
	 *            (Required)
	 * @return
	 * @throws RegistroException
	 */
	public PersonInfo[] getInterestedOutputRegister(UserInfo user,
			String folderNumber, Entidad entidad) throws RegistroException;

	/**
	 * Método para obtener los datos de los remitentes de un registro de entrada
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param folderNumber:
	 *            Identificador del registro sobre el que relizamos la operacion
	 *            (Required)
	 * @param entidad
	 *            (Required)
	 * @return
	 * @throws RegistroException
	 */
	public PersonInfo[] getInterestedInputRegister(UserInfo user,
			String folderNumber, Entidad entidad) throws RegistroException;

	/**
	 * Método para obtener los datos de un registro entrada y los datos
	 * completos de los remitentes
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param folderNumber:
	 *            Identificador del registro sobre el que relizamos la operacion
	 *            (Required)
	 * @param entidad
	 *            (Required)
	 * @return
	 * @throws RegistroException
	 */
	public RegisterWithPagesInfoPersonInfo getInputRegister(UserInfo user,
			String folderNumber, Entidad entidad) throws RegistroException;

	/**
	 * Método para obtener los datos de un registro salida y los datos completos
	 * de los destinatarios
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param folderNumber:
	 *            Identificador del registro sobre el que relizamos la operacion
	 *            (Required)
	 * @param entidad
	 *            (Required)
	 * @return
	 * @throws RegistroException
	 */
	public RegisterWithPagesInfoPersonInfo getOutputRegister(UserInfo user,
			String folderNumber, Entidad entidad) throws RegistroException;

	/**
	 * Método que comprueba si un usuario puede crear un registro o no.
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param bookType:
	 *            Tipo de libro (1.- Libro de Entrada; 2.- Libro de Salida)
	 *            (Required)
	 * @param oficAsoc:
	 *            Si tiene un valor distinto de 0 no comprueba permisos sobre
	 *            las oficinas asociadas al usuario (Required - Default 0)
	 * @param onlyOpenBooks:
	 *            Si tiene un valor distinto de 0 comprueba solo los libros que
	 *            no estan cerrados (Required - Default 0)
	 * @param entidad
	 *            (Required)
	 * @return
	 * @throws RegistroException
	 */
	public Boolean canCreateRegister(UserInfo user, Integer bookType,
			Integer oficAsoc, Integer onlyOpenBooks, Entidad entidad)
			throws RegistroException;

	/**
	 * Método para obtener los libros de un determinado tipo
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param bookType:
	 *            Tipo de libro (1.- Libro de Entrada; 2.- Libro de Salida)
	 *            (Required)
	 * @param oficAsoc:
	 *            Si tiene un valor distinto de 0 no comprueba permisos sobre
	 *            las oficinas asociadas al usuario (Required - Default 0)
	 * @param onlyOpenBooks:
	 *            Si tiene un valor distinto de 0 comprueba solo los libros que
	 *            no estan cerrados (Required - Default 0)
	 * @param entidad
	 *            (Required)
	 * @return
	 * @throws RegistroException
	 */
	public BookInfo[] getBooksForType(UserInfo user, Integer bookType,
			Integer oficAsoc, Integer onlyOpenBooks, Entidad entidad)
			throws RegistroException;

	/**
	 * Método que nos devuelve las oficinas de un usuario que tienen permisos
	 * para crear un registro en un determinado libro
	 *
	 * Este método esta realizado siguiendo los criterios definidos por la
	 * tramitación de expedientes
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param bookId:
	 *            Identificador del Libro sobre el que realizaremos la operación
	 *            (Required)
	 * @param entidad
	 *            (Required)
	 * @return
	 * @throws RegistroException
	 */
	public OfficeInfo[] getOfficeCanCreateRegisterByUser(UserInfo user,
			Integer bookID, Entidad entidad) throws RegistroException;

	/**
	 * Método que comprueba si existe un tipo de asunto asociado a una
	 * oficina sobre la que el usuario que realiza la operación tiene
	 * permisos
	 *
	 * @param user:
	 *            Datos del usuario que realiza la operación (Required)
	 * @param matterTypeCode:
	 *            Código del tipo de asunto (Required)
	 * @param officeCode:
	 *           Código de oficina (Required)
	 * @param entidad:
	 *            (Required)
	 * @return
	 * @throws RegistroException
	 */
	public Boolean existMatterTypeInOffice(UserInfo user, String matterTypeCode,
			String officeCode, Entidad entidad) throws RegistroException;
}
